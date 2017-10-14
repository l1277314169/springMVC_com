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
		<input type="hidden" id="categoryId" name="categoryId" value="${category.categoryId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label"><i class="cc1">*</i>品类级别：</td>
                    <td class="td_control">
                    	<input type="text" id = "grade" name ="grade"  value="${category.grade!''}" required>
                    </td>
					
					<td class="td_label" id="parentCategory1">上级品类：</td>
                    <td id="parentCategory2" class="td_control">
	                    <input type="text" id="parenetId" name="parentId" value="${category.parentId}">
                    </td>
                </tr> 
                <tr>
                	<td class="td_label"><i class="cc1">*</i>品类名称：</td>
                    <td  class="td_control">
                    	<input type="text" name="categoryName" value="${category.categoryName}" required=true />
                    </td>
                	<td class="td_label">英文名称：</td>
                    <td class="td_control">
                    	<input id="categoryNameEn" name="categoryNameEn" value="${category.categoryNameEn!''}" type="text" />
                    </td>
                    
                </tr>
                <tr>
                 	 <td class="td_label" >品类编号：</td>
                    	
                    <td class="td_control">
                  		<input type="text" name="categoryNo" value="${category.categoryNo!''}"/ >
					</td>
					<td class="td_label"></td><td class="td_control"></td>
                </tr>
				<tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
                    <td class="td_control" colspan="3" valign="middle">
                    	<textarea rows=2 maxlength="300" name="remark" placeholder="不超过200个字">${category.remark!''}</textarea>
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
		if($("#grade").val()=="1"){
			$("#parentCategory1").hide();
			$("#parentCategory2").hide();
		}
		var editcategoryData = [{ id: 1, text: '一级品类' }, { id: 2, text: '二级品类' }];
		$("#grade").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: editcategoryData
		});
		//加载上级品类
		loadCategory();
		//上级品类展示
		$("#grade").bind("change",function(){
			if($(this).val() != "2"){
			$("#parentCategory1").hide();
			$("#parentCategory2").hide();
			$("#parenetId").val("");
			}else if($(this).val() == "2"){
			$("#parentCategory1").show();
			$("#parentCategory2").show();
			}
		});	
		
	var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules:{
			grade:
				{required:true},
			categoryName:
				{maxlength:50},
			categoryNameEn:
				{maxlength:50}
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
		
	//编辑品类--保存
	$("#savetButton").bind("click",function(){
		$(this).attr("disabled","disabled");
		//验证
		if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}
		$.ajax({
			url : "${contextPath}/category/addCategory",
			type : "post",
			dataType:"json",
			async: false,
			data : $("#dataForm").serialize(),
			success : function(result) {
			   if(result.code=="success"){
				   	layer.alert(result.message,function(){
		   			//	window.location.reload();
		   			//	editDialog.close();
		   			parent.document.location.href = "${contextPath}/category/query";
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

function loadCategory(){
var categoryId = $('#categoryId').val();
	$.ajax({
			type : "post",
			url : "${contextPath}/category/loadCategory",
			dataType : "json",
			data:{categoryId:categoryId},
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				if(jsonData != null){
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.categoryId+"\",\"text\":\""+item.categoryName+"\"},";
						} else {
							thisData += "{\"id\":\""+item.categoryId+"\",\"text\":\""+item.categoryName+"\"}";
						}
					});
					thisData += "]";
				}
				var cData = $.parseJSON(thisData);
				if(cData == null){
					cData=[];
				}
				$("#parenetId").select2({
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
