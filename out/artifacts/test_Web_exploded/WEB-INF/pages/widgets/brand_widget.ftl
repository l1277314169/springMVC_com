<!-- 品牌 -->
<input type="text" id="clientStructureId_brand" name="clientStructureName_brand"  class="input-medium" readonly onclick="showMenu_brand();">
<script type="text/javascript">
var setting_brand = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/brand/getTreeNode",
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
            beforeClick: beforeClick_brand,
            onClick: onClick_brand,
			onAsyncSuccess: zTreeOnAsyncSuccess_brand
        }
    };
        
    function beforeClick_brand(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_brand");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick_brand(e, treeId, treeNode) {
		$("#clientStructureId_brand").attr("value", treeNode.name);
		$("#${brandArg}").attr("value", treeNode.id);
		hideMenu_brand();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess_brand(event, treeId, treeNode, msg) {
		//alert(msg);
	    //var zTree = $.fn.zTree.getZTreeObj("treeDemo_brand");
		//zTree.expandAll(true);
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_brand");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#${brandArg}").val(), null);
		if(node != null){
			$("#clientStructureId_brand").val(node.name);
		}
	};
	
    function showMenu_brand() {
        var structureObj = $("#clientStructureId_brand");
        var clientStructureOffset = $("#clientStructureId_brand").position();
        $("#menuContent_brand").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown_brand);
    }
    function hideMenu_brand() {
        $("#menuContent_brand").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown_brand);
    }        
    function onBodyDown_brand(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_brand" || $(event.target).parents("#menuContent_brand").length > 0)) {
            hideMenu_brand();
        }
    }
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo_brand"), setting_brand, zNodes);
        $.fn.zTree.init($("#treeDemo_brand"), setting_brand);
        
    });
    
$(function(){
    $("#clientStructureId_brand").keydown(function(e){ 
    	if(e.keyCode == 8 || e.keyCode == 46){ 
    		$("#clientStructureId_brand").val(""); 
    		$("#searchclientStructureId_brand").val("");
    	}; 
    });
});


</script>

<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;z-index:999;">
	<ul id="treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>

