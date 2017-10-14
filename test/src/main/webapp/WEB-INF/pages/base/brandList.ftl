<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>品牌</title>
</head>
<body class="main_body">
	<#assign privCode="C2M9">
	<#include "/base.ftl" />
	<div id="content"  ">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基础数据管理</a><a class="linkPage active" href="${contextPath}/brand/query">品牌管理</a>
			</div>
		</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label " for="brandNo">品牌编号：</label>
							<div class="controls">
							  <input id="brandNo" type="text" class="input-medium" name="brandNo" value="${brandNo!''}"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="searchBrandName">品牌名称：</label>
							<div class="controls">
							  <input id="searchBrandName" type="text" class="input-medium" name="searchBrandName" value="${searchBrandName!''}"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="isOwner">是否自有品牌：</label>
							<div class="controls">
							  <input type="text" id="isOwner" name="isOwner" value="${isOwner}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="parentId">上级品牌：</label>
							<div class="controls">
							  <input type="text" id="parentId" name="parentId" value="${parentId}">
							</div>
						</div>
					</div>
					<div class="form-actions  margin-top-18">
						<@shiro.hasPermission name="C2M9F1">
						<button type="button" id="new_btn" class="btn btn-success">新增</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M9F4">
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" name="parentId" value="${parentId!''}">
					<input type="hidden" id="isOwnerText" name="isOwner" value="0">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th width="10%">品牌编号</th>
						<th width="12%">品牌名称</th>
						<th width="12%">品牌英文名称</th>
						<th width="12%">上级品牌</th>
						<th width="12%">所属公司</th>
						<th width="10%">是否自有品牌</th>
						<th>备注</th>
						<th width="10%">操作</th>
					</tr>
					<tbody>
						<#list brandList as brand>
							<tr>
								<td>${brand.brandNo!''}</td>
								<td>${brand.brandName!''}</td>
								<td>${brand.brandNameEn!''}</td>
								<td>${brand.parentBrandName!''}</td>
								<td>${brand.firm!''}</td>
								<td><#if brand.isOwner == "1">是<#else>否</#if></td>
								
								<td title="${brand.remark!''}">
								<#if brand.remark ?? && brand.remark?length gt 16>
										${brand.remark?substring(0,10)}...
									<#else>
										${brand.remark!''}
									</#if>
								</td>
								<td>
								
								
									<@shiro.hasPermission name="C2M9F2">
									<a class="editStore" href="javascript:void(0);" dataId="${brand.brandId!''}" dataName="${brand.brandName!''}">编辑</a>
									</@shiro.hasPermission>
									<a class="moreDetails" href="javascript:void(0);" dataId="${brand.brandId!''}" dataName="${brand.brandName!''}">查看</a>
									<@shiro.hasPermission name="C2M9F3">
									<a class= "deleteStore"  href="javascript:void(0)};" data="${brand.brandId!''}">删除</a>
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
	loadParentBrand();
	//是否自主品牌 ，选中为是，不选中意为不是自主品牌
	$("#isOwner").bind("click",function(){
		if($(this).attr('checked')){
			$("#isOwnerText").val("1");
		}else{
			$("#isOwnerText").val("0");
		}
	});
	var brandData = [{ id: 0, text: '否' }, { id: 1, text: '是' }];
	$("#isOwner").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: brandData
	 });
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/brand/showImportDialog";
	//	importDialog = new xDialog(url, {}, {title:"品牌导入",width:700,height:330 });
	//	return true;
		 layer.open({
			    type: 2,
			    title: '品牌导入',
			    closeBtn: 1,
			    area: ['650px', '350px'],
			    content: url
			});
	});
	
	//新增
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/brand/showAddBrand";
		//addDialog = new xDialog(url, {}, {title:"新增品牌",width:700,height:330 });
		//return false;
		 layer.open({
			    type: 2,
			    title: '新增品牌',
			    closeBtn: 1,
			    area: ['730px', '400px'],
			    content: url
			});
			
	});
	//编辑
	$("a.editStore").bind("click",function(){
	    var brandId=$(this).attr("dataId");
	    var brandName=$(this).attr("dataName");
		var url = "${contextPath}/brand/showEditBrand/"+brandId;
		//editDialog = new xDialog(url,{},{title:"编辑品牌信息---"+brandName,width:700,height:330}); 
		//return false;	
		 layer.open({
			    type: 2,
			    title:'编辑品牌信息---'+brandName,
			    closeBtn: 1,
			    area: ['730px', '400px'],
			    content: url
			});
	});	
	//查看品牌详细信息
	$("a.moreDetails").bind("click",function(){
	    var brandId=$(this).attr("dataId");
	    var brandName=$(this).attr("dataName");
		var url = "${contextPath}/brand/showBrandDetail/"+brandId;
	//	moreDetailsDialog = new xDialog(url,{},{title:brandName+"信息",width:690,height:'auto'}); 
	//	return false;
		 layer.open({
			    type: 2,
			    title:brandName+"信息",
			    closeBtn: 1,
			      area: ['730px', '300px'],
			    content: url
			});	
	});	
	//删除
	$("a.deleteStore").bind("click",function(){
		var brandId =$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/brand/deleteBrand/"+brandId,
				type : "get",
				async: false,
				dataType:'JSON',
				data : {brandId : brandId},
				success : function(result) {
					 confirmAndRefresh(result);
				}
			});
	   });
		   return false;
	});
	
	
	//品牌层级
	$("#grade").bind("change",function(){
		if($(this).val() == "1"){
			$("#searchForm").submit();
			$(this).val("");
		}else{
			$("#searchForm").submit();
			$(this).val("");
		}
	
	});
	
	function confirmAndRefresh(result){
		if (result.code == "success") {
				window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//$.alert(result.message);
			}});
		}
	}
});

function loadParentBrand(){
	$.ajax({
			type : "post",
			url : "${contextPath}/brand/loadParentBrand",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				if(jsonData != null){
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"},";
						} else {
							thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"}";
						}
					});
					thisData += "]";
				}
					var cData = $.parseJSON(thisData);
					if(cData == null){
						cData = [];
					}
					$("#parentId").select2({
						width:145,
						placeholder: "请选择",
						allowClear: true,
						data: cData
					});
			},
			error : function(data) {
				alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
}
</script>
