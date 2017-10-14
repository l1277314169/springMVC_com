<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>促销物料</title>
</head>
<body class="main_body">
	  
	<#assign privCode="C6M3">
	<#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">POSM管理</a>
				<a class="linkPage active" href="${contextPath}/promotionMaterial/query">物料管理</a>
			</div>
	 	</div>

			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label" for="materialCode">物料编号：</label>
							<div class="controls">
							  <input id="materialCode" type="text" class="input-medium" name="materialCode" value="${materialCode!''}"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label" for="materialName">物料名称：</label>
							<div class="controls">
							  <input id="materialName" type="text" class="input-medium" name="materialName" value="${materialName!''}"/>
							</div>
						</div>
								 
					</div>
					<div class="form-actions  margin-top-18">
					<@shiro.hasPermission name="C6M3F1">
					 	<button type="button" id="new_btn" class="btn btn-success">新增</button>
					 </@shiro.hasPermission>
					 <@shiro.hasPermission name="C6M3F2">
						 <button type="button" id="importBtn" class="btn btn-primary">导入</button>
					 </@shiro.hasPermission>
					  <@shiro.hasPermission name="C6M3F3">
                     	 <button type="button" id="exportBtn" class="btn btn-primary">导出</button>
                      </@shiro.hasPermission>
                     <input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
     				<input type="hidden" name="page" value="${page}">
				  </form>
			</div>
		<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
					    <th width="220px;">物料名称</th>
						<th>物料编号</th>
					    <th>品牌</th>
					    <th>物料分类</th>
					    <th>子分类</th>
					    <th>单价</th>
					    <th>关键节点</th>
					    <th>年份</th>
					    <th>状态</th>
					    <th>备注</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as promotionMaterial>
						<tr>
						   <td>${(promotionMaterial.materialName)!''}</td>
						   <td>${(promotionMaterial.materialCode)!''}</td>
						   <td>${(promotionMaterial.brand)!''}</td>
						   <td>${(promotionMaterial.category)!''}</td>
						   <td>${(promotionMaterial.subCategory)!''}</td>
						   <td>${(promotionMaterial.price)!''}</td>
						   <td>${(promotionMaterial.spec)!''}</td>
						   <td>${(promotionMaterial.batch)!''}</td>
						   <td>
						   		<#if promotionMaterial.status ?? && promotionMaterial.status == '0'>
									无效
								<#else>
									有效
								</#if>
						   </td>
						   <td title="${promotionMaterial.remark!''}">
								<#if promotionMaterial.remark ?? && promotionMaterial.remark?length gt 16>
										${promotionMaterial.remark?substring(0,10)}...
									<#else>
										${promotionMaterial.remark!''}
									</#if>
								</td>
						    <td>
						     <@shiro.hasPermission name="C6M3F4">
								<a class="editPromoteMaterial" href="javascript:void(0);" dataId="${promotionMaterial.materialId!''}">编辑</a>
							</@shiro.hasPermission>
							<a class="checkPromoteMaterial"  href="javascript:void(0);" dataId="${promotionMaterial.materialId!''}" dataName="${promotionMaterial.materialName!''}">查看</a>
						     <@shiro.hasPermission name="C6M3F5">
						    	<a class="deletePromoteMaterial" href="javascript:void(0);" data="${promotionMaterial.materialId!''}">删除</a>
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
		</div>
		<#include "/footer.ftl" />
</body>
</html>

<script>
var addDialog,editDialog,checkDialog,importDialog;
 $(function(){
    //促销物料的导入
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/promotionMaterial/showImportDialog";
		 layer.open({
			 type:2,
			 title:'物料导入',
			 closeBtn:1,
			 shadeClose:true,
			 area:['650px','330px'],
			 content: url	 
	 });
		//importDialog = new xDialog(url, {}, {title:"物料导入",width:650,height:330});
		return false;
	});	
	
	//促销物料导出
	$("#exportBtn").bind("click",function(){
	  
		layer.confirm("确认导出促销物料数据吗？", function () {
			var url1 = "${contextPath}/promotionMaterial/exportPromotionMaterial";
			$("#searchForm").attr("action",url1);
			$("#searchForm").submit();
			var url2 = "${contextPath}/promotionMaterial/query";
			$("#searchForm").attr("action",url2);
			layer.closeAll();
		});
		return false;	
	});	
	
 //新增促销物料
    $("#new_btn").bind("click",function(){
       var url = "${contextPath}/promotionMaterial/showAddPromotionMaterial";
		    layer.open({
			    type: 2,
			    title: '促销物料新增',
			    closeBtn: 1,
			    area: ['800px', '400px'],
			    content: url
			});
    });
   //删除促销物料
   $("a.deletePromoteMaterial").bind("click",function(){
       var PromotionMaterialIdData=$(this).attr("data");
       layer.confirm("确定要删除吗？",function(){
         var resultcode;
         $.ajax({
           url:"${contextPath}/promotionMaterial/showDeletePromotionMaterial/"+PromotionMaterialIdData,
           type:"post",
           async: false,
           dataType:'Json',
           success:function(result){
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
  
  //编辑促销物料
  $("a.editPromoteMaterial").bind("click",function(){
       var promotionMaterialIdData=$(this).attr("dataId");
       var url = "${contextPath}/promotionMaterial/showEditPromotionMaterial/"+promotionMaterialIdData;
		    layer.open({
			    type: 2,
			    title: '编辑促销物料',
			    closeBtn: 1,
			   area: ['800px', '400px'],
			    content: url
			});
			return false;	
    });
    
  //查看促销物料
   $("a.checkPromoteMaterial").bind("click",function(){
	    var promotionMaterialIdData=$(this).attr("dataId");
	    var PromotionMaterialmaterialName=$(this).attr("dataName");
		var url = "${contextPath}/promotionMaterial/showCheckPromotionMaterial/"+promotionMaterialIdData;
		layer.open({
			    type: 2,
			    title: "查看促销物料---"+PromotionMaterialmaterialName,
			    closeBtn: 1,
			   area: ['800px', '420px'],
			    content: url
			});
			return false;	
	});
    
 });
</script>