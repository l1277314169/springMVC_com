<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>产品分销管理</title>
</head>
<body class="main_body">
	<#assign privCode="C2M7">
		<#include "/base.ftl" />
		
		<div id="content"> 
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础数据管理</a>
					<a class="linkPage active" href="${contextPath}/skuDistribution/query">产品分销管理</a>
				</div>
		 	</div>
		<div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label " for="channel">渠道：</label>
						<!--	<div class="controls">
						  		<input id="channel" type="text" class="input-medium" name="channel" readonly="readonly"  value="${channelName!''}" onclick="showMenu(this.id);"/>
							</div>
							-->
							<div class="controls">
							  <input type="text" id="clientStructureId_channel" name="clientStructureName_channel"  class="input-medium" readonly onclick="showMenu_channel();" />
							  <input type="hidden" id="arg_channel_ids" name="channelId" value="${channelId!''}" />
							  <#assign channelFTL="arg_channel_ids">
							  <#include "/widgets/channel_widget.ftl" />
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label " for="chain">连锁：</label>
						<!--	<div class="controls">
						  		<input id="chain" type="text" class="input-medium" name="chain" readonly="readonly" value="${chainName!''}" onclick="showMenu(this.id);"/>
							</div>
							-->
							<div class="controls">
							  <input type="text" id="clientStructureId_chain" name="clientStructureName_chain"  class="input-medium" readonly onclick="showMenu_chain();">
							  <input type="hidden" id="arg_types" name="chainId" value="${chainId!''}" >
							  <#assign chainFTL="arg_types">
							  <#include "/widgets/chain_widget.ftl" />
							</div>
						</div>
					</div>
					<div class="form-actions">
							<@shiro.hasPermission name="C2M7F1">
					  		<button type="button" id="new_btn" class="btn btn-success">新增</button>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="C2M7F4">
							<!--<button type="button" id="importBtn" class="btn btn-primary">导入</button>-->
							</@shiro.hasPermission>
							<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					</div>
					<input type="hidden" name="page" value="${page}">
					<input type="hidden" id="channelId" name="channelId" value="${channelId}"/>
					<input type="hidden" id="chainId" name="chainId" value="${chainId}"/>
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>分组名称</th>
						<th>渠道</th>
						<th>连锁</th>
						<th>经销商</th>
						<th>产品分销数量</th>
						<th>操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as skuDistribution>
							<tr>
								<td>${skuDistribution.groupName!''}</td>
								<td>${skuDistribution.channelName!''}</td>
								<td>${skuDistribution.chainName!''}</td>
								<td>${skuDistribution.distributorName!''}</td>
								<td>${skuDistribution.skuNumber!''}</td>
								<td>
								<@shiro.hasPermission name="C2M7F2">
									<a class="editSkuDistribution" href="javascript:void(0);" dataId="${skuDistribution.skuDistributionId!''}">编辑</a>
								</@shiro.hasPermission>									
								<a class= "lookSkuDistribution" href="javascript:void(0)};" dataId="${skuDistribution.skuDistributionId!''}">查看</a>
								<@shiro.hasPermission name="C2M7F3">
									<a class= "deleteSkuDistribution"  href="javascript:void(0)};" dataId="${skuDistribution.skuDistributionId!''}">删除</a>
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
		
		<div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_channel" class="ztree" style="margin-top: 0; width: 160px;"></ul>
		</div>
		<div id="menuContent_ch" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_chain" class="ztree" style="margin-top: 0; width: 160px;"></ul>
		</div>
		<div id="menuContent_db" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo_distributor" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
		</div>
			</div>
		</div>
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">   
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/skuDistribution/showImportDialog";
		importDialog = new xDialog(url, {}, {title:"产品分销导入",width:650,height:330 });
		return true;
	});
	
	//新增
	$("#new_btn").bind("click",function(){
		//var url = "${contextPath}/skuDistribution/showAddSkuDistribution";
		//addDialog = new xDialog(url, {}, {title:"新增产品分销",width:450,height:290});
		//return false;
		document.location.href = "${contextPath}/skuDistribution/showAddSkuDistribution";
	});
	
	//编辑
	$("a.editSkuDistribution").bind("click",function(){
		var skuDistributionId = $(this).attr("dataId");
		//var url = "${contextPath}/skuDistribution/showEditSkuDistribution?skuDistributionId="+skuDistributionId;
		//editDialog = new xDialog(url,{},{title:"编辑产品分销",width:450,height:290 });
		//return false;	
		document.location.href = "${contextPath}/skuDistribution/showEditSkuDistribution?skuDistributionId="+skuDistributionId;
	});
	
	//查看
	$("a.lookSkuDistribution").bind("click",function(){
		var skuDistributionId = $(this).attr("dataId");
		var url = "${contextPath}/skuDistribution/lookSkuDistribution?skuDistributionId="+skuDistributionId;
		//lookDialog = new xDialog(url,{},{title:"查看产品分销",iframe:true,width:800,height:660});
		//return false;	
		 layer.open({
			    type: 2,
			    title:'查看产品分销',
			    closeBtn: 1,
			    area: ['830px', '660px'],
			    content: url
				});	
		
	});

	//删除
	$("a.deleteSkuDistribution").bind("click",function(){
		var skuDistributionId = $(this).attr("dataId"); 
	     layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			 $.ajax({
				url : "${contextPath}/skuDistribution/deleteSkuDistribution",
				type : "post",
				async: false,
				dataType:'JSON',
				data : {skuDistributionId:skuDistributionId},
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
				layer.alert(result.message);
			}});
		}
	}
	
	});
	
	var selectId;
	//渠道树
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
    //连锁树
    var setting_ch = {
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
    //经销商
     var setting_db= {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/distributor/getTreeNode",
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
    
    $("#channel").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#channel").val(""); 
			$("#channelId").val(""); 
			}; 
	});
	$("#chain").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#chain").val(""); 
			$("#chainId").val(""); 
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
		if(treeId == "treeDemo_channel"){
			var node = zTree.getNodeByParam("id", $("#channelId").val(), null);
			if(node != null){
				$("#channel").val(node.name);
			}
		}
		if(treeId == "treeDemo_chain"){
			var node = zTree.getNodeByParam("id", $("#chainId").val(), null);
			if(node != null){
				$("#chain").val(node.name);
			}
		}
		if(treeId == "treeDemo_distributor"){
			var node = zTree.getNodeByParam("id", $("#distributorId").val(), null);
			if(node != null){
				$("#distributor").val(node.name);
			}
		}
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'chain') {
        	$("#menuContent_ch").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if (objName == 'channel'){
        	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        }else if(objName == 'distributor'){
			$("#menuContent_db").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="chain") {
     	   $("#menuContent_ch").fadeOut("fast");
    	}
    	if(selectId =="channel") {
     	   $("#menuContent_cl").fadeOut("fast");
    	}
    	if(selectId =="distributor"){
    		$("#menuContent_db").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="chain"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_ch" || $(event.target).parents("#menuContent_ch").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="channel") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
	            hideMenu();
        	}
    	}
    	if(selectId =="distributor") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_db" || $(event.target).parents("#menuContent_db").length > 0)) {
	            hideMenu();
        	}
    	}
    }
</script>
