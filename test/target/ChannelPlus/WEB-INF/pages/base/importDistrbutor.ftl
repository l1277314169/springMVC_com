<#include "/common/head.ftl" />
 <#include "/common/foot.ftl" /> 
<img id="loading" src="${contextPath}/images/loading.gif" style="display:none;">
<form id="uploadForm" action="" method="post" enctype="multipart/form-data">
		<div>
			<div>
				<table width="100%" border="0" style="font-size:12px">
					<tr>
						<td colspan="2" height="50px"><div align="left" style="margin:20px">
						<a href="http://www.channelplus.cn/download/import_template/0/distributor_import_template.xlsx" target="_self" style="text-decoration:none;color:#fff;">
								<span style="background-color:#00bc9c;height: 40px;width:70px; padding:10px;" align="center">
								模板下载</span></a>
							</div></td>
					</tr>	
					<tr>
						<td width="20%" height="50px" align="right" valign="top">导入说明：</td>
						<td width="80%">请先下载人员导入模板，仔细阅读表头文字说明。请严格按照表头说明填入内容，以确保导入顺利完成。</td>
					</tr>
					<tr>
						<td align="right" valign="middle" height="50px" style="vertical-align:middle;">选择文件：</td>
						<td style="padding:10px;vertical-align:middle">
								<input type="file" name="file" id="fileField" size="28"/>
							</td>
					</tr>
					<tr>
						<td colspan="2" align="center" style="vertical-align:middle; text-align:center; height:100px">
						 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
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
                        url:"${contextPath}/clientUser/import",  
                        type:"post",  
                        enctype:"multipart/form-data",  
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
                        dataType:"json",  
                        success: function(data){
	                        if(data.code == 'error'){
	                        	layer.alert(data.message);
	                        }else if(data.code == 'success'){
	                        	importDialog.close();
	                        	layer.alert(data.message);
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
</script>