<html>
<head>
<title></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
</head>
<body style="width:800px;height:600px;">
<div class="widget-content nopadding">
<form id="dataForm"  class="form-horizontal" action="#"  novalidate="novalidate">
		<input type="hidden" id="clientUserId1" name="clientUserId1" value="${clientUserIds}">
		<input type="hidden" id="clientId" name="clientId" value="${clientId}">
					<div class="fl">
						<label class="control-label " for="structure">所属部门：</label>
						<div class="controls">
						 	<input id="clientStructureId" class="input-medium" name="structureSel"  type="text" readonly   onclick="showMenu();"/>
						 	<input type="text" id="structureId" name="clientStructureId" value="${clientStructureId}" style="display:none;">
						</div>
					</div>
			         <div class="fl">
						<label class="control-label fl" for="storeName">姓名：</label> 
						<div class="controls">
						  <input type="text" id="name" name="name" class="input-medium" >
						</div>
					</div>
					<div style="height:30px;margin-top:3px;">
						<span class="control-label fl" for="clientUserId" style="margin-top:-3px">当前上级：</span>
						<span>
							<input type="text" id="clientUserId" name="clientUserId" >
						</span>
					</div>
					<div class="f2">
						<label class="control-label f2" for="status"><nobr>仅显示在职人员：</nobr></label> 
						<div class="controls">
						  <input type="checkbox" id="status" name="status" value="1" class="input-medium" >
						</div>
					</div>
					<div class="form-actions">
							<span style="text-align: center;display: inline-flex;font-size: 12px">您已选择：<label id ="checkCount"  style="color:red;width: 50px;">0</label>个人员</span>
							<span style="margin-left: 50px;font-size: 12px">所有人员：<input type="checkbox" id="clientUserCheckBox" name="clientUserCheckBox"></span>
						<input type="button" value="查询" class="btn btn-info fr" id="search_btn">
						<input type="hidden" id="hiddenClientUserId" name="hiddenClientUserId">
					</div>
</form>
	<div class="tabs-body">
		<h1>请查询....</h1>
	</div>
	<button data-dismiss="dialog" type="button" class="btn btn-danger" style="margin-left:300px">取消</button>
	<button id="savetButton" type="button" class="btn btn-success margin-left-18px">更新上级</button>&nbsp;
	<input id="clientUserId2" type="text" class="input-medium" name=" clientUserId2">
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
//保存
$("#savetButton").bind("click",function(){
	var clientUserId2=$('#clientUserId2').val();
	var hiddenClientUserId=$('#hiddenClientUserId').val();
	var hiddenClientUserIds=hiddenClientUserId;
	if(hiddenClientUserIds.substring(0,1) == ','){
		hiddenClientUserIds=hiddenClientUserId.substring(1);
	}
	var clientUserId=$('#clientUserId').val();
	var clientUserId2=$('#clientUserId2').val();
	if(hiddenClientUserIds == ''){
		layer.alert('请选择人员!');
		return false;
	}else if(clientUserId2 == ''){
		layer.alert('请选择你要更新的上级人员!');
		return false;
	}else if(hiddenClientUserIds.indexOf(clientUserId2) >= 0){
		$.confirm("人员上级不能为选中的用户。确定更新,该用户的上级不变。",function(){
			var array = hiddenClientUserIds.split(",");
			array.splice($.inArray(clientUserId2,array),1);
			hiddenClientUserIds = array.join(",");
			showCriteria(clientUserId,hiddenClientUserIds,clientUserId2);
	   	});
		return false;
	}else{
		showCriteria(clientUserId,hiddenClientUserIds,clientUserId2);
	}
});
function showCriteria(clientUserId,hiddenClientUserIds,clientUserId2){
	$.ajax({
		url : "${contextPath}/clientUser/updateParentClientUser",
		type : "post",
		dataType:"json",
		async: false,
		data : {clientUserId : clientUserId,hiddenClientUserIds : hiddenClientUserIds,clientUserId2:clientUserId2},
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
$(document).ready(function () {
	$.fn.zTree.init($("#treeDemo"), setting);
	//当前上级
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
	
	
	//人员嵌入到页面中
	function showclientUser(){
		$.post("${contextPath}/promotions/replaceUpClientUser",$('#dataForm').serialize(),
				function(data){
					$(".tabs-body").html(data);
				}
		);
	}
	//查询
	$('#search_btn').click(function(){
		showclientUser();
	});
});

//更新上级
	$("#clientUserId2").select2({
		width:145,
		placeholder: "输入字符查询",
		minimumInputLength: 2,
		allowClear : true,
		ajax: {
			url: "${contextPath}/clientUser/getClientUserWithoutSelf",
			dataType: 'json',
			quietMillis: 250,
			data: function (term, page) {
				return {
					q: term,
					page: page,
				};
			},
			results: function (data,page) {
				var more = page;
				return { results: data,more: more };
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
