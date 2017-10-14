<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-ui-timepicker-addon.min.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.min.js"></script> 
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-zh-CN.js"></script>
<title>学习资料维护</title>
</head>
<body>
<div>
       <div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				<div class="control-group">
					<div nowrap="true" class="fl">
							<label class="control-label " for="title">标题：</label>
							<div class="controls">
								<input type="text" name="title" class="input-medium" value="${title}">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="startDate">开始时间：</label>
							<div class="controls">
								<input type="text" id="startDate" name="startDate" value="${startDate}" class="input-medium">
							</div>
					</div>
					<div nowrap="true" class="fl">
							<label class="control-label " for="endDate">结束时间：</label>
							<div class="controls">
								<input type="text" id="endDate" name="endDate" value="${endDate}" class="input-medium">
							</div>
					</div>
					</div>
				<div class="form-actions">
	              <@shiro.hasPermission name="C2M2F1">
		          <button type="button" id="new_btn" class="btn btn-success">新增</button>
	              </@shiro.hasPermission>
		          <input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
	            </div>
		<input type="hidden" name="page" value="${page}">
			</form>
		</div>
    
     <div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
					    <th>标题</th>
					    <th>内容</th>
					    <th>资料展示照片</th>
					    <th>阅读数</th>
					    <th>点赞数</th>
					    <th>开始时间</th>
					    <th>结束时间</th>
					    <th>创建时间</th>
					    <th>最后更新时间</th>
						<th width="110px">操作</th>
					</tr>
					<tbody>
						<#list pageParam.items as knowledge>
						<tr>
						    <td>${(knowledge.title)!''}</td>
						       <td title="${(knowledge.content)!''}">
								<#if knowledge.content ?? && knowledge.content?length gt 10>
										${knowledge.content?substring(0,10)}...
									<#else>
										${knowledge.content!''}
									</#if>
								</td>
							<td>
							<#if (knowledge.avatar)?? >
							   <#list (knowledge.avatar)?split(",") as x> 
							   <#if (x) !=null>
							   <img class="img_lightBox" href="${contextPath}/uploadfiles/${knowledge.clientId}/web/${x}" src="${contextPath}/uploadfiles/${knowledge.clientId}/web/thumbnail/s_${x}" />
							   </#if>
							   </#list>
							</#if>
							</td>
							<td>${(knowledge.readTimes)!''}</td>
							<td>${(knowledge.likeTimes)!''}</td>
							<td><#if (knowledge.startDate)??>${(knowledge.startDate)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							<td><#if (knowledge.endDate)??>${(knowledge.endDate)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							<td><#if (knowledge.createTime)??>${(knowledge.createTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
							<td><#if (knowledge.lastUpdateTime)??>${(knowledge.lastUpdateTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
								<td>
									<@shiro.hasPermission name="C2M2F2">
									<a class="editKnowledge" href="javascript:void(0);" dataId="${knowledge.knowledgeId!''}">编辑</a>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M2F3">
									<a class="deleteKnowledge" href="javascript:void(0);"  data="${knowledge.knowledgeId!''}">删除</a>
									</@shiro.hasPermission>
									<!--<a class="addKnowledgeattachment" href="javascript:void(0);"  data="${knowledge.knowledgeId!''}">新增附件</a>-->
									<a class="checkKnowledge" href="javascript:void(0);"  dataId="${knowledge.knowledgeId!''}" dataName="${knowledge.title!''}">查看</a>
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
</body>
</html>

<script>
var editDialog,checkDialog;
 $(function(){
 
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
 //新增学习资料
    $("#new_btn").bind("click",function(){
       var url = "${contextPath}/knowledge/showAddKnowledge";
		    layer.open({
			    type: 2,
			    title: '学习资料新增',
			    closeBtn: 1,
			    area: ['700px', '560px'],
			    content: url
			});
    });
    
    
    //删除学习资料
    $("a.deleteKnowledge").bind("click",function(){
       var knowledgeIdDate=$(this).attr("data");
       layer.confirm("确定要删除吗？",function(){
         var resultcode;
         $.ajax({
           url:"${contextPath}/knowledge/showDeleteKnowledge/"+knowledgeIdDate,
           type:"post",
           async: false,
           dataType:'Json',
           success:function(result){
             confirmAndRefresh(result);
           }
         });
       });
       return false;
    });
    
    function confirmAndRefresh(result){
		if (result.code == "success") {
			window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//$.alert(result.message);
			}});
		}
  }
	
	
	  //编辑学习资料
    $("a.editKnowledge").bind("click",function(){
       var KnowledgeIdData=$(this).attr("dataId");
       var url = "${contextPath}/knowledge/showEditKnowledge/"+KnowledgeIdData;
	  layer.open({
			    type: 2,
			    title: '学习资料编辑',
			    closeBtn: 1,
			    area: ['700px', '560px'],
			    content: url
		});
    });
    //查看学习资料
     $("a.checkKnowledge").bind("click",function(){
	    var knowledgeId=$(this).attr("dataId");
	    var knowledgeTitle=$(this).attr("dataName");
		var url = "${contextPath}/knowledge/showCheckKnowledge/"+knowledgeId;
		checkDialog = new xDialog(url,{},{title:"查看学习资料---"+knowledgeTitle ,width:680,height:400}); 
		return false;	
	});	
	//新增学习资料附件
	 $("a.addKnowledgeattachment").bind("click",function(){
       var KnowledgeIdData=$(this).attr("data");
       var url = "${contextPath}/knowledgeattachmentController/showAddKnowledgeAttachment/"+KnowledgeIdData;
		editDialog = new xDialog(url,{},{title:"添加学习资料附件",width:900,height:340}); 
		return false;	
    });
	
 });
</script>