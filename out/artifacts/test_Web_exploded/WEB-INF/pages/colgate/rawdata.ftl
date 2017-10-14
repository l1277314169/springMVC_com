<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0065)http://colgate.alwaysmkt.com.cn/StoreRadar/Reports/RawReport.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Colgate In-Store  Tracking</title> 
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
<script src="${contextPath}/colgate2/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/colgate2/js/Common.js" type="text/javascript"></script>
<script type="text/javascript">

        function refreshSearchResult() {
            //__HideLB_Frame('');
            $("#btnRefresh").click();
        }

    </script>
<link href="${contextPath}/colgate2/css/buttons.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/buttonsCN.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/dashboard.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/DataEntry.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/jquery.lightbox-0.5.css" type="text/css"
	rel="stylesheet">
<link href="${contextPath}/colgate2/css/messageBox.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/overview.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/RBControls.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/report.css" type="text/css" rel="stylesheet">
<link href="${contextPath}/colgate2/css/Site.css" type="text/css" rel="stylesheet">
</head>
<body style="background-image: none; background-color: White;"
	cz-shortcut-listen="true">
	<form method="post" action="./RawData_files/RawData.html" id="form1">
		<div class="aspNetHidden">
			<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
			<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT"
				value=""> <input type="hidden" name="tm_HiddenField"
				id="tm_HiddenField"
				value=";;AjaxControlToolkit, Version=3.5.40412.0, Culture=neutral, PublicKeyToken=28f01b0e84b6d53e:zh-CN:065e08c0-e2d1-42ff-9483-e5c14441b311:5546a2b:475a4ef5:497ef277:effe2a26:a43b07eb:751cdd15:dfad98a5:1d3ed089:3cf12cf1">
			
		</div>

		<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script>


		<script src="${contextPath}/colgate2/css/WebResource.axd" type="text/javascript"></script>


		<script src="${contextPath}/colgate2/css/ScriptResource.axd" type="text/javascript"></script>
		<script type="text/javascript">
//<![CDATA[
if (typeof(Sys) === 'undefined') throw new Error('ASP.NET Ajax client-side framework failed to load.');
//]]>
</script>

		<script src="${contextPath}/colgate2/css/ScriptResource(1).axd" type="text/javascript"></script>
		<script src="${contextPath}/colgate2/css/RawReport.aspx" type="text/javascript"></script>
		<div class="aspNetHidden">
		</div>
		<script type="text/javascript">
//<![CDATA[
Sys.WebForms.PageRequestManager._initialize('tm', 'form1', [], [], [], 90, '');
//]]>
</script>


		<div class="headerNew">

			<div style="margin: 0px auto; width: 960px;">
				<div
					style="text-align: right; padding-top: 10px; padding-right: 5px;">
					<a id="ucHeader_hyDataEntry" class="headerLink"
						target="view_window" href="http://www.channelplus.cn/"
						style="color: #c00700; font-size: 10px; text-decoration: none;">Data
						Entry</a> <span id="ucHeader_dataEntrySeparator"
						style="color: #c00700;">|</span> <a id="ucHeader_hyAdmin"
						class="headerLink"
						href="#"
						style="color: #c00700; font-size: 10px; text-decoration: none;">Admin</a>
					<span id="ucHeader_adminSeparator" style="color: #c00700;">|</span>
					<!--
					<a id="ucHeader_hyChangePwd" class="headerLink"
						onclick="__ShowLB_FrameDelay(&#39;modalIFHeaderTitle&#39;,&#39;Change Password&#39;,&#39;600px&#39;,&#39;200px&#39;,&#39;/StoreRadar/ChangePassword.aspx&#39;);return false;"
						href="http://colgate.alwaysmkt.com.cn/StoreRadar/UserControl/#"
						style="color: #ce1613; font-size: 10px; text-decoration: none;">Change
						Password</a> <span style="color: #c00700;">|
						-->
						</span> 
						<a id="ucHeader_hyLogout" class="headerLink" href="${contextPath}/logout"
						style="color: #c00700; font-size: 10px; text-decoration: none;">Logout</a>

				</div>
				<div>
					<div style="float: left;">
						<img src="${contextPath}/colgate2/Images/equity_chevron.png">
					</div>
					<div
						style="float: left; color: #ce1613; font-family: Rockwell, Helvetica, Arial, sans-serif; font-style: italic; font-size: 20px; margin: 15px 0px 0px 10px;">
						In-Store Tracking</div>

					<div
						style="float: right; margin: 23px 0px 0px 0px; text-align: right; width: 540px;">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td>
                          				<a targer="_self" href="${contextPath}/colgate/home/"><input type="button" id="ucHeader_btnHome" class="btnHome"></input></a>
                      			  </td>
                        
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Overview" onclick="window.location.href='${contextPath}/colgate/overView/'" class="NewMenuButton">
                                </td>
                            
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                     <input type="button" value="Dashboard" onclick="window.location.href='${contextPath}/colgate/dashboard/'" class="NewMenuButton"></input>
                                </td>
                            
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Reports" onclick="window.location.href='${contextPath}/colgate/reports/'" class="NewMenuButton"></input>
                                </td>
                                
                                 <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                     <input type="button" value="RAW DATA" onclick="window.location.href='${contextPath}/colgate/rawdata/'" class="NewMenuButtonSelected"></input>
                                </td>
                                
                                <@shiro.hasPermission name="C5M5">
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Gallery" onclick="window.location.href='${contextPath}/colgate/gallery'" class="NewMenuButton"></input>
                                </td>
                                </@shiro.hasPermission>
                                
								</tr>
							</tbody>
						</table>

					</div>

				</div>
			</div>

		</div>
		<div class="page">


			<div class="content">
				<br>
				<div class="pageTitle">
					<div class="pageTitleLeft"></div>

					<div class="pageTitleMid">
						<span id="lblPageTitle" class="pageTitleLbl">Raw Report</span>
					</div>
					<div class="pageTitleRight"></div>
					<div class="pageTitleClear"></div>
				</div>
				<div class="pageFunction"></div>
				<div class="section">
					<div class="sectionTop">
						<div class="sectionTitle">
							<div class="sectionTitleLeft"></div>


							<div class="sectionTitleMid">
								<span id="lblTotalRecords" class="sectionTitleLbl">Total
									Records : ${dataList?size}</span>

							</div>

							<div class="sectionTitleRight"></div>
							<div
								style="float: right; margin: 5px 0px 0px 0px; padding-right: 10px;">

								&nbsp;</div>
							<div style="clear: left;"></div>
						</div>
					</div>
					<div class="sectionMid""="">

						<br>
						<div style="width: 100%; min-height: 340px;">

							<table border="0" cellpadding="0" cellspacing="0" width="99%"
								class="searchAccFilterHeader">
								<colgroup>
									<col width="12%">
									<col width="20%">
									<col width="20%">
									<col width="5%">
									<col width="43%">
								</colgroup>

								<tbody>
									<tr>
										<th><span id="lblPeriodTop" class="formLabel">Period</span>
										</th>
										<th><span id="lblReportTypeTop" class="formLabel">Report
												Type</span></th>
										<th><span id="lblDateCreatedTop" class="formLabel">Date
												Created</span></th>
										<th><span id="Label1" class="formLabel"></span></th>
										<th><span id="Label3" class="formLabel"></span></th>
										<th>&nbsp;</th>
									</tr>
									
									<#list dataList as s>
										<tr>
											<td><span id="rpRawReportEdit_lblPeriod_0">${s.periodDesc}</span></td>
											<td><span id="rpRawReportEdit_lblReportType_0">${s.dataDesc}</span></td>
											<td><span id="rpRawReportEdit_lblDateCreated_0">${s.dataCreated?string("yyyy-MM-dd HH:mm:ss")}</span></td>
											<td><input type="button" onclick="javascript:window.open('${contextPath}/uploadfiles/${clientId}/download/${s.filePath}')" name="btnEdit" id="rpRawReportEdit" class="icoSave">
											</td>
											<td><span id="rpRawReportEdit_Label5_0"></span></td>
										</tr>
									</#list>
									
								</tbody>
							</table>
							<br>

						</div>
						<br>

					</div>
					<div class="sectionBottom"></div>
				</div>
			</div>
			<br>

			<div class="footerNew">
				<div style="margin: 5px 0px 0px 20px;">
					Copyright® 2012 Always Marketing<br> All Rights Reserved. <br>
					<a
						href="http://colgate.alwaysmkt.com.cn/StoreRadar/Reports/RawReport.aspx#">Terms</a>
					&amp; <a
						href="http://colgate.alwaysmkt.com.cn/StoreRadar/Reports/RawReport.aspx#">Privacy</a>
				</div>

			</div>

		</div>

		<input type="submit" name="msg_btn" value="" id="msg_btn"
			style="display: none; visibility: hidden;">
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
		<input type="submit" name="lbi_btn" value="" id="lbi_btn"
			style="display: none; visibility: hidden;">
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
				style="background-color: Transparent;" scrolling="no"
				frameborder="0" marginheight="0" marginwidth="0"></iframe>
			<div class="modalMessageHeaderXButtonContainer" align="right"
				style="position: absolute; top: 0px; right: 0px;">
				<input type="button" class="buttonX"
					onclick="__HideLB_Frame(&#39;&#39;);return false;">
			</div>
		</div>
		<input type="submit" name="lb_btn" value="" id="lb_btn"
			style="display: none; visibility: hidden;">
		<div id="lb_pnl" class="modalPopupLoading"
			style="display: none; position: fixed;">
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td align="right"><img src="${contextPath}/colgate2/css/WebResource(1).axd"></td>
							<td>&nbsp;</td>
							<td align="left"><span class="loading">This page is
									Loading, please wait....</span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div id="msg_mpeMessage_backgroundElement" class="modalBackground"
			style="display: none; position: fixed; left: 0px; top: 0px;"></div>
		<div id="lbi_mpeIframe_backgroundElement" class="modalBackground"
			style="display: none; position: fixed; left: 0px; top: 0px;"></div>
		<div id="lb_mpeLoading_backgroundElement" class="modalBackground"
			style="display: none; position: fixed; left: 0px; top: 0px;"></div>
	</form>


</body>
</html>