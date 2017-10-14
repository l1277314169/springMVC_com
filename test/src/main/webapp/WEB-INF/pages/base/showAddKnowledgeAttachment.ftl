<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>学习资料维护</title>
</head>
<body>
	<div class="widget-content nopadding" style="margin:0px;">
			<table class="table_white_bg" style="width:100%;">
                <tbody>
                  <tr>
                   <td colspan="2"></td>
                 </tr>
                   <tr>
                   <td class="td_label fill_td" style="padding-left:10px;">顺序：</td>
                   <td>
                   <input type="text" id="orderby" name="orderby"/>
                   </td>
                </tr>
                <tr>
                   <td class="td_label fill_td line-middle" style="padding-left:10px;">学习资料附件：</td>
                   <td colspan="3">
                   <input type="hidden" id="knowledgeId" name="knowledgeId" value="${knowledgeId}"/>
                   <input type="hidden" id="fileName" name="fileName"/>
                    <#assign objectIdFTL="fileName">
					<#include "/widgets/fileUploader.ftl" />
                   </td>
                </tr>
                <!--   删除操作：2015-10-20
                <tr>
				 <td colspan="4" class="td_buttons">
					 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
					 <button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
				 </td>
			   </tr>
                -->
            </tbody>
        </table>
  </div>
</body>
</html>
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
