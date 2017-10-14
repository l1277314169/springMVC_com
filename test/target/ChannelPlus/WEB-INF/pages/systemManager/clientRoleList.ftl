<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
<title>角色管理</title>
</head>
<body class="main_body">

		<#assign privCode="C3M2">
	<#include "/base.ftl" />
	
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">系统设置</a>
				<a class="linkPage active" href="${contextPath}/clientRoles/query">角色管理</a>
			</div>
	 	</div>
	 	
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
							<div class="fl">
								<label class="control-label fl" for="searchClientRoleName">角色名称：</label>
								<div class="controls">
									 <input type="text" name="searchClientRoleName" class="input-medium" value="${searchClientRoleName!''}">
								</div>
							</div>
					</div>
					<div class="form-actions">
						<@shiro.hasPermission name="C3M2F1">
						<button type="button" id="new_btn" class="btn btn-success">新增</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C3M2F4">
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th width="22%">角色名称</th>
						<th width="22%">角色名称(英文)</th>
						<th width="22%">web登录权限</th>
						<th width="22%">app登录权限</th>
						<th>操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientRole>
							<tr>
								<td>${clientRole.roleName!''}</td>
								<td>${clientRole.roleNameEn!''}</td>
								<td>
									<#if clientRole.webLogin =="1">
									 有
									<#else >
									 无
									</#if>
									</td>
								<td>
									<#if clientRole.appLogin =="1">
									 有
									<#else >
									 无
									</#if>
								</td>
								<td>
									<@shiro.hasPermission name="C3M2F2">
									<a class="editRole" href="javascript:void(0);" dataId="${clientRole.roleId!''}" dataName="${clientRole.roleName!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C3M2F3">
									<a class="deleteRole" href="javascript:void(0)};" data="${clientRole.roleId!''}">删除</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C3M2F5">
									<a class="showRolePrivileges" href="javascript:void(0)};" dataId="${clientRole.roleId!''}" dataName="${clientRole.roleName!''}">分配权限</a>
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
				<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
				</div>
		</div>
	
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
var importDialog,addDialog,editDialog,showRolePrivileges;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/clientRoles/showImportDialog";
		layer.open({
			    type: 2,
			    title:'角色导入',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['620px', '373px'],
			    content: url
			});
		//importDialog = new xDialog(url, {}, {title:"角色导入",width:650,height:330 });
		return true;
	});
	//新增
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/clientRoles/showAddRole";
		layer.open({
			    type: 2,
			    title:'新增用户',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['650px', '337px'],
			    content: url
			});
		//addDialog = new xDialog(url, {}, {title:"新增角色",width:500,height:273});
		
		return false;	
	});
	
	//编辑
	$("a.editRole").bind("click",function(){
	    var roleId=$(this).attr("dataId");
	    var roleName=$(this).attr("dataName");
		var url = "${contextPath}/clientRoles/showEditRole/"+roleId;
		layer.open({
			    type: 2,
			    title:'编辑用户',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['650px', '337px'],
			    content: url
			});
		//editDialog = new xDialog(url,{},{title:"编辑品牌信息--"+roleName,width:500,height:273}); 
		return false;	
	});	
	
	//分配权限
	$("a.showRolePrivileges").bind("click",function(){
	    var roleId=$(this).attr("dataId");
	    var roleName=$(this).attr("dataName");
		var url = "${contextPath}/clientRoles/showRolePrivileges/"+roleId;
		layer.open({
			    type: 2,
			    title:roleName+"--分配权限",
			    closeBtn: 1, //不显示关闭按钮
			   // shadeClose: true, 
			    area: ['750px', '500px'],
			    content: url
			});
		//showRolePrivileges = new xDialog(url,{},{title:roleName+"--分配权限",width:750,height:500}); 
		return false;	
	});	

	//删除
	$("a.deleteRole").bind("click",function(){
		var roleId =$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/clientRoles/deleteClientRole/"+roleId,
				type : "get",
				async: false,
				dataType:'JSON',
				data : {roleId : roleId},
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
