package com.test.weixin.controller.alipay;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.test.pay.alipay.AlipayConfig;

/* *
 * 功能：支付宝手机网站alipay.trade.close (统一收单交易关闭接口)调试入口页面
 * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
 * 版本：2.0
 * 修改日期：2016-11-01
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 请确保项目文件有可写权限，不然打印不了日志。
 */
@Controller
@RequestMapping("/pay")
public class AlipayClose {
	@RequestMapping(value = "alipayClose", method = RequestMethod.POST)
	public void alipay(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AlipayApiException {
		if (request.getParameter("WIDout_trade_no") != null
				|| request.getParameter("WIDtrade_no") != null) {
			// 商户订单号和支付宝交易号不能同时为空。 trade_no、 out_trade_no如果同时存在优先取trade_no
			// 商户订单号，和支付宝交易号二选一
			String out_trade_no = request.getParameter("WIDout_trade_no");
			// 支付宝交易号，和商户订单号二选一
			String trade_no = request.getParameter("WIDtrade_no");
			/**********************/
			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
			AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL,
					AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
					AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayTradeCloseRequest alipay_request = new AlipayTradeCloseRequest();

			AlipayTradeCloseModel model = new AlipayTradeCloseModel();
			model.setOutTradeNo(out_trade_no);
			model.setTradeNo(trade_no);
			alipay_request.setBizModel(model);

			AlipayTradeCloseResponse alipay_response = client
					.execute(alipay_request);
			System.out.println(alipay_response.getBody());
		}
	}
}
