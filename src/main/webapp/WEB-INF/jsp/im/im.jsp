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
    <title>聊天室</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>

  </head>
  
  <body>
	<div class="im_container">
		<!-- <div class="im-header">
			<div class="weui-cell">
                <div class="weui-cell__bd">
                    <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
	                    <img src="${ userInfo.headimgurl }" style="width: 30px;display: block;border-radius: 50%">
	                </div>
                </div>
                <div class="icon-plus"></div>
            </div>
		</div> -->
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
					<div class="weui-cells" style="margin-top:0;">
						<!-- <div class="weui-cell weui-cell_access">
			                <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
			                    <img src="img/boy.png" style="width: 50px;display: block;border-radius: 50%">
			                    <span class="weui-badge" style="position: absolute;top: -.4em;right: -.4em;">8</span>
			                </div>
			                <div class="weui-cell__bd">
			                    <p>名称</p>
			                    <p style="font-size: 13px;color: #888888;">摘要信息</p>
			                </div>
			                <div class="cell__rt">time</div>
			            </div> -->
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
			            <div id="addFriends" class="weui-cell weui-cell_access cell__p">
			                <div class="weui-cell__bd">
			                    <span style="vertical-align: middle">新的朋友</span>
			                </div>
			                <div class="icon-plus"></div>
			            </div>
			            <div id="myFriend" class="weui-cell cell__p" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
			                <div class="weui-cell__bd">
			                    <span style="vertical-align: middle">我的好友</span>
			                </div>
			                <div class="cell__rb">0</div>
			            </div>
			            <!-- 好友列表-点击后加载 -->
			            <div id="myFriendList" style="display: none;">
				            <div class="weui-cell weui-cell_access cell__c">
				                <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
				                    <img src="img/boy.png" style="width: 50px;display: block;border-radius: 50%">
				                    <!-- <span class="weui-badge" style="position: absolute;top: -.4em;right: -.4em;">8</span> -->
				                </div>
				                <div class="weui-cell__bd">
				                    <p>名称</p>
				                    <p style="font-size: 13px;color: #888888;">摘要信息</p>
				                </div>
				            </div>
				        </div>
			            <div id="myGroup" class="weui-cell cell__p" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
			                <div class="weui-cell__bd">
			                    <span style="vertical-align: middle">我的群聊</span>
			                    <!-- <span class="weui-badge" style="margin-left: 5px;">New</span> -->
			                </div>
			                <div class="cell__rb">0</div>
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
                    <span style="display: inline-block;position: relative;">
                        <img src="img/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                        <!-- <span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">8</span> -->
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
	</div>
  </body>
  
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
  	
  	weui.tab('#tab',{
	    defaultIndex: 0
	});
	
	weui.searchBar('#searchBar');
	
	//帐号模式，0-表示独立模式，1-表示托管模式
    var accountMode = 0;
    
    //当前用户身份
    /* var loginInfo = {
        'sdkAppID': '1400047514', //用户所属应用id,必填
        'identifier': null, //当前用户ID,必须是否字符串类型，必填
        'accountType': 	18747, //用户所属应用帐号类型，必填
        'userSig': null, //当前用户身份凭证，必须是字符串类型，必填
        'identifierNick': '${ userInfo.nickname }', //当前用户昵称，不用填写，登录接口会返回用户的昵称，如果没有设置，则返回用户的id
        'headurl': '${ userInfo.headimgurl }' //当前用户默认头像，选填，如果设置过头像，则可以通过拉取个人资料接口来得到头像信息
    }; */
    
    var myFriend = document.getElementById("myFriend");
    var myFriendList = document.getElementById("myFriendList");
    myFriend.addEventListener("click", function() {
    	if(myFriendList.style.display == "none") {
	    	myFriendList.style.display = "block";
    	} else {
    		myFriendList.style.display = "none";
    	}
    });
  </script>
</html>
