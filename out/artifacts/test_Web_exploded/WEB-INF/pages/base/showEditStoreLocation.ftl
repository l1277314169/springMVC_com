<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/channel-style.css">
<title>地址解析</title>
<script type="text/javascript" src="${contextPath}/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<style type="text/css">
	#allmap {width: 100%;height: 450px; overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
</head>
<body>
<div style="text-align: center;padding: 5px;">
	<form action="#" id="dataForm" novalidate="novalidate">
		<input type="hidden" id="storeId"  name="storeId" value="${store.storeId!''}"/>
		<label for="addr">地址：</label>
		<input id="addr" type="text" name="addr" value="${store.addr!''}" />
		<label for="longitude">经度：</label>
		<input id="longitude" type="text" name="longitude" value="${store.longitude!''}" readonly disabled='disabled'/>
		<label for="structureName">纬度：</label>
		<input id="latitude" type="text" name="latitude" value="${store.latitude!''}" readonly disabled='disabled'/>
		<input type="button" value="查询" class="btn btn-info fr" id="search_btn">	
	</form>
</div>
<div id="allmap">
</div>
<div style="text-align: center;padding-top: 5px;padding-bottom: 5px;">
	 <button data-dismiss="dialog" id="cancelButton" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
	<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
</div>
</body>
</html>
<script type="text/javascript">

function loadMap(addr,init){
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.enableScrollWheelZoom();
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);
	var lng = $("#longitude").val();
	var lat = $("#latitude").val();
	var point;
	var marker;
	if(init && lng !=null && lng != "" && lat != null && lat != ""){
		point = new BMap.Point(lng,lat);
		marker = new BMap.Marker(point);
		map.centerAndZoom(point,16);
		marker.enableDragging();
		marker.addEventListener("dragend",function(){
			var p = this.getPosition();//p 就是marker当前的坐标;
			$("#longitude").val(p.lng);
			$("#latitude").val(p.lat);
			//alert(p.lng+","+p.lat);//弹出来看下
		});
		map.addOverlay(marker);
	} else {
		point = new BMap.Point(103.2319,35.3349);
		marker = new BMap.Marker(point);
		map.centerAndZoom(point,5);
		// 创建地址解析器实例
		var myGeo = new BMap.Geocoder();
		// 将地址解析结果显示在地图上,并调整地图视野
		myGeo.getPoint(addr, function(point){
			if (point) {
				//alert(point);
				map.centerAndZoom(point, 16);
				marker = new BMap.Marker(point);
				marker.enableDragging();
				marker.addEventListener("dragend",function(){
					var p = this.getPosition();//p 就是marker当前的坐标;
					//alert(p.lng+","+p.lat);//弹出来看下
					$("#longitude").val(p.lng);
					$("#latitude").val(p.lat);
				});
				$("#longitude").val(point.lng);
				$("#latitude").val(point.lat);
				map.addOverlay(marker);
			}else{
				layer.alert("您选择地址没有解析到结果!");
			}
		}, "中国");
	}
	map.addEventListener("click", showInfo);

	function showInfo(e){
		map.clearOverlays();
		point = new BMap.Point(e.point.lng,e.point.lat);
		marker = new BMap.Marker(point);
		marker.enableDragging();
		map.addOverlay(marker);
		$("#longitude").val(point.lng);
		$("#latitude").val(point.lat);
		marker.addEventListener("dragend",function(){
			var p = this.getPosition();//p 就是marker当前的坐标;
			//alert(p.lng+","+p.lat);//弹出来看下
			$("#longitude").val(p.lng);
			$("#latitude").val(p.lat);
		});
		
	};	
}

$(function(){
	var addr = $("#addr").val();
	loadMap(addr,true);
	$("#search_btn").bind("click",function(){
		addr = $("#addr").val();
		loadMap(addr,false);
	})
		
	//保存定位
	$("#savetButton").bind("click",function(){
		var storeId = $("#storeId").val();
		var longitude = $("#longitude").val();
		var latitude = $("#latitude").val();
		if ((longitude == null || longitude == undefined || longitude == '')&&(latitude == null || latitude == undefined || latitude == '')) {
			layer.alert("经纬度必填");
			return;
		} 
		$.post("${contextPath}/store/saveStoreLocation",{storeId:storeId,longitude:longitude,latitude:latitude},function(result){
			if(result.code=="success"){
   				window.parent.location.reload();
   				window.parent.editStoreLocationDialog.close();
			}else {
				layer.alert(result.message);
	   		}
		});	 
	});
	
	//取消定位弹窗
	$("#cancelButton").bind("click",function(){
		window.parent.editStoreLocationDialog.close();	
	});
		
})
</script>