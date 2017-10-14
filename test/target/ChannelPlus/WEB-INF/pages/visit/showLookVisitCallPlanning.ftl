<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/updatePassword.css"/>
<form id="dataForm" method="post">
<input type="hidden" id="minDate" name="minDate" value="<#if minDate??>${minDate?string("yyyy-MM-dd")}</#if>">
	  <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label" style="line-height:50px"><i class="cc1">*</i>人员：</td>
                    <td class="controls"><input type="text" id="clientUserId" name="clientUserId"  value="${callPlanning.clientUserId}" disabled="disabled"></td>
                </tr> 
                <tr>
                	<td  class="td_label" style="line-height:50px"><i class="cc1">*</i>任务内容：</td>
                    <td class="controls">
	                   <input type="text" id="enumValue" name="enumValue" value="${callPlanning.enumValue}" disabled="disabled">
                    </td>
                </tr> 
                <tr>
                	<td  class="td_label" style="line-height:45px">计划日期：</td>
                	 <td class="controls">
	                   <input type="text" id="callDate" style="background-color: #f6f6f6; name="callDate" value="<#if callPlanning.callDate??>${callPlanning.callDate?string("yyyy-MM-dd")}</#if>" class="input-medium" disabled="disabled" readonly="true">
                    </td>
                </tr>
                 <tr>
                	<td  class="td_label" style="line-height:45px">计划类型：</td>
                	 <td class="controls">
	                  <input type="text" id="visitType" name="visitType" value="1" disabled="disabled">
                    </td>
                </tr>
		         <tr>
			 		<td class="td_label"></td>
					<td colspan="3" class="td_buttons">
						 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger" style="margin-left:-100px">取消</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
$(document).ready(function () {
	loadtTaskContents();
	$("#clientUserId").select2({
	        	width:150,
				placeholder: "输入字符查询",
				minimumInputLength: 2,
				allowClear : true,
				ajax: {
					url: "${contextPath}/clientUser/getClientUserPosition",
					dataType: 'json',
					quietMillis: 250,
					data: function (term, page) {
						return {
							q: term,
							page: page
						};
					},
					results: function (data,page) {
						return { results: data};
					}
				},
				initSelection: function(element, callback) {
					var id = $(element).val();
					if (id !== "") {
						$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
							dataType: "json"
						}).done(function(data) { callback(data); });
					}
				},
				formatResult: repoFormatResult,
				formatSelection: repoFormatSelection,
				escapeMarkup: function (m) { return m; }
	}); 
	
	function repoFormatResult(repo) {
		return repo.name;
	}
	function repoFormatSelection(repo) {
		return repo.name;
	}
//任务内容
	$("#enumValue").select2({
	        	width:150,
				placeholder: "请选择",
				data:[]
	});
	//计划类型
var visitType = [{ id: 1, text: '门店拜访' }, { id: 2, text: '门店协访' },{id:3,text:'店内工作'}];
	  $("#visitType").select2({
			width:145,
			placeholder: "请选择",
			data: visitType
});
	//计划时间
	$("#callDate").datepicker({
		minDate:new Date($("#minDate").val()),
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$(this).focus();
			$(this).blur();
		}	
	});
});


function loadtTaskContents(){
	$.ajax({
		type : "post",
		url : "${contextPath}/callPlanning/getTaskContentsAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData); 
			$("#enumValue").select2({
				width:150,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};


</script>
