<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList">
	<table class="table table-bordered data-table" id="c_list">
		<tr>
			<@shiro.hasPermission name="C2M4F5">
			<th width="2%"><input type="checkbox" id="checkAll" name="checkAll"></th>
			</@shiro.hasPermission>
			<th width="5%">大区</th>
			<th width="7%">所属部门</th>
			<th width="5%">省份</th>
			<th width="5%">城市</th>
			<th width="5%">一级渠道</th>
			<th width="5%">二级渠道</th>
			<th width="5%">店铺渠道</th>
			<th width="8%">所属经销商</th>
			<th width="5%">管理渠道</th>
			<th width="6%">店铺编号</th>
			<th width="10%">店铺名称</th>
			<th width="10%">地址</th>
			<th width="13%">促销员/业务代表/主管</th>
			<th width="8%">操作</th>
		</tr>
		<tbody>
			<#list pageParam.items as store>
				<tr>
					<@shiro.hasPermission name="C2M4F5">
					<td><input name="chkItem" type="checkbox" class="checkboxStore" value="${store.storeId!''}" data="${store.storeName!''}"/></td>
					</@shiro.hasPermission>
					<#if store.structureName1?? && store.structureName2??>
						<td title="${store.structureName1!''}">
							<#if store.structureName1?? && store.structureName1?length gt 5>
								${store.structureName1?substring(0,5)}..
							<#else>
								${store.structureName1!''}
							</#if>
						</td>
						<td title="${store.structureName2!''}">
							<#if store.structureName2?? && store.structureName2?length gt 5>
								${store.structureName2?substring(0,5)}..
							<#else>
								${store.structureName2!''}
							</#if>
						</td>
					</#if>
					<#if !(store.structureName1??) && store.structureName2??>
						<td title="${store.structureName2!''}">
							<#if store.structureName2?? && store.structureName2?length gt 5>
								${store.structureName2?substring(0,5)}..
							<#else>
								${store.structureName2!''}
							</#if>
						</td>
						<td></td>
					</#if>	
					<#if !(store.structureName1??) && !(store.structureName2??)>
						<td></td>
						<td></td>
					</#if>	
					<td>
						${store.provinceName!''}
					</td>
					<td title="${store.cityName!''}">
						<#if store.cityName?? && store.cityName?length gt 3>
							${store.cityName?substring(0,3)!''}...
						<#else>
							${store.cityName!''}
						</#if>
					</td>
					<#if store.channelName1?? && store.channelName2??>
						<td title="${store.channelName1!''}">
							<#if store.channelName1?? && store.channelName1?length gt 3>
								${store.channelName1?substring(0,3)!''}...
							<#else>
								${store.channelName1!''}
							</#if>
						</td>
					
						<td title="${store.channelName2!''}">
							<#if store.channelName2?? && store.channelName2?length gt 3>
								${store.channelName2?substring(0,3)!}...
							<#else>
								${store.channelName2!''}
							</#if>
						</td>
					</#if>
					<#if !(store.channelName1??) && store.channelName2??>
						<td title="${store.channelName2!''}">
							<#if store.channelName2?? && store.channelName2?length gt 3>
								${store.channelName2?substring(0,3)!}...
							<#else>
								${store.channelName2!''}
							</#if>
						</td>
						<td></td>
					</#if>
					<#if !(store.channelName1??) && !(store.channelName2??)>
						<td></td>
						<td></td>
					</#if>
					<td title="${store.chainName!''}">
						<#if store.chainName?? && store.chainName?length gt 3>
							${store.chainName?substring(0,3)!}...
						<#else>
							${store.chainName!''}
						</#if>
					</td>
					<td title="${store.distributorName}">
						<#if store.distributorName?? && store.distributorName?length gt 6>
							${store.distributorName?substring(0,6)}...
						<#else>
							${store.distributorName!''}
						</#if>
					</td>
					<td>
						${store.storeTypeName!''}
					</td>
					<td title="${store.storeNo!''}">
						<#if store.storeNo?? && store.storeNo?length gt 8>
							${store.storeNo?substring(0,8)}...
						<#else>
							${store.storeNo!''}
						</#if>
					</td>
					<td title="${store.storeName!''}">
						<#if store.storeName?? && store.storeName?length gt 8>
							${store.storeName?substring(0,8)}...
						<#else>
							${store.storeName!''}
						</#if>
					</td>
					<td title="${store.addr!''}">
						<#if store.addr?? && store.addr?length gt 8>
							${store.addr?substring(0,8)}...
						<#else>
							${store.addr!''}
						</#if>
					</td>
					<td id="clientUserName" title="${store.name!''}">
						<#if store.name?? && store.name?length gt 16>
							${store.name?substring(0,16)}...
						<#else>	
							${store.name!''}
						</#if>					
					</td>
					<td>
						<@shiro.hasPermission name="C2M4F2">
						<a class="editStore" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}">编辑</a>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M4F3">
						<a class= "deleteStore"  href="javascript:void(0);" data="${store.storeId!''}">删除</a>
						</@shiro.hasPermission>
						<a class="moreDetails" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}">查看</a>
						<#if store.longitude?? && store.latitude??>
							<a class="storeMap" href="javascript:void(0);" storeName="${store.storeName!''}" longitude="${store.longitude!''}" latitude="${store.latitude!''}"><img src="${contextPath}/img/green12.png"></a>
						<#else>
							<a href="javascript:void(0);"><img src="${contextPath}/img/grey12.png"></a>
						</#if>
					</td>  
				</tr>
			</#list>
		</tbody>
	</table>
	<div class="paging">	
    	<@pagination.paging pageParam true "#logResultList"/>
	</div>
</div>