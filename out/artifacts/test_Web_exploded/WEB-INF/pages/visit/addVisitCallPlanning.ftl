<#import "/common/pagination.ftl" as pagination>
<input type="hidden" name="page" value="${page}">
<div class="widget-content nopadding" id="logResultList" style="width : 760px;">
	<table id="storeData" class="table table-bordered data-table" style="margin-top: 0px" >
			<tr>
				<th><input type="checkbox" id="checkAlls" name="checkAlls"></th>
				<th>门店编号</th>
				<th>门店名称</th>
				<th>地址</th>
				<th>业务员</th>
			</tr>
		<tbody>
		<#list pageParam.items as store>
		<tr>
			<td>
				<input type="checkbox"  name="chkItem" class="checkboxStore" <#if store.visitYesOrNot??> checked="checked" disabled="disabled"</#if> value="${store.storeId!''}"/>
			</td>
			<td>${store.storeNo!''}</td>
			<td>${store.storeName!''}</td>
			<td title="${store.addr!''}">
				<#if store.addr?? && store.addr?length gt 8>
					${store.addr?substring(0,8)}...
				<#else>
					${store.addr!''}
				</#if>
			</td>
			<td>${store.name!''}</td>
		</tr>
		</#list>
		<tbody>
 	</table>
 	<div class="paging">	
    	<@pagination.paging pageParam true "#logResultList"/>
	</div>
</div>
<script type="text/javascript">
//初始化选中框
var pobj = $("#storeParameterDatas");
var storeIds = pobj.val();
var array = storeIds.split(',');
$("[name = chkItem]:checkbox").each(function () {
	for (var index in array) {
        if ($(this).val() == array[index]) {
            $(this).attr("checked", "checked");
            break;
        }
    }
});

$(".checkboxStore").click(function(){
	storeIds = pobj.val();
	if($(this).prop("checked")){
		array.push($(this).val());
		storeIds=array.join(',');
		pobj.attr("value",storeIds);
		$('#checkCount').html(array.length-1);
	}
	
	if(!$(this).prop("checked")){
		storeIds = pobj.val();
		array.splice($.inArray($(this).val(),array),1);
		storeIds = array.join(",");
		pobj.attr("value",storeIds);
		$('#checkCount').html(array.length-1);
	}
	if($('.checkboxStore:checked').length == $('.checkboxStore').length){
		$('#checkAlls').attr("checked",true);
	}else{
		$('#checkAlls').attr("checked",false);
	}
});
//全选
$('#checkAlls').click(function(){
	storeIds = pobj.val();
	array = storeIds.split(',');
	if($(this).prop("checked")){
		$(".checkboxStore").each(function(){
			if(!$(this).prop("checked")){
   				  $(this).attr("checked",true);
   				  array.push($(this).val());
			 }
		});
	}else{
		$(".checkboxStore").each(function(){
		if($(this).attr("disabled") != "disabled"){
			  $(this).attr("checked",false);
			  array.splice($.inArray($(this).val(),array),1);
		}
		});
	}
	storeIds = array.join(",");
    pobj.attr("value",storeIds);
	$('#checkCount').html(array.length-1);
});
//翻页全选中不变
if($('.checkboxStore:checked').length == $('.checkboxStore').length && $('.checkboxStore:checked').length > 0){
		$('#checkAlls').attr("checked",true);
	}else{
		$('#checkAlls').attr("checked",false);
}
</script>

