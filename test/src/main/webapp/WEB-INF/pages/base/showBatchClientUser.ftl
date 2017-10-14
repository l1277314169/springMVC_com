<html>
<head>
<title></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body style="width:800px;height:600px;">
<div class="widget-content nopadding">
<form id="dataForm"  class="form-horizontal" action="#"  novalidate="novalidate">
	<div class="fl">
		<label class="control-label " for="structure">所属部门：</label>
		<div class="controls">
		 	<input id="clientStructureId" class="input-medium" name="structureSel"  type="text" readonly   onclick="showMenu();"/>
		 	<input type="text" id="structureId" name="clientStructureId" value="${clientStructureId}" style="display:none;">
		</div>
	</div>
	<div class="fl">
		<label class="control-label fl" for="storeName">门店名称：</label> 
		<div class="controls">
		  <input type="text" id="storeName" name="storeName" class="input-medium" >
		</div>
	</div>
	<div>
		<span class="control-label fl" for="clientUserId"><i style="color:red">*</i>当前负责人：</span>
		<span>
			<input type="text" id="clientUserId" name="clientUserId" >
		</span>
	</div>
<div class="form-actions">
		<span style="text-align: center;display: inline-flex;font-size: 12px">您已选择：<label id ="checkCount"  style="color:red;width: 50px;">0</label>家门店</span>
		<span style="margin-left: 50px;font-size: 12px">所有门店：<input type="checkbox" id="storeAlls" name="storeAlls"></span>
	<input type="button" value="查询" class="btn btn-info fr" id="search_btn">
	<input type="hidden" id="hiddenStoreId" name="hiddenStoreId">
</div>
</form>
<div class="tabs-body">
	<h1>请查询当前负责人的数据....</h1>
</div>
<button data-dismiss="dialog" type="button" class="btn btn-danger" onClick="javascript:parent.layer.closeAll('iframe');" style="margin-left:300px">取消</button>
<button id="replaceButton" type="button" class="btn btn-success margin-left-18px">更新负责人</button>&nbsp;<input id="clientUserId2" type="text" class="input-medium" name=" clientUserId2">
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
	parent.batchClientUserDialog.close();
});
var flag = false;

$("#replaceButton").click(function(){
	var clientUserId2=$('#clientUserId2').val();
	var hiddenStore=$('#hiddenStoreId').val();
	var hiddenStoreId=hiddenStore;
	if(hiddenStoreId.substring(0,1) == ','){
		hiddenStoreId=hiddenStoreId.substring(1);
	}
	var clientUserId=$('#clientUserId').val();
	if($("#clientUserId").val() ==''){
		layer.alert("请查询当前负责人的数据....");
		return false;
	}else if($('#hiddenStoreId').val() == ''){
		layer.alert('请选择门店!');
		return false;
	}else if($('#clientUserId2').val() == ''){
		layer.alert('请选择你要更新的负责人!');
		return false;
	}else if($('#clientUserId2').val() == $("#clientUserId").val()){
		layer.alert('更新的负责人不能为当前负责人!');
		return false;
	}else{
		if(flag){
			$.confirm("解除门店关系,将会解除未拜访计划。",function(){
				$.ajax({
					url : "${contextPath}/store/updateClientUser",
					type : "post",
					dataType:"json",
					async: false,
					data : {clientUserId2:clientUserId2,hiddenStoreId:hiddenStoreId,clientUserId:clientUserId},
					success : function(result) {
					   if(result.code=="success"){
						   layer.alert(result.message,function(){
				   				window.parent.location.reload();
				   				parent.batchClientUserDialog.close();
				   			});
						}else {
							layer.alert(result.message);
				   		}
					}
				});	
		   	});
		}else{
			$.ajax({
				url : "${contextPath}/store/updateClientUser",
				type : "post",
				dataType:"json",
				async: false,
				data : {clientUserId2:clientUserId2,hiddenStoreId:hiddenStoreId,clientUserId:clientUserId},
				success : function(result) {
				   if(result.code=="success"){
					   layer.alert(result.message,function(){
			   				window.parent.location.reload();
			   				parent.batchClientUserDialog.close();
			   			});
					}else {
						layer.alert(result.message);
			   		}
				}
			});	
		}
	}
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
   
//门店嵌入到页面中
function showStroe(){
	$.post("${contextPath}/store/addBatchClientUser",$('#dataForm').serialize(),
			function(data){
				$(".tabs-body").html(data);
			}
	);
}

$('#search_btn').click(function(){
	if($('#clientUserId').val() == ''){
		layer.alert("请查询当前负责人的数据....");
		return false;
	}
	showStroe();

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
