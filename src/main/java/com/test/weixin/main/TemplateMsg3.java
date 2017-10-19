package com.test.weixin.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.test.weixin.domain.req.Template;
import com.test.weixin.domain.req.TemplateParam;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

public class TemplateMsg3 {
	public static void sendMsg() throws ClientProtocolException, IOException {
		// 调用接口获取access_token
		String at = (String) TokenUtil.getToken().get("access_token");
		
		// 模板id
		String templateId = "KO_XaQTs8fy_hhNZDZZpqXqOIeBonhYVJKy1ONjKdBw";
		
		Object[] userList = WeixinUtil.userList(at);
		
		for(int i = 0; i < userList.length; i++) {
			String userName = WeixinUtil.userInfo(at, userList[i].toString()).getNickname();
			
			Template tem = new Template();
			tem.setTemplateId(templateId);
			tem.setToUser(userList[i].toString());
			tem.setUrl("");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"HH:mm:ss");
			String date = dateFormat.format(new Date());
			
			List<TemplateParam> paras = new ArrayList<TemplateParam>();
			paras.add(new TemplateParam("name", userName, "#459ae9"));
			paras.add(new TemplateParam("time", date, "#459ae9"));
			
			tem.setTemplateParamList(paras);
			WeixinUtil.sendTemplateMsg(at, tem);
		}
	}
}
