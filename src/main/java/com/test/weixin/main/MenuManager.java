package com.test.weixin.main;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weixin.Common;
import com.test.weixin.domain.menu.Button;
import com.test.weixin.domain.menu.CommonButton;
import com.test.weixin.domain.menu.ComplexButton;
import com.test.weixin.domain.menu.Menu;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

/**
 * 描述:菜单管理器类 </br>
 * 直接运行即可创建菜单
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// 调用接口获取access_token
		String at = (String) TokenUtil.getToken().get("access_token");

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at);

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("多类型消息");
		btn11.setType("click");
		btn11.setKey("11");
		
		CommonButton btn12 = new CommonButton();
		btn12.setName("模板消息");
		btn12.setType("click");
		btn12.setKey("12");
		
		CommonButton btn31 = new CommonButton();
		btn31.setName("手机短信验证");
		btn31.setType("view");
		btn31.setUrl(Common.HTTP + "/apiTest");
		
		CommonButton btn32 = new CommonButton();
		btn32.setName("发送模板消息");
		btn32.setType("view");
		btn32.setUrl(Common.HTTP + "/apiTest/sendMsg");

		/**
		 * 微信： mainBtn1,mainBtn2,mainBtn3底部的三个菜单。
		 */

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("消息");
		mainBtn1.setSub_button(new CommonButton[] { btn11, btn12 });

		CommonButton mainBtn2 = new CommonButton();
		mainBtn2.setName("聊天室");
		mainBtn2.setType("view");
		mainBtn2.setUrl(Common.HTTP + "/apiTest/oAuth");

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("其他");
		mainBtn3.setSub_button(new CommonButton[] { btn31, btn32 });

		/**
		 * 封装整个菜单
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
