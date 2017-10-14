<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList">
	<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th><input type="checkbox" id="checkAll" name="checkAll"></th>
						<th>姓名</th>
						<th>用户名</th>
						<th>部门</th>
						<th>职位</th>
						<th>直属上级</th>
					    <th>在职状态</th>
					    <th>账号状态</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientUser>
							<tr>
								<td><input name="chkItem" type="checkbox" class="checkboxStore" value="${clientUser.clientUserId!''}"/></td>
								<td>${clientUser.name!''}</td>
								<td>${clientUser.loginName!''}</td>
								<td>${clientUser.structureName!''}</td>
								<td>${clientUser.positionName!''}</td>
								<td>${clientUser.parentName!''}</td>
								<td><#if clientUser.status = 1>在职<#elseif clientUser.status = 0>离职</#if></td>
								<td><#if clientUser.isActivation = 1>启用<#elseif clientUser.isActivation = 0>禁用</#if></td>
							</tr>
						</#list>
					</tbody>
				</table>
			<div class="paging">	
    			<@pagination.paging pageParam true "#logResultList"/>
			</div>
			<input type="hidden" id="count" value="${count}">
			<input type="hidden" id="oldClientUserIds" value="${replaceAlls}">
</div>
<script>
//全选              
var array;
var clientUserId;
$("#checkAll").bind("click",function(){
//取消所有人员的时候再次得到里面的值
array = $('#hiddenClientUserId').val().split(',');
	if($(this).attr('checked')){
		 $("[name = chkItem]:checkbox").each(function(){
			 if(!$(this).prop("checked")){
   				  $(this).attr("checked",true);
   				  array.push($(this).val());
   				  $('#checkCount').html(array.length-1);
			 }
		 });
			clientUserId = array.join(",");
	}else{
		 $("[name = chkItem]:checkbox").each(function(){
			  $(this).attr("checked",false);
			  array.splice($.inArray($(this).val(),array),1);
			  $('#checkCount').html(array.length-1);
 		}); 
 			clientUserId = array.join(","); 
	}
	$("#hiddenClientUserId").val(clientUserId);

});
//单选
$("[name = chkItem]:checkbox").bind("click",function(){
array = $('#hiddenClientUserId').val().split(',');
		if($(this).attr('checked')){
			$(this).attr("checked",true);
			array.push($(this).val());
			$('#checkCount').html(array.length-1);
			clientUserId = array.join(",");
		}else{
			$(this).attr("checked",false);
			array.splice($.inArray($(this).val(),array),1);
			$('#checkCount').html(array.length-1);
			clientUserId = array.join(",");
		}
	$("#hiddenClientUserId").val(clientUserId);
	if($('.checkboxStore:checked').length == $('.checkboxStore').length){
		$('#checkAll').attr("checked",true);
	}else{
		$('#checkAll').attr("checked",false);
		$('#clientUserCheckBox').attr("checked",false);
	}
});
	
	//翻页选中
var array = $('#hiddenClientUserId').val().split(",");
$("#checkCount").html(array.length-1);
 $("[name = chkItem]:checkbox").each(function () {
    	for (var index in array) {
            if ($(this).val() == array[index]){
                $(this).attr("checked", "checked");
                break;
            }
        }
});
//如果全选中翻页也就选中
if($('.checkboxStore:checked').length == $('.checkboxStore').length && $('.checkboxStore:checked').length > 0){
		$('#checkAll').attr("checked",true);
	}else{
		$('#checkAll').attr("checked",false);
}
	
//所有人员
$('#clientUserCheckBox').removeAttr("checked");
$('#clientUserCheckBox').die().live("click",function(){
	if($(this).attr("checked")){
		$('.checkboxStore').attr("checked",true);
		$('#checkAll').attr("checked",true);
		//所有人员替换
		$.ajax({
			url : "${contextPath}/promotions/replaceAlls",
			type : "post",
			dataType:"json",
			data : $("#dataForm").serialize(),
			success : function(result) {
				//$('#hiddenClientUserId').val(result);
					var newResult = compareArray(result);
					var clientUserIds = $('#hiddenClientUserId').val()+newResult;
					$('#hiddenClientUserId').val(clientUserIds);
					var checkboxId = $('#hiddenClientUserId').val().split(",");
					$('#checkCount').html(checkboxId.length-1);
			}
		});
	}else{
		var oldClientUserIds = $('#oldClientUserIds').val().split(",");
		var array = $('#hiddenClientUserId').val().split(",");
		for(var i in oldClientUserIds){
			if($.inArray(oldClientUserIds[i],array) != -1){
				array.splice($.inArray(oldClientUserIds[i],array),1);
			}
		}
		$('#checkCount').html(array.length-1);
		$('.checkboxStore').attr("checked",false);
		$('#checkAll').attr("checked",false);
		$('#hiddenClientUserId').val(array.toString());
		$('#clientUserCheckBox').removeAttr("checked");
	}
});
	
function compareArray(result){
	var array = $('#hiddenClientUserId').val().split(",");
	var arrayResult = result.split(",");
	var newResult = "";
	for(var index in arrayResult){
		if($.inArray(arrayResult[index],array) == -1){
			newResult+=arrayResult[index]+",";
		}
	}
	return newResult;
}
</script>
