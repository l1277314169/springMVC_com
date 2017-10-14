<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>产品管理</title>
</head>
<body class="main_body">
	<#assign privCode="C2M5">
    <#include "/base.ftl" />
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基本数据管理</a>
				<a class="linkPage active" href="${contextPath}/survey/query">产品管理</a>
			</div>
	 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label" for="searchSkuNo">产品编号：</label>
							<div class="controls">
							  <input id="searchSkuNo" type="text" class="input-medium" name="searchSkuNo" value="${searchSkuNo!''}"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label" for="searchSkuName">产品名称：</label>
							<div class="controls">
							  <input id="searchSkuName" type="text" class="input-medium" name="searchSkuName" value="${searchSkuName!''}"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="brand">品牌：</label>
							<div class="controls">
							  <input id="brand" type="text" class="input-medium" name="brand" readonly="readonly"  value="${brand!''}" onclick="showMenu(this.id);"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="category">品类：</label>
							<div class="controls">
							  <input id="category" type="text" class="input-medium" name="category" readonly="readonly" value="${category!''}" onclick="showMenu(this.id);"/>
							</div>
						</div>
					</div>
					<div class="form-actions  margin-top-18">
						<@shiro.hasPermission name="C2M5F1">
						<button type="button" id="new_btn" class="btn btn-success">新增</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M5F4">
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" id="brandId" name="brandId" value="${brandId!''}">
					<input type="hidden" id="categoryId" name="categoryId" value="${categoryId!''}">
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>产品编号</th>
						<th>产品名称</th>
						<th>产品条码</th>
						<th>品牌</th>
						<th>产品分组</th>
						<th>类别</th>
						<th>价格</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as sku>
							<tr>
								<td>${sku.skuNo!''}</td>
								<td title="${sku.skuName!''}">
									<#if sku.skuName?? && sku.skuName?length gt 15>
										${sku.skuName?substring(0,15)}...
									<#else>
										${sku.skuName!''}
									</#if>
								</td>
								<td>${sku.barcode!''}</td>
								<td>${sku.brandName!''}</td>
								<td>${sku.groupName}</td>
								<td>${sku.categoryName!''}</td>
								<td>${sku.price!''}</td>
								<td>
								<@shiro.hasPermission name="C2M5F2">
									<a class="editSku" href="javascript:void(0);" dataId="${sku.skuId!''}" dataName="${sku.skuName!''}">编辑</a>
								</@shiro.hasPermission>	
									<a class="moreDetails" href="javascript:void(0);" dataId="${sku.skuId!''}" dataName="${sku.skuName!''}">查看</a>
								<@shiro.hasPermission name="C2M5F3">
									<a class="deleteSku" href="javascript:void(0);" dataId="${sku.skuId!''}">删除</a>
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
		
		<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
		</div>
		<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
			</div>
		</div>
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo_brand"), setting_brand);
	    $.fn.zTree.init($("#treeDemo_category"), setting_category);
	});
	
	var importDialog,addDialog,editDialog,moreDetailsDialog;
	$(function(){
		//导入窗口
		$("#importBtn").bind("click",function(){
			var url = "${contextPath}/sku/showImportDialog";
			
			layer.open({
			    type: 2,
			    title:'产品导入',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['660px', '350px'],
			    content: url
			});
		});
		//新增产品
		$("#new_btn").bind("click",function(){
			var url = "${contextPath}/sku/showAddDialog";
			layer.open({
			    type: 2,
			    title:'新增产品',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['700px', '552px'],
			    content: url
			});
		});
		
		
		//编辑产品
		$("a.editSku").bind("click",function(){
			var skuId=$(this).attr("dataId");
	    	var skuName=$(this).attr("dataName");
			var url = "${contextPath}/sku/showEditSku/"+skuId;
	 		 layer.open({
					 type:2,
					 title:'编辑产品---'+skuName,
					 closeBtn:1,
					 shadeClose:true,
					 area:['730px','590px'],
					 content: url
					 
				 })
		});
		//查看产品详细信息
		$("a.moreDetails").bind("click",function(){
		    var skuId=$(this).attr("dataId");
		    var skuName=$(this).attr("dataName");
			var url = "${contextPath}/sku/showSkuDetail/"+skuId;
		
			layer.open({
					 type:2,
					 title:skuName+'信息',
					 closeBtn:1,
					 shadeClose:true,
					 area:['680px','507px'],
					 content: url
					 
				 })
		});	
		//删除产品
		$("a.deleteSku").bind("click",function(){
		var skuId =$(this).attr("dataId");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/sku/deleteSku/"+skuId,
				type : "get",
				async: false,
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
	
	
		$("#brand").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#brand").val(""); 
			$("#brandId").val(""); 
			}; 
			});
		$("#category").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#category").val(""); 
			$("#categoryId").val(""); 
			}; 
			});
	});
	//品牌、品类树
	var selectId;
	var setting_brand = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/brand/getTreeNode",
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
	
	var setting_category = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/category/getTreeNode",
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
			onAsyncSuccess: zTreeOnAsyncSuccess,
	        onClick: onClick
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
    	if(selectId == 'brand') {
			$("#brandId").attr("value", treeNode.id);
        }
        if(selectId == 'category') {
			$("#categoryId").attr("value", treeNode.id);
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
        if(objName == 'brand') {
        	$("#menuContent_brand").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        };if(objName == 'category') {
        	$("#menuContent_category").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        };
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="brand") {
     	   $("#menuContent_brand").fadeOut("fast");
    	}
    	if(selectId =="category") {
     	   $("#menuContent_category").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="brand"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_brand" || $(event.target).parents("#menuContent_brand").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="category") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_category" || $(event.target).parents("#menuContent_category").length > 0)) {
	            hideMenu();
        	}
    	}
    }
</script>