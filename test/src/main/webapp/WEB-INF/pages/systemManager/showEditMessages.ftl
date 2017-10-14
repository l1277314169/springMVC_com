<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/kindeditor-min.js"></script>
<script type="text/javascript" src="${contextPath}/js/kindeditor-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/kindeditor-default.css">
<body class="main_body">
		<#assign privCode="C3M3">
		<#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">系统设置</a>
					<a class="linkPage active" href="${contextPath}/message/query">消息通知管理</a>
				</div>
		 	</div>
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
		<input type="hidden"  id="messageId" name="messageId" value="${messages.messageId}">
		<input type="hidden" id="oldCheckboxId" name="oldCheckboxId" value="${messages.checkboxId}">
			<table class="table_white_bg">
		  	<tbody>
		  	<tr>
					<td class="td_label" >消息编号：</td>
                    <td  class="td_control"><input type="text" name="messageNo" value="${messages.messageNo!''}"></td>
					<td class="td_label" ><i class="cc1">*</i>消息类型：</td>
                    <td  class="td_control">
	                    <input id="messageType" name="messageType" required=true value="${messages.messageType!''}">
                    </td>
			</tr>
				<tr>
					<td class="td_label" >生效日期：</td>
                    <td  class="td_control"><input type="text" id="enableDate" name="enableDate" value="<#if (messages.enableDate)??>${(messages.enableDate)?string("yyyy-MM-dd")}</#if>" readonly ></td>
                    <td class="td_label" >失效日期：</td>
                    <td  class="td_control"><input type="text" id="expiredDate" name="expiredDate" value="<#if (messages.expiredDate)??>${(messages.expiredDate)?string("yyyy-MM-dd")}</#if>" readonly ></td>
				</tr>
				<tr>
                	 <td class="td_label" >关联人员：</td>
                    <td  class="td_control">
                    	<input  type="checkbox" id="relateClientUserIdbox"  name="versionDesc" >全部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<button id="relateClientUserIdButn" type="button" class="btn btn-success margin-left-18px">已关联<font id="clientUserCheckbox" color="#ff0000"><#if  !checkboxIds?? >0<#else>${checkboxIds}</#if></font>个人员</button>
                    <td>
                </tr>
				<tr>
					<td class="td_label" ><i class="cc1">*</i>消息主题：</td>
                    <td  class="td_control" colspan="4"><input type="text" name="messageTitle" style="width:535px" value="${messages.messageTitle!''}" required=true></td>
				</tr>
			</tbody>
			</table>
			<textarea id="messageContent" name="messageContent" style="width:790px;height:350px;">${messages.messageContent}</textarea>
			<input type="hidden" id="checkboxId" name="checkboxId" value="${messages.checkboxId},">
			<input type="hidden" id="oldCheckboxId" name="oldCheckboxId" value="${messages.checkboxId}">
			<input type="hidden" id="chilCheckboxId" name="chilCheckboxId" value="0">
		</form>
			<div class="td_buttons" style="margin-left:330px">
				<button type="button" id="messageButton" class="btn btn-success margin-left-18px">保存</button>
			</div>
		</div>	
    <#include "/footer.ftl" />
</body>
</html>
<script>
var replaceDialog;
$(function(){
	var msgData = [{ id: 1, text: '通知' }, { id: 2, text: '私信' }];
	 $("#messageType").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: msgData
	        });
//取消按钮
$('.btn-danger').click(function(){
	editDialog.close();
});

	$("#enableDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#expiredDate").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		},
	});
	
	$("#expiredDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#enableDate").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		},
	});
//富文本编辑器	
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="messageContent"]', {
				resizeType : 0,
				cssPath : '${contextPath}/js/plugins/code/prettify.css',
				uploadJson : '${contextPath}/message/uploadImg',
				allowFileManager : false,
				formatUploadUrl :false,
				afterUpload : function(url) {
                },
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
	});
});

//验证
	var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules : { 
					messageNo: {
						maxlength: 50
					},
					attachment: {
						maxlength: 200
					},
					messageTitle: {
						maxlength: 50
					}
				},
		messages:{
			messageType:{
				required:"不能为空"
			}
		},
		errorPlacement: function (error, element) {
            var elem = $(element);
            error.insertAfter(element);
        },
		
		highlight: function (element, errorClass, validClass) {
			var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').addClass(errorClass);
            } else {
                elem.addClass(errorClass);
                //$('a.select2-choice').addClass(errorClass);
            }
		},

        unhighlight: function (element, errorClass, validClass) {
            var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').removeClass(errorClass);
            } else {
                elem.removeClass(errorClass);
                //$('a.select2-choice').removeClass(errorClass);
            }
        }
	});
	
	$(document).bind("change", ".select2-offscreen", function () {
        if (!$.isEmptyObject(validobj.submitted)) {
            validobj.form();
        }
    });
//修改信息通知
	$("#messageButton").bind("click",function(){
		$(this).attr("disabled","disabled");
	//验证
	if(!validobj.form()){
		$("#messageButton").removeAttr("disabled");
		return false;
	}
	$('#messageContent').attr("value",editor.html());
	if($('#messageContent').val().length > 7500){
		layer.alert("亲!您输入的字符过长。");
		$("#messageButton").removeAttr("disabled");
		return false;
	}
	var checkboxIds=$('#checkboxId').val().substring(0,$('#checkboxId').val().length-1);
	$('#checkboxId').val(checkboxIds);
	$.ajax({
			url : "${contextPath}/message/editMessages",
			type : "post",
			dataType:"json",
			data : $("#dataForm").serialize(),
			success : function(result) {
				if(result.code=="success"){
		   			layer.alert(result.message,function(){
			   				document.location.href = "${contextPath}/message/query";
			   			});
				}else {
						layer.alert(result.message);
			   	}
			},
			error:function(){
				$("#messageButton").removeAttr("disabled");
			}
		});
	});
	
	//全部复选禁用按钮
		$('#relateClientUserIdbox').click(function(){
			   if($(this).prop("checked")){
			   	 	$('#relateClientUserIdButn').attr("disabled",true);
			   }else{
			    	$('#relateClientUserIdButn').attr("disabled",false);
			   }
		  });
		    //关联人的按钮
	  	$("#relateClientUserIdButn").bind("click",function(){
	  		var messageId = $('#messageId').val();
			var url = "${contextPath}/message/showMessageEditRelateClientUser/"+messageId;
			layer.open({
			    type: 2,
			    title: '关联人员',
			    closeBtn: 1,
			    area: ['750px', '583px'],
			    content: url
			});
		});
});
</script>
