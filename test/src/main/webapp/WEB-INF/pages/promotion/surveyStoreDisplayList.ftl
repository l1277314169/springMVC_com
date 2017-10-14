<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>promotion导入</title>
</head>
<body class="main_body">
		<#assign privCode="C1M16">
		<#include "/base.ftl" />
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/surveyStoreDisplay/query">promotion导入</a>
				</div>
			</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label fl" for="planCode">plan_code：</label>
							<div class="controls">
							  <input type="text" id="planCode" name="planCode" class="input-medium" value="${planCode}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeNo">门店编号：</label>
							<div class="controls">
							  <input type="text" id="storeNo" name="storeNo" class="input-medium" value="${storeNo}">
							</div>
						</div>
					</div>
					<div class="form-actions">
						<@shiro.hasPermission name="C1M16F1">
							<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
				      	<th>plan_code</th>
				      	<th>store_no</th>
				      	<th>valid_from</th>
				      	<th>valid_to</th>
				      	<th>display_type_desc</th>
				      	<th>promo_sku</th>
				      	<th>create_time</th>
					</tr>
					<tbody>
						<#list pageParam.items as surveyStoreDisplay>
						<tr>
								<td>${surveyStoreDisplay.planCode!''}</td>
								<td>${surveyStoreDisplay.storeNo!''}</td>
								<td><#if surveyStoreDisplay.validFrom??>${surveyStoreDisplay.validFrom?date}</#if></td>
								<td><#if surveyStoreDisplay.validTo??>${surveyStoreDisplay.validTo?date}</#if></td>
								<td>${surveyStoreDisplay.displayTypeDesc!''}</td>
								<td>${surveyStoreDisplay.promoSku!''}</td>
								<td><#if surveyStoreDisplay.createTime??>${surveyStoreDisplay.createTime?date}</#if></td>
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
var importDialog;
$(function(){
		//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/surveyStoreDisplay/showImportDialog";
		 layer.open({
			    type: 2,
			    title: 'promotion导入',
			    closeBtn: 1,
			    area: ['650px', '347px'],
			    content: url
			});
		//importDialog = new xDialog(url, {}, {title:"promotion导入",width:650,height:330 });
		//return false;
	});	
});
</script>