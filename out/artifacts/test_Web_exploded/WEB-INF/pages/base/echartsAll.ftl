<style type="text/css">
.chart_title{
	font-size: 34px;
	margin:20px;
	color: #86cdf9;
}
.chart_content{
	text-align: center;
	font-size: 54px;
	margin-top: 50px;
	color: #fe7e4f;
	font-weight: bold;
}
.chart_module{
	margin-left: 20px;
}

</style>

<div style="margin: 20px;height:600px;overflow-y: scroll;">
		<div class="chart_module fl" id="chart2" len = "3">
			<div class="chart_title">销售金额</div>
			<div class="chart_content">￥0
				<input type="hidden" value="0" id="chart_content_hid" name="chart_content_hid" />
			</div>
		</div>
		<div class="chart_module fl" id="chart3" len = "3"></div>
		<div class="chart_module fl" id="chart4" len = "3"></div>

		<div class="chart_module fl" id="chart6" len = "2" style="height: 580px;"></div>
		<div class="chart_module fl" id="chart1" len = "2"></div>
		<div class="chart_module fl" id="chart5" len = "2" ></div>

		
		<div class="chart_module fl" id="chart8" len = "1" ></div>
		<div class="chart_module fl" id="chart9" len = "1" ></div>
		<div class="chart_module fl" id="chart7" len = "1" ></div>

		<script type="text/javascript">
			var wid = jQuery(document).width()*0.98;
			var obj = jQuery(".chart_module");
			for (var i = 0; i < obj.length; i++) {	
				var len = jQuery(obj[i]).attr("len");
				if(len=='1'){
					jQuery(obj[i]).width(wid-100);
				}else if(len=='2'){
					var _wid = (wid-(obj.length-1)*15);				
					var _width = _wid/len;
					jQuery(obj[i]).width(_width);
				}else{
					var _wid = (wid-(obj.length-1)*18);				
					var _width = _wid/len;
					jQuery(obj[i]).width(_width);
				}
			}

			jQuery(document).ready(function() {
				timeTicket = setInterval(function (){
					var val = parseInt(jQuery("#chart_content_hid").val());
					val+=3125;
					//jQuery("#chart_content_hid").val(val);
					var input = "<input type='hidden' value='"+val+"' id='chart_content_hid' name='chart_content_hid' />";
					jQuery(".chart_content").html("￥"+val+input);
					if(val>82410){
						clearInterval(timeTicket);
					}
				},100);

			});

		</script>
		
		
		<script type="text/javascript">
			// 路径配置
			require.config({paths: {echarts: '${contextPath}/js/echarts'}});
			// 使用
			require( [
						'echarts',
						'echarts/theme/infographic',
						'echarts/chart/line',
						'echarts/chart/bar',
						'echarts/chart/scatter',
						'echarts/chart/k',
						'echarts/chart/pie',
						'echarts/chart/radar',
						'echarts/chart/force',
						'echarts/chart/chord',
						'echarts/chart/gauge',
						'echarts/chart/funnel',
						'echarts/chart/eventRiver',
						'echarts/chart/map'
					],
				function (ec) {
					/* 图1 */
					var myChart = ec.init(document.getElementById('chart1')); 
					myChart.setTheme('default');
					
					option = {
						title : {
							text: '门店拜访'
						},
						tooltip : {
							trigger: 'axis'
						},
						legend: {
							data:['实际拜访数','计划拜访数']
						},
						calculable : false,
						xAxis : [
							{
								type : 'category',
								data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
							}
						],
						yAxis : [
							{
								type : 'value'
							}
						],
						series : [
							{
								name:'实际拜访数',
								type:'bar',
								data:[142.0, 144.9, 137.0, 123.2, 125.6, 166.7, 135.6, 162.2, 132.6, 140.0, 156.4, 153.3],
								markPoint : {
									data : [
										{type : 'max', name: '最大值', symbolSize:12},
										{type : 'min', name: '最小值', symbolSize:12}
									]
								},
								markLine : {
									data : [
										{type : 'average', name: '平均值'}
									]
								}
							},
							{
								name:'计划拜访数',
								type:'bar',
								data:[152.6, 145.9, 149.0, 136.4, 148.7, 180.7, 155.6, 172.2, 148.7, 158.8, 176.0, 172.3],
								markPoint : {
									data : [
										{type : 'max', name: '最大值', symbolSize:12},
										{type : 'min', name: '最小值', symbolSize:12}
									]
								},
								markLine : {
									data : [
										{type : 'average', name : '平均值'}
									]
								}
							}
						]
					};
			
					myChart.setOption(option);

					/* 图3 */
					myChart = ec.init(document.getElementById('chart3'));
					option = {
						title : {
							text: '品牌销量占比',
							subtext: '虚拟数据',
							x:'center'
						},
						tooltip : {
							trigger: 'item',
							formatter: "{a} <br/>{b} : {c} ({d}%)"
						},
						legend: {
							orient : 'vertical',
							x : 'left',
							y : 'top',
							data:['SKU1','SKU2','其它','SKU4','SKU3']
						},
						calculable : false,
						series : [
							{
								name:'访问来源',
								type:'pie',
								radius : '55%',
								center: ['50%', '60%'],
								data:[
									{value:335, name:'SKU1'},
									{value:310, name:'SKU2'},
									{value:234, name:'其它'},
									{value:135, name:'SKU4'},
									{value:1548, name:'SKU3'}
								]
							}
						]
					};
					myChart.setOption(option); 


					/* 图4 */
					myChart = ec.init(document.getElementById('chart4'));
					option = {
						title : {
							text: '拜访达成率'
						},
					    tooltip : {
					        formatter: "{a} <br/>{b} : {c}%"
					    },
					    series : [
					        {
					            name:'业务指标',
					            type:'gauge',
					            splitNumber: 10,       // 分割段数，默认为5
					            axisLine: {            // 坐标轴线
					                lineStyle: {       // 属性lineStyle控制线条样式
					                    color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
					                    width: 8
					                }
					            },
					            axisTick: {            // 坐标轴小标记
					                splitNumber: 10,   // 每份split细分多少段
					                length :12,        // 属性length控制线长
					                lineStyle: {       // 属性lineStyle控制线条样式
					                    color: 'auto'
					                }
					            },
					            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
					                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					                    color: 'auto'
					                }
					            },
					            splitLine: {           // 分隔线
					                show: true,        // 默认显示，属性show控制显示与否
					                length :30,         // 属性length控制线长
					                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
					                    color: 'auto'
					                }
					            },
					            pointer : {
					                width : 5
					            },
					            title : {
					                show : true,
					                offsetCenter: [0, '-40%'],       // x, y，单位px
					                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					                    fontWeight: 'bolder'
					                }
					            },
					            detail : {
					                formatter:'{value}%',
					                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					                    color: 'auto',
					                    fontWeight: 'bolder'
					                }
					            },
					            data:[{value: 92.5, name: '达成率'}]
					        }
					    ]
					};

					myChart.setOption(option); 


					/* 图5 */
					myChart = ec.init(document.getElementById('chart5'));
					option = {
						title : {text:'拜访统计'},
						tooltip : {
							trigger: 'axis'
						},
						calculable : false,
						legend: {
							data:['实际拜访数','计划拜访数','平均时间']
						},
						xAxis : [
							{
								type : 'category',
								data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
							}
						],
						yAxis : [
							{
								type : 'value',
								name : '频次',
								axisLabel : {
									formatter: '{value}家次'
								}
							},
							{
								type : 'value',
								name : '分钟',
								axisLabel : {
									formatter: '{value} mins'
								}
							}
						],
						series : [

							{
								name:'实际拜访数',
								type:'bar',
								data:[142.0, 144.9, 137.0, 123.2, 125.6, 166.7, 135.6, 162.2, 132.6, 140.0, 156.4, 153.3],
							},
							{
								name:'计划拜访数',
								type:'bar',
								data:[152.6, 145.9, 149.0, 136.4, 148.7, 180.7, 155.6, 172.2, 148.7, 158.8, 176.0, 172.3],
							},
							{
								name:'平均时间',
								type:'line',
								yAxisIndex: 1,
								data:[22.0, 23.2, 24.3, 30.5, 29.3, 19.2, 25.3, 22.5, 28.0, 29.5, 21.0, 22.2]
							}
						]
					};
					myChart.setOption(option); 


					/* 图6 */
					myChart = ec.init(document.getElementById('chart6'));
					option = {
						title : {
							text: '全国SKU销量',
							subtext: '虚拟数据',
						},
						tooltip : {
							trigger: 'item'
						},
						legend: {
							data:['SKU1','SKU2','SKU3']
						},
						dataRange: {
							min: 0,
							max: 2500,
							x: 'left',
							y: 'bottom',
							text:['高','低'],
							calculable : true
						},
						roamController: {
							show: false,
							x: 'right',
							mapTypeControl: {
								'china': true
							}
						},
						series : [
							{
								name: 'SKU1',
								type: 'map',
								mapType: 'china',
								roam: false,
								itemStyle:{
									normal:{label:{show:true}},
									emphasis:{label:{show:true}}
								},
								data:[
									{name: '北京',value: Math.round(Math.random()*1000)},
									{name: '天津',value: Math.round(Math.random()*1000)},
									{name: '上海',value: Math.round(Math.random()*1000)},
									{name: '重庆',value: Math.round(Math.random()*1000)},
									{name: '河北',value: Math.round(Math.random()*1000)},
									{name: '河南',value: Math.round(Math.random()*1000)},
									{name: '云南',value: Math.round(Math.random()*1000)},
									{name: '辽宁',value: Math.round(Math.random()*1000)},
									{name: '黑龙江',value: Math.round(Math.random()*1000)},
									{name: '湖南',value: Math.round(Math.random()*1000)},
									{name: '安徽',value: Math.round(Math.random()*1000)},
									{name: '山东',value: Math.round(Math.random()*1000)},
									{name: '新疆',value: Math.round(Math.random()*1000)},
									{name: '江苏',value: Math.round(Math.random()*1000)},
									{name: '浙江',value: Math.round(Math.random()*1000)},
									{name: '江西',value: Math.round(Math.random()*1000)},
									{name: '湖北',value: Math.round(Math.random()*1000)},
									{name: '广西',value: Math.round(Math.random()*1000)},
									{name: '甘肃',value: Math.round(Math.random()*1000)},
									{name: '山西',value: Math.round(Math.random()*1000)},
									{name: '内蒙古',value: Math.round(Math.random()*1000)},
									{name: '陕西',value: Math.round(Math.random()*1000)},
									{name: '吉林',value: Math.round(Math.random()*1000)},
									{name: '福建',value: Math.round(Math.random()*1000)},
									{name: '贵州',value: Math.round(Math.random()*1000)},
									{name: '广东',value: Math.round(Math.random()*1000)},
									{name: '青海',value: Math.round(Math.random()*1000)},
									{name: '西藏',value: Math.round(Math.random()*1000)},
									{name: '四川',value: Math.round(Math.random()*1000)},
									{name: '宁夏',value: Math.round(Math.random()*1000)},
									{name: '海南',value: Math.round(Math.random()*1000)},
									{name: '台湾',value: Math.round(Math.random()*1000)},
									{name: '香港',value: Math.round(Math.random()*1000)},
									{name: '澳门',value: Math.round(Math.random()*1000)}
								]
							},
							{
								name: 'SKU2',
								type: 'map',
								mapType: 'china',
								itemStyle:{
									normal:{label:{show:true}},
									emphasis:{label:{show:true}}
								},
								data:[
									{name: '北京',value: Math.round(Math.random()*1000)},
									{name: '天津',value: Math.round(Math.random()*1000)},
									{name: '上海',value: Math.round(Math.random()*1000)},
									{name: '重庆',value: Math.round(Math.random()*1000)},
									{name: '河北',value: Math.round(Math.random()*1000)},
									{name: '安徽',value: Math.round(Math.random()*1000)},
									{name: '新疆',value: Math.round(Math.random()*1000)},
									{name: '浙江',value: Math.round(Math.random()*1000)},
									{name: '江西',value: Math.round(Math.random()*1000)},
									{name: '山西',value: Math.round(Math.random()*1000)},
									{name: '内蒙古',value: Math.round(Math.random()*1000)},
									{name: '吉林',value: Math.round(Math.random()*1000)},
									{name: '福建',value: Math.round(Math.random()*1000)},
									{name: '广东',value: Math.round(Math.random()*1000)},
									{name: '西藏',value: Math.round(Math.random()*1000)},
									{name: '四川',value: Math.round(Math.random()*1000)},
									{name: '宁夏',value: Math.round(Math.random()*1000)},
									{name: '香港',value: Math.round(Math.random()*1000)},
									{name: '澳门',value: Math.round(Math.random()*1000)}
								]
							},
							{
								name: 'SKU3',
								type: 'map',
								mapType: 'china',
								itemStyle:{
									normal:{label:{show:true}},
									emphasis:{label:{show:true}}
								},
								data:[
									{name: '北京',value: Math.round(Math.random()*1000)},
									{name: '天津',value: Math.round(Math.random()*1000)},
									{name: '上海',value: Math.round(Math.random()*1000)},
									{name: '广东',value: Math.round(Math.random()*1000)},
									{name: '台湾',value: Math.round(Math.random()*1000)},
									{name: '香港',value: Math.round(Math.random()*1000)},
									{name: '澳门',value: Math.round(Math.random()*1000)}
								]
							}
						]
					};

					myChart.setOption(option); 


					//堆积面积图
					myChart = ec.init(document.getElementById('chart7'));
					option = {
						title : {
							text: '品牌销量周报'
						},
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['七度空间','ABC','苏菲','护舒宝','其它']
					    },
					    calculable : false,
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : false,
					            data : ['周一','周二','周三','周四','周五','周六','周日']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'七度空间',
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:[120, 132, 101, 134, 90, 230, 210]
					        },
					        {
					            name:'ABC',
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:[220, 182, 191, 234, 290, 330, 310]
					        },
					        {
					            name:'苏菲',
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:[150, 232, 201, 154, 190, 330, 410]
					        },
					        {
					            name:'护舒宝',
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:[320, 332, 301, 334, 390, 330, 320]
					        },
					        {
					            name:'其它',
					            type:'line',
					            stack: '总量',
					            itemStyle: {normal: {areaStyle: {type: 'default'}}},
					            data:[820, 932, 901, 934, 1290, 1330, 1320]
					        }
					    ]
					};
				    myChart.setOption(option);             

					//
					myChart = ec.init(document.getElementById('chart8'));


					option = {
					    title : {
					        text: '产品年度销量排行榜',
					        subtext: ''
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['高露洁','黑人']
					    },
					    calculable : false,
					    xAxis : [
					        {
					            type : 'category',
					            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'高露洁',
					            type:'bar',
					            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
					            markPoint : {
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
					        },
					        {
					            name:'黑人',
					            type:'bar',
					            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
					            markPoint : {
					                data : [
					                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
					                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name : '平均值'}
					                ]
					            }
					        }
					    ]
					};
                    
					myChart.setOption(option); 

					//
					myChart = ec.init(document.getElementById('chart9'));
					option = {
					    title : {
					        text: 'SKU销量周报'
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['SKU1','SKU2']
					    },
					    calculable : false,
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : false,
					            data : ['周一','周二','周三','周四','周五','周六','周日']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel : {
					                formatter: '{value} °C'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'SKU1',
					            type:'line',
					            data:[11, 11, 15, 113, 102, 113, 10],
					            markPoint : {
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
					        },
					        {
					            name:'SKU2',
					            type:'line',
					            data:[11, 22, 12, 50, 30, 21, 10],
					            markPoint : {
					                data : [
					                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name : '平均值'}
					                ]
					            }
					        }
					    ]
					};
					                    
					myChart.setOption(option); 
				}
			);
		</script>
</div>