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
        <link rel="stylesheet" type="text/css" href="${contextPath}/css/showLoading.css?cVer=${cVer}">
        <script type="text/javascript" src="${contextPath}/js/jquery-1.7.min.js?cVer=${cVer}"></script>
        <script type="text/javascript" src="${contextPath}/js/jquery.cookie.js?cVer=${cVer}"></script>
        <script type="text/javascript" src="${contextPath}/js/jquery.validate.min.js?cVer=${cVer}"></script>
        <script type="text/javascript" src="${contextPath}/js/jquery.showLoading.min.js?cVer=${cVer}"></script>
		<script language="JavaScript">
			if (window != top) {
				top.location.href = "${contextPath}/welcome";
			}
			<@shiro.user>
				top.location.href = "${contextPath}/welcome";
			</@shiro.user>
		</script>
    </head>
    <body style="background-color:#33414e;">
        
        <div class="login_content">
			<div class="login_bg">
				<div style="padding:17px 16px 17px 20px;">
					<div class="fl"><img src="${contextPath}/img/placeholder<#if clientTag =="loreal">_oly</#if>.png" height="426" width="494"><!-- 此处的图片可更换 --></div>
					<div class="fl" style="margin-left:20px;width:280px;">
						<!-- div style="margin-top:20px;"><img src="${contextPath}/img/logo.png"></div>
						<div class="login_logo_title">Channel Plus</div -->
						<div style="margin-top:25px;margin-bottom:5px;height:45px;line-height:45px;vertical-align:middle;"><img src="${contextPath}/img/little-logo.png" height="41" style="vertical-align:middle;margin-top:-5px;margin-bottom:5px;"><span class="margin-left-10 login_logo_title">Channel Plus</span></div>
						<form id="loginForm" action="${contextPath}/login" method="post" class="form-horizontal">
							<div style="font-size:12px;color:red;height:20px;">
								<#if loginError ??>
										${loginError}
								</#if>
							</div>
							<div class="login-form-group">
									<input type="text" class="input-client" placeholder="客户名称" id="clientName" name="clientName" value="${clientName}" required/>
							</div>
							<div class="login-form-group">
									<input type="text" class="input-uname" placeholder="账号" id="username" name="username" value="${username}" required/>
							</div>
							<div class="login-form-group">
									<input type="password" class="input-passwd" placeholder="密码" id="password" name="password" value="" required/>
							</div>
							<div class="login-form-group">
									<button class="btn-login" onclick="saveUserInfo()"> 登 录 </button>
							</div>
							<div class="login-form-remember">
								<input type="checkbox" id="rmbUser"><label for="rmbUser" style="font-size:12px">记住用户名</label>
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
$(document).ready(function(){
	<#if clientNameError ??>
		$("#clientName").select();
	<#elseif userNameOrPassWordError??>
		$("#username").select();
	</#if>
	
	if ($.cookie("rmbUser") == "true") {
		$("#rmbUser").prop("checked", true);
		$("#clientName").val($.cookie("clientName"));
		$("#username").val($.cookie("username"));
		$("#password").val($.cookie("password"));
	}
});

function saveUserInfo() {
	if ($("#rmbUser").prop("checked") == true) {
		var clientName = $("#clientName").val();
		var username = $("#username").val();
		var password = $("#password").val();
		$.cookie("rmbUser", "true", { expires: 366 }); // 存储一个带7天期限的 cookie
		$.cookie("clientName", clientName, { expires: 366 }); // 存储一个带7天期限的 cookie
		$.cookie("username", username, { expires: 366 }); // 存储一个带7天期限的 cookie
		$.cookie("password", password, { expires: 366 }); // 存储一个带7天期限的 cookie
	}
	else {
		$.cookie("rmbUser", "false", { expires: -1 }); // 删除 cookie
		$.cookie("clientName", '', { expires: -1 });
		$.cookie("username", '', { expires: -1 });
		$.cookie("password", '', { expires: -1 });
    }
    
    //验证
	if(!$("#loginForm").validate({
		errorPlacement: function(error, element) {},
		rules : {
			clientName: {
				required:true
			},
			username: {
				required:true
			},
			password: {
				required:true
			}
		}
	}).form()){
		return false;
	}
	
	jQuery("body").hideLoading();
}
</script>