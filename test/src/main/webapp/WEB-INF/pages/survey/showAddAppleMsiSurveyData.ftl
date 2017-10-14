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
	<#assign privCode="C5M3">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/appleMsiSurvey/query">问卷二期</a>
					<a class="linkPage active" href="${contextPath}/appleMsiSurvey/queryStore"> 添加问卷</a>
				</div>
					</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="dataForm" method="post">
				<input type="hidden" id="initialScore" name="initialScore" value="${msiSurvey.initialScore!''}"/>
				<input type="hidden" id="msiSurveyId" name="msiSurveyId" value="${msiSurveyId!''}"/>
				<input type="hidden" id="clientUserId" name="clientUserId" value="${clientUserId!''}"/>
				<input type="hidden" id="clientId" name="clientId" value="${clientId!''}"/>
				<input type="hidden" id="storeId" name="storeId" value="${storeId!''}"/>
				<input type="hidden" id="score" name="score" value=""/>
				<!--<input type="hidden" name="page" value="${page}">-->
				<div class="control-group">
					<div nowrap="true" class="fl" title="${storeNo!''}">
						<label class="control-label">门店编号：</label>
						<label class="control-label" style="margin-left:-50px"><#if storeNo?length gt 15>${storeNo?substring(0,15)}…<#else>${storeNo}</#if></label>
					</div>
					<div nowrap="true" class="fl" title="${storeName!''}">
						<label class="control-label">门店名称：</label>
						<label class="control-read"><#if storeName?length gt 15>${storeName?substring(0,15)}…<#else>${storeName}</#if></label>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="feedbackDate">调研日期：</label>
						<div class="controls">
							<input type="text" id="feedbackDate" name="feedbackDate" class="input-medium required" value="" readonly/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="visitor">访问员：</label>
						<div class="controls">
							<input type="text" id="visitor" name="visitor" class="input-medium required" value=""/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="startTime">开始时间：</label>
						<div class="controls">
							<input type="text" id="startTime" name="startTime" class="input-medium required" value="" readonly/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="endTime">结束时间：</label>
						<div class="controls">
							<input type="text" id="endTime" name="endTime" class="input-medium required" value="" readonly/>
						</div>
					</div>
				</div>
				<div class="">
					<#if msiQuestionGroups ??>
					<#assign tCount = 0/>
					<#list msiQuestionGroups as msiQuestionGroup>
					
						<table class="table table-bordered data-table" id="survey_table" style="width: auto;height: auto;margin-bottom: 10px;" >
							<thead>
								<th style="background:#777;color: #fff;width: 100%;" colspan="2">
									${msiQuestionGroup.groupName}
								</th>
							</thead>
							<tbody>
								<#list msiQuestionGroup.childrenList as childrenMsiQuestionGroup>
									<tr>
										<td style="width:150px">${childrenMsiQuestionGroup.groupName}</td>
										<td class="group" id="${childrenMsiQuestionGroup.questionGroupId}">
											<#if childrenMsiQuestionGroup.msiQuestionVos ??>
												<#list childrenMsiQuestionGroup.msiQuestionVos as msiQuestion>
													<#assign questionType = msiQuestion.questionType/>
													<div class="msi_suv <#if questionType==1 || questionType==2>parent</#if>" questionId="${msiQuestion.msiQuestionId}">
														<input type="hidden" class="question_total" id="${msiQuestion.msiQuestionId}_total" questionType="${questionType}" value=""/>
														<span style="font-weight:bold;">${msiQuestion.questionNo}</span><span style="padding-left:5px;font-weight:bold;" id="${msiQuestion.msiQuestionId}_show">${msiQuestion.question}</span>
														<#if msiQuestion.msiAnswerList??>
															<div class="msi_question">
																<#list msiQuestion.msiAnswerList as msiAnswer>
																	<#if questionType =="1">
																		<input type="radio" answerNo="${childrenMsiQuestionGroup.questionGroupId}_${msiQuestion.msiQuestionId}_${msiAnswer.answerNo}" groupAnswer="group_${msiAnswer.msiAnswerId}" class="question <#if msiAnswer.msiAnswerId==273||msiAnswer.msiAnswerId==274>corpOrManager<#elseif msiAnswer.msiAnswerId==275>noCorpOrManager</#if><#if msiAnswer.msiAnswerId==539>hasPspFlag<#elseif msiAnswer.msiAnswerId==540>noHasPspFlag</#if>" id="${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="${msiAnswer.msiQuestionId}" value="${msiAnswer.point}" isReset="${msiAnswer.isReset}"/>
																		<input type="hidden" class="parameters" id="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" value="<#if msiAnswer.checked == '1'>true</#if>"/>
																	<#elseif questionType =="2">
																		<input type="checkbox" class="question" id="${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="${msiAnswer.msiQuestionId}" value="${msiAnswer.point}" isReset="${msiAnswer.isReset}" />
																		<input type="hidden" class="parameters" id="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" name="checked_${msiAnswer.msiQuestionId}_${msiAnswer.msiAnswerId}" />
																	</#if>
																	<span>${msiAnswer.answerNo}</span><span style="padding-left:5px;">${msiAnswer.answer}</span></br>
																</#list>
															</div>
														</#if>
														<#if questionType =="3">
															<input type="text" <#if msiQuestion.isEditable==0>readonly="readonly"</#if> class="parameters <#if msiQuestion.msiQuestionId!=309 && msiQuestion.msiQuestionId!=308>required</#if> <#if msiQuestion.msiQuestionId==140>intType</#if>" id="${msiQuestion.msiQuestionId}_col1" name="${msiQuestion.msiQuestionId}_col1">
														</#if>
													</div>
													
													<#if msiQuestion.childrenList??>
														<#list msiQuestion.childrenList as childrenMsiQuestion>
															<#assign childrenQuestionType = childrenMsiQuestion.questionType/>
															<div class="msi_suv" style="max-width:1000px;overflow:auto;width:auto;">
																<#if childrenQuestionType=="3">
																	<span style="font-weight:bold;">${childrenMsiQuestion.questionNo}</span><span style="padding-left:5px;font-weight:bold;" id="${childrenMsiQuestion.msiQuestionId}_show">${childrenMsiQuestion.question}</span>
																	<input type="text" <#if childrenMsiQuestion.isEditable==0>readonly="readonly"</#if> class="parameters <#if childrenMsiQuestion.msiQuestionId==226 || childrenMsiQuestion.msiQuestionId==227>corpOrManagerText<#elseif childrenMsiQuestion.msiQuestionId==228>noCorpOrManagerText</#if>" id="${childrenMsiQuestion.msiQuestionId}_col1" name="${childrenMsiQuestion.msiQuestionId}_col1" >
																<#elseif childrenQuestionType=="4">
																	<table class="table table-bordered data-table" style="width:500px" id="c_list">
																		<tr>
																			<th class="fill_td">
																			</th>
																			<#list childrenMsiQuestion.msiAnswerList as msiAnswer>
																				<#if msiAnswer_index ==0>
																					<#list msiAnswer.msiSurveyParameters as msiSurveyParameter>
																			      		<th class="fill_td">${msiSurveyParameter.parameterName}</th>
																		      		</#list>	
																				</#if>
																			</#list>
																		</tr>
																		<tbody>
																			<#list childrenMsiQuestion.msiAnswerList as msiAnswer>
																				<tr>
																					<td class="fill_td">${msiAnswer.answer}</td>
																					<#list msiAnswer.msiSurveyParameters as msiSurveyParameter>
																						<td class="td_control_d fill_td">
																							<#assign key="${msiAnswer.msiAnswerId}_${msiSurveyParameter.msiSurveyParameterId}"/>
																							<#if msiSurveyParameter.controlType=='1'>
																								<input type="text" id="${childrenMsiQuestion.msiQuestionId}_${msiAnswer.msiAnswerId}_${msiSurveyParameter.msiSurveyParameterId}" name="${childrenMsiQuestion.msiQuestionId}_${msiAnswer.msiAnswerId}_${msiSurveyParameter.msiSurveyParameterId}"  <#if msiAnswer.msiSurveyParameterDataMap?exists>value="${msiAnswer.msiSurveyParameterDataMap[key]}"</#if> >
																							</#if>
																						</td>
																					</#list>
																				</tr>
																			</#list>
																		</tbody>
																	</table>
																</#if>
															</div>
														</#list>
													</#if>
													
													<#assign objectId="img_${msiQuestion.msiQuestionId}">
													<div id="uploader-demo" class="wu-example" style="width:800px;">
													    <input type="hidden" id="${objectId}" name="${objectId}" />
													    <div id="fileList_${objectId}" class="uploader-list"></div>
													    <div id="filePicker_${objectId}"  style="margin-top:30px;">选择图片</div>
													</div>	
													<#include "/survey/survey_uploader.ftl" />
													
												</#list>
											</#if>
										</td>
									</tr>
								</#list>
							</tbody>
						</table>
					</#list>

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
		</div>
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	$(document).ready(function () {
	
		//苹果2逻辑控制
		$(".corpOrManager").each(function(){
			$(this).bind("click",function(){
				$(".noCorpOrManagerText").each(function(){
					$(this).val("");
					$(this).attr("readonly","readonly");
				});
				$(".corpOrManagerText").each(function(){
					$(this).removeAttr("readonly");
				});
			});
			if($(this).attr("checked")){
				$(".noCorpOrManagerText").each(function(){
					$(this).attr("readonly","readonly");
				});
				$(".corpOrManagerText").each(function(){
					$(this).removeAttr("readonly");
				});	
			}
		});
		
		$(".noCorpOrManager").each(function(){
			$(this).bind("click",function(){
				$(".noCorpOrManagerText").each(function(){
					$(this).removeAttr("readonly");
				});
				$(".corpOrManagerText").each(function(){
					$(this).val("");
					$(this).attr("readonly","readonly");
				});
			});
			$(".noCorpOrManagerText").each(function(){
				$(this).removeAttr("readonly");
			});
			$(".corpOrManagerText").each(function(){
				$(this).attr("readonly","readonly");
			});	 
		});
		
		$(".hasPspFlag").bind("click",function(){
			$("#303_col1").val("");
			$("#303_col1").attr("readonly","readonly");
		});
		
		$(".noHasPspFlag").bind("click",function(){
			$("#303_col1").removeAttr("readonly");	
		});
		
		var idsContainer = $(".question_total");
		var len = idsContainer.length; 
		var isTrue = true;
		var isFalse = false;
		
		$(".btn-danger").live("click",function(){
			var url = "${contextPath}/msiSurveyFeedback/query";
			window.location.href = url;
		});
		
		//新增
		$("#savetButton").bind("click",function(){
			layer.closeAll();
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
					layer.tips('不能为空。','#'+id,{
					    tips: [2, '#3595CC'],
					    time: 40000,
					    tipsMore: true
					});
					//layer.tips('有选项为空，请填写。', '#savetButton');
					//$("html,body").animate({scrollTop:$("#"+id).offset().top},800);
					return;
				}
			});
	 		
	 		$(".question_total").each(function(){
				var id = $(this).attr("id");
				var type = $("#"+id).attr("questionType");
			    var arr = id.split('_');
				var qId = arr[0];
				var sId = qId+"_show";
				if(type ==1){
					var val = $("input:radio[name='"+qId+"']:checked").val();
					if(val == "" || val== undefined || val==null){
						dataFlag = false;
						$(this).blur();
						$("#savetButton").removeAttr("disabled");
						layer.tips('不能为空。',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
		                return;
		            }
				} 
				if(type ==2) {
					var isChecked = false;
					$("input[name='"+qId+"']:checked").each(function(){ 
						if($(this).attr("checked")){
							isChecked = true;
							return;
						}
					});
					if(! isChecked){
						dataFlag = false;
						$(this).blur();
						$("#savetButton").removeAttr("disabled");
						layer.tips('不能为空。',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
						return;
					}
				}
			 
			});
			
			$(".intType").each(function(){			
				var id = $(this).attr("id");
			 	var arr = id.split('_');
				var qId = arr[0];
				var sId = qId+"_show";
				var value = $("#"+id).val();
				if(!isNaN(value)){
					if(parseInt(value)!=value && value!=""){
						dataFlag = false;
						layer.tips('必须为整数。',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
				 		return;
					}
				}else{
					dataFlag = false;	
					layer.tips('必须为数字。',"#"+sId,{
					    tips: [2, '#3595CC'],
					    time: 40000,
					    tipsMore: true
					});
	 				return;
				}
			});
	 		
			setUploaderVal();
			
			$(".uploader-list").each(function(){
				var id = $(this).prev().attr("id");
				var arr = id.split('_');
				var qId = arr[1];
				var sId = qId+"_show";
				var imageStr = $("#"+id).val();
				var images = imageStr.split(',');
				if(qId == 133 || qId == 148 || qId == 186 || qId == 190 || qId == 217){
					if(images.length>10 || imageStr==""){
						dataFlag = false;	
						layer.tips('至少上传1张图片，最多上传10张',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
		 				return;	
					}
				}else if(qId == 180){
					if(images.length>30 || imageStr==""){
						dataFlag = false;	
						layer.tips('至少上传1张图片，最多上传30张',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
		 				return;	
					}
				}else if(qId == 132 || qId == 308){
					if(images.length>20 || imageStr==""){
						dataFlag = false;	
						layer.tips('至少上传1张图片，最多上传20张',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
		 				return;	
					}
				}else{
					if(images.length>5 || imageStr==""){
						dataFlag = false;	
						layer.tips('至少上传1张图片，最多上传5张',"#"+sId,{
						    tips: [2, '#3595CC'],
						    time: 40000,
						    tipsMore: true
						});
		 				return;	
					}
				}
			});
			
			if(dataFlag){
				judgeScore();    			//打分
				$("body").showLoading();
				$.ajax({
					url : "${contextPath}/appleMsiSurvey/addMsiSurveyData",
					type : "post",
					dataType:"json",
					async: false,
					data : $("#dataForm").serialize(),
					success : function(result) {
						$("body").hideLoading();
						if(result.code=="success"){
							layer.alert('问卷填写完成',function(){
								window.location.href="${contextPath}/appleMsiSurvey/query";
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
			var url = "${contextPath}/appleMsiSurvey/queryStore";
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
			var score = Number($("#initialScore").val());
			
			//设置选中的为true
			if($("#"+id).attr("checked")){
				$("#"+idP).val(isTrue);
			}else {
				$("#"+idP).val(isFalse);
			}
			
			//计算单个问题失分
			$("input[name='"+name+"']:checked").each(function(){ 
				if($(this).attr("checked")){
					var isReset = $(this).attr("isReset");
	            	if(isReset ==1){
	            		p = -score;
	            		return false;
	            	} else {
	                	p += Number($(this).val());
	                }
				}
            });
			
			//设定单个问题失分
			$("#"+idT).val(p);
			
			//重新计算总得分-循环所有问题计算
			//countScore();
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
			var score = Number($("#initialScore").val());
			
			//先设置所有为false，再设置选中的为true
			if($("#"+id).attr("checked")){
				$("input[id^='"+idV+"']").val(isFalse);
				$("#"+idP).val(isTrue);
				//计算单个问题失分
				var isReset = $("#"+id).attr("isReset");
				if(isReset ==1){
            		p = -score;
            	} else {
                	p = Number($("#"+id).val());
                }
			}
			
			//设定单个问题失分
			$("#"+idT).val(p);
				
			//重新计算总得分-循环所有问题计算
			//countScore();
		});
		
		function countScore(){
			//获取总分数
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
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
			}
		});


		$('#startTime').datetimepicker({
			dateFormat: 'yy-mm-dd', 
			timeFormat: 'HH:mm:ss',
			showButtonPanel :false,
			minDate:"${msiSurvey.startDate?date}",
			onSelect: function (selectedDateTime){
				var endDate = getEndTime();
				if(null!=endDate && ''!=endDate){
					if(!compareDate()){
						$(this).val("");
					}
				}
			}
		});
		
		$('#endTime').datetimepicker({
			dateFormat: 'yy-mm-dd', 
			timeFormat: 'HH:mm:ss',
			showButtonPanel :false,
			minDate:"${msiSurvey.startDate?date}",
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

	});

function setUploaderVal(){
	var uploaderList = $(".uploader-list");
	$.each(uploaderList, function(index, obj) {
		 var fid = $(obj).attr("id");
		 var id = fid.replace("fileList_",'');
		 var vals = getValues(obj);
		 $("#"+id).val(vals);
	});  
}

function getValues(obj){
	var uploadobj = $(obj).children(".upload-state-done");
	var value = '';
    $.each(uploadobj, function(index, val) {
        var v = $(val).attr("val");
        if(index==0){
             value+=v;
        }else{
            value+=(","+v);
        }
    });
    var idx = value.indexOf(",");
    if(idx==0){
        value = value.substring(1);
    }
    return value;
}

function judgeScore(){
	var groupCount = 35;    //总共35个分组，每个分组各一分
	var correctGroupCount = 0;
	$(".group").each(function(){
		var id = this.id;    			//groupId
		var answerNo = this.answerNo;
		var groupAnswer = this.groupAnswer;
		var count = $("#"+id+"> div[class='msi_suv parent']").length;
		var correctCount = 0;
		if(id != 41 && id != 2 && id !=42){
			$.each($("#"+id+"> div[class='msi_suv parent']"), function(index, val){
		        var questionId = $(val).attr("questionId");
		        var answerno =id+"_"+questionId+"_a)";
		        if($("input[answerno='"+answerno+"']").attr("checked")){
		        	correctCount++;
		        }
		    });
		    if(count==correctCount){
		    	correctGroupCount++
		    }
		}else if(id==2){
			$.each($("#"+id+"> div[class='msi_suv parent']"), function(index, val){
		        var questionId = $(val).attr("questionId");
		        var answerno =id+"_"+questionId+"_a)";
		        var answernoB =id+"_"+questionId+"_b)";
		        var answernoC =id+"_"+questionId+"_c)";
		        if($("input[answerno='"+answerno+"']").attr("checked")){
		        	correctCount++;
		        }
	         	if($("input[answerno='"+answernoB+"']").attr("checked")){
		        	correctCount++;
		        }
	            if($("input[answerno='"+answernoC+"']").attr("checked")){
		        	correctCount++;
		        }
		    });
		    if(count==correctCount){
		    	correctGroupCount++
		    }	
		}
	});
	var score = parseInt(correctGroupCount/groupCount*100);
	$("#score").val(score);
}
</script>