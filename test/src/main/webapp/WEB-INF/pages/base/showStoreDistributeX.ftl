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
		#l-map {
			    width: 98%;
			    height: 70%;
			    margin: 0 auto;
			    overflow: hidden;
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
			margin-top:8px;
			height:50px;
			line-height: 50px;
			vertical-align: middle;
		}
		.filter_div_element{
			float:left;
			height:50px;
			margin-left:30px;
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

 	 #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
    .allH{
		height:100%;
	}
  </style>
  	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
 	<#include "/common/head.ftl" />
	<#include "/common/foot.ftl" />
</head>
<body  class="allH main_body">
	<#assign privCode="C4M3">
	<#include "/base.ftl" />
	<div id="content"  style="height:100%;">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">统计报表</a>
				<a class="linkPage active" href="${contextPath}/store/distributeX">门店分布图</a>
			</div>
	 	</div>
	   <div id="div_info">
			<div class="info_tab">
				<span>覆盖城市数量</span>
				<div>1367</div>
			</div>
			<div class="info_tab">
				<span>覆盖门店数量</span>
				<div>47080</div>
			</div>
			<div class="info_tab">
				<span>促销员数量</span>
				<div>70105</div>
			</div>
			<div class="info_tab">
				<span>项目数量</span>
				<div>1008</div>
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
		
		<div id="l-map"></div>
  </div>
  <#include "/footer.ftl" />
    <script type="text/javascript">
    var map = new BMap.Map("l-map", {});                        // 创建Map实例
    map.centerAndZoom(new BMap.Point(105.000, 38.000), 5);     // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom();                        //启用滚轮放大缩小
	
	if (document.createElement('canvas').getContext) {  // 判断当前浏览器是否支持绘制海量点
        var points = [];  // 添加海量点数据
        <#list stroeList as store>
        	points.push(new BMap.Point(${store.longitude}, ${store.latitude}));
        </#list>
        var options = {
            size: BMAP_POINT_SIZE_SMALL,
            shape: BMAP_POINT_SHAPE_STAR,
            color: '#d340c3'
        }
        var pointCollection = new BMap.PointCollection(points, options);  // 初始化PointCollection
        pointCollection.addEventListener('click', function (e) {
        	//alert('单击点的坐标为：' + e.point.lng + ',' + e.point.lat);  // 监听点击事件
        });
        map.addOverlay(pointCollection);  // 添加Overlay
    } else {
        alert('请在chrome、safari、IE8+以上浏览器查看本示例');
    }
  </script>
</body>
</html>