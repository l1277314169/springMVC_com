<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body>
		<div style="height:10px;"></div>
		<div>
			<form class="form-search" action="#" id="searchForm">
				<div style="height:50px;">
					<span class="search_item">渠道名称：<input type="text" name="searchBrandName" class="input-medium" value="${searchBrandName!''}"> </span>
				</div>
				<div class="fun_button">
					<button type="button" class="btn_green" id="">导入</button>
					<button type="submit" class="btn_search" id="search_btn">查询</button>
				</div>
				<input type="hidden" name="page" value="${page}">
			</form>
			
			<div class="div_list_show">
				<table class="table table-bordered c_list" id="c_list">
					<tr>
						<th width="15%">一级渠道名称</th>
						<th width="15%">二级渠道名称</th>
						<th width="15%">系统名称</th>
						<th width="15%">分销产品数量</th>
						<th>备注</th>
						<th width="10%">操作</th>
					</tr>
					<tbody>
						<#list brandList as brand>
							<tr>
								<td>${brand.brandNo!''}</td>
								<td>${brand.brandName!''}</td>
								<td>${brand.brandNameEn!''}</td>
								<td>${brand.firm!''}</td>
								<td>${brand.remark!''}</td>
								<td>
									<a class="editStore" href="javascript:void(0);" dataId="${brand.brandId!''}" dataName="${brand.brandName!''}">编辑</a>
									<a class= "deleteStore"  href="javascript:void(0)};" data="${brand.brandId!''}">删除</a>
								</td>
							</tr>
						</#list>
					</tbody>
					<tfoot>
						<tr>
							<td style="background-color:#EEE;" colspan="10">
								<#if pageParam.items?exists> 
									<div class="paging" > 
									   ${pageParam.getPagination()}
									</div> 
								</#if>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
		</div>
</body>
</html>

<script type="text/javascript">
var importDialog,addDialog,editDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/store/showImportDialog";
		importDialog = new xDialog(url, {}, {title:"门店导入",width:650,height:330 });
		return true;
	});
	
	//新增
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/brand/showAddBrand";
		addDialog = new xDialog(url, {}, {title:"新增品牌",width:600,height:350 });
		return false;	
	});
	
	
	
	//编辑
	$("a.editStore").bind("click",function(){
	    var brandId=$(this).attr("dataId");
	    var brandName=$(this).attr("dataName");
		var url = "${contextPath}/brand/showEditBrand/"+brandId;
		editDialog = new xDialog(url,{},{title:"编辑品牌信息---"+brandName,width:650,height:350}); 
		return false;	
	});	


	//删除
	$("a.deleteStore").bind("click",function(){
		var brandId =$(this).attr("data");
	    $.confirm("确认删除吗？", function () {
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
	
	function confirmAndRefresh(result){
		if (result.code == "success") {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				$("#searchForm").submit();
			}});
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
	
	});
	
	
	
</script>
