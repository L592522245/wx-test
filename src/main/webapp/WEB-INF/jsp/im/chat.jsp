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
  	<div class="container">
  		<div class="chat-header">
			<div class="weui-cell">
				<div class="icon-back">返回</div>
		        <div class="weui-cell__bd">
                    <span style="vertical-align: middle;text-align: center;">name</span>
                </div>
		        <div class="icon-user">
		        	<img src="img/user.png" style="width: 30px;display: block">
		        </div>
		    </div>
		</div>
  	</div>
  </body>
  
  <script>
  	document.body.addEventListener("touchstart", function () {});
  	
  </script>
</html>
