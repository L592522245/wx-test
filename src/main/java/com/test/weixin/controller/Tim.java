package com.test.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.weixin.domain.userInfo.UserInfo;
import com.test.weixin.service.UserInfoService;

@Controller
@RequestMapping("/tim")
public class Tim {
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	@ResponseBody
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String nickname = request.getParameter("nickname");
		
		Map parameterMap = new HashMap();
		parameterMap.put("nickname", nickname);
		List<UserInfo> userInfo = userInfoService.queryList(parameterMap);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\"data\":[");
		for(int i = 0; i < userInfo.size(); i++) {
			buffer.append("{");
			buffer.append(String.format("\"openid\":\"%s\"", userInfo.get(i).getOpenid())).append(",");  
			buffer.append(String.format("\"nickname\":\"%s\"", userInfo.get(i).getNickname())).append(",");  
			buffer.append(String.format("\"city\":\"%s\"", userInfo.get(i).getCity())).append(",");  
			buffer.append(String.format("\"country\":\"%s\"", userInfo.get(i).getCountry())).append(",");  
			buffer.append(String.format("\"province\":\"%s\"", userInfo.get(i).getProvince())).append(",");  
			buffer.append(String.format("\"sex\":\"%s\"", userInfo.get(i).getSex())).append(",");  
			buffer.append(String.format("\"headimgurl\":\"%s\"", userInfo.get(i).getHeadimgurl()));  
			buffer.append("}");
			if(userInfo.size() > 1) {
				buffer.append(",");
			}
		}
		buffer.append("]");
		buffer.append("}");
		String userInfoStr = buffer.toString();
		
		PrintWriter out = response.getWriter();
		if(userInfoStr == null) {
			out.print("none");
		} else {
			out.print(userInfoStr);
		}
		out.close();
	}
	
	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String chat(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String account = request.getParameter("account");
		model.put("account", account);
		return "im/chat";
	}
}
