<html>
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
    <script type="text/javascript">
        function scollPos() {
            var ypos = document.getElementById("divResult").scrollTop;
            $("#FixedColumn").scrollTop(ypos);

            var xpos = document.getElementById("divResult").scrollLeft;
            $("#FixedHeader").scrollLeft(xpos);
        }

        function switchFontColor(obj, num, isOverview) {
            if (num == 0) {

                $(obj).stop().find('.rpNewDashboardMenuItemDesc').animate({ color: '#ffffff' }, 300);
                $(obj).stop().find('.rpNewDashboardMenuItemName').animate({ color: '#ffffff' }, 300);
                if (isOverview) {
                    $(obj).find('img.arrow').attr('src', '${contextPath}/apple2/Images/RightArrowBigWhite.png');
                }
                else {
                    $(obj).find('img').attr('src', '${contextPath}/apple2/Images/RightArrowWhite.png');
                }

            }
            else {

                $(obj).stop().find('.rpNewDashboardMenuItemDesc').animate({ color: '#7c7c7c' }, 300);
                $(obj).stop().find('.rpNewDashboardMenuItemName').animate({ color: '#C91F27' }, 300);
                if (isOverview) {
                    $(obj).find('img.arrow').attr('src', '${contextPath}/apple2/Images/RightArrowBig.png');
                }
                else {
                    $(obj).find('img').attr('src', '${contextPath}/apple2/Images/RightArrow.png');
                }
            }
        }

        $(document).ready(function () {
            //currentView = "div.reportMenu";
            $('#filterContent').hide();
            $('#btnMenuBackToReport').hide();
            $('#btnFilterBackToReport').hide();

            $('<img/>').src = '${contextPath}/apple2/Images/RightArrowWhite.png';
            $('<img/>').src = '${contextPath}/apple2/Images/RightArrowBigWhite.png';

            $('.rpNewDashboardMenuItem').hover(function () {
                $(this).css('background-position', '-260px 0');
                $(this).stop().animate({ "background-position": "0" }, 300, '', switchFontColor(this, 0, 0));


            }, function () {
                $(this).stop().animate({ "background-position": "-260px" }, 300, '', switchFontColor(this, 1, 0));
            });
            
            $('.rpNewDashboardMenuItemOverviewBG').hover(function () {
                $(this).css('background-position', '-888px 0');
                $(this).stop().animate({ "background-position": "0" }, 300, '', switchFontColor(this, 0, 1));


            }, function () {
                $(this).stop().animate({ "background-position": "-888px" }, 300, '', switchFontColor(this, 1, 1));
            });

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
<link href="${contextPath}/apple2/css/Site.css" type="text/css" rel="stylesheet"></head>
<body style="background-image:none;background-color:White;">
    <form method="post" action="" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="tm_HiddenField" id="tm_HiddenField" value=";;AjaxControlToolkit, Version=3.5.40412.0, Culture=neutral, PublicKeyToken=28f01b0e84b6d53e:zh-CN:065e08c0-e2d1-42ff-9483-e5c14441b311:5546a2b:475a4ef5:497ef277:effe2a26:a43b07eb:751cdd15:dfad98a5:1d3ed089:3cf12cf1">
<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="">
<input type="hidden" name="__SERVER_VIEWSTATE_KEY" id="__SERVER_VIEWSTATE_KEY" value="09e76d53-f9f8-4773-baf4-eb6bb34320a0">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
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


<script src="${contextPath}/apple2/css/WebResource.axd" type="text/javascript"></script>


<script src="${contextPath}/apple2/css/ScriptResource.axd" type="text/javascript"></script>
<script src="${contextPath}/apple2/css/ScriptResource(1).axd" type="text/javascript"></script>
<script src="${contextPath}/apple2/css/Reports.aspx" type="text/javascript"></script>
<div class="aspNetHidden">

	
</div>
   <script type="text/javascript">
//<![CDATA[
Sys.WebForms.PageRequestManager._initialize('tm', 'form1', ['tupProduct','upProduct','tupStore','upStore','tupGeo','upGeo','tupReport','upReport'], [], [], 90, '');
//]]>
</script>

    
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
                    <div class="reportMenu" style="min-height:320px;">
                        <div>
                            <div style="float:left;">
                                <span id="lblReportMenuTitle" class="ReportTitle" style="display:none;">Reports</span>

                            </div>
                            <div style="float:right;">
                                <input type="button" name="btnMenuBackToReport" value="" onclick_me="$(&#39;#reportContent&#39;).show();_rpScrollTo(&#39;div.report&#39;);return false;" id="btnMenuBackToReport" class="btnPreviousReport" style="display: none;">
                            </div>
                            <div style="clear:both;">&nbsp;</div>
                        </div>
                        <br>
                        <div>
                            
                            <div class="NewReportPanel">
                                <div class="NewReportPanelTop">
                
                                </div>
                                <div class="NewReportPanelMid">
                                    <div style="width:838px;margin:0px 0px 0px 0px;">
                                    	<a href="${contextPath}/apple/appleOverView" >
	                                        <div id="overviewItem" class="rpNewDashboardMenuItemOverview" onclick_me="_rpSelectReport(4013,&#39;Overview&#39;)" style="cursor:pointer;">
	                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                                <tbody><tr>
	                                                    <td valign="top" style="padding-right:30px;">
	                                                        <img alt="" src="${contextPath}/apple2/Images/OverviewStockImage.png">
	                                                    </td>       
		                                                    <td valign="top">
			                                                    <div class="rpNewDashboardMenuItemOverviewBG">
			                                                        <div style="float:left;">
			                                                            <a targer="_self" href="${contextPath}/apple/appleOverView" onclick_me="_rpSelectReport(4013,&#39;Overview&#39;)"><span class="rpNewDashboardMenuItemName" style="font-size:18px;">Overview</span></a>
			                                                            <div class="rpNewDashboardMenuItemDesc" style="width:340px;font-size:12px;line-height:18px;"></div>
			                                                        </div>
			                                                        <div style="float:right;padding:35px 0px 0px 0px;">
			                                                            <a targer="_self" href="/colgate/page/ReportsFilter.html" onclick_me="_rpSelectReport(4013,&#39;Overview&#39;)">
			                                                            <img class="arrow" style="border:none;" alt="" src="${contextPath}/apple2/Images/RightArrowBig.png"></a>
			                                                        </div>
			                                                    </div>
		                                                    </td>
	                                                </tr>
	                                            </tbody></table>
	                                        	</div>
	                                    	</div>
	                              		</a>
                                    <div>
                                        
                                             <a href="${contextPath}/report/queryApple/80" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4001">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody>
                                                        <tr>
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4001,&#39;Mandated SKU Distribution&#39;)"><span class="rpNewDashboardMenuItemName">Report</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe distribution status statistics of all categories, brands and Key SKUs by store." onclick_me="_rpSelectReport(4001,&#39;Mandated SKU Distribution&#39;)" style="cursor:pointer;"></div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4001,&#39;Mandated SKU Distribution&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/apple2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                              </a>
                                            
                                             <a href="${contextPath}/report/queryApple/81" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4003">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4003,&#39;Share of Shelf&#39;)"><span class="rpNewDashboardMenuItemName">Raw data</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe SOS status statistics of all categories and key brands." onclick_me="_rpSelectReport(4003,&#39;Share of Shelf&#39;)" style="cursor:pointer;"></div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4003,&#39;Share of Shelf&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/apple2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                             </a>
                                             
                                            <div class="rpNewDashboardMenuItem" style="margin-right:88px;">
                                                    
                                                </div>
                                            <div style="clear:left;">
                                            &nbsp;
                                            </div>
                                    </div>
                                </div>
                                <div class="NewReportPanelBtm">
                
                                </div>
            
                            </div>
                        </div>
                    </div>
                    <div class="reportFilter">
                         <div>
                            <div style="float:left;">
                                <span id="lblReportFilterTitle" class="ReportTitle"></span>
                                
                            </div>
                            <div style="float:right;">
                                
                                <input type="button" name="btnFilterBackToMenu" value="" onclick_me="_rpScrollTo(&#39;div.reportMenu&#39;,function(){$(&#39;#filterContent&#39;).hide();});return false;" id="btnFilterBackToMenu" class="btnMenu">
                                <input type="button" name="btnFilterBackToReport" value="" onclick_me="$(&#39;#reportContent&#39;).show();_rpScrollTo(&#39;div.report&#39;,function(){$(&#39;#filterContent&#39;).hide();});return false;" id="btnFilterBackToReport" class="btnPreviousReport" style="display: none;">
                            </div>
                            <div style="clear:both;">&nbsp;</div>
                        </div>
                        <div id="filterContent" style="display: none;">
                            <input type="hidden" name="hfReportId" id="hfReportId">
                            <input type="hidden" name="hfReportName" id="hfReportName">
                            <div id="GroupingTitle">
                                <div class="sectionTitle">
                                    <div class="sectionTitleLeft"></div>
                                   <!-- <div class="sectionTitleMid">
                                        <span id="lblRowLabel" class="sectionTitleLbl" style="display:inline-block;font-weight:bold;width:90px;">ROW LABEL</span>
                                    </div>-->
                                    <div class="sectionTitleRight"></div>
                                    <div style="clear:left;">&nbsp;</div>
                                </div>
                            </div>
                            <div class="filterPanel" style="margin-top:-10px;" id="Grouping">
                                <table border="0" cellpadding="5" cellspacing="0" width="915">
                                    <tbody><tr>
                                        <td>
                                            <span id="lblLvl1Grouping" class="formLabel">Level 1 Grouping</span>
                                        </td>
                                        <td>
                                            <span id="lblLvl2Grouping" class="formLabel">Level 2 Grouping</span>
                                        </td>
                                        <td>
                                            <span id="lblLvl3Grouping" class="formLabel">Level 3 Grouping</span>
                                        </td>
                                        <td rowspan="2">
                                            <span id="lblErr_Grouping" class="filterError" style="display:inline-block;width:200px;"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                           
                                        </td>
                                        <td>
                                           
                                        </td>
                                        <td>
                                         
                                        </td>
                                        
                                    </tr>
                                </tbody></table>
                            </div>
                            <br>
                            <div>
                                <div class="sectionTitle">
                                    <div class="sectionTitleLeft"></div>
                                    <div class="sectionTitleMid">
                                        <span id="lblFilter" class="sectionTitleLbl" style="display:inline-block;font-weight:bold;width:90px;">FILTER</span>
                                    </div>
                                    <div class="sectionTitleRight"></div>
                                    <div style="clear:left;">&nbsp;</div>
                                </div>
                            </div>
                            <div class="filterPanel" style="margin-top:-10px;" id="Period">
                                <table border="0" cellpadding="5" cellspacing="0" width="915">
                                    <colgroup>
                                        <col width="15%">
                                        <col width="85%">
                                    </colgroup>
                                    <tbody><tr id="tr_ddlPeriod" class="filterRow">
                                        <td>
                                            <span id="lbl_ddlPeriod" class="formLabel">Period</span><span id="lblMand_ddlPeriod"></span>
                                        </td>
                                        <td>
                                           
                                            &nbsp;<span id="lblErr_ddlPeriod" class="filterError"></span>
                                        </td>
                                    </tr>
                                    <tr id="tr_ddlPeriodTo" class="filterRow">
                                        <td>
                                            <span id="lbl_ddlPeriodTo" class="formLabel">To</span><span id="lblMand_ddlPeriodTo"></span>
                                        </td>
                                         <td>
                                          
                                            &nbsp;<span id="lblErr_ddlPeriodTo" class="filterError"></span>
                                        </td>
                                    </tr>
                                </tbody></table>
                            </div>
                            <div id="upProduct">
	
                                    <div class="filterPanel" id="Product">
                                        <table border="0" cellpadding="5" cellspacing="0" width="915">
                                            <colgroup>
                                                <col width="15%">
                                                <col width="85%">
                                            </colgroup>
                                            <tbody><tr id="tr_ddlBrand" class="filterRow">
                                                <td>
                                                    <span id="lbl_ddlBrand" class="formLabel">Brand</span><span id="lblMand_ddlBrand"></span>
                                                </td>
                                                <td>
                                                  
                                                    &nbsp;<span id="lblErr_ddlBrand" class="filterError"></span>
                                                </td>
                                            </tr>
                                            <tr id="tr_rbCategory" class="filterRow">
                                                <td>
                                                    <span id="lbl_rbCategory" class="formLabel">Category</span><span id="lblMand_rbCategory"></span>
                                                </td>
                                                <td>
                                                    <span id="rbCategory"><input id="rbCategory_0" type="radio" name="rbCategory" value="1" checked="checked"><label for="rbCategory_0">Toothpaste</label><input id="rbCategory_1" type="radio" name="rbCategory" value="2" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;rbCategory$1\&#39;,\&#39;\&#39;)&#39;, 0)"><label for="rbCategory_1">Toothbrush</label><input id="rbCategory_2" type="radio" name="rbCategory" value="3" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;rbCategory$2\&#39;,\&#39;\&#39;)&#39;, 0)"><label for="rbCategory_2">Mouthwash</label></span>
                                                    &nbsp;<span id="lblErr_rbCategory" class="filterError"></span>
                                                </td>
                                            </tr> 
                                            <tr id="tr_rbBrand" class="filterRow">
                                                <td>
                                                    <span id="lbl_rbBrand" class="formLabel">Brand</span><span id="lblMand_rbBrand"></span>
                                                </td>
                                                <td>
                                                    <span id="rbBrand"><input id="rbBrand_0" type="radio" name="rbBrand" value="1" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;rbBrand$0\&#39;,\&#39;\&#39;)&#39;, 0)"><label for="rbBrand_0">Colgate</label><input id="rbBrand_1" type="radio" name="rbBrand" value="0" checked="checked"><label for="rbBrand_1">All Brands</label><input id="rbBrand_2" type="radio" name="rbBrand" value="2" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;rbBrand$2\&#39;,\&#39;\&#39;)&#39;, 0)"><label for="rbBrand_2">Key Competitors Brands</label></span>
                                                    &nbsp;<span id="lblErr_rbBrand" class="filterError"></span>
                                                </td>
                                            </tr>
                                            
                                            <tr id="tr_ddlSKU" class="filterRow">
                                                <td>
                                                    <span id="lbl_ddlSKU" class="formLabel">SKU</span><span id="lblMand_ddlSKU"></span>
                                                </td>
                                                <td>
                                                   
                                                    &nbsp;<span id="lblErr_ddlSKU" class="filterError"></span>
                                                </td>
                                            </tr>
                                        </tbody></table>

                                    </div>
                               
</div>
                            <div class="filterPanel" id="DisplayType">
                                <table border="0" cellpadding="5" cellspacing="0" width="915">
                                    <colgroup>
                                        <col width="15%">
                                        <col width="85%">
                                    </colgroup>
                                    <tbody><tr id="tr_ddlDisplayType" class="filterRow">
                                        <td>
                                            <span id="lbl_ddlDisplayType" class="formLabel">Display Type</span><span id="lblMand_ddlDisplayType"></span>
                                        </td>
                                        <td>
                                            
                                            &nbsp;<span id="lblErr_ddlDisplayType" class="filterError"></span>
                                        </td>
                                    </tr>
                                </tbody></table>
                            </div>
                             <div id="upStore">
	
                                 <div class="filterPanel" id="Store">
                                    <table border="0" cellpadding="5" cellspacing="0" width="915">
                                        <colgroup>
                                            <col width="15%">
                                            <col width="85%">
                                        </colgroup>
                                        <tbody><tr id="tr_cbChannel" class="filterRow">
                                            <td>
                                                <span id="lbl_cbChannel" class="formLabel">Channel</span><span id="lblMand_cbChannel"></span>
                                            </td>
                                            <td>
                                                <table id="cbChannel">
		<tbody><tr>
			<td><input id="cbChannel_0" type="checkbox" name="cbChannel$0" checked="checked" value="2"><label for="cbChannel_0">Big Supermarket</label></td><td><input id="cbChannel_1" type="checkbox" name="cbChannel$1" checked="checked" value="12"><label for="cbChannel_1">Cash &amp; Carry</label></td><td><input id="cbChannel_2" type="checkbox" name="cbChannel$2" checked="checked" value="1"><label for="cbChannel_2">Hypermarket</label></td><td><input id="cbChannel_3" type="checkbox" name="cbChannel$3" checked="checked" value="13"><label for="cbChannel_3">Supermarket</label></td><td></td><td></td>
		</tr>
	</tbody></table>
                                                <span id="lblErr_cbChannel" class="filterError"></span>
                                            </td>
                                        </tr>
                                        <tr id="tr_ddlKeyAccount" class="filterRow">
                                            <td>
                                                <span id="lbl_ddlKeyAccount" class="formLabel">APG</span><span id="lblMand_ddlKeyAccount"></span>
                                            </td>
                                            <td>
                                              
                                                 &nbsp;<span id="lblErr_ddlKeyAccount" class="filterError"></span>
                                            </td>
                                        </tr>
                                        <tr id="tr_ddlCustomer" class="filterRow">
                                            <td>
                                                <span id="lbl_ddlCustomer" class="formLabel">Account</span><span id="lblMand_ddlCustomer"></span>
                                            </td>
                                            <td>
                                               
                                                 &nbsp;<span id="lblErr_ddlCustomer" class="filterError"></span>
                                            </td>
                                        </tr>
                                    </tbody></table>
                                </div>
                                
</div>
                             <div id="upGeo">
	
                                 <div class="filterPanel" id="Geo">
                                    <table border="0" cellpadding="5" cellspacing="0" width="915">
                                        <colgroup>
                                            <col width="15%">
                                            <col width="85%">
                                        </colgroup>
                                        <tbody><tr id="tr_cbRegion" class="filterRow">
                                            <td>
                                                <span id="lbl_cbRegion" class="formLabel">Region</span><span id="lblMand_cbRegion"></span>
                                            </td>
                                            <td>
                                                <table id="cbRegion">
		<tbody><tr>
			<td><input id="cbRegion_0" type="checkbox" name="cbRegion$0" checked="checked" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;cbRegion$0\&#39;,\&#39;\&#39;)&#39;, 0)" value="1"><label for="cbRegion_0">East</label></td><td><input id="cbRegion_1" type="checkbox" name="cbRegion$1" checked="checked" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;cbRegion$1\&#39;,\&#39;\&#39;)&#39;, 0)" value="4"><label for="cbRegion_1">North</label></td><td><input id="cbRegion_2" type="checkbox" name="cbRegion$2" checked="checked" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;cbRegion$2\&#39;,\&#39;\&#39;)&#39;, 0)" value="2"><label for="cbRegion_2">South</label></td><td><input id="cbRegion_3" type="checkbox" name="cbRegion$3" checked="checked" onclick_me="javascript:setTimeout(&#39;__doPostBack(\&#39;cbRegion$3\&#39;,\&#39;\&#39;)&#39;, 0)" value="3"><label for="cbRegion_3">West</label></td><td></td><td></td>
		</tr>
	</tbody></table>
                                                &nbsp;<span id="lblErr_cbRegion" class="filterError"></span>
                                            </td>
                                        </tr>
                                        <tr id="tr_ddlProvince" class="filterRow">
                                            <td>
                                                <span id="lbl_ddlProvince" class="formLabel">Province</span><span id="lblMand_ddlProvince"></span>
                                            </td>
                                            <td>
                                              
                                                &nbsp;<span id="lblErr_ddlProvince" class="filterError"></span>
                                            </td>
                                        </tr>
                                        <tr id="tr_ddlCity" class="filterRow">
                                            <td>
                                                <span id="lbl_ddlCity" class="formLabel">City</span><span id="lblMand_ddlCity"></span>
                                            </td>
                                            <td>
                                               
                                                &nbsp;<span id="lblErr_ddlCity" class="filterError"></span>
                                            </td>
                                        </tr>
                                    </tbody></table>
                                </div>
                                
</div>
                            <div class="filterPanel" id="Email">
                                <table border="0" cellpadding="5" cellspacing="0" width="915">
                                    <colgroup>
                                        <col width="15%">
                                        <col width="85%">
                                    </colgroup>
                                    <tbody><tr id="tr_tbEmail" class="filterRow">
                                        <td>
                                            <span id="lbl_tbEmail" class="formLabel">电子邮件地址</span><span id="lblMand_tbEmail"></span>
                                        </td>
                                        <td>
                                            <input name="tbEmail" type="text" value="Nicke.Jiang@alwaysmkt.com" id="tbEmail" class="textBox" style="width:220px;">
                                            &nbsp;<span id="lblErr_tbEmail" class="filterError"></span>
                                        </td>
                                    </tr>
                                </tbody></table>
                            </div>
                            <div style="text-align:right;">
                                <input type="button" name="btnDownload" value="" onclick_me="return _rpValidate(1);" id="btnDownload" class="btnDownload">
                                <input type="button" name="btnRefresh" value="" onclick_me="$(&#39;#lblReportTitle&#39;).html(selectedReportName);_rpSetReportDesc(selectedReportId);_rpValidate();return false;" id="btnRefresh" class="btnRefresh">
                            </div>
                        </div>
                        <div id="filterLoading" style="text-align:center;">
                            <img alt="loading" src="${contextPath}/apple2/Images/loader.gif">
                        </div>
                    
                </div>
                <div class="report">
                    <div>
                        <div style="float:left;">
                            <span id="lblReportTitle" class="ReportTitle"></span>&nbsp;&nbsp;
                            <span id="lblReportDesc"></span>
                        </div>
                        <div style="float:right;">
                            <input type="button" name="btnMenu" value="" onclick_me="_rpScrollTo(&#39;div.reportMenu&#39;,function(){$(&#39;#reportContent&#39;).hide();});return false;" id="btnMenu" class="icoMenu">&nbsp;
                            <input type="button" name="btnBackToFilter" value="" onclick_me="$(&#39;#filterContent&#39;).show();_rpScrollTo(&#39;div.reportFilter&#39;,function(){$(&#39;#reportContent&#39;).hide();});return false;" id="btnBackToFilter" class="icoFilter">&nbsp;
                            <input type="button" name="btnExport" value="" id="btnExport" class="icoSave">
                        </div>
                        <div style="clear:both;">&nbsp;</div>
                    </div>
                    <div id="reportContent">
                        <div id="upReport">
	
                                <input type="button" name="btnLoadReport" value="" id="btnLoadReport" style="display:none;">
                                <input type="hidden" name="hfTimeStamp" id="hfTimeStamp">
                                <div id="reportTable" class="fakeContainer">
                                    
                                </div>
                            
</div>
                    </div>
                    <div id="reportLoading" style="text-align:center;">
                            <img alt="loading" src="${contextPath}/apple2/Images/loader.gif">
                        </div>
                </div>
                <div style="clear:left;">
                    &nbsp;
                </div>
            </div>
            
            
        </div>
        
<div class="footerNew">
	<#include "/colgate/clogateFooter.ftl"/>
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

<script type="text/javascript">
//<![CDATA[
var __callback_LB;var __callback_LB_NoButton;var __curMode_LB;var __callBackDelay = 10;var __callBackBtnId;var __countDownTimer=0;var __countDownMsg;var __countDownTimeoutMsg;var __countDownObj;function __YesButton_Clicked() {$find("msg_mpeMessage").hide();if( __callback_LB && __callback_LB.length > 0 ) {setTimeout("__evalCallBack_LB()", __callBackDelay);} else {var prm = Sys.WebForms.PageRequestManager.getInstance();prm._doPostBack(__callBackBtnId, '');__callBackBtnId="";}}function __ContinueButton_Clicked() {$find("msg_mpeMessage").hide();__polling('SSU');if(__countDownObj){clearTimeout(__countDownObj);}}function __OkButton_Clicked() {$find("msg_mpeMessage").hide();setTimeout("__evalCallBack_LB()", __callBackDelay);}function __XButton_Clicked() {$find("msg_mpeMessage").hide();if( __curMode_LB == 0) {setTimeout("__evalCallBack_LB()", __callBackDelay);}else if(__curMode_LB==2){__pollingStart(30000);if(__countDownObj){clearTimeout(__countDownObj);}}}function __evalCallBack_LB() {eval(__callback_LB);__callback_LB = "";}function __evalCallBack_LB_No() {eval(__callback_LB_NoButton);__callback_LB_NoButton = "";}function __NoButton_Clicked() {$find("msg_mpeMessage").hide();if( __callback_LB_NoButton && __callback_LB_NoButton.length > 0 ) {setTimeout("__evalCallBack_LB_No()", __callBackDelay);}}function ShowMessageLBDelay(title, msg, bid, mode, callback) {if($find("msg_mpeMessage")) {ShowMessageLB(title, msg, bid, mode, callback);}else{setTimeout(function(){ShowMessageLBDelay(title, msg, bid, mode, callback)}, 20);}}function UpdateCountDown(){var tmp = __countDownMsg.replace('[TIMER]', __countDownTimer);$get("msgltMessage").innerHTML = tmp;__countDownTimer--; if( __countDownTimer >= 0 ){__countDownObj = setTimeout('UpdateCountDown()', 1000);}else {__curMode_LB=0;$get("msgltMessage").innerHTML = __countDownTimeoutMsg;$common.setVisible($get("msg__divAlert"), true);$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divContinue"), false);}}function ShowMessageLBCountDown(title, msg, tmsg, bid, mode, callback, timer) {if($find("msg_mpeMessage")) {__countDownTimer=timer;__countDownMsg=msg;__countDownTimeoutMsg=tmsg;var tmp = __countDownMsg.replace('[TIMER]', __countDownTimer);ShowMessageLB(title, tmp, bid, mode, callback);UpdateCountDown();}else{setTimeout(function(){ShowMessageLBCountDown(title, msg, bid, mode, callback, timer)}, 20);}}function ShowMessageLBYesNo(title, msg, bid, mode, callbackYes, callbackJsNo) {__callback_LB_NoButton = callbackJsNo;ShowMessageLB(title,msg,bid,mode,callbackYes);}function ShowMessageLB(title, msg, bid, mode, callback) {__callback_LB = callback;__curMode_LB = mode;__callBackBtnId = bid;$get("msglblHeader").innerHTML = title;$get("msgltMessage").innerHTML = msg;if( mode && mode == 1){$common.setVisible($get("msg__divConfirm"), true);$common.setVisible($get("msg__divAlert"), false);$common.setVisible($get("msg__divContinue"), false);}else if( mode && mode == 2){$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divAlert"), false);$common.setVisible($get("msg__divContinue"), true);} else {$common.setVisible($get("msg__divAlert"), true);$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divContinue"), false);}$find("msg_mpeMessage").show();}function __ShowLB_FrameDelay(clsName, hText, width, height, url) {if( $find("lbi_mpeIframe") ){__ShowLB_Frame(clsName, hText, width, height, url);}else{setTimeout(new function(){__ShowLB_FrameDelay(clsName, hText, width, height, url)}, 100);}}function __SetTitle_Frame(hText){$get("__ifHeaderImgTitle").innerHTML = hText;}function __SetClass_Frame(clsName){$get("__ifHeaderImgTitle").className = clsName;}function __ShowLB_Frame(clsName, hText, width, height, url){var myi = $get("__ifHeaderImgTitle");var myf = $get("__iFModal");var div = $get("lbi_pnl");myi.className = clsName;myi.innerHTML = hText;div.style.width = width;myf.style.width = width;myf.style.height = height;myf.src = url;$find("lbi_mpeIframe").show();}function __HideLB_Frame(callbackJs){$find("lbi_mpeIframe").hide();$get("__iFModal").src = "";if( callbackJs && callbackJs != '' ){eval(callbackJs);}}__InitUpdateProgressPanel();
WebForm_InitCallback();function __polling(message){var context = 'polling';WebForm_DoCallback('__Page',message,__pollingCallBack,context,__pollingErrorCallBack,true);}function __pollingCallBack(returnmessage, context){if(returnmessage.length > 0){eval(returnmessage)}else{__pollingStart(300000);}}function __pollingErrorCallBack(returnmessage, context){alert("Callback Error: " + returnmessage + ", " + context);}function __pollingStart(timeInterval){setTimeout("__polling('SS')", timeInterval);}__pollingStart(300000);(function() {var fn = function() {$get('tm_HiddenField').value = '';Sys.Application.remove_init(fn);};Sys.Application.add_init(fn);})();function __ShowLBLoading() { var lf = $find("lb_mpeLoading"); if( lf ) lf.show();} function __HideLBLoading() { var lf = $find("lb_mpeLoading"); if( lf ) lf.hide(); else setTimeout(function(){__HideLBLoading();}, 100);}Sys.Net.WebRequestManager.add_invokingRequest(__onInvoke); Sys.Net.WebRequestManager.add_completedRequest(__onComplete); var __loadingDigShowed = false; var __loadingST;var __showAjaxLoading=false;function __onInvoke(sender, args) { if( !__showAjaxLoading) return; __loadingST = setTimeout("__ShowLoadingDig()", 100);} function __ShowLoadingDig() { if( !__loadingDigShowed ) top.__ShowLBLoading(); __loadingDigShowed = true; } function __onComplete(sender, args) { __showAjaxLoading=false;if( __loadingDigShowed ) top.__HideLBLoading(); __loadingDigShowed = false; clearTimeout(__loadingST); } function pageUnload() { Sys.Net.WebRequestManager.remove_invokingRequest(__onInvoke); Sys.Net.WebRequestManager.remove_completedRequest(__onComplete); }Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"msg_pnl","dynamicServicePath":"/StoreRadar/Reports/Reports.aspx","id":"msg_mpeMessage"}, null, null, $get("msg_btn"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"lbi_pnl","dynamicServicePath":"/StoreRadar/Reports/Reports.aspx","id":"lbi_mpeIframe"}, null, null, $get("lbi_btn"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"lb_pnl","dynamicServicePath":"/StoreRadar/Reports/Reports.aspx","id":"lb_mpeLoading"}, null, null, $get("lb_btn"));
});
//]]>
</script>
<div id="msg_mpeMessage_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lbi_mpeIframe_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lb_mpeLoading_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div></form>


</body></html>