<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>选择产品</title>
</head>
<body>
<div>
	<div class="widget-content nopadding">
		<form class="form-horizontal" action="#" id="searchForm">
			<div class="control-group">
				<div class="fl">
					<label class="control-label " for="skuName">sku名称：</label>
					<div class="controls">
					  <input type="text" name="skuName" value="${sku.skuName!''}">
					</div>
				</div>
			</div>
			<div class="form-actions">
				<span class="choice-text">您已选择：<span id ="checkCount" style="color:red"></span>个产品</span><input type="submit" value="查询" class="btn btn-info fr" id="search_btn"/>
			</div>
			<input type="hidden" name="key" value="${key}">
			<input type="hidden" name="page" value="${page}">
		</form>
	</div>
	<div class="widget-content nopadding">
		<table class="table table-bordered data-table" id="c_list">
			<tr>
				<th class="th_checkbox">选择</th>
				<th>产品编码</th>
				<th>产品名称</th>
				<th>品牌</th>
				<th>类别</th>
				<th>价格</th>
			</tr>
			<tbody>
				<#list pageParam.items as sku>
					<tr>
						<td><input name="chkItem" type="checkbox" class="checkboxStore" value="${sku.skuId!''}" data="${sku.skuName!''}"/></td>
						<td>${sku.skuNo!''}</td>
						<td>${sku.skuName!''}</td>
						<td>${sku.brandName!''}</td>
						<td>${sku.categoryName!''}</td>
						<td>${sku.price!''}</td>
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
</body>
<script type="text/javascript" language="javascript">
	$(document).ready(function () {
		//初始化选中框
		var stepPops = $("input[name ^='visitingTaskSteps[${step}].taskStepParameterDatas']",window.parent.document);
		var skuIds = stepPops.val();
		var array = skuIds.split(',');
		$("#checkCount").html(array.length-1);
		
        $("[name = chkItem]:checkbox").each(function () {
        	for (var index in array) {
                if ($(this).val() == array[index]) {
                    $(this).attr("checked", "checked");
                    break;
                }
            }
     	});
		
		$(".checkboxStore").click(function(){
			//alert($(this).prop("checked"));
			skuIds = stepPops.val();
			if($(this).prop("checked")){
				skuIds += $(this).val()+",";
				stepPops.attr("value",skuIds);
				stepPops.attr("value",skuIds);
				array = skuIds.split(',');
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
			if(!$(this).prop("checked")){
				var skuId = $(this).val();
				skuIds = stepPops.val();
				array = skuIds.split(',');
				array.splice($.inArray(skuId,array),1);
				skuIds = array.join(",");
				stepPops.attr("value",skuIds);
				stepPops.attr("value",skuIds);
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
		});
	});
</script>
</html>