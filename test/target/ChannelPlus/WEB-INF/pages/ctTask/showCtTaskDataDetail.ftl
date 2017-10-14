<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/msi_survey_uploader.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<title>门店管理</title>
</head>
<body class="main_body">
<#assign privCode="C1M13">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/message/query">周期任务管理</a>
					<a class="linkPage active" href="${contextPath}/ctTask/showCtTaskList?ctTaskId=${ctTask.ctTaskId}">${ctTask.taskName}</a>
					
				</div>
		 	</div>
		 	<div id="div_step_detail" class="div_step_detail" style="width: 100%;height:auto;border:none;background: #fff;margin-left:0px;">
<div class="widget-content nopadding"  style="height:80%">
<div class="widget-content nopadding">
<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
<input type="hidden" id="ctTaskId" name="ctTaskId" value="${ctTaskDataSerchVo.ctTaskId}">
<input type="hidden" id="ctTaskDataId" name="ctTaskDataId" value="${ctTaskDataSerchVo.ctTaskDataId}">
<input type="hidden" id="popId" name="popId" value="${ctTaskDataSerchVo.popId}">
	<div class="control-group">
<#if objectFilters?? && ctTask.taskType==1>		
	<#list objectFilters as objectFilter>
		<#if objectFilter==2>
			<div class="fl">
				<label class="control-label " for="category">品类：</label>
				<div class="controls">
				  <input type="text" id="category" name="category" readonly="readonly" value="${ctTaskDataSerchVo.category!''}" onclick="showMenu(this.id);"/>
				</div>
			</div>
		<#elseif objectFilter==1>
			<div class="fl">
				<label class="control-label " for="brand">品牌：</label>
				<div class="controls">
				  <input type="text" id="brand" name="brand" required onclick="showMenu(this.id);" value="${ctTaskDataSerchVo.brand!''}" readonly="readonly"/>
				</div>
			</div>
		<#elseif objectFilter==4>					
			<div class="fl">
				<label class="control-label fl" for="skuType">分类：</label>
				<div class="controls">
				  <input type="text" id="skuTypeId" name="skuTypeId" value="${ctTaskDataSerchVo.skuTypeId}"/>
				</div>
			</div>
		<#elseif objectFilter==3>	
			<div class="fl">
				<label class="control-label fl" for="skuSeries">系列：</label>
				<div class="controls">
				  <input type="text" name="skuSeriesId" id="skuSeriesId" value="${ctTaskDataSerchVo.skuSeriesId}"/>
				</div>
			</div>	
		</#if>		
	</#list>
	<div class="fl">
		<label class="control-label fl" for="objectName">对象名称：</label>
		<div class="controls">
		  <input type="text" id="objectName" name="objectName" class="input-medium" value="${ctTaskDataSerchVo.objectName}">
		</div>
	</div>
<#elseif ctTask.taskType!=4>
	<div class="fl">
		<label class="control-label fl" for="objectName">对象名称：</label>
		<div class="controls">
		  <input type="text" id="objectName" name="objectName" class="input-medium" value="${ctTaskDataSerchVo.objectName}">
		</div>
	</div>										 	 		 
</#if>	
</div>	
<#if ctTask.taskType!=4>
  	<div class="form-actions"> 	
  		<button type="button" id="backButton" class="btn btn-success">返回</button>
		<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">		
	</div>
	<input type="hidden" id="brandId" name="brandId" value="${ctTaskDataSerchVo.brandId!''}">
	<input type="hidden" id="categoryId" name="categoryId" value="${ctTaskDataSerchVo.categoryId!''}">
	<input type="hidden" name="page" value="${page}" />	
<#else>
	<div class="form-actions">
  		<button type="button" id="backButton" class="btn btn-success">返回</button>
	</div>	
</#if>
</form>
</div>
<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;">
	</ul>
</div>
<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;">
	</ul>
</div>
<div id="menuContent_skuType" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_skuType" class="ztree" style="margin-top: 0; width: 160px;">
	</ul>
</div>
<div id="menuContent_skuSeries" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_skuSeries" class="ztree" style="margin-top: 0; width: 160px;">
	</ul>
</div>
<div style="overflow-x:auto;">	
<form id="dataForm" method="post">
		<table class="table table-bordered data-table" id="c_list">
			<#if ctTask.taskType != '4'>
				<tr>
					<th class="fill_td">
						<#if ctTask.taskType == '1'>产品名称<#elseif ctTask.taskType == '2'>品牌名称<#elseif ctTask.taskType == '3'>品类相关
						<#elseif ctTask.taskType == '4'>问卷(无对象)<#elseif ctTask.taskType == '5'>人员名称<#elseif ctTask.taskType == '6'>终端对象
						<#elseif ctTask.taskType == '7'>产品系列<#elseif ctTask.taskType == '8'>产品分类<#elseif ctTask.taskType == '10'>物料名称
						</#if>
					</th class="fill_td">		      	 
		      		<#list ctTaskParameters as ctTaskParameter>
			      		<th class="fill_td">${ctTaskParameter.parameterName}</th>
		      		</#list>		      	
				</tr>
				<tbody>
					<#list ctTaskObjects as ctTaskObject>
						<tr >
							<td class="fill_td">${ctTaskObject.objectName}</td>
							<#list ctTaskParameters as ctTaskParameter>
			      				<td class="td_control_d fill_td">	   				
									<#assign key="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}"/>
									<#if ctTaskParameter.controlType=='3'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskParameter.controlType=='1'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskParameter.controlType=='2'>
										<textarea rows=2 style="height:50;" maxlength=500 ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" placeholder="不超过500个字"></textarea>	
									<#elseif ctTaskParameter.controlType=='4'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									<#elseif ctTaskParameter.controlType=='5'>										
										<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />											
									<#elseif ctTaskParameter.controlType=='6'>
										<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if>  />
									<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
										<input type="checkbox" controlType="checkbox" class="input-medium loadCheckBoxOption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" value="1" <#if parameterMap?exists && parameterMap[key]==1>checked="checked"</#if> />
									<#elseif  ctTaskParameter.controlType=='11'>
										<#assign objectId="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}">
											<div id="uploader-demo" class="wu-example" style="width:800px;">
											    <input type="hidden" id="${objectId}" name="${objectId}" />
											    <div id="fileList_${objectId}" class="uploader-list">
											    	<#if parameterMap[key]?exists>
												    	<#list parameterMap[key]?split(",") as imgName>
												    		<div id="E_WU_FILE_${imageName_index}" class="file-item thumbnail" val="${imgName}">
												    			<#if imgName?substring(0,1)!='w'>
												    				<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/${imgName?split("_")[2]}/${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/${imgName?split("_")[2]}/thumbnail/l_${imgName}" />
												    			<#else>
												    				<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/xl_${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/l_${imgName}" />
												    			</#if>
												    		</div>
												    	</#list>
											    	</#if>
											    </div>
											</div>	
											<#include "/survey/survey_uploader.ftl" />
									<#elseif  ctTaskParameter.controlType=='9'>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> dateTime" value="${parameterMap[key]}" readonly="readonly" />
									<#elseif  ctTaskParameter.controlType=='100'>
										<input type="text" controlType="select" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> monthControl" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />	
									<#else>
										<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
									</#if>	
			      				</td>
		      				</#list>
						</tr>
					</#list>			 
				</tbody>
			<#else>
				<tr>
					<th class="fill_td">参数名称</th>	
					<th class="fill_td">参数值</th>		
				</tr>
				<tbody>
					<#list ctTaskParameters as ctTaskParameter>
						<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.parameterName=='访问员'>
							<@shiro.hasPermission name="C1M13F4">
								<tr>
					      			<td class="fill_td">${ctTaskParameter.parameterName}</td>
					      			<td>
				      					<#assign key="${ctTaskParameter.ctTaskParameterId}_"/>
				      					<#if ctTaskParameter.controlType=='1'>
											<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==92>
			      								<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> correctAddress" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#else>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											</#if>
										<#elseif ctTaskParameter.controlType=='3'>					
											<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==57>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumForm1" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==58>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumMax1" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==59>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumForm2" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==60>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumMax2" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#else>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											</#if>
										<#elseif ctTaskParameter.controlType=='4'>
											<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
										<#elseif ctTaskParameter.controlType=='2'>
											<textarea rows=2 style="height:50;" maxlength=500 ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" placeholder="不超过500个字"></textarea>								 
										<#elseif ctTaskParameter.controlType=='5'>
											<#if ctTaskDataSerchVo.clientId=='13'>
												<#if ctTaskParameter.optionName=='store_invalid_reason'>
													<input type="text" controlType="select" class="storeInvalidReason storeInvalidFlag input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
												<#else>
													<input type="hidden" controlType="text" class="input-medium loadoptionRadio ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
												</#if>
											<#else>
												<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											</#if>	 
										<#elseif ctTaskParameter.controlType=='6'>
											<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if>  />
										<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
											<input type="checkbox" controlType="checkbox" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" value="${parameterMap[key]}" <#if parameterMap?exists && parameterMap[key]==1>checked="checked"</#if> />									
										<#elseif  ctTaskParameter.controlType=='11'>
											<#assign objectId="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}">
											<div id="uploader-demo" class="wu-example" style="width:800px;">
											    <input type="hidden" id="${objectId}" name="${objectId}" />
											    <div id="fileList_${objectId}" class="uploader-list">
											    	<#if parameterMap[key]?exists>
												    	<#list parameterMap[key]?split(",") as imgName>
												    		<div id="E_WU_FILE_${imageName_index}" class="file-item thumbnail" val="${imgName}">
												    			<#if imgName?substring(0,1)!='w'>
											    					<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/${imgName?split("_")[2]}/${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/${imgName?split("_")[2]}/thumbnail/l_${imgName}" />
												    			<#else>
												    				<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/xl_${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/l_${imgName}" />
												    			</#if>
												    		</div>
												    	</#list>
											    	</#if>
											    </div>
											</div>	
											<#include "/survey/survey_uploader.ftl" />
										<#elseif  ctTaskParameter.controlType=='9'>
											<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> dateTime" value="${parameterMap[key]}" readonly="readonly" />
										<#else>
											<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
										</#if>	
					      			</td>
					      		</tr>
				      		</@shiro.hasPermission>
			      		<#else>
			      			<tr>
					      			<td class="fill_td">${ctTaskParameter.parameterName}</td>
					      			<td>
				      					<#assign key="${ctTaskParameter.ctTaskParameterId}_"/>
				      					<#if ctTaskParameter.controlType=='1'>
											<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==92>
			      								<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> correctAddress" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#else>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											</#if>
										<#elseif ctTaskParameter.controlType=='3'>					
											<#if ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==57>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumForm1" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==58>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumMax1" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==59>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumForm2" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#elseif ctTaskDataSerchVo.clientId=='13' && ctTaskParameter.ctTaskParameterId==60>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType valNumMax2" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											<#else>
												<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> intType" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											</#if>
										<#elseif ctTaskParameter.controlType=='4'>
											<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
										<#elseif ctTaskParameter.controlType=='2'>
											<textarea rows=2 style="height:50;" maxlength=500 ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" placeholder="不超过500个字"></textarea>								 
										<#elseif ctTaskParameter.controlType=='5'>
											<#if ctTaskDataSerchVo.clientId=='13'>
												<#if ctTaskParameter.optionName=='store_invalid_reason'>
													<input type="text" controlType="select" class="storeInvalidReason storeInvalidFlag input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
												<#else>
													<input type="hidden" controlType="text" class="input-medium loadoptionRadio ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
												</#if>
											<#else>
												<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
											</#if>
										<#elseif ctTaskParameter.controlType=='6'>
											<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" <#if parameterMap?exists>value="${parameterMap[key]}"</#if>  />
										<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
											<input type="checkbox" controlType="checkbox" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" value="${parameterMap[key]}" <#if parameterMap?exists && parameterMap[key]==1>checked="checked"</#if> />									
										<#elseif  ctTaskParameter.controlType=='11'>
											<#assign objectId="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}">
											<div id="uploader-demo" class="wu-example" style="width:800px;">
											    <input type="hidden" id="${objectId}" name="${objectId}" />
											    <div id="fileList_${objectId}" class="uploader-list">
											    	<#if parameterMap[key]?exists>
												    	<#list parameterMap[key]?split(",") as imgName>
												    		<div id="E_WU_FILE_${imgName_index}" class="file-item thumbnail" val="${imgName}">
												    			<#if imgName?substring(0,1)!='w'>
												    				<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/${imgName?split("_")[2]}/${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/${imgName?split("_")[2]}/thumbnail/l_${imgName}" />		
												    			<#else>
												    				<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/xl_${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/l_${imgName}" />
												    			</#if>
												    		</div>
												    	</#list>
											    	</#if>
											    </div>
											</div>	
											<#include "/survey/survey_uploader.ftl" />
										<#elseif  ctTaskParameter.controlType=='9'>
											<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> dateTime" value="${parameterMap[key]}" readonly="readonly" />
										<#elseif  ctTaskParameter.controlType=='100'>
											<input type="text" controlType="select" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> monthControl" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />	
										<#else>
											<input type="text" controlType="text" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>" <#if parameterMap?exists>value="${parameterMap[key]}"</#if> />
										</#if>	
					      			</td>
					      		</tr>
			      		</#if>
		      		</#list>
				</tbody>
			</#if>
		</table>
	</form>
		<!--dom结构部分-->
		<span class="img_title">问卷图片：</span>
		<div id="uploader-demo" class="wu-example" style="width:730px;">
		    <!--用来存放item-->
		    <input type="hidden" id="imageNames" name="imageNames" />
		    <div id="fileList" class="uploader-list">
		    	<#list ctTaskData.ctTaskDataAttachments as atts>
		    		<div href="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/xl_${atts.attachment}" id="E_WU_FILE_${atts_index}" class="file-item thumbnail" val="${atts.attachment}">
		    			<#if atts.attachment?substring(0,1)!='w'>
		    				<img href="/uploadfiles/${ctTaskDataSerchVo.clientId}/${atts.attachment?split("_")[2]}/${imgName}" src="/uploadfiles/${ctTaskDataSerchVo.clientId}/${atts.attachment?split("_")[2]}/thumbnail/l_${imgName}" />		
		    			<#else>
		    				<img src="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/l_${atts.attachment}" href="/uploadfiles/${ctTaskDataSerchVo.clientId}/web/thumbnail/xl_${atts.attachment}" />
		    			</#if>
		    		</div>
		    	</#list>
		    </div>
		</div>
	</div>
	<!--<div class="td_buttons" style="margin-left: 340;margin-buttom:-5px;">
		<button data-dismiss="dialog" type="button" class="btn btn-danger">取消</button>
		<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
	</div>-->
</div>
<div id="tabs-body" class="div_data_detail" style="width: 100%;height: 100%;overflow:visible;background: #fff;"></div>
<div id="tabs-body" class="div_data_detail" style="width: 100%;height: 100%;overflow:visible;background: #fff;"></div>
	</div>
<#include "/footer.ftl" />
</body>
</body>
</html>
<script>
$(function(){
 
	$(".file-item img").lightBox();
	
	$("#dataForm input").each(function(){
		$(this).attr("disabled",true);
	});	
	
	$("#backButton").bind("click",function(){
		$('#breadcrumb a:last-child',window.top.document).remove();
		window.history.go(-1); 
	});
	
	$(".loadoptionRadio").each(function(){
		loadoptionRadio($(this).attr("optionName"),$(this).attr("value"),$(this).attr("ctTaskParameterId"));
	});
		
	$.fn.zTree.init($("#treeDemo_brand"), setting_brand);
    $.fn.zTree.init($("#treeDemo_category"), setting_category);	
	
	loadStatus($(".loadoption").attr("optionName"));	
	loadMultipleStatus($(".multipleloadoption").attr("optionName"));
	
	var clientId = "${ctTaskDataSerchVo.clientId}";
	if(clientId == "13"){
		loadMonthControl();
	}
		
	$("#categoryId").on("change",function(){
		var value = $(this).val();
		if(value == "") {
			$("#categoryId").val('');	 
			$("#categoryId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
		}else{
			$.ajax({
				type : "post",
				url : "${contextPath}/skuType/getSkuTypeByCategoryId?categoryId="+value,
				dataType : "json",
				cache : false,
				success : function(data) {
					var jsonData = eval(data);
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.skuTypeId+"\",\"text\":\""+item.skuTypeName+"\"},";
						} else {
							thisData += "{\"id\":\""+item.skuTypeId+"\",\"text\":\""+item.skuTypeName+"\"}";
						}
					});
					thisData += "]";
					var cData = $.parseJSON(thisData);
					$("#skuTypeId").select2({
						width:145,
						placeholder: "请选择",
						allowClear: true,
						data: cData
					});
				},
				error : function(data) {
					alert("数据加载失败！");
				}
			});
		}
	});
	
	var categoryId = '${ctTaskDataSerchVo.categoryId!''}';
	if(categoryId!=''){
		loadSkuType(categoryId);	
	}else{
		$("#skuTypeId").select2({
        	width:145,
			placeholder: "请选择",
			data:[]
		});
	}
		
	var brandId = '${ctTaskDataSerchVo.brandId!''}';
	if(brandId!=''){
		loadSkuSeries(brandId);
	}else{
		$("#skuSeriesId").select2({
        	width:145,
			placeholder: "请选择",
			data:[]
		});
	}
	
	$('.btn-danger').click(function(){
		addDialog.close();
	}); 

	$("#savetButton").bind("click",function(){
		var searchFlag = "true";
		if($("#storeId").val()==''){
			searchFlag = "false";
			if($("#storeId ~ i").length==0){
				$("#storeId").after("<i for=\"storeId\" class=\"error\">不能为空</i>");
			}				
		}
		$(".require").each(function(){
			var value = null;
			var id = $(this).attr("id");			
			var controlType = $("#"+id).attr("controlType");    //控件类型
			if(controlType=="select"||controlType=="multipleSelect"){
				value = $("#"+id).select2("val")
			}else if(controlType=="text"){
				value = $("#"+id).val();
			}
			if(value==''){
				searchFlag = "false";
				if($("#"+id+"~ i").length==0){
					$(this).after("<i for=\""+id+"\" class=\"error\">不能为空</i>");	
				}		 		
			}
		}); 
		var dataFlag = "true";	
		$(".maxValueValidate").each(function(){
			var currentValue = $(this).val();
			if(currentValue==''){
				currentValue = parseInt("0");
			}
			var maxValue = parseInt($(this).attr("maxvalue"));
			var minValue = parseInt($(this).attr("minvalue"));
			var id = $(this).attr("id");
			if($("#"+id+"~ i").length==0){
				if(currentValue>maxValue){
					$(this).after("<i for=\""+id+"\" class=\"error\">不能超过最大值"+maxValue+"</i>");		
				}else if(currentValue<minValue){
					$(this).after("<i for=\""+id+"\" class=\"error\">不能小于最小值"+minValue+"</i>");	
				}
			}			
		});
		if($(".error").length>0){
			dataFlag = "false";
		}
		if(dataFlag=="true"&&searchFlag=="true"){
		 	var dataString = $("#dataForm input").serialize();		
	 		var datas = dataString.split("&");
	 		var ctTaskId = $("#ctTaskId").val(); 			
	 		var ctTaskData = new Object();
	 		var storeId = $("#storeId").val(); 
	 		var popType = $("#popType").val();
	 			ctTaskData.popId = storeId;
	 			ctTaskData.ctTaskId = ctTaskId;
	 			ctTaskData.popType = popType;
	 		var ctTaskDetailDatas = new Array();
	 		for(var index in datas){
	 			var str1 = datas[index].split("=")[0];
	 			var ctTaskDetailData = new Object();
	 			ctTaskDetailData.ctTaskParameterId = str1.split("_")[0];
	 			ctTaskDetailData.target1Id = str1.split("_")[1];
	 			var str2 = datas[index].split("=")[1];
	 			if(controlType=="multipleSelect"||controlType=="select"){
	 				ctTaskDetailData.value = $("#"+str1).select2("val")+"";
	 			}else{
	 				ctTaskDetailData.value = str2;
	 			}
	 			ctTaskDetailDatas.push(ctTaskDetailData);
	 		}
	 		ctTaskData.ctTaskDetailDatas = ctTaskDetailDatas;
	 		$.ajax({
					url : "${contextPath}/ctTaskData/saveCtTaskData",
					type : "post",
					dataType:'JSON',
					contentType : 'application/json',  
					data : JSON.stringify(ctTaskData),
					success : function(result) {
						window.location.reload();
						addDialog.close();
					}
			});	
		}	 
	});
	
	
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#workDate2").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	}); 
	
	$(":checkbox").each(function(){
		$(this).click(function(){
			if($(this).attr("checked")=="checked"){
				$(this).next().remove();
			}else{			
				var id = $(this).attr("id");
				$(this).after("<input type=\"hidden\" id=\""+id+"\" name=\""+id+"\" value=\"0\" />");	
			}
		});
	});
	
	$("#brand").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#brand").val(""); 
			$("#brandId").val(""); 
		}; 
	});
	$("#category").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#category").val(""); 
			$("#categoryId").val(""); 
		}; 
	});
	
});

function loadStatus(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("."+optionName).select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadCheckbox(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {						 
				thisData+=""+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";		 
			});
			$(".loadCheckBoxOption").parent().append(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadMultipleStatus(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("."+optionName).select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				multiple: true,
				closeOnSelect:false,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadoptionRadio(optionName,value){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {									 
				thisData+="<input type='radio' disabled='disabled' name='"+optionName+"' value='' checked='checked' />"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";		 
			});
			$(".loadCheckBoxOption").parent().append(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

//品牌、品类树
	var selectId;
	var setting_brand = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/brand/getTreeNode",
			error : function() {
				alert('亲，组织架构加载失败！');  
	        }, 
			autoParam : [ "id" ]
		},
	    view: {
	        dblClickExpand: false,
	        selectedMulti: true, //是否允许多选
	        txtSelectedEnable: false //是否允许选中节点的文字
	        //autoCancelSelected: false //不允许按下Ctrl键取消节点选中状态
	    },
	    data: {
	        simpleData: {
	            enable: true
	        }
	    },
	    callback: {
	        beforeClick: beforeClick,
	        onClick: onClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
	    }
	};
	
	var setting_category = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/category/getTreeNode",
			error : function() {
				alert('亲，组织架构加载失败！');  
	        }, 
			autoParam : [ "id" ]
		},
	    view: {
	        dblClickExpand: false,
	        selectedMulti: true, //是否允许多选
	        txtSelectedEnable: false //是否允许选中节点的文字
	        //autoCancelSelected: false //不允许按下Ctrl键取消节点选中状态
	    },
	    data: {
	        simpleData: {
	            enable: true
	        }
	    },
	    callback: {
	        beforeClick: beforeClick,
			onAsyncSuccess: zTreeOnAsyncSuccess,
	        onClick: onClick
	    }
	};	

	function beforeClick(treeId, treeNode) {
    	var demo = "treeDemo_"+selectId;
        var zTree = $.fn.zTree.getZTreeObj(demo);
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#"+selectId).attr("value", treeNode.name);
    	if(selectId == 'brand') {
			$("#brandId").attr("value", treeNode.id);	
			loadSkuSeries(treeNode.id);
        }
        if(selectId == 'category') {
			$("#categoryId").attr("value", treeNode.id);
			loadSkuType(treeNode.id);
        }
        hideMenu();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandAll(true);
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'brand') {
        	$("#menuContent_brand").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        };if(objName == 'category') {
        	$("#menuContent_category").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        };
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="brand") {
     	   $("#menuContent_brand").fadeOut("fast");
    	}
    	if(selectId =="category") {
     	   $("#menuContent_category").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="brand"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_brand" || $(event.target).parents("#menuContent_brand").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="category") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_category" || $(event.target).parents("#menuContent_category").length > 0)) {
	            hideMenu();
        	}
    	}  	
    }
    
 function loadSkuSeries(brandId){
 		if(skuSeriesId == "") {
			$("#skuSeriesId").val('');	 
			$("#skuSeriesId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
		}else{
			$.ajax({
			type : "post",
			url : "${contextPath}/skuSeries/getSkuSeriesByBrandId?brandId="+brandId,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.skuSeriesId+"\",\"text\":\""+item.skuSeriesName+"\"},";
					} else {
						thisData += "{\"id\":\""+item.skuSeriesId+"\",\"text\":\""+item.skuSeriesName+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				$("#skuSeriesId").select2({
					width:145,
					placeholder: "请选择",
					allowClear: true,
					data: cData
				});
			},
			error : function(data) {
				alert("数据加载失败！");
			}
		});
	}	
};  

function loadSkuType(categoryId){
	if(categoryId == "") {
		$("#categoryId").val('');	 
		$("#categoryId").select2({
        	width:145,
			placeholder: "请选择",
			data:[]
		});
	}else{
		$.ajax({
		type : "post",
		url : "${contextPath}/skuType/getSkuTypeByCategoryId?categoryId="+categoryId,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.skuTypeId+"\",\"text\":\""+item.skuTypeName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.skuTypeId+"\",\"text\":\""+item.skuTypeName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#skuTypeId").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
  }
};

function loadoptionRadio(optionName,value,ctTaskParameterId){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {	
				if(item.optionValue==value){
					if(ctTaskParameterId=='56'){					//高露洁客户逻辑验证的需求
						thisData+="<input type='radio' disabled='disabled' onclick='isCorrect(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' controlType='radio' value='"+item.optionValue+"' checked='checked' />"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";			
					}else{
						thisData+="<input type='radio' disabled='disabled' onclick='changeRadioValue(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"' checked='checked' />"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";		
					}				
				}else{
					if(ctTaskParameterId=='56'){
						thisData+="<input type='radio' disabled='disabled' onclick='isCorrect(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";	
					}else{
						thisData+="<input type='radio' disabled='disabled' onclick='changeRadioValue(this)' name='"+ctTaskParameterId+"' parameterId='"+ctTaskParameterId+"' value='"+item.optionValue+"'/>"+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}								 
					 
			});
			$("#"+ctTaskParameterId+"_").parent().prepend(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function isCorrect(element){
	var id = element.name+"_";
	$("#"+id).attr("value",element.value);          //给参数赋值
	if($("#"+id+" ~ i").length>0){
		$("#"+id).next().remove();	
	}
	if(element.value==2){		//选择否的时候		
		$(".correctAddress").attr("disabled",false); 
	}else{
		$(".correctAddress").val("");					
		$(".correctAddress").attr("disabled",true); 
	}
}

function changeRadioValue(element){
	var id = element.name+"_";
	$("#"+id).attr("value",element.value);           //给参数赋值
	if($("#"+id+" ~ i").length>0){
		$("#"+id).next().remove();
	}
}
function loadMonthControl(){
	$.ajax({
		type : "post",
		url : "${contextPath}/comms/initializeMonthControl",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.value+"\",\"text\":\""+item.text+"\"},";
				} else {
					thisData += "{\"id\":\""+item.value+"\",\"text\":\""+item.text+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$(".monthControl").select2({
				width:150,
				placeholder: "请选择",
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};  
  
//业务员
$("#clientUserId").select2({
	width:150,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	ajax: {
		url: "${contextPath}/clientUser/getClientUserPositionBystoreId?storeId=${ctTaskDataSerchVo.popId}",
		dataType: 'json',
		quietMillis: 250,
		data: function (term, page) {
		return {
			q: term,
			page: page
		};
	},
	results: function (data,page) {
		return { results: data};
	}
},
initSelection: function(element, callback) {
	var id = $(element).val();
	if (id !== "") {
		$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
				dataType: "json"
			}).done(function(data) { callback(data); });
		}
	},
	formatResult: repoFormatResult,
	formatSelection: repoFormatSelection,
	escapeMarkup: function (m) { return m; }
}); 
		
function repoFormatResult(repo) {
	return repo.name;
}
function repoFormatSelection(repo) {
	return repo.name;
}	
</script>