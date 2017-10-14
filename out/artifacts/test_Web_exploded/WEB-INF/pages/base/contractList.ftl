<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>会员招募</title>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css" media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<style type="text/css">

.overlay { 
	position: fixed; 
	top: 0px; 
	left: 0px; 
	width: 100%; 
	height: 100%; 
	z-index: 1999; 
	background-color: #000; 
	opacity: 0.2; 
	filter: alpha(opacity=20); 
} 

.moveBar { 
	position: absolute; 
	float:left;
	width: 800px; 
	height: 600px; 
	background-color:#ffffff; 
	border: solid 1px #000; 
	//left:20%; 
	//top:15%;
	margin: 0 auto; 
	text-align:center;
	padding:2px; 
	z-index: 2999; 
} 
</style>
</head>
<body class="main_body">
		<#assign privCode="C2M14">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础数据管理</a>
					<a class="linkPage active" href="${contextPath}/contract/query">会员招募</a>
				</div>
		 	</div>
		<div class="boloc_moveBar" style="display:none" ><h2>更新中，请稍等……</h2></div> 
		<div>
		
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label " for="structureName">门店编号：</label>
							<div class="controls">
							  <input id="storeNo" type="text" class="input-medium" name="storeNo" value="${contractContent.storeNo!''}" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeNo">大区：</label>
							<div class="controls">
					  		  <input type="text" style="height:20px;" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
							  <input type="text" id="structureId" name="structureId" value="${contractContent.structureId}" style="display:none;">
							  <#assign structureFtlName="structureId">
							  <#include "/widgets/structure.ftl" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="city">城市：</label>
							<div class="controls">
							  <input type="text" id="city" name="city" class="input-medium" value="${contractContent.city!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="users">门店名称：</label>
							<div class="controls">
								<input id="storeName" name="storeName" value ="${contractContent.storeName!''}">
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="channelName">签订日期：</label>
							<div class="controls">
							  <input id="signDate" type="text" name="signDate" class="input-medium" <#if contractContent.signDate ??> value="${contractContent.signDate?string("yyyy-MM-dd")}"</#if> readonly="readonly" />
								<#assign dateFtlName="signDate">
					  			<#include "/widgets/date_select_one.ftl" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="distributorName">合作起始日期：</label>
							<div class="controls">
							  <input id="startDate" type="text" name="startDate" class="input-medium"<#if contractContent.startDate ??> value="${contractContent.startDate?string("yyyy-MM-dd")}"</#if> readonly="readonly" />
								<#assign dateFtlName="startDate">
					  			<#include "/widgets/date_select_one.ftl" />
							</div>
						</div>
						<div  class="fl">
						<label class="control-label fl" for="status">合作结束日期：</label>
						<div class="controls">
		                   <input type="text" class="input-medium" autocomplete="off" id="endDate" name="endDate" <#if contractContent.endDate ??> value="${contractContent.endDate?string("yyyy-MM-dd")}"</#if> />
								<#assign dateFtlName="endDate">
					  			<#include "/widgets/date_select_one.ftl" />
						</div>
					</div>
	              	</div>
					<div class="form-actions">
					<@shiro.hasPermission name="C2M14F1">
						<button type="button" id="importBtn" class="btn btn-primary">协议导入</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M14F2">
						<button type="button" id="importBtn2" class="btn btn-danger">核销导入</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M14F3">
						<button type="button" id="importBtn3" class="btn btn-danger">总数据导出</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M14F8">
						<button type="button" id="importBtn4" class="btn btn-danger">合同导出</button>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="C2M14F4">
						<button type="button" id="batchDel" class="btn btn-primary">一键删除</button>
					</@shiro.hasPermission>
					
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
				</form>
				<input type="hidden" id="hiddenDelContractId" name="hiddenDelContractId"/>
			</div>
			<div class="widget-content nopadding" style="max-height:700px;overflow:auto;width:auto;">
					<table class="table table-bordered data-table" style="max-height:700px;overflow:auto;" id="c_list">
						<tr> 
						<th class="fill_td"><input type="checkbox" id="delCheckAll" name="delCheckAll">&nbsp;删除</th>
	    				<th scope="col" colspan="1"></th> 
	    				<th class="fill_td" >地区</th> 
	    				<th class="fill_td" >城市</th> 
	    				<th class="fill_td">店铺名称</th> 
	    				<th class="fill_td" >连锁名称</th>  
	    				<th class="fill_td" >门店编号</th> 
	    				<th class="fill_td">合作协议</th> 
	    				<th class="fill_td" scope="col" colspan="2">合作期间</th>
	    				<th class="fill_td" scope="col" rowspan="1">协议照片</th>
	    				<th class="fill_td" scope="col" rowspan="1">操作</th>
	    				</tr> 
	    				<tr><th class="fill_td"></th>
							<th class="fill_td">Store Code</th>
							<th class="fill_td">Zone</th>
			
							<th class="fill_td">City</th>
							<th class="fill_td">StoreName</th>
						
							<th class="fill_td">Chain</th>
							<th class="fill_td">编号</th>
							<th class="fill_td">签订日期</th>
							<th class="fill_td">合作起始日期</th>
							<th class="fill_td">合作结束日期</th>
							<th class="fill_td"></th>
							<th class="fill_td"></th>
						</tr>
						
						<#list pageParam.items as s>
						<tr>
							<td class="fill_td"><input name="delChkItem" type="checkbox" class="delCheckboxContract" value="${s.contractId!''}" /></td>
							<td class="fill_td">${s.storeNo}</td>
							<td class="fill_td">${s.structureName}</td>
							<td class="fill_td">${s.city}</td>
							<td class="fill_td">${s.storeName}</td>
							
							<td class="fill_td">${s.chainName}</td>
							<td class="fill_td">${s.storeNo}</td>
							<td class="fill_td">${s.signDate?string("yyyy年MM月dd日")}</td>
							<td class="fill_td">${s.startDate?string("yyyy年MM月dd日")}</td>
							<td class="fill_td">${s.endDate?string("yyyy年MM月dd日")}</td>
							<td class="fill_td">
							<#if s.contractPic!="">
								<#list s.contractPics as a>
								<img class="img_lightBox" href="/uploadfiles/${s.clientId}/web/${a}" src="/uploadfiles/${s.clientId}/web/thumbnail/s_${a}" />
								</#list>
							</#if>
							</td>
							<td class="fill_td">
								<a class="return" href="javascript:void(0);" contractId="${s.contractId}" >回执</a>
								<@shiro.hasPermission name="C2M14F5">
								<a class="delete" href="javascript:void(0);" contractId="${s.contractId}" >删除</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C2M14F6">
								<a class="upload" href="javascript:void(0);" contractId="${s.contractId}" >上传协议</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C2M14F7">
								<a class="edit" href="javascript:void(0);" contractId="${s.contractId}" >修改</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C2M14F9">
								<a class="exportPdf" href="javascript:void(0);" contractId="${s.contractId}">合同下载</a>
								</@shiro.hasPermission>
							</td>
						</tr>
						</#list>
					</table>
					<#if pageParam.items?exists> 
						<div class="paging" > 
						   ${pageParam.getPagination()}
						</div> 
					</#if>
			</div>
		</div>
		</div>
	<#include "/footer.ftl" />
</body>
</html>
<script type="text/javascript">
$(function(){
	//修改
	$("a.edit").bind("click",function(){
		var contractId =$(this).attr("contractId");
		var url = "${contextPath}/contract/showEditContract/"+contractId;
		window.location.href=url;
	});
	//删除
	$("a.delete").bind("click",function(){
		var contractId =$(this).attr("contractId");
	   layer.confirm("确认删除吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/contract/deleteContract/"+contractId,
				type : "get",
				dataType:'JSON',
				success : function(result) {
				layer.alert()
				
					 confirmAndRefresh(result);
				}
			});
	   });
		   return false;
	});
	
	$(".img_lightBox").lightBox();
	
})
 	function confirmAndRefresh(result){
		if (result.code == "success") {
			window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				layer.alert(result.message);
			}});
		}
	}
 	
// 	$("a.upload").bind("click",function(){
// 		var contractId=$(this).attr("contractId");    
//		var url = "${contextPath}/contract/uploadContract?contractId="+contractId;
//		window.location.href=url;
//	});
 	
 	//回执
 	$("a.return").bind("click",function(){
 		var contractId=$(this).attr("contractId");    
		var url = "${contextPath}/wyethContractDetail/query?contractId="+contractId;
		window.location.href=url;
 	});
	//协议导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/contract/showImportDialog";
		 layer.open({
			    type: 2,
			    title: '协议导入',
			    closeBtn: 1,
			    area: ['650px', '347px'],
			    content: url
			});
		//importDialog = new xDialog(url, {}, {title:"协议导入",width:650,height:330 });
		//return true;
	});
		//核销导入窗口
	$("#importBtn2").bind("click",function(){
		var url = "${contextPath}/contract/showImportDialog2";
		   layer.open({
			    type: 2,
			    title: '核销导入',
			    closeBtn: 1,
			    area: ['650px', '347px'],
			    content: url
			});
		//importDialog = new xDialog(url, {}, {title:"核销导入",width:650,height:330 });
		//return true;
	});
    
	//上传协议
	$("a.upload").bind("click",function(){
		var contractId =$(this).attr("contractId");
		
		var url = "${contextPath}/contract/showUploadContract/"+contractId;
		    layer.open({
			    type: 2,
			    title: '协议上传',
			    closeBtn: 1,
			    area: ['650px', '357px'],
			    content: url
			});
	})
	

	
	//导出
	$("#importBtn3").bind("click",function(){
		var url = "${contextPath}/contract/export";
		layer.confirm("确认导出", function (index) {
			jQuery('#searchForm').attr("action",url);
			jQuery('#searchForm').submit();
			jQuery('#searchForm').attr("action","${contextPath}/contract/query");
			layer.close(index);
		});
	});

	//导出合同
	$("#importBtn4").bind("click",function(){
		var url = "${contextPath}/contract/exportHT";
		layer.confirm("确认导出", function (index) {
			jQuery('#searchForm').attr("action",url);
			jQuery('#searchForm').submit();
			jQuery('#searchForm').attr("action","${contextPath}/contract/query");
			layer.close(index);
		});
	});
	
	//导出pdf
	$("a.exportPdf").bind("click",function(){
		var contractId =$(this).attr("contractId");
		var url = "${contextPath}/contract/exportPDF/"+contractId;
		layer.confirm("确认导出", function (index) {
			window.location.href=url;
			layer.close(index);
		});
	});
	
	
//一键删除全选              
var delArray = $("#hiddenDelContractId").val().split(',');
var delContractIds = $("#hiddenDelContractId").val();

$("#delCheckAll").bind("click",function(){
	//取消所有人员的时候再次得到里面的值
	if($('#hiddenDelContractId').val()==''){
		delArray = new Array();	
	}else{
		delArray = $('#hiddenDelContractId').val().split(',');
	}
	if($(this).attr('checked')){
		 $("[name = delChkItem]:checkbox").each(function(){
			 if(!$(this).prop("checked")){
	 	  		$(this).attr("checked",true);
		  		delArray.push($(this).val());			
			 }
		 });
		delContractIds = delArray.join(",");
	}else{
		 $("[name = delChkItem]:checkbox").each(function(){
			  $(this).attr("checked",false);
			  delArray.splice($.inArray($(this).val(),delArray),1);
 		}); 
 			delContractIds = delArray.join(","); 
	}
	$("#hiddenDelContractId").val(delContractIds);
});

$("[name = delChkItem]:checkbox").bind("click",function(){
		if($('#hiddenDelContractId').val()==''){
			delArray = new Array();	
		}else{
			delArray = $('#hiddenDelContractId').val().split(',');
		}
		if($(this).attr('checked')){
			$(this).attr("checked",true);
			delArray.push($(this).val());
			delContractIds = delArray.join(",");
		}else{
			$(this).attr("checked",false);
			delArray.splice($.inArray($(this).val(),delArray),1);
			delContractIds = delArray.join(",");
		}
	$("#hiddenDelContractId").val(delContractIds);
	if($('.delCheckboxContract:checked').length == $('.delCheckboxContract').length){
		$('#delCheckAll').attr("checked",true);
	}else{
		$('#delCheckAll').attr("checked",false);
	}
});

$("#batchDel").bind("click",function(){
	var contractIds = $("#hiddenDelContractId").val();
	layer.confirm("确认一键删除？", function(){
		$.post("${contextPath}/contract/batchDelContract",{contractIds:contractIds},function(data){
			if(data.code=="success"){
				layer.alert(data.message,function(){
					window.location.href="${contextPath}/contract/query";
				});
			}	
		});
	});
});
</script>