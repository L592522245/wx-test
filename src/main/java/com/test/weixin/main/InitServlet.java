package com.test.weixin.main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Component;

import com.test.pay.unionpay.sdk.SDKConfig;
import com.test.weixin.util.WeixinUtil;


/**
* 类名: InitServlet </br>
* 描述: 初始化servlet </br>
* 开发人员： souvc </br>
* 创建时间：  Oct 6, 2015 </br>
* 发布版本：V1.0  </br>
 */
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        // 获取web.xml中配置的参数
        TokenThread.appid = WeixinUtil.APPID;
        TokenThread.appsecret = WeixinUtil.APPSECRET;

        // 未配置appid、appsecret时给出提示
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
        	
        } else {
            // 启动定时获取access_token的线程
            new Thread(new TokenThread()).start();
        }
        
        // 从应用的classpath下加载acp_sdk.properties属性文件并将该属性文件中的键值对赋值到SDKConfig类中
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }
}