<html>
<head>
<#include "/common/head.ftl" />
<title>促销管理</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
		<#assign privCode="C1M6">
	    <#include "/base.ftl" />
	
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">促销管理</a>
				<a class="linkPage active" href="${contextPath}/promotionActivity/query">促销管理</a>
			</div>
	 	</div>
			<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm">
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="activityName">活动名称：</label>
						<div class="controls">
							  <input type="text" name="activityName" class="input-medium" >
						</div>
					</div>
					<div nowrap="true" class="fl">
						<label class="control-label fl" for="activityType">活动类型：</label>
						<div class="controls">
							  <select id="activityType" name="activityType" class="input-medium">
							    <option value="">--请选择--</option>
			                    <option value="1">买赠</option>
			                    <option value="2">折扣</option>
			                </select>
						</div>
					</div>
              	</div>
				<div class="form-actions">
				<@shiro.hasPermission name="C1M6F1">
					<button type="button" id="addBtn" class="btn btn-success">新增</button>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="C1M6F4">
					<button type="button" id="importBtn" class="btn btn-primary">导入</button>
				</@shiro.hasPermission>
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
				</div>
				<input type="hidden" id="searchClientStructureId" name="searchClientStructureId" value="">
				<input type="hidden" name="page" value="${page}">
			</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th>编号</th>
						<th>活动名称</th>
						<th>活动英文</th>
						<th>活动内容</th>
						<th>活动类型</th>
						<th>开始时间</th>
					    <th>结束时间</th>
					    <th>折扣</th>
					    <th>优惠金额</th>
					    <th>客户</th>
					    <@shiro.hasAnyPermission name="C1M6F2,C1M6F3">
						<th width="110px">操作</th>
						</@shiro.hasAnyPermission>
					</tr>
					<tbody>
						<#list pageParam.items as promotionActivity>
							<tr>
								<td>${promotionActivity.activityNo!''}</td>
								<td>${promotionActivity.activityName!''}</td>
								<td>${promotionActivity.activityNameEn!''}</td>
								<td>${promotionActivity.activityContent!''}</td>
								<td><#if promotionActivity.activityType==1>买赠<#else>折扣</#if></td>
								<td><#if (promotionActivity.startTime)??>${(promotionActivity.startTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
								<td><#if (promotionActivity.endTime)??>${(promotionActivity.endTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
								<td>${promotionActivity.discount!''}</td>
								<td>${promotionActivity.bonus!''}</td>
								<td>${promotionActivity.clientName!''}</td>
								<@shiro.hasAnyPermission name="C1M6F2,C1M6F3">
								<td>
									<@shiro.hasPermission name="C1M6F2">
									<a class="editPromotionActivity" href="javascript:void(0);" dataId="${promotionActivity.activityId!''}" dataName="${promotionActivity.activityName!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C1M6F3">
									<a class="deletePromotionActivity" href="javascript:void(0);" data="${promotionActivity.activityId!''}">删除</a>
									</@shiro.hasPermission>
								</td>
								</@shiro.hasAnyPermission>
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
		<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
var importDialog;
$(function(){
	//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/promotionActivity/showImportDialog";
		// importDialog = new xDialog(url, {}, {title:"人员导入",width:650,height:330 });
		 layer.open({
			    type: 2,
			    title: '人员导入',
			    closeBtn: 1,
			    area: ['650px', '330px'],
			    content: url
			});
		return true;
	});
	//新增
	$("#addBtn").bind("click",function(){
		var url = "${contextPath}/promotionActivity/showAddPromotionActivity";
		// addDialog = new xDialog(url, {}, {title:"新增促销活动",width:700,height:600 });
		//return false;	
			layer.open({
				    type: 2,
				    title: '新增促销活动',
				    closeBtn: 1,
				    area: ['800px', '800px'],
				    content: url
				});
		
	});
		//编辑
	$("a.editPromotionActivity").bind("click",function(){
	    var activityId=$(this).attr("dataId");
	    var promotionActivityName=$(this).attr("dataName");
		var url = "${contextPath}/promotionActivity/showEditPromotionActivity/"+activityId;
		// editDialog = new xDialog(url,{},{title:"编辑职位---"+promotionActivityName,width:600,height:550});
		 //return false;
		 layer.open({
			    type: 2,
			    title:'编辑职位---'+promotionActivityName,
			    closeBtn: 1,
			    area: ['600px', '550px'],
			    content: url
			});
			
	});
		//删除
	$("a.deletePromotionActivity").bind("click",function(){
		var activityId=$(this).attr("data");
	    layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/promotionActivity/deletePromotionActivity/"+activityId,
				type : "get",
				async: false,
				data : {distributorId : distributorId},
				dataType:'JSON',
				success : function(result) {
					 confirmAndRefresh(result); 
				}
			});
	   });
		   return false;
	});

	function confirmAndRefresh(result){
		if (result.code == "success") {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				$("#searchForm").submit();
			}});
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
});

</script>