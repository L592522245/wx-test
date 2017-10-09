package com.test.weixin.main;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.weixin.domain.Button;
import com.test.weixin.domain.CommonButton;
import com.test.weixin.domain.ComplexButton;
import com.test.weixin.domain.Menu;
import com.test.weixin.util.RedisCacheManager;
import com.test.weixin.util.WeixinUtil;

/**
 * 描述:菜单管理器类 </br>
 * 直接运行即可创建菜单
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	@Autowired  
    private static RedisCacheManager redisCacheManager;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Object tk = redisCacheManager.get("wx_token");
		if(tk == null){  
			String token = WeixinUtil.getAccessToken().getAccessToken();
			redisCacheManager.set("wx_token", token, 7000);
            tk = redisCacheManager.get("wx_token");
        }
				
		// 调用接口获取access_token
		String at = tk.toString();

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
		CommonButton btn31 = new CommonButton();
		btn31.setName("子菜单1");
		btn31.setType("click");
		btn31.setKey("31");

		/**
		 * 微信： mainBtn1,mainBtn2,mainBtn3底部的三个菜单。
		 */

		CommonButton mainBtn1 = new CommonButton();
		mainBtn1.setName("消息模板");
		mainBtn1.setType("click");
		mainBtn1.setKey("11");

		CommonButton mainBtn2 = new CommonButton();
		mainBtn2.setName("接口调用");
		mainBtn2.setType("view");
		mainBtn2.setUrl("http://1m609e2841.iask.in/apiTest");

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("子菜单");
		mainBtn3.setSub_button(new CommonButton[] { btn31});

		/**
		 * 封装整个菜单
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
