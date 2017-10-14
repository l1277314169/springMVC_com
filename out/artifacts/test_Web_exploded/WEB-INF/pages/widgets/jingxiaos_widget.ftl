<script type="text/javascript">
var setting_distributor = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/distributor/getTreeNode",
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
            beforeClick: beforeClick_distributor,
            onClick: onClick_distributor,
			onAsyncSuccess: zTreeOnAsyncSuccess_distributor
        }
    };
        
    function beforeClick_distributor(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_distributor");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick_distributor(e, treeId, treeNode) {
		$("#clientStructureId_distributor").attr("value", treeNode.name);
		$("#${distributorFTL}").attr("value", treeNode.id);
		hideMenu_distributor();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess_distributor(event, treeId, treeNode, msg) {
		//alert(msg);
	    //var zTree = $.fn.zTree.getZTreeObj("treeDemo_distributor");
		//zTree.expandAll(true);
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_distributor");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#${distributorFTL}").val(), null);
		if(node != null){
			$("#clientStructureId_distributor").val(node.name);
		}
	};
	
    function showMenu_distributor() {
        var structureObj = $("#clientStructureId_distributor");
        var clientStructureOffset = $("#clientStructureId_distributor").position();
        $("#menuContent_distributor").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown_distributor);
    }
    function hideMenu_distributor() {
        $("#menuContent_distributor").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown_distributor);
    }        
    function onBodyDown_distributor(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_distributor" || $(event.target).parents("#menuContent_distributor").length > 0)) {
            hideMenu_distributor();
        }
    }
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo_distributor"), setting_distributor, zNodes);
        $.fn.zTree.init($("#treeDemo_distributor"), setting_distributor);
        
    });
    
$(function(){
    $("#clientStructureId_distributor").keydown(function(e){ 
    	if(e.keyCode == 8 || e.keyCode == 46){ 
    		$("#clientStructureId_distributor").val(""); 
    		$("#searchclientStructureId_distributor").val("");
    	}; 
    });
});


</script>

<div id="menuContent_distributor" class="menuContent" style="display: none; position: absolute;z-index:999;">
	<ul id="treeDemo_distributor" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>

