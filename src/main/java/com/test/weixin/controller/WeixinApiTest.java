package com.test.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.qcloudsms.*;
import com.test.msg.domain.Msg;
import com.test.msg.service.MsgService;
import com.test.weixin.domain.AccessToken;
import com.test.weixin.domain.userInfo.UserInfo;
import com.test.weixin.main.TemplateMsg4;
import com.test.weixin.util.SignUtil;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

@Controller
@RequestMapping("/apiTest")
public class WeixinApiTest {
	private static Logger log = LoggerFactory.getLogger(WeixinApiTest.class);
	
	@Autowired
	private MsgService msgService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		return "smsVerify";
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	@RequestMapping(value = "getCode", method = RequestMethod.POST)
	public void sendSMS(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String phone = request.getParameter("phone");
		int code = (int) ((Math.random() * 9000) + 1000);
		
		// 无法申请签名，无法使用短信功能
		/*
		try {
	        SmsSingleSender sender = new SmsSingleSender(1400047698, "b54c34d6a8e90e7a67e3d64a2db2c043");
			SmsSingleSenderResult result = sender.send(0, "86", phone, "【abc】验证码测试" + code, "", "123");
			System.out.print(result);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		*/
		
		String codeStr = String.valueOf(code);
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		
		PrintWriter out = response.getWriter();
        out.print(codeStr);
        out.close();
	}
	
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	public void verify(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		int code;
		if(session.getAttribute("code") == null) {
			out.print("false");
			return;
		} else {
			code = (int) session.getAttribute("code");
		}
		
		int codeReq = Integer.parseInt(request.getParameter("code"));
		if(code == codeReq) {
	        out.print("apiTest/wxjs");
	        session.removeAttribute("code");
		} else {
			out.print("false");
		}
		out.close();
	}
	
	@RequestMapping(value = "wxjs", method = RequestMethod.GET)
	public String apiTest(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// 调用微信jssdk
		String jsapi_ticket = (String) TokenUtil.getTicket().get("ticket");
		String url = request.getRequestURL() + "?" + request.getQueryString();
		int i = url.indexOf("#");
		if(i != -1) {
			url = url.substring(url.indexOf("#"));
		}
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url);
		
		model.put("appid", WeixinUtil.APPID);
		model.put("timestamp", ret.get("timestamp"));
		model.put("nonceStr", ret.get("nonceStr"));
		model.put("signature", ret.get("signature"));
		return "api-test";
	}
	
	@RequestMapping(value = "/imgApi", method = RequestMethod.GET)
	public String imgApi(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String jsapi_ticket = (String) TokenUtil.getTicket().get("ticket");
		String url = request.getRequestURL() + "?" + request.getQueryString();
		int i = url.indexOf("#");
		if(i != -1) {
			url = url.substring(url.indexOf("#"));
		}
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url);
		
		model.put("appid", WeixinUtil.APPID);
		model.put("timestamp", ret.get("timestamp"));
		model.put("nonceStr", ret.get("nonceStr"));
		model.put("signature", ret.get("signature"));
		return "apiTest/imgApi";
	}
	
	@RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
	public String sendMsg(HttpServletRequest request, HttpServletResponse response) {
		
		return "sendMsg";
	}
	
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	public void _sendMsg(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");
		String date = dateFormat.format(new Date());
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date2 = dateFormat2.format(new Date());
		Timestamp now = Timestamp.valueOf(date2);
		
		Msg msg = new Msg();
		msg.setName(name);
		msg.setTitle(title);
		msg.setContent(content);
		msg.setCreateTime(now);
		msgService.addMsg(msg);
		
		String id = msg.getId();
		TemplateMsg4.sendMsg(title, name, content, date, id);
	}
	
	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	public String msg(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String id = request.getParameter("id");
		Msg msg = msgService.getMsgById(id);
		
		String[] cont = msg.getContent().split("lineBreak");
		for(int i = 0; i < cont.length; i++) {
			StringBuffer stringBuffer = new StringBuffer(cont[i]);
			cont[i] = stringBuffer.insert(0, "<p>").append("</p>").toString();
		}
		
		msg.setContent(StringUtils.join(cont, "").replace(" ", "&nbsp;"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");
		String date = dateFormat.format(msg.getCreateTime());
		
		model.put("msg", msg);
		model.put("time", date);
		return "msg";
	}
	
	@RequestMapping(value = "/hintPage", method = RequestMethod.GET)
	public String hintPage(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		return "hintPage";
	}
	
	/*@RequestMapping(value = "/submitTrade", method = RequestMethod.GET)
	public String submitTrade(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		model.put("price", "1.00");
		model.put("title", "苹果");
		model.put("desc", "红富士");
		
		return "submitTrade";
	}*/
	
	@RequestMapping(value = "/wxPay", method = RequestMethod.GET)
	public String wxPay(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String jsapi_ticket = (String) TokenUtil.getTicket().get("ticket");
		String url = request.getRequestURL() + "?" + request.getQueryString();
		int i = url.indexOf("#");
		if(i != -1) {
			url = url.substring(url.indexOf("#"));
		}
		Map<String, String> ret = SignUtil.sign(jsapi_ticket, url);
		
		model.put("appid", WeixinUtil.APPID);
		model.put("timestamp", ret.get("timestamp"));
		model.put("nonceStr", ret.get("nonceStr"));
		model.put("signature", ret.get("signature"));
		
		
		return "apiTest/wxPay";
	}
	
	// 微信登录
	@RequestMapping(value = "/oAuth", method = RequestMethod.GET)
	public String oAuth(HttpServletRequest request, HttpServletResponse response) {
		String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		String appid = WeixinUtil.APPID;
		requestUrl = requestUrl.replace("APPID", appid).replace("REDIRECT_URI", "http%3a%2f%2fl1867227l4.iask.in%2fapiTest%2fregister").replace("SCOPE", "snsapi_userinfo").replace("STATE", "1");
		
		return "redirect:" + requestUrl;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ClientProtocolException, IOException {
		HttpSession session = request.getSession();
		
		String code = request.getParameter("code");
		UserInfo userInfo = new UserInfo();
		
		if(session.getAttribute("code") == null) {
			session.setAttribute("code", "");
		}
		
		if(session.getAttribute("code").equals(code)) {
			userInfo = (UserInfo) session.getAttribute("userInfo");
			model.put("userInfo", userInfo);
			if(userInfo == null) {
				log.info("登陆失败！");
				return "hintPage";
			}
			log.info("登陆成功！openid：" + session.getAttribute("openid") + "，昵称：" + userInfo.getNickname());
			return "im/im";
		} else {
			session.setAttribute("code", code);
			AccessToken accessToken = WeixinUtil.getWebAccessToken(code);
			if(accessToken.getErrcode() != null) {
				log.info("错误代码：" + accessToken.getErrcode() + "，错误信息：" + accessToken.getErrmsg());
				return "redirect:oAuth";
			} else {
				session.setAttribute("accessToken", accessToken);
				session.setAttribute("openid", accessToken.getOpenid());
				
				userInfo = WeixinUtil.userInfoFromWeb(accessToken.getAccessToken(), accessToken.getOpenid());
				session.setAttribute("userInfo", userInfo);
				
				if(userInfo == null) {
					log.info("登陆失败！");
					return "hintPage";
				}
				
				model.put("userInfo", userInfo);
				log.info("登陆成功！openid：" + session.getAttribute("openid") + "，昵称：" + userInfo.getNickname());
				return "im/im";
			}
		}
	}
}
