<html>
<head>
<title>特殊事件</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
</head>
<body class="main_body">

		<#assign privCode="C1M8">
	<#include "/base.ftl" />
	
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">执行管理</a>
				<a class="linkPage active" href="${contextPath}/specialEvent/query">特殊事件</a>
			</div>
	 	</div>
	 	
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
							<label class="control-label " for="event">事件类型：</label>
							<div class="controls">
								<select id="eventId" name="optionValue">
								 <#list  optionsList as option>
								 	<option value="${option.optionValue}">${option.optionText}</option>
								 </#list>
								</select>
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="clientStructureId">部门：</label>
							<div class="controls">
								<input id="clientStructureId" class="input-medium" name="structureName" value="${structureSel}" type="text" readonly   onclick="showMenu();"/>
								<input type="text" id="structureId" name="structureId" value="${searchClientStructureId}" style="display:none;">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="searchName">人员姓名：</label>
							<div class="controls">
								<input type="text" id="searchName" class="input-medium" name="searchName" value="${searchName!''}">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label">开始时间：</label>
							<div class="controls">
								<input type="text" id="startDate" name="startDate" value="${startDate!''}" class="input-medium" readonly>
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label">结束时间：</label>
							<div class="controls">
								<input type="text" id="endDate" name="endDate" value="${endDate!''}" class="input-medium" readonly>
							</div>
					</div>
				</div>
				<div class="form-actions">
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					<@shiro.hasPermission name="C1M8F1">
							<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
					</@shiro.hasPermission>
				</div>
				<input type="hidden" id="searchClientStructureId" name="clientStructureId">
			</form>
		</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered c_list" id="c_list">
					<tr>
					<th>人员姓名</th>
					<th>部门</th>
					<#list clientBusinessDefinitionList as clientBusinessDefinition>
						<th >${clientBusinessDefinition.columnDesc}</th>
					</#list>
					</tr>
					<tbody>
							<#list pageParam.items as specialEvent>
									<tr>
										<#list specialEvent as it>
												<td <#if it.cvalue ??>width="${it.colWidth}%"</#if><#if it.type == 0> width="${it.colWidth}%" title="${it.cvalue?substring(it.cvalue?last_index_of(',')+1)}"</#if>>
												<#if it.type == 11 && it.picNames ??>
													<#list it.picNames as pic>
														<a href="${contextPath}/uploadfiles/${pic}" class="lightbox"><img src="${contextPath}/uploadfiles/${pic}" style="width:20;height:20;" /></a>
													</#list>
													${pic}
												<#elseif it.type == 9 && it.cvalue ?? >
													${it.cvalue?substring(0,16)}
												<#elseif it.type == 0>
													${it.cvalue?substring(0,it.cvalue?last_index_of(','))}
												<#else>
													${it.cvalue}
												</#if>
												</td>
										</#list>
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
			<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
	</div>

		<#include "/footer.ftl" />
</body>
</html>
<SCRIPT type="text/javascript">
	//导出
	$("#exportBtn").bind("click",function(){
			layer.confirm("确认导出", function () {
				var url ="${contextPath}/specialEvent/showExportDialog";
				$('#searchForm').attr("action",url);
				$('#searchForm').submit();
				$('#searchForm').attr("action","${contextPath}/specialEvent/query");
				layer.closeAll();
		});
		return false;
	});
	
	$(".lightbox").lightbox({
		fitToScreen: true
	});
	//部门加载树
	var setting = {
		async : {
			enable : true,
			type: "get",
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
    $("#startDate").datetimepicker({
    	changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#endDate").datepicker("option","minDate",dateText);
			$("#endDate").datetimepicker("option","minDateTime",$('#startDate').datetimepicker('getDate'));
		}
	});
	
	$("#endDate").datetimepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#startDate").datepicker("option","maxDate",dateText);
			//$("#startDate").datetimepicker("option","maxDateTime",$('#endDate').datetimepicker('getDate'));
		}
	});
});
	
</SCRIPT>
