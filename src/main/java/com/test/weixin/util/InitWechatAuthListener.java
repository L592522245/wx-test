package com.test.weixin.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.test.weixin.domain.AccessToken;

/**
 * 初始化生成微信token
 * 
 * @author phil
 * @date 2017年7月9日
 * 
 */
@Component
public class InitWechatAuthListener implements
		ApplicationListener<ContextRefreshedEvent> {

	// private static final Logger logger =
	// Logger.getLogger(InitWechatAuthListener.class);

	@Autowired
	private RedisCacheManager redisCacheManager;

	private static boolean isStart = false; // 防止二次调用

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!isStart) {
			isStart = true;
			String token = null;
			try {
				token = WeixinUtil.getAccessToken().getAccessToken();
				System.out.println("获取access_token成功，access_token：" + token);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			redisCacheManager.set("wx_token", token, 7000);
		}
	}
}