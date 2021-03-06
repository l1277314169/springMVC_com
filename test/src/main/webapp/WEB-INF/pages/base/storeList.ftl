<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>门店管理</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<style type="text/css">
.overlay { 
	position: fixed; 
	top: 0px; 
	left: 0px; 
	width: 100%; 
	height: 100%; 
	z-index: 1999; 
	background-color: #000; 
	opacity: 0.2; 
	filter: alpha(opacity=20); 
} 
.moveBar { 
	position: absolute; 
	float:left;
	width: 800px; 
	height: 600px; 
	background-color:#ffffff; 
	border: solid 1px #000; 
	//left:20%; 
	//top:15%;
	margin: 0 auto; 
	text-align:center;
	padding:2px; 
	z-index: 2999; 
} 
</style>
</head>
<body  class="main_body">
<div class="boloc_moveBar" style="display:none" ><h2>更新中，请稍等……</h2></div> 
		<#assign privCode="C2M4">
		<#include "/base.ftl" />
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基本信息管理</a>
				<a class="linkPage active" href="${contextPath}/attendance/query">门店管理</a>
			</div>
	 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label " for="structureName">所属部门：</label>
							<div class="controls">
							  <input id="structureName" type="text" class="input-medium" name="structureName" readonly="readonly" value="${storeSearchVO.structureName!''}" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeNo">门店编号：</label>
							<div class="controls">
							  <input type="text" id="storeNo" name="storeNo" class="input-medium" value="${storeSearchVO.storeNo!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeName">门店名称：</label>
							<div class="controls">
							  <input type="text" id="storeName" name="storeName" class="input-medium" value="${storeSearchVO.storeName!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="users">业务员：</label>
							<div class="controls">
								<input id="clientUserId" name="clientUserId" value ="${storeSearchVO.clientUserId!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="channelName">所属渠道：</label>
							<div class="controls">
							 <input id="channelName" type="text" name="channelName" class="input-medium" value="${storeSearchVO.channelName!''}" readonly="readonly" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="distributorName">所属经销商：</label>
							<div class="controls">
							  <input id="distributorName"type="text" name="distributorName" class="input-medium" value="${storeSearchVO.distributorName!''}" readonly="readonly" />
							</div>
						</div>
						<div  class="fl">
						<label class="control-label fl" for="status">门店状态：</label>
						<div class="controls">
		                   <input type="text" class="input-medium" autocomplete="off" id="status" name="status" value="${storeSearchVO.status}"  />
						</div>
					</div>
						
	              	</div>
					<div class="form-actions">
					<@shiro.hasPermission name="C2M4F1">
						<button type="button" id="new_btn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M4F4">
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M4F7">
						<button type="button" id="exportStore" class="btn btn-success">门店导出</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M4F6">
						<button type="button" id="updatePosition" class="btn btn-primary">补全门店坐标</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M4F5">
						<button type="button" id="batchClientUser" class="btn btn-danger">业务员批量替换</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M4F9">
						<button type="button" id="batchChoiceStore" class="btn btn-danger">业务员门店维护</button>
					</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" id="clientStructureId" name="clientStructureId" value="${storeSearchVO.clientStructureId!''}">
					<input type="hidden" id="channelId" name="channelId" value="${storeSearchVO.channelId!''}">
					<input type="hidden" id="distributorId" name="distributorId" value="${storeSearchVO.distributorId!''}">
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" id="count" name="count" value="${count}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>大区</th>
						<th>所属部门</th>
						<th>省份</th>
						<th>城市</th>
						<th>一级渠道</th>
						<th>二级渠道</th>
						<th>店铺渠道</th>
						<th>所属经销商</th>
						<th>管理渠道</th>
						<th>店铺编号</th>
						<th>店铺名称</th>
						<th>地址</th>
						<th>促销员/业务代表/主管</th>
						<th>定位</th>
						<th>操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as store>
							<tr>
								<#if store.upStructureName?? && store.structureName??>
									<td title="${store.upStructureName!''}">
										<#if store.upStructureName?? && store.upStructureName?length gt 5>
											${store.upStructureName?substring(0,5)}..
										<#else>
											${store.upStructureName!''}
										</#if>
									</td>
									<td title="${store.structureName!''}">
										<#if store.structureName?? && store.structureName?length gt 5>
											${store.structureName?substring(0,5)}..
										<#else>
											${store.structureName!''}
										</#if>
									</td>
								</#if>
								<#if !(store.upStructureName??) && store.structureName??>
									<td title="${store.structureName!''}">
										<#if store.structureName?? && store.structureName?length gt 5>
											${store.structureName?substring(0,5)}..
										<#else>
											${store.structureName!''}
										</#if>
									</td>
									<td></td>
								</#if>	
								<#if !(store.upStructureName??) && !(store.structureName??)>
									<td></td>
									<td></td>
								</#if>	
								<td>
									${store.provinceName!''}
								</td>
								<td>
									${store.cityName!''}
								</td>
								<#if store.channelName1?? && store.channelName2??>
									<td title="${store.channelName1!''}">
										<#if store.channelName1?? && store.channelName1?length gt 3>
											${store.channelName1?substring(0,3)!''}...
										<#else>
											${store.channelName1!''}
										</#if>
									</td>
								
									<td title="${store.channelName2!''}">
										<#if store.channelName2?? && store.channelName2?length gt 3>
											${store.channelName2?substring(0,3)!}...
										<#else>
											${store.channelName2!''}
										</#if>
									</td>
								</#if>
								<#if !(store.channelName1??) && store.channelName2??>
									<td title="${store.channelName2!''}">
										<#if store.channelName2?? && store.channelName2?length gt 3>
											${store.channelName2?substring(0,3)!}...
										<#else>
											${store.channelName2!''}
										</#if>
									</td>
									<td></td>
								</#if>
								<#if !(store.channelName1??) && !(store.channelName2??)>
									<td></td>
									<td></td>
								</#if>
								<td title="${store.chainName!''}">
									<#if store.chainName?? && store.chainName?length gt 3>
										${store.chainName?substring(0,3)!}...
									<#else>
										${store.chainName!''}
									</#if>
								</td>
								<td title="${store.distributorName}">
									<#if store.distributorName?? && store.distributorName?length gt 6>
										${store.distributorName?substring(0,6)}...
									<#else>
										${store.distributorName!''}
									</#if>
								</td>
								<td>
									${store.storeTypeName!''}
								</td>
								<td title="${store.storeNo!''}">
									<#if store.storeNo?? && store.storeNo?length gt 8>
										${store.storeNo?substring(0,8)}...
									<#else>
										${store.storeNo!''}
									</#if>
								</td>
								<td title="${store.storeName!''}">
									<#if store.storeName?? && store.storeName?length gt 8>
										${store.storeName?substring(0,8)}...
									<#else>
										${store.storeName!''}
									</#if>
								</td>
								<td title="${store.addr!''}">
									<#if store.addr?? && store.addr?length gt 8>
										${store.addr?substring(0,8)}...
									<#else>
										${store.addr!''}
									</#if>
								</td>
								<td id="clientUserName" title="${store.names!''}">
									<#if store.names?? && store.names?length gt 16>
										${store.names?substring(0,16)}...
									<#else>	
										${store.names!''}
									</#if>					
								</td>
								<td>
									<#if store.longitude?? && store.latitude??>
										<a class="storeMap" href="javascript:void(0);" storeName="${store.storeName!''}" storeAddr="${store.addr!''}" longitude="${store.longitude!''}" latitude="${store.latitude!''}"><img src="${contextPath}/img/green12.png"></a>
									<#else>
										<a href="javascript:void(0);"><img src="${contextPath}/img/grey12.png"></a>
									</#if>
								</td>
								<td>
									<@shiro.hasPermission name="C2M4F2">
									<a class="editStore" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M4F3">
									<a class= "deleteStore"  href="javascript:void(0);" data="${store.storeId!''}">删除</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M4F8">
									 <a class="editStoreLocation" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}">定位</a> 								 
									</@shiro.hasPermission>
									<a class="moreDetails" href="javascript:void(0);" dataId="${store.storeId!''}" dataName="${store.storeName!''}">查看</a>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
					<#if pageParam.items?exists> 
						<div class="paging" > 
						   ${pageParam.getPagination()}
						</div> 
					</#if>
		</div>
		<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_structureName" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
		<div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_channelName" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
		<div id="menuContent_distributor" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_distributorName" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
		<a class="divMap" href="javascript:void(0);">
			<div id="divMap" class="overlay" style="display: none;"></div>
		</a>
		<div>
		<div></div>
		<div id="container" class="moveBar" style="display: none;"></div>
		</div>
			</div>
		</div>
     <#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
var importDialog,addDialog,editDialog,batchClientUserDialog,moreDetailsDialog,editStoreLocationDialog,batchChoiceStoreDialog;
$(function(){
	$("#structureName,#channelName,#distributorName").on("click",function(){
		var id = $(this).attr("id");
		showMenu(id);
	});
	var status = $("#status").val();
	loadStatus(status);
	
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
	
	
	
	//批量替换负责人
	$("#batchClientUser").bind("click",function(){
			var url = "${contextPath}/store/showBatchClientUser";
			// batchClientUserDialog = new xDialog(url, {}, {title:"批量替换负责人",iframe:true,width:800,height:640});
			layer.open({
			    type: 2,
			    title: '批量替换负责人',
			    iframe:true,
			    closeBtn: 1,
			    area: ['800px', '640px'],
			    content: url
			});
	});
	
	//业务员门店维护
	$("#batchChoiceStore").bind("click",function(){
			var url = "${contextPath}/store/showBatchChoiceStore"
		//	batchChoiceStoreDialog = new xDialog(url, {}, {title:"批量选择门店",iframe:true,width:800,height:640});
		layer.open({
			    type: 2,
			    title: '批量选择门店',
			    closeBtn: 1,
			    area: ['800px', '640px'],
			    content: url
			});
	});
	
	$("#updatePosition").on("click",function(){
		$('.boloc_moveBar').css('display','block');
		if($('.boloc_moveBar').css("display") == 'block'){
			$.ajax({
					url : "${contextPath}/store/updateStorePosition",
					type : "get",
					async: true,
					dataType:'JSON',
					success : function(result) {
						$('.boloc_moveBar').css('display','none');
						layer.alert("操作完成"); 
					},
					complete : function(data) {
						$('.boloc_moveBar').css('display','none');
					}
				});
		}else{
			$('.boloc_moveBar').css('display','none');
		}
	});
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/store/showImportDialog";
		// importDialog = new xDialog(url, {}, {title:"门店导入",width:650,height:330 });
		layer.open({
			    type: 2,
			    title: '门店导入',
			    iframe:true,
			    closeBtn: 1,
			    area: ['650px', '320px'],
			    content: url
			});
		return true;
	});
	//导出门店信息
	$("#exportStore").bind("click",function(){
		layer.confirm("确认导出门店数据吗？", function () {
			var url1 = "${contextPath}/store/exportStore";
			$("#searchForm").attr("action",url1);
			$("#searchForm").submit();
			var url2 = "${contextPath}/store/query";
			$("#searchForm").attr("action",url2);
		});
		return false;	
	});	
	 
	//新增
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/store/showAddStore";
		// addDialog = new xDialog(url, {}, {title:"新增门店",width:760,height:656});
		layer.open({
			    type: 2,
			    title: '新增门店',
			    closeBtn: 1,
			    area: ['760px', '656px'],
			    content: url
			});
		//return false;	
	});
	
	//编辑
	$("a.editStore").bind("click",function(){
	    var storeId=$(this).attr("dataId");
	    var storeName=$(this).attr("dataName");
		var url = "${contextPath}/store/showEditStore/"+storeId;
	//	editDialog = new xDialog(url,{},{title:"编辑门店--"+storeName,width:760,height:656}); 
	    layer.open({
			    type: 2,
			    title:'编辑门店--'+storeName,
			    closeBtn: 1,
			    area: ['760px', '620px'],
			    content: url
			});
	//	return false;	
	});	
	
	//编辑门店坐标
	$("a.editStoreLocation").bind("click",function(){
	    var storeId=$(this).attr("dataId");
	    var storeName=$(this).attr("dataName");
		var url = "${contextPath}/store/showEditStoreLocation/"+storeId;
	//	editStoreLocationDialog = new xDialog(url,{},{title:"编辑门店坐标--"+storeName,iframe:true,width:800,height:600});
	    layer.open({
			    type: 2,
			    title:'编辑门店坐标--'+storeName,
			    closeBtn: 1,
			    area: ['800px', '570px'],
			    content: url
			});
	});	
	
	//查看门店详细信息
	$("a.moreDetails").bind("click",function(){
	    var storeId=$(this).attr("dataId");
	    var storeName=$(this).attr("dataName");
		var url = "${contextPath}/store/showStoreDetail/"+storeId;
	//	moreDetailsDialog = new xDialog(url,{},{title:storeName+"信息",width:620,height:'auto'});
	    layer.open({
			    type: 2,
			    title:storeName+'信息',
			    closeBtn: 1,
			    area: ['620px', '511px'],
			    content: url
			});
	//	return false;	
	});	
	
	//删除
	$("a.deleteStore").bind("click",function(){
		var storeId =$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/store/deleteStore/"+storeId,
				type : "get",
				dataType:'JSON',
				success : function(result) {
					 confirmAndRefresh(result);
				}
			});
	   });
		   return false;
	});
	
	function confirmAndRefresh(result){
		if (result.code == "success") {
				//删除重新加载
				window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				layer.alert(result.message);
			}});
		}
	}
	
	$("#clientStructureName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#clientStructureName").val(""); 
			$("#clientStructureId").val(""); 
		}; 
	});
	$("#channelName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#channelName").val(""); 
			$("#channelId").val(""); 
		}; 
	});
	$("#distributorName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#distributorName").val(""); 
			$("#distributorId").val(""); 
		}; 
	});
		
});
	
	var selectId;
	var setting_st = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
        view: {
            dblClickExpand: false,
            selectedMulti: true, //是否允许多选
            txtSelectedEnable: false //是否允许选中节点的文字
            //autoCancelSelected: false //不允许按下Ctrl键取消节点选中状态
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
    
    var setting_cl = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
			error : function() {  
                 layer.alert('亲，渠道加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
        view: {
            dblClickExpand: false,
            selectedMulti: true, //是否允许多选
            txtSelectedEnable: false //是否允许选中节点的文字
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onAsyncSuccess: zTreeOnAsyncSuccess,
            onClick: onClick
			
        }
    };
    
    var setting_distributor = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/distributor/getTreeNode",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
        view: {
            dblClickExpand: false,
            selectedMulti: true, //是否允许多选
            txtSelectedEnable: false //是否允许选中节点的文字
            //autoCancelSelected: false //不允许按下Ctrl键取消节点选中状态
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
        
     function beforeClick(treeId, treeNode) {
    	var demo = "treeDemo_"+selectId;
        var zTree = $.fn.zTree.getZTreeObj(demo);
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#"+selectId).attr("value", treeNode.name);
    	if(selectId == 'structureName') {
			$("#clientStructureId").attr("value", treeNode.id);
        }
        if(selectId == 'channelName') {
			$("#channelId").attr("value", treeNode.id);
        }
        if(selectId == 'distributorName'){
        	$("#distributorId").attr("value", treeNode.id);
  
        }
        hideMenu();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandAll(true);
		if(treeId == 'treeDemo_structureName'){
			var node = zTree.getNodeByParam("id", $("#clientStructureId").val(), null);
			if(node != null){
				$("#structureName").val(node.name);
			}
		}
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'structureName') {
        	$("#menuContent_st").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if (objName == 'channelName'){
        	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if(objName == 'distributorName') {
			$("#menuContent_distributor").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}else {
			
       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="structureName") {
     	   $("#menuContent_st").fadeOut("fast");
    	}
    	if(selectId =="channelName") {
     	   $("#menuContent_cl").fadeOut("fast");
    	}
    	if(selectId =="distributorName"){
    		$("#menuContent_distributor").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="structureName"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_st" || $(event.target).parents("#menuContent_st").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="distributorName"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_distributor" || $(event.target).parents("#menuContent_distributor").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="channelName") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
	            hideMenu();
        	}
    	}
    }
    
    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo_structureName"), setting_st);
        $.fn.zTree.init($("#treeDemo_channelName"), setting_cl);
        $.fn.zTree.init($("#treeDemo_distributorName"), setting_distributor);
        
        
        $("#clientUserId").select2({
        	width:150,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear: true,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserPosition",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						page: page
					};
				},
				results: function (data,page) {
					return { results: data};
				}
			},
			initSelection: function(element, callback) {
				var id = $(element).val();
				if (id !== "") {
					$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
						dataType: "json"
					}).done(function(data) { callback(data); });
				}
			},
			formatResult: repoFormatResult,
			formatSelection: repoFormatSelection,
			escapeMarkup: function (m) { return m; }
		}); 
		
		function repoFormatResult(repo) {
			return repo.name;
		}
		function repoFormatSelection(repo) {
			return repo.name;
		}
    });
    
function loadStatus(status){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/getStatus",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#status").select2({
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