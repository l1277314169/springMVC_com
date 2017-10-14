<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<div id="uploader-demo" class="wu-example line-middle imgUpload"  style="border: none;padding:0;<#if imageWidthFTL??>width:${imageWidthFTL}px;<#else>width:730px;</#if>">
    <input type="hidden" id="${objectIdFTL}" name="${objectIdFTL}" />
    <div id="fileList_${objectIdFTL}" class="uploader-list"></div>
    <div id="filePicker_${objectIdFTL}" >选择图片</div>
</div>
<#include "/widgets/uploaderForImage.ftl" />