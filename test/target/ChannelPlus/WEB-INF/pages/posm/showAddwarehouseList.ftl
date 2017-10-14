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
        <input type="hidden" id="clientStructureId" name="clientStructureId" >
		<input type="hidden" name="clientId">
        <table class="table_white_bg">
            <tbody >
            	 <tr >
	                   <td class="td_label"><i class="cc1">*</i>仓库名称：</td>
	                   <td class="td_control">
	                  		<input type="text" name="warehouseName" id="warehouseName" class="required" >
					   </td>
					    <td class="td_label" ><i class="cc1">*</i>仓库编码：</td>
	                   <td class="td_control" >
	                  		<input type="text" name="warehouseNo" id="warehouseNo" class="required" >
					   </td>      
                 </tr>
	             <tr >
	                          
	                   <td class="td_label" ><i class="cc1">*</i>所属部门：</td>                   
	                   <td class="td_control">
	                	   <input type="text" id="structureName" name="structureName" readonly  required>
	                   </td>  
	                   <td class="td_label">仓库面积：</td>
	                   <td class="td_control">
	                  	   <input type="text" name="area" >
					   </td>         
                </tr>
                 
                 <tr >
	                   <td class="td_label">地址:</td>
	                   <td class="td_control">
	                   		<input type="text" name="addr" >
	                   </td>
	                   <td class="td_label">省份：</td>
		                  <td class="td_control">
			                 <input type="text" id="provinceId" name="provinceId"  >
		               </td>
                 </tr>      
                 <tr >
	                     <td class="td_label">城市：</td>
		                  <td class="td_control"> 
			                  <input type="text" id="cityId" name="cityId"  >
		                 </td>
	                    <td class="td_label">区县：</td>
		                  <td class="td_control"> 
			                  <input type="text" id="districtId" name="districtId"  >
		                 </td>
                 </tr>   
                 <!--
                 <tr >
	                   <td class="td_label">经度:</td>
	                   <td class="td_control">
	                   	<input type="text" name="longitude" id="longitude" >
	                   </td>
	                    <td class="td_label">纬度：</td>
		                  <td class="td_control">
			                 <input type="text" id="latitude" name="latitude" >
		                 </td>
                 </tr>
                     -->        
                 <tr >
	                   <td class="td_label" >联系人：</td>
	                   <td class="td_control">
	                  		<input type="text" name="contact" >
					   </td>
	                   <td class="td_label">联系电话：</td>
	                   <td class="td_control">
	                    	<input type="text" name="telephoneNo" >
	                   </td>    
                 <tr>
	                    <td class="td_label" style="vertical-align:top;">备注：</td>
	                    <td class="td_control" colspan="3" valign="middle">
	                    	<textarea rows=2  maxlength="300" name="remark" placeholder="不超过200个字"></textarea>
	                    </td>
                </tr>
						<td colspan="4" class="td_buttons">
								<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
								<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
								
						</td>
			   </tr>                
            </tbody>
        </table>
</form>
<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_pop" class="ztree" style="margin-top: 0; width: 160px; height: 319px;">
		</ul>
</div>
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
       
	   //新增产品
		$("#savetButton").bind("click",function(){
		  		if(!validobj.form()){
			$("#savetButton").removeAttr("disabled");
			return false;
			}
			if($("#warehouseName").next().html() != "仓库名称已存在" && $("#warehouseNo").next().html() != "仓库编号已存在") {				 
			$.ajax({
				url : "${contextPath}/warehouse/add",
				type : "post",
				dataType:"json",
				async: false,
				data : $("#dataForm").serialize(),
				success : function(result) {
				   if(result.code=="success"){
					   layer.alert(result.message,function(){
					   			parent.document.location.href = "${contextPath}/warehouse/query";
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
			validobj.form();
			 
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
			validobj.form(); 
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
    
    function onClick(e, treeId, treeNode) {
    	$("#structureName").attr("value", treeNode.name);
		$("#clientStructureId").attr("value", treeNode.id);
		hideMenu();
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
	 $('#cancel').click(function(){
		addDialog.close();
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
        $("#sex").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: sexData
        });
        //加载上级人员
        $("#parentId").select2({
        	width:145,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			allowClear : true,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserWithoutSelf",
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
			$.ajax({
				type : "post",
				url : "${contextPath}/commService/findCityListByProvinceId/"+value,
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
					alert("数据加载失败！");
				}
			});
		}
	});
	
	$("#cityId").on("change", function () {
		var value = $(this).val();
		if(value == "") {
	        $("#districtId").select2({
	        	width:145,
				placeholder: "请选择",
				data: []
	        });
		}
		else{
			$.ajax({
				type : "post",
				url : "${contextPath}/commService/findDistrictListByCityId/"+value,
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
					alert("数据加载失败！");
				}
			});
		}
	});
	
function loadIsActivation(){
	$("#isActivation").val("1");
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
	$("#status").val("1");
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
</script>
