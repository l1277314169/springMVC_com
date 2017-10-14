<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
</head>

<body>
    <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
    <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
    <div id="main_gauge" style="height:500px;border:1px solid #ccc;padding:10px;margin:10px;padding-top:20px;"></div>
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
            'echarts/chart/gauge'
        ],
        function (ec) {
            //--- 折柱 ---
            var myChart = ec.init(document.getElementById('main_gauge'));
            myChart.setOption({
                tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    series : [
			        {
			            name:'业务指标',
			            type:'gauge',
			            detail : {formatter:'{value}%'},
			            data:[{value: 50, name: '完成率'}]
			        }
			    ]
            });
            
        }
    );
    <!-- 
    clearInterval(timeTicket);
	timeTicket = setInterval(function (){
	    option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
	    myChart.setOption(option, true);
	},2000);
    -->
    </script>
</body>
</html>