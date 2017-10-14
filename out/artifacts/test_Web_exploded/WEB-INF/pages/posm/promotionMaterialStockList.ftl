<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>仓库明细</title>
</head>
<body class="main_body">

	<#assign privCode="C6M2">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">POSM管理</a>
				<a class="linkPage active" href="${contextPath}/promotionMaterialStock/query ">库存明细</a>
			</div>
	 	</div>

<div class="boloc_moveBar" style="display:none" ><h2>更新中，请稍等……</h2></div> 
	
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
				<label class="control-label fl" for="warehouseId">仓库：</label>
							<div class="controls">
								<input id="warehouseId" type="text" class="input-medium"  required ="required"  name="warehouseId"  value ="${promotionMaterialStockVo.warehouseId!''}" >
							</div>
						</div>		
						<div class="fl">
							<label class="control-label " for="materialCode">POSM编号：</label>
							<div class="controls">
							  <input id="materialCode" type="text" class="input-medium" name="materialCode" value="${promotionMaterialStockVo.materialCode!''}" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="materialName">POSM名称：</label>
							<div class="controls">
								<input id="materialName" type="text" class="input-medium" name="materialName" value ="${promotionMaterialStockVo.materialName!''}">
							</div>
						</div>					
					</div>
	              	
	              	<div class="form-actions  margin-top-18">
	              	<@shiro.hasPermission name="C6M2F1">
                      <button type="button" id="importBtn3" class="btn btn-primary">导出</button>
                  	</@shiro.hasPermission>
                     <input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
				</form>
			</div>
			<div class="widget-content nopadding" style="overflow-x:auto;margin-bottom: 15px;">
					<table class="table table-bordered data-table" id="c_list">
	    				<tr>
							<th class="fill_td">大区</th>
							<th class="fill_td">城市</th>
							<th class="fill_td">仓库所在地</th>
							<th class="fill_td">品牌</th>
							<th class="fill_td">子分类</th>
							<th class="fill_td">物料分类</th>
							<th class="fill_td">关键节点</th>
							<th class="fill_td">物料名称</th>
							<th class="fill_td">年份</th>
							<th class="fill_td">POSMCode</th>
							<th class="fill_td">数量(箱)</th>
							<th class="fill_td">数量(个)</th>
							<th class="fill_td">单价</th>
							<th class="fill_td">库存金额</th>
							<th class="fill_td">数据维护时间</th>
							<th class="fill_td">备注</th>
						</tr>
					<#if pageParam.items??>
						<#list pageParam.items as s>
						<tr>
							<td class="fill_td">${s.structureName}</td>
							<td class="fill_td">${s.city}</td>
									<td title="${s.details!''}">
								<#if s.details ?? && s.details?length gt 12>
										${s.details?substring(0,7)}...
									<#else>
										${s.details!''}
									</#if>
							</td>
							<td class="fill_td">${s.brand}</td>
							<td class="fill_td">${s.subCategory}</td>
							<td class="fill_td">${s.category}</td>
							<td class="fill_td"></td>
							<td class="fill_td">${s.materialName}</td>
							<td class="fill_td">${s.batch}</td>
							<td class="fill_td">${s.materialCode}</td>
							<td class="fill_td">${s.spec}</td>
							<td class="fill_td">${s.quantity}</td>
							<td class="fill_td">${s.price}</td>
							<td class="fill_td"><#if s.price??&&s.quantity??>${s.price*s.quantity}</#if></td>
							<td class="fill_td">${s.lastUpdateTime?string("yyyy年MM月dd日")}</td>
							<td class="fill_td">${s.remark}</td>
						</tr>
						</#list>
						</#if>
					</table>
					<#if pageParam.items?exists> 
						<div class="paging" > 
						   ${pageParam.getPagination()}
						</div> 
					</#if>
			</div>
		</div>
		</div>
			<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
$(function(){
loadPosition();

})
//导出
	$("#importBtn3").bind("click",function(){
		var url = "${contextPath}/promotionMaterialStock/importPosmStock";
		layer.confirm("确认导出", function () {
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				$('#searchForm').attr("action","${contextPath}/promotionMaterialStock/query");
		});
		return false;
	});
	
	
function loadPosition(positionId){
	$.ajax({
		type : "post",
		url : "${contextPath}/promotionMaterialStock/getWareHouseAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			$("#warehouseId").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: data
			
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};
</script>