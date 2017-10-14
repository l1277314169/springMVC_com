<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>任务管理</title>
<style type="text/css">
	.ui-datepicker-calendar {
    	display: none;
    }
</style>
</head>
<body class="main_body">
	  
	<#assign privCode="C1M5">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">执行管理</a>
				<a class="linkPage active" href="${contextPath}/factMonthlySales/query">销量管理</a>
			</div>
	 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<input type="hidden" id="structureId" name="structureId" value="${factMonthlySalesSearchVo.structureId!''}">
			    	<input type="hidden" id="channelId" name="channelId" value="${factMonthlySalesSearchVo.channelId!''}">
			    	<input type="hidden" id="chainId" name="chainId" value="${factMonthlySalesSearchVo.chainId!''}">	
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="storeId">部门：</label>
							<div class="controls">
								<input id="structureName" name="structureName" required="required" readonly type="text" value="${factMonthlySalesSearchVo.structureName}"/>
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">月份编号：</label>
							<div class="controls">
							  <input type="text" id="monthId" name="monthId" value="<#if factMonthlySalesSearchVo.monthId!=''>${factMonthlySalesSearchVo.monthId}</#if>" readonly="readonly" class="input-medium" />
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">所属渠道：</label>
							<div class="controls">
							  <input id="channelName" name="channelName" readonly type="text" required=true value="${factMonthlySalesSearchVo.channelName}"/>
							</div>
						</div>
					<!--	<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">连锁渠道：</label>
							<div class="controls">
							  <input id="chainName" name="chainName" readonly type="text" required=true value="${factMonthlySalesSearchVo.chainName}"/>
							</div>
						</div>   -->
					<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">连锁渠道：</label>
            	 		<div class="controls">
							  <input type="text" id="clientStructureId_chain" name="chainName"  required=true value="${factMonthlySalesSearchVo.chainName}" class="input-medium" readonly onclick="showMenu_chain();">
							  <input type="hidden" id="arg_types" name="chainId" required=true value="${factMonthlySalesSearchVo.chainName}" />
							  <#assign chainFTL="arg_types">
							  <#include "/widgets/chain_widget.ftl" />
							</div>
              	  </div>
						
					</div>				 
					<div class="form-actions">			
						<!--<@shiro.hasPermission name="C1M5F2">	 
							<button type="button" id="new_btn" class="btn btn-success">添加销量</button>
						</@shiro.hasPermission>-->
						<@shiro.hasPermission name="C1M5F1">
							<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C1M5F3">
							<button type="button" id="exportFactMonthlySales" class="btn btn-success">导出</button>
						</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding" style="overflow:auto;width:auto;">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th class="fill_td">月份编号</th>
						<th class="fill_td">门店自编号</th>
						<th class="fill_td">门店编号</th>
				      	<th class="fill_td">门店名称</th>
				      	<th class="fill_td">大区</th>
				      	<th class="fill_td">分部</th>
				      	<th class="fill_td">省</th>
				      	<th class="fill_td">市</th>
				      	<th class="fill_td">渠道</th>
				      	<th class="fill_td">连锁(店铺渠道)</th>
				      	<th class="fill_td">经销商</th>
				      	<th class="fill_td">月份</th>
				      	<th class="fill_td">产品条码</th>
				      	<th class="fill_td">sku名称</th>
				      	<th class="fill_td">销售数量</th>
				      	<th class="fill_td">销售金额</th>
				      	<th class="fill_td">零售</th>
				      	<th class="fill_td">建议零售价</th>
				      	<th class="fill_td">标准零售价</th>				      	
				      	<th class="fill_td">业代</th>
				      	<th class="fill_td">业代主管</th>
			      		<th class="fill_td">促销员(ba)</th>
						<th class="fill_td">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as factMonthlySalesVo>
						<tr>					
							<td class="fill_td">${factMonthlySalesVo.monthId}</td>
							<td class="fill_td">${factMonthlySalesVo.externalNo}</td>
							<td class="fill_td">${factMonthlySalesVo.storeNo}</td>
							<td class="fill_td">${factMonthlySalesVo.storeName}</td>
							<td class="fill_td">${factMonthlySalesVo.salesArea}</td>
							<td class="fill_td">${factMonthlySalesVo.salesDivision}</td>							
							<td class="fill_td">${factMonthlySalesVo.province}</td>
							<td class="fill_td">${factMonthlySalesVo.city}</td>
							<td class="fill_td">${factMonthlySalesVo.channelName}</td>
							<td class="fill_td">${factMonthlySalesVo.chainName}</td>
							<td class="fill_td">${factMonthlySalesVo.distributorName}</td>
							<td class="fill_td">${factMonthlySalesVo.monthDesc}</td>
							<td class="fill_td">${factMonthlySalesVo.barcode}</td>
							<td class="fill_td">${factMonthlySalesVo.skuName}</td>
							<td class="fill_td">${factMonthlySalesVo.salesVolume}</td>
							<td class="fill_td">${factMonthlySalesVo.salesAmount}</td>
							<td class="fill_td">${factMonthlySalesVo.sellingPrice}</td>
							<td class="fill_td">${factMonthlySalesVo.retailPrice}</td>
							<td class="fill_td">${factMonthlySalesVo.price}</td>
							<td class="fill_td">${factMonthlySalesVo.salesman}</td>
							<td class="fill_td">${factMonthlySalesVo.salesLeader}</td>	
							<td class="fill_td">${factMonthlySalesVo.promoter}</td>						
							<td class="fill_td">
								<a class="showFactMonthlySales" href="javascript:void(0);" dataId="${factMonthlySalesVo.dataId}">查询</a>	
								<@shiro.hasPermission name="C1M5F4">								 							 					 
									<a class="editFactMonthlySales" href="javascript:void(0);" dataId="${factMonthlySalesVo.dataId}">编辑</a>
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
	
		<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
			<ul id="add_treeDemo_structureName" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
		<div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
			<ul id="add_treeDemo_channelName" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
			<div id="menuContent_chainName" class="menuContent" style="display: none; position: absolute;">
			<ul id="add_treeDemo_chainName" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
		</div>
				</div>
		</div>
		<#include "/footer.ftl" />
</body>
</html>
<script>
var importDialog;
var editDialog;
var addDialog;

var selectId;
var add_setting_st = {
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

var add_setting_chainName = {
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

var add_setting_cl = {
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

function beforeClick(treeId, treeNode) {
	var demo = "add_treeDemo_"+selectId;
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
	if(selectId == 'structureName') {
		$("#structureId").attr("value", treeNode.id);
    }
    if(selectId == 'channelName') {
		$("#channelId").attr("value", treeNode.id);
    }
    if(selectId == 'chainName'){
    	$("#chainId").attr("value", treeNode.id);
    }
    hideMenu();
    $("#"+selectId).focus();
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
    if(objName == 'structureName') {
    	$("#menuContent_st").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
    } else if (objName == 'channelName'){
    	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
    } else if(objName == 'chainName') {
		$("#menuContent_chainName").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
   	}else {

   	}
    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	if(selectId =="structureName") {
 	   $("#menuContent_st").fadeOut("fast");
	}
	if(selectId =="channelName") {
 	   $("#menuContent_cl").fadeOut("fast");
	}
	if(selectId =="chainName"){
	   $("#menuContent_chainName").fadeOut("fast");
	}
    $("body").unbind("mousedown", onBodyDown);
}        
function onBodyDown(event) {
	if(selectId =="structureName"){
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_st" || $(event.target).parents("#menuContent_st").length > 0)) {
            hideMenu();
    	}
	};
	if(selectId =="chainName"){
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_chainName" || $(event.target).parents("#menuContent_chainName").length > 0)) {
            hideMenu();
    	}
	};
	if(selectId =="channelName") {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
            hideMenu();
    	}
	}
}	

$(function(){
 	
 	$("#structureName,#channelName,#distributorName,#chainName").on("click",function(){
		var id = $(this).attr("id");
		showMenu(id);
	});
 	
 	$.fn.zTree.init($("#add_treeDemo_structureName"), add_setting_st);
    $.fn.zTree.init($("#add_treeDemo_channelName"), add_setting_cl);
    $.fn.zTree.init($("#add_treeDemo_chainName"), add_setting_chainName);
 	
 	$("#structureName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#structureName").val(""); 
			$("#structureId").val(""); 
		}; 
	});
	$("#channelName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#channelName").val(""); 
			$("#channelId").val(""); 
		}; 
	});
	$("#chainName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#chainName").val(""); 
			$("#chainId").val(""); 
		}; 
	});
 	
	$("#monthId").datepicker({
		changeMonth:true,
		changeYear:true,
		dateFormat:"yymm",
		yearRange:"2008:2025",
		prevText: "<",
		nextText: ">",
		closeText: "确定",
		showButtonPanel: true,
		onClose:function(dateText,inst){
            var date = new Date();
            var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();//得到选中的月份值	
            var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();//得到选中的年份值
            date.setFullYear(year);
            date.setMonth(month);           
            $("#monthId").datepicker('option', 'dateFormat','yymm');
            $('#monthId').datepicker('setDate',date);
		}
	});
	
 	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/factMonthlySales/showImportDialog";
		//importDialog = new xDialog(url, {}, {title:"销量导入",width:650,height:330 });
		//return false;
		  layer.open({
			    type: 2,
			    title: '销量导入',
			    closeBtn: 1,
			    area: ['600px', '380px'],
			    content: url
			});
		
	});
	
	//导出门店信息
	$("#exportFactMonthlySales").bind("click",function(){
		layer.confirm("确认导出销量数据吗？", function () {
			var url1 = "${contextPath}/factMonthlySales/exportFactMonthlySales";
			$("#searchForm").attr("action",url1);
			$("#searchForm").submit();
			var url2 = "${contextPath}/factMonthlySales/query";
			$("#searchForm").attr("action",url2);
		});
		return false;	
	});
	
	//编辑
	$("a.editFactMonthlySales").bind("click",function(){	      
		var dataId = $(this).attr("dataId");
		var url = "${contextPath}/factMonthlySales/showEditFactMonthlySales/?dataId="+dataId;
		//editDialog = new xDialog(url,{},{title:"编辑销量",iframe:true,width:400,height:300 }); 
		//return false;	
		 layer.open({
			    type: 2,
			    title: '编辑销量',
			    closeBtn: 1,
			    area: ['450px', '320px'],
			    content: url
			});
	});
	
	//查看
	$("a.showFactMonthlySales").bind("click",function(){	      
		var dataId = $(this).attr("dataId");
		var url = "${contextPath}/factMonthlySales/showFactMonthlySales/?dataId="+dataId;
		//editDialog = new xDialog(url,{},{title:"查看销量",width:400,height:300 }); 
		//return false;	
		 layer.open({
			    type: 2,
			    title: '查看销量',
			    closeBtn: 1,
			    area: ['450px', '330px'],
			    content: url
			});
	});
	
	
	$("#new_btn").bind("click",function(){
		var url = "${contextPath}/factMonthlySales/showAddFactMonthlySales";
		addDialog = new xDialog(url,{},{title:"添加销量",iframe:true,width:700,height:300});	
	});
	
});
</script>