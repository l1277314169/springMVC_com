<#include "/common/taglibs.ftl" />
<div id="widget_content_${parts.partId}" style="max-height:600px;overflow:auto;width:auto;"></div>
<script type="text/javascript">
	
	jQuery(document).ready(function(e) {
		jQuery("body").showLoading();
		jQuery("#widget_content_${parts.partId}").load('${contextPath}/report/loadGrid/${parts.partId}',jQuery("#searchForm").serialize(),function(){
			jQuery("body").hideLoading();
		});
		
		//钻取
		jQuery("td[name='drill_td']")  .live('click', function() {
			var drillType = jQuery(this).attr("drillType");
			var showName = jQuery(this).attr("showName");

			var foreignname = jQuery(this).attr("foreignname");
			var hideVal = jQuery(this).parents("tbody").find("#"+foreignname).html();
			var argName = jQuery(this).attr("ArgName");
			
			var params = "isDrill=1&drillType="+drillType+"&partId="+${parts.partId}+"&"+
			foreignname+"="+jQuery.trim(hideVal)+"&foreignName="+foreignname+"&argName="+argName;

			//alert(params);		
    		drillReport(params,showName);
		});		
	});
</script>