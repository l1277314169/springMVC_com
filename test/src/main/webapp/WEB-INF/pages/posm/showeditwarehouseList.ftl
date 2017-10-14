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
		<input type="hidden" name="clientId">
        <table class="table_white_bg">
         <input type="hidden" name="clientId" value="${warehous.clientId!''}">
		<input type="hidden" id="clientUserId" name="clientUserId" value="${warehous.clientUserId}">
		<input type="hidden" id="warehouseId" name="warehouseId" value="${warehous.warehouseId}">
	     <tbody >
            	 <tr >
	                   <td class="td_label"><i class="cc1">*</i>仓库名称：</td>
	                   <td class="td_control">
	                  		<input type="text" name="warehouseName" id="warehouseName" value="${warehous.warehouseName!''}" required>
					   </td>
					    <td class="td_label" ><i class="cc1">*</i>仓库编码：</td>
	                   <td class="td_control" >
	                  		<input type="text" name="warehouseNo" id="warehouseNo" value="${warehous.warehouseNo!''}" required>
					   </td>           
                 </tr>
                 <tr >
	                  <td class="td_label" ><i class="cc1">*</i>所在部门：</td>
	                  <td class="td_control">
	                     <input type="text" id="clientStructureId_structure" name="clientStructureName_structure"  readonly class="input-medium" onclick="showMenu_structure()">
	                    	<input type="hidden" id="clientStructureId"  name="clientStructureId" value="${warehous.clientStructureId}" required>
	                    	 <#assign structureFtlName="clientStructureId">
						     <#include "/widgets/structure.ftl" />
	
	                    </td>                 
	                   <td class="td_label">仓库面积：</td>
	                   <td class="td_control">
	                  		<input type="text" name="area" value="${warehous.area!''}" >
					   </td>             
                 </tr>
                 <tr>
	                   <td class="td_label">地址:</td>
	                   <td class="td_control">
	                   	<input type="text" name="addr" value="${warehous.addr!''}" >
	                   </td>
	                   <td class="td_label">省份：</td>
		               <td class="td_control">
			                 <input type="text" id="provinceId" name="provinceId" value="${warehous.provinceId!''}" >
		               </td>
		          </tr>
                  <tr>
                    <td class="td_label">城市：</td>
	                  <td class="td_control"> 
		                  <input type="text" id="cityId" name="cityId" value="${warehous.cityId!''}"  >
	                 </td>
                    <td class="td_label">区县：</td>
	                  <td class="td_control"> 
		                  <input type="text" id="districtId" name="districtId" value="${warehous.districtId!''}"  >
	                 </td>         
                 </tr>    
                 <tr >
                   <td class="td_label" >联系人：</td>
                   <td class="td_control">
                  		<input type="text" name="contact" value="${warehous.contact!''}" >
				   </td>
                   <td class="td_label">联系电话：</td>
                   <td class="td_control">
                    	<input type="text" name="telephoneNo"  value="${warehous.telephoneNo!''}" >
                   </td>
                  <tr/>
                
               <tr>
                    <td class="td_label" style="vertical-align:top;">备注：</td>
                    <td colspan="3" valign="middle" class="td_control">
                    	<textarea rows=2 maxlength="300" name="remark" placeholder="不超过200个字">${warehous.remark!''}</textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
							
					</td>
			   </tr>                
            </tbody>
        </table>
</form>
<div id="menuContent_structure" class="menuContent" style="display: none; position: absolute;z-index: 99;">
	<ul id="treeDemo_structure" class="ztree" style="margin-top: 0; width: 160px;"></ul>
</div>
<style type="text/css">
	#treeDemo_structure{
		height: 270px;
	}
</style>
<script type="text/javascript">
       
         		//验证
				var validobj = $("#dataForm").validate({
					ignore: [],
					onkeyup: false,
					errorClass: "error",
					rules : {
						warehouseNo:{maxlength:50},
						skuNameAbbr:{maxlength:50},
						addr:{maxlength:100},					
						area:{isNumber:true,maxlength:8},
						contact:{maxlength:20},			
						telephoneNo:{isTel:true},
						longitude:{isNumber:true},
						latitude:{isNumber:true},
						remark:{maxlength:200}	 	          
					},
				errorPlacement: function (error, element) {
		            var elem = $(element);
		            error.insertAfter(element);
		        },	
			});
	   //修改仓库信息
		$("#savetButton").bind("click",function(){
		  	if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
		}	 
	   if($("#warehouseName").next().html() != "仓库名称已存在" && $("#warehouseNo").next().html() != "仓库编号已存在") {			
	 		$.ajax({
				url : "${contextPath}/warehouse/updateWarehouse",
				type : "post",
				dataType:"json",
				async: false,
				data : $("#dataForm").serialize(),
				success : function(result) {
				   if(result.code=="success"){
					   layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/warehouse/query/";
		   						parent.layer.closeAll('iframe');
				   				//addDialog.close();
				   		});		
					}else {
						layer.alert(result.message);
						$("#savetButton").removeAttr("disabled");
			   		}
				 },
				 error:function(){
				 	$("#savetButton").removeAttr("disabled");
				 }
			});
			
			}else {
			$("#savetButton").removeAttr("disabled");
		}			
		});	
				
	  $('#warehouseName').bind("change",function(){
		var elem = $(this);
		var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
		var warehousename = $("#warehouseName").val().trim();
		if(warehousename == ""){
			elem.next().remove();
		
			 
		} else {
     	elem.next().remove();
			$.ajax({
				type : "get",
				url : "${contextPath}/warehouse/findForwarehouseName/"+warehousename,
				dataType : "json",
				cache : false,
				success : function(data) {
					if(data != null){
						elem.after(errorHtml);
						elem.next().html("仓库名称已存在");
					}else{
						elem.next().remove();
						validobj.form();
					}
				},
			});
		}
	});
	  $('#warehouseNo').bind("change",function(){
		var elem = $(this);
		var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
		var warehouseno = $("#warehouseNo").val().trim();
		
		if(warehouseno == ""){
			elem.next().remove();	
		} else {
     	elem.next().remove();
			$.ajax({
				type : "get",
				url : "${contextPath}/warehouse/findForwarehouseNo/"+warehouseno,
				dataType : "json",
				cache : false,
				success : function(data) {
					if(data != null){
						elem.after(errorHtml);
						elem.next().html("仓库编号已存在");
					}else{
						elem.next().remove();
						validobj.form();
					}
				},
			});
		}
	});
var setting = {
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
    	var structureId = $("#clientStructureId").val();
    	$("#structureName").attr("value", treeNode.name);
		$("#clientStructureId").attr("value", treeNode.id);
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
$(document).ready(function () {
	$('#cancelEdit').click(function(){
		editDialog.close();
	});	
	$(document).bind("change", ".select2-offscreen", function () {
        if (!$.isEmptyObject(validobj.submitted)) {
            validobj.form();
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

 


</script>
