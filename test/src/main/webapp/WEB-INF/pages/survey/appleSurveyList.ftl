<html>
<head>
  <title>问卷</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/css/channelPlus-divPopup.css"/>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
</head>

<body  class="main_body">
		<#assign privCode="C5M1">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/appleSurvey/query">问卷查看</a>
				</div>
		
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="" id="searchForm" novalidate="novalidate">
				<input type="hidden" id="arg_client_id" name="arg_client_id" value="${params.arg_client_id}" />
				<div class="control-group">
					<div nowrap="true" class="fl">
						<label class="control-label fl">城市：</label>
						<div class="controls">
						  <input type="text" id="city_ids" name="city_ids" value="${params.city_ids}" class="input-medium" />
						  <#assign cityIdFTL="city_ids">
						  <#include "/widgets/city_widget.ftl" />
						</div>
					</div>
					
					<div nowrap="true" class="fl">
						<label class="control-label fl">门店编号：</label>
						<div class="controls">
						  <input type="text" id="arg_store_no" name="arg_store_no" value="${params.arg_store_no}" class="input-medium" dataPosition=""/>
						</div>
					</div>
					
					<div nowrap="true" class="fl">
						<label class="control-label fl">轮次：</label>
						<div class="controls">
						  <input type="text" id="arg_store_type" name="arg_store_type" value="${params.arg_store_type}" class="input-medium" dataPosition=""/>
						</div>
					</div>
					
					<div nowrap="true" class="fl">
						<label class="control-label fl">门店名称：</label>
						<div class="controls">
						  <input type="text" id="arg_store_name" name="arg_store_name" value="${params.arg_store_name}" class="input-medium" dataPosition=""/>
						</div>
					</div>
					
					<div nowrap="true" class="fl">
						<label class="control-label fl">开始日期：</label>
						<div class="controls">
						  <input type="text" id="arg_start_date" name="arg_start_date" value="${params.arg_start_date}" class="input-medium" />
						</div>
					</div>
					
					<div nowrap="true" class="fl">
						<label class="control-label fl">结束日期：</label>
						<div class="controls">
						  <input type="text" id="arg_end_date" name="arg_end_date" value="${params.arg_end_date}" class="input-medium" />
						   <#assign startDate="arg_start_date">
						   <#assign endDate="arg_end_date">
						   <#assign spaceDate="30">
						   <#include "/widgets/date_select.ftl" />
						</div>
					</div>
					
				</div>

				<div class="form-actions">
					<@shiro.hasPermission name="C5M1F1">
						<button type="button" id="addBtn" class="btn btn-success">新增</button>
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
					
						<button type="button" id="downloadImage" class="btn btn-success">照片下载</button>
	
					 
					<input type="submit" value="查询" class="btn btn-info fr" id="search_btn" />
				</div>
			</form>
		</div>


		<div class="widget-content nopadding">
			<table class="table table-bordered data-table">
				<thead>
					<tr>
						<th>轮次</th>
						<th>门店编号</th>
						<th>门店名称</th>
						<th>执行城市</th>
						<th>门店地址</th>
						<th>审核状态</th>
						<th>操作</th>
					</tr>
				</thead>

				<#list surveys as s>
				<tbody>
					<tr>
						<td>${s.storeType}</td>
						<td>${s.storeNo}</td>
						<td title="${s.storeName!''}">
							<#if s.storeName?? && s.storeName?length gt 15>
								${s.storeName?substring(0,15)}...
							<#else>
								${s.storeName!''}
							</#if>
						</td>
						<td>${s.city}</td>
						<td title="${s.addr!''}">
							<#if s.addr?? && s.addr?length gt 15>
								${s.addr?substring(0,15)}...
							<#else>
								${s.addr!''}
							</#if>
						</td>
						<td>
							<#if s.status==0>
								未保存
							<#elseif s.status==1>
								已保存
							<#elseif s.status==2>
								已上传
							<#elseif s.status==3>
								审核通过
							<#elseif s.status==4>
								审核未通过
							</#if>
						</td>
						<td class="table_img" style="text-align: center;" >
							<#if s.status==0 && s.status!=3 && s.status!=2>
								<@shiro.hasPermission name="C5M1F6">
									<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="edit_img_link">修改</a>	
								</@shiro.hasPermission>
							<#elseif s.status ==4>
								<@shiro.hasPermission name="C5M1F6">
									<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="edit_img_link">修改</a>	
								</@shiro.hasPermission>
								<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="revocation_img_link">撤销审核</a>	
							<#elseif s.status==1>
								<@shiro.hasPermission name="C5M1F6">
									<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="edit_img_link">修改</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C5M1F7">
									<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="upload_img_link">上传</a>	
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C5M1F8">
									<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="check_img_link">审核</a>
								</@shiro.hasPermission>
							<#elseif s.status==2>
								<@shiro.hasPermission name="C5M1F8">
									<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="check_img_link">审核</a>
								</@shiro.hasPermission>
							<#elseif s.status==3>
								<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="revocation_img_link">撤销审核</a>
							</#if>
							<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="show_img_link">查看</a>
							<@shiro.hasPermission name="C5M1F9">
								<a surveyId="${s.surveyId}" href="#" storeId="${s.storeId}" feedbackId="${s.feedbackId}" class="delete_img_link">删除</a>
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
	<div id="light" class="white_content moveBar">
    	<div id="banner" class="content">审核</div>  
		     <table class="table_white_bg">
	            <tbody>
	            	<tr>
	            	    <td align="left"> 审核内容：</td>
	            	</tr>
	           		<tr>
           				<td colspan="3" class="td_control">
	                    	<textarea maxlength="300" id="checkContent" name="checkContent" placeholder="不超过200个字"></textarea>
	                    </td>
	           		</tr>
	            </tbody>
	        </table>
	    	<div><button data-dismiss="dialog" type="button" feedbackId="" class="refuseButton btn btn-danger">审核不通过</button><button type="button" feedbackId="" class="passButton btn btn-success margin-left-18px">审核通过</button></div>
	    </div>
	    <div id="fade" class="black_overlay"></div>
    </div> 
    	<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
	
		jQuery("#addBtn").click(function(){
			var url = "${contextPath}/appleSurvey/queryAppleSurveyStore";
			document.location.href=url;
		})	
		
		jQuery("#downloadImage").click(function(){ 
		 	    var url="${contextPath}/appleSurvey/showPictureout";
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
		
		$(".edit_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");
			var storeId = $(this).attr("storeId");
			var url = "${contextPath}/appleSurvey/showEditAppleSurveyMain/"+storeId+"/"+feedbackId;
			document.location.href=url;
			var navObject = jQuery("#breadcrumb",parent.document);
			jQuery(navObject).append("<a class='linkPage active' href='"+url+"' target='main'>添加问卷</a>");
		})
		
		$(".upload_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");
			var url = "${contextPath}/appleSurvey/uploadAppleSurvey?feedbackId="+feedbackId;
			$.post(url,function(data){
				if(data.code=="success"){
					layer.alert("上传成功",function(){
						document.location.href="${contextPath}/appleSurvey/query";
					});
				}
			});
		});
		
		$(".check_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");
			$("#light .refuseButton").attr("feedbackId",feedbackId);
			$("#light .passButton").attr("feedbackId",feedbackId);
			 $("#light").show();
			 $("#fade").show();
		});
		
		$(".refuseButton").click(function(){
			var aproveContent = $("#light textarea").val();
			var feedbackId = $(this).attr("feedbackId");
			var textAreaVal = $("#light textarea").val();
			if(textAreaVal.length>200){
				layer.alert("审核内容不能超过200个字符");
				return;
			}
			var url = "${contextPath}/appleSurvey/aproveAppleSurvey?feedbackId="+feedbackId+"&aproveContent="+aproveContent+"&status=4";
			$("#light").hide();
			$("#fade").hide();
			$.post(url,function(data){
				if(data.code=="success"){
					layer.alert("审核成功",function(){
						document.location.href="${contextPath}/appleSurvey/query";
					});
				}	
			});
		});
		
		$(".passButton").click(function(){
			var aproveContent = $("#light textarea").val();
			var feedbackId = $(this).attr("feedbackId");
			var textAreaVal = $("#light textarea").val();
			if(textAreaVal.length>200){
				layer.alert("审核内容不能超过200个字符");
				return;
			}
			var url = "${contextPath}/appleSurvey/aproveAppleSurvey?feedbackId="+feedbackId+"&aproveContent="+aproveContent+"&status=3";
			$("#light").hide();
			$("#fade").hide();
			$.post(url,function(data){
				if(data.code=="success"){
					layer.alert("审核成功",function(){
						document.location.href="${contextPath}/appleSurvey/query";
					});
				}	
			});
		});
		
		$(".delete_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");	
			var url = "${contextPath}/appleSurvey/deleteAppleSurvey?feedbackId="+feedbackId;
			layer.confirm("确认删除?",function(){
				$.post(url,function(data){
					if(data.code=="success"){
						layer.alert(data.message,function(){
							document.location.href="${contextPath}/appleSurvey/query";
						});
					}
				});
			});
		});
		
		$(".revocation_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");
			var url = "${contextPath}/appleSurvey/revocationAprove?feedbackId="+feedbackId;	
			layer.confirm("确认撤销审核?",function(){
				$.post(url,function(data){
					if(data.code=="success"){
						layer.alert("撤销成功",function(){
							document.location.href="${contextPath}/appleSurvey/query";
						});
					}
				});
			});
		});
		
		$(".show_img_link").click(function(){
			var feedbackId = $(this).attr("feedbackId");
			var storeId = $(this).attr("storeId");
			var url = "${contextPath}/appleSurvey/showEditAppleSurveyMain/"+storeId+"/"+feedbackId+"?show=1";
			document.location.href=url;
			var navObject = jQuery("#breadcrumb",parent.document);
			jQuery(navObject).append("<a class='linkPage active' href='"+url+"' target='main'>问卷详情</a>");
		})
		
	});
</script>