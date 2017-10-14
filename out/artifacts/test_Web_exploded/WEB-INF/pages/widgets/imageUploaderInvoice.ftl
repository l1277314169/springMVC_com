<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<div id="uploader-demo" class="wu-example"  style="<#if imageWidthFTL??>width:px;${imageWidthFTL}px<#else>width:730px;</#if>">
    <input type="hidden" id="${objectIdFTL}" name="${objectIdFTL}" />
    <div id="fileList_${objectIdFTL}" class="uploader-list">
    <#list wc.invoicePics as a>						
    <div id = "${a}" class = "file-item thumbnail upload-state-done" val = "${a}" > 
		<img class="img_lightBox" href="/uploadfiles/${clientId}/web/${a}" src="/uploadfiles/${clientId}/web/thumbnail/l_${a}" />
	<div class = "info_del"></div>  
	</div> 
	</#list>
    </div>
    <div id="filePicker_${objectIdFTL}"  style="margin-top:30px;">选择图片</div>
</div>
<#include "/widgets/uploaderForImage.ftl" />