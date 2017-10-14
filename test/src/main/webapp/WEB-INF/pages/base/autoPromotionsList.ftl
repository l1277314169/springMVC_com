<!--促销员管理-->
<html>
<head>
<title>促销员管理</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
 <style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
    .allH{
		height:100%;
	}
</style>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
</head>
<body  class="main_body">
			<#assign privCode="C2M13">
			<#include "/base.ftl" />
				<div id="content">
					<div id="content-header">
						<div id="breadcrumb"> 
							<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
							<a href="#">基础数据管理</a>
							<a class="linkPage active" href="${contextPath}//promotions/query?mod=conf">促销员管理</a>
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
					<@shiro.hasPermission name="C2M13F1">
					<button type="button" id="addBtn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M13F8">
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M13F7">
						<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M13F6">
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
									<@shiro.hasPermission name="C2M13F2">
									<a class="editClientUser" href="javascript:void(0);" dataId="${clientUser.clientUserId!''}" dataName="${clientUser.name!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M13F3">
									<a class="deleteClientUser" href="javascript:void(0);" data="${clientUser.clientUserId!''}">删除</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M13F4">
									<a class="resetPasswd" href="javascript:void(0);" data="${clientUser.clientUserId!''}">重置密码</a>
									</@shiro.hasPermission>									
									<@shiro.hasPermission name="C2M13F5">
										<a class="batchChoiceStore" href="javascript:void(0);" structureId="${clientUser.clientStructureId!''}"  dataId="${clientUser.clientUserId!''}"  dataName="${clientUser.name!''}">关联门店</a>
									</@shiro.hasPermission>
									<a class="check" href="javascript:void(0);" dataId="${clientUser.clientUserId!''}"  dataName="${clientUser.name!''}">查看</a>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
					<#if pageParam.items?exists>
							<div class="paging">
							   ${pageParam.getPagination()}
							</div>
					</#if>
		
		<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
	</div>
		</div>
		</div>
<#include "/footer.ftl" />
</body>
</html>

<script type="text/javascript">
var importDialog,addDialog,editDialog,checkDialog,exportBtn,batchClientUserDialog,batchChoiceStoreDialog;
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
	$("#select2-chosen-2").html('${clientRole.roleName}');	
	
	$("#rolesId").val(${clientRole.roleId});
	$("#rolesId").attr("readonly","readonly")
	var isActivation = $("#isActivation").val();
	var status = $("#status").val();
	
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
		var url = "${contextPath}/promotions/showImportDialog";
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
				var url ="${contextPath}/promotions/showExportDialog";
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				//$('#searchForm').attr("action","${contextPath}/clientUser/query");
				 parent.layer.closeAll('iframe');
		});
		return false;
	});
	//新增
	$("#addBtn").bind("click",function(){
		var url = "${contextPath}/promotions/showAddClientUser?mod=conf";
		//addDialog = new xDialog(url, {"mod":"conf"}, {title:"新增人员",width:750,height:570 });
		//return false;
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
		var url = "${contextPath}/promotions/showEditClientUser/"+clientUserId+"?mod=conf";
		//editDialog = new xDialog(url,{"mod":"conf"},{title:"编辑人员---"+clientUserName,width:750,height:570 }); 
		//return false;	
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
	//	batchChoiceStoreDialog = new xDialog(url, {}, {title:"批量选择门店",iframe:true,width:800,height:640});
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
		var url = "${contextPath}/promotions/showClientUserDtail/"+clientUserId+"?mod=conf";
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
		var url = "${contextPath}/promotions/showBatchClientUser";
	//	batchClientUserDialog = new xDialog(url, {}, {title:"批量替换上级",iframe:true,width:800,height:640});
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
				url : "${contextPath}/promotions/deleteClinetUser/"+clientUserId,
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
				url : "${contextPath}/promotions/resetPasswd/"+clientUserId,
				type : "get",
				async: false,
				data : {clientUserId : clientUserId},
				dataType:'JSON',
				success : function(result) {
						layer.alert("重置成功，重置密码为：8888",function(){
				   			 parent.document.location.href = "${contextPath}/promotions/query?mod=conf";
                   			 parent.layer.closeAll('iframe');
			   			});
				}
			});
	   });
		   return false;
	});
	function confirmAndRefresh(result){
		if (result.code == "success") {
			window.location.reload();
		}else {
			layer.alert(result.message);
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
		url: "${contextPath}/promotions/getClientUserWithoutSelf",
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
				$.ajax("${contextPath}/promotions/getClientUser/"+id, {
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
		url : "${contextPath}/promotions/getStatus",
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
		url : "${contextPath}/promotions/getIsActivation",
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