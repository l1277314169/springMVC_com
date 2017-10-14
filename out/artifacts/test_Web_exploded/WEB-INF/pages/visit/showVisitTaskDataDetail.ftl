<html>
<head>
<title>执行查看</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
<style type="text/css">
.overlay { 
	position: fixed; 
	top: 0px; 
	left: 0px; 
	width: 100%; 
	height: 100%; 
	z-index: 1999; 
	background-color: #000; 
	opacity: 0.2; 
	filter: alpha(opacity=20); 
} 
.moveBar { 
	position: absolute; 
	width: 550px; 
	height: 350px; 
	background-color:#ffffff; 
	border: solid 1px #000; 
	left:20%; 
	top:15%; 
	padding:2px; 
	z-index: 2999; 
}
</style> 
</head>
<body>
<input type="hidden" id="clientUserId" value="${clientUser.clientUserId!''}">
<input type="hidden" id="clientPositionId" value="${clientUser.clientPositionId!''}">
<input type="hidden" id="visitDate" value="${visitDate!''}">
<input type="hidden" id="taskType" value="${taskType!''}">
<div class="margin-top-18 margin-left-18px">
	姓名：${clientUser.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职位：${clientUser.positionName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;执行日期：${visitDate}
</div>
		<div class="div_widget_narrow div_scrollbar">
			<ul id="leftUl">
				<#if cpList?? && cpList?size gt 0 >
					<#list cpList as callPlanning>
						<#if callPlanning.callStatus != 3>
							<#if callPlanning_index == 0>
								<li class="popClass_cur" data-id="${callPlanning.popId!''}" data-type="${callPlanning.popType!''}"><span>${callPlanning.popName!''}</span><#if callPlanning.planningType?? && callPlanning.planningType == 3><span class="pop_lin" style="color: #fc9600;margin-left: 5px;border: 1px solid #fc9600;">临</span></#if></br>
									<span><#if callPlanning.inTime??>${callPlanning.inTime?string("HH:mm")}</#if><#if callPlanning.outTime??>-${callPlanning.outTime?string("HH:mm")}</#if></span>
								</li>
							<#else>
								<li class="popClass_nor" data-id="${callPlanning.popId!''}" data-type="${callPlanning.popType!''}"><span>${callPlanning.popName!''}</span><#if callPlanning.planningType?? && callPlanning.planningType == 3><span class="pop_lin" style="color: #fc9600;margin-left: 5px;border: 1px solid #fc9600;">临</span></#if></br>
									<span><#if callPlanning.inTime??>${callPlanning.inTime?string("HH:mm")}</#if><#if callPlanning.outTime??>-${callPlanning.outTime?string("HH:mm")}</#if></span>
								</li>
							</#if>
						</#if>
					</#list>
				<#else>
					未获取到数据
				</#if>
			</ul>
		</div>
		
		<div id="div_step_detail" class="div_step_detail">
		</div>
		</body>
</html>
<script type="text/javascript" language="javascript">
$(document).ready(function () {
	//获取第一个对象的DOM数据
	var fPopId = $("#leftUl li").eq(0).attr("data-id"); 
	var fPopType = $("#leftUl li").eq(0).attr("data-type");
	var clientUserId = $("#clientUserId").val();
	var clientPositionId = $("#clientPositionId").val();
	var visitDate = $("#visitDate").val();
	var taskType = $("#taskType").val();
	//alert(fPopId);
	if(typeof(fPopId) != "undefined"){
		loadingTaskStepDetail(fPopId,fPopType,clientUserId,clientPositionId,visitDate,taskType);
	} else {
		$(".div_step_detail").html("没有数据");
	}
	function loadingTaskStepDetail(popId,popType,clientUserId,clientPositionId,visitDate,taskType){
		//alert("popId:"+popId+"popType:"+popType+"clientUserId:"+clientUserId+"clientPositionId:"+clientPositionId+"visitDate:"+visitDate+"taskType:"+taskType);	
		$.post("/visitTaskData/showTaskDataDetailByObjectId",
			{
				popId:popId,
				popType:popType,
				clientUserId:clientUserId,
				clientPositionId:clientPositionId,
				visitDate:visitDate,
				taskType:taskType
			},
			function(data){
				$(".div_step_detail").html(data);
			}
		);
	};
	
	$("#leftUl > li").bind('click',function(){
		if($(this).attr("class") == "popClass_cur") {
        	return false;
        } else {
        	 $(this).parent().children("li").attr("class", "popClass_nor");//将所有选项置为未选中
            $(this).attr("class", "popClass_cur"); //设置当前选中项为选中样式
			var popId = $(this).attr("data-id"); 
			var popType = $(this).eq(0).attr("data-type");
			var clientUserId = $("#clientUserId").val();
			var clientPositionId = $("#clientPositionId").val();
			var visitDate = $("#visitDate").val();
			//alert("popId:"+popId+"popType:"+popType+"clientUserId:"+clientUserId+"clientPositionId:"+clientPositionId+"visitDate:"+visitDate);
			loadingTaskStepDetail(popId,popType,clientUserId,clientPositionId,visitDate,taskType);
        }
	});
});
</script>