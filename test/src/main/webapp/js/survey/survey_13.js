function checkSurvey(flag) {
	if (flag) {
		layer.closeAll();
	}
	flag = checkStoreState(flag);
	flag = checkaddress(flag);
	flag = checkDate(flag);
	flag = checkDistribution(flag);
	flag = checkPrice(flag);
	flag = checkShelf(flag);
	flag = checkShelf2(flag);
	flag = checkImge(flag)
	flag = checkCause(flag)
	return flag;
}
/**
 * 判断门面地址是否正确
 * 
 */
$(function() {
	$("#461_164_80").blur(function() {
		var vules = $("#461_164_80").val();
		if (vules == 1) {
			$("#461_165_80").attr("disabled", "disabled");
			$("#461_165_80").val('');
		} else {
			$("#461_165_80").removeAttr("disabled");
		}
	})
	$("#462_168_81").click(function() {
		var val = $("#462_168_81").select2("val");
		if (val != 8) {
			$("#462_169_81").attr("disabled", true);
		} else {
			$("#462_169_81").attr("disabled", false);
		}

		if (val == 1) {
			for (var j = 63; j <= 89; j++) {
				for (var i = 70; i <= 79; i++) {
					$("#4" + j + "_1" + i + "_82").attr("disabled", false);
				}
			}
		} else {
			for (var j = 63; j <= 89; j++) {
				for (var i = 70; i <= 79; i++) {
					$("#4" + j + "_1" + i + "_82").attr("disabled", true);
					$("#4" + j + "_1" + i + "_82").val('');
				}
			}
		}
	})

})
/**
 * 门店状态
 */
function checkStoreState(flag) {
	var val = $("#462_168_81").select2("val");
	if (isEmpty(val)) {
		layer.tips('不能为空', '#s2id_462_168_81', {
			tips : [ 2, '#3595CC' ],
			time : 10000,
			tipsMore : true
		});
		flag = false;
	} else if (val == 1) {
		for (var j = 63; j <= 89; j++) {
			for (var i = 70; i <= 78; i++) {
				var b = $("#4" + j + "_1" + i + "_82").val();
				if (isEmpty(b)) {
					layer.tips('不能为空', "#4" + j + "_1" + i + "_82", {
						tips : [ 2, '#3595CC' ],
						time : 10000,
						tipsMore : true
					});
					flag = false;
				}
			}
		}
	}
	return flag;
}

function checkDistribution(flag) {
	for (var j = 63; j <= 89; j++) {
		var val = $("#4" + j + "_170_82").val();
		if (val == 0) {
			for (var i = 71; i <= 78; i++) {
				var a = $("#4" + j + "_1" + i + "_82").val();
				if (a != 0) {
					layer.tips('只能为0', "#4" + j + "_1" + i + "_82", {
						tips : [ 2, '#3595CC' ],
						time : 10000,
						tipsMore : true
					});
					flag = false;
				}
			}
			if (!isEmpty($("#4" + j + "_179_82").val())) {
				layer.tips('备注不能填写', "#4" + j + "_179_82", {
					tips : [ 2, '#3595CC' ],
					time : 10000,
					tipsMore : true
				});
				flag = false;
			}
		}
	}
	/*
	 * for (var j = 63; j <= 89; j++) { var val = $("#4" + j + "_170_82").val();
	 * if (val == 1) { var ids = new Array("463_171_82", "463_173_82",
	 * "463_174_82", "464_171_82", "464_173_82", "464_174_82", "465_171_82",
	 * "465_173_82", "465_174_82", "466_171_82", "466_173_82", "466_174_82");
	 * $.each(ids, function(index, val) { var v = $("#" + val).val(); if
	 * (isEmpty(v)) { layer.tips('不能为空', "#" + val, { tips : [ 2, '#3595CC' ],
	 * time : 10000, tipsMore : true }); flag = false; $("#4" + j +
	 * "_179_82").attr("disabled", false); } }); } else if (val == 2 || val ==
	 * 3) { var v = $("#4" + j + "_171_82").val(); if (isEmpty(v)) {
	 * layer.tips('不能为空', "#4" + j + "_171_82", { tips : [ 2, '#3595CC' ], time :
	 * 10000, tipsMore : true }); flag = false; $("#4" + j +
	 * "_179_82").attr("disabled", false); } } }
	 */
	return flag;
}
/**
 * 最低零售价必须小于建议零售价
 * 
 * @param flag
 * @returns {Boolean}
 */
function checkPrice(flag) {
	for (var j = 63; j <= 89; j++) {
		var val = $("#4" + j + "_171_82").val();
		var a = parseInt(val);
		var val2 = $("#4" + j + "_172_82").val();
		var b = parseInt(val2);
		if (!isEmpty(val)) {
			if (isEmpty(val2)) {
				layer.tips('不能为空', "#4" + j + "_172_82", {
					tips : [ 2, '#3595CC' ],
					time : 10000,
					tipsMore : true
				});
				flag = false;
			}
		if (a < b) {
				layer.tips('最低零售价必须小于建议零售价', "#4" + j + "_172_82", {
					tips : [ 2, '#3595CC' ],
					time : 10000,
					tipsMore : true
				});
				flag = false;
			}
		}
	
	}
	
	return flag;
}
/**
 * 如果为空返回 true 否则返回 false
 * 
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
	if (null == val || '' == val) {
		return true;
	} else {
		return false;
	}
}

/**
 * 判断正确地址是否能填写
 * 
 * @param flag
 * @returns {Boolean}
 */
function checkaddress(flag) {
	var val = $("#461_164_80").val();
	if (val == 0) {
		if (isEmpty($("#461_165_80").val())) {
			layer.tips('不能为空', '#461_165_80', {
				tips : [ 2, '#3595CC' ],
				time : 10000,
				tipsMore : true
			});
			flag = false;
		}

	}
	return flag;
}
/**
 * 校验时间
 * 
 * @param flag
 * @returns {Boolean}
 */
function checkDate(flag) {
	var startDate = $("#461_166_80").val();
	if (isEmpty(startDate)) {
		layer.tips('不能为空', '#461_166_80', {
			tips : [ 2, '#3595CC' ],
			time : 10000,
			tipsMore : true
		});
		flag = false;
	}

	var endDate = $("#461_167_80").val();
	if (isEmpty(endDate)) {
		layer.tips('不能为空', '#461_167_80', {
			tips : [ 2, '#3595CC' ],
			time : 10000,
			tipsMore : true
		});
		flag = false;
	}

	var d1 = new Date(startDate.replace(/\-/g, "\/"));
	var d2 = new Date(endDate.replace(/\-/g, "\/"));

	if (d1 >= d2) {
		layer.tips('开始时间不能大于结束时间', '#461_166_80', {
			tips : [ 2, '#3595CC' ],
			time : 10000,
			tipsMore : true
		});
		flag = false;
	}

	return flag;
}

function checkShelf(flag) {
	for (var j = 63; j <= 89; j++) {
		var val = $("#4" + j + "_174_82").val();
		if (val == 1) {
			if (isEmpty($("#4" + j + "_175_82").val())) {
				layer.tips('不能为空', "#4" + j + "_175_82", {
					tips : [ 2, '#3595CC' ],
					time : 10000,
					tipsMore : true
				});
				flag = false;
			}
		}
	}
	for (var j = 63; j <= 89; j++) {
		var val = $("#4" + j + "_174_82").val();
		if (val == 2 || val == 3 || val == 4 || val == 5) {
			if (isEmpty($("#4" + j + "_176_82").val())) {
				layer.tips('不能为空', "#4" + j + "_176_82", {
					tips : [ 2, '#3595CC' ],
					time : 10000,
					tipsMore : true
				});
				flag = false;
			}
		}
	}
	return flag;
}
function checkShelf2(flag) {
	for (var j = 63; j <= 89; j++) {
		var val = $("#4" + j + "_176_82").val();
		if (val == 3) {
			if (isEmpty($("#4" + j + "_179_82").val())) {
				layer.tips('不能为空', "#4" + j + "_179_82", {
					tips : [ 2, '#3595CC' ],
					time : 10000,
					tipsMore : true
				});
				flag = false;
			}
		}
	}
	return flag;
}
function checkImge(flag) {
	var val = $("#462_168_81").select2("val");
	if (val == 1) {
		flag = checkMaxImge(flag, "490_180_83");
		flag = checkMaxImge2(flag, "491_180_83");
	}
	return flag;
}
function checkMaxImge(flag, id) {
	setUploaderVal();
	var value = $("#" + id).val();
	if (!isEmpty(value)) {
		var temp = value.split(",");
		if (temp.length < 2) {
			layer.tips('图片最少上传2张', '#fileList_' + id, {
				tips : [ 2, '#3595CC' ],
				time : 10000,
				tipsMore : true
			});
			flag = false;
		}
	} else {
		layer.tips('不能为空', '#fileList_' + id, {
			tips : [ 2, '#3595CC' ],
			time : 10000,
			tipsMore : true
		});
		flag = false;
	}
	return flag;
}
function checkMaxImge2(flag, id) {
	setUploaderVal();
	var value = $("#" + id).val();
	if (!isEmpty(value)) {
		var temp = value.split(",");
		if (temp.length < 3) {
			layer.tips('图片最少上传3张', '#fileList_' + id, {
				tips : [ 2, '#3595CC' ],
				time : 10000,
				tipsMore : true
			});
			flag = false;
		}
	} else {
		layer.tips('不能为空', '#fileList_' + id, {
			tips : [ 2, '#3595CC' ],
			time : 10000,
			tipsMore : true
		});
		flag = false;
	}
	return flag;
}
/**
 * 其他原因校验
 * 
 * @param flag
 */
function checkCause(flag) {
	var val = $("#462_168_81").select2("val");
	if (val == 8) {
		if (isEmpty($("#462_169_81").val())) {
			layer.tips('其他原因不能为空', "#462_169_81", {
				tips : [ 2, '#3595CC' ],
				time : 10000,
				tipsMore : true
			});
			flag = false;
		}
	}
	return flag;
}
function setPart() {
	var val = $("#462_168_81").select2("val");
	if (val != 1) {
		for (var j = 63; j <= 89; j++) {
			for (var i = 70; i <= 79; i++) {
				$("#4" + j + "_1" + i + "_82").attr("disabled", true);
			}
		}
	} else {
		for (var j = 63; j <= 89; j++) {
			for (var i = 70; i <= 79; i++) {
				$("#4" + j + "_1" + i + "_82").attr("disabled", false);
			}
		}
	}
	var vules = $("#461_164_80").val();
	if (vules == 1) {
		$("#461_165_80").attr("disabled", "disabled");
	} else {
		$("#461_165_80").removeAttr("disabled");
	}
	var val = $("#462_168_81").select2("val");
	if (val != 8) {
		$("#462_169_81").attr("disabled", true);
	} else {
		$("#462_169_81").attr("disabled", false);
	}
}