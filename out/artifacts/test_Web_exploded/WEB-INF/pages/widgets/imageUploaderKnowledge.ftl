<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<div id="uploader-demo" class="wu-example  line-middle imgUpload"  style="border: none;padding:0;<#if imageWidthFTL??>width:${imageWidthFTL}px;<#else>width:730px;</#if>">
    <input type="hidden" id="${objectIdFTL}" name="${objectIdFTL}" />
    <div id="fileList_${objectIdFTL}" class="uploader-list">
	    <#if (knowledge.avatar)?? >
			<#list (knowledge.avatar)?split(",") as x> 
				<#if (x) !=null>
			    <div id = "${x}" class = "file-item thumbnail upload-state-done" val = "${x}" > 
			   		 <img class="img_lightBox" href="${contextPath}/uploadfiles/${knowledge.clientId}/web/${x}" src="${contextPath}/uploadfiles/${knowledge.clientId}/web/thumbnail/s_${x}" name="avatar" id="avatar"/>
					  <div class = "info_del"></div>
			     </div>
			   </#if>
			 </#list>
		</#if>
	  </div>  
    <div id="filePicker_${objectIdFTL}"  style="margin-top:30px;">选择图片</div>
</div>
<#include "/widgets/uploaderForImage.ftl" />