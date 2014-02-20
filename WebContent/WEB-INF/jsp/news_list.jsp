<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%> 
<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible"  content="IE-edge" />
	<meta name="author" content="magicyuli" />
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/manage-common.css" />
	<link href="/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet" />
	<title>岛城鹏程脂渣后台新闻列表</title>
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
	<div class="table_container">
		<table id="newsListTable" class="table table-hover list_table">
			<thead>
				<tr>
					<th>#</th>
					<th>标题</th>
					<th>发布人</th>
					<th>最近更新人</th>
					<th>发布时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>
	
	<div class="pagination_container">
		<ul id="pagination" class="pagination">
		  <li id="firstPage"><a href="#">&lt;&lt;</a></li>
		  <li id="prevPage"><a href="#">&lt;</a></li>
		  <li data-page="1" class="page_num"><a href="#">1</a></li>
		  <li id="nextPage"><a href="#">&gt;</a></li>
		  <li id="lastPage"><a href="#">&gt;&gt;</a></li>
		</ul>
	</div>


	
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="/js/tools.js"></script>
	<script type="text/javascript" src="/js/tpls.js"></script>
	<script type="text/javascript" src="/js/conf.js"></script>
	<script type="text/javascript">
		var initPage = 1;
		var pageSize = CONF.news.list.pageSize.manage;
		var halfPage = CONF.news.list.halfPagination.manage;
		
		var renderTable = function(result) {
			var $tbody = $('#newsListTable tbody').empty();
			var i;
			for (i = 0; i < result.length; i++) {
				var array = [];
				array.push(i + 1 + (this.page - 1) * this.pageSize);
				array.push(TOOLS.truncate(result[i].title, 20));
				array.push(result[i].title);
				array.push(result[i].publishTime);
				array.push(result[i].id);
				array.push(result[i].publishUser.name);
				array.push(result[i].lastUpdateUser.name);
				$tbody.append($(TOOLS.hydrateTpl(TPLS.newsList.manage_item, array)));
			}
		}
		
		$(document).on('click', '.delete', function(e) {
			e.preventDefault();
			if (confirm('您确定删除吗？')) {
				window.location = $(this).attr('href');
			}
		});
		
		window.onload = function() {
			TOOLS.adjustParentIframeHeight("content_frame", 600);
		}
		
		TOOLS.listUtils.init(initPage, pageSize, halfPage, renderTable);
	</script>
</body>
</html>