<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form>
		<input type="hidden" name="storeId" value="${store.storeId!''}">
		<input type="hidden" id="storeNoss"  value="${store.storeNo!''}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
            		<td class="td_label">门店编号：</td>
                    <td id="storeNos" class="td_control">
                    	${store.storeNo!''}
                    </td >
                	<td class="td_label">门店名称：</td>
                    <td class="td_control" title="${store.storeName!''}">
                    	${store.storeName!''}
                    </td>
                </tr>
                <tr>
                	<td class="td_label">所属部门：</td> 
                    <td class="td_control" >
	                    ${store.structureName!''}
					</td>
                	<td class="td_label">所属渠道：</td>
                    <td class="td_control" >
                    	${store.channelName!''}
                    </td>
                </tr>
                
                <tr>
                	<td class="td_label">所属连锁：</td>
                    <td class="td_control" >
                    	${store.chainName!''}
                    </td>
                	<td class="td_label">门店状态：</td>
                    <td class="td_control" >
                    	<#if optionsList??>
	                    	<#list optionsList as options>
	                    		<#if store.status == options.optionValue>
	                    			${options.optionText!''}
	                    		</#if>
	                    	</#list>
	                    </#if>
                    </td>
                </tr>
                <tr>
                	<td class="td_label">所属经销商：</td>
                     <td title="${store.distributorName}" >
								<#if store.distributorName ?? && store.distributorName?length gt 8>
							${store.distributorName?substring(0,8)}...
									<#else>
							${store.distributorName}"
									</#if>
								</td>
                    <td class="td_label">管理渠道：</td>
                    <td class="td_control" >
                    	<#if storeTypeList??>
	                    	<#list storeTypeList as options>
	                    		<#if store.storeType == options.optionValue>
	                    			${options.optionText!''}
	                    		</#if>
	                    	</#list>
	                    </#if>
                    </td>
                </tr>
                <tr>
                	<td class="td_label">联系人：</td>
                    <td class="td_control" >${store.contact!''}</td>
                	<td class="td_label">联系电话：</td>
                    <td class="td_control" >${store.telephoneNo!''}</td>
                </tr>
                
                <tr>
               	    <td class="td_label">联系人手机：</td>
                    <td title="${store.mobileNo!''}" >
								<#if store.mobileNo ?? && store.mobileNo?length gt 8>
							${store.mobileNo?substring(0,8)}...
									<#else>
							${store.mobileNo}
									</#if>
								</td>
                	<td class="td_label">省份：</td>
                	<td class="td_control" >
                			<#if province??>
        						${province.province!''}
        					</#if>
                    </td>
                </tr> 
                <tr>
                	<td class="td_label">传真号：</td>
                	<td class="td_control" >
                    	${store.fax!''}
                    </td>
                    <td class="td_label">城市：</td>
                	<td class="td_control" >
                			<#if city??>
        						${city.city!''}
        					</#if>
                    </td>
                </tr> 
                <tr>
                	<td class="td_label">门店分组：</td>
                    <td class="td_control" >
                    	<#if storeGroupList??>
                    		<#list storeGroupList as storeGroup>
                    			<#if store.storeGroupId == storeGroup.storeGroupId>
                    				${storeGroup.groupName!''}
                    			</#if>
                    		</#list>
                    	</#if>
                    </td>
                    <td class="td_label">区县：</td>	
                	<td class="td_control" >
                			<#if district??>
        						${district.district!''}
        					</#if>
                    </td>
                </tr>
                <tr>
                	<td class="td_label">地址：</td>
                    <td class="td_control" colspan="3" title="${store.addr!''}">${store.addr!''}</td>
                 <tr>
	                <td class="td_label">业务员:</td>
	                <td class="td_control" colspan="3">
	                	<input type="text" id="clientUserIds" name="clientUserIds" value="" readonly>
	                </td>
                </tr>
                </tr> 
                
				<tr>
                    <td style="height:39px" class="td_label">备注：</td>
                    <td style="height:39px" class="td_control" colspan="3">
                    	${store.remark!''}
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
				<button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">关闭</button>
					</td>
			   </tr>
            </tbody>
        </table>
        
</form>
<script>
 $(document).ready(function () {
         //加载人员(人员)
         var preload_data = [
         	<#if sumList??>
				<#list sumList as sum>
					<#if !sum_has_next>
						{id: '${sum.clientUserId}', name: '${sum.name}'}
					<#else>
						{id: '${sum.clientUserId}', name: '${sum.name}'},
					</#if>
				</#list>
			</#if>
			];
		$("#clientUserIds").select2({
        	width:450,
        	multiple: true,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserPosition",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						page: page
					};
				},
				results: function (data,page) {
					var more = page;
					return { results: data,more: more };
				}
			},
			formatResult: repoFormatResult,
			formatSelection: repoFormatSelection,
			escapeMarkup: function (m) { return m; }
		});
		$('#clientUserIds').select2('data', preload_data );
		function repoFormatResult(repo) {
			return repo.name;
		}
		function repoFormatSelection(repo) {
			return repo.name;
		}
    });

</script>
