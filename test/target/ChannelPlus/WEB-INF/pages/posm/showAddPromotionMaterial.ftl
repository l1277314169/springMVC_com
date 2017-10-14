<html>
<head>
<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title></title>
</head>
<body>
<div>
	<div class="widget-content nopadding" style="margin:0px;">
		<form class="form-horizontal" action="#" id="dataForm">
			<table class="table_white_bg">
            <tbody >
            	<tr>
            		<td class="td_label fill_td"><i class="cc1">*</i>物料编号：</td>
                    <td  class="td_control">
                    	<input id="materialCode" name="materialCode" type="text" class="required"/></td>
                	<td class="td_label fill_td"><i class="cc1">*</i>物料名称：</td>
                	<td class="td_control"><input type="text" name="materialName" id="materialName" class="required"/></td>
                	
                </tr> 
                <tr>
					<td class="td_label">物料分类：
                	</td>
                    <td class="td_control">
	                   <input type="text" name="category" id="category">
                    </td>
                    <td class="td_label">子分类：</td>
                    <td class="td_control">
                    	<input id="subCategory" name="subCategory" type="text" />
                    </td>
                </tr>
                <tr>
                   <td class="td_label">单价：</td>
                    <td  class="td_control">
                         <input type="text" id="price" name="price" />                
                   </td>
                   <td class="td_label">品牌：</td> 
                    <td class="td_control">
	                    <input id="brand" name="brand" type="text" />
					</td>
                </tr> 
                   <tr>
                   <td class="td_label">关键节点：</td>
                     <td class="td_control">
                       <input type="text" id="spec" name="spec" />
                    </td>
                   <td class="td_label">年份：</td>
                    <td  class="td_control">
                         <input type="text" id="batch" name="batch" />                
                   </td>
       
                </tr>
                 <tr>
                   <td class="td_label">状态：</td>
                     <td class="td_control">
   	                   <input type="text" id="status" name="status" class="required" />
                    </td>
       
                </tr>
                <tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
	                    <td class="td_control" colspan="3" valign="middle">
	                    	<textarea rows=2  maxlength="300" name="remark" placeholder="不超过200个字"></textarea>
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
	 //新增促销物料
    $("#savetButton").bind("click",function(){
        if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}
        submitFrm();
     });
       
     function submitFrm(){  
        if($("#materialCode").next().html() != "物料编号已存在" && $("#materialName").next().html() != "物料的名称已存在")
        {
		$.ajax({
            url : "${contextPath}/promotionMaterial/addPromotionMaterial",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
				   if(result.code=="success"){
				   		layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/promotionMaterial/query/";
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
			materialCode: {
				required: true
			},
			materialName: {
				required: true
			},
			category: {
			    maxlength:50
			},
			subCategory: {
			    maxlength:50
			},
			brand: {
			   maxlength:50
			},
			spec: {
			   maxlength:50
			},
			remark: {
			   maxlength:200
			},
			price: {
			  isFloatGteZero:true
			}
		},
		messages:{
			materialCode:{
				required:"不能为空"
			},
			materialName:{
				required:"不能为空"
			}
		},
		errorPlacement: function (error, element) {
            var elem = $(element);
            error.insertAfter(element);
        },
	});
	
     $('#materialCode').bind("change",function(){
		var elem = $(this);
		var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
		var materialCode = $("#materialCode").val().trim();
		if(materialCode == ""){
			elem.next().remove();
		} else {
			elem.next().remove();
		$.ajax({
	       url : "${contextPath}/promotionMaterial/queryPromotionMaterialmaterialCode/"+materialCode,
	       type :"post",
	       dataType:"text",
	       cache : false,
	       success:function(data)
	       {
			  if(data=="success"){
				  elem.after(errorHtml);
				  elem.next().html("物料编号已存在");
					}else{
						elem.next().remove();
						validobj.form();
					}
				},
			});
		}
	});
	
	$('#materialName').bind("change",function(){
		var elem = $(this);
		var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
		var materialName = $("#materialName").val().trim();
		if(materialName == ""){
			elem.next().remove();
		} else {
			elem.next().remove();
		$.ajax({
	       url : "${contextPath}/promotionMaterial/queryPromotionMaterialByParm/"+materialName,
	       type :"post",
	       dataType:"text",
	       cache : false,
	       success:function(data)
	       {
			  if(data=="success"){
				  elem.after(errorHtml);
				  elem.next().html("物料的名称已存在");
					}else{
						elem.next().remove();
						validobj.form();
					}
				},
			});
		}
	});
		 });

$(function() {
	var status = $("#status").val();
	loadStatus(status);

	
});
function loadStatus(status) {
	$.ajax({
		type: "post",
		url: "${contextPath}/promotionMaterial/getType",
		dataType: "json",
		cache: false,
		success: function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData,
			function(index, item) {
				if (index != jsonData.length - 1) {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"},";
				} else {
					thisData += "{\"id\":\"" + item.id + "\",\"text\":\"" + item.name + "\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#status").select2({
				width: 145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error: function(data) {
			layer.alert("数据加载失败！");
		}
	});
};
</script>