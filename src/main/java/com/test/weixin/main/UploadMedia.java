package com.test.weixin.main;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.client.ClientProtocolException;

import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

public class UploadMedia {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws ClientProtocolException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws KeyManagementException, ClientProtocolException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		String path = "F:/Program Files/myeclipse workspace/wx-test/src/main/webapp/media/voice/超能陆战队大白的balalala.mp3";
		String at = (String) TokenUtil.getToken().get("access_token");
        JSONObject object = WeixinUtil.uploadMedia(at, "voice", path);
        System.out.println(object.toString());
	}

}
