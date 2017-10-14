<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<#include "/common/taglibs.ftl" />
    <style type="text/css">
        .HdrColTrunc300
        {
            min-width: 300px;
            max-width: 300px;
	        width: 300px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }           
        .HdrColTrunc200
        {
            min-width: 200px;
            max-width: 200px;
	        width: 200px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }           
        .HdrColTrunc150
        {
            min-width: 150px;
            max-width: 150px;
	        width: 150px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }                   
        .HdrColTrunc120 
        {
            min-width: 120px;
            max-width: 120px;
	        width: 120px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }           
        .HdrColTrunc100 
        {
            min-width: 100px;
            max-width: 100px;
	        width: 100px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }           
        .HdrColTrunc80
        {
            min-width: 80px;
            max-width: 80px;
	        width: 80px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }           
        .HdrColTrunc60
        {
            min-width: 60px;
            max-width: 60px;
	        width: 60px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }           
        .HdrColTrunc40
        {
            min-width: 40px;
            max-width: 40px;
	        width: 40px;
	        white-space: nowrap;
	        overflow: hidden;
	        text-overflow: ellipsis;
	        background-color: #E6DFDF;
	        font-weight:bold;
        }          
        .DataCol300
        {
            min-width: 300px;
            max-width: 300px;
	        width: 300px;
	        white-space: nowrap;
	        text-align:center;
        }           
        .DataCol200
        {
            min-width: 200px;
            max-width: 200px;
	        width: 200px;
	        white-space: nowrap;
	        text-align:center;
        }           
        .DataCol150
        {
            min-width: 150px;
            max-width: 150px;
	        width: 150px;
	        white-space: nowrap;
	        text-align:center;
        }                   
        .DataCol120
        {
            min-width: 120px;
            max-width: 120px;
	        width: 120px;
	        white-space: nowrap;
	        text-align:center;
        }           
        .DataCol100 
        {
            min-width: 100px;
            max-width: 100px;
	        width: 100px;
	        white-space: nowrap;
	        text-align:center;
        }           
        .DataCol80
        {
            min-width: 80px;
            max-width: 80px;
	        width: 80px;
	        white-space: nowrap;
	        text-align:center;
        }           
        .DataCol60
        {
            min-width: 60px;
            max-width: 60px;
	        width: 60px;
	        white-space: nowrap;
	        text-align:center;
        }           
        .DataCol40
        {
            min-width: 40px;
            max-width: 40px;
	        width: 40px;
	        white-space: nowrap;
	        text-align:center;
        }   
        
       #rbCategory label, #rbBrand label, #cbChannel label,#cbRegion label
       {
   	    margin-right:15px;
   	    text-align:left;
       }
       body {
	    background-color: #ffffff;
        font-family: Tahoma, Arial, sans serif;
        }
        .fakeContainer { /* The parent container */
            margin: 20px;
            padding: 0px;
            border: none;
            width:900px;
            overflow: hidden; /* Required to set */
            background-color: transparent;
        }
        .rpReportMenuItem a img
        {
    	    border:none;
    	    text-decoration:none;   
        }
        #clientStructureId_structure{
        	height:20px;
        }
   </style>
    <title>

</title>
    <script type="text/javascript" src="${contextPath}/apple2/js/jquery-1.7.2.min.js"></script>
    <script src="${contextPath}/apple2/js/jquery-ui-1.9.1.custom.min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jquery.scrollTo-min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/apple2/js/Common.js"></script>
    <script src="${contextPath}/apple2/js/superTables.js" type="text/javascript"></script>
    <link href="${contextPath}/apple2/css/superTables.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/apple2/js/report.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.ztree.core-3.5.min.js?cVer=${cVer}"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.ztree.exedit-3.5.min.js?cVer=${cVer}"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/zTreeStyle/zTreeStyle.css?cVer=${cVer}">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/channel-style.css?cVer=${cVer}">
	<script type="text/javascript" src="${contextPath}/js/jquery.showLoading.min.js?cVer=${cVer}"></script>
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/showLoading.css?cVer=${cVer}">
	<script type="text/javascript" src="${contextPath}/js/layer/layer.js?cVer=${cVer}"></script>
    
    <script type="text/javascript">
        $(document).ready(function () {
            $("#searchRefresh").click(function(){
            	$("#searchForm").submit();
        	});
        	
        	
        	$("#btnDownload").click(function(){
            	exportExcel();
        	});
        	
        	function exportExcel(){
        		var url ="${contextPath}/reportExport/execute/1";
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/report/queryApple/${baseReport.reportId}");
			}
      });
    </script>
	<link href="${contextPath}/apple2/css/buttons.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/buttonsCN.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/dashboard.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/DataEntry.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/jquery.lightbox-0.5.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/messageBox.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/overview.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/RBControls.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/report.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/apple2/css/Site.css" type="text/css" rel="stylesheet">
</head>
<body style="background-image:none;background-color:White;">
<form id="searchForm" name="searchForm" action="${contextPath}/report/queryApple/${baseReport.reportId}" method="POST" >
<div class="aspNetHidden">
</div>
<div class="headerNew">
   <div style="margin:0px auto;width:960px;">
       		<#assign currentMenu="2">
        	<#include "/apple/appleHead.ftl" />
        </div>
   </div>
</div>
    <div class="page">
        
        <div class="content">
           
            <div class="mask">
                <div class="longContainer">
                    <div class="reportFilter">
                         <div>
                            <div style="float:left;">
                                <span id="lblReportFilterTitle" class="ReportTitle">${baseReport.reportName}</span>
                            </div>
                            <div style="float:right;">
                                <a targer="_self" href="${contextPath}/apple/appleReports">
                                <input type="button" name="btnFilterBackToMenu" id="btnFilterBackToMenu" class="btnMenu"></input></a>
                               </div>
                            <div style="clear:both;">&nbsp;</div>
                        </div>
                        <div id="filterContent" style="display: block;">
                            <input type="hidden" name="hfReportId" id="hfReportId" value="4003">
                            <input type="hidden" name="hfReportName" id="hfReportName" value="Share of Shelf">
                             <div id="GroupingTitle">
                                <div class="sectionTitle">
                                    <!--<div class="sectionTitleLeft"></div>
                                   <div class="sectionTitleMid">
                                        <span id="lblRowLabel" class="sectionTitleLbl" style="display:inline-block;font-weight:bold;width:90px;">ROW LABEL</span>
                                    </div>
                                    <div class="sectionTitleRight"></div>-->
                                    <div style="clear:left;">&nbsp;</div>
                                </div>
                            </div>
                            
                            <input type="hidden" id="reportId" name="reportId" value="${baseReport.reportId}" />
                            
                            <div class="filterPanel" style="margin-top:-10px;" id="Grouping">
                                <table border="0" cellpadding="5" cellspacing="0" width="915">
                                    <tbody>
                                    	<#assign i=0 />
                                        <#list baseReport.reportFilterList as fls>
                                        	<#if fls.type == 4 || fls.type == 18 || fls.type == 19 || fls.type == 20 || fls.type == 21 || fls.type == 22 || fls.type == 23 || fls.type == 26 || fls.type == 27 || fls.type == 30 || fls.type == 50 || fls.type == 52 || fls.type == 53 || fls.type == 57>
                                        	<#else>
	                                        	<#if i==0 || i%3==0>
	                                        		<tr>
	                                        	</#if>
	                                        	<td style="height:30px;">
	                                            	<span id="lblLvl1Grouping" class="formLabel">${fls.label}</span>
	                                        	</td>
	                                        	<td style="height:30px;">
	                                        	<#if fls.type == 58>
	                                        		<#include "/report/widget/arg_enum_widget2.ftl" />
	                                        	<#elseif fls.type == 75 >
													<#include "/report/widget/arg_year2.ftl" />
												<#elseif fls.type == 76 >
													<#include "/report/widget/arg_province2.ftl" />
												<#elseif fls.type == 9>
	                                        		<#include "/report/widget/arg_channel_id_widget2.ftl" />
	                                        	<#elseif fls.type == 1>
	                                        		<#include "/report/widget/arg_structure_ids_widget.ftl" />
	                                        	<#elseif fls.type == 83>
	                                        		<#include "/report/widget/arg_am.ftl" />
	                                        	<#elseif fls.type == 84>
	                                        		<#include "/report/widget/arg_rm.ftl" />
	                                        	<#else>
	                                        		<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" />
	                                        	</#if>
	                                        	</td>
	                                        	<#assign i=i+1 />
	                                        	<#if i%3==0>
	                                        		</tr>
	                                        	</#if>
                                        	</#if>
                                        </#list>
                                </tbody>
                               	</table>
                            </div>
                           
                            <div style="text-align:right;">
                                <input type="button" name="btnDownload" id="btnDownload" class="btnDownload">
                                <input type="button" name="searchRefresh" id="searchRefresh" class="btnRefresh">
                            </div>
                        </div>
                        <div id="filterLoading" style="text-align: center; display: none;">
                            <img alt="loading" src="${contextPath}/apple2/Images/loader.gif">
                        </div>
                    
                </div>
                <div class="report_me">
                    <div>
                        <div style="float:left;">
                            <span id="lblReportTitle" class="ReportTitle">Overview</span>&nbsp;&nbsp;
                            <span id="lblReportDesc"></span>
                        </div>
                        <div style="float:right;">
                           <input type="button" name="btnExport" value="" id="btnExport" class="icoSave">
                        </div>
                        <div style="clear:both;">&nbsp;</div>
                    </div>
                    <div id="upReport">
                            <div id="reportTable" class="fakeContainer" style="max-height:600px;width:915px;overflow:auto;margin-left:-10px;">
                            	<#list baseReport.reportVo as parts >
									<#list parts.chartTypes as c >
										<#include "/report/chart/${c.charType}.ftl"/>
									</#list>
								</#list> 
                            </div>
                        </div>
                    <div id="reportLoading" style="text-align: center; display: none;">
                            <img alt="loading" src="${contextPath}/apple2/Images/loader.gif">
                        </div>
                </div>
                <div style="clear:left;">
                    &nbsp;
                </div>
            </div>
        </div>
        
<div class="footerNew">
	<#include "/apple/appleFooter.ftl"/>
</div>

    </div>
    </div>
    <input type="button" name="msg_btn" value="" id="msg_btn" style="display:none;visibility:hidden;"><div id="msg_pnl" class="modalPopupMessageBox" style="display: none; position: fixed;">
	<div class="modalMessageHeader"><table width="100%" border="0"><tbody><tr><td><span class="Title" id="msglblHeader"></span></td></tr></tbody></table></div><div class="modalMessageBody"><div class="modalMessageContent"><span id="msgltMessage"></span></div> <div id="msg__divConfirm"><table border="0" cellspacing="0" cellpadding="0" width="100%"><tbody><tr><td align="center"><input type="button" class="buttonYes" onclick_me="__YesButton_Clicked();return false;">&nbsp;<input type="button" class="buttonNo" onclick_me="__NoButton_Clicked();"></td></tr></tbody></table></div><div id="msg__divAlert"><input type="button" class="buttonOk" onclick_me="__OkButton_Clicked();"></div><div id="msg__divContinue"><input type="button" class="buttonContinue" onclick_me="__ContinueButton_Clicked();"></div></div><div class="modalMessageHeaderXButtonContainer" align="right" style="position:absolute;top:0px;right:0px;"><input class="buttonX" onclick_me="__XButton_Clicked();return false;" type="button"></div>
</div><input type="button" name="lbi_btn" value="" id="lbi_btn" style="display:none;visibility:hidden;"><div id="lbi_pnl" class="modalPopupIF" style="display: none; position: fixed;">
	<div class="modalIFHeader"><table width="100%" cellspacing="0" cellpadding="0" border="0"><tbody><tr><td class="left"></td><td class="middle" align="left"><div id="__ifHeaderImgTitle" class=""></div></td><td class="middle" align="right"></td><td class="right"></td></tr></tbody></table></div><iframe id="__iFModal" allowtransparency="true" style="background-color:Transparent;" scrolling="no" frameborder="0" marginheight="0" marginwidth="0"></iframe><div class="modalMessageHeaderXButtonContainer" align="right" style="position:absolute;top:0px;right:0px;"><input type="button" class="buttonX" onclick_me="__HideLB_Frame(&#39;&#39;);return false;"></div>
</div><input type="button" name="lb_btn" value="" id="lb_btn" style="display:none;visibility:hidden;"><div id="lb_pnl" class="modalPopupLoading" style="display: none; position: fixed;">
	<div><table width="100%" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td align="right"><img src="${contextPath}/apple2/css/WebResource(1).axd"></td><td>&nbsp;</td><td align="left"><span class="loading">This page is Loading, please wait....</span></td></tr></tbody></table></div>
</div>
<div id="msg_mpeMessage_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lbi_mpeIframe_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lb_mpeLoading_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div>
	</form>
</body></html>