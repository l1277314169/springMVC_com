<html>
<head>
<title></title>
<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body>
	<div class="widget-content nopadding" style="margin:0px;">
		<form class="form-horizontal" action="#" id="dataForm">
		<input type="hidden" name="customerId" value="${wrCustomer.customerId!''}">
        <table class="table_white_bg" style="width:100%;">
            <tbody> 
               <tr>
            		<td class="td_label fill_td"><i class="cc1">*</i>客户名称：</td>
                    <td  class="td_control">
                    	<input id="customerName" name="customerName" type="text" value="${wrCustomer.customerName!''}"/>
                    </td>
                    </tr>
                    <tr>
                        <td class="td_label"><i class="cc1">*</i>品牌：</td>
                    <td class="td_control">
                    	<input id="brandIds" name="brandIds" type="text" value="${wrCustomer.brandIds!''}" required/>
                    </td>
                </tr>
                <tr>
                  <td class="td_label">备注：</td>
                    <td class="td_control">
                    	<textarea name="remark" maxlength="200" id="remark">${wrCustomer.remark!''}</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   	</tr>
            </tbody>
        </table>
</form>
</div>
</body>
</html>
<script>
var editDialog; 
 $(function(){
 loadBindNames();
//编辑客户信息
$("#savetButton").bind("click",function(){
	$(this).attr("disabled","disabled");
	 if(!validobj.form()){
		$("#savetButton").removeAttr("disabled");
		return false;
		}	  
     if($("#customerName").next().html() != "该客户已存在")
        {
		$.ajax({
            url : "${contextPath}/wrCustomer/editWrCustomer",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
			   if(result.code=="success"){
				   	layer.alert(result.message,function(){
				   			// window.location.reload();
		   				   //  editDialog.close();
		   				   parent.document.location.href = "${contextPath}/wrCustomer/query/";
		   				   parent.layer.closeAll('iframe');
			   		});	
				}else {
					layer.alert(result.message);
					$("#savetButton").removeAttr("disabled");
		   		}
			},
			error:function(){
				$("#savetButton").removeAttr("disabled");
			}
        });
	}
	  });
	var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules : {
		 customerName: {
				required: true
			},
			remark: {
			   maxlength:200
			}
		},
		messages:{
			customerName:{
				required:"不能为空"
			},
		},
		errorPlacement: function (error, element) {
            var elem = $(element);
            error.insertAfter(element);
        },
	});
     $('#customerName').bind("change",function(){
		var elem = $(this);
		var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
		var customerName= $("#customerName").val().trim();
		if(customerName == ""){
			elem.next().remove();
		} else {
			elem.next().remove();
		$.ajax({
	       url : "${contextPath}/wrCustomer/validateWrCustomer/"+customerName,
	       type :"post",
	       dataType:"text",
	       cache : false,
	       success:function(data)
	       {
			  if(data=="success"){
				  elem.after(errorHtml);
				  elem.next().html("该客户已存在");
					}else{
						elem.next().remove();
						validobj.form();
					}
				},
			});
		}
	});
	 });
	 
	 //品牌的设置
    function loadBindNames(){
	$.ajax({
		type : "post",
		url : "${contextPath}/wrCustomer/getwrCustomerAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
		    var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#brandIds").select2({
				width:450,
				multiple: true,
				placeholder: "请选择",
				allowClear: true,
				closeOnSelect:false,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};
</script>
