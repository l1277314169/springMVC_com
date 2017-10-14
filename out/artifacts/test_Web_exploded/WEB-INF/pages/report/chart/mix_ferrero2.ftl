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
	   		  	drawEcharts(data);
	   		  	jQuery("body").hideLoading();
	   		  },
	   		  error: function(xhr, textStatus, errorThrown) {
	   		   	layer.alert(errorThrown);
	   		  }
		  });
		  
		  function drawTable(data){
		  		var _table = "<table class='table table-bordered data-table' style='height:auto;'><tbody><tr>";
		  		
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
		  
		  function drawEcharts(data){
		  		var j = 0;
	  			$.each(data.keyIndexs, function(index, val) {
	  				var base = 'ferrero_mix_'+j;
		  			var _div = "<div id='"+base+"' style='width:100%;height:250px;'></div>";
		  			$("#echart_div").append(_div);
			  		var option = getOptions();
			  		option.title.text=val;
			  		$.each(data.mapValues[val], function(index2, val2) {
			  			var datas = [];
			  			var i = 0;
			  			$.each(data.ferreros, function(index3, val3) {
			  				var key = val3.argName;
			  				var val = val2[key].value;
			  				if(val.indexOf("%")!=-1){
			  					var v = val.replace('%', "");
				  				datas[i] = (Number(v));
				  				i++;
			  				}
			  			});
			  			var v = val2[data.divCol2].value;
			  			var item = {
				            name:v,
				            type:'line',
				            data:datas
				        };
			  			option.series.push(item);
			  		});
			  		
			  		var mixChart = echarts.init(document.getElementById(base),'infographic');
			  		mixChart.setTheme(firms());
			  		if(null!=data.legendData && ""!=data.legendData){
						var legendData = data.legendData.split(",");
				   		jQuery.each(legendData, function(index, val) {
				   		  	option.legend.data.push(val);
				   		});
			   		}
			   		if(null!=data.buttomData && ""!=data.buttomData){
			   			var xAxis0 = data.buttomData.split(",");
				   		jQuery.each(xAxis0, function(index, val) {
				   		  	option.xAxis[0].data.push(val);
				   		});
			   		}
			  		mixChart.setOption(option);
			  		j++;
	   		  	});
	   		  	
		  		
		  }
		  
		  function getOptions(){
		  	var option = {
				    title : {
				        text: '',
				        subtext: ''
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:[]
				    },
				    toolbox: {
				        show : true
				    },
				    calculable : false,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : []
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value}'
				            }
				        }
				    ],
				    series : []
			};
            return option;  
		  }
	});
</script>

