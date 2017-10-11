package com.test.weixin.main;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weixin.domain.message.TemplateMsg1;
import com.test.weixin.domain.message.TemplateMsg2;
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
		String respMessage = "success";
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
			
			// 查询用户信息
			String userName = WeixinUtil.userInfo(at, fromUserName).getNickname();
			
			log.info("微信号：" + fromUserName + "  昵称：" + userName + "  消息类型：" + msgType + "  内容：" + content);
			
			// 自定义菜单点击事件
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				
				// 事件KEY值，与创建自定义菜单时指定的KEY值对应
				String eventKey = requestMap.get("EventKey");
				if(eventKey == null) return respMessage;
				
				if (eventKey.equals("11")) {
					int result = TemplateMsg1.sendMsg(fromUserName);
					if (0 == result)
						log.info("模板消息发送成功！");
					else
						log.info("模板消息失败，错误码：" + result);
				} else if (eventKey.equals("31")) {
					int result = TemplateMsg2.sendMsg(fromUserName);
					if (0 == result)
						log.info("模板消息发送成功！");
					else
						log.info("模板消息失败，错误码：" + result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respMessage;
	}
}
