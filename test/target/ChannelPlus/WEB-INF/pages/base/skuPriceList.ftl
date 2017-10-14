<html>
<head>
<#include "/common/head.ftl" />
<title>价格管理</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
<#assign privCode="C2M8">
<#include "/base.ftl" />
	<div  id="content" >
		<div id="content-header">
		<div id="breadcrumb"> 
			<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
			<a href="#">基础数据管理</a>
			<a class="linkPage active" href="${contextPath}/skuPrice/query">价格管理</a>
		</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="channelId">
							渠道：
						</label>
						<div class="controls">
							<input id="channel" type="text" name="channelName" class="input-medium"
							readonly="readonly" value="${channelName}" onclick="showMenu(this.id);"
							/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="chainId">
							连锁：
						</label>
						<div class="controls">
							<input type="text" id="chain" name="chainName" class="input-medium" readonly="readonly"
							value="${chainName}" onclick="showMenu(this.id);">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="distributorId">
							经销商：
						</label>
						<div class="controls">
							<input id="distributor" type="text" name="distributorName" class="input-medium"
							readonly="readonly" value="${distributorName}" onclick="showMenu(this.id);"
							/>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<@shiro.hasPermission name="C2M8F1">
						<button type="button" id="addBtn" class="btn btn-success">
							新增
						</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M8F4">
						<button type="button" id="importBtn" class="btn btn-primary">
							导入
						</button>
					</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" id="checkboxId" name="checkboxId" value="">
				<input type="hidden" id="parentcheckboxId" name="parentcheckboxId" value="">
				<input type="hidden" id="channelId" name="channelId" value="${channelId}">
				<input type="hidden" id="chainId" name="chainId" value="${chainId}">
				<input type="hidden" id="distributorId" name="distributorId" value="${distributorId}">
				<input type="hidden" id="channelName">
				<input type="hidden" id="distributorName">
				<input type="hidden" id="chainName">
				<input type="hidden" id="groupId" name="groupId">
				<input type="hidden" name="page" value="${page}">
			</form>
		</div>
		<div class="widget-content nopadding">
			<table class="table table-bordered data-table" id="c_list">
				<tr>
					<th style="text-align:center;">
						分组名称
					</th>
					<th style="text-align:center;">
						渠道
					</th>
					<th style="text-align:center;">
						连锁
					</th>
					<th style="text-align:center;">
						经销商
					</th>
					<th style="text-align:center;">
						包含产品数量
					</th>
					<@shiro.hasAnyPermission name="C2M8F2,C2M8F3">
						<th style="text-align:center;">
							操作
						</th>
					</@shiro.hasAnyPermission>
				</tr>
				<tbody>
					<#list pageParam.items as skuPrice>
						<tr>
							<td style="text-align:center;">
								${skuPrice.groupName!''}
							</td>
							<td style="text-align:center;">
								${skuPrice.channelName!''}
							</td>
							<td style="text-align:center;">
								${skuPrice.chainName!''}
							</td>
							<td style="text-align:center;">
								${skuPrice.distributorName!''}
							</td>
							<td style="text-align:center;">
								${skuPrice.skuNumber!''}
							</td>
							<@shiro.hasAnyPermission name="C2M8F2,C2M8F3">
								<td>
									<@shiro.hasPermission name="C2M8F2">
										<a class="editSkuPrice" href="javascript:void(0);" dataId="${skuPrice.skuPriceGroupId!''}"
										dataName="${skuPrice.groupName!''}">
											编辑
										</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M8F3">
										<a class="deleteSkuPrice" href="javascript:void(0);" data="${skuPrice.skuPriceGroupId!''}">
											删除
										</a>
									</@shiro.hasPermission>
								</td>
							</@shiro.hasAnyPermission>
						</tr>
					</#list>
				</tbody>
			</table>
			<#if pageParam.items?exists>
				<div class="paging">
					${pageParam.getPagination()}
				</div>
			</#if>
		</div>
	</div>
	<div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_channel" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_chain" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_distributor" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_distributor" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
</div>
<#include "/footer.ftl" />
</body>
</html>

<script type="text/javascript">
var editDialog;
var importDialog;
var addDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/skuPrice/showImportDialog";
		importDialog = new xDialog(url, {}, {title:"价格导入",width:650,height:330 });
		return true;
	});
	//新增
	$("#addBtn").bind("click",function(){
		var url = "${contextPath}/skuPrice/showAddSkuPrice";
		//layer.open({type: 2,title: '新增SOB价格',closeBtn: 1,area: ['450px', '330px'],content: url});
		window.location.href=url;
	});
		//编辑
	$("a.editSkuPrice").bind("click",function(){
	    var skuPriceGroupId=$(this).attr("dataId");
		var url = "${contextPath}/skuPrice/showEditSkuPrice/"+skuPriceGroupId;
		window.location.href=url;
	});
	
	
		//删除
	$("a.deleteSkuPrice").bind("click",function(){
	   var skuPriceId=$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/skuPrice/deleteSkuPrice",
				type : "get",
				async: false,
				data : {skuPriceId : skuPriceId},
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

$("#chain").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
		$("#chain").val(""); 
		$("#chainId").val(""); 
	}; 
});
$("#channel").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
		$("#channel").val(""); 
		$("#channelId").val(""); 
	}; 
});
$("#distributor").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
		$("#distributor").val(""); 
		$("#distributorId").val(""); 
	}; 
});
		
	var selectId;
	var setting_st = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/chain/getTreeNode",
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
    
    var setting_cl = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
			error : function() {  
                 alert('亲，渠道加载失败！');  
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
    	if(selectId == 'chain') {
			$("#chainId").attr("value", treeNode.id);
        }
        if(selectId == 'channel') {
			$("#channelId").attr("value", treeNode.id);
        }
        if(selectId == 'distributor'){
        	$("#distributorId").attr("value", treeNode.id);
        }
        hideMenu();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandAll(true);
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'chain') {
        	$("#menuContent_st").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if (objName == 'channel'){
        	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if(objName == 'distributor') {
			$("#menuContent_distributor").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="chain") {
     	   $("#menuContent_st").fadeOut("fast");
    	}
    	if(selectId =="channel") {
     	   $("#menuContent_cl").fadeOut("fast");
    	}
    	if(selectId =="distributor"){
    		$("#menuContent_distributor").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="chain"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_st" || $(event.target).parents("#menuContent_st").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="distributor"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_distributor" || $(event.target).parents("#menuContent_distributor").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="channel") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
	            hideMenu();
        	}
    	}
    }
$(document).ready(function () {
        $.fn.zTree.init($("#treeDemo_channel"), setting_cl);
        $.fn.zTree.init($("#treeDemo_chain"), setting_st);
        $.fn.zTree.init($("#treeDemo_distributor"), setting_distributor);
  });
       
</script>