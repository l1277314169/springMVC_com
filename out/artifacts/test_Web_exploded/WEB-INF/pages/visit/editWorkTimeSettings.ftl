<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input  name="settingId"  type="hidden" value="${workTimeSettings.settingId}"/>
		<input  name="workDate"  type="hidden" value="${workTimeSettings.workDate?string("yyyy-MM-dd")}"/>
        <table class="table_white_bg">
            <tbody>
            	<tr>
            		<td class="td_label_d">排班人员：</td>
                    <td class="td_control">
                    	${workTimeSettings.clientUserName}
                    	<input  name="clientUserId"  type="hidden" value="${workTimeSettings.clientUserId}"/>
                    </td>
                </tr> 
                <tr>
					<td class="td_label_d">门店名称：</td>
                    <td class="td_control">
                    	${workTimeSettings.storeName}
                    	<input  name="storeId"  type="hidden" value="${workTimeSettings.storeId}"/>
                    </td>
                </tr>
                <tr>
                	<td class="td_label_d">排班类型：</td>
                    <td class="td_control">
                    	<input type="text" id="workTimeType" name="workTimeType" value = "${workTimeSettings.workTimeType}">
                    </td>
                </tr>
                <tr>
                	<td class="td_label_d">开始时间：</td>
                    <td class="td_control">
                    <input type="text" id="startTime" name="startTime" <#if workTimeSettings.startTime??>value="${(workTimeSettings.startTime)?string("yyyy-MM-dd HH:mm")}"<#else>value="${(workTimeSettings.workDate)?string("yyyy-MM-dd HH:mm")}"</#if> readonly/><span>&nbsp;示例 ：2015-01-01 00:00</span>
                    </td>
                </tr>
                <tr>
                	<td class="td_label_d">结束时间：</td>
                    <td class="td_control">
                    <input type="text" id="endTime" name="endTime" <#if workTimeSettings.endTime??>value="${(workTimeSettings.endTime)?string("yyyy-MM-dd HH:mm")}"</#if> readonly/><span>&nbsp;示例 ：2015-01-01 00:00</span>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" class="btn btn-danger"  onClick="javascript:parent.layer.closeAll('iframe');">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
$(document).ready(function () {
	$("#savetButton").bind("click",function(){
		$.ajax({
		url : "${contextPath}/workTimeSettings/updateWorkTimeSettings",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
		  	 layer.alert(result.message,function(){
		  		//	window.location.reload();
	   			//	editDialog.close();
	   			parent.document.location.href = "${contextPath}/workTimeSettings/query";
                parent.layer.closeAll('iframe');
	   		 });
			}else {
				layer.alert(result.message);
	   		}
		   }
		});						
	});
	
	//开始日期
	var startTime = $('#startTime').val();
	if(startTime != null && startTime.length > 0){
		$('#startTime').datetimepicker({
			minDate: 0, 
			maxDate: "-1",
			onSelect: function (selectedDateTime){
				$("#endTime").datetimepicker("option","minDateTime",$('#startTime').datetimepicker('getDate'));
			}
		});
	}else{
		$('#startTime').datetimepicker();
	}
		var endTime = $('#endTime').val();
		if(endTime != null && endTime.length > 0){
			if(startTime != null && startTime.length > 0){
				$('#endTime').datetimepicker({
						minDateTime: $('#startTime').datetimepicker('getDate')
				});
			}else{
				$('#endTime').datetimepicker();
			}
		}else{
			if(startTime != null && startTime.length > 0){
				$('#endTime').datetimepicker({
					minDateTime: $('#startTime').datetimepicker('getDate')
				});
			}else{
				$('#endTime').datetimepicker();
			}
		}
	
	
	loadWorkTimeType();
	
});
function loadWorkTimeType(){
	$.ajax({
		type : "post",
		url : "${contextPath}/workTimeSettings/loadWorkTimeType",
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
			$("#workTimeType").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			$("#workTimeType").select2({
		    	width:145,
				placeholder: "请选择",
				data:[]
			});
			alert("数据加载失败！");
		}
	});
}
</script>
