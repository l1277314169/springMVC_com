    <!-- 同步方式获取图形报表页面备份 -->
    <div id="main_bar" dataId = "${parts.partId}" style="height:250px;border:1px solid #ccc;padding:10px;margin:10px;padding-top:20px;"></div>
    <script type="text/javascript">
            var barChart = echarts.init(document.getElementById('main_bar'));
            var option = {
                title : {
                    text: '${parts.partName}'
                },
                tooltip : {
                    trigger: 'axis'
                },
                noDataLoadingOption :{
			    	text :'抱歉，未能查询到数据',
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
                                <#if ps.data!=''>
                                ,markPoint : {
                                    data : [
                                        {type : 'max', name: '最大值'},
                                        {type : 'min', name: '最小值'}
                                    ]
                                },
                                markLine : {
                                    data : [
                                        {type : 'average', name: '平均值'}
                                    ]
                                }
                                </#if>
                            }<#if parts.series?size gt ps_index+1 >,</#if>
                    </#list>

                ]
            }
            
            //绑定事件
            //var ecConfig = require('echarts/config');
            function eConsole(param) {              
                var drillCols_div = jQuery(".drillCols_div_${parts.partId}").html();
                var cols = drillCols_div.split("@");
                for (var i = 0; i < cols.length; i++) {
                    var v = jQuery.trim(cols[i]);
                    if(param.seriesName==v){
                        //var partId = jQuery("#partId").val();
                        //var partId = jQuery("#main_bar").attr("dataId");
                        var params = "isDrill=1&drillType="+v+"&partId="+${parts.partId};

                        drillReport(params,param.seriesName);
                    }
                }        
            }

            barChart.setOption(option);
            barChart.on(echarts.config.EVENT.CLICK, eConsole);
    </script>

<div class="drillCols_div_${parts.partId}" style="display:none;">
    <#list parts.drillCols as dcs>
        ${dcs.desc}<#if parts.drillCols?size gt dcs_index+1 >@</#if>
    </#list>
</div>