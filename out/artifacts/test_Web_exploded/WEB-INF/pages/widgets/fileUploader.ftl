<link rel="stylesheet" type="text/css" href="${contextPath}/css/webuploader.css"/>
<script type="text/javascript" src="${contextPath}/js/webuploader.min.js"></script>

<div id="uploader_${objectIdFTL}" class="wu-example line-middle fileUpload" style="border:0;padding-top:0;">
    <!--用来存放文件信息-->
    <input type="hidden" id="${objectIdFTL}" name="${objectIdFTL}" />
    <div id="thelist_${objectIdFTL}" class="uploader-list"></div>
    <div class="btns">
        <div id="picker_${objectIdFTL}">选择文件</div>
        <!--
        	<button id="ctlBtn" class="btn btn-default">开始上传</button>
         -->
    </div>
</div>

<#include "/widgets/uploaderForFile.ftl" />