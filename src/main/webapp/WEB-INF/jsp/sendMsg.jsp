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
    <title>发送模板消息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
	<link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
	<link rel="stylesheet" href="css/app.css">
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head>
  
  <body>
  	<div id="toptips" class="weui-toptips weui-toptips_warn js_tooltips" style="display: none;"></div>
    <article class="weui-article">
	    <h1>模板消息</h1>
	    <p style="color:#888">主动发送模板消息给用户</p>
	</article>
	<section class="form-container">
    	<div class="weui-cells__title">公告</div>
    	<div class="weui-cells weui-cells_form">
    		<div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">标题</label></div>
                <div class="weui-cell__bd">
                    <input id="title" name="title" class="weui-input" type="text" placeholder="请输入标题" value="关于2017年元旦放假安排的通知">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd"><label class="weui-label">发布人</label></div>
                <div class="weui-cell__bd">
                    <input id="name" name="name" class="weui-input" type="text" placeholder="请输入发布人" value="杨浪轩">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea id="content" name="content" class="weui-textarea" placeholder="请输入公告内容" rows="5">根据国家关于2018年元旦节假日时间的具体安排，公司对元旦放假时间及注意事项通知如下：&#10    1、2018年元旦放假时间为：2017年12月30日至2018年1月1日元旦节放假，共三天。&#10    2、元旦放假期间，如需要正常上班的部门，请部门负责人于12月29日之前将值班表报办公室备案。&#10    3、元旦放假期间全体员工及领导必须保证手机24小时开机状态，确保联系畅通;&#10    4、元旦放假期间，未经批准，非值班人员(除部门负责人外)一律不得进入公司，如有特殊情况须请示部门负责人批准，经同意方可进入公司;&#10    5、元旦放假期间回家或外出旅游的员工，应事先知会部门负责人，并协调处理好放假期间的本职工作;&#10    6、元旦放假期间监控中心对公司财产的安全负全责，请做好防火、防盗工作。监控中心值班长并负责关好办公区域的门、窗等。
                    </textarea>
                    <!-- <div class="weui-textarea-counter"><span>0</span>/100</div> -->
                </div>
            </div>
	    </div>
	    <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:;" id="release" onclick="submit()">确认发布</a>
        </div>
    </section>
  </body>
  
  <script>
  	// 解决 Safari Mobile 伪类:active没有效果的问题，给body添加一个touchstart事件
  	document.body.addEventListener("touchstart", function () {});
  	
  	var flag = true;
  	function submit() {
  		var content = document.getElementById("content");
  		var cont = content.value.replace(/\n/g, "lineBreak");
  		var name = document.getElementById("name").value;
  		var title = document.getElementById("title").value;
  		
  		var toptip = document.getElementById("toptips");
  		if(title == "") {
  			toptips(toptip, "请填写标题！");
  			return;
  		} else if(name == "") {
  			toptips(toptip, "请填写发布人！");
  			return;
  		} else if(cont == "") {
  			toptips(toptip, "请填写内容！");
  			return;
  		}
  		
  		if(flag) {
  			flag = false;
  			var btn = document.getElementById("release");
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
			      window.location.href =  "apiTest/sendMsg";
			    }
			}
			xmlhttp.open("POST","apiTest/sendMsg",true);
			xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			xmlhttp.send("name=" + name + "&title=" + title + "&content=" + cont);
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
