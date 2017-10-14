<html>
<head>
<title></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body>
	<div class="widget-content nopadding" style="margin:0px;">
        <table class="table_white_bg" >
            <tbody>
              <tr>
            		<td class="td_label" ><i class="cc1">*</i>仓库：</td>
                    <td  class="td_control">
						<input type="text" id="warehouseId" name="warehouseId" value="${inOut.warehouseId}" disabled="disabled"/>
					</td>
                    <td class="td_label" ><i class="cc1">*</i>类型：</td>
	                <td class="td_control">
	                   <input type="text" id="billType" name="billType" class="required"  value="${inOut.billType}" disabled="disabled"/>
	                </td>
               </tr> 
               <tr>
            		<td class="td_label" ><i class="cc1">*</i>物料：</td>
                    <td  class="td_control">
						<input type="text" id="materialId" name="materialId"   value="${inOut.materialId}"class="required" disabled="disabled">
					</td>
                    <td class="td_label" ><i class="cc1">*</i>数量：</td>
                    <td  class="td_control">
                    	<input type="text" id="quantity" name="quantity"  value="${inOut.quantity}"class="required" disabled="disabled">
                    </td>
               </tr> 
               <tr>
            		<td class="td_label" >负责人：</td>
                    <td  class="td_control"><input type="text" name="handler" value="${inOut.handler}" class="required" disabled="disabled"></td>
                    <td class="td_label"><i class="cc1">*</i>时间：</td>
                    <td class="td_control">
						<input type="text" id="inOutTime" name="inOutTime"  value="<#if (inOut.inOutTime)??>${(inOut.inOutTime)?string("yyyy-MM-dd")}</#if>"  class="required" disabled="disabled">
                    </td>
               <tr>
               		<td class="td_label" >门店：</td>
                    <td  class="td_control">
						<input type="text" id="storeId" name="storeId"  value="${inOut.storeId}" disabled="disabled"/>
                    </td>
                    <td class="td_label" >目标仓库：</td>
                    <td  class="td_control">
						<input type="text" id="outWarehouseId" name="outWarehouseId"  value="${inOut.receivingWarehouseId}" disabled="disabled"/>
               		</td>
               <tr>
               		<td class="td_label" >备注：</td>
                    <td class="td_control" colspan="4" valign="middle">
                    	<textarea rows=4  maxlength="300" name="remark" placeholder="不超过200个字" class="required" disabled="disabled">${inOut.remark}</textarea>
                    </td>
              </tr>
				 <tr>
					<td colspan="4" class="td_buttons">
						<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
					</td>
			   </tr>
            </tbody>
        </table>
<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
<script >
$("#warehouseId").select2({
	width: 145,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	allowClear: true,
	ajax: {
		url: "${contextPath}/warehouse/getWarehousesByLikeName",
		dataType: 'json',
		quietMillis: 250,
		data: function(term) {
			return {
				name: term
			};
		},
		results: function(data, page) {
			var more = page;
			return {
				results: data,
				more: more
			};
		}
	},
	initSelection: function(element, callback) {
		var id = $(element).val();
		if (id != "") {
			$.ajax("${contextPath}/warehouse/getWarehousesByKey/" + id, {
				dataType: "json"
			}).done(function(data) {
				callback(data);
			});
		}
	},
	formatResult: repoFormatResult,
	formatSelection: repoFormatSelection,
	escapeMarkup: function(m) {
		return m;
	}
});
function repoFormatResult(repo) {
	return repo.name;
}
function repoFormatSelection(repo) {
	return repo.name;
}

$("#outWarehouseId").select2({
	width: 145,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	allowClear: true,
	ajax: {
		url: "${contextPath}/warehouse/getWarehousesByLikeName",
		dataType: 'json',
		quietMillis: 250,
		data: function(term) {
			return {
				name: term
			};
		},
		results: function(data, page) {
			var more = page;
			return {
				results: data,
				more: more
			};
		}
	},
	initSelection: function(element, callback) {
		var id = $(element).val();
		if (id != "") {
			$.ajax("${contextPath}/warehouse/getWarehousesByKey/" + id, {
				dataType: "json"
			}).done(function(data) {
				callback(data);
			});
		}
	},
	formatResult: repoFormatResult,
	formatSelection: repoFormatSelection,
	escapeMarkup: function(m) {
		return m;
	}
});
function repoFormatResult(repo) {
	return repo.name;
}
function repoFormatSelection(repo) {
	return repo.name;
}

$("#storeId").select2({
	width: 145,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	allowClear: true,
	ajax: {
		url: "${contextPath}/store/getStoresKeyValue",
		dataType: 'json',
		quietMillis: 250,
		data: function(term) {
			return {
				name: term
			};
		},
		results: function(data, page) {
			var more = page;
			return {
				results: data,
				more: more
			};
		}
	},
	initSelection: function(element, callback) {
		var id = $("#storeId").val();
		if (id != "") {
			$.ajax("${contextPath}/store/getStore/"+id, {
				dataType: "json"
			}).done(function(data) { callback(data); });
		}
	},
	formatResult: repoFormatResult,
	formatSelection: repoFormatSelection,
	escapeMarkup: function(m) {
		return m;
	}
});
function repoFormatResult(repo) {
	return repo.name;
}
function repoFormatSelection(repo) {
	return repo.name;
}
$("#materialId").select2({
	width: 145,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	allowClear: true,
	ajax: {
		url: "${contextPath}/promotionMaterial/materialByLikeName",
		dataType: 'json',
		quietMillis: 250,
		data: function(term) {
			return {
				name: term
			};
		},
		results: function(data, page) {
			var more = page;
			return {
				results: data,
				more: more
			};
		}
	},
	initSelection: function(element, callback) {
		var id = $("#materialId").val();
		if (id != "") {
			$.ajax("${contextPath}/promotionMaterial/getMaterialByKey/"+id, {
				dataType: "json"
			}).done(function(data) { callback(data); });
		}
	},
	formatResult: repoFormatResult,
	formatSelection: repoFormatSelection,
	escapeMarkup: function(m) {
		return m;
	}
});
function repoFormatResult(repo) {
	return repo.name;
}
function repoFormatSelection(repo) {
	return repo.name;
}

$(function() {
	var status = $("#billType").val();
	loadStatus(status);

	$("#inOutTime").datepicker({
		changeYear: true,
		yearRange: "2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect: function(dateText, inst) {
			$(this).focus();
			$(this).blur();
		}
	});
});
function loadStatus(status) {
	$.ajax({
		type: "post",
		url: "${contextPath}/posmInOut/getBillType",
		dataType: "json",
		cache: false,
		success: function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData,
			function(index, item) {
				if (index != jsonData.length - 1) {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"},";
				} else {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#billType").select2({
				width: 145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error: function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

//验证
var validobj = $("#dataForm").validate({
	ignore: [],
	onkeyup: false,
	errorClass: "error",
	rules: {
		warehouseId: {
			isNumber: true,
			maxlength: 50,
			required: true
		},
		materialId: {
			maxlength: 100,
			required: true
		},
		quantity: {
			isNumber: true,
			maxlength: 8,
			required: true
		},
		handler: {
			maxlength: 20,
			required: true
		},
		//	inOutTime:{isTel:true,required:true},
		billType: {
			isNumber: true,
			required: true
		},
		remark: {
			maxlength: 200,
			required: true
		}
	},
	errorPlacement: function(error, element) {
		var elem = $(element);
		error.insertAfter(element);
	}
});
</script>
</div>
</body>
</html>