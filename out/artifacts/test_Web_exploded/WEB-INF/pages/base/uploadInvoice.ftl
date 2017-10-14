<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="uploadForm" action="" method="post" enctype="multipart/form-data" >
		<div>
			<div>
				<table class="table_white_bg">
					<tr>
						<td colspan="2"> 发票照片：</td>
					</tr>
					<tr>
						<td colspan="2" style="padding:10px;">
							<input type="hidden" id="feedbackId" value="${feedbackId}"/>
							<input type="hidden" id="image" name="image"/>
							<#assign objectIdFTL="image">
							<#assign imageWidthFTL="400">
					  		<#include "/widgets/imageUploaderInvoice.ftl"/>
						</td>
					</tr>
					<tr style="height:100px;">
						<td colspan="2" align="center">
							<button data-dismiss="dialog" type="button" onclick="cancel()" class="btn btn-danger">取消</button>
							<button class="btn btn-success margin-left-18px" id="add" type="button">添加</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
<script>
$(function(){
	$("#add").click(function(){
	setUploaderVal();
	var invoicePic= $("#image").val();
	var feedbackId=$("#feedbackId").val();
	
	layer.confirm('确认上传?',function(){
	$.ajax({
			url : "${contextPath}/contract/uploadInvoice",
			type : "post",
			async: false,
			dataType:"json",
			data:{"invoicePic":invoicePic,"feedbackId":feedbackId},
			success : function(result) {
					layer.alert('上传成功！',function(){
						window.parent.location.reload();
						parent.layer.closeAll('iframe');
						
					}) ;
				},
			error:function(){
					layer.msg('上传失败！') ;
			}
			});
		})
	})

})
function cancel(){
	parent.layer.closeAll('iframe');
}
function getValues(obj){
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

function setUploaderVal(){
	var uploaderList = $(".uploader-list");
	$.each(uploaderList, function(index, obj) {
		 var fid = $(obj).attr("id");
		 var id = fid.replace("fileList_",'');
		 var vals = getValues(obj);
		 $("#"+id).val(vals);
	});  
}
</script>