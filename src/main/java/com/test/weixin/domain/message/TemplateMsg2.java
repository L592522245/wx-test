package com.test.weixin.domain.message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.test.weixin.domain.Common;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

public class TemplateMsg2 {
	public static int sendMsg(String fromUserName) throws ClientProtocolException, IOException {
		// 调用接口获取access_token
		String at = (String) TokenUtil.getToken().get("access_token");
		
		// 模板id
		String templateId = "Og_7rixmk48zJtWs3mnxWgm7a5u2qx6F3k9qenj4zrY";
		
		String userName = WeixinUtil.userInfo(at, fromUserName).getNickname();

		Template tem = new Template();
		tem.setTemplateId(templateId);
		tem.setToUser(fromUserName);
		tem.setUrl(Common.HTTP + "/apiTest");

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());

		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("first", "你点击了子菜单按钮", "#173177"));
		paras.add(new TemplateParam("name", userName, ""));
		paras.add(new TemplateParam("time", date, ""));
		paras.add(new TemplateParam("content", "模板消息样例", "#173177"));

		tem.setTemplateParamList(paras);
		return WeixinUtil.sendTemplateMsg(at, tem);
	}
}
