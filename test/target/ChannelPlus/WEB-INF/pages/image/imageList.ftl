<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/freewall.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/layer/layer.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.imageScroller.js?cVer=${cVer}"></script>
<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
<title>拜访图片查看</title>
</head>
<body class="main_body">
	<#assign privCode="C1M13">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础管理</a>
					<a class="linkPage active" href="${contextPath}/image/query">拜访图片查看</a>
				</div>
		 	</div>
			<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
				<div class="fl">
					<label class="control-label fl" for="storeId">门店名称：</label>
					<div class="controls">
						<input type="text" id="storeId" name="storeId" required class="input-medium" value="${storeId!''}" />
						<#assign storeFtlName="storeId">
						<#include "/widgets/store_select2.ftl" />
					</div>
				</div>
				
				<div class="fl">
					<label class="control-label fl" for="storeId">门店编号：</label>
					<div class="controls">
						<input type="text" id="storeNo" name="storeNo" required class="input-medium" value="${storeNo!''}" />
					</div>
				</div>
				
				<div class="fl">
					<label class="control-label fl" for="inTime">开始日期：</label>
					<div class="controls">
					  <input type="text" id="startDate" name="startDate"  class="inTimePicker" value="${startDate!''}">
					</div>
				</div>
				<div class="fl">
					<label class="control-label fl" for="inTime">结束日期：</label>
					<div class="controls">
					  <input type="text" id="endDate" name="endDate" class="inTimePicker" value="${endDate!''}">
					  <#include "/widgets/date_select_7.ftl" />
					</div>
				</div>
				</div>
				<div class="form-actions">
					<input type="button" value="查询" class="btn btn-info fr" id="search_btn">
					<@shiro.hasPermission name="C1M14F1">
						<button type="button" id="zipDownload" class="btn btn-primary">打包下载</button>
					</@shiro.hasPermission>
					
				</div>
			</form>
			</div>
			<div class="widget-content nopadding" id="freewall" style="max-height:600px;overflow-y:scroll;width:auto;">
				<#if images?exists>
				<#list images as img>
					<#include "/image/imageLrtK.ftl" />
				</#list>
				</#if>
			</div>
		</div>
	<#include "/footer.ftl" />
</body>
<script type="text/javascript">
	jQuery(document).ready(function() {
		//验证
		jQuery("#search_btn").click(function(){
			var storeId = jQuery("#storeId").val();
			var storeNo = jQuery("#storeNo").val();
			if((null==storeId || ''==storeId) && (null==storeNo || ''== storeNo)){
				layer.alert("门店名称与门店编号必须输入一项！");
			}else{
				jQuery("#searchForm").submit();
			}
		});

		//打包下载
		jQuery("#zipDownload").click(function(){
			var url ="${contextPath}/image/download/";
			jQuery('#searchForm').attr("action",url);
			jQuery('#searchForm').submit();
			jQuery('#searchForm').attr("action","${contextPath}/image/query/");
		});

	});

</script>

</html>