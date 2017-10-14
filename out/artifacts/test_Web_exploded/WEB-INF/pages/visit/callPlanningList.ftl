<html>
<head>
<title>执行计划管理</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript">
	function toggle(targetid) {
		if (document.getElementById) {
			target = document.getElementById(targetid);
			if (target.style.display == "block") {

				target.style.display = "none";
			} else {
				target.style.display = "block";
			}
		}
	}
	
	function changeProvince(cityId, districtId) {
		$("#city").empty();
		$("#district").empty();
		var value = $("#province").val();
		var parms = {
			"provinceId" : value,
		};
		$.ajax({
			type : "POST",
			url : "${ctx}/city/list",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理 
				$.each(jsonData, function(index, item) {
					$("#city").append(
							"<option value='"+item.cityId+"'>" + item.city
									+ "</option>");
				});
				if (cityId != null) {
					$("#city").val(cityId);
				}
				changeCity(districtId);
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});

	}
	function changeCity(districtId) {
		$("#district").empty();
		var value = $("#city").val();
		var parms = {
			"cityId" : value,
		};
		$.ajax({
			type : "POST",
			url : "${ctx}/district/list",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理 
				$.each(jsonData, function(index, item) {
					$("#district").append(
							"<option value='"+item.districtId+"'>"
									+ item.district + "</option>");
				});
				if (districtId != null) {
					$("#district").val(districtId);
				}
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
	}
	
	function save() {
		var storeNo = $("#storeNo").val();
		var storeName = $("#storeName").val();
		var channel = $("#channel").val();
		var chain = $("#chain").val();
		var province = $("#province").val();
		var city = $("#city").val();
		var district = $("#district").val();
		var addr = $("#addr").val();
		var contact = $("#contact").val();
		var telephoneNo = $("#telephoneNo").val();
		var mobileNo = $("#mobileNo").val();
		var fax = $("#fax").val();
		var remark = $("#remark").val();
		var clientStructure = $("#clientStructure").val();
		var parms;
		var storeId = $("#storeId").val();
		if (storeId == "") {
			parms = {
				"storeNo" : storeNo,
				"storeName" : storeName,
				"province.provinceId" : province,
				"city.cityId" : city,
				"clientStructure.clientStructureId" : clientStructure,
				"telephoneNo" : telephoneNo,
				"mobileNo" : mobileNo,
				"province.provinceId" : province,
				"city.cityId" : city,
				"district.districtId" : district,
				"addr" : addr,
				"contact" : contact,
				"fax" : fax,
				"remark" : remark,
				"channel.id" : channel,
				"chain.id": chain
			};
		} else {
			parms = {
				"storeNo" : storeNo,
				"storeName" : storeName,
				"province.provinceId" : province,
				"city.cityId" : city,
				"clientStructure.clientStructureId" : clientStructure,
				"telephoneNo" : telephoneNo,
				"mobileNo" : mobileNo,
				"province.provinceId" : province,
				"city.cityId" : city,
				"district.districtId" : district,
				"addr" : addr,
				"contact" : contact,
				"fax" : fax,
				"remark" : remark,
				"channel.id" : channel,
				"chain.id": chain,
				"storeId" : storeId
				
			};
		}

		$.ajax({
			type : "POST",
			url : "${ctx}/store/updateStore",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				if (data == "1") {
					layer.alert("添加成功!");
					window.location.reload();
				} else {
					layer.alert("添加失败!");
				}
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
	}
	
	function emptyStoreForm() {
		$("#storeId").val("");
		$("#storeNo").val("");
		$("#storeName").val("");
		$("#mobileNo").val("");
		$("#telephoneNo").val("");
		$("#addr").val("");
		$("#fax").val("");
		$("#contact ").val("");
		$("#remark").val("");
		$("#province option:first").prop("selected", 'selected');
		$("#clientStructure option:first").prop("selected", 'selected');
		$("#channel option:first").prop("selected", 'selected');
		$("#chain option:first").prop("selected", 'selected');
		
	}

	function editStore(id) {
		emptyStoreForm();
		$("#showtitle").html("修改门店");
		$("#storeId").val(id);

		var parms = {
			"storeId" : id,
		};
		$.ajax({
			type : "POST",
			url : "${ctx}/store/getStoreInfo",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理 
				toggle('openwindow');
				$("#storeNo").val(jsonData.storeNo);
				$("#storeName").val(jsonData.storeName);
				
				$("#clientStructure").val(
						jsonData.clientStructure.clientStructureId);
				$("#mobileNo").val(jsonData.mobileNo);
				$("#fax").val(jsonData.fax);
				$("#telephoneNo").val(jsonData.telephoneNo);
				$("#province").val(jsonData.province.provinceId);
				var cityId = jsonData.city.cityId;
				var districtId = jsonData.district.districtId;
				changeProvince(cityId, districtId);

				$("#addr").val(jsonData.addr);
				$("#contact").val(jsonData.contact);
				$("#remark").val(jsonData.remark);
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
	}
</script>
</head>
<body  class="main_body">
	     <#assign privCode="C1M1">
		 <#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/callPlanning/query">执行计划管理</a>
				</div>
		 	</div>
	 
	 
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
							<label class="control-label " for="clientStructureName">部门：</label>
							<div class="controls">
								<input type="text" id="clientStructureId" name="clientStructureName" value=" " class="input-medium" readonly   onclick="showMenu()">
								<input type="text" id="structureId" name="structureId" value="${clientStructureId}" style="display:none;">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="clientUserName">姓名：</label>
							<div class="controls">
								<input type="text" name="clientUserName" class="input-medium" value="${clientUserName}">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="startDate">开始时间：</label>
							<div class="controls">
								<input type="text" id="startDate" name="startDate" value="${startDate}" class="input-medium" readonly="true">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="endDate">结束时间：</label>
							<div class="controls">
								<input type="text" id="endDate" name="endDate" value="${endDate}" class="input-medium" readonly="true">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="visitType">计划类型：</label>
							<div class="controls">
								<input type="text" id="visitType" name="visitType" class="input-medium" value="${visitType}"/>
							</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientPositionId">职位：</label>
						<div class="controls">
							<input  id="clientPositionId" class="input-medium" name="clientPositionId"  value="${clientPositionId!''}">
						</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="visitContent">任务内容：</label>
							<div class="controls">
							  <input id="enumValue" name="enumValue" class="input-medium" value="${enumValue}">
							</div>
					</div>
				</div>
				<div class="form-actions">
				<@shiro.hasPermission name="C1M1F1">
					<button type="button" id="new_button" class="btn btn-primary">新增拜访计划</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="C1M1F5">
					<button type="button" id="old_button" class="btn btn-success">新增非拜访计划</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="C1M1F4">
					<button type="button" id="importBtn" class="btn btn-primary">导入</button>
				</@shiro.hasPermission>
				 <@shiro.hasPermission name="C1M1F6">
					<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
				</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
			</form>
		</div>
		<input type="hidden" id="today"  value="${today!''}"/>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>姓名</th>
						<th>职位</th>
						<th>部门</th>
						<th>日期</th>
						<th>计划类型</th>
						<th width="100px">计划拜访门店数</th>
						<th width="100px" >任务内容</th>
						<th width="200px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as callPlanning>
							<tr>
								<td>${callPlanning.name!''}</td>
								<td>${callPlanning.positionName!''}</td>
								<td>${callPlanning.structureName!''}</td>
								<td><#if (callPlanning.callDate)??>${(callPlanning.callDate)?string("yyyy-MM-dd EE")}</#if></td>
								<td>
									<#if callPlanning.visitType == 1>
										门店拜访
									</#if>
									<#if callPlanning.visitType == 2>
										门店协访
									</#if>
									<#if callPlanning.visitType == 3>
										店内工作
									</#if>
								</td>
								<td>
									<#if (callPlanning.callDate)?date gt sysDate?date>
										${callPlanning.isNotDeleteStore}
									<#else>
										${callPlanning.planedTimes}
									</#if>
								</td>
								<td>
									<#if callPlanning.workType == 0>
									走店
									<#else>
										${callPlanning.optionText}
									</#if>
								</td>
								<td>
									<@shiro.hasPermission name="C1M1F2">
										<a class="editCallPlanning" href="javascript:void(0);" style=${callPlanning.showEdit!''} id="${callPlanning.planningId}" workType="${callPlanning.workType!''}" dataName="${callPlanning.name!''}" dataVisitType="${callPlanning.visitType!''}" dataId="${callPlanning.clientUserId}" dataDate="<#if (callPlanning.callDate)??>${(callPlanning.callDate)?string("yyyy-MM-dd")}</#if>">编辑</a>
									</@shiro.hasPermission>
									<a class="moreDetail" href="javascript:void(0);"   workType="${callPlanning.workType!''}" id="${callPlanning.planningId}" dataVisitType="${callPlanning.visitType!''}"  dataName="${callPlanning.name!''}" dataId="${callPlanning.clientUserId}" dataDate="<#if (callPlanning.callDate)??>${(callPlanning.callDate)?string("yyyy-MM-dd")}</#if>">查看</a>
									<@shiro.hasPermission name="C1M1F3">
										<a class="deleteCallPlanning" href="javascript:void(0);" style=${callPlanning.showEdit!''} dataId="${(callPlanning.callDate)?string("yyyy-MM-dd")}" dataVisitType="${callPlanning.visitType!''}" dataclientUserId="${callPlanning.clientUserId}">删除</a>
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
		
			<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
			</div>
			
			
	</div>
			<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript" language="javascript">
$(document).ready(function () {
	//加载职位
	loadPosition();
	
	$(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting);
        loadtTaskContents();
    });
    
	var visitType = [{ id: 1, text: '门店拜访' }, { id: 2, text: '门店协访' },{id:3,text:'店内工作'}];
	  $("#visitType").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: visitType
        });
	    $("#enumValue").select2({
        	width:145,
			placeholder: "请选择",
			data:[]
		});
		
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#endDate").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
	
	$("#endDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#startDate").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
});
var importDialog,editDialog,detailDialog,addDialog,odlAddDialog;
$(function(){
	//编辑
	$("a.editCallPlanning").bind("click",function(){
		$('#storeParameterDatas').val('');
		var clientUserName = $(this).attr("dataName");
		var clientUserId =$(this).attr("dataId");
		var callDate = $(this).attr("dataDate");
		var workType = $(this).attr("workType");
		var visitType = $(this).attr("dataVisitType");
		var planningId = $(this).attr("id");
		var url = "${contextPath}/callPlanning/showVisitCallPlanning/"+clientUserId+"/"+callDate+"/"+workType+"/"+visitType;
		if(workType == 0){
		// editDialog = new xDialog(url,{},{title:clientUserName+"--"+callDate+"--拜访门店",iframe:true,width:800,height:650});
		 layer.open({
			    type: 2,
			    title: clientUserName+'--'+callDate+'--计划拜访门店',
			    closeBtn: 1,
			    iframe:true,
			    area: ['800px', '650px'],
			    content: url
			});
		}else{
			url = "${contextPath}/callPlanning/showEditVisitCallPlanning/"+planningId;
		// editDialog = new xDialog(url,{},{title:clientUserName+"--"+callDate+"--非拜访计划",width:450,height:290});
		 layer.open({
			    type: 2,
			    title: clientUserName+'--'+callDate+'--非拜访计划',
			    closeBtn: 1,
			    iframe:true,
			    area: ['450px', '290px'],
			    content: url
			});
		}
		
	});
	//查看
	$("a.moreDetail").bind("click",function(){
		var clientUserName = $(this).attr("dataName");
		var clientUserId =$(this).attr("dataId");
		var callDate = $(this).attr("dataDate");
		var workType = $(this).attr("workType");
		var visitType = $(this).attr("dataVisitType");
		var planningId = $(this).attr("id");
		var url = "${contextPath}/callPlanning/moreDetail/"+clientUserId+"/"+callDate+"/"+visitType+"/"+workType;
		if(workType == 0){
			// detailDialog = new xDialog(url,{},{title:clientUserName+"--"+callDate+"--计划拜访门店",iframe:true,width:700,height:650});
			 layer.open({
			    type: 2,
			    title: clientUserName+'--'+callDate+'--计划拜访门店',
			    closeBtn: 1,
			    iframe:true,
			    area: ['700px', '650px'],
			    content: url
			});
		}else{
			url = "${contextPath}/callPlanning/showLookVisitCallPlanning/"+planningId;
			// editDialog = new xDialog(url,{},{title:clientUserName+"--"+callDate+"--非拜访计划查看",width:300,height:290});
				layer.open({
			    type: 2,
			    title:clientUserName+'--'+callDate+'--非拜访计划查看',
			    closeBtn: 1,
			    iframe:true,
			    area: ['330px', '290px'],
			    content: url
			});
		}
		
	});
	//导入
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/callPlanning/showImportDialog";
		// importDialog = new xDialog(url, {}, {title:"计划导入",width:650,height:330 });
		  layer.open({
			    type: 2,
			    title: '计划导入',
			    closeBtn: 1,
			    area: ['650px', '330px'],
			    content: url
			});
		return true;
	});
	//导出
	$("#exportBtn").bind("click",function(){
			layer.confirm("确认导出", function () {
				var url ="${contextPath}/callPlanning/showExportDialog";
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				$('#searchForm').attr("action","${contextPath}/callPlanning/query");
				layer.closeAll();
		});
		return false;
	});
	
	//新增拜访计划
	$("#new_button").bind("click",function(){
		$('#storeParameterDatas').val('');
		var url = "${contextPath}/callPlanning/showAddCallPlanning";
		// addDialog = new xDialog(url, {}, {title:"新增拜访计划",iframe:true,width:800,height:650});
		 layer.open({
			    type: 2,
			    title: '新增拜访计划',
			    closeBtn: 1,
			    iframe:true,
			    area: ['800px', '650px'],
			    content: url
			});
	});
	//新增非拜访计划
	$("#old_button").bind("click",function(){
		var url = "${contextPath}/callPlanning/showAddOldCallPlanning";
		// odlAddDialog = new xDialog(url, {}, {title:"新增非拜访计划",width:450,height:290});
		layer.open({
			    type: 2,
			    title: '新增非拜访计划',
			    closeBtn: 1,
			    iframe:true,
			    area: ['450px', '320px'],
			    content: url
			});
	});
	
	//删除
	$("a.deleteCallPlanning").bind("click",function(){
		var callDate = $(this).attr("dataId");
		var visitType = $(this).attr("dataVisitType");
		var clientUserId =$(this).attr("dataclientUserId");
		layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/callPlanning/deleteCallPlanning",
				type : "post",
				async: false,
				dataType:'JSON',
				data : {callDate : callDate,clientUserId:clientUserId,visitType:visitType},
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
});
	
	

var setting = {
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
        
    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#clientStructureId").attr("value", treeNode.name);
		$("#structureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#structureId").val(), null);
		if(node != null){
			$("#clientStructureId").val(node.name);
		}
	};
    function showMenu() {
        var structureObj = $("#clientStructureId");
        var clientStructureOffset = $("#clientStructureId").position();
        $("#menuContent").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
        
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    
//树-input选项,判断用户的按键行为,如果为"Backspace"或者"delete"按键，则清空选择项。
	$("#clientStructureId").keydown(function(e){
		if(e.keyCode == 8 || e.keyCode == 46){
			$("#clientStructureId").val("");
			$("#structureId").val("");
		};
	});

	function loadtTaskContents(){
	$.ajax({
		type : "post",
		url : "${contextPath}/callPlanning/getTaskContentsAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			thisData += "{\"id\":\""+0+"\",\"text\":\"走店\"},";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData); 
			$("#enumValue").select2({
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

function loadPosition(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientPosition/getClientPositionAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#clientPositionId").select2({
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