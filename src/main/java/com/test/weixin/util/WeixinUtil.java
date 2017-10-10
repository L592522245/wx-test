package com.test.weixin.util;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.domain.menu.Menu;
import com.test.weixin.domain.message.Template;
import com.test.weixin.domain.userInfo.UserInfo;

/**
 * 描述: 公众平台通用接口工具类 </br>
 */
public class WeixinUtil {

	public static final String APPID = "wxbb4ca07ffff6a141";
	public static final String APPSECRET = "c7279e47b76346357787a35602ba7c48";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 获取用户列表
	 * 
	 * @param token
	 * @param openId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Object[] userList(String token)
			throws ClientProtocolException, IOException {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token).replace(
				"next_openid", "");

		Object[] userList = null;
		JSONObject jsonObject = doGetStr(requestUrl);
		if (jsonObject != null) {
			JSONArray jsonArray = jsonObject.getJSONObject("data")
					.getJSONArray("openid");
			userList = jsonArray.toArray();
		}
		return userList;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 * @param openId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static UserInfo userInfo(String token, String openId)
			throws ClientProtocolException, IOException {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token).replace(
				"OPENID", openId);

		UserInfo userInfo = new UserInfo();
		JSONObject jsonObject = doGetStr(requestUrl);
		if (jsonObject != null) {
			userInfo.setNickname(jsonObject.getString("nickname"));
		}
		return userInfo;
	}

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static int createMenu(Menu menu, String accessToken)
			throws ClientProtocolException, IOException {
		int result = 0;
		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = doPostStr(url, jsonMenu);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param token
	 * @param template
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static int sendTemplateMsg(String token, Template template)
			throws ClientProtocolException, IOException {
		int result = 0;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

		JSONObject jsonResult = doPostStr(requestUrl, template.toJSON());
		if (jsonResult != null) {
			int errorCode = jsonResult.getInt("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				result = 0;
			} else {
				System.out
						.println("模板消息发送失败:" + errorCode + "," + errorMessage);
				result = jsonResult.getInt("errcode");
			}
		}
		return result;
	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ClientProtocolException,
			IOException {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			token.setAccessToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}

	@SuppressWarnings("deprecation")
	public static JSONObject doGetStr(String url)
			throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}

	@SuppressWarnings("deprecation")
	public static JSONObject doPostStr(String url, String outStr)
			throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
}
