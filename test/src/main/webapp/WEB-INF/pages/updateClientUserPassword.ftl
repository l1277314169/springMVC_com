<!DOCTYPE html>
<html class="body-full-height">
        <head>
		<#include "/common/taglibs.ftl" />
        <title>Channel Plus</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
        <link rel="stylesheet" type="text/css" id="theme" href="${contextPath}/css/bootstrap.min.css?cVer=${cVer}"/>
        <link rel="stylesheet" type="text/css" id="theme" href="${contextPath}/css/channel-style.css?cVer=${cVer}"/>
        <script type="text/javascript" src="${contextPath}/js/jquery-1.7.min.js?cVer=${cVer}"></script>
    </head>
    <body style="background-color:#33414e;">
        
        <div class="login_content">
			<div class="login_bg">
				<div style="padding:17px 16px 17px 20px;">
					<div class="fl" style="margin-left:50px;width:500px;">
						<form id="passwordForm" action="" method="post" class="form-horizontal" style="padding-left:200px;text-align:left;width:100%">
						<div style="margin-top:25px;margin-bottom:5px;height:45px;line-height:45px;a"><span class="margin-left-10">第一次登录，请修改您的初始密码！</span></div>
							<div class="login-form-group">
									<input type="password" class="input-passwd" maxlength="18" placeholder="原密码(4-18位)" id="oldPassword" name="oldPassword" value="" required=true/><div style="display: inline" id="tipOld"></div>
							</div>
							<div class="login-form-group">
									<input type="password" class="input-passwd" maxlength="18" placeholder="新密码(4-18位)" id="newPassword" name="newPassword" value="" required=true/><div style="display: inline" id="tipNew"></div>
							</div>
							<div class="login-form-group">
									<input type="password" class="input-passwd" maxlength="18" placeholder="确认新密码(4-18位)" id="rePassword" name="rePassword" value="" required=true/><div style="display: inline" id="tipRe"></div>
							</div>
							<div class="login-form-group">
									<button id="updatePassword" class="btn-login"> 确 认 修 改 </button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="login_foot">
				Copyright &copy; 2014-2015 By Mobilizer Information Technology Co., Ltd.
			</div>
        </div>
    </body>
</html>
<script>
$(window).bind('beforeunload',function(){ 
	$.ajax({
		url:"${contextPath}/ajaxLogout",
		success:function(e){
			console.log("已登出！");
		}
	});
}); 

$(document).ready(function(){
	$('#oldPassword').change(function(){
		var oldPassword = $('#oldPassword').val();
		if(oldPassword == ""){
			$("#tipOld").html("<span style='font-size:12px;color:red'>  不能为空</span>");
			return false;
		}
		var newPassword = $('#newPassword').val();
		if(newPassword !="" && newPassword == oldPassword) {
			$("#tipNew").html("<span style='font-size:12px;color:red'>  新密码不能跟原密码一样</span>");
			return false;
		}
		//alert(oldPassword);
		$.ajax({
			url:"${contextPath}/clientUser/checkoldpassword",
			data:{oldPassword:oldPassword},
			success:function(e){
				if(e){
					$("#tipOld").html("<span style='font-size:12px;color:green'>正确</span>");
				} else{
					$("#tipOld").html("<span style='font-size:12px;color:red'>错误</span>");
				}
			}
		});
	});
	
	$('#newPassword').keyup(function(){
		var oldPassword = $('#oldPassword').val();
		var newPassword = $('#newPassword').val();
		var rePassword = $('#rePassword').val();
		var newPasswordLength = $('#newPassword').val().length;
		
		if(oldPassword != "" && newPassword == oldPassword){
			$("#tipNew").html("<span style='font-size:12px;color:red'>新密码不能跟原密码一样</span>");
			return false;
		}
		if(newPasswordLength < 4){
			$("#tipNew").html("<span style='font-size:12px;color:red'>密码不能小于4位</span>");
			return false;
		} else if(newPasswordLength > 18){
			$("#tipNew").html("<span style='font-size:12px;color:red'>密码不能大于18位</span>");
			return false;
		} else if(!isHf(newPassword)){
			$("#tipNew").html("<span style='font-size:12px;color:red'>只能包含字符、数字和下划线</span>");
			return false;
		} else {
			$("#tipNew").html("<span style='font-size:12px;color:green'>正确</span>");
		}
		if(rePassword != "" && newPassword != rePassword){
			$("#tipRe").html("<span style='font-size:12px;color:red'>两次密码不一致</span>");
			return false;
		} 
		$("#tipNew").html("<span style='font-size:12px;color:green'>正确</span>");
		checkeRePassword();
	});

	$('#rePassword').keyup(function(){
		var newPassword = $('#newPassword').val();
		var rePassword = $('#rePassword').val();
		var rePasswordLength = $('#rePassword').val().length;
		if(rePasswordLength > 0 && rePasswordLength < 4){
			$("#tipRe").html("<span style='font-size:12px;color:red'>密码不能小于4位</span>");
			return false;
		} else if(rePasswordLength > 18){
			$("#tipRe").html("<span style='font-size:12px;color:red'>密码不能大于18位</span>");
			return false;
		} else if(newPassword != null && rePassword != newPassword){
			$("#tipRe").html("<span style='font-size:12px;color:red'>两次密码不一致</span>");
			return false;
		} else if(!isHf(rePassword)){
			$("#tipRe").html("<span style='font-size:12px;color:red'>只能包含字符、数字和下划线</span>");
			return false;
		}
		$("#tipRe").html("<span style='font-size:12px;color:green'>正确</span>");
	});
	
	function checkeRePassword(){
		var newPassword = $('#newPassword').val();
		var rePassword = $('#rePassword').val();
		var rePasswordLength = $('#rePassword').val().length;
		if(rePasswordLength > 0 && rePasswordLength < 4){
			$("#tipRe").html("<span style='font-size:12px;color:red'>密码不能小于4位</span>");
			return false;
		} else if(rePasswordLength > 18){
			$("#tipRe").html("<span style='font-size:12px;color:red'>密码不能大于18位</span>");
			return false;
		} else if(newPassword != null && rePassword != newPassword){
			$("#tipRe").html("<span style='font-size:12px;color:red'>两次密码不一致</span>");
			return false;
		} else {
		}
		$("#tipRe").html("<span style='font-size:12px;color:green'>正确</span>");
	}
	
	function isHf(value){
		var reg=/\w*/gi;
		if (reg.test(value)) {
			return true;
		} 
		return false;
	}
	
	
	
	$('#updatePassword').click(function(){
		var oldPassword = $('#oldPassword').val();
		var newPassword = $('#newPassword').val();
		var rePassword = $('#rePassword').val();
		if(oldPassword == null || oldPassword == "" ){
			$("#tipOld").html("<span style='font-size:12px;color:red'>不能为空</span>");
			return false;
		}
		
		if(newPassword == null || newPassword == "" ){
			$("#tipNew").html("<span style='font-size:12px;color:red'>不能为空</span>");
			return false;
		}
		
		if(rePassword == null || rePassword == "" ){
			$("#tipRe").html("<span style='font-size:12px;color:red'>不能为空</span>");
			return false;
		}
		
		if($("#tipOld > span").html() == "正确" && $("#tipNew > span").html() == "正确" && $("#tipRe > span").html() == "正确"){
		} else {
			return false;
		}
		$.ajax({
		url : "${contextPath}/clientUser/updatePassword",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#passwordForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
		   			window.location.href= "${contextPath}/welcome";
			}else {
				layer.alert(result.message);
	   		}
		   }
		});	
	});
});
</script>