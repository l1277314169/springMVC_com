<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<img id="loading" src="${contextPath}/images/loading.gif" style="display:none;">
<form id="uploadForm" action="" method="post">
		<div>
			<div>
				<table class="table_white_bg">
					<tr>
						<td colspan="2">
							<div align="left" style="margin:20px">
								<a href="${contextPath}/download/import_template/${clientId}/store_import_template.xlsx" target="_self" style="text-decoration:none;color:#fff;">
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
						<td width="20%" align="right" valign="top"><a id="downloadLink" href="" target="_self" style="text-decoration:none;color:#FF0000;display:none">下载结果&nbsp;&nbsp;&nbsp;<a></td>
						<td width="80%">&nbsp;<span style="text-decoration:none;color:#FF0000;display:none" id="countDiv">成功<span id="successCount"></span>条&nbsp;&nbsp;失败<span id="errorCount"></span>条</span></td>
					</tr>
					<tr style="height:100px;">
						<td colspan="2" align="center">
						 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
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
                submitHandler: function() {
                	var clientId = "${clientId}";
                	var url = "${contextPath}/store/import";  
                	if(clientId=="15"){
                		url = "${contextPath}/store/importColgateStore";  
                	}else if(clientId=="17"){
                		url = "${contextPath}/store/importAppleStore";
                	}else if(clientId=="9"){
                		url = "${contextPath}/store/importBwybStore";
                	}else if(clientId=="8"){
                		url = "${contextPath}/store/importWorkLogStore";
                	}else if(clientId=="11"){
                		url = "${contextPath}/store/importFerreroStore";
                	}else if(clientId=="25"){
                		url = "${contextPath}/store/importHuishi";
                	}else if(clientId=="13"){
                		url = "${contextPath}/store/importColgate2";
                	}
                	jQuery("body").showLoading();
                    $("#uploadForm").ajaxSubmit({  
                        url:url,  
                        type:"post",  
                        enctype:"multipart/form-data",  
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",  
                        dataType:"json",  
                        success: function(data){
	                        if(data.UnknownColumnName != '' && data.UnknownColumnName != undefined){
	                        	jQuery("body").hideLoading();
		                		layer.alert(data.UnknownColumnName);
		                		$("#downloadLink").hide(); 
		                		$("#countDiv").hide();   
		                	}else{
		                		jQuery("body").hideLoading();  
		                		if(data.errDataExcelPath!= ''){
		                		if(data.errorMsg==null){
			                		layer.alert("导入成功，请点击下载结果查看错误数据");  
			                		$("#downloadLink").attr("href","${contextPath}/download/import_error/${clientId}/"+data.errDataExcelPath).show();    
			                		$("#successCount").html(data.successCount);
			                		$("#errorCount").html(data.errorCount);
			                		$("#countDiv").show();
			                		}else{
			                		layer.alert(data.errorMsg);
			                		}
			                		
			                	}else{
			                		$("#successCount").html(data.successCount);
			                		$("#errorCount").html(data.errorCount);
			                		$("#downloadLink").hide(); 
			                		$("#countDiv").hide();
			                		layer.alert("导入完成"); 
			                	}    
		                	}
                        },  
                        error: function() {
                        	jQuery("body").hideLoading();
                            layer.alert('导入失败！');  
                        }  
                    });  
                    return false;  
                }  
            });  
        }); 

</script>