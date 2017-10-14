<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" name="clientId">
        <table class="table_white_bg">
            <tbody >
            	 <tr >
                   <td class="td_label"><i class="cc1">*</i>产品名称：</td>
                   <td class="td_control">
                  		<input type="text" name="skuName">
				   </td>
                   <td class="td_label">产品英文名称：</td>
                   <td class="td_control">
                    	<input type="text" name="skuNameEn" >
                   </td>
                    
                 </tr>
                 <tr >
                   <td class="td_label" >产品编码：</td>
                   <td class="td_control" >
                  		<input type="text" name="skuNo">
				   </td>
                   <td class="td_label" >产品简称：</td>
                   <td class="td_control">
                    	<input type="text" name="skuNameAbbr" >
                   </td>
                    
                 </tr>
                
                 <tr >
                   <td class="td_label">产品条码:</td>
                   <td class="td_control">
                   	<input type="text" name="barcode">
                   </td>
                   <td class="td_label">产品价格：</td>
                   <td class="td_control">
                  		<input type="text" name="price">
				   </td>
                   
                 </tr>
                 <tr >
                   <td class="td_label" ><i class="cc1">*</i>品牌：</td>
                   <td class="td_control">
                  		<input type="hidden" id="brandId" name="brandId" >
                  		<#assign brandArg="brandId">
                    	<#include "/widgets/brand_widget.ftl" />
				   </td>
                   <td class="td_label" ><i class="cc1">*</i>品类：</td>
                   <td class="td_control">
                    	<input type="hidden" id="categoryId" name="categoryId" >
                    	<#assign categoryArg="categoryId">
                    	<#include "/widgets/category_widget.ftl" />
                   </td>
                    
                 </tr>
                 <tr >
                   <td class="td_label" >产品规格：</td>
                   <td class="td_control">
                  		<input type="text" name="spec">
				   </td>
                   <td class="td_label">包装规格：</td>
                   <td class="td_control">
                    	<input type="text" name="packSpec" >
                   </td>
                    
                 </tr>
                 <tr >
                   <td class="td_label">产品重量：</td>
                   <td class="td_control">
                  		<input type="text" name="weight">
				   </td>
                   <td class="td_label" >产品体积：</td>
                   <td class="td_control">
                    	<input type="text" name="volume" >
                   </td>
                    
                 </tr>
                 
                 <tr >
                   <td class="td_label"><i class="cc1">*</i>单位：</td>
                   <td class="td_control">
                   		<select name="unitId">
                   			<option value="">--请选择--</option>
	                  		<#list unitList as unit >
	                  			<option value="${unit.unitId!''}">${unit.subUnitName!''}</option>
	                  		</#list>
                  		</select>
				   </td>
				   <td class="td_label">产品分组:</td>
				   <td class="td_control">
				   		<select name="skuGroupId"  value="skuGroupId">
				   			<option value="">--请选择--</option>
				   			<#list sgList as skuGroup>
				   				<option value ="${skuGroup.skuGroupId}">${skuGroup.groupName}</option>
				   			</#list>
				   		</select>
				   </td>
                 </tr>
                 <tr>
                   <td class="td_label">产品分类:</td>
                    <td class="td_control">
                   	  <input id="skutypeid" type="text" class="input-medium" name="skutypeid" value=""  />
                      <#include "/widgets/skuType.ftl" />
                   </td>
                    <td class="td_label">产品系列:</td>
                   <td class="td_control">
                    	<input type="text" id="skuSeriesId" name="skuSeriesId"  value="" class="input-medium" />
                    	<#include "/widgets/skuSeries.ftl" />
                   </td>
                 </tr>
                  
                 <tr>
                   <td class="td_label">是否主打：</td>
                   <td class="td_control">
                    	<input type="radio" name="isMain" value="1" >是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    <input type="radio" name="isMain" value="0" checked="true">否
                   </td>
                   <td class="td_label"></td><td class="td_control"></td>
                 </tr>
                 <tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
                    <td class="td_control" colspan="3" valign="middle">
                    	<textarea rows=2  maxlength="300" name="remark" placeholder="不超过200个字"></textarea>
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
      
	   //新增产品
		$("#savetButton").bind("click",function(){
			$(this).attr("disabled","disabled");
			//验证
			if(!$("#dataForm").validate({
					rules : {
						skuName:{maxlength:50,required:true},
						skuNo:{maxlength:50},
						skuNameAbbr:{maxlength:50},
						barcode:{maxlength:50},
						clientStructureName_brand:{required:true},
						clientStructureName_category:{required:true},
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
				url : "${contextPath}/sku/addSku",
				type : "post",
				dataType:"json",
				async: false,
				data : $("#dataForm").serialize(),
				success : function(result) {
				   if(result.code=="success"){
					   layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/sku/query";
		   						parent.layer.closeAll('iframe');
				   				//addDialog.close();
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
