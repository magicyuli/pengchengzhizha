/**
*  @author magicyuli
*  @page news_list.html
*
*/

define(['header', 'footer', "tools", "conf"],
function(header, footer) {
	header.doInit();
	footer.doInit();
	
	var initPage = 1;
	var pageSize = CONF.news.list.pageSize.pub;
	var halfPage = CONF.news.list.halfPagination.pub;
	
	var renderTable = function(result) {
		var $tbody = $('#newsListTable tbody').empty();
		var i;
		for (i = 0; i < result.length; i++) {
			var array = [];
			array.push(i + 1 + (this.page - 1) * this.pageSize);
			array.push(TOOLS.truncate(result[i].title, 50));
			array.push(result[i].title);
			array.push('[' + result[i].publishTime.substr(0, 10) + ']');
			array.push(result[i].id);
			$tbody.append($(TOOLS.hydrateTpl(TPLS.newsList.public_item, array)));
		}
	}
	
	TOOLS.listUtils.init(initPage, pageSize, halfPage, renderTable);
});