<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>聊天窗口</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>

  </head>
  
  <body>
  	<div class="chat-container">
  		<div class="chat-header">
			<div class="weui-cell">
		        <div class="weui-cell__bd">
                    <span id="nickname" style="vertical-align: middle;text-align: center;"></span>
                </div>
		        <div class="icon-user" onclick="userInfo()">
		        	<img src="img/user.png" style="width: 20px;display: block;">
		        </div>
		    </div>
		</div>
		
		<div class="chat-inputFile">
			<input type="text" >
		</div>
  	</div>
  	
  	<!-- 资料页面 -->
    <div id="userInfoPage">
    	<!-- <div class="weui-tab__content">
			<div class="weui-cells">
	            <div class="weui-cell weui-cell_access">
	                <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
	                    <img src="${ userInfo.headimgurl }" style="width: 50px;display: block">
	                </div>
	                <div class="weui-cell__bd">
	                    <p>${ userInfo.nickname }</p>
	                    <p style="font-size: 13px;color: #888;">${ userInfo.sex }</p>
	                    <p style="font-size: 13px;color: #888;">${ userInfo.country } ${ userInfo.province } ${ userInfo.city }</p>
	                </div>
	                <div class="weui-cell__ft"></div>
	            </div>
	        </div>
		</div> -->
    </div>
  </body>
  
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <!--web im sdk-->
  <script type="text/javascript" src="sdk/webim.js"></script>
  <script type="text/javascript" src="sdk/json2.js"></script>
  <!--web im sdk 登录 示例代码-->
  <script type="text/javascript" src="js/login/login.js"></script>
  <!--web im sdk 登出 示例代码-->
  <script type="text/javascript" src="js/logout/logout.js"></script>
  <!--web im 解析一条消息 示例代码-->
  <script type="text/javascript" src="js/common/show_one_msg.js"></script>
  <!--web im demo 基本逻辑-->
  <!-- <script type="text/javascript" src="js/base.js"></script> -->
  <!--web im sdk 资料管理 api 示例代码-->
  <script type="text/javascript" src="js/profile/profile_manager.js"></script>
  <!--web im sdk 好友管理 api 示例代码-->
  <script type="text/javascript" src="js/friend/friend_manager.js"></script>
  <!--web im sdk 好友申请管理 api 示例代码-->
  <script type="text/javascript" src="js/friend/friend_pendency_manager.js"></script>
  <!--web im sdk 好友黑名单管理 api 示例代码-->
  <script type="text/javascript" src="js/friend/friend_black_list_manager.js"></script>
  <!--web im sdk 群组管理 api 示例代码-->
  <script type="text/javascript" src="js/group/group_manager.js"></script>
  <!--web im sdk 群成员管理 api 示例代码-->
  <script type="text/javascript" src="js/group/group_member_manager.js"></script>
  <!--web im sdk 加群申请管理 api 示例代码-->
  <script type="text/javascript" src="js/group/group_pendency_manager.js"></script>
  <!--web im 切换聊天好友或群组 示例代码-->
  <script type="text/javascript" src="js/switch_chat_obj.js"></script>
  <!--web im sdk 获取c2c获取群组历史消息 示例代码-->
  <script type="text/javascript" src="js/msg/get_history_msg.js"></script>
  <!--web im sdk 发送普通消息(文本和表情) api 示例代码-->
  <script type="text/javascript" src="js/msg/send_common_msg.js"></script>
  <!--web im sdk 上传和发送图片消息 api 示例代码-->
  <script type="text/javascript" src="js/msg/upload_and_send_pic_msg.js"></script>
  <!--web im sdk 切换播放语音消息 示例代码-->
  <script type="text/javascript" src="js/msg/switch_play_sound_msg.js"></script>
  <!--web im sdk 发送自定义消息 api 示例代码-->
  <script type="text/javascript" src="js/msg/send_custom_msg.js"></script>
  <!--web im 监听新消息(c2c或群) 示例代码-->
  <script type="text/javascript" src="js/msg/receive_new_msg.js"></script>
  <!--web im 监听群系统通知消息 示例代码-->
  <script type="text/javascript" src="js/msg/receive_group_system_msg.js"></script>
  <!--web im 监听好友系统通知消息 示例代码-->
  <script type="text/javascript" src="js/msg/receive_friend_system_msg.js"></script>
  <!--web im 监听资料系统通知消息 示例代码-->
  <script type="text/javascript" src="js/msg/receive_profile_system_msg.js"></script>
  <script>
  	document.body.addEventListener("touchstart", function () {});
  	
  	var userInfoPage = $("#userInfoPage");
  	
  	/*
	*  云通讯js配置
	*/
	//帐号模式，0-表示独立模式，1-表示托管模式
    var accountMode = 0;
    
    //当前用户身份
    var loginInfo = {
        'sdkAppID': '${ sdkAppID }', //用户所属应用id,必填
        'identifier': '${ identifier }', //当前用户ID,必须是否字符串类型，必填
        'accountType': 	'${ accountType }', //用户所属应用帐号类型，必填
        'userSig': '${ sig }', //当前用户身份凭证，必须是字符串类型，必填
        'identifierNick': null, //当前用户昵称，不用填写，登录接口会返回用户的昵称，如果没有设置，则返回用户的id
        'headurl': null //当前用户默认头像，选填，如果设置过头像，则可以通过拉取个人资料接口来得到头像信息
    };
    
    //存放c2c或者群信息（c2c用户：c2c用户id，昵称，头像；群：群id，群名称，群头像）
    var infoMap = {}; //初始化时，可以先拉取我的好友和我的群组信息

    var emotionFlag = false; //是否打开过表情选择框

    var curPlayAudio = null; //当前正在播放的audio对象

    var getPrePageC2CHistroyMsgInfoMap = {}; //保留下一次拉取好友历史消息的信息
    var getPrePageGroupHistroyMsgInfoMap = {}; //保留下一次拉取群历史消息的信息

    //监听新消息事件
	//newMsgList 为新消息数组，结构为[Msg]
	function onMsgNotify(newMsgList) {
	    //console.warn(newMsgList);
	    var sess, newMsg;
	    //获取所有聊天会话
	    var sessMap = webim.MsgStore.sessMap();
	
	    for (var j in newMsgList) {//遍历新消息
	        newMsg = newMsgList[j];
	
	        if (newMsg.getSession().id() == selToID) {//为当前聊天对象的消息
	            selSess = newMsg.getSession();
	            //在聊天窗体中新增一条消息
	            //console.warn(newMsg);
	            addMsg(newMsg);
	        }
	    }
	    //消息已读上报，以及设置会话自动已读标记
	    webim.setAutoRead(selSess, true, true);
	
	    for (var i in sessMap) {
	        sess = sessMap[i];
	        if (selToID != sess.id()) {//更新其他聊天对象的未读消息数
	            updateSessDiv(sess.type(), sess.id(), sess.unread());
	        }
	    }
	}
    
    //监听（多终端同步）群系统消息方法，方法都定义在receive_group_system_msg.js文件中
    //注意每个数字代表的含义，比如，
    //1表示监听申请加群消息，2表示监听申请加群被同意消息，3表示监听申请加群被拒绝消息
    var onGroupSystemNotifys = {
        "1": onApplyJoinGroupRequestNotify, //申请加群请求（只有管理员会收到）
        "2": onApplyJoinGroupAcceptNotify, //申请加群被同意（只有申请人能够收到）
        "3": onApplyJoinGroupRefuseNotify, //申请加群被拒绝（只有申请人能够收到）
        "4": onKickedGroupNotify, //被管理员踢出群(只有被踢者接收到)
        "5": onDestoryGroupNotify, //群被解散(全员接收)
        "6": onCreateGroupNotify, //创建群(创建者接收)
        "7": onInvitedJoinGroupNotify, //邀请加群(被邀请者接收)
        "8": onQuitGroupNotify, //主动退群(主动退出者接收)
        "9": onSetedGroupAdminNotify, //设置管理员(被设置者接收)
        "10": onCanceledGroupAdminNotify, //取消管理员(被取消者接收)
        "11": onRevokeGroupNotify, //群已被回收(全员接收)
        "15": onReadedSyncGroupNotify, //群消息已读同步通知
        "255": onCustomGroupNotify, //用户自定义通知(默认全员接收)
        "12":onInvitedJoinGroupNotifyRequest//邀请加群(被邀请者接收,接收者需要同意)
    };

    //监听好友系统通知函数对象，方法都定义在receive_friend_system_msg.js文件中
    var onFriendSystemNotifys = {
        "1": onFriendAddNotify, //好友表增加
        "2": onFriendDeleteNotify, //好友表删除
        "3": onPendencyAddNotify, //未决增加
        "4": onPendencyDeleteNotify, //未决删除
        "5": onBlackListAddNotify, //黑名单增加
        "6": onBlackListDeleteNotify //黑名单删除
    };

    var onC2cEventNotifys = {
        "92": onMsgReadedNotify, //消息已读通知,
        "96" : onMultipleDeviceKickedOut
    };

    //监听资料系统通知函数对象，方法都定义在receive_profile_system_msg.js文件中
    var onProfileSystemNotifys = {
        "1": onProfileModifyNotify //资料修改
    };
    
    //监听连接状态回调变化事件
    var onConnNotify = function(resp) {
        var info;
        switch (resp.ErrorCode) {
            case webim.CONNECTION_STATUS.ON:
                webim.Log.warn('建立连接成功: ' + resp.ErrorInfo);
                break;
            case webim.CONNECTION_STATUS.OFF:
                info = '连接已断开，无法收到新消息，请检查下你的网络是否正常: ' + resp.ErrorInfo;
                // alert(info);
                webim.Log.warn(info);
                break;
            case webim.CONNECTION_STATUS.RECONNECT:
                info = '连接状态恢复正常: ' + resp.ErrorInfo;
                // alert(info);
                webim.Log.warn(info);
                break;
            default:
                webim.Log.error('未知连接状态: =' + resp.ErrorInfo);
                break;
        }
    };
    
    //IE9(含)以下浏览器用到的jsonp回调函数
    function jsonpCallback(rspData) {
        webim.setJsonpLastRspData(rspData);
    }

    //监听事件
    var listeners = {
        "onConnNotify": onConnNotify //监听连接状态回调变化事件,必填
            ,
        "jsonpCallback": jsonpCallback //IE9(含)以下浏览器用到的jsonp回调函数，
            ,
        "onMsgNotify": onMsgNotify //监听新消息(私聊，普通群(非直播聊天室)消息，全员推送消息)事件，必填
            ,
        /* "onBigGroupMsgNotify": onBigGroupMsgNotify //监听新消息(直播聊天室)事件，直播场景下必填
            , */
        "onGroupSystemNotifys": onGroupSystemNotifys //监听（多终端同步）群系统消息事件，如果不需要监听，可不填
            ,
        "onGroupInfoChangeNotify": onGroupInfoChangeNotify //监听群资料变化事件，选填
            ,
        "onFriendSystemNotifys": onFriendSystemNotifys //监听好友系统通知事件，选填
            ,
        "onProfileSystemNotifys": onProfileSystemNotifys //监听资料系统（自己或好友）通知事件，选填
            ,
        "onKickedEventCall": onKickedEventCall //被其他登录实例踢下线
            ,
        "onC2cEventNotifys": onC2cEventNotifys //监听C2C系统消息通道
            /* ,
        "onAppliedDownloadUrl": onAppliedDownloadUrl //申请文件/音频下载地址的回调 */
    };

    var isAccessFormalEnv = true; //是否访问正式环境

    var isLogOn = false; //是否开启sdk在控制台打印日志

    //初始化时，其他对象，选填
    var options = {
        'isAccessFormalEnv': isAccessFormalEnv, //是否访问正式环境，默认访问正式，选填
        'isLogOn': isLogOn //是否开启控制台打印日志,默认开启，选填
    }
    
    //sdk登录
    webim.login(
        loginInfo, listeners, options,
        function (resp) {
            /* loginInfo.identifierNick = resp.identifierNick;//设置当前用户昵称
            loginInfo.headurl = resp.headurl;//设置当前用户头像  */
            if(resp.identifierNick == null || resp.headurl == undefined) {
            	webim.setProfilePortrait(
		            profileOptions,
		            function (resp) {
		                console.log('设置个人资料成功');
		            },
		            function (err) {
		                console.log(err.ErrorInfo);
		            }
		   		);
            }
            console.log("login success");
            // 拉取用户信息
            webim.getAllFriend(
				friendsOptions,
				function (resp) {
					console.log("get friends success");
				    //清空聊天对象列表
				    var sessList = document.getElementById("myFriendList");
				    sessList.innerHTML = "";
				    if (resp.ErrorCode == "0") {
				        var friends = resp.InfoItem;
				        if (!friends || friends.length == 0) {
				        	console.log("no friend");
				        	myFriendCount.innerHTML = 0;
				            return;
				        }
				        
				        var count = friends.length;
				    	myFriendCount.innerHTML = count;
				        
				        var friendCard = "";
				        for (var i = 0; i < count; i++) {
				            var friend_name, friend_headUrl, friend_account;
				            friend_account = friends[i].Info_Account;
				            if (friends[i].SnsProfileItem && friends[i].SnsProfileItem[0] 
				                && friends[i].SnsProfileItem[0].Tag) {
				                friend_name = friends[i].SnsProfileItem[0].Value;
				                friend_headUrl = friends[i].SnsProfileItem[1].Value;
				            }
				            if (friend_name.length > 7) {//帐号或昵称过长，截取一部分
				                friend_name = friend_name.substr(0, 7) + "...";
				            }
				            //增加一个好友div
				            friendCard += '<div class="weui-cell weui-cell_access cell__c friend" onclick="chat(\'' + friend_account + '\')">' +
						                '<div class="weui-cell__hd" style="position: relative;margin-right: 10px;">' +
						                    '<img src="' + friend_headUrl + '" style="width: 50px;display: block;border-radius: 50%">' +
						                '</div>' +
						                '<div class="weui-cell__bd">' +
						                    '<p>' + friend_name + '</p>' +
						                '</div>' +
						            '</div>';
				        }
				        myFriendList.innerHTML = friendCard;
				        /* if (selType == SessionType.C2C) {
				            //清空聊天界面
				            document.getElementsByClassName("msgflow")[0].innerHTML = "";
				            //默认选中当前聊天对象
				            selToID = friends[0].Info_Account;
				            //设置当前选中用户的样式为选中样式
				            var selSessDiv = $("#sessDiv_" + selToID)[0];
				            selSessDiv.className = "sessinfo-sel";
				            var selBadgeDiv = $("#badgeDiv_" + selToID)[0];
				            selBadgeDiv.style.display = "none";
				        } 
				        if (cbOK)
				            cbOK();*/
				    }
				},
				function (err) {
				    //alert(err.ErrorInfo);
				}
		    );
        },
        function (err) {
            //alert(err.ErrorInfo);
        }
    );
  	
  	// 拉取用户资料
  	function userInfo() {
		location.hash = "userInfo";
	   	
		var tag_list = [
	        "Tag_Profile_IM_Nick",//昵称
	        "Tag_Profile_IM_Gender",//性别
	        "Tag_Profile_IM_Image",//头像
	        "Tag_Profile_IM_Location"
	    ];
	    var options = {
	        'To_Account': '${ account }',
	        'TagList': tag_list
	    };
	    webim.getProfilePortrait(
	           options,
	           function (resp) {
	               var data = [];
	               if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
	                   for (var i in resp.UserProfileItem) {
	                       var to_account = resp.UserProfileItem[i].To_Account;
	                       var nick = null, gender = null, imageUrl = null, location = null;
	                       for (var j in resp.UserProfileItem[i].ProfileItem) {
	                           switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
	                               case 'Tag_Profile_IM_Nick':
	                                   nick = resp.UserProfileItem[i].ProfileItem[j].Value;
	                                   break;
	                               case 'Tag_Profile_IM_Gender':
	                                   switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
	                                       case 'Gender_Type_Male':
	                                           gender = '男';
	                                           break;
	                                       case 'Gender_Type_Female':
	                                           gender = '女';
	                                           break;
	                                       case 'Gender_Type_Unknown':
	                                           gender = '未知';
	                                           break;
	                                   }
	                                   break;
	                               case 'Tag_Profile_IM_Image':
	                                   imageUrl = resp.UserProfileItem[i].ProfileItem[j].Value;
	                                   break;
	                               case 'Tag_Profile_IM_Location':
	                               	location = resp.UserProfileItem[i].ProfileItem[j].Value;
	                               	break;
	                           }
	                       }
	                       data.push({
	                           'To_Account': to_account,
	                           'Nick': webim.Tool.formatText2Html(nick),
	                           'Gender': gender,
	                           'Image': imageUrl,
	                           'Location': location
	                       });
	                   }
	               }
	               
	               $("nickname").innerHTML = data.Nick;
				
				var userInfo = "";
	               userInfo += '<div id="friendAccount" class="weui-cell weui-cell_access cell__c" data-account=' + data.To_Account + '>' +
					                '<div class="weui-cell__hd" style="position: relative;margin-right: 10px;">' +
					                    '<img src="' + data.Image + '" style="width: 50px;display: block;border-radius: 50%">' +
					                '</div>' +
					                '<div class="weui-cell__bd">' +
					                    '<p>' + json.data[i].nickname + '</p>' +
					                    '<p style="font-size: 13px;color: #888888;">' + data.Gender + ' ' + data.Location + '</p>' +
					                '</div>' +
					                '<div class="icon-plus"></div>' +
					            '</div>';
				userInfoPage.insert(userInfo);
	
	           },
	           function (err) {
	               weui.alert(err.ErrorInfo);
	           }
	 	);
  	}
  	
    // 页面url跳转
   	$("body").onhashchange = function() {
   		if(location.hash == 0) {
		   	userInfoPage.style.left = '100%';
		   	setTimeout(function(){
		   		userInfoPage.style.display = "none";
			}, 400);
   		} else if(location.hash == "#userInfo") {
		   	userInfoPage.style.display = "block";
		   	setTimeout(function(){
		   		userInfoPage.style.left = 0;
			}, 100);
   		}
   	};
  </script>
</html>
