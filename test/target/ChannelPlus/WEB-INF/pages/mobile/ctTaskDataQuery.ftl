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
<link rel="stylesheet" href="${contextPath}/css/channel-mobile.css?cVer=${cVer}"" />
<link rel="stylesheet" href="${contextPath}/css/mobile-report.css?cVer=${cVer}" />
<script src="${contextPath}/js/zepto.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.zepto.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.core.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.scroller.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.datetime.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.select.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.scroller.ios.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.scroller.android.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.scroller.android-ics.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.scroller.wp.js" type="text/javascript"></script>
<script src="${contextPath}/js/mobiscroll/js/mobiscroll.i18n.zh.js" type="text/javascript"></script>
<link href="${contextPath}/js/mobiscroll/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/js/mobiscroll/css/mobiscroll.scroller.sense-ui.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width:100%">
<#if clientUser??>
	<form id="dataForm" action="${contextPath}/mobile/ctTask/ctTaskDataList" method="post">
	<input type="hidden" name="clientId" id="clientId" value="${clientId}">
	<input type="hidden" name="clientUserId" id="clientUserId" value="${clientUserId}"/>
	<table class="table data-table">
		<tr style="border:0px">
			<td class="_td" style="border:0">起始时间:</td>
		</tr>
		<tr>
			<td class="_td">
                <input type="date" name="startDate" id="startDate" data-clear-btn="false" name="date-1" id="frmMain_txt_SB_SERVERTIME" value="" />
            </div>
			</td>
		</tr>
		<tr>
			<td class="_td">结束时间:</td>
		</tr>
		<tr>
			<td class="_td">
				<input type="date" name="endDate" id="endDate" data-clear-btn="false" name="date-1" id="frmMain_txt_SB_SERVERTIME" value=""/>
			</td>
		</tr>
		<tr>
			<td class="_td">任务名称:</td>
		</tr>
		<tr>
			<td class="_td">
				<select name="ctTaskId" value="${ctTaskId}">
           			<option value="">--请选择--</option>
                	<#list ctTasks as ctTask>
                		<#if ctTask.ctTaskId == ctTaskId>
                			<option value = "${ctTask.ctTaskId!''}" selected = "selected">${ctTask.taskName!''}</option>
                		<#else>
                			<option value = "${ctTask.ctTaskId!''}">${ctTask.taskName!''}</option>
                		</#if>
                	</#list>
          		</select>
			</td>
		</tr>
		<tr>
			<td class="_td">门店关键字:</td>
		</tr>
		<tr>
			<td class="_td"><input name="storeName" id="storeName" value="${storeName}" /></td>
		</tr>
		<tr>
			<td class="_td">提报人关键字:</td>
		</tr>
		<tr>
			<td class="_td"><input name="workerUserName" id="workerUserName" value="${workerUserName}" /></td>
		</tr>
		<tr>
			<td align="center" class="_td"><input type="submit" value="查询" /></td>
		</tr>
	</table>
</form>
<#else>
<div class="actions"> 
		<lable>没有此用户</lable>
	</div>
</#if>
</div>
</body>
</html>