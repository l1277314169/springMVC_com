<div style="text-align:right;padding-top:10px;padding-right:5px;">
        <a id="ucHeader_hyDataEntry" class="headerLink" target="view_window" href="http://www.channelplus.cn/" style="color:#c00700;font-size:10px;text-decoration:none;">Data Entry</a>
        <span id="ucHeader_dataEntrySeparator" style="color:#c00700;">|</span>
         <a id="ucHeader_hyAdmin" class="headerLink" href="#" style="color:#c00700;font-size:10px;text-decoration:none;"> <@shiro.principal property="name" /></a>
        <span id="ucHeader_adminSeparator" style="color:#c00700;">|</span>
        <a id="ucHeader_hyLogout" class="headerLink" href="${contextPath}/logout" style="color:#c00700;font-size:10px;text-decoration:none;">Logout</a>
    </div> 
<div>
    <div style="float:left;">
        <!-- <img src="${contextPath}/colgate2/Images/equity_chevron.png"> -->
    </div>
    <div style="float:left;color:#ce1613;font-family:Rockwell,Helvetica, Arial, sans-serif;font-style:italic;font-size:20px;margin:15px 0px 0px 10px;">
         AppleCare Spot Check
    </div>
    <div style="float:right;margin:23px 0px 0px 0px;text-align:right;width:540px;">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tbody>
             <tr>
                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                    <input type="button" value="Overview" onclick="window.location.href='${contextPath}/apple/appleOverView'" class="<#if currentMenu==1>NewMenuButtonSelected<#else>NewMenuButton</#if>">
                </td>
            
            	<td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                    <input type="button" value="Dashboard" onclick="window.location.href='${contextPath}/apple/showDashboard/'" class="<#if currentMenu==3>NewMenuButtonSelected<#else>NewMenuButton</#if>"></input>
                </td>
            
                <td style="padding-left:0px;border-left:solid 1px #969696;" align="center">
                    <input type="button" value="Reports" onclick="window.location.href='${contextPath}/apple/appleReports/'" class="<#if currentMenu==2>NewMenuButtonSelected<#else>NewMenuButton</#if>"></input>
                </td>
            </tr>
        	</tbody>
        </table>
    </div>