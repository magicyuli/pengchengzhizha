/**
*  @author magicyuli
*  @module header
*
*/
define(['tpls','jquery.tmpl'],
function(tpls) {
	function doInit() {
		render();
		addListeners();
	}
	
	function render() {
		$('#headerContainer').append($.tmpl(tpls.header));
		$('.nav').children().eq(SEQ).find('a').addClass('selected');
	}
	
	function addListeners() {
		
	}
	
	return {
		doInit: doInit
	};
});