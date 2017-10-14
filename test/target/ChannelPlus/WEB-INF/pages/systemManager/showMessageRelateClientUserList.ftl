<html>
<head>
<#include "/common/head.ftl" />
<title>信息关联人员页面</title>
<#include "/common/foot.ftl" />
</head>
<body>
	<div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientStructureId">所属部门：</label>
						<div class="controls">
							<input id="clientStructureId" class="input-medium" name="structureSel" value="${structureSel}" type="text" readonly   onclick="showMenu();"/>
							<input type="text" id="structureId" name="structureId" value="${searchClientStructureId}" style="display:none;">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientPositionId">职位：</label>
						<div class="controls">
							<select id="clientPositionId" class="input-medium" name="clientPositionId"   display: none; value="${clientPositionId!''}">
							    <option value="">--请选择--</option>
	                    		<#list cpList as option>
	                    		   <#if clientPositionId == option.clientPositionId>
	                    				<option value="${option.clientPositionId!''}" selected="selected">${option.positionName!''}</option>
	                    			<#else>
	                    				<option value="${option.clientPositionId!''}" >${option.positionName!''}</option>
	                    			</#if>
	                    		</#list>
							</select>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="searchName">姓名：</label>
						<div class="controls">
							<input type="text" class="input-medium" name="searchName" value="${searchName!''}">
						</div>
					</div>
				</div>
				<div>
					<div class="form-actions">
							<span style="text-align: center;display: inline-flex;">您已选择：<label id ="checkCount" style="color:red;width:50px;">0</label>个人员</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所有人员：
							<input type="checkbox" id="checkAlls" name="checkAlls" style="margin-top:-2px">
							<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					<div>
				</div>
				<input type="hidden" id="searchClientStructureId" name="clientStructureId" value="${searchClientStructureId}">
				<input type="hidden" name="structureName" value="${structureName}">
				<input type="hidden" name="page" value="${page}">
				<input type="hidden" id="count" value="${count}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th><div><input type="checkbox" id="checkAll"></div></th>
						<th>姓名</th>
						<th>用户名</th>
						<th>部门</th>
						<th>职位</th>
						<th>电话</th>
					    <th>在职状态</th>
					    <th>账号状态</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientUser>
							<tr>
								<td><input type="checkbox" id="checkboxId" class="checkboxMobileVersion" value="${clientUser.clientUserId}"></td>
								<td>${clientUser.name!''}</td>
								<td>${clientUser.loginName!''}</td>
								<td>${clientUser.structureName!''}</td>
								<td>${clientUser.positionName!''}</td>
								<td>${clientUser.mobileNo}&nbsp;</td>
								<td><#if clientUser.status = 1>在职<#elseif clientUser.status = 0>离职</#if></td>
								<td><#if clientUser.isActivation = 1>启用<#elseif clientUser.isActivation = 0>禁用<#elseif clientUser.isActivation = 2>无法正常使用APP</#if></td>
							</tr>
						</#list>
					</tbody>
				</table>
					<#if pageParam.items?exists>
							<div class="paging" >
							   ${pageParam.getPagination()}
							</div>
					</#if>
			</div>
		</div>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div class="td_buttons" style="text-align: center;">
		<button type="button" id="repalceButton" class="btn btn-danger margin-left-18px">关闭</button>
	</div>
	<input type="hidden" id="oldClientUserIds" value="${strReplaceAlls}">
</body>
</html>
<script type="text/javascript">
var pobj =  $(window.parent.document).find("#checkboxId");
var storeIds = pobj.val();
if(storeIds == ","){
 storeIds = '';
}
var array = storeIds.split(",");
$("#checkCount").html(array.length-1);
 $(".checkboxMobileVersion:checkbox").each(function () {
    	for (var index in array) {
            if ($(this).val() == array[index]){
                $(this).attr("checked", "checked");
                break;
            }
        }
 	});

$(".checkboxMobileVersion").click(function(){
	storeIds=pobj.val();
	if(storeIds == ","){
	 	storeIds = '';
	}
	if($('.checkboxMobileVersion:checked').length == $('.checkboxMobileVersion').length){
		$('#checkAll').attr("checked",true);
	}else{
		$('#checkAll').attr("checked",false);
	}
	if($(this).prop("checked")){
		storeIds+=$(this).val()+",";
		pobj.val(storeIds);
		array = storeIds.split(',');
		$("#checkCount").html(array.length-1);
		$(window.parent.document).find("#clientUserCheckbox").html($('#checkCount').html());
		//如果全选中 所有人员就选中
		if($('#checkCount').html() == $('#count').val()){
			$('#checkAlls').attr("checked",true);
			$('#checkAlls').removeAttr("disabled");
		}
	}else{
		var shows= pobj.val();
		array=shows.split(',');
		array.splice($.inArray($(this).val(),array),1);
		pobj.attr("value",array.join(","));
		$("#checkCount").html(array.length-1);
		$(window.parent.document).find("#clientUserCheckbox").html($('#checkCount').html());
		$('#checkAlls').attr("checked",false);
	}
});



$(document).ready(function(){
	$('#repalceButton').on("click",function(){
		parent.replaceDialog.close();
	});
	
	$('#checkCount').html($(window.parent.document).find("#clientUserCheckbox").html());
	//树-input选项,判断用户的按键行为,如果为"Backspace"或者"delete"按键，则清空选择项。
	$("#clientStructureId").keydown(function(e){
		if(e.keyCode == 8 || e.keyCode == 46){
			$("#clientStructureId").val("");
			$("#searchClientStructureId").val("");
		};
	});
	if($('.checkboxMobileVersion:checked').length == $('.checkboxMobileVersion').length){
		$('#checkAll').attr("checked",true);
	}else{
		$('#checkAll').attr("checked",false);
	}
	
		if($('#checkAll').prop("checked")){
			var oldClientUserIds = $('#oldClientUserIds').val();
			var checkboxId = $(window.parent.document).find("#checkboxId").val();
			if(checkboxId.indexOf(oldClientUserIds) >= 0){
				$('#checkAlls').attr("checked",true);
			}
		}else{
			$('#checkAlls').attr("checked",false);
		}
	
	//全选
	$('#checkAll').click(function(){
		if($(this).prop("checked")){
			storeIds=pobj.val();
			if(storeIds == ","){
			 storeIds = '';
			}
			$('.checkboxMobileVersion').each(function(){
				if(!$(this).prop("checked")){
					storeIds+=$(this).val()+",";
					pobj.val(storeIds);
					array = storeIds.split(',');
					$("#checkCount").html(array.length-1);
					$(this).attr("checked",true);
				};	
			})
			$(window.parent.document).find("#clientUserCheckbox").html($('#checkCount').html());
			//如果全选中 所有人员就选中
			if($('#checkCount').html() == $('#count').val()){
				$('#checkAlls').attr("checked",true);
			}
		}else{
			$('.checkboxMobileVersion').each(function(){
				if($(this).prop("checked")){
					var show= pobj.val();
					array=show.split(',');
					array.splice($.inArray($(this).val(),array),1);
					pobj.attr("value",array.join(","));
					$("#checkCount").html(array.length-1);
					$(this).attr("checked",false);
				}
			})
			$(window.parent.document).find("#clientUserCheckbox").html($('#checkCount').html());
			$('#checkAlls').attr("checked",false);
		}
	});
});
	
var setting = {
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
            beforeClick: beforeClick,
            onClick: onClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
        }
    };
        
    function beforeClick(treeId, treeNode) {
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
		$("#clientStructureId").attr("value", treeNode.name);
		$("#searchClientStructureId").attr("value", treeNode.id);
		$("#structureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#structureId").val(), null);
		if(node != null){
			$("#clientStructureId").val(node.name);
		}
	};
    function showMenu() {
        var structureObj = $("#clientStructureId");
        var clientStructureOffset = $("#clientStructureId").position();
        $("#menuContent").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
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
        $.fn.zTree.init($("#treeDemo"), setting);
        $('#clientPositionId').select2();
    });
//所有人员
$('#checkAlls').click(function(){
		if($(this).prop("checked")){
			//父页面的保存clientUserId全部清空
			$('.checkboxMobileVersion').attr("checked",true);
			$('#checkAll').attr("checked",true);
			//关联所有人员
			$.ajax({
				url : "${contextPath}/message/relevanceAllsClientUser",
				type : "post",
				dataType:"json",
				async: false,
				data : $("#searchForm").serialize(),
				success : function(result) {
					var newResult = compareArray(result);
					var clientUserIds = $(window.parent.document).find("#checkboxId").val()+newResult;
					$(window.parent.document).find("#checkboxId").val(clientUserIds);
					var checkboxId = $(window.parent.document).find("#checkboxId").val().split(",");
					$(window.parent.document).find("#clientUserCheckbox").html(checkboxId.length-1);
					$('#checkCount').html(checkboxId.length-1);
					return false;
				}
			});
		}else{
			var oldClientUserIds = $('#oldClientUserIds').val().split(",");
			var array = $(window.parent.document).find("#checkboxId").val().split(",");
			for(var i in oldClientUserIds){
				array.splice($.inArray(oldClientUserIds[i],array),1);
			}
			$(window.parent.document).find("#clientUserCheckbox").html(array.length-1);
			$('#checkCount').html(array.length-1);
			$('.checkboxMobileVersion').attr("checked",false);
			$('#checkAll').attr("checked",false);
			$(window.parent.document).find("#checkboxId").val(array.toString());
		}
	});


function compareArray(result){
	var array = $(window.parent.document).find("#checkboxId").val().split(",");
	var arrayResult = result.split(",");
	var newResult = "";
	for(var index in arrayResult){
		if($.inArray(arrayResult[index],array) == -1){
			newResult+=arrayResult[index]+",";
		}
	}
	return newResult;
}

</script>