<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>选择物料</title>
</head>
<body>
<div>
	<div class="widget-content nopadding">
		<form class="form-horizontal"  action="#" id="searchForm">
			<div class="control-group">
				<div class="fl">
					<label class="control-label " for="materialName">物料名称：</label>
					<div class="controls">
						<input type="text" name="materialName" value="${promotionMaterial.materialName!''}">
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
				<th>物料编号</th>
				<th>物料名称</th>
				<th>单位</th>
				<th>开始时间</th>
				<th>结束时间</th>
			</tr>
			<tbody>
				<#list pageParam.items as promotionMaterial>
					<tr>
						<td class="td_checkbox"><input name="chkItem" type="checkbox" class="checkboxStore" value="${promotionMaterial.materialId!''}" data="${promotionMaterial.materialName!''}"/></td>
						<td>${promotionMaterial.materialCode!''}</td>
						<td>${promotionMaterial.materialName!''}</td>
						<td>${promotionMaterial.unit!''}</td>
						<td>${promotionMaterial.startTime!''}</td>
						<td>${promotionMaterial.endTime!''}</td>
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
		var materialIds = stepPops.val();
		var array = materialIds.split(',');
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
			materialIds = stepPops.val();
			if($(this).prop("checked")){
				materialIds += $(this).val()+",";
				stepPops.attr("value",materialIds);
				stepPops.attr("value",materialIds);
				array = materialIds.split(',');
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
			if(!$(this).prop("checked")){
				var materialId = $(this).val();
				materialIds = stepPops.val();
				array = materialIds.split(',');
				array.splice($.inArray(materialId,array),1);
				materialIds = array.join(",");
				stepPops.attr("value",materialIds);
				stepPops.attr("value",materialIds);
				$("#checkCount").html(array.length-1);
				$("#stepTypeParameterCount",window.parent.document).html(array.length-1);
			}
		});
	});
</script>
</html>