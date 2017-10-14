<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<title>POSM 明细管理</title>
</head>
<body class="main_body">

		<#assign privCode="C6M4">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">POSM管理</a>
				<a class="linkPage active" href="${contextPath}/posmInOut/query">进出明细</a>
			</div>
	 	</div>

	<div class="widget-content nopadding" style="max-height:auto;overflow:auto;width:auto;">
		<div class="widget-content nopadding">
		<input type="hidden" id="count"  >
		<input type="hidden" id="total" value="${count}">
		<input type="hidden" id="childAll" value="${childAll}">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="parentId">仓库编号：</label>
						<div class="controls">
							<input type="text" id="warehouseNo" name="warehouseNo" class="input-medium" value="${warehouseNo}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="parentId">仓库名称：</label>
						<div class="controls">
							<input type="text" id="warehouseName" name="warehouseName" class="input-medium" value="${warehouseName}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="parentId">POSM 编号：</label>
						<div class="controls">
							<input type="text" id="materialCode" name="materialCode" class="input-medium" value="${materialCode}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="parentId">POSM 名称：</label>
						<div class="controls">
							<input type="text" id="materialName" name="materialName" class="input-medium" value="${materialName}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="parentId">类型：</label>
						<div class="controls">
							<input type="text" id="billType" name="billType" class="input-medium" value="${billType}"/>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<@shiro.hasPermission name="C6M4F1">
						<button type="button" id="addBtn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C6M4F2">
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C6M4F3">
						<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
					</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" id="clientStructureId" name="clientStructureId" value="${clientStructureId}">
				<input type="hidden" id="isLower" name="isLower" value="${isLower}">
				<input type="hidden" name="page" value="${page}">
				<input type="hidden" id="clientUserIds" name="clientUserIds" value="${clientUserIds}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th  class="fill_td" >大区</th>
						<th  class="fill_td" >城市</th>
						<th  class="fill_td" >仓库编号</th>
						<th  class="fill_td" >品牌</th>
						<th  class="fill_td" >物料分类</th>
						<th  class="fill_td" >物料名称</th>
					    <th  class="fill_td" >物料编号</th>
					    <th  class="fill_td" >年份</th>
					    <th  class="fill_td" >收进/发出</th>
						<th  class="fill_td" >数量(每箱规格)</th>
						<th  class="fill_td" >数量(个)</th>
						<th >时间</th>
						<th class="fill_td" >门店编号</th>
						<th class="fill_td" >客户</th>
						<th class="fill_td" >门店</th>
						<th class="fill_td" >仓库编号(目的地)</th>
						<th class="fill_td" >运输负责人</th>
						<th class="fill_td" >备注</th>
						<th class="fill_td" >操作</th>
						<!--
							<th>单价</th>
						<th>库存金额</th>
						<th>数据维护时间</th>
						<th>时间</th>
						<th>门店编号</th>
						<th>客户</th>
						<th>门店</th>
						<th>仓库编号</th>
						<th>运输负责人</th>
						<th>备注</th>
						-->
					</tr>
					<tbody>
						<#list LIST as inout>
						<tr>
							<td>${inout.structureName}</td>
							<td>${inout.cityName}</td>
							<td>${inout.warehouseNo}</td>
							<td>${inout.brand}</td>
							<td>${inout.category}</td>
							<td>${inout.materialName}</td>
							<td>${inout.materialCode}</td>
							<td>${inout.batch}</td>
							<#if inout.billType == "1" || inout.billType == 1 >
							<td>收进</td>
							</#if>
							<#if inout.billType == "2" || inout.billType == 2 >
							<td>发出</td>
							</#if>
							<td>${inout.spec}</td>
							<td>${inout.quantity}</td>
							<td><#if (inout.inOutTime)??>${(inout.inOutTime)?string("yyyy-MM-dd")}</#if></td>
							<td>${inout.storeNo}</td>
							<td>${inout.chainName}</td>
							<td>${inout.storeName}</td>
							<td>${inout.receiveWarehouseNo}</td>
							<td>${inout.handler}</td>
							<td title="${inout.remark!''}">
								<#if inout.remark ?? && inout.remark?length gt 16>
										${inout.remark?substring(0,10)}...
									<#else>
										${inout.remark!''}
									</#if>
								</td>
							<td>
								<a class="moreDetails"  href="javascript:void(0);" dataId="${inout.inOutId!''}" dataName="${inout.inOutId!!''}">查看</a>
								<!--
								<a class="editwarehouse" href="javascript:void(0);" dataId="${inout.inOutId!!''}" dataName="${inout.inOutId!!''}">编辑</a>
								-->
								<@shiro.hasPermission name="C6M4F4">
									<a class="deletewarehouse" href="javascript:void(0);" dataId="${inout.inOutId!''}">删除</a>
								</@shiro.hasPermission>
							</td>
						</tr>
						</#list>
					</tbody>
				</table>
				<!-- 分页 -->
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
		</div>
<#include "/footer.ftl" />
</body>
</html>

<script type="text/javascript">
var importDialog,addDialog,editDialog,checkDialog,exportBtn,batchClientUserDialog,clientUserIds,array,batchChoiceStoreDialog,batchChoiceStoreDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/posmInOut/showImportPosmInOut";
		layer.open({
			 type:2,
			 title:'进出明细导入',
			 closeBtn:1,
			 shadeClose:true,
			 area:['650px','330px'],
			 content: url	 
	 });
		//importDialog = new xDialog(url, {}, {title:"进出明细导入",width:650,height:330 });
		return false;
	});
	//导出
	$("#exportBtn").bind("click",function(){
			layer.confirm("确认导出", function () {
				var url ="${contextPath}/posmInOut/importPosmInOut";
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				$('#searchForm').attr("action","${contextPath}/posmInOut/query");
				layer.closeAll();
		});
		return false;
	});



		 	//删除仓库
$("a.deletewarehouse").bind("click",function(){
	var warehouseId =$(this).attr("dataId");
    layer.confirm("确认删除吗？", function () {
    	var resultCode; 
		$.ajax({
			url : "${contextPath}/posmInOut/detelePostInOut?inOutId="+warehouseId,
			type : "get",
			async: false,
			dataType:'JSON',
			success : function(result) {
				 if (result.code == "success") {
		   				layer.alert(result.message,function(){
					   			window.location.reload();
				   				//addDialog.close();
				   		});	
				} else {
					layer.alert(result.message);
					$("#savetButton").removeAttr("disabled");
				}
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
	//新增
$("#addBtn").bind("click",function(){
	var url = "${contextPath}/posmInOut/showAddPosmInOut";
	 layer.open({
		 type:2,
		 title:'明细新增',
		 closeBtn:1,
		 shadeClose:true,
		 area:['720px','380px'],
		 content: url
		 
	 });
});
//编辑仓库
$("a.editwarehouse").bind("click",function(){
	var warehouseid=$(this).attr("dataId");
	var url = "${contextPath}/posmInOut/showEditPosmInOut/"+warehouseid;
	 layer.open({
			 type:2,
			 title:'明细编辑',
			 closeBtn:1,
			 shadeClose:true,
			 area:['680px','480px'],
			 content: url
			 
		 });
});
//查看仓库详细信息
/*****/
$("a.moreDetails").bind("click",function(){
  var warehouseId=$(this).attr("dataId");
	var url = "${contextPath}/posmInOut/showPosmInOut/"+warehouseId;
	layer.open({
			 type:2,
			 title:'明细信息',
			 closeBtn:1,
			 shadeClose:true,
			 area:['650px','325px'],
			 content: url
		 })
});
$(function() {
	var status = $("#billType").val();
	loadStatus(status);
});
function loadStatus(status) {
	$.ajax({
		type: "post",
		url: "${contextPath}/posmInOut/getBillType",
		dataType: "json",
		cache: false,
		success: function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData,
			function(index, item) {
				if (index != jsonData.length - 1) {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"},";
				} else {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#billType").select2({
				width: 145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error: function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

</script>