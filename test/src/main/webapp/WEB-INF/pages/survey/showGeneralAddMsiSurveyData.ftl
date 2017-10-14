<html>
<head>
<title>暗访问卷数据列表</title>
<#include "/common/foot.ftl" />
<#include "/common/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
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
	<br/>
	<div class="tab_container margin-top-18 no_bottom_border margin-left-10" style="height:100%">
		<div class="tabs">
			<ul id="stepTbas" class="margin-left-1" style="float:left">
				<#list msiSurveyFeedbacks as msiSurveyFeedback>
					<li id="step1" class="<#if msiSurveyFeedback_index==0>tab_current<#else>tab_normal</#if>" msiSurveyId="${msiSurveyFeedback.msiSurveyId}" feedbackId="<#if msiSurveyFeedback.feedbackId??>${msiSurveyFeedback.feedbackId}</#if>" storeId="${msiSurveyFeedback.storeId}" promoterNo="<#if msiSurveyFeedback.promoterNo??>${msiSurveyFeedback.promoterNo}</#if>" style="width:150"><span class="tab_num_label">${(msiSurveyFeedback_index)+1}</span><b>${msiSurveyFeedback.promoter}</b></li>
				</#list>
			</ul>		
		</div>
		<div id="tabs-body" class="tabs-body">
			
		</div>
	</div>
	
	</body>
</html>
<script type="text/javascript" language="javascript">
$(document).ready(function () {
 	var storeId = $("#stepTbas li:first-child").attr("storeId");
	var msiSurveyId = $("#stepTbas li:first-child").attr("msiSurveyId");
	var feedbackId = $("#stepTbas li:first-child").attr("feedbackId");
	var promoterNo = $("#stepTbas li:first-child").attr("promoterNo");
 	loadingSubEditMsiSurveyDatap(storeId,msiSurveyId,feedbackId,promoterNo);
 	
 	$("#stepTbas li").bind("click", function () {
        //如果是当前tab则不做任何动作，反之异步请求新的页面参数。
        if($(this).attr("class") == "tab_current") {
        	return false;
        } else {        	
            $(this).parent().children("li").attr("class", "tab_normal");//将所有选项置为未选中
            $(this).attr("class", "tab_current"); //设置当前选中项为选中样式
            var storeId = $(".tab_current").attr("storeId");
			var msiSurveyId = $(".tab_current").attr("msiSurveyId");
			var feedbackId = $(".tab_current").attr("feedbackId");
			var promoterNo = $(".tab_current").attr("promoterNo");
            loadingSubEditMsiSurveyDatap(storeId,msiSurveyId,feedbackId,promoterNo);
		}
    });
 });
 
 function loadingSubEditMsiSurveyDatap(storeId,msiSurveyId,feedbackId,promoterNo){
	$.post("/msiSurveyFeedback/showSubEditMsiSurveyData/",
		{
			storeId:storeId,
			msiSurveyId:msiSurveyId,
			feedbackId:feedbackId,
			promoterNo:promoterNo
		},
		function(data){
			$(".tabs-body").html(data);
		}
	);
}
</script>