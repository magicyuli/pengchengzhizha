/**
*  @author magicyuli
*  @conf global
*
*/

(function() {
window.CONF = window.CONF || {};

var news = CONF.news = {};
var message = CONF.message = {};
var admin = CONF.admin = {};





news.list = {};
news.list.pageSize = {
	manage : 12,
	pub : 15
}
news.list.halfPagination = {
	manage : 4,
	pub : 5
}
news.list.ajaxUrl = {
	pub : "/news/newsListJSON/",
	manage : "/manage/news/newsListJSON/"
}




message.list = {};
message.list.pageSize = 10;
message.list.halfPagination = 4;
message.list.ajaxUrl = "/manage/message/messageListJSON/";



admin.list = {};
admin.list.pageSize = 10000;
admin.list.halfPagination = 4;
admin.list.ajaxUrl = "/manage/admin/adminListJSON/";


})();