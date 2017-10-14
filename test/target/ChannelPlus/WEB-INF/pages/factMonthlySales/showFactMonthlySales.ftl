<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${factMonthlySales.clientId}"/>
		<input type="hidden" name="dataId" value="${factMonthlySales.dataId}"/>
		<input type="hidden" name="orderType" value="${factMonthlySales.orderType}"/>	
		<input type="hidden" id="clientUserId" name="clientUserId" value="${factMonthlySales.clientUserId}"/>
		<input type="hidden" id="clientStructureId"  name="clientStructureId" value="${factMonthlySales.clientStructureId}"/>
        <table class="table_white_bg">
            <tbody>
            	<tr>
            	    <td class="td_label" ><i class="cc1">*</i>月份编号：</td>
                    <td  class="td_control">${factMonthlySales.monthId!''}</td>           	    
            	</tr>
            	<tr>
            		<td class="td_label" ><i class="cc1">*</i>所在部门：</td>
                    <td class="td_control">
                    	${factMonthlySales.clientStructureName!''}
                    </td>
            	</tr>
            	<tr>
                    <td class="td_label" ><i class="cc1">*</i>门店名称：</td>
                    <td class="td_control">
                    	${factMonthlySales.storeName!''}
                    </td> 
                </tr>
                <tr>
                	<td class="td_label">sku：</td>
                    <td class="td_control">
                    	${factMonthlySales.skuName!''}
                    </td>
                </tr> 
                <tr>
	                <td class="td_label" >销量：</td>
	                    <td class="td_control">
	                    	${factMonthlySales.salesVolume!''}
	                    </td>	                  
                </tr>
                <tr>
                	<td class="td_label">销售金额：</td>
	                   <td class="td_control"> 
		                   ${factMonthlySales.salesAmount!''}
	                 </td>
                </tr> 
                <tr>
					<td colspan="2" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>							
					</td>
			   </tr>		 
            </tbody>
        </table>
</form>
<script>

</script>
 