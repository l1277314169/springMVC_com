

<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" name="clientId">
        <table class="table_white_bg">
            <tbody >
            	 <tr >
                   <td class="td_label" ><i class="cc1">*</i>角色名称：</td>
                    <td class="td_control" id="roles">
                  		<input type="text" id="roleName" name="roleName"/ >
                  		<i id="hint" class="error" style="display:none">已存在</i>
					</td>
				 </tr>
               	 <tr>
                	<td class="td_label" >角色名称(英文)：</td>
                    <td class="td_control" >
                    	<input type="text" id="roleNameEn" name="roleNameEn" >
                    </td>
                 </tr>
                 <tr>
                	<td class="td_label" >角色等级：</td>
                    <td class="td_control" >
                    	<select id="roleLevel" name="roleLevel" value="" style="width:60px">
                    		<option value="">--</option>
                    		<#list 1..10 as i>
                    			<option value="${i}">${i}</option>
                    		</#list>
                    	</select>
                    	<font color="#FF0000">(1级为最高等级)</font> 
                    </td>
                 </tr>
                 <tr>
                	<td class="td_label">Web登陆权限：</td>
                    <td class="td_control" >
                    	<input id="webLogin"  type="checkbox">
                    	<input id="webLoginText" value="0" name="webLogin" type="hidden">
                    </td>
                 </tr>
                 <tr>
                	<td class="td_label" >App登陆权限：</td>
                    <td class="td_control">
                    	<input id="appLogin" type="checkbox">
                    	<input id="appLoginText" value="0" name="appLogin" type="hidden">
                    </td>
                 </tr>
                 <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');"  class="btn btn-danger">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			  	</tr>
            </tbody>
        </table>
</form>
<script>
$(document).ready(function() {
	//权限默认选中，值为1，不选中值为0
	$("#webLogin").bind("click",function(){
		if($(this).attr('checked')){
			$("#webLoginText").val("1");
		}else{
			$("#webLoginText").val("0");
		}
	
	});
	$("#appLogin").bind("click",function(){
		if($(this).attr('checked')){
			$("#appLoginText").val("1");
		}else{
			$("#appLoginText").val("0");
		}
	
	});
	$("#roleName").bind("change",function(){
			var value=$('#roleName').val();
			$.ajax({
				url :"${contextPath}/clientRoles/validateRoleName",
				type : "post",
				data:{roleName:value},
				success:function(result){
					if(result.code =="error"){
						$("#hint").css("display","");
					}else if(result.code =="success"){
						$("#hint").css("display","none");
					}
				}
			});
		});
	//新增角色
	$("#savetButton").bind("click",function(){
		$(this).attr("disabled","disabled");
			if($("#hint").css("display")=="inline"){
				$("#savetButton").removeAttr("disabled");
				return false;
			}
			//验证
			if(!$("#dataForm").validate({
					rules : {
						roleName: {
							maxlength:50,required:true
						}
					}
			}).form()){
				$("#savetButton").removeAttr("disabled");
				return false;
			}
			$.ajax({
				url : "${contextPath}/clientRoles/addClientRole",
				type : "post",
				dataType:"json",
				data : $("#dataForm").serialize(),
				success : function(result) {
				   if(result.code=="success"){
			   			layer.alert(result.message,function(){
		   					//window.location.reload();
		   					//addDialog.close();
		   					parent.document.location.href = "${contextPath}/clientRoles/query?mod=conf";
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
	});
});
</script>

