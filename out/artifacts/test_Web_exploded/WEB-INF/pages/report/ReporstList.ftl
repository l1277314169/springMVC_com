<html>
<head>
<title>
	报表管理-<#if baseReport?exists>${baseReport.reportName}</#if>
</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/freewall.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body class="main_body">
	
	<#assign privCode="${PCode}">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">统计报表</a>
				<a class="linkPage active" href="/report/query/${baseReport.reportId}?PCode=${PCode}">${baseReport.reportName}</a>
			</div>
	 	</div>
	
	<#if baseReport?exists>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="/report/query/${baseReport.reportId}" method="Post" id="searchForm" novalidate="novalidate">
				<input type="hidden" id="PCode" name="PCode" value="${PCode}" />
				<div class="control-group">
					
					<input type="hidden" id="reportId" name="reportId" value="${baseReport.reportId}" />
					
					<#list baseReport.reportFilterList as fls>
						<#if fls.type == 4 || fls.type == 18 || fls.type == 19 || fls.type == 20 || fls.type == 21 || fls.type == 22 || fls.type == 23 || fls.type == 26 || fls.type == 27 || fls.type == 30 || fls.type == 50 || fls.type == 52 || fls.type == 53 || fls.type == 57>
							 <#include "/report/widget/arg_hidden_widget.ftl" />
						<#elseif fls.type == 34 >
							 <div nowrap="true" class="fl">
								<label class="control-label">${fls.label}：</label>
								<div class="controls">
									 <#include "/report/widget/arg_weekly_id_widget.ftl" />
								</div>
							</div>
						<#elseif fls.type == 35 >
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
					<#if baseReport.reportType==0>
						<input type="button" value="查询" class="btn btn-info fr" id="search_btn" />
					</#if>
					<#include "/report/widget/func_widget.ftl" />
					
					<#if baseReport.remark??>
						<br />
						<font style="font-size:13px;color:blue;">${baseReport.remark}</font>
					</#if>
					
					<#if alertMsg??>
						<br />
						<font style="font-size:13px;color:blue;">${alertMsg}</font>
					</#if>
				</div>
			</form>
		</div>
		
		<script src="${contextPath}/js/echarts/echarts-all.js"></script>
		<script src="${contextPath}/js/echarts/theme/themeAll.js"></script>
		
		<#if baseReport.reportType==0>
			<div id="freewall_BG" class="container-fluid" style="margin-top:10px;">
			<#list baseReport.reportVo as parts >
				<#list parts.chartTypes as c >
					<#include "/report/chart/${c.charType}.ftl"/>
				</#list>
			</#list>
			</div>
		</#if>
	</#if>
	 </div>
	</div>
		
	<#include "/footer.ftl" />
	
</body>

<script type="text/javascript">

	jQuery(document).ready(function($) {
		jQuery("#search_btn").click(function(){
			//alert(jQuery("#searchForm").serialize());
			jQuery.ajax({
			  url: '/report/verify/${baseReport.reportId}',
			  type: 'POST',
			  dataType: 'json',
			  data: jQuery("#searchForm").serialize(),
			  success: function(data, textStatus, xhr) {
			    if(data.code=='success'){
			    	jQuery("#searchForm").submit();	
			    }else{
			    	 layer.alert(data.message);
			    }
			  },
			  error: function(xhr, textStatus, errorThrown) {
			    	layer.alert(errorThrown);
			  }
			});
  		});
  		
  		
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

	
	function drillReport(value,navName){
		var navObject = jQuery("#breadcrumb",parent.document);
		var formVal = jQuery("#searchForm").serialize();
		var val = formVal+"&"+value;
		jQuery(navObject).append("<a class='linkPage active' href='/report/query/${baseReport.reportId}?"+val+"' target='main'>"+navName+"</a>");
		document.location.href= "${contextPath}/report/query/${baseReport.reportId}?"+val;
	}

	function isExist(object,name){
		var childrens = jQuery(object).children("a");
		var flag = false;
		for (var i = 0; i < childrens.length; i++) {
			var v = jQuery(childrens[i]).html();
			if(v==name){
		  		flag =  true;
		  		break;
		  	}
		}
		return flag;
	}
</script>

</html>