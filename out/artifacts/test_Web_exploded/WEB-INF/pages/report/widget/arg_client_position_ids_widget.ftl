<!-- 职位多选 -->
<input type="text" id="clientPositionName" name="clientPositionName" class="input-medium" readonly onclick="showMenu_position()">
<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" style="display:none;">
<script type="text/javascript">
var setting_position = {
	async : {
		enable : true,
		type: "get",
		url : "${contextPath}/clientPosition/getClientPositionZtree",
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
        beforeClick: beforeClick_position,
        onClick: onClick_position,
        onCheck: zTreeOnCheck,
		onAsyncSuccess: zTreeOnAsyncSuccess_position
    }
};
    
function beforeClick_position(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo_position");
    zTree.checkNode(treeNode,!treeNode.checked,null,true);
    return true;
}
function onClick_position(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_position");
	var nodes = zTree.getCheckedNodes(true);
	var positionName = "";
	var positionId = [];
	for(var i=0,l=nodes.length;i<l;i++){
		positionName+=nodes[i].name+",";
		positionId.push(nodes[i].id);
	}
	if(positionName.length > 0){
		positionName = positionName.substring(0,positionName.length-1);
	}
	$("#clientPositionName").attr("value", positionName);
	$("#${fls.arg}").attr("value", positionId.join(","));
}

function zTreeOnCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_position");
	var nodes = zTree.getCheckedNodes(true);
	var positionName = "";
	var positionId = [];
	for(var i=0,l=nodes.length;i<l;i++){
		positionName+=nodes[i].name+",";
		positionId.push(nodes[i].id);
	}
	if(positionName.length > 0){
		positionName = positionName.substring(0,positionName.length-1);
	}
	$("#clientPositionName").attr("value", positionName);
	$("#${fls.arg}").attr("value", positionId.join(","));
}

//异步加载成功后
function zTreeOnAsyncSuccess_position(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo_position");
	zTree.expandAll(true);
};
function showMenu_position() {
    var positionObj = $("#clientPositionName");
    var clientPositionOffset = $("#clientPositionName").position();
    $("#menuContent_position").css({ left: clientPositionOffset.left + "px", top: clientPositionOffset.top + positionObj.outerHeight() + "px" }).slideDown("fast");
    $("body").bind("mousedown", onBodyDown_position);
    
}
function hideMenu_position() {
    $("#menuContent_position").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown_position);
}        
function onBodyDown_position(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_position" || $(event.target).parents("#menuContent_position").length > 0)) {
        hideMenu_position();
    }
}	

$(document).ready(function () {
    $.fn.zTree.init($("#treeDemo_position"), setting_position);
});
</script>

<div id="menuContent_position" class="menuContent" style="display: none; position: absolute;z-index: 99;">
	<ul id="treeDemo_position" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>