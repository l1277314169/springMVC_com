<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审批</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${contextPath}/css/channel-mobile.css" />
<script src="${contextPath}/js/zepto.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$('.moveBar').hide();
	$('#auditId').hide();
	$('#NoauditId').hide();
	$.each($('.title'), function(index, name) {
		if(!$(this).hasClass('open')) 
		$(this).addClass('open');
		$(this).find('i').addClass('arrow_up');
		$(this).find('i').removeClass('arrow_down');
	});
	$('.title > span').click(function(e)
	{
		$(this).parents('li').toggleClass('open');
		$(this).children('i').toggleClass('arrow_down');
		$(this).children('i').toggleClass('arrow_up');
	});
	
$('#Audit').click(function(){
	$('#AuditMoveBar').show();
	$('.overlay').css('display','block');
});

$('#NoAudit').click(function(){
	$('#NoAuditMoveBar').show();
	$('.overlay').css('display','block');
});

$('#cancel').click(function(){
$('.overlay').css('display','none');
	$('#NoAuditMoveBar').hide();
});

$('#AuditIdCancel').click(function(){
$('.overlay').css('display','none');
	$('#AuditMoveBar').hide();
});

var clientId = $('#clientId').val();
$('#confrim').click(function(){
		var clientUserId=$('#clientUserId').val();
		$.ajax({
				url : "${contextPath}/mobile/callplanningApprove/failAudit/"+clientUserId+"/"+clientId,
				type : "get",
				async: false,
				dataType:'json',
				success : function(result) {
					if(result.code == 'success'){
					$('.overlay').css('display','none');
						$('.actions').hide();
						$('#NoAuditMoveBar').hide();
						$('#NoauditId').show();
					}else{
						alert(result.message);
					}
				}
			});
})
$('#AuditIdconfrim').click(function(){
		var clientUserId=$('#clientUserId').val();
		$.ajax({
				url : "${contextPath}/mobile/callplanningApprove/successAudit/"+clientUserId+"/"+clientId,
				type : "get",
				async: false,
				dataType:'json',
				success : function(result) {
					if(result.code == 'success'){
					$('.overlay').css('display','none');
						$('.actions').hide();
						$('#AuditMoveBar').hide();
						$('#auditId').show();
					}else{
						alert(result.message);
					}
				}
			});
	})
});
</script>
</head>
<body>
	<input type="hidden" id="clientId" value="${clientId}">
	<div data-mask="overlay" class="overlay" style="display: none;"></div>
	<div class="moveBar" id="NoAuditMoveBar">  
        <div id="banner" class="content">提示信息</div>  
        <div class="content">是否确定审批不通过？ </div>  
        <button id="confrim" class="btn btn-success ">确定</button>
        <button id="cancel" class="btn btn-danger">取消</button>
    </div>  
    <div class="moveBar" id="AuditMoveBar">  
        <div id="banner" class="content">提示信息</div>  
        <div class="content">是否确定审批通过？ </div>  
        <button id="AuditIdconfrim" class="btn btn-success">确定</button>
        <button id="AuditIdCancel" class="btn btn-danger">取消</button>
    </div> 
	<input type="hidden"  id="clientUserId" name="clientUserId" value="${clientUserId}">

	<div class="actions" id="auditId">
		<lable>已审批(审批通过)</lable>
	</div>
	<div class="actions" id="NoauditId">
		<lable>已审批(审批不通过)</lable>
	</div>
<div>
  <div class="actions" id="foAudit">
		<input type="button" id="NoAudit" value="审批不通过" class="btn btn_red">
		<input type="button" id="Audit" value="审批通过" class="btn btn_green">
  </div>
  <ul>
  <#if databo>
  <#list dateList?keys as key>
    <li class="title open"><span>${key}<i class="arrow_up"></i></span>
      <ul>
      	<#list dateList[key] as pendingSchedulelist>
	      	<#if pendingSchedulelist.workType == 1>
	      		<li><div>${pendingSchedulelist.sob}</div></li>
	      	<#else>
	        	<li><div>${pendingSchedulelist.soa}</div><div class="div_addr">${pendingSchedulelist.sob}</div></li>
	      	</#if>
       </#list>
      </ul>
	</li>
 </#list>
 <#else>
<script language="javascript" type="text/javascript">
$('#foAudit').hide();
</script>
 	<div class="actions"> 
		<lable>没有审批的数据</lable>
	</div>
 </#if>
  </ul>
</div>
</body>
</html>