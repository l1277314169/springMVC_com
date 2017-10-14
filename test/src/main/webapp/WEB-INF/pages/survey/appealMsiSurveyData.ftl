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
<link rel="stylesheet" type="text/css" href="${contextPath}/css/channelPlus-divPopup.css"/>
</head>
<body class="main_body">
	<#assign privCode="C5M2">
		<#include "/base.ftl" />
	<div id="content" >
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
					<#assign key="${msiQuestion.msiQuestionId}"/>
					<div class="msi_suv">
						<input type="hidden" class="question_total" id="${msiQuestion.msiQuestionId}_total" name="${msiQuestion.msiQuestionId}_total" questionType="${questionType}" value="" />
						<span style="font-weight:bold;">Q${(msiQuestion_index)+1}</span><span style="padding-left:5px;font-weight:bold;" id="${msiQuestion.msiQuestionId}_show">${msiQuestion.question}</span>
						<#if (parameterMap[key].complainContent)??>
							<span style="color:red;">申诉状态：<span id="${parameterMap[key].complainId}_status"><#if parameterMap[key].status==1>已申诉<#elseif parameterMap[key].status==2>审批已通过<#elseif parameterMap[key].status==3>审批未通过</#if></span></span>
						</#if>
						<button type="button" class="btn btn-success appealBtn ${msiQuestion.msiQuestionId}" msiQuestionId="${msiQuestion.msiQuestionId}" style="margin-left:10px;"><#if (parameterMap[key].complainContent)??>修改申诉<#else>申诉</#if></button>
						<textarea maxlength="300" id="${msiQuestion.msiQuestionId}" name="${msiQuestion.msiQuestionId}" value="<#if (parameterMap[key].complainContent)??>${parameterMap[key].complainContent}</#if>" placeholder="不超过200个字" style="display:none"><#if (parameterMap[key].complainContent)??>${parameterMap[key].complainContent}</#if></textarea>
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
					<div id="uploader-demo" class="wu-example" style="width:730px;">
					    <#if attachmentList2??>
						    <!--用来存放item-->
						     <br/>
						    <span style="font-weight:bold;">问卷图片：</span>
						    <div class="uploader-list">
						    	<#list attachmentList2 as atts>
						    		<div id="E_WU_FILE_${atts_index}" class="file-item thumbnail" val="${atts.attachment}"  >
						    			<img src="/uploadfiles/${clientId}/web/thumbnail/l_${atts.attachment}" href="/uploadfiles/${clientId}/web/thumbnail/xl_${atts.attachment}" />
						    		</div>
						    	</#list>
						    </div>
						</#if>
						<span style="font-weight:bold;">申诉图片：</span>
					    <input type="hidden" id="imageNames" name="imageNames" />
						 <div id="fileList" class="uploader-list">
					    	<#list attachmentList as atts>
					    		<div id="E_WU_FILE_${atts_index}" class="file-item thumbnail upload-state-done" val="${atts.attachment}"  >
					    			<img src="/uploadfiles/${clientId}/web/thumbnail/l_${atts.attachment}" href="/uploadfiles/${clientId}/web/thumbnail/xl_${atts.attachment}" />
					    			<div class="info_del"></div>
					    		</div>
					    	</#list>
					    </div>
					    <div id="filePicker">选择图片</div>
					</div>
					<div class="msi_button">
						<button id="re_btn" type="button" class="btn btn-danger">返回</button>
						<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</div>
					<#else>
						<div style="text-align:center"><span style="color:red;">该门店没有配置暗访问卷!</span></div>
					</#if>
				</div>
			</form>
			</div>
		</div>
    	<div id="light" class="white_content moveBar">
    	<div id="banner" class="content">问卷申诉</div>  
		     <table class="table_white_bg">
	            <tbody>
	            	<tr>
	            	    <td align="left"> 申诉原因：</td>
	            	</tr>
	           		<tr>
           				<td colspan="3" class="td_control">
	                    	<textarea maxlength="300" id="complainContent" name="complainContent" placeholder="不超过200个字"></textarea>
	                    </td>
	           		</tr>
	            </tbody>
	        </table>
	    	<button id="confrim" class="btn btn-success" msiQuestionId="">确定</button>
        	<button id="cancel" class="btn btn-danger">取消</button>
	    </div>
	    <div id="fade" class="black_overlay"></div>
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

		$(".file-item img").lightBox();
		
		$(".appealBtn").bind("click",function(){
			 var msiQuestionId = $(this).attr("msiQuestionId");
			 $("#confrim").attr("msiQuestionId",msiQuestionId);
			 var id = msiQuestionId;
			 $("#complainContent").attr("value",$("#"+id).val());
			 $("#light").show();
			 $("#fade").show();
		});
		
		$("#confrim").bind("click",function(){
			var id = $("#confrim").attr("msiQuestionId");
			if($("#complainContent").val().length > 200){
				layer.alert("不能超过200个字数");
				return;
			}
			$("#"+id).attr("value",$("#complainContent").val());
			$("#confrim").attr("msiQuestionId","");
			$("#complainContent").attr("value","");
			if($("#"+id).val()!=""){
				$("."+id).html("修改申诉");		
			}
			$("#light").hide();
			$("#fade").hide();
		});
		
		$("#cancel").bind("click",function(){
			 $("#light").hide();
			 $("#fade").hide();
		});
		
		$("#savetButton").bind("click",function(){
			$("#savetButton").attr("disabled","disabled");
			var imageNames = getUploaderVal();
			var imgs = imageNames.split(",");
			if(imageNames!="" && imgs.length<1){
				$("#savetButton").removeAttr("disabled");
				layer.alert("至少上传1张图片");
				return;
			}else if(imageNames == ""){
				$("#savetButton").removeAttr("disabled");
				layer.alert("至少上传1张图片");
				return;
			}
			var feedbackId = $("#feedbackId").val();
			var saveMsiSurveyComplainVo = new Object();
			saveMsiSurveyComplainVo.feedbackId = feedbackId;
			var msiSurveyComplains = new Array();	
			$("#dataForm textarea").each(function(){
				var complainContent = $(this).val();
				if(complainContent!=""){
					var id = $(this).attr("id");
					var msiSurveyComplain = new Object();
					msiSurveyComplain.feedbackId = feedbackId;
					msiSurveyComplain.msiQuestionId = id;
					msiSurveyComplain.complainContent = complainContent;
					msiSurveyComplains.push(msiSurveyComplain);
				}
			});
			saveMsiSurveyComplainVo.msiSurveyComplains = msiSurveyComplains;
			saveMsiSurveyComplainVo.imageNames = imageNames;
			$.ajax({
					url : "${contextPath}/msiSurveyFeedback/saveMsiSurveyComplain",
					type : "post",
					dataType:'JSON',
					contentType : 'application/json',  
					data : JSON.stringify(saveMsiSurveyComplainVo),
					success : function(result) {
						layer.alert(result.message,function(){
							window.location.href="${contextPath}/msiSurveyFeedback/query";
					   });
					}
				});	
		})
		
	});
</script>
</html>