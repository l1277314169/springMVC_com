<#include "/common/taglibs.ftl" />
<div id="widget_content_${parts.partId}" style="overflow:auto;width:auto;height:auto;background:#fff;">
	
</div>
<div id="echart_div" style="height:auto;background:#fff;">

</div>
<script type="text/javascript">
	$(document).ready(function() {
		var _url = "${contextPath}/report/ferrero/loadEcharts/${parts.partId}";
		jQuery("body").showLoading();
		$.ajax({
	   		  url: _url,
	   		  type: 'POST',
	   		  dataType: 'json',
	   		  cache:false,
	   		  data: jQuery("#searchForm").serialize(),
	   		  success: function(data, textStatus, xhr) {
	   		  	drawTable(data);
	   		  	jQuery("body").hideLoading();
	   		  },
	   		  error: function(xhr, textStatus, errorThrown) {
	   		   	layer.alert(errorThrown);
	   		  }
		  });
		  
		  function drawTable(data){
		  		var _table = "<table class='table table-bordered data-table'><tbody><tr>";
		  		
		  		var len = data.columns.length;
		  		$.each(data.columns, function(index, val) {
		  			var td = "<th>"+val.colName+"</th>";
		  			_table+=td;
		  		});
		  		_table+="</tr></tbody>";
		  		
		  		$.each(data.keyIndexs, function(index, val) {
		  			_table+="<tbody><tr><td colspan="+len+" style='padding-left:10px;font-weight:bold;' >"+val+"</td></tr></tbody>";
			  		$.each(data.mapValues[val], function(index2, val2) {
			  			_table+="<tr><tbody>";
			  			$.each(data.columns, function(index3, val3) {
			  				var key = val3.argName;
			  				var td = "<td>"+val2[key].value+"</td>";
			  				_table+=td;
			  			});
			  			_table+="</tr></tbody>";
			  		});
	   		  	});
	   		  	_table+="</table>";
	  			$("#widget_content_${parts.partId}").append(_table);
	   		  	
		  }
	});
</script>

