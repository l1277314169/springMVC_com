<html>
<head class="main_body">
  <title>问卷</title>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
  <script type="text/javascript" src="${contextPath}/js/freewall.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/layer/layer.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.imageScroller.js?cVer=${cVer}"></script>
<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
</head>

<body class="main_body">
	<#assign privCode="C5M4">
		<#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">问卷管理</a>
					<a class="linkPage active" href="${contextPath}/surveyStoreDisplay/query">问卷照片</a>
				</div>
		 	</div>
		 
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="" id="searchForm" novalidate="novalidate">
				<input type="hidden" id="arg_client_id" name="arg_client_id" value="${params.arg_client_id}" />
				<input type="hidden" id="arg_start_date" name="arg_start_date" value="${params.arg_start_date}" />
				<input type="hidden" id="arg_end_date" name="arg_end_date" value="${params.arg_end_date}" />
				
				<div class="control-group">
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">周期：</label>
					<div class="controls">
					  <input type="hidden" id="feedbackDate" name="feedbackDate" value="${params.feedbackDate}" class="input-medium" dataPosition=""/>
					  <#assign month="feedbackDate">
					  <#include "/widgets/month_widget.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">区域：</label>
					<div class="controls">
					  <input type="text" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
					  <input type="text" id="arg_dept_ids" name="arg_dept_ids" value="${params.arg_dept_ids}" style="display:none;">
					  <#assign structureFtlName="arg_dept_ids">
					  <#include "/widgets/structure.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">渠道类型：</label>
					<div class="controls">
					  <input type="text" id="clientStructureId_channel" name="clientStructureName_channel"  class="input-medium" readonly onclick="showMenu_channel();" />
					  <input type="hidden" id="arg_channel_ids" name="arg_channel_ids" value="${params.arg_channel_ids}" />
					  <#assign channelFTL="arg_channel_ids">
					  <#include "/widgets/channel_widget.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">客户类型：</label>
					<div class="controls">
					  <input type="text" id="clientStructureId_chain" name="clientStructureName_chain"  class="input-medium" readonly onclick="showMenu_chain();">
					  <input type="hidden" id="arg_types" name="arg_types" value="${params.arg_types}" >
					  <#assign chainFTL="arg_types">
					  <#include "/widgets/chain_widget.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">状态：</label>
					<div class="controls">
					 	<input type="text" id="arg_status" name="arg_status"  class="input-medium" value="${params.arg_status}" />
					  	<#assign statusFTL="arg_status">
					  	<#include "/widgets/store_status.ftl" />
					</div>
				</div>
				
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">省：</label>
					<div class="controls">
					  <input type="text" id="province_ids" name="province_ids" value="${params.province_ids}" class="input-medium" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">市：</label>
					<div class="controls">
					  <input type="text" id="city_ids" name="city_ids" value="${params.city_ids}" class="input-medium" />
					  <#include "/widgets/province_city_widget2.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">门店编号：</label>
					<div class="controls">
					  <input type="text" id="arg_store_no" name="arg_store_no" value="${params.arg_store_no}" class="input-medium" dataPosition=""/>
					</div>
				</div>
				
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">门店名称：</label>
					<div class="controls">
					  <input type="text" id="arg_store_name" name="arg_store_name" value="${params.arg_store_name}" class="input-medium" dataPosition=""/>
					</div>
				</div>

				<div nowrap="true" class="fl">
					<label class="control-label fl">访问员：</label>
					<div class="controls">
					  <input type="text" id="arg_visitor" name="arg_visitor" class="input-medium" value="${params.arg_visitor}" required=true />
					</div>
				</div>

				</div>

				<div class="form-actions">
					<button type="button" id="zipDownload" class="btn btn-primary">打包下载</button>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn" />
				</div>
				
			</form>
		</div>


		<div class="widget-content nopadding" id="freewall" style="max-height:600px;overflow-y:scroll;width:auto;">
			<#if images?exists>
			<#list images?keys as key>
				<#assign name = key?split("@")>
				<#include "/image/colgateImageLrtK.ftl" />
			</#list>
			</#if>
		</div>
	</div>
	<#include "/footer.ftl" />
</body>

<script type="text/javascript">
	jQuery(document).ready(function() {
		//打包下载
		jQuery("#zipDownload").click(function(){
			var url ="${contextPath}/image/downloadColgatImage/";
			jQuery('#searchForm').attr("action",url);
			jQuery('#searchForm').submit();
			jQuery('#searchForm').attr("action","${contextPath}/image/querySurveyImage/");
		});

	});

</script>
</html>