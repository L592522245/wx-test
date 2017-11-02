package com.test.weixin.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weixin.Common;
import com.test.weixin.domain.resp.Article;
import com.test.weixin.domain.resp.Image;
import com.test.weixin.domain.resp.ImageMessage;
import com.test.weixin.domain.resp.Music;
import com.test.weixin.domain.resp.MusicMessage;
import com.test.weixin.domain.resp.NewsMessage;
import com.test.weixin.domain.resp.TextMessage;
import com.test.weixin.domain.resp.Video;
import com.test.weixin.domain.resp.VideoMessage;
import com.test.weixin.domain.resp.Voice;
import com.test.weixin.domain.resp.VoiceMessage;
import com.test.weixin.util.MessageUtil;
import com.test.weixin.util.TokenUtil;
import com.test.weixin.util.WeixinUtil;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-05-20
 */
public class CoreService {
	private static Logger log = LoggerFactory.getLogger(CoreService.class);
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return 
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知类型消息！";
        // 假如服务器无法保证在五秒内处理并回复，必须做出下述回复，这样微信服务器才不会对此作任何处理，并且不会发起重试（这种情况下，可以使用客服消息接口进行异步回复）
        String respCont = "success";
        
		// 调用接口获取access_token
		String at = (String) TokenUtil.getToken().get("access_token");
		
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String content = requestMap.get("Content");
			// 消息创建时间
			String createTime = requestMap.get("CreateTime");
			// 消息id
			String msgId = requestMap.get("MsgId");
			
			// 查询用户信息
			String userName = WeixinUtil.userInfo(at, fromUserName).getNickname();
			
			log.info("微信端请求！微信号：" + fromUserName + "  昵称：" + userName + "  消息类型：" + msgType + "  内容：" + content);
			
			// 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
               if (content.equals("文字")) {
            	   respContent = "文字文字文字文字文字文字！";
               } else if (content.equals("图片")) {
            	   // 回复图片消息
                   ImageMessage imageMessage = new ImageMessage();
                   imageMessage.setToUserName(fromUserName);
                   imageMessage.setFromUserName(toUserName);
                   imageMessage.setCreateTime(new Date().getTime());
                   imageMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
                   Image image = new Image();
                   image.setMediaId("Pr2hrQVGxfBlQzlY0F5ecbDXwU3dm2R0rwNrJ4odnliqetzjHqAgis6YZN4KYmdu");
                   imageMessage.setImage(image);
                   
                   respXml = MessageUtil.messageToXml(imageMessage);
                   return respXml;
               } else if (content.equals("视频")) {
            	   // 回复视频消息
                   VideoMessage videoMessage = new VideoMessage();
                   videoMessage.setToUserName(fromUserName);
                   videoMessage.setFromUserName(toUserName);
                   videoMessage.setCreateTime(new Date().getTime());
                   videoMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VIDEO);
                   Video video = new Video();
                   video.setMediaId("GIyyjL6jP-WRj5rf4I3a-DemHmRuu31iNSE_VZcHLZ7JHnUnvYhRrYXTr3FIcMYL");
                   videoMessage.setVideo(video);
                   
                   respXml = MessageUtil.messageToXml(videoMessage);
                   return respXml;
               } else if (content.equals("语音")) {
            	   VoiceMessage voiceMessage = new VoiceMessage();
            	   voiceMessage.setToUserName(fromUserName);
            	   voiceMessage.setFromUserName(toUserName);
            	   voiceMessage.setCreateTime(new Date().getTime());
            	   voiceMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);
            	   Voice voice = new Voice();
            	   voice.setMediaId("DuhomKK5YL5MgQWkUITJJxaNMRr0KVmNYwOMpc1Ls2DENiDBpOCUnqpV9WetViMl");
            	   voiceMessage.setVoice(voice);
            	   
            	   respXml = MessageUtil.messageToXml(voiceMessage);
                   return respXml;
               } else if (content.equals("音乐")) {
            	   // 回复音乐消息
                   MusicMessage musicMessage = new MusicMessage();
                   musicMessage.setToUserName(fromUserName);
                   musicMessage.setFromUserName(toUserName);
                   musicMessage.setCreateTime(new Date().getTime());
                   musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
                   Music music = new Music();
                   music.setTitle("Everything's Alright - Laura Shigihara/Kan R. Gao");
                   music.setDescription("To the Moon");
                   music.setMusicUrl(Common.HTTP + "/media/music/Kan R. Gao,Laura Shigihara - Everything's Alright.mp3");
                   music.setHQMusicUrl(Common.HTTP + "/media/music/Kan R. Gao,Laura Shigihara - Everything's Alright.mp3");
                   music.setThumbMediaId("uX6p33zl12CLH0pnLN30XMds06yDT59lnaFWa6TOZWT29TgB8rT1vjUv9UY074Eg");
                   musicMessage.setMusic(music);
                   
                   respXml = MessageUtil.messageToXml(musicMessage);
                   return respXml;
               } else if (content.equals("图文")) {
            	   // 回复图文消息
                   NewsMessage newsMessage = new NewsMessage();
                   newsMessage.setToUserName(fromUserName);
                   newsMessage.setFromUserName(toUserName);
                   newsMessage.setCreateTime(new Date().getTime());
                   newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                   newsMessage.setArticleCount(2);
                   Article article1 = new Article();
                   article1.setTitle("测试测试测试");
                   article1.setDescription("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
                   article1.setPicUrl(Common.HTTP + "/img/1.jpg");
                   article1.setUrl(Common.HTTP + "/apiTest");
                   Article article2 = new Article();
                   article2.setTitle("测试测试测试");
                   article2.setDescription("测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试");
                   article2.setPicUrl(Common.HTTP + "/img/1.jpg");
                   article2.setUrl(Common.HTTP + "/apiTest");
                   
                   List<Article> articles = new ArrayList<>();
                   articles.add(article1);
                   articles.add(article2);
                   newsMessage.setArticles(articles);
                   
                   respXml = MessageUtil.messageToXml(newsMessage);
                   return respXml;
               } else {
            	   respContent = "未知类型，请重新输入！";
               }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                	String latitude = requestMap.get("Latitude");
                	String longitude = requestMap.get("Longitude");
                	String precision = requestMap.get("Precision");
                	System.out.println("用户进入公众号时会返回用户地理信息：维度 " + latitude + "，经度 " + longitude + "，精度 " + precision);
                	return respCont;
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应
    				String eventKey = requestMap.get("EventKey");
    				
    				if (eventKey.equals("11")) {
    					
    					respContent = "回复以下关键字可接收不同类型的消息：\n" +
    							"1、文字;\n" + 
    							"2、图片;\n" +
    							"3、视频;\n" +
    							"4、语音;\n" +
    							"5、音乐;\n" +
    							"6、图文。\n";
    					textMessage.setContent(respContent);
    		            respXml = MessageUtil.messageToXml(textMessage);
    		            return respXml;
    					
    				} else if (eventKey.equals("12")) {
    					int result = TemplateMsg1.sendMsg(fromUserName);
    					if (0 == result)
    						log.info("模板消息发送成功！");
    					else
    						log.info("模板消息失败，错误码：" + result);
    					
    					return respCont;
    				}
    				
                }
            }
            
            // 设置文本消息的内容
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(textMessage);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
