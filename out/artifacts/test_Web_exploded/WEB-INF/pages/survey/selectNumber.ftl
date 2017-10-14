<div id="menuContent_number_${objectId}" class="menuContent_number_${objectId}" style="display: none; position: absolute;"> 
<ul id="treeDemo_number_${objectId}" class="ztree" style="margin-top: 0; width: 160px;"> 
</ul> 
</div>
<script type="text/javascript"> 
var setting_number_${objectId} = { 
check: { 
	enable: true, 
	chkStyle: "checkbox", 
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
		beforeClick: beforeClick_number, 
		onClick: onClick_number, 
		onCheck: zTreeOnCheck, 
	} 
}; 
function beforeClick_number(treeId, treeNode) { 
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_number_${objectId}"); 
	zTree.checkNode(treeNode,!treeNode.checked,null,true); 
	return true; 
} 
function onClick_number(e, treeId, treeNode) { 
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_number_${objectId}"); 
	var nodes = zTree.getCheckedNodes(true); 
	var numberId = []; 
	for(var i=0,l=nodes.length;i<l;i++){ 
	numberId.push(nodes[i].id); 
	} 
	$("#${objectId}").attr("value", numberId.join(",")); 
} 

function zTreeOnCheck(e, treeId, treeNode) { 
	var zTree = $.fn.zTree.getZTreeObj("treeDemo_number_${objectId}"); 
	var nodes = zTree.getCheckedNodes(true); 
	var numberId = []; 
	for(var i=0,l=nodes.length;i<l;i++){ 
		numberId.push(nodes[i].id); 
	} 
	$("#${objectId}").attr("value", numberId.join(",")); 
} 

function showMenu_number_${objectId}() { 
	var numberId = $("#${objectId}"); 
	var numberOffset = $("#${objectId}").position(); 
	$("#menuContent_number_${objectId}").css({ left: numberOffset.left + "px", top: numberOffset.top + numberId.outerHeight() + "px" }).slideDown("fast"); 
		$("body").bind("mousedown", onBodyDown_number_${objectId}); 
	
	} 
	function hideMenu_number_${objectId}() { 
		$("#menuContent_number_${objectId}").fadeOut("fast"); 
		$("body").unbind("mousedown", onBodyDown_number_${objectId}); 
	} 
	function onBodyDown_number_${objectId}(event) { 
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_number_${objectId}" || $(event.target).parents("#menuContent_number_${objectId}").length > 0)) { 
			hideMenu_number_${objectId}(); 
		} 
	}	


function Digital(number){ 
	if(number!=""){
		var numberJson = []; 
		for(var i=0;i<=number;i++){ 
		var num = { id:i,name:""+i, open:true}; 
		numberJson.push(num); 
		} 
		return numberJson; 	
	}
}; 

$(document).ready(function () { 
	var clientId = "${clientId}";
	$("#${objectId}").bind('click',function(){ 
		var prevId;
		if(clientId == "24"){
			prevId = "${objectId}".replace("178","177"); 	
		}else{
			prevId = "${objectId}".replace("162","161"); 
		}
		var number = $("#"+prevId).val();
		if(clientId == "24"){
			if(number>20){
				number = 20;
			}
		}else{
			if(number>12){
				number = 12;
			}
		}
		var JsonNum = Digital(number); 
		$.fn.zTree.init($("#treeDemo_number_${objectId}"),setting_number_${objectId},JsonNum); 
	}); 

	$("#${objectId}").on("click",function(){ 
		showMenu_number_${objectId}(); 
	}); 

}); 
</script>