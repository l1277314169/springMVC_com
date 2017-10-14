<#list dds.funcs as f>
	<button type="button" id="${f.name}" class="btn btn-primary">${f.viewName}</button>
	<script type="text/javascript">
	jQuery(document).ready(function($) {
		jQuery("#${f.name}").live("click",function(){
			jQuery("body").showLoading();
			jQuery.ajax({
			  url: '/export/verify/${dds.settingId}',
			  type: 'POST',
			  dataType: 'json',
			  data: jQuery("#searchForm").serialize(),
			  success: function(data, textStatus, xhr) {
			    if(data.code=='success'){						    	
			    	callBack${f.id}();
			    }else{
			    	 layer.alert(data.message);
			    }
			    jQuery("body").hideLoading();
			  },
			  error: function(xhr, textStatus, errorThrown) {
			    	layer.alert(errorThrown);
			    	jQuery("body").hideLoading();
			  }
			});
		});
	});


	function callBack${f.id}(){
		if(${f.id}=='3'){
			$("#widget_content_${dds.settingId}").load("/export/loadGrid/${dds.settingId}",jQuery("#searchForm").serialize(),function(){
					
				}
			 );
		}else{
			export${f.id}();
		}
	}

	function export${f.id}(){
		<#if f.confirmMsg??>
			layer.confirm("${f.confirmMsg}", function (index) {
				var url ="${contextPath}/export/execute/${dds.settingId}";
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/report/query/${dds.settingId}");
				layer.close(index);
			});
		<#else>
			layer.confirm("确认导出？", function (index) {
				var url ="${contextPath}/export/execute/${dds.settingId}";
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/report/query/${dds.settingId}");
				layer.close(index);
			});
		</#if>
	}


	</script>
</#list>

