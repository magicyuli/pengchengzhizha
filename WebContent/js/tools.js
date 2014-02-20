/**
*  @author magicyuli
*  @module TOOLS
*
*/

(function(window, $) {

var tools = window.TOOLS = window.TOOLS || {};

tools["truncate"] = function(str, len) {
	return str.length > len ? str.substr(0, len) + "..." : str;
}

tools["hydrateTpl"] = function(tpl, items) {
	for (var i = 0; i < items.length; i++) {
		tpl = tpl.replace(/\$\{(\d)\}/g, function(match, s1, p) {
			if (s1 == i) {
				return items[i];
			} else {
				return match;
			}
		});
	}
	return tpl;
}

tools["adjustParentIframeHeight"] = function(id, contentMinHeight) {
	$(parent.document).find('#' + id).attr('height', Math.max(window.document.body.scrollHeight + 20, contentMinHeight));
	$(top.document).find('iframe').attr('height', top.main_frame.document.body.scrollHeight + 20);
}

})(window, $);


/**
*  @author magicyuli
*  @module TOOLS.listUtils
*
*/

(function(tools, $) {

var listUtils = tools.listUtils = tools.listUtils || {};

var _totalPage, _page, _pageSize, _halfPage, _render;

listUtils["init"] = function(page, pageSize, halfPage, render) {
	_page = page;
	_pageSize = pageSize;
	_halfPage = halfPage;
	_render = render;
	
	$(document).on('click', '#pagination a', function(e) {
		e.preventDefault();
	}).on('click', '#pagination li', function(e) {
		if (! $(this).hasClass('disabled') && ! $(this).hasClass('active')) {
			switch ($(this).attr('id')) {
				case 'firstPage':
					_page = 1;
					break;
				case 'prevPage':
					_page--;
					break;
				case 'lastPage':
					_page = _totalPage;
					break;
				case 'nextPage':
					_page++;
					break;
				default:
					_page = parseInt($(this).attr('data-page'));
			}
			listUtils.fetchAndRenderList();
		}
	});
	listUtils.fetchAndRenderList();
}

listUtils["fetchAndRenderList"] = function() {
	$.ajax({
		async: false,
		url: "/news/newsListJSON/",
		type: "post",
		dataType: "json",
		data: {
			page: _page,
			pageSize: _pageSize
		},
		success: function(response) {
			var result = response.pageContent;
			listUtils.page = _page;
			listUtils.pageSize = _pageSize;
			_render.call(listUtils, result);
			_totalPage = Math.ceil(response.total / _pageSize);
			renderPagination(_totalPage, _page, _pageSize, _halfPage);
		}
	});
}

function renderPagination(totalPage, page, pageSize, halfPage) {
	var $prev = $('#prevPage');
	var $next = $('#nextPage');
	var $ellipsis = $('<li class="ellipsis disabled"><span>...</span></li>');
	var $pageNum = $('<li data-page="" class="page_num"><a href="#"></a></li>');
	
	$('.page_num').add('.ellipsis').remove();
	clonePageNum(page).addClass('active').insertBefore($next);
	
	for (var i = 1; i <= halfPage; i++) {
		if (page - i > 0) {
			clonePageNum(page - i).insertAfter($prev);
		}
		if (page + i <= totalPage) {
			clonePageNum(page + i).insertBefore($next);
		}
	}
	
	if (page - halfPage > 1) {
		$ellipsis.clone().insertAfter($prev);
	}
	if (page + halfPage < totalPage) {
		$ellipsis.clone().insertBefore($next);
	}
	
	if (page == 1) {
		$prev.addClass('disabled').prev().addClass('disabled');
	} else {
		$prev.removeClass('disabled').prev().removeClass('disabled');
	}
	if (page == totalPage) {
		$next.addClass('disabled').next().addClass('disabled');
	} else {
		$next.removeClass('disabled').next().removeClass('disabled');
	}
		
	function clonePageNum(page) {
		return $pageNum.clone().attr('data-page', page).find('a').text(page).end();
	}

}


})(window.TOOLS, $);