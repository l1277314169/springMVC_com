<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
		<input type="hidden" id="clientUserId" name="clientUserId" value="${clientUser.clientUserId}">
		<input type="hidden" id="clientStructureId"  name="clientStructureId" value="${clientUser.clientStructureId}">
		<input type="hidden" id="structureId" name="structureId" value="${clientUser.clientStructureId}" />				
		<input type="hidden" id="oldRoleNames" name="oldRoleNames" value="${clientUser.roleNames}">
		<input type="hidden" id="oldParentId" name="oldParentId" value="${clientUser.parentId}">
		<input type="hidden" id="oldLoginName" name="oldLoginName" value="${clientUser.loginName}">
        <table class="table_white_bg">
            <tbody>
            	<#assign qp = 0/>
	            <#list editList as ls>
	            	<#if ls.remark =="br">
	            		<#assign qp = 0/>
	            		<tr>
	            			<td class="td_label" ><#if ls.isMustDo = 1><i class="cc1">*</i></#if>${ls.columnDesc}：</td>
		                    <td class="td_control" colspan="3">
		                    	<#if ls.controlType = 2>
		                    		<textarea   maxlength="${ls.maxLength}" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> placeholder="不超过${ls.maxLength}个字" <#if ls.isMustDo = 1>required</#if>>${clientUser['${ls.attributeName}']}</textarea>
		                    	<#else>
		                    		<input type="text" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> <#if ls.isMustDo = 1>required</#if> style="width:450px;" value="${clientUser['${ls.attributeName}']}"/>
		                    	</#if>
		                    </td>
						</tr>
					<#else>
						<#if qp%2 == 0>
							<tr>
		        		</#if>
							<td class="td_label" ><#if ls.isMustDo = 1><i class="cc1">*</i></#if>${ls.columnDesc}：</td>
		                    <td class="td_control">
		                    	<#if ls.controlType = 2>
		                    		<textarea   maxlength="${ls.maxLength}" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> placeholder="不超过${ls.maxLength}个字" <#if ls.isMustDo = 1>required</#if>>${clientUser['${ls.attributeName}']}</textarea>
		                    	<#else>
		                    		<input type="text" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> <#if ls.isMustDo = 1>required</#if> value="${clientUser['${ls.attributeName}']}"/>
		                    	</#if>
		                    </td>
						<#if qp%2 == 1>
							</tr>
		        		</#if>
	            	</#if>
	            	<#assign qp = qp+1/>
	            </#list>
               <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_pop" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
</div>
<input type="hidden" id="logname" name="logname">
<script>
var sexData = [{ id: 1, text: '男' }, { id: 2, text: '女' }];

var setting = {
	async : {
		enable : true,
		type: "get",
		url : "${contextPath}/clientStructure/getTreeNodeWithPer",
		error : function() {
			alert('亲，组织架构加载失败！');  
        }, 
		autoParam : [ "id" ]
	},
	view: {
        dblClickExpand: false,
        selectedMulti: true, //是否允许多选
        txtSelectedEnable: false //是否允许选中节点的文字
        //autoCancelSelected: false //不允许按下Ctrl键取消节点选中状态
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
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
        
    var updateStructureFlag = false;
    function onClick(e, treeId, treeNode) {
    	var structureId = $("#clientStructureId").val();
    	$("#structureName").attr("value", treeNode.name);
		$("#clientStructureId").attr("value", treeNode.id);
		hideMenu();
		if(structureId!=treeNode.id){
			updateStructureFlag = true;
		}
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");
		zTree.expandAll(true);
	};
	
    function showMenu() {
        var structureObj = $("#structureName");
        var clientStructureOffset = $("#structureName").position();
        $("#menuContent").css({ left: clientStructureOffset.left + "px", top: clientStructureOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
       
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    
$(document).ready(function () {
	$('#cancelEdit').click(function(){
		editDialog.close();
	});
	
    //验证
	var validobj = $("#dataForm").validate({
		ignore: [],
		onkeyup: false,
		errorClass: "error",
		rules : {
			name: {
				maxlength: 50
			},
			clientPositionId: {
				required: true
			},
			mobileNo: {
				isMobile : true
			},
			addr: {
				maxlength: 200
			},
			telephoneNo: {
				isPhone : true
			},
			postcode: {
				isZipCode : true
			},
			idcard: {
				isIdCardNo : true
			},
			remark: {
				maxlength: 200
			},
		},
		messages:{
			clientPositionId:{
				required:"不能为空"
			},
			parentId:{
				required:"不能为空"
			},
			roles:{
				required:"不能为空"
			},
		},
		errorPlacement: function (error, element) {
            var elem = $(element);
            error.insertAfter(element);
        },
		
		highlight: function (element, errorClass, validClass) {
			var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').addClass(errorClass);
            } else {
                elem.addClass(errorClass);
                //$('a.select2-choice').addClass(errorClass);
            }
		},

        unhighlight: function (element, errorClass, validClass) {
            var elem = $(element);
            if (elem.prev().hasClass("select2-container")) {
                $parent = elem.prev();
                $parent.find('a.select2-choice').removeClass(errorClass);
            } else {
                elem.removeClass(errorClass);
                //$('a.select2-choice').removeClass(errorClass);
            }
        }
	});
    
	$(document).bind("change", ".select2-offscreen", function () {
        if (!$.isEmptyObject(validobj.submitted)) {
            validobj.form();
        }
    });
    
    $("#structureName").on("click",function(){
		showMenu();
	});
    
    //更新人员
	$("#savetButton").bind("click",function(){
		var oldStructureId = jQuery("#structureId").val();
		var newStructureId = jQuery("#clientStructureId").val();

		if(oldStructureId!=newStructureId){	
			$.confirm("更换组织架构会强制解除原门店关系，并且请在调整组织架构后，重新确认人员所对应的上级。", function () {
			 	$(this).attr("disabled","disabled");
				//验证
				if(!validobj.form()){
					$("#savetButton").removeAttr("disabled");
					return false;
				}
				if($("#loginName").next().html() != "用户名已存在") {
					update();	
				}else{
					$("#savetButton").removeAttr("disabled");
				}   	 
			});				
		}else{
			$(this).attr("disabled","disabled");
			//验证
			if(!validobj.form()){
				$("#savetButton").removeAttr("disabled");
				return false;
			}
			if($("#loginName").next().html() != "用户名已存在") {
				update();	
			}else{
				$("#savetButton").removeAttr("disabled");
			}
		}
		
	});
    
    $('#loginName').bind("change",function(){
    	var oldLoginName = $("#oldLoginName").val();
		var elem = $(this);
		var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
		var logname = $("#loginName").val().trim();
		if(logname == "" || logname == oldLoginName){
			elem.next().remove();
			validobj.form();
		} else {
			elem.next().remove();
			$.ajax({
				type : "get",
				url : "${contextPath}/clientUser/findForLogName/"+logname,
				dataType : "json",
				cache : false,
				success : function(data) {
					if(data != null){
						elem.after(errorHtml);
						elem.next().html("用户名已存在");
					}else{
						elem.next().remove();
						validobj.form();
					}
				},
			});
		}
	});
    
	$(document).on("select2-opening", function (arg) {
		var elem = $(arg.target);
		if ($("#s2id_" + elem.attr("id") + " ul").hasClass("myErrorClass")) {
			$(".select2-drop ul").addClass("myErrorClass");
		} else {
			$(".select2-drop ul").removeClass("myErrorClass");
		}
	});

    $.fn.zTree.init($("#treeDemo_pop"), setting);
        
        //职位
        loadPosition();
        //在职状态
        loadStatus();
        //账号状态
        loadIsActivation();
        loadRoleNames();
        loadProvince();
        var provinceId = $('#provinceId').val();
        var cityId = $('#cityId').val();
        if(provinceId != null && provinceId != ""){
        	loadCity(provinceId);
        	if(cityId != null && cityId !=""){
	        	loadDistrict(cityId);
        	}
        } else {
        	$("#cityId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
	        $("#districtId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
        }
        $("#sex").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: sexData
        });
        
		var clientUserId=$('#clientUserId').val();
		 //加载上级人员
        $("#parentId").select2({
        	width:145,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear : true,
			ajax: {
			    type:"post",
				url: "${contextPath}/clientUser/getClientUserWithoutSelf",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
						clientUserId: clientUserId
					};
				},
				results: function (data,page) {
					return { results: data };
				},
				cache: true
			},
			initSelection: function(element, callback) {
				//alert("22222::"+element.val());
				var id = $("#clientUserId").val();
				if (id != "") {
					$.ajax("${contextPath}/clientUser/getParentClientUser/"+id, {
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
		
    });
    
    
	$("#structureName").keydown(function(e){ 
		if(e.keyCode == 8 || e.keyCode == 46){ 
		$("#structureName").val(""); 
		$("#clientStructureId").val(""); 
		}; 
	});
	
	$("#provinceId").on("change", function () {
		var value = $(this).val();
		if(value == "") {
			$("#cityId").val('');
			$("#districtId").val('');
			$("#cityId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
	        $("#districtId").select2({
	        	width:145,
				placeholder: "请选择",
				data: []
	        });
		}
		else{
			loadCity(value);
		}
	});
	
	$("#cityId").on("change", function () {
		var value = $(this).val();
		if(value == "") {
	        $("#districtId").val('');
	        $("#districtId").select2({
	        	width:145,
				placeholder: "请选择",
				data: []
	        });
		}
		else{
			loadDistrict(value);
		}
	});
	
function loadIsActivation(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientUser/getIsActivation",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"},";
				} else {
					thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#isActivation").select2({
				width:145,
				placeholder: "请选择",
				allowClear: false,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadStatus(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientUser/getStatus",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"},";
				} else {
					thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#status").select2({
				width:145,
				placeholder: "请选择",
				allowClear: false,
				data: cData
			});
		},
		error : function(data) {
			layer.alert("数据加载失败！");
		}
	});
};

function loadPosition(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientPosition/getClientPositionAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#clientPositionId").select2({
				width:145,
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

function loadRoleNames(){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientRoles/getClientRolesAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"},";
				} else {
					thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#roleNames").select2({
				width:450,
				multiple: true,
				placeholder: "请选择",
				allowClear: true,
				closeOnSelect:false,
				data: cData
			});
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};

function loadProvince(){
	$.ajax({
		type : "post",
		url : "${contextPath}/commService/getProvinceAjax",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"},";
				} else {
					thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#provinceId").select2({
				width:145,
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
function loadCity(provinceId){
	//alert(provinceId);
	$.ajax({
		type : "post",
		url : "${contextPath}/commService/findCityListByProvinceId/"+provinceId,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"},";
				} else {
					thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#cityId").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			$("#cityId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
			alert("数据加载失败！");
		}
	});
};
function loadDistrict(cityId){
	$.ajax({
		type : "post",
		url : "${contextPath}/commService/findDistrictListByCityId/"+cityId,
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.districtId+"\",\"text\":\""+item.district+"\"},";
				} else {
					thisData += "{\"id\":\""+item.districtId+"\",\"text\":\""+item.district+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#districtId").select2({
				width:145,
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
		},
		error : function(data) {
			$("#districtId").select2({
		    	width:145,
				placeholder: "请选择",
				data:[]
			});
			alert("数据加载失败！");
		}
	});
};

function update(){
	var clientUserId = jQuery("#clientUserId").val();
	var isActivation = jQuery("#isActivation").val();
	if(isActivation==0){ //启用不需要验证关系

		jQuery.ajax({
		  url: '${contextPath}/clientUser/checkUserStoreRelation',
		  type: 'POST',
		  dataType: 'json',
		  data: {clientUserId: clientUserId},
		  success: function(data, textStatus, xhr) {
		  		if(data.code=='success'){
		  			updateClientUser();
		  		}else{
		  			layer.alert(data.message);
		  		}
		  		$("#savetButton").removeAttr("disabled");
		  		
		  },
		  error: function(xhr, textStatus, errorThrown) {
		  		layer.alert("checkUserStoreRela error "+errorThrown);
		  		$("#savetButton").removeAttr("disabled");
		  }
		});

	}else{
		updateClientUser();
	}	
}

function updateClientUser(){

	$.ajax({
		url : "${contextPath}/clientUser/updateClientUser",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
			if(result.code=="success"){
				layer.alert(result.message,function(){
	   			//	window.location.reload();
	   			//	editDialog.close();
	   				parent.document.location.href = "${contextPath}/clientUser/query?mod=conf";
                    parent.layer.closeAll('iframe');
	   			});
			}else if(result.code=="error"){
				layer.alert(result.message);
				$("#savetButton").removeAttr("disabled");
			}else {
				layer.alert(result);
				$("#savetButton").removeAttr("disabled");
	   		}
	   	},
	   	error: function(result) {
			$("#savetButton").removeAttr("disabled");
		}
	});	
}

</script>