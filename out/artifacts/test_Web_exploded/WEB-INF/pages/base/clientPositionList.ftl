<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>职位管理</title>
</head>
<body class="main_body">
		<#assign privCode="C2M2">
	    <#include "/base.ftl" />
	
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基本数据管理</a>
				<a class="linkPage active" href="${contextPath}/clientPosition/query">职位管理</a>
			</div>
	 	</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="positionNo">职位编号：</label>
						<div class="controls">
							<input type="text" name="positionNo" class="input-medium" value="${positionNo!''}">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="searchPositionName">职位名称：</label>
						<div class="controls">
							<input type="text" name="searchPositionName" class="input-medium" value="${searchPositionName!''}">
						</div>
					</div>
				</div>
				<div class="form-actions">
				<@shiro.hasPermission name="C2M2F1">
					<button type="button" id="new_btn" class="btn btn-success">新增</button>
				</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th width="25%">职位编号</th>
					    <th>职位名称</th>
					    <th>职位英文名称</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientPosition>
						<tr>
							<td>${(clientPosition.positionNo)!''}</td>
								<td>${(clientPosition.positionName)!''}</td>
								<td>${(clientPosition.positionNameEn)!''}</td>
								<td>
									<@shiro.hasPermission name="C2M2F2">
									<a class="editClientPosition" href="javascript:void(0);" dataId="${clientPosition.clientPositionId!''}" dataName="${clientPosition.positionName!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M2F3">
									<a class="deleteClientPosition" href="javascript:void(0);"  data="${clientPosition.clientPositionId!''}">删除</a>
									</@shiro.hasPermission>
									<a class="checkClientPosition" href="javascript:void(0);"  dataId="${clientPosition.clientPositionId!''}" dataName="${clientPosition.positionName!''}">查看</a>
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
<script>
var editDialog, addDialog;

$(function(){	
	//新增
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/clientPosition/showAddClinetPostion";
		// addDialog = new xDialog(url, {}, {title:"新增职位",width:600,height:295 });
		layer.open({
			    type: 2,
			    title: '新增职位',
			    closeBtn: 1,
			    area: ['600px', '295px'],
			    content: url
			});
		return false;	
	});
	//编辑
	$("a.editClientPosition").bind("click",function(){
	    var clientPositionId=$(this).attr("dataId");
	    var positionName=$(this).attr("dataName");
		var url = "${contextPath}/clientPosition/showEditClientPosition/"+clientPositionId;
		// editDialog = new xDialog(url,{},{title:"编辑职位---"+positionName,width:600,height:295});
		 layer.open({
			    type: 2,
			    title: '编辑职位---'+positionName,
			    closeBtn: 1,
			    area: ['600px', '295px'],
			    content: url
			}); 
		return false;	
	});	
	//查看
	$("a.checkClientPosition").bind("click",function(){
	    var clientPositionId=$(this).attr("dataId");
	    var positionName=$(this).attr("dataName");
		var url = "${contextPath}/clientPosition/showCheckClientPosition/"+clientPositionId;
		// checkDialog = new xDialog(url,{},{title:"查看职位---"+positionName,width:400,height:'auto'});
		   layer.open({
			    type: 2,
			    title: '查看职位---'+positionName,
			    closeBtn: 1,
			    area: ['400px', '243px'],
			    content: url
			}); 
		return false;	
	});	
	
	//删除
	$("a.deleteClientPosition").bind("click",function(){
		var clientPositionId=$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/clientPosition/deleteClinetPostion/"+clientPositionId,
				type : "get",
				async: false,
				data : {clientPositionId : clientPositionId},
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
</script>