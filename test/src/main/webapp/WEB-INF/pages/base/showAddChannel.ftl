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
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
                	<td class="td_label" ><i class="cc1">*</i>渠道名称：</td>
                    <td class="td_control"><input type="text" name="channelName" required=true></td>
                </tr> 
                <tr>
                	<td class="td_label"><i class="cc1">*</i>当前层级：</td>
                     <td class="td_control">
	                    <input type="text" id="superior" name="grade"  required=true>
                    </td>
                </tr>
                 <tr id="ids">
                 <td class="td_label" >上级渠道：</td>
                    <td class="td_control">
	                    <input type="text" id="ditch" name="parentId">
                    </td> 
                </tr>
                <tr>
                 	<td class="td_label" >英文：</td>
                    <td class="td_control"><input type="text" name="channelNameEn"></td>
                </tr>
                  <tr>
                  	
					<td colspan="2" class="td_buttons">
							<button data-dismiss="dialog" type="button" class="btn btn-danger" onClick="javascript:parent.layer.closeAll('iframe');"  >取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script type="text/javascript">
var channelData = [{ id: 1, text: '一级渠道' }, { id: 2, text: '二级渠道' }, { id: 3, text: '三级渠道' }];
$("#superior").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: channelData
        });
        
var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		 rules : {
			channelName: {
				maxlength: 50,
			},
			channelNameEn: {
				isEnglishContain : true,
			}
		},
		messages:{
			superior:{
				required:"不能为空"
			}
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
//新增职位
$("#savetButton").bind("click",function(){
	$(this).attr("disabled","disabled");
	//验证
	if(!validobj.form()){
		$("#savetButton").removeAttr("disabled");
		return false;
	}
	$.ajax({
		url : "${contextPath}/channel/addChannel",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
			   	layer.alert(result.message,function(){
	   				parent.document.location.href = "${contextPath}/channel/query"
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

$("#superior").bind("change",function(){
	if($(this).val()==1||$(this).val()==''){
	    $('#ids').hide();
		$("#ditch").text("");
	}
	if($(this).val()==2){
	$('#ids').show();
	changeChannel()
	}
	if($(this).val()==3){
	$('#ids').show();
	changeChannel()
	}
});

$(function(){
    $('#ids').hide();

});

function changeChannel() {
	$("#ditch").empty();
 	var value=$("#superior").val();
	var parms = {gradeId : value,};
		$.ajax({
			type : "post",
			url : "${contextPath}/channel/findForListGrade",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				if(jsonData != null){
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.channelId+"\",\"text\":\""+item.channelName+"\"},";
						} else {
							thisData += "{\"id\":\""+item.channelId+"\",\"text\":\""+item.channelName+"\"}";
						}
					});
					thisData += "]";
				}
				var cData = $.parseJSON(thisData);
				if(cData == null){
					cData =[];
				}
				$("#ditch").select2({
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
