<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>会员招募</title>
</head>
<body>
		<div>
			<div class="widget-content nopadding" style="max-height:500px;overflow:auto;width:auto;margin-top:20px;">
					<table class="table table-bordered data-table" id="c_list">
						<tr> 
		    				<th scope="col" rowspan="2">storeCode</th> 
		    				<th scope="col" rowspan="2">StoreName</th> 
		    				<th scope="col" rowspan="2">月份</th>
		    				<th colspan="2">礼品发放数量</th> 
		    				<th colspan="2">联招会员数量</th> 
		    				<th rowspan="2">核销金额</th>
		    				<th class="fill_td" scope="col" rowspan="2">操作</th>
	    				</tr> 
	    				<tr>
							<th class="fill_td">预计</th>
							<th class="fill_td">实际</th>
							<th class="fill_td">预计</th>
							<th class="fill_td">实际</th>
						</tr>
						
						<#list vos as s>
						<tr>
							<td class="fill_td">${s.storeCode}</td>
							<td class="fill_td">${s.storeName}</td>
							<td class="fill_td">${s.monthId}</td>
							<td class="fill_td">${s.numOfGift}</td>
							<td class="fill_td">${s.realNumOfGift}</td>
							<td class="fill_td">${s.numOfMember}</td>
							<td class="fill_td">${s.realNumOfMember}</td>
							<td class="fill_td">${s.totalAmount}</td>
							<td class="fill_td">
								
								<!--
								<a class="confirm" href="javascript:void(0);" feedbackId="${s.feedbackId}" >确认</a>
								<a class="appeal" href="javascript:void(0);" feedbackId="${s.feedbackId}" >申述</a>
								-->
								
							</td>
						</tr>
						</#list>
						<tr>
							<td colspan="9"  style="text-align:center;" >
								<button data-dismiss="dialog" id="returnBut" type="button" onclick="javascript:document.location.href='${contextPath}/contract/query'" class="btn btn-danger">返回</button>
							</td>
						</tr>
						
					</table>
			</div>
		</div>
		</div>
</body>
</html>
<script type="text/javascript">
 			//删除
	$("a.confirm").bind("click",function(){
		var feedbackId=$(this).attr("feedbackId");
	    $.confirm("确认核销吗？", function () {
	    	var resultCode; 
			$.ajax({
				url : "${contextPath}/contractFeedback/confirmContractFeedback/"+feedbackId,
				type : "get",
				async: false,
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
			window.location.reload();
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
	//$(document).ready(function() {
	
	//$("a.checked").bind("click",function(){
	 //		var contractId=$(this).attr("contractId");    
		//	var url = "${contextPath}/ctTask/showEditCtTask?ctTaskId="+ctTaskId+"&popId="+popId+"&isInCycel="+isInCycel;
		//	var popName = $(this).attr("popName");
			//$('#breadcrumb',window.top.document).append('<a>'+popName+'</a>');
			//window.location.href=url;
	 	//}
	 	
		//协议导入窗口
		//$("#importBtn").bind("click",function(){
		//	var url = "${contextPath}/contract/showImportDialog";
		//	importDialog = new xDialog(url, {}, {title:"协议导入",width:650,height:330 });
		//	return true;
		//});
			//核销导入窗口
		//$("#importBtn2").bind("click",function(){
		//	var url = "${contextPath}/contract/showImportDialog2";
		//	importDialog = new xDialog(url, {}, {title:"核销导入",width:650,height:330 });
		//	return true;
		//});
		
	//});

	
	
</script>