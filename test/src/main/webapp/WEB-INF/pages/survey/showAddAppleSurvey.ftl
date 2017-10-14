<html>
<head>
<title>问卷</title>
<#include "/common/taglibs.ftl" />
</head>
<#if blockId==3><!-- 店内调查问卷 -->
	<script type="text/javascript" src="${contextPath}/js/survey/survey_3.js?cVer=${cVer}"></script>
<#elseif blockId==5>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_5.js?cVer=${cVer}"></script>
<#else>
	<script type="text/javascript" src="${contextPath}/js/survey/survey_base.js?cVer=${cVer}"></script>
</#if>
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<body>
	<div>
		<div class="widget-content nopadding" style="text-align: center;margin: 0px;overflow:auto;width:auto;">
			<form id="frm" name="frm" method="POST" action="${contextPath}/appleSurvey/doAddAppleSurvey" enctype="multipart/form-data" >

			<input type="hidden" name="surveyId" value="${surveyId}" />
			<input type="hidden" id="feedbackId" name="feedbackId" />
			<input type="hidden" id="clientId" name="clientId" value="${clientId}" />
			<input type="hidden" id="blockId" name="blockId" value="${blockId}" >

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
							<th <#if h.width!=''>style="width:${h.width}"</#if>>${h.parameterName}</th>
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
						</#list>
					</tr>
					</#if>
				</#list>

				</tbody>
			</table>
			</#list>
			</form>

			<div style="margin: 0 auto;margin-top: 20px;height: 50px;line-height: 50px;vertical-align: middle;">
				<button data-dismiss="dialog" type="button" id="exit_but" class="btn btn-danger">取消</button>
				<input type="button" id="sava_survey_but" name="sava_survey_but" class="btn btn-success" value="保存" />
			</div>
			
		</div>
	</div>
</body>
<script type="text/javascript">
function dakai(v){
	window.open(v, v);
}
	$(document).ready(function(){
		
		$("#sava_survey_but").die().click(function(){
				
			var dataFlag = true;

			$(".intType").each(function(){			
				var id = $(this).attr("id");
				var value = $("#"+id).val();
				if(!isNaN(value)){
					if(parseInt(value)!=value && value!=""){
						dataFlag = false;						
					 	layer.tips('必须为整数', '#'+id,{
						    tips: [2, '#3595CC'],
						    time: 5000,
						    tipsMore: true
						});
					}
				}else{
					dataFlag = false;
		 			layer.tips('必须为数字', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 5000,
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
					    time: 5000,
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
								    time: 5000,
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
					    time: 5000,
					    tipsMore: true
					});
				}
			});
			
			$("textarea").each(function(){
				var value = $(this).val();
				var id = $(this).attr("id");
				var maxlength = $(this).attr("maxlength");
				if(value.length>maxlength){
					dataFlag = false;
					layer.tips('不能超过'+maxlength+'个字符', '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 5000,
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
				if(currentValue>maxValue){
					dataFlag = false;
					layer.tips('不能超过最大值'+maxValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 5000,
					    tipsMore: true
					});				
				}else if(currentValue<minValue){
					dataFlag = false;
					layer.tips('不能小于最小值'+minValue, '#'+id,{
					    tips: [2, '#3595CC'],
					    time: 5000,
					    tipsMore: true
					});
				}						
			});
			
			var flag = checkSurvey(dataFlag);
			if(dataFlag && flag){
				setUploaderVal();
				$("body").showLoading();
				$("#feedbackId").val($("#_feedbackId").val());
				$.ajax({
				  url: '${contextPath}/appleSurvey/doAddAppleSurvey',
				  type: 'POST',
				  dataType: 'json',
				  data: $("#frm").serialize(),
				  complete: function(xhr, textStatus) {
				    $("body").hideLoading();
				  },
				  success: function(data, textStatus, xhr) {
				    	if(data.code=='success'){
				    		layer.alert(data.message);
				    		//layer.alert(data.message, function(){
							    //var navObject = jQuery("#breadcrumb",parent.document);                            
	                            //jQuery(navObject).find("a:last").remove();
	                            //window.location.href='${contextPath}/appleSurvey/query';
	                            layer.alert(data.message);
							//})
				    	}else{
				    		layer.alert(data.message);
				    	}
				  },
				  error: function(xhr, textStatus, errorThrown) {
				    	layer.alert("问卷添加失败"+errorThrown);
				  }
				});	
			}else{
				layer.msg('数据验证失败', {icon: 2,time: 5000});
			}
		})

		$("#exit_but").die().click(function(){
			var navObject = jQuery("#breadcrumb",parent.document); 
			jQuery(navObject).find("a:last").remove();
			document.location.href='${contextPath}/appleSurvey/query';
		});

		//reading
		loadDetaiData($("#_feedbackId").val());		
		loadStatus($(".loadoption").attr("optionName"));
	
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
		
		$(".checkDate").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
				if($(this).hasClass("checkDate")){
					$(".checkBeginDate").datepicker("option","maxDate",dateText);
					$(".checkBeginDate").datepicker("option","minDate",dateText);
					$(".checkEndDate").datepicker("option","maxDate",dateText);
					$(".checkEndDate").datepicker("option","minDate",dateText);	
				}
			}
		});
		
		$(".checkBeginDate").datetimepicker({
			duration: "normal",
			changeYear: true,
			yearRange:"2014:2025",
			timeFormat: 'HH:mm:ss',
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
				$(".checkEndDate").datepicker("option","maxDate",dateText);
			},
		});
		$(".checkEndDate").datetimepicker({
			duration: "normal",
			changeYear: true,
			yearRange:"2014:2025",
			timeFormat: 'HH:mm:ss',
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
				$(".checkBeginDate").datepicker("option","minDate",dateText);	
			},
		});
	});


	function loadDetaiData(feedbackId){
		jQuery("body").showLoading();
		$.ajax({
			  url: '${contextPath}/appleSurvey/getAppleSurveyFeedbackDetail/'+feedbackId,
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
			  					var html = '<div id="E_WU_FILE_'+index+'" class="file-item thumbnail upload-state-done" val="'+vals[i]+'"><img href="/uploadfiles/'+clientId+'/web/thumbnail/xl_'+vals[i]+'" src="/uploadfiles/'+clientId+'/web/thumbnail/l_'+vals[i]+'" /><a target="_black" style="position:absolute;margin-left:28px" href="${contextPath}/uploadfiles/${clientId}/web/'+vals[i]+'">原图</a><div class="info_del"></div></div>';
			  					$("#fileList_"+v.controlName).append(html);
			  					$("#"+v.controlName).val(v.value);
			  				}
			  				$(".upload-state-done img").lightBox();
			  			}else if(v.controlType==5){
			  				$("#"+v.controlName).select2("val",v.value);
			  			}else if(v.controlType==14){
			  				var clientId = $("#clientId").val();
			  				var vals = v.value.split(",");
			  				for (var i = 0; i < vals.length; i++) {
			  					if(null == vals[i] || vals[i]==''){
			  						continue;
			  					}
								var html ='<div id="E_WU_FILE_'+index+'" style="width:300px;height:50px" class="file-item thumbnail" val="'+vals[i]+'"><audio controls="controls"><source src="/uploadfiles/'+clientId+'/web/'+vals[i]+'"></audio><div class="info_del" style="margin-left:130px;top:60px"></div><a href="/audio/downLoad?fileName='+clientId+'/web/'+vals[i]+'">下载</a></div>';
			  					$("#fileList_"+v.controlName).append(html);
			  					$("#"+v.controlName).val(v.value);
			  				}
			  			}else{
			  				$("#"+v.controlName).val(v.value);
			  			}
			  		});	
			  		jQuery("body").hideLoading();  		
			  },
			  error: function(xhr, textStatus, errorThrown) {
			  		jQuery("body").hideLoading();
			    	layer.msg("数据加载异常"+errorThrown);
			  }
			});
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