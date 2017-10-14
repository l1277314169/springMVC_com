<html>
<head>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/showLoading.css?cVer=${cVer}">
<script type="text/javascript" src="${rc.contextPath}/js/echarts/echarts.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.7.min.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery.showLoading.min.js?cVer=${cVer}"></script>
<style>
body {
       /*padding-top:20px;*/
       background-color:#fff;
}
 
.fl {float:left;}
 
.chart_module {
   /* height: 400px;
    width: 778px !important;*/
    height: 280px;
    overflow: hidden;
    margin-bottom: 20px;
    border: 1px solid #e3e3e3;
    -webkit-border-radius: 4px;
       -moz-border-radius: 4px;
            border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
       -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
}
</style>
</head>

<body>
<#if clientId == 4>
	<div align=center>
		<img src="${rc.contextPath}/img/placeholder_mg.png" />
	</div>
<#elseif clientId == 7>
	<div id="centerBG"></div>
<#elseif clientId == 2>
	<div align=center>
		<img src="${rc.contextPath}/img/placeholder_ctbat.jpg" />
	</div>
<#elseif clientId == 5 || clientId == 6>
		<#include "/base/echartsAll.ftl" />	
<#else>
<div align=center>
	<img src="${rc.contextPath}/images/u41_normal.png" />
</div>
</#if>
</body>
<script type="text/javascript">
$(document).ready(function(){
	<#if clientId == 7>
		loadClientDashBoard();
	</#if>
});

function loadClientDashBoard(){
	jQuery("body").showLoading();
	$.post("${rc.contextPath}/dashboard/query/27",
		function(data){
			$("#centerBG").html(data);
			jQuery("body").hideLoading();
		}
	);
}
</script>
</html>
