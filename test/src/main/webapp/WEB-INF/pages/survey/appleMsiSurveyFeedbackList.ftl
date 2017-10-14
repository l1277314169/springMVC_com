<html>
<head>
<#include "/common/head.ftl" />
<title>问卷二期</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body" >	
	<#assign privCode="C5M3">
		<#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/appleMsiSurvey/query">问卷二期</a>
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
						<label class="control-label fl" for="visitor">访问员：</label>
						<div class="controls">
							<input type="text" id="visitor" name="visitor" class="input-medium" value="${visitor}"/>
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
					<@shiro.hasPermission name="C5M3F1">	
						<button type="button" id="addBtn" class="btn btn-success">添加</button>
					</@shiro.hasPermission>
					<!--<button type="button" id="exportMsiSurveyFeedback" class="btn btn-success">导出</button>-->
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
						<th>访问员</th>
						<th>提报时间</th>
						<#if clientId == 20>
							<th>申诉状态</th>
						</#if>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as msiSurveyFeedback>
							<tr>
								<td>${msiSurveyFeedback.storeNo}</td>
								<td>${msiSurveyFeedback.storeName}</td>
								<td title="${msiSurveyFeedback.addr!''}">
									<#if msiSurveyFeedback.addr?? && msiSurveyFeedback.addr?length gt 8>
										${msiSurveyFeedback.addr?substring(0,8)}...
									<#else>
										${msiSurveyFeedback.addr!''}
									</#if>
								</td>
								<td>${msiSurveyFeedback.createUserName}</td>
								<td>${msiSurveyFeedback.visitor}</td>
								<td>${msiSurveyFeedback.feedbackDate?string("yyyy-MM-dd")}</td>
								<td>
											<#if clientId == 20 && msiSurveyFeedback.status ?? && msiSurveyFeedback.status ==1>
												已申诉
											<#else>
												未申诉
											</#if>
								</td>
								<td>
									<@shiro.hasPermission name="C5M3F3">				
										<a class="edit" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">修改</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C5M3F4">
										<a class="approval" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">审核</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C5M3F2">	
										<a class="delete" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">删除</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C5M3F6">
										<a class="exportPdf" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">pdf导出</a>
									</@shiro.hasPermission>
									<#if clientId == 20 && msiSurveyFeedback.status ?? && msiSurveyFeedback.status !=1>
											<@shiro.hasPermission name="C5M3F7">
												<a class="appeal" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">申诉</a>
											</@shiro.hasPermission>
									</#if>
									<a class="view" href="javascript:void(0);" feedbackId="${msiSurveyFeedback.feedbackId}" dataId="${msiSurveyFeedback.storeId}"  dataNo="${msiSurveyFeedback.storeNo}" dataName="${msiSurveyFeedback.storeName}">查看</a>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
				
				
	
	</div>
					<#if pageParam.items?exists>
							<div class="paging" >
							   ${pageParam.getPagination()}
							</div>
					</#if>
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
			$('#breadcrumb',window.top.document).append("<a class='linkPage active' href='${contextPath}/appleMsiSurvey/queryStore' target='main'> 添加暗访问卷</a>");
			var url = "${contextPath}/appleMsiSurvey/queryStore";
			window.location.href=url;
		});
		
		$("#exportMsiSurveyFeedback").bind("click",function(){
			$.confirm("确认导出历史数据吗？", function () {
				var url = "${contextPath}/appleMsiSurvey/exportMsiSurveyFeedback";
				$("#searchForm").attr("action",url);
				$("#searchForm").submit();
				$("#searchForm").attr("action","");
			});
		});
		
		$(".edit").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
			var url = "${contextPath}/appleMsiSurvey/showEditAppleMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName;
			window.location.href=url;
		});
		
		$(".exportPdf").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
			var url = "${contextPath}/appleMsiSurvey/exportPDF/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName;
			window.location.href=url;
		});
		
		$(".appeal").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
			var url = "${contextPath}/appealMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName;
			window.location.href=url;
		});

		$(".view").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
			var url = "${contextPath}/appleMsiSurvey/showEditAppleMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName+"&view=1";
			window.location.href=url;
		});
		
		$(".appeal").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			var storeId=$(this).attr("dataId");
		    var storeNo=$(this).attr("dataNo");
		    var storeName=$(this).attr("dataName");
		    var url = "${contextPath}/appleMsiSurvey/appealMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName+"&view=1";
			window.location.href=url;
		});
		
		$(".delete").bind("click",function(){
			var feedbackId=$(this).attr("feedbackId");
			layer.confirm("确认删除？", function () {
				var url = "${contextPath}/appleMsiSurvey/deleteMsiSurveyData/"+feedbackId; 
				$.post(url,function(result){
					if(result.code=="success"){
						layer.alert(result.message,function(){
							window.location.href="${contextPath}/appleMsiSurvey/query";
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
		    var url = "${contextPath}/appleMsiSurvey/approvalMsiSurveyData/"+feedbackId+"/?storeId="+storeId+"&storeNo="+storeNo+"&storeName="+storeName;
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