<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge" />
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
				<div class="navbar-brand" style="cursor:default;">岛城鹏程</div>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<!--li class="active"><a href="./user/">用户管理</a></li-->
					<li><a href="/manage/news/" target="main_frame">新闻管理</a></li>
					<li><a href="/manage/msg/" target="main_frame">消息管理</a></li>
					<li><a href="#">网站统计</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
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
			$('ul.nav:not(".navbar-right") li').eq(0).find('a')[0].click();
		});
	</script>
</body>
</html>