package com.test.weixin.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.test.weixin.Common;
import com.test.weixin.domain.req.Template;
import com.test.weixin.domain.req.TemplateParam;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

public class TemplateMsg4 {
	public static void sendMsg(String title, String name, String content, String date, String id) throws ClientProtocolException, IOException {
		// 调用接口获取access_token
		String at = (String) TokenUtil.getToken().get("access_token");
		
		// 模板id
		String templateId = "zaazOvUouAKumgJR3IOCTfyGpoxxstQmSz7srBLNlXs";
		
		Object[] userList = WeixinUtil.userList(at);
		
		for(int i = 0; i < userList.length; i++) {
			Template tem = new Template();
			tem.setTemplateId(templateId);
			tem.setToUser(userList[i].toString());
			tem.setUrl(Common.HTTP + "/apiTest/msg?id=" + id);
			
			String[] cont = content.split("lineBreak");
			content = StringUtils.join(cont, "\\n");
			
			List<TemplateParam> paras = new ArrayList<TemplateParam>();
			paras.add(new TemplateParam("title", title, "#173177"));
			paras.add(new TemplateParam("name", name, "#173177"));
			paras.add(new TemplateParam("time", date, "#173177"));
			paras.add(new TemplateParam("content", content, "#173177"));
			
			tem.setTemplateParamList(paras);
			WeixinUtil.sendTemplateMsg(at, tem);
		}
		
		/*Template tem = new Template();
		tem.setTemplateId(templateId);
		tem.setToUser("oN_5ev6R_q_3vobSe30GTRnBdWIU");
		tem.setUrl(Common.HTTP + "/apiTest/msg?id=" + id);
		
		String[] cont = content.split("lineBreak");
		content = StringUtils.join(cont, "\\n");
		
		List<TemplateParam> paras = new ArrayList<TemplateParam>();
		paras.add(new TemplateParam("title", title, "#173177"));
		paras.add(new TemplateParam("name", name, "#173177"));
		paras.add(new TemplateParam("time", date, "#173177"));
		paras.add(new TemplateParam("content", content, "#173177"));
		
		tem.setTemplateParamList(paras);
		WeixinUtil.sendTemplateMsg(at, tem);*/
	}
}
