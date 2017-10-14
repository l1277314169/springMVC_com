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
		#l-map{
			width: 98%;
  		    height: 78%;
			border:1px solid #dbdbdb;
			float:left;
			margin-left:20px;
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
	<#assign privCode="C4M5">
	<#include "/base.ftl" />
	<div id="content"  style="height:100%;">
		<div class="div_main_map allH" style="width:100%;">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">统计报表</a>
					<a class="linkPage active" href="${contextPath}/store/showKPI">KPI详情</a>
				</div>
		 	</div>
			<div id="filter_div">
				<form id="searchFrm" name="searchFrm" action="#">
					
					<#include "/base/showStore_filter.ftl" />
					
					<div class="filter_div_element">
						KPI：<input type="text" id="KPI" name="KPI" class="input-medium" value="${KPI}" style="top:-3px;" />
						<script type="text/javascript">
							loadKPI();
							//账号状态
							function loadKPI(){
								$.ajax({
								type : "post",
								url : "${contextPath}/comms/getKPI",
								dataType : "json",
								cache : false,
								success : function(data) {
									var jsonData = eval(data);
									var thisData = "[";
									$.each(jsonData, function(index, item) {
										if(index != jsonData.length-1){
											thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"},";
										} else {
											thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"}";
										}
									});
									thisData += "]";
									var cData = $.parseJSON(thisData);
									$("#KPI").select2({
										width:145,
										placeholder: "请选择",
										allowClear: true,
										data: cData
									});
								},
								error : function(data) {
									layer.alert("数据加载失败！");
								}
							});
							};
						</script>
					</div>
					
					
					<div class="filter_div_element">
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn" style="margin-top:10px;" />			
					</div>
					
				</form>
			</div>
			<div style="background:#FAEFC1;margin-left:20px;width:120px;height:40px;line-height:40px;vertical-align: middle;text-align:center;font-size:20px;">销量</div>
			<div id="l-map"></div>
		</div>
	  </div>
  <#include "/footer.ftl" />
    <script type="text/javascript">
    var map = new BMap.Map("l-map", {});                        // 创建Map实例
    map.centerAndZoom(new BMap.Point(121.523975,31.311873),10);     // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom();                        //启用滚轮放大缩小
    
    if (document.createElement('canvas').getContext) {  // 判断当前浏览器是否支持绘制海量点
        var points = [];  // 添加海量点数据
        
        //浦东
    	points.push(new BMap.Point(121.547403,31.236812));
    	points.push(new BMap.Point(121.540791,31.221496));
    	points.push(new BMap.Point(121.558039,31.222237));
    	points.push(new BMap.Point(121.578736,31.250397));
    	points.push(new BMap.Point(121.553439,31.252619));
    	points.push(new BMap.Point(121.587934,31.224214));
    	points.push(new BMap.Point(121.598283,31.196294));
    	points.push(new BMap.Point(121.586785,31.275832));
    	points.push(new BMap.Point(121.603888,31.298976));
    	points.push(new BMap.Point(121.567381,31.242431));
    	points.push(new BMap.Point(121.625735,31.220446));
    	points.push(new BMap.Point(121.637952,31.227981));
    	points.push(new BMap.Point(121.657068,31.268115));
    	points.push(new BMap.Point(121.658505,31.289719));
    	points.push(new BMap.Point(121.681071,31.246013));
    	points.push(new BMap.Point(121.695156,31.288238));
    	points.push(new BMap.Point(121.701336,31.222546));
    	points.push(new BMap.Point(121.701624,31.287374));
    	points.push(new BMap.Point(121.715709,31.232922));
    	points.push(new BMap.Point(121.728214,31.198827));
    	points.push(new BMap.Point(121.728789,31.258979));
    	points.push(new BMap.Point(121.683514,31.252681));
    	
    	//嘉定
    	points.push(new BMap.Point(121.263087,31.373958));
    	points.push(new BMap.Point(121.258488,31.411934));
    	points.push(new BMap.Point(121.233192,31.371984));
    	points.push(new BMap.Point(121.300457,31.35077));
    	points.push(new BMap.Point(121.222268,31.414399));
    	
    	points.push(new BMap.Point(121.264812,31.293022));
    	points.push(new BMap.Point(121.256763,31.320667));
    	points.push(new BMap.Point(121.232617,31.369518));
    	points.push(new BMap.Point(121.232042,31.372478));
    	points.push(new BMap.Point(121.253314,31.400099));
    	
    	points.push(new BMap.Point(121.236066,31.398619));
    	points.push(new BMap.Point(121.219394,31.324122));
    	points.push(new BMap.Point(121.221118,31.39418));
    	points.push(new BMap.Point(121.254463,31.360144));
    	points.push(new BMap.Point(121.255613,31.400592));
    	
    	points.push(new BMap.Point(121.214794,31.430669));
    	points.push(new BMap.Point(121.184899,31.390728));
    	points.push(new BMap.Point(121.227442,31.396646));
    	points.push(new BMap.Point(121.21307,31.363598));
    	points.push(new BMap.Point(121.251589,31.405523));
        
        //青浦
        points.push(new BMap.Point(121.12477,31.166712));
        points.push(new BMap.Point(121.163864,31.177588));
        points.push(new BMap.Point(121.117871,31.21515));
        points.push(new BMap.Point(121.164439,31.128636));
        points.push(new BMap.Point(121.167889,31.113303));
        
        points.push(new BMap.Point(121.036808,31.038578));
        points.push(new BMap.Point(121.08855,31.077185));
        points.push(new BMap.Point(121.16214,31.181542));
        points.push(new BMap.Point(121.140293,31.206255));
        points.push(new BMap.Point(121.182262,31.166218));
        
        points.push(new BMap.Point(120.980466,31.070751));
        points.push(new BMap.Point(120.985641,31.078174));
        points.push(new BMap.Point(121.031634,31.114292));
        points.push(new BMap.Point(121.05693,31.086092));
        points.push(new BMap.Point(121.213307,31.222561));
        
        points.push(new BMap.Point(121.101773,31.196865));
        points.push(new BMap.Point(121.179962,31.248251));
        points.push(new BMap.Point(121.283447,31.232443));
        points.push(new BMap.Point(121.269649,31.178082));
        points.push(new BMap.Point(121.240903,31.149407));
        
        //徐汇区
        points.push(new BMap.Point(121.441261,31.196124));
        points.push(new BMap.Point(121.44586,31.198348));
        points.push(new BMap.Point(121.44586,31.188463));
        points.push(new BMap.Point(121.432062,31.194641));
        points.push(new BMap.Point(121.441549,31.197112));
        
        points.push(new BMap.Point(121.458796,31.225526));
        points.push(new BMap.Point(121.434362,31.227255));
        points.push(new BMap.Point(121.490129,31.238618));
        points.push(new BMap.Point(121.489267,31.238865));
        points.push(new BMap.Point(121.48323,31.220091));
        points.push(new BMap.Point(121.50249,31.269242));
        points.push(new BMap.Point(121.41194,31.252202));
        points.push(new BMap.Point(121.434075,31.210208));
        
        //宝山
        points.push(new BMap.Point(121.476331,31.382758));
        points.push(new BMap.Point(121.48208,31.38769));
        points.push(new BMap.Point(121.505652,31.394102));
        points.push(new BMap.Point(121.443561,31.39854));
        points.push(new BMap.Point(121.472307,31.377825));
        
        points.push(new BMap.Point(121.478631,31.382758));
        points.push(new BMap.Point(121.48553,31.383744));
        points.push(new BMap.Point(121.431488,31.400513));
        points.push(new BMap.Point(121.488404,31.373879));
        points.push(new BMap.Point(121.428613,31.379305));
        
        //松江
        points.push(new BMap.Point(121.222218,31.051696));
        points.push(new BMap.Point(121.249814,31.065555));
        points.push(new BMap.Point(121.265337,31.025953));
        points.push(new BMap.Point(121.183699,31.073473));
        points.push(new BMap.Point(121.261887,31.081391));
        
        //奉贤
        points.push(new BMap.Point(121.465408,30.925388));
        points.push(new BMap.Point(121.470582,30.909525));
        points.push(new BMap.Point(121.436662,30.938274));
        points.push(new BMap.Point(121.555669,30.907047));
        points.push(new BMap.Point(121.507951,30.9685));
        
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
	
	function getBoundary(){      
		//map.clearOverlays();        //清除地图覆盖物      
		var bdary = new BMap.Boundary();
		
		bdary.get("浦东新区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});   
		
		bdary.get("黄浦区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		}); 
		
		bdary.get("徐汇区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("长宁区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("静安区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("普陀区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("闸北区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("虹口区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("杨浦区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		
		bdary.get("卢湾区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		
		bdary.get("闵行区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#ffffff"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		
		bdary.get("宝山区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("嘉定区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("金山区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("松江区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("青浦区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("奉贤区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("崇明县", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
	}

	setTimeout(function(){
		getBoundary();
	}, 1000);
	
  </script>
  </body>
</html>