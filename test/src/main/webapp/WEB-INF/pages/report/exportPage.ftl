<html>
<head>
<title>导出报表-<#if dds?exists>${dds.name}</#if></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body class="main_body">
	
	<#assign privCode="${PCode}">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>>
			<a href="#">统计报表</a>
			<a class="linkPage active" href="/export/query/${dds.settingId}?PCode=${PCode}">>${dds.name}</a>
	 	</div>

	<#if dds?exists>
	<div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				
				<div class="control-group">
					<input type="hidden" id="settingId" name="settingId" value="${dds.settingId}" />
					
					<#list dds.filterList as fls>
					
						<#if fls.type == 4 || fls.type == 18 || fls.type == 19 || fls.type == 20 || fls.type == 21 || fls.type == 22 || fls.type == 23 || fls.type == 26 || fls.type == 27 || fls.type == 30 >
							<#include "/report/widget/arg_hidden_widget.ftl" />
						<#else>
							<div nowrap="true" class="fl">
								<label class="control-label">${fls.label}：</label>
								<div class="controls">
									<#include "/report/widgets.ftl" />
								</div>
							</div>
						</#if>
					
					</#list>
				</div>
				
				<div class="form-actions">
					<#include "/report/widget/export_func_widget.ftl" />	
					<#if dds.remark??>
						<font style="font-size:13px;color:blue;">${dds.remark}</font>
					</#if>				
				</div>
				
			</form>
		</div>
	</div>
	<div id="widget_content_${dds.settingId}" style="max-height:600px;overflow:scroll;width:auto;"></div>
	</#if>
	
	</div></div>
	<#include "/footer.ftl" />
	
</body>
</html>