<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>考勤管理</title>
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
	width: 550px; 
	height: 350px; 
	background-color:#ffffff; 
	border: solid 1px #000; 
	left:20%; 
	top:15%; 
	padding:2px; 
	z-index: 2999; 
} 
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
<SCRIPT type="text/javascript">

	$(document).ready(function(){
		$(".lightbox").lightbox({
			fitToScreen: true
		});
	});
</SCRIPT>
</head>
<body class="main_body">
        <#assign privCode="C1M4">
		<#include "/base.ftl" />
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">执行管理</a>
				<a class="linkPage active" href="${contextPath}/attendance/query">考勤管理</a>
			</div>
	 	</div>
			<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
					<div class="fl">
						<label class="control-label fl" for="clientUserName">人员姓名：</label>
						<div class="controls">
						  <input type="text" id="clientUserName" name="clientUserName" class="input-medium" value="${clientUserName!''}">
						</div>
					</div>
					<div class="fl">
						<label class="control-label fl" for="structure">所属部门：</label>
						<div class="controls">
							<input id="structure" type="text" class="input-medium" name="clientStructureName" readonly   value="${clientStructureName!''}" readonly onclick="showMenu(this.id);"/>
							<input type="text" id="structureId" name="structureId" value="${clientStructureId}" style="display:none;">
						</div>
					</div>
					<div class="fl">
						<label class="control-label fl" for="inTime">开始日期：</label>
						<div class="controls">
						  <input type="text" id="firstDayOfCurrentMonth" name="firstDayOfCurrentMonth"  class="inTimePicker" value="${firstDayOfCurrentMonth!''}">
						</div>
					</div>
					<div class="fl">
						<label class="control-label fl" for="inTime">结束日期：</label>
						<div class="controls">
						  <input type="text" id="lastDayOfCurrentMonth" name="lastDayOfCurrentMonth"  class="inTimePicker" value="${lastDayOfCurrentMonth!''}">&nbsp;&nbsp;&nbsp;(最长1个月)
						</div>
					</div>
              	</div>
				<div class="form-actions">
				<@shiro.hasPermission name="C1M4F1">
					<button type="button" class="btn btn-primary" id="new_btn" onclick="">考勤导出</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="C1M4F2">
					<button type="button" class="btn btn-primary" id="importBtn">导入</button>
				</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" name="page" value="${page}">
				<input type="hidden" id="clientStructureId" name="clientStructureId" value="${clientStructureId!''}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th width="7%">姓名</th>
				      	<th width="10%">部门</th>
				      	<th width="10%">考勤日期</th>
				      	<th width="12%">上班时间</th>
				      	<th width="12%">下班时间</th>
				      	<th width="7%">考勤照片</th>
				      	<th width ="7%">上班定位</th>
				      	<th width ="7%">下班定位</th>
						<th>备注</th>
					</tr>
					<tbody>
						<#list pageParam.items as mod>
						<tr>
							<td title="${mod.clientUserName!''}:${mod.loginName!''}">${(mod.clientUserName)!''}</td>
							<td>${(mod.structureName)!''}</td>
							<td>
								<#if (mod.attrDate)?? && mod.clientId == 4>
										${(mod.attrDate)?string("yyyy-MM-dd EE")}
									<#else>
										<#if (mod.inTime)??>
											${(mod.inTime)?string("yyyy-MM-dd EE")}
										</#if>
									</#if>
							</td>
							<td><#if (mod.inTime)??>${(mod.inTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							<td><#if (mod.outTime)??>${mod.outTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							<td>
								<#if mod.inPic?? && mod.inPic?length gt 0>
								<a href="${contextPath}/uploadfiles/${mod.clientId}/${mod.clientUserId}/${mod.inPic}" class="lightbox"><img src="${contextPath}/uploadfiles/${mod.clientId}/${mod.clientUserId}/${mod.inPic}" style="width:20;height:20;" />&nbsp;
								</#if>
								<#if mod.outPic?? && mod.outPic?length gt 0>
								<a href="${contextPath}/uploadfiles/${mod.clientId}/${mod.clientUserId}/${mod.outPic}" class="lightbox"><img src="${contextPath}/uploadfiles/${mod.clientId}/${mod.clientUserId}/${mod.outPic}" style="width:20;height:20;" />
								</#if>
							</td>
							<td>
								<#if mod.inLongitude?? && mod.inLatitude??>
									<a class="inCheckMap" href="javascript:void(0);" inLongitude="${mod.inLongitude!}" inLatitude="${mod.inLatitude!}" 
									outLongitude="${mod.outLongitude!}" outLatitude="${mod.outLatitude!}" userName="${mod.clientUserName!''}">
										<img src="${contextPath}/img/green12.png">
									</a>
								<#else>
									<a  href="javascript:void(0);"><img src="${contextPath}/img/grey12.png"></a>
								</#if>
							</td>
							<td>
								<#if mod.outLongitude?? && mod.outLatitude??>
									<a class="outCheckMap" href="javascript:void(0);" inLongitude="${mod.inLongitude!}" inLatitude="${mod.inLatitude!}" 
									outLongitude="${mod.outLongitude!}" outLatitude="${mod.outLatitude!}" userName="${mod.clientUserName!''}">
										<img src="${contextPath}/img/green12.png">
									</a>
								<#else>
									<a href="javascript:void(0);"><img src="${contextPath}/img/grey12.png"></a>
								</#if>
							</td>
							<td>${(mod.remark)!''}</td>
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
		
<a class="divMap" href="javascript:void(0);">
<div id="divMap" class="overlay" style="display: none;"></div>
</a>
<div id="container" class="moveBar" style="display: none;"></div>
<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_structure" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>
 
</div>
 <#include "/footer.ftl" />
</body>
</html>
<script>
var importDialog;
$(function(){	
  function AddDays(date){
		var nd = new Date(date);
		nd.setDate(nd.getDate()+30);
		var y = nd.getFullYear();
		var m = nd.getMonth()+1;
		var d = nd.getDate();
		if(m <= 9) m = "0"+m;
		if(d <= 9) d = "0"+d; 
		var cdate = y+"-"+m+"-"+d;
		return cdate;
	}
	
	//时间插件
	$("#firstDayOfCurrentMonth").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			var endText=AddDays(dateText);
			$("#lastDayOfCurrentMonth").datepicker("option","minDate",dateText);
			$("#lastDayOfCurrentMonth").datepicker("option","maxDate",endText);
			$(this).focus();
			$(this).blur();
		}
	});
	
	$("#lastDayOfCurrentMonth").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		beforeShow: function() {
			var query=$("#firstDayOfCurrentMonth").val();
			var dateText=AddDays(query);
			$("#lastDayOfCurrentMonth").datepicker("option","maxDate",dateText);
		},
		onSelect:function(dateText,inst){
			$("#firstDayOfCurrentMonth").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
	
	//导出
	$("#new_btn").bind("click",function(){
		var inStartTime = $("#firstDayOfCurrentMonth").val();
		var inEndTime = $("#lastDayOfCurrentMonth").val();
		var structureName = $("#structure").val();
		var clientUserName = $("#clientUserName").val();
		var structureId = $("#structureId").val();
		layer.confirm("确认导出"+"-"+structureName+"-"+inStartTime+" 至 "+inEndTime+"期间考勤数据吗？", function () {
			var url = "${contextPath}/attendance/exportAttendance?inStartTime="+inStartTime+"&inEndTime="+inEndTime+"&clientUserName="+clientUserName+"&structureId="+structureId+"&structureName="+structureName;
			window.location.href = url;
		});
		return false;	
	});	
	
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/attendance/showImportDialog";
		// importDialog = new xDialog(url, {}, {title:"考勤导入",width:660,height:340 });
		 layer.open({
			    type: 2,
			    title: '考勤导入',
			    closeBtn: 1,
			    area: ['660px', '340px'],
			    content: url
			});
		return false;
	});
	
	
	//上班定位
	$("a.inCheckMap").bind("click",function(){
		$("#divMap").css("display","block");
		$("#container").css("display","block");
		var inLongitude = $(this).attr("inLongitude");
		var inLatitude = $(this).attr("inLatitude");
		var outLongitude = $(this).attr("outLongitude");
		var outLatitude = $(this).attr("outLatitude");
		//alert("上班定位"+inLongitude+"--"+inLatitude+"下班定位"+outLongitude+"--"+outLatitude);
		var clientUserName = $(this).attr("userName");
		var map = new BMap.Map("container"); // 创建地图实例
		map.enableScrollWheelZoom();
		var point = new BMap.Point(inLongitude, inLatitude); // 创建点坐标
		map.centerAndZoom(point, 17); // 初始化地图，设置中心点坐标和地图级别
		
		var adds = [
			new BMap.Point(inLongitude, inLatitude)
		];
		
		for(var i = 0; i<adds.length; i++){
			var marker = new BMap.Marker(adds[i]);
			map.addOverlay(marker);
			marker.setLabel(new BMap.Label("上班：进店定位",{offset:new BMap.Size(10,-15)}));
		}
		
		var data_info = [[outLongitude,outLatitude,"下班：出店定位"]];
		var opts = {
				width : 10,     // 信息窗口宽度
				height: 10,     // 信息窗口高度
				title : ""  // 信息窗口标题
			   };
		
		for(var i=0;i<data_info.length;i++){
			var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
			var content = data_info[i][2];
			map.addOverlay(marker);               // 将标注添加到地图中
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
	
	//下班定位
	$("a.outCheckMap").bind("click",function(){
		$("#divMap").css("display","block");
		$("#container").css("display","block");
		var inLongitude = $("a.inCheckMap").attr("inLongitude");
		var inLatitude = $("a.inCheckMap").attr("inLatitude");
		var outLongitude = $(this).attr("outLongitude");
		var outLatitude = $(this).attr("outLatitude");
		var clientUserName = $(this).attr("userName");
		var map = new BMap.Map("container"); // 创建地图实例
		map.enableScrollWheelZoom();
		var point = new BMap.Point(outLongitude, outLatitude); // 创建点坐标
		map.centerAndZoom(point, 17); // 初始化地图，设置中心点坐标和地图级别
		
		var adds = [
			new BMap.Point(outLongitude, outLatitude)
		];
		
		for(var i = 0; i<adds.length; i++){
			var marker = new BMap.Marker(adds[i]);
			map.addOverlay(marker);
			marker.setLabel(new BMap.Label("下班：出店定位",{offset:new BMap.Size(10,-15)}));
		}
		
		var data_info = [[inLongitude,inLatitude,"上班：进店定位"]];
		var opts = {
				width : 10,     // 信息窗口宽度
				height: 10,     // 信息窗口高度
				title : ""  // 信息窗口标题
			   };
		
		for(var i=0;i<data_info.length;i++){
			var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
			var content = data_info[i][2];
			map.addOverlay(marker);               // 将标注添加到地图中
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
    
    $("#structure").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#structure").val(""); 
			$("#clientStructureId").val(""); 
		}; 
	});
	
	
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
	if(selectId == 'structure') {
		$("#clientStructureId").attr("value", treeNode.id);
    }
    $("#structureId").attr("value", treeNode.id);
    hideMenu();
     return false;
}

//异步加载成功后
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.expandAll(true);
	//多棵树的时候要判断下
	if(treeId = 'treeDemo_structure'){
		var node = zTree.getNodeByParam("id", $("#structureId").val(), null);
		if(node != null){
			$("#structure").val(node.name);
		}
	}
	
};

function showMenu(objName) {
	selectId = objName;
	var obj = $("#"+objName);
    var objOffset = obj.position();
    if(objName == 'structure') {
    	$("#menuContent_st").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
    }
    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	if(selectId =="structure") {
 	   $("#menuContent_st").fadeOut("fast");
	}
    $("body").unbind("mousedown", onBodyDown);
}        
function onBodyDown(event) {
	if(selectId =="structure"){
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_st" || $(event.target).parents("#menuContent_st").length > 0)) {
            hideMenu();
    	}
	}
}

$(document).ready(function(){
	 $.fn.zTree.init($("#treeDemo_structure"), setting_st);
	 if($(".inTimePicker").val() == ''){
	 	$(".inTimePicker").val(new Date().format("yyyy-MM-dd"));
	 }
	 
});

Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1, 
        "d+" : this.getDate(), 
        "h+" : this.getHours(), 
        "m+" : this.getMinutes(), 
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
</script>