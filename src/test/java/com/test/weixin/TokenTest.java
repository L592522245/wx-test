package com.test.weixin;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

public class TokenTest {
	@Test
	public void testGetToken3() {
		Map<String, Object> token = TokenUtil.getToken();
		System.out.println(token.get("access_token"));
		System.out.println(token.get("expires_in"));
	}

	@Test
	public void testSaveToken4() throws ClientProtocolException, IOException {
		AccessToken token = WeixinUtil.getAccessToken();
		TokenUtil.saveToken(token);
	}

}
