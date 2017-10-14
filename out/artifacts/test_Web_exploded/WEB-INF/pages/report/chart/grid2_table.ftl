<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList">
<table class="table table-bordered data-table" id="c_list">
	<tr>
		<#list reportVo.dataInfo.heads as head >
			<th class="fill_td" >${head}</th>
		</#list>
	</tr>
	
	<#list reportVo.dataInfo.values as map >
		<tbody>
			<tr>
			  <#list map?keys as itemKey>
				<td class="fill_td">
					${map[itemKey].value}
				</td>
			  </#list>
			<tr>
		</tbody>
	</#list>
</table>

</div>
