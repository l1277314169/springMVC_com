<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>核销查看</title>
<link rel="stylesheet" href="${contextPath}/css/jquery.lightbox-0.5.css" type="text/css" media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox-0.5.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/channelPlus-divPopup.css"/>
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
<div class="boloc_moveBar" style="display:none" ><h2>更新中，请稍等……</h2></div> 
			<#assign privCode="C2M15">
		<#include "/base.ftl" />
		
		<div id="content">
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础数据管理</a>
					<a class="linkPage active" href="${contextPath}/contract/checkedQuery">核销查看</a>
				</div>
		 	</div>
		
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div class="fl">
							<label class="control-label " for="structureName">门店编号：</label>
							<div class="controls">
							  <input id="storeCode" type="text" class="input-medium" name="storeCode" value="${contractFeedbackVo.storeCode!''}" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="structureName">门店名称：</label>
							<div class="controls">
							  <input id="storeNo" type="text" class="input-medium" name="storeName" value="${contractFeedbackVo.storeName!''}" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="structureName">账户：</label>
							<div class="controls">
							  <input id="storeNo" type="text" class="input-medium" name="bankAcct" value="${contractFeedbackVo.bankAcct!''}" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label " for="structureName">开户行：</label>
							<div class="controls">
							  <input id="storeNo" type="text" class="input-medium" name="bankName" value="${contractFeedbackVo.bankName!''}" />
							</div>
						</div>
						
						<div class="fl">
							<label class="control-label fl" for="storeNo">大区：</label>
							<div class="controls">
							  <input type="text" style="height:20px;" id="clientStructureId_structure" name="clientStructureName_structure" readonly class="input-medium" onclick="showMenu_structure()">
							  <input type="text" id="structureId" name="structureId" value="${contractFeedbackVo.structureId}" style="display:none;">
							  <#assign structureFtlName="structureId">
							  <#include "/widgets/structure.ftl" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="city">城市：</label>
							<div class="controls">
							  <input type="text" id="city" name="city" class="input-medium" value="${contractFeedbackVo.city!''}">
				
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="channelName">月份：</label>
							<div class="controls">
							  <input id="monthId" type="hidden" name="monthId" class="input-medium" value="${contractFeedbackVo.monthId}" />
							  <#assign month="monthId">
					  			<#include "/widgets/month_widget2.ftl"/>
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="channelName">审核状态：</label>
							<div class="controls">
								<select name="status">
									<option value="">--请选择--</option>
									<option value="0" <#if contractFeedbackVo.status == 0> selected=true </#if>>待确认</option>
									<option value="1" <#if contractFeedbackVo.status == 1> selected=true </#if>>已确认</option>
									<option value="2" <#if contractFeedbackVo.status == 2> selected=true </#if>>已申诉</option>
									<option value="3" <#if contractFeedbackVo.status == 3> selected=true </#if>>核销完成</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-actions">
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
						<@shiro.hasPermission name="C2M15F5">
							<button type="button" id="batchCheked" class="btn btn-danger">一键核销</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M15F6">
							<button type="button" id="batchDel" class="btn btn-primary">一键删除</button>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="C2M15F9">
						  <button type="button" id="importBtn5" class="btn btn-danger">核销导出</button>
						  </@shiro.hasPermission>
					</div>
				</form>
				<input type="hidden" id="hiddenFeedBackId" name="hiddenFeedBackId"/>
				<input type="hidden" id="hiddenDelFeedBackId" name="hiddenDelFeedBackId"/>
			</div>
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;">
					<table class="table table-bordered data-table" id="c_list">
						<tr> 
							<th class="fill_td" ><input type="checkbox" id="checkAll" name="checkAll"> 核销 </th>
							<th class="fill_td" ><input type="checkbox" id="delCheckAll" name="delCheckAll"> 删除 </th>
		    				<th class="fill_td">store Code</th> 
		    				<th class="fill_td">store Name</th> 
		    				<th class="fill_td">Zone</th> 
		    				<th class="fill_td">City</th>
		    				<th class="fill_td">是否能够提供发票</th>  
		    				<th class="fill_td">发票类型</th> 
		    				<th class="fill_td">账号类型</th> 
		    				<th class="fill_td">开户行</th>
		    				<th class="fill_td">账号</th>
		    				<th class="fill_td">收款人</th>
		    				<th class="fill_td">时间</th>
		    				<th class="fill_td">礼品发放数量</th>
		    				<th class="fill_td">联招会员数量</th>
		    				<th class="fill_td">电访合格率</th>
		    				<th class="fill_td">不合格会员1</th>
		    				<th class="fill_td">不合格会员2</th>
		    				<th class="fill_td">合格核销数</th>
		    				<th class="fill_td">最终服务费金额</th>
		    				<th class="fill_td">发票照片</th>
							<th class="fill_td">申诉原因</th>
		    				<th class="fill_td">状态</th>
		    				<th class="fill_td">操作</th>
	    				</tr> 
						
						<#list pageParam.items as s>
						<tr>
							<td><input name="chkItem" type="checkbox" <#if s.status!=1 || s.invoicePic==''>disabled="disabled"</#if> class="checkboxContract" value="${s.feedbackId!''}" /></td>
							<td><input name="delChkItem" type="checkbox" <#if s.status==3>disabled="disabled"</#if> class="delCheckboxContract" value="${s.feedbackId!''}" /></td>
							<td class="fill_td">${s.storeCode}</td>
							<td class="fill_td">${s.storeName}</td>
							<td class="fill_td">${s.structureName}</td>
							<td class="fill_td">${s.city}</td>
							<td class="fill_td">${s.hasInvoice}</td>
							<td class="fill_td">${s.invoiceType}</td> 
							<td class="fill_td">${s.acctType}</td>
							<td class="fill_td">${s.bankName}</td>
							<td class="fill_td">${s.bankAcct}</td>
							<td class="fill_td">${s.acctHolder}</td>
							<td class="fill_td">${s.monthId}</td>
							<td class="fill_td">${s.numOfGift}</td>
							<td class="fill_td">${s.numOfMember}</td>
							<td class="fill_td">${s.rateOfReview}</td>
							<td class="fill_td">${s.num1OfUnqualified}</td>
							<td class="fill_td">${s.num2OfUnqualified}</td>
							<td class="fill_td">${s.numOfVerification}</td>
							<td class="fill_td">${(s.numOfVerification*100)+(s.numOfGift*30)}</td>
							<td class="fill_td">
							<#list s.invoicePics as a>
								<img class="img_lightBox" href="/uploadfiles/${s.clientId}/web/${a}" src="/uploadfiles/${s.clientId}/web/thumbnail/s_${a}" />
							</#list>
							</td>
							<td class="fill_td">${s.reason}</td>
							<td class="fill_td"><#if s.status==0>待确认<#elseif s.status==1>已确认<#elseif s.status==2>已申诉<#elseif s.status==3>核销完成</#if></td>
							<td class="fill_td">
								<#if s.status==0>
									<@shiro.hasPermission name="C2M15F1">
									<#if s.invoicePic!=''>
										<a class="checked" href="javascript:void(0);" feedbackId="${s.feedbackId}" status="1">确认</a>
										</#if>
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M15F2">
										<a class="appeal" href="javascript:void(0);" feedbackId="${s.feedbackId}" status="2">申诉</a>
									</@shiro.hasPermission>
								<#elseif s.status==1>
									<@shiro.hasPermission name="C2M15F3">
								
										<a class="checked" href="javascript:void(0);" feedbackId="${s.feedbackId}" status="3">核销完成</a>
										
									</@shiro.hasPermission>
									<@shiro.hasPermission name="C2M15F7">
										<a class="checked" href="javascript:void(0);" feedbackId="${s.feedbackId}" status="0">取消</a>
									</@shiro.hasPermission>
								</#if>
								<@shiro.hasPermission name="C2M15F4">
									<a class="uploadImg" href="javascript:void(0);" feedbackId="${s.feedbackId}" >上传照片</a>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="C2M15F8">
								<a class="edit" href="javascript:void(0);" feedbackId="${s.feedbackId}">修改</a>
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
		<div id="light" class="white_content moveBar" style="width:500;height:250">
	    	<div id="banner" class="content">申诉</div>  
			     <table class="table_white_bg">
		            <tbody>
		            	<tr>
		            	    <td align="left"> 申诉原因：</td>
		            	</tr>
		           		<tr>
	           				<td colspan="3" class="td_control">
		                    	<textarea maxlength="300" id="complainContent" name="complainContent" placeholder="不超过200个字"></textarea>
		                    </td>
		           		</tr>
		            </tbody>
		        </table>
		    	<button id="confrim" class="btn btn-success" feedbackId="">确定</button>
	        	<button id="cancel" class="btn btn-danger">取消</button>
		    </div>
		    <div id="fade" class="black_overlay"></div>
	  <#include "/footer.ftl" />  
</body>
</html>
<script type="text/javascript">

$(function(){
    
    //核销导出窗口
      $("#importBtn5").bind("click",function(){
		var url = "${contextPath}/contract/exportHXHS";
		layer.confirm("确认导出", function (index) {
			jQuery('#searchForm').attr("action",url);
			jQuery('#searchForm').submit();
			jQuery('#searchForm').attr("action","${contextPath}/contract/query");
			layer.close(index);
		});
	});

	$(".img_lightBox").lightBox();
	
});
$("a.checked").bind("click",function(){
	var feedbackId=$(this).attr("feedbackId");    
	var status = $(this).attr("status");    
	var url = "${contextPath}/contract/contractChecked?feedbackId="+feedbackId+"&status="+status;
	var remindMsg = "";
		if(status=="1"){
			remindMsg = "是否要确认";
		}else if(status=="3"){
			remindMsg = "是否要完成核销";
		}else if(status=="2"){
			remindMsg = "是否要取消";
		}else if(status=="0"){
			remindMsg = "是否要取消";
		}
	layer.confirm(remindMsg, function(){
		$.post(url,function(data){
			var msg = "";
			if(status=="1"){
				msg = "已确认";
			}else if(status=="3"){
				msg = "核销完成";
			}else if(status=="2"){
				msg = "已经取消";
			}else if(status=="0"){
				msg = "已经取消";
			}
			if(data.code=="success"){
				layer.alert(msg,function(){
					window.location.href="${contextPath}/contract/checkedQuery";
				});
			}
		});
	});
});
$("a.uploadImg").bind("click",function(){
	var feedbackId=$(this).attr("feedbackId"); 
	var url = "${contextPath}/contract/showUploadInvoice/"+feedbackId;
		    layer.open({
			    type: 2,
			    title: '照片上传',
			    closeBtn: 1,
			    area: ['650px', '357px'],
			    content: url
			});
})
$("a.edit").bind("click",function(){
	var feedbackId=$(this).attr("feedbackId"); 
	var url = "${contextPath}/contract/showEditFeedback/"+feedbackId;
		    layer.open({
			    type: 2,
			    title: '核销修改',
			    closeBtn: 1,
			    area: ['720px', '400px'],
			    content: url
			});
})

$("a.appeal").bind("click",function(){
	var feedbackId = $(this).attr("feedbackId"); 
 	$("#confrim").attr("feedbackId",feedbackId);
	$("#light").show();
	$("#fade").show();
})

$("#cancel").bind("click",function(){
	 $("#light").hide();
	 $("#fade").hide();
});

$("#confrim").bind("click",function(){
	var feedbackId = $("#confrim").attr("feedbackId");
	if($("#complainContent").val().length > 200){
		layer.alert("不能超过200个字数");
		return;
	}
	$.post("${contextPath}/contract/appeal/"+feedbackId+"?reason="+$("#complainContent").val(),function(data){
			if(data.code=="success"){
				layer.alert(data.message,function(){
					window.location.href="${contextPath}/contract/checkedQuery";
				});
			}
		});
	$("#complainContent").attr("value","");
	$("#light").hide();
	$("#fade").hide();
});

$("#batchCheked").bind("click",function(){
	var feedBackIds = $("#hiddenFeedBackId").val();
	layer.confirm("确认一键核销？", function(){
		$.post("${contextPath}/contract/batchCheked",{feedBackIds:feedBackIds},function(data){
			if(data.code=="success"){
				layer.alert(data.message,function(){
					window.location.href="${contextPath}/contract/checkedQuery";
				});
			}	
		});
	});
});

//全选              
	var array = $("#hiddenFeedBackId").val().split(',');
	var feedBackIds = $("#hiddenFeedBackId").val();
	
	$("#checkAll").bind("click",function(){
		//取消所有人员的时候再次得到里面的值
		if($('#hiddenFeedBackId').val()==''){
			array = new Array();	
		}else{
			array = $('#hiddenFeedBackId').val().split(',');
		}
		if($(this).attr('checked')){
			 $("[name = chkItem]:checkbox").each(function(){
				 if(!$(this).prop("checked")){
				 	  if($(this).attr("disabled")!="disabled"){
				 	  		$(this).attr("checked",true);
	   				  		array.push($(this).val());			
				 	  }
				 }
			 });
			feedBackIds = array.join(",");
		}else{
			 $("[name = chkItem]:checkbox").each(function(){
				  $(this).attr("checked",false);
				  array.splice($.inArray($(this).val(),array),1);
	 		}); 
	 			feedBackIds = array.join(","); 
		}
		$("#hiddenFeedBackId").val(feedBackIds);
	});
	
	$("[name = chkItem]:checkbox").bind("click",function(){
			if($('#hiddenFeedBackId').val()==''){
				array = new Array();	
			}else{
				array = $('#hiddenFeedBackId').val().split(',');
			}
			if($(this).attr('checked')){
				$(this).attr("checked",true);
				array.push($(this).val());
				feedBackIds = array.join(",");
			}else{
				$(this).attr("checked",false);
				array.splice($.inArray($(this).val(),array),1);
				feedBackIds = array.join(",");
			}
		$("#hiddenFeedBackId").val(feedBackIds);
		if($('.checkboxContract:checked').length == $('.checkboxContract').length){
			$('#checkAll').attr("checked",true);
		}else{
			$('#checkAll').attr("checked",false);
		}
	});
	
	
	//一键删除全选              
	var delArray = $("#hiddenDelFeedBackId").val().split(',');
	var delFeedBackIds = $("#hiddenDelFeedBackId").val();
	
	$("#delCheckAll").bind("click",function(){
		//取消所有人员的时候再次得到里面的值
		if($('#hiddenDelFeedBackId').val()==''){
			delArray = new Array();	
		}else{
			delArray = $('#hiddenDelFeedBackId').val().split(',');
		}
		if($(this).attr('checked')){
			 $("[name = delChkItem]:checkbox").each(function(){
				 if(!$(this).prop("checked")){
				 	 if($(this).attr("disabled")!="disabled"){
				 	  	$(this).attr("checked",true);
			  			delArray.push($(this).val());		 		
				 	  }
				 }
			 });
			delFeedBackIds = delArray.join(",");
		}else{
			 $("[name = delChkItem]:checkbox").each(function(){
				  $(this).attr("checked",false);
				  delArray.splice($.inArray($(this).val(),delArray),1);
	 		}); 
	 			delFeedBackIds = delArray.join(","); 
		}
		$("#hiddenDelFeedBackId").val(delFeedBackIds);
	});
	
	$("[name = delChkItem]:checkbox").bind("click",function(){
			if($('#hiddenDelFeedBackId').val()==''){
				delArray = new Array();	
			}else{
				delArray = $('#hiddenDelFeedBackId').val().split(',');
			}
			if($(this).attr('checked')){
				$(this).attr("checked",true);
				delArray.push($(this).val());
				delFeedBackIds = delArray.join(",");
			}else{
				$(this).attr("checked",false);
				delArray.splice($.inArray($(this).val(),delArray),1);
				delFeedBackIds = delArray.join(",");
			}
		$("#hiddenDelFeedBackId").val(delFeedBackIds);
		if($('.delCheckboxContract:checked').length == $('.delCheckboxContract').length){
			$('#delCheckAll').attr("checked",true);
		}else{
			$('#delCheckAll').attr("checked",false);
		}
	});
	
$("#batchDel").bind("click",function(){
	var feedBackIds = $("#hiddenDelFeedBackId").val();
	layer.confirm("确认一键删除？", function(){
		$.post("${contextPath}/contract/batchDel",{feedBackIds:feedBackIds},function(data){
			if(data.code=="success"){
				layer.alert(data.message,function(){
					window.location.href="${contextPath}/contract/checkedQuery";
				});
			}	
		});
	});
});
</script>