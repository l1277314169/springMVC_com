<#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
 <table class="table_white_bg">
    <tbody>
    	<tr>
            <td class="td_label" >编号：</td>
            <td class="td_control">
            	${distributor.distributorNo!''}
            </td>
            <td class="td_label" >联系人：</td>
            <td class="td_control">
            	${distributor.contact!''}
            </td>
       </tr> 
       <tr>
        	<td class="td_label">名称：</td>
            <td class="td_control">
            	${distributor.distributorName!''}
            </td>
             <td class="td_label">简称：</td>
            <td class="td_control">
            	${distributor.distributorAbbr!''}
            </td>
       </tr> 
		<tr>
            <td class="td_label">手机号：</td>
            <td class="td_control">
            	${distributor.mobileNo!''}
            </td>
             <td class="td_label">部门：</td>
            <td class="td_control">
            	  ${distributor.structureName!''}
            </td>
        </tr>
      	<tr>
             <td class="td_label">状态：</td>
               <td class="td_control">
               <#if distributor.status == 1>
               					启用
               				<#else>
               					禁用
               </#if>
             </td>
              <td class="td_label">省份：</td>
              <td class="td_control"> 
	        		${distributor.province}
             </td>
        </tr>
         <tr>
          	<td class="td_label">用户姓名：</td>
        	<td class="td_control">
        		${distributor.clientUserName}
        	</td>
			  <td class="td_label">城市：</td>
              <td class="td_control"> 
                ${distributor.city!''}
             </td>
         </tr>    
          <tr>
            <td class="td_label">类别：</td>
            <td class="td_control">
	        		<#if distributor.distributorType == 1>
               					联营
               				<#else>
               					其他
               		</#if>
	        	
            </td>
              <td class="td_label">区县：</td>
              <td class="td_control"> 
                 ${distributor.district!''}
             </td>
  	    </tr>
        <tr>
              <td class="td_label">固定电话：</td>
              <td class="td_control">
            	${distributor.telephoneNo!''}
             </td>
             <td class="td_label">纬度：</td>
              <td class="td_control"> 
                 ${distributor.longitude!''}
              </td>
       </tr>
         <tr>
            <td class="td_label">邮编：</td>
            <td class="td_control">
            	${distributor.postcode!''}
            </td>
            <td class="td_label">经度：</td>
            <td class="td_control">
            	${distributor.latitude!''}
            </td>
        </tr>
        <tr>
            <td class="td_label">传真：</td>
            <td class="td_control">
            	${distributor.fax!''}
            </td>
             <td class="td_label">地址：</td>
            <td class="td_control">
            	${distributor.addr!''}
            </td>
        </tr>
		<tr>
           <td class="td_label">备注：</td>
           <td  colspan="3" class="td_control">
            	${distributor.remark!''}
           </td>
       </tr>
       <tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				<button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger" style="margin-left:-150px;" >关闭</button>
			</td>
	   </tr>
    </tbody>
</table>



