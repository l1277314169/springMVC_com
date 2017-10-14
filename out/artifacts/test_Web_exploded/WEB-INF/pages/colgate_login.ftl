<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0053)http://colgate.alwaysmkt.com.cn/StoreRadar/Login.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
<title>Colgate In-Store  Tracking</title> 
<link href="${contextPath}/colgate2/css/buttons.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/buttonsCN.css" type="text/css" 	rel="stylesheet">
<link href="${contextPath}/colgate2/css/dashboard.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/DataEntry.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/jquery.lightbox-0.5.css"	type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/messageBox.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/overview.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/RBControls.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/report.css" type="text/css"	rel="stylesheet">
<link href="${contextPath}/colgate2/css/Site.css" type="text/css"	rel="stylesheet">

<script type="text/javascript" src="${contextPath}/js/jquery-1.7.min.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.cookie.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.validate.min.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/messages_zh.js?cVer=${cVer}"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.showLoading.min.js?cVer=${cVer}"></script>
<script language="JavaScript">
</script>
</head>
<body>

	<div class="aspNetHidden"></div>
	<div class="aspNetHidden">
		<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION"
			value="/wEdAAhskLhPS42ee6jLI/Zq8otPCsWPVvNophT/TW3PbEy8ZY5ifYKPhElOzDhB5frsn4JjatcIZvP+pdSo/hhrCFrc7ulJDAS/T7uaH7prHVSLWUjJLX3h77piRMjdgVXAMqYC636Fv2+POGWuyRgThCJByLU2gNPzhFrMwGuQJ2FjAKZ3HkKJDsJyRZ4uHOk3FIGo5mgFxgn1+jGrZIUE6OlG">
	</div>
	<div class="page">
		<div class="Content">
			<div class="loginPanel">
				<table>
					<tbody>
						<tr>
							<td>

								<div class="ctlLoginContent">
										<form id="loginForm" action="${contextPath}/login" method="post" class="form-horizontal">
									<table width="100%" border="0" cellpadding="3" cellspacing="0">
										<tbody>
											<tr>
												<td colspan="2">
													<input type="hidden" class="input-client" id="clientName" name="clientName" value="高露洁" required/>
													<input id="username" name="username" type="text"  value="${username}"
													style="height: 20px; width: 360px;" placeholder="example@always.com" class="required">
												</td>
											</tr>
											<tr>
												<td colspan="2"><span
													id="CtlLogin1_RequiredFieldValidator3"
													class="ReqValidationMsg" style="color: red; display: none;">Required</span>&nbsp;</td>
											</tr>
											<tr>
												<td colspan="2">
													<input id="password" name="password" type="password" style="height: 20px; width: 360px;" required 	placeholder="enter your password">
												</td>
											</tr>
											<tr>
												<td colspan="2"><span
													id="CtlLogin1_RequiredFieldValidator1"
													class="ReqValidationMsg" style="color: red; display: none;">Required</span>&nbsp;</td>
											</tr>
		<!--								
        <tr>
            <td>
                <img id="loginImg" src="${contextPath}/ImageServlet?key=colgate" border="0" width="150" height="25" onclick="img();">
            </td>
            <td>
                <input name="ctlLogin1_txtCaptcha" type="text" id="ctlLogin1_txtCaptcha" style="height:20px;width:200px;" placeholder="enter characters shown in image" required>
            </td>
        </tr>
       	 -->
       	
        <tr><td></td><td><span id="CtlLogin1_RequiredFieldValidator2" class="ReqValidationMsg" style="color:Red;display:none;">Required</span>&nbsp;</td></tr>
        
											<tr>
												
            <td>
				<div id="retText" style="color:#EF382B;font-size:12px;"><#if loginError ??>${loginError}</#if></div>
            </td>
            
												<td align="right" valign="top" colspan="2">
														<input type="submit"  value=""	  class="btnLogin" />
												</td>
											</tr>
											<tr>
												<td colspan="2"><span id="CtlLogin1_lblMessage"
													class="ctlLabelTextMessage"
													style="display: inline-block; color: Red; font-size: 12px; width: 350px;"></span>
												</td>
											</tr>
										</tbody>
									</table>
										</form>
									<div></div>
								</div>

							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div
				style="margin: 0px auto; width: 420px; font-size: 10px; text-align: center;">
				Copyright© 2015 Mobilizer. All Rights Reserved</div>
		</div>

	</div>
	<input type="submit" name="msg_btn" value=""
		onclick="javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions(&quot;msg_btn&quot;, &quot;&quot;, true, &quot;&quot;, &quot;&quot;, false, false))"
		id="msg_btn" style="display: none; visibility: hidden;">
	<div id="msg_pnl" class="modalPopupMessageBox"
		style="display: none; position: fixed;">
		<div class="modalMessageHeader">
			<table width="100%" border="0">
				<tbody>
					<tr>
						<td><span class="Title" id="msglblHeader"></span></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="modalMessageBody">
			<div class="modalMessageContent">
				<span id="msgltMessage"></span>
			</div>
			<div id="msg__divConfirm">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tbody>
						<tr>
							<td align="center"><input type="button" class="buttonYes"
								onclick="__YesButton_Clicked();return false;">&nbsp;<input
								type="button" class="buttonNo" onclick="__NoButton_Clicked();"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="msg__divAlert">
				<input type="button" class="buttonOk"
					onclick="__OkButton_Clicked();">
			</div>
			<div id="msg__divContinue">
				<input type="button" class="buttonContinue"
					onclick="__ContinueButton_Clicked();">
			</div>
		</div>
		<div class="modalMessageHeaderXButtonContainer" align="right"
			style="position: absolute; top: 0px; right: 0px;">
			<input class="buttonX" onclick="__XButton_Clicked();return false;"
				type="button">
		</div>
	</div>
	<input type="submit" name="lbi_btn" value=""
		onclick="javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions(&quot;lbi_btn&quot;, &quot;&quot;, true, &quot;&quot;, &quot;&quot;, false, false))"
		id="lbi_btn" style="display: none; visibility: hidden;">
	<div id="lbi_pnl" class="modalPopupIF"
		style="display: none; position: fixed;">
		<div class="modalIFHeader">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<td class="left"></td>
						<td class="middle" align="left"><div id="__ifHeaderImgTitle"
								class=""></div></td>
						<td class="middle" align="right"></td>
						<td class="right"></td>
					</tr>
				</tbody>
			</table>
		</div>
		<iframe id="__iFModal" allowtransparency="true"
			style="background-color: Transparent;" scrolling="no" frameborder="0"
			marginheight="0" marginwidth="0"></iframe>
		<div class="modalMessageHeaderXButtonContainer" align="right"
			style="position: absolute; top: 0px; right: 0px;">
			<input type="button" class="buttonX"
				onclick="__HideLB_Frame(&#39;&#39;);return false;">
		</div>
	</div>
	<input type="submit" name="lb_btn" value=""
		onclick="javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions(&quot;lb_btn&quot;, &quot;&quot;, true, &quot;&quot;, &quot;&quot;, false, false))"
		id="lb_btn" style="display: none; visibility: hidden;">
	<div id="lb_pnl" class="modalPopupLoading"
		style="display: none; position: fixed;">
		<div>
		<!--
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td align="right"><img src="${contextPath}/colgate/overView"></td>
						<td>&nbsp;</td>
						<td align="left"><span class="loading">This page is
								Loading, please wait....</span></td>
					</tr>
				</tbody>
			</table>
		-->
		</div>
	</div>
	<div id="msg_mpeMessage_backgroundElement" class="modalBackground"
		style="display: none; position: fixed; left: 0px; top: 0px;"></div>
	<div id="lbi_mpeIframe_backgroundElement" class="modalBackground"
		style="display: none; position: fixed; left: 0px; top: 0px;"></div>
	<div id="lb_mpeLoading_backgroundElement" class="modalBackground"
		style="display: none; position: fixed; left: 0px; top: 0px;"></div>
</body>


<script type="text/javascript">
function img(){
	//$("#loginImg").attr("src", "${contextPath}/ImageServlet?key=colgate&number="+Math.random());
}

$(".btnLogin").bind("click",function(){
    //验证
	if(!$("#loginForm").validate({
	onkeyup: false,
		rules : {
			username: {
				required:true
			},
			password: {
				required:true
			}/*,
			ctlLogin1_txtCaptcha:{
				required : true,
				remote:{
					url: "${contextPath}/colgate/isCode", 
				    type: "post",
				    dataType: "json",
				    async:false,            
				    data: {                
				        code: function() {
				            return $("#ctlLogin1_txtCaptcha").val();
				        },
				        key:function(){
						  return "colgate";
						}   
				   	 }
				}
			}*/
		},
		messages: {
			username: {
				required:"请输入登录名"
			},
			password: {
				required:"请输入密码"
			}/*,
			 ctlLogin1_txtCaptcha: {
                remote: "验证码有误请重新输入"
            }*/
	    },
	    errorPlacement: function(error, element) {
	    	var username = $('#username').val();
	    	var password = $('#password').val();
	    	//var ctlLogin1_txtCaptcha = $('#ctlLogin1_txtCaptcha').val();
		    if(username == ''){
		    	$("#retText").html("请输入登录名");
		    }else if(password == ''){
		    	$("#retText").html("请输入密码");
		    }/*else if(ctlLogin1_txtCaptcha == ''){
		    	$("#retText").html("请输入验证码");
		    }*/else{
		    	$("#retText").html(error);
		    }
		},
	}).form()){
		return false;
	}
});

</script>
</html>