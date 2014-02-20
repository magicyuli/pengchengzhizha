/**
*  @author magicyuli
*  @conf global
*
*/

(function() {
window.CONF = window.CONF || {};

var news = CONF.news = {};
var msg = CONF.message = {};





news.list = {};
news.list.pageSize = {
	manage : 12,
	pub : 15
}
news.list.halfPagination = {
	manage : 4,
	pub : 5
}




msg.list = {};
msg.list.pageSize = 10;
msg.list.halfPagination = 4;

})();