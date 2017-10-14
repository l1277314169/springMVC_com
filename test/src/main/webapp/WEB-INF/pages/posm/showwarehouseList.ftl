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
		
        <table class="table_white_bg">
        <input type="hidden" name="clientId" value="${warehous.clientId!''}">
		<input type="hidden" name="warehouseId" value="${warehous.warehouseId!''}">	
            <tbody >
            	 <tr>
	                   <td class="td_label"><i class="cc1">*</i>仓库名称：</td>
	                   <td class="td_control">
	                  		<input type="text" name="warehouseName" value="${warehous.warehouseName!''}" disabled="disabled">
					   </td>
					    <td class="td_label" >仓库编码：</td>
	                   <td class="td_control" >
	                  		<input type="text" name="warehouseNo" value="${warehous.warehouseNo!''}" disabled="disabled">
					   </td>
                 </tr>
	                  <td class="td_label" ><i class="cc1">*</i>所属部门：</td>
	                   <td class="td_control">       
	                    	<input id="clientStructureId" type="text" class="input-medium" id="clientStructureId" name="clientStructureId" value="${warehous.clientStructureId!''}" disabled="disabled"/>                 
	                        <#include "/widgets/structureall.ftl" />
	                   </td> 
	                    <td class="td_label">仓库面积：</td>
	                    <td class="td_control">
	                  		<input type="text" name="area" value="${warehous.area!''}" disabled="disabled">
					   </td>             
                 </tr>
                 
                 <tr>
	                   <td class="td_label">地址:</td>
	                   <td class="td_control">
	                   		<input type="text" name="addr" value="${warehous.addr!''}" disabled="disabled">
	                   </td>
	                   <td class="td_label">省份：</td>
		                  <td class="td_control">
			                 <input type="text" id="provinceId" name="provinceId" value="${warehous.provinceId!''}" disabled="disabled"/>
		                 </td>
	              </tr>  
	             <tr>
                    
                    <td class="td_label">城市：</td>
	                  <td class="td_control"> 
		                  <input type="text" id="cityId" name="cityId" value="${warehous.cityId!''}" disabled="disabled" />
	                 </td>
                    <td class="td_label">区县：</td>
	                  <td class="td_control"> 
		                  <input type="text" id="districtId" name="districtId" value="${warehous.districtId!''}" disabled="disabled" />
	                 </td>
                 
                 </tr> 
	                                            
                   <tr>
                   <td class="td_label">联系人：</td>
                   <td class="td_control">
                  		<input type="text" name="contact" value="${warehous.contact!''}" disabled="disabled">
				   </td>
                   <td class="td_label">联系电话：</td>
                   <td class="td_control">
                    	<input type="text" name="telephoneNo"  value="${warehous.telephoneNo!''}" disabled="disabled">
                   </td>
                    
           		  <tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
                    <td colspan="3" valign="middle" class="td_control">
                    	<textarea rows=2 maxlength="300" name="remark" placeholder="不超过200个字" disabled="disabled">${warehous.remark!''}</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>						
					</td>
			   </tr>                
            </tbody>
        </table>
</form>
	<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
<script type="text/javascript">
       
	    
var setting = {
	async : {
		enable : true,
		type: "post",
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
    
    //var updateStructureFlag = false;
    function onClick(e, treeId, treeNode) {
    
		 
		hideMenu();
		if(structureId!=treeNode.id){
			//updateStructureFlag = true;
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
	if(isActivation==0){//禁用需要校验人员门店关系
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
	   				window.location.reload();
	   				editDialog.close();
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
</script>
