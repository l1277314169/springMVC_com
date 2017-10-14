<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>门店管理</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
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
		<#assign privCode="C5M1">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/appleSurvey/query">问卷查看</a>
				</div>
		
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label fl" for="arg_store_no">门店编号：</label>
							<div class="controls">
							  <input type="text" id="storeNo" name="storeNo" class="input-medium" value="${storeNo!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="arg_store_name">门店名称：</label>
							<div class="controls">
							  <input type="text" id="storeName" name="storeName" class="input-medium" value="${storeName!''}">
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl">轮次：</label>
							<div class="controls">
							  <input type="text" id="storeType" name="storeType" class="input-medium" value="${storeType!''}">
							</div>
						</div>
	              	</div>
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" id="count" name="count" value="${count}">
					<div class="form-actions">
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>大区</th>
						<th>所属部门</th>
						<th>省份</th>
						<th>城市</th>
						<th>轮次</th>
						<th>店铺编号</th>
						<th>店铺名称</th>
						<th>地址</th>
						<th>操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as store>
							<tr>
								<#if store.upStructureName?? && store.structureName??>
									<td title="${store.upStructureName!''}">
										<#if store.upStructureName?? && store.upStructureName?length gt 5>
											${store.upStructureName?substring(0,5)}..
										<#else>
											${store.upStructureName!''}
										</#if>
									</td>
									<td title="${store.structureName!''}">
										<#if store.structureName?? && store.structureName?length gt 5>
											${store.structureName?substring(0,5)}..
										<#else>
											${store.structureName!''}
										</#if>
									</td>
								</#if>
								<#if !(store.upStructureName??) && store.structureName??>
									<td title="${store.structureName!''}">
										<#if store.structureName?? && store.structureName?length gt 5>
											${store.structureName?substring(0,5)}..
										<#else>
											${store.structureName!''}
										</#if>
									</td>
									<td></td>
								</#if>	
								<#if !(store.upStructureName??) && !(store.structureName??)>
									<td></td>
									<td></td>
								</#if>	
								<td>
									${store.provinceName!''}
								</td>
								<td>
									${store.cityName!''}
								</td>
								<td>${store.storeType}</td>
								<td title="${store.storeNo!''}">
									<#if store.storeNo?? && store.storeNo?length gt 8>
										${store.storeNo?substring(0,8)}...
									<#else>
										${store.storeNo!''}
									</#if>
								</td>
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
								<td>
									<a class="addAppleData" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}">添加</a>
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
		<div>
		<div></div>
			<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
 	$(".addAppleData").click(function(){
		var dataId = $(this).attr("dataId");
		var url = "${contextPath}/appleSurvey/showAppleSurveyMain/"+dataId;
		$.post("${contextPath}/appleSurvey/getSurveyFeedbackByStoreId?storeId="+dataId,function(data){
			if(data!="" && data !=undefined && data.feedbackId!="" && data.feedbackId!=undefined){
				layer.alert("此门店已经填写过问卷");
				return;
			}
			document.location.href=url;
			var navObject = jQuery("#breadcrumb",parent.document);
			jQuery(navObject).append("<a class='linkPage active' href='"+url+"' target='main'>添加问卷</a>");
		});
	})
</script>