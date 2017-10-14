<#list baseReport.funcs as f>
	<button type="button" id="${f.name}" class="btn btn-primary">${f.viewName}</button>
	<script type="text/javascript">
	jQuery(document).ready(function($) {
		jQuery("#${f.name}").live("click",function(){			
			jQuery.ajax({
				  url: '/report/verify/${baseReport.reportId}',
				  type: 'POST',
				  dataType: 'json',
				  data: jQuery("#searchForm").serialize(),
				  success: function(data, textStatus, xhr) {
				    if(data.code=='success'){						    	
				    	exportExcel();
				    }else{
				    	 layer.alert(data.message);
				    }
				  },
				  error: function(xhr, textStatus, errorThrown) {
				    	layer.alert(errorThrown);
				    	jQuery("body").hideLoading();
				  }
			});
		});
	});


	function exportExcel(){
		<#if f.confirmMsg??>
			layer.confirm("${f.confirmMsg}", function (index) {
				var url ="${contextPath}/reportExport/execute/${f.id}";
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/report/query/${baseReport.reportId}");
				layer.close(index);
			});
		<#else>
			layer.confirm("确认导出", function (index) {
				var url ="${contextPath}/reportExport/execute/${f.id}";
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/report/query/${baseReport.reportId}");
				layer.close(index);
			});
		</#if>
	}

	</script>
</#list>

