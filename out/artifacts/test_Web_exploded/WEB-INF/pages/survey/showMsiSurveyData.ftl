<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css"  media="screen">
<#include "/common/head.ftl" />
<title>暗访问卷数据列表</title>
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/msi_survey_uploader.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
</head>
<body class="main_body">
	<#assign privCode="C5M2">
		<#include "/base.ftl" />
	<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">问卷管理</a>
					<a class="linkPage active" href="${contextPath}/message/query">暗访管理</a>
				</div>
		 	</div>
	 	<div id="div_step_detail" class="div_step_detail" style="width: 100%;height:auto;border:none;background: #fff;margin-left:0px;">
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="dataForm" method="post">
				<input type="hidden" id="initialScore" name="initialScore" value="${msiSurveyFeedback.initialScore!''}"/>
				<input type="hidden" id="feedbackId" name="feedbackId" value="${msiSurveyFeedback.feedbackId!''}"/>
				<input type="hidden" id="msiSurveyId" name="msiSurveyId" value="${msiSurveyFeedback.msiSurveyId!''}"/>
				<input type="hidden" id="clientUserId" name="clientUserId" value="${clientUserId!''}"/>
				<input type="hidden" id="clientId" name="clientId" value="${clientId!''}"/>
				<input type="hidden" id="storeId" name="storeId" value="${storeId!''}"/>
				<input type="hidden" id="score" name="score" value=""/>
				<input type="hidden" id="point" name="point" value="${msiSurveyFeedback.point!''}"/>
				

				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label">门店编号：</label>
						<label class="control-label">${storeNo}</label>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label">门店名称：</label>
						<label class="control-read"><#if storeName?length gt 15>${storeName?substring(0,15)}…<#else>${storeName}</#if></label>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label">分数：</label>
						<label class="control-point">${msiSurveyFeedback.score!''}</label>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="feedbackDate">调研日期：</label>
						<div class="controls">
							${(msiSurveyFeedback.feedbackDate)?string("yyyy-MM-dd")}
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="startTime">开始时间：</label>
						<div class="controls">
							${(msiSurveyFeedback.startTime)?string("yyyy-MM-dd HH:mm:ss")}
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="endTime">结束时间：</label>
						<div class="controls">
							${(msiSurveyFeedback.endTime)?string("yyyy-MM-dd HH:mm:ss")}
						</div>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="visitor">访问员：</label>
						<div class="controls">
							${msiSurveyFeedback.visitor}
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoter">促销员姓名：</label>
						<div class="controls">
							${msiSurveyFeedback.promoter}
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoterNo">促销员工号：</label>
						<div class="controls">
							${msiSurveyFeedback.promoterNo}
						</div>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoterNo">促销员是否在岗：</label>
						<div class="controls">
							<input type="checkbox" disabled="disabled" id="onDuty" name="onDuty" class="input-medium" value="1" <#if msiSurveyFeedback.onDuty == '1'>checked="checked"</#if> />
						</div>
					</div>
				</div>
				<!--<input type="hidden" name="page" value="${page}">-->
				<div class="">
					<#if msiQuestionVOList ??>
					<#assign tCount = 0/>
					<#list msiQuestionVOList as msiQuestion>
					<#assign questionType = msiQuestion.questionType/>
					<div class="msi_suv">
						<input type="hidden" class="question_total" id="${msiQuestion.msiQuestionId}_total" name="${msiQuestion.msiQuestionId}_total" questionType="${questionType}" value="" />
						<span style="font-weight:bold;">Q${(msiQuestion_index)+1}</span><span style="padding-left:5px;font-weight:bold;" id="${msiQuestion.msiQuestionId}_show">${msiQuestion.question}</span>
						<#if msiQuestion.msiAnswerList??>
							<div class="msi_question">
								<#list msiQuestion.msiAnswerList as msiAnswer>
									<#if questionType =="1">
										<input type="radio" disabled="disabled" class="question" id="${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="${msiAnswer.msiQuestionId}" value="${msiAnswer.point}" isReset="${msiAnswer.isReset}" <#if msiAnswer.checked == "1">checked="checked"</#if>/>
										<input type="hidden" class="parameters" name="msiSurveyFeedbackDetails[${tCount}].msiQuestionId" value="${msiQuestion.msiQuestionId}"/>
										<input type="hidden" class="parameters" name="msiSurveyFeedbackDetails[${tCount}].msiAnswerId" value="${msiAnswer.msiAnswerId}"/>
										<input type="hidden" class="parameters" id="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="msiSurveyFeedbackDetails[${tCount}].checked" value="<#if msiAnswer.checked == '1'>true</#if>"/>
									<#elseif questionType =="2">
										<input type="checkbox" disabled="disabled" class="question" id="${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="${msiAnswer.msiQuestionId}" value="${msiAnswer.point}" isReset="${msiAnswer.isReset}" <#if msiAnswer.checked == "1">checked="checked"</#if>/>
										<input type="hidden" class="parameters" name="msiSurveyFeedbackDetails[${tCount}].msiQuestionId" value="${msiQuestion.msiQuestionId}"/>
										<input type="hidden" class="parameters" name="msiSurveyFeedbackDetails[${tCount}].msiAnswerId" value="${msiAnswer.msiAnswerId}"/>
										<input type="hidden" class="parameters" id="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="msiSurveyFeedbackDetails[${tCount}].checked" value="<#if msiAnswer.checked == '1'>true</#if>"/>
									</#if>
									<span>${msiAnswer.answerNo}</span><span style="padding-left:5px;">${msiAnswer.answer}</span></br>
									<#assign tCount = tCount+1/>
								</#list>
							</div>
						</#if>
					</div>
					</#list>
					
					<!--dom结构部分-->
					<span class="img_title">问卷图片：</span>
					<div id="uploader-demo" class="wu-example" style="width:730px;">
					    <!--用来存放item-->
					    <input type="hidden" id="imageNames" name="imageNames" />
					    <div id="fileList" class="uploader-list">
					    	<#list attachmentList as atts>
					    		<div href="/uploadfiles/${clientId}/web/thumbnail/xl_${atts.attachment}" id="E_WU_FILE_${atts_index}" class="file-item thumbnail" val="${atts.attachment}">
					    			<img src="/uploadfiles/${clientId}/web/thumbnail/l_${atts.attachment}" />
					    		</div>
					    	</#list>
					    </div>
					</div>

					<div class="msi_button">
						<button id="re_btn" type="button" class="btn btn-danger">返回</button>
					</div>
					<#else>
						<div style="text-align:center"><span style="color:red;">该门店没有配置暗访问卷!</span></div>
					</#if>
				</div>
			</form>
			</div>
			<div id="tabs-body" class="div_data_detail" style="width: 100%;height: 100%;overflow:visible;background: #fff;"></div>
		</div>
		</div>
	<#include "/footer.ftl" />
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#re_btn").bind("click",function(){
			var url = "${contextPath}/msiSurveyFeedback/query";
			window.location.href=url;
		});

		$(".file-item").lightBox();
	});
</script>
</html>