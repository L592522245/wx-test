package com.test.weixin.domain.message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

public class TemplateMsg1 {
	
	public static int sendMsg(String fromUserName) throws ClientProtocolException, IOException {
		// 调用接口获取access_token
		String at = (String) TokenUtil.getToken().get("access_token");
		
		// 模板id
		String templateId = "Je_lPY46SfxguVkNqz_WZgjQLsOHYOoC4shzoA1e5lI";
		
		String userName = WeixinUtil.userInfo(at, fromUserName).getNickname();

		Template tem = new Template();
		tem.setTemplateId(templateId);
		tem.setToUser(fromUserName);
		tem.setUrl("http://1m609e2841.iask.in/apiTest");

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());

		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "你点击了模板消息按钮", "#173177"));
		paras.add(new TemplateParam("name", userName, "#173177"));
		paras.add(new TemplateParam("time", date, "#173177"));
		paras.add(new TemplateParam("content", "模板消息样例", "#173177"));

		tem.setTemplateParamList(paras);
		return WeixinUtil.sendTemplateMsg(at, tem);
	}
}
