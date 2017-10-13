package com.test.weixin.main;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weixin.domain.AccessToken;
import com.test.weixin.domain.ApiTicket;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

/**
* 类名: TokenThread </br>
* 描述: 定时获取微信access_token的线程 </br>
* 开发人员： souvc </br>
* 创建时间：  Oct 6, 2015 </br>
* 发布版本：V1.0  </br>
 */
public class TokenThread implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);
    // 第三方用户唯一凭证
    public static String appid = WeixinUtil.APPID;
    // 第三方用户唯一凭证密钥
    public static String appsecret = WeixinUtil.APPSECRET;
    public static AccessToken accessToken = null;
    public static ApiTicket apiTicket = null;

    public void run() {
        while (true) {
            try {
                accessToken = WeixinUtil.getAccessToken();
                apiTicket = WeixinUtil.getApiTicket(accessToken.getAccessToken());
                if (null != accessToken && null != apiTicket) {
                    //调用存储到数据库
                    TokenUtil.saveToken(accessToken);
                    log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());
                    //保存apiTicket
                    TokenUtil.saveTicket(apiTicket);
                    log.info("获取api_ticket成功，有效时长{}秒 token:{}", apiTicket.getExpiresIn(), apiTicket.getTicket());
                    // 休眠7000秒
                    Thread.sleep((accessToken.getExpiresIn() - 200)*1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException | IOException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }
        }
    }
}