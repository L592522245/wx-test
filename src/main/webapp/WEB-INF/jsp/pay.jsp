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
    <title>商品描述商品描述商品描述</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head>
  
  <body>
    <article class="weui-article">
	    <h1>移动支付</h1>
	    <p style="color:#888">微信、银联、支付宝移动支付测试</p>
	</article>
	<div class="wxPay-container">
		<div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">付款金额</label>
                    <em class="weui-form-preview__value">¥<span id="goodsAmount">0.01</span></em>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品</label>
                    <span id="goodsName" class="weui-form-preview__value">商品名称</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">商品描述</label>
                    <span id="goodsDesc" class="weui-form-preview__value">商品描述商品描述商品描述</span>
                </div>
            </div>
        </div>
        <div class="weui-cells__title">选择支付方式</div>
        <div class="weui-cells weui-cells_radio">
	        <label class="weui-cell weui-check__label" for="alipay">
	       		<img src="img/alipay-logo.png" style="width:22px;height:20px;margin: 0 19px 0 4px;">
	            <div class="weui-cell__bd">
	                <p>支付宝支付</p>
	            </div>
	            <div class="weui-cell__ft">
	                <input type="radio" name="pay" class="weui-check" id="alipay" value="ali" checked="checked">
	                <span class="weui-icon-checked"></span>
	            </div>
	        </label>
	        <label class="weui-cell weui-check__label" for="unionpay">
	        	<img src="img/unionpay-logo.png" style="width:30px;height:20px;margin-right:15px">
	            <div class="weui-cell__bd">
	                <p>银联支付</p>
	            </div>
	            <div class="weui-cell__ft">
	                <input type="radio" name="pay" class="weui-check" id="unionpay" value="union">
	                <span class="weui-icon-checked"></span>
	            </div>
	        </label>
	        <label class="weui-cell weui-check__label" for="wxpay">
	        	<img src="img/wxpay-logo.png" style="width:22px;height:20px;margin: 0 19px 0 4px;">
	        	<div></div>
	            <div class="weui-cell__bd">
	                <p>微信支付</p>
	            </div>
	            <div class="weui-cell__ft">
	                <input type="radio" class="weui-check" name="pay" id="wxpay" value="wx" >
	                <span class="weui-icon-checked"></span>
	            </div>
	        </label>
	    </div>
        <div style="margin:.77em;">
        	<a id="submitBtn" href="javascript:;" class="weui-btn weui-btn_plain-primary" onclick="pay()">确认支付</a>
        </div>
    </div>
    <form name="alipayment" action="pay/alipay" method="POST">
    	<input name="WIDout_trade_no" id="WIDout_trade_no" type="hidden">
    	<input name="WIDsubject" id="WIDsubject" type="hidden">
    	<input name="WIDtotal_amount" id="WIDtotal_amount" type="hidden">
    	<input name="WIDbody" id="WIDbody" type="hidden">
    	<input type="submit" style="display:none">
    </form>
    
    <form name="unionpayment" action="pay/unionpay" method="POST">
		<input id="merId" type="hidden" name="merId" />
		<input id="txnAmt" type="hidden" name="txnAmt" />
		<input id="txnTime" type="hidden" name="txnTime" />
		<input id="orderId" type="hidden" name="orderId" />
		<input type="submit" style="display:none">
	</form>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  	
  	function pay() {
  		var check;
  		var radio = document.getElementsByName("pay");
  		for (var i = 0; i < radio.length; i++) {
  			if (radio[i].checked) {
  				check = radio[i].value;
  				if (check == "wx") {
  					wxPay();
  				} else if (check == "ali") {
  					aliPay();
  				} else if (check == "union") {
  					unionPay();
  				}
  			}
  		}
  		
  	}
  	
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
		    package: '${prepay_id}', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		    paySign: '${paySign}', // 支付签名
		    success: function (res) {
		        // 支付成功后的回调函数
		        window.location = '${mweb_url}';
		    }
		});
	}
	
	var flag = true;
	function aliPay() {
		var vNow = new Date();
		var sNow = "";
		sNow += String(vNow.getFullYear());
		sNow += String(vNow.getMonth() + 1);
		sNow += String(vNow.getDate());
		sNow += String(vNow.getHours());
		sNow += String(vNow.getMinutes());
		sNow += String(vNow.getSeconds());
		sNow += String(vNow.getMilliseconds());
  		var WIDsubject = document.getElementById("goodsName").innerHTML;
  		var WIDtotal_amount = document.getElementById("goodsAmount").innerHTML;
  		var WIDbody = document.getElementById("goodsDesc").innerHTML;
		document.getElementById("WIDout_trade_no").value =  sNow;
		document.getElementById("WIDsubject").value = WIDsubject;
		document.getElementById("WIDtotal_amount").value = WIDtotal_amount;
        document.getElementById("WIDbody").value = WIDbody;
  		if(flag) {
  			flag = false;
  			document.getElementsByName("alipayment")[0].submit();
  		}
  	}
	
	function unionPay() {
		var vNow = new Date();
		var sNow = "";
		sNow += String(vNow.getFullYear());
		sNow += String(vNow.getMonth() + 1);
		sNow += String(vNow.getDate());
		sNow += String(vNow.getHours());
		sNow += String(vNow.getMinutes());
		sNow += String(vNow.getSeconds());
  		var txnAmt = document.getElementById("goodsAmount").innerHTML * 100;
		document.getElementById("txnTime").value = sNow;
		document.getElementById("txnAmt").value = txnAmt;
		document.getElementById("orderId").value = sNow;
  		if(flag) {
  			flag = false;
  			document.getElementsByName("unionpayment")[0].submit();
  		}
	}
  </script>
</html>
