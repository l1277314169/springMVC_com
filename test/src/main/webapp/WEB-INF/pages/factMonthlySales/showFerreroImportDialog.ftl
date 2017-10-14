<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="uploadForm" action="" method="post">
		<div>
			<div>
				<table class="table_white_bg">
					<tr>
						<td colspan="2">
							<div align="left" style="margin:20px">
								<a href="${contextPath}/download/import_template/${clientId}/import_factMonthlySales.xlsx" target="_self" style="text-decoration:none;color:#fff;">
									<span style="background-color:#00bc9c;height: 40px;width:70px; padding:10px;">
									<img height="20px" alt="" src="${contextPath}/images/u11_normal.png" style="margin-right:10px">模板下载</span></a>
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
							<input type="file" name="fileField" id="fileField" size="28" accept=".xlsx" onchange="check()" />
						</td>
					</tr>
					<tr>
						<td width="20%" align="right" valign="top"><a id="downloadLink" href="" target="_self" style="text-decoration:none;color:#FF0000;display:none">下载结果&nbsp;&nbsp;&nbsp;<a></td>
						<td width="80%">&nbsp;<span style="text-decoration:none;color:#FF0000;display:none" id="countDiv">成功<span id="successCount"></span>条&nbsp;&nbsp;失败<span id="errorCount"></span>条</span></td>
					</tr>
					<tr style="height:110px;">
						<td colspan="2" align="center">
						<button data-dismiss="dialog" type="button" class="btn btn-danger" onclick="closeDailog();">取消</button>
						<button class="btn btn-success margin-left-18px" id="exportBut" type="submit">导入</button>
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
        	jQuery("body").showLoading();
            $("#uploadForm").ajaxSubmit({  
                url:"${contextPath}/factMonthlySales/importFerrero",  
                type:"post",  
                enctype:"multipart/form-data",  
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType:"json",  
                success: function(data){
                 if(data.errorMsg != '' && data.errorMsg !=undefined ){
                		jQuery("body").hideLoading();
                		layer.alert(data.errorMsg);
                		$("#downloadLink").hide(); 
                		$("#countDiv").hide(); 
                	}else{
                		jQuery("body").hideLoading();
                		if(data.errDataExcelPath != '' && data.errDataExcelPath!=undefined){
	                		layer.alert("导入成功，请点击下载结果查看错误数据");                      		
	                		$("#downloadLink").attr("href","${contextPath}/download/import_error/${clientId}/"+data.errDataExcelPath).show();    
	                		$("#successCount").html(data.successCount);
	                		$("#errorCount").html(data.errorCount);
	                		$("#countDiv").show();
	                	}else{
	                		$("#successCount").html(data.successCount);
	                		$("#errorCount").html(data.errorCount);
	                		$("#downloadLink").hide(); 
	                		$("#countDiv").hide();
	                		layer.alert("导入成功"); 
	                	}    
                	}                       	             	               	                  	                 	                      	             	                        	                  	              	 
                },  
                error: function() {  
                    layer.alert('导入失败');  
                }                      
        	});
        }  
    });
}); 

function check(){
	var val = $("#fileField").val();
	if(null==val || ''==val){
		layer.tips("请选择导入数据","#fileField");
	}else if(val.indexOf('.xlsx') == -1){
		layer.tips("导入模板格式错误只支持xlsx类型文件","#fileField");
	}
}

function closeDailog(){
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); //再执行关闭    
}
</script>