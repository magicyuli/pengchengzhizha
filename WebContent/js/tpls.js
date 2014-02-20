/**
*  @author magicyuli
*  @module templates
*
*/

typeof define === "function" && define({
    "footer":'﻿<div class="bot_dec w1220"></div><p>	<span>版权所有：岛城鹏程脂渣</span>	<span>联系地址：山东省青岛市城阳区上马街道李仙庄工业园</span>	<span>联系电话：0532-87010077</span>	<span>手机：18561326777</span>	<span>网站邮箱：<a href="mailto:18561326777@163.com">18561326777@163.com</a></span></p><p>	<span>传真：0532-87010066</span>	<span>备案序号：青123458789P12</span></p>',
    "header":'﻿<div class="top_bar"></div><div id="header" class="header">	<div class="title"></div>	<div class="logo center"></div>	<div class="opts">		<ul class="clearfix">			<li id="favorite" class="fl">岛城鹏程</li>			<li id="homepage" class="fl">欢迎您来</li>		</ul>	</div>	<div class="contact">		<span class="phone">0532-87010077</span>		<span class="time">周一至周日8:00-18:00</span>	</div>	<ul class="nav center clearfix">		<li class="fl"><a href="/index.html">網站首頁</a></li>		<li class="fl"><a href="/about.html">關於我們</a></li>		<li class="fl"><a href="/history.html">歷史傳說</a></li>		<li class="fl"><a href="/display.html">產品展示</a></li>		<li class="fl"><a href="/joinus.html">加入我們</a></li>		<li class="fl"><a href="http://shop70021591.taobao.com/" target="_blank">網上商城</a></li>		<li class="fl"><a href="/contactus.html">聯系我們</a></li>	</ul></div>'
});

(function() {

window.TPLS = window.TPLS || {};
TPLS.newsList = {
	public_item : '<tr><td>${0}</td><td><a target="_blank" title="${2}" href="/news/display/?id=${4}">${1}</a></td><td>${3}</td></tr>',
	manage_item : '<tr><td>${0}</td><td><a target="_blank" title="${2}" href="/news/display/?id=${4}">${1}</a></td><td>${5}</td><td>${6}</td><td>${3}</td><td><a target="_self" href="/manage/news/edit/?id=${4}" style="margin-right:5px;">编辑</a><a target="_self" href="/manage/news/delete/?id=${4}" class="delete">删除</a></td></tr>'
};

})();
