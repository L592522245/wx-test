package com.test.weixin;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.test.weixin.main.TemplateMsg3;

public class MsgTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		TemplateMsg3.sendMsg();
	}

}
