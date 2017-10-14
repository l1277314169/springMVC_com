<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>选择类别</title>
</head>
<body>
<div>
	<div class="widget-content nopadding">
		<form class="form-horizontal" action="#" id="searchForm">
			<div class="control-group">
				<div class="fl">
					<label class="control-label " for="categoryName">品类名称：</label>
					<div class="controls">
						<input type="text" name="categoryName" value="${category.categoryName!''}">
					</div>
				</div>
			</div>
			<div class="form-actions">
				<span class="choice-text">您已选择：<span id ="checkCount" style="color:red"></span>个品类</span><input type="submit" value="查询" class="btn btn-info fr" id="search_btn"/>
			</div>
			<input type="hidden" name="key" value="${key}">
			<input type="hidden" name="page" value="${page}">
		</form>
	</div>
	<div class="widget-content nopadding">
		<table class="table table-bordered data-table" id="c_list">
			<tr>
				<th class="th_checkbox">选择</th>
				<th>品类编号</th>
				<th>品类名称</th>
				<th>品类名称(英文)</th>
			</tr>
			<tbody>
				<#list pageParam.items as category>
					<tr>
						<td><input name="chkItem" type="checkbox" class="checkboxStore" value="${category.categoryId!''}" data="${category.categoryName!''}"/></td>
						<td>${category.categoryNo!''}</td>
						<td>${category.categoryName!''}</td>
						<td>${category.categoryNameEn!''}</td>
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
		var categoryIds = stepPops.val();
		var array = categoryIds.split(',');
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
			categoryIds = stepPops.val();
			if($(this).prop("checked")){
				categoryIds += $(this).val()+",";
				stepPops.attr("value",categoryIds);
				stepPops.attr("value",categoryIds);
				array = categoryIds.split(',');
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
			if(!$(this).prop("checked")){
				var categoryId = $(this).val();
				categoryIds = stepPops.val();
				array = categoryIds.split(',');
				array.splice($.inArray(categoryId,array),1);
				categoryIds = array.join(",");
				stepPops.attr("value",categoryIds);
				stepPops.attr("value",categoryIds);
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
		});
	});
</script>
</html>