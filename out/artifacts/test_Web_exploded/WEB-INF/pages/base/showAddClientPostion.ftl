
 <#include "/common/head.ftl" />
 <#include "/common/foot.ftl" /> 
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label_d"><i style="color: red;">*</i>职位名称：</td>
                    <td class="td_control">
                    	<input type="text" name="positionName" required=true>
                    	<font id="msg" color="red"></font>
                    </td>
                </tr> 
                <tr>
                	<td class="td_label_d">职位编号：</td>
                    <td class="td_control">
                    	<input type="text" name="positionNo">
                    </td>
                </tr>
                <tr>
                	<td class="td_label_d">职位名称(英文)：</td>
                    <td class="td_control">
                    	<input type="text" name="positionNameEn">
                    </td>
                </tr> 
                  <tr id="parentId">
                	<td class="td_label_d">上级：</td>
                    <td class="td_control">
                    	<select name="parentId" >
	                    	<option value="">--请选择--</option>
	                    	<#list list as option>
	                    		<option value="${option.clientPositionId!''}">${option.positionName!''}</option>
	                    	</#list>		
                    	</select>
                    </td>
                </tr> 
                <tr id="sysPositionId">
                	<td class="td_label_d">系统职位<td>
                    <td class="td_control">
                    	<select name="sysPosition" >
	                    	<option value="">--请选择-</option>
	                    	<#list optionsList as option>
	                    		<option value="${option.optionValue!''}">${option.optionText!''}</option>
	                    	</#list>		
                    	</select>
                    </td>
                </tr>
				<tr>
                    <td class="td_label_d">备注：</td>
                    <td  class="td_control_d">
                    	<textarea  name="remark" placeholder="不超过200个字"></textarea>
                    </td>
                </tr>
                 <tr>
					<td colspan="4" class="td_buttons">
							 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   	</tr>
            </tbody>
        </table>
</form>
<script>
//新增职位
$("#savetButton").bind("click",function(){
	$(this).attr("disabled","disabled");
	//验证
	if(!$("#dataForm").validate({
	   rules : {
				positionName: {
					maxlength: 50,
				},
				positionNameEn: {
					maxlength: 50,
				},
				remark: {
					maxlength: 200
				},
			}
	}).form()){
		$("#savetButton").removeAttr("disabled");
		return;
	}
	$.ajax({
		url : "${contextPath}/clientPosition/verifyUserName",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
		     	$.ajax({
					url : "${contextPath}/clientPosition/addClientPosition",
					type : "post",
					dataType:"json",
					async: false,
					data : $("#dataForm").serialize(),
					success : function(result) {
					   if(result.code=="success"){
					     	layer.alert(result.message,function(){
				   				// window.location.reload();
				   				// addDialog.close();
				   				parent.document.location.href = "${contextPath}/clientPosition/query/";
		   						parent.layer.closeAll('iframe');
				   			});
						}else {
							layer.alert(result.message);
							$("#savetButton").removeAttr("disabled");
				   		}
					   },
					   error:function(){
					   		$("#savetButton").removeAttr("disabled");
					   }
					});	
		     	
			}else {
				layer.alert(result.message);
				$("#savetButton").removeAttr("disabled");
	   		}
		   }
	});
});

$("input[name='positionName']").bind("blur",function(){
 				$.ajax({
					url : "${contextPath}/clientPosition/verifyUserName",
					type : "post",
					dataType:"json",
					async: false,
					data : $("#dataForm").serialize(),
					success : function(result) {
					   if(result.code!="success"){
				   			jQuery("#msg").html(result.message);
						}else{
							jQuery("#msg").html("");
						}
					   }
					});	
});

$('#sysPositionId,#parentId').hide();
</script>
