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
		<input type="hidden" name="materialId" value="${promotionMaterial.materialId!''}">
        <table class="table_white_bg" style="width:100%;">
            <tbody> 
               <tr>
            		<td class="td_label fill_td"><i class="cc1">*</i>物料编号：</td>
                    <td  class="td_control">
                    	<input id="materialCode" name="materialCode" type="text" value="${promotionMaterial.materialCode!''}"/>
                    </td>
                	<td class="td_label fill_td"><i class="cc1">*</i>物料名称：</td>
                	<td class="td_control"><input type="text" name="materialName" id="materialName" value="${promotionMaterial.materialName!''}"></td>
                	
                </tr> 
                <tr>
					<td class="td_label">物料分类：
                	</td>
                    <td class="td_control">
	                   <input type="text" name="category" id="category" value="${promotionMaterial.category!''}">
                    </td>
                    <td class="td_label">子分类：</td>
                    <td class="td_control">
                    	<input id="subCategory" name="subCategory" type="text" value="${promotionMaterial.subCategory!''}"/>
                    </td>
                </tr>
                <tr>
                   <td class="td_label">单价：</td>
                    <td  class="td_control">
                         <input type="text" id="price" name="price" value="${promotionMaterial.price!''}"/>                
                   </td>
                   <td class="td_label">品牌：</td> 
                    <td class="td_control">
	                    <input id="brand" name="brand" type="text" value="${promotionMaterial.brand!''}"/>
					</td>
                </tr> 
                   <tr>
                   <td class="td_label">关键节点：</td>
                     <td class="td_control">
                       <input type="text" id="spec" name="spec" value="${promotionMaterial.spec!''}"/>
                    </td>
                   <td class="td_label">年份：</td>
                    <td  class="td_control">
                         <input type="text" id="batch" name="batch" value="${promotionMaterial.batch!''}"/>                
                   </td>
       
                </tr>
                <tr>
                   <td class="td_label">状态：</td>
                     <td class="td_control">
                       <input type="text" id="status" name="status" value="<#if promotionMaterial.status ?? && promotionMaterial.status == '0'>0<#else>1</#if>"/>
                    </td>
                </tr>
                <tr>
                    <td class="td_label" >备注：</td>
                    <td class="td_control" colspan="4" valign="middle">
                    	<textarea rows=4  maxlength="300" name="remark" placeholder="不超过200个字" >${promotionMaterial.remark!''}</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
													<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>

							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   	</tr>
            </tbody>
        </table>
</form>

<style type="text/css">
#ui-datepicker-div {z-index:99999 !important;}
</style>
<script >
var editDialog; 
 $(function(){
 
//编辑促销物料
$("#savetButton").bind("click",function(){
	$(this).attr("disabled","disabled");
	 if(!validobj.form()){
		$("#savetButton").removeAttr("disabled");
		return false;
		}	  
     if($("#materialCode").next().html() != "物料编号已存在" && $("#materialName").next().html() != "物料的名称已存在")
        {
		$.ajax({
            url : "${contextPath}/promotionMaterial/editPromotionMaterial",
            type : "post",
            dataType:"json",
            data : $("#dataForm").serialize(),
            success : function(result) {
			   if(result.code=="success"){
				   	layer.alert(result.message,function(){
				   			parent.document.location.href = "${contextPath}/promotionMaterial/query";
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
			},
			batch:{
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
</div>
</body>
</html>