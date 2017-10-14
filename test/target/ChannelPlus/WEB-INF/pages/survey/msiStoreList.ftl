<html>
<head>
<#include "/common/head.ftl" />
<title>暗访门店列表</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
		<#assign privCode="C5M2">
		<#include "/base.ftl" />
	<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">问卷管理</a>
					<a class="linkPage active" href="${contextPath}/msiSurveyFeedback/query">暗访管理</a>
				</div>
		 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label fl" for="storeNo">门店编号：</label>
							<div class="controls">
							  <input type="text" id="storeNo" name="storeNo" class="input-medium" value="${storeNo!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeName">门店名称：</label>
							<div class="controls">
							  <input type="text" id="storeName" name="storeName" class="input-medium" value="${storeName!''}">
							</div>
						</div>
					</div>
					<div class="form-actions">
						<button type="button" id="re_btn" class="btn btn-success">返回</button>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" id="count" name="count" value="${count}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>店铺编号</th>
						<th>店铺名称</th>
						<th>地址</th>
						<th>负责人</th>
						<th>操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as store>
							<tr>
								<td>${store.storeNo!''}</td>
								<td title="${store.storeName!''}">
									<#if store.storeName?? && store.storeName?length gt 8>
										${store.storeName?substring(0,8)}...
									<#else>
										${store.storeName!''}
									</#if>
								</td>
								<td title="${store.addr!''}">
									<#if store.addr?? && store.addr?length gt 8>
										${store.addr?substring(0,8)}...
									<#else>
										${store.addr!''}
									</#if>
								</td>
								<td>${store.names!''}</td>
								<td>
									<!-- <@shiro.hasPermission name="C2M4F3">
									<a class= "deleteStore"  href="javascript:void(0);" data="${store.storeId!''}">删除</a>
									</@shiro.hasPermission> -->
									<a class="addMsi" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}" dataNo="${store.storeNo!''}">添加</a>
									<!--
									<a class="addHistoryMsi" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}" dataNo="${store.storeNo!''}">添加历史问卷</a>
									-->
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
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
var addHisDialog;
$(function(){
	$("#re_btn").bind("click",function(){
		$('#breadcrumb a:last-child',window.top.document).remove();
		var url = "${contextPath}/msiSurveyFeedback/query";
		window.location.href=url;
	});
	$(".addMsi").bind("click",function(){
		var storeId=$(this).attr("dataId");
	    var storeNo=$(this).attr("dataNo");
	    var storeName=$(this).attr("dataName");
		var url = "${contextPath}/msiSurveyFeedback/showAddMsiSurveyData/"+storeId+"/"+storeNo+"/"+storeName;
		window.location.href=url;
	});
 
	//添加历史问卷
	$("a.addHistoryMsi").bind("click",function(){	      
		var storeId = $(this).attr("dataId");
		var url = "${contextPath}/msiSurveyFeedback/findHistoryMsiSurveyList/"+storeId;
		addHisDialog = new xDialog(url,{},{title:"历史问卷",width:760,height:656 }); 
		return false;	
	});
});
</script>