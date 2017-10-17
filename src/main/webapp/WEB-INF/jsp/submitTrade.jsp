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
    <title>提交订单</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head>
  
  <body>
    <article class="weui-article">
	    <h1>提交订单</h1>
	    <p style="color:#888">提交订单以，使用微信支付功能</p>
	</article>
	<div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">订单详情</div>
        <div class="weui-panel__bd">
            <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="img/1.jpg" alt="">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title">${ title }</h4>
                    <p class="weui-media-box__desc">${ desc }</p>
                </div>
                <div class="weui-media-box__bd">
                    <h3 class="price">价格：<strong>${ price }</strong></h3>
                </div>
            </a>
        </div>
        <div class="weui-form-preview__ft">
        	<a class="weui-form-preview__btn weui-btn_plain-default" style="border-color:#e5e5e5;" href="javascript:" onclick="submitTrade()">提交订单</a>
        </div>
    </div>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  	
  	function submitTrade() {
  		window.location.href = "apiTest/wxPay";
  	}
  </script>
</html>
