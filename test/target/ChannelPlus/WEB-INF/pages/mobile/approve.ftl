<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>计划审批</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<#include "/common/taglibs.ftl" />
<link rel="stylesheet" href="${contextPath}/css/channel-mobile.css?cVer=${cVer}"" />
</head>

<body>

<table>
<#if clientUserList?size != 0 >
	<#list clientUserList as clientUser>
		<tr><td class="wide"><a href="${contextPath}/mobile/callplanningApprove/approveDetail/${clientUser.id}/${clientUser.clientId}">${clientUser.name}</a></td><td class="narrow"></td></tr>
	</#list>
	<#else>
	<div class="actions"> 
		<lable>没有待审批的数据</lable>
	</div>
</#if>
</table>

<body>

</html>