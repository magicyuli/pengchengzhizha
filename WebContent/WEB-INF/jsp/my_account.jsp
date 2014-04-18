<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css"/>
	<title>岛城鹏程脂渣后台我的账户</title>
	<style type="text/css">
		body,h1,h2,h3,h4,h5,h6{font-family:"微软雅黑";}
	</style>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<div class="col-md-3">
		<div class="list-group">
			<a href="/manage/myAccount/modifyPassword/" target="content_frame" class="list-group-item">修改密码</a>
			<!--a href="/manage/msg//" target="content_frame" class="list-group-item">新闻发布</a-->
		</div>
	</div>
	<div class="col-md-9">
		<iframe src="" frameborder="0" width="100%" id="content_frame" name="content_frame" onload=""></iframe>
	</div>
	
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(document).on('click', '.list-group a', function() {
			$(this).addClass('active').siblings().removeClass('active');
		});
		/*var contentFrameOnload = function() {
			if (/newsList/.test(window.content_frame.location.href)) {
				$('.list-group a').eq(0).addClass('active').siblings().removeClass('active');
			}
		}*/
		$(function() {
			$('.list-group a').eq(0)[0].click();
		});
	</script>
</body>
</html>