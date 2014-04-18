<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="com.pengchengzhizha.entity.User;"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<%
		User user = (User) request.getSession().getAttribute("user");
	%>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css"/>
	<title>岛城鹏程脂渣后台管理</title>
	<style type="text/css">
		body,h1,h2,h3,h4,h5,h6{font-family:"微软雅黑";}
		body{padding-top:70px;}
	</style>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/" target="_blank">岛城鹏程</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<%
					if (user.isAdmin()) {
					%>
					<li><a href="./admin/" target="main_frame">用户管理</a></li>
					<%
					}
					if (user.isNewsManager()) {
					%>
					<li><a href="/manage/news/" target="main_frame">新闻管理</a></li>
					<%
					}
					if (user.isMessageManager()) {
					%>
					<li><a href="/manage/message/" target="main_frame">留言管理</a></li>
					<%
					}
					%>
					<li><a target="_blank" href="http://tongji.baidu.com/web/6976614/overview/sole?siteId=4254052">网站统计</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/manage/myAccount/" target="main_frame"><%=user.getName() %></a></li>
					<li><a href="/logout/">登出</a></li>
				</ul>
			</div>
		</div>	
	</div>
	<div class="container">
		<iframe src="" frameborder="0" width="100%" name="main_frame" onload="this.height = main_frame.document.body.scrollHeight"></iframe>
	</div>
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(document).on('click', 'ul.nav:not(".navbar-right") li', function() {
			$(this).addClass('active').siblings().removeClass('active');
		});
		$(function() {
			$('ul.nav:not(".navbar-right") li').find('a').not('[href="./admin/"],[href="http://tongji.baidu.com/web/6976614/overview/sole?siteId=4254052"]').eq(0)[0].click();
		});
	</script>
</body>
</html>