<html>
<head>
<#include "/common/taglibs.ftl" />
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
<link href="${contextPath}/colgate2/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css">
<style type="text/css">
    #distributionBox .jqplot-grid-canvas, #shareOfDMBox .jqplot-grid-canvas, #shareOfShelfBox .jqplot-grid-canvas
    {
        display: none;
    }
    .jqplot-point-label
    {
        font-size: 12px;
        font-weight: bold;
        font-family: Rockwell, Arial, Sans-Serif;
        color: #c00800;
    }
</style>
<title>Colgate In-Store  Tracking</title> 
    <script type="text/javascript" src="${contextPath}/colgate2/js/jquery-1.7.2.min.js"></script>
    <script src="${contextPath}/colgate2/js/jquery.scrollTo-min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${contextPath}/colgate2/js/Common.js"></script>
    <script type="text/javascript" src="${contextPath}/colgate2/js/raphael-min.js"></script>
    <!--[if lt IE 9]><script src="Scripts/jqplot/excanvas.min.js" type="text/javascript"></script><![endif]-->
    <script src="${contextPath}/colgate2/js/jquery.jqplot.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/utilities.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/jqplot.barRenderer.min.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/jqplot.categoryAxisRenderer.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/jqplot.pointLabels.min.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/jqplot.highlighter.min.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/chart.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/dashboard_revamped.js" type="text/javascript"></script>
    <script src="${contextPath}/colgate2/js/overView.js?cVer=${cVer}" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/showLoading.css?cVer=${cVer}">
    <script type="text/javascript" src="${contextPath}/js/jquery.showLoading.min.js?cVer=${cVer}"></script>
    
    <script type="text/javascript">
        var colorArray = ["#7e0303", "#df6a00", "#f9ad61", "#fcdcad"];
        var colorObject = new Object();
        colorObject["Toothpaste"] = "#7e0303";
        colorObject["Toothbrush"] = "#df6a00";
        colorObject["Mouthwash"] = "#f9ad61";
       

        function refresh() {
			loadOverView();
        }

        $(document).ready(function () {
           loadOverView();
        });
    </script>
	<link href="${contextPath}/colgate2/css/buttons.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/buttonsCN.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/dashboard.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/DataEntry.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/jquery.lightbox-0.5.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/messageBox.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/overview.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/RBControls.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/report.css" type="text/css" rel="stylesheet">
	<link href="${contextPath}/colgate2/css/Site.css" type="text/css" rel="stylesheet">
</head>
<body style="background-image:none;background-color:White;">
<form method="post" action="" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="tm_HiddenField" id="tm_HiddenField" value=";;AjaxControlToolkit, Version=3.5.40412.0, Culture=neutral, PublicKeyToken=28f01b0e84b6d53e:zh-CN:065e08c0-e2d1-42ff-9483-e5c14441b311:5546a2b:475a4ef5:d2e10b12:effe2a26:37e2e5c9:5a682656:f9029856:1d3ed089:d1a1d569:497ef277:a43b07eb:751cdd15:dfad98a5:3cf12cf1">
<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="">

	<input type="hidden" id="argChainIds" name="argChainIds" value="${filterVo.argChainIds}" />
	<input type="hidden" id="argAccountIds" name="argAccountIds" value="${filterVo.argAccountIds}" />
	<input type="hidden" id="province" name="province" value="${filterVo.province}" />
	<input type="hidden" id="city" name="city" value="${filterVo.city}" />
	<input type="hidden" id="argMonthId" name="argMonthId" value="${filterVo.argMonthId}" />
	
	<input type="hidden" id="argFilterUserIds" name="argFilterUserIds" value="${filterVo.argFilterUserIds}" />
	<input type="hidden" id="argFilterStructureIds" name="argFilterStructureIds" value="${filterVo.argFilterStructureIds}" />
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
<script src="${contextPath}/colgate2/css/Overview.aspx" type="text/javascript"></script>
<div class="aspNetHidden">
	<input type="hidden" id="_root" name="_root" value="${contextPath}" />
	
</div>
    <script type="text/javascript">
//<![CDATA[
Sys.WebForms.PageRequestManager._initialize('tm', 'form1', ['tupOverview','upOverview','tupAPG','upAPG','tupRegion','upRegion'], [], [], 90, '');
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
                                    <input type="button" value="Overview" onclick="window.location.href='${contextPath}/colgate/overView/'" class="NewMenuButtonSelected">
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
            <input type="hidden" name="hfDashboardId" id="hfDashboardId" value="5001">
            <input type="hidden" name="hfDashboardName" id="hfDashboardName" value="Overview">
            <br>
            <div>
                <div class="sideFilterPanel">
                   
                            <div class="sideFilterPanelTop" style="padding-top: 12px;">
                                <span id="lblDashboardFilterTitle">Overview</span>
                            </div>
                            <div class="sideFilterPanelMid">
                                <div style="padding: 0px 0px 5px 0px; text-align: center;">
                                    <div style="font-size: 14px; font-weight: bold;">
                                        # STORES AUDITED
                                    </div>
                                    <div style="padding: 0px 0px 0px 0px; text-align: center; font-weight: bold; font-size: 60px;">
                                    
                                        <span id="_lblNumOfStores">
                                        	<!-- 
                                        	
                                        	-->
                                        </span>
                                    </div>
                                </div>
                                <div id="upOverview">
	
                                <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_cbChannel">
                                    
<div class="RBDropdownList">    
        
    <input type="hidden" name="cbChannel$hfTotalNoOfItems" id="cbChannel_hfTotalNoOfItems" value="4">
    <input type="hidden" name="cbChannel$hfSelectedValue" id="cbChannel_hfSelectedValue">
    <input type="hidden" name="cbChannel$hfSelectedText" id="cbChannel_hfSelectedText">   
    <table id="cbChannel_ddl" border="0" cellpadding="0" cellspacing="0">
		<tbody><tr>
			<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                <div class="filterIconChannel">
                </div>
            </td>
			<td class="midColumn">
                <div class="selectedData">
                    <span id="lblSelectedcbChannel">All Channels</span>
                </div>
            </td>
			<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                <div id="cbChannel_dropdownIcon">
				
                    <div class="filterIconArrow">
                    </div>
                
			</div>
            </td>
		</tr>
	</tbody></table>
	
    
    <div id="cbChannel_PopupMenu" class="RBDropdownCheckboxListItems" style="display: none; visibility: hidden; position: absolute;">
        <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                    <td style="width:15px;" align="right">
                                
                    </td>
                    <td align="left">
                        <table id="cbChannel_Items" class="RBDropdownCheckboxListItem">
			<tbody id="channel_tbody">
			
			<!--
			<tr>
				<td>
				<span class="rbDdlCB">
					<input id="cbChannel_Items_0" type="checkbox" name="cbChannel$Items$0" checked="checked" onclick_me="" value="141">
					<label for="cbChannel_Items_0">Big Supermarket</label>
				</span>
				</td>
			</tr>
			 -->
			 
			 
			</tbody>
			
			</table>
                                
                    </td>
                </tr>
            </tbody></table>
                    
                    
        </div>
            
         
    
	</div>
        
    
</div>
                                </div>
                                    <div id="upAPG">
		
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlKeyAccount">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlKeyAccount$hfSelectedValue" id="ddlKeyAccount_hfSelectedValue" value="0">
            <input type="hidden" name="ddlKeyAccount$hfSelectedText" id="ddlKeyAccount_hfSelectedText" value="All APG">
            <table id="ddlKeyAccount_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconAPG">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlKeyAccount">All APG</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlKeyAccount_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlKeyAccount_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
				<div><input type="button" name="argChainIds_but" data="" value="All APG" id="ddlKeyAccount_rpItems_Item" class="RBDropdownListItem"></div>
	       </div>
    
</div>



                                            </div>
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlCustomer">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlCustomer$hfSelectedValue" id="ddlCustomer_hfSelectedValue" value="0">
            <input type="hidden" name="ddlCustomer$hfSelectedText" id="ddlCustomer_hfSelectedText" value="All Accounts">
            <table id="ddlCustomer_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconCustomer">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlCustomer">All Accounts</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlCustomer_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlCustomer_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
			
                <div>
                	<input type="button" name="argAccountIds_but" data="" value="All Accounts" id="ddlCustomer_rpItems_Item" class="RBDropdownListItem">
                </div>
                      
                    
                           <!--   
                        <div>
                            <input type="button" name="ddlCustomer$rpItems$ctl466$Item" value="龙田贸易" onclick_me="return __rbDDLSelectedItem(&#39;ddlCustomer_hme2&#39;,&quot;173&quot;,&quot;龙田贸易&quot;,&#39;ddlCustomer_hfSelectedValue&#39;,&#39;ddlCustomer_hfSelectedText&#39;,&#39;lblSelectedddlCustomer&#39;,0);" id="ddlCustomer_rpItems_Item_466" class="RBDropdownListItem">
                        </div>
            		 -->
                    
            
		</div>
        
    
</div>



                                            </div>
                                        
	</div>
                                    <div id="upRegion">
		
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_cbRegion">
                                                
<div class="RBDropdownList">    
        
    <input type="hidden" name="cbRegion$hfTotalNoOfItems" id="cbRegion_hfTotalNoOfItems" value="4">
    <input type="hidden" name="cbRegion$hfSelectedValue" id="cbRegion_hfSelectedValue">
    <input type="hidden" name="cbRegion$hfSelectedText" id="cbRegion_hfSelectedText">   
    <table id="cbRegion_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                <div class="filterIconRegion">
                </div>
            </td>
				<td class="midColumn">
                <div class="selectedData">
                    <span id="lblSelectedcbRegion">All Regions</span>
                </div>
            </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                <div id="cbRegion_dropdownIcon">
					
                    <div class="filterIconArrow">
                    </div>
                
				</div>
            </td>
			</tr>
		</tbody></table>
		
    
    <div id="cbRegion_PopupMenu" class="RBDropdownCheckboxListItems" style="display: none; visibility: hidden; position: absolute;">
			
        
        <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                    <td style="width:35px;" align="right">
                                
                    </td>
                    <td align="left">
                        <table id="cbRegion_Items" class="RBDropdownCheckboxListItem">
				
				<tbody id="cbRegion_PopupMenu_tbody">
				
				<!--
				<tr>
					<td><span class="rbDdlCB"><input id="cbRegion_Items_0" type="checkbox" name="cbRegion$Items$0" checked="checked" onclick_me="__rbCBLSelectedItem(&#39;cbRegion_Items&#39;,&#39;cbRegion_hfSelectedValue&#39;,&#39;cbRegion_hfSelectedText&#39;,&#39;lblSelectedcbRegion&#39;,&#39;cbRegion_hfTotalNoOfItems&#39;,&#39;All Regions&#39;,&#39;Select Region(s)&#39;);setTimeout(&#39;__doPostBack(\&#39;cbRegion$Items$0\&#39;,\&#39;\&#39;)&#39;, 0)" value="1"><label for="cbRegion_Items_0">East</label></span></td>
				</tr>
				 -->
				
			</tbody>
			
			</table>
                                
                    </td>
                </tr>
            </tbody></table>
                    
                    
        </div>
            
         
    
		</div>
        
    
</div>
                                            </div>
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlProvince">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlProvince$hfSelectedValue" id="ddlProvince_hfSelectedValue" value="0">
            <input type="hidden" name="ddlProvince$hfSelectedText" id="ddlProvince_hfSelectedText" value="All Provinces">
            <table id="ddlProvince_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconProvince">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlProvince">All Provinces</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlProvince_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlProvince_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
			
                
                       <!--
                    
                        <div>
                            <input type="button" name="ddlProvince$rpItems$ctl29$Item" value="Zhejiang" onclick_me="return __rbDDLSelectedItem(&#39;ddlProvince_hme2&#39;,&quot;19&quot;,&quot;Zhejiang&quot;,&#39;ddlProvince_hfSelectedValue&#39;,&#39;ddlProvince_hfSelectedText&#39;,&#39;lblSelectedddlProvince&#39;,1);" id="ddlProvince_rpItems_Item_29" class="RBDropdownListItem">
                        </div>
            		 -->
                    
            
		</div>
        
    
</div>



                                            </div>
                                            <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlCity">
                                                
<div class="RBDropdownList">
               
            <input type="hidden" name="ddlCity$hfSelectedValue" id="ddlCity_hfSelectedValue" value="0">
            <input type="hidden" name="ddlCity$hfSelectedText" id="ddlCity_hfSelectedText" value="All Cities">
            <table id="ddlCity_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconCity">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlCity">All Cities</span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlCity_dropdownIcon">
					
                            <div class="filterIconArrow">
                            </div>
                        
				</div>
                    </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlCity_PopupMenu" class="RBDropdownListItems" style="visibility: hidden; position: absolute; left: 501px; top: 458px; z-index: 1000; display: none;">
			
                	<!-- 
                        <div>
                            <input type="button" name="ddlCity$rpItems$ctl00$Item" value="All Cities" onclick_me="return __rbDDLSelectedItem(&#39;ddlCity_hme2&#39;,&quot;0&quot;,&quot;All Cities&quot;,&#39;ddlCity_hfSelectedValue&#39;,&#39;ddlCity_hfSelectedText&#39;,&#39;lblSelectedddlCity&#39;,0);" id="ddlCity_rpItems_Item_0" class="RBDropdownListItem">
                        </div>
            		-->
                    
            
		</div>
        
    
</div>



                                            </div>
                                        
	</div>
                                
</div>
                                <div style="text-align: center; padding-top: 25px; padding-bottom: 100px;">
                                    <input type="button" name="btnRefresh" value="" onclick="refresh()" id="btnRefresh" class="btnNewRefresh">
                                </div>
                            </div>
                            <div class="sideFilterPanelBtm">
                            </div>
                        
                </div>
                <div class="overviewPanel">

                    
<script type="text/javascript">
    
    function btnPeriodClicked(btn) {

        $('#ddlPeriod_hfSelectedValue').val(period);

        var val = $('#div_ddlPeriod input[type=submit].btnPeriodSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'btnPeriod');
        }
        $(btn).attr('class', 'btnPeriodSelected');
        refresh();
    }
    $(document).ready(function () {
        $('#btnPeriodRight').click(function () {
            $('#periodMask').scrollTo('+=232px', 800);
            periodClickCounter = periodClickCounter + 1;
            return false;
        });
        $('#btnPeriodLeft').click(function () {
            if (periodClickCounter > 0) {
                $('#periodMask').scrollTo('-=232px', 800);
                periodClickCounter = periodClickCounter - 1;
            }
            return false;
        });
        $('#periodMask').scrollTo('max', 800);

    });
</script>
<div style="padding-top: 12px; margin-bottom: 30px;background-color:White;" id="div_ddlPeriod">
        <input type="hidden" name="ddlPeriod$hfSelectedValue" id="ddlPeriod_hfSelectedValue" value="Jul 2015">
        <table border="0" cellpadding="0" cellspacing="0" id="periodTab">
            <tbody><tr>
                <td style="padding-right:10px;">
                    <div style="width:12px;">
                        <input type="button" id="btnPeriodLeft" class="btnPeriodLeft">
                    </div>
                                    
                </td>
                <td>
                    <div style="width:696px;overflow:hidden;" id="periodMask">
                        <div id="divCon" style="width: 8120px; min-width: 696px;">
                            <#list months as m>
								 <input type="button" name="ddlPeriod" data="${m.monthId}" value="${m.name}" class="<#if filterVo.argMonthId==m.monthId>btnPeriodSelected<#else>btnPeriod</#if>">
                            </#list>
                        </div>
                    </div>
                </td>
                <td style="padding-left:10px;">
                    <div style="width:12px;">
                        <input type="button" id="btnPeriodRight" class="btnPeriodRight">
                    </div>
                                    
                </td>
            </tr>
        </tbody></table>
                        
                       
    </div>

                    <div id="dbContents" style="display: block;">
                        <div style="height: 180px; width: 100%;">
                            <div style="width: 33%; border-right: solid 1px #eebfbf; float: left; height: 100%;">
                                <div>
                                    <span class="labelTitle">DISTRIBUTION</span>
                                </div>
                                <div id="distributionBox" style="height: 160px; width: 260px; margin-left: -20px; position: relative;" class="jqplot-target">
                                
                                </div>
                            </div>
                            <div style="width: 33%; border-right: solid 1px #eebfbf; float: left; height: 100%;">
                                <div style="margin-left: 10px;">
                                    <span class="labelTitle">SHARE OF DM</span>
                                </div>
                                <div id="shareOfDMBox" style="height: 160px; width: 250px; margin-left: -10px;"><div id="shareOfDMBoxLbl" style="font-size: 12px; font-family: Helvetica, Arial, sans-serif; color: rgb(102, 102, 102); padding: 0px 0px 0px 20px; margin-top: 10px;">No Results Found</div></div>
                            </div>
                            <div style="width: 33%; float: left;">
                                <div style="margin-left: 10px;">
                                    <span class="labelTitle">SHARE OF SHELF</span>
                                </div>
                                <div id="shareOfShelfBox" style="height: 160px; width: 250px; margin-left: -10px; position: relative;" class="jqplot-target">
	                                
                                </div>
                            </div>
                        </div>
                        <div style="height: 200px; width: 100%; border-top: solid 1px #eebfbf; margin-top: 15px;">
                            <div style="width: 33%; border-right: solid 1px #eebfbf; float: left; height: 100%;
                                margin-top: 15px;">
                                <div>
                                    <span class="labelTitle">PROMOTER</span>
                                </div>
                                <div id="promoterBox" style="height: 180px; width: 240px; margin-top: 10px;">
                                	  
                                </div>
                            </div>
                            <div style="width: 66%; float: left; margin-top: 15px;">
                                <div style="margin-left: 10px;">
                                    <span class="labelTitle">PLAN-O-GRAM</span>
                                </div>
                                <div id="planoBox" style="margin-left: 10px; margin-top: 10px;">
                                    <div style="float: left; width: 220px; color: #666666;">
                                        <div>SKU Displayed in the Middle of Shelf</div>
                                        <div id="plano1Box" style="width: 220px; height: 160px; margin-top: 10px;">
                                        	
                                        </div>
                                    </div>
                                    <div style="float: left; width: 220px; margin-left: 20px; color: #666666">
                                        <div>5p Displayed Guideline Compliance</div>
                                        <div id="plano2Box" style="width: 220px; height: 160px; margin-top: 10px;">
										                    
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="height: 120px; border-top: solid 1px #eebfbf; margin-top: 30px; padding-top: 15px;">
                            <div>
                                <span class="labelTitle">PROMOTION</span>
                            </div>
                            <div style="margin-top: 0px;">
                                <div style="float: left; color: #666666; width: 100%;">
                                    <div id="promotionBox1" style="width: 240px; float: left;">
                                    <div style="margin-top: 10px; display: none;" id="promotionBoxValue1Lbl1"></div>
                                        <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                            <tbody><tr>
                                                <td valign="top" align="right">
                                                    <div id="Div1" style="height:65px;text-align:right;">
                                                        <span id="promotionBoxValue1" style="font-size: 58px; font-weight: bold; color: #df6a00;font-family: Rockwell,Times New Roman,Serif;">
                                                        	
                                                        </span>
                                                    </div>
                                                </td>
                                                <td style="padding-left: 15px; font-size: 14px; font-weight: bold; color: #df6a00;
                                                    font-family: Rockwell,Times New Roman,Serif;" id="promotionBoxLabel1">Number of Promotion Planned</td>
                                            </tr>
                                        </tbody></table>
                                    </div>
                                    <div id="promotionBox2" style="width: 240px; float: left; margin-left: 10px;">
                                        <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                            <tbody><tr>
                                                <td style="padding-top:10px;">
                                                    <div id="promotionBox2Value" style="width:90px;height:55px;">
                                                    	
                                                    </div>
                                                </td>
                                                <td style="padding-left: 15px; font-size: 14px; font-weight: bold; color: #df6a00;
                                                    font-family: Rockwell,Times New Roman,Serif;" id="promotionBox2Label">Store With Promotion</td>
                                            </tr>
                                        </tbody></table>
                                    </div>
                                    <div id="promotionBox3" style="width: 240px; float: left; margin-left: 10px;">
                                        <table width="100%" cellpadding="0" cellspacing="0">
                                            <tbody><tr>
                                                <td style="padding-top:10px;">
                                                    <div id="promotionBox3Value" style="width:90px;height:55px;">
	                                                    
                                                    </div>
                                                </td>
                                                <td style="padding-left: 15px; font-size: 14px; font-weight: bold; color: #df6a00;font-family: Rockwell,Times New Roman,Serif;" id="promotionBox3Label">Promotion Execution Index</td>
                                            </tr>
                                        </tbody></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="dashboardLoading" style="text-align: center; display: none;">
                        <img alt="loading" src="${contextPath}/colgate2/Images/loader.gif">
                    </div>
                </div>
                <div style="clear: both;">
                </div>
            </div>
        </div>
        
<div class="footerNew">
<#include "/colgate/clogateFooter.ftl"/>
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
var __callback_LB;var __callback_LB_NoButton;var __curMode_LB;var __callBackDelay = 10;var __callBackBtnId;var __countDownTimer=0;var __countDownMsg;var __countDownTimeoutMsg;var __countDownObj;function __YesButton_Clicked() {$find("msg_mpeMessage").hide();if( __callback_LB && __callback_LB.length > 0 ) {setTimeout("__evalCallBack_LB()", __callBackDelay);} else {var prm = Sys.WebForms.PageRequestManager.getInstance();prm._doPostBack(__callBackBtnId, '');__callBackBtnId="";}}function __ContinueButton_Clicked() {$find("msg_mpeMessage").hide();__polling('SSU');if(__countDownObj){clearTimeout(__countDownObj);}}function __OkButton_Clicked() {$find("msg_mpeMessage").hide();setTimeout("__evalCallBack_LB()", __callBackDelay);}function __XButton_Clicked() {$find("msg_mpeMessage").hide();if( __curMode_LB == 0) {setTimeout("__evalCallBack_LB()", __callBackDelay);}else if(__curMode_LB==2){__pollingStart(30000);if(__countDownObj){clearTimeout(__countDownObj);}}}function __evalCallBack_LB() {eval(__callback_LB);__callback_LB = "";}function __evalCallBack_LB_No() {eval(__callback_LB_NoButton);__callback_LB_NoButton = "";}function __NoButton_Clicked() {$find("msg_mpeMessage").hide();if( __callback_LB_NoButton && __callback_LB_NoButton.length > 0 ) {setTimeout("__evalCallBack_LB_No()", __callBackDelay);}}function ShowMessageLBDelay(title, msg, bid, mode, callback) {if($find("msg_mpeMessage")) {ShowMessageLB(title, msg, bid, mode, callback);}else{setTimeout(function(){ShowMessageLBDelay(title, msg, bid, mode, callback)}, 20);}}function UpdateCountDown(){var tmp = __countDownMsg.replace('[TIMER]', __countDownTimer);$get("msgltMessage").innerHTML = tmp;__countDownTimer--; if( __countDownTimer >= 0 ){__countDownObj = setTimeout('UpdateCountDown()', 1000);}else {__curMode_LB=0;$get("msgltMessage").innerHTML = __countDownTimeoutMsg;$common.setVisible($get("msg__divAlert"), true);$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divContinue"), false);}}function ShowMessageLBCountDown(title, msg, tmsg, bid, mode, callback, timer) {if($find("msg_mpeMessage")) {__countDownTimer=timer;__countDownMsg=msg;__countDownTimeoutMsg=tmsg;var tmp = __countDownMsg.replace('[TIMER]', __countDownTimer);ShowMessageLB(title, tmp, bid, mode, callback);UpdateCountDown();}else{setTimeout(function(){ShowMessageLBCountDown(title, msg, bid, mode, callback, timer)}, 20);}}function ShowMessageLBYesNo(title, msg, bid, mode, callbackYes, callbackJsNo) {__callback_LB_NoButton = callbackJsNo;ShowMessageLB(title,msg,bid,mode,callbackYes);}function ShowMessageLB(title, msg, bid, mode, callback) {__callback_LB = callback;__curMode_LB = mode;__callBackBtnId = bid;$get("msglblHeader").innerHTML = title;$get("msgltMessage").innerHTML = msg;if( mode && mode == 1){$common.setVisible($get("msg__divConfirm"), true);$common.setVisible($get("msg__divAlert"), false);$common.setVisible($get("msg__divContinue"), false);}else if( mode && mode == 2){$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divAlert"), false);$common.setVisible($get("msg__divContinue"), true);} else {$common.setVisible($get("msg__divAlert"), true);$common.setVisible($get("msg__divConfirm"), false);$common.setVisible($get("msg__divContinue"), false);}$find("msg_mpeMessage").show();}function __ShowLB_FrameDelay(clsName, hText, width, height, url) {if( $find("lbi_mpeIframe") ){__ShowLB_Frame(clsName, hText, width, height, url);}else{setTimeout(new function(){__ShowLB_FrameDelay(clsName, hText, width, height, url)}, 100);}}function __SetTitle_Frame(hText){$get("__ifHeaderImgTitle").innerHTML = hText;}function __SetClass_Frame(clsName){$get("__ifHeaderImgTitle").className = clsName;}function __ShowLB_Frame(clsName, hText, width, height, url){var myi = $get("__ifHeaderImgTitle");var myf = $get("__iFModal");var div = $get("lbi_pnl");myi.className = clsName;myi.innerHTML = hText;div.style.width = width;myf.style.width = width;myf.style.height = height;myf.src = url;$find("lbi_mpeIframe").show();}function __HideLB_Frame(callbackJs){$find("lbi_mpeIframe").hide();$get("__iFModal").src = "";if( callbackJs && callbackJs != '' ){eval(callbackJs);}}__InitUpdateProgressPanel();$('#lblSelectedcbChannel').html('All Channels');$('#lblSelectedddlKeyAccount').html('All APG');$('#lblSelectedddlCustomer').html('All Accounts');$('#lblSelectedcbRegion').html('All Regions');$('#lblSelectedddlProvince').html('All Provinces');$('#lblSelectedddlCity').html('All Cities');
WebForm_InitCallback();function __polling(message){var context = 'polling';WebForm_DoCallback('__Page',message,__pollingCallBack,context,__pollingErrorCallBack,true);}function __pollingCallBack(returnmessage, context){if(returnmessage.length > 0){eval(returnmessage)}else{__pollingStart(300000);}}function __pollingErrorCallBack(returnmessage, context){alert("Callback Error: " + returnmessage + ", " + context);}function __pollingStart(timeInterval){setTimeout("__polling('SS')", timeInterval);}__pollingStart(300000);(function() {var fn = function() {$get('tm_HiddenField').value = '';Sys.Application.remove_init(fn);};Sys.Application.add_init(fn);})();$('#divCon').width(8120);var periodClickCounter = 32;function __ShowLBLoading() { var lf = $find("lb_mpeLoading"); if( lf ) lf.show();} function __HideLBLoading() { var lf = $find("lb_mpeLoading"); if( lf ) lf.hide(); else setTimeout(function(){__HideLBLoading();}, 100);}Sys.Net.WebRequestManager.add_invokingRequest(__onInvoke); Sys.Net.WebRequestManager.add_completedRequest(__onComplete); var __loadingDigShowed = false; var __loadingST;var __showAjaxLoading=false;function __onInvoke(sender, args) { if( !__showAjaxLoading) return; __loadingST = setTimeout("__ShowLoadingDig()", 100);} function __ShowLoadingDig() { if( !__loadingDigShowed ) top.__ShowLBLoading(); __loadingDigShowed = true; } function __onComplete(sender, args) { __showAjaxLoading=false;if( __loadingDigShowed ) top.__HideLBLoading(); __loadingDigShowed = false; clearTimeout(__loadingST); } function pageUnload() { Sys.Net.WebRequestManager.remove_invokingRequest(__onInvoke); Sys.Net.WebRequestManager.remove_completedRequest(__onComplete); }Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Overview.aspx","id":"cbChannel_hme2","popupElement":$get("cbChannel_PopupMenu")}, null, null, $get("cbChannel_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Overview.aspx","id":"ddlKeyAccount_hme2","popupElement":$get("ddlKeyAccount_PopupMenu")}, null, null, $get("ddlKeyAccount_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Overview.aspx","id":"ddlCustomer_hme2","popupElement":$get("ddlCustomer_PopupMenu")}, null, null, $get("ddlCustomer_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Overview.aspx","id":"cbRegion_hme2","popupElement":$get("cbRegion_PopupMenu")}, null, null, $get("cbRegion_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Overview.aspx","id":"ddlProvince_hme2","popupElement":$get("ddlProvince_PopupMenu")}, null, null, $get("ddlProvince_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.HoverMenuBehavior, {"PopDelay":50,"PopupPosition":3,"dynamicServicePath":"/StoreRadar/Overview.aspx","id":"ddlCity_hme2","popupElement":$get("ddlCity_PopupMenu")}, null, null, $get("ddlCity_ddl"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"msg_pnl","dynamicServicePath":"/StoreRadar/Overview.aspx","id":"msg_mpeMessage"}, null, null, $get("msg_btn"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"lbi_pnl","dynamicServicePath":"/StoreRadar/Overview.aspx","id":"lbi_mpeIframe"}, null, null, $get("lbi_btn"));
});
Sys.Application.add_init(function() {
    $create(Sys.Extended.UI.ModalPopupBehavior, {"BackgroundCssClass":"modalBackground","PopupControlID":"lb_pnl","dynamicServicePath":"/StoreRadar/Overview.aspx","id":"lb_mpeLoading"}, null, null, $get("lb_btn"));
});
//]]>
</script>
<div id="msg_mpeMessage_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lbi_mpeIframe_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div><div id="lb_mpeLoading_backgroundElement" class="modalBackground" style="display: none; position: fixed; left: 0px; top: 0px;"></div>
</body>
</form>
</html>