<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.fixedtableheader.min.js"></script>
<SCRIPT type="text/javascript">
	$(document).ready(function(){
		$(".lightPhoto").lightbox({
			fitToScreen: true
		});
		//$("#fixedtableheader0").remove();
		$("#tbl").fixedtableheader();
	});
	
</SCRIPT>
<table class="tbl table table-bordered" id="tbl">
<#if stepType = 4>
	<tr>
		<th>参数名称</th>
		<th>值</th>
	</tr>
	<#list vParameterList as vp>
		<tr>
		<td>${vp.parameterName}</td>
		<td>
			<#if detailMap[stepType]??>
				<#list detailMap[stepType]?keys as key>
					<#if vp.visitingParameterId == key && vp.controlType == '11'>
						<#list detailMap[stepType][key] ? split(",") as str>
							<a href="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" rel="flowers_${stepType}_${key}" class="lightPhoto"><img src="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" style="width:20;height:20;" /></a>
						</#list>
					<#elseif vp.visitingParameterId == key>
						${detailMap[stepType][key]}
					</#if>
				</#list>
			</#if>
		</td>
		</tr>
	</#list>
<#elseif stepType = 5>
	<tr>
		<th>名称</th>
		<#list vObjectList as item>
			<th>${item.objectName!''}</th>
		</#list>
	</tr>
	<#list vParameterList as vp>
		<tr>
			<td>${vp.parameterName!''}</td>
			<#list vObjectList as item>
				<td>
				<#if vp.controlType != '12' && detailMap[item.target1Id]??>
				<#list detailMap[item.target1Id]?keys as key>
					<#if vp.visitingParameterId == key && vp.controlType == '11'>
						<#list detailMap[item.target1Id][key] ? split(",") as str>
							<a href="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" rel="flowers_${item.target1Id}_${key}" class="lightPhoto"><img src="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" style="width:20;height:20;" /></a>
						</#list>
					<#elseif vp.visitingParameterId == key>
						${detailMap[item.target1Id][key]}
					</#if>
				</#list>
				</#if>
				</td>
			</#list>
		</tr>
	</#list>
<#elseif vParameterList?? && vParameterList?size gt 0 >
	<tr>
		<th>名称</th>
		<#list vParameterList as vp>
			<th>${vp.parameterName}</th>
		</#list>
	</tr>
	<#list vObjectList as item>
		<tr>
			<td>${item.objectName!''}</td>
			<#list vParameterList as vp>
				<td>
				<#if detailMap[item.target1Id]??>
				<#list detailMap[item.target1Id]?keys as key>
					<#if vp.visitingParameterId == key && vp.controlType == '11'>
						<#list detailMap[item.target1Id][key] ? split(",") as str>
							<a href="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" rel="flowers_${item.target1Id}_${key}" class="lightPhoto"><img src="${contextPath}/uploadfiles/${clientId}/${clientUserId}/${str}" style="width:20;height:20;" /></a>
						</#list>
					<#elseif vp.visitingParameterId == key>
						${detailMap[item.target1Id][key]}
					</#if>
				</#list>
				</#if>
				</td>
			</#list>
		</tr>
	</#list>
</#if>
</table>