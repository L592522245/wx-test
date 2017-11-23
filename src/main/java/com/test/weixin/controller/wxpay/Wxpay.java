package com.test.weixin.controller.wxpay;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pay")
public class Wxpay {
	@RequestMapping(value = "wxpayNotify", method = RequestMethod.POST)
	public void wxpayNotify(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
	}
	
	@RequestMapping(value = "wxpayReturn", method = RequestMethod.POST)
	public void wxpayReturn(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
	}
}
