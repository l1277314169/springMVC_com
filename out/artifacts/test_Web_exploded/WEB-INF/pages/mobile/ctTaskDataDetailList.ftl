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
</head>
<body>
 
				 
<table class="table table-bordered" style="margin:10;">
	<#if ctTask.taskType != '4'>
		<tr>
			<th>
				<#if ctTask.taskType == '1'>产品名称<#elseif ctTask.taskType == '2'>品牌名称<#elseif ctTask.taskType == '3'>品类相关
				<#elseif ctTask.taskType == '4'>问卷(无对象)<#elseif ctTask.taskType == '5'>人员名称<#elseif ctTask.taskType == '6'>终端对象
				<#elseif ctTask.taskType == '7'>产品系列<#elseif ctTask.taskType == '8'>产品分类<#elseif ctTask.taskType == '10'>物料名称
				</#if>
			</th>		      	 
      		<#list ctTaskParameters as ctTaskParameter>
	      		<th>${ctTaskParameter.parameterName}</th>
      		</#list>		      	
		</tr>
		<#list ctTaskObjects as ctTaskObject>
			<tr >
				<th>${ctTaskObject.objectName}</th>
				<#list ctTaskParameters as ctTaskParameter>
      				<th>	   				
						<#if parameterMap?exists>
							<#assign key="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}"/>
							<#if ctTaskParameter.controlType=='1' || ctTaskParameter.controlType=='3' || ctTaskParameter.controlType=='4' || ctTaskParameter.controlType=='9'>
			            		${parameterMap[key]}
		            		<#elseif ctTaskParameter.controlType=='11'>
		            			<#if parameterMap[key]??>
		            				<#list parameterMap[key]?split(",") as imgSrc>
										<img  src="${contextPath}/uploadfiles/${ctTaskDataSerchVo.clientId}/${ctTaskDataSerchVo.clientUserId}/thumbnail/s_${imgSrc}" />
									</#list>
		            			</#if>
							<#else>
								${parameterMap[key]}
			            	</#if>
      					</#if>							
      				</th>
  				</#list>
			</tr>
		</#list>			 
	<#else>
		<tr>
			<th class="merge_td">参数名称</th>	
			<th class="merge_td">参数值</th>		
		</tr>
		<#list ctTaskParameters as ctTaskParameter>
			<tr>
      			<td>${ctTaskParameter.parameterName}</td>
      			<td>
      				<#if parameterMap?exists>
      					<#assign key="${ctTaskParameter.ctTaskParameterId}_"/>
      					<#if ctTaskParameter.controlType=='1' || ctTaskParameter.controlType=='3' || ctTaskParameter.controlType=='4' || ctTaskParameter.controlType=='9'>
		            		${parameterMap[key]}
	            		<#elseif ctTaskParameter.controlType=='11'>
	            			<#if parameterMap[key]??>
	            				<#list parameterMap[key]?split(",") as imgSrc>
									<img  src="${contextPath}/uploadfiles/${ctTaskDataSerchVo.clientId}/${ctTaskDataSerchVo.clientUserId}/thumbnail/s_${imgSrc}" />
								</#list>
	            			</#if>
						<#else>
							${parameterMap[key]}
		            	</#if>
      				</#if>
      			</td>
      		</tr>
  		</#list>
	</#if>
</table>
</div>
</html>