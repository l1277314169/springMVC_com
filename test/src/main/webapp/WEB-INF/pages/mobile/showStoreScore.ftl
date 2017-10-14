<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<#include "/common/taglibs.ftl" />
<link rel="stylesheet" href="${contextPath}/css/mobile-report.css?cVer=${cVer}" />
<script src="${contextPath}/js/zepto.js" type="text/javascript"></script>
</head>

<body width="10000px">
<table class="table table-bordered data-table">
	<tr>
		<th class="merge_td" rowspan="2" width="80px">门店编号</th>
		<th class="merge_td" rowspan="2" width="100px">门店名称</th>
		<th class="merge_td" rowspan="2" width="80px">CM</th>
		<th class="merge_td" rowspan="2" width="50px">得分</th>
		<th class="merge_td" rowspan="2" width="100px">RE</th>
		<th class="merge_td" colspan="3" width="240px">Distribution-TP</th>
		<th class="merge_td" colspan="3" width="240px">Distribution-Tb</th>
		<th class="merge_td" colspan="2" width="230px">Share of Shelf</th>
		<th class="merge_td" colspan="2" width="230px">5P Guideline Compliance</th>
		<th class="merge_td" colspan="2" width="230px">2nd Display Rate</th>
		<th class="merge_td" rowspan="2" width="210px">Regimen Display Rate</th>
	</tr>
	<tr>
		<th class="merge_td">应分销</th>
		<th class="merge_td">实际分销</th>
		<th class="merge_td">分销率</th>
		<th class="merge_td">应分销</th>
		<th class="merge_td">实际分销</th>
		<th class="merge_td">分销率</th>
		<th class="merge_td">Toothpaste</th>
		<th class="merge_td">ToothBrush</th>
		<th class="merge_td">Toothpaste</th>
		<th class="merge_td">ToothBrush</th>
		<th class="merge_td">Toothpaste</th>
		<th class="merge_td">ToothBrush</th>
	</tr>
	<tbody>
		<#if (scoreList?size>0) >
			<#assign size = scoreList?size/>
			<#assign count = 0/>
			<#list scoreList as score>
				<#if score.actualScore??>
					<#assign count = score.actualScore + count/>
				</#if>
		    <tr>
		      <td class="text_td">${score.storeNo}</td>
		      <td class="text_td" title="${score.storeName}">${score.storeName}</td>
		      <td class="text_td">${score.name}</td>
		      <td class="text_td">${score.actualScore}</td>
		      <td class="text_td" title="${score.storeName}">${score.channelName}</td>
		      <td class="text_td">${score.toothpasteDistTarget}</td>
		      <td class="text_td">${score.toothpasteDistActual}</td>
		      <td class="text_td">${score.toothpasteDistribution?default(0.0000) *100}%</td>
		      <td class="text_td">${score.toothbrushDistTarget}</td>
		      <td class="text_td">${score.toothbrushDistActual}</td>
		      <td class="text_td">${score.toothbrushDistribution?default(0.0000) *100}%</td>
		      <td class="text_td">${score.toothpasteSos?default(0.0000) *100}%</td>
		      <td class="text_td">${score.toothbrushSos?default(0.0000) *100}%</td>
		      <td class="text_td">${score.toothpaste5p?default(0.0000)}%</td>
		      <td class="text_td">${score.toothbrush5p?default(0.0000)}%</td>
		      <td class="text_td">${score.toothpaste2nd?default(0.0000) *100}%</td>
		      <td class="text_td">${score.toothbrush2nd?default(0.0000) *100}%</td>
		      <td class="text_td">${score.regimenDisplay?default(0.0000) *100}%</td>
		    </tr>
		    </#list>
		    <tr>
		      <td style="text-align: center;font-weight: bold;" colspan="3">最终得分</td>
		      <td class="text_td" colspan="15">${(count/size)?string("#.##")}</td>
		    </tr>
		</#if>
    </tbody>
</table>

</body>
</html>