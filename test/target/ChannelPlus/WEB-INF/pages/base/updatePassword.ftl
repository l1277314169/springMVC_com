<link rel="stylesheet" type="text/css" href="${contextPath}/css/updatePassword.css"/>
<script type="text/javascript" src="${contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.validate.expand.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.validate-methods.js"></script>
<script type="text/javascript" src="${contextPath}/js/messages_zh.js"></script>

<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="table_white_bg" >
            <tbody>
            	<tr>
                	<td class="td_label">原始密码：</td>
                    <td class="controls"><input type="password" id="oldPassword" class="input-medium"  name="oldPassword" required=true> <span id="tip2"><font color="red">密码不正确!</font></span></td>
                </tr> 
                <tr>
                	<td class="td_label">新密码：</td>
                    <td class="controls">
	                    <input type="password"   name="newPassword"  class="input-medium" id="newPassword" required=true>
                    </td>
                </tr> 
                <tr>
                	<td class="td_label">确认密码：</td>
                    <td class="controls"><input type="password" id="confirmPassword" class="input-medium"  name="confirmPassword" required=true>&nbsp;<span id="tip3"><font color="red">两次密码不一致!</font></span></td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" id="cancel" type="button" class="btn btn-danger">取消</button>
							<button  type="button" id="savetButton"  class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
$("#tip3").hide();
$("#tip2").hide();
$("#oldPassword").bind("change",function(){
                var param=$("#oldPassword").val();
	                $.ajax({
	                	type:"post",
	                    url:"${contextPath}/clientUser/checkoldpassword",
	                    data:{oldPassword : param},
	                    success:function(result){
	                       if(result){
	                       	 $("#tip2").hide();
	                       }else{
	                      	 $("#tip2").show();
	                      	 return false;
	                       }
	                    }                
	                });
           });
$('#confirmPassword').bind("change",function(){
  	var password1=$('#newPassword').val();
  	var password2=$('#confirmPassword').val();
	 if(password1 != password2){
	   	 $("#tip3").show();
	   	 return false;
	  }else{
	 	 $("#tip3").hide();
	  }
});


$("#savetButton").bind("click",function(){
	if( !$("#tip2").is(":hidden")||!$("#tip3").is(":hidden")){
		return false;
	}
	//验证
	if(!$("#dataForm").validate({
	    rules : {
				newPassword: {
					isRightfulString : true
				},
			}
	}).form()){
		return false;
	}
	var password2=$('#confirmPassword').val();
	if(confirm("操作成功!")){
		$.ajax({
			url : "${contextPath}/clientUser/updatePass",
			type : "post",
			dataType:"json",
			data: {userPassword:password2},
			success : function(result) {
				   if(result.code=="success"){
			   				updatePasswordDialog.close();
			   				window.location.href="${contextPath}/logout";
					}else {
						layer.alert(result.message);
			   		}
			   }
		});						
	}
});




</script>
