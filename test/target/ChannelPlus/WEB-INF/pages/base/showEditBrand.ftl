<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" name="clientId">
		<input type="hidden" id="brandId" name="brandId" value="${brand.brandId}">
        <table class="table_white_bg">
            <tbody>
           		 <tr>
                    <td class="td_label" ><i class="cc1">*</i>品牌层级：</td>
                    <td class="td_control">
                    	<input type="text" id = "grade" name ="grade" value="${brand.grade!''}" >
                    </td>
                    
                	<td class="td_label" id="parentBrand">上级品牌：</td>
                    <td class="td_control" id="parentBrand1">
                    	<input type="text" id="parentId" name="parentId" value="${brand.parentId!''}">
                    </td>
                </tr>
                <tr>	
                	<td class="td_label"><i class="cc1">*</i>品牌名称：</td>
                   
                    <td class="td_control">
                    	<input type="text" name="brandName" value="${brand.brandName!''}" required=true />
                    </td>
                    
                	<td class="td_label">英文名称：</td>
                    <td class="td_control">
                    	<input id="brandNameEn" name="brandNameEn" type="text"  value="${brand.brandNameEn!''}"/>
                    </td>
                </tr> 
                <tr>
                    <td class="td_label" >品牌编号：</td>
                    <td class="td_control">
                  		<input type="text" name="brandNo"  value="${brand.brandNo!''}"/ >
					</td>
					
                	<td class="td_label">是否自有品牌：</td>
                    <td class="td_control">
                    	<#if brand.isOwner == "1">
                    	<input type="radio" name="isOwner" value="1" checked="checked">&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;
                    	<input type="radio" name="isOwner" value="0">&nbsp;否
                    	<#else>
                    	<input type="radio" name="isOwner" value="1">&nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;
                    	<input type="radio" name="isOwner" value="0" checked="checked">&nbsp;否
                    	</#if>
                    </td>
                </tr>
                <tr>
                	<td class="td_label">所属公司：</td>
                    <td class="td_control">
                   		<input id="firm" name="firm" type="text" value="${brand.firm!''}">
                    </td>
                    <td class="td_label"></td><td class="td_control"></td>
                	 </tr>
                
				<tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
                    <td class="td_control" colspan="3" valign="middle">
                    	<textarea rows=2 maxlength="300" name="remark">${brand.remark!''}</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
								<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
$(document).ready(function () {
	var editBrandData = [{ id: 1, text: '一级品牌' }, { id: 2, text: '二级品牌' }];
	$("#grade").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: editBrandData
	 });
	loadParentBrand();
 	if($("#grade").val()=="1"){
	 	$("#parentBrand").hide();
		$("#parentBrand1").hide();
 	}
	//上级品牌展示
	$("#grade").bind("change",function(){
		if($(this).val() != "2"){
			$("#parentBrand").hide();
			$("#parentBrand1").hide();
			$("#parentId").val("");
		
		}else if($(this).val() == "2"){
			$("#parentBrand").show();
			$("#parentBrand1").show();
		}
		
	});
 var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules:{
			grade:
				{required:true},
			brandName:
				{maxlength:50},
			brandNo:
				{maxlength:50},
			firm:
				{maxlength:50},
			brandNameEn:
				{isEnglish:true,
				maxlength:50},
			remark:
				{maxlength:200}
			},
		messages:{
			grade:{
				required:"不能为空"
			},
		},
		errorPlacement: function (error, element) {
            var elem = $(element);
            error.insertAfter(element);
        },
		highlight: function (element, errorClass, validClass) {
			var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').addClass(errorClass);
            } else {
                elem.addClass(errorClass);
            }
		},
        unhighlight: function (element, errorClass, validClass) {
            var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').removeClass(errorClass);
            } else {
                elem.removeClass(errorClass);
            }
        }
	});
	
	$(document).die().live("change", ".select2-offscreen", function () {
        if (!$.isEmptyObject(validobj.submitted)) {
            validobj.form();
        }
    });
	//编辑品牌--保存
	$("#savetButton").bind("click",function(){
		$(this).attr("disabled","disabled");
		//验证
		if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}
		$.ajax({
			url : "${contextPath}/brand/updateBrand",
			type : "post",
			dataType:"json",
			async: false,
			data : $("#dataForm").serialize(),
			success : function(result) {
			   if(result.code=="success"){
				   layer.alert(result.message,function(){
		   				parent.document.location.href = "${contextPath}/brand/query";
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
	});

	
});
//加载上级品牌
function loadParentBrand(){
	var brandId  = $('#brandId').val();
	$.ajax({
			type : "post",
			url : "${contextPath}/brand/loadParentBrand",
			dataType : "json",
			data:{brandId:brandId},
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				if(jsonData != null){
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
								thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"},";
						} else {
								thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"}";
						}
					});
					thisData += "]";
				}
					var cData = $.parseJSON(thisData);
					if(cData == null){
						cData = [];
					}
					$("#parentId").select2({
						width:145,
						placeholder: "请选择",
						allowClear: true,
						data: cData
					});
			},
			error : function(data) {
				alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
}
</script>
