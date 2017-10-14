<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form>
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label">品类级别：</td>
                    <td class="td_control">
                    	<#if category.grade == "1">
                    		一级品类
                    	</#if>
                    	<#if category.grade == "2">
                    		二级品类
                    	</#if>
                    </td>
					<td class="td_label">上级品类：</td>
                    <td id="parentCategory2" class="td_control">
	                    <#if parentCategory??>
	                    	${parentCategory.categoryName!''}
	                    <#else>
	                    	//
	                    </#if>
                    </td>
                </tr> 
                <tr>
                	<td class="td_label">品类名称：</td>
                    <td  class="td_control">
                    	${category.categoryName}
                    </td>
                	<td class="td_label">英文名称：</td>
                    <td class="td_control">
                    	${category.categoryNameEn!''}
                    </td>
                    
                </tr>
                <tr>
                 	 <td class="td_label" >品类编号：</td>
                    	
                    <td class="td_control">
                  		${category.categoryNo!''}
					</td>
					<td class="td_label"></td><td class="td_control"></td>
                </tr>
                
                
				<tr>
                    <td style="height:39px" class="td_label" style="vertical-align:top;">备注：</td>
                    <td style="height:39px" class="td_control" colspan="3" valign="middle">
                    	${category.remark!''}
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
