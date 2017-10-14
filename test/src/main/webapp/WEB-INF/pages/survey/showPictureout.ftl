<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<style type="text/css"> 
    .table_white_bg tr td{ 
    padding-top:20px;
    }
    #_reminder{
    align: center; 
    line-height:30px;
    padding-left:20px;
    padding-right:20px;    
    } 
   #_td{
   padding-left:45px
   }
   #download{
     color : #FF781C;
   }
   #format{
   color : #D81B00;
  
   }     
</style>
<form id="_dataForm" method="post" name="_dataForm">
    <table class="table_white_bg"id="table_white_bg">
        <tbody>
           <tr id="tr_email">
                <td class="td_label" id="_td"><i class="cc1">*</i>请输邮箱号：</td>
                <td class="td_control">
                	<input type="text" id="email" name="email" class="input-medium" /><span id="format" type="text"></span>
                </td>
            </tr>
            <tr> 
           <td colspan="2" id ="_reminder"> <span id="reminder"></span></td>
            </tr>
            <tr>
				<td colspan="2" class="td_buttons">
					<button id="cancel" data-dismiss="dialog" type="button" onClick="javascript:layer.closeAll();" class="btn btn-danger">取消</button>
					<button id="exportButton" type="button" class="btn btn-success margin-left-18px">导出</button>
				</td>
		   </tr>
        </tbody>
    </table>
</form>
<script type="text/javascript">
	
  //导出
	$("#exportButton").bind("click",function(){
		$(this).attr("disabled","disabled");
		//验证
		if(!$("#_dataForm").validate({
				rules : {
					email:{
						required:true
					}
				},messages:{
					email:{
						required:"不能为空"
					} 
				},
				}).form()){
				$("#exportButton").removeAttr("disabled");
				return;
		 }
		 else{
		    var email =$("#email").val();  
		     var filter  = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
			 if (filter.test(email)){ 
			 	 var _url ="${contextPath}/appleSurvey/downloadImage?email="+email;  
			 	alert("文件正在生成，文件生成后会立即把下载地址发送到您的邮箱，可打开邮箱查看下载地址，请务必2天内完成下载。(如果文件过大时间可能会有点长，请见谅)");
			 	layer.closeAll();
			 	jQuery.ajax({
					url : _url,
					type : 'POST',
					dataType : 'json',
					data:$("#searchForm").serialize(),
					cache : false,
			        success : function(data, textStatus, xhr) {		         
					     if(data.code=='success'){
					     //   $("#format").hide();
						 //   $("#download").attr("href",data.message);  
			     		 //   $("#reminder").html("文件已经生成，您可以选择<a id='download' href=\""+data.message+"\">直接下载</a>，下载地址已经发送到邮箱，也可打开邮箱查看下载地址，请于2天内下载完成。");							 	
				           }
				         else{
				             alert(data.message);
				         } 
					},
					error : function(xhr, textStatus, errorThrown) {
							 $("#reminder").html("系统异常");							
					}
				}); 	
		    }else {
			 	   $("#exportButton").removeAttr("disabled");
	            	 	 $("#format").text("格式错误");
	            	     $("#format").show();
			 }
		 }		
	});
	
</script>