 <html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<style type="text/css">
	.ui-datepicker-calendar {
    	display: none;
    }
</style>
</head>
<body>
<form id="dataForm" method="post">
		<input type="hidden" id="clientStructureId"  name="clientStructureId" value=""/>
        <table class="table_white_bg">
            <tbody>
            	<tr>
            	    <td class="td_label" ><i class="cc1">*</i>月份编号：</td>
                    <td  class="td_control"><input type="text" id="monthId" name="monthId" value="" readonly="readonly" class="input-medium" required/></td>           	    
            	</tr>
            	<tr>
            		<td class="td_label" ><i class="cc1">*</i>所在部门：</td>
                    <td class="td_control">
                    	<input type="text" id="clientStructureName" name="clientStructureName" readonly value=""  onclick="showMenu();" required>
                    </td>
            	</tr>
            	<tr>
                    <td class="td_label" ><i class="cc1">*</i>门店名称：</td>
                    <td class="td_control">
                    	<input type="text" id="storeId" name="storeId" value="" required/>
                    </td> 
                </tr>
                <tr>
                	<td class="td_label">sku：</td>
                    <td class="td_control">
                    	<input type="text" id="skuId" name="skuId" value=""  />
                    </td>
                </tr> 
                <tr>
	                <td class="td_label" >销量：</td>
	                    <td class="td_control">
	                    	<input type="text" id="salesVolume" name="salesVolume" value=""/>
	                    </td>	                  
                </tr>
                <tr>
                	<td class="td_label">销售金额：</td>
	                   <td class="td_control"> 
		                   <input type="text" id="salesAmount" name="salesAmount" value=""/>
	                 </td>
                </tr> 
                <tr>
					<td colspan="2" class="td_buttons">
							<button data-dismiss="dialog" id="cancelEdit" type="button" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>		 
            </tbody>
        </table>
</form>
<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_pop" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
 /body>
</html>
<script>

var setting = {
	async : {
		enable : true,
		type: "get",
		url : "${contextPath}/clientStructure/getTreeNodeWithPer",
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
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    
    function onClick(e, treeId, treeNode) {
    	var structureId = $("#clientStructureId").val();
    	$("#clientStructureName").attr("value", treeNode.name);
		$("#clientStructureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");
		zTree.expandAll(true);
	};
	
    function showMenu() {
        var structureObj = $("#clientStructureName");
        var clientStructureOffset = $("#clientStructureName").position();
        $("#menuContent").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);       
    }
    
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

	$("#monthId").datepicker({
		changeMonth:true,
		changeYear:true,
		dateFormat:"yymm",
		yearRange:"2014:2025",
		prevText: "<",
		nextText: ">",
		closeText: "确定",
		showButtonPanel: true,
		onClose:function(dateText,inst){
            var date = new Date();
            var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();//得到选中的月份值	
            var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();//得到选中的年份值
            date.setFullYear(year);
            date.setMonth(month);           
            $("#monthId").datepicker('option', 'dateFormat','yymm');
            $('#monthId').datepicker('setDate',date);
		}
	});

	$('#cancelEdit').click(function(){
		window.parent.location.reload();
   		window.parent.editDialog.close();
	});
	
    //验证
	var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules : {
			monthId: {
				required: true
			},
			storeId: {
				required: true
			},
			clientStructureId: {
				required: true
			},
			salesVolume:{
				required: true
			},
			salesAmount:{
				required: true
			}
		},
		messages:{
			clientStructureId:{
				required:"不能为空"
			},
			storeId:{
				required:"不能为空"
			},
			monthId:{
				required:"不能为空"
			},
			salesVolume:{
				required:"不能为空"
			},
			salesAmount:{
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
                //$('a.select2-choice').addClass(errorClass);
            }
		},

        unhighlight: function (element, errorClass, validClass) {
            var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').removeClass(errorClass);
            } else {
                elem.removeClass(errorClass);
                //$('a.select2-choice').removeClass(errorClass);
            }
        }
	});
	
	$(document).bind("change", ".select2-offscreen", function () {
        if (!$.isEmptyObject(validobj.submitted)) {
            validobj.form();
        }
    });
    
    $("#clientStructureName").on("click",function(){
		showMenu();
	});
    
   //更新销量
	$("#savetButton").bind("click",function(){	 			 
	 	$(this).attr("disabled","disabled");
		//验证
		if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}
		$.ajax({
			url : "${contextPath}/factMonthlySales/addFactMonthlySales",
			type : "post",
			dataType:"json",
			async: false,
			data : $("#dataForm").serialize(),
			success : function(result) {
				if(result.code=="success"){
					layer.alert(result.message,function(){
						window.parent.location.reload();
		   				window.parent.editDialog.close();
		   			});
				}else if(result.code=="error"){
					layer.alert(result.message);
					$("#savetButton").removeAttr("disabled");
				}else {
					layer.alert(result);
					$("#savetButton").removeAttr("disabled");
		   		}
		   	},
		   	error: function(result) {
				$("#savetButton").removeAttr("disabled");
			}
		});				
	}); 
    
$(document).on("select2-opening", function (arg) {
	var elem = $(arg.target);
	if ($("#s2id_" + elem.attr("id") + " ul").hasClass("myErrorClass")) {
		$(".select2-drop ul").addClass("myErrorClass");
	} else {
		$(".select2-drop ul").removeClass("myErrorClass");
	}
});

$.fn.zTree.init($("#treeDemo_pop"), setting);

});
$("#clientStructureName").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
	$("#clientStructureName").val(""); 
	$("#clientStructureId").val(""); 
	}; 
});
 
$("#storeId").select2({
		width:145,
		placeholder: "输入字符查询",
		minimumInputLength: 2,
		allowClear : true,
		ajax: {
			url: "${contextPath}/store/getStoreInfoByName",
			dataType: 'json',
			quietMillis: 250,
			data: function (term, page) {
				return {
					q: term,
					page: page
				};
			},
			results: function (data,page) {
				var more = page;
				return { results: data,more: more };
			}
		},
		initSelection: function(element, callback) {
			var id = $(element).val();
			if (id != "") {
				$.ajax("${contextPath}/store/getStore/"+id, {
					dataType: "json"
				}).done(function(data) { callback(data); });
			}
		},
		formatResult: repoFormatResult,
		formatSelection: repoFormatSelection,
		escapeMarkup: function (m) { return m; }
	});

	function repoFormatResult(repo) {
		return repo.name;
	}
	function repoFormatSelection(repo) {
		return repo.name;
	} 
 
$("#skuId").select2({
		width:145,
		placeholder: "输入字符查询",
		minimumInputLength: 2,
		allowClear : true,
		ajax: {
			url: "${contextPath}/sku/getSkuInfoByName",
			dataType: 'json',
			quietMillis: 250,
			data: function (term, page) {
				return {
					q: term,
					page: page
				};
			},
			results: function (data,page) {
				var more = page;
				return { results: data,more: more };
			}
		},
		initSelection: function(element, callback) {
			var id = $(element).val();
			if (id != "") {
				$.ajax("${contextPath}/sku/getSku/"+id, {
					dataType: "json"
				}).done(function(data) { callback(data); });
			}
		},
		formatResult: repoFormatResult,
		formatSelection: repoFormatSelection,
		escapeMarkup: function (m) { return m; }
	}); 
 	
</script>
 