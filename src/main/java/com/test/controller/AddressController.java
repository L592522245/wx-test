package com.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.test.pojo.Address;
import com.test.service.IAddressService;

/*
 * 4、处理器适配器找到相应处理器，将处理结果和视图封装到ModelAndView并返回给spring-mvc.xml。
 */
@Controller                // 表示该类为处理器
@RequestMapping("/test")   // 处理器方法，该方法要对value属性指定的URL进行处理与响应
public class AddressController {
	// 自动扫描装配bean，spring会自动装配该使用哪个类
	@Autowired                    
	// 用来区分service的不同实现类，值为@Service(@Component)中设置的值
	@Qualifier("addressService")  
	private IAddressService addressService;
	
	@RequestMapping(value = "/getAddress", method = RequestMethod.GET)
	public String getAddress(ModelMap model) {
		List<Address> address = addressService.queryList();
		model.put("address", address);
		List<Address> country = addressService.getCountry();
		model.put("country", country);
		return "getAddress";
	}
}
