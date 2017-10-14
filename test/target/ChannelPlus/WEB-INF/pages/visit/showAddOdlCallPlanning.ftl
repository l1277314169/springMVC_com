<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
 <#include "/common/head.ftl" />
 <#include "/common/foot.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/updatePassword.css"/>
<form id="dataForm">
<input type="hidden" id="minDate" name="minDate" value="<#if minDate??>${minDate?string("yyyy-MM-dd")}</#if>">
	  <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label" style="line-height:50px"><i class="cc1">*</i>人员：</td>
                    <td class="controls"><input type="text" id="clientUserId" name="clientUserId" required></td>
                </tr> 
                <tr>
                	<td  class="td_label" style="line-height:50px"><i class="cc1">*</i>任务内容：</td>
                    <td class="controls">
	                   <input type="text" id="enumValue" name="enumValue" required>
                    </td>
                </tr> 
                <tr>
                	<td  class="td_label" style="line-height:45px">计划日期：</td>
                	 <td class="controls">
	                   <input type="text" id="callDate" name="callDate" value="<#if callDate??>${callDate?string("yyyy-MM-dd")}</#if>" class="input-medium" readonly="true">
                    </td>
                </tr>
                 <tr>
                	<td  class="td_label" style="line-height:45px">计划类型：</td>
                	 <td class="controls">
	                  <input type="text" id="visitType" name="visitType" value="1" disabled="disabled">
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
							<button type="button" id="oldSavetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
$(document).ready(function () {
	$('.btn-danger').bind("click",function(){
		odlAddDialog.close();
	});
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
	
	var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules : {
			clientUserId: {
				required: true
			},
			enumValue: {
				required: true
			}
		},
		messages:{
			clientUserId:{
				required:"不能为空"
			},
			enumValue:{
				required:"不能为空"
			}
		},
		errorPlacement: function (error, element) {
            var elem = $(element);
            error.insertAfter(element);
        },
		highlight: function (element, errorClass, validClass) {
			var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').addClass(errorClass);
            } else {
                elem.addClass(errorClass);
            }
		},
        unhighlight: function (element, errorClass, validClass) {
            var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').removeClass(errorClass);
            } else {
                elem.removeClass(errorClass);
            }
        }
	});
	
	$(document).die().live("change", ".select2-offscreen", function () {
        if (!$.isEmptyObject(validobj.submitted)) {
            validobj.form();
        }
    });

    //保存
$('#oldSavetButton').on("click",function(){
	$(this).attr("disabled","disabled");
	var clientUserId = $("#clientUserId").val();
	var callDate = $("#callDate").val();
	var workType = 1;
	var visitType = $('#visitType').val();
	var enumValue = $('#enumValue').val();
	//验证
	if(!validobj.form()){
		$("#oldSavetButton").removeAttr("disabled");
		return false;
	}
	$.ajax({
		url : "${contextPath}/callPlanning/addNotCallPlanning",
		type : "post",
		dataType:"json",
		async: false,
		data : {clientUserId:clientUserId,callDate:callDate,visitType : visitType,
		workType:workType,enumValue:enumValue,deleteOld:false},
		success:function(result){
			if(result.code=="success"){
				layer.alert(result.message,function(){
	   				// window.location.reload();
	   				// odlAddDialog.close();
	   				parent.document.location.href = "${contextPath}/callPlanning/query/";
		   			parent.layer.closeAll('iframe');
	   			});
	   		}else if(result.code=="existVisit"){
	   			$("#oldSavetButton").removeAttr("disabled");
	   			layer.confirm(callDate+"--"+result.message+",是否删除原有计划并添加新计划?", function () {
					$.ajax({
						url : "${contextPath}/callPlanning/addNotCallPlanning",
						type : "post",
						async: false,
						dataType:'JSON',
						data : {clientUserId:clientUserId,callDate:callDate,visitType : visitType,workType:workType,enumValue:enumValue,deleteOld:true},
						success : function(result) {
							 layer.alert(result.message,function(){
				   				// window.location.reload();
				   				// odlAddDialog.close();
				   				parent.document.location.href = "${contextPath}/callPlanning/query/";
		   			            parent.layer.closeAll('iframe');
				   			});
						}
					});
	   			});
			}else{
				layer.alert(result.message);
				$("#oldSavetButton").removeAttr("disabled");
		   	}
		},
		error : function(){
			$("#oldSavetButton").removeAttr("disabled");
		}
	});

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
