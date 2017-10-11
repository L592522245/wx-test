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
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>

  </head>
  
  <body>
    <article class="weui-article">
	    <h1>微信JSSDK体验</h1>
	</article>
	<div class="container">
		<div class="btn_title">微信网页授权</div>
		<div class="btn_desc">进入网页需得到用户同意，且只能从微信端进入该网页，不能从其他浏览器进入</div>
	    <a href="apiTest/oAuth" class="weui-btn weui-btn_primary">微信登录</a>
	</div>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  </script>
</html>
