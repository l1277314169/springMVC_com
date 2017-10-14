<html>
<head>
  <title>问卷</title>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
</head>

<body class="main_body">
	<#assign privCode="C5M1">
	<#include "/base.ftl" />
	
	<div id="content" >
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
				<a href="#">问卷管理</a>
				<a class="linkPage active" href="${contextPath}/survey/query">问卷查看</a>
			</div>
	 	</div>

		<div class="widget-content nopadding">
			<form class="form-horizontal" action="" id="searchForm" novalidate="novalidate">
				<input type="hidden" id="arg_client_id" name="arg_client_id" value="${params.arg_client_id}" />
				<input type="hidden" id="arg_start_date" name="arg_start_date" value="${params.arg_start_date}" />
				<input type="hidden" id="arg_end_date" name="arg_end_date" value="${params.arg_end_date}" />
				<input type="hidden" id="arg_filter_structure_ids" name="arg_filter_structure_ids" value="${params.arg_filter_structure_ids}" />
				<input type="hidden" id="arg_filter_user_ids" name="arg_filter_user_ids" value="${params.arg_filter_user_ids}" />
				<input type="hidden" id="clientId" name="clientId" value= "${clientId}" />
				
				<input type="hidden" id="feedbackId" name="feedbackId" />
				<input type="hidden" id="storeId" name="storeId" />
				
				<div class="control-group">
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">周期：</label>
					<div class="controls">
					  <input type="hidden" id="feedbackDate" name="feedbackDate" value="${params.feedbackDate}" class="input-medium" dataPosition=""/>
					  <#assign month="feedbackDate">
					  <#include "/widgets/month_widget.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">区域：</label>
					<div class="controls">
					  <input type="text" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
					  <input type="text" id="arg_dept_ids" name="arg_dept_ids" value="${params.arg_dept_ids}" style="display:none;">
					  <#assign structureFtlName="arg_dept_ids">
					  <#include "/widgets/structure.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">渠道类型：</label>
					<div class="controls">
					  <input type="text" id="clientStructureId_channel" name="clientStructureName_channel"  class="input-medium" readonly onclick="showMenu_channel();" />
					  <input type="hidden" id="arg_channel_ids" name="arg_channel_ids" value="${params.arg_channel_ids}" />
					  <#assign channelFTL="arg_channel_ids">
					  <#include "/widgets/channel_widget.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">客户类型：</label>
					<div class="controls">
					  <input type="text" id="clientStructureId_chain" name="clientStructureName_chain"  class="input-medium" readonly onclick="showMenu_chain();">
					  <input type="hidden" id="arg_types" name="arg_types" value="${params.arg_types}" >
					  <#assign chainFTL="arg_types">
					  <#include "/widgets/chain_widget.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">状态：</label>
					<div class="controls">
					 	<input type="text" id="arg_status" name="arg_status"  class="input-medium" value="${params.arg_status}" />
					  	<#assign statusFTL="arg_status">
					  	<#include "/widgets/store_status.ftl" />
					</div>
				</div>
				
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">省：</label>
					<div class="controls">
					  <input type="text" id="province_ids" name="province_ids" value="${params.province_ids}" class="input-medium" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">市：</label>
					<div class="controls">
					  <input type="text" id="city_ids" name="city_ids" value="${params.city_ids}" class="input-medium" />
					  <#include "/widgets/province_city_widget2.ftl" />
					</div>
				</div>
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">门店编号：</label>
					<div class="controls">
					  <input type="text" id="arg_store_no" name="arg_store_no" value="${params.arg_store_no}" class="input-medium" dataPosition=""/>
					</div>
				</div>
				
				
				<div nowrap="true" class="fl">
					<label class="control-label fl">门店名称：</label>
					<div class="controls">
					  <input type="text" id="arg_store_name" name="arg_store_name" value="${params.arg_store_name}" class="input-medium" dataPosition=""/>
					</div>
				</div>

				
				<@shiro.lacksPermission name="C5M1F13">
				<div nowrap="true" class="fl">
					<label class="control-label fl">访问员：</label>
					<div class="controls">
					  <input type="text" id="arg_visitor" name="arg_visitor" class="input-medium" value="${params.arg_visitor}" required=true />
					</div>
				</div>
				</@shiro.lacksPermission>

				</div>

				<div class="form-actions">
					<@shiro.hasPermission name="C5M1F1">
						<button type="button" id="addBtn" class="btn btn-success">新增</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M1F15">
						<button type="button" id="colgateAddBtn" class="btn btn-success">新增问卷</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M1F2">
						<button type="button" dataId="17" class="btn btn-success exportBut">问卷信息导出</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M1F3">
						<button type="button" dataId="14" class="btn btn-success exportBut">Main Questionnaire</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M1F4">
						<button type="button" dataId="15" class="btn btn-success exportBut">Secondary Display Questionnaire</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M1F5">
						<button type="button" dataId="16" class="btn btn-success exportBut">Supplementary Questionnaire</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C5M1F11">
						<button type="button" dataId="20" class="btn btn-success exportBut">问卷数据导出</button>
					</@shiro.hasPermission>
					 
						<button type="button" id="downloadImage" class="btn btn-success">照片下载</button>
	 
					 
				 
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn" />
				</div>
				
			</form>
		</div>


		<div class="widget-content nopadding">
			<table class="table table-bordered data-table">
				<thead>
					<tr>
						<th>问卷编号</th>
						<th>门店编号</th>
						<th>门店名称</th>
						<th>城市</th>
						<@shiro.lacksPermission name="C5M1F13">
						<th>访问员</th>
						</@shiro.lacksPermission>
						<th>首次提交时间</th>
						<th>最后修改时间</th>
						<th>操作</th>
					</tr>
				</thead>

				<#list surveys as s>
				<tbody>
					<tr>
						<td>${s.surveyNo}</td>
						<td>${s.storeNo}</td>
						<td>${s.storeName}</td>
						<td>${s.city}</td>
						<@shiro.lacksPermission name="C5M1F13">
						<td>${s.visitor}</td>
						</@shiro.lacksPermission>
						<td><#if s.submitTime??>${s.submitTime?datetime}</#if></td>
						<td><#if s.lastUpdateTime??>${s.lastUpdateTime?datetime}</#if></td>
						<td class="table_img" style="text-align: center;" >
							<@shiro.hasPermission name="C5M1F6">
								<a surveyId="${s.surveyId}" href="#" feedbackId="${s.feedbackId}" class="edit_img_link">
									修改
								</a>
							</@shiro.hasPermission>
							<a surveyId="${s.surveyId}" href="#" feedbackId="${s.feedbackId}" class="show_img_link">
								查看
							</a>
							<@shiro.hasPermission name="C5M1F10">
								<a surveyId="${s.surveyId}" href="#" feedbackId="${s.feedbackId}" storeId = ${s.storeId} class="export_img_link">
									导出
								</a>
							</@shiro.hasPermission>
							
							<@shiro.hasPermission name="C5M1F9">
								<a surveyId="${s.surveyId}" href="#" feedbackId="${s.feedbackId}" class="delete_img_link">
									删除
								</a>
							</@shiro.hasPermission>
							
						</td>
					</tr>
				</tbody>
				</#list>

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
	var addDialog = null;
	$(document).ready(function() {
		jQuery("#addBtn").click(function(){
			var url = "${contextPath}/survey/showAddSurveyFeedback";
		    layer.open({
			    type: 2,
			    title: '问卷采集',
			    closeBtn: 1,
			    area: ['500px', '420px'],
			    content: url
			});
		});
		
		jQuery("#colgateAddBtn").click(function(){
			var url = "${contextPath}/survey/showAddColgateSurveyFeedback";
			layer.open({
			    type: 2,
			    title: '问卷采集',
			    closeBtn: 1,
			    area: ['500px', '420px'],
			    content: url
			});
		});
		
		jQuery("#downloadImage").click(function(){ 
		
		 	    var url="${contextPath}/survey/showColgatPictureout";
		     	$.post(url, {}, function(str){
				layer.open({
					    type: 1,
					    title:'照片下载',
					    closeBtn: 1, //不显示关闭按钮
					    shadeClose: true, //开启遮罩关闭
					    area: ['400px', '260px'],
					    content: str,
					    move:false
					});
				});
		 
		})

		$(".exportBut").click(function(){
			var dataId = $(this).attr("dataId");
			var clientId = $("#clientId").val();
			if(clientId==15 && dataId !=17){
				var url = "${contextPath}/survey/showExportCyclePage?dataId="+dataId;
			    layer.open({
				    type: 2,
				    title: '选择问卷导出周期',
				    closeBtn: 1,
				    area: ['400px', '130px'],
				    content: url
				});
			}else if(clientId==15 && dataId ==17){
				layer.confirm("确认导出", function (index) {
					var feedbackDate = $("#feedbackDate").val();
					var year = feedbackDate.substring(0,4);
					var month = feedbackDate.substring(4,6);
					var start = year + '-' + month + '-01';
					var day = new Date(year,month,0);
					var end = year + '-' + month + '-' + day.getDate();//获取当月最后一天日期
					$("#arg_start_date").val(start);
					$("#arg_end_date").val(end);
					
					var url ="${contextPath}/export/execute/"+dataId;
					jQuery('#searchForm').attr("action",url);
					jQuery('#searchForm').submit();
					jQuery('#searchForm').attr("action","${contextPath}/survey/query");
					layer.close(index);
				});
			}else{
				layer.confirm("确认导出", function (index) {
					var url ="${contextPath}/export/execute/"+dataId;
					jQuery('#searchForm').attr("action",url);
					jQuery('#searchForm').submit();
					jQuery('#searchForm').attr("action","${contextPath}/survey/query");
					layer.close(index);
				});
			}
		})
		
	
		 
		
		$(".exportImageBut").click(function(){
			layer.confirm("确认导出", function (index) {
				var url ="${contextPath}/image/downloadImage/";
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/survey/query");
				layer.close(index);
			});
		})
		
		$(".edit_img_link").click(function(){
			var surveyId = $(this).attr("surveyId");
			var feedbackId = $(this).attr("feedbackId");

			var url = "${contextPath}/survey/showSurveyMain/"+surveyId+"?feedbackId="+feedbackId;
			document.location.href=url;
			var navObject = jQuery("#breadcrumb",parent.document);
			jQuery(navObject).append("<a class='linkPage active' href='"+url+"' target='main'>添加问卷</a>");
		})


		$(".show_img_link").click(function(){
			var surveyId = $(this).attr("surveyId");
			var feedbackId = $(this).attr("feedbackId");

			var url = "${contextPath}/survey/showSurveyMain/"+surveyId+"?feedbackId="+feedbackId+"&show=1";
			document.location.href=url;
			var navObject = jQuery("#breadcrumb",parent.document);
			jQuery(navObject).append("<a class='linkPage active' href='"+url+"' target='main'>问卷详情</a>");
		})
		
		$(".delete_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");
			layer.confirm("确认删除该问卷",function(index){
			    layer.close(index);
				$.ajax({
					type : "post",
					url : "${contextPath}/survey/deleteSurvey/"+feedbackId,
					dataType : "json",
					cache : false,
					success : function(data) {
						if(data.code=='success'){
							layer.alert("问卷删除成功",function(){
								window.location.reload();
							})
						}else{
							layer.alert(data.message);
						}
					},
					error : function(data) {
						layer.alert("问卷删除异常");
					}
				})
			});
		})
		
		
		$(".export_img_link").click(function(){
			var surveyId = $(this).attr("surveyId");
			var feedbackId = $(this).attr("feedbackId");
			var storeId = $(this).attr("storeId");
			var url ="${contextPath}/survey/exportDetail/"+surveyId;
			$("#feedbackId").val(feedbackId);
			$("#storeId").val(storeId);
			layer.confirm("确认导出", function (index) {
				jQuery('#searchForm').attr("action",url);
				jQuery('#searchForm').submit();
				jQuery('#searchForm').attr("action","${contextPath}/survey/query");
				$("#feedbackId").val("");
				$("#storeId").val("");
				layer.close(index);
			});
		})
		
		
	});
</script>