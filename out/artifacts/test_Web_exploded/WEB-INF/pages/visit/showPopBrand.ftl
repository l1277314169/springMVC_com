<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>选择品牌</title>
</head>
<body>
<div>
	<div class="widget-content nopadding">
		<form class="form-horizontal" action="#" id="searchForm">
			<div class="control-group">
				<div class="fl">
					<label class="control-label " for="brandName">品牌名称：</label>
					<div class="controls">
					  <input type="text" name="brandName" value="${brand.brandName!''}">
					</div>
				</div>
			</div>
			<div class="form-actions">
				<span class="choice-text">您已选择：<span id ="checkCount" style="color:red"></span>个品牌</span><input type="submit" value="查询" class="btn btn-info fr" id="search_btn"/>
			</div>
			<input type="hidden" name="key" value="${key}">
			<input type="hidden" name="page" value="${page}">
		</form>
	</div>
	<div class="widget-content nopadding">
		<table class="table table-bordered data-table" id="c_list">
			<tr>
				<th class="th_checkbox">选择</th>
				<th>品牌编号</th>
				<th>品牌名称</th>
				<th>是否自有品牌</th>
				<th>所属公司</th>
			</tr>
			<tbody>
				<#list pageParam.items as brand>
					<tr>
						<td><input name="chkItem" type="checkbox" class="checkboxStore" value="${brand.brandId!''}" data="${brand.brandName!''}"/></td>
						<td>${brand.brandNo!''}</td>
						<td>${brand.brandName!''}</td>
						<td><#if brand.isOwner=="0" >否<#elseif brand.isOwner=="1">是</#if></td>
						<td>${brand.firm!''}</td>
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
		var brandIds = stepPops.val();
		var array = brandIds.split(',');
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
			brandIds = stepPops.val();
			if($(this).prop("checked")){
				brandIds += $(this).val()+",";
				stepPops.attr("value",brandIds);
				stepPops.attr("value",brandIds);
				array = brandIds.split(',');
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
			if(!$(this).prop("checked")){
				var brandId = $(this).val();
				brandIds = stepPops.val();
				array = brandIds.split(',');
				array.splice($.inArray(brandId,array),1);
				brandIds = array.join(",");
				stepPops.attr("value",brandIds);
				stepPops.attr("value",brandIds);
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
		});
	});
</script>
</html>