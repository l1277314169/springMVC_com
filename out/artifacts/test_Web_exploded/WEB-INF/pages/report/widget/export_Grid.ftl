<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList" style="margin-top:30px;">
<table class="table table-bordered data-table" id="c_list">
	<tr>
		<#list dds.columnList as cols >
			<th class="fill_td" >${cols.desc}</th>
		</#list>
	</tr>
	
	<#list dataInfo.values as map >
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

<div class="paging">	
	<@pagination.paging pageParam true "#logResultList"/>
</div>
<input type="hidden" id="count" value="${dataInfo.items}">

</div>
