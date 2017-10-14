<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<title>手机版本管理</title>
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
</head>
<body class="main_body">
		 <#assign privCode="C3M1"> 
		 <#include "/base.ftl" />
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">系统设置</a>
					<a class="linkPage active" href="${contextPath}/mobileVersion/query">手机版本管理</a>
				</div>
		 	</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="version">APP版本号：</label>
							<div class="controls">
								<input type="text" name="version" class="input-medium" value="${version!''}">
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="appcode">APP类型：</label>
							<div class="controls">
								<select id="appCode" name="appcode" class="input-medium" >
			                    		<option value="">--请选择--</option>
					                    <#list appType as option>
					                    <#if appcode == option.getKey()>
				        					<option value="${option.getKey()}" selected="selected">${option.getCnName()}</option>
				        					<#else>
				        					<option value="${option.getKey()}" >${option.getCnName()}</option>
				        					</#if>
				        				</#list>	
			                    </select>
							</div>
						</div>
				</div>
				<div class="form-actions">
				<@shiro.hasPermission name="C3M1F1">
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
					<th width="10%">APP类型</th>
					<th width="10%">APP版本号</th>
					<th width="12%">生效时间</th>
					<th width="23%">下载</th>
					<th>版本说明</th>
					<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as mobileVersion>
							<tr>
								<td><#if mobileVersion.appCode == 1>Android
								   				<#elseif mobileVersion.appCode == 2>iPhone
								   					<#elseif mobileVersion.appCode == 3>iPad
								   					</#if>
								</td>
								<td>${mobileVersion.version!''}</td>
								<td><#if (mobileVersion.enableDate)??>${(mobileVersion.enableDate)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
								<td>${mobileVersion.appLink!''}</td>
								<td>${mobileVersion.versionDesc!''}</td>
								<td>
									<@shiro.hasPermission name="C3M1F2">
										<a class="editMobileVersion" href="javascript:void(0);" dataId="${mobileVersion.mobileVersionId!''}" dataName="${mobileVersion.version!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C3M1F3">
										<a class="deleteMobileVersion" href="javascript:void(0);" data="${mobileVersion.mobileVersionId!''}">删除</a>
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
var editDialog;
var importDialog;
var addDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/channel/showImportDialog";
		//importDialog = new xDialog(url, {}, {title:"手机版本导入",width:650,height:330});
		//return true;
		 layer.open({
			    type: 2,
			    title:'手机版本导入',
			    closeBtn: 1,
			    area: ['700px', '500px'],
			    content: url
				});	
		
	});
	//新增
	$("#addBtn").bind("click",function(){
	    var url = "${contextPath}/mobileVersion/showAddMobileVersion";
		//addDialog = new xDialog(url, {}, {title:"新增手机版本",width:650,height:430});
		//return false;	
		window.location ="${contextPath}/mobileVersion/showAddMobileVersion";
	});
		//编辑
	$("a.editMobileVersion").bind("click",function(){
	    var mobileVersionId=$(this).attr("dataId");
	    var mobileVersionName=$(this).attr("dataName");
		var url = "${contextPath}/mobileVersion/showEditMobileVersion/"+mobileVersionId;
		//editDialog = new xDialog(url,{},{title:"编辑手机版本-"+mobileVersionName,width:650,height:430});
		//return false;	
		window.location ="${contextPath}/mobileVersion/showEditMobileVersion/"+mobileVersionId;
	});
	
		//删除
	$("a.deleteMobileVersion").bind("click",function(){
		var mobileVersionId=$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/mobileVersion/deleteMobileVersion/"+mobileVersionId,
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


</script>