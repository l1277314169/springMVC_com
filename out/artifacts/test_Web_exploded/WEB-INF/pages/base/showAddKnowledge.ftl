<html>
<head>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${rc.contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<title>学习资料维护</title>
<style>
	  textarea#content{
	  	width: 315px;
	  }
	  .line-middle{
	  	vertical-align:middle;
	  }
	  .txt-center{
	  	text-align:center;
	  }
</style>
</head>
<body>
<div class="fw">
	<div class="widget-content nopadding" style="margin:0px;">
		<form class="form-horizontal" action="#" id="dataForm" enctype="multipart/form-data">
			<table class="table_white_bg" style="width:100%;">
                <tbody>
                <tr>
                	<td colspan="4" class="txt-center"  style="background-color:#EAEAEA;">
                			学习资料基础数据新增
                	</td>
                </tr>
            	<tr>
            		<!--<td class="td_label fill_td" style="padding-left:10px;"><i class="cc1">*</i>资料类型：</td>
                    <td  class="td_control">
                    	<input id="knowledgeType" name="knowledgeType" type="text"/>
                    </td>-->
                	<td class="td_label"  style="padding-left:10px;"><i class="cc1">*</i>标题：</td>
                	<td class="td_control"><input type="text" name="title" id="title"></td>
                	<td class="td_label">阅读数：</td> 
                    <td class="td_control">
	                    <input id="readTimes" name="readTimes" type="text" />
					</td>
                </tr> 
                <tr>
					<td class="td_label">点赞数：
                	</td>
                    <td class="td_control">
	                    <input type="text" class="input-medium" autocomplete="off" id="likeTimes" name="likeTimes" />
                    </td>
                    <td class="td_label">排序：</td>
                    <td class="td_control">
                    	<input id="sequence" name="sequence" type="text" />
                    </td>
                </tr>
                <tr>
                   <td class="td_label">开始时间：</td>
                    <td  class="td_control">
                         <input type="text" id="td_startDate" name="startDate" /> 
                   </td>
                   	<td class="td_label">结束时间：</td>
                    <td class="td_control">
                       <input type="text" id="td_endDate" name="endDate" />
                    </td>
                </tr> 
                <tr>
                  <td class="td_label"><i class="cc1">*</i>内容：</td>
                    <td class="td_control">
                    	<textarea name="content" maxlength="500" id="content"></textarea>
                    </td>
                </tr>
                <tr>
                   <td class="td_label fill_td line-middle" style="padding-left:10px;">资料展示照片：</td>
                   <td colspan="3">
                      <input type="hidden" id="avatar" name="avatar"/>
                      <#assign imageWidthFTL="400">
                      <#assign objectIdFTL="avatar">
					  <#include "/widgets/imageUploader.ftl" />
                   </td>
                </tr>
                <tr>
                	<td colspan="4" class="txt-center"  style="background-color:#EAEAEA;">
                		学习资料附件新增
                	</td>
                </tr>
                <tr>
	                <td colspan="4">
	               			  <#include "/base/showAddKnowledgeAttachment.ftl" />
	                 </td>
                </tr>
              <tr>
				 <td colspan="4" class="td_buttons">
					 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
					 <button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
				 </td>
			   </tr>
            </tbody>
        </table>
	</form>
  </div>
</body>
</html>

<script>

	 function submitFrm(){
		$.ajax({
            url : "${contextPath}/knowledge/insertKnowledge",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
				   if(result.code=="success"){
				   		layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/knowledge/query/";
		   						parent.layer.closeAll('iframe');
				   		});
					}else {
						$.alert(result.message);
						$("#savetButton").removeAttr("disabled");
			   		}
			},
			 error:function(){
				$("#savetButton").removeAttr("disabled");
			}
        });
	}
    	 var addDialog,editDialog;
	   var addKnowledgeData = [{ id: 1, text: '资料1' }, { id: 2, text: '资料2' }];
		//$("#knowledgeType").select2({
					//width:145,
					//placeholder: "请选择",
					//allowClear: true,
					//data: addKnowledgeData
		// });
	 $(function(){
		 var valibobj=$("#dataForm").validate({
				errorClass: "error",
				rules : {
					content: {
						required: true
					},
					knowledgeType:{
					   required: true
					},
					title:{
						required: true
					}
				},
				messages:{
					content:{
						required:"不能为空"
					},
					knowledgeType:{
						required:"不能为空"
					},
					title:{
					   required:"不能为空"
					}
				},
				errorPlacement: function (error, element) {
					var elem = $(element);
					error.insertAfter(element);
				}
			});
		  //新增学习资料
	    $("#savetButton").click(function(){
	        $(this).attr("disabled","disabled");
	        if(!valibobj.form()){
				$("#savetButton").removeAttr("disabled");
				return false;
			}
		   setFileUploaderVal();
		   setImgUploaderVal();
	       submitFrm();
	     });
		 
		  $('#cancel').click(function(){
				parent.addDialog.close();
		  });
	 
		 $("#td_startDate").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$("#td_endDate").datepicker("option","minDate",dateText);
				$(this).focus();
				$(this).blur();
			}
		});
	
		$("#td_endDate").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$("#td_startDate").datepicker("option","maxDate",dateText);
				$(this).focus();
				$(this).blur();
			}
		});
	});	 
   //资料照片上传
   function getImgValues(obj){
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

	function setImgUploaderVal(){
		var uploaderList = $(".imgUpload .uploader-list");
		$.each(uploaderList, function(index, obj) {
			 var fid = $(obj).attr("id");
			 var id = fid.replace("fileList_",'');
			 var vals = getImgValues(obj);
			 $("#"+id).val(vals);
		});  
	}
</script>