<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>仓库管理</title>
</head>
<body class="main_body">

		<#assign privCode="C6M1">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">POSM管理</a>
				<a class="linkPage active" href="${contextPath}/warehouse/query">仓库管理</a>
			</div>
	 	</div>
	 	
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
							
					<div class="control-group">
						<div class="fl">
							<label class="control-label" for="searchWarehouseNo">仓库编号：</label>
							<div class="controls">
							  <input id="warehouseNo" type="text" class="input-medium" name="warehouseNo" value="${warehouseNo!''}"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label" for="searchWarehouseName">仓库名称：</label>
							<div class="controls">
							  <input id="warehouseName" type="text" class="input-medium" name="warehouseName" value="${warehouseName!''}"/>
							</div>
						</div>
								 
					</div>
					<div class="form-actions  margin-top-18">
					<@shiro.hasPermission name="C6M1F1">
					 	<button type="button" id="ADD_btn" class="btn btn-success">新增</button>
					 </@shiro.hasPermission>
					 <@shiro.hasPermission name="C6M1F2">
						 <button type="button" id="importBtn" class="btn btn-primary">导入</button>
					 </@shiro.hasPermission>
					 <@shiro.hasPermission name="C6M1F3">
                      	<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
                  	</@shiro.hasPermission>
                     <input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
     				<input type="hidden" name="page" value="${page}">
     				<input type="hidden" name="clientStructureId" value="${clientStructureId}">
     				<input type="hidden" name="createBy" value="${createBy}">  
				  </form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>仓库编号</th>
						<th>所在部门</th>
						<th>仓库名称</th>
						<th>地址</th>
						<th>仓库面积</th>
						<th>联系人</th>
						<th>联系电话</th>						
						<th>创建人</th>
						<th>备注</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as warehouse>
							<tr>
								<td>${warehouse.warehouseNo!''}</td>						        
						        <td>${warehouse.structureName!''}</td>
								<td title="${warehouse.warehouseName!''}">
									<#if warehouse.warehouseName ?? && warehouse.warehouseName?length gt 16>
										${warehouse.warehouseName?substring(0,10)}...
									<#else>
										${warehouse.warehouseName!''}
									</#if>
								</td>
								<td title="${warehouse.addr!''}">
									<#if warehouse.addr ?? && warehouse.addr?length gt 16>
										${warehouse.addr?substring(0,10)}...
									<#else>
										${warehouse.addr!''}
									</#if>
								</td>
								<td>${warehouse.area}</td>
								<td>${warehouse.contact!''}</td>
								<td>${warehouse.telephoneNo!''}</td>
								<td>${warehouse.name!''}</td>
								<td title="${warehouse.remark!''}">
									<#if warehouse.remark ?? && warehouse.remark?length gt 16>
										${warehouse.remark?substring(0,10)}...
									<#else>
										${warehouse.remark!''}
									</#if>
								</td>
 								<td>
								<@shiro.hasPermission name="C6M1F4">
									<a class="editwarehouse" href="javascript:void(0);" dataId="${warehouse.warehouseId!''}" dataName="${warehouse.warehouseName!''}">编辑</a>
						       	</@shiro.hasPermission>
									<a class="moreDetails"  href="javascript:void(0);" dataId="${warehouse.warehouseId!''}" dataName="${warehouse.warehouseName!''}">查看</a>
							 	<@shiro.hasPermission name="C6M1F5">
									<a class="deletewarehouse" href="javascript:void(0);" dataId="${warehouse.warehouseId!''}">删除</a>
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
				<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
				</div>
					<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;">
					<ul id="treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;">
					</ul>
					</div>
		</div>
	<#include "/footer.ftl" />
		
</body>
</html>
<script type="text/javascript">
	 
	//新增仓库
		$("#ADD_btn").bind("click",function(){	  
			var url = "${contextPath}/warehouse/showAddwarehouse";
			layer.open({
			    type: 2,
			    title:'新增仓库',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['675px', '444px'],
			    content: url
			});
		});
			//导入仓库
		$("#importBtn").bind("click",function(){
			var url = "${contextPath}/warehouse/importWarehouse";
			layer.open({
			    type: 2,
			    title:'仓库导入',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area:['650px','330px'],
			    content: url
			});
		});
		
		//导出
		$("#exportBtn").bind("click",function(){
				layer.confirm("确认导出", function () {
					var url ="${contextPath}/warehouse/exportBtnWarehouse";
					$('#searchForm').attr("action",url);
					$('#searchForm').submit();
					$('#searchForm').attr("action","${contextPath}/warehouse/query");
					layer.closeAll();
			});
			return false;
		});
			
	 	//编辑仓库
		$("a.editwarehouse").bind("click",function(){
			var warehouseid=$(this).attr("dataId");
	    	var warehouseName=$(this).attr("dataName");	
			var url = "${contextPath}/warehouse/showEditwarehouse/"+warehouseid;
	 		 layer.open({
					 type:2,
					 title:'编辑仓库'+warehouseName,
					 closeBtn:1,
					 shadeClose:true,
					area: ['675px', '429px'],
					 content: url
					 
				 });
		});
			//查看仓库详细信息
		$("a.moreDetails").bind("click",function(){
		  var warehouseId=$(this).attr("dataId");
	      var warehouseName=$(this).attr("dataName");
			var url = "${contextPath}/warehouse/showWarehouseDetail/"+warehouseId;
		
			layer.open({
					 type:2,
					 title:warehouseName+'信息',
					 closeBtn:1,
					 shadeClose:true,
					area: ['675px', '430px'],
					 content: url
					 
				 })
		});
		 
		 	//删除仓库
		$("a.deletewarehouse").bind("click",function(){
		var warehouseId =$(this).attr("dataId");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/warehouse/deleteWarehouse/"+warehouseId,
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
 
 
	
	 
</script>