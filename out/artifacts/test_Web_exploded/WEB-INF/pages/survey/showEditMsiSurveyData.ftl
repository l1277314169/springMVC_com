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
							<input type="text" id="feedbackDate" name="feedbackDate" class="input-medium required" <#if (msiSurveyFeedback.feedbackDate)??> value=" ${(msiSurveyFeedback.feedbackDate)?string("yyyy-MM-dd")}"</#if> readonly/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="startTime">开始时间：</label>
						<div class="controls">
							<input type="text" id="startTime" name="startTime" class="input-medium required" <#if (msiSurveyFeedback.startTime)??> value=" ${(msiSurveyFeedback.startTime)?string("yyyy-MM-dd HH:mm:ss")}"</#if> readonly/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="endTime">结束时间：</label>
						<div class="controls">
							<input type="text" id="endTime" name="endTime" class="input-medium required" <#if (msiSurveyFeedback.endTime)??> value=" ${(msiSurveyFeedback.endTime)?string("yyyy-MM-dd HH:mm:ss")}"</#if> readonly/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="visitor">访问员：</label>
						<div class="controls">
							<input type="text" id="visitor" name="visitor" class="input-medium required" value="${msiSurveyFeedback.visitor}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoter">促销员姓名：</label>
						<div class="controls">
							<input type="text" id="promoter" name="promoter" class="input-medium required" value="${msiSurveyFeedback.promoter}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoterNo">促销员工号：</label>
						<div class="controls">
							<input type="text" id="promoterNo" name="promoterNo" class="input-medium required" value="${msiSurveyFeedback.promoterNo}"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoterNo">促销员是否在岗：</label>
						<div class="controls">
							<input type="checkbox" id="onDuty" name="onDuty" class="input-medium" value="1" <#if msiSurveyFeedback.onDuty == '1'>checked="checked"</#if> />
							<input type="checkbox" id="onDuty2" name="onDuty" style="display:none" class="input-medium" value="0"/>
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
						<input type="hidden" class="question_total" id="${msiQuestion.msiQuestionId}_total" name="${msiQuestion.msiQuestionId}_total" questionPoint="${msiQuestion.point}" questionType="${questionType}" value="" />
						<span style="font-weight:bold;">Q${(msiQuestion_index)+1}</span><span style="padding-left:5px;font-weight:bold;" id="${msiQuestion.msiQuestionId}_show">${msiQuestion.question}</span>
						<#if msiQuestion.msiAnswerList??>
							<div class="msi_question">
								<#list msiQuestion.msiAnswerList as msiAnswer>
									<#if questionType =="1">
										<input type="radio" class="question" id="${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="${msiAnswer.msiQuestionId}" value="${msiAnswer.point}" isReset="${msiAnswer.isReset}" <#if msiAnswer.checked == "1">checked="checked"</#if>/>
										<input type="hidden" class="parameters" name="msiSurveyFeedbackDetails[${tCount}].msiQuestionId" value="${msiQuestion.msiQuestionId}"/>
										<input type="hidden" class="parameters" name="msiSurveyFeedbackDetails[${tCount}].msiAnswerId" value="${msiAnswer.msiAnswerId}"/>
										<input type="hidden" class="parameters" id="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="msiSurveyFeedbackDetails[${tCount}].checked" value="<#if msiAnswer.checked == '1'>true</#if>"/>
									<#elseif questionType =="2">
										<input type="checkbox" class="question" id="${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="${msiAnswer.msiQuestionId}" value="${msiAnswer.point}" isReset="${msiAnswer.isReset}" <#if msiAnswer.checked == "1">checked="checked"</#if>/>
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
					    		<div id="E_WU_FILE_${atts_index}" class="file-item thumbnail upload-state-done" val="${atts.attachment}">
					    			<img href="/uploadfiles/${clientId}/web/thumbnail/xl_${atts.attachment}" src="/uploadfiles/${clientId}/web/thumbnail/l_${atts.attachment}" />
					    		<div class="info_del"></div></div>
					    	</#list>
					    </div>
					    <div id="filePicker">选择图片</div>
					</div>

					<div class="msi_button">
						<button id="re_btn" type="button" class="btn btn-danger">返回</button>
						<button id="savetButton" type="button" class="btn btn-success margin-left-18px">更新</button>
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
</html>
<script type="text/javascript">
	$(document).ready(function () {
	
		//初始化不在岗时,禁用掉后面的选项
		if(!$('#onDuty').prop("checked")){
			$(".msi_suv input:checkbox").each(function(){
				$(this).attr("checked",false);
				$(this).attr("disabled","disabled");
			});
			$(".msi_suv input:radio").each(function(){
				$(this).attr("checked",false);
				$(this).attr("disabled","disabled");
			});
		}else{
			$(".msi_suv input:checkbox").each(function(){
				$(this).removeAttr("disabled");
			});
			$(".msi_suv input:radio").each(function(){
				$(this).removeAttr("disabled");
			});
		}
	
		var idsContainer = $(".question_total");
		var len = idsContainer.length; 
		var isTrue = true;
		var isFalse = false;
		
		//初始化每个问题的得分
		initQuestionTotla();
		
		$(".btn-danger").live("click",function(){
			var url = "${contextPath}/msiSurveyFeedback/query";
			window.location.href = url;
		});
		
		$("#onDuty").bind("click",function(){
			if(!$('#onDuty').prop("checked")){
				$(".msi_suv input:checkbox").each(function(){
					$(this).attr("checked",false);
					$(this).attr("disabled","disabled");
				});
				$(".msi_suv input:radio").each(function(){
					$(this).attr("checked",false);
					$(this).attr("disabled","disabled");
				});
			}else{
				$(".msi_suv input:checkbox").each(function(){
					$(this).removeAttr("disabled");
				});
				$(".msi_suv input:radio").each(function(){
					$(this).removeAttr("disabled");
				});
			}
		});
		
		//新增
		$("#savetButton").bind("click",function(){
			var dataFlag = true;
			$(this).attr("disabled","disabled");
			//验证
			$(".required").each(function(){
				var id = $(this).attr("id");			
				var value = $("#"+id).val();									
				if(value==''){
					dataFlag = false;
					$(this).blur();
					$("#savetButton").removeAttr("disabled");
					layer.tips('不能为空。', '#'+id);
					//layer.tips('有选项为空，请填写。', '#savetButton');
					//$("html,body").animate({scrollTop:$("#"+id).offset().top},800);
					return;
				}
			});
			var onDutyIsChecked = $('#onDuty').prop("checked"); 
			if(!onDutyIsChecked){
				$("#onDuty2").attr("checked","checked");
			}
			$(".question_total").each(function(){
				var id = $(this).attr("id");
				var type = $("#"+id).attr("questionType");
			    var arr = id.split('_');
				var qId = arr[0];
				var sId = qId+"_show";
				if(type == 1){
					var val = $("input:radio[name='"+qId+"']:checked").val();
					if(val==null && onDutyIsChecked){
						dataFlag = false;
						$(this).blur();
						$("#savetButton").removeAttr("disabled");
						layer.tips('不能为空。', '#'+sId);
		                return;
		            }
				} 
				if(type == 2) {
					var isChecked = false;
					$("input[name='"+qId+"']:checked").each(function(){ 
						if($(this).attr("checked")){
							isChecked = true;
						}
		            });
					if(! isChecked  && onDutyIsChecked){
						dataFlag = false;
						$(this).blur();
						$("#savetButton").removeAttr("disabled");
						layer.tips('不能为空。', '#'+sId);
						return;
					}
				}
			});
			
			if(dataFlag){
				var imageName = getUploaderVal();
				var imgs = imageName.split(",");
				if(imgs.length<3){
					$("#savetButton").removeAttr("disabled");
					layer.alert("至少上传3张图片");
					return;
				}
				$("#imageNames").val(imageName);
				$("body").showLoading();
				$.ajax({
					url : "${contextPath}/msiSurveyFeedback/updateMsiSurveyData",
					type : "post",
					dataType:"json",
					async: false,
					data : $("#dataForm").serialize(),
					success : function(result) {
						$("body").hideLoading();
						if(result.code=="success"){
				    		layer.alert(result.message,function(){
								window.location.href="${contextPath}/msiSurveyFeedback/query";
						   });
						}else {
							layer.alert(result.message);
							$("#savetButton").removeAttr("disabled");
				   		}
				   	},
				   	error: function(result) {
				   		$("body").hideLoading();
						$("#savetButton").removeAttr("disabled");
					}
				});
			} else {
				$("#savetButton").removeAttr("disabled");
				layer.msg('数据验证失败');
			}
			
		});
		
		$("#re_btn").bind("click",function(){
			var url = "${contextPath}/msiSurveyFeedback/query";
			window.location.href=url;
		});
		
		$('input:checkbox').click(function () {
			//获取定义内容
			var id = this.id;
			var name = this.name;
			var idT = name+"_total";
			var idV = "checked_"+name;
			var idP = "checked_"+id;
			var p = 0;
			//alert("id:"+id+";name:"+name+";idP:"+idP+";idV:"+idV);
			
			//获取总分数
			var point = Number($("#point").val());
			
			//设置选中的为true
			if($("#"+id).attr("checked")){
				$("#"+idP).val(isTrue);
			}else {
				$("#"+idP).val(isFalse);
			}
			
			//计算单个问题失分
			var number = 0;
			$("input[name='"+name+"']:checked").each(function(){ 
				if($(this).attr("checked")){
					var isReset = $(this).attr("isReset");
	            	if(isReset ==1){
	            		p = -point;
	            		return false;
	            	}else{
	            		number += Number($(this).val());
	            		var questionPoint = Math.abs(Number($("#"+idT).attr("questionPoint")));
	            		if((Math.abs(number))>questionPoint){
	            			p = Number($("#"+idT).attr("questionPoint"));
	            		}else{
							p += number;
	            		} 
	                }
				}
            });
			//设定单个问题失分
			$("#"+idT).val(p);
			
			//重新计算总得分-循环所有问题计算
			countScore();
		});
		
		$("input:radio").click(function () {
			//获取定义内容
			var id = this.id;
			var name = this.name;
			var idT = name+"_total";
			var idV = "checked_"+name;
			var idP = "checked_"+id;
			var p = 0;
			//alert("id:"+id+";name:"+name+";idP:"+idP+";idV:"+idV);
			//获取总分数
			var point = Number($("#point").val());
			
			//先设置所有为false，再设置选中的为true
			if($("#"+id).attr("checked")){
				$("input[id^='"+idV+"']").val(isFalse);
				$("#"+idP).val(isTrue);
				//计算单个问题失分
				var isReset = $("#"+id).attr("isReset");
				if(isReset ==1){
            		p = -point;
            	} else {
                	p = Number($("#"+id).val());
                }
			}
			
			//设定单个问题失分
			$("#"+idT).val(p);
			//重新计算总得分-循环所有问题计算
			countScore();
		});
		
		function countScore(){
			//获取初始分
			var score = Number($("#initialScore").val());
			for(var index = 0; index < len; index++){  
			    var $container = $(idsContainer[index]);  
			    var tmp  = Number($container.val());
			    score = score+tmp;
			}
			if(score<0){score= 0;}
			$(".control-point").html(score);
			$("#score").val(score);
		}
		
		$("#feedbackDate").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			minDate:"${msiSurvey.startDate?date}",
			maxDate:"${msiSurvey.endDate?date}",
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
			}
		});
		
		/*$('#startTime').datetimepicker({
			 timeFormat: 'HH:mm:ss'
		});
		$('#endTime').datetimepicker({
			timeFormat: 'HH:mm:ss',
			minDate:"${msiSurvey.startDate?date}",
			maxDate:"${msiSurvey.endDate?date}"
		});*/

		$('#startTime').datetimepicker({
			dateFormat: 'yy-mm-dd', 
			timeFormat: 'HH:mm:ss',
			showButtonPanel :false,
			minDate:"${msiSurvey.startDate?date}",
			maxDate:"${msiSurvey.endDate?date}",
			onSelect: function (selectedDateTime){
				if(!compareDate()){
					$(this).val("");
				}
			}
		});
		
		$('#endTime').datetimepicker({
			dateFormat: 'yy-mm-dd', 
			timeFormat: 'HH:mm:ss',
			showButtonPanel :false,
			minDate:"${msiSurvey.startDate?date}",
			maxDate:"${msiSurvey.endDate?date}",
			onSelect: function (selectedDateTime){
				var startTime = getStartTime();
				if(null==startTime || ''==startTime){
					layer.alert("请先选择开始时间");
					$(this).val("");
				}else{
					if(!compareDate()){
						$(this).val("");
					}
				}		
			}
		});

		function getStartTime(){
			var date = $('#startTime').datetimepicker('getDate');
			return date;
		}

		function getEndTime(){
			var date = $('#endTime').datetimepicker('getDate');
			return date;
		}

		function compareDate(){
			var begin = new Date($("#startTime").val().replace(/-/g,"/"));
	      	var end= new Date($("#endTime").val().replace(/-/g,"/"));
	      	if(begin-end>0){
	         	layer.alert("开始时间要在结束时间之前");  
	         	return false;
	      	}else{
	      		return true;	
	      	}	      	
		}
		
		
		function initQuestionTotla(){
			$(".question_total").each(function(){
				var id = $(this).attr("id");
				var type = $("#"+id).attr("questionType");
			    var arr = id.split('_');
				var qId = arr[0];
				//得到总分：
				var point = Number($("#point").val());
				//alert("type::"+type);
				if(type == 1){
					var p = 0;		            
		            $("input[name='"+qId+"']").each(function(){ 
						if($(this).attr("checked")){
							var isReset = $(this).attr("isReset");
			            	if(isReset ==1){
			            		p = -point;
			            		return;
			            	} else {
			                	p = Number($(this).val());
			                }
						}
		            });
		            $("#"+id).val(p);
		            //alert(id+"value:"+$("#"+id).val());
				} 
				if(type == 2) {
					var p = 0;		            
		            $("input[name='"+qId+"']").each(function(){ 
						if($(this).attr("checked")){
							var isReset = $(this).attr("isReset");
			            	if(isReset ==1){
			            		p = -point;
			            		return;
			            	} else {
			                	p += Number($(this).val());
			                }
						}
		            });
		            $("#"+id).val(p);
				}
			});
		}


		//ready
		$(".upload-state-done img").lightBox();

	});
</script>