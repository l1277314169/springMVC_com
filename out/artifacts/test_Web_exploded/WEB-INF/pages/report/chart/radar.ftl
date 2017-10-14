<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
</head>

<body>
    <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
    <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
    <div id="main_radar" style="height:500px;border:1px solid #ccc;padding:10px;margin:10px;padding-top:20px;"></div>
    <!--Step:2 Import echarts.js-->
    <!--Step:2 引入echarts.js-->
    <script src="${contextPath}/js/echarts/echarts.js"></script>
    
    <script type="text/javascript">
    // Step:3 conifg ECharts's path, link to echarts.js from current page.
    // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
    require.config({
        paths: {
            echarts: '${contextPath}/js/echarts'
        }
    });
    
    // Step:4 require echarts and use it in the callback.
    // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
    require(
        [
            'echarts',
            'echarts/chart/radar'
        ],
        function (ec) {
            //--- 折柱 ---
            var myChart = ec.init(document.getElementById('main_radar'));
            myChart.setOption({
			    title : {
			        text: '预算 vs 开销（Budget vs spending）',
			        subtext: '纯属虚构'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'right',
			        y : 'bottom',
			        data:['预算分配（Allocated Budget）','实际开销（Actual Spending）']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    polar : [
			       {
			           indicator : [
			               { text: '销售（sales）', max: 6000},
			               { text: '管理（Administration）', max: 16000},
			               { text: '信息技术（Information Techology）', max: 30000},
			               { text: '客服（Customer Support）', max: 38000},
			               { text: '研发（Development）', max: 52000},
			               { text: '市场（Marketing）', max: 25000}
			            ]
			        }
			    ],
			    calculable : true,
			    series : [
			        {
			            name: '预算 vs 开销（Budget vs spending）',
			            type: 'radar',
			            data : [
			                {
			                    value : [4300, 10000, 28000, 35000, 50000, 19000],
			                    name : '预算分配（Allocated Budget）'
			                },
			                 {
			                    value : [5000, 14000, 28000, 31000, 42000, 21000],
			                    name : '实际开销（Actual Spending）'
			                }
			            ]
			        }
			    ]
            });
            
        }
    );
    </script>
</body>
</html>