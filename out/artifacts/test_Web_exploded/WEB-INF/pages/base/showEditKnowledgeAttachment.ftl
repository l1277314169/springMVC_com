<html>
<head>
<title></title>
</head>
<body>
<div class="widget-content nopadding" style="margin:0px;">
        <input type="hidden" id="knowledgeId" name="knowledgeId" value="${knowledge.knowledgeId}"/>
        <table class="table_white_bg" style="width:100%;">
            <tbody> 
                <tr>
                   <td class="td_label fill_td">学习资料附件：</td>
                    <td class="td_control">
                       <input type="hidden" id="fileName" name="fileName">
                        <#assign objectIdFTL="fileName">
					    <#include "/widgets/imageUploaderKnowledgeAttachment.ftl" />
                    </td>
                </tr>
            </tbody>
        </table>
</div>
</body>
</html>
<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
<script>
function getFileValues(obj){
	var uploadobj = $(obj).children(".upload-state-done");
	var value = '';
    $.each(uploadobj, function(index, val) {
        var v = $(val).attr("val");
        if(index==0){
             value+=v;
        }else{
            value+=(","+v);
        }
    });
    var idx = value.indexOf(",");
    if(idx==0){
        value = value.substring(1);
    }
    return value;
}

function setFileUploaderVal(){
	var uploaderList = $(".fileUpload .uploader-list");
	$.each(uploaderList, function(index, obj) {
		 var fid = $(obj).attr("id");
		 var id = fid.replace("thelist_",'');
		 var vals = getFileValues(obj);
		 $("#fileName").val(vals);
	});  
}
</script>
