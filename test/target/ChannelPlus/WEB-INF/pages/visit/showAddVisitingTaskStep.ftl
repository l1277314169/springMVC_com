<div class="margin-top-18">
	<div class="control-group">
		<div class="fl margin-top-10">
			<label class="control-label" for="visitingTaskStepName"><i class="cc6">*</i>步骤名称：</label>
			<div class="controls">
			  <input type="text" id="visitingTaskStepName" value="" ><span id="visitingTaskStepNameMsg" style='color:red;'></span>
			</div>
		</div>
		<div class="fl margin-top-10" id="visitingTaskStepTypeSpan" style="display:none;">
			<label class="control-label " for="visitingTaskStepSortBy">分组方式：</label>
			<div class="controls">
				<select id="visitingTaskStepSortBy" name="visitingTaskStepSortBy" class="input-medium">
		        	<option value="">--请选择--</option>
		        	<#list sortBys as option>
		        		<option value="${option.getKey()}">${option.getCnName()}</option>
		        	</#list>		
	        	</select>
			</div>
		</div>
		<div class="fl margin-top-10" id="visitingTaskStepSelectTypeSpan" style="display:none;">
			<label class="control-label " for="visitingTaskStepSelectType">选择方式：</label>
			<div class="controls">
				<select id="visitingTaskStepSelectType" name="visitingTaskStepSelectType" class="input-medium">
		        	<option value="">--请选择--</option>
		        	<#list selectTypes as option>
		        		<option value="${option.getKey()}">${option.getCnName()}</option>
		        	</#list>		
	        	</select>
			</div>
		</div>
		<div class="fl margin-top-10">
			<label class="control-label " for="visitingTaskStepIsMustDo">是否必做：</label>
			<div class="controls">
				<input type="checkbox" id="visitingTaskStepIsMustDo" value="0">
			</div>
		</div>
		<div class="fl margin-top-10">
			<label class="control-label " for="visitingTaskStepIsOnePage">一页操作：</label>
			<div class="controls">
				<input type="checkbox" id="visitingTaskStepIsOnePage" value="0">
			</div>
		</div>
		<div class="fl margin-top-10">
			<label class="control-label " for="visitingTaskStepType"><i class="cc6">*</i>步骤类型：</label>
			<div class="controls">
				<select id="visitingTaskStepType">
	        	<option value="">--请选择--</option>
	        	<#list stepType as option>
	        		<option value="${option.getKey()}">${option.getCnName()}</option>
	        	</#list>
	        	</select><span id="visitingTaskStepTypeMsg" style='color:red;'></span>
			</div>
		</div>
		<div class="fl margin-top-10">
			  <button id="stepTypeParameter" class="btn btn-success margin-left-18px" type="button">已关联<i id="stepTypeParameterCount" style="color:red">0</i>个对象</span> </button>
		</div></br>
	</div>
	
	<div class="control-label">采集参数：</div>
	<table id="addTable" class="table table_white_bg" cellspacing="0" cellpadding="0">
		<thead><tr>
			<th>参数名称</th>
			<th>参数类型</th>
			<th>选项名称</th>
			<th>参数顺序</th>
			<th>默认值</th>
			<th>最大值</th>
			<th>最小值</th>
			<th>小数位数(最大3位)</th>
			<th>是否必填</th>
			<th>是否记忆</th>
			<th>是否可编辑</th>
			<th width="50px" align="center">操作</th>
		</tr></thead>
		<tr id="addTr" class="tr_border normal_font">
			<td class="td_control_pop"><i class="cc6">*</i><input type="text" id="addParameterName" style="width:140px"><span id="addParameterNameMsg" style='color:red;'></span></td>
			<td class="td_control_pop"><i class="cc6">*</i>
				<select id="addControlType" style="width:100px">
				<option value="">--请选择--</option>
				<#list controlType as option>
	        		<option value="${option.getKey ()}">${option.getCnName()}</option>
	        	</#list>
	        	</select><span id="addControlTypeMsg" style='color:red;'></span>
	        </td>
			<td class="td_control_pop"><i class="cc6">*</i>
				<select id="addControlName" style="width:100px">
				<option value="">--请选择--</option>
	        	</select><span id="addControlTypeMsg" style='color:red;'></span>
	        </td>
			<td class="td_control_pop"><input type="text" id="addSequence" style="width:35px"></td>
			<td class="td_control_pop"><input type="text" id="addDefaultValue" style="width:35px"></td>
			<td class="td_control_pop"><input type="text" id="addMaxValue" style="width:35px"><span id="addMaxValueMsg" style='color:red;'></span></td>
			<td class="td_control_pop"><input type="text" id="addMinValue" style="width:35px"><span id="addMinValueMsg" style='color:red;'></span></td>
			<td class="td_control_pop"><input type="text" id="addScale" style="width:35px"><span id="addScaleMsg" style='color:red;'></span></td>
			<td class="td_control_pop"><input type="checkbox" id="addIsMustDo"></td>
			<td class="td_control_pop"><input type="checkbox" id="addIsRemember"></td>
			<td class="td_control_pop"><input type="checkbox" id="addIsEditable"></td>
			<td align="center"><span class="save_span" title="保存"></span></td>
		</tr>
	</table>
		<!--<div class="btn_full" title="添加参数" onclick="alert('添加参数');">+ 添加参数</div>-->
</div>


<script type="text/javascript" language="javascript">
	initBody();
    $(document).ready(function () {
    	var stepName,addParameterName,addControlType,addControlName,addSequence,addDefaultValue,addMaxValue,addMinValue,addScale,addUnit,addIsMustDo,addIsRemember,addIsEditable;
    	
    	//初始化步骤对象数量
    	var stepPops = $("input[name ^='visitingTaskSteps[${step}].taskStepParameterDatas']");
		var stepTypeParameterCount = $("#stepTypeParameterCount");
		var popIds = stepPops.val();		
		var array = popIds.split(',');
		stepTypeParameterCount.html(array.length-1);
    	
    	//选择步骤类型参数
	    $("#stepTypeParameter").click(function(){
	    	var popValue = $("#visitingTaskStepType").val();
	    	if(popValue ==null || popValue == ""){
	    		layer.alert("请先选择步骤类型!");
	    		$("#visitingTaskStepType").focus();
	    		return false;
	    	};
	    	var popText = $("#visitingTaskStepType").find("option:selected").text(); 
			var url = "${contextPath}/visit/showStepTypeParameter?key="+popValue+"&step="+${step};
			//stepTypeDialog = new xDialog(url, {}, {title:"选择关联"+popText,iframe:true,width:900,height:650 });
			//return false;
			layer.open({
			    type: 2,
			    title:'选择关联'+popText,
			    closeBtn: 1, //不显示关闭按钮
			    shadeClose: true, //开启遮罩关闭
			    area: ['900px', '650px'],
			    content: url
			});
		});
    	$("#controlType").change(function(){
    		var controlTypeValue = $("#controlType").val();
    		if(controlTypeValue == 5 || controlTypeValue == 6){
				
			}
    	});
    	//选择步骤类型。
		$("#visitingTaskStepType").change(function(){
	    	var stepTypeValue = $("#visitingTaskStepType").val();	    	 
	    	//如果是产品相关，展示选择方式和分组的选择。
	    	if(stepTypeValue == "1"){
	    		$("#visitingTaskStepTypeSpan").css("display","block");
	    		$("#visitingTaskStepSelectTypeSpan").css("display","block");
	    	} else {
	    		$("#visitingTaskStepSortBy").val("");
	    		$("#visitingTaskStepSelectType").val("");
	    		$("#visitingTaskStepTypeSpan").css("display","none");
	    		$("#visitingTaskStepSelectTypeSpan").css("display","none");
	    	}
		});
        $(".save_span").click(function(){
        	//验证名称、参数、最大值、最小值
        	var stepName = validateStepName();
        	var stepType = validateStepTpye();
        	var name = validateName();
        	var type = validateType();
        	var max = validateMax();
        	var min = validateMin();
        	
        	if(!stepName || !name || !type || !max || !min){
        		return false;
        	}
        	addParameterName = $("#addParameterName").val();
        	addControlType = $("#addControlType").val();
        	addControlName = $("#addControlName").val();
        	addSequence = $("#addSequence").val();
        	addDefaultValue = $("#addDefaultValue").val();
        	addMaxValue = $("#addMaxValue").val();
        	addMinValue = $("#addMinValue").val();
        	addScale = $("#addScale").val();
			if($('#addIsMustDo').attr('checked')) {
				addIsMustDo = 1;
			} else {
				addIsMustDo = 0;
			}
			if($('#addIsRemember').attr('checked')) {
				addIsRemember = 1;
			} else {
				addIsRemember = 0;
			}
			if($('#addIsEditable').attr('checked')) {
				addIsEditable = 1;
			} else {
				addIsEditable = 0;
			}
        	
			//生成编号
			var index = $("#addTable tr").length -2;
        	var str = "<tr data='"+index+"'>";
        	str += "<td class='td_control_pop'>"+addParameterName+"</td>";
        	str +="<td class='td_control_pop'>"+$('#addControlType').find('option:selected').text()+"</td>";
        	if(addControlName ==0){
        		str +="<td class='td_control_pop'></td>";
        	} else {
        		str +="<td class='td_control_pop'>"+$('#addControlName').find('option:selected').text()+"</td>";
        	}
        	str +="<td class='td_control_pop'>"+addSequence+"</td>";
        	str +="<td class='td_control_pop'>"+addDefaultValue+"</td>";
        	str +="<td class='td_control_pop'>"+addMaxValue+"</td>";
        	str +="<td class='td_control_pop'>"+addMinValue+"</td>";
        	str +="<td class='td_control_pop'>"+addScale+"</td>";
        	if(addIsMustDo == 1){
        		str +="<td class='td_control_pop'>是</td>";
    		} else {
        		str +="<td class='td_control_pop'>否</td>";
    		}
        	if(addIsRemember == 1){
        		str +="<td class='td_control_pop'>是</td>";
    		} else {
        		str +="<td class='td_control_pop'>否</td>";
    		}
        	if(addIsEditable == 1){
        		str +="<td class='td_control_pop'>是</td>";
    		} else {
        		str +="<td class='td_control_pop'>否</td>";
    		}
        	str +="<td class='td_control_pop'><span class='delete_span' title='删除'></span></td>";
        	str +="</tr>";
			$("#addTable #addTr").before(str);
			//动态增加隐藏域
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].parameterName' value='"+addParameterName+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].controlType' value='"+addControlType+"' data='"+$('#addControlType').find('option:selected').text()+"'/>");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].controlName' value='"+addControlName+"' data='"+$('#addControlName').find('option:selected').text()+"'/>");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].sequence' value='"+addSequence+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].defaultValue' value='"+addDefaultValue+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].maxValue' value='"+addMaxValue+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].minValue' value='"+addMinValue+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].scale' value='"+addScale+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].isMustDo' value='"+addIsMustDo+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].isRemember' value='"+addIsRemember+"' />");
			$("#dataForm").append("<input type='hidden' class='parameter_"+index+"' name='visitingTaskSteps[${step}].visitingParameters["+index+"].isEditable' value='"+addIsEditable+"' />");
			
			//添加成功，清空状态
			$("#addParameterName").attr("value","");
			$("#addControlType").attr("value","");
			$("#addControlName").attr("value","");
			$("#addSequence").attr("value","");
			$("#addDefaultValue").attr("value","");
			$("#addMaxValue").attr("value","");
			$("#addMinValue").attr("value","");
			$("#addScale").attr("value","");
			$("#addIsMustDo").attr("checked",false);
			$("#addIsRemember").attr("checked",false);
			$("#addIsEditable").attr("checked",false);
			
		});
		//绑定删除按钮
     	$(".delete_span").die().live('click',function(){
     		//获取获取当前tr的data值
     		var index = $(this).parents("tr").attr("data");
     		alert(index);
     		var str = "visitingTaskSteps[${step}].visitingParameters["+index+"]";
     		$(this).parents("tr").remove();
     		$("input[name ^='"+str+"']").remove();
		});
		
		$("#visitingTaskStepName").blur(function(){
			var str = $("#visitingTaskStepName").val();
			$("input[name ^='visitingTaskSteps[${step}].stepName']").val(str);
		});
		
		$("visitingTaskStepSortBy").change(function(){
			var str = $(this).children('option:selected').val();
			$("input[name ^='visitingTaskSteps[${step}].sortBy']").val(str);
		});
		
		$("visitingTaskStepSelectType").change(function(){
			var str = $(this).children('option:selected').val();
			$("input[name ^='visitingTaskSteps[${step}].selectType']").val(str);
		});
		
		$("#visitingTaskStepIsMustDo").click(function(){
			var str;
			if($(this).prop("checked")){
				str = 1;
			} else {
				str = 0;
			}			
			//$("#visitingTaskSteps[${step}].isMustDo").attr("value",str);
			$("input[name ^='visitingTaskSteps[${step}].isMustDo']").val(str);
		});
		
		$("#visitingTaskStepIsOnePage").click(function(){
			var str;
			if($(this).prop("checked")){
				str = 1;
			} else {
				str = 0;
			}			
			//$("#visitingTaskSteps[${step}].isMustDo").attr("value",str);
			$("input[name ^='visitingTaskSteps[${step}].isOnePage']").val(str);
		});
		
		$("#visitingTaskStepType").change(function(){
			var str = $(this).children('option:selected').val();
			$("input[name ^='visitingTaskSteps[${step}].stepType']").val(str);
			
			//切换步骤类型时对象清空
			var popIds = $("input[name ^='visitingTaskSteps[${step}].taskStepParameterDatas']").val("");
			
			//var skuIds = $("input[name ^='visitingTaskSteps[${step}].skuParameterDatas']").val();
			//var brandIds = $("input[name ^='visitingTaskSteps[${step}].brandParameterDatas']").val();
			//var categoryIds = $("input[name ^='visitingTaskSteps[${step}].categoryParameterDatas']").val();
			//var materialIds = $("input[name ^='visitingTaskSteps[${step}].promotionMaterialParameterDatas']").val();
			
			//alert(str);
			//alert("skuIds:"+skuIds+"|brandIds:"+brandIds+"|categoryIds:"+categoryIds+"|materialIds:"+materialIds);
			if(str == 1 || str == 2 || str == 3 || str == 10){
				$('#stepTypeParameter').show();
			}else {
				$('#stepTypeParameter').hide();
			};
		});
		
    });
    
    function validateStepName(){
    	if($("#visitingTaskStepName").val() ==null || $("#visitingTaskStepName").val() ==""){
        		$("#visitingTaskStepNameMsg").html("名称不能为空");
        		return false;
    	} else {
    		$("#visitingTaskStepNameMsg").html("");
    		return true;
    	};
    };
    function validateStepTpye(){
    	if($("#visitingTaskStepType").val() ==null || $("#visitingTaskStepType").val() ==""){
        		$("#visitingTaskStepTypeMsg").html("步骤类型不能为空");
        		return false;
    	} else {
    		$("#visitingTaskStepTypeMsg").html("");
    		return true;
    	};
    };
    function validateName(){
    	if($("#addParameterName").val() ==null || $("#addParameterName").val() ==""){
        		$("#addParameterNameMsg").html("名称不能为空");
        		return false;
    	} else {
    		$("#addParameterNameMsg").html("");
    		return true;
    	};
    };
    function validateType(){
    	if($("#addControlType").val() ==null || $("#addControlType").val() ==""){
    		$("#addControlTypeMsg").html("参数不能为空");
    		return false;
    	}else {
    		$("#addControlTypeMsg").html("");
    		return true;
    	};
    };
    function validateMax(){
    	if($("#addMaxValue").val() !=null && $("#addMaxValue").val() != ""){
    		if(isNaN($("#addMaxValue").val())){
    			$("#addMaxValueMsg").html("必须是数字");
    			return false;
    		} else {
    			$("#addMaxValueMsg").html("");
    			return true;
    		}
    	}else {
    		$("#addMaxValueMsg").html("");
    		return true;
    	};
    };
    function validateMin(){
    	if($("#addMinValue").val() !=null && $("#addMinValue").val() != ""){
    		if(isNaN($("#addMinValue").val())){
    			$("#addMinValueMsg").html("必须是数字");
    			return false;
    		} else {
    			$("#addMinValueMsg").html("");
    			return true;
    		}
    	}else {
    		$("#addMinValueMsg").html("");
    		return true;
    	};
    };
    
    //初始化页面
    function initBody(){
    	//判断是否有该步骤属性对应的隐藏域，如果没有创建一个，有的话对各属性进行初始化；
    	//步骤名称
    	if(typeof($("input[name ^='visitingTaskSteps[${step}].stepName']").val()) != "undefined"){
    		var str = $("input[name ^='visitingTaskSteps[${step}].stepName']").val();
			$("#visitingTaskStepName").attr("value",str);
    		//alert("赋值"+$("#visitingTaskStepName").val());
		} else {
			//alert("新建");
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].stepName' name='visitingTaskSteps[${step}].stepName' value='' />");
		};
		//分组方式
    	if(typeof($("input[name ^='visitingTaskSteps[${step}].sortBy']").val()) != "undefined"){
    		var str = $("input[name ^='visitingTaskSteps[${step}].sortBy']").val();
			$("#visitingTaskStepSortBy").attr("value",str);
    		//alert("赋值"+$("#visitingTaskStepSortBy").val());
		} else {
			//alert("新建");
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].sortBy' name='visitingTaskSteps[${step}].sortBy' value='' />");
		};
		//选择方式
    	if(typeof($("input[name ^='visitingTaskSteps[${step}].selectType']").val()) != "undefined"){
    		var str = $("input[name ^='visitingTaskSteps[${step}].selectType']").val();
			$("#visitingTaskStepSelectType").attr("value",str);
    		//alert("赋值"+$("#visitingTaskStepSelectType").val());
		} else {
			//alert("新建");
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].selectType' name='visitingTaskSteps[${step}].selectType' value='' />");
		};
    	//是否必做
    	if(typeof($("input[name ^='visitingTaskSteps[${step}].isMustDo']").val()) != "undefined" ){
			if($("input[name ^='visitingTaskSteps[${step}].isMustDo']").val() ==1) {
				$("#visitingTaskStepIsMustDo").attr("checked", "checked");
			}
		} else {
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].isMustDo' name='visitingTaskSteps[${step}].isMustDo' value='0' />");
		};
    	//是否一页操作
    	if(typeof($("input[name ^='visitingTaskSteps[${step}].isOnePage']").val()) != "undefined" ){
			if($("input[name ^='visitingTaskSteps[${step}].isOnePage']").val() ==1) {
				$("#visitingTaskStepIsOnePage").attr("checked", "checked");
			}
		} else {
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].isOnePage' name='visitingTaskSteps[${step}].isOnePage' value='0' />");
		};
    	//步骤类型
    	if(typeof($("input[name ^='visitingTaskSteps[${step}].stepType']").val()) != "undefined"){
			var opText = $("input[name ^='visitingTaskSteps[${step}].stepType']").val();
			$("#visitingTaskStepType").find("option[value='"+opText+"']").attr("selected",true);
		} else {
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].stepType' name='visitingTaskSteps[${step}].stepType' value='' />");
		};
		//关联对象----sku、brand等
		if(typeof($("input[name ^='visitingTaskSteps[${step}].taskStepParameterDatas']").val()) == "undefined"){
			$("#dataForm").append("<input type='hidden' id='visitingTaskSteps[${step}].taskStepParameterDatas' name='visitingTaskSteps[${step}].taskStepParameterDatas' value='' />");
		}
		
		
		//参数列表
		//构建参数形态
		var patts = [];
		$("input[name ^='visitingTaskSteps[${step}].visitingParameters']").each(function(i){  
  			var patt = $(this).attr('class');
  			//判断patts中是否存在，如果没有则添加
  			if('-1' == $.inArray(patt, patts)){
  				patts.push(patt);
  			}
  			//alert(patts);
		});
		$.each(patts,function(n,val) {
			var index = val.substring(val.length-1,val.length);
			var parameterName = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].parameterName']").val();
			var controlType = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].controlType']").attr('data');
			var controlName = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].controlName']").attr('data');
			var sequence = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].sequence']").val();
			var defaultValue = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].defaultValue']").val();
			var maxValue = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].maxValue']").val();
			var minValue = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].minValue']").val();
			var scale = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].scale']").val();
			var isMustDo = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].isMustDo']").val();
			var isRemember = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].isRemember']").val();
			var isEditable = $("input[name ^='visitingTaskSteps[${step}].visitingParameters["+index+"].isEditable']").val();
			
			var str = "<tr data='"+index+"'>";
			str +="<td class='td_control_pop'>"+parameterName+"</td>";
			str +="<td class='td_control_pop'>"+controlType+"</td>";
			str +="<td class='td_control_pop'>"+controlName+"</td>";
			str +="<td class='td_control_pop'>"+sequence+"</td>";
			str +="<td class='td_control_pop'>"+defaultValue+"</td>";
			str +="<td class='td_control_pop'>"+maxValue+"</td>";
			str +="<td class='td_control_pop'>"+minValue+"</td>";
			str +="<td class='td_control_pop'>"+scale+"</td>";
			if(isMustDo == 1) {
				str +="<td class='td_control_pop'>是</td>";
			} else {
				str +="<td class='td_control_pop'>否</td>";
			}
			if(isRemember == 1) {
				str +="<td class='td_control_pop'>是</td>";
			} else {
				str +="<td class='td_control_pop'>否</td>";
			}
			if(isEditable == 1) {
				str +="<td class='td_control_pop'>是</td>";
			} else {
				str +="<td class='td_control_pop'>否</td>";
			}
			str +="<td class='td_control_pop'><span class='delete_span' title='删除'></span></td>";
			str +="</tr>";
			$("#addTable #addTr").before(str);
		});
    };
</script>