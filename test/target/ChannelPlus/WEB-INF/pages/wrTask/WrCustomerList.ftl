<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>客户信息维护</title>
<body class="main_body">

	<#assign privCode="C2M16">
	<#include "/base.ftl" />
	
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基本数据管理</a>
				<a class="linkPage active" href="${contextPath}/wrCustomer/query">客户维护</a>
			</div>
	 	</div>
	 	
       <div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div class="fl">
							<label class="control-label " for="materialName">客户名称：</label>
							<div class="controls">
								<input type="text" id="customerName" name="customerName" value="${customerName}" class="input-medium">
							</div>
					</div>
				    </div>
				<div class="form-actions  margin-top-18">
				 
		        <@shiro.hasPermission name="C2M16F1">
		          <button type="button" id="new_btn" class="btn btn-success">新增</button>
	              </@shiro.hasPermission>
	              
				 <input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				  </div>
		         <input type="hidden" name="page" value="${page}">
			</form>
    </div>
     <div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
					    <th>客户名称</th>
					    <th>品牌</th>
					    <th>备注</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as wrCustomer>
						<tr>
						   <td>${(wrCustomer.customerName)!''}</td>
						   <td title="${wrCustomer.brandName!''}">
								<#if wrCustomer.brandName ?? && wrCustomer.brandName?length gt 20>
										${wrCustomer.brandName?substring(0,20)}...
									<#else>
										${wrCustomer.brandName!''}
									</#if>
								</td>
						    <td title="${wrCustomer.remark!''}">
								<#if wrCustomer.remark ?? && wrCustomer.remark?length gt 30>
										${wrCustomer.remark?substring(0,30)}...
									<#else>
										${wrCustomer.remark!''}
									</#if>
								</td>
						    <td>
							   <@shiro.hasPermission name="C2M16F2">
								<a class="editPromoteMaterial" href="javascript:void(0);" dataId="${wrCustomer.customerId!''}">编辑</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C2M16F3">
								<a class="deletePromoteMaterial" href="javascript:void(0);"  data="${wrCustomer.customerId!''}">删除</a>
								</@shiro.hasPermission>
								<a class="checkPromoteMaterial" href="javascript:void(0);"  dataId="${wrCustomer.customerId!''}" dataName="${wrCustomer.customerName!''}">查看</a>
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
 //新增客户
    $("#new_btn").bind("click",function(){
       var url = "${contextPath}/wrCustomer/showAddWrCustomer";
		    layer.open({
			    type: 2,
			    title: '客户新增',
			    closeBtn: 1,
			    area: ['580px', '240px'],
			    content: url
			});
    });
    
    //删除客户
    $("a.deletePromoteMaterial").bind("click",function(){
       var customerIdData=$(this).attr("data");
       layer.confirm("确定要删除吗？",function(){
         var resultcode;
         $.ajax({
           url:"${contextPath}/wrCustomer/showDeleteWrCustomer/"+customerIdData,
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
  
  //编辑客户
   $("a.editPromoteMaterial").bind("click",function(){
       var customerIdData=$(this).attr("dataId");
       var url = "${contextPath}/wrCustomer/showEditWrCustomer/"+customerIdData;
		// editDialog = new xDialog(url,{},{title:"编辑客户信息维护",width:550,height:240});
		 layer.open({
			    type: 2,
			    title: '编辑客户信息维护',
			    closeBtn: 1,
			    area: ['550px', '270px'],
			    content: url
			}); 
		return false;	
    });
    
    //查看客户
     $("a.checkPromoteMaterial").bind("click",function(){
	    var customerIdData=$(this).attr("dataId");
	    var customerName=$(this).attr("dataName");
		var url = "${contextPath}/wrCustomer/showCheckWrCustomer/"+customerIdData;
		// checkDialog = new xDialog(url,{},{title:"查看客户信息维护---"+customerName ,width:550,height:200}); 
		layer.open({
			    type: 2,
			    title: '查看客户信息维护'+customerName,
			    closeBtn: 1,
			    area: ['400px', '180px'],
			    content: url
			}); 
		return false;	
	});
 });
</script>