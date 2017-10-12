<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>wxapi-test</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>提示页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>

  </head>
  
  <body>
  	<article class="weui-article">
	    <h1>登录成功！</h1>
	    <p style="font-size: 13px;color: #888888;">登录授权后可获得当前用户信息</p>
	</article>
	<div class="hintPage_container">
		<div class="weui-cells">
            <div class="weui-cell">
                <div class="weui-cell__hd" style="position: relative;margin-right: 10px;">
                    <img src="${ userInfo.headimgurl }" style="width: 50px;display: block">
                </div>
                <div class="weui-cell__bd">
                    <p>${ userInfo.nickname }</p>
                    <p style="font-size: 13px;color: #888888;">${ userInfo.sex }</p>
                    <p style="font-size: 13px;color: #888888;">${ userInfo.country } ${ userInfo.province } ${ userInfo.city }</p>
                </div>
            </div>
        </div>
	</div>
  </body>
  
  <script>
  	document.body.addEventListener("touchstart", function () {});
  </script>
</html>
