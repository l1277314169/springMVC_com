<html>
<head>
<title>执行计划管理编辑</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body style="width:800px;height:600px;">
	<div class="widget-content nopadding">
		<form id="dataForm" action="" method="post" class="form-horizontal">
			<input type="hidden" id="callDate" name="callDate" value="<#if callDate??>${callDate?string("yyyy-MM-dd")}</#if>"/>
			<input type="hidden" id="clientUserId" name="clientUserId" value="${clientUserId!''}"/>
			<input type="hidden" id="workType" name="workType" value="${workType!''}"/>
			<input type="hidden" id="visitType" name="visitType" value="${visitType}"/>
			<input type="hidden" name="page" value="${page}">
			<div class="widget-content no_bottom_border">
				<div class="control-group">
					<div class="fl">
						<label class="control-label " for="searchStoreName">门店名称：</label>
						<div class="controls">
						  <input type="text" name="searchStoreName" value="${searchStoreName!''}">
						</div>
					</div>
					<div class="fl">
						<label class="control-label " for="searchStoreName">拜访状态：</label>
						<div class="controls">
						  <select type="text" id="callStatus" name="callStatus" >
						  	<option value="">--请选择--</option>
						  	<#list callStatuslist as callStatus>
						  		<option value="${callStatus.getKey()}">${callStatus.getCnName()}</option>
						  	</#list>
						  </select>
						</div>
					</div>
				</div>
				<div class="form-actions">
				<div style="text-align: center;display: inline-flex;margin-left:20px;font-size:12px;margin-bottom: -10;margin-top: 10;">您已选择：<label id ="checkCount"  style="color:red;width: 50px;">0</label>家门店</div>
					<input type="button" value="查询" class="btn btn-info fr" id="search_btn"/>
					<input type="hidden" id="storeParameterDatas" name="storeParameterDatas" value="${oldStoreIds}"/>
					<input type="hidden" id="oldStoreIds" name="oldStoreIds" value="${oldStoreIds}">
				</div>
		</form>
	</div>
	<div class="tabs-body" >
	
	</div>
	<div id="button" style="text-align: center;">
		<button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
		<button type="button" id="editButton"  class="btn btn-success margin-left-18px">确定</button>
	</div>
</body>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	addVisit();
	//拜访状态
	$('#callStatus').select2({
		width:145
	});
$('#search_btn').click(function(){
	addVisit();
});

	//门店嵌入到页面中
	function addVisit(){
		$.post("${contextPath}/callPlanning/showEditDialog",$('#dataForm').serialize(),
				function(data){
					$(".tabs-body").html(data);
				}
		);
	}
	
	//保存	
$('#editButton').bind("click",function(){
	var storeIdAndComma = $("#storeParameterDatas").val();
	var storeId=storeIdAndComma;
	if(storeId.substring(0,1) == ','){
		storeId=storeIdAndComma.substring(1);
	}
	var clientUserId = $("#clientUserId").val();
	var callDate = $("#callDate").val();
	var visitType = $("#visitType").val();
	var oldStr = $('#oldStoreIds').val();
	if(oldStr.substring(0,1) == ','){
		oldStr=oldStr.substring(1);
	}
	$.ajax({
		url : "${contextPath}/callPlanning/editCallPlanning",
		type : "post",
		dataType:"json",
		async: false,
		data : {storeId:storeId,clientUserId:clientUserId,callDate:callDate,
		visitType:visitType,oldStr : oldStr},
		success:function(result){
			if(result.code=="success"){
				layer.alert(result.message,function(){
	   			//	window.parent.location.reload();
	   			// parent.editDialog.close();
	   			parent.document.location.href = "${contextPath}/callPlanning/query";
		   		parent.layer.closeAll('iframe');	
	   			});
			}else {
				layer.alert(result.message);
		   	}
		}
	});
});
});
</script>
</html>
