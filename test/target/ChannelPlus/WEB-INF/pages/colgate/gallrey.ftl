<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0055)http://colgate.alwaysmkt.com.cn/StoreRadar/Gallery.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
	<link href="${contextPath}/colgate2/css/colorbox.css" rel="stylesheet" type="text/css">
	<title>Colgate In-Store  Tracking</title> 
	<script src="${contextPath}/colgate2/js/jquery-1.8.2.js" type="text/javascript"></script>
	<#include "/common/head.ftl" />
	<#include "/common/foot.ftl" /> 
	<script type="text/javascript" src="${contextPath}/js/freewall.js?cVer=${cVer}"></script>
	<script type="text/javascript" src="${contextPath}/js/layer/layer.js?cVer=${cVer}"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.imageScroller.js?cVer=${cVer}"></script>
	<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
	<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
	<script src="${contextPath}/colgate2/js/jquery.colorbox.js" type="text/javascript"></script>
	<script src="${contextPath}/colgate2/js/jquery-ui-1.9.1.custom.min.js" 	type="text/javascript"></script>
	<script type="text/javascript" src="${contextPath}/colgate2/js/Common.js"></script>
		
		<script type="text/javascript">
        var refreshClass;
        function Unrefresh() {
           
            $('#btnSearch').removeClass('btnNewRefreshLoading');
            $('#btnSearch').attr('onclick', 'return Refresh();');
        }
        function Refresh() {


            var refreshbtn = $('#btnSearch');
            refreshClass = refreshbtn.attr('class');
            refreshbtn.addClass('btnNewRefreshLoading');
            refreshbtn.attr('onclick', 'return false;');
            return true;

        }
    </script>

		<link href="${contextPath}/colgate2/css/buttons.css" type="text/css" rel="stylesheet">
			<link href="${contextPath}/colgate2/css/buttonsCN.css" type="text/css" rel="stylesheet">
				<link href="${contextPath}/colgate2/css/dashboard.css" type="text/css" rel="stylesheet">
					<link href="${contextPath}/colgate2/css/DataEntry.css" type="text/css" rel="stylesheet">
						<link href="${contextPath}/colgate2/css/jquery.lightbox-0.5.css" type="text/css"
							rel="stylesheet">
							<link href="${contextPath}/colgate2/css/messageBox.css" type="text/css"
								rel="stylesheet">
								<link href="${contextPath}/colgate2/css/overview.css" type="text/css"
									rel="stylesheet">
									<link href="${contextPath}/colgate2/css/RBControls.css" type="text/css"
										rel="stylesheet">
										<link href="${contextPath}/colgate2/css/report.css" type="text/css"
											rel="stylesheet">
											<link href="${contextPath}/colgate2/css/Site.css" type="text/css" rel="stylesheet">
</head>
<body style="background-image: none; background-color: White;"
	cz-shortcut-listen="true">
	<form method="post" action="${contextPath}/colgate/gallery/" id="searchForm">
		<div class="aspNetHidden">
			<input type="hidden" id="arg_client_id" name="arg_client_id" value="${params.arg_client_id}" />
			<input type="hidden" id="arg_start_date" name="arg_start_date" value="${params.arg_start_date}" />
			<input type="hidden" id="arg_end_date" name="arg_end_date" value="${params.arg_end_date}" />
		</div>
		<script src="${contextPath}/colgate2/css/WebResource.axd" type="text/javascript"></script>
		<script src="${contextPath}/colgate2/css/ScriptResource.axd" type="text/javascript"></script>
		<script src="${contextPath}/colgate2/css/ScriptResource(1).axd" type="text/javascript"></script>
		<script src="${contextPath}/colgate2/css/Gallery.aspx" type="text/javascript"></script>
		<div class="aspNetHidden"></div>


		<div class="headerNew">

			<div style="margin: 0px auto; width: 960px;">
					<#include "/colgate/colgateHead.ftl" />
				</div>
				<div>
					<div style="float: left;">
						<img src="${contextPath}/colgate2/Images/equity_chevron.png">
					</div>
					<div style="float: left; color: #ce1613; font-family: Rockwell, Helvetica, Arial, sans-serif; font-style: italic; font-size: 20px; margin: 15px 0px 0px 10px;">
						In-Store Tracking
					</div>

					<div style="float:right;margin:23px 0px 0px 0px;text-align:right;width:540px;">
                	<table width="100%" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td>
                            			<a targer="_self" href="${contextPath}/colgate/home/"><input type="button" id="ucHeader_btnHome" style="width:20px;" class="btnHome"></input></a>
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
                                     <input type="button" value="RAW DATA" onclick="window.location.href='${contextPath}/colgate/rawdata/'" class="NewMenuButton"></input>
                                </td>
                                
                                <@shiro.hasPermission name="C5M5">
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Gallery" onclick="window.location.href='${contextPath}/colgate/gallery/'" class="NewMenuButtonSelected"></input>
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
					<div class="NewReportPanel">
						<div class="NewPanelTop">GALLERY</div>
						<div class="NewReportPanelMid"
							style="padding-left: 20px; width: 960px;">
							<table cellpadding="3" cellspacing="0" border="0">
								<colgroup>
									<col width="10px">
										<col width="70px">
											<col width="250px">
												<col width="75px">
													<col width="250px">
														<col width="70px">
															<col width="130px">
								</colgroup>
								<tbody>
									<tr>
										<td></td>
										<td><span id="lblPeriod" class="formLabel">周期</span>*</td>
										<td>
											<input type="hidden" id="feedbackDate" name="feedbackDate" value="${params.feedbackDate}" class="input-medium" dataPosition=""/>
					  <#assign month="feedbackDate">
					  <#include "/widgets/month_widget.ftl" />
										</td>
										<td><span id="lblRegion" class="formLabel">区域</span></td>
										<td>
												  <input type="text" style="height:20px;" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
					  <input type="text" id="arg_dept_ids" name="arg_dept_ids" value="${params.arg_dept_ids}" style="display:none;">
					  <#assign structureFtlName="arg_dept_ids">
					  <#include "/widgets/structure.ftl" />
										</td>
										<td><span id="lblStoreName" class="formLabel">门店名称</span>
										</td>
										<td><input type="text" style="height:20px;" id="arg_store_name" name="arg_store_name" value="${params.arg_store_name}" class="input-medium" dataPosition=""/></td>

									</tr>
									<tr>
										<td></td>
										<td><span id="lblClientType" class="formLabel">客户类型</span>
										</td>
										<td>
											<input type="text" style="height:20px;" id="clientStructureId_chain" name="clientStructureName_chain"  class="input-medium" readonly onclick="showMenu_chain();">
					  <input type="hidden" id="arg_types" name="arg_types" value="${params.arg_types}" >
					  <#assign chainFTL="arg_types">
					  <#include "/widgets/chain_widget.ftl" />
										</td>
										<td><span id="lblProvince" class="formLabel">省份</span></td>
										<td>
											<table cellpadding="0" cellspacing="0" border="0">
												<tbody>
													<tr>
														<td>
														<input type="text" id="province_ids" name="province_ids" value="${params.province_ids}" class="input-medium" />
														</td>
														<td style="padding: 2px 0px 0px 2px;">
															<div id="progressProvince" style="display: none;"
																role="status" aria-hidden="true">

																<img id="imgLoadingProv"
																	src="${contextPath}/colgate2/Images/ajax-loader-small.gif">
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td><span id="lblStoreNo" class="formLabel">门店编号</span></td>
										<td><input type="text" style="height:20px;" id="arg_store_no" name="arg_store_no" value="${params.arg_store_no}" class="input-medium" dataPosition=""/></td>

									</tr>
									<tr>
										<td></td>
										<td><span id="lblChannel" class="formLabel">渠道类型</span></td>
										<td><input type="text" style="height:20px;" id="clientStructureId_channel" name="clientStructureName_channel"  class="input-medium" readonly onclick="showMenu_channel();" />
					  <input type="hidden" id="arg_channel_ids" name="arg_channel_ids" value="${params.arg_channel_ids}" />
					  <#assign channelFTL="arg_channel_ids">
					  <#include "/widgets/channel_widget.ftl" /></td>
										<td><span id="lblCity" class="formLabel">城市</span></td>
										<td>
											<table cellpadding="0" cellspacing="0" border="0">
												<tbody>
													<tr>
														<td><span id="upCity"><input type="text" id="city_ids" name="city_ids" value="${params.city_ids}" class="input-medium" />
					  <#include "/widgets/province_city_widget2.ftl" />
														</span></td>
														<td style="padding: 2px 0px 0px 2px;">
															<div id="progressCity" style="display: none;"
																role="status" aria-hidden="true">

																<img id="imgLoadingCity"
																	src="${contextPath}/colgate2/Images/ajax-loader-small.gif">
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td></td>
										<td></td>

									</tr>
								</tbody>
							</table>
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tbody>
									<tr>

										<td align="right" style="padding-right: 65px;">
			                                <input type="button" style="width:110px;" name="btnDownload" id="btnDownload" class="btnDownload">
			                                <input type="submit" style="width:110px;" name="searchRefresh" id="searchRefresh" class="btnRefresh" value="">
										</td>
									</tr>
								</tbody>
							</table>

						</div>
						<div class="NewReportPanelBtm"></div>
					</div>
					
					
					<div class="widget-content nopadding" id="freewall" style="margin-top:20px;">
						<#if images?exists>
						<#list images?keys as key>
							<#assign name = key?split("@")>
							<#include "/image/colgateImageLrtK.ftl" />
						</#list>
						</#if>
					</div>
					
			</div>
		</div>
	</form>
</body>

<script type="text/javascript">
	jQuery(document).ready(function() {
		//打包下载
		jQuery("#btnDownload").click(function(){
			var url ="${contextPath}/image/downloadColgatImage/";
			jQuery('#searchForm').attr("action",url);
			jQuery('#searchForm').submit();
			jQuery('#searchForm').attr("action","${contextPath}/colgate/gallery/");
		});

	});

</script>

</html>