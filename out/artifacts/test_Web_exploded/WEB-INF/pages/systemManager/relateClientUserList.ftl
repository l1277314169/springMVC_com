<html>
<head>
<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<title>手机版本关联人员页面</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
</head>
<body>
	<div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div class="fl">
						<label class="control-label fl" for="clientStructureId">所属部门：</label>
						<div class="controls">
							<input id="clientStructureId" class="input-medium" name="structureSel" value="${structureSel}" type="text" readonly   onclick="showMenu();"/>
							<input type="text" id="structureId" name="structureId" value="${searchClientStructureId}" style="display:none;">
						</div>
					</div>
					<div class="fl">
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
					<div class="fl">
						<label class="control-label fl" for="searchName">姓名：</label>
						<div class="controls">
							<input type="text" class="input-medium" name="searchName" value="${searchName!''}">
						</div>
					</div>
				</div>
				<div>
					<div class="form-actions">
							您已选择：<span id ="checkCount" style="color:red"></span>个人员
							<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					<div>
				</div>
				<input type="hidden" id="searchClientStructureId" name="clientStructureId" value="${searchClientStructureId}">
				<input type="hidden" name="structureName" value="${structureName}">
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th><input type="checkbox" id="currPageAll" </th>
						<th>工号</th>
						<th>姓名</th>
						<th>用户名</th>
						<th>部门</th>
						<th>职位</th>
						<th>手机号码</th>
					    <th>在职状态</th>
					    <th>账号状态</th>
					</tr>
					<tbody>
						<#list pageParam.items as clientUser>
							<tr>
								<td><input type="checkbox" id="checkboxId" name="checkboxId" class="checkboxMobileVersion" value="${clientUser.clientUserId}"></td>
								<td>${clientUser.userNo!''}</td>
								<td>${clientUser.name!''}</td>
								<td>${clientUser.loginName!''}</td>
								<td>${clientUser.structureName!''}</td>
								<td>${clientUser.positionName!''}</td>
								<td>${clientUser.mobileNo}&nbsp;</td>
								<td><#if clientUser.status = 1>在职<#elseif clientUser.status = 0>离职</#if></td>
								<td><#if clientUser.isActivation = 1>启用<#elseif clientUser.isActivation = 0>禁用</#if></td>
							</tr>
						</#list>
					</tbody>
				</table>
					<#if pageParam.items?exists>
							<div class="paging" >
							   ${pageParam.getPagination()}
							</div>
					</#if>
					<button id="cannel" data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');"  class="btn btn-danger" style="margin-left:320px">关闭</button>
			</div>
		</div>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
</body>
</html>
<script type="text/javascript">


$('#cannel').click(function(){
	parent.addDialog.close();
});
$('#savetButton').click(function(){
	$(window.parent.document).find("#clientUserCheckbox").html($("#checkCount").html());
	parent.addDialog.close();
});
var pobj =  $(window.parent.document).find("#checkboxId");
var storeIds = pobj.val();
var array = [];
if(storeIds != ""){
	array = storeIds.split(",");
}
$("#checkCount").html(array.length);
 $(".checkboxMobileVersion:checkbox").each(function () {
    	for (var index in array) {
            if ($(this).val() == array[index]){
                $(this).attr("checked", "checked");
                break;
            }
        }
 	});

//当前page页全部选中 全选就选中
if($('.checkboxMobileVersion:checked').length == $('.checkboxMobileVersion').length){
	$('#currPageAll').attr("checked",true);
}else{
	$('#currPageAll').attr("checked",false);
}

$(".checkboxMobileVersion").click(function(){
	storeIds=pobj.val();
	if(storeIds != ""){
		array = storeIds.split(',');
	}
	if($(this).prop("checked")){
		array.push($(this).val());
		$("#checkCount").html(array.length);
		$(window.parent.document).find("#clientUserCheckbox").html($("#checkCount").html());
	}else{
		array.splice($.inArray($(this).val(),array),1);
		$("#checkCount").html(array.length);
		$(window.parent.document).find("#clientUserCheckbox").html($("#checkCount").html());
	}
		pobj.attr("value",array.join(","));
	if($('.checkboxMobileVersion:checked').length == $('.checkboxMobileVersion').length){
		$('#currPageAll').attr("checked",true);
	}else{
		$('#currPageAll').attr("checked",false);
	}
});




$(function(){
	//树-input选项,判断用户的按键行为,如果为"Backspace"或者"delete"按键，则清空选择项。
	$("#clientStructureId").keydown(function(e){
		if(e.keyCode == 8 || e.keyCode == 46){
			$("#clientStructureId").val("");
			$("#searchClientStructureId").val("");
		};
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
        //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo"), setting);
        $('#clientPositionId').select2();
    });
//全选
$("#currPageAll").bind("click",function(){
	storeIds=pobj.val();
	if(storeIds != "") {
		array = storeIds.split(',');
	}
	if($(this).attr('checked')){
		 $("[name = checkboxId]:checkbox").each(function(){
			 if(!$(this).prop("checked")){
   				  $(this).attr("checked",true);
				  array.push($(this).val());
   				  $('#checkCount').html(array.length);
			 }
		 });
	}else{
		 $("[name = checkboxId]:checkbox").each(function(){
			  $(this).attr("checked",false);
			  array.splice($.inArray($(this).val(),array),1);
			  $('#checkCount').html(array.length);
 		}); 
	}
	pobj.attr("value",array.join(","));
	$(window.parent.document).find("#clientUserCheckbox").html($("#checkCount").html());

});
</script>