<div class="widget-content nopadding part_main">
		<!-- <div>${parts.partName}</div> -->
		<table class="table table-bordered data-table" id="c_list" dataId = "${parts.partId}">
			<tr>
				<#list parts.columns as col >
					<th>${col.desc}</th>
				</#list>
			</tr>

			<#list parts.values as map >
				<tbody>
					<tr>
					  <#list map?keys as itemKey>
						<td <#list parts.drillCols as dcs>
								<#list itemKey?split("@") as s>
									<#if s==dcs.colName>class='drill_td' columnNams='${dcs.colName}' drillType='${dcs.desc}' </#if>
								</#list>
							</#list>>
							${map[itemKey]}
						</td>
					  </#list>
					<tr>
				</tbody>
			</#list>

		</table>
</div>
<script type="text/javascript">
	jQuery(document).ready(function(e) {
		
		jQuery(".drill_td").live('click', function() {
			var drillType = jQuery(this).attr("drillType");
			var partId = jQuery(this).parents("table").attr("dataId");
			var params = "isDrill=1&drillType="+drillType+"&partId="+partId;
    		drillReport(params,drillType);
		});

	});
</script>