/**
*  @author magicyuli
*  @module footer
*
*/
define(['tpls','jquery.tmpl'],
function(tpls) {
	function doInit() {
		render();
		addListeners();
	}
	
	function render() {
		$('#footer').append($.tmpl(tpls.footer));
	}
	
	function addListeners() {
		$(document).on('mousemove', 'body', function() {
			if ($('a[href^="http://tongji.baidu.com/hm-web/welcome/ico"]').remove().length) {
				$(document).off('mousemove');
			};
		});
	}
	
	return {
		doInit: doInit
	};
});