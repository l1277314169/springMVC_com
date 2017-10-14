<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>经销商管理</title>
</head>
<body class="main_body">
	<#assign privCode="C2M6">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基本数据管理</a>
				<a class="linkPage active" href="${contextPath}/survey/query">经销商管理</a>
			</div>
	 	</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="distributorNo">经销商编号： </label>
						<div class="controls">
							<input type="text" name="distributorNo" class="input-medium" value="${distributorNo!''}">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="distributorName">经销商名称：</label>
						<div class="controls">
							<input type="text" name="distributorName" class="input-medium" value="${distributorName!''}">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="structureSel">所属部门：</label>
						<div class="controls">
							<input id="clientStructureId" name="structureSel" type="text" readonly value="${structureSel}" class="input-medium" onclick="showMenu();"/>
							<input type="text" id="structureId" name="structureId" value="${searchClientStructureId}" style="display:none;">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="status">经销商状态：</label>
						<div class="controls">
							<select name="status">
								<option value="">--请选择--</option>
								<option value="1" <#if status == 1> selected=true </#if>>有效</option>
								<option value="0" <#if status == 0> selected=true </#if>>无效</option>
							</select>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<@shiro.hasPermission name="C2M6F1">
					<button type="button" id="addBtn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M6F4">
					<button type="button" id="importBtn" class="btn btn-primary">导入</button>
					</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" id="searchClientStructureId" name="clientStructureId" value="">
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
				    <th width="15%">分管部门</th>
					<th width="10%">经销商编号</th>
					<th>名称</th>
					<th>经销商类型</th>
					<th width="15%">地址</th>
					<th width="10%">联系人</th>
					<th>电话</th>
					<th>经销商状态</th>
					<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as distributor>
							<tr>
								<td>${distributor.structureName!''}</td>
								<td>${distributor.distributorNo!''}</td>
								<td>${distributor.distributorName!''}</td>
								<td>${distributor.optionText}</td>
								<td title="${distributor.addr!''}">
									<#if distributor.addr?? && distributor.addr?length gt 5>
										${distributor.addr?substring(0,5)!}...
									<#else>
										${distributor.addr!''}
									</#if>
								</td>
								<td>${distributor.contact!''}</td>
								<td>${distributor.telephoneNo!''}</td>
								
								<td><#if distributor.status == 1>有效<#else>无效</#if></td>
								<td>
									<@shiro.hasPermission name="C2M6F2">
									<a class="editDistributor" href="javascript:void(0);" dataId="${distributor.distributorId!''}" dataName="${distributor.distributorName!''}">编辑</a>
									</@shiro.hasPermission>
										<@shiro.hasPermission name="C2M6F3">
									<a class="deleteDistributor" href="javascript:void(0);" data="${distributor.distributorId!''}">删除</a>
									</@shiro.hasPermission>
									<a class="checkDistributor" href="javascript:void(0);" dataId="${distributor.distributorId!''}" dataName="${distributor.distributorName!''}">查看</a>
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

<script type="text/javascript">
var importDialog,addDialog,editDialog,checkDialog
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/distributor/showImportDialog";
		// importDialog = new xDialog(url, {}, {title:"经销商导入",width:650,height:330 });
		 layer.open({
			    type: 2,
			    title: '经销商导入',
			    closeBtn: 1,
			    area: ['650px', '330px'],
			    content: url
			});
		return true;
	});
	//新增
	$("#addBtn").bind("click",function(){
		var url = "${contextPath}/distributor/showAddDistributor";
		// addDialog = new xDialog(url, {}, {title:"新增经销商",width:650,height:545 });
		 layer.open({
			    type: 2,
			    title: '新增经销商',
			    closeBtn: 1,
			    area: ['650px', '545px'],
			    content: url
			});
		return false;	
	});
		//编辑
	$("a.editDistributor").bind("click",function(){
	    var distributorId=$(this).attr("dataId");
	    var distributorName=$(this).attr("dataName");
		var url = "${contextPath}/distributor/showEditDistributor/"+distributorId;
		// editDialog = new xDialog(url,{},{title:"编辑经销商---"+distributorName,width:650,height:545});
		  layer.open({
			    type: 2,
			    title: '编辑经销商---'+distributorName,
			    closeBtn: 1,
			    area: ['650px', '545px'],
			    content: url
			});
		return false;	
	});
	//查看
	$("a.checkDistributor").bind("click",function(){
	    var distributorId=$(this).attr("dataId");
	    var distributorName=$(this).attr("dataName");
		var url = "${contextPath}/distributor/showCheckDistributor/"+distributorId;
		// checkDialog = new xDialog(url,{},{title:"查看经销商---"+distributorName,width:530,height:'auto'});
		layer.open({
			    type: 2,
			    title: '查看经销商---'+distributorName,
			    closeBtn: 1,
			    area: ['570px', '420px'],
			    content: url
			});
		return false;	
	});
		//删除
	$("a.deleteDistributor").bind("click",function(){
		var distributorId=$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/distributor/deleteDistributor/"+distributorId,
				type : "get",
				async: false,
				data : {distributorId : distributorId},
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
				window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
});

$("#clientStructureId").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
	$("#clientStructureId").val(""); 
	$("#searchClientStructureId").val(""); 
	}; 
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
        //var check = (treeNode && !treeNode.isParent);
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
		$("#searchClientStructureId").attr("value", treeNode.id);
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
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo"), setting);
        
    });

</script>