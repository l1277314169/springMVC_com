<#assign objectId="${obj.objectId}_${pls.surveyParameterId}_${obj.subSurveyId}">
<#if pls.controlType==7>
	<input type="checkbox" value="1" id="${objectId}" name="${objectId}" class="input_cons_cls <#if pls.isVerify==1>require</#if>" <#if pls.isEditable==0>readonly</#if>  /><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif  pls.controlType == 1 > <!-- 单行文本 -->
	<input type="text" value="<#if obj.objectId=='274'>${store.cityName}<#elseif obj.objectId=='275'>${store.storeNo}<#elseif obj.objectId=='276'>${store.storeName}<#elseif obj.objectId=='277'>${store.addr}<#elseif obj.objectId=='278'>${store.contact}<#elseif obj.objectId=='279'>${store.fax}<#elseif obj.objectId=='281'>${store.telephoneNo}<#elseif obj.objectId=='280'>${store.bannerPhoto}<#elseif obj.objectId=='282'>${store.mobileNo}<#elseif obj.objectId=='283'>${store.remark}</#if>" style="width: ${pls.width}" id="${objectId}" name="${objectId}" class="input_cons_cls <#if pls.isVerify==1>require</#if>" <#if pls.isEditable==0>readonly</#if> /><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif  pls.controlType == 2 > <!-- 多行文本 -->
	<textarea style="width: ${pls.width};height:130px" maxlength=2000 id="${objectId}" name="${objectId}" class="input_cons_cls <#if pls.isVerify==1>require</#if>" <#if pls.isEditable==0>readonly</#if>></textarea><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif  pls.controlType == 3 ><!-- 整数 -->
	<input type="text" style="width: ${pls.width}" id="${objectId}" name="${objectId}" class="input_cons_cls intType maxValueValidate <#if pls.isVerify==1>require</#if>" maxValue="${pls.maxValue}" minValue="${pls.minValue}"  <#if pls.isEditable==0>readonly</#if> /><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif  pls.controlType == 4 ><!-- 小数 -->
	<input type="text" style="width: ${pls.width}" id="${objectId}" name="${objectId}" class="input_cons_cls float maxValueValidate <#if pls.isVerify==1>require</#if>" maxValue="${pls.maxValue}" minValue="${pls.minValue}" scale="${pls.scale}" <#if pls.isEditable==0>readonly</#if> /><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif  pls.controlType == 5 ><!-- 单选下拉框 -->
	<input type="text" style="width: ${pls.width}"  id="${objectId}" name="${objectId}" class="input_cons_cls loadoption ${pls.optionName} <#if pls.isVerify==1>require</#if>" loadoption="${pls.optionName}" optionName="${pls.optionName}" <#if pls.isEditable==0>readonly</#if> /><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif  pls.controlType == 11 ><!-- 拍照 -->	
	<#if pls.isVisible==1>${pls.parameterName}</#if>
	<div id="uploader-demo" class="wu-example"  style="width:730px;">
	    <input type="hidden" id="${objectId}" name="${objectId}" />
	    <div id="fileList_${objectId}" class="uploader-list"></div>
	    <#if show!=1>
	    	<#if pls.isEditable==1>
	    		<div id="filePicker_${objectId}"  style="margin-top:30px;">选择图片</div>
	    	</#if>
	    </#if>
	</div>
	<#include "/survey/survey_uploader.ftl" />
<#elseif pls.controlType== 9>
	<input type="text" style="width: ${pls.width}"  id="${objectId}" name="${objectId}" class="input_cons_cls <#if pls.surveyParameterId != 117>dateTime</#if> <#if pls.isVerify==1>require</#if> <#if pls.surveyParameterId == 117>checkDate<#elseif pls.surveyParameterId == 118>checkBeginDate<#elseif pls.surveyParameterId == 119>checkEndDate</#if>" <#if pls.isEditable==0>readonly</#if> /><#if pls.isVisible==1>${pls.parameterName}</#if>
<#elseif pls.controlType==14>
			<#if pls.isVisible==1>${pls.parameterName}</#if>
	<div id="uploader-demo" class="wu-example"  style="width:730px;">
	    <input type="hidden" id="${objectId}" name="${objectId}" />
	    <div id="fileList_${objectId}" class="uploader-list"></div>
	    <#if show!=1>
	    	<#if pls.isEditable==1>
	    		<div id="filePicker_${objectId}"  style="margin-top:30px;">选择音频文件</div>
	    	</#if>
	    </#if>
	</div>
	<#include "/survey/survey_audio_uploader.ftl" />

<#elseif pls.controlType==200>
	<input type="text" id="${objectId}" name="${objectId}" readonly <#if pls.isVerify==1>class='require'</#if> />
	<#include "/survey/selectNumber.ftl" />
</#if>