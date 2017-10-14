
<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/msi_survey_uploader.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<title>任务管理</title>
</head>
<body class="main_body">
<input type="hidden" id="ctTaskId" name="ctTaskId" value="${ctTaskDataSerchVo.ctTaskId}">
<input type="hidden" id="storeId" name="storeId" value="${ctTaskDataSerchVo.popId}">
<input type="hidden" id="startTime" name="startTime" value="${startTime}">	
<input type="hidden" id="clientId" name="clientId" value="${ctTaskDataSerchVo.clientId}" />
<input type="hidden" id="ctTaskDataId" name="ctTaskDataId" value="${ctTaskDataSerchVo.ctTaskDataId}" />
<input type="hidden" id="isInCycel" name="isInCycel" value="${isInCycel}" >
<#assign privCode="C1M13">
		<#include "/base.ftl" />
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/message/query">周期任务管理</a>
				</div>
		 	</div>
		 	<div id="div_step_detail" class="div_step_detail" style="width: 100%;height:auto;border:none;background: #fff;margin-left:0px;">
<div style="overflow-x:auto;">	
<form id="dataForm" action="" class="form-horizontal" method="post">		 		
	<table class="table table-bordered data-table" style="" id="c_list">
		<#if ctTask.taskType != '4'>
				<tr>
					<th class="fill_td">
						<#if ctTask.taskType == '1'>产品名称<#elseif ctTask.taskType == '2'>品牌名称<#elseif ctTask.taskType == '3'>品类相关
						<#elseif ctTask.taskType == '4'>问卷(无对象)<#elseif ctTask.taskType == '5'>人员名称<#elseif ctTask.taskType == '6'>终端对象
						<#elseif ctTask.taskType == '7'>产品系列<#elseif ctTask.taskType == '8'>产品分类<#elseif ctTask.taskType == '10'>物料名称
						</#if>
					</th class="fill_td">		      	 
		      		<#list ctTaskParameters as ctTaskParameter>
			      		<th class="fill_td">${ctTaskParameter.parameterName}</th>
		      		</#list>		      	
				</tr>
				<tbody>
					<#list ctTaskObjects as ctTaskObject>
						<tr >
							<td class="fill_td">${ctTaskObject.objectName}</td>
							<#list ctTaskParameters as ctTaskParameter>
			      				<td class="td_control_d fill_td">	   				
									<#assign key="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}"/>
									<#if ctTaskParameter.controlType=='3'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskParameter.controlType=='1'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskParameter.controlType=='2'>
										<textarea rows=2 style="height:50;" maxlength=500 ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" placeholder="不超过500个字"></textarea>	
									<#elseif ctTaskParameter.controlType=='4'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskParameter.controlType=='5'>										
										<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />											
									<#elseif ctTaskParameter.controlType=='6'>
										<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if>  />
									<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
										<input type="checkbox" controlType="checkbox" class="input-medium loadCheckBoxOption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" value="1" <#if parameterMap?exists && parameterMap[key]==1>checked="checked"</#if> />
									<#elseif  ctTaskParameter.controlType=='11'>
									<#elseif  ctTaskParameter.controlType=='9'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> dateTime" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> readonly="readonly" />
									<#else>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									</#if>	
			      				</td>
		      				</#list>
						</tr>
					</#list>
				</tbody>
			<#else>
				<tr>
					<th class="fill_td">参数名称</th>	
					<th class="fill_td">参数值</th>		
				</tr>
				<tbody>
					<#list ctTaskParameters as ctTaskParameter>
						<tr>
			      			<td class="fill_td">${ctTaskParameter.parameterName}</td>
			      			<td>
		      					<#assign key="${ctTaskParameter.ctTaskParameterId}_"/>
		      					<#if ctTaskParameter.controlType=='1'>
									<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId=='92'>
	      								<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> correctAddress" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId=='198'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> otherInvalidReason storeInvalidFlag" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#else>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> <#if ctTaskParameter.ctTaskParameterId==78>storeInvalidFlag</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									</#if>
								<#elseif ctTaskParameter.controlType=='3'>					
									<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==57>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumForm1" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==58>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumMax1" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==59>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumForm2" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==60>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumMax2" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#else>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									</#if>
								<#elseif ctTaskParameter.controlType=='4'>
									<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
								<#elseif ctTaskParameter.controlType=='2'>
									<textarea rows=2 style="height:50;" maxlength=500 ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" placeholder="不超过500个字"></textarea>								 
								<#elseif ctTaskParameter.controlType=='5'>
									<#if ctTaskDataSerchVo.clientId=='13'>
										<#if ctTaskParameter.optionName=='store_invalid_reason'>
											<input type="text" controlType="select" class="storeInvalidReason storeInvalidFlag input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
										<#else>
											<input type="hidden" controlType="text" class="input-medium loadoptionRadio ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
										</#if>
									<#else>
										<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									</#if>
								<#elseif ctTaskParameter.controlType=='6'>
									<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
								<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
									<input type="checkbox" controlType="checkbox" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" value="${parameterMap[key]}" <#if parameterMap?exists && parameterMap[key]==1>checked="checked"</#if>/>									
								<#elseif  ctTaskParameter.controlType=='11'>
								<#elseif  ctTaskParameter.controlType=='9'>
									<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> dateTime storeInvalidFlag" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> readonly="readonly" />
								<#elseif  ctTaskParameter.controlType=='100'>
									<input type="text" controlType="select" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> monthControl storeInvalidFlag" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />	
								<#else>
									<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
								</#if>	
			      			</td>
			      		</tr>
		      		</#list>
				</tbody>
			</#if>
	</table>
</form>
	<span class="img_title" style="border-top:0px">图片：</span>
	<div id="uploader-demo" class="wu-example" style="width:730px;">
	    <input type="hidden" id="imageNames" name="imageNames" />
	    <div id="fileList" class="uploader-list">
	    	<#list ctTaskData.ctTaskDataAttachments as atts>
	    		<div id="E_WU_FILE_${atts_index}" class="file-item thumbnail upload-state-done" val="${atts.attachment}"  >
	    			<img src="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/l_${atts.attachment}" href="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/xl_${atts.attachment}" />
	    			<div class="info_del"></div>
	    		</div>
	    	</#list>
	    </div>
	    <div id="filePicker">选择图片</div>
	</div>
 </div>	
<div class="td_buttons" style="margin-top:10;margin-bottom:10;margin-buttom:-5px;text-align:center;">
	<button data-dismiss="dialog" type="button" class="btn btn-danger">取消</button>
	<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
</div>
<div id="tabs-body" class="div_data_detail" style="width: 100%;height: 100%;overflow:visible;background: #fff;"></div>
	</div>
<#include "/footer.ftl" />
</body>
</html>
<script>
$(function(){	 
	//初始访问日期----高露洁用户需求
 	var initVisiDate = "${initDate}";
 	
	$(".file-item img").lightBox();
 
	$(".correctAddress").attr("disabled",true); 
	
	$(".otherInvalidReason").attr("disabled",true); 
	
	$(".storeInvalidReason").attr("disabled",true); 
	 
	loadStatus($(".loadoption").attr("optionName"));	
	
	loadMultipleStatus($(".multipleloadoption").attr("optionName"));
	
	loadMonthControl();
	
	$(".loadoptionRadio").each(function(){
		var isInvalidValue = $("#197_").val();
		var ctTaskParameterId = $(this).attr("ctTaskParameterId");
		if(isInvalidValue=="1"||isInvalidValue==undefined){
			loadoptionRadio($(this).attr("optionName"),$(this).attr("value"),$(this).attr("ctTaskParameterId"));
		}else{
			if(ctTaskParameterId=='197'){
				loadoptionRadio($(this).attr("optionName"),$(this).attr("value"),$(this).attr("ctTaskParameterId"));
			}else{
				loadoptionRadio2($(this).attr("optionName"),$(this).attr("value"),$(this).attr("ctTaskParameterId"),isInvalidValue);
			}
		}
	});
	
	var monthControlValue = $(".monthControl").val();
	var clientId = "${ctTaskDataSerchVo.clientId}";
	if(monthControlValue!="" && clientId =="13"){
		var month = monthControlValue;
		var currentMonth = monthControlValue+"-01";
		var maxDate = "";
		$.post("${contextPath}/ctTask/getMaxVisitDate",{visitDate:currentMonth},function(result){
			if(parseInt(result)<10){
				maxDate = "0"+result;
			}else{
				maxDate = result;
			}
			if(maxDate!=""){
				$(".dateTime").datepicker("option","minDate",month+'-01');
				$(".dateTime").datepicker("option","maxDate",month+'-'+maxDate);
			}
		});	
	}
	
	$(".monthControl").click(function(){
		var month = $(this).val();
		$(".dateTime").attr("value","");
		var currentMonth = $(this).val()+"-01";
		var maxDate = "";
		$.post("${contextPath}/ctTask/getMaxVisitDate",{visitDate:currentMonth},function(result){
			if(parseInt(result)<10){
				maxDate = "0"+result;
			}else{
				maxDate = result;
			}
			if(maxDate!=""){
				$(".dateTime").datepicker("option","minDate",month+'-01');
				$(".dateTime").datepicker("option","maxDate",month+'-'+maxDate);
			}
		});
	});
 
	$(".dateTime").datepicker({
		duration: "slow",
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$(this).focus();
			$(this).blur();
			var id = $(this).attr("id");
			if($("#"+id+"~ i").length>0){
 				$(this).next().remove();			
 			}	
		},
	});
	
	$('.btn-danger').click(function(){
		$('#breadcrumb a:last-child',window.top.document).remove();
		window.history.go(-1); 
	});

	var str56Val = $("#56_").val();
	if(str56Val=="1"||str56Val==""){
		$(".correctAddress").attr("readonly",true);
	}

	$("#savetButton").bind("click",function(){
		var ctTaskDataId = $("#ctTaskDataId").val();
		$("#savetButton").attr("disabled","disabled");
		var imageNames = getUploaderVal();
		var dataFlag = "true";
		$(".require").each(function(){
			var value = null;
			var id = $(this).attr("id");			
			var controlType = $("#"+id).attr("controlType");    //控件类型
			if(controlType=="select"||controlType=="multipleSelect"){
				value = $("#"+id).select2("val")
			}else if(controlType=="text"){
				value = $("#"+id).val();
			}						
			if($(this).attr("disabled")!="disabled"){
				if(value==''){
					dataFlag = "false";
					if($("#"+id+"~ i").length==0){
						$(this).after("<i for=\""+id+"\" class=\"error\">不能为空</i>");	
					}		 		
				}	
			}
		}); 
 		$(".intType").each(function(){			
				var id = $(this).attr("id");
				var controlType = $("#"+id).attr("controlType");
				var value = $("#"+id).val();
				if(!isNaN(value)){
					if(parseInt(value)!=value && value!=""){
						dataFlag = "false";
						if($("#"+id+"~ i").length==0){
					 		$(this).after("<i for=\""+id+"\" class=\"error\">必须为整数</i>");	
					 	}
					}
				}else{
					dataFlag = "false";	
					if($("#"+id+"~ i").length==0){
		 				$(this).after("<i for=\""+id+"\" class=\"error\">必须为数字</i>");	
		 			}
				}
				
			});
			$(".float").each(function(){
				var id = $(this).attr("id");
				var controlType = $("#"+id).attr("controlType");
				var value = $("#"+id).val();
				var scale = $(this).attr("scale");
				if(value!=''){
					if(!isNaN(value)){
						if(scale!="" && value!=""){
							if(value.split(".").length!=1 && value.split(".")[1].length > scale){
					 			dataFlag = "false";	
					 			if($("#"+id+"~ i").length==0){
					 				$(this).after("<i for=\""+id+"\" class=\"error\">不能超过"+scale+"位小数</i>");	
					 			}
							}				
						}
					}else{
						dataFlag = "false";	
						if($("#"+id+"~ i").length==0){
			 				$(this).after("<i for=\""+id+"\" class=\"error\">必须为数字</i>");	
			 			}	
					}			
				}
			}); 				
			
			$(".maxValueValidate").each(function(){
				var currentValue = $(this).val();
				if(currentValue!=''){
					var maxValue = parseInt($(this).attr("maxvalue"));
					var minValue = parseInt($(this).attr("minvalue"));
					var id = $(this).attr("id");
					if(currentValue>maxValue){
						dataFlag = "false";	
						if($("#"+id+"~ i").length==0){
							$(this).after("<i for=\""+id+"\" class=\"error\">不能超过最大值"+maxValue+"</i>");	
						}						
					}else if(currentValue<minValue){
						dataFlag = "false";	
						if($("#"+id+"~ i").length==0){
							$(this).after("<i for=\""+id+"\" class=\"error\">不能小于最小值"+minValue+"</i>");	
						}
					}	 
				}
			});
	 
			if('${ctTaskDataSerchVo.clientId}'=='13'){
				var value1 = parseInt($(".valNumMax1").val());
				var maxNum1 = parseInt($(".valNumForm1").val());
				var valNumMax1Id = $(".valNumMax1").attr("id");
				if(value1>maxNum1){
					dataFlag = "false";
					if($("#"+valNumMax1Id+"~ i").length==0){
						$(".valNumMax1").after("<i for=\""+valNumMax1Id+"\" class=\"error\">不能超过所有牙膏陈列面的值"+maxNum1+"</i>");		
					}				
				}
				
				var value2 = parseInt($(".valNumMax2").val());
				var maxNum2 = parseInt($(".valNumForm2").val());
				var valNumMax2Id = $(".valNumMax2").attr("id");
				if(value2>maxNum2){
					dataFlag = "false";
					if($("#"+valNumMax2Id+"~ i").length==0){
						$(".valNumMax2").after("<i for=\""+valNumMax2Id+"\" class=\"error\">不能超过所有牙膏陈列面的值"+maxNum2+"</i>");		
					}
				}
				var isCorrectValue = $("#56_").val();       //高露洁需求 门店地址是否正确验证
				if(isCorrectValue=='2'){
					var addressValue = $(".correctAddress").val();	             //正确地址参数值
					var id = $(".correctAddress").attr("id");
					if(addressValue == null || addressValue == undefined || addressValue == ''){
						dataFlag = "false";
						if($("#"+id+"~ i").length==0){
							$(".correctAddress").after("<i for=\""+id+"\" class=\"error\">请填写正确地址</i>");		
						}	
					}
				}
				var storeInvalidReasonValue = $(".storeInvalidReason").select2("val");
				if(storeInvalidReasonValue=='7'){
					var otherInvalidReasonValue = $(".otherInvalidReason").val();		//门店无效其他原因
					var id = $(".otherInvalidReason").attr("id");
					if(otherInvalidReasonValue == null || otherInvalidReasonValue == undefined || otherInvalidReasonValue == ''){
						dataFlag = "false";
						if($("#"+id+"~ i").length==0){
							$(".otherInvalidReason").after("<i for=\""+id+"\" class=\"error\">请填写门店无效其他原因</i>");		
						}	
					}
				}
				var storeInvalidReasonValue = $(".storeInvalidReason").select2("val");
				if(storeInvalidReasonValue=='7'){
					var otherInvalidReasonValue = $(".otherInvalidReason").val();		//门店无效其他原因
					var id = $(".otherInvalidReason").attr("id");
					if(otherInvalidReasonValue == null || otherInvalidReasonValue == undefined || otherInvalidReasonValue == ''){
						dataFlag = "false";
						if($("#"+id+"~ i").length==0){
							$(".otherInvalidReason").after("<i for=\""+id+"\" class=\"error\">请填写门店无效其他原因</i>");		
						}	
					}
				}
			}
	 
			if($(".error").length>0){
				dataFlag = "false";
			}
		if('${ctTaskDataSerchVo.clientId}'=='13'){
			var ctTaskId = $("#ctTaskId").val();
			var visitDate = $(".dateTime").val();
			var popId = $("#storeId").val();
			$.post("${contextPath}/ctTaskData/getCtTaskDataByStartTime",{ctTaskId:ctTaskId,popId:popId,visitDateStr:visitDate},function(result){
				var selectMonth = $(".monthControl").select2("val");
				var isSameMonth = false; 
				if(selectMonth!=initVisiDate && result['existCtTaskData']){
					isSameMonth = true;	
				}
				if(isSameMonth){
					$("#savetButton").removeAttr("disabled");
					layer.alert("该月份已有数据");	
				}else{
					if(dataFlag=="true"){
						var dataString = $("#dataForm").serialize();	 	
				 		var datas = dataString.split("&");
				 		var ctTaskId = $("#ctTaskId").val();
				 		var storeId = $("#storeId").val(); 		
				 		var startTime = $("#startTime").val();
				 		var ctTaskData = new Object();
				 			ctTaskData.popId = storeId;
				 			ctTaskData.ctTaskDataId = ctTaskDataId;
				 			ctTaskData.ctTaskId = ctTaskId;
				 			ctTaskData.startTime = startTime;
				 			ctTaskData.imageNames = imageNames;
				 			if('${ctTaskDataSerchVo.clientId}'=='13'){
				 				ctTaskData.visitDate = $(".dateTime").val();	
				 			}
				 		var ctTaskDetailDatas = new Array();
				 		var parameterIdFlag = '';				//防止radio控件参数重复保存
				 		for(var index in datas){
				 			var str1 = datas[index].split("=")[0];
				 			if(str1.split("_").length!=0){
				 				var ctTaskDetailData = new Object();
					 			ctTaskDetailData.ctTaskParameterId = str1.split("_")[0];
					 			var parameterId = str1.split("_")[0];
					 			if(parameterIdFlag==parameterId){
									continue;	
								}else{
									parameterIdFlag = parameterId; 
								}
					 			ctTaskDetailData.target1Id = str1.split("_")[1]; 		 
					 			var controlType = $("#"+str1).attr("controlType");	
					 			var str2 = datas[index].split("=")[1];
					 			if(controlType=="multipleSelect"||controlType=="select"){
					 				ctTaskDetailData.value = $("#"+str1).select2("val")+"";
					 			}else{
					 				ctTaskDetailData.value = str2;
					 			}	 
				 				ctTaskDetailDatas.push(ctTaskDetailData);
				 			}	 			 				 			 	 									 				
				 		}
				 		ctTaskData.ctTaskDetailDatas = ctTaskDetailDatas;
				 		$.ajax({
								url : "${contextPath}/ctTaskData/updateCtTaskData",
								type : "post",
								dataType:'JSON',
								contentType : 'application/json',  
								data : JSON.stringify(ctTaskData),
								success : function(result) {
									var ctTaskId = $("#ctTaskId").val();
									var clientId = $("#clientId").val();
									layer.alert("保存成功",function(){
										if(clientId==7){
											var isInCycel = $("#isInCycel").val();
											var url = "${contextPath}/ctTask/showCtTaskList?ctTaskId="+ctTaskId+"&isInCycel="+isInCycel;
											window.location.href=url;
											$('#breadcrumb a:last-child',window.top.document).remove();
										}else{
											window.location.href="${contextPath}/ctTask/ctTaskStoreList?ctTaskId="+ctTaskId;
											$('#breadcrumb a:last-child',window.top.document).remove();
										}
									});
								}
							});		 
					}else{
						$("#savetButton").removeAttr("disabled");
					}	 			 
				}			
			});
		}else{
			if(dataFlag=="true"){
				var dataString = $("#dataForm").serialize();	 	
		 		var datas = dataString.split("&");
		 		var ctTaskId = $("#ctTaskId").val();
		 		var storeId = $("#storeId").val(); 		
		 		var startTime = $("#startTime").val();
		 		var ctTaskData = new Object();
		 			ctTaskData.popId = storeId;
		 			ctTaskData.ctTaskDataId = ctTaskDataId;
		 			ctTaskData.ctTaskId = ctTaskId;
		 			ctTaskData.startTime = startTime;
		 			ctTaskData.imageNames = imageNames;
		 			if('${ctTaskDataSerchVo.clientId}'=='13'){
		 				ctTaskData.visitDate = $(".dateTime").val();	
		 			}
		 		var ctTaskDetailDatas = new Array();
		 		for(var index in datas){
		 			var str1 = datas[index].split("=")[0];
 		 			if(str1.split("_").length!=0){
		 				var ctTaskDetailData = new Object();
			 			ctTaskDetailData.ctTaskParameterId = str1.split("_")[0];
			 			var parameterId = str1.split("_")[0];
			 			ctTaskDetailData.target1Id = str1.split("_")[1]; 		 
			 			var controlType = $("#"+str1).attr("controlType");	
			 			var str2 = datas[index].split("=")[1];
			 			if(controlType=="multipleSelect"||controlType=="select"){
			 				ctTaskDetailData.value = $("#"+str1).select2("val")+"";
			 			}else{
			 				ctTaskDetailData.value = str2;
			 			}		 			
			 			if(ctTaskDetailData.value != null && ctTaskDetailData.value != undefined && ctTaskDetailData.value != ''){
			 				ctTaskDetailDatas.push(ctTaskDetailData);
			 			}else{
			 				continue;
			 			}
		 			}	 			 				 			 	 									 				
		 		}
		 		ctTaskData.ctTaskDetailDatas = ctTaskDetailDatas;
		 		$.ajax({
						url : "${contextPath}/ctTaskData/updateCtTaskData",
						type : "post",
						dataType:'JSON',
						contentType : 'application/json',  
						data : JSON.stringify(ctTaskData),
						success : function(result) {
							var ctTaskId = $("#ctTaskId").val();
							layer.alert("保存成功",function(){
								window.location.href="${contextPath}/ctTask/showCtTaskList?ctTaskId="+ctTaskId+"&isInCycel="+$("#isInCycel").val();
								$('#breadcrumb a:last-child',window.top.document).remove();
							});
						}
					});		 
			}else{
				$("#savetButton").removeAttr("disabled");
			}	 		 
		} 
	});
	
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#workDate2").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
	
	$(":checkbox").each(function(){
		$(this).click(function(){
			if($(this).attr("checked")=="checked"){
				$(this).next().remove();
			}else{			
				var id = $(this).attr("id");
				$(this).after("<input type=\"hidden\" id=\""+id+"\" name=\""+id+"\" value=\"0\" />");	
			}
		});
	});
	
	$(".maxValueValidate").change(function(){
		var currentVal = $(this).val();
		if(currentVal==''){
			currentVal = parseInt("0");
		}
		var maxValue = parseInt($(this).attr("maxvalue"));
		var minValue = parseInt($(this).attr("minvalue"));
		var id = $(this).attr("id");
		if($("#"+id+"~ i").length==0){			
			if(currentVal>maxValue && currentVal!=""){
				$(this).after("<i for=\""+id+"\" class=\"error\">不能超过最大值"+maxValue+"</i>");	
			}else if(currentVal<minValue && currentVal!=""){
				$(this).after("<i for=\""+id+"\" class=\"error\">不能小于最小值"+minValue+"</i>");		
			}
		}else{	 
			$(this).next().remove();
			maxError="false";		 
		}
					
	});
	
	$(".require").change(function(){
		var currentVal = $(this).val();
		var id = $(this).attr("id");
		if($("#"+id+"~ i").length==0){
			if(currentVal==''){
				$(this).after("<i for=\""+id+"\" class=\"error\">不能为空</i>");				
			} 
		}else{
			if(currentVal!=''){
				$(this).next().remove();				 
			}
		}
					
	});
	
	$(".intType").change(function(){
		var currentVal = $(this).val();
		var id = $(this).attr("id");
		var controlType = $("#"+id).attr("controlType");
		var value = $("#"+id).val();
		if(!isNaN(value)){
			if($("#"+id+"~ i").length==0){
 				$(this).next().remove();	
 			}			
			if(parseInt(value)!=value && value!=""){
				if($("#"+id+"~ i").length==0 && value!=""){
		 			$(this).after("<i for=\""+id+"\" class=\"error\">必须为整数</i>");	
		 		}
			}else{			 
				$(this).next().remove();				 	 
			}			
		}else{
			if($("#"+id+"~ i").length==0){
 				$(this).after("<i for=\""+id+"\" class=\"error\">必须为数字</i>");	
 			}	
		}							
	});
	
	$(".float").change(function(){
		var currentVal = $(this).val();
		var id = $(this).attr("id");
		var controlType = $("#"+id).attr("controlType");
		var value = $("#"+id).val();
		var scale = $(this).attr("scale");		
		if(!isNaN(value)){
			if($("#"+id+"~ i").length==0){
 				$(this).next().remove();	
 			}
			if(scale!="" && value!=""){			
				if(value.split(".").length!=1 && value.split(".")[1].length > scale){
		 			if($("#"+id+"~ i").length==0){
		 				$(this).after("<i for=\""+id+"\" class=\"error\">不能超过"+scale+"位小数</i>");	
		 			}
				}else{
					$(this).next().remove();		
				}								
			}		 
		}else{
			if($("#"+id+"~ i").length==0){
 				$(this).after("<i for=\""+id+"\" class=\"error\">必须为数字</i>");	
 			}				 	
		}
	});	
	
	$(".valNumMax1").change(function(){
		var id = $(this).attr("id");
		var currentValue = parseInt($(".valNumMax1").val());
		var maxNum1 = parseInt($(".valNumForm1").val());
		if(currentValue<=maxNum1){
			if($("#"+id+"~ i").length>0){
 				$(this).next().remove();			
 			}		
		}else{
			if($("#"+id+"~ i").length==0){
 				$(this).after("<i for=\""+id+"\" class=\"error\">不能超过所有牙膏陈列面的值"+maxNum1+"</i>");	
 			}		
		}
	});
	
	$(".valNumForm1").change(function(){
		var id = $(".valNumMax1").attr("id");
		var currentValue = parseInt($(".valNumMax1").val());
		var maxNum1 = parseInt($(".valNumForm1").val());
		if(currentValue<=maxNum1){
			if($("#"+id+"~ i").length>0){
 				$(".valNumMax1").next().remove();			
 			}		
		}else{
			if($("#"+id+"~ i").length==0){
 				$(".valNumMax1").after("<i for=\""+id+"\" class=\"error\">不能超过所有牙膏陈列面的值"+maxNum1+"</i>");	
 			}		
		}
	});
	
	$(".valNumMax2").change(function(){
		var currentValue = parseInt($(".valNumMax2").val());
		var maxNum2 = parseInt($(".valNumForm2").val());
		var id = $(this).attr("id");
		if(currentValue<=maxNum2){
			if($("#"+id+"~ i").length>0){
 				$(this).next().remove();			
 			}		
		}else{
			if($("#"+id+"~ i").length==0){
 				$(this).after("<i for=\""+id+"\" class=\"error\">不能超过所有牙膏陈列面的值"+maxNum2+"</i>");	
 			}		
		}
	});
	
	$(".valNumForm2").change(function(){
		var currentValue = parseInt($(".valNumMax2").val());
		var maxNum2 = parseInt($(".valNumForm2").val());
		var id = $(".valNumMax2").attr("id");
		if(currentValue<=maxNum2){
			if($("#"+id+"~ i").length>0){
 				$(".valNumMax2").next().remove();			
 			}		
		}else{
			if($("#"+id+"~ i").length==0){
 				$(".valNumMax2").after("<i for=\""+id+"\" class=\"error\">不能超过所有牙膏陈列面的值"+maxNum2+"</i>");	
 			}		
		}
	});
	
	$(".correctAddress").change(function(){
		var addressValue = $(this).val();       //高露洁需求 门店地址是否正确验证
		var id = $(this).attr("id");
		if(addressValue != null && addressValue != undefined && addressValue != ''){
			if($("#"+id+"~ i").length>0){
 				$(this).next().remove();			
 			}	
		}
	});
	
	$(".storeInvalidReason").change(function(){
		var invalidaReasonValue = $(this).val();
		var id = $(this).attr("id");
		if(invalidaReasonValue=="7"){    						 //门店无效原因为其他时
			$(".otherInvalidReason").attr("disabled",false); 	
		}else{
			$(".otherInvalidReason").val("");
			$(".otherInvalidReason").attr("disabled",true);
		}
		if(invalidaReasonValue != null && invalidaReasonValue != undefined && invalidaReasonValue != ''){
			if($("#"+id+"~ i").length>0){
 				$(this).next().remove();			
 			}	
		}
	});
	
	$(".otherInvalidReason").change(function(){
		var otherInvalidReasonValue = $(this).val();       //高露洁需求 门店地址是否正确验证
		var id = $(this).attr("id");
		if(otherInvalidReasonValue != null && otherInvalidReasonValue != undefined && otherInvalidReasonValue != ''){
			if($("#"+id+"~ i").length>0){
 				$(this).next().remove();			
 			}	
		}
	});
	
	var isInvalidValue = $("#197_").val();
	if(isInvalidValue==2){
		$(".storeInvalidReason").addClass("require");
		$(".storeInvalidReason").attr("disabled",false); 
		$("#dataForm input").each(function(){
			if(!$(this).hasClass("storeInvalidFlag")){
				var controlType = $(this).attr("controlType");	
	 			if(controlType=="multipleSelect"||controlType=="select"){
	 				$(this).select2("val","");
	 			}else{
	 				$(this).val("");
	 			}
				$(this).attr("disabled",true);
			}
		});	
	}else{
		$(".storeInvalidReason").removeClass("require");
		$(".storeInvalidReason").select2("val","");		
		$(".otherInvalidReason").val(""); 		
		$(".storeInvalidReason").attr("disabled",true);
		$("#dataForm input").each(function(){
			if(!$(this).hasClass("storeInvalidFlag")){
				$(this).attr("disabled",false);
			}
		});
	}
});

function changeStore(){	
	$.post("${contextPath}/ctTask/getCtTaskData",{popId:$("#storeId").val(),ctTaskId:$("#ctTaskId").val()},function(data){	
		if(data!=''){	
			$("input ~ .error").remove();	
			$.each(data, function(key, value){				
				var controlType = $("#"+key).attr("controlType");    //控件类型
				if(controlType=="select"||controlType=="multipleSelect"){
					$("#"+key).select2("val",value);	
				}else if(controlType=="checkbox"){
					if(value==1){
						$("#"+key).attr("checked","checked");			
					}			
				}else if(controlType=="text"){
					$("#"+key).val(value);
				}					  
			});		
		}else{
			//清空表单
			$('#dataForm :input').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');  
		}	 		
	});	
	if($("#storeId").val()!=''){
		$("#storeId ~ i").remove();
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
};

function loadMultipleStatus(optionName){
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
				multiple: true,
				closeOnSelect:false,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadoptionRadio(optionName,value,ctTaskParameterId){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {	
				if(item.optionValue==value){
					if(ctTaskParameterId=='56'){					//高露洁客户逻辑验证的需求
						thisData+="<input type='radio' controlType='radio' onclick='isCorrect(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' controlType='radio' value='"+item.optionValue+"' checked='checked' />"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";			
					}else if(ctTaskParameterId=='197'){
						//高露洁客户逻辑需求
						thisData+="<input type='radio' controlType='radio' onclick='isStoreInvalid(this)' class='storeInvalidFlag' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' controlType='radio' value='"+item.optionValue+"' checked='checked' />"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";			
					}else{
						thisData+="<input type='radio' controlType='radio' onclick='changeRadioValue(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"' checked='checked' />"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";		
					}				
				}else{
					if(ctTaskParameterId=='56'){
						thisData+="<input type='radio' controlType='radio' onclick='isCorrect(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";	
					}else if(ctTaskParameterId=='197'){
						//高露洁客户逻辑需求
						thisData+="<input type='radio' controlType='radio' onclick='isStoreInvalid(this)' class='storeInvalidFlag' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";
					}else{
						thisData+="<input type='radio' controlType='radio' onclick='changeRadioValue(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}			
			});
			$("#"+ctTaskParameterId+"_").parent().prepend(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadoptionRadio2(optionName,value,ctTaskParameterId,isInvalidValue){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {	
				if(ctTaskParameterId=='56'){
					thisData+="<input type='radio' controlType='radio' disabled='disabled' onclick='isCorrect(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";	
				}else if(ctTaskParameterId=='197'){
					//高露洁客户逻辑需求
					thisData+="<input type='radio' controlType='radio' disabled='disabled' onclick='isStoreInvalid(this)' class='storeInvalidFlag' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";
				}else{
					thisData+="<input type='radio' controlType='radio' disabled='disabled' onclick='changeRadioValue(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";
				}
			});
			$("#"+ctTaskParameterId+"_").parent().prepend(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadCheckbox(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {						 
				thisData+=""+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";		 
			});
			$(".loadCheckBoxOption").parent().append(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function isCorrect(element){
	var id = element.name+"_";
	$("#"+id).attr("value",element.value);          //给参数赋值
	if($("#"+id+" ~ i").length>0){
		$("#"+id).next().remove();	
	}
	if(element.value==2){		//选择否的时候		
		$(".correctAddress").attr("readonly",false); 
	}else{
		$(".correctAddress").val("");
		$("#92_").next().remove();
		$(".correctAddress").attr("readonly",true); 
	}
}

function isStoreInvalid(element){
	var id = element.name+"_";
	$("#"+id).attr("value",element.value);          //给参数赋值
	if($("#"+id+" ~ i").length>0){
		$("#"+id).next().remove();	
	}
	if(element.value==2){		//选择否的时候	
		$(".storeInvalidReason").addClass("require");	
		$(".storeInvalidReason").attr("disabled",false); 
		$("#dataForm input").each(function(){
			if(!$(this).hasClass("storeInvalidFlag")){
				var controlType = $(this).attr("controlType");	
	 			if(controlType=="multipleSelect"||controlType=="select"){
	 				$(this).select2("val","");
	 			}else if(controlType=="radio"){
	 				$(this).attr("checked",false);	
	 			}else{
	 				$(this).val("");
	 			}
				$(this).attr("disabled",true);
			}
		});
	}else{
		$(".storeInvalidReason").removeClass("require");
		$(".storeInvalidReason").select2("val","");		
		$(".otherInvalidReason").val(""); 		
		$(".storeInvalidReason").attr("disabled",true);
		$("#dataForm input").each(function(){
			if(!$(this).hasClass("storeInvalidFlag")){
				$(this).attr("disabled",false);
			}
		});
	}
}

function changeRadioValue(element){
	var id = element.name+"_";
	$("#"+id).attr("value",element.value);           //给参数赋值
	if($("#"+id+" ~ i").length>0){
		$("#"+id).next().remove();
	}
}

function loadMonthControl(){
	$.ajax({
		type : "post",
		url : "${contextPath}/comms/initializeMonthControl?beginDate=${ctTaskDataSerchVo.beginDate}",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.value+"\",\"text\":\""+item.text+"\"},";
				} else {
					thisData += "{\"id\":\""+item.value+"\",\"text\":\""+item.text+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$(".monthControl").select2({
				width:150,
				placeholder: "请选择",
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};	
</script>