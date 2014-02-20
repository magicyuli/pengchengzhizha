<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pengchengzhizha.entity.News" %>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link href="/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet" />
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
	<% News news = (News) request.getAttribute("news"); %>
	<form id="newsForm" action="/manage/news/save/" method="post" role="form" onsubmit="return check();">
		<div class="form-group">
			<label for="">新闻标题</label>
			<input type="text" class="form-control" id="title" name="title" />
		</div>
		<div id="ueditor" style="margin-bottom:15px;" name="content"></div>
		<input type="hidden" id="isEdit" name="isEdit" value="0" />
		<input type="hidden" id="newsId" name="newsId" />
		<button type="submit" class="btn btn-default">发布</button>
	</form>
	
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script src="/ueditor/ueditor.config.js" charset="utf-8" type="text/javascript"></script>
	<script src="/ueditor/ueditor.all.min.js" charset="utf-8" type="text/javascript"></script>
	<script src="/ueditor/lang/zh-cn/zh-cn.js" charset="utf-8" type="text/javascript"></script>
	<script type="text/javascript" src="/js/tools.js"></script>
	<script type="text/javascript">
		var editor = UE.getEditor('ueditor');
		var check = function() {
			if (! editor.hasContents()) {
				alert("请输入正文内容！");
				return false;
			}
			if (! $('#title').val().trim()) {
				alert("请输入标题！");
				return false;
			}
			return true;
		}
		window.onload = function() {
			TOOLS.adjustParentIframeHeight("content_frame");
		};
	</script>
	<%
		if (news != null) {
	%>
	<script type="text/javascript">
		window.onload = function() {
			$('#title').val('<%=news.getTitle() %>');
			editor.setContent('<%=news.getContent() %>');
			$('#isEdit').val('1');
			$('#newsId').val('<%=news.getId() %>');
			TOOLS.adjustParentIframeHeight("content_frame");
		}	
	</script>
	<%
		}
	%>
</body>
</html>