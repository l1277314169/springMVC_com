<html>
<head>
<#include "/common/head.ftl" />
<title>渠道管理</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
<#assign privCode="C2M11">
<#include "/base.ftl" />
	<div id="content" style="height:100%; ">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基础数据管理</a><a class="linkPage active" href="${contextPath}/channel/query">渠道管理</a>
			</div>
		</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
				<div nowrap="true" class="fl">
						<label class="control-label fl" for="searchName">渠道名称：</label>
						<div class="controls">
							<input type="text" name="searchName" class="input-medium" value="${searchName!''}">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientStructureId">上级渠道：</label>
						<div class="controls">
							<input type="text" id="clientStructureId" name="clientStructureId"  class="input-medium" value="${clientStructureId!''}"  readonly onclick="showMenu();">
						</div>
					</div>
				</div>
				<div class="form-actions">
					<@shiro.hasPermission name="C2M11F1">
					<button type="button" id="addBtn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M11F4">
					<button type="button" id="importBtn" class="btn btn-primary">导入</button>
					</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" id="parentId" name="parentId" >
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
					<th>渠道名称</th>
					<th>渠道英文名称</th>
					<th>渠道层级</th>
					<th>上级渠道</th>
					<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as channel>
							<tr>
								<td>${channel.channelName!''}</td>
								<td>${channel.channelNameEn!''}</td>
								<td>
								<#if channel.grade==1>一级渠道
								 <#elseif  channel.grade==2> 二级渠道
								 							<#else>三级渠道
								</#if>
								</td>
									<td>${channel.parentName!''}</td>
								<td>
									<@shiro.hasPermission name="C2M11F2">
									<a class="editChannel" href="javascript:void(0);" dataId="${channel.channelId!''}" dataName="${channel.channelName!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M11F3">
									<a class="deleteChannel" href="javascript:void(0);" data="${channel.channelId!''}">删除</a>
									</@shiro.hasPermission>
									<a class="checkChannel" href="javascript:void(0);" dataId="${channel.channelId!''}" dataName="${channel.channelName!''}">查看</a>
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
		
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
		</div>
		</div>
<#include "/footer.ftl" />
<body>
<script type="text/javascript">
var editDialog, addDialo,checkDialog;
var importDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/channel/showImportDialog";
	 	layer.open({type: 2,title: '渠道导入', closeBtn: 1,area: ['650px', '350px'],content: url});
	});
	//新增
	$("#addBtn").bind("click",function(){
		var url = "${contextPath}/channel/showAddChannel";
		layer.open({type: 2,title: '新增渠道',closeBtn: 1,area: ['450px', '330px'],content: url});
	});
		//编辑
	$("a.editChannel").bind("click",function(){
	    var channelId=$(this).attr("dataId");
	    var channelName=$(this).attr("dataName");
		var url = "${contextPath}/channel/showEditChannel/"+channelId;
		layer.open({type: 2,title: '编辑渠道---'+channelName,closeBtn: 1,area: ['450px', '330px'],content: url});
	});
	//查看
		$("a.checkChannel").bind("click",function(){
	    var channelId=$(this).attr("dataId");
	    var channelName=$(this).attr("dataName");
		var url = "${contextPath}/channel/showCheckChannel/"+channelId;
		layer.open({type: 2,title: "查看渠道---"+channelName,closeBtn: 1,area: ['450px', '330px'],content: url});
	});
	
		//删除
	$("a.deleteChannel").bind("click",function(){
		var channelId=$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/channel/deleteChannel/"+channelId,
				type : "get",
				async: false,
				data : {channelId : channelId},
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


var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
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
		$("#clientStructureId").attr("value", treeNode.name);
		$("#parentId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
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
    
$(function(){
  $("#clientStructureId").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
	$("#clientStructureId").val(""); 
	$("#searchClientStructureId").val(""); 
	}; 
});
});


</script>