<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pengchengzhizha.entity.News" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<% News news = (News) request.getAttribute("news"); %>
	<meta charset="UTF-8">
	<title><%=news != null ? news.getTitle() : "" %>|岛城鹏程新闻</title>
	<link href="favicon.ico" type="/image/x-icon" rel="shortcut icon">
	<link type="text/css" rel="stylesheet" href="/css/base.css" media="all" />
	<link type="text/css" rel="stylesheet" href="/css/common.css" media="all" />
	<link type="text/css" rel="stylesheet" href="/css/news.css" media="all" />
</head>
<body>
	<div id="bg" class="bg w1220 center">
	
		<div id="headerContainer">
			<div class="top_bar"></div>
			<div id="header" class="header">
				<div class="title"></div>
				<div class="logo center"></div>
				<div class="opts">
					<ul class="clearfix">
						<li id="favorite" class="fl">岛城鹏程</li>
						<li id="homepage" class="fl">欢迎您来</li>
					</ul>
				</div>
				<div class="contact">
					<span class="phone">0532-87010077</span>
					<span class="time">周一至周日8:00-18:00</span>
				</div>
				<ul class="nav center clearfix">
					<li class="fl"><a href="/index.html">網站首頁</a></li>
					<li class="fl"><a href="/about.html">關於我們</a></li>
					<li class="fl"><a href="/history.html">歷史傳說</a></li>
					<li class="fl"><a href="/display.html">產品展示</a></li>
					<li class="fl"><a href="/joinus.html">加入我們</a></li>
					<li class="fl"><a href="http://shop70021591.taobao.com/" target="_blank">網上商城</a></li>
					<li class="fl"><a href="/contactus.html">聯系我們</a></li>
				</ul>
			</div>
		</div>
				
		<div id="main" class="main w964 center">
			<h1 class="news_title"><%=news != null ? news.getTitle() : "" %></h1>
			<span class="news_time"><%=news != null ? news.getPublishTime() : "" %></span>
			<div class="delimiter"></div>
			<div class="news_content center"><%=news != null ? news.getContent() : "" %></div>
		</div>
		<div id="footer" class="footer">
			<div class="bot_dec w1220"></div>
			<p>
				<span>版权所有：岛城鹏程脂渣</span>
				<span>联系地址：山东省青岛市城阳区上马街道李仙庄工业园</span>
				<span>联系电话：0532-87010077</span>
				<span>手机：18561326777</span>
				<span>网站邮箱：<a href="mailto:18561326777@163.com">18561326777@163.com</a></span>
			</p>
			<p>
				<span>传真：0532-87010066</span>
				<span>备案序号：青123458789P12</span>
			</p>
		</div>
	</div>
	<script type="text/javascript" src="/js/require-jquery.js"></script>
	<script type="text/javascript">
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F63a4b87e1919766237ab9be47ffba671' type='text/javascript'%3E%3C/script%3E"));
		$(document).on('mousemove', 'body', function() {
			if ($('a[href^="http://tongji.baidu.com/hm-web/welcome/ico"]').remove()) {
				$(document).off('mousemove');
			};
		});
	</script>
</body>
</html>