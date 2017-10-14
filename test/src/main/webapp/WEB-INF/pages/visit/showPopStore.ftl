<html>
<head>
<#include "/common/head.ftl" />
<title>选择门店</title>
<#include "/common/foot.ftl" />
</head>
<body>
<div>
	<div class="widget-content nopadding">
	<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
		<div class="control-group">
			<div class="fl">
				<label class="control-label fl" for="searchStoreName">门店名称：</label>
				<div class="controls">
				  <input type="text" name="searchStoreName" value="${searchStoreName!''}">
				</div>
			</div>
			<div class="fl">
				<label class="control-label fl" for="searchAddr">门店地址：</label>
				<div class="controls">
				  <input type="text" name="searchAddr" value="${searchAddr!''}">
				</div>
			</div>
		</div>
		<div class="form-actions">
			<span class="choice-text">您已选择：<span id ="checkCount" style="color:red"></span>个对象</span><input type="submit" value="查询" class="btn btn-info fr" id="search_btn"/>
		</div>
		<input type="hidden" name="key" value="${key}">
		<input type="hidden" name="page" value="${page}">
	</form>
	</div>
	<div class="widget-content nopadding">
		<table class="table table-bordered data-table" id="c_list">
			<tr>
				<th class="th_checkbox">选择</th>
				<th width="200px">门店名称</th>
				<th width="50px">联系人</th>
				<th>手机号码</th>
				<th>渠道</th>
				<th>所属部门</th>
				<th>地址</th>
			</tr>
			<tbody>
				<#list pageParam.items as store>
					<tr>
						<td ><input name="chkItem" type="checkbox" class="checkboxStore" value="${store.storeId!''}" data="${store.storeName!''}"/></td>
						<td title=${store.storeName!''}>
							<#if store.storeName?? && store.storeName?length gt 16>
								${store.storeName?substring(0,15)}...
							<#else>
								${store.storeName!''}
							</#if>
						</td>
						<td>${store.contact!''}</td>
						<td>${store.mobileNo!''}</td>
						<td>${store.channelName!''}</td>
						<td>${store.structureName!''}</td>
						<td title=${store.addr!''}>
							<#if store.addr?? && store.addr?length gt 26>
								${store.addr?substring(0,25)}...
							<#else>
								${store.addr!''}
							</#if>
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
</body>
<script type="text/javascript" language="javascript">
	$(document).ready(function () {
		//初始化选中框
		var pobj = $(window.parent.document).find("#storeParameterDatas");
		var pobjCount = $(window.parent.document).find("#parameterCount");
		var storeIds = pobj.val();
		var array = storeIds.split(',');
		$("#checkCount").html(array.length-1);
		pobjCount.html(array.length-1);
		
        $("[name = chkItem]:checkbox").each(function () {
        	for (var index in array) {
                if ($(this).val() == array[index]) {
                    $(this).attr("checked", "checked");
                    break;
                }
            }
     	});
		
		$(".checkboxStore").click(function(){
			//alert($(this).prop("checked"));
			storeIds = pobj.val();
			if($(this).prop("checked")){
				storeIds += ","+$(this).val();
				pobj.attr("value",storeIds);
				array = storeIds.split(',');
				$("#checkCount").html(array.length-1);
				pobjCount.html(array.length-1);
			}
			if(!$(this).prop("checked")){
				var storeId = $(this).val();
				storeIds = pobj.val();
				array = storeIds.split(',');
				array.splice($.inArray(storeId,array),1);
				storeIds = array.join(",");
				pobj.attr("value",storeIds);
				$("#checkCount").html(array.length);
				pobjCount.html(array.length);
			}
		});
	});
</script>
</html>