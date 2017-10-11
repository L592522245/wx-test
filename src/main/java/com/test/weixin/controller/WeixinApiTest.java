package com.test.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.domain.userInfo.UserInfo;
import com.test.weixin.util.WeixinUtil;

@Controller
@RequestMapping("/apiTest")
public class WeixinApiTest {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		return "wx-test";
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	@RequestMapping(value = "oAuth", method = RequestMethod.GET)
	public void oAuth(HttpServletRequest request, HttpServletResponse response) {
		String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		String appid = WeixinUtil.APPID;
		requestUrl = requestUrl.replace("APPID", appid).replace("REDIRECT_URI", "http://1m609e2841.iask.in/apiTest/hintPage").replace("SCOPE", "snsapi_userinfo").replace("state", "1");
		
		try {
			response.sendRedirect(requestUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "hintPage", method = RequestMethod.GET)
	public String hintPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws ClientProtocolException, IOException {
		
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		AccessToken accessToken = WeixinUtil.getWebAccessToken(code);
		String token = accessToken.getAccessToken();
		String openid = accessToken.getOpenid();
		
		session.setAttribute("token", token);
		session.setAttribute("openid", openid);
		UserInfo userInfo = new UserInfo();
		if(session.getAttribute("token") != null) {
			userInfo = WeixinUtil.userInfoFromWeb(session.getAttribute("token").toString(), session.getAttribute("openid").toString());
		} else {
			userInfo = WeixinUtil.userInfoFromWeb(accessToken.getAccessToken(), accessToken.getOpenid());
		}
		
		model.put("userInfo", userInfo);
		
		return "hintPage";
	}
}
