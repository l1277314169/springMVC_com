<#import "/common/pagination.ftl" as pagination>
<input type="hidden" name="page" value="${page}">
<div class="widget-content nopadding" id="logResultList" style="width : 760px;">
	<table id="storeData" class="table table-bordered data-table" style="margin-top: 0px" >
					<tr>
						<th>姓名</th>
						<th>职位</th>
						<th>部门</th>
						<th>日期</th>
						<th>非拜访任务内容</th>
					</tr>
				<tbody>
					<#list pageParam.items as callPlanning>
						<tr>
							<td>${callPlanning.name!''}</td>
							<td>${callPlanning.positionName!''}</td>
							<td>${callPlanning.structureName!''}</td>
							<td><#if (callPlanning.callDate)??>${(callPlanning.callDate)?string("yyyy-MM-dd EE")}</#if></td>
							<td>
								<#if callPlanning.enumValue == 0>
									走店
								<#else>
									${callPlanning.optionText}
								</#if>
							</td>
						</tr>
					</#list>
				</tbody>
 	</table>
 	<div class="paging">	
    	<@pagination.paging pageParam true "#logResultList"/>
	</div>
</div>
<script type="text/javascript">
$('#savetButton').bind("click",function(){
	var clientUserId = $("#clientUserId").val();
	var callDate = $("#callDate").val();
	var workType = $(".tab_current_b").val();
	var enumValue = $('#enumValue').val();
	$.ajax({
		url : "${contextPath}/callPlanning/addCallPlanning",
		type : "post",
		dataType:"json",
		async: false,
		data : {clientUserId:clientUserId,callDate:callDate,
		workType:workType,enumValue:enumValue},
		success:function(result){
			if(result.code=="success"){
				layer.alert(result.message,function(){
	   			 // window.parent.location.reload();
	   			// parent.addDialog.close();
	   			window.location.href = "${contextPath}/callPlanning/query"
		   		parent.layer.closeAll('iframe');
	   			});
			}else {
				layer.alert(result.message);
		   	}
		}
	});

});

</script>

