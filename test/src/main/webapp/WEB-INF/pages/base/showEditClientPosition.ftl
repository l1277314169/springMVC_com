
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
<form id="dataForm" method="post">
		<input type="hidden" name="clientPositionId" value="${clientPosition.clientPositionId!''}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label_d"><i style="color: red;">*</i>职位名称：</td>
                    <td class="td_control">
                    	<input type="text" name="positionName" value="${clientPosition.positionName!''}" required=true>
                    	<font id="msg" color="red"></font>
                    </td>
                </tr> 
                <tr>
                	<td class="td_label_d">职位编号：</td>
                    <td class="td_control">
                    	<input type="text" name="positionNo" value="${clientPosition.positionNo!''}">
                    </td>
                </tr>
                <tr>
                	<td class="td_label_d">职位名称(英文)：</td>
                    <td class="td_control">
                    	<input type="text" name="positionNameEn" value="${clientPosition.positionNameEn!''}">
                    </td>
                </tr> 
                <tr id="parentId">
                	<td class="td_label_d">上级：
                    <td class="td_control">
                    	<select name="parentId"  value="${clientPosition.positionName!''}">
	                    	<option value="">--请选择--</option>
	                    	<#list optionlist as option>
	                    	<#if clientPosition.parentId == option.clientPositionId>
	                    		   	 	<option value="${option.clientPositionId!''}" selected="selected">${option.positionName!''}</option>
	                    		    <#else>
	                    		    	<option value="${option.clientPositionId!''}" >${option.positionName!''}</option>
	                    		    </#if>
	                    	</#list>
                    	</select>
                    </td>
                </tr> 
                <tr id="sysPositionId">
                	<td class="p_label">系统职位<td>
                    <td>
                    	<select name="sysPosition" value="${clientPosition.sysPosition!''}" style="width:145;">
                    	        <option value="">--请选择-</option>
	                			<option value="0" <#if clientPosition.sysPosition == 0 > selected="selected" </#if>>管理人员</option>
	                			<option value="1" <#if clientPosition.sysPosition == 1 > selected="selected" </#if>>销售代表</option>
	                			<option value="2" <#if clientPosition.sysPosition == 2 > selected="selected" </#if>>促销人员</option>
	                			<option value="3" <#if clientPosition.sysPosition == 3 > selected="selected" </#if>>送货人员</option>
                    	</select>
                    </td>
                </tr>
				<tr>
                    <td class="td_label_d">备注：</td>
                    <td  class="td_control_d">
                    	<textarea rows=2  maxlength=200 name="remark" placeholder="不超过200个字">${clientPosition.remark!''}</textarea>
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



//编辑职位
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
		url : "${contextPath}/clientPosition/verifyUserNameForUpdate",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
	   				$.ajax({
						url : "${contextPath}/clientPosition/updateClientPosition",
						type : "post",
						dataType:"json",
						async: false,
						data : $("#dataForm").serialize(),
						success : function(result) {
						   if(result.code=="success"){
						   		layer.alert(result.message,function(){
						   				// window.location.reload();
						   				// editDialog.close();
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
					url : "${contextPath}/clientPosition/verifyUserNameForUpdate",
					type : "post",
					dataType:"json",
					async: false,
					data : $("#dataForm").serialize(),
					success : function(result) {
					   if(result.code!="success"){
				   			//layer.alert(result);
				   			jQuery("#msg").html(result.message);
						}else{
							jQuery("#msg").html("");
						}
					   }
					});	
});
$('#sysPositionId,#parentId').hide();
</script>
