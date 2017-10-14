<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>任务管理</title>
</head>
<body  class="main_body">
		<#assign privCode="C1M13">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/ctTask/query">周期任务管理</a>
				</div>
		 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="clientPositionId">任务分组：</label>
							<div class="controls">
								<select id="ctTaskGroup" class="input-medium" name="ctTaskGroup" value="${ctTaskGroup}">
								    <option value="">--请选择--</option>
								    <#list ctTaskGroupOptions as ctTaskGroupOption>
								    		<option value="${ctTaskGroupOption.optionValue}" <#if ctTaskGroupOption.optionValue==ctTaskGroup>selected="selected"</#if>>${ctTaskGroupOption.optionText}</option>		
								    </#list>		                    		                    			                    	                  				                     		 
								</select>
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">任务名称：</label>
							<div class="controls">
							  <input type="text" id="taskName" name="taskName" value="${taskName}" class="input-medium" dataPosition=""/>
							</div>
						</div>
					</div>
					<div class="form-actions">
					<@shiro.hasPermission name="C1M13F2">
						<!--<button type="button" id="new_btn" class="btn btn-success">添加任务</button>-->
					</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<!--<th>任务类型</th>-->
				      	<th>任务名称</th>
				      	<th>频率</th>
				      	<th>上报周期</th>
				      	<th>状态</th>
						<th width="100px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as ctTask>
						<tr>
								<!--<#assign key="${ctTask.taskType}"/>
								<td>${taskTypeMap[key]}</td>-->
								<td>${ctTask.taskName!''}</td>
								<td>
									<#if ctTask.cycleType == '1'>天<#elseif ctTask.cycleType == '2'>周<#elseif ctTask.cycleType == '3'>半月<#elseif ctTask.cycleType == '4'>月</#if>
								</td>
								<td><#if ctTask.cycleType == '1'>${ctTask.validFromDate?date}<#else>${ctTask.validFromDate?date} - ${ctTask.validEndDate?date}</#if></td>
								<td><#if ctTask.isInCycel=='true'>可填报<#else>不可填报</#if></td>
								<td>
									
									<!-- -->
									<#if clientId!=7>
									<@shiro.hasPermission name="C1M13F1">
										<#if ctTask.isInCycel=='true'>
											<a class="addCtTask" href="javascript:void(0);" taskName="${ctTask.taskName!''}" ctTaskId="${ctTask.ctTaskId!''}">添加数据</a>
										</#if>				 							
									</@shiro.hasPermission>
									</#if>
									<a class="showCtTask" href="javascript:void(0);"  taskName="${ctTask.taskName!''}" ctTaskId="${ctTask.ctTaskId!''}">查询</a>				 							 					 
								</td>
						</tr>
						</#list> 
					</tbody>
				</table>
				<#if pageParam.items?exists> 
					<div class="paging" > 
					   ${pageParam.getPagination()}
					</div> 
				</#if>
			</div>
		</div>
		<#include "/footer.ftl" />
</body>
</html>
<script>
var addDialog;
$(function(){
	//任务提报
	$("a.showCtTask").bind("click",function(){	
		var ctTaskId=$(this).attr("ctTaskId");
		var url = "${contextPath}/ctTask/showCtTaskList?ctTaskId="+ctTaskId;
		var taskName = $(this).attr("taskName"); 
		$('#breadcrumb',window.top.document).append('<a>'+taskName+'</a>');
		window.location.href=url;	
	});
	
	$("a.addCtTask").bind("click",function(){
	    var ctTaskId=$(this).attr("ctTaskId");    
	    var taskName = $(this).attr("taskName"); 
	    $('#breadcrumb',window.top.document).append('<a>'+taskName+'</a>');
		var url = "${contextPath}/ctTask/ctTaskStoreList?ctTaskId="+ctTaskId;
		window.location.href=url;
	});
	
});


</script>