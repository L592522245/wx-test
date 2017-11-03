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
    <title>登录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head>
  
  <body>
  	<div id="toptips" class="weui-toptips weui-toptips_warn js_tooltips" style="display: none;"></div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell weui-cell_vcode">
            <div class="weui-cell__hd">
                <label class="weui-label">手机号</label>
            </div>
            <div class="weui-cell__bd">
                <input id="phone" name="phone" class="weui-input" type="tel" placeholder="请输入手机号" required>
            </div>
            <div class="weui-cell__ft">
                <button id="getCodeBtn" class="weui-vcode-btn" onclick="getCode()">获取验证码</button>
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
            <div class="weui-cell__bd">
                <input id="code" name="code" class="weui-input" type="number" placeholder="请输入验证码" required>
            </div>
        </div>
    </div>
    <div id="cellTips" class="weui-cells__tips"></div>
    <div class="weui-btn-area">
        <a id="submit" class="weui-btn weui-btn_primary" href="javascript:" onclick="submit()">登录</a>
    </div>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  	
  	var flag = true;
  	function getCode() {
  		var phone = document.getElementById("phone").value;
  		var toptip = document.getElementById("toptips");
  		if(phone == "") {
  			toptips(toptip, "请填写手机号！");
  			return;
  		}
  		
  		var btn = document.getElementById("getCodeBtn");
  		
  		if(flag) {
  			flag = false;
  			btn.style.color = "#e5e5e5";
  			
  			var time = 30;
  			var interval = setInterval(function(){
				btn.innerHTML = "请" + time-- + "秒后获取";
			}, 1000);
	  		
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
			    	// 暂时无法使用短信验证功能
					var cellTips = document.getElementById("cellTips");
					var code = xmlhttp.responseText;
					cellTips.innerHTML = "暂时无法使用短信验证功能，验证码为" + code;
			    }
			}
			xmlhttp.open("POST","apiTest/getCode",true);
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send("phone=" + phone);
			
  		}
  		
  		setTimeout(function(){
			clearInterval(interval);
			flag = true;
			btn.style.color = "#3cc51f";
			btn.innerHTML = "获取验证码";
		}, 30000);
  	}
  	
  	var flag2 = true;
  	function submit() {
  		var code = document.getElementById("code").value;
  		var phone = document.getElementById("phone").value;
  		var toptip = document.getElementById("toptips");
  		if(phone == "") {
  			toptips(toptip, "请填写手机号！");
  			return;
  		} else if(code == "") {
  			toptips(toptip, "请填写验证码！");
  			return;
  		}
  		
  		if(flag2) {
  			flag2 = false;
  			var btn = document.getElementById("submit");
	  		btn.classList.add("weui-btn_loading");
	  		var i = document.createElement('i');
	  		i.setAttribute("class", "weui-loading");
	  		btn.insertBefore(i, btn.childNodes[0]);
	  		
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
			    	if(response == "false") {
			    		toptips(toptip, "验证码错误！");
			    		btn.classList.remove("weui-btn_loading");
			    		btn.removeChild(i);
			    		flag2 = true;
			    	} else {
				    	window.location.href =  response;
			    	}
			    }
			}
			xmlhttp.open("POST","apiTest/verify",true);
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send("code=" + code);
  		}
  	}
  	
  	function toptips(toptip, content) {
  		toptip.innerHTML = content;
  		toptip.style.display = "block";
		setTimeout(function() {
			toptip.style.display = "none";
		}, 1500);
  	}
  </script>
</html>
