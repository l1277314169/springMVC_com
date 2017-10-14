<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<div id="uploader_${objectIdFTL}" class="wu-example  line-middle fileUpload" style="border:0;width:500px;">
    <input type="hidden" id="${objectIdFTL}" name="${objectIdFTL}" />
    <div id="fileList_${objectIdFTL}" class="uploader-list">
	    <#if (knowledge.fileName)?? >
		  <#list (knowledge.fileName)?split(",") as c> 
			   <#if (c) !=null>
				    <div id = "${c}" class = "file-item thumbnail upload-state-done" val = "${c}" > 
				    <a href="${contextPath}/uploadfiles/${knowledge.clientId}/web/${c}"  name="fileName" id="fileName">${c}</a>
				    <div class = "info_del"></div>
				  </div>
		  		</#if>
		 </#list>
		</#if>
	  </div>  
    <div id="picker_${objectIdFTL}">选择文件</div>
</div>
<#include "/widgets/uploaderForFile.ftl" />