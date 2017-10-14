<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>拜访任务管理</title>
</head>
<body   class="main_body">
		 <#assign privCode="C1M2">
		 <#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/visit/query">拜访任务管理</a>
				</div>
		 	</div>
		
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="clientPositionId">职位：</label>
							<div class="controls">
								<select id="clientPositionId" class="input-medium" name="clientPositionId"  value="${clientPositionId!''}">
								    <option value="">--请选择--</option>s
		                    		<#list cpList as option>
		                    		   <#if clientPositionId == option.clientPositionId>
		                    				<option value="${option.clientPositionId!''}" selected="selected">${option.positionName!''}</option>
		                    			<#else>
		                    				<option value="${option.clientPositionId!''}" >${option.positionName!''}</option>
		                    			</#if>
		                    		</#list>
								</select>
							</div>
						</div>
					</div>
					<div class="form-actions">
					<@shiro.hasPermission name="C1M2F1">
						<button type="button" id="new_btn" class="btn btn-success">添加任务</button>
					</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>拜访任务名称</th>
				      	<th>任务类型</th>
				      	<th>执行人员职位</th>
				      	<th>拜访对象</th>
				      	<th>开始时间</th>
				      	<th>结束时间</th>
						<th width="100px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as visitingTask>
						<tr>
							<td>${(visitingTask.visitingTaskName)!''}</td>
								<td><#if visitingTask.visitingTaskType == '1'>日常拜访<#elseif visitingTask.visitingTaskType == '2' >协访<#elseif visitingTask.visitingTaskType == '3' >店检</#if></td>
								<td>${(visitingTask.clientPositionName)!''}</td>
								<td><#if visitingTask.popType == '1'>门店<#elseif visitingTask.popType == '2' >经销商</#if></td>
								<td><#if (visitingTask.startDate)??>${(visitingTask.startDate)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
								<td><#if (visitingTask.endDate)??>${visitingTask.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
								<td>
								<@shiro.hasPermission name="C1M2F2">
									<a class="editVisitingTask" href="javascript:void(0);" dataId="${visitingTask.visitingTaskId!''}" dataName="${visitingTask.visitingTaskName!''}">编辑</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C1M2F3">
									<!--<a class="deleteVisitingTask" href="javascript:void(0);"  data="${visitingTask.visitingTaskId!''}">删除</a>-->
								</@shiro.hasPermission>
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
var editDialog, addDialog;
$(function(){	
	$("#new_btn").bind("click",function(){
		$('#breadcrumb',window.top.document).append('<a> 添加拜访任务</a>');
		var url = "${contextPath}/visit/showAddVisitingTask";
		window.location.href=url;
	});
	
	$(".editVisitingTask").bind("click",function(){
		$('#breadcrumb',window.top.document).append('<a> 修改拜访任务</a>');
		var visitingTaskId = $(this).attr("dataId");
		var url = "${contextPath}/visit/showEditVisitingTask/"+visitingTaskId;
		window.location.href=url;
	});
	
});

$('#clientPositionId').select2();
</script>