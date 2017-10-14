<html>
<head>
<title>选择门店</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body>
	<div class="widget-content nopadding">
		<form id="dataForm" action="" method="post" class="form-horizontal">
			<input type="hidden" name="page" value="${page}">
			<div class="widget-content no_bottom_border">
				<div class="control-group">
					<div class="fl">
						<label class="control-label " for="searchStoreName">门店名称：</label>
						<div class="controls">
						  <input type="text" name="searchStoreName" value="${searchStoreName!''}">
						</div>
					</div>
					<div class="fl">
						<label class="control-label " for="searchStoreName">拜访状态：</label>
						<div class="controls">
						  <select type="text" id="callStatus" name="callStatus" value="${callStatusId}">
						  	<option value="">--请选择--</option>
						  	<#list callStatuslist as callStatus>
						  		<option value="${callStatus.getKey()}" <#if callStatusId == callStatus.getKey()> selected="selected" </#if>>${callStatus.getCnName()}</option>
						  	</#list>
						  </select>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn"/>
				</div>
			</div>
		</form>
	</div>
	<div class="widget-content nopadding">
		<table id="storeData" class="table table-bordered data-table">
			<thead>
				<th width="2%"></th>
				<th>门店编号</th>
				<th>门店名称</th>
				<th>地址</th>
				<th>拜访状态</th>
			</thead>
			<tbody>
			<#list pageParam.items as store>
			<tr>
				<td>
					<input name="chkItem" type="checkbox" disabled class="checkboxStore" <#if store.visitYesOrNot??> checked="checked" </#if> value="${store.storeId!''}"/>
				</td>
				<td>${store.storeNo!''}</td>
				<td>
					<#if store.storeName?? && store.storeName?length gt 15>
						${store.storeName?substring(0,15)}...
					<#else>
						${store.storeName!''}
					</#if>
					<#if store.planningType == 3>
						<span class="pop_lin" style="color: #fc9600;margin-left: 5px;border: 1px solid #fc9600;">临</span>
					</#if>
				</td>
				<td title="${store.addr!''}">
					<#if store.addr?? && store.addr?length gt 15>
						${store.addr?substring(0,15)}...
					<#else>
						${store.addr!''}
					</#if>
				</td>
				<td>
					<#if store.callStatus == 0>
						未拜访
					<#elseif store.callStatus == 1>
						拜访中
					<#elseif store.callStatus == 2>
						完成拜访
					<#elseif store.callStatus == 3>
						取消拜访
					</#if>
				</td>
			</tr>
			</#list>
			<tbody>
	 	</table>
	 	<#if pageParam.items?exists> 
			<div class="paging" > 
			   ${pageParam.getPagination()}
			</div> 
		</#if>
 	</div>
	<button data-dismiss="dialog"  type="button"  onClick="javascript:parent.layer.closeAll('iframe');" id="shut" class="btn btn-danger" style="margin-left:350px">关闭</button>
</body>
</html>
<script>
$(function(){
	//拜访状态
	$('#callStatus').select2({
		width:145
	});
	
	$('#shut').on("click",function(){
		parent.detailDialog.close();
	});
});

</script>
