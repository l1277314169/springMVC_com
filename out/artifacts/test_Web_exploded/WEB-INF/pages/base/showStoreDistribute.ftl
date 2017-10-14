<!DOCTYPE HTML>
<html>
<head>
  <title>门店分布-加载海量点</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
  <link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/baiduMap.css">
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
  <script type="text/javascript" src="${rc.contextPath}/js/RichMarker.js"></script>
  <style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
    #map_list_table {
	    position:static;
	    width: 20%;
	    height: 80%;
	    background: #fbfbfb;
	    border: 1px solid #dbdbdb;
	    overflow: scroll;
	    float: left;
	    margin-left: 10px;
	}
	#map {
	    height: 80%;
	    width: 76%;
	    float: left;
	    left: 1%;
	}
	.allH,html{
		height:100%;
	}
</style>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
</head>
<body class="allH main_body">
<#assign privCode="C4M2">
<#include "/base.ftl" />
	<div id="content"  class="allH">
	  <div id="mapBG">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">统计报表</a>
				<a class="linkPage active" href="${contextPath}/store/distribute">门店分布</a>
			</div>
	 	</div>
	 	 <div id="filter_div">
				<form id="searchFrm" name="searchFrm" action="#">
					<input type="hidden" id="slng" name="slng" />
					<input type="hidden" id="slat" name="slat" />
					<input type="hidden" id="nlng" name="nlng" />
					<input type="hidden" id="nlat" name="nlat" />
					<input type="hidden" id="zoom" name="zoom" />
	
					<input type="hidden" id="page" name="page" value="${page}" />
					<input type="hidden" id="allPages" name="allPages" value="${allPages}" />
	
					<div class="filter_div_element">
						门店编号：<input type="text" id="storeNo" name="storeNo">
					</div>
					<div class="filter_div_element">
						门店名称：<input type="text" id="storeId" name="storeId" style="top:-3px;">				
						<#assign storeFtlName="storeId">
						<#include "/widgets/store_select2.ftl" />
					</div>
					<div class="filter_div_element">
						组织架构：<input type="text" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
						<input type="text" id="structureId" name="structureId" value="${structureId}" style="display:none;">
						<#assign structureFtlName="structureId">
						<#include "/widgets/structure.ftl" />
					</div>
					<div class="filter_div_element">
						<input type="button" value="查询" class="btn btn-info fr" id="search_btn" style="margin-top:10px;" />			
					</div>
				</form>
		</div>
	    <div id="map_list_table"></div>
	    <div id="map"></div>
	    </div>
	 </div>
	 <#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	var map = null;
	var zoomLevel = 12;
	init();
	loadMarker(6);

	function init() {		
	    map = new BMap.Map("map");
		var point = new BMap.Point(121.495748,30.953091);
		map.centerAndZoom(point, 6); 
		map.enableScrollWheelZoom();
		map.addControl(new BMap.NavigationControl());

		map.addEventListener("zoomend", function(){ //改变地图级别以后
			map.clearOverlays();
			var zoom = this.getZoom();
			loadMarker(zoom);
		});

		map.addEventListener("moveend", function(){ //移动完成以后
			map.clearOverlays();
			var zoom = this.getZoom();
			loadMarker(zoom);
		});
	} 

	

	//var bs = map.getBounds();   //获取可视区域
	//var bssw = bs.getSouthWest();   //可视区域左下角
	//var bsne = bs.getNorthEast();   //可视区域右上角
	//alert("当前地图可视范围是：" + bssw.lng + "," + bssw.lat + "到" + bsne.lng + "," + bsne.lat);

	function getCurrentBounds(zoom){
		var bs = map.getBounds();   //获取可视区域
		var bssw = bs.getSouthWest();   //可视区域左下角
		var bsne = bs.getNorthEast();   //可视区域右上角
		jQuery("#slng").val(bssw.lng);
		jQuery("#slat").val(bssw.lat);

		jQuery("#nlng").val(bsne.lng);
		jQuery("#nlat").val(bsne.lat);
		jQuery("#zoom").val(zoom);
		//alert("当前地图可视范围是：" + bssw.lng + "," + bssw.lat + "到" + bsne.lng + "," + bsne.lat);
	}


	function loadMarker(zoom){
		getCurrentBounds(zoom);
		jQuery.ajax({
		  url: '${rc.contextPath}/store/loadDistributeMarker',
		  type: 'POST',
		  dataType: 'json',
		  data: jQuery("#searchFrm").serialize(),
		  success: function(data, textStatus, xhr) {				
				if(zoom<=zoomLevel){
					map.clearOverlays();
					addRichMarker(data);
				}else{
					addMarker(data);
				}
		  },
		  error: function(xhr, textStatus, errorThrown) {
		    layer.alert("加载门店出错，"+errorThrown);
		  }
		});
	}


	function addRichMarker(data) {

		jQuery.each(data, function(index, store) {
			var htm = '<div class="baidu_map_info">'+store.name+'<br />'+store.cnt+'</div>';			
			var point = new BMap.Point(store.longitude,store.latitude);
			var marker = new BMapLib.RichMarker(htm,point,{"anchor": new BMap.Size(-72, -84), "enableDragging": true});
			map.addOverlay(marker);

			marker.addEventListener("onclick", function(e) {
			    var zoom = map.getZoom();
			    if(zoom<=8){//钻取到市
			    	map.centerAndZoom(point,9);
			    	loadMarker(9);
			    }else if(zoom<=12){			    	
			    	map.centerAndZoom(point,13);	    	
			    	loadMarker(13);
			    }
			});
		});
	}

	

	function addMarker(data) {		

		$.each(data, function(index,store) {			
			var point = new BMap.Point(store.longitude,store.latitude);
			var marker = new BMap.Marker(point);

			map.addOverlay(marker);
			marker.addEventListener("onclick", function(e) { 
			    callback(store.longitude,store.latitude,store.storeId);
			});
		});
	}

	

	function callback(longitude,latitude,storeId)
	{
		jQuery.ajax({
		  url: '${rc.contextPath}/store/getStoreInfo/'+storeId,
		  type: 'POST',
		  dataType: 'json',
		  success: function(data, textStatus, xhr) {
		  		openWindow(data,longitude,latitude);
		  },
		  error: function(xhr, textStatus, errorThrown) {
		    layer.alert("数据加载异常"+errorThrown);
		  }
		});	
	}


	function openWindow(data,longitude,latitude){
		var opts = {
		  width : 300,
		  height: 80,
		  title : data.storeName , // 信息窗口标题
		  enableMessage:false,//设置允许信息窗发送短息
		  message:''
		}
		var infoWindow = new BMap.InfoWindow(data.addr, opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,new BMap.Point(longitude,latitude)); //开启信息窗口
	}

	jQuery(document).ready(function($) {
		loadLeftStores();//初始化左侧门店

		jQuery("#search_btn").click(function(){
			 loadLeftStores();
		});

		jQuery(".map_list_main_bg").live('click',function(){
			var lng = jQuery(this).attr("lng");
			var lat = jQuery(this).attr("lat");
			var storeId = jQuery(this).attr("storeid");
			var point = new BMap.Point(lng,lat);		
	 		map.centerAndZoom(point,15);
			callback(lng,lat,storeId);
		});

		//首页
		jQuery("#left_page_home").live('click',function(){
			jQuery("#page").val(1);
			loadLeftStores();
		});


		jQuery("#left_page_pre").live('click',function(){
			var page = parseInt(jQuery("#_page").val());
			var allPages = jQuery("#_allPages").val();
			page = page -1;
			if(page<=0){
				page = 1;
			}
			jQuery("#page").val(page);
			loadLeftStores();
		});

		jQuery("#left_page_next").live('click',function(){
			var page = parseInt(jQuery("#_page").val())
			var allPages = parseInt(jQuery("#_allPages").val());
			page = page+1;
			if(page>=allPages){
				page = allPages;
			}
			jQuery("#page").val(page);
			loadLeftStores();
		});

		jQuery("#left_page_end").live('click',function(){
			var allPages = parseInt(jQuery("#_allPages").val());
			jQuery("#page").val(allPages);
			loadLeftStores();
		});


	});

	function loadLeftStores(){
		jQuery("#map_list_table").showLoading();
		jQuery.post("${contextPath}/store/loadStoreList?"+jQuery("#searchFrm").serialize(),
			function(data){
				$("#map_list_table").html(data);
				jQuery("#map_list_table").hideLoading();
			}
		);
	}
  </script>