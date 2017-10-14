<img id="loading" src="${contextPath}/images/loading.gif" style="display:none;">
<form id="uploadForm" action="" method="post">
		<div>
			<div>
				<table class="table_white_bg">
					<tr>
						<td colspan="2">
							<div align="left" style="margin:20px">
								<a href="http://www.channelplus.cn/download/import_template/0/store_import_template.xlsx" target="_self" style="text-decoration:none;color:#fff;">
									<span
										style="background-color:#00bc9c;height: 40px;width:70px; padding:10px;"><img
										height="20px" alt="" src="${contextPath}/images/u11_normal.png"
										style="margin-right:10px">模板下载</span></a>
							</div>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" valign="top">导入说明：</td>
						<td width="80%">本功能适用请先下载导入模板，仔细查看模板表头说明，整理好数据后使用导入功能。</td>
					</tr>
					<tr>
						<td align="right" valign="middle">选择文件：</td>
						<td style="padding:10px;">
								<input type="file" name="fileField" id="fileField" size="28"/>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr style="height:100px;">
						<td colspan="2" align="center">
						<button data-dismiss="dialog" type="button" class="btn btn-danger">取消</button>
						<button class="btn btn-success margin-left-18px" type="submit">导入</button>
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
                        url:"${contextPath}/store/import",  
                        type:"post",  
                        enctype:"multipart/form-data",  
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
                        dataType:"json",  
                        success: function(data){
	                        if(data.code == 'error'){
	                        	alert(data.message);
	                        }else if(data.code == 'success'){
	                        	importDialog.close();
	                        	alert(data.message);
	                        	window.location.href = "${contextPath}/store/query"
	                        }
                        },  
                        error: function() {  
                            alert('导入失败！');  
                        }  
                    });  
                    return false;  
                }  
            });  
        }); 

</script>