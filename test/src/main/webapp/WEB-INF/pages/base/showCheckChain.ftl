<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<table class="table_white_bg" >
    <tbody>
    	<tr>
        	<td class="td_label" >连锁名称：</td>
            <td class="td_control">
          		${chain.chainName!''}
            </td>
         </tr> 
         <tr>
       		 <td class="td_label">当前层级：</td>
             <td class="td_control">
             
                   <#if chain.grade == 1>
                  		 	一级连锁
                  		 <#elseif chain.grade == 2>
                  			 二级连锁
                  		 <#else>
					</#if>
            </td> 
         </tr>
         <tr id="tpd">
         <td  class="td_label" >上级连锁：</td>
            <td class="td_control">
          	 	${parent.chainName!''}
            </td>
        </tr> 
         <tr>
        	 <td class="td_label">所属渠道：</td>
            <td class="td_control">
            	${chain.channelName!''}
            </td>
        </tr>
        <tr>
        	 <td class="td_label">英文：</td>
            <td class="td_control">
            	${chain.chainNameEn!''}
            </td>
        </tr>
       	<tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				<button data-dismiss="dialog" type="button" class="btn btn-danger" onClick="javascript:parent.layer.closeAll('iframe');" style="margin-right:80px">关闭</button>
			</td>
	   </tr>
    </tbody>
</table>
