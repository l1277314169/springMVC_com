<!-- 品类 -->
<input type="text" id="clientStructureId_category" name="clientStructureName_category"  class="input-medium" readonly onclick="showMenu_category();">
<input type="hidden" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" >

<script type="text/javascript">
var setting_category = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/category/getTreeNode",
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
            beforeClick: beforeClick_category,
            onClick: onClick_category,
			onAsyncSuccess: zTreeOnAsyncSuccess_category
        }
    };
        
    function beforeClick_category(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_category");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick_category(e, treeId, treeNode) {
		$("#clientStructureId_category").attr("value", treeNode.name);
		$("#${fls.arg}").attr("value", treeNode.id);
		hideMenu_category();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess_category(event, treeId, treeNode, msg) {
		//alert(msg);
	    //var zTree = $.fn.zTree.getZTreeObj("treeDemo_category");
		//zTree.expandAll(true);
		
		var zTree = $.fn.zTree.getZTreeObj("treeDemo_category");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#${fls.arg}").val(), null);
		if(node != null){
			$("#clientStructureId_category").val(node.name);
		}
	};
	
    function showMenu_category() {
        var structureObj = $("#clientStructureId_category");
        var clientStructureOffset = $("#clientStructureId_category").position();
        $("#menuContent_category").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown_category);
    }
    function hideMenu_category() {
        $("#menuContent_category").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown_category);
    }        
    function onBodyDown_category(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_category" || $(event.target).parents("#menuContent_category").length > 0)) {
            hideMenu_category();
        }
    }
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo_category"), setting_category, zNodes);
        $.fn.zTree.init($("#treeDemo_category"), setting_category);
        
    });
    
$(function(){
    $("#clientStructureId_category").keydown(function(e){ 
    	if(e.keyCode == 8 || e.keyCode == 46){ 
    		$("#clientStructureId_category").val(""); 
    		$("#searchclientStructureId_category").val("");
    	}; 
    });
});


</script>

<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;z-index:999;">
	<ul id="treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>

