<html>
<head>
<title></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
</head>
<body>
<div class="widget-content no_bottom_border">
			<input type="hidden" id="visiTypeVal" value="1">
			<form id="dataForm" method="post">
					<br>
					<input type="hidden" id="minDate" name="minDate" value="<#if minDate??>${minDate?string("yyyy-MM-dd")}</#if>">
					<span class="control-label" for="clientUserId" style="margin-left:10px;font-size:12px;"><i style="color:red">*</i>人员：</span>
					<span>
						<input type="text" id="clientUserId" name="clientUserId" value="${storeSearchVO.clientUserId!''}">
					</span>
					
					<span id="visitTypeContext" class="control-label" for="visitType" style="margin-left:10px;font-size:12px;"><i style="color:red">*</i>&nbsp;计划类型：</span>
					<span id="visitTypeInput">
						<input type="text" id="visitType" name="visitType" value="1">
					</span>
					
					<span id="enumValueContext" class="control-label" for="enumValue" style="margin-left:5px;font-size:12px;display: none;"><i style="color:red">*</i>任务内容：</span>
					<span id="enumValueInput" style="display: none;">
							<input type="text" id="enumValue" name="enumValue" >
					</span>
					<span class="control-label" for="callDate" style="font-size:12px;margin-left:5px;">计划日期：</span>
					<span>
						<input type="text" id="callDate" name="callDate" value="<#if callDate??>${callDate?string("yyyy-MM-dd")}</#if>" class="input-medium" readonly="true" style="margin-bottom:3px;">
					</span>
			</form>
				<div>
					<input type="button" value="查询" class="btn btn-info fr" id="search_btn" style="margin-right:10px;"/>
					<input type="hidden" id="storeParameterDatas" name="storeParameterDatas" >
					<div  id="storeNumber" style="text-align: center;display: inline-flex;margin-left:20px;font-size:12px;margin-top: 10px;">您已选择：<label id ="checkCount"  style="color:red;width: 50px;">0</label>家门店</div>
				</div>
				<div id="tabs-body" class="tabs-body" style="position:absolute;">
					<h1 style="margin-top:150px;margin-left:300px">请查询人员计划....</h1>
				</div>
</div>
		<div id="button" style="margin-left:340px;margin-top: 470px;position:absolute;">
				<button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
				<button type="button" id="comfirButton"  class="btn btn-success margin-left-18px">确定</button>
		</div>
</body>
</html>		
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	loadtTaskContents();
	//取消
	$('.btn-danger').click(function(){
		parent.addDialog.close();
	});
	/*
	$("#workType li").bind("click",function(){
		var workType = $(this).val();
        //如果是当前tab则不做任何动作，反之异步请求新的页面参数。
        if($(this).attr("class") == "tab_current_b") {
        	return false;
        } else {
            $(this).parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
            $(this).attr("class", "tab_current_b"); //设置当前选中项为选中样式
		}
		if(workType == 0){
			$('#enumValueContext').hide();
			$('#enumValueInput').hide();
			$('#storeNumber').css("visibility","visible");
			$('#tabs-body').show();
			$('#visitTypeContext').show();
			$('#visitTypeInput').show();
			$('#NotTabs-body').hide();
		}else{
			$('#tabs-body').hide();
			$('#storeNumber').css("visibility","hidden");
			$('#visitTypeContext').hide();
			$('#visitTypeInput').hide();
			$('#enumValueContext').show();
			$('#enumValueInput').show();
			$('#NotTabs-body').show();
			
		}
	});
	*/
	
	//查询门店
	$("#search_btn").bind("click",function(){
			var clientUserId = $("#clientUserId").val();
			var callDate = $("#callDate").val();
			var visitType = $('#visitType').val();
			//非拜访查询
				if($('#clientUserId').val() == ''){
					layer.alert("请指定一个人员要拜访的门店信息!");
					return false;
				}
				$.post("/callPlanning/showAddCallPlanningDetail",
					{
						clientUserId:clientUserId,callDate:callDate,visitType:visitType
					},
					function(data){
						$(".tabs-body").html(data);
					}
				)
	});
});

//计划类型变化onchange
$('#visitType').on("change",function(){
		var storeId = $('#storeParameterDatas').val();
		if(storeId != ""){
			if(confirm("如果切换计划类型,保存的数据将会丢失!")){
				$('#storeParameterDatas').val("");
				$('#checkCount').html("0");
				var clientUserId = $("#clientUserId").val();
				var callDate = $("#callDate").val();
				var visitType = $(this).val();
				if($('#clientUserId').val() == ''){
					layer.alert("请指定一个人员要拜访的门店信息!");
					return false;
				}
				$.post("/callPlanning/showAddCallPlanningDetail",
					{
						clientUserId:clientUserId,callDate:callDate,visitType:visitType
					},
					function(data){
						$(".tabs-body").html(data);
					}
				)
			}else{
				$('#visitType').val($('#visiTypeVal').val());
				var visitType = [{ id: 1, text: '门店拜访' }, { id: 2, text: '门店协访' },{id:3,text:'店内工作'}];
				  $("#visitType").select2({
						width:145,
						placeholder: "请选择",
						data: visitType
				 });
			}
			$('#visiTypeVal').val($(this).val());
		}else{
			$('#visiTypeVal').val($(this).val());
			var clientUserId = $("#clientUserId").val();
			var callDate = $("#callDate").val();
			var visitType = $(this).val();
			if($('#clientUserId').val() == ''){
				layer.alert("请指定一个人员要拜访的门店信息!");
				return false;
			}
			$.post("/callPlanning/showAddCallPlanningDetail",
				{
					clientUserId:clientUserId,callDate:callDate,visitType:visitType
				},
				function(data){
					$(".tabs-body").html(data);
				}
			)
		}
});

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
//门店嵌入到页面中
$('#search_btn').click(function(){
	function addVisit(){
		$.post("${contextPath}/callPlanning/addCallPlanning",
				function(data){
					$(".tabs-body").html(data);
				}
		);
	}
});
//任务内容
$("#enumValue").select2({
        	width:145,
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
				width:145,
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
//保存
$('#comfirButton').on("click",function(){
	$(this).attr("disabled","disabled");
	var workType = $(".tab_current_b").val();
	if($('#checkCount').html() == 0){
		layer.alert("请选择你要拜访的门店!");
		$("#comfirButton").removeAttr("disabled");
		return false;
	}
	var clientUserId = $('#clientUserId').val();
	if(clientUserId  == ""){
		layer.alert("人员不能为空!");
		$("#comfirButton").removeAttr("disabled");
		return false;
	}
	var storeIdAndComma = $("#storeParameterDatas").val();
	storeId = storeIdAndComma.substring(1);
	var clientUserId = $("#clientUserId").val();
	var callDate = $("#callDate").val();
	var visitType = $('#visitType').val();
	var workType = $(".tab_current_b").val();
	var enumValue = $('#enumValue').val();
	$.ajax({
		url : "${contextPath}/callPlanning/addCallPlanning",
		type : "post",
		dataType:"json",
		async: false,
		data : {storeId:storeId,clientUserId:clientUserId,callDate:callDate,visitType:visitType,
		workType:workType,enumValue:enumValue},
		success:function(result){
			if(result.code=="success"){
				layer.alert(result.message,function(){
	   				// window.parent.location.reload();
	   				//parent.addDialog.close();
	   				parent.document.location.href = "${contextPath}/callPlanning/query";
		   		    parent.layer.closeAll('iframe');
	   			});
	   		}else if(result.code=="exist"){
	   			$("#comfirButton").removeAttr("disabled");
	   			layer.confirm(callDate+"--"+result.message+",是否删除原有非拜访计划并添加新计划?", function () {
					$.ajax({
						url : "${contextPath}/callPlanning/isDeleteNotCallplanning",
						type : "post",
						async: false,
						dataType:'JSON',
						data : {storeId:storeId,clientUserId:clientUserId,callDate:callDate,visitType:visitType,workType:workType,enumValue:enumValue},
						success : function(result) {
							 layer.alert(result.message,function(){
				   				// window.parent.location.reload();
	   							// parent.addDialog.close();
	   					     parent.document.location.href = "${contextPath}/callPlanning/query";
		   		             parent.layer.closeAll('iframe');
				   			});
						}
					});
	   			});
			}else {
				layer.alert(result.message);
				$("#comfirButton").removeAttr("disabled");
		   	}
		}
	});

});
</script>
</html>
