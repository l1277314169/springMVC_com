<html>
<head>
<title>工作任务</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
 <style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
    .allH{
		height:100%;
	}
</style>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
</head>
<body  class="allH main_body">
	<#assign privCode="C1M15">
	<#include "/base.ftl" />
	<div id="content"  class="allH">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">基础数据管理</a>
				<a class="linkPage active" href="${contextPath}/wrTask/query">工作任务查看</a>
			</div>
	 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<input type="hidden" id="structureId" name="structureId" value="${structureId!''}">
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="storeId">部门：</label>
							<div class="controls">
								<input id="structureName" name="structureName" required="required" readonly type="text" value="${structureName}"/>
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">姓名：</label>
							<div class="controls">
							  <input type="text" id="userName" name="userName" value="${userName}" class="input-medium" dataPosition=""/>
							</div>
						</div>
						<div nowrap="true" class="fl">
								<label class="control-label fl" for="channelName">开始日期：</label>
								<div class="controls">
								  <input id="startDate" type="text" name="startTime" class="input-medium" value="${startTime}" readonly="readonly" />
								</div>
							</div>
						<div nowrap="true" class="fl">
								<label class="control-label fl" for="distributorName">结束日期：</label>
								<div class="controls">
								  <input id="endDate"type="text" name="endTime" class="input-medium" value="${endTime}" readonly="readonly" />
								</div>
							</div>
					</div>
					<div class="form-actions">
					<@shiro.hasPermission name="C1M13F2">
						<!--<button type="button" id="new_btn" class="btn btn-success">添加任务</button>-->
					</@shiro.hasPermission>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
				      	<th>日期</th>
				      	<th>姓名</th>
				      	<th>部门</th>
				      	<th>工作任务数</th>
				      	<th>工作时长</th>
						<th width="100px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as wrTaskFinishCount>
						<tr>
								<td>${(wrTaskFinishCount.finishDate)?string("yyyy-MM-dd")}</td>
								<td>${wrTaskFinishCount.userName}</td>
								<td>${wrTaskFinishCount.structureName}</td>
							 	<td>${wrTaskFinishCount.finishCount}</td>
							 	<td>
							 		<span style="<#if wrTaskFinishCount.finishHour < 9>color:red;<#elseif wrTaskFinishCount.finishHour gte 9 && wrTaskFinishCount.finishHour lte 12>color:Orange;<#elseif wrTaskFinishCount.finishHour gt 12>color:Green;</#if>">
		      							<#if wrTaskFinishCount.finishHour == 0>未完成离店小结<#else>${wrTaskFinishCount.finishHour}小时</#if>
	      							</span>
								</td>
								<td>
									<a class="showWrTaskData" href="javascript:void(0);" clientUserId="${wrTaskFinishCount.clientUserId}" finishDate="${(wrTaskFinishCount.finishDate)?string("yyyy-MM-dd")}">查看详情</a>				 							 					 
								</td>
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
	</div>
	<#include "/footer.ftl" />
</body>
</html>
<script>
var showWrTaskDatalDialog;
$(function(){
	
	$("a.showWrTaskData").bind("click",function(){
	    var finishDate = $(this).attr("finishDate");
	    var clientUserId = $(this).attr("clientUserId");
		var url = "${contextPath}/wrTask/showWrTaskData?finishDate="+finishDate+"&clientUserId="+clientUserId;
		// showWrTaskDatalDialog = new xDialog(url,{},{title:"查看详情",iframe:true,width:1010,height:600});
		 layer.open({
			    type: 2,
			    title: '查看详情',
			    closeBtn: 1,
			    iframe:true,
			    area: ['1010px', '600px'],
			    content: url
			});
		return false;	
	});
	
	$.fn.zTree.init($("#treeDemo"), setting);
	
	$("#structureName").bind("click",function(){
		showMenu();
	});
	
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#endDate").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});

	$("#endDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#startDate").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
	
});

var setting = {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
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
		$("#structureName").attr("value", treeNode.name);
		$("#structureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#structureId").val(), null);
		//alert(node.id);
		if(node != null){
			$("#structureName").val(node.name);
		}
	};
    function showMenu() {
        var structureObj = $("#structureName");
        var clientStructureOffset = $("#structureName").position();
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
</script>