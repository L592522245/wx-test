package com.test.weixin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.test.pay.wxpay.MyConfig;
import com.test.weixin.Common;
import com.test.weixin.util.MessageUtil;
import com.test.weixin.util.SignUtil;
import com.test.weixin.util.TokenUtil;

@Controller
@RequestMapping("/pay")
public class Pay {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String doGet(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		/*
		 * 微信支付生成订单
		 * H5支付申请入口：登录商户平台-->产品中心-->我的产品-->支付产品-->H5支付
		 */
		MyConfig config = new MyConfig();
		
		//true为沙箱测试，正式环境改为WXPay wxpay = new WXPay(config);
		WXPay wxpay = new WXPay(config);
//        WXPay wxpay = new WXPay(config, WXPayConstants.SignType.MD5, true);

        //订单号
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(new Date()); 
		//用户ip
		String ip = getIp(request);
		
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", "商品描述商品描述商品描述");
        data.put("out_trade_no", date);  //商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
        data.put("device_info", "WEB");  //设备号终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");      //订单总金额，单位为分
        data.put("spbill_create_ip", ip);  //必须传正确的用户端IP
        data.put("notify_url", Common.HTTP + "/pay/wxpayNotify");  //接收微信支付异步通知回调地址
        data.put("trade_type", "MWEB");  //交易类型，H5支付的交易类型为MWEB
        data.put("product_id", "12");  //trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
        
        //调用微信jssdk
  		String jsapi_ticket = (String) TokenUtil.getTicket().get("ticket");
  		String url = request.getRequestURL() + "?" + request.getQueryString();
  		int i = url.indexOf("#");
  		if(i != -1) {
  			url = url.substring(url.indexOf("#"));
  		}
  		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url);
  		
  		model.put("timestamp", ret.get("timestamp"));
  		model.put("nonceStr", ret.get("nonceStr"));
  		model.put("signature", ret.get("signature"));
        model.put("appid", config.getAppID());

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
        	String prepay_id = resp.get("prepay_id");
        	//mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
        	//可以在MWEB_URL后拼接上redirect_url参数，来指定回调页面。
        	String mweb_url = resp.get("mweb_url");
        	mweb_url = mweb_url + "&redirect_url=" + Common.urlencodeWXPayReturn;
        	
        	//获得prepay_id后再进行一次签名，参与签名的参数有appId, timeStamp, nonceStr, package, signType
        	Map<String, String> map2 = new HashMap<String, String>(); 
        	map2.put("appId", config.getAppID()); 
        	map2.put("timeStamp", ret.get("timestamp")); 
        	map2.put("nonceStr", ret.get("nonceStr")); 
        	map2.put("package", "prepay_id="+prepay_id); 
        	map2.put("signType", "MD5"); 
        	String paySign = WXPayUtil.generateSignature(map2, config.getKey());
            
        	model.put("prepay_id", prepay_id);
        	model.put("paySign", paySign);
        	model.put("mweb_url", mweb_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return "pay";
	}

	// 获取用户ip
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
