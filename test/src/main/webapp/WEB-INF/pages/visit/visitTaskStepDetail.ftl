<input type="hidden" id="selectPopId" value="${popId}">
<input type="hidden" id="selectPopType" value="${popType}">
<input type="hidden" id="selectVisitDate" value="${visitDate}">
<div class="tabs no_bottom_border">
	<ul id="stepTbas"  class="margin-left-1">
		<#if taskType = 3 && vtStepList?? && vtStepList?size gt 0>
			<#list vtStepList as vtStep>
				<#if vtStep_index == 0>
				<li class="tab_current_b" select-type="${vtStep.selectType}" data-id="${vtStep.visitingTaskStepId}" data-type="${vtStep.stepType}">${vtStep.stepName}</li>
				<#else>
					<li class="tab_normal_b" select-type="${vtStep.selectType}" data-id="${vtStep.visitingTaskStepId}" data-type="${vtStep.stepType}">${vtStep.stepName}</li>
				</#if>
			</#list>
		<#elseif (taskType = 1 || taskType = 2) && vtStepList?? && vtStepList?size gt 0>
			<li class="tab_current_b" select-type="-1" data-id="-1" data-type="-1">进出店详细</li>
			<#if vtStepList?? && vtStepList?size gt 0 >
				<#list vtStepList as vtStep>
					<li class="tab_normal_b" select-type="${vtStep.selectType}" data-id="${vtStep.visitingTaskStepId}" data-type="${vtStep.stepType}">${vtStep.stepName}</li>
				</#list>
			</#if>
		</#if>
	</ul>
</div>
<div id="tabs-body" class="div_data_detail">
</div>
<script type="text/javascript" language="javascript">
$(document).ready(function () {
	//获取第一个step的DOM数据初始化dateDetail
	var visitingTaskStepId;
	var stepType;
	var taskType;
	var popId;
	var popType;
	var visitDate;
	var clientUserId;
	var clientPositionId;
	var selectType;
	//alert($('#stepTbas li').eq(0).length );
	if($('#stepTbas li').eq(0).length > 0){
		visitingTaskStepId = $('#stepTbas li').eq(0).attr("data-id");
		stepType = $('#stepTbas li').eq(0).attr("data-type");
		selectType = $('#stepTbas li').eq(0).attr("select-type");
		taskType = $("#taskType").val();
		popId = $("#selectPopId").val();
		popType = $("#selectPopType").val();
		visitDate = $("#selectVisitDate").val();
		clientUserId = $("#clientUserId").val();
		clientPositionId = $("#clientPositionId").val();
		if(visitingTaskStepId == '-1'){//判断是否为"进出店详细"页面
			loadingVisitTaskData(clientUserId,popId,popType,visitDate,taskType);
		} else {
			loadingTaskStepDataDetail(visitingTaskStepId,stepType,popId,popType,clientUserId,clientPositionId,visitDate,taskType,selectType);
		}
	} else{
		$("#tabs-body").html("没有数据");
	};
	
	$("#stepTbas > li").click(function(){
		if($(this).attr("class") == "tab_current_b") {
        	return false;
        } else {
            $(this).parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
            $(this).attr("class", "tab_current_b"); //设置当前选中项为选中样式
			
			visitingTaskStepId = $(this).attr("data-id");
			stepType = $(this).attr("data-type");
			selectType = $(this).attr("select-type");
			taskType = $("#taskType").val();
			popId = $("#selectPopId").val();
			popType = $("#selectPopType").val();
			visitDate = $("#selectVisitDate").val();
			clientUserId = $("#clientUserId").val();
			clientPositionId = $("#clientPositionId").val();
			if(visitingTaskStepId == '-1'){//判断是否为"进出店详细"页面
				loadingVisitTaskData(clientUserId,popId,popType,visitDate,taskType);
			} else {
				loadingTaskStepDataDetail(visitingTaskStepId,stepType,popId,popType,clientUserId,clientPositionId,visitDate,taskType,selectType);
			}
		}
	});
	
	function loadingVisitTaskData(clientUserId,popId,popType,visitDate,taskType){
		$.post("/visitTaskData/showVisitTaskDataMain",
			{
				popId:popId,
				popType:popType,
				clientUserId:clientUserId,
				visitDate:visitDate,
				taskType:taskType
			},
			function(data){
				$("#tabs-body").html(data);
			}
		);
	};
	
	function loadingTaskStepDataDetail(visitingTaskStepId,stepType,popId,popType,clientUserId,clientPositionId,visitDate,taskType,selectType){
		$.post("/visitTaskData/showTaskDataDetailByStepId",
			{
				visitingTaskStepId:visitingTaskStepId,
				stepType:stepType,
				popId:popId,
				popType:popType,
				clientUserId:clientUserId,
				clientPositionId:clientPositionId,
				visitDate:visitDate,
				taskType:taskType,
				selectType:selectType
			},
			function(data){
				$("#tabs-body").html(data);
			}
		);
	};
});
</script>