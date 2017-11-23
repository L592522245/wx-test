package com.test.weixin.controller.alipay;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.test.pay.alipay.AlipayConfig;

/* *
 * 功能：支付宝手机网站alipay.trade.query (统一收单线下交易查询)调试入口页面
 * 版本：2.0
 * 修改日期：2016-11-28
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 请确保项目文件有可写权限，不然打印不了日志。
 */
@Controller
@RequestMapping("/query")
public class AlipayQuery {
	// 支付宝订单查询
	@RequestMapping(value = "alipayQuery", method = RequestMethod.POST)
	public void alipayQuery(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, AlipayApiException {
		if (request.getParameter("WIDout_trade_no") != null
				|| request.getParameter("WIDtrade_no") != null) {
			// 商户订单号，商户网站订单系统中唯一订单号，必填
			String out_trade_no = request.getParameter("WIDout_trade_no");
			// 支付宝交易号
			String trade_no = request.getParameter("WIDtrade_no");
			/**********************/
			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
			AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL,
					AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
					AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();

			AlipayTradeQueryModel model = new AlipayTradeQueryModel();
			model.setOutTradeNo(out_trade_no);
			model.setTradeNo(trade_no);
			alipay_request.setBizModel(model);

			AlipayTradeQueryResponse alipay_response = client
					.execute(alipay_request);
			System.out.println(alipay_response.getBody());
		}
	}
}
