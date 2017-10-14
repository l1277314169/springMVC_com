<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>新增拜访任务</TITLE>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript">
	var popTypeDialog;
	var roleDialog;
	var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
			error : function() {  
                 alert('亲，渠道加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
        view: {
            dblClickExpand: false,
            selectedMulti: true, //是否允许多选
            txtSelectedEnable: false //是否允许选中节点的文字
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
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#channelSel").attr("value", treeNode.name);
		$("#channelId").attr("value", treeNode.id);
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
	};
	
    function showMenu() {
        var structureObj = $("#channelSel");
        var cityOffset = $("#channelSel").offset();
        $("#menuContent").css({ left: cityOffset.left + "px", top: cityOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    };
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    };     
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    };
    
    $(document).ready(function () {
        $.fn.zTree.init($("#treeDemo"), setting);
		//选择终端类型参数
	    $("#popTypeParameter").click(function(){
	    	var popValue = $("#popType").val();
	    	if(popValue ==null || popValue == ""){
	    		layer.alert("请先选择终端类型!");
	    		$("#popType").focus();
	    		return false;
	    	};
	    	var popText = $("#popType").find("option:selected").text(); 
			var url = "${contextPath}/visit/showPopTypeParameter?key="+popValue;
			//popTypeDialog = new xDialog(url, {}, {title:"选择关联"+popText,iframe:true,width:900,height:700});
			//return false;	
				 layer.open({
				    type: 2,
				    title:'选择关联'+popText,
				    closeBtn: 1,
				    area: ['900px', '700px'],
				    content: url
				});
		});
		
		$("#roleTypeParameter").click(function(){
			var url = "${contextPath}/clientRoles/showRoleParameter";
			//roleDialog = new xDialog(url, {}, {title:"选择关联角色",iframe:true,width:900,height:700});
			//return false;
			
			layer.open({
				    type: 2,
				    title:'选择关联角色',
				    closeBtn: 1,
				    area: ['900px', '700px'],
				    content: url
				});
		});
		
		//选择终端类型。
		$("#popType").change(function(){
	    	var popValue = $("#popType").val();
	    	var storeIds = $("#storeParameterDatas").val();
	    	var distributorIds = $("#distributorParameterDatas").val();
	    	//如果是门店，展示终端渠道和分组的选择。
	    	if(popValue == "1"){
	    		$("#storeChannelSpan").css("display","block");
	    		$("#storeGroupIdSpan").css("display","block");
	    		var array = storeIds.split(',');
				$("#parameterCount").html(array.length-1);
	    	} else {
	    		$("#storeChannelSpan").css("display","none");
	    		$("#storeGroupIdSpan").css("display","none");
	    		var array = distributorIds.split(',');
				$("#parameterCount").html(array.length-1);
	    	}
		});
		
		var popValue = $("#popType").val();
    	var storeIds = $("#storeParameterDatas").val();
    	var distributorIds = $("#distributorParameterDatas").val();
    	//如果是门店，展示终端渠道和分组的选择。
    	if(popValue == "1"){
    		$("#storeChannelSpan").css("display","block");
    		$("#storeGroupIdSpan").css("display","block");
    		var array = storeIds.split(',');
			$("#parameterCount").html(array.length-1);
    	} else {
    		$("#storeChannelSpan").css("display","none");
    		$("#storeGroupIdSpan").css("display","none");
    		var array = distributorIds.split(',');
			$("#parameterCount").html(array.length-1);
    	}
		
		//开始定位。
		$("#visitingStartLocating").click(function(){
			if($('#visitingStartLocating').attr('checked')) {
				$("#startLocating").val('1');
			} else {
				$("#startLocating").val('0');
			}
		});
		//结束定位。
		$("#visitingEndLocating").click(function(){
			if($('#visitingEndLocating').attr('checked')) {
				$("#endLocating").val('1');
			} else {
				$("#endLocating").val('0');
			}
		});
		//开始拍照。
		$("#visitingStartPic").click(function(){
			if($('#visitingStartPic').attr('checked')) {
				$("#startPic").val('1');
			} else {
				$("#startPic").val('0');
			}
		});
		//结束拍照。
		$("#visitingEndPic").click(function(){
			if($('#visitingEndPic').attr('checked')) {
				$("#endPic").val('1');
			} else {
				$("#endPic").val('0');
			}
		});
    });
</script>
</HEAD>
      <#assign privCode="C1M2">
		 <#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/visit/showEditVisitingTask">修改拜访任务</a>
				</div>
		 	</div>


<body class="main_body">
	<form id="dataForm" action="${contextPath}/visit/addVisitingTask" method="post" class="form-horizontal">
		<input type="hidden" id="visitingTaskId" name="visitingTaskId" value="${visitingTaskVO.visitingTaskId}" />
		<input type="hidden" id="clientId" name="clientId" value="${clientId}" />
		<input type="hidden" id="channelId" name="visitingTaskVO.channelId" value="${visitingTaskVO.channelId}" />
		<input type="hidden" id="startLocating" name="startLocating" value="${visitingTaskVO.startLocating}" />
		<input type="hidden" id="endLocating" name="endLocating" value="${visitingTaskVO.endLocating}" />
		<input type="hidden" id="startPic" name="startPic" value="${visitingTaskVO.startPic}" />
		<input type="hidden" id="endPic" name="endPic" value="${visitingTaskVO.endPic}" />
		<input type="hidden" id="storeParameterDatas" name="storeParameterDatas" value="${visitingTaskVO.storeParameterDatas}"/>
		<input type="hidden" id="distributorParameterDatas" name="distributorParameterDatas" value="${visitingTaskVO.distributorParameterDatas}"/>
		<input type="hidden" id="rolesParameterDatas" name="rolesParameterDatas" value="${visitingTaskVO.rolesParameterDatas}"/>
		<div class="widget-content no_bottom_border">
			<div class="control-group">
				<div class="fl margin-top-10">
					<label class="control-label" for="visitingTaskName"><i class="cc1">*</i>任务名称：</label>
					<div class="controls ifloat">
						<input type="text" name="visitingTaskName" class="input-medium" value="${visitingTaskVO.visitingTaskName}" required=true>
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="startDatePicker"><i class="cc1">*</i>开始时间：</label>
					<div class="controls ifloat">
						<input type="text" name="startDate" class="input-medium" value="<#if visitingTaskVO.startDate??>${visitingTaskVO.startDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" id="startDatePicker"required=true>
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="endDatePicker">结束时间：</label>
					<div class="controls ifloat">
						<input type="text" name="endDate" value="<#if visitingTaskVO.endDate??>${visitingTaskVO.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" c-lass="input-medium" id="endDatePicker" >
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="clientPositionId"><i class="cc1">*</i>任务类型：</label>
					<div class="controls ifloat">
						<select id="visitingTaskType" name="visitingTaskType" class="input-medium" required=true>
				        	<option value="">--请选择--</option>
				        	<#list taskType as option>
				        		<option value="${option.key}" <#if visitingTaskVO.visitingTaskType==option.key>selected="selected"</#if>>${option.getCnName()}</option>
				        	</#list>		
			        	</select>
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="clientPositionId">关联职位：</label>
					<div class="controls ifloat">
						<select id="clientPositionId" name="clientPositionId" class="input-medium" required=true>
				        	<option value="">--请选择--</option>
				        	<#list cpList as option>
				        		<option value="${option.clientPositionId!''}" <#if visitingTaskVO.clientPositionId==option.clientPositionId>selected="selected"</#if>>${option.positionName!''}</option>
				        	</#list>		
			        	</select>
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="visitingStartLocating">开始定位：</label>
					<div class="controls ifloat">
						<input type="checkbox" class="input-medium" id="startLocating" <#if visitingTaskVO.startLocating==1>checked="true"</#if> value="${visitingTaskVO.startLocating}">
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="visitingEndLocating">结束定位：</label>
					<div class="controls ifloat">
						<input type="checkbox" class="input-medium" id="endLocating" <#if visitingTaskVO.endLocating==1>checked="true"</#if> value="${visitingTaskVO.endLocating}">
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="visitingStartPic">开始拍照：</label>
					<div class="controls ifloat">
						<input type="checkbox" class="input-medium" id="startPic" <#if visitingTaskVO.startPic==1>checked="true"</#if> value="${visitingTaskVO.startpic}">
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="visitingEndPic">结束拍照：</label>
					<div class="controls ifloat">
						<input type="checkbox" class="input-medium" id="endPic" <#if visitingTaskVO.endPic==1>checked="true"</#if> value="${visitingTaskVO.endPic}">
					</div>
				</div>
				<div class="fl margin-top-10">
					<label class="control-label" for="popType"><i class="cc1">*</i>终端类型：</label>
					<div class="controls ifloat">
						<select id="popType" name="popType" class="input-medium" required=true>
						<option value="">--请选择--</option>
							<#list popType as option>
				        		<option value="${option.key}" <#if visitingTaskVO.popType == option.key>selected="selected"</#if>>${option.getCnName()}</option>
				        	</#list>
			        	</select>
					</div>
				</div>
				<div id="storeChannelSpan" class="fl margin-top-10 " style="display:none;">
					<label class="control-label" for="channelSel">终端渠道：</label>
					<div class="controls ifloat">
							  <input type="text" id="clientStructureId_channel" name="clientStructureName_channel"  class="input-medium" readonly onclick="showMenu_channel();" />
							  <input type="hidden" id="arg_channel_ids" name="channelId" value="${channelId!''}" />
							  <#assign channelFTL="arg_channel_ids">
							  <#include "/widgets/channel_widget.ftl" />
							</div>
				</div>
				<div id="storeGroupIdSpan" class="fl margin-top-10" style="display:none;">
					<label class="control-label" for="popGroupId">门店分组：</label>
					<div class="controls ifloat">
						<select id="popGroupId" name="popGroupId" class="input-medium" >
						<option value="">--请选择--</option>
							<#list sgList as option>
				        		<option value="${option.storeGroupId!''}" <#if visitingTaskVO.storeGroupId == option.storeGroupId>selected="selected"</#if>>${option.groupName!''}</option>
				        	</#list>
			        	</select>
					</div>
				</div>
				<div class="fl margin-top-10">
					<button id="popTypeParameter" class="btn btn-success margin-left-18px" type="button">已关联<i id="parameterCount" style="color:red">0</i>个对象</span> </button>
				</div>
				<div class="fl margin-top-10">
					<button id="roleTypeParameter" class="btn btn-success margin-left-18px" type="button">已关联<i id="parameterRoleCount" style="color:red">0</i>个角色</span> </button>
				</div>
				<div class="clear"></div>
				<div class="tab_container margin-top-18 no_bottom_border">
					<div class="tabs">					
		 				<ul id="stepTbas" class="margin-left-1" style="float:left">
			 				<#list visitingTaskVO.visitingTaskSteps as visitingTaskStep>
								<li id="step${visitingTaskStep_index+1}" class="<#if (visitingTaskVO.visitingTaskSteps?size==(visitingTaskStep_index+1))>tab_current<#else>tab_normal</#if>" step="${visitingTaskStep_index+1}"><span class="tab_num_label">${visitingTaskStep_index+1}</span><span class="tab_close" alt="close" title="close"></span><b>步骤</b></li>
							</#list>	
						</ul>			 														 					
						<ul id="addStep" class="margin-left-1" style="margin-left:5px">
							<li id="addStepLi" class="tab_add"><a id="" class="add_task_step">增加步骤</a></li>
						</ul>
					</div>
					<div id="tabs-body" class="tabs-body">
					</div>
				</div>
			</div>
			<div class="align_center form-actions">
				<button class="btn btn-danger" id="backButton" type="button">返回</button>
				<button type="button" id="addVistingTask" class="btn btn-success margin-left-18px" type="button">保存</button>
			</div>
		</div>	
		<#list visitingTaskVO.visitingTaskSteps as visitingTaskStep>
			<#list visitingTaskStep.visitingParameters as visitingParameter>
				<#assign controlTypeKey="${visitingParameter.controlType}"/>
				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].visitingTaskStepId" value="${visitingParameter.visitingTaskStepId}" />
				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].visitingParameterId" value="${visitingParameter.visitingParameterId}" />
				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].parameterName" value="${visitingParameter.parameterName}" />
				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].controlType" value="${visitingParameter.controlType}" data="${controlTypeMap[controlTypeKey]}"/>
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].controlName" value="${visitingParameter.controlName}" data=""/>
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].sequence" value="${visitingParameter.sequence}" />
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].defaultValue" value="${visitingParameter.defaultValue}" />
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].maxValue" value="${visitingParameter.maxValue}" />
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].minValue" value="${visitingParameter.minValue}" />
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].scale" value="${visitingParameter.scale}" />
 				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].isMustDo" value="${visitingParameter.isMustDo}" />
	 			<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].isRemember" value="${visitingParameter.isRemember}" />
				<input type="hidden" class="parameter_${visitingParameter_index}" name="visitingTaskSteps[${visitingTaskStep_index}].visitingParameters[${visitingParameter_index}].isEditable" value="${visitingParameter.isEditable}" />
			</#list>			
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].visitingTaskStepId' name='visitingTaskSteps[${visitingTaskStep_index}].visitingTaskStepId' value='${visitingTaskStep.visitingTaskStepId}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].visitingTaskId' name='visitingTaskSteps[${visitingTaskStep_index}].visitingTaskId' value='${visitingTaskStep.visitingTaskId}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].stepName' name='visitingTaskSteps[${visitingTaskStep_index}].stepName' value='${visitingTaskStep.stepName}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].sortBy' name='visitingTaskSteps[${visitingTaskStep_index}].sortBy' value='${visitingTaskStep.sortBy}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].selectType' name='visitingTaskSteps[${visitingTaskStep_index}].selectType' value='${visitingTaskStep.selectType}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].isMustDo' name='visitingTaskSteps[${visitingTaskStep_index}].isMustDo' value='${visitingTaskStep.isMustDo}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].isOnePage' name='visitingTaskSteps[${visitingTaskStep_index}].isOnePage' value='${visitingTaskStep.isOnePage}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].stepType' name='visitingTaskSteps[${visitingTaskStep_index}].stepType' value='${visitingTaskStep.stepType}' />
			<input type='hidden' id='visitingTaskSteps[${visitingTaskStep_index}].taskStepParameterDatas' name='visitingTaskSteps[${visitingTaskStep_index}].taskStepParameterDatas' value='${visitingTaskStep.taskStepParameterDatas}' />
		</#list>           	
	</form>
		<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo" class="ztree" style="margin-top: 0; width: 160px;">
			</ul>
		</div>
	</div>
	     <#include "/footer.ftl" />
</body>
<script type="text/javascript" language="javascript">
$(document).ready(function () {

	//初始化关联数
	var pobj = $("#storeParameterDatas");
	var pobjCount = $("#parameterCount");
	var storeIds = pobj.val();
	var array = storeIds.split(',');
	pobjCount.html(array.length-1);
	
	var roleObj = $("#rolesParameterDatas");
	var parameterRoleCount = $("#parameterRoleCount");
	var roleIds = roleObj.val();
	var array = roleIds.split(',');
	parameterRoleCount.html(array.length-1);
	
	
	
	var visitingTaskStepsCount = '${visitingTaskVO.visitingTaskSteps?size-1}'
	for(var i=0 ; i <= visitingTaskStepsCount ; i++){
		loadingTaskStep(i);
	}
	
    $("#backButton").bind("click",function(){
    	$('#breadcrumb a:last-child',window.top.document).remove();
		window.location.href = "${contextPath}/visit/query";
	});
    
	$("#addVistingTask").bind("click",function(){
		//验证
		if(!$("#dataForm").validate({
			errorPlacement: function(error, element) {}
		}).form()){
			return;
		}
		$.ajax({
			url : "${contextPath}/visit/updateVisitingTask",
			type : "post",
			dataType:"json",
			async: false,
			data : $("#dataForm").serialize(),
			success : function(result) {
			   if(result.code=="success"){
					layer.alert(result.message,function(){
		   				window.location.href = "${contextPath}/visit/query"
		   			});
				}else {
					layer.alert(result.message);
				}
		   	}
		});
	});
	
	$("#startDatePicker").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#endDatePicker").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		},
		
	});
	
	$("#endDatePicker").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#startDatePicker").datepicker("option","maxDate",dateText);
			$(this).focus();
			$(this).blur();
		},
	});
	
	$("#stepTbas li").bind("click", function () {
        var index = $(this).index();
        var step = index+1;
        //如果是当前tab则不做任何动作，反之异步请求新的页面参数。
        if($(this).attr("class") == "tab_current") {
        	return false;
        } else {        	
            $(this).parent().children("li").attr("class", "tab_normal");//将所有选项置为未选中
            $(this).attr("class", "tab_current"); //设置当前选中项为选中样式
            loadingTaskStep(index);
		}
    });
    
    $("#addStepLi").bind("click",function(){
    	//获取最后一个步骤的数值
    	var lastStep = $("#stepTbas").find(">li:last").attr("step");
    	if(lastStep == null || lastStep =="") {
    		lastStep = 0
    	}
    	var step = parseInt(lastStep)+1;
        var msg = "<li class='tab_normal' step='"+step+"'><span id='step"+step+"' class='tab_num_label'>"+ step+"</span><span class='tab_close' alt='close' title='close'></span><b>步骤</b></li>";
        $("#stepTbas").append(msg);
        var thisStep = $("#stepTbas").find(">li:last");
        $("#stepTbas").children("li").attr("class", "tab_normal");//将所有选项置为未选中
        thisStep.attr("class", "tab_current");//设置新增的tab为选中状态
        //绑定onclick方法
        thisStep.click(function(){
        	var index = $(this).attr("step")-1;
	        var step = index+1;
	        //alert(step);
	        //如果是当前tab则不做任何动作，反之异步请求新的页面参数。
	        if($(this).attr("class") == "tab_current") {
	        	return false;
	        } else {
	            $(this).parent().children("li").attr("class", "tab_normal");//将所有选项置为未选中
	            $(this).attr("class", "tab_current"); //设置当前选中项为选中样式
	            loadingTaskStep(index);
			}
		});
		//绑定onclick方法
        $("span.tab_close").unbind("click").click(function(e){
        	var li = $(this).parent();
        	//var index = li.index();
        	var index = li.attr("step")-1;
        	var str = "visitingTaskSteps["+index+"]";
        	//alert(str);
        	layer.confirm("删除步骤会删除该步骤下所有数据，确定删除？", function (index) {
				if(li.attr("class") == "tab_current") {
					var selectLi = $("#stepTbas li:eq(0)");
					selectLi.attr("class", "tab_current");
					var index = parseInt(selectLi.attr("step"))-1;
					loadingTaskStep(index);
					parent.layer.closeAll();
				}
		     	li.remove();
		     	//删除对应的dom元素
		     	$("input[name ^='"+str+"']").remove();
        	 });
		});
		
        loadingTaskStep(step-1);
    });
    $("span.tab_close").unbind("click").click(function(e){
        	var li = $(this).parent();
        	//var index = li.index();
        	var index = li.attr("step")-1;
        	var str = "visitingTaskSteps["+index+"]";
        	var strVisitingTaskStepId = "visitingTaskSteps["+index+"].visitingTaskStepId"; 
        	//alert(str);
        	 layer.confirm("删除步骤会删除该步骤下所有数据，确定删除？", function () {
				if(li.attr("class") == "tab_current") {
					var selectLi = $("#stepTbas li:eq(0)");
					selectLi.attr("class", "tab_current");
					var index = parseInt(selectLi.attr("step"))-1;
					loadingTaskStep(index);
					parent.layer.closeAll();
				}
		     	li.remove();
		     	//删除对应的dom元素
		     	var visitingTaskStepIdObj = $("input[name ^='"+strVisitingTaskStepId+"'");
	     		$("input[name ^='"+str+"']").remove();	 
	     		$("#dataForm").append(visitingTaskStepIdObj);    		
		     	$("#dataForm").append("<input type='hidden' name='visitingTaskSteps["+index+"].isDelete' value='1' />");	     		     	
        	 });
        	
		});
     loadingTaskStep(step-1);
    $(".tab_close").bind("click", function () {
	     var li = $(this).parent();
	     li.remove();
    });
});

function loadingTaskStep(step){
	var i = step;
	if(i == null || i ==""){
		i= 0;
	};
	$.post("/visit/showEditVisitingTaskStep/"+i,
		{
			step:step
		},
		function(data){
			$(".tabs-body").html(data);
		}
	);
}
</script>
</HTML>