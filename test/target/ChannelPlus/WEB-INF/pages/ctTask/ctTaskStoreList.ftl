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
<body class="main_body">
<#assign privCode="C1M13">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/ctTask/query">周期任务管理</a>
					<a class="linkPage active" href="${contextPath}/ctTask/ctTaskStoreList?ctTaskId=${ctTask.ctTaskId}">${ctTask.taskName}</a>
				</div>
		 	</div>
<div class="boloc_moveBar" style="display:none" ><h2>更新中，请稍等……</h2></div> 
		<div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<input type="hidden" id="ctTaskId" name="ctTaskId" value="${ctTask.ctTaskId}">
					<div class="control-group">						 
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
	              	</div>	
	              	<div class="form-actions">
	              		<button type="button" id="backButton" class="btn btn-success">返回</button>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}" />				 
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<#if storeTask.clientId!=13>
							<th>门店编号</th>
							<th>门店名称</th>
							<th>省份</th>
							<th>城市</th>
							<th>操作</th>
						<#else>
							<th>门店编号</th>
							<th>门店名称</th>
							<th>省份</th>
							<th>市</th>
							<th>店铺地址</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>档期</th>
							<th>操作</th>
						</#if>
					</tr>
					<tbody>
						<#list pageParam.items as store>
							<tr>	
								<#if storeTask.clientId!=13>
									<td>${store.storeNo}</td>
									<td>${store.storeName}</td>	
									<td>${store.provinceName!''}</td>
									<td>${store.cityName!''}</td>
								<#else>
									<td>${store.storeNo}</td>
									<td>${store.storeName}</td>
									<td>${store.provinceName!''}</td>	
									<td>${store.fax}</td>
									<td>${store.addr}</td>
									<td>${store.contact}</td>
									<td>${store.telephoneNo}</td>
									<td>${store.creditPeriod}</td>
								</#if>	
								<td><a class="addCtTaskData" href="javascript:void(0);" popName="${store.storeName}" beginDate="${store.contact}" taskName="${ctTask.taskName!''}" ctTaskId="${ctTask.ctTaskId!''}" popId="${store.storeId}" >添加数据</a></td>					
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
	
	$("a.addCtTaskData").bind("click",function(){
	    var ctTaskId=$(this).attr("ctTaskId");    
	    var popId = $(this).attr("popId");
	    var beginDate = $(this).attr("beginDate");
		var url = "${contextPath}/ctTask/showEditCtTask?ctTaskId="+ctTaskId+"&popId="+popId+"&beginDate="+beginDate;
		var popName = $(this).attr("popName");
		$('#breadcrumb',window.top.document).append('<a>'+popName+'</a>');
		window.location.href=url;
	});
	
	$("#backButton").bind("click",function(){
		window.location.href="${contextPath}/ctTask/query";	
		$('#breadcrumb a:last-child',window.top.document).remove();
	});
	
	$("a.showCtTaskDataDetail").bind("click",function(){
	    var ctTaskId=$(this).attr("ctTaskId");    
	    var popId=$(this).attr("popId");
	    var taskCycle=$(this).attr("taskCycle");
		var url = "${contextPath}/ctTask/showCtTaskDataDetail?ctTaskId="+ctTaskId+"&popId="+popId+"&taskCycle="+taskCycle;
		window.location.href=url;	
	});
	
	loadProvince();
	
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
</script>