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
	<title>Colgate In-Store  Tracking</title> 
	<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
    <script type="text/javascript" src="${contextPath}/colgate2/js/jquery-1.7.2.min.js"></script>
    <script src="${contextPath}/colgate2/js/jquery-ui-1.9.1.custom.min.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/jquery.scrollTo-min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/colgate2/js/Common.js"></script>
    <script src="${contextPath}/colgate2/js/superTables.js" type="text/javascript"></script>
    <link href="${contextPath}/colgate2/css/superTables.css" rel="stylesheet" type="text/css">
    <script src="${contextPath}/colgate2/js/report.js" type="text/javascript"></script>
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
                    $(obj).find('img.arrow').attr('src', '${contextPath}/colgate2/Images/RightArrowBigWhite.png');
                }
                else {
                    $(obj).find('img').attr('src', '${contextPath}/colgate2/Images/RightArrowWhite.png');
                }

            }
            else {

                $(obj).stop().find('.rpNewDashboardMenuItemDesc').animate({ color: '#7c7c7c' }, 300);
                $(obj).stop().find('.rpNewDashboardMenuItemName').animate({ color: '#C91F27' }, 300);
                if (isOverview) {
                    $(obj).find('img.arrow').attr('src', '${contextPath}/colgate2/Images/RightArrowBig.png');
                }
                else {
                    $(obj).find('img').attr('src', '${contextPath}/colgate2/Images/RightArrow.png');
                }
            }
        }

        $(document).ready(function () {
            //currentView = "div.reportMenu";
            $('#filterContent').hide();
            $('#btnMenuBackToReport').hide();
            $('#btnFilterBackToReport').hide();

            $('<img/>').src = '${contextPath}/colgate2/Images/RightArrowWhite.png';
            $('<img/>').src = '${contextPath}/colgate2/Images/RightArrowBigWhite.png';

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
<link href="${contextPath}/colgate2/css/buttons.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/buttonsCN.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/dashboard.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/DataEntry.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/jquery.lightbox-0.5.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/messageBox.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/overview.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/RBControls.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/report.css" type="text/css" rel="stylesheet"><link href="${contextPath}/colgate2/css/Site.css" type="text/css" rel="stylesheet"></head>
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


<script src="${contextPath}/colgate2/css/WebResource.axd" type="text/javascript"></script>


<script src="${contextPath}/colgate2/css/ScriptResource.axd" type="text/javascript"></script>
<script src="${contextPath}/colgate2/css/ScriptResource(1).axd" type="text/javascript"></script>
<script src="${contextPath}/colgate2/css/Reports.aspx" type="text/javascript"></script>
<div class="aspNetHidden">

	
</div>
   <script type="text/javascript">
//<![CDATA[
Sys.WebForms.PageRequestManager._initialize('tm', 'form1', ['tupProduct','upProduct','tupStore','upStore','tupGeo','upGeo','tupReport','upReport'], [], [], 90, '');
//]]>
</script>

    
<div class="headerNew">
    
   <div style="margin:0px auto;width:960px;">
        <#include "/colgate/colgateHead.ftl" />
        </div>
        <div>
            <div style="float:left;">
                <img src="${contextPath}/colgate2/Images/equity_chevron.png">
            </div>
            <div style="float:left;color:#ce1613;font-family:Rockwell,Helvetica, Arial, sans-serif;font-style:italic;font-size:20px;margin:15px 0px 0px 10px;">
                In-Store Tracking
            </div>
            
            <div style="float:right;margin:23px 0px 0px 0px;text-align:right;width:540px;">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody><tr>
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
                                    <input type="button" value="Reports" onclick="window.location.href='${contextPath}/colgate/reports/'" class="NewMenuButtonSelected"></input>
                                </td>
                                
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                     <input type="button" value="RAW DATA" onclick="window.location.href='${contextPath}/colgate/rawdata/'" class="NewMenuButton"></input>
                                </td>
                                
                                <@shiro.hasPermission name="C5M5">
                                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                                    <input type="button" value="Gallery" onclick="window.location.href='${contextPath}/colgate/gallery/'" class="NewMenuButton"></input>
                                </td>
                                 </@shiro.hasPermission>
                            
                    </tr>
                </tbody></table>
               
            </div>
            
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
                                    	<a href="${contextPath}/report/queryColgate/58" >
	                                        <div id="overviewItem" class="rpNewDashboardMenuItemOverview" onclick_me="_rpSelectReport(4013,&#39;Overview&#39;)" style="cursor:pointer;">
	                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
	                                                <tbody><tr>
	                                                    <td valign="top" style="padding-right:30px;">
	                                                        <img alt="" src="${contextPath}/colgate2/Images/OverviewStockImage.png">
	                                                    </td>       
		                                                    <td valign="top">
			                                                    <div class="rpNewDashboardMenuItemOverviewBG">
			                                                        <div style="float:left;">
			                                                            <a targer="_self" href="${contextPath}/report/queryColgate/58" onclick_me="_rpSelectReport(4013,&#39;Overview&#39;)"><span class="rpNewDashboardMenuItemName" style="font-size:18px;">Overview</span></a>
			                                                            <div class="rpNewDashboardMenuItemDesc" style="width:340px;font-size:12px;line-height:18px;">Classified by 6 categories of Distribution, Share of Shelf, Promotion ,DM, Plan-O-Gram, Promoter, which make it more convenient for dashboard by Region, Client Type and Brand..</div>
			                                                        </div>
			                                                        <div style="float:right;padding:35px 0px 0px 0px;">
			                                                            <a targer="_self" href="/colgate/page/ReportsFilter.html" onclick_me="_rpSelectReport(4013,&#39;Overview&#39;)">
			                                                            <img class="arrow" style="border:none;" alt="" src="${contextPath}/colgate2/Images/RightArrowBig.png"></a>
			                                                        </div>
			                                                    </div>
		                                                    </td>
	                                                </tr>
	                                            </tbody></table>
	                                        	</div>
	                                    	</div>
	                              		</a>
                                    <div>
                                        
                                             <a href="${contextPath}/report/queryColgate/48" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4001">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody>
                                                        <tr>
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4001,&#39;Mandated SKU Distribution&#39;)"><span class="rpNewDashboardMenuItemName">Mandated SKU Distribution</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe distribution status statistics of all categories, brands and Key SKUs by store." onclick_me="_rpSelectReport(4001,&#39;Mandated SKU Distribution&#39;)" style="cursor:pointer;">The distribution status statistics of all categories, brands and Key SKUs by store.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4001,&#39;Mandated SKU Distribution&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                              </a>
                                            
                                             <a href="${contextPath}/report/queryColgate/49" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4003">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4003,&#39;Share of Shelf&#39;)"><span class="rpNewDashboardMenuItemName">Share of Shelf</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe SOS status statistics of all categories and key brands." onclick_me="_rpSelectReport(4003,&#39;Share of Shelf&#39;)" style="cursor:pointer;">The SOS status statistics of all categories and key brands.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4003,&#39;Share of Shelf&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                             </a>
                                             
                                             
                                             <!--
                                             <a href="${contextPath}/report/queryColgate/50" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4002">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4002,&#39;Out of Stock&#39;)"><span class="rpNewDashboardMenuItemName">Out of Stock</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aLorem ipsum dolor sit amet, consectetuer adipiscing elit." onclick_me="_rpSelectReport(4002,&#39;Out of Stock&#39;)" style="cursor:pointer;">Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4002,&#39;Out of Stock&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                             -->
                                             
                                            <a href="${contextPath}/report/queryColgate/51" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4004">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4004,&#39;SKU on Shelf&#39;)"><span class="rpNewDashboardMenuItemName">SKU on Shelf</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe SKU # on shelf status statistics of all categories and key brands by store." onclick_me="_rpSelectReport(4004,&#39;SKU on Shelf&#39;)" style="cursor:pointer;">The SKU # on shelf status statistics of all categories and key brands by store.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4004,&#39;SKU on Shelf&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/52" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4005">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4005,&#39;Price&#39;)"><span class="rpNewDashboardMenuItemName">Price</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aStatistics of RSP/ RRSP of all categories, brands and key SKUs by store." onclick_me="_rpSelectReport(4005,&#39;Price&#39;)" style="cursor:pointer;">Statistics of RSP/ RRSP of all categories, brands and key SKUs by store.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4005,&#39;Price&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/61" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4007">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4007,&#39;Secondary Display&#39;)"><span class="rpNewDashboardMenuItemName">Secondary Display</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aAnalyze the 2nd display quantity/area  share rate among all the stores by display  type, category and brand" onclick_me="_rpSelectReport(4007,&#39;Secondary Display&#39;)" style="cursor:pointer;">Analyze the 2nd display quantity/area  share rate among all the stores by display  type, category and brand</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4007,&#39;Secondary Display&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                            
                                            
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4011">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4011,&#39;DM&#39;)"><span class="rpNewDashboardMenuItemName">DM</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aDM distribution status statistics of categories  and all brands by customer" onclick_me="_rpSelectReport(4011,&#39;DM&#39;)" style="cursor:pointer;">DM distribution status statistics of categories  and all brands by customer</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4011,&#39;DM&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            
                                            
                                            <a href="${contextPath}/report/queryColgate/53" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4012">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4012,&#39;Plan-O-Gram&#39;)"><span class="rpNewDashboardMenuItemName">Plan-O-Gram</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe Plan-O-Gram  status statistics of all categories  by store." onclick_me="_rpSelectReport(4012,&#39;Plan-O-Gram&#39;)" style="cursor:pointer;">The Plan-O-Gram  status statistics of all categories  by store.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4012,&#39;Plan-O-Gram&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/54" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4010">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4010,&#39;Promotion&#39;)"><span class="rpNewDashboardMenuItemName">Promotion</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe promotion status statistics of all categories by store." onclick_me="_rpSelectReport(4010,&#39;Promotion&#39;)" style="cursor:pointer;">The promotion status statistics of all categories by store.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4010,&#39;Promotion&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                              </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/55" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4009">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4009,&#39;Promoter&#39;)"><span class="rpNewDashboardMenuItemName">Promoter</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aThe promoter  status statistics by store." onclick_me="_rpSelectReport(4009,&#39;Promoter&#39;)" style="cursor:pointer;">The promoter  status statistics by store.</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4009,&#39;Promoter&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                             </a>
                                            
                                             <a href="${contextPath}/report/queryColgate/60" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4014">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4014,&#39;Trend&#39;)"><span class="rpNewDashboardMenuItemName">Trend</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aLast 12 month results by store" onclick_me="_rpSelectReport(4014,&#39;Trend&#39;)" style="cursor:pointer;">Last 12 month results by store</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4014,&#39;Trend&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                               </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/56" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right: 88px; id="divItem4015">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4015,&#39;Secondary Display: Colgate vs Competitors&#39;)"><span class="rpNewDashboardMenuItemName" style="color: rgb(210, 71, 78);">Secondary Display: Colgate vs Competitors</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aAnalyze the 2nd display quantity/area  share rate among all the stores" onclick_me="_rpSelectReport(4015,&#39;Secondary Display: Colgate vs Competitors&#39;)" style="cursor: pointer; color: rgb(147, 147, 147);">Analyze the 2nd display quantity/area  share rate among all the stores</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4015,&#39;Secondary Display: Colgate vs Competitors&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                              </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/57" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4016">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4016,&#39;Secondary Display: Breakdown&#39;)"><span class="rpNewDashboardMenuItemName">Secondary Display: Breakdown</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aAnalyze the 2nd display area  of Colgate among all the stores by display  type, category and brand" onclick_me="_rpSelectReport(4016,&#39;Secondary Display: Breakdown&#39;)" style="cursor:pointer;">Analyze the 2nd display area  of Colgate among all the stores by display  type, category and brand</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4016,&#39;Secondary Display: Breakdown&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                            
                                            <a href="${contextPath}/report/queryColgate/59" >
                                                <div class="rpNewDashboardMenuItem" style="margin-right:88px;" id="divItem4017">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a onclick_me="_rpSelectReport(4017,&#39;Execution&#39;)"><span class="rpNewDashboardMenuItemName">Execution</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" id="aExecution Score Report" onclick_me="_rpSelectReport(4017,&#39;Execution&#39;)" style="cursor:pointer;">Execution Score Report</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a onclick_me="_rpSelectReport(4017,&#39;Execution&#39;)">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
                                                </div>
                                            </a>
                                            
                                            <div class="rpNewDashboardMenuItem" style="margin-right:88px;">
                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                        <tbody><tr>
                                           
                                                            <td valign="top">
                                                                <a href="#"><span class="rpNewDashboardMenuItemName">Requested Reports</span></a>
                                                                <div class="rpNewDashboardMenuItemDesc" onclick_me="window.location.href=&#39;RequestedReport.aspx&#39;" style="cursor:pointer;">Last 20 requested Trend and Overview Score Report</div>
                                                            </td>
                                                                <td valign="top" align="right" style="padding:25px 5px 0px 10px;">
                                                                <a href="#">
                                                                    <img alt="" style="border:none;" src="${contextPath}/colgate2/Images/RightArrow.png"></a>
                                                            </td>
                                                        </tr>
                                                    </tbody></table>
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
                                    <div class="sectionTitleMid">
                                        <span id="lblRowLabel" class="sectionTitleLbl" style="display:inline-block;font-weight:bold;width:90px;">ROW LABEL</span>
                                    </div>
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
                                            <select name="ddlLvl1Grouping" id="ddlLvl1Grouping" style="width:200px;">
	<option value="Region">Region</option>
	<option value="Province">Province</option>
	<option value="City">City</option>
	<option value="Channel">Channel</option>
	<option value="KeyAccount">APG</option>
	<option value="CM">CM</option>
	<option value="BM">BM</option>
	<option value="RD">RD</option>
	<option value="SR">SR</option>
	<option value="Customer">Account</option>

</select>
                                        </td>
                                        <td>
                                            <select name="ddlLvl2Grouping" id="ddlLvl2Grouping" style="width:200px;">
	<option value=""></option>
	<option value="Region">Region</option>
	<option selected="selected" value="Province">Province</option>
	<option value="City">City</option>
	<option value="Channel">Channel</option>
	<option value="KeyAccount">APG</option>
	<option value="CM">CM</option>
	<option value="BM">BM</option>
	<option value="RD">RD</option>
	<option value="SR">SR</option>
	<option value="Customer">Account</option>

</select>
                                        </td>
                                        <td>
                                            <select name="ddlLvl3Grouping" id="ddlLvl3Grouping" style="width:200px;">
	<option value=""></option>
	<option value="Region">Region</option>
	<option value="Province">Province</option>
	<option selected="selected" value="City">City</option>
	<option value="Channel">Channel</option>
	<option value="KeyAccount">APG</option>
	<option value="CM">CM</option>
	<option value="BM">BM</option>
	<option value="RD">RD</option>
	<option value="SR">SR</option>
	<option value="Customer">Account</option>

</select>
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
                                            <select name="ddlPeriod" id="ddlPeriod" style="width:220px;">
	<option value="Aug 2015">Aug 2015</option>
	<option value="Jul 2015">Jul 2015</option>
	<option value="Jun 2015">Jun 2015</option>
	<option value="May 2015">May 2015</option>
	<option value="Apr 2015">Apr 2015</option>
	<option value="Mar 2015">Mar 2015</option>
	<option value="Feb 2015">Feb 2015</option>
	<option value="Jan 2015">Jan 2015</option>
	<option value="Dec 2014">Dec 2014</option>
	<option value="Nov 2014">Nov 2014</option>
	<option value="Oct 2014">Oct 2014</option>
	<option value="Sep 2014">Sep 2014</option>
	<option value="Aug 2014">Aug 2014</option>
	<option value="Jul 2014">Jul 2014</option>
	<option value="Jun 2014">Jun 2014</option>
	<option value="May 2014">May 2014</option>
	<option value="Apr 2014">Apr 2014</option>
	<option value="Mar 2014">Mar 2014</option>
	<option value="Feb 2014">Feb 2014</option>
	<option value="Jan 2014">Jan 2014</option>
	<option value="Dec 2013">Dec 2013</option>
	<option value="Nov 2013">Nov 2013</option>
	<option value="Oct 2013">Oct 2013</option>
	<option value="Sep 2013">Sep 2013</option>
	<option value="Aug 2013">Aug 2013</option>
	<option value="Jul 2013">Jul 2013</option>
	<option value="Jun 2013">Jun 2013</option>
	<option value="May 2013">May 2013</option>
	<option value="Apr 2013">Apr 2013</option>
	<option value="Mar 2013">Mar 2013</option>
	<option value="Feb 2013">Feb 2013</option>
	<option value="Jan 2013">Jan 2013</option>
	<option value="Dec 2012">Dec 2012</option>
	<option value="Nov 2012">Nov 2012</option>
	<option value="Oct 2012">Oct 2012</option>
	<option value="Sep 2012">Sep 2012</option>

</select>
                                            &nbsp;<span id="lblErr_ddlPeriod" class="filterError"></span>
                                        </td>
                                    </tr>
                                    <tr id="tr_ddlPeriodTo" class="filterRow">
                                        <td>
                                            <span id="lbl_ddlPeriodTo" class="formLabel">To</span><span id="lblMand_ddlPeriodTo"></span>
                                        </td>
                                         <td>
                                            <select name="ddlPeriodTo" id="ddlPeriodTo" style="width:220px;">
	<option value="Aug 2015">Aug 2015</option>
	<option value="Jul 2015">Jul 2015</option>
	<option value="Jun 2015">Jun 2015</option>
	<option value="May 2015">May 2015</option>
	<option value="Apr 2015">Apr 2015</option>
	<option value="Mar 2015">Mar 2015</option>
	<option value="Feb 2015">Feb 2015</option>
	<option value="Jan 2015">Jan 2015</option>
	<option value="Dec 2014">Dec 2014</option>
	<option value="Nov 2014">Nov 2014</option>
	<option value="Oct 2014">Oct 2014</option>
	<option value="Sep 2014">Sep 2014</option>
	<option value="Aug 2014">Aug 2014</option>
	<option value="Jul 2014">Jul 2014</option>
	<option value="Jun 2014">Jun 2014</option>
	<option value="May 2014">May 2014</option>
	<option value="Apr 2014">Apr 2014</option>
	<option value="Mar 2014">Mar 2014</option>
	<option value="Feb 2014">Feb 2014</option>
	<option value="Jan 2014">Jan 2014</option>
	<option value="Dec 2013">Dec 2013</option>
	<option value="Nov 2013">Nov 2013</option>
	<option value="Oct 2013">Oct 2013</option>
	<option value="Sep 2013">Sep 2013</option>
	<option value="Aug 2013">Aug 2013</option>
	<option value="Jul 2013">Jul 2013</option>
	<option value="Jun 2013">Jun 2013</option>
	<option value="May 2013">May 2013</option>
	<option value="Apr 2013">Apr 2013</option>
	<option value="Mar 2013">Mar 2013</option>
	<option value="Feb 2013">Feb 2013</option>
	<option value="Jan 2013">Jan 2013</option>
	<option value="Dec 2012">Dec 2012</option>
	<option value="Nov 2012">Nov 2012</option>
	<option value="Oct 2012">Oct 2012</option>
	<option value="Sep 2012">Sep 2012</option>

</select>
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
                                                    <select name="ddlBrand" id="ddlBrand" style="width:220px;">
		<option value="0">All Brand</option>
		<option value="1">Colgate</option>
		<option value="13">Colgate others</option>
		<option value="3">Crest</option>
		<option value="7">Darlie</option>
		<option value="5">Lion</option>
		<option value="4">Listerine</option>
		<option value="8">Other</option>
		<option value="10">Plax</option>
		<option value="11">saky</option>
		<option value="6">Sensodyne</option>
		<option value="12">Smiclean</option>
		<option value="9">YNBY</option>
		<option value="2">Zhonghua</option>
		<option value="18">三笑</option>
		<option value="14">高露洁 2</option>
		<option value="15">高露洁 3</option>
		<option value="16">高露洁 4</option>
		<option value="17">高露洁 5</option>

	</select>
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
                                                    <select name="ddlSKU" id="ddlSKU" style="width:220px;">
		<option value="0">All SKU</option>
		<option value="a119">360全面口腔健康 - 修护牙釉质140克</option>
		<option value="a120">360全面口腔健康 - 修护牙釉质200克</option>
		<option value="a118">360全面口腔健康 - 修护牙釉质90克</option>
		<option value="a008">360全面口腔健康 - 健康牙龈140克</option>
		<option value="a009">360全面口腔健康 - 健康牙龈200克</option>
		<option value="a105 ">360全面口腔健康 - 健康牙龈90克 </option>
		<option value="a012">360全面口腔健康 - 健洁140克 </option>
		<option value="a013">360全面口腔健康 - 健洁200克</option>
		<option value="a202 ">360全面口腔健康 - 卓效护龈美白140克 </option>
		<option value="a201 ">360全面口腔健康 - 卓效护龈美白90克 </option>
		<option value="a011">360全面口腔健康 - 美白 200克</option>
		<option value="a010">360全面口腔健康 - 美白140克</option>
		<option value="a106 ">360全面口腔健康 - 美白90克 </option>
		<option value="a115">360全面口腔健康-健康牙龈140克3支装</option>
		<option value="a110">360全面口腔健康-健康牙龈200克2支装</option>
		<option value="a117">360全面口腔健康-健康牙龈90克6支装</option>
		<option value="a108 ">360全面口腔健康-备长炭120克 </option>
		<option value="a109 ">360全面口腔健康-备长炭180克 </option>
		<option value="a107 ">360全面口腔健康-备长炭90克 </option>
		<option value="a112">360全面口腔健康-美白200克2支装</option>
		<option value="a104">360卓效护龈牙膏140克</option>
		<option value="a111">360卓效护龈牙膏140克2支装</option>
		<option value="a103">360卓效护龈牙膏90克</option>
		<option value="a001">专效抗敏多重保护50克</option>
		<option value="a005">专效抗敏多重保护牙膏110克</option>
		<option value="a006">专效抗敏牙膏 110克</option>
		<option value="a004">专效抗敏牙釉质修护110克</option>
		<option value="a003">专效抗敏美白牙膏110克</option>
		<option value="a020">健白防蛀牙膏－140克</option>
		<option value="a021">健白防蛀牙膏－200克</option>
		<option value="a113">健白防蛀牙膏200克2支装</option>
		<option value="a122 ">健白防蛀牙膏－90克 </option>
		<option value="a204 ">儿童牙膏苹果味送赠品(2-5岁) 40克 </option>
		<option value="a203 ">儿童牙膏蓝莓味送赠品(2-5岁)40克 </option>
		<option value="a207 ">光感白(沁亮薄荷)牙膏170克</option>
		<option value="a208">光感白(沁亮薄荷)牙膏70克</option>
		<option value="a209">光感白(清悦薄荷)牙膏170克</option>
		<option value="a034">光感白沁亮薄荷牙膏113克</option>
		<option value="a102">光感白清悦薄荷70克</option>
		<option value="a033">光感白清悦薄荷牙膏113克</option>
		<option value="a024">全面防蛀冰凉薄荷牙膏-140 克</option>
		<option value="a116">全面防蛀冰凉薄荷牙膏140克3支装</option>
		<option value="a025">全面防蛀冰凉薄荷牙膏-250 克</option>
		<option value="a123 ">全面防蛀冰凉薄荷牙膏-90克 </option>
		<option value="a022">全面防蛀清新香型牙膏-140 克</option>
		<option value="a023">全面防蛀清新香型牙膏-250 克</option>
		<option value="a018">冰爽劲白牙膏－120克</option>
		<option value="a019">冰爽劲白牙膏－180克</option>
		<option value="a017">冰爽激凉牙膏－180克</option>
		<option value="a101">冰爽茶香牙膏－120 克</option>
		<option value="a016">冰爽茶香牙膏－180克</option>
		<option value="a014">冰爽薄荷牙膏－120克</option>
		<option value="a015">冰爽薄荷牙膏－180克</option>
		<option value="a114">冰爽薄荷牙膏180克2支装</option>
		<option value="a121 ">冰爽薄荷牙膏－90克</option>
		<option value="d001">所有高露洁牙膏</option>
		<option value="a035">所有高露洁牙膏</option>
		<option value="a002">抗敏清新彩条120克</option>
		<option value="a007">抗敏美白牙膏 120克</option>
		<option value="a028">草本牙膏140克</option>
		<option value="a026">超强牙膏140克-高钙</option>
		<option value="a027">超强牙膏200克-高钙</option>
		<option value="a031">防蛀健齿儿童牙膏水果香型(蜘蛛侠)40 克 </option>
		<option value="a032">防蛀健齿儿童牙膏草莓香型(芭比娃娃)40 克 </option>
		<option value="a030">防蛀美白牙膏140克</option>
		<option value="a205">高露洁劲白竹炭薄荷牙膏-120克</option>
		<option value="a206">高露洁劲白竹炭薄荷牙膏-180克</option>
		<option value="a210">高露洁劲白竹炭薄荷牙膏-90克（冰爽激凉牙膏90克）</option>
		<option value="a211">高露洁劲白茉莉花牙膏牙膏-90克</option>
		<option value="a029">高露洁草本牙膏200克</option>
		<option value="a037">中华健齿白牙膏155g</option>
		<option value="a036">中华双钙防蛀沁爽薄荷味170克</option>
		<option value="a038">中华皓清牙膏130g</option>
		<option value="a039">中华魔丽迅白冰极薄荷味100g</option>
		<option value="a040">所有中华牙膏</option>
		<option value="d005">所有中华牙膏</option>
		<option value="a052">佳洁士健康科学快速抗敏90g</option>
		<option value="a053">佳洁士健康科学新生护龈140g</option>
		<option value="a051">佳洁士全优7效牙膏140g</option>
		<option value="a050">佳洁士全优7效牙膏90g</option>
		<option value="a058">佳洁士双效炫白120g</option>
		<option value="a059">佳洁士双效炫白180g</option>
		<option value="a048">佳洁士海洋草本牙膏140g</option>
		<option value="a056">佳洁士茶爽炫白茉莉香型牙膏120g </option>
		<option value="a057">佳洁士茶爽炫白茉莉香型牙膏180g  </option>
		<option value="a047">佳洁士草本水晶140g</option>
		<option value="a060">佳洁士闪耀炫白牙膏116g</option>
		<option value="a601">佳洁士闪耀炫白牙膏140g</option>
		<option value="a054">佳洁士防蛀修护牙膏140g </option>
		<option value="a055">佳洁士防蛀修护牙膏200g</option>
		<option value="a061">所有佳洁士牙膏</option>
		<option value="d002">所有佳洁士牙膏</option>
		<option value="a049">盐白140克</option>
		<option value="a065">所有舒适达牙膏</option>
		<option value="d006">所有舒适达牙膏</option>
		<option value="a064">舒适达专业修复100g</option>
		<option value="a062">舒适达薄荷牙膏120g</option>
		<option value="a063">舒适达速效抗敏120g</option>
		<option value="a046">所有黑人牙膏</option>
		<option value="d003">所有黑人牙膏</option>
		<option value="a042">黑人双重薄荷牙膏175g</option>
		<option value="a041">黑人双重薄荷牙膏225g </option>
		<option value="a043">黑人茶倍健牙膏(薄荷)140g</option>
		<option value="a044">黑人超白牙膏140g</option>
		<option value="a045">黑人透心爽薄荷牙膏120g</option>
		<option value="d007">其他品牌牙膏</option>
		<option value="a066">所有其他牙膏</option>
		<option value="a067 ">所有云南白药牙膏</option>
		<option value="d004">所有云南白药牙膏</option>
		<option value="a068">所有舒客牙膏</option>
		<option value="a251">高露洁基础抗敏-美白</option>
		<option value="a250">高露洁基础抗敏-酷凉薄荷</option>

	</select>
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
                                            <select name="ddlDisplayType" id="ddlDisplayType" style="width:220px;">
	<option value="0">All Display</option>
	<option value="4">门店柱子</option>
	<option value="5">落地架</option>
	<option value="3">端架</option>
	<option value="12">牙刷陈列中心</option>
	<option value="8">牙刷多支装陈列架</option>
	<option value="7">挂条</option>
	<option value="9">所有的货架外陈列和嵌入式的主货架</option>
	<option value="2">小堆</option>
	<option value="1">大堆</option>
	<option value="10">其他</option>
	<option value="11">促销篮</option>
	<option value="6">侧挂架</option>

</select>
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
                                                 <select name="ddlKeyAccount" onchange="javascript:setTimeout(&#39;__doPostBack(\&#39;ddlKeyAccount\&#39;,\&#39;\&#39;)&#39;, 0)" id="ddlKeyAccount" style="width:220px;">
		<option selected="selected" value="0">All APG</option>
		<option value="-1">Key Account</option>
		<option value="28">A-BEST - SOUTH </option>
		<option value="29">A-BEST - WEST </option>
		<option value="30">AH HEFEI BAIDA HEJIAFU </option>
		<option value="1">AuChan</option>
		<option value="31">AUCHAN - EAST </option>
		<option value="32">AUCHAN - NORTH </option>
		<option value="105">AUCHAN - SOUTH</option>
		<option value="33">AUCHAN - WEST </option>
		<option value="34">BEIJING JINGKELONG </option>
		<option value="2">BJ HL</option>
		<option value="35">BJ HUALIAN </option>
		<option value="36">BJ HUALIAN - EAST </option>
		<option value="37">BJ HUALIAN - NORTH </option>
		<option value="38">BJ HUALIAN - SOUTH </option>
		<option value="39">BJ HUALIAN - WEST </option>
		<option value="3">BJ_Wumart</option>
		<option value="40">BUBUGAO </option>
		<option value="4">Carrefour</option>
		<option value="41">CARREFOUR -  EAST</option>
		<option value="43">CARREFOUR -  NORTH</option>
		<option value="45">CARREFOUR - WEST </option>
		<option value="48">CARREFOUR -SOUTH</option>
		<option value="107">CHENGDU HONGQI</option>
		<option value="50">CRC - EAST </option>
		<option value="55">CRC - SOUTH </option>
		<option value="58">DENNIS  </option>
		<option value="61">DIRECT - EAST - OTHERS </option>
		<option value="63">DIRECT - NORTH - OTHERS </option>
		<option value="65">DIRECT - SOUTH - OTHERS </option>
		<option value="68">DIRECT - WEST - OTHERS </option>
		<option value="16">Direct_East</option>
		<option value="5">Direct_North</option>
		<option value="19">Direct_South</option>
		<option value="14">Direct_West</option>
		<option value="18">Dist_East</option>
		<option value="6">Dist_North</option>
		<option value="20">Dist_South</option>
		<option value="15">Dist_West</option>
		<option value="70">DISTRIBUTOR - NORTH </option>
		<option value="71">DISTRIBUTOR - WEST </option>
		<option value="72">DISTRIBUTOR- EAST </option>
		<option value="73">DISTRIBUTOR -SOUTH </option>
		<option value="76">FJ XINHUADU SHIYE </option>
		<option value="79">GD DONGGUAN JIARONG </option>
		<option value="80">GD SHENZHEN SUIBAO </option>
		<option value="81">GX XINNANCHENG </option>
		<option value="83">HANGZHOU HUASHANG </option>
		<option value="84">HENAN DAZHANG </option>
		<option value="86">HUBEI WUHAN ZHONGBAI </option>
		<option value="89">JIA JIA YUE </option>
		<option value="91">JUSCO - SOUTH </option>
		<option value="93">JX NANCHANG HONGKELONG </option>
		<option value="95">LOTUS - EAST </option>
		<option value="96">LOTUS - NORTH </option>
		<option value="98">LOTUS - SOUTH </option>
		<option value="100">LOTUS - WEST </option>
		<option value="13">Metro</option>
		<option value="101">METRO - EAST </option>
		<option value="102">METRO - NORTH </option>
		<option value="99">METRO - SOUTH </option>
		<option value="97">METRO - WEST </option>
		<option value="10">NCT</option>
		<option value="106">NX XINHUA</option>
		<option value="27">other</option>
		<option value="103">PARKN SHOP - SOUTH </option>
		<option value="104">PARKN SHOP - WEST </option>
		<option value="21">PNS South</option>
		<option value="94">RRL - NORTH </option>
		<option value="92">RRL - SOUTH </option>
		<option value="90">RRL - WEST </option>
		<option value="11">RT_Mart</option>
		<option value="88">RT-MART - EAST </option>
		<option value="87">RT-MART - NORTH </option>
		<option value="85">RT-MART - SOUTH </option>
		<option value="82">RT-MART - WEST </option>
		<option value="17">Sam's Club</option>
		<option value="78">SD YINGZUO </option>
		<option value="77">SHANGHAI HUALIAN </option>
		<option value="75">SHIDAI </option>
		<option value="74">SHIJI LIANHUA - EAST </option>
		<option value="69">SHIJI LIANHUA - NORTH </option>
		<option value="67">SHIJI LIANHUA - WEST </option>
		<option value="66">SUGUO </option>
		<option value="64">SX QINGXU MEITEHAO </option>
		<option value="7">Tesco</option>
		<option value="62">TIANHONG - EAST </option>
		<option value="60">TIANHONG - NORTH </option>
		<option value="59">TIANHONG - SOUTH </option>
		<option value="57">TIANHONG - WEST </option>
		<option value="12">Trust-Mart</option>
		<option value="56">VANGUARD - NORTH </option>
		<option value="54">VANGUARD - SOUTH </option>
		<option value="53">VANGUARD - WEST </option>
		<option value="52">WAL MART </option>
		<option value="8">Wal't Mart</option>
		<option value="51">WH WUSHANG </option>
		<option value="49">WUHAN ZHONGBAI BIANMIN </option>
		<option value="9">Wumart</option>
		<option value="47">WUMART - EAST </option>
		<option value="46">WUMART - NORHT </option>
		<option value="44">WUXI HUIQUAN </option>
		<option value="109">YONGHUI - EAST</option>
		<option value="110">YONGHUI - NORTH</option>
		<option value="111">YONGHUI - SOUTH</option>
		<option value="108">YONGHUI - WEST</option>
		<option value="42">ZHONGSHAN YIJIAYI </option>

	</select>
                                                 &nbsp;<span id="lblErr_ddlKeyAccount" class="filterError"></span>
                                            </td>
                                        </tr>
                                        <tr id="tr_ddlCustomer" class="filterRow">
                                            <td>
                                                <span id="lbl_ddlCustomer" class="formLabel">Account</span><span id="lblMand_ddlCustomer"></span>
                                            </td>
                                            <td>
                                                 <select name="ddlCustomer" id="ddlCustomer" style="width:220px;">
		<option value="0">All Accounts</option>
		<option value="174">**SH 上海AuChan(沈阳店)</option>
		<option value="309">1+1连锁</option>
		<option value="288">7/11</option>
		<option value="431">A-BEST </option>
		<option value="432">AH HEFEI BAIDA HEJIAFU </option>
		<option value="310">Auchan</option>
		<option value="433">BEIJING JINGKELONG </option>
		<option value="1">BJ HL</option>
		<option value="347">BJ HUALIAN </option>
		<option value="2">BJHL</option>
		<option value="434">BUBUGAO </option>
		<option value="251">C4</option>
		<option value="3">C4 Anhui</option>
		<option value="4">C4 Beijing</option>
		<option value="220">C4 Changchun</option>
		<option value="218">C4 Changsha</option>
		<option value="175">C4 Chengdu</option>
		<option value="176">C4 Chongqing</option>
		<option value="217">C4 Dongguan</option>
		<option value="5">C4 Fujian</option>
		<option value="6">C4 Guangzhou</option>
		<option value="219">C4 Guiyang</option>
		<option value="7">C4 Haerbin</option>
		<option value="8">C4 Henan</option>
		<option value="9">C4 Hubei</option>
		<option value="10">C4 Jiangsu</option>
		<option value="252">C4 Jiangxi</option>
		<option value="289">C4 Kunming</option>
		<option value="11">C4 Liaoning</option>
		<option value="221">C4 Qingdao</option>
		<option value="12">C4 Shandong</option>
		<option value="13">C4 Shanghai</option>
		<option value="14">C4 Shenzhen</option>
		<option value="15">C4 Shijiazhuang</option>
		<option value="222">C4 Tianjin</option>
		<option value="16">C4 Urumuqi</option>
		<option value="17">C4 Zhejiang</option>
		<option value="311">Carrefour</option>
		<option value="18">Changchun HeKeLong C</option>
		<option value="19">ChangChun OuYaShangD</option>
		<option value="435">Chengdu hongqi </option>
		<option value="20">CRC</option>
		<option value="302">C-Store</option>
		<option value="348">DaShang JiTuan</option>
		<option value="436">Dashang Xinmate </option>
		<option value="437">DENNIS  </option>
		<option value="372">DIA</option>
		<option value="438">FJ XINHUADU SHIYE </option>
		<option value="439">GD DONGGUAN JIARONG </option>
		<option value="440">GD SHENZHEN SUIBAO </option>
		<option value="441">GX XINNANCHENG </option>
		<option value="442">HANGZHOU HUASHANG </option>
		<option value="443">HENAN DAZHANG </option>
		<option value="21">HLJ DaQing QingKeLon</option>
		<option value="22">Homeclub</option>
		<option value="444">HUBEI WUHAN ZHONGBAI </option>
		<option value="250">IDT</option>
		<option value="445">JIA JIA YUE </option>
		<option value="446">Jia Le Jia </option>
		<option value="224">Jusco</option>
		<option value="447">JX NANCHANG HONGKELONG </option>
		<option value="23">LN Dalian ChangLin W</option>
		<option value="24">LN DaLian JingMa</option>
		<option value="177">LN ShenYang AiKeJia ChaoShi</option>
		<option value="178">LN ShenYang HomeClub </option>
		<option value="25">Lottemart</option>
		<option value="26">Lotus</option>
		<option value="179">Meet ALL</option>
		<option value="253">Metro</option>
		<option value="464">NaN</option>
		<option value="448">NGS </option>
		<option value="180">North Wal-Mart</option>
		<option value="465">NX XINHUA</option>
		<option value="430">other</option>
		<option value="181">Others</option>
		<option value="449">PARKN SHOP</option>
		<option value="349">PNS - SOUTH</option>
		<option value="450">RRL </option>
		<option value="312">RT-Mart</option>
		<option value="313">Sam</option>
		<option value="254">Sam‘s</option>
		<option value="255">Sam’s Club</option>
		<option value="451">SAMS </option>
		<option value="452">SD YINGZUO </option>
		<option value="453">SHANGHAI HUALIAN </option>
		<option value="454">SHIDAI </option>
		<option value="27">Shiji LianHua</option>
		<option value="422">Suguo</option>
		<option value="455">SX QINGXU MEITEHAO </option>
		<option value="28">Tangshan HuaSheng</option>
		<option value="256">Tesco</option>
		<option value="456">TIANHONG </option>
		<option value="29">Trust-Mart</option>
		<option value="423">Vanguard</option>
		<option value="328">WAL MART</option>
		<option value="30">Wal't Mart</option>
		<option value="457">WH WUSHANG </option>
		<option value="458">WUHAN ZHONGBAI BIANMIN </option>
		<option value="350">WUMART</option>
		<option value="314">Wu-Mart</option>
		<option value="459">WUXI HUIQUAN </option>
		<option value="460">Xin Shi Ji Chaoshi </option>
		<option value="461">Yiteng Yanghuatang &amp; Wudongfeng </option>
		<option value="462">YongHui </option>
		<option value="463">ZHONGSHAN YIJIAYI </option>
		<option value="373">万众连锁</option>
		<option value="31">万佳连锁超市</option>
		<option value="374">万家福连锁</option>
		<option value="332">万联</option>
		<option value="225">三和连锁</option>
		<option value="32">三江购物俱乐部股份</option>
		<option value="33">上海华联</option>
		<option value="290">上海华联连锁</option>
		<option value="34">上海文峰购物广场有限公司</option>
		<option value="257">世纪华联连锁</option>
		<option value="375">世纪联众</option>
		<option value="35">世纪联华</option>
		<option value="36">世纪联华超市</option>
		<option value="37">东北区RTM</option>
		<option value="38">东北区Tesco</option>
		<option value="39">东区RTM</option>
		<option value="40">东区Tesco</option>
		<option value="258">东方冷库连锁</option>
		<option value="376">东方连锁</option>
		<option value="335">东泰</option>
		<option value="41">东港城超级市场</option>
		<option value="259">东莞市东孚商贸有限公司</option>
		<option value="377">中新超市连锁</option>
		<option value="42">中百仓储超市</option>
		<option value="182">中营购物连锁</option>
		<option value="43">丰和</option>
		<option value="359">丰采超市</option>
		<option value="378">临沂市东方系统</option>
		<option value="379">临沂市桃源超市系统</option>
		<option value="226">临海耀达连锁</option>
		<option value="44">丹尼斯</option>
		<option value="260">乐万家购物中心连锁</option>
		<option value="291">乐万家连锁超市</option>
		<option value="360">乐兴百货</option>
		<option value="45">乐天</option>
		<option value="303">乐天玛特</option>
		<option value="227">乐玛特购物连锁</option>
		<option value="183">乐玛特连锁</option>
		<option value="361">乐福广场</option>
		<option value="184">乐购</option>
		<option value="185">九龙连锁超市</option>
		<option value="380">五洲华联</option>
		<option value="46">京客隆商业集团股份</option>
		<option value="419">京鹏日化</option>
		<option value="47">人人乐</option>
		<option value="48">人人乐商业</option>
		<option value="49">伊藤洋华堂</option>
		<option value="424">余姚华联</option>
		<option value="50">佳惠超市</option>
		<option value="51">供销超市</option>
		<option value="186">保定惠友连锁</option>
		<option value="340">信佳连锁</option>
		<option value="52">信和商业连锁</option>
		<option value="327">信誉楼</option>
		<option value="381">信阳西亚连锁</option>
		<option value="261">六和连锁</option>
		<option value="53">兴客隆超市</option>
		<option value="54">兴福兴超市</option>
		<option value="315">其他</option>
		<option value="55">农工商连锁超市</option>
		<option value="56">利客隆超市</option>
		<option value="351">利群利客来</option>
		<option value="187">利群集团</option>
		<option value="188">北京北京</option>
		<option value="216">北京华联</option>
		<option value="262">北京天虹商业管理有限公司 </option>
		<option value="57">北京物美</option>
		<option value="358">北京超市发连锁股份有限公司</option>
		<option value="189">北国商城股份有限公司</option>
		<option value="228">北国超市</option>
		<option value="382">北斗星</option>
		<option value="58">北方国贸 </option>
		<option value="304">华东区RTM</option>
		<option value="59">华东物美</option>
		<option value="60">华中区RTM</option>
		<option value="263">华亿购物中心连锁</option>
		<option value="190">华亿购物连锁</option>
		<option value="229">华亿超市</option>
		<option value="292">华亿连锁超市</option>
		<option value="230">华北区RTM</option>
		<option value="191">华北区Tesco</option>
		<option value="305">华南区RTM</option>
		<option value="316">华商</option>
		<option value="61">华堂</option>
		<option value="62">华大贸易</option>
		<option value="63">华强</option>
		<option value="64">华普</option>
		<option value="65">华润万家</option>
		<option value="264">华润万家有限公司</option>
		<option value="66">华润万家生活超市</option>
		<option value="67">华润万家超市</option>
		<option value="68">华润万家超市-江苏</option>
		<option value="69">华润万家超市-河南</option>
		<option value="70">华润万家超市-浙江</option>
		<option value="71">华联吉买盛购物中心</option>
		<option value="425">华联超市</option>
		<option value="72">南区RTM</option>
		<option value="73">南区Tesco</option>
		<option value="265">南区Walmart </option>
		<option value="192">南城百货</option>
		<option value="383">南大超市连锁</option>
		<option value="74">南天大新集团</option>
		<option value="75">南海大沥泰广商场</option>
		<option value="266">博美超市连锁</option>
		<option value="76">友好集团股份</option>
		<option value="193">双流快乐购物连锁</option>
		<option value="194">台州华联</option>
		<option value="77">台州华联超市</option>
		<option value="231">台州华联连锁</option>
		<option value="78">合力超市</option>
		<option value="79">合肥娴洁商贸有限公司</option>
		<option value="232">吉之岛</option>
		<option value="384">和大福超市</option>
		<option value="80">唐久便利</option>
		<option value="81">嘉荣商贸</option>
		<option value="362">国惠康</option>
		<option value="82">国泰</option>
		<option value="83">国芳综超连锁</option>
		<option value="267">国货</option>
		<option value="268">城市购物连锁</option>
		<option value="84">壹加壹商业连锁</option>
		<option value="85">大商</option>
		<option value="233">大商新玛特连锁</option>
		<option value="385">大张实业</option>
		<option value="269">大润发</option>
		<option value="86">天客隆</option>
		<option value="87">天恒</option>
		<option value="234">天虹</option>
		<option value="88">天虹商场</option>
		<option value="89">奥士凯</option>
		<option value="342">奥德隆</option>
		<option value="90">好又多</option>
		<option value="235">好又多超市</option>
		<option value="236">好又多连锁</option>
		<option value="91">好家乡超市</option>
		<option value="92">好家乡连锁</option>
		<option value="93">好家乡连锁超市</option>
		<option value="195">威海家家悦</option>
		<option value="94">季华货仓商场</option>
		<option value="95">宁波加贝超市</option>
		<option value="237">宁波加贝超市连锁</option>
		<option value="96">宁波新江厦超市</option>
		<option value="196">安岳家和超市连锁</option>
		<option value="97">安德利</option>
		<option value="270">安德利连锁</option>
		<option value="98">家乐福</option>
		<option value="363">家家乐超市</option>
		<option value="99">家得利超市</option>
		<option value="426">家润多</option>
		<option value="100">家润多超市</option>
		<option value="101">家美家</option>
		<option value="102">宾隆超市</option>
		<option value="103">富万家超市</option>
		<option value="386">小型超市</option>
		<option value="333">山东圣豪</option>
		<option value="238">山东银座</option>
		<option value="197">山西美特好</option>
		<option value="104">岁宝百货</option>
		<option value="387">岛内价</option>
		<option value="198">崇州三羊开泰连锁</option>
		<option value="105">崇州九龙超市连锁</option>
		<option value="106">平和堂</option>
		<option value="271">广东吉之岛天贸百货有限公司</option>
		<option value="199">广西利客隆超市</option>
		<option value="326">建都</option>
		<option value="388">开化利群</option>
		<option value="389">得一</option>
		<option value="293">德惠超市</option>
		<option value="107">德惠超市连锁</option>
		<option value="200">德惠连锁超市</option>
		<option value="352">心连心超市</option>
		<option value="108">心连心连锁</option>
		<option value="294">恒生</option>
		<option value="109">成商集团股份武侯超市</option>
		<option value="110">成都百货大楼</option>
		<option value="111">房山华冠</option>
		<option value="329">振华商厦</option>
		<option value="364">捷龙购物广场</option>
		<option value="317">摩尔</option>
		<option value="272">摩尔华盛连锁</option>
		<option value="295">摩尔百盛</option>
		<option value="273">摩尔百盛连锁</option>
		<option value="112">新一佳超市</option>
		<option value="390">新世界购物广场</option>
		<option value="239">新世纪</option>
		<option value="391">新世纪超市</option>
		<option value="392">新乡儿童超市连锁</option>
		<option value="393">新乡华隆超市连锁</option>
		<option value="394">新乡金城连锁</option>
		<option value="113">新华都购物广场股份</option>
		<option value="114">新合作家佳乐</option>
		<option value="353">新星</option>
		<option value="343">新玛特</option>
		<option value="201">新郑万佳</option>
		<option value="274">新郑万佳连锁</option>
		<option value="395">无</option>
		<option value="354">日新超市</option>
		<option value="275">时代摩尔购物连锁</option>
		<option value="276">昌平新世纪</option>
		<option value="396">明湖</option>
		<option value="397">明豪超市</option>
		<option value="115">易买得超市</option>
		<option value="116">易买得连锁超市</option>
		<option value="296">易初莲花</option>
		<option value="117">易初莲花连锁超市</option>
		<option value="118">星力超市</option>
		<option value="365">有荣</option>
		<option value="119">有荣配销</option>
		<option value="120">杭州联华华商集团</option>
		<option value="277">桂湖摩尔连锁</option>
		<option value="306">梅尼百货</option>
		<option value="366">梅尼超市</option>
		<option value="202">欧尚</option>
		<option value="121">欧尚(中国)投资</option>
		<option value="278">欧尚(中国)投资有限公司</option>
		<option value="203">欧玲珑超市连锁</option>
		<option value="122">正道思达连锁商业</option>
		<option value="123">步步高商业连锁股份</option>
		<option value="240">武汉中百</option>
		<option value="241">武汉武商</option>
		<option value="367">民乐福</option>
		<option value="124">民润农产品</option>
		<option value="125">永旺</option>
		<option value="126">永辉</option>
		<option value="127">永辉超市</option>
		<option value="128">永辉超市连锁</option>
		<option value="398">汇丰商贸</option>
		<option value="428">汉中华盛连锁超市</option>
		<option value="399">汉中天天超市</option>
		<option value="429">汉中桃心岛连锁超市</option>
		<option value="400">汉中镇巴心连心超市</option>
		<option value="204">沃尔玛</option>
		<option value="279">沃尔玛(中国)投资有限公司</option>
		<option value="129">河北保定惠友连锁超市</option>
		<option value="205">河北天客隆商业有限公司(一店)</option>
		<option value="130">河南大商新玛特</option>
		<option value="131">河南开封三毛</option>
		<option value="132">河南郑州世纪联华</option>
		<option value="418">泃阳小燕</option>
		<option value="133">法宝</option>
		<option value="242">洪客隆</option>
		<option value="280">济南华联</option>
		<option value="341">淄博商厦</option>
		<option value="344">淄博惠仟佳</option>
		<option value="281">深圳市百佳华百货有限公司</option>
		<option value="134">温州人本超市</option>
		<option value="135">温州民丰超市</option>
		<option value="136">温州永丰超市</option>
		<option value="368">湾畔百货</option>
		<option value="330">潍坊中百大厦系统</option>
		<option value="282">潍坊佳乐家系统</option>
		<option value="334">潍坊百货大楼连锁</option>
		<option value="337">烟台振华商场</option>
		<option value="345">爱客多</option>
		<option value="137">爱家超市</option>
		<option value="138">爱家超市集团</option>
		<option value="346">特信</option>
		<option value="139">玉环六和超市</option>
		<option value="401">王一实业集团衡阳香江百货有限公司</option>
		<option value="140">王府井</option>
		<option value="369">瑞丰园</option>
		<option value="402">甘肃天水宝商佳美佳连锁超市</option>
		<option value="141">百事佳亨晖商贸</option>
		<option value="142">百佳</option>
		<option value="143">百佳华百货</option>
		<option value="206">百佳超市</option>
		<option value="243">百佳超级市场</option>
		<option value="403">百分百连锁</option>
		<option value="404">百和超市</option>
		<option value="144">百大集团合家福百大</option>
		<option value="370">百姓超市</option>
		<option value="145">百家惠商贸</option>
		<option value="307">百惠</option>
		<option value="146">百杨</option>
		<option value="355">百汇购物中心</option>
		<option value="297">百汇超市</option>
		<option value="147">百货大楼股份</option>
		<option value="339">益康连锁超市</option>
		<option value="207">石家庄北人集团连锁</option>
		<option value="405">福泰隆连锁</option>
		<option value="208">简阳美好家园连锁</option>
		<option value="406">红塔连锁</option>
		<option value="148">红府超市</option>
		<option value="244">红府超市连锁</option>
		<option value="149">红旗</option>
		<option value="298">红旗连锁</option>
		<option value="209">红旗连锁批发</option>
		<option value="210">红运来连锁</option>
		<option value="299">红运连锁超市</option>
		<option value="150">统一优玛特</option>
		<option value="151">统一优马特</option>
		<option value="152">统一银座</option>
		<option value="283">维客</option>
		<option value="407">绿源</option>
		<option value="153">置禾商业连锁</option>
		<option value="211">美好家园</option>
		<option value="245">美好家园连锁</option>
		<option value="154">美好家园连锁超市</option>
		<option value="300">美特好</option>
		<option value="301">联华超市</option>
		<option value="155">联华超市股份</option>
		<option value="318">联合一百</option>
		<option value="212">联合一百连锁</option>
		<option value="156">胶南糖酒 </option>
		<option value="157">苏果超市</option>
		<option value="420">荣盛旗</option>
		<option value="158">西单</option>
		<option value="159">西太华连锁超市</option>
		<option value="421">诚盛百佳</option>
		<option value="466">诸暨一百</option>
		<option value="213">资阳好人家连锁</option>
		<option value="214">资阳龙汇连锁</option>
		<option value="160">辽宁大连大红福</option>
		<option value="284">辽宁大连好易家</option>
		<option value="161">辽宁沈阳兴隆</option>
		<option value="408">辽宁鞍山佳泰</option>
		<option value="409">辽宁鞍山泰纳隆</option>
		<option value="410">辽宁鞍山百姓</option>
		<option value="223">连锁店</option>
		<option value="162">连锁店Store Hierarchy (DT Chain Store+IDT Chain Store)</option>
		<option value="163">通程万惠</option>
		<option value="356">邹平联华</option>
		<option value="246">郑州世纪联华连锁</option>
		<option value="247">重客隆</option>
		<option value="285">重庆百货大楼股份</option>
		<option value="411">重百超市</option>
		<option value="412">金华联连锁</option>
		<option value="164">金声超市</option>
		<option value="427">金玛</option>
		<option value="357">金盛隆（连锁）百货</option>
		<option value="371">金鹏百货</option>
		<option value="413">铁岭兴隆</option>
		<option value="414">银川新华百货连锁超市</option>
		<option value="286">银座</option>
		<option value="338">长江汇泉</option>
		<option value="248">长沙人人乐</option>
		<option value="249">长沙天虹百货</option>
		<option value="415">阜阳华联集团股份</option>
		<option value="165">陕西民生家乐投资有限公司</option>
		<option value="166">陕西省军人服务社</option>
		<option value="167">陕西老百姓医药连锁超市</option>
		<option value="168">陕西隆顺和街坊集市有限公司</option>
		<option value="319">青岛国货集团</option>
		<option value="320">青岛家佳源集团</option>
		<option value="321">青岛维客</option>
		<option value="215">青白江博美超市连锁</option>
		<option value="169">顺天府</option>
		<option value="308">顺客隆</option>
		<option value="170">首航国力商贸</option>
		<option value="336">香江百货</option>
		<option value="171">香江百货超市</option>
		<option value="322">鸿通超市</option>
		<option value="323">鹏泰连锁</option>
		<option value="324">麦凯乐</option>
		<option value="287">麦德龙</option>
		<option value="331">黄岛国货</option>
		<option value="172">黄岩百货</option>
		<option value="325">鼎润连锁系统</option>
		<option value="416">齐齐哈尔正源超市</option>
		<option value="417">齐齐哈尔百花超市</option>
		<option value="173">龙田贸易</option>

	</select>
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
                                                <select name="ddlProvince" onchange="javascript:setTimeout(&#39;__doPostBack(\&#39;ddlProvince\&#39;,\&#39;\&#39;)&#39;, 0)" id="ddlProvince" style="width:220px;">
		<option selected="selected" value="0">All Provinces in selected region</option>
		<option value="20">Anhui</option>
		<option value="1">Beijing</option>
		<option value="11">Chongqing</option>
		<option value="22">Fujian</option>
		<option value="15">Gansu</option>
		<option value="26">Guangdong</option>
		<option value="10">Guangxi</option>
		<option value="13">Guizhou</option>
		<option value="28">Hainan</option>
		<option value="3">Hebei</option>
		<option value="8">Heilongjiang</option>
		<option value="21">Henan</option>
		<option value="24">Hubei</option>
		<option value="25">Hunan</option>
		<option value="18">Jiangsu</option>
		<option value="23">Jiangxi</option>
		<option value="7">Jilin</option>
		<option value="6">Liaoning</option>
		<option value="5">Neimenggu</option>
		<option value="29">Ningxia</option>
		<option value="9">Shandong</option>
		<option value="17">Shanghai</option>
		<option value="4">Shanxi</option>
		<option value="14">Shanxi</option>
		<option value="12">Sichuan</option>
		<option value="2">Tianjin</option>
		<option value="16">Xinjiang</option>
		<option value="27">Yunnan</option>
		<option value="19">Zhejiang</option>

	</select>
                                                &nbsp;<span id="lblErr_ddlProvince" class="filterError"></span>
                                            </td>
                                        </tr>
                                        <tr id="tr_ddlCity" class="filterRow">
                                            <td>
                                                <span id="lbl_ddlCity" class="formLabel">City</span><span id="lblMand_ddlCity"></span>
                                            </td>
                                            <td>
                                                <select name="ddlCity" id="ddlCity" style="width:220px;">
		<option value="0">All Cities in selected region</option>
		<option value="-4">All Tier C cities</option>
		<option value="-3">All Tier B cities</option>
		<option value="-2">All Tier A cities</option>
		<option value="-1">All Key cities</option>
		<option value="71">Anqing</option>
		<option value="72">Anshan</option>
		<option value="149">anyang</option>
		<option value="3">Baoding</option>
		<option value="73">Baotou</option>
		<option value="74">Beihai</option>
		<option value="1">Beijing</option>
		<option value="147">bengbu</option>
		<option value="75">Cangzhou</option>
		<option value="11">Changchun</option>
		<option value="46">Changde</option>
		<option value="45">Changsha</option>
		<option value="26">Changzhou</option>
		<option value="76">Chaozhou</option>
		<option value="18">Chengdu</option>
		<option value="159">chenzhou</option>
		<option value="17">Chongqing</option>
		<option value="10">Dalian</option>
		<option value="77">Datong</option>
		<option value="78">Dawukou</option>
		<option value="143">dazhou</option>
		<option value="144">deyang</option>
		<option value="50">Dongguan</option>
		<option value="132">dongying</option>
		<option value="47">Foshan</option>
		<option value="79">Fushun</option>
		<option value="81">Fuyang</option>
		<option value="42">Fuzhou-fj</option>
		<option value="80">Fuzhou-jx</option>
		<option value="82">Ganzhou</option>
		<option value="48">Guangzhou</option>
		<option value="16">Guilin</option>
		<option value="19">Guiyang</option>
		<option value="84">Haikou</option>
		<option value="83">Hami</option>
		<option value="6">Handan</option>
		<option value="33">Hangzhou</option>
		<option value="85">Hanzhong</option>
		<option value="12">Harbin</option>
		<option value="86">Hechi</option>
		<option value="38">Hefei</option>
		<option value="87">Hengyang</option>
		<option value="8">Hohhot</option>
		<option value="61">Huaian</option>
		<option value="152">huaihua</option>
		<option value="150">huainan</option>
		<option value="155">huanggang</option>
		<option value="89">Huangshi</option>
		<option value="90">Huizhou</option>
		<option value="88">Huzhou</option>
		<option value="53">Jiangmen</option>
		<option value="93">Jiangyou</option>
		<option value="92">Jiaxing</option>
		<option value="91">Jilin</option>
		<option value="14">Jinan</option>
		<option value="96">Jingmen</option>
		<option value="139">jingzhou</option>
		<option value="94">Jinhua</option>
		<option value="57">Jining</option>
		<option value="95">Jinzhou</option>
		<option value="97">Jiujiang</option>
		<option value="55">Kunming</option>
		<option value="25">Kunshan</option>
		<option value="153">langfang</option>
		<option value="21">Lanzhou</option>
		<option value="68">Leshan</option>
		<option value="62">Lianyungang</option>
		<option value="158">liaocheng</option>
		<option value="98">Lijiang</option>
		<option value="100">Linfen</option>
		<option value="101">Linyi</option>
		<option value="99">Lishui</option>
		<option value="138">liuzhou</option>
		<option value="102">Longyan</option>
		<option value="103">Luoyang</option>
		<option value="145">luzhou</option>
		<option value="104">Maanshan</option>
		<option value="105">Maoming</option>
		<option value="106">Meizhou</option>
		<option value="69">Mianyang</option>
		<option value="107">Mudanjiang</option>
		<option value="40">Nanchang</option>
		<option value="70">Nanchong</option>
		<option value="30">Nanjing</option>
		<option value="15">Nanning</option>
		<option value="29">Nantong</option>
		<option value="134">nanyang</option>
		<option value="34">Ningbo</option>
		<option value="157">panzhihua</option>
		<option value="108">Puer</option>
		<option value="109">Puning</option>
		<option value="13">Qingdao</option>
		<option value="111">Qingyuan</option>
		<option value="110">Qiqihaer</option>
		<option value="43">Quanzhou</option>
		<option value="113">Qujing</option>
		<option value="112">Quzhou</option>
		<option value="114">Ruian</option>
		<option value="23">Shanghai</option>
		<option value="135">shangqiu</option>
		<option value="156">shangrao</option>
		<option value="52">Shantou</option>
		<option value="115">Shaoxing</option>
		<option value="9">Shenyang</option>
		<option value="49">Shenzhen</option>
		<option value="4">Shijiazhuang</option>
		<option value="140">shiyan</option>
		<option value="116">Shuangyashan</option>
		<option value="137">suqian</option>
		<option value="24">Suzhou</option>
		<option value="133">taian</option>
		<option value="7">Taiyuan</option>
		<option value="63">Taizhou-js</option>
		<option value="35">Taizhou-zj</option>
		<option value="5">Tangshan</option>
		<option value="2">Tianjin</option>
		<option value="117">Tianshui</option>
		<option value="118">Tongliao</option>
		<option value="160">tongren</option>
		<option value="22">Urumchi</option>
		<option value="58">Weifang</option>
		<option value="56">Weihai</option>
		<option value="37">Wenzhou</option>
		<option value="44">Wuhan</option>
		<option value="148">wuhu</option>
		<option value="27">Wuxi</option>
		<option value="119">Wuzhou</option>
		<option value="41">Xiamen</option>
		<option value="20">Xi'an</option>
		<option value="141">xiangfan</option>
		<option value="65">Xiangtan</option>
		<option value="142">xiaogan</option>
		<option value="36">Xiaoshan</option>
		<option value="154">xichang</option>
		<option value="120">Xinxiang</option>
		<option value="121">Xinyang</option>
		<option value="31">Xuzhou</option>
		<option value="32">Yancheng</option>
		<option value="28">Yangzhou</option>
		<option value="59">Yantai</option>
		<option value="122">Yibin</option>
		<option value="123">Yichang</option>
		<option value="124">Yinchuan</option>
		<option value="66">Yongzhou</option>
		<option value="126">Yueyang</option>
		<option value="151">yulin</option>
		<option value="125">Yuxi</option>
		<option value="127">Zhanjiang</option>
		<option value="128">Zhaoqing</option>
		<option value="39">Zhengzhou</option>
		<option value="64">Zhenjiang</option>
		<option value="51">Zhongshan</option>
		<option value="136">zhoukou</option>
		<option value="129">Zhuhai</option>
		<option value="130">Zhumadian</option>
		<option value="67">Zhuzhou</option>
		<option value="60">Zibo</option>
		<option value="146">ziyang</option>
		<option value="131">Zunyi</option>

	</select>
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
                            <img alt="loading" src="${contextPath}/colgate2/Images/loader.gif">
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
                            <img alt="loading" src="${contextPath}/colgate2/Images/loader.gif">
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
	<div><table width="100%" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td align="right"><img src="${contextPath}/colgate2/css/WebResource(1).axd"></td><td>&nbsp;</td><td align="left"><span class="loading">This page is Loading, please wait....</span></td></tr></tbody></table></div>
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