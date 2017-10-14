<!-- 组织架构 -->
<input type="text" id="clientStructureId_structure" name="clientStructureName_structure" class="input-medium" readonly onclick="showMenu_structure()">
<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" style="display:none;">
<script type="text/javascript">
var setting_structure = {
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
        beforeClick: beforeClick_structure,
        onClick: onClick_structure,
		onAsyncSuccess: zTreeOnAsyncSuccess_structure
    }
};
    
function beforeClick_structure(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
    //判断当前节点是否选中 如果选中则取消选中，反之选中
    if (zTree.isSelectedNode(treeNode)) {
        zTree.cancelSelectedNode(treeNode);
    } else {
        zTree.selectNode(treeNode);
    }
    return true;
}
function onClick_structure(e, treeId, treeNode) {
	$("#clientStructureId_structure").attr("value", treeNode.name);
	$("#${fls.arg}").attr("value", treeNode.id);
	hideMenu_structure();
    return false;
}

//异步加载成功后
function zTreeOnAsyncSuccess_structure(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
	zTree.expandAll(true);
	var node = zTree.getNodeByParam("id", $("#${fls.arg}").val(), null);
	if(node != null){
		$("#clientStructureId_structure").val(node.name);
	}
};
function showMenu_structure() {
    var structureObj = $("#clientStructureId_structure");
    var clientStructureOffset = $("#clientStructureId_structure").position();
    $("#menuContent_structure").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
    $("body").bind("mousedown", onBodyDown_structure);
    
}
function hideMenu_structure() {
    $("#menuContent_structure").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown_structure);
}        
function onBodyDown_structure(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_structure" || $(event.target).parents("#menuContent_structure").length > 0)) {
        hideMenu_structure();
    }
}	

$(document).ready(function () {
    $.fn.zTree.init($("#treeDemo_structure"), setting_structure);
});
</script>

<div id="menuContent_structure" class="menuContent" style="display: none; position: absolute;z-index: 99;">
	<ul id="treeDemo_structure" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>