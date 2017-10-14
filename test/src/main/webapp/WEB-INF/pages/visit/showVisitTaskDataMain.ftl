<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fixedtableheader.min.js"></script>
<SCRIPT type="text/javascript">
	$(document).ready(function(){
		$(".lightPhoto").lightbox({
			fitToScreen: true
		});
	});
	$("#tbl").fixedtableheader();
</SCRIPT>
<table class="tbl table table-bordered" id="tbl">
	<tr>
		<th width="60px">属性</th>
		<th>值</th>
	</tr>
	<tr>
		<td>进店</td>
		<td>
			<#if visitingTaskData??>
				<#if visitingTaskData.inTime??><span>${visitingTaskData.inTime?string('HH:mm')}</span></#if>
				<#if visitingTaskData.inPic??>
					<span style="margin-left:15px">
					<#list visitingTaskData.inPic ? split(",") as str>
						<a href="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" rel="flowers_inPic" class="lightPhoto"><img src="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" style="width:20;height:20;" /></a>
					</#list>
					</span>
				</#if>
				<#if visitingTaskData.inLongitude?? && visitingTaskData.inLatitude??>
					<span style="margin-left:15px"><a class="baiduMap P_in" href="javascript:void(0);" data-inOrOut="in" data-longitude="${visitingTaskData.inLongitude!''}" data-latitude="${visitingTaskData.inLatitude!''}"><img src="${contextPath}/img/map_24_24.png" style="width:20;height:20;" /></a></span>
					<span style="margin-left:15px"><#if visitingTaskData.inRange?? && visitingTaskData.inRange lte 500>吻合<#else>不吻合</#if></span>
				</#if>
			</#if>
		</td>
	</tr>
	<tr>
		<td>出店</td>
		<td>
			<#if visitingTaskData??>
				<#if visitingTaskData.outTime??><span>${visitingTaskData.outTime?string('HH:mm')}</span></#if>
				<#if visitingTaskData.outPic??>
					<span style="margin-left:15px">
					<#list visitingTaskData.outPic ? split(",") as str>
						<a href="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" rel="flowers_outPic" class="lightPhoto"><img src="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" style="width:20;height:20;" /></a>
					</#list>
					</span>
				</#if>
				<#if visitingTaskData.outLongitude?? && visitingTaskData.outLatitude??>
					<span style="margin-left:15px"><a class="baiduMap p_out" href="javascript:void(0);" data-inOrOut="out" data-longitude="${visitingTaskData.outLongitude!''}" data-latitude="${visitingTaskData.outLatitude!''}"><img src="${contextPath}/img/map_24_24.png" style="width:20;height:20;" /></a></span>
					<span style="margin-left:15px"><#if visitingTaskData.outRange?? && visitingTaskData.outRange lte 500>吻合<#else>不吻合</#if></span>
				</#if>
			</#if>
		</td>
	</tr>
	<tr>
		<td>小结</td>
		<td>
			<#if visitingTaskData??>
				<span>${visitingTaskData.summary!''}</span>
			</#if>
		</td>
	</tr>
</table>

<a class="divMap" href="javascript:void(0);">
	<div id="divMap" class="overlay" style="display: none;"></div>
</a>
<div id="container" class="moveBar" style="display: none;"></div>
<script type="text/javascript">
$(function(){
	//进店
	$("a.baiduMap").bind("click",function(){
		var inOrOut = $(this).attr("data-inOrOut");
		var taskType = $("#taskType").val();
		$("#divMap").css("display","block");
		$("#container").css("display","block");
		var inLongitude = $("a.P_in").attr("data-longitude");
		var inLatitude = $("a.P_in").attr("data-latitude");
		var outLongitude = $("a.p_out").attr("data-longitude");
		var outLatitude = $("a.p_out").attr("data-latitude");
		var storeLongitude = "${store.longitude}";
		var storeLatitude = "${store.latitude}";
		var storeName = "${store.storeName}";
		//alert("storeLongitude:"+storeLongitude);
		//alert("storeLatitude:"+storeLatitude);
		var map = new BMap.Map("container"); // 创建地图实例
		var point;
		var data_info;
		var opts;
		var msg;
		map.enableScrollWheelZoom();
		
		var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
		var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
		map.addControl(top_left_control);        
		map.addControl(top_left_navigation);  
		
		if(inOrOut == "in"){
			point = new BMap.Point(inLongitude, inLatitude); // 创建点坐标
		} else if(inOrOut == "out"){
			point = new BMap.Point(outLongitude, outLatitude); // 创建点坐标
		}
		map.centerAndZoom(point, 17); // 初始化地图，设置中心点坐标和地图级别
		
		if(taskType == 1){
			var msg = "门店拜访：";
		} else if(taskType == 2) {
			var msg = "门店协访：";
		} else {
			
		}
		
		data_info = [[inLongitude,inLatitude,msg+"进店定位"],[outLongitude,outLatitude,msg+"出店定位"]];
		opts = {
			width : 200,     // 信息窗口宽度
			height: 50,     // 信息窗口高度
			title : "",  // 信息窗口标题
			enableMessage:false//设置允许信息窗发送短息
		};
		
		if(inOrOut == "in"){
			var infoWindow = new BMap.InfoWindow(msg+"进店定位", opts);
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		} else if(inOrOut == "out"){
			var infoWindow = new BMap.InfoWindow(msg+"出店定位", opts);
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		}
		
		
		for(var i=0;i<data_info.length;i++){
			var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
			var content = data_info[i][2];
			map.addOverlay(marker);               // 将标注添加到地图中
			addClickHandler(content,marker);
		}
		
		if(storeLongitude !="" && storeLatitude !=""){
			var pt = new BMap.Point(storeLongitude, storeLatitude);
			var myIcon = new BMap.Icon("${contextPath}/img/map_24_24.png", new BMap.Size(32,32),{
    			anchor: new BMap.Size(15, 30)
			});
			var marker = new BMap.Marker(pt,{icon:myIcon}); 
			var content = storeName;
			map.addOverlay(marker);
			addClickHandler(content,marker);
		}
		
		function addClickHandler(content,marker){
			marker.addEventListener("click",function(e){
				openInfo(content,e)}
			);
		}
		
		function openInfo(content,e){
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		}
	});
	

	
	//关闭定位地图
	$("a.divMap").bind("click",function(){
		document.getElementById("divMap").style.display="none";
		document.getElementById("container").style.display="none";
	});
});
</script>