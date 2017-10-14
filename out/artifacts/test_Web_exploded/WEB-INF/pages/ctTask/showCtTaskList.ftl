<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>门店管理</title>
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
	float:left;
	width: 800px; 
	height: 600px; 
	background-color:#ffffff; 
	border: solid 1px #000; 
	//left:20%; 
	//top:15%;
	margin: 0 auto; 
	text-align:center;
	padding:2px; 
	z-index: 2999; 
} 
</style>
</head>
<body  class="main_body">
<div class="boloc_moveBar" style="display:none" ><h2>更新中，请稍等……</h2></div> 
		<#assign privCode="C1M13">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/ctTask/query">周期任务管理</a>
					<a class="linkPage active" href="${contextPath}/ctTask/showCtTaskList?ctTaskId=${ctTask.ctTaskId}">${ctTask.taskName}</a>
				</div>
		 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<input type="hidden" id="ctTaskId" name="ctTaskId" value="${ctTask.ctTaskId}">
					<input type="hidden" id="isInCycel" name="isInCycel" value="<#if storeTask.isInCycel==''>false<#else>${storeTask.isInCycel}</#if>" >
					<div class="control-group">
						<#if ctTask.cycleType==1>
							<div class="fl">
								<label class="control-label fl" for="channelName">开始时间：</label>
								<div class="controls">
								  <input id="startDate" type="text" name="startDate" class="input-medium" value="${storeTask.startDate?date}" readonly="readonly" />
								</div>
							</div>
							<div class="fl">
								<label class="control-label fl" for="distributorName">结束时间：</label>
								<div class="controls">
								  <input id="endDate"type="text" name="endDate" class="input-medium" value="${storeTask.endDate?date}" readonly="readonly" />
								</div>
							</div>
						<#else>
							<div class="fl">
								<label  class="control-label fl" for="taskCycle">任务周期：</label>
								<div class="controls">
									<input type="text" id="taskCycle" name="taskCycle" value="${storeTask.taskCycle}"/>	
								</div>
							</div>
						</#if>	
						<div class="fl">
							<label class="control-label " for="structureName">省份：</label>
							<div class="controls">
							  <input type="text" id="provinceId" name="provinceId" value="${storeTask.provinceId}"/>
							</div>
						</div>
						<#if storeTask.clientId!=13>
							<div class="fl">
								<label class="control-label " for="structureName">城市：</label>
								<div class="controls">
								  <input type="text" id="cityId" name="cityId" value="${storeTask.cityId}" required  />
								</div>
							</div>							
						</#if>																	
						<div class="fl">
							<label class="control-label fl" for="storeNo">门店编号：</label>
							<div class="controls">
							  <input type="text" id="storeNo" name="storeNo" class="input-medium" value="${storeTask.storeNo}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeName">门店名称：</label>
							<div class="controls">
							  <input type="text" id="storeName" name="storeName" class="input-medium" value="${storeTask.storeName}">
							</div>
						</div>			
						
						
						<#if clientId==7>
						<div class="fl">
							<label class="control-label fl" for="storeName">是否提交数据：</label>
							<div class="controls">
							<input type="text" id="reportData" name="reportData" value="${storeTask.reportData}" class="input-medium" />
							<#include "/widgets/ctTask_status.ftl" />
							</div>
						</div>
						
						<div class="fl">
							<label class="control-label fl" for="storeName">更新人员：</label>
							<div class="controls">
								<input type="text" id="updateClientUserName" name="updateClientUserName" value="${storeTask.updateClientUserName}" class="input-medium" />
							</div>
						</div>

						</#if>
												
	              	</div>	
	              	<div class="form-actions">
	              		<button type="button" id="backButton" class="btn btn-success">返回</button>
	              		<@shiro.hasPermission name="C1M13F3">
	              			<button type="button" id="exportCtTaskData" class="btn btn-success">导出</button>
	              		</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}" />				 
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<#if storeTask.clientId!=13>
							<th>周期</th>
							<th>门店编号</th>
							<th>门店名称</th>
							<th>更新日期</th>
							<#if storeTask.clientId==7>
								<th>提报人</th>
							</#if>
							<th>更新人员</th>
							<th>操作</th>
						<#else>
							<th>周期</th>
							<th>门店编号</th>
							<th>门店名称</th>
							<th>省份</th>
							<th>市</th>
							<th>店铺地址</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>档期</th>
							<th>更新日期</th>
							<th>更新人员</th>
							<th>操作</th>
						</#if>
					</tr>
					<tbody>
						<#list pageParam.items as storeTaskInfo>
							<tr>
								<td>${storeTaskInfo.showCycleDate}</td>
								<#if storeTask.clientId!=13>
									<td>${storeTaskInfo.storeNo}</td>
									<td>${storeTaskInfo.storeName}</td>	
									<td>
										<#if storeTaskInfo.lastUpdateTime??>
											${(storeTaskInfo.lastUpdateTime)?string("yyyy-MM-dd HH:mm:ss")}
										</#if>
									</td>
									<#if storeTask.clientId==7>
										<td>${storeTaskInfo.clientUserName}</td>
									</#if>
									<td>${storeTaskInfo.createClientUserName}</td>
								<#else>
									<td>${storeTaskInfo.storeNo}</td>
									<td>${storeTaskInfo.storeName}</td>
									<td>${storeTaskInfo.provinceName!''}</td>	
									<td>${storeTaskInfo.fax}</td>
									<td>${storeTaskInfo.addr}</td>
									<td>${storeTaskInfo.contact}</td>
									<td>${storeTaskInfo.telephoneNo}</td>
									<td>${storeTaskInfo.creditPeriod}</td>
									<td>
									<#if storeTaskInfo.lastUpdateTime??>
										${(storeTaskInfo.lastUpdateTime)?string("yyyy-MM-dd HH:mm:ss")}
									</#if>
									</td>
									<td>${storeTaskInfo.clientUserName}</td>
								</#if>
								<td>
								
								<#if storeTaskInfo.lastUpdateTime??>
								<@shiro.hasPermission name="C1M13F5">
									<a class="deleteCtTaskData" href="javascript:void(0);" storeName="${storeTaskInfo.storeName}" taskName="${ctTask.taskName!''}" ctTaskDataId="${storeTaskInfo.ctTaskDataId}" ctTaskId="${ctTask.ctTaskId!''}" popId="${storeTaskInfo.storeId}" >删除</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C1M13F6">
									<#if storeTaskInfo.isInCycel=='true' && clientId==7>
										<a class="editCtTaskData" href="javascript:void(0);" storeName="${storeTaskInfo.storeName}" taskName="${ctTask.taskName!''}" ctTaskDataId="${storeTaskInfo.ctTaskDataId}" beginDate="${storeTaskInfo.contact}" ctTaskId="${ctTask.ctTaskId!''}" popId="${storeTaskInfo.storeId}" >修改</a>
									<#elseif clientId==13>
										<a class="editCtTaskData" href="javascript:void(0);" storeName="${storeTaskInfo.storeName}" taskName="${ctTask.taskName!''}" ctTaskDataId="${storeTaskInfo.ctTaskDataId}" beginDate="${storeTaskInfo.contact}" ctTaskId="${ctTask.ctTaskId!''}" popId="${storeTaskInfo.storeId}" >修改</a>
									</#if>
								</@shiro.hasPermission>
								<a class="showCtTaskDataDetail" href="javascript:void(0);" storeName="${storeTaskInfo.storeName}" taskName="${storeTaskInfo.taskName!''}" taskCycle="${storeTaskInfo.showCycleDate}" popId="${storeTaskInfo.storeId}" ctTaskId="${ctTask.ctTaskId!''}" ctTaskDataId="${storeTaskInfo.ctTaskDataId}">查询</a>
								<#else>
									<#if clientId==7>
									<@shiro.hasPermission name="C1M13F1">
										<#if storeTaskInfo.isInCycel=='true'>
											<a class="addCtTaskData" href="javascript:void(0);" popName="${storeTaskInfo.storeName}" taskName="${ctTask.taskName!''}" ctTaskId="${ctTask.ctTaskId!''}" popId="${storeTaskInfo.storeId}" >添加数据</a>
										</#if>				 							
									</@shiro.hasPermission>
									</#if>
								</#if>
								
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
	</div>	 
</div>
<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
$(function(){

	//导出门店信息
	$("#exportCtTaskData").bind("click",function(){
		var index = layer.confirm("确认导出历史数据吗？", function () {
			var url = "${contextPath}/ctTask/exportCtTaskData";
			$("#searchForm").attr("action",url);
			$("#searchForm").submit();
			$("#searchForm").attr("action","");
			layer.close(index);
		});
	});
	
	//添加数据
	$("a.addCtTaskData").bind("click",function(){
	    var ctTaskId=$(this).attr("ctTaskId");    
	    var popId = $(this).attr("popId");
	    var isInCycel = $("#isInCycel").val();
		var url = "${contextPath}/ctTask/showEditCtTask?ctTaskId="+ctTaskId+"&popId="+popId+"&isInCycel="+isInCycel;
		var popName = $(this).attr("popName");
		$('#breadcrumb',window.top.document).append('<a>'+popName+'</a>');
		window.location.href=url;
	});
	
	$("a.deleteCtTaskData").bind("click",function(){
		var ctTaskDataId = $(this).attr("ctTaskDataId");
		var ctTaskId = $(this).attr("ctTaskId");
		var isInCycel = $("#isInCycel").val();
		layer.confirm("确认删除吗？",function(){
			var url = "${contextPath}/ctTaskData/deleteCtTaskData?ctTaskDataId="+ctTaskDataId;
			$.post(url,function(result){
				if(result.code=="success"){
					layer.alert(result.message,function(){
						window.location.href="${contextPath}/ctTask/showCtTaskList?ctTaskId="+ctTaskId+"&isInCycel="+isInCycel;
					});
				}	
			});
		});
	});
		
	$("#backButton").bind("click",function(){
		window.location.href="${contextPath}/ctTask/query";	
		$('#breadcrumb a:last-child',window.top.document).remove();
	});
	
	$("a.showCtTaskDataDetail").bind("click",function(){
	    var ctTaskId=$(this).attr("ctTaskId");    
	    var popId=$(this).attr("popId");
	    var storeName=$(this).attr("storeName");
	    var ctTaskDataId=$(this).attr("ctTaskDataId");
	    var isInCycel = $("#isInCycel").val();
	    if(null==isInCycel || ''==isInCycel){
			isInCycel = false;
		}
		var url = "${contextPath}/ctTask/showCtTaskDataDetail?ctTaskId="+ctTaskId+"&popId="+popId+"&ctTaskDataId="+ctTaskDataId+"&isInCycel="+isInCycel;
		$('#breadcrumb',window.top.document).append('<a><b>'+storeName+'</b></a>');
		window.location.href=url;	
	});
	
	$("a.editCtTaskData").bind("click",function(){
		var clientId = "${clientId}";
		if(clientId==13){
			var ctTaskDataId=$(this).attr("ctTaskDataId");
		    var ctTaskId=$(this).attr("ctTaskId");    
		    var popId = $(this).attr("popId");
		    var isInCycel = $("#isInCycel").val();
		    var beginDate = $(this).attr("beginDate");
			var url = "${contextPath}/ctTask/showEditCtTaskData?ctTaskId="+ctTaskId+"&popId="+popId+"&ctTaskDataId="+ctTaskDataId+"&isInCycel="+isInCycel+"&beginDate="+beginDate;
			var storeName=$(this).attr("storeName");
			$('#breadcrumb',window.top.document).append('<a>'+storeName+'</a>');
			window.location.href=url;
		}else{
			var ctTaskId=$(this).attr("ctTaskId");    
		    var popId = $(this).attr("popId");
		     var isInCycel = $("#isInCycel").val();
			var url = "${contextPath}/ctTask/showEditCtTask?ctTaskId="+ctTaskId+"&popId="+popId+"&isInCycel="+isInCycel;
			var storeName=$(this).attr("storeName");
			$('#breadcrumb',window.top.document).append('<a>'+storeName+'</a>');
			window.location.href=url;
		}    
	});
	
	loadProvince();
	
	var currentCycle = '${storeTask.taskCycle}';
	
	loadTaskCycle(currentCycle);
	
	var provinceId = '${storeTask.provinceId}'; 
	if(provinceId!=''){
		$.ajax({
			type : "post",
			url : "${contextPath}/commService/findCityListByProvinceId/"+provinceId,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"},";
					} else {
						thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				$("#cityId").select2({
					width:145,
					placeholder: "请选择",
					allowClear: true,
					data: cData
				});
			},
			error : function(data) {
				alert("数据加载失败！");
			}
		});
	}else{
		$("#cityId").select2({
        	width:145,
			placeholder: "请选择",
			data:[]
		});
	}
	
	$("#provinceId").on("change", function () {
			var value = $(this).val();
			if(value == "") {
				$("#cityId").val('');
				$("#districtId").val('');
				$("#cityId").select2({
		        	width:145,
					placeholder: "请选择",
					data:[]
				});
		        $("#districtId").select2({
		        	width:145,
					placeholder: "请选择",
					data: []
		        });
			}
			else{
				$.ajax({
					type : "post",
					url : "${contextPath}/commService/findCityListByProvinceId/"+value,
					dataType : "json",
					cache : false,
					success : function(data) {
						var jsonData = eval(data);
						var thisData = "[";
						$.each(jsonData, function(index, item) {
							if(index != jsonData.length-1){
								thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"},";
							} else {
								thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"}";
							}
						});
						thisData += "]";
						var cData = $.parseJSON(thisData);
						$("#cityId").select2({
							width:145,
							placeholder: "请选择",
							allowClear: true,
							data: cData
						});
					},
					error : function(data) {
						alert("数据加载失败！");
					}
				});
			}
		});
	
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#endDate").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});

	$("#endDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#startDate").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
});
function loadProvince(){
	$.ajax({
		type : "post",
		url : "${contextPath}/commService/getProvinceAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"},";
				} else {
					thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#provinceId").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};

function loadTaskCycle(currentCycle){
	var ctTaskId = $("#ctTaskId").val();
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTask/getTaskDate?ctTaskId="+ctTaskId,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(currentCycle=="" && item.currentCycle){
					currentCycle = item.dataCycle;		
				}
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.dataCycle+"\",\"text\":\""+item.dataCycle+"\"},";
				} else {
					thisData += "{\"id\":\""+item.dataCycle+"\",\"text\":\""+item.dataCycle+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#taskCycle").select2({
				width:200,
				placeholder: "请选择",
				data: cData
			});
			$("#taskCycle").select2("val",currentCycle);
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};	
</script>