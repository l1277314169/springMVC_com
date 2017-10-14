<html>
<head>
<title>工作任务查看</title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<link rel="stylesheet" href="${contextPath}/css/lightbox.css" type="text/css"  media="screen">
<script type="text/javascript" src="${contextPath}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D93a31278160293ed962f7a4c40f1fa5"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
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
	width: 550px; 
	height: 350px; 
	background-color:#ffffff; 
	border: solid 1px #000; 
	left:20%; 
	top:15%; 
	padding:2px; 
	z-index: 2999; 
}
</style> 
</head>
<body>
<input type="hidden" id="finishDate" value="${finishDate}">
<input type="hidden" id="clientUserId" value="${clientUser.clientUserId}">
<div style="margin-top:10;margin-left:18;">
	姓名：${clientUser.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部门：${clientStructure.structureName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;执行日期：${finishDate}
</div>
		<div class="div_widget_narrow div_scrollbar">
			<ul id="leftUl">
				<#if wrTaskDatas?? && wrTaskDatas?size gt 0 >
					<#list wrTaskDatas as wrTaskData>
						<#if callPlanning_index == 0>
							<li class="popClass_cur" data-id="${wrTaskData.wrTaskDataId!''}" data-type="">
								<span>${wrTaskData.wrTaskName!''}</span>
								<span><#if wrTaskData.workplaceType==1>门店<#elseif wrTaskData.workplaceType==2>奥办<#elseif wrTaskData.workplaceType==3>现场</#if></span>
								<span><#if wrTaskData.workplaceType==1><br/>${wrTaskData.workPlace}</#if></span>
								<br/><span>${(wrTaskData.startDate)?string("HH:mm:ss")}-${(wrTaskData.endDate)?string("HH:mm:ss")}</span>
							</li>
						<#else>
							<li class="popClass_nor" data-id="${wrTaskData.wrTaskDataId!''}" data-type="">
								<span>${wrTaskData.wrTaskName!''}</span>
								<span><#if wrTaskData.workplaceType==1>门店<#elseif wrTaskData.workplaceType==2>奥办<#elseif wrTaskData.workplaceType==3>现场</#if></span>
								<span><#if wrTaskData.workplaceType==1><br/>${wrTaskData.workPlace}</#if></span>
								<br/><span>${(wrTaskData.startDate)?string("HH:mm:ss")}-${(wrTaskData.endDate)?string("HH:mm:ss")}</span>
							</li>
						</#if>
					</#list>
				<#else>
					未获取到数据
				</#if>
			</ul>
		</div>
		
		<div id="div_step_detail" class="div_step_detail">
		</div>
		</body>
</html>
<script type="text/javascript" language="javascript">
$(document).ready(function () {
	//获取第一个对象的DOM数据
	var wrTaskDataId = $("#leftUl li").eq(0).attr("data-id");  
	$("#leftUl li").eq(0).attr("class","popClass_cur");
	var clientUserId = $("#clientUserId").val();	 
	var finishDate = $("#finishDate").val();
	if(wrTaskDataId != "undefined"){
		loadingWrTaskDetail(wrTaskDataId,clientUserId,finishDate);
	} else {
		$(".div_step_detail").html("没有数据");
	}
	function loadingWrTaskDetail(wrTaskDataId,clientUserId,finishDate){
		$.post("/wrTaskData/showWrTaskDetail",
			{
				wrTaskDataId:wrTaskDataId,
				clientUserId:clientUserId,
				finishDate:finishDate
			},
			function(data){
				$(".div_step_detail").html(data);
			}
		);
	};
	
	$("#leftUl > li").bind('click',function(){
		if($(this).attr("class") == "popClass_cur") {
        	return false;
        } else {
        	 $(this).parent().children("li").attr("class", "popClass_nor");//将所有选项置为未选中
            $(this).attr("class", "popClass_cur"); //设置当前选中项为选中样式
			var wrTaskDataId = $(this).attr("data-id"); 
			var clientUserId = $("#clientUserId").val();
			var finishDate = $("#finishDate").val();
			loadingWrTaskDetail(wrTaskDataId,clientUserId,finishDate);
        }
	});
	
	//编辑门店坐标
	$("a.editStoreLocation").bind("click",function(){
	    var storeId=$(this).attr("dataId");
	    var storeName=$(this).attr("dataName");
		var url = "${contextPath}/store/showEditStoreLocation/"+storeId;
		// editStoreLocationDialog = new xDialog(url,{},{title:"ding--"+storeName,iframe:true,width:800,height:600}); 
		 layer.open({
			    type: 2,
			    title: 'ding--'+storeName,
			    closeBtn: 1,
			    iframe:true,
			    area: ['800px', '600px'],
			    content: url
			});
		return false;	
	});
});
</script>