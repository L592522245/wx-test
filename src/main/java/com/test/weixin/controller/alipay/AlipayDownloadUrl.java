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
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.test.pay.alipay.AlipayConfig;

/* *
 * 功能：支付宝手机网站alipay.data.dataservice.bill.downloadurl.query (查询对账单下载地址)接口调试入口页面
 * 版本：2.0
 * 修改日期：2016-11-01
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 请确保项目文件有可写权限，不然打印不了日志。
 */
@Controller
@RequestMapping("/pay")
public class AlipayDownloadUrl {
	// 查询支付宝对账单下载地址
	@RequestMapping(value = "alipayDownloadUrl", method = RequestMethod.POST)
	public void alipay(HttpServletRequest request, HttpServletResponse response)
			throws IOException, AlipayApiException {
		if (request.getParameter("WIDbill_type") != null
				&& request.getParameter("WIDbill_date") != null) {
			// 账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型：trade、signcustomer；
			// trade指商户基于支付宝交易收单的业务账单；signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单；
			String bill_type = request.getParameter("WIDbill_type");
			// 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
			String bill_date = request.getParameter("WIDbill_date");
			/**********************/
			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
			AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL,
					AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
					AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayDataDataserviceBillDownloadurlQueryRequest alipay_request = new AlipayDataDataserviceBillDownloadurlQueryRequest();

			AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
			model.setBillType(bill_type);
			model.setBillDate(bill_date);
			alipay_request.setBizModel(model);

			AlipayDataDataserviceBillDownloadurlQueryResponse alipay_response = client
					.execute(alipay_request);
			System.out.println(alipay_response.getBillDownloadUrl());
		}
	}
}
