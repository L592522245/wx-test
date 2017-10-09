package com.test.weixin;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.util.WeixinUtil;

public class WeixinTest {
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		AccessToken token = WeixinUtil.getAccessToken();
		System.out.println("AccessToken: " + token.getAccessToken());
		System.out.println("ExpiresIn: " + token.getExpiresIn());
	}

}
