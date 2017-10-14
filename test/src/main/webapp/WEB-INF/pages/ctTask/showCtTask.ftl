
<div class="widget-content nopadding" style="height:80%">
<form id="searchForm" action="" class="form-horizontal" method="post">	 
	<div class="control-group">
		<div nowrap="true" class="fl">
			<label class="control-label fl" for="storeId">门店名称：</label>
			<div class="controls">
				<input type="text" id="storeId" name="storeId" class="input-medium" value="c75ca81a-6410-11e4-be7b-3c4a92ea8078" onChange="changeStore()" />
			</div>
		</div>
		<div nowrap="true" class="fl">
			<label  class="control-label fl" for="taskCycle"></label>
			<div class="controls">
				<input type="text" id="taskCycle" name="taskCycle"/>	
			</div>
		</div>
		<div nowrap="true" class="fl">
			<label  class="control-label fl" for="taskCycle"></label>
			<input value="查询" class="btn btn-info fr" id="search_btn">
		</div>	
	</div>
	<input type="hidden" id="ctTaskId" name="ctTaskId" value="${ctTask.ctTaskId}">	
</form>	
<div style="overflow:scroll;height:420px;">	
<form id="dataForm" action="" class="form-horizontal" method="post">	 
	<table class="table table-bordered data-table" id="c_list">
		<#if ctTask.taskType != '4'>
				<tr>
					<th class="fill_td">
						<#if ctTask.taskType == '1'>产品名称<#elseif ctTask.taskType == '2'>品牌名称<#elseif ctTask.taskType == '3'>品类相关
						<#elseif ctTask.taskType == '4'>问卷(无对象)<#elseif ctTask.taskType == '5'>人员名称<#elseif ctTask.taskType == '6'>终端对象
						<#elseif ctTask.taskType == '7'>产品系列<#elseif ctTask.taskType == '8'>产品分类<#elseif ctTask.taskType == '10'>物料名称
						</#if>
					</th class="fill_td">		      	 
		      		<#list ctTaskParameters as ctTaskParameter>
			      		<th class="fill_td">${ctTaskParameter.parameterName}</th>
		      		</#list>		      	
				</tr>
				<tbody>
					<#list ctTaskObjects as ctTaskObject>
						<tr >
							<td class="fill_td">${ctTaskObject.objectName}</td>
							<#list ctTaskParameters as ctTaskParameter>
			      				<td class="td_control_d fill_td">	   				
									<#if ctTaskParameter.controlType=='1'>
										<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>"/>
									<#elseif ctTaskParameter.controlType=='3'>					
										<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>intType</#if>"/>
									<#elseif ctTaskParameter.controlType=='4'>
										<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float"/>
									<#elseif ctTaskParameter.controlType=='2'>
										<textarea rows=2 style="height:50;" maxlength=500 ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" placeholder="不超过500个字"></textarea>								 
									<#elseif ctTaskParameter.controlType=='5'>
										<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" value=""  />
									<#elseif ctTaskParameter.controlType=='6'>
										<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" value=""  />
									<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
										<input type="checkbox" controlType="checkbox" class="input-medium loadCheckBoxOption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" value="1"/>									
									<#elseif  ctTaskParameter.controlType=='11'>
									<#else>
										<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" name="${ctTaskParameter.ctTaskParameterId}_${ctTaskObject.target1Id}" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>"/>
									</#if>								
			      				</td>
		      				</#list>
						</tr>
					</#list>			 
				</tbody>
		<#else>
			<tr>
				<th class="fill_td">参数名称</th>	
				<th class="fill_td">参数值</th>		
			</tr>
			<tbody>
				<#list ctTaskParameters as ctTaskParameter>
					<tr>
		      			<td class="fill_td">${ctTaskParameter.parameterName}</td>
		      			<td>
		      				<#if ctTaskParameter.controlType=='1'>
								<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>"/>
							<#elseif ctTaskParameter.controlType=='3'>					
								<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>intType</#if>"/>
							<#elseif ctTaskParameter.controlType=='4'>
								<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" scale="${ctTaskParameter.scale}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if> float"/>
							<#elseif ctTaskParameter.controlType=='2'>
								<textarea rows=2 style="height:50;" maxlength=500 ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" placeholder="不超过500个字"></textarea>								 
							<#elseif ctTaskParameter.controlType=='5'>
								<input type="text" controlType="select" class="input-medium loadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" value=""  />
							<#elseif ctTaskParameter.controlType=='6'>
								<input type="text" controlType="multipleSelect" class="input-medium multipleloadoption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" optionName="${ctTaskParameter.optionName}" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" id="${ctTaskParameter.ctTaskParameterId}_" autocomplete="off" loadoption="${ctTaskParameter.optionName}" name="${ctTaskParameter.ctTaskParameterId}_" value=""  />
							<#elseif ctTaskParameter.controlType=='7'||ctTaskParameter.controlType=='16'>
								<input type="checkbox" controlType="checkbox" class="input-medium loadCheckBoxOption ${ctTaskParameter.optionName} <#if ctTaskParameter.isMustDo==1>require</#if>" ctTaskParameterId="${ctTaskParameter.ctTaskParameterId}" optionName="${ctTaskParameter.optionName}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" value="1"/>									
							<#elseif  ctTaskParameter.controlType=='11'>
							<#else>
								<input type="text" controlType="text" ct_task_parameter_id="${ctTaskParameter.ctTaskParameterId}" maxValue="${ctTaskParameter.maxValue}" minValue="${ctTaskParameter.minValue}" id="${ctTaskParameter.ctTaskParameterId}_" name="${ctTaskParameter.ctTaskParameterId}_" class="input-medium <#if ctTaskParameter.isMustDo==1>require</#if> <#if ctTaskParameter.maxValue!=''>maxValueValidate</#if>"/>
							</#if>	
		      			</td>
		      		</tr>
	      		</#list>
			</tbody>
		</#if>
	</table>
</form>		
 </div>	
<div class="td_buttons" style="margin-left: 340;margin-buttom:-5px;">
	<button data-dismiss="dialog" type="button" class="btn btn-danger">取消</button>
</div>
</div>
<script>
$(function(){	 	 
	$("#search_btn").click(function(){
		var url = "${contextPath}/ctTask/getCtTaskData";	
		$.post(url,{popId:$("#storeId").val(),ctTaskId:$("#ctTaskId").val(),taskCycle:$("#taskCycle").val()},function(data){	
		if(data!=null){	
				$("input ~ .error").remove();	
				$.each(data, function(key, value){				
					var controlType = $("#"+key).attr("controlType");    //控件类型
					if(controlType=="select"){
						$("#"+key).select2("val",value);	
					}else if(controlType=="checkbox"){
						if(value==1){
							$("#"+key).attr("checked","checked");			
						}			
					}else if(controlType=="text"){
						$("#"+key).val(value);
					}					  
				});						
			}	 		
		});	
		if($("#storeId").val()!=''){
			$("#storeId ~ i").remove();
		} 	
	});
	 
	loadStatus($(".loadoption").attr("optionName"));	
	loadCheckbox($(".loadCheckBoxOption").attr("optionName"));	
	loadMultipleStatus($(".multipleloadoption").attr("optionName"));
	loadTaskCycle();
	
	$('.btn-danger').click(function(){
		addDialog.close();
	});

	$("#savetButton").bind("click",function(){	
		var searchFlag = "true";
		if($("#storeId").val()==''){
			searchFlag = false;
			if($("#storeId ~ i").length==0){
				$("#storeId").after("<i for=\"storeId\" class=\"error\">不能为空</i>");
			}				
		}
		$(".require").each(function(){
			var value = null;
			var id = $(this).attr("id");			
			var controlType = $("#"+id).attr("controlType");    //控件类型
			if(controlType=="select"){
				value = $("#"+id).select2("val")
			}else if(controlType=="text"){
				value = $("#"+id).val();
			}						
			if(value==''){
				searchFlag = "false";
				if($("#"+id+"~ i").length==0){
					$(this).after("<i for=\""+id+"\" class=\"error\">不能为空</i>");	
				}		 		
			}
		}); 
		var dataFlag = "true";	
		$(".maxValueValidate").each(function(){
			var currentValue = $(this).val();
			if(currentValue==''){
				currentValue = parseInt("0");
			}
			var maxValue = parseInt($(this).attr("maxvalue"));
			var minValue = parseInt($(this).attr("minvalue"));
			var id = $(this).attr("id");
			if($("#"+id+"~ i").length==0){
				if(currentValue>maxValue){
					$(this).after("<i for=\""+id+"\" class=\"error\">不能超过最大值"+maxValue+"</i>");		
				}else if(currentValue<minValue){
					$(this).after("<i for=\""+id+"\" class=\"error\">不能小于最小值"+minValue+"</i>");	
				}
			}			
		});
		if($(".error").length>0){
			dataFlag = "false";
		}
		if(dataFlag=="true"&&searchFlag=="true"){
			var dataString = $("#dataForm").serialize();	 	
	 		var datas = dataString.split("&");
	 		var ctTaskId = $("#ctTaskId").val();
	 		var storeId = $("#storeId").val(); 		
	 		var ctTaskData = new Object();
	 			ctTaskData.popId = storeId;
	 			ctTaskData.ctTaskId = ctTaskId;
	 		var ctTaskDetailDatas = new Array();
	 		for(var index in datas){
	 			var str1 = datas[index].split("=")[0];
	 			var ctTaskDetailData = new Object();
	 			ctTaskDetailData.ctTaskParameterId = str1.split("_")[0];
	 			ctTaskDetailData.target1Id = str1.split("_")[1];
	 			var controlType = $("#"+str1).attr("controlType");	
	 			var str2 = datas[index].split("=")[1];
	 			if(controlType=="multipleSelect"){
	 				ctTaskDetailData.value = $("#"+str1).select2("val")+"";
	 			}else{
	 				ctTaskDetailData.value = str2;
	 			}	 
	 			ctTaskDetailDatas.push(ctTaskDetailData);
	 		}
	 		ctTaskData.ctTaskDetailDatas = ctTaskDetailDatas;
	 		$.ajax({
					url : "${contextPath}/ctTaskData/saveCtTaskData",
					type : "post",
					dataType:'JSON',
					contentType : 'application/json',  
					data : JSON.stringify(ctTaskData),
					success : function(result) {
						window.location.reload();
						addDialog.close();
					}
				});		 
		}	 	
	});
	
	$("#startDate").datepicker({
		changeYear: true,
		yearRange:"2014:2025",
		prevText: '<',
		nextText: '>',
		onSelect:function(dateText,inst){
			$("#workDate2").datepicker("option","minDate",dateText);
			$(this).focus();
			$(this).blur();
		}
	});
	
	$(":checkbox").each(function(){
		$(this).click(function(){
			if($(this).attr("checked")=="checked"){
				$(this).next().remove();
			}else{			
				var id = $(this).attr("id");
				$(this).after("<input type=\"hidden\" id=\""+id+"\" name=\""+id+"\" value=\"0\" />");	
			}
		});
	});
	
	$(".maxValueValidate").change(function(){
		var currentVal = $(this).val();
		if(currentVal==''){
			currentVal = parseInt("0");
		}
		var maxValue = parseInt($(this).attr("maxvalue"));
		var minValue = parseInt($(this).attr("minvalue"));
		var id = $(this).attr("id");
		if($("#"+id+"~ i").length==0){			
			if(currentVal>maxValue){
				$(this).after("<i for=\""+id+"\" class=\"error\">不能超过最大值"+maxValue+"</i>");	
			}else if(currentVal<minValue){
				$(this).after("<i for=\""+id+"\" class=\"error\">不能小于最小值"+minValue+"</i>");		
			}
		}else{	 
			$(this).next().remove();
			maxError="false";		 
		}
					
	});
	
	$(".require").change(function(){
		var currentVal = $(this).val();
		var id = $(this).attr("id");
		if($("#"+id+"~ i").length==0){
			if(currentVal==''){
				$(this).after("<i for=\""+id+"\" class=\"error\">不能为空</i>");				
			} 
		}else{
			if(currentVal!=''){
				$(this).next().remove();				 
			}
		}
					
	});
	
});

function changeStore(){	
	$.post("${contextPath}/ctTask/getCtTaskData",{popId:$("#storeId").val(),ctTaskId:$("#ctTaskId").val()},function(data){	
		if(data!=null){	
			$("input ~ .error").remove();	
			$.each(data, function(key, value){				
				var controlType = $("#"+key).attr("controlType");    //控件类型
				if(controlType=="select"){
					$("#"+key).select2("val",value);	
				}else if(controlType=="checkbox"){
					if(value==1){
						$("#"+key).attr("checked","checked");			
					}			
				}else if(controlType=="text"){
					$("#"+key).val(value);
				}					  
			});		
			
		 	$("input").each(function(){
				$(this).attr("disabled",true);
			});	
		}	 		
	});	
	if($("#storeId").val()!=''){
		$("#storeId ~ i").remove();
	} 
}

function loadStatus(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("."+optionName).select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadCheckbox(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "";
			$.each(jsonData, function(index, item) {						 
				thisData+=""+item.optionText+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";		 
			});
			$(".loadCheckBoxOption").parent().append(thisData);
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadMultipleStatus(optionName){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTaskParameter/getOptionByName?optionName="+optionName,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("."+optionName).select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				multiple: true,
				closeOnSelect:false,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

$("#storeId").select2({
		width:145,
		placeholder: "输入字符查询",
		minimumInputLength: 2,
		allowClear : true,
		ajax: {
			url: "${contextPath}/ctTask/getStoreByNameAndTaskId?ctTaskId="+${ctTaskId},
			dataType: 'json',
			quietMillis: 250,
			data: function (term, page) {
				return {
					q: term,
					page: page
				};
			},
			results: function (data,page) {
				var more = page;
				return { results: data,more: more };
			}
		},
		initSelection: function(element, callback) {
			var id = $(element).val();
			if (id != "") {
				$.ajax("${contextPath}/store/getStore/"+id, {
					dataType: "json"
				}).done(function(data) { callback(data); });
			}
		},
		formatResult: repoFormatResult,
		formatSelection: repoFormatSelection,
		escapeMarkup: function (m) { return m; }
	});
	
	function repoFormatResult(repo) {
		return repo.name;
	}
	function repoFormatSelection(repo) {
		return repo.name;
	}
	
function loadTaskCycle(){
	$.ajax({
		type : "post",
		url : "${contextPath}/ctTask/getTaskDate",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.dataCycle+"\",\"text\":\""+item.dataCycle+"\"},";
				} else {
					thisData += "{\"id\":\""+item.dataCycle+"\",\"text\":\""+item.dataCycle+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#taskCycle").select2({
				width:200,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};	
</script>