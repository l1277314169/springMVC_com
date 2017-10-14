<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<img id="loading" src="${contextPath}/images/loading.gif" style="display:none;">
<form id="uploadForm" action="" method="post">
	<input type="hidden" name="clientId" value="${clientId}">
		<div>
			<div>
				<table width="100%" border="0" style="font-size:12px">
					<tr>
						<td colspan="2"><div align="left" style="margin:20px">
						<a href="${contextPath}/download/import_template/${clientId}/sku_import_template.xlsx" target="_self" style="text-decoration:none;color:#fff;">
								<span
									style="background-color:#00bc9c;height: 40px;width:70px; padding:10px;"><img
									height="20px" alt="" src="${contextPath}/images/u11_normal.png"
									style="margin-right:10px">模板下载</span></a>
							</div></td>
					</tr>
					<tr>
						<td width="20%" align="right" valign="top">导入说明：</td>
						<td width="80%" style="line-height: 2;">请先下载导入模板，仔细阅读表头文字说明。请严格按照表头说明填入内容，以确保导入顺利完成。</td>
					</tr>
					<tr>
						<td align="right" valign="middle">选择文件：</td>
						<td style="padding:10px;" id="fileId">
								<input type="file" name="file" id="fileField" size="28" required onchange="change(this)"/>
							</td>
					</tr>
					<tr>
						<td style="padding-left: 30px;vertical-align:middle;"><a id="downloadLink" href="" target="_self" style="text-decoration:none;color:#FF0000;display:none">下载失败结果&nbsp;&nbsp;&nbsp;<a></td>
						<td style="vertical-align:middle;"><span style="text-decoration:none;color:#FF0000;display:none" id="countDiv">成功<span id="successCount"></span>条&nbsp;&nbsp;失败<span id="errorCount"></span>条</span></td>
					</tr>
					<tr style="height:100px;">
						<td colspan="2" align="center">
				          	<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
           					<button class="btn btn-success margin-left-18px" type="submit" id="saveImport" >导入</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>

<script type="text/javascript">

$(function() { 
            $("#uploadForm").resetForm().validate({  
                rules: {  
                    "fileField": {  
                        required: true,  
                        accept: "xlsx"  
                    }  
                },  
                messages: {  
                    "fileField": {
                        required: "请选择上传文件",  
                        accept: "文件格式不支持，请上传 xls 格式的文件"  
                    }  
                },  
                submitHandler: function() {  
                    $("#uploadForm").ajaxSubmit({  
                        url:"${contextPath}/sku/mixImport",  
                        type:"post",  
                        enctype:"multipart/form-data",  
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
                        dataType:"json",  
                        success: function(data){
	                        if(data.code == 'error'){
	                        	layer.alert(data.message);
	                        }else if(data.attributes['errorCount'] == 0){
	                       		layer.alert(data.message);
	                        }else if(data.code == 'success'){
	                        	$("#downloadLink").attr("href","${contextPath}/download/import_error/${clientId}/"+data.attributes['errDataExcelPath']).show();
	                        	$('#successCount').html(data.attributes['successCount']);
	                        	$('#errorCount').html(data.attributes['errorCount']);
	                        	$("#countDiv").show();
	                        	//window.location.reload();
	                        }
                        },  
                        error: function() {
                            layer.alert('导入失败！');  
                        }  
                    });  
                    return false;  
                }  
            });  
        }); 
function change(o) {
    if(o.value.indexOf('.xls') == -1){
    	layer.alert("文件格式不支持，请上传 xls 格式的文件");
		$('#fileField').val('');
    	return false;
    }
}

</script>