<html>
<head>
<title>排班计划</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript">
	var selectId;
	var setting_st = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {
				alert('亲，组织架构加载失败！'); 
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
    	if(selectId == 'structure') {
			$("#clientStructureId").attr("value", treeNode.id);
        }
        hideMenu();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
		zTree.expandAll(true);
		//多棵树的情况要判断下
		if(treeId = 'treeDemo_structure'){
			var node = zTree.getNodeByParam("id", $("#clientStructureId").val(), null);
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
     $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo_structure"), setting_st);
        
        $("#structure").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
				$("#structure").val(""); 
				$("#clientStructureId").val(""); 
			}; 
		});
   });
</script>
</head>
<body class="main_body">
		<#assign privCode="C1M7">
	<#include "/base.ftl" />
	
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">执行管理</a>
				<a class="linkPage active" href="${contextPath}/workTimeSettings/query">排班计划</a>
			</div>
	 	</div>
	 	
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
							<label class="control-label " for="structure">人员部门：</label>
							<div class="controls">
								<input type="text" id="structure" name="clientStructureName" value="${workTimeSettingsSearchVO.clientStructureName!''}" class="input-medium" readonly="readonly" onclick="showMenu(this.id);">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="clientUserName">人员姓名：</label>
							<div class="controls">
								<input type="text" name="clientUserName" class="input-medium"  value="${workTimeSettingsSearchVO.clientUserName!''}">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="clientUserName">职位：</label>
							<div class="controls">
								<input type="text" id="clientPositionId" name="clientPositionId" class="input-medium"  value="${workTimeSettingsSearchVO.getClientPositionId()!''}">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label">开始时间：</label>
							<div class="controls">
								<input type="text" id="workDate1" name="workDate1"  value="${workTimeSettingsSearchVO.workDate1!''}"  class="input-medium" >
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label">结束时间：</label>
							<div class="controls">
								<input type="text" id="workDate2" name="workDate2" value="${workTimeSettingsSearchVO.workDate2!''}" class="input-medium" >
							</div>
					</div>
					
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="status">在职状态：</label>
						<div class="controls">
		                   <input type="text" id="status" name="status" class="input-medium" value="${workTimeSettingsSearchVO.status}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="isActivation">账号状态：</label>
						<div class="controls">
		                   <input type="text" id="isActivation" name="isActivation" class="input-medium" value="${workTimeSettingsSearchVO.isActivation}"/>
						</div>
					</div>
				</div>
				<div class="form-actions">
				<@shiro.hasPermission name="C1M7F1">
					<button type="button" id="new_btn" class="btn btn-primary">导出</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="C1M7F3">
					<button type="button" class="btn btn-primary" id="importBtn">导入</button>
				</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" name="page" value="${page}">
				<input type="hidden" id="clientStructureId" name="clientStructureId" value="${workTimeSettingsSearchVO.clientStructureId}">
			</form>
		</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered c_list" id="c_list">
					<tr>
						<th width="6%" style="text-align:center;">人员姓名</th>
						<th width="3%" style="text-align:center;">在职状态</th>
						<th width="3%" style="text-align:center;">账号状态</th>
						<th width="8%" style="text-align:center;">人员部门</th>
						<th width="6%" style="text-align:center;">职位</th>
						<th width="13%" style="text-align:center;">工作日期</th>
						<th width="7%" style="text-align:center;">班次</th>
						<th width="6%" style="text-align:center;">开始时间</th>
						<th width="6%" style="text-align:center;">结束时间</th>
						<th width="8%" style="text-align:center;">排班门店</th>
						<th style="text-align:center;">备注</th>
						<th width="10%" style="text-align:center;">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as workTimeSettings>
							<tr>
								<td title="${workTimeSettings.clientUserName!''}:${workTimeSettings.loginName!''}">${workTimeSettings.clientUserName!''}</td>
								<td width="6%"><#if workTimeSettings.clientUserStatus = 1>在职<#elseif workTimeSettings.clientUserStatus = 0>离职</#if></td>
								<td width="6%"><#if workTimeSettings.isActivation = 1>启用<#elseif workTimeSettings.isActivation = 0>禁用<#elseif workTimeSettings.isActivation = 2>无法正常使用APP</#if></td>
								<td>${workTimeSettings.structureName!''}</td>
								<td>${workTimeSettings.clientPositionName!''}</td>
								<td><#if workTimeSettings.workDate??>${workTimeSettings.workDate?string("yyyy-MM-dd EE")}</#if></td>
								<td>${workTimeSettings.workType!''}</td>
								<td><#if workTimeSettings.startTime??>${workTimeSettings.startTime?string("HH:mm")}</#if></td>
								<td><#if workTimeSettings.endTime??>${workTimeSettings.endTime?string("HH:mm")}</#if></td>
								<td title="${workTimeSettings.storeName!''}">
									<#if workTimeSettings.storeName?? && workTimeSettings.storeName?length gt 12>
										${workTimeSettings.storeName?substring(0,12)}...
									<#else>
										${workTimeSettings.storeName!''}
									</#if>
								</td>
								<td title="${workTimeSettings.remark!''}">
									<#if workTimeSettings.remark?? && workTimeSettings.remark?length gt 60>
										${workTimeSettings.remark?substring(0,60)}...
									<#else>
										${workTimeSettings.remark!''}
									</#if>
								</td>
								<td>
									<@shiro.hasPermission name="C1M7F2">
										<#if workTimeSettings.settingId??>
											<a class="editWorkTimeSettings" href="javascript:void(0);" dataId="${workTimeSettings.settingId}">修改排班计划</a>
										</#if>
									</@shiro.hasPermission>
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
	</div>
	<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_structure" class="ztree" style="margin-top:30; width: 160px; margin-left:220px;">
			</ul>
	</div>
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript" language="javascript">
$(document).ready(function () {
		loadIsActivation();
		loadStatus();
	
	$("#workDate1").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#workDate2").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
	
	$("#workDate2").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#workDate1").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
});


var editDialog,importDialog;
$(function(){
	var cData = [
	<#list clientPositionlist as clientPosition>
		<#if clientPosition_has_next>
			{id:${clientPosition.clientPositionId} ,text : '${clientPosition.positionName}'},
		<#else>
			{id:${clientPosition.clientPositionId} ,text : '${clientPosition.positionName}'}
		</#if>
	</#list>
	];
	$("#clientPositionId").select2({
		width:145,
		placeholder: "请选择",
		allowClear: true,
		data: cData
	});


	//导出
	$("#new_btn").bind("click",function(){
		var workDate1 = $("#workDate1").val();
		var workDate2 = $("#workDate2").val();
		layer.confirm("确认导出"+workDate1+"到"+workDate2+"期间的排班计划吗？", function () {
				var url = "${contextPath}/workTimeSettings/exportWTS";
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				$('#searchForm').attr("action","#");
		});
		return false;	
	});	
	
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/workTimeSettings/showImportDialog";
		   layer.open({
			    type: 2,
			    title: '排班导入',
			    closeBtn: 1,
			     area: ['620px', '282px'],
			    content: url
			});
		//importDialog = new xDialog(url, {}, {title:"排班导入",width:660,height:340 });
		//return false;
	});
	
	//编辑排班计划
	$("a.editWorkTimeSettings").bind("click",function(){
		var settingId = $(this).attr("dataId");
		var url = "${contextPath}/workTimeSettings/showEditDialog/"+settingId;
		layer.open({
			    type: 2,
			    title: '编辑排班计划',
			    closeBtn: 1,
			     area: ['550px', '375px'],
			    content: url
			});
		//editDialog = new xDialog(url, {}, {title:"编辑排班计划",width:550,height:275 });
		//return true;
	});
});

function loadIsActivation(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientUser/getIsActivation",
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
			$("#isActivation").select2({
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

function loadStatus(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientUser/getStatus",
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