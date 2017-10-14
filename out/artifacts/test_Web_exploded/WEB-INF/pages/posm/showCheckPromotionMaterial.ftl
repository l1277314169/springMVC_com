<html>
<head>
<title></title>
<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />

</head>
<body>

<table class="table_white_bg">
            <tbody> 
                <tr>
                    <td class="td_label" ><i class="cc1">*</i>物料编号：</td>
                    <td class="td_control">
                   	 <input type="text" id="materialId" name="materialId" value="${promotionMaterial.materialCode!''}"  disabled="disabled" class="required" >
                    </td>
                    <td class="td_label"><i class="cc1">*</i>物料名称：</td>
                    <td class="td_control">
                     <input type="text" id="materialId" name="materialId" value="${promotionMaterial.materialName!''}"  disabled="disabled" class="required" >
                    </td>
                </tr>
                    <tr>
                    <td class="td_label">物料分类：</td>
                    <td class="td_control">
                    	 <input type="text" id="materialId" name="materialId" value="${promotionMaterial.category!''}"  disabled="disabled" class="required" >
                    </td>
                    <td class="td_label">子分类：</td>
                    <td class="td_control">
                        <input type="text" id="materialId" name="materialId" value="${promotionMaterial.subCategory!''}"  disabled="disabled" class="required" >
                    </td>
                </tr>
               <tr>
                    <td class="td_label">单价：</td>
                    <td class="td_control">
                     <input type="text" id="materialId" name="materialId" value="${promotionMaterial.price!''}"  disabled="disabled" class="required" >
                    </td>
                    <td class="td_label">品牌：</td>
                    <td  class="td_control">
                    	 <input type="text" id="materialId" name="materialId" value="${promotionMaterial.brand!''}"  disabled="disabled" class="required" >
                    </td>
                </tr>
				<tr>
                    <td class="td_label">关键节点：</td>
                    <td class="td_control">
                      <input type="text" id="materialId" name="materialId" value="${promotionMaterial.spec!''}"  disabled="disabled" class="required" >
                    </td>
                    <td class="td_label">年份：</td>
                    <td  class="td_control">
                       <input type="text" id="materialId" name="materialId" value="${promotionMaterial.batch!''}"  disabled="disabled" class="required" >
                    </td>
                </tr>
                <tr>
                   <td class="td_label">状态：</td>
                     <td class="td_control">
                       <input type="text" id="status" name="status" value="<#if promotionMaterial.status ?? && promotionMaterial.status == '0'>0<#else>1</#if>"/>
                    </td>
                </tr>
                <tr>
                    <td class="td_label" ><i class="cc1">*</i>备注：</td>
                    <td class="td_control" colspan="4" valign="middle">
                    	<textarea rows=4  maxlength="300" name="remark" placeholder="不超过200个字" class="required" disabled="disabled">${promotionMaterial.remark}</textarea>
                    </td>
                </tr>
             <tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger" style="margin-left:-151px;">关闭</button>
			</td>
	   </tr>   
    </tbody>
</table>

<script>
$(function() {
	var status = $("#status").val();
	loadStatus(status);

	
});
function loadStatus(status) {
	$.ajax({
		type: "post",
		url: "${contextPath}/promotionMaterial/getType",
		dataType: "json",
		cache: false,
		success: function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData,
			function(index, item) {
				if (index != jsonData.length - 1) {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"},";
				} else {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#status").select2({
				width: 145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error: function(data) {
			layer.alert("数据加载失败！");
		}
	});
};
</script>
</body>
</html>