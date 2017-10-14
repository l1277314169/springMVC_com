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
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label" ><i class="cc1">*</i>渠道名称：</td>
                    <td class="td_control"><input type="text" name="channelName" value="${channel.channelName!''}" required=true disabled="disabled"></td>
                </tr> 
                <tr>
                	<td class="td_label"><i class="cc1">*</i>当前层级：</td>
                     <td class="td_control">
	                    <input type="text" id="superior" name="grade" value="<#if channel.grade == 1>一级渠道<#elseif channel.grade == 2>二级渠道<#elseif channel.grade == 3><#else></#if>" required=true disabled="disabled">
                    </td>
                </tr>
                 <tr id="ids">
                 <td class="td_label" >上级渠道：</td>
                    <td class="td_control">
	                    <input type="text" id="ditch" name="parentId" value="${parent.channelName!''}" disabled="disabled">
                    </td> 
                </tr>
                <tr>
                 	<td class="td_label" >英文：</td>
                    <td class="td_control"><input type="text" name="channelNameEn" value="${channel.channelNameEn!''}" disabled="disabled"></td>
                </tr>
                  <tr>
					<td colspan="2" class="td_buttons">
						<button data-dismiss="dialog" type="button" class="btn btn-danger" onClick="javascript:parent.layer.closeAll('iframe');"  >取消</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>

