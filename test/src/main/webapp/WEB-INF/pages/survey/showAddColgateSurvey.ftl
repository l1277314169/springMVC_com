<html>
<head>
<title>问卷</title>
<#include "/common/taglibs.ftl" />
</head>
<#if blockId==3><!-- 店内调查问卷 -->
	<script type="text/javascript" src="${contextPath}/js/survey/survey_3.js?cVer=${cVer}"></script>
<#elseif blockId==5>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_5.js?cVer=${cVer}"></script>
<#elseif blockId==11>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_11.js?cVer=${cVer}"></script>
<#elseif blockId==12>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_12.js?cVer=${cVer}"></script>
<#elseif blockId==13>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_13.js?cVer=${cVer}"></script>
<#else>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_base.js?cVer=${cVer}"></script>
</#if>
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<body>
	<div>
		<div class="widget-content nopadding" style="text-align: center;margin: 0px;overflow:auto;width:auto;">
			<form id="frm" name="frm" method="POST" action="${contextPath}/survey/doAddSurvey" enctype="multipart/form-data" >

			<input type="hidden" name="surveyId" value="${surveyId}" />
			<input type="hidden" id="feedbackId" name="feedbackId" />
			<input type="hidden" id="clientId" name="clientId" value="${clientId}" />
			<input type="hidden" id="blockId" name="blockId" value="${blockId}" >
			<input type="hidden" id="_blockId" name="_blockId" value="${blockId}" >

			<#list vos as vs>
			<input type="hidden" name="surveySubId" value="${vs.surveySubId}" />
			<table class="table table-bordered data-table" id="survey_table" style="width: auto;height: auto;margin-bottom: 10px;" >
				<thead>
					<#if vs.subSurveyType==1>
						<tr>
							<th style="background:#777;color: #fff;width: 100%;" colspan="${(vs.head.theadColsList?size)+(vs.head.parameterList?size)}">
								${vs.surveySubName}
							</th>
						</tr>
					</#if>

					<tr>
						<#list vs.head.theadColsList as tcl>
							<th <#if tcl.width!=''>style="width:${tcl.width}"</#if>>${tcl.colName}</th>
						</#list>
						<#list vs.head.parameterList as h>
							<#if h.isHidden==0>
								<th <#if h.width!=''>style="width:${h.width}"</#if>>${h.parameterName}</th>
							</#if>
						</#list>
					</tr>
				</thead>

				<tbody>
				<#list vs.surveyObjectVos as object>

					<#if object.groupName!=''>
					<tr style="background: #fbfbfb;height: 20px;line-height: 20px;vertical-align: middle;" >
						<td style="padding-left:${object.grade*20}px;" colspan="${(vs.head.theadColsList?size)+(vs.head.parameterList?size)}">
							&nbsp;&nbsp;${object.groupName}
						</td>	
					</tr>
					</#if>

					<#list object.surveyObjects as obj>

						<#if obj.objectId!=''>
						<tr>
							<#list vs.head.theadColsList as hd>
								<td>${obj['${hd.foreignCol}']}</td>
							</#list>
							<#list vs.head.parameterList as pls>
							
							<#if pls.isHidden==1>
								<input type="hidden" id="${obj.objectId}_${pls.surveyParameterId}_${obj.subSurveyId}" name="${obj.objectId}_${pls.surveyParameterId}_${obj.subSurveyId}" />
							<#else>	
								<td style="text-align: center;">
									<#if obj.parameterIdList??>
										<#list obj.parameterIdList as param>
											<#if param==pls.surveyParameterId>
												<#include "/survey/control.ftl" />
											</#if>
										</#list>
									<#else>
										<#include "/survey/control.ftl" />
									</#if>
								</td>
							</#if>
							
							</#list>
						</tr>
						</#if>
					</#list>

				</#list>


				<#list vs.surveyObjects as obj>
					<#if obj.objectId!=''>
					<tr>
						<#list vs.head.theadColsList as hd>
							<td>${obj['${hd.foreignCol}']}</td>
						</#list>
						<#list vs.head.parameterList as pls>
						
						<#if pls.isHidden==1>
							<input type="hidden" id="${obj.objectId}_${pls.surveyParameterId}_${obj.subSurveyId}" name="${obj.objectId}_${pls.surveyParameterId}_${obj.subSurveyId}" />
						<#else>	
							<td style="text-align: center;">
								<#if obj.parameterIdList??>
									<#list obj.parameterIdList as param>
										<#if param==pls.surveyParameterId>
											<#include "/survey/control.ftl" />
										</#if>
									</#list>
								<#else>
									<#include "/survey/control.ftl" />
								</#if>
							</td>	
						</#if>
						</#list>
					</tr>
					</#if>
				</#list>

				</tbody>
			</table>
			</#list>
			</form>

			<div style="margin: 0 auto;margin-top: 20px;height: 50px;line-height: 50px;vertical-align: middle;">
				<#if blockId==5>
					<input type="button" id="savaBtn" name="savaBtn" class="btn btn-success" value="保存" />
				<#else>
					<input type="button" id="sava_survey_but" name="sava_survey_but" class="btn btn-success" value="下一步" />
				</#if>
			</div>
			
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
	
		initLoadDetaiData();
		
		$("#savaBtn").click(function(){
			
			var blockId = $("#_blockId").val();
			$("#sava_survey_but").attr("disabled","disabled");
			var dataFlag = true;
			$(".intType").each(function(){			
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(!isNaN(value)){
					if(parseInt(value)!=value && value!=""){
						dataFlag = false;						
					 	layer.tips('必须为整数', '#'+id,{
						    tips: [2, '#3595CC'],
						    time: 15000,
						    tipsMore: true
						});
					}
				}else{
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}				
			});

			$(".float").each(function(){
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(isNaN(value)){
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}else{//如果为数字需要验证精确到多少位
					var scale = $(this).attr("scale");
					if(null!=scale && ''!=scale){
						var dot = value.indexOf(".");
			            if(dot != -1){
			                var dotCnt = value.substring(dot+1,value.length);
			                if(dotCnt.length > scale){
			                    dataFlag = false;
		 						layer.tips('小数位已超过'+scale+'位', '#'+id,{
								    tips: [2, '#3595CC'],
								    time: 15000,
								    tipsMore: true
								});
			                }
			            }
					}
				}
			});
			
			$(".require").each(function(){
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(value==null || ''==value){
					dataFlag = false;
		 			layer.tips('不能为空', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}
			});
			
			$(".maxValueValidate").each(function(){
				var currentValue = $(this).val();
				if(currentValue==''){
					currentValue = parseInt("0");
				}
				var maxValue = parseInt($(this).attr("maxvalue"));
				var minValue = parseInt($(this).attr("minvalue"));
				var id = $(this).attr("id");
				if(null!=currentValue && ''!= currentValue && currentValue>maxValue){
					dataFlag = false;
					layer.tips('不能超过最大值'+maxValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});				
				}else if(null!=currentValue && ''!= currentValue &&  currentValue<minValue){
					dataFlag = false;
					layer.tips('不能小于最小值'+minValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}						
			});
			
			setUploaderVal();
			var flag = checkSurvey(dataFlag);
			if(dataFlag && flag){
				$("body").showLoading();
				$("#feedbackId").val($("#_feedbackId").val());
				$.ajax({
				  url: '${contextPath}/survey/doAddColgateSurvey',
				  type: 'POST',
				  dataType: 'json',
				  data: $("#frm").serialize(),
				  complete: function(xhr, textStatus) {
				    $("body").hideLoading();
				  },
				  success: function(data, textStatus, xhr) {
					$("#blockId"+blockId).val(JSON.stringify(data));
				  },
				  error: function(xhr, textStatus, errorThrown) {
				    	layer.alert("问卷添加失败"+errorThrown);
				  }
				});	
			}else{
				$("#sava_survey_but").removeAttr("disabled");
				layer.msg('数据验证失败', {icon: 2,time: 15000});
				errfalg = false;
			}
			
			var feedbackId = $("#_feedbackId").val();
			var blockId6 = $("#blockId6").val();	
			var blockId3 = $("#blockId3").val();
			var blockId4 = $("#blockId4").val();
			var blockId5 = $("#blockId5").val();
			if(blockId3=="" && blockId3!="[]"){
				layer.msg('店内调查问卷验证失败', {icon: 2,time: 15000});
				return;
			}
			if(blockId4=="" && blockId4 !="[]"){
				layer.msg('附加问卷验证失败', {icon: 2,time: 15000});
				return;
			}
			if(blockId5==""&& blockId5 !="[]"){
				layer.msg('货架外陈列验证失败', {icon: 2,time: 15000});
				return;
			}
			var surveyId = $("#surveyId").val();
			var feedbackDate = $("#feedbackDate").val();
			var storeNo = $("#storeNo").val();
			var visitor = $("#visitor").val();
			$.post("${contextPath}/survey/doAddColgateSurvey",{surveyId:surveyId,feedbackDate:feedbackDate,storeNo:storeNo,visitor:visitor,feedbackId:feedbackId,blockId6:blockId6,blockId3:blockId3,blockId4:blockId4,blockId5:blockId5},function(data){
				if(data.code=="success"){
                    layer.alert(data.message);
                    window.location.href='${contextPath}/survey/query';
				}	
			});
		});
		
		$("#440_156_77").live("change",function(){
			var v_156 = $("#440_156_77").select2("val");
			if(v_156=="8"){
				$("#440_158_77").removeAttr("disabled");
				$("#440_158_77").addClass("require");
			}else{
				$("#440_158_77").val("");
				$("#440_158_77").removeClass("require");
				$("#440_158_77").attr("disabled","disabled");
			}
		});
		
		$("input[name*='177']").each(function(){
			var id = $(this).attr("id");
			if(id !='' && id != undefined && id !=null){
				var nextObjId = id.replace("177","178"); 
				$(this).on("change",function(){
					$("#"+nextObjId).val("");
				});
			}
		});
		
		$("input[name*='161']").each(function(){
			var id = $(this).attr("id");
			if(id !='' && id != undefined && id !=null){
				var nextObjId = id.replace("161","162"); 
				$(this).on("change",function(){
					$("#"+nextObjId).val("");
				});
			}
		});
		
		$("#sava_survey_but").die().click(function(){
			var blockId = $("#_blockId").val();
			$("#sava_survey_but").attr("disabled","disabled");
			var dataFlag = true;
			$(".intType").each(function(){			
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(!isNaN(value)){
					if(parseInt(value)!=value && value!=""){
						dataFlag = false;						
					 	layer.tips('必须为整数', '#'+id,{
						    tips: [2, '#3595CC'],
						    time: 15000,
						    tipsMore: true
						});
					}
				}else{
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}				
			});

			$(".float").each(function(){
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(isNaN(value)){
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}else{//如果为数字需要验证精确到多少位
					var scale = $(this).attr("scale");
					if(null!=scale && ''!=scale){
						var dot = value.indexOf(".");
			            if(dot != -1){
			                var dotCnt = value.substring(dot+1,value.length);
			                if(dotCnt.length > scale){
			                    dataFlag = false;
		 						layer.tips('小数位已超过'+scale+'位', '#'+id,{
								    tips: [2, '#3595CC'],
								    time: 15000,
								    tipsMore: true
								});
			                }
			            }
					}
				}
			});
			
			$(".require").each(function(){
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(value==null || ''==value){
					dataFlag = false;
		 			layer.tips('不能为空', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}
			});
			
			$(".maxValueValidate").each(function(){
				var currentValue = $(this).val();
				if(currentValue==''){
					currentValue = parseInt("0");
				}
				var maxValue = parseInt($(this).attr("maxvalue"));
				var minValue = parseInt($(this).attr("minvalue"));
				var id = $(this).attr("id");
				if(null!=currentValue && ''!= currentValue && currentValue>maxValue){
					dataFlag = false;
					layer.tips('不能超过最大值'+maxValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});				
				}else if(null!=currentValue && ''!= currentValue &&  currentValue<minValue){
					dataFlag = false;
					layer.tips('不能小于最小值'+minValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
				}						
			});
			
			setUploaderVal();
			var flag = checkSurvey(dataFlag);
			if(dataFlag && flag){
				$("body").showLoading();
				$("#feedbackId").val($("#_feedbackId").val());
				$.ajax({
				  url: '${contextPath}/survey/doAddColgateSurvey',
				  type: 'POST',
				  dataType: 'json',
				  data: $("#frm").serialize(),
				  complete: function(xhr, textStatus) {
				    $("body").hideLoading();
				  },
				  success: function(data, textStatus, xhr) {
					$("#blockId"+blockId).val(JSON.stringify(data));
					$("#sava_survey_but").removeAttr("disabled");
					if(blockId==6){
						 $("li[id='3']").parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
	            		$("li[id='3']").attr("class", "tab_current_b"); //设置当前选中项为选中样式
						var action = "${contextPath}/survey/showAddColgateSurvey/3";
						$("#tabs-body").load(action,function(){
							 $("body").hideLoading();
						});
					}else if(blockId==3){
						 $("li[id='4']").parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
	            		$("li[id='4']").attr("class", "tab_current_b"); //设置当前选中项为选中样式
						var action = "${contextPath}/survey/showAddColgateSurvey/4";
						$("#tabs-body").load(action,function(){
							 $("body").hideLoading();
						});
					}else if(blockId==4){
						$("li[id='5']").parent().children("li").attr("class", "tab_normal_b");//将所有选项置为未选中
	            		$("li[id='5']").attr("class", "tab_current_b"); //设置当前选中项为选中样式
						var action = "${contextPath}/survey/showAddColgateSurvey/5";
						$("#tabs-body").load(action,function(){
							 $("body").hideLoading();
						});
					}
				  },
				  error: function(xhr, textStatus, errorThrown) {
				    	layer.alert("问卷添加失败"+errorThrown);
				  }
				});	
			}else{
				$("#sava_survey_but").removeAttr("disabled");
				layer.msg('数据验证失败', {icon: 2,time: 15000});
			}
		})

		$("#exit_but").die().click(function(){
			var navObject = jQuery("#breadcrumb",parent.document); 
			jQuery(navObject).find("a:last").remove();
			document.location.href='${contextPath}/survey/query';
		});

		//reading
		loadDetaiData($("#_feedbackId").val());		
		//loadStatus($(".loadoption").attr("optionName"));
		
		var options = $(".loadoption");
		$.each(options, function(index, val) {
			 var name = $(this).attr("optionName");
			 loadStatus(name);
		});
		
		
		$(".dateTime").datetimepicker({
			duration: "normal",
			changeYear: true,
			yearRange:"2014:2025",
			dateFormat: 'yy-mm-dd', 
			timeFormat: 'HH:mm:ss',
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
			},
		});

	});


	function loadDetaiData(feedbackId){
		jQuery("body").showLoading();
		$.ajax({
			  url: '${contextPath}/survey/getSurveyFeedbackDetail/'+feedbackId,
			  type: 'POST',
			  dataType: 'json',
			  data: $("#frm").serialize(),
			  success: function(data, textStatus, xhr) {
			  		$.each(data, function(index, v) {
			  			if(v.controlType==7){
			  				$("#"+v.controlName).attr("checked", "checked");
			  			}else if(v.controlType==11){//图片
			  				var clientId = $("#clientId").val();
			  				var vals = v.value.split(",");
			  				for (var i = 0; i < vals.length; i++) {
			  					if(null == vals[i] || vals[i]==''){
			  						continue;
			  					}
			  					var html = '<div id="E_WU_FILE_'+index+'" class="file-item thumbnail upload-state-done" val="'+vals[i]+'"><img href="/uploadfiles/'+clientId+'/web/thumbnail/xl_'+vals[i]+'" src="/uploadfiles/'+clientId+'/web/thumbnail/l_'+vals[i]+'" /><div class="info_del"></div></div>';

			  					$("#fileList_"+v.controlName).append(html);
			  					$("#"+v.controlName).val(v.value);
			  				}
			  				$(".upload-state-done img").lightBox();
			  			}else if(v.controlType==5){
			  				$("#"+v.controlName).select2("val",v.value);
			  			}else{
			  				$("#"+v.controlName).val(v.value);
			  			}
			  		});
			  		
			  		var blockId = $("#blockId").val();
		  			if(blockId==11){
		  				setPart();
		  			}
		  			if(blockId==12){
		  				var v_156 = $("#440_156_77").select2("val");
						if(v_156=="8"){
							$("#440_158_77").removeAttr("disabled");
							$("#440_158_77").addClass("require");
						}else{
							$("#440_158_77").val("");
							$("#440_158_77").removeClass("require");
							$("#440_158_77").attr("disabled","disabled");
						}
		  			}
		  			if(blockId==13){
		  				setPart();
		  			}
		  			jQuery("body").hideLoading();
		  			
			  },
			  error: function(xhr, textStatus, errorThrown) {
			  		jQuery("body").hideLoading();
			    	layer.msg("数据加载异常"+errorThrown);
			  }
			});
	}


function initLoadDetaiData(){
		var blockId = $("#_blockId").val();
		var jsonStr = $("#blockId"+blockId).val();
		if(jsonStr!="" && blockId!=7){
			var data = $.parseJSON(jsonStr);
			jQuery("body").showLoading();
			$.each(data, function(index, v) {
	  			if(v.controlType==7){
	  				$("#"+v.controlName).attr("checked", "checked");
	  			}else if(v.controlType==11){//图片	
	  				var clientId = $("#clientId").val();
	  				var vals = v.value.split(",");
	  				for (var i = 0; i < vals.length; i++) {
	  					if(null == vals[i] || vals[i]==''){
	  						continue;
	  					}
	  					var html = '<div id="E_WU_FILE_'+index+'" class="file-item thumbnail upload-state-done" val="'+vals[i]+'"><img href="/uploadfiles/'+clientId+'/web/thumbnail/xl_'+vals[i]+'" src="/uploadfiles/'+clientId+'/web/thumbnail/l_'+vals[i]+'" /><div class="info_del"></div></div>';
	
	  					$("#fileList_"+v.controlName).append(html);
	  					$("#"+v.controlName).val(v.value);
	  				}
	  				$(".upload-state-done img").lightBox();
	  			}else if(v.controlType==5){
	  				$("#"+v.controlName).select2("val",v.value);
	  			}else{
	  				$("#"+v.controlName).val(v.value);
	  			}
	  		});
	  		jQuery("body").hideLoading();
		}
		
	}

function loadStatus(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("."+optionName).select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
}


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

</script>
</html>