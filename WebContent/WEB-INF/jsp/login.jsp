<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge" />
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

      <form class="form-signin" role="form" action="/login/" method="post">
        <h2 class="form-signin-heading">请登录</h2>
        <input type="text" name="name" class="form-control" placeholder="用户名" required autofocus />
        <input type="password" name="password" class="form-control" placeholder="密码" required />
		<input type="hidden" name="url" />
        <label class="checkbox">
          <input type="checkbox" name="remember_me" value="yes"> 下次自动登录
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
      </form>

    </div>
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<%
		if (request.getAttribute("msg") != null) {
	%>
	<script type="text/javascript">
		alert('<%=request.getAttribute("msg") %>');
	</script>
	<%
		}
		if (request.getAttribute("url") != null) {
	%>
	<script type="text/javascript">
		document.forms[0].elements['url'].value = '<%=request.getAttribute("url") %>';
	</script>
	<%
		}
	%>
	<script type="text/javascript">
		if (location.search) {
			document.forms[0].elements['url'].value = unescape(location.search.split('?')[1].split('=')[1]) || "";
		}
	</script>
</body>
</html>