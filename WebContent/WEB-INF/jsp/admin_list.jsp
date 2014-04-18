<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge,chrome=1" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/manage-common.css" />
	<link href="/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet" />
	<title>岛城鹏程脂渣后台用户列表</title>
	<style type="text/css">
		body,h1,h2,h3,h4,h5,h6{font-family:"微软雅黑";}
		#newsListTable tbody input{vertical-align:text-top;}
		#newsListTable tbody label{padding:0 10px 0 5px;}
		.btn_container{margin:40px 0;}
	</style>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="table_container">
		<table id="newsListTable" class="table table-hover list_table">
			<thead>
				<tr>
					<th>#</th>
					<th>用户名</th>
					<th>邮箱</th>
					<th>权限</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		<div class="btn_container">
			<button type="button" class="btn btn-primary btn-block btn-lg" id="submit">提交</button>
		</div>
	</div>
	
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/tools.js"></script>
	<script type="text/javascript" src="/js/tpls.js"></script>
	<script type="text/javascript" src="/js/conf.js"></script>
	<script type="text/javascript">
		var initPage = 1;
		var pageSize = CONF.admin.list.pageSize;
		var ajaxUrl = CONF.admin.list.ajaxUrl;
		var halfPage = CONF.admin.list.halfPagination;
		
		var changedTRs = {};
		
		var renderTable = function(result) {
			var $tbody = $('#newsListTable tbody').empty();
			var i;
			for (i = 0; i < result.length; i++) {
				var array = [];
				array.push(i + 1 + (this.page - 1) * this.pageSize);
				array.push(result[i].name);
				array.push(result[i].email);
				array.push(result[i].isAdmin ? 'checked' : '');
				array.push(result[i].isNewsManager ? 'checked' : '');
				array.push(result[i].isMessageManager ? 'checked' : '');
				array.push(result[i].id);
				$tbody.append($(TOOLS.hydrateTpl(TPLS.adminList.item, array)));
			}
		}
				
		window.onload = function() {
			TOOLS.adjustParentIframeHeight("content_frame", 600);
		}
		
		$(document).on('change', ':checkbox', function() {
			var $tr = $(this).closest('tr');
			changedTRs[$tr.index()] = $tr;
		}).on('click', '#submit', function() {
			var data = {
				authList: []
			};
			for (var k in changedTRs) {
				var $tr = changedTRs[k];
				if ($tr.has('input:checked:not([checked]), input[checked]:not(:checked)').length) {
					var obj = {};
					obj['id'] = parseInt($tr.attr('data-id'));
					obj['isAdmin'] = $tr.find('.is_admin').prop('checked') ? 1 : 0;
					obj['isNewsManager'] = $tr.find('.is_news_manager').prop('checked') ? 1 : 0;
					obj['isMessageManager'] = $tr.find('.is_message_manager').prop('checked') ? 1 : 0;
					data.authList.push(obj);
				}
			}
			if (! data.authList.length) {
				alert('没有任何改变，不必提交！');
				return;
			}
			$.ajax({
				url: '/manage/admin/updateAuth',
				type: 'post',
				contentType: 'application/json',
				data: JSON.stringify(data),
				success: function(resp) {
					if (resp.success) {
						alert("权限更新成功！");
						window.location.reload();
					} else {
						alert("权限更新失败！");
						window.location.reload();
					}
				},
				error: function() {
					alert("权限更新失败！");
					window.location.reload();
				}
			});
		});
		
		
		TOOLS.listUtils.init(initPage, pageSize, halfPage, ajaxUrl, renderTable);
	</script>
</body>
</html>