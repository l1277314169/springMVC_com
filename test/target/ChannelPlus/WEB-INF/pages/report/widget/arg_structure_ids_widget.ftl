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
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType:{ "Y" : "s", "N" : "ps" }
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
        onCheck: zTreeOnCheck,
		onAsyncSuccess: zTreeOnAsyncSuccess_structure
    }
};
    
function beforeClick_structure(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
    zTree.checkNode(treeNode,!treeNode.checked,null,true);
    return true;
}
function onClick_structure(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
	var nodes = zTree.getCheckedNodes(true);
	var structureName = "";
	var structureId = [];
	for(var i=0,l=nodes.length;i<l;i++){
		structureName+=nodes[i].name+",";
		structureId.push(nodes[i].id);
	}
	if(structureName.length > 0){
		structureName = structureName.substring(0,structureName.length-1);
	}
	$("#clientStructureId_structure").attr("value", structureName);
	$("#${fls.arg}").attr("value", structureId.join(","));
}

function zTreeOnCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
	var nodes = zTree.getCheckedNodes(true);
	var structureName = "";
	var structureId = [];
	for(var i=0,l=nodes.length;i<l;i++){
		structureName+=nodes[i].name+",";
		structureId.push(nodes[i].id);
	}
	if(structureName.length > 0){
		structureName = structureName.substring(0,structureName.length-1);
	}
	$("#clientStructureId_structure").attr("value", structureName);
	$("#${fls.arg}").attr("value", structureId.join(","));
}

//异步加载成功后
function zTreeOnAsyncSuccess_structure(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo_structure");
	var ids = $("#${fls.arg}").val();
	var structureName = "";
	var arrayId;
	if(ids != '' && ids != undefined){
		arrayId = ids.split(',');
	}
	for(var i = 0;i < arrayId.length;i++){
		var node = zTree.getNodeByParam("id", arrayId[i], null);
		structureName+=node.name+",";
		zTree.checkNode(node, true, false);
	}
	if(structureName.length > 0){
		structureName = structureName.substring(0,structureName.length-1);
	}
	$("#clientStructureId_structure").attr("value", structureName);
	zTree.expandAll(true);
	
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