<!--业务员维护-->
<html>
<head>
<title></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body style="width:800px;height:630px;">
<div class="widget-content nopadding">
<form id="dataForm"  class="form-horizontal" action="#"  novalidate="novalidate">
	<div class="control-group">
		<div class="fl">
			<label class="control-label " for="structure">所属部门：</label>
			<div class="controls">
			 	<input id="clientStructureId" class="input-medium" name="structureSel"  type="text" readonly   onclick="showMenu();"/>
			 	<input type="text" id="structureId" name="clientStructureId" value="${clientStructureId}" style="display:none;">
			</div>
		</div>
		<div class="fl">
			<label class="control-label fl" for="clientUserId"><i style="color:red">*</i>人员：</label> 
			<div class="controls">
			  <input type="text" id="clientUserId" name="clientUserId" value="${clientUserId}" class="input-medium" <#if clientUserId??>readonly</#if>>
			</div>
		</div>
		<div class="fl">
			<label class="control-label" for="isChecked" style="padding-left: 27px;">未关联业务代表：</label>
			<div class="controls">
			  <input type="checkbox" id="isChecked" class="input-medium" name="isChecked" value="0" >
			</div>
		</div>
			<div class="fl">
				<label class="control-label fl" for="clientUserId">门店编码：</label> 
				<div class="controls">
				  <input type="text" id="storeNo" name="storeNo" value="${storeNo}" class="input-medium">
				</div>
			</div>
			<input type="hidden" id="storeNo" name="storeNo" value="${storeNo}" class="input-medium">
			<div class="fl">
				<label class="control-label fl" for="storeName">门店名称：</label> 
				<div class="controls">
				  <input type="text" id="storeName" name="storeName" value="${storeName}" class="input-medium" style="margin-top: 3px;"> 
				</div>
			</div>
			<input type="hidden" id="storeName" name="storeName" value="${storeName}" class="input-medium">
	</div>
<div class="form-actions">
		<span style="text-align: center;display: inline-flex;font-size: 12px">您已关联：<label id ="checkCount"  style="color:red;width: 50px;">0</label>家门店</span>
		<span style="margin-left: 50px;font-size: 12px">所有门店：<input type="checkbox" id="storeAlls" name="storeAlls"></span>
		<input type="button" value="查询" class="btn btn-info fr" id="search_btn">
	<input type="hidden" id="hiddenStoreId" name="hiddenStoreId" value="">
	<input type="hidden" id="oldStoreIdStrs" name="oldStoreIdStrs" value="">
</div>
</form>
<div class="tabs-body">
	<h1>请查询当前负责人的数据....</h1>
</div>
<button data-dismiss="dialog" type="button" class="btn btn-danger"  onClick="javascript:parent.layer.closeAll('iframe');" style="margin-left:300px;margin-top:10;margin-bottom:10;">取消</button>
<button id="replaceButton" type="button" class="btn btn-success margin-left-18px" style="margin-top:10;margin-bottom:10;">更新负责人</button>
</div>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
</body>
</html>
<script>
//取消
$('.btn-danger').click(function(){
	parent.batchChoiceStoreDialog.close();
});

var flag = false;

$("#replaceButton").click(function(){ 
	var hiddenStore=$('#hiddenStoreId').val();
	var hiddenStoreId=hiddenStore;
	var oldStoreIdStrs=$('#oldStoreIdStrs').val();
	if(hiddenStoreId.substring(0,1) == ','){
		hiddenStoreId=hiddenStoreId.substring(1);
	}
	var clientUserId=$('#clientUserId').val();
	if(flag){
		layer.confirm("解除门店关系,将会解除未拜访计划。",function(){
			$.ajax({
				url : "${contextPath}/store/batchChoiceStoreUser",
				type : "post",
				dataType:"json",
				async: false,
				data : {hiddenStoreId:hiddenStoreId,oldStoreIdStrs:oldStoreIdStrs,clientUserId:clientUserId},
				success : function(result) {
				   if(result.code=="success"){
					   layer.alert(result.message,function(){
			   				window.parent.location.reload();
			   				parent.batchChoiceStoreDialog.close();
			   			});
					}else {
						layer.alert(result.message);
			   		}
				}
			});		 
		});
	}else{
		$.ajax({
			url : "${contextPath}/store/batchChoiceStoreUser",
			type : "post",
			dataType:"json",
			async: false,
			data : {hiddenStoreId:hiddenStoreId,oldStoreIdStrs:oldStoreIdStrs,clientUserId:clientUserId},
			success : function(result) {
			   if(result.code=="success"){
				   layer.alert(result.message,function(){
		   				window.parent.location.reload();
		   				parent.batchChoiceStoreDialog.close();
		   			});
				}else {
					layer.alert(result.message);
		   		}
			}
		});		 
	}
});


	

$("#clientUserId").change(function(){
 	$("#storeAlls").attr("checked",false);
});


$(document).ready(function () {
		$('.select2-choice').css("margin-top",5);
		$.fn.zTree.init($("#treeDemo"), setting);
        $("#clientUserId2").select2({
        	width:150,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear: true,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserPosition",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						page: page
					};
				},
				results: function (data,page) {
					return { results: data};
				}
			},
			initSelection: function(element, callback) {
				var id = $(element).val();
				if (id !== "") {
					$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
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
		//当前负责人
		  $("#clientUserId1").select2({
        	width:150,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear: true,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserPosition",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						page: page
					};
				},
				results: function (data,page) {
					return { results: data};
				}
			},
			initSelection: function(element, callback) {
				var id = $(element).val();
				if (id !== "") {
					$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
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
    });
    
//业务员
$("#clientUserId").select2({
	width:150,
	placeholder: "输入字符查询",
	minimumInputLength: 2,
	ajax: {
		url: "${contextPath}/clientUser/getClientUserPosition",
		dataType: 'json',
		quietMillis: 250,
		data: function (term, page) {
		return {
			q: term,
			page: page
		};
	},
	results: function (data,page) {
		return { results: data};
	}
},
initSelection: function(element, callback) {
	var id = $(element).val();
	if (id !== "") {
		$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
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
   
   

//门店嵌入到页面中
function showStroe(){
	if($("#clientUserId").val() ==''){
		layer.alert("请查询当前负责人的数据....");
		return false;
	}
 	
	$.post("${contextPath}/store/getBatchCheckStoreUser",$('#dataForm').serialize(),
			function(data){
				var oldStoreIdStrs = $("#oldStoreIdStrs").val();
				if(oldStoreIdStrs=="" && data!=""){
					$("#hiddenStoreId").val(data);
					$("#oldStoreIdStrs").val(data);
				}
			}
	);
 
	var structureId = $("#structureId").val();
	var clientUserId = $("#clientUserId").val();
	var storeNo = $("#storeNo").val();
	var storeName = $("#storeName").val();
	var isChecked ="";
	if($("#isChecked").attr('checked')){
		isChecked = $("#isChecked").val();
	}
	$.post("${contextPath}/store/addBatchChoiceStore",{clientStructureId:structureId,clientUserId:clientUserId,storeNo:storeNo,storeName:storeName,isChecked:isChecked},
			function(data){
				$(".tabs-body").html(data);
			}
	);
}

var clientUserId = '${clientUserId}';
if(clientUserId!=''){
	showStroe();
}

$('#search_btn').click(function(){	 
	showStroe();
});

$("#isChecked").click(function(){
	if($(this).attr('checked')){
		$("#storeAlls").attr("checked",false);
	}
});

$("#clientUserId").change(function(){
	$("#hiddenStoreId").val("");
	$("#oldStoreIdStrs").val("");
	$('#checkAll').attr("checked",false);
	$('#checkCount').html(0);
	$("[name = chkItem]:checkbox").each(function(){
		$(this).attr("checked",false);
	});
});


var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
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
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
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
		$("#storeAlls").attr("checked",false);	
		$("#structureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#structureId").val(), null);
		if(node != null){
			$("#clientStructureId").val(node.name);
		}
	};
    function showMenu() {
        var structureObj = $("#clientStructureId");
        var clientStructureOffset = $("#clientStructureId").position();
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
</script>
