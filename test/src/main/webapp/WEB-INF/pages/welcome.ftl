<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />

<title>Channel Plus</title>
<link rel="shortcut icon" type="image/x-icon" href="${contextPath}/img/favicon.ico?cVer=${cVer}">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body class="main_body">

<#assign privCode="BASE">
<#include "/base.ftl" />
<!-- 页面内容 -->
<div id="content">
	
	<div id="content-header">
		<div id="breadcrumb"> <a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a></div>
 	</div>
	
	<#if clientId == 4>
	<div align=center>
		<img src="${contextPath}/img/placeholder_mg.png" />
	</div>
	<#elseif clientId == 7>
		<div id="centerBG"></div>
	<#elseif clientId == 2>
		<div align=center>
			<img src="${contextPath}/img/placeholder_ctbat.jpg" />
		</div>
	<#elseif clientId == 5 || clientId == 6>
			<#include "/base/echartsAll.ftl" />	
	<#else>
	<div align=center>
		<img src="${contextPath}/images/u41_normal.png" />
	</div>
	</#if>
</div>	
<#include "/footer.ftl" />
</body>
</html>
