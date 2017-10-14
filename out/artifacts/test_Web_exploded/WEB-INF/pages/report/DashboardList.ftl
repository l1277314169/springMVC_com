<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>
	报表管理-<#if baseReport?exists>${baseReport.reportName}</#if>
</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/freewall.js?cVer=${cVer}"></script>
</head>
<body class="main_body">
	<#assign privCode="C4M41">
	<#include "/base.ftl" />
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">系统报表</a>
				<a class="linkPage active" href="${contextPath}/dashboard/query/35">DASHBOARD</a>
			</div>
	 	</div>
	
	<#if baseReport?exists>
		<!-- 导入图表 -->
		<div id="freewall_BG" class="container-fluid" style="margin-top:10px;height:auto;padding-bottom:50px;">
			<script src="${contextPath}/js/echarts/echarts-all.js"></script>
			<script src="${contextPath}/js/echarts/theme/themeAll.js"></script>
			
			<input type="hidden" id="clientId" name="clientId" value="${clientId}" />
			<#list baseReport.reportVo as parts >
				<#list parts.chartTypes as c >
					<form class="form-horizontal" id="searchForm_${parts.partId}" novalidate="novalidate">
						<#list parts.filterList as fls>
							<input type="hidden" id="${fls.arg}" name="${fls.arg}" value="${parts.params[fls.arg]}" class="input-medium" />			
						</#list>
					</form>
					<#include "/report/chart/${c.charType}.ftl"/>
				</#list>
			</#list>
		</div>
	</#if>
	</div>
	<#include "/footer.ftl" />
</body>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		var wall = new freewall("#freewall_BG");
		wall.reset({
					selector: '.echarts_div',
					cellW: 20,
					cellH: 20,
					onResize: function() {
						wall.fitHeight($(window).height());
						wall.fitWidth($(window).width());
					}
				});
		wall.fitHeight($(window).height());
		wall.fitWidth($(window).width());
		$(window).trigger("resize");
	});
</script>

</html>