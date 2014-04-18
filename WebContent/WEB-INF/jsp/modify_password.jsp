 <%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pengchengzhizha.entity.User" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<title>岛城鹏程脂渣后台新闻发布</title>
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
	<% User user = (User) request.getSession().getAttribute("user"); %>
	<form class="form-horizontal" id="newsForm" action="/manage/myAccount/modifyPassword/" method="post" role="form" onsubmit="return check();">
		<div class="container">
			<div class="form-group">
				<label for="oldPassword" class="col-sm-3 control-label">旧密码</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="oldPassword" name="oldPassword" maxLength="20" required="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="newPassword" class="col-sm-3 control-label">新密码</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="newPassword" name="newPassword" maxLength="20" required="true" />
				</div>
				<div class="col-sm-offset-3 col-sm-9">
					<p class="help-block">6-20个字符，只能包含字母数字以及下划线</p>
				</div>
			</div>
			<div class="form-group">
				<label for="confirmNewPassword" class="col-sm-3 control-label">确认新密码</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="confirmNewPassword" name="confirmNewPassword" maxLength="20" required="true" />
				</div>
			</div>
			<input type="hidden" id="userId" name="userId" />
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-9">
					<button type="submit" class="btn btn-default">提交</button>
				</div>
			</div>
		</div>
	</form>
	
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/tools.js"></script>
	<script type="text/javascript">
		$('#userId').val(<%=user.getId() %>);
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
			if (/[^a-zA-Z0-9_]/.test($('#newPassword').val())) {
				alert("新密码包含非法字符！");
				highlight($('#newPassword').get(0));
				return false;
			}
			if ($('#newPassword').val() !== $('#confirmNewPassword').val()) {
				alert("两次输入的新密码不一致！");
				highlight($('#confirmNewPassword').get(0));
				return false;
			}
			if ($('#newPassword').val().length < 6) {
				alert("新密码过短！");
				highlight($('#newPassword').get(0));
				return false;
			}
			return true;
		}
		function highlight(item) {
			$(item).focus().closest('.form-group').addClass('has-error');
		}
		window.onload = function() {
			TOOLS.adjustParentIframeHeight("content_frame");
		};
	</script>
	
</body>
</html>