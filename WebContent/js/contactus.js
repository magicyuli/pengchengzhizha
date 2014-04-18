/**
*  @author magicyuli
*  @page contactus
*
*/

define(['header', 'footer'],
function(header, footer) {
	header.doInit();
	footer.doInit();
	
	window.check = function() {
		var items = $('input,textarea').get();
		var i;
		for (i in items) {
			$(items[i]).val($.trim($(items[i]).val()));
		}
		items = $('[require="true"]').get();
		for (i in items) {
			if (! $.trim($(items[i]).val())) {
				alert("请将必填项填完整！");
				return false;
			}
		}
		if (! /^[0-9a-zA-Z_]+@([0-9a-zA-Z]+\.)+[a-zA-Z]+$/.test($('#email').val())) {
			alert("邮箱格式有误！");
			$('#email').focus();
			return false;
		}
		if ($('#email').val() != $('#confirmEmail').val()) {
			alert("两次输入的邮箱地址不一致！");
			$('#confirmEmail').focus();
			return false;
		}
		if (/[~`\!@#$%\^&\*\(\)_\+\|\{\}":\?><\[\]\\,\/！￥…（）—、“”‘’；：？〈〉《》	。，]+/.test($('#name').val())) {
			alert("姓名包含非法字符！");
			$('#name').focus();
			return false;
		}
		if (/<\/?[a-zA-Z]+\s*\/*\s*>/.test($('#subject').val())) {
			alert("留言主题包含非法内容！");
			$('#subject').focus();
			return false;
		}
		if (/<\/?[a-zA-Z]+\s*\/*\s*>/.test($('#content').val())) {
			alert("留言内容包含非法内容！");
			$('#content').focus();
			return false;
		}
		if ($('#company').val() && /<\/?[a-zA-Z]+\s*\/*\s*>/.test($('#company').val())) {
			alert("单位名称包含非法内容！");
			$('#company').focus();
			return false;
		}
		if ($('#phone').val() && ! /^\+?((\([0-9]+\))|(（[0-9]+）))?[0-9\-]{3,}$/.test($('#phone').val())) {
			alert("电话格式有误！");
			$('#phone').focus();
			return false;
		}
		if (! validateVC($('#validationCode').val())) {
			alert("验证码输入错误！");
			$('#VCImage').click();
			$('#validationCode').focus();
			return false;
		}
		return true;
	}
	
	function validateVC(VC) {
		var passed = false;
		$.ajax({
			async: false,
			type: 'post',
			url: '/validationCode/',
			dataType: 'json',
			data: {
				vc: VC
			},
			success: function(resp) {
				if (resp.passed == 1) {
					passed = true;
				}
			}
		});
		return passed;
	}
});