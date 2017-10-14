<html>
<head>
<#include "/common/head.ftl" />
<title>关联部门领导</title>
<#include "/common/foot.ftl" />
<script type="text/javascript">
	var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNode",
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
        //var check = (treeNode && !treeNode.isParent);
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
    	//alert(treeNode.name);
        //var zTree = $.fn.zTree.getZTreeObj("treeDemo");            
        //var nodes = zTree.getSelectedNodes(),v = "";
        //nodes.sort(function compare(a, b) { return a.id - b.id; });
        //for (var i = 0, l = nodes.length; i < l; i++) {
        //    v += nodes[i].name + ",";
        //}
        //if (v.length > 0) v = v.substring(0, v.length - 1);
        //var structureObj = $("#structureSel");
		//structureObj.attr("value", v);
		$("#structureSel").attr("value", treeNode.name);
		$("#clientStructureId").attr("value", treeNode.id);
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
	};
	
    function showMenu() {
        var structureObj = $("#structureSel");
        var structureOffset = $("#structureSel").offset();
        $("#menuContent").css({ left: structureOffset.left + "px", top: structureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
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
    $(document).ready(function () {
        //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo"), setting);
        
    });
</script>
</head>
<body>
		<div>
			<form class="form-search" action="#" id="searchForm">
				<div style="height:40px;">
					<span class="pop_search_item">姓名：<input type="text" name="name" class="input-medium" value="${clientUser.name!''}"></span> 
					<span class="pop_search_item">部门：<input id="structureSel" name="structureName" type="text" readonly value="${clientUser.structureName!''}" style="width: 140px;" onclick="showMenu();"/></span>
					<span class="pop_search_item"><button type="submit" class="btn_search" id="search_btn">查询</button></span> 
				</div>
				<input type="hidden" id="clientStructureId" name="clientStructureId" value="">
				<input type="hidden" name="page" value="${page}">
			</form>
			<div class="pop_fun_button">
				<span class="pop_search_item">已选领导：</span><span id="popSelectLeaderNames"></span>
			</div>
			<div class="poo_div_list_show">
				<table class="table table-bordered c_list" id="c_list">
					<tr>
					<th width="30px">选择</th>
					<th>姓名</th>
					<th>工号</th>
					<th>部门</th>
					<th>职位</th>
					<th>性别</th>
				    <th>人员状态</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientUser>
							<tr>
								<td style="text-align: center"><input name="chkItem" type="checkbox" class="checkboxUsers" value="${clientUser.clientUserId!''}" data="${clientUser.name!''}"/></td>
								<td>${clientUser.name!''}</td>
								<td>${clientUser.userNo!''}</td>
								<td>${clientUser.structureName!''}</td>
								<td>${clientUser.positionName!''}</td>
								<td><#if clientUser.sex =1>男<#elseif clientUser.sex =2>女</#if></td>
								<td><#if clientUser.status =1>在职<#elseif clientUser.status =0>离职</#if></td>
							</tr>
						</#list>
						<tfoot>
						<tr>
							<td style="background-color:#EEE;" colspan="13">
								<#if pageParam.items?exists> 
									<div class="paging" > 
									   ${pageParam.getPagination()}
									</div> 
								</#if> 
							</td>
						</tr>
					</tfoot>
					</tbody>
				</table>
			</div>
		</div>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
</body>
<script type="text/javascript" language="javascript">
	$(document).ready(function () {
		//初始化
		var objId = $(window.parent.document).find("#selectLeaderIds");
		var userIds = objId.val();
		var arrayId = userIds.split(',');
		
		var objName = $(window.parent.document).find("#selectLeaderNames");
		var userNames = objName.val();
		var arrayName = userNames.split(',');
		
		//alert(userNames);
		var selectNames = "";
		for (var index in arrayName) {
			var name = arrayName[index];
			alert(name);
			if(name !="") {
				selectNames += "<span class='tab_cur_num_label'>"+arrayName[index]+"<img class='tab_close' width='20px' alt='删除' src='${contextPath}/images/tab_close.png'></span>";
			}
        }
		$("#popSelectLeaderNames").html(selectNames);
		
        $("[name = chkItem]:checkbox").each(function () {
        	for (var index in arrayId) {
                if ($(this).val() == arrayId[index]) {
                    $(this).attr("checked", "checked");
                    break;
                }
            }
     	});
		
		$(".checkboxUsers").click(function(){
			userIds = objId.val();
			userNames = objName.val();
			if($(this).prop("checked")){
				userIds += $(this).val()+",";
				userNames += $(this).attr("data")+",";
				objId.attr("value",userIds);
				objName.attr("value",userNames);
				
				arrayId = userIds.split(',');
				arrayName = userNames.split(',');
				var selectNames_tp = "";
				for (var index in arrayName) {
					var name = arrayName[index];
					if(name !="") {
						selectNames_tp += "<span class='tab_cur_num_label'>"+arrayName[index]+"<img class='tab_close' width='20px' alt='删除' src='${contextPath}/images/tab_close.png'></span>";
					}
	        	}
				$("#popSelectLeaderNames").html(selectNames_tp);
			}
			if(!$(this).prop("checked")){
				var userId = $(this).val();
				var name = $(this).attr("data");
				
				userIds = objId.val();
				arrayId = userIds.split(',');
				arrayId.splice($.inArray(userId,arrayId),1);
				userIds = arrayId.join(",");
				objId.attr("value",userIds);
				
				userNames = objName.val();
				arrayName = userNames.split(',');
				arrayName.splice($.inArray(name,arrayName),1);
				userNames = arrayName.join(",");
				objName.attr("value",userNames);
				var selectNames_tp = "";
				for (var index in arrayName) {
					var name = arrayName[index];
					if(name !="") {
						selectNames_tp += "<span class='tab_cur_num_label'>"+arrayName[index]+"<img class='tab_close' width='20px' alt='删除' src='${contextPath}/images/tab_close.png'></span>";
					}
	        	}
				$("#popSelectLeaderNames").html(selectNames_tp);
			}
		});
	});
</script>
</html>