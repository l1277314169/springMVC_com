<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>人员管理</title>
</head>
<body  class="main_body">
		<#assign privCode="C2M3">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础数据管理</a>
					<a class="linkPage active" href="${contextPath}/clientUser/query?mod=conf">人员管理</a>
				</div>
		 	</div>
	
		<div class="widget-content nopadding">
		<input type="hidden" id="count"  value="${count}">
		<input type="hidden" id="childAll" value="${childAll}">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<#list queryList as query>
						<#assign view="${query.attributeName}"/>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="${query.attributeName}">${query.columnDesc}：</label>
							<div class="controls">
								<#if query.controlType = 7>
									<input type="checkbox" class="input-medium" id="${query.attributeName}" name="${query.attributeName}" value="">
								<#else>
									<input type="text" class="input-medium" autocomplete="off" id="${query.attributeName}" name="${query.attributeName}" value="${Request['${query.attributeName}']}" <#if query.editable = 0>readonly="readonly"</#if>>
								</#if>
							</div>
						</div>
					</#list>
				</div>
				
				<div class="form-actions">
					<@shiro.hasPermission name="C2M3F1">
					<button type="button" id="addBtn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M3F4">
					<button type="button" id="importBtn" class="btn btn-primary">导入</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M3F6">
					<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M3F7">
					<button type="button" id="btnParent" class="btn btn-danger">批量替换上级</button>
					</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" id="mod" name="mod" value="${mod}">
				<input type="hidden" id="clientStructureId" name="clientStructureId" value="${clientStructureId}">
				<input type="hidden" id="isLower" name="isLower" value="${isLower}">
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
					<#list viewList as vl>
						<th>${vl.columnDesc}</th>
					</#list>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientUser>
							<tr>
								<#list viewList as vl>
									<td>
										<#if vl.attributeName = 'status'>
											<#if clientUser.status = 1>在职<#elseif clientUser.status = 0>离职</#if>
										<#elseif vl.attributeName = 'isActivation'>
											<#if clientUser.isActivation = 1>启用<#elseif clientUser.isActivation = 0>禁用<#elseif clientUser.isActivation = 2>无法正常使用APP</#if>
										<#else>
											${clientUser['${vl.attributeName}']!''}
										</#if>
									</td>
								</#list>
								<td>
									<@shiro.hasPermission name="C2M3F2">
									<a class="editClientUser" href="javascript:void(0);" dataId="${clientUser.clientUserId!''}" dataName="${clientUser.name!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M3F3">
									<a class="deleteClientUser" href="javascript:void(0);" data="${clientUser.clientUserId!''}">删除</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M3F5">
									<a class="resetPasswd" href="javascript:void(0);" data="${clientUser.clientUserId!''}">重置密码</a>
									</@shiro.hasPermission>									
									<@shiro.hasPermission name="C2M3F8">
										<a class="batchChoiceStore" href="javascript:void(0);" structureId="${clientUser.clientStructureId!''}"  dataId="${clientUser.clientUserId!''}"  dataName="${clientUser.name!''}">关联门店</a>
									</@shiro.hasPermission>
									<a class="check" href="javascript:void(0);" dataId="${clientUser.clientUserId!''}"  dataName="${clientUser.name!''}">查看</a>
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
				<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;"></ul>
			</div>
			
		</div>
		
		<#include "/footer.ftl" />
		
	
</body>
</html>

<script type="text/javascript">
var importDialog,addDialog,editDialog,checkDialog,exportBtn,batchClientUserDialog,batchChoiceStoreDialog;
$(function(){
	var positionId = $("#clientPositionId").val();
	var isActivation = $("#isActivation").val();
	var status = $("#status").val();
	
	loadPosition(positionId);
	loadIsActivation(isActivation);
	loadStatus(status);
	
	var isLower = $("#isLower").val();
	$("#theLower").val(isLower);
	//alert(isLower);
	if(isLower == 1){
		$(theLower).attr("checked","checked");
	}
	
	$("#theLower").bind("click",function(){
		if($(this).attr("checked")) {
			$("#isLower").val("1");
		}else {
			$("#isLower").val("0");
		}
	});
	
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/clientUser/showImportDialog";
	//	importDialog = new xDialog(url, {}, {title:"人员导入",width:650,height:330 });
	//	return false;
		 layer.open({
			    type: 2,
			    title: '人员导入',
			    closeBtn: 1,
			    area: ['650px', '330px'],
			    content: url
			});
	});
	
	//导出
	$("#exportBtn").bind("click",function(){
		 layer.confirm("确认导出", function () {
				var url ="${contextPath}/clientUser/showExportDialog";
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				$('#searchForm').attr("action","${contextPath}/clientUser/query");
				layer.closeAll();
		});
		return false;
	});
	//新增
	$("#addBtn").bind("click",function(){
		var url = "${contextPath}/clientUser/showAddClientUser?mod=conf";
	//	addDialog = new xDialog(url, {"mod":"conf"}, {title:"新增人员",width:750,height:570 });
	//	return false;	
		 layer.open({
			    type: 2,
			    title: '新增人员',
			    closeBtn: 1,
			    area: ['750px', '650px'],
			    content: url
			});
	});
		//编辑
	$("a.editClientUser").bind("click",function(){
	    var clientUserId=$(this).attr("dataId");
	    var clientUserName=$(this).attr("dataName");
		var url = "${contextPath}/clientUser/showEditClientUser/"+clientUserId+"?mod=conf";
	//	editDialog = new xDialog(url,{"mod":"conf"},{title:"编辑人员---"+clientUserName,width:750,height:570 }); 
	//	return false;
			layer.open({
			    type: 2,
			    title: "编辑人员---"+clientUserName,
			    closeBtn: 1,
			    area: ['750px', '650px'],
			    content: url
			});
	});
	
	//业务员维护
	$("a.batchChoiceStore").bind("click",function(){
		var clientStructureId = $(this).attr("structureId");
		var clientUserId = $(this).attr("dataId");
		var url = "${contextPath}/store/showBatchChoiceStore?clientStructureId="+clientStructureId+"&clientUserId="+clientUserId;
		//batchChoiceStoreDialog = new xDialog(url, {}, {title:"批量选择门店",iframe:true,width:800,height:640});
		  layer.open({
			    type: 2,
			    title: "批量选择门店",
			    closeBtn: 1,
			    area: ['820px', '680px'],
			    content: url
			});
	});
	
	//查看
	$("a.check").bind("click",function(){
	    var clientUserId=$(this).attr("dataId");
	    var clientUserName=$(this).attr("dataName");
		var url = "${contextPath}/clientUser/showClientUserDtail/"+clientUserId+"?mod=conf";
		//checkDialog = new xDialog(url,{"mod":"conf"},{title:"查看人员---"+clientUserName,width:750,height:570 }); 
		//return false;	
		  layer.open({
			    type: 2,
			    title:"查看人员---"+clientUserName,
			    closeBtn: 1,
			    area: ['750px', '630px'],
			    content: url
			});
	});
	//替换上级人员
	$('#btnParent').bind("click",function(){
		var url = "${contextPath}/clientUser/showBatchClientUser";
		//batchClientUserDialog = new xDialog(url, {}, {title:"批量替换上级",iframe:true,width:800,height:640});
		  layer.open({
			    type: 2,
			    title: "批量替换上级",
			    closeBtn: 1,
			    area: ['820px', '680px'],
			    content: url
			});
	});
		//删除
	$("a.deleteClientUser").bind("click",function(){
		var clientUserId=$(this).attr("data");
	    layer.confirm("亲，你要解除门店关系和下级人员关系。确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/clientUser/deleteClinetUser/"+clientUserId,
				type : "get",
				async: false,
				data : {clientUserId : clientUserId},
				dataType:'JSON',
				success : function(result) {
					 confirmAndRefresh(result); 
				}
			});
	   });
		   return false;
	});
	
	//树-input选项,判断用户的按键行为,如果为"Backspace"或者"delete"按键，则清空选择项。
	$("#structureName").keydown(function(e){
		if(e.keyCode == 8 || e.keyCode == 46){
			$("#structureName").val("");
			$("#clientStructureId").val("");
		};
	});
	
	$("a.resetPasswd").bind("click",function(){
		var clientUserId=$(this).attr("data");
	    layer.confirm("是否确认重置密码？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/clientUser/resetPasswd/"+clientUserId,
				type : "get",
				async: false,
				data : {clientUserId : clientUserId},
				dataType:'JSON',
				success : function(result) {
					 alert("重置成功，重置密码为：8888");
						 confirmAndRefresh(result); 
				}
			});
	   });
		   return false;
	});
	function confirmAndRefresh(result){
		if (result.code == "success") {
			window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
	
});

//上级
$("#parentId").select2({
	width:145,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	allowClear : true,
	ajax: {
		url: "${contextPath}/clientUser/getClientUserWithoutSelf",
		dataType: 'json',
		quietMillis: 250,
		data: function (term, page) {
			return {
				q: term,
				page: page
			};
		},
		results: function (data,page) {
			var more = page;
			return { results: data,more: more };
		}
	},
	initSelection: function(element, callback) {
			var id = $(element).val();
			if (id != "") {
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
	
function loadStatus(status){
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

function loadIsActivation(isActivation){
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


function loadPosition(positionId){
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
			alert("数据加载失败！");
		}
	});
};

var setting = {
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
		$("#structureName").attr("value", treeNode.name);
		$("#clientStructureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#clientStructureId").val(), null);
		//alert(node.id);
		if(node != null){
			$("#structureName").val(node.name);
		}
	};
    function showMenu() {
        var structureObj = $("#structureName");
        var clientStructureOffset = $("#structureName").position();
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
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo"), setting);
        $("#structureName").on("click",function(){
			showMenu();
		});
    });
	
	//角色
	$("#rolesId").select2({
		width:145,
		placeholder: "输入字符查询",
		minimumInputLength: 2,
		allowClear : true,
		ajax: {
			url: "${contextPath}/clientRoles/getRolesByselectTwo",
			dataType: 'json',
			quietMillis: 250,
			data: function (term, page) {
				return {
					q: term,
					page: page
				};
			},
			results: function (data,page) {
				var more = page;
				return { results: data,more: more };
			}
		},
		initSelection: function(element, callback) {
			var id = $(element).val();
			if (id != "") {
				$.ajax("${contextPath}/clientRoles/getRolesById/"+id, {
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
</script>