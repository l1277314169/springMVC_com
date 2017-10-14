<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<style>
	  .line-middle{
	  	vertical-align:middle;
	  }
	  .txt-center{
	  	text-align:center;
	  }
	  .table_white_bg .td_control {
	    padding-top: 0px; 
	    padding-left: 0px; 
     }
</style>
</head>
<body>
<div class="widget-content nopadding"  style="margin:0px;">
<form id="dataForm" method="post" class="form-horizontal">
		<input type="hidden" id="knowledgeId" name="knowledgeId" value="${knowledge.knowledgeId!''}">
        <table class="table_white_bg" style="width:100%;">
            <tbody> 
              <tr>
                	<td colspan="4" class="txt-center"  style="background-color:#EAEAEA;">
                		学习资料基础数据编辑
                	</td>
                </tr>
                <tr>
                    <td class="td_label fill_td"><i style="color:red;">*</i>标题：</td>
                    <td class="td_control">
                    	<input type="text" name="title" value="${knowledge.title!''}">
                    </td>
                </tr>
                    <tr>
                	<td class="td_label fill_td">阅读数：</td>
                    <td class="td_control">
                    	<input type="text" name="readTimes" value="${knowledge.readTimes!''}">
                    </td>
                    <td class="td_label fill_td">点赞数：</td>
                    <td class="td_control">
                    	<input type="text" name="likeTimes" value="${knowledge.likeTimes!''}">
                    </td>
                </tr>
                 <tr>
                	<td class="td_label fill_td">顺序：</td>
                    <td class="td_control">
                    	<input type="text" name="sequence" value="${knowledge.sequence!''}">
                    </td>
                    <td class="td_label fill_td">开始时间：</td>
                    <td class="td_control">
                     <input type="text" id="startDate" name="startDate" class="startDatePicker"  <#if (knowledge.startDate)??> value=" ${(knowledge.startDate)?string("yyyy-MM-dd HH:mm:ss")}"</#if> readonly/>
                    </td>
                </tr>
                  <tr>
                	<td class="td_label fill_td">结束时间：</td>
                    <td class="td_control">
                    	<input type="text" id="endDate" name="endDate" class="startDatePicker"  <#if (knowledge.endDate)??> value=" ${(knowledge.endDate)?string("yyyy-MM-dd HH:mm:ss")}"</#if> readonly/>
                    </td>
                    	<td class="td_label fill_td">是否删除：</td>
                    <td class="td_control">
                    	<select id="isDelete" name="isDelete" value="${knowledge.isDelete!''}">
                    	        <option value="">--请选择-</option>
	                			<option value="0" <#if knowledge.isDelete == 0 > selected="selected" </#if>>正常</option>
	                			<option value="1" <#if knowledge.isDelete == 1 > selected="selected" </#if>>删除</option>
                    	</select>
                    </td>
                </tr>
				<tr>
                    <td class="td_label fill_td"><i style="color: red;">*</i>内容：</td>
                    <td  class="td_control_d">
                    	<textarea rows=2  maxlength=5000 name="content">${knowledge.content!''}</textarea>
                    </td>
                </tr>
                <tr>
                    <td class="td_label fill_td line-middle">资料展示照片：</td>
                    <td class="td_control" colspan="3">
					<input type="hidden" id="avatar" name="avatar"/>
                        <#assign imageWidthFTL="400">
                        <#assign objectIdFTL="avatar">
					     <#include "/widgets/imageUploaderKnowledge.ftl" />
                    </td>
                </tr>
                <tr>
                	<td colspan="4" class="txt-center"  style="background-color:#EAEAEA;">
                		学习资料附件编辑
                	</td>
                </tr>
                <tr>
                    <td class="td_control" colspan="4"> 
                       <input type="hidden" id="fileName" name="fileName" value="${knowledge.fileName!''}"/>
					   <#include "/base/showEditKnowledgeAttachment.ftl" />
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');"  type="button" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   	</tr>
            </tbody>
        </table>
</form>
</div>
</body>
</html>
<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
<script>
var editDialog; 
 $(function(){
     $('#startDate').datetimepicker({
	    showSecond: true,
	    timeFormat: 'HH:mm:ss'
      });
     	
     	$('#endDate').datetimepicker({
	    showSecond: true,
	    timeFormat: 'HH:mm:ss'
      });
      
      $('#attachmentid').bind("click",function(){
         var knowledgeIdData=$('#knowledgeId').val();
         var url = "${contextPath}/knowledge/showKnowledgeAttachment/"+knowledgeIdData;
		 editDialog = new xDialog(url,{},{title:"编辑学习资料附件",width:1000,height:300}); 
		return false;	
      });
//编辑学习资料
$("#savetButton").bind("click",function(){
	$(this).attr("disabled","disabled");
	 if(!valibobj.form()){
	   $("#savetButton").removeAttr("disabled");
	   return false;
	}
	   setFileUploaderVal();
	   setImgUploaderVal();
       submitFrm();
	});
		  
      function submitFrm(){
		$.ajax({
            url : "${contextPath}/knowledge/updateKnowledge",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
			   if(result.code=="success"){
			   		  if(result.code=="success"){
				   		layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/knowledge/query/";
		   						parent.layer.closeAll('iframe');
				   		});
					}else {
						$.alert(result.message);
						$("#savetButton").removeAttr("disabled");
			   		}
			   		
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
