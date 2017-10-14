	<div class="div_main_map" style="width:400px;height:400px;">
		<div class="div_main_map_title">销量</div>
		<div id="l-map4"></div>
	</div>
	
    <script type="text/javascript">
    var map4 = new BMap.Map("l-map4", {});                        // 创建Map实例
    map4.centerAndZoom(new BMap.Point(121.523975,31.311873),9);     // 初始化地图,设置中心点坐标和地图级别
    map4.enableScrollWheelZoom();                        //启用滚轮放大缩小
	
	function getBoundary4(){     
		map4.clearOverlays(); //清除地图覆盖物      
		var bdary = new BMap.Boundary();
		
		bdary.get("浦东新区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});   
		
		bdary.get("黄浦区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		}); 
		
		bdary.get("徐汇区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("长宁区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("静安区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("普陀区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#328535"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("闸北区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#328535"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("虹口区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("杨浦区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#328535"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		
		bdary.get("卢湾区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#328535"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		
		bdary.get("闵行区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		
		bdary.get("宝山区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("嘉定区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#328535"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("金山区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#91D493"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("松江区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#FFFFFF"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("青浦区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("奉贤区", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
		bdary.get("崇明县", function(rs){       //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000",fillColor: "#3B973E"}); //建立多边形覆盖物
				map4.addOverlay(ply);  //添加覆盖物
				//map.setViewport(ply.getPath());    //调整视野         
			}                
		});
		
	}

	setTimeout(function(){
		getBoundary4();
	},1000);
	
  </script>