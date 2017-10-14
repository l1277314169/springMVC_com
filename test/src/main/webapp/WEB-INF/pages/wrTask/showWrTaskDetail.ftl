<table class="table table-bordered data-table" style="overflow:auto;">
    <tbody>
    	<tr>
			<td class="fill_td">任务地点：</td>
			<td class="fill_td">${workplace}</td>
		</tr>
		<#if storeName??>
			<tr>
				<td class="fill_td">门店名称：</td>
				<td class="fill_td">${storeName}</td>
			</tr>
		</#if>
		<#if customerName??>
			<tr>
				<td class="fill_td">客户名称：</td>
				<td class="fill_td">${customerName}</td>
			</tr>
		</#if>
		<#if brandName??>
			<tr>
				<td class="fill_td">品牌名称：</td>
				<td class="fill_td">${brandName}</td>
			</tr>
		</#if>
		<tr>
			<td class="fill_td">项目类型：</td>
			<td class="fill_td">${projectName}</td>
		</tr>
		<tr>
			<td class="fill_td">定位：</td>
			<td class="fill_td">
				<a class="storeMap" href="javascript:void(0);" storeAddr="${store.addr!''}" longitude="${wrTaskData.inLongitude!''}" latitude="${wrTaskData.inLatitude!''}"><img src="${contextPath}/img/green12.png"></a>
			</td>
		</tr>
    	<#list wrTaskParameters as wrTaskParameter>
    		<#assign key="${wrTaskParameter.wrTaskParameterId}"/>
    		<tr>
	    	    <td class="fill_td">${wrTaskParameter.parameterName}：</td>
	            <td  class="fill_td">
	            	<#if wrTaskParameter.controlType=='1' || wrTaskParameter.controlType=='3' || wrTaskParameter.controlType=='4' || wrTaskParameter.controlType=='9'>
	            		${parameterValueMap[key]}
            		<#elseif wrTaskParameter.controlType=='11'>
            			<#if parameterValueMap[key]??>
            				<#list parameterValueMap[key]?split(",") as imgSrc>
            					<a href="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${imgSrc}" rel="flowers" class="lightPhoto" style="display: block;float: left;">
									<img  src="${contextPath}/uploadfiles/${clientId}/${clientUserId}/thumbnail/s_${imgSrc}" />
								</a>
							</#list>
            			</#if>
					<#else>
						${parameterValueMap[key]}
	            	</#if>
            	</td>           	    
	    	</tr>
    	</#list>
    	<a class="divMap" href="javascript:void(0);">
			<div id="divMap" class="overlay" style="display: none;"></div>
		</a>
		<div>
		<div></div>
		<div id="container" class="moveBar" style="display: none;"></div>
		</div>
    </tbody>
</table>
<script type="text/javascript">
	jQuery(document).ready(function() {
		$(".lightPhoto").lightbox({
			fitToScreen: true
		});
		
		//门店定位
		$("a.storeMap").bind("click",function(){
			$("#divMap").css("display","block");
			//让container居中
			var windowWidth = document.body.offsetWidth;
			var windowHeight  = document.body.offsetHeight;
			
			var popupHeight =$("#container").height();
			var popupWidth =$("#container").width();
			$("#container").css({
				"top": (windowHeight-popupHeight-200)/2+$(document).scrollTop()+130,
				"left": (windowWidth-popupWidth)/2
			});
			
			$("#container").css("display","block");
			var longitude = $(this).attr("longitude");
			var latitude = $(this).attr("latitude");
			var storeName = $(this).attr("storeName");
			var storeAddr = $(this).attr("storeAddr");
			var map = new BMap.Map("container"); // 创建地图实例
			map.enableScrollWheelZoom();
			
			var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
			var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
			map.addControl(top_left_control);        
			map.addControl(top_left_navigation);     
			
			var point = new BMap.Point(longitude, latitude); // 创建点坐标
			var marker = new BMap.Marker(point);  // 创建标注
			map.addOverlay(marker);// 将标注添加到地图中
			map.centerAndZoom(point, 17); // 初始化地图，设置中心点坐标和地图级别
	
			var opts = {
			  width : 200,     // 信息窗口宽度
			  height: 70,     // 信息窗口高度
			  title : storeName, // 信息窗口标题
			  enableMessage:false,//设置允许信息窗发送短息
			  message : "欢迎使用channelPlus系统…"
			}
			var infoWindow = new BMap.InfoWindow("地址："+storeAddr, opts);  // 创建信息窗口对象 
			map.openInfoWindow(infoWindow,point); //开启信息窗口
			
			marker.addEventListener("click", function(){          
				map.openInfoWindow(infoWindow,point); //开启信息窗口
			});
			
		});
		
		//关闭定位地图
		$("a.divMap").bind("click",function(){
			document.getElementById("divMap").style.display="none";
			document.getElementById("container").style.display="none";
		});
	
	});
</script>