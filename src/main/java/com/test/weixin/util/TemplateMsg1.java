package com.test.weixin.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.weixin.domain.Template;
import com.test.weixin.domain.TemplateParam;

public class TemplateMsg1 {
	@Autowired
	private static RedisCacheManager redisCacheManager;

	public static int sendMsg() throws ClientProtocolException, IOException {
		Object tk = redisCacheManager.get("wx_token");
		if (tk == null) {
			String token = WeixinUtil.getAccessToken().getAccessToken();
			redisCacheManager.set("wx_token", token, 7000);
			tk = redisCacheManager.get("wx_token");
		}
		// 调用接口获取access_token
		String at = tk.toString();

		Template tem = new Template();
		tem.setTemplateId("JelPY46SfxguVkNqzWZgjQLsOHYOoC4shzoA1e5lI");
		tem.setToUser("oN5ev6Rq3vobSe30GTRnBdWIU");
		tem.setUrl("");

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());

		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "你点击了模板消息按钮", "#173177"));
		paras.add(new TemplateParam("name", "用户", "#173177"));
		paras.add(new TemplateParam("time", date, "#173177"));
		paras.add(new TemplateParam("content", "模板消息样例", "#173177"));

		tem.setTemplateParamList(paras);
		return WeixinUtil.sendTemplateMsg(at, tem);
	}
}
