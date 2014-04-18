<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css"/>
	<link rel="stylesheet" href="/css/login.css"/>
	<title>岛城鹏程脂渣注册</title>
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

      <form class="form-horizontal form-signup" id="newsForm" action="/register/save/" method="post" role="form" onsubmit="return check();">
		<div class="container">
			<h2 class="col-sm-offset-4 form-signup-heading" style="padding:10px;">请注册</h2>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">用户名</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="name" name="name" maxLength="20" required="true" autofocus />
				</div>
				<div class="col-sm-offset-4 col-sm-4">
					<p class="help-block">4-20个字符，只能包含字母数字以及下划线</p>
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-4 control-label">密码</label>
				<div class="col-sm-4">
					<input type="password" class="form-control" id="password" name="password" maxLength="20" required="true" />
				</div>
				<div class="col-sm-offset-4 col-sm-4">
					<p class="help-block">6-20个字符，只能包含字母数字以及下划线</p>
				</div>
			</div>
			<div class="form-group">
				<label for="confirmPassword" class="col-sm-4 control-label">确认密码</label>
				<div class="col-sm-4">
					<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" maxLength="20" required="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-4 control-label">邮箱</label>
				<div class="col-sm-4">
					<input type="email" class="form-control" id="email" name="email" maxLength="50" required="true" />
				</div>
				<div class="col-sm-offset-4 col-sm-4">
					<p class="help-block">请输入有效邮箱，将用作激活帐户<a href="/register/resendActivationEmail/">重新发送激活邮件？</a></p>
				</div>
			</div>
			<div class="form-group">
				<label for="confirmEmail" class="col-sm-4 control-label">确认邮箱</label>
				<div class="col-sm-4">
					<input type="email" class="form-control" id="confirmEmail" name="confirmEmail" maxLength="50" required="true" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-4">
					<button type="submit" class="btn btn-primary btn-block">提交</button>
				</div>
			</div>
		</div>
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
			if (/[^a-zA-Z0-9_]/.test($('#name').val())) {
				alert("用户名包含非法字符！");
				highlight($('#name').get(0));
				return false;
			}
			if ($('#name').val().length < 4) {
				alert("用户名过短！");
				highlight($('#name').get(0));
				return false;
			}
			if (isNameOccupied($('#name').val())) {
				alert("用户名被占用！");
				highlight($('#name').get(0));
				return false;
			}
			if (/[^a-zA-Z0-9_]/.test($('#password').val())) {
				alert("密码包含非法字符！");
				highlight($('#password').get(0));
				return false;
			}
			if ($('#password').val().length < 6) {
				alert("密码过短！");
				highlight($('#password').get(0));
				return false;
			}
			if ($('#password').val() !== $('#confirmPassword').val()) {
				alert("两次输入的密码不一致！");
				highlight($('#confirmPassword').get(0));
				return false;
			}
			if (! /^[0-9a-zA-Z_]+@([0-9a-zA-Z]+\.)+[a-zA-Z]+$/.test($('#email').val())) {
				alert("邮箱格式有误！");
				highlight($('#email').get(0));
				return false;
			}
			if ($('#email').val() != $('#confirmEmail').val()) {
				alert("两次输入的邮箱地址不一致！");
				highlight($('#confirmEmail').get(0));
				return false;
			}
			if (isEmailOccupied($('#email').val())) {
				alert("该邮箱已注册过！");
				highlight($('#email').get(0));
				return false;
			}
			return true;
		}
		function highlight(item) {
			$(item).focus().closest('.form-group').addClass('has-error');
		}
		function isNameOccupied(name) {
			var occupied = true;
			$.ajax({
				async: false,
				url: '/register/checkName/',
				type: 'post',
				data: {
					name: name
				},
				success: function(resp) {
					if (resp.passed) {
						occupied = false;
					}
				},
				error: function(a,b,c) {
					alert("服务器故障，注册失败！");
				}
			});
			return occupied;
		}
		function isEmailOccupied(email) {
			var occupied = true;
			$.ajax({
				async: false,
				url: '/register/checkEmail/',
				type: 'post',
				dataType: 'json',
				data: {
					email: email
				},
				success: function(resp) {
					if (resp.passed) {
						occupied = false;
					}
				},
				error: function() {
					alert("服务器故障，注册失败！");
				}
			});
			return occupied;
		}
	</script>
</body>
</html>