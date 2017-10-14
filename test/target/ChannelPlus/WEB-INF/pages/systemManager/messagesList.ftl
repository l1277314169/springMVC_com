<html>
<head>
<#include "/common/head.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/kindeditor-default.css">
<title>消息通知</title>
<script type="text/javascript" src="${contextPath}/js/kindeditor-min.js"></script>
<script type="text/javascript" src="${contextPath}/js/kindeditor-zh_CN.js"></script>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
		<#assign privCode="C3M3">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">系统设置</a>
					<a class="linkPage active" href="${contextPath}/message/query">消息通知管理</a>
				</div>
		 	</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="messageType">消息类型：</label>
							<div class="controls">
								<input id="appCode" name="messageType" class="input-medium" value="${messageType}">
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="enableDate">生效日期：</label>
							<div class="controls">
								<input type="text" id="enableDate" name="enableDate" class="input-medium" value="${enableDate}">
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="expiredDate">失效日期：</label>
							<div class="controls">
								<input type="text" id="expiredDate" name="expiredDate" class="input-medium" value="${expiredDate}">
							</div>
						</div>
				</div>
				
				<div class="form-actions">
				<@shiro.hasPermission name="C3M3F1">
					<button type="button" id="addBtn" class="btn btn-success">新增</button>
				</@shiro.hasPermission>	
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
					<th width="15%">消息主题</th>
					<th width="5%">消息类型</th>
					<th width="12%">生效日期</th>
					<th width="12%">失效日期</th>
					<th width="10%">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as message>
							<tr>
								<td><a href="javascript:void(0)" class="theme" title="预览" id="${message.messageId}">${message.messageTitle!''}</a></td>
								<td><#if message.messageType == 2>私信<#else>通知</#if></td>
								<td><#if (message.enableDate)??>${(message.enableDate)?string("yyyy-MM-dd")}</#if></td>
								<td><#if (message.expiredDate)??>${(message.expiredDate)?string("yyyy-MM-dd")}</#if></td>
								<!--<td  id="${message.messageId}_CBP" style="display:none"><h1>${message.messageTitle!''}</h1><lable>正文</lable><hr size="0">${message.messageContent!''}</td>-->
								<td>
									<@shiro.hasPermission name="C3M3F2">
										<a class="editMessage" href="javascript:void(0);" dataId="${message.messageId!''}" dataName="${message.messageTitle!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C3M3F3">
										<a class="deleteMessage" href="javascript:void(0);" data="${message.messageId!''}">删除</a>
									</@shiro.hasPermission>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
				<#if pageParam.items?exists> 
					<div class="paging" > 
					   ${pageParam.getPagination()}
					</div> 
				</#if> 
			</div>
		</div>
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
KindEditor.ready(function(K) {
});

var editDialog;
var addDialog;
var contentsDialog;
$(function(){
	//日期
	$("#enableDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#expiredDate").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
		
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
		}
	});
	//新增
	$("#addBtn").bind("click",function(){
		document.location.href = "${contextPath}/message/showAddMessages";
	});
		//编辑
	$("a.editMessage").bind("click",function(){
	    var messageId=$(this).attr("dataId");
	    var messageName=$(this).attr("dataName");
		var url = "${contextPath}/message/showEditMessages/"+messageId;
		 document.location.href = url;
	});
	
		//删除
	$("a.deleteMessage").bind("click",function(){
		var messageId=$(this).attr("data");
	   layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/message/deleteMessages/"+messageId,
				type : "get",
				async: false,
				dataType:'JSON',
				success : function(result) {
					 confirmAndRefresh(result); 
	
				}
			});
	   });
		   return false;
	});
	function confirmAndRefresh(result){
		if (result.code == "success") {
				window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
});

$('.theme').click(function(){
		var id=$(this).attr("id");
		var messageTitle=$(this).html();
		var url = "${contextPath}/message/showMessagesContents/"+id;
		contentsDialog = new xDialog(url, {}, {title:messageTitle,width:600,height:547});
		return false;
});

var msgData = [{ id: 1, text: '通知' }, { id: 2, text: '私信' }];
 $("#appCode").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: msgData
        });

</script>