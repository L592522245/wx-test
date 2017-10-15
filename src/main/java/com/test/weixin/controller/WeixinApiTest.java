package com.test.weixin.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.domain.userInfo.UserInfo;
import com.test.weixin.util.SignUtil;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

@Controller
@RequestMapping("/apiTest")
public class WeixinApiTest {
	private static Logger log = LoggerFactory.getLogger(WeixinApiTest.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
	
	@RequestMapping(value = "/oAuth", method = RequestMethod.GET)
	public String oAuth(HttpServletRequest request, HttpServletResponse response) {
		String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		String appid = WeixinUtil.APPID;
		requestUrl = requestUrl.replace("APPID", appid).replace("REDIRECT_URI", "http%3a%2f%2fl1867227l4.iask.in%2fapiTest%2fgetUserInfo").replace("SCOPE", "snsapi_userinfo").replace("STATE", "1");
		
		return "redirect:" + requestUrl;
	}
	
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
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
			return "userInfo";
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
				return "userInfo";
			}
		}
	}
}
