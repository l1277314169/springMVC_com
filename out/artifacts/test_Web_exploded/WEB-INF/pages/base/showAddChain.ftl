<style type="text/css">
    #ui-datepicker-div {z-index:99 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="table_white_bg" >
            <tbody>
            	<tr>
                	<td class="td_label" ><i class="cc1">*</i>连锁名称：</td>
                    <td class="td_control"><input type="text"   name="chainName" required=true></td>
                </tr> 
                <tr>
                	 <td class="td_label"><i class="cc1">*</i>当前层级：</td>
                     <td class="td_control">
	                    <input type="text" id="superior" name="grade"  required=true>
                    </td>
                </tr>
                <tr id="ids">
                <td  class="td_label">上级连锁：</td>
                    <td class="td_control">
	                    <input type="text" id="ditch" name="parentId">
                    </td>
                </tr> 
                <tr>
                	<td  class="td_label"><i class="cc1">*</i>所属渠道：</td>
                	 <td class="td_control">
	                    <input type="text" id="clientStructureId" name="clientStructureId"  class="input-medium"   readonly onclick="showMenu();" required=true>
                    	<input type="hidden" id="channelId" name="channelId">
                    </td>
                </tr>
                <tr>
                	<td class="td_label">英文：</td>
                    <td class="td_control"><input type="text"  name="chainNameEn"></td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
						<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
        <div id="menuContent" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemoChannel" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
</form>
<script>
var chainData = [{ id: 1, text: '一级连锁' }, { id: 2, text: '二级连锁' }];
$("#superior").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: chainData
        });
var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules : {
				chainName: {
					maxlength: 50,
				},
				chainNameEn: {
					isEnglish : true
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
		url : "${contextPath}/chain/addChain",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
			   	layer.alert(result.message,function(){
	   				parent.document.location.href = "${contextPath}/chain/query";
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
		$("#ditch").val("");
	}
	if($(this).val()==2){
	    $('#ids').show();
	    changeChain()
	}
});
$(function(){
    $('#ids').hide();

});
function changeChain() {
	$("#ditch").empty();
 	var value=$("#superior").val();
	var parms = {gradeId : value,};
		$.ajax({
			type : "post",
			url : "${contextPath}/chain/findForListChain",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				if(jsonData != null){
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.chainId+"\",\"text\":\""+item.chainName+"\"},";
						} else {
							thisData += "{\"id\":\""+item.chainId+"\",\"text\":\""+item.chainName+"\"}";
						}
					});
					thisData += "]";
				}
				var cData = $.parseJSON(thisData);
				if(cData == null){
					cData = [];
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


var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
			error : function() {
				alert('亲，组织架构加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
        view: {
            dblClickExpand: false,
            selectedMulti: true, //是否允许多选
            txtSelectedEnable: false //是否允许选中节点的文字
            //autoCancelSelected: false //不允许按下Ctrl键取消节点选中状态
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
        
    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemoChannel");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#clientStructureId").attr("value", treeNode.name);
		$("#channelId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    function showMenu() {
        var structureObj = $("#clientStructureId");
        var clientStructureOffset = $("#clientStructureId").position();
        $("#menuContent").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemoChannel");
		zTree.expandAll(true);
	};
	

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemoChannel"), setting);
        
    });
    
$(function(){
  $("#clientStructureId").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
	$("#clientStructureId").val(""); 
	$("#channelId").val(""); 
	}; 
});
});
</script>
