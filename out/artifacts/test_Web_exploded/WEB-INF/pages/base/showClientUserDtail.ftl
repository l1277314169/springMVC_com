<table class="table_white_bg" >
    <tbody>
    	<tr>
    	    <td class="td_label" >工号：</td>
            <td  class="td_control">${clientUser.userNo}</td>
    	  <td class="td_label" >姓名：</td>
            <td  class="td_control">
     			${clientUser.name}
            </td>
    	</tr>
    	<tr>
            <td class="td_label" >所在部门：</td>
            <td class="td_control">
            	${clientUser.structureName}
            </td>
    	   <td class="td_label">职位：</td>
            <td class="td_control">
            	${clientUser.positionName}
            	</select>
            </td>
        </tr> 
        <tr>
            <td class="td_label" >用户名：</td>
                <td class="td_control">
                	${clientUser.loginName}
                </td>
              <td class="td_label">在职状态：</td>
               <td class="td_control"> 
                 <#if clientUser.status == 1>
                  			在职
                  			 <#elseif clientUser.status == 0>
                  			离职
                  			<#else>
                  	</#if>
             </td>
        </tr> 
	<tr>
            <td class="td_label">固定电话：</td>
            <td class="td_control">
            	${clientUser.telephoneNo}
            </td>
            
		  <td class="td_label">帐号状态：</td>
            <td class="td_control">
            	<#if clientUser.isActivation == 1>
                  			启用
                  			 <#else>
                  			禁用
                  	</#if>
            </td>
        </tr>
      <tr>
            <td class="td_label">身份证号：</td>
                <td class="td_control">
              		${clientUser.idcard}
                </td>
             <td class="td_label">性别：</td>
               <td class="td_control"> 
                   <#if clientUser.sex == 1>
                  			 男
                  			 <#elseif clientUser.sex == 2>
                  			 女
                  			 <#else>
                  			 
                  	</#if>
             </td>
        </tr>
        <tr>
          <td class="td_label">手机号：</td>
            <td class="td_control">
            	${clientUser.mobileNo}
            </td>
         <td class="td_label">省份：</td>
          <td class="td_control"> 
                	${clientUser.province!''}
         </td>
       </tr>
       <tr>
          <td class="td_label">家庭地址：</td>
             <td class="td_control">
            	${clientUser.addr}
            </td>
            <td class="td_label">城市：</td>
          <td class="td_control">
                	${clientUser.city!''}
         </td>
     </tr>    
     	<tr>
            <td class="td_label">邮编：</td>
            <td class="td_control">
            	${clientUser.postcode}
            </td>
              <td class="td_label">区县：</td>
              <td class="td_control">
                	${clientUser.district!''}
             </td>
    	</tr>
        <tr>
             <td class="td_label">上级领导：</td>
            <td class="td_control">
            	${clientUser.parentName}
            </td>
        </tr>
        <tr>
        	 <td class="td_label">系统角色：</td>
            <td colspan="3" class="td_control">
           		 <#list clientRolesUserMappingList as clientRolesUserMapping>
           		 <#if clientRolesUserMapping_has_next>
             			${clientRolesUserMapping.name}&nbsp;
             		<#else>
             			${clientRolesUserMapping.name}
             		</#if>
             	</#list>
            </td>
        </tr>
       <tr>
            <td class="td_label">备注：</td>
            <td  colspan="3" class="td_control">
            	${clientUser.remark}
            </td>
       </tr>
 		<tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				<button data-dismiss="dialog" type="button" class="btn btn-danger" style="margin-right:80px">关闭</button>
			</td>
	   </tr>
    </tbody>
</table>

