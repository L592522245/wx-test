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
    <title>api测试</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head>
  
  <body>
    <article class="weui-article">
	    <h1>微信JS-SDK体验</h1>
	    <p style="color:#888">通过使用微信JS-SDK，网页开发者可借助微信高效地使用拍照、选图、语音、位置等手机系统的能力，同时可以直接使用微信分享、扫一扫、卡券、支付等微信特有的能力，为微信用户提供更优质的网页体验。</p>
	</article>
	<div class="weui-cells">
        <div class="weui-cell weui-cell_access" onclick="imgApi()">
            <div class="weui-cell__bd">上传、预览图像</div>
            <div class="weui-cell__ft" style="font-size: 0">
            </div>
        </div>
        <div class="weui-cell weui-cell_access" onclick="getLocation()">
            <div class="weui-cell__bd">获取地理位置</div>
            <div class="weui-cell__ft" style="font-size: 0">
            </div>
        </div>
        <div class="weui-cell weui-cell_access" onclick="scan()">
            <div class="weui-cell__bd">微信扫一扫</div>
            <div class="weui-cell__ft" style="font-size: 0">
            </div>
        </div>
        <div class="weui-cell weui-cell_access" onclick="wxPay()">
            <div class="weui-cell__bd">微信支付</div>
            <div class="weui-cell__ft" style="font-size: 0">
            </div>
        </div>
    </div>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  	
  	function imgApi() {
  		window.location.href = "apiTest/imgApi";
  	}
  	
  	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appid}', // 必填，公众号的唯一标识
	    timestamp: '${timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ["onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareWeibo", "onMenuShareQZone", "startRecord",
	    "stopRecord", "onVoiceRecordEnd", "playVoice", "pauseVoice", "stopVoice", "onVoicePlayEnd", "uploadVoice", "downloadVoice", "chooseImage",
	    "previewImage", "uploadImage", "downloadImage", "translateVoice", "getNetworkType", "openLocation", "getLocation", "hideOptionMenu",
	    "showOptionMenu", "hideMenuItems", "showMenuItems", "hideAllNonBaseMenuItem", "showAllNonBaseMenuItem", "closeWindow", "scanQRCode",
	    "chooseWXPay", "openProductSpecificView", "addCard", "chooseCard", "openCard"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	function getLocation() {
		wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		        /* var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度 */
		        
		        wx.openLocation({
				    latitude: res.latitude, // 纬度，浮点数，范围为90 ~ -90
				    longitude: res.longitude, // 经度，浮点数，范围为180 ~ -180。
				    name: '我的位置', // 位置名
				    address: 'xxx', // 地址详情说明
				    scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
				    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
				});
		    }
		});
	}
	
	function scan() {
		wx.scanQRCode({
		    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
		    	var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			}
		});
	}
	
	function wxPay() {
		window.location.href = "apiTest/wxPay";
	}
  </script>
</html>
