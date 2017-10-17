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
    <title>支付</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head>
  
  <body>
    <article class="weui-article">
	    <h1>微信支付</h1>
	    <p style="color:#888">调用微信支付接口</p>
	</article>
	<div class="wxPay-container">
		<div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">付款金额</label>
                    <em class="weui-form-preview__value">¥1.00</em>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品</label>
                    <span class="weui-form-preview__value">微信支付</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品描述</label>
                    <span class="weui-form-preview__value">微信支付接口测试</span>
                </div>
            </div>
            <div class="weui-form-preview__ft">
            	<a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:" onclick="wxPay()">支付</a>
            </div>
        </div>
    </div>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  	
  	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appid}', // 必填，公众号的唯一标识
	    timestamp: '${timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ["chooseWXPay"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	function wxPay() {
		wx.chooseWXPay({
		    timestamp: '${timestamp}', // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		    nonceStr: '${nonceStr}', // 支付签名随机串，不长于 32 位
		    package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: 'SHA1', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		    paySign: '', // 支付签名
		    success: function (res) {
		        // 支付成功后的回调函数
		        alert("支付成功！");
		    }
		});
	}
  </script>
</html>
