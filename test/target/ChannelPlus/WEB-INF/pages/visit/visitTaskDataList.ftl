<html>
<head>
<title>执行查看</title>
<#include "/common/head.ftl" />
<link rel="stylesheet" href="${contextPath}/css/store-export.css" />
<#include "/common/foot.ftl" />
</head>
<script type="text/javascript">
	var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {  
                 layer.alert('亲，组织架构加载失败！');  
            }, 
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
		var check = (treeNode && !treeNode.isParent);
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
		$("#structureSel").attr("value", treeNode.name);
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
			$("#structureSel").val(node.name);
		}
	};
	
    function showMenu() {
        var structureObj = $("#structureSel");
        var cityOffset = $("#structureSel").offset();
        $("#menuContent").css({ left: cityOffset.left + "px", top: cityOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }        
    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting);
    });
</script>
<body  class="main_body">
	    <#if taskType == 1>
		<#assign privCode="C1M9">
	    <#include "/base.ftl" />
		<#elseif taskType == 2>
 		<#assign privCode="C1M10">	
 		 <#include "/base.ftl" />	
 		<#elseif taskType == 3>
	    <#assign privCode="C1M11">
	    <#include "/base.ftl" />
		</#if>  
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<#if taskType == 1>
						<a class="linkPage active" href="${contextPath}/visitTaskData/query/1">门店拜访查看</a>
					<#elseif taskType == 2>
						<a class="linkPage active" href="${contextPath}/visitTaskData/query/2">门店检核查看</a>
					<#elseif taskType == 3>
						<a class="linkPage active" href="${contextPath}/visitTaskData/query/3">门店工作查看</a>
					</#if>
				</div>
		 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<input type="hidden" id="taskType" name="taskType" value="${taskType}">
					<!--<input type="hidden" id="task1id" name="task1id" value="2"> -->
					<input type="hidden" id="visitedFlag" name="visitedFlag" value="${visitedFlag}">
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="structureSel">所属部门：</label>
						<!--	<div class="controls">
							  <input id="structureSel" type="text" class="input-medium" name="structureSel" readonly="readonly" value="" onclick="showMenu(this.id);"/>
							  <input type="text" id="structureId" name="structureId" value="${structureId}" style="display:none;">
							</div>
							-->
						  <div class="controls">
						  <input type="text" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
						  <input type="text" id="arg_dept_ids" name="structureId" value="${structureId}" style="display:none;">
						  <#assign structureFtlName="arg_dept_ids">
						  <#include "/widgets/structure.ftl" />
						   </div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="userName">人员姓名：</label>
							<div class="controls">
							  <input type="text" name="userName" class="input-medium" value="${userName}">
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">人员职位：</label>
							<div class="controls">
							  <input type="text" id="positionId" name="positionId" class="input-medium" dataPosition="${positionId}"/>
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="visitingDate">拜访时间：</label>
							<div class="controls">
							  <input type="text" id="visitingDate" name="visitingDate" class="input-medium" value="${visitingDate}" readonly>
							</div>
						</div>
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="visitedFlag">已提报：</label>
							<div class="controls">
							  <input type="checkbox" class="input-medium" id="isVisited" value="">
							</div>
						</div>
					</div>
					
					<div class="form-actions">
						<!--<button type="button" id="new_btn" class="btn btn-success">新增</button>-->
					<!--<#if taskType == 1>
							<@shiro.hasPermission name="C1M9F1">
								<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
							</@shiro.hasPermission>
						<#elseif taskType == 2>
							<@shiro.hasPermission name="C1M10F1">
								<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
							</@shiro.hasPermission>
						<#else>
							<@shiro.hasPermission name="C1M11F1">
								<button type="button" id="exportBtn" class="btn btn-primary">导出</button>
							</@shiro.hasPermission>
						</#if>-->
						<!-- <span id="hint" style="color : #E06200;" >默认不查询数据，点击查询再查询数据</span>-->
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					
					<div data-mask="overlay" class="overlay" style="display: none;"></div>
					<div style="display: none;" id="dateId">
							<div class="moveBar">  
						        <div id="banner" class="content">门店导出时间</div>
						        <div class="startAndendDate"> 
						        	<div style="float:left;">
										<span style="margin-left:30px">开始时间：</span>
										<span><input type="text" id="startDate" name="startDate" class="input-medium" value="${startDate}"  readonly/></span>
									</div>
									<div style="margin-left:270px">
										<span>结束时间： </span>
										<span><input type="text" id="endDate" name="endDate" class="input-medium" value="${endDate}"  readonly/></span>
									</div>
							        <button id="confrim" class="btn btn-success ">确定</button>
							        <button id="cancel" class="btn btn-danger">取消</button>
						        </div>
					    </div> 
					</div>
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th width="10%">部门</th>
						<th width="9%">职位</th>
						<th width="10%">姓名</th>
						<#if taskType == 3>
							<th width="10%">是否提报</th>
						<#elseif taskType == 2>
							<th>实际拜访数</th>
							<th width="17%">首次进店/最后出店时间</th>
							<th width="10%">店内平均时间</th>
							<th>定位匹配数</th>
						<#else>
							<th>（拜访计划）计划/临时/取消</th>
							<th>（实际拜访）计划/临时</th>
							<th width="12%">拜访达成率（计划门店）</th>
							<th width="17%">首次进店/最后出店时间</th>
							<th width="10%">店内平均时间</th>
							<th width="10%">定位匹配数</th>
						</#if>
						<th width="7%">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as item>
							<tr>
								<td>${item.structureName!''}</td>
								<td>${item.positionName!''}</td>
								<td>${item.name!''}</td>
								<#if taskType == 3>
									<td>
										<#if item.firstInTime ??>
											已提报
										<#else>
										</#if>
									</td>
								<#elseif taskType == 2>
									<td>${item.visitedStores!''}</td>
									<td><#if (item.firstInTime)??>${item.firstInTime[11..item.firstInTime?length-3]}</#if> - <#if (item.lastOutTime)??>${item.lastOutTime[11..item.firstInTime?length-3]}</#if></td>
									<td>${item.avgVisitedMinutes}分钟</td>
									<td>吻合${item.locateMatch!''}/不吻合${item.locateMismatch!''}/无定位${item.noLocate!''}</td>
								<#else>
									<td>${item.plannedPops!''}/${item.tempPlanned!''}/${item.canceledPops!''}</td>
									<td>${(item.visitedStores)-(item.tempVisited)}/${item.tempVisited!''}</td>
								<#if (item.visitedStores)-(item.tempVisited) = 0  && item.tempVisited = 0>
									<td>N/A</td>
									<td></td>
									<td></td>
									<td></td>
								<#else>
									<td><#if (item.plannedPops == 0) || (item.plannedPops == item.canceledPops)>N/A<#elseif (item.visitedStores!=0 && item.plannedPops!=0 && item.plannedPops-item.canceledPops !=0)>${((item.visitedStores-item.tempVisited)/(item.plannedPops-item.canceledPops))?string(",##0.0#%")}<#else>0%</#if></td>
									<td><#if (item.firstInTime)??>${item.firstInTime[11..item.firstInTime?length-3]}</#if> - <#if (item.lastOutTime)??>${item.lastOutTime[11..item.firstInTime?length-3]}</#if></td>
									<td>${item.avgVisitedMinutes}分钟</td>
									<td>吻合${item.locateMatch!''}/不吻合${item.locateMismatch!''}/无定位${item.noLocate!''}</td>
								</#if>
							</#if>
								<td>
									<a class="showVisitTaskDataDetail" href="javascript:void(0);" dataUserId="${item.clientUserId!''}" dataPositionId="${item.clientPositionId!''}">查看详细</a>
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
				<div id="menuContent" class="menuContent" style="display: none; position: absolute;background-color:#cfcfcf;">
				<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 200px;">
				</ul>
				</div>
		
	</div>
			<#include "/footer.ftl" />

</body>
</html>
<script type="text/javascript" language="javascript">
var dataDetailDialog;
$(document).ready(function () {
	var isVisited = $("#visitedFlag").val();
	if(isVisited == 1){
		$("#isVisited").attr("checked","checked");
	}
	
	$("#isVisited").bind("click",function(){
		if($(this).attr("checked")) {
			$(visitedFlag).val("1");
		}else {
			$(visitedFlag).val("0");
		}
	});
	
	function AddDays(date,days){
		var nd = new Date(date);
		nd = nd.valueOf();
		nd = nd + days * 24 * 60 * 60 * 1000;
		nd = new Date(nd);
		var y = nd.getFullYear();
		var m = nd.getMonth()+1;
		var d = nd.getDate();
		if(m <= 9) m = "0"+m;
		if(d <= 9) d = "0"+d; 
		var cdate = y+"-"+m+"-"+d;
		return cdate;
	}
	
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			var NewDate=AddDays(dateText,6);
			$("#endDate").datepicker("option","minDate",dateText);
			$("#endDate").datepicker("option","maxDate",NewDate);
			$(this).focus();
			$(this).blur();
		},
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
		},
	});
	
	$("#visitingDate").datepicker({
		duration: "slow",
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$(this).focus();
			$(this).blur();
		},
	});
	//导出
	$("#exportBtn").bind("click",function(){
		$('#dateId').show();
		$('.overlay').css('display','block');
	});
	$('#confrim').bind('click',function(){
		var type=$("#taskType").val();
		var url ="${contextPath}/visitTaskData/showExportDialog/"+type;
		$('#searchForm').attr("action",url);
		$('#searchForm').submit();
		$('#searchForm').attr("action","${contextPath}/visitTaskData/query/"+type);
		$('#dateId').hide();
		$('.overlay').css('display','none');
		return false;
	});
	
	$('#cancel').bind('click',function(){
		$('#dateId').css('display','none');
		$('.overlay').css('display','none');
	});
	$("a.showVisitTaskDataDetail").bind("click",function(){
    	var visitDate = $("input[name='visitingDate']").val();
    	var clientUserId = $(this).attr("dataUserId");
    	var clientPositionId = $(this).attr("dataPositionId");
    	var taskType = $("#taskType").val();
		var url = "${contextPath}/visitTaskData/showVisitTaskDataDetail";
		url += "?clientUserId="+clientUserId+"&clientPositionId="+clientPositionId+"&visitDate="+visitDate+"&taskType="+taskType;
		//alert(url);
		//dataDetailDialog = new xDialog(url, {}, {title:"查看详细",iframe:true,width:1010,height:600 });
		//return false;
		
			layer.open({
			    type: 2,
			    title:'查看详细',
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['1010px', '600px'],
			    content: url
			});
	});
	var positionId = $("#positionId").attr("dataPosition");
	loadPosition(positionId);
});

function loadPosition(positionId){
	var taskType=$("#taskType").val();
	$.ajax({
		type : "post",
		url : "${contextPath}/clientPosition/getClientPositionByMobilePer",
		data : {taskType:taskType},
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#positionId").select2({
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
			$("#positionId").select2("val", positionId);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

//树-input选项,判断用户的按键行为,如果为"Backspace"或者"delete"按键，则清空选择项。
	$("#structureSel").keydown(function(e){
		if(e.keyCode == 8 || e.keyCode == 46){
			$("#structureSel").val("");
			$("#searchClientStructureId").val("");
			$("#structureId").val("");
		};
	});
</script>