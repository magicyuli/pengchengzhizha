<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="/css/login.css"/>
	<title>岛城鹏程脂渣登录</title>
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
	<div class="container">

      <form class="form-resend" role="form" action="/register/resendActivationEmail/" method="post">
        <h2 class="form-resend-heading">请填写邮箱</h2>
        <input type="email" name="email" class="form-control" placeholder="邮箱" required autofocus />
        <button class="btn btn-lg btn-primary btn-block" type="submit">重新发送</button>
      </form>

    </div>
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		var check = function() {
			$('.form-group').removeClass('has-error');
			var items = $('input[required=true]').get();
			for (var i = 0; i < items.length; i++) {
				if (! $.trim($(items[i]).val())) {
					alert('请填充必填信息！');
					highlight(items[i]);
					return false;
				}
			}
			if (! /^[0-9a-zA-Z_]+@([0-9a-zA-Z]+\.)+[a-zA-Z]+$/.test($('#email').val())) {
				alert("邮箱格式有误！");
				highlight($('#email').get(0));
				return false;
			}
			return true;
		}
		function highlight(item) {
			$(item).focus().closest('.form-group').addClass('has-error');
		}
	</script>
</body>
</html>