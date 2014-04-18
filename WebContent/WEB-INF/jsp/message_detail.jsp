<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="com.pengchengzhizha.entity.Message"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/manage-common.css" />
	<link rel="stylesheet" href="/css/message_detail.css" />
	<link href="/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet" />
	<title>岛城鹏程脂渣后台留言详情</title>
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
	<%
		Message message = (Message) request.getAttribute("message");
		if (message == null) {
			response.sendError(500, "获取留言失败！");
		} 
	%>
	<script type="text/javascript">
		var messageId = <%=message.getId() %>;
	</script>
	<div class="panel panel-default" id="messagePanel">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-question-sign"></span>
			<span id="subject"><%=message.getSubject() %></span>
			<span id="sendingTime" class="fr"><%=message.getSendingTime() %></span>
		</div>
		<div class="panel-body">
			<p id="content"><%=message.getContent() %></p>
		</div>
		<div class="panel-footer">
			<span class=" glyphicon glyphicon-user"></span>
			<span id="senderName"><%=message.getSenderName() %></span>
			<span class="vertical_delimiter">|</span>
			<span id="senderPhone"><%=message.getSenderPhone() %></span>
			<span class="vertical_delimiter">|</span>
			<span id="senderEmail"><%=message.getSenderEmail() %></span>
			<span class="vertical_delimiter">|</span>
			<span id="senderCompany"><%=message.getSenderCompany() %></span>
		</div>
	</div>
	
	<div class="panel panel-primary" id="replyPanel">
		<%
			if (message.isReplied()) {
		%>
		<div class="panel-heading replied">
			<span class="glyphicon glyphicon-ok-sign"></span>
			<span id="replyingUser"><%=message.getReplyingUser().getName() %></span>
			<span id="replyingTime" class="fr"><%=message.getReplyingTime() %></span>
		</div>
		<div class="panel-body replied">
			<p id="replyContent"><%=message.getReplyContent() %></p>
		</div>
		<!--div class="panel-footer"></div-->
		<%
			} else {
		%>
		<div class="panel-heading not_replied">
			<span class="">回复方式: </span>
			<label for="byEmail"><span class="glyphicon glyphicon-envelope"></span> </label><input type="radio" name="reply_method" id="byEmail" checked="true" value="0" />
			<label for="byPhone"><span class="glyphicon glyphicon-phone-alt"></span> </label><input type="radio" name="reply_method" id="byPhone" value="1" />
		</div>
		<div class="panel-body not_replied">
			<div id="ueditor"></div>
		</div>
		<div class="panel-footer">
			<button type="button" class="btn btn-primary" id="replyBtn">发送邮件</button>
		</div>
		<script src="/ueditor/ueditor.config.js" charset="utf-8" type="text/javascript"></script>
		<script src="/ueditor/ueditor.all.min.js" charset="utf-8" type="text/javascript"></script>
		<script src="/ueditor/lang/zh-cn/zh-cn.js" charset="utf-8" type="text/javascript"></script>
		<script type="text/javascript">
			UEDITOR_CONFIG.initialFrameHeight = 320;
			var editor = UE.getEditor('ueditor');
		
			$(document).on('change', '#byEmail', function() {
				if ($(this).prop('checked')) {
					$('.panel-body.not_replied').show();
					$('#replyBtn').text("发送邮件");
				}
			}).on('change', '#byPhone', function() {
				if ($(this).prop('checked')) {
					$('.panel-body.not_replied').hide();
					$('#replyBtn').text("标记为已回复");
				}
			}).on('click', '#replyBtn', reply);
			
			function reply() {
				$(document).off('click', '#replyBtn', reply);
				$('#replyBtn').addClass('disabled');
				var data = {
						id: messageId,
						method: $('#byEmail').prop('checked') ? $('#byEmail').val() : $('#byPhone').val(),
						content: $('#byEmail').prop('checked') ? editor.getContent() : "",
						email: $('#senderEmail').text(),
						subject: $('#subject').text()
					}
				$.ajax({
					url: '/manage/message/reply/',
					type: 'post',
					data: data,
					dataType: 'JSON',
					success: function(result) {
						if (result.succeeded == 1) {
							alert('回复成功！');
							window.location.reload();
						} else {
							alert('回复失败！');
							$(document).on('click', '#replyBtn', reply);
							$('#replyBtn').removeClass('disabled');
						}
					},
					error: function() {
						alert('回复失败！');
						$(document).on('click', '#replyBtn', reply);
						$('#replyBtn').removeClass('disabled');
					}
				});
			}
		</script>
		<% 
			}
		%>
	</div>


	
	
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/tools.js"></script>
	<script type="text/javascript" src="/js/tpls.js"></script>
	<script type="text/javascript" src="/js/conf.js"></script>
	<script type="text/javascript">
		
		
		$(document).on('change', '.range_radio', function(e) {
			TOOLS.listUtils.setPage(initPage);
			TOOLS.listUtils.fetchAndRenderList();
		});
		
		window.onload = function() {
			TOOLS.adjustParentIframeHeight("content_frame", 600);
		}
		
		
	</script>
</body>
</html>