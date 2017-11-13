<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html id="imHtml">
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>聊天室</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>

  </head>
  
  <body>
	<div class="im_container">
	    <div class="weui-tab" id="tab">
            <div class="weui-tab__panel">
            	<!-- 消息 -->
				<div class="weui-tab__content">
					<div class="weui-search-bar" id="searchBar">
			            <div class="weui-search-bar__form" >
			                <div class="weui-search-bar__box">
			                    <i class="weui-icon-search"></i>
			                    <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="">
			                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
			                </div>
			                <label class="weui-search-bar__label" id="searchText">
			                    <i class="weui-icon-search"></i>
			                    <span>搜索</span>
			                </label>
			            </div>
			            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
			        </div>
					<div id="chatList" class="weui-cells" style="margin-top:0;">
						<!-- 消息列表 -->
			        </div>
				</div>
				<!-- 联系人 -->
		        <div class="weui-tab__content">
		        	<div class="weui-search-bar" id="searchBar">
			            <div class="weui-search-bar__form">
			                <div class="weui-search-bar__box">
			                    <i class="weui-icon-search"></i>
			                    <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="">
			                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
			                </div>
			                <label class="weui-search-bar__label" id="searchText">
			                    <i class="weui-icon-search"></i>
			                    <span>搜索</span>
			                </label>
			            </div>
			            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
			        </div>
					<div class="weui-cells" style="margin-top:0;">
			            <div id="addFriends" class="weui-cell weui-cell_access cell__p" onclick="searchPerson()">
			                <div class="weui-cell__bd">
			                    <span style="vertical-align: middle">新的朋友</span>
			                </div>
			                <div class="icon-plus"></div>
			            </div>
			            <div id="myFriend" class="weui-cell cell__p" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
			                <div class="weui-cell__bd">
			                    <span style="vertical-align: middle">我的好友</span>
			                </div>
			                <div id="myFriendCount" class="cell__rb"></div>
			            </div>
			            <!-- 好友列表-点击后加载 -->
			            <div id="myFriendList" style="display: none;"></div>
			            <div id="myGroup" class="weui-cell cell__p" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
			                <div class="weui-cell__bd">
			                    <span style="vertical-align: middle">我的群聊</span>
			                    <!-- <span class="weui-badge" style="margin-left: 5px;">New</span> -->
			                </div>
			                <div class="cell__rb"></div>
			            </div>
			            <!-- 好友列表-点击后加载 -->
			        </div>
				</div>
				<!-- 我 -->
		        <div class="weui-tab__content">
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
				</div>
            </div>
            
            <!-- 底部导航栏 -->
            <div class="weui-tabbar">
                <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on">
                    <span id="tabbar-msg" style="display: inline-block;position: relative;">
                        <img src="img/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    </span>
                    <p class="weui-tabbar__label">消息</p>
                </a>
                <a href="javascript:;" class="weui-tabbar__item">
                    <img src="img/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">联系人</p>
                </a>
                <a href="javascript:;" class="weui-tabbar__item">
                    <span style="display: inline-block;position: relative;">
                        <img src="img/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                        <!-- <span class="weui-badge weui-badge_dot" style="position: absolute;top: 0;right: -6px;"></span> -->
                    </span>
                    <p class="weui-tabbar__label">我</p>
                </a>
            </div>
        </div>
        <!-- 添加好友页面 -->
        <div id="addFriendPage">
        	<div class="weui-search-bar" id="searchBar">
	            <div class="weui-search-bar__form">
	                <div class="weui-search-bar__box">
	                    <i class="weui-icon-search"></i>
	                    <input name="nickname" type="search" class="weui-search-bar__input" id="searchFriendInput" placeholder="搜索" required="" onkeyup="search()">
	                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
	                </div>
	                <label class="weui-search-bar__label" id="searchText">
	                    <i class="weui-icon-search"></i>
	                    <span>昵称</span>
	                </label>
	            </div>
	            <a href="javascript:" class="weui-search-bar__cancel-btn" id="">取消</a>
	        </div>
	        <div id="searchFriendResult" class="weui-cells" style="margin-top:0;"></div>
        </div>
        <!-- 聊天页面 -->
        <div id="chatPage" data-toaccount="">
        	<div class="chat-header">
				<div class="weui-cell">
			        <div class="weui-cell__bd">
	                    <span id="toAccountNickname" style="vertical-align: middle;text-align: center;"></span>
	                </div>
			        <div class="icon-user" onclick="userInfo()">
			        	<img src="img/user.png" style="width: 20px;display: block;">
			        </div>
			    </div>
			</div>
			<div id="chatBody" class="chat-body">
				<div class="msg-bd">
				  <div class="msg-headimg__to"><img src="" style="width:50px;"></div>
				  <div class="msg-word__to"></div>
				</div>
			</div>
			<div class="chat-inputFile">
				<input id="chatMsg" type="text" onkeyup="sendMsg()">
			</div>
        </div>
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
  <!--web im 获取我的最近联系人 示例代码-->
  <script type="text/javascript" src="js/recentcontact/recent_contact_list_manager.js"></script>
  <script>
  	document.body.addEventListener("touchstart", function () {});
  	
  	weui.tab('#tab',{
	    defaultIndex: 0
	});
	
	weui.searchBar('#searchBar');
	
	var myFriend = document.getElementById("myFriend");
    var myFriendList = document.getElementById("myFriendList");
    var myFriendCount = document.getElementById("myFriendCount");
    myFriend.addEventListener("click", function() {
    	if(myFriendList.style.display == "none") {
	    	myFriendList.style.display = "block";
    	} else {
    		myFriendList.style.display = "none";
    	}
    });
	
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
    
    var AdminAcount = 'admin';
    var selType = webim.SESSION_TYPE.C2C; //当前聊天类型
    var selToID = null; //当前选中聊天id（当聊天类型为私聊时，该值为好友帐号，否则为群号）
    var selSess = null; //当前聊天会话对象
    var recentSessMap = {}; //保存最近会话列表
    var reqRecentSessCount = 50; //每次请求的最近会话条数，业务可以自定义
    
    //存放c2c或者群信息（c2c用户：c2c用户id，昵称，头像；群：群id，群名称，群头像）
    var infoMap = {}; //初始化时，可以先拉取我的好友和我的群组信息

    var emotionFlag = false; //是否打开过表情选择框

    var curPlayAudio = null; //当前正在播放的audio对象

    var getPrePageC2CHistroyMsgInfoMap = {}; //保留下一次拉取好友历史消息的信息
    var getPrePageGroupHistroyMsgInfoMap = {}; //保留下一次拉取群历史消息的信息

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

    var sex = "";
    if("${ userInfo.sex }" == "男") {
    	sex = "Gender_Type_Male";
    } else if("${ userInfo.sex }" == "女") {
    	sex = "Gender_Type_Female";
    } else {
    	sex = "Gender_Type_Unknown";
    }
    
    //sdk登录
    webim.login(
        loginInfo, listeners, options,
        function (resp) {
        	var data = [];
        	data.push({
        		'nick': '${ userInfo.nickname }',
        		'image': '${ userInfo.headimgurl }',
        		'allowType': 'AllowType_Type_NeedConfirm',
        		'gender': sex,
        		'location': '${ userInfo.city }'
        	});
            loginInfo.identifierNick = resp.identifierNick;//设置当前用户昵称
            loginInfo.headurl = resp.headurl;//设置当前用户头像 
            if(resp.identifierNick == null || resp.headurl == undefined) {
            	setProfilePortrait(data);
            }
            console.log("login success");
            getMyFriend();// 拉取好友列表
            initInfoMapByMyFriends(); //将好友信息保存到infoMap
            getPendency(true);// 拉取好友申请会话
            getRecentContactList(); // 拉取最近消息
        },
        function (err) {
            //alert(err.ErrorInfo);
        }
    );
    
    // 添加朋友页面
   	var addFriendPage = document.getElementById("addFriendPage");
   	var searchFriendResult = document.getElementById("searchFriendResult");
   	var chatPage = document.getElementById("chatPage");
    function searchPerson() {
    	location.hash = "addFriend";
    	searchFriendResult.innerHTML = "";
    }
    // 页面url跳转
   	document.getElementsByTagName("BODY")[0].onhashchange = function() {
   		if(location.hash == 0) {
		   	addFriendPage.style.left = '100%';
		   	chatPage.style.left = '100%';
		   	setTimeout(function(){
		   		addFriendPage.style.display = "none";
		   		chatPage.style.display = "none";
			}, 400);
   		} else if(location.hash == "#addFriend") {
		   	addFriendPage.style.display = "block";
		   	setTimeout(function(){
		   		addFriendPage.style.left = 0;
			}, 100);
   		} else if(location.hash == "#chat") {
   			chatPage.style.display = "block";
   				setTimeout(function(){
		   		chatPage.style.left = 0;
			}, 100);
   		}
   	};
   	
   	// 查找用户
   	var searchFriendInput = document.getElementById("searchFriendInput");
   	function search() {
   		var code = event.keyCode;
   		var friendCard = "";
		if(code == 13){
			var nickname = searchFriendInput.value;
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
				//  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
				xmlhttp=new XMLHttpRequest();
			}
			else
			{
				// IE6, IE5 浏览器执行代码
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function() {
				if (xmlhttp.readyState==4 && xmlhttp.status==200)
			    {
			    	var response = xmlhttp.responseText;
			    	var json = JSON.parse(response);
			    	if(json.data == 0) {
			    		friendCard = "没有找到该用户！";
			    	}
			    	var i;
			        for (i in json.data) {
			            friendCard += '<div id="friendAccount" class="weui-cell weui-cell_access cell__c" data-account=' + json.data[i].openid + ' onclick="addFriendConfirm()">' +
					                '<div class="weui-cell__hd" style="position: relative;margin-right: 10px;">' +
					                    '<img src="' + json.data[i].headimgurl + '" style="width: 50px;display: block;border-radius: 50%">' +
					                '</div>' +
					                '<div class="weui-cell__bd">' +
					                    '<p>' + json.data[i].nickname + '</p>' +
					                    '<p style="font-size: 13px;color: #888888;">' + json.data[i].sex + ' ' + json.data[i].city + '</p>' +
					                '</div>' +
					                '<div class="icon-plus"></div>' +
					            '</div>';
			        }
			        searchFriendResult.innerHTML = friendCard;
			    }
			}
			xmlhttp.open("POST","tim/searchUser",true);
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send("nickname=" + nickname);
		}
   	}
   	
   	// 添加好友
   	function addFriendConfirm() {
	   	weui.confirm('<div style="padding: 10px;border: 1px solid #eee;"><input id="addWording" class="weui-input" type="text" placeholder="你好，我想添加你为好友" ></div>', {
		    title: '添加好友',
		    buttons: [{
		        label: '取消',
		        type: 'default',
		        onClick: function(){ }
		    }, {
		        label: '确定',
		        type: 'primary',
		        onClick: function(){ 
		        	applyAddFriend();
				}
		    }]
		});
   	}
    
    // 进入聊天页面
    function chat(msgType, nickname, account) {
    	if(!account) account = "";
    	location.hash = "chat";
  		$("#toAccountNickname").html(nickname);
  		$("#chatPage").data("toaccount", account);
  		selToID = $("#chatPage").data("toaccount");
  		$("#chatBody").html("");
  		if(msgType == "system") {
  			getPendency(false);
  		} else if(msgType == "friend") {
  			getLastC2CHistoryMsgs();
  		}
    }
    
  	//发送消息
  	function sendMsg() {
  		var code = event.keyCode;
		if(code == 13){
			onSendMsg($("#chatMsg").val());
		}
  	}
    
    // 拉取用户资料
    var dialog;
  	function userInfo() {
  		var chatAccount = $("#chatPage").data("toaccount");
  		getProfilePortraitClick(chatAccount);
  	}
  	
  	// 删除好友
  	function deleteF() {
  		dialog.hide();
  		deleteFriend();
  		location.hash = "";
  	}
  	
  	/* $(window).resize(function() {
  		//滚动到底部
  		setTimeout(function() {
  			var h = $("#chatPage").innerHeight();
  			$("#chatPage").scrollTop(h);
  		}, 400);
	}); */
  </script>
</html>
