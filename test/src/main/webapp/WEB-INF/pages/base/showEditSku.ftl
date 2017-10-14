<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${sku.clientId!''}">
		<input type="hidden" name="skuId" value="${sku.skuId!''}">
		
        <table class="table_white_bg">
            <tbody >
            	 <tr >
                   <td class="td_label"><i class="cc1">*</i>产品名称：</td>
                   <td class="td_control"  title="${sku.skuName!''}">
                  		<input type="text" name="skuName" value="${sku.skuName!''}">
				   </td>
                   <td class="td_label">产品英文名称：</td>
                   <td class="td_control">
                    	<input type="text" name="skuNameEn" value="${sku.skuNameEn!''}">
                   </td>
                 </tr>
                 <tr >
                   <td class="td_label" >产品编码：</td>
                   <td class="td_control">
                  		<input type="text" name="skuNo" value="${sku.skuNo!''}">
				   </td>
                   <td class="td_label" >产品简称：</td>
                   <td class="td_control">
                    	<input type="text" name="skuNameAbbr" value="${sku.skuNameAbbr!''}">
                   </td>
                 </tr>
                 <tr >
                   <td class="td_label">产品条码:</td>		
                   <td class="td_control">
                   	<input type="text" name="barcode" value="${sku.barcode!''}">
                   </td>
                   <td class="td_label">产品价格：</td>
                   <td class="td_control">
                  		<input type="text" name="price" value="${sku.price!''}">
				   </td>
                 </tr>
                 <tr >
                   <td class="td_label" ><i class="cc1">*</i>品牌：</td>
                   <td class="td_control">
                    	<input type="hidden" id="brandId" name="brandId" value="${sku.brandId!''}" >
                    	<#assign brandArg="brandId">
					  	<#include "/widgets/brand_widget.ftl" />
				   </td>
                   <td class="td_label" ><i class="cc1">*</i>品类：</td>
                   <td class="td_control">
                    	<input type="hidden" id="categoryId" name="categoryId" value="${sku.categoryId!''}" >
                    	<#assign categoryArg="categoryId">
					  	<#include "/widgets/category_widget.ftl" />
                   </td>
                 </tr>
                 <tr >
                   <td class="td_label" >产品规格：</td>
                   <td class="td_control">
                  		<input type="text" name="spec" value="${sku.spec!''}">
				   </td>
                   <td class="td_label">包装规格：</td>
                   <td class="td_control">
                    	<input type="text" name="packSpec" value="${sku.packSpec!''}">
                   </td>
                    
                 </tr>
                 <tr >
                   <td class="td_label">产品重量：</td>
                   <td class="td_control">
                  		<input type="text" name="weight" value="${sku.weight!''}">
				   </td>
                   <td class="td_label" >产品体积：</td>
                   <td class="td_control">
                    	<input type="text" name="volume" value="${sku.volume!''}">
                   </td>
                 </tr>
                 
                 <tr >
                   <td class="td_label"><i class="cc1">*</i>单位：</td>
                   <td class="td_control">
                   		<select name="unitId" value="${sku.unitId!''}">
                   			<option value="">--请选择--</option>
	                    	<#list unList as unit>
	                    		<#if sku.unitId == unit.unitId>
	                    		<option value = "${unit.unitId!''}" selected = "selected">${unit.subUnitName!''}</option>
	                    		<#else>
	                    		<option value = "${unit.unitId!''}">${unit.subUnitName!''}</option>
	                    		</#if>
	                    	</#list>
                  		</select>
				   </td>
				   <td class="td_label">产品分组</td>
				   <td class="td_control">
				   		<select name="skuGroupId" value="skuGroupId">
				   			<option value="">--请选择--</option>
				   			<#list sgList as skuGroup>
					   			<#if sku.groupName == skuGroup.groupName>
					   				<option value ="${skuGroup.skuGroupId!''}" selected="selected">${sku.groupName!''}</option>
					   			<#else>
					   				<option value ="${skuGroup.skuGroupId!''}">${skuGroup.groupName!''}</option>
					   			</#if>
				   			</#list>	
				   		</select>
				   </td>
                 </tr>
                  <tr>
                   <td class="td_label">产品分类:</td>
                    <td class="td_control">
                   	  <input id="skutypeid" type="text" class="input-medium" name="skutypeid"   value="${sku.skuTypeId!''}"  />
                      <#include "/widgets/skuType.ftl" />
                   </td>
                    <td class="td_label">产品系列:</td>
                   <td class="td_control">
                    <input id="skuSeriesId" type="text" name="skuSeriesId" class="input-medium"  value="${sku.skuSeriesId!''}"  />
                    	<#include "/widgets/skuSeries.ftl" />
                   </td>
				    
                 </tr>
                 
                 <tr>
                   <td class="td_label"><i class="cc1">*</i>是否主打：</td>
                   <td class="td_control">
                   		<#if sku.isMain == "1">
	                    	<input type="radio" name="isMain" value="1" checked="true">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<input type="radio" name="isMain" value="0">否
                    	<#else>
	                    	<input type="radio" name="isMain" value="1">是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<input type="radio" name="isMain" value="0" checked="true">否
                    	</#if>
                   </td>
                   <td class="td_label"></td><td class="td_control"></td>
                 </tr>
                 <tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
                    <td colspan="3" valign="middle" class="td_control">
                    	<textarea rows=2 maxlength="300" name="remark" placeholder="不超过200个字">${sku.remark!''}</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
	<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
<script>
   //编辑保存
	$("#savetButton").bind("click",function(){
		$(this).attr("disabled","disabled");
		//验证
		if(!$("#dataForm").validate({
				rules : {
					skuName:{maxlength:50,required:true},
					skuNo:{maxlength:50},
					skuNameAbbr:{maxlength:50},
					barcode:{maxlength:50},
					brand:{required:true},
					category:{required:true},
					spec:{isFloatGteZero:true},
					packSpec:{isFloatGteZero:true},
					weight:{isFloatGteZero:true},
					volume:{isFloatGteZero:true},
					unitId:{required:true},
					price:{isFloatGteZero:true},
					remark:{maxlength:200}
				}
				}).form()){
				$("#savetButton").removeAttr("disabled");
				return;
		}
		$.ajax({
			url : "${contextPath}/sku/updateSku",
			type : "post",
			dataType:"json",
			async: false,
			data : $("#dataForm").serialize(),
			success : function(result) {
			   if(result.code=="success"){
			   		layer.alert(result.message,function(){
		   				parent.document.location.href = "${contextPath}/sku/query/";
		   				parent.layer.closeAll('iframe');
		   				//editDialog.close();
		   			});
				}else {
					layer.alert(result.message);
					$("#savetButton").removeAttr("disabled");
		   		}
			 },
			 error:function(){
			 	$("#savetButton").removeAttr("disabled");
			 }
		});						
	});
</script>
