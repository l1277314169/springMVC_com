<!DOCTYPE HTML>
<html>
<head>
  <title>加载海量点</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
  <style type="text/css">
    body, html{
			width: 100%;
			height: 100%;
			margin:0;
			font-family:"微软雅黑";
			font-size:14px;
		}
		
		.div_main_map{
			width:400px; 
			height:400px;
			float:left;
			margin-left:18px;
			margin-top:60px;
		}
		
		.div_main_map_title{
			width:160px;
			height:40px;
			background:#FAEFC1;
			float:right;
			line-height:40px;
			vertical-align: middle;
			text-align:center;
			font-size:20px;
		}
		
		#l-map,#l-map2,#l-map3,#l-map4{
			width:400px; 
			height:400px;
		}
		#result{
			width:100%;
		}
		li{
			line-height:28px;
		}
		.cityList{
			height: 320px;
			width:372px;
			overflow-y:auto;
		}
		.sel_container{
			z-index:9999;
			font-size:12px;
			position:absolute;
			right:0px;
			top:0px;
			width:140px;
			background:rgba(255,255,255,0.8);
			height:30px;
			line-height:30px;
			padding:5px;
			top:150px;
		}
		.map_popup {
			position: absolute;
			z-index: 200000;
			width: 382px;
			height: 344px;
			right:0px;
			top:40px;
		}
		.map_popup .popup_main { 
			background:#fff;
			border: 1px solid #8BA4D8;
			height: 100%;
			overflow: hidden;
			position: absolute;
			width: 100%;
			z-index: 2;
		}
		.map_popup .title {
			background: url("http://map.baidu.com/img/popup_title.gif") repeat scroll 0 0 transparent;
			color: #6688CC;
			font-weight: bold;
			height: 24px;
			line-height: 25px;
			padding-left: 7px;
		}
		.map_popup button {
			background: url("http://map.baidu.com/img/popup_close.gif") no-repeat scroll 0 0 transparent;
			cursor: pointer;
			height: 12px;
			position: absolute;
			right: 4px;
			top: 6px;
			width: 12px;
		}
		
		#filter_div{
			width:100%;
			margin: 0 auto;
			padding: 5px;
			margin-top:40px;
			height:50px;
			line-height: 50px;
			vertical-align: middle;
		}
		.filter_div_element{
			float:left;
			height:50px;
			margin-left:20px;
			line-height: 50px;
			vertical-align:bottom;
		}
		#div_info{
			width:100%;
			height:80px;
			background: #fbfbfb;
			border-bottom: 1px solid #dbdbdb;
		}

		#div_info .info_tab{
			width: 24%;
			height: 80px;
			float:left;
			border-left: 1px solid #dbdbdb;
		}

		.info_tab span{
			width: 100%;
			display: block;
			float: left;
			font-size:13px;
			height: 30px;
			text-align:center;
			line-height: 30px;
			vertical-align: middle;
		}

		.info_tab div{
			font-size: 28px;
			height: 50px;
			line-height: 50px;
			vertical-align: middle;
			font-weight: bold;
			text-align: center;
			margin:0 auto;
			margin-top:30px;
			width:120px;
			background: #FAEFC1;
		}
		
		ul li{
			list-style-type: none;
		}
		
		#div_sel_main{
			width:100%;
			height:30px;
			
		}
		
		#div_sel_main div{
			float:left;
			margin:0 auto;
			height:30px;
			width:120px;
			line-height:30px;
		}
		
		 #ui-datepicker-div {z-index:99999 !important;}
	    .table_white_bg tr td{
	        height: 30px;
	        padding: 5px;
	    }
  </style>
  	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
 	<#include "/common/head.ftl" />
	<#include "/common/foot.ftl" />
</head>
<body  class="main_body">
	<#assign privCode="C4M4">
	<#include "/base.ftl" />
	<div id="content"  class="allH">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">统计报表</a>
				<a class="linkPage active" href="${contextPath}/store/showKPIList">KPI</a>
			</div>
	 	</div>
		<div id="filter_div">
			<form id="searchFrm" name="searchFrm" action="#">
				<#include "/base/showStore_filter.ftl" />
				
				<div class="filter_div_element">
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn" style="margin-top:10px;" />			
				</div>
				
			</form>
		</div>
		<div class="mapKPI">
			<#include "/base/map_KPI_1.ftl" />
			<#include "/base/map_KPI_2.ftl" />
			<#include "/base/map_KPI_3.ftl" />
			<#include "/base/map_KPI_4.ftl" />
		</div>
  </div>
  <#include "/footer.ftl" />
</body>
</html>