<html>
<head>
<#include "/common/head.ftl" />
<title>暗访问卷数据列表</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
		<#assign privCode="C5M2">
		<#include "/base.ftl" />
	<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">问卷管理</a>
					<a class="linkPage active" href="${contextPath}/msiSurveyFeedback/query">暗访管理</a>
				</div>
		 	</div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label " for="structure">部门：</label>
						<div class="controls">
						 	<input id="clientStructureId" class="input-medium" name="structureSel"  type="text" readonly   onclick="showMenu();"/>
						 	<input type="text" id="structureId" name="clientStructureId" value="${clientStructureId}" style="display:none;">
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="structureName">门店编号：</label>
						<div class="controls">
							<input type="text" id="storeNo" name="storeNo" class="input-medium" value="${storeNo}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientPositionId">门店名称：</label>
						<div class="controls">
							<input type="text" id="storeName" name="storeName" class="input-medium" value="${storeName}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="promoter">促销员：</label>
						<div class="controls">
							<input type="text" id="promoter" name="promoter" class="input-medium" value="${promoter}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="provinceId">省份：</label>
							<div class="controls">
							  <input type="text" id="provinceId" name="provinceId" value="${provinceId}"/>
							</div>
						</div>
					<div nowrap="true" class="fl">
						<label class="control-label " for="cityId">城市：</label>
						<div class="controls">
						  <input type="text" id="cityId" name="cityId" value="${cityId}"  />
						</div>
					</div>							
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientPositionId">起始时间：</label>
						<div class="controls">
							<input type="text" id="startDate" name="startDate" class="input-medium" value="${startDate}"/>
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="clientPositionId">结束时间：</label>
						<div class="controls">
							<input type="text" id="endDate" name="endDate" class="input-medium" value="${endDate}"/>
						</div>
					</div>
				</div>
				
				<div class="form-actions">
					<@shiro.hasPermission name="C5M2F1">
						<button type="button" id="addBtn" class="btn btn-success">添加</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M2F6">
						<button type="button" id="exportMsiSurveyFeedback" class="btn btn-success">导出</button>
					</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" name="page" value="${page}">
				<input type="hidden" id="count" name="count" value="${count}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>门店编号</th>
						<th>门店名称</th>
						<th>门店地址</th>
						<th>提报人</th>
						<th>促销员</th>
						<th>提报时间</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as msiSurveyFeedback>
							<tr>
								<td>${msiSurveyFeedback.storeNo}</td>
								<td>${msiSurveyFeedback.storeName}</td>
								<td>${msiSurveyFeedback.addr}</td>
								<td>${msiSurveyFeedback.createUserName}</td>
								<td>${msiSurveyFeedback.promoter}</td>
								<td>${msiSurveyFeedback.feedbackDate?string("yyyy-MM-dd")}</td>
								<td>
									<@shiro.hasPermission name="C5M2F5">	
										<a class="delete" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">删除</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C5M2F2">				
									<a class="edit" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">修改</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C5M2F3">		
										<a class="appeal" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">申诉</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C5M2F4">
										<a class="approval" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">审批</a>
									</@shiro.hasPermission>
									<a class="view" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">查看</a>
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
			<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
			</div>
		</div>
	<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	$(document).ready(function () {
	
		$.fn.zTree.init($("#treeDemo"), setting);
		
		$("#addBtn").bind("click",function(){
			$('#breadcrumb',window.top.document).append("<a class='linkPage active' href='${contextPath}/msiSurveyFeedback/queryStore' target='main'> 添加暗访问卷</a>");
			var url = "${contextPath}/msiSurveyFeedback/queryStore";
			window.location.href=url;
		});
		
		$("#exportMsiSurveyFeedback").bind("click",function(){
			layer.confirm("确认导出历史数据吗？", function () {
				var url = "${contextPath}/msiSurveyFeedback/exportMsiSurveyFeedback";
				$("#searchForm").attr("action",url);
				$("#searchForm").submit();
				$("#searchForm").attr("action","");
				layer.closeAll();
			});
		});
		
		$(".edit").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
			var url = "${contextPath}/msiSurveyFeedback/showEditMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName;
			window.location.href=url;
		});

		$(".view").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
			var url = "${contextPath}/msiSurveyFeedback/showEditMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName+"&view=1";
			window.location.href=url;
		});
		
		$(".appeal").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
		    var url = "${contextPath}/msiSurveyFeedback/appealMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName+"&view=1";
			window.location.href=url;
		});
		
		$(".delete").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			layer.confirm("确认删除？", function () {
				var url = "${contextPath}/msiSurveyFeedback/deleteMsiSurveyData/"+feedbackId; 
				$.post(url,function(result){
					if(result.code=="success"){
						layer.alert(result.message,function(){
							window.location.href="${contextPath}/msiSurveyFeedback/query";
						});
					}	
				});
			});
		});
		
		$(".approval").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
		    var url = "${contextPath}/msiSurveyFeedback/approvalMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName+"&view=1";
			window.location.href=url;
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
		
		loadProvince();
		
		var provinceId = '${provinceId}'; 
		if(provinceId!=''){
			$.ajax({
				type : "post",
				url : "${contextPath}/commService/findCityListByProvinceId/"+provinceId,
				dataType : "json",
				cache : false,
				success : function(data) {
					var jsonData = eval(data);
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"},";
						} else {
							thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"}";
						}
					});
					thisData += "]";
					var cData = $.parseJSON(thisData);
					$("#cityId").select2({
						width:145,
						placeholder: "请选择",
						allowClear: true,
						data: cData
					});
				},
				error : function(data) {
					alert("数据加载失败！");
				}
			});
		}else{
			$("#cityId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
		}
		
		$("#provinceId").on("change", function () {
			var value = $(this).val();
			if(value == "") {
				$("#cityId").val('');
				$("#districtId").val('');
				$("#cityId").select2({
		        	width:145,
					placeholder: "请选择",
					data:[]
				});
		        $("#districtId").select2({
		        	width:145,
					placeholder: "请选择",
					data: []
		        });
			}
			else{
				$.ajax({
					type : "post",
					url : "${contextPath}/commService/findCityListByProvinceId/"+value,
					dataType : "json",
					cache : false,
					success : function(data) {
						var jsonData = eval(data);
						var thisData = "[";
						$.each(jsonData, function(index, item) {
							if(index != jsonData.length-1){
								thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"},";
							} else {
								thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"}";
							}
						});
						thisData += "]";
						var cData = $.parseJSON(thisData);
						$("#cityId").select2({
							width:145,
							placeholder: "请选择",
							allowClear: true,
							data: cData
						});
					},
					error : function(data) {
						alert("数据加载失败！");
					}
				});
			}
		});
});

function loadProvince(){
	$.ajax({
		type : "post",
		url : "${contextPath}/commService/getProvinceAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"},";
				} else {
					thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#provinceId").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
}

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
		$("#storeAlls").attr("checked",false);	
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
</script>