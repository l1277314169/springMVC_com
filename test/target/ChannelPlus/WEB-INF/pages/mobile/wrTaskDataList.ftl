<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<#include "/common/taglibs.ftl" />
<link rel="stylesheet" href="${contextPath}/css/mobile-report.css?cVer=${cVer}" />
</head>

<body>
<table class="table table-bordered data-table table-fixed">
	<tr>
		<th class="merge_td" rowspan="2" width="80px">姓名</th>
		<th class="merge_td" rowspan="2" width="100px">工作日期</th>
		<th class="merge_td" rowspan="2" width="80px">工作时长</th>
	</tr>
	<tbody>
		<#list wrTaskFinishCounts as wrTaskFinishCount>
		    <tr>
		      <td class="text_td">${wrTaskFinishCount.userName}</td>
		      <td class="text_td">${(wrTaskFinishCount.finishDate)?string("yyyy-MM-dd")}</td>
		      <td class="text_td">
		      	<span style="<#if wrTaskFinishCount.finishHour < 9>color:red;<#elseif wrTaskFinishCount.finishHour gte 9 && wrTaskFinishCount.finishHour lte 12>color:Orange;<#elseif wrTaskFinishCount.finishHour gt 12>color:Green;</#if>">
		      		<#if wrTaskFinishCount.finishHour == 0>未完成离店小结<#else>${wrTaskFinishCount.finishHour}小时</#if>
	      		</span>
	      	  </td>
		    </tr>
	    </#list>
    </tbody>
</table>

<body>

</html>