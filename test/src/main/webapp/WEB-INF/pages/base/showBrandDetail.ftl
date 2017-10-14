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
		<input type="hidden" name="clientId">
		<input type="hidden" name="brandId" value="${brand.brandId}">
        <table class="table_white_bg">
            <tbody>
           		 <tr>
                    <td class="td_label" ><i class="cc1">*</i>品牌层级：</td>
                    <td class="td_control">
                    	<#if brand.grade == "1">
                    		一级品牌
                    	</#if>
                    	<#if brand.grade == "2">
                    		一级品牌
                    	</#if>
                    </td>
                    
                	<td class="td_label" id="parentBrand">上级品牌：</td>
                    <td class="td_control" id="parentBrand1">
                    	<#if parentBrand??>
                    		${parentBrand.brandName!''}
                    	<#else>
                    		//
                    	</#if>
                    </td>
                </tr>
                <tr>	
                	<td class="td_label"><i class="cc1">*</i>品牌名称：</td>
                    <td class="td_control">
                    	${brand.brandName!''}
                    </td>
                    
                	<td class="td_label">英文名称：</td>
                    <td class="td_control">
                    	${brand.brandNameEn!''}
                    </td>
                </tr> 
                <tr>
                    <td class="td_label" >品牌编号：</td>
                    <td class="td_control">
                  		${brand.brandNo!''}
					</td>
					
                	<td class="td_label">是否自有品牌：</td>
                    <td class="td_control">
                    	<#if brand.isOwner == "1">
                    		是
                    	</#if>
                    	<#if brand.isOwner == "0">
                    		否
                    	</#if>
                    </td>
                </tr>
                <tr>
                	<td class="td_label">所属公司：</td>
                    <td class="td_control">
                   		${brand.firm!''}
                    </td>
                    <td class="td_label"></td><td class="td_control"></td>
                	 </tr>
                
				<tr>
                    <td style="height:39px" class="td_label" style="vertical-align:top;">备注：</td>
                    <td style="height:39px" class="td_control" colspan="3" valign="middle">
                    	${brand.remark!''}
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">关闭</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
$(document).ready(function () {
 	if($("#grade").val()=="1"){
	 	$("#parentBrand").hide();
		$("#parentBrand1").hide();
 	}
	//上级品牌展示
	$("#grade").bind("change",function(){
		if($(this).val() != "2"){
			$("#parentBrand").hide();
			$("#parentBrand1").hide();
			$("#parentId").val("");
		
		}else if($(this).val() == "2"){
			$("#parentBrand").show();
			$("#parentBrand1").show();
		}
		
	});
	//编辑品牌--保存
	$("#savetButton").bind("click",function(){
		//验证
		if(!$("#dataForm").validate({
			rules:{
					grade:
						{required:true},
					brandName:
						{maxlength:50},
					brandNo:
						{maxlength:50},
					firm:
						{maxlength:50},
					brandNameEn:
						{maxlength:50},
					remark:
						{maxlength:200}
			
				}}).form()){
			return;
		}
		$.ajax({
		url : "${contextPath}/brand/updateBrand",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
		   			window.location.href = "${contextPath}/brand/query"
	   				editDialog.close();
	   				
			}else {
				layer.alert(result.message);
	   		}
		   }
		});						
	});

	
});
</script>
