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
		<#if authorise>
			<div class="dowload_content">
				<div><img src="${contextPath}/app/img/${(client.logoPrefix)!''}icon-logo.png" width="150"></div>
				<div class="download_title"><#if client.remark??>${client.remark}<#elseif client.logoPrefix??>Channel Plus</#if></div>
				<div class="download_title" style="font-size: 14px;">${(client.contactName)!''}</div>
				<div class="download_foot">
					<span class="ios_div" style="background-color:#000000"><a href="<#if iosUrl??>${iosUrl}<#else>javascript:void(0);</#if>">苹果版下载</a></span>
					<span class="android_div" style="background-color:#97c024"><a href="<#if androidUrl??>${androidUrl}<#else>javascript:void(0);</#if>">安卓版下载</a></span>
					<div class="download_height"></div>
					<span>Copyright &copy; 2015 By Mobilizer</span>
				</div>
			</div>
		<#else>
			<h1>禁止访问!</h1>
		</#if>	
    </body>
</html>