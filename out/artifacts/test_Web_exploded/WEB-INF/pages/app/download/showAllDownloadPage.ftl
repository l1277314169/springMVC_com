<html class="body-full-height">
    <head>        
        <!-- META SECTION -->
        <title>手机版本下载</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <link rel="icon" href="${contextPath}/img/favicon.ico" type="image/x-icon" />
        <!-- END META SECTION -->
        
        <!-- CSS INCLUDE -->        
        <link rel="stylesheet" type="text/css" id="theme" href="${contextPath}/app/css/channel-style.css"/>
        <!-- EOF CSS INCLUDE -->                                    
    </head>

	<body style="margin:0;padding:0;">
	<div class="dowload_content_all">	
		<div style="padding: 20px;"><span style="font-size:16px;">下载链接</span></div>
		<div class="">
			<table align="center">
			<#list clientList as client>
				<#if client.androidUrl?? || client.iphoneUrl?? || client.ipadUrl??>
				<tr>
					<td>${client.clientName}</td>
					<td><#if client.androidUrl??><span class="android_div_all" style="background-color:#e7220f"><a href="${client.androidUrl}">安卓下载</a></span><#else><span class="android_div_all" style="background-color:#fff"></span></#if></td>
					<td><#if client.iphoneUrl??><span class="ios_div_all" style="background-color:#000000"><a href="${client.iphoneUrl}">iPhone下载</a></span><#else><span class="ios_div_all" style="background-color:#fff"></span></#if></td>
					<td><#if client.ipadUrl??><span class="ios_div_all" style="background-color:#000000"><a href="${client.ipadUrl}">iPad下载</a></span><#else><span class="ios_div_all" style="background-color:#fff"></span></#if></td>
				</tr>
				</#if>
			</#list>
			</table>
			<div class="download_height"></div>
			<span>Copyright &copy; 2015 By Mobilizer</span>
		</div>
	</div>
    </body>
</html>