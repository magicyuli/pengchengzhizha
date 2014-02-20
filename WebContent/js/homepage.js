/**
*  @author magicyuli
*  @page index
*
*/

define(['header', 'footer'],
function(header, footer) {
	header.doInit();
	footer.doInit();
	
	var small = {},
		big = {};
		
	window.slideSmall = function() {
		small.cur = small.cur + 1 > small.LAST ? 0 : small.cur + 1;
		$('.slideshow.small').find('.slide_pointer li').eq(small.cur).click();
		setSmallTimeout(3000);
	};
	
	window.slideBig = function() {
		big.cur = big.cur + 1 > big.LAST ? 0 : big.cur + 1;
		$('.slideshow.big').find('.slide_pointer li').eq(big.cur).click();
		setBigTimeout(3000);
	}

	$(function() {
		addListeners();
		initTimeouts();
	});
	
	function addListeners() {
		$(document).on('click', '.slide_pointer li', function() {
			var pics = $(this).parent().prev();
			var width = pics.children().width();
			var index = $(this).index();
			pics.animate({left: - width * index});
			$(this).addClass('onselected').siblings().removeClass('onselected');
			if (pics.hasClass('big')) {
				big.cur = index;
				setBigTimeout(6000);
			} else if (pics.hasClass('small')) {
				small.cur = index;
				setSmallTimeout(6000);
			}
		}).on('click', '#arrow_right', function() {
			var last = $('.recoms').children().length - 1;
			var width = $('.recom_unit').width();
			var length = $('.recom_slide').outerWidth();
			if ($('.recoms').children().eq(last).offset().left + width < $('.recom_slide').offset().left + length) {
				return;
			}
			$('.recoms').animate({left: "-=" + (width + 12)});
		}).on('click', '#arrow_left', function() {
			if ($('.recoms').children().eq(0).offset().left > $('.recom_slide').offset().left) {
				return;
			}
			var width = $('.recom_unit').width();
			var length = $('.recoms').width();
			$('.recoms').animate({left: "+=" + (width + 12)});
			
		}).on('click', '#playVideo', function() {
			_left = ($(window).width() - 900) / 2;
			_top = 100;
			$('body').append($('<div class="mask"><div id="videoContainer" style="position:absolute;top:' + _top + 'px;left:' + _left + 'px"><object id="tudouHomePlayer" width="900" height="450" type="application/x-shockwave-flash" data="http://js.tudouui.com/bin/lingtong/PortalPlayer_62.swf" name="tudouHomePlayer"><param value="true" name="allowfullscreen"><param value="always" name="allowscriptaccess"><param value="#000000" name="bgcolor"><param value="window" name="wMode"><param value="high" name="quality"><param value="abtest=0&referrer=&href=http%3A%2F%2Fwww.tudou.com%2Fprograms%2Fview%2FaLrwvZtEeQs%2F%3Fqq-pf-to%3Dpcqq.c2c&USER_AGENT=Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20WOW64%3B%20rv%3A25.0)%20Gecko%2F20100101%20Firefox%2F25.0&yjuid=&yseid=1384430787544CSEqZc&ypvid=1384430787543TI8NQw&yrpvid=&yrct=&frame=0&noCookie=0&yseidtimeout=1384437987554&yseidcount=1&fac=0&aop=0&showWS=1&wtime=0&lb=0&scale=0&dvd=0&hideDm=0&ahcb=undefined&icode=aLrwvZtEeQs&iid=180405824&sp=http%3A%2F%2Fi1.tdimg.com%2F180%2F405%2F824%2Fp.jpg&s...%BE%E5%B9%B4%E7%BB%8F%E5%85%B8&mediaType=vi&np=0&sh=0&st=0&videoOwner=100741223&time=108&vcode=&ymulti=&listType=0&listCode=&lid=&paid=&paidTime=&paidType=&uid=0&juid=0189b82mu01ts&seid=0189b82mv82vgn&vip=0&actionID=0&resourceId=&tpa=&pepper=&panelEnd=http://css.tudouui.com/bin/lingtong/PanelEnd_10.swz&panelRecm=http://css.tudouui.com/bin/lingtong/PanelRecm_9.swz&panelShare=http://css.tudouui.com/bin/lingtong/PanelShare_7.swz&panelCloud=&panelDanmu=http://css.tudouui.com/bin/lingtong/PanelDanmu_1.swz&aca=" name="flashvars"></object></div></div>'));
		}).on('click', '.mask', function() {
			$(this).remove();
		});
	}
	
	function initTimeouts() {
		small.LAST = big.LAST = 4;
		small.cur = big.cur = 0;
		setTimeouts();
	}
	
	function setTimeouts() {
		setSmallTimeout(3000);
		setBigTimeout(3000);
	}
	
	function setSmallTimeout(time) {
		clearTimeout(small.TO);
		small.TO = setTimeout("window.slideSmall()", time);
	}
	
	function setBigTimeout(time) {
		clearTimeout(big.TO);
		big.TO = setTimeout("window.slideBig()", time);
	}
});