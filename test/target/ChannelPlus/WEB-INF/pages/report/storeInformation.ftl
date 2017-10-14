<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/channel-style.css">
<title>地址解析</title>
<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
 
  <style type="text/css">
  	#allmap {width: 98%;height: 81%; overflow: hidden;margin: 0 auto;font-family:"微软雅黑";}
  
    body, html{
			width: 100%;
			height: 100%;
			margin:0;
			font-family:"微软雅黑";
			font-size:14px;
		}
		#l-map {
			width:100%; 
			height:100%;
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
</head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<body  class="allH main_body">
<#assign privCode="C4M10">
<#include "/base.ftl" />
	<div id="content"  style="height:100%;">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">统计报表</a>
				<a class="linkPage active" href="${contextPath}/store/information">门店信息窗口</a>
			</div>
	 	</div>
		<div id="filter_div">
		<form id="searchFrm" name="searchFrm" action="#">
			<#include "/base/showStore_filter.ftl" />
		</form>
	</div>
	<div id="allmap"></div>
  </div>
  <#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var sContent1 = 
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100'><h4>上海易买得老西门店<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:leftpadding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
 
	var sContent2 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails10.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4>世纪联华大卖场<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
	
	var sContent3 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails4.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4>上海黄埔新苑店<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
	
	var sContent4 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails4.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4>淮海中路OPA店<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
	var sContent5 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails6.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4> 乐购超市(河南南路店)<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
	var sContent6 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails4.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4> 乐购超市(河南南路店)<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
	var sContent7 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4> 乐购超市(河南南路店)<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
	var sContent8 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails4.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4> 乐购超市(河南南路店)<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
 	var sContent9 =
	"<table width='410' height='500' class='table-bordered table-striped'><tr><td><div width='300'><div style='float:left;padding:3px'><img id='imgDemo' src='${contextPath}/img/thumbnails.jpg' width='200' height='101' />"+
	"</div></td><td><div style='float:left;padding:3px;padding-bottom:50px;padding-left:10px' width='100' ><h4> 乐购超市(河南南路店)<br>促销人员数：6<br>项目数：2</h4></div></td></tr>"+
	"<tr><td><div style='float:left;padding:3px'><img src='${contextPath}/img/thumbnails2.jpg' width='200' height='101'></div>"+
	"</td><td><div style='float:left;padding:3px'><img src='${contextPath}/img/logo11914.png' width='200' height='101'></div></td></tr></table>";
 
	var map = new BMap.Map("allmap");
	//var point = new BMap.Point(116.404, 31.203005);
	var point1 = new BMap.Point(121.48013,31.203005);
	var point2 = new BMap.Point(121.45282,31.206358);
	var point3 = new BMap.Point(121.46634166666666,31.219498333333334);
	var point4 = new BMap.Point(121.43633,31.203558);
	var point5 = new BMap.Point(121.46607,31.207203);
	var point6 = new BMap.Point(121.477974,31.208573);
	var point7 = new BMap.Point(121.41156,31.203019);
	var point8 = new BMap.Point(121.42514,31.209156);
	var point9 = new BMap.Point(121.44078,31.20292);
	
	var point10 = new BMap.Point(121.420158,31.205337);
	var point11 = new BMap.Point(121.466841,31.204658);
	var point12 = new BMap.Point(121.450075,31.209074);
	var point13 = new BMap.Point(121.476852,31.266789);
	var point14 = new BMap.Point(121.428002,31.242218);
	var point15 = new BMap.Point(121.432889,31.270612);
	var point16 = new BMap.Point(121.446338,31.291724);
	var point17 = new BMap.Point(121.410722,31.282619);
	var point18 = new BMap.Point(121.422231,31.276511);
	var point19 = new BMap.Point(121.496637,31.261112);
	var point20 = new BMap.Point(121.480744,31.257715);			
	
	
 
	var point21 = new BMap.Point(121.420158,31.215337);
	var point22 = new BMap.Point(121.466841,31.214658);
	var point23 = new BMap.Point(121.450075,31.219074);
	var point24 = new BMap.Point(121.476852,31.216789);
	var point25 = new BMap.Point(121.428002,31.212218);
	var point26 = new BMap.Point(121.432889,31.210612);
	var point27 = new BMap.Point(121.446338,31.211724);
	var point28 = new BMap.Point(121.410722,31.212619);
	var point29 = new BMap.Point(121.422231,31.216511);
	var point30 = new BMap.Point(121.496637,31.211112);
	var point31 = new BMap.Point(121.480744,31.217715);	
	
	
	
	//var marker = new BMap.Marker(point);
	
	var myIcon = new BMap.Icon("${contextPath}/img/map_24_24.png", new BMap.Size(32,32),{
    			anchor: new BMap.Size(15, 30)
			});
	var marker1 = new BMap.Marker(point1);
	var marker2 = new BMap.Marker(point2);
	var marker3 = new BMap.Marker(point3,{icon:myIcon});
	var marker4 = new BMap.Marker(point4,{icon:myIcon});
	var marker5 = new BMap.Marker(point5,{icon:myIcon});
	var marker6 = new BMap.Marker(point6,{icon:myIcon});
	var marker7 = new BMap.Marker(point7);
	var marker8 = new BMap.Marker(point8);
	var marker9 = new BMap.Marker(point9,{icon:myIcon});	
	var marker10 = new BMap.Marker(point10);
	var marker11 = new BMap.Marker(point12);
	var marker12 = new BMap.Marker(point13,{icon:myIcon});
	var marker13 = new BMap.Marker(point14,{icon:myIcon});
	var marker14 = new BMap.Marker(point15);
	var marker15 = new BMap.Marker(point16,{icon:myIcon});
	var marker16 = new BMap.Marker(point17);
	var marker17 = new BMap.Marker(point18);
	var marker18 = new BMap.Marker(point19,{icon:myIcon});
	var marker19 = new BMap.Marker(point11);
	var marker20 = new BMap.Marker(point20,{icon:myIcon});
	
	var marker21 = new BMap.Marker(point21);
	var marker22 = new BMap.Marker(point22);
	var marker23 = new BMap.Marker(point23,{icon:myIcon});
	var marker24 = new BMap.Marker(point24,{icon:myIcon});
	var marker25 = new BMap.Marker(point25);
	var marker26 = new BMap.Marker(point26,{icon:myIcon});
	var marker27 = new BMap.Marker(point27);
	var marker28 = new BMap.Marker(point28);
	var marker29 = new BMap.Marker(point29,{icon:myIcon});
	var marker30 = new BMap.Marker(point30);
	var marker31 = new BMap.Marker(point31,{icon:myIcon});
	
	
	
	var infoWindow1 = new BMap.InfoWindow(sContent1);  // 创建信息窗口对象
	var infoWindow3 = new BMap.InfoWindow(sContent2);  // 创建信息窗口对象
	var infoWindow2 = new BMap.InfoWindow(sContent3);  // 创建信息窗口对象
	var infoWindow4 = new BMap.InfoWindow(sContent4);  // 创建信息窗口对象
	var infoWindow5 = new BMap.InfoWindow(sContent5);  // 创建信息窗口对象
	var infoWindow6 = new BMap.InfoWindow(sContent6);  // 创建信息窗口对象
	var infoWindow7 = new BMap.InfoWindow(sContent7);  // 创建信息窗口对象
	var infoWindow8 = new BMap.InfoWindow(sContent8);  // 创建信息窗口对象
	var infoWindow9 = new BMap.InfoWindow(sContent9);  // 创建信息窗口对象
	//map.centerAndZoom(point, 15);
	map.centerAndZoom(point1, 15);
	map.centerAndZoom(point2, 15);
	//map.centerAndZoom(point3, 15);
	//map.addOverlay(marker);
	map.addOverlay(marker1);
	map.addOverlay(marker2);
	map.addOverlay(marker3);
	map.addOverlay(marker4);
	map.addOverlay(marker5);
	map.addOverlay(marker6);
	map.addOverlay(marker7);
	map.addOverlay(marker8);
	map.addOverlay(marker9);	
	map.addOverlay(marker10);
	map.addOverlay(marker11);
	map.addOverlay(marker12);
	map.addOverlay(marker13);
	map.addOverlay(marker14);
	map.addOverlay(marker15);
	map.addOverlay(marker16);
	map.addOverlay(marker17);
	map.addOverlay(marker18);
	map.addOverlay(marker19);
	map.addOverlay(marker20);
	
	map.addOverlay(marker21);
	map.addOverlay(marker22);
	map.addOverlay(marker23);
	map.addOverlay(marker24);
	map.addOverlay(marker25);
	map.addOverlay(marker26);
	map.addOverlay(marker27);
	map.addOverlay(marker28);
	map.addOverlay(marker29);
	map.addOverlay(marker30);
	map.addOverlay(marker31);
	marker1.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow1);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker2.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow2);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker3.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow3);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker4.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow4);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker5.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow5);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker6.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow6);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker7.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow7);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker8.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow8);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker9.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	
	marker10.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow5);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	
	marker11.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow6);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker12.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow1);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker13.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow2);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker14.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow3);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker15.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow4);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker16.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow5);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker17.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow6);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker18.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow7);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker19.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow8);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker20.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker21.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker22.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker23.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker24.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker25.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker26.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker27.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker28.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker29.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker30.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
	marker31.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow9);
	   //图片加载完毕重绘infowindow
	   document.getElementById('imgDemo').onload = function (){
		   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   }
	});
</script>
