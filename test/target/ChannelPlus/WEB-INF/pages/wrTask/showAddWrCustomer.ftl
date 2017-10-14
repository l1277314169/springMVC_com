<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title></title>
</head>
<body>
<div>
	<div class="widget-content nopadding" style="margin:0px;">
		<form class="form-horizontal" action="#" id="dataForm">
			<table class="table_white_bg" style="width:100%;">
                <tbody>
                 <tr>
                	<td class="td_label fill_td"><i class="cc1">*</i>客户名称：</td>
                	<td class="td_control"><input type="text" name="customerName" id="customerName" class="required"/></td>
                </tr> 
                <tr>
                   <td class="td_label"><i class="cc1">*</i>品牌：</td>
	                <td colspan="3" class="td_control">
	                  <input type="text" id="brandId" name="brandId" required>
	                </td>
                </tr>
                 <tr>
                  <td class="td_label">备注：</td>
                    <td class="td_control">
                    	<textarea name="remark" maxlength="200" id="remark"></textarea>
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
  var addDialog,editDialog;
	 $(function(){
	    $('#cancel').click(function(){
		parent.addDialog.close();
	   });
	   loadBindNames();
	 //新增促销物料
    $("#savetButton").bind("click",function(){
        if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}
        submitFrm();
     });
       
     function submitFrm(){  
        if($("#customerName").next().html() != "该客户已存在")
        {
		$.ajax({
            url : "${contextPath}/wrCustomer/addWrCustomer",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
				   if(result.code=="success"){
				   		layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/wrCustomer/query";
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
	}
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
			$("#brandId").select2({
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