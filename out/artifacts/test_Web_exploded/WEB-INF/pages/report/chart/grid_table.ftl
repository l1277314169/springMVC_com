<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList">
<table class="table table-bordered data-table" id="c_list_${reportVo.partId}" dataId = "${reportVo.partId}">
	<tr>
		<#list reportVo.columns as col >
			<#if col.hide!=1>
				<th class="fill_td" <#if col.drillArg=='1'> style="display:none;" </#if> >${col.desc}</th>
			</#if>
		</#list>
	</tr>
	
	<#list reportVo.values as map >
		<tbody>
			<tr>
			  <#list map?keys as itemKey>
					
					<#if map[itemKey].hide!=1>
					<td class="fill_td" 
						<#if map[itemKey].drillArg=='1'>
							 style="display:none;" id="${map[itemKey].colName}" 
						</#if>
						
						<#if map[itemKey].drill=='1'> 
							 name="drill_td" 
							 style="color: #00bb9c" 
							 columnNams='${map[itemKey].colName}' 
							 drillType='${map[itemKey].desc}' 
							 foreignName ='${map[itemKey].foreignName}' 
							 ArgName="${map[itemKey].argName}" 
							 showName="${map[itemKey].showName}"
							 foreignNamex ="${map[itemKey].foreignNamex}"
							 argNamex="${map[itemKey].argNamex}"
						</#if>
					>
					
					<#if map[itemKey].style=='max' >
						<font color="#F33333">${map[itemKey].value}</font>								
					<#elseif map[itemKey].style=='min' >
						<font color="#F6EA0B" >${map[itemKey].value}</font>
					<#elseif map[itemKey].style=='mid' >
						<font color="#157B19" >${map[itemKey].value}</font>
					<#else>
						${map[itemKey].value}
					</#if>
					</td>
					</#if>
					
			  </#list>
			<tr>
		</tbody>
	</#list>
</table>

<div class="paging">	
	<@pagination.paging pageParam true "#logResultList"/>
</div>
<input type="hidden" id="count" value="${reportVo.allItems}">
	
</div>