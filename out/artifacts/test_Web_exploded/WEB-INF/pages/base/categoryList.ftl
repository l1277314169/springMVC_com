<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>品类</title>
</head>
<body class="main_body">
<#assign privCode="C2M10">
<#include "/base.ftl" />
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb">
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基础数据管理</a><a class="linkPage active" href="${contextPath}/category/query">品类管理</a>
			</div>
		</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
								<label class="control-label fl" for="searchCategoryNo">品类编号：</label>
								<div class="controls">
								  <input type="text" name="searchCategoryNo" class="input-medium" value="${searchCategoryNo!''}"/>
								</div>
						</div>
						<div class="fl">
								<label class="control-label fl" for="searchCategoryName">品类名称：</label>
								<div class="controls">
								  <input type="text" name="searchCategoryName" class="input-medium" value="${searchCategoryName!''}"/>
								</div>
						</div>
					</div>
					<div class="form-actions  margin-top-18">
						<@shiro.hasPermission name="C2M10F1">
						<button type="button" id="new_btn" class="btn btn-success">新增</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M10F4">
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
						<th width="20%">品类编号</th>
						<th width="20%">品类名称</th>
						<th width="20%">品类英文名称</th>
						<th  width="20%">备注</th>
						<th >操作</th>
					</tr>
					<tbody>
						<#list categoryList as category>
							<tr>
								<td>${category.categoryNo!''}</td>
								<td>${category.categoryName!''}</td>
								<td>${category.categoryNameEn!''}</td>
											<td title="${category.remark!''}">
								<#if category.remark ?? && category.remark?length gt 16>
										${category.remark?substring(0,10)}...
									<#else>
										${category.remark!''}
									</#if>
								</td>
								<td>
									<@shiro.hasPermission name="C2M10F2">
									<a class="editCategory" href="javascript:void(0);" dataId="${category.categoryId!''}" dataName="${category.categoryName!''}">编辑</a>
									</@shiro.hasPermission>
									<a class="moreDetails" href="javascript:void(0);" dataId="${category.categoryId!''}" dataName="${category.categoryName!''}">查看</a>
									<@shiro.hasPermission name="C2M10F3">
									<a class= "deleteCategory"  href="javascript:void(0)};" data="${category.categoryId!''}">删除</a>
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
		<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
		</div>
</div>
<#include "/footer.ftl" />
</body>
</html>

<script type="text/javascript">
var importDialog,addDialog,editDialog,moreDetailsDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/category/showImportDialog";
		//importDialog = new xDialog(url, {}, {title:"品类导入",width:650,height:330 });
		//return true;
			 layer.open({
			    type: 2,
			    title: '品类导入',
			    closeBtn: 1,
			    area: ['650px', '350px'],
			    content: url
			});
	});
	//新增
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/category/showAddCategory";
		 layer.open({type: 2,title: '新增品类',closeBtn: 1,area: ['750px', '330px'],content: url});
	});
	//编辑
	$("a.editCategory").bind("click",function(){
	    var categoryId=$(this).attr("dataId");
	    var categoryName=$(this).attr("dataName");
		var url = "${contextPath}/category/showEditCategory/"+categoryId;
	    layer.open({type: 2,title:'编辑品类信息---'+categoryName,closeBtn: 1,area: ['750px', '330px'],content: url});
	});	
	//查看品类详细信息
	$("a.moreDetails").bind("click",function(){
	    var categoryId=$(this).attr("dataId");
	    var categoryName=$(this).attr("dataName");
		var url = "${contextPath}/category/showCategoryDetail/"+categoryId;
	//	moreDetailsDialog = new xDialog(url,{},{title:categoryName+"信息",width:600,height:200}); 
	//	return false;
			 layer.open({
			    type: 2,
			    title:categoryName+"信息",
			    closeBtn: 1,
			    area: ['750px', '280px'],
			    content: url
				});		
	});	

	//删除
	$("a.deleteCategory").bind("click",function(){
		var categoryId =$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/category/deleteCategory/"+categoryId,
				type : "get",
				async: false,
				dataType:'JSON',
				data : {categoryId : categoryId},
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
