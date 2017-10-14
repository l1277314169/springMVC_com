<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
    .table_white_bg tr td i{
        color:red;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<table class="table_white_bg">
    <tbody>
    	<tr>
        	<td class="td_label_d"><i class="cc1">*</i>职位名称：</td>
            <td class="td_control">
            	${clientPosition.positionName!''}
            </td>
        </tr> 
        <tr>
        	<td class="td_label_d">职位编号：</td>
            <td class="td_control">
            	${clientPosition.positionNo!''}
            </td>
        </tr>
        <tr>
        	<td class="td_label_d">职位名称(英文)：</td>
            <td class="td_control">
            	${clientPosition.positionNameEn!''}
            </td>
        </tr> 
		<tr>
            <td class="td_label_d">备注：</td>
            <td  class="td_control_d">
            	${clientPosition.remark!''}
            </td>
        </tr>
        <tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger" style="margin-left:-170px">关闭</button>
			</td>
	   </tr>
    </tbody>
</table>


