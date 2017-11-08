package com.test.weixin.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.domain.ApiTicket;
import com.test.weixin.domain.menu.Menu;
import com.test.weixin.domain.req.Template;
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
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
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
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			userInfo.setNickname(jsonObject.getString("nickname"));
		}
		return userInfo;
	}
	
	/**
	 * 网页授权获取用户信息
	 * 
	 * @param token
	 * @param openId
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static UserInfo userInfoFromWeb(String token, String openId)
			throws ClientProtocolException, IOException {
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token).replace(
				"OPENID", openId);

		UserInfo userInfo = new UserInfo();
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			userInfo.setNickname(jsonObject.getString("nickname"));
			int sex = jsonObject.getInt("sex");
			if(sex == 1) {
				userInfo.setSex("男");
			} else if(sex == 2) {
				userInfo.setSex("女");
			} else {
				userInfo.setSex("未知");
			}
			userInfo.setProvince(jsonObject.getString("province"));
			userInfo.setCity(jsonObject.getString("city"));
			userInfo.setCountry(jsonObject.getString("country"));
			userInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			userInfo.setUnionid(jsonObject.getString("unionid"));
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
		String requestUrl = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpsRequest(requestUrl, "POST", jsonMenu);
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

		JSONObject jsonResult = httpsRequest(requestUrl, "POST", template.toJSON());
		if (jsonResult != null) {
			int errorCode = jsonResult.getInt("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				result = 0;
			} else {
				System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
				result = jsonResult.getInt("errcode");
			}
		}
		return result;
	}
	
	/**
	 * 上传素材
	 * 
	 * @param token
	 * @param template
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static JSONObject uploadMedia(String token, String type, String fileUrl)
			throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token).replace("TYPE", type);
		
		File file = new File(fileUrl);
		String result = connectHttpsByPost(requestUrl, null, file);
        result = result.replaceAll("[\\\\]", "");
        System.out.println("result:" + result);
        JSONObject resultJSON = JSONObject.fromObject(result);
        if (resultJSON != null) {
            if (resultJSON.get("media_id") != null) {
                System.out.println("上传" + type + "素材成功");
                return resultJSON;
            } else {
                System.out.println("上传" + type + "素材失败");
            }
        }
        return null;
	}
	
	/**
	 * 上传素材post请求
	 * @param path
	 * @param KK
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static  String connectHttpsByPost(String requestUrl, String KK, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
	    URL urlObj = new URL(requestUrl);
	    //连接
	    HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
	    String result = null;
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setUseCaches(false); // post方式不能使用缓存

	    // 设置请求头信息
	    con.setRequestProperty("Connection", "Keep-Alive");
	    con.setRequestProperty("Charset", "UTF-8");
	    // 设置边界
	    String BOUNDARY = "----------" + System.currentTimeMillis();
	    con.setRequestProperty("Content-Type",
	            "multipart/form-data; boundary="
	                    + BOUNDARY);

	    // 请求正文信息
	    // 第一部分：
	    StringBuilder sb = new StringBuilder();
	    sb.append("--"); // 必须多两道线
	    sb.append(BOUNDARY);
	    sb.append("\r\n");
	    sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\"" + file.getName() + "\"\r\n");
	    sb.append("Content-Type:application/octet-stream\r\n\r\n");
	    byte[] head = sb.toString().getBytes("utf-8");
	    // 获得输出流
	    OutputStream out = new DataOutputStream(con.getOutputStream());
	    // 输出表头
	    out.write(head);

	    // 文件正文部分
	    // 把文件已流文件的方式 推入到url中
	    DataInputStream in = new DataInputStream(new FileInputStream(file));
	    int bytes = 0;
	    byte[] bufferOut = new byte[1024];
	    while ((bytes = in.read(bufferOut)) != -1) {
	        out.write(bufferOut, 0, bytes);
	    }
	    in.close();
	    // 结尾部分
	    byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
	    out.write(foot);
	    out.flush();
	    out.close();
	    StringBuffer buffer = new StringBuffer();
	    BufferedReader reader = null;
	    try {
	        // 定义BufferedReader输入流来读取URL的响应
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        if (result == null) {
	            result = buffer.toString();
	        }
	    } catch (IOException e) {
	        System.out.println("发送POST请求出现异常！" + e);
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            reader.close();
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
		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
				"APPSECRET", APPSECRET);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			token.setAccessToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 * 获取网页授权access_token
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static AccessToken getWebAccessToken(String code) throws ClientProtocolException,
			IOException {
		AccessToken token = new AccessToken();
		
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", APPID).replace(
				"SECRET", APPSECRET).replace("CODE", code);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (!jsonObject.containsKey("errcode")) {
			token.setAccessToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
			token.setRefresh_token(jsonObject.getString("refresh_token"));
			token.setOpenid(jsonObject.getString("openid"));
			log.info("网页授权请求成功！openid：" + token.getOpenid());
			return token;
		} else {
			token.setErrcode(jsonObject.getString("errcode"));
			token.setErrmsg(jsonObject.getString("errmsg"));
			return token;
		}
	}
	
	/**
	 * 获取api_ticket
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static ApiTicket getApiTicket(String token) throws ClientProtocolException,
			IOException {
		ApiTicket apiTicket = new ApiTicket();
		
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			apiTicket.setTicket(jsonObject.getString("ticket"));
			apiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return apiTicket;
	}

	/**
     * 发送https请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }
}
