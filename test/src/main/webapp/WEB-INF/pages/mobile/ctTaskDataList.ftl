<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>历史数据查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<#include "/common/taglibs.ftl" />
<link rel="stylesheet" href="${contextPath}/css/channel-mobile.css?cVer=${cVer}"" />
<link rel="stylesheet" href="${contextPath}/css/mobile-report.css?cVer=${cVer}" />
<script src="${contextPath}/js/zepto.js" type="text/javascript"></script>
</head>
<body>
<table class="table data-table">
	<#if storeTasks?size != 0 >
 		<#list storeTasks as storeTask>
			<tr class="showDetial" clientId="${clientId}" clientUserId="${clientUserId}" ctTaskDataId="${storeTask.ctTaskDataId}" popId="${storeTask.storeId}" ctTaskId="${ctTask.ctTaskId!''}">
				<td class="_td">${storeTask.storeName}<br/>提报人：${storeTask.clientUserName}<br/>提报时间：${(storeTask.lastUpdateTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
				<td class="_td narrow"></td>
			</tr>
		</#list>		
	<#else>
	<div class="actions"> 
		<lable>没有历史数据</lable>
	</div>
</#if>
</table>
<script language="javascript" type="text/javascript">
$(function(){
	$(".showDetial").bind("click",function(){
		var ctTaskDataId = $(this).attr("ctTaskDataId");
		var ctTaskId = $(this).attr("ctTaskId");
		var popId = $(this).attr("popId");
		var clientId = $(this).attr("clientId");
		var clientUserId = $(this).attr("clientUserId");
		window.location.href="${contextPath}/mobile/ctTask/ctTaskDataDetailList?ctTaskDataId="+ctTaskDataId+"&ctTaskId="+ctTaskId+"&popId="+popId+"&clientId="+clientId+"&clientUserId="+clientUserId;	
	});
});
</script>
</html>