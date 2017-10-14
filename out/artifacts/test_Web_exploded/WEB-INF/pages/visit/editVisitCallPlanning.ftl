<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList">
		<table id="storeData" class="table table-bordered data-table">
			<thead>
				<th style="width:40px;"><input type="checkbox" id="checkAlls" name="checkAlls" <#if toDate == forematCallDate>disabled="disabled"</#if>style="margin-left:-28px;"></th>
				<th>门店编号</th>
				<th>门店名称</th>
				<th>地址</th>
				<th>拜访状态</th>
				<th>操作</th>
			</thead>
			<tbody>
			<#list pageParam.items as store>
			<tr>
				<td>
					<input name="chkItem" type="checkbox" class="checkboxStore" <#if toDate == forematCallDate>disabled="disabled"</#if> value="${store.storeId!''}"/>
				</td>
				<td>${store.storeNo!''}</td>
				<td title="${store.storeName!''}">
					<#if store.storeName?? && store.storeName?length gt 12>
						${store.storeName?substring(0,12)}...
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
				<td>
					<#if store.visitYesOrNot?? && toDate == forematCallDate && store.callStatus == 0>
						<a href="javascript:void(0);" class="cancelCallPlanning" dataId="${store.storeId!''}">取消拜访</a>
					<#else>
						
					</#if>
				</td>
			</tr>
			</#list>
			<tbody>
	 	</table>
		 <div class="paging">	
	    	<@pagination.paging pageParam true "#logResultList"/>
		</div>
 	</div>
<script type="text/javascript" language="javascript">
$(function(){
	//取消
	$('.btn-danger').click(function(){
		parent.editDialog.close();
	});
	//要拜访的门店信息
	
	//初始化选中框
	var pobj = $("#storeParameterDatas");
	var storeIds = pobj.val();
	var array = storeIds.split(',');
   $('#checkCount').html(array.length-1);
    $(".checkboxStore").each(function () {
    	for (var index in array) {
            if ($(this).val() == array[index]) {
                $(this).attr("checked", "checked");
                break;
            }
        }
 	});
		
//取消拜访计划
$(".cancelCallPlanning").click(function(){
	var dataId=$(this).attr("dataId");
	var visitType = $("#visitType").val();
	var clientUserId = $("#clientUserId").val();
	var callDate = $("#callDate").val();
	$.ajax({
		url : "${contextPath}/callPlanning/cancelCallPlanning",
		type : "post",
		dataType:"json",
		data : {storeId:dataId,clientUserId:clientUserId,
		visitType:visitType,callDate:callDate},
		success : function(result){
			if(result.code=="success"){
				layer.alert(result.message,function(){
	   				window.location.reload();
				});
			}else{
				layer.alert(result.message);
			}
		}
	});
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
	//如果单选全部选中 全选就选中
	if($('.checkboxStore:checked').length == $('.checkboxStore').length){
		$('#checkAlls').attr("checked",true);
	}else{
		$('#checkAlls').attr("checked",false);
	}
});
//翻页全选中不变
if($('.checkboxStore:checked').length == $('.checkboxStore').length && $('.checkboxStore:checked').length > 0){
	$('#checkAlls').attr("checked",true);
}else{
	$('#checkAlls').attr("checked",false);
}
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
			  $(this).attr("checked",false);
			  array.splice($.inArray($(this).val(),array),1);
		});
	}
	storeIds = array.join(",");
    pobj.attr("value",storeIds);
	$('#checkCount').html(array.length-1);
});
});
</script>