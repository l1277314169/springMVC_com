
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />

<table class="table_white_bg">
            <tbody> 
                 <tr>
                    <td class="td_label"><i style="color: red;">*</i>客户名称：</td>
                    <td  class="td_control_d">
                    	${wrCustomer.customerName!''}
                    </td>
                 </tr>
                 <tr>
                    <td class="td_label"><i style="color: red;">*</i>品牌：</td>
                    <td class="td_control">
                    	${wrCustomer.brandName!''}
                    </td>
                </tr>
				<tr>
                    <td class="td_label">备注：</td>
                    <td class="td_control">
                    	${wrCustomer.remark!''}
                    </td>
                </tr>
             <tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger"  style="margin-left:-160px">关闭</button>
			</td>
	   </tr>   
    </tbody>
</table>