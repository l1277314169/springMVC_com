<html>
<head>
<title>问卷</title>
<#include "/common/taglibs.ftl" />
</head>
<body>
	<div>

		<div class="widget-content nopadding" style="text-align: center;margin: 0px;overflow:auto;width:auto;">
			<form id="frm" name="frm" method="POST" action="${contextPath}/survey/doAddSurvey" enctype="multipart/form-data" >

			<input type="hidden" name="surveyId" value="${surveyId}" />
			<input type="hidden" id="feedbackId" name="feedbackId" />
			<input type="hidden" id="clientId" name="clientId" value="${clientId}" />

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
								<td style="width: ${pls.width};text-align: center;" class="survey_talbe_td">
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
							<td style="width: ${pls.width};text-align: center;">
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

			<div style="margin: 0 auto;margin-top: 20px;height: 30px;line-height: 30px;vertical-align: middle;">
				<button data-dismiss="dialog" type="button" id="exit_but" class="btn btn-danger">返回</button>
			</div>
			
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#exit_but").die().click(function(){
			var navObject = jQuery("#breadcrumb",parent.document); 
			jQuery(navObject).find("a:last").remove();
			document.location.href='${contextPath}/survey/query';
		});

		//reading
		loadDetaiData($("#_feedbackId").val());
		var options = $(".loadoption");
		$.each(options, function(index, val) {
			 var name = $(this).attr("optionName");
			 loadStatus(name);
		});
	});


	function loadDetaiData(feedbackId){
		jQuery("body").showLoading();
		var _url = '${contextPath}/survey/getSurveyFeedbackDetail/'+feedbackId;
		$.ajax({
			  url: _url,
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
			  					//var v="/uploadfiles/'+clientId+'/web/thumbnail/xl_'+vals[i]+'";
			  					var html = '<div id="E_WU_FILE_'+index+'" class="file-item thumbnail upload-state-done" val="'+vals[i]+'"><img href="/uploadfiles/'+clientId+'/web/thumbnail/xl_'+vals[i]+'" src="/uploadfiles/'+clientId+'/web/thumbnail/l_'+vals[i]+'" /><a href=javascript:void(0) onclick=dk("/uploadfiles/'+clientId+'/web/'+vals[i]+'") >原图</a></div>';

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
			  },
			  error: function(xhr, textStatus, errorThrown) {
			  		jQuery("body").hideLoading();
			    	layer.msg("数据加载异常"+errorThrown);
			  }
			});
	}
function dk(v){
window.open(v,v);
}

function loadStatus(optionName){
	if(null!=optionName && ''!=optionName){
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