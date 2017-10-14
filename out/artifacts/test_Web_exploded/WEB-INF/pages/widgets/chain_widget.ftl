<script type="text/javascript">
var setting_chain = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/chain/getTreeNode",
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
            beforeClick: beforeClick_chain,
            onClick: onClick_chain,
			onAsyncSuccess: zTreeOnAsyncSuccess_chain
        }
    };
        
    function beforeClick_chain(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_chain");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick_chain(e, treeId, treeNode) {
		$("#clientStructureId_chain").attr("value", treeNode.name);
		$("#${chainFTL}").attr("value", treeNode.id);
		hideMenu_chain();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess_chain(event, treeId, treeNode, msg) {
		//alert(msg);
	    //var zTree = $.fn.zTree.getZTreeObj("treeDemo_chain");
		//zTree.expandAll(true);
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_chain");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#${chainFTL}").val(), null);
		if(node != null){
			$("#clientStructureId_chain").val(node.name);
		}
	};
	
    function showMenu_chain() {
        var structureObj = $("#clientStructureId_chain");
        var clientStructureOffset = $("#clientStructureId_chain").position();
        $("#menuContent_chain").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown_chain);
    }
    function hideMenu_chain() {
        $("#menuContent_chain").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown_chain);
    }        
    function onBodyDown_chain(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_chain" || $(event.target).parents("#menuContent_chain").length > 0)) {
            hideMenu_chain();
        }
    }
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo_chain"), setting_chain, zNodes);
        $.fn.zTree.init($("#treeDemo_chain"), setting_chain);
        
    });
    
$(function(){
    $("#clientStructureId_chain").keydown(function(e){ 
    	if(e.keyCode == 8 || e.keyCode == 46){ 
    		$("#clientStructureId_chain").val(""); 
    		$("#searchclientStructureId_chain").val("");
    	}; 
    });
});


</script>

<div id="menuContent_chain" class="menuContent" style="display: none; position: absolute;z-index:999;">
	<ul id="treeDemo_chain" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>

