<!-- 店铺渠道 -->
<input type="text" id="clientStructureId_channel" name="clientStructureName_channel"  class="input-medium" readonly onclick="showMenu_channel();">
<input type="hidden" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" >

<script type="text/javascript">
var setting_channel = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
			error : function() {
				alert('亲，加载失败！');  
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
            beforeClick: beforeClick_channel,
            onClick: onClick_channel,
			onAsyncSuccess: zTreeOnAsyncSuccess_channel
        }
    };
        
    function beforeClick_channel(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_channel");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick_channel(e, treeId, treeNode) {
		$("#clientStructureId_channel").attr("value", treeNode.name);
		$("#${fls.arg}").attr("value", treeNode.id);
		hideMenu_channel();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess_channel(event, treeId, treeNode, msg) {
		//alert(msg);
	    //var zTree = $.fn.zTree.getZTreeObj("treeDemo_channel");
		//zTree.expandAll(true);
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_channel");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#${fls.arg}").val(), null);
		if(node != null){
			$("#clientStructureId_channel").val(node.name);
		}
	};
	
    function showMenu_channel() {
        var structureObj = $("#clientStructureId_channel");
        var clientStructureOffset = $("#clientStructureId_channel").position();
        $("#menuContent_channel").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown_channel);
    }
    function hideMenu_channel() {
        $("#menuContent_channel").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown_channel);
    }        
    function onBodyDown_channel(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_channel" || $(event.target).parents("#menuContent_channel").length > 0)) {
            hideMenu_channel();
        }
    }
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo_channel"), setting_channel, zNodes);
        $.fn.zTree.init($("#treeDemo_channel"), setting_channel);
        
    });
    
$(function(){
    $("#clientStructureId_channel").keydown(function(e){ 
    	if(e.keyCode == 8 || e.keyCode == 46){ 
    		$("#clientStructureId_channel").val(""); 
    		$("#searchclientStructureId_channel").val("");
    	}; 
    });
});


</script>

<div id="menuContent_channel" class="menuContent" style="display: none; position: absolute;z-index:999;">
	<ul id="treeDemo_channel" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>

