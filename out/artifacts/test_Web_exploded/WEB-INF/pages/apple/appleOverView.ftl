<html>
<head>
<#include "/common/taglibs.ftl" />
<link href="${contextPath}/apple2/css/jquery.jqplot.min.css" rel="stylesheet" type="text/css">
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
	<title></title>
    <script src="${contextPath}/apple2/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jquery.scrollTo-min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/Common.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/raphael-min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jquery.jqplot.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/utilities.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jqplot.barRenderer.min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jqplot.categoryAxisRenderer.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jqplot.pointLabels.min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/jqplot.highlighter.min.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/chart.js" type="text/javascript"></script>
    <script src="${contextPath}/apple2/js/dashboard_revamped.js" type="text/javascript"></script>
    <script src="${contextPath}/js/jquery.showLoading.min.js" type="text/javascript"></script>
    
    <link href="${contextPath}/css/showLoading.css" type="text/css" rel="stylesheet">
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
   	<script src="${contextPath}/apple2/js/appleOverView.js?cVer=${cVer}" type="text/javascript"></script>
    <script type="text/javascript">
        var colorArray = ["#7e0303", "#df6a00", "#f9ad61", "#fcdcad"];
        var colorObject = new Object();
        colorObject["Toothpaste"] = "#7e0303";
        colorObject["Toothbrush"] = "#df6a00";
        colorObject["Mouthwash"] = "#f9ad61";

        function refresh() {
			loadOverView();
        }
    </script>
</head>
<body style="background-image:none;background-color:White;">
<form method="post" action="" id="form1">
<div class="aspNetHidden">
	<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
	<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
	<input type="hidden" name="tm_HiddenField" id="tm_HiddenField" value=";;AjaxControlToolkit, Version=3.5.40412.0, Culture=neutral, PublicKeyToken=28f01b0e84b6d53e:zh-CN:065e08c0-e2d1-42ff-9483-e5c14441b311:5546a2b:475a4ef5:d2e10b12:effe2a26:37e2e5c9:5a682656:f9029856:1d3ed089:d1a1d569:497ef277:a43b07eb:751cdd15:dfad98a5:3cf12cf1">
	<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="">

	<input type="hidden" id="_root" name="_root" value="${contextPath}" />
	<input type="hidden" id="deptIds" name="deptIds" value="${filterVo.deptIds}" />
	<input type="hidden" id="yearId" name="yearId" value="${filterVo.yearId}" />
	<input type="hidden" id="channel" name="channel" value="${filterVo.channel}" />
	<input type="hidden" id="province" name="province" value="${filterVo.province}" />
	<input type="hidden" id="amName" name="amName" value="${filterVo.amName}" />
	<input type="hidden" id="rmName" name="rmName" value="${filterVo.rmName}"  />
	<input type="hidden" id="filterStructureIds" name="filterStructureIds" value="${filterVo.filterStructureIds}" />
</div>
<div class="headerNew">
   		<div style="margin:0px auto;width:960px;">
   			<#assign currentMenu="1">
        	<#include "/apple/appleHead.ftl" />
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
                                        	
                                        </span>
                                    </div>
                                </div>
                                <div id="upOverview">
	
                                <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_cbChannel">
                                    
<div class="RBDropdownList">    
        
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
	
    
    <div id="cbChannel_PopupMenu" class="RBDropdownCheckboxListItems" style="position: absolute;display:none;">
        <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                    <td style="width:15px;" align="right">
                                
                    </td>
                    <td align="left">
                        <table id="cbChannel_Items" class="RBDropdownCheckboxListItem">
			<tbody id="channel_tbody">
			 	<span class="rbDdlCB"><input type="button" name="argChainIds_but" data="0" value="All Channels" id="argChainIds_but0" class="RBDropdownListItem"></span>
			</tbody>
			
			</table>
                                
                    </td>
                </tr>
            </tbody></table>
        </div>
	</div>
   </div>
    </div>
                                    
                                    
       <div id="upRegion">
         <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlProvince">                           
		<div class="RBDropdownList">
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
            
            <div id="ddlProvince_PopupMenu" class="RBDropdownListItems" style="display: none; position: absolute;">
            </div>
        	</div></div>
        	<!-- start -->
        	
        	 <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlCity">                             
             <div class="RBDropdownList">
               
            <table id="ddlCity_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconCity">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <span id="lblSelectedddlCity">
                            	<input type="text" id="city" name="city" value="${filterVo.city}" style="width:110px;border:none;height:30px;border-bottom:1px solid #c00800;outline:medium;" />
                            </span>
                        </div>
                    </td>
				<td class="rightColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div id="ddlCity_dropdownIcon">  
				</div>
                </td>
			</tr>
		</tbody></table>
		
            
            <div id="ddlCity_PopupMenu" class="RBDropdownListItems" style="display: none; visibility: hidden; position: absolute;">
			
            
			</div>
        	<!-- end -->
        	</div></div>
        	<!-- start1 -->
        	

          <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlKeyAccount">                                  
            <div class="RBDropdownList">
            <table id="ddlKeyAccount_ddl" border="0" cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconAPG">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <!--<span id="lblSelectedddlKeyAccount">
                            	AM：
                            	<input type="text" id="amName" name="amName" value="${filterVo.amName}"  style="width:110px;border:none;height:30px;border-bottom:1px solid #c00800;outline:medium;" />
                            </span>-->
                             <span id="lblSelectedddlKeyAccount">AM</span>
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
           
           
            <div id="ddlKeyAccount_PopupMenu" class="RBDropdownListItems" style="position: absolute;display:none;">
            	<div>
                	<!--<div><input type="button" name="argAccountIds_but" data="0" value="All APG" id="ddlKeyAccount_rpItems_Item" class="RBDropdownListItem"></div>-->
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                    <td style="width:15px;" align="right">             
                    </td>
                    <td align="left">
                        <table id="cbChannel_Items" class="RBDropdownCheckboxListItem">
			<tbody id="Account_tbody">
			 	<span class="rbDdlCB"><input type="button" name="argAccountIds_but" data="0" value="All Account" id="ddlKeyAccount_rpItems_Item" class="RBDropdownListItem"/></span>
			</tbody>
			</table>             
                </td>
                </tr>
            </tbody></table>	
            </div>
			</div>
        	<!--end1 -->
        	</div></div>
        	
        	
        	
        	
        	<!-- start1 -->
        	
             <div style="margin: 5px 0px; display: block;" class="filterRow" id="div_ddlKeyAccount2">                                   
             <div class="RBDropdownList">
             <table id="ddlKeyAccount_ddl2" border="0" cellpadding="0" cellspacing="0">
			 <tbody><tr>
				<td class="leftColumn" style="padding-top:2px;padding-bottom:2px;">
                        <div class="filterIconAPG">
                        </div>
                    </td>
				<td class="midColumn">
                        <div class="selectedData">
                            <!--<span id="lblSelectedddlKeyAccount">
                            	RM：<input type="text" id="rmName" name="rmName" value="${filterVo.rmName}"  style="width:110px;border:none;height:30px;border-bottom:1px solid #c00800;outline:medium;" />
                            </span>-->
                             <span id="lblSelectedddlKeyAccount1">RM</span>
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
		
            
            <!--<div id="ddlKeyAccount_PopupMenu2" class="RBDropdownListItems" style="visibility: hidden; position: absolute; left: 501px; top: 391px; z-index: 1000; display: none;">
            	<div>
                	<div><input type="button" name="argChainIds_but" data="" value="All APG" id="ddlKeyAccount_rpItems_Item" class="RBDropdownListItem"></div>
                </div>
			</div>-->
        	 <div id="ddlKeyAccount_PopupMenu2" class="RBDropdownListItems" style="position: absolute;display:none;">
            <div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tbody><tr>
                   <td style="width:15px;" align="right">             
                   </td>
                   <td align="left">
               <table id="cbChannel_Items" class="RBDropdownCheckboxListItem">
			   <tbody id="Account1_tbody">
			 	<span class="rbDdlCB"><input type="button" name="argRmAccountIds_but" data="0" value="All Account" id="ddlKeyAccount_rpItems_rm_Item" class="RBDropdownListItem"/></span>
			</tbody>
			</table>             
                </td>
                </tr>
            </tbody></table>	
            </div>
			</div>
        	<!--end1 -->
        	
        	
        	</div></div></div></div>
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
                            <#list years as m>
								 <input type="button" name="ddlPeriod" data="${m.yearId}" value="${m.yearId}" class="<#if filterVo.yearId==m.yearId>btnPeriodSelected<#else>btnPeriod</#if>">
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
                                    <span class="labelTitle">A. Professional Appearance</span>
                                </div>
                               <!-- <div id="distributionBox" style="height: 160px; width: 260px;margin-top: 10px; position: relative;" class="jqplot-target">
                                
                                </div>-->
                            </div>
                            <div style="width: 33%; border-right: solid 1px #eebfbf; float: left; height: 100%;">
                                <div style="margin-left: 10px;">
                                    <span class="labelTitle">B. Operational Procedures</span>
                                </div>
                               <!-- <div id="shareOfDMBox" style="height: 160px; width: 250px; margin-top: 10px; margin-left:10px;">
                                
                                </div>-->
                            </div>
                            <div style="width: 33%; float: left;">
                                <div style="margin-left: 10px;">
                                    <span class="labelTitle">C. Customer Experience</span>
                                </div>
                                <div id="shareOfShelfBox" style="height: 160px; width: 250px; margin-top: 10px; margin-left:10px; position: relative;" class="jqplot-target">
	                                
                                </div>
                            </div>
                        </div>
                        
                        <div style="height: 200px; width: 100%; border-top: solid 1px #eebfbf; margin-top: 15px;">
                            <div style="width: 33%; border-right: solid 1px #eebfbf; float: left; height: 100%;
                                margin-top: 15px;">
                                <div>
                                    <span class="labelTitle">D. Organizational Compliance</span>
                                </div>
                                <div id="promoterBox" style="height: 180px; width: 240px; margin-top: 10px;">
                                	  
                                </div>
                            </div>
                            <div style="width: 66%; float: left; margin-top: 15px;">
                                <div style="margin-left: 10px;">
                                    <span class="labelTitle">E. Repair Workflow</span>
                                </div>
                                <div id="planoBox" style="margin-left: 10px; margin-top: 10px;">
                                    
                                </div>
                            </div>
                        </div>
                        
                        <div style="height: 120px; border-top: solid 1px #eebfbf; margin-top: 30px; padding-top: 15px;">
                            <div>
                                <span class="labelTitle"></span>
                            </div>
                            <div style="margin-top: 0px;">
                                <div style="float: left; color: #666666; width: 100%;">
                                    <div id="promotionBox1" style="width: 240px; float: left;">
                                        <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                            <tbody><tr>
                                                <td valign="top" align="right">
                                                    <div id="Div1" style="height:65px;text-align:right;">
                                                        <span id="promotionBoxValue1" style="font-size: 58px; font-weight: bold; color: #df6a00;font-family: Rockwell,Times New Roman,Serif;">
                                                        	
                                                        </span>
                                                    </div>
                                                </td>
                                                <td style="padding-left: 15px; font-size: 14px; font-weight: bold; color: #df6a00;
                                                    font-family: Rockwell,Times New Roman,Serif;" id="promotionBoxLabel1">Average Score</td>
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
                                                    font-family: Rockwell,Times New Roman,Serif;" id="promotionBox2Label">Scored > 90</td>
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
                                                <td style="padding-left: 15px; font-size: 14px; font-weight: bold; color: #df6a00;font-family: Rockwell,Times New Roman,Serif;" id="promotionBox3Label">Scored < 60</td>
                                            </tr>
                                        </tbody></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="dashboardLoading" style="text-align: center; display: none;">
                        <img alt="loading" src="${contextPath}/apple2/Images/loader.gif">
                    </div>
                </div>
                <div style="clear: both;">
                </div>
            </div>
        </div>
        
<div class="footerNew">
<#include "/apple/appleFooter.ftl"/>
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
</body>
</form>
</html>