package com.test.pay.wxpay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.test.weixin.util.MyX509TrustManager;

public class MyConfig implements WXPayConfig {
	private byte[] certData;

    public MyConfig() throws Exception {
    	/*
    	 * 整数目录，涉及资金回滚的接口会使用到商户证书，包括退款、撤销接口。
    	 * 微信商户平台(pay.weixin.qq.com)-->账户中心-->账户设置-->API安全-->证书下载 
    	 */
        String certPath = "F:/Program Files/myeclipse workspace/wx-test/src/main/java/com/test/pay/wxpay/cer/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }
    
	//公众账号ID
    public String getAppID() {
        return "wx2415ef6710d05176";
    }

    //商户号
    public String getMchID() {
        return "1229093102";
    }

    //API密钥, key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    // kgt8ON7yVITDhtdwci0qeSJZ8UZ7Z8hL
    public String getKey() {
//    	String key = "";
//        try {
//        	key = GetSignKey();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return key;
    	return "kgt8ON7yVITDhtdwci0qeSJZ8UZ7Z8hL";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
    
    //获取沙箱秘钥
    public String GetSignKey() throws Exception {
    	String key = "kgt8ON7yVITDhtdwci0qeSJZ8UZ7Z8hL";
        Map<String, String> map = new HashMap<String, String>(); 
        map.put("appid", getAppID());
        map.put("mch_id", getMchID());
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        String sign = WXPayUtil.generateSignature(map, key);
        
        Map<String, String> map2 = new HashMap<String, String>(); 
        map2.put("mch_id", getMchID());
        map2.put("nonce_str", WXPayUtil.generateNonceStr());
        map2.put("sign", sign);
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        String xml = WXPayUtil.mapToXml(map2);
        
        String response = httpsRequest(url, "POST", xml);
        System.out.println("---------" + response);
        Map<String, String> resp = WXPayUtil.xmlToMap(response);
        if(resp.get("sandbox_signkey") != null) {
            return resp.get("sandbox_signkey");
        } else {
        	return resp.get("return_msg");
        }
    }
    
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    	String resp = "";
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
            
            resp = buffer.toString();
        } catch (ConnectException ce) {
        } catch (Exception e) {
        }
        return resp;
    }
}
