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
    <title>提示页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>

  </head>
  
  <body>
  	<div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="${ iconCss } weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">${ payResult }</h2>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商户订单号</label>
                    <span class="weui-form-preview__value">${ out_trade_no }</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">交易流水号</label>
                    <span class="weui-form-preview__value">${ trade_no }</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品金额</label>
                    <span class="weui-form-preview__value">${ total_amount }元</span>
                </div>
            </div>
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="pay" class="weui-btn weui-btn_primary">返回</a>
            </p>
        </div>
    </div>
  </body>
  
  <script>
  	document.body.addEventListener("touchstart", function () {});
  </script>
</html>
