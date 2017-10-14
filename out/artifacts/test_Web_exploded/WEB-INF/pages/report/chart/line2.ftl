	<div id="main_line" style="height:250px;border:1px solid #ccc;padding:10px;margin:10px;padding-top:20px;"></div>    
    <script type="text/javascript">
    
            //--- 折柱 ---
            var lineChart = echarts.init(document.getElementById('main_line'));
            var option = {
                    title : {
				        text: '${parts.partName}'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    noDataLoadingOption :{
				    	text :'${parts.partName}，未能查询到数据',
				    	effect:'whirling',
				    	textStyle:{
				    		fontSize:'25'
				    	}
					},
				    legend: {
				        data:[${parts.legendData}]
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        
				        {
				            type : 'category',
				            data : [
				            	${parts.buttomData}
				            ]
				        },
				        {
				            type : 'category',
				            data : [
				            	${parts.topData}
				            ]
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name : '${parts.leftName}',
				            axisLabel : {
				                formatter: '{value}'
				            }
				        },
				        {
				            type : 'value',
				            name : '${parts.rightName}',
				            axisLabel : {
				                formatter: '{value}'
				            }
				        }
				    ],
				    series : [
				        
				        <#list parts.series as ps >
                            {
                                name: '${ps.name}',
                                type: '${ps.type}',
                                <#if ps.place=='right'>yAxisIndex:1,</#if>
                                data: [${ps.data}]
                            }<#if parts.series?size gt ps_index+1 >,</#if>
                    </#list>

				    ]
            }

			//绑定事件
            //var ecConfig = require('echarts/config');
            function eConsole(param) {              
                var drillCols_div = jQuery(".drillCols_div").html();
                var cols = drillCols_div.split("@");
                for (var i = 0; i < cols.length; i++) {
                    var v = jQuery.trim(cols[i]);
                    if(param.seriesName==v){
                        //var partId = jQuery("#main_line").attr("dataId");
                        var params = "isDrill=1&drillType="+v+"&partId="+${parts.partId};
                        drillReport(params,param.seriesName);
                    }
                };         
            }
            
            lineChart.setOption(option);
            lineChart.on(echarts.config.EVENT.CLICK, eConsole);
        }
    );
    </script>

<div class="drillCols_div_${parts.partId}" style="display:none;">
    <#list parts.drillCols as dcs>
        ${dcs.desc}<#if parts.drillCols?size gt dcs_index+1 >@</#if>
    </#list>
</div>