/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
(function ($) {
	$.extend($.validator.messages, {
		required: "不能为空",
		remote: "请修正该字段",
		email: "邮件格式有误",
		url: "网址格式有误",
		date: "日期格式有误",
		dateISO: "日期格式有误",
		number: "数字不合法",
		digits: "必须为正整数",
		creditcard: "信用卡号有误",
		equalTo: "请再次输入",
		accept: "后缀名有误",
		maxlength: $.validator.format("长度最多为{0}"),
		minlength: $.validator.format("长度最少为 {0}"),
		rangelength: $.validator.format("长度介于 {0} 和 {1}"),
		range: $.validator.format("值介于 {0} 和 {1}"),
		max: $.validator.format("最大为 {0} 的值"),
		min: $.validator.format("最小为 {0} 的值")
	});
}(jQuery));