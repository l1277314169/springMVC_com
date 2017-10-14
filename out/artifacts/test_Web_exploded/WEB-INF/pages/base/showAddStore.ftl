<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" id="clientId" name="clientId" value="${clientId!''}">
        <input type="hidden"  id="clientStructureId" name="clientStructureId" >
    	<input type="hidden" id="channelId" name="channelId" >
    	<input type="hidden" id="chainId" name="chainId" value="">
    	<input type="hidden" id="distributorId" name="distributorId" >
        <table class="table_white_bg">
            <tbody>
            	<tr>
            		<td class="td_label">门店编号：</td>
                    <td id="storeNos" class="td_control">
                    	<input id="storeNo" name="storeNo" type="text"/>
                    </td>
                	<td class="td_label" ><i class="cc1">*</i>门店名称：</td>
                    <td class="td_control"><input type="text" name="storeName" required=true></td>
                </tr> 
                <tr>
                	<td class="td_label">门店自编号：</td>
                	<td class="td_control"><input type="text" name="externalNo" ></td>
                	<td class="td_label"><i class="cc1">*</i>所属渠道：</td>
                    <td class="td_control">
                    	<input id="channelName" readonly name="channelName" type="text" required=true />
                    </td>
                </tr>
                <tr>
                	<td class="td_label" ><i class="cc1">*</i>所属部门：</td> 
                    <td class="td_control">
	                    <input id="structureName" required="required" readonly name="structureName" type="text" />
					</td>
					<td class="td_label">门店状态：
                	</td>
                    <td class="td_control">
	                    <input type="text" class="input-medium" autocomplete="off" id="status" name="status" />
                    </td>
                </tr>
                <tr>
                	<td class="td_label"><i class="cc1">*</i>店铺渠道：</td>
                    <td class="td_control">
                    	<input id="chainName" name="chainName" readonly type="text" required=true />
                    </td>
                	<td class="td_label">管理渠道：</td>
                    <td  class="td_control">
	                    <input id="storeType" name="storeType" type="text" />
                    </td>
                </tr>
                <tr>
                	<td class="td_label"><i class="cc1">*</i>所属经销商：
                	</td>
                    <td class="td_control">
                    <input id="distributorName" name="distributorName" required=true readonly type="text"/>
                    </td>
                	<td class="td_label">门店分组：
                	</td>
                    <td class="td_control">
                    	<input type="text" id="storeGroupId" name="storeGroupId" />
                    </td>
                </tr>
                <tr>
                	<td class="td_label">联系人：
                	</td>
                    <td  class="td_control"><input type="text" name="contact">
                    </td>
                	 <td class="td_label"><i class="cc1">*</i>省份：</td>
                	<td class="td_control">
                		<input type="text" id="provinceId" name="provinceId" />
                    </td>
                </tr>
                <tr>
				</tr>
                <tr>
                	<td class="td_label">联系人手机：
                	</td>
                    <td class="td_control"><input type="text" name="mobileNo" isPhone="true">
                    <div id="mobileNoErro"></div>
                    </td>
                    <td class="td_label"><i class="cc1">*</i>城市：</td>
                	<td class="td_control">
                	 	<input type="text" id="cityId" name="cityId" required/>
                    </td>
                </tr> 
                <tr>
                	
                    <td class="td_label">传真号：</td>
                    <td class="td_control">
                    	<input id="fax" name="fax" type="text"/>
                    </td>
                    <td class="td_label">区县：
                	</td>
                    <td class="td_control">
               	 		<input type="text" id="districtId" name="districtId" />
                    </td>
                </tr> 
                <tr>
                    <td class="td_label">联系电话：
                	</td>
                    <td class="td_control">
                    <input id="telephoneNo" name="telephoneNo" type="text" />
                    </td>
                </tr>
                <tr>
                	<td class="td_label"><i class="cc1">*</i>地址：</td>
                    <td colspan="3" class="td_control">
                    	<input type="text" name="addr" style="width:450" required=true>
                    </td>
                </tr>
                <tr>
	                <td class="td_label">业务员：</td>
	                <td colspan="3" class="td_control">
	                	<input type="text" id="businessman" name="businessman" value="" >
	                </td>
                </tr>
                <tr>
	                <td class="td_label">促销员：</td>
	                <td colspan="3" class="td_control">
	                	<input type="text" id="promotions" name="promotions" relationValue="61,65">
	                </td>
                </tr>
				<tr>
                    <td class="td_label">备注：</td>
                    <td colspan="3" class="td_control">
                    	<textarea maxlength="300" name="remark" placeholder="不超过200个字"></textarea>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
							 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
	<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_structureName" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_channelName" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_distributorName" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_distributorName" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
	<div id="menuContent_chainName" class="menuContent" style="display: none; position: absolute;">
		<ul id="add_treeDemo_chainName" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
	</div>
<script>
	var selectId;
	var add_setting_st = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
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
    
    var add_setting_chainName = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/chain/getTreeNode",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
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
    
    var add_setting_cl = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/channel/getTreeNode",
			error : function() {  
                 layer.alert('亲，渠道加载失败！');  
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
            onAsyncSuccess: zTreeOnAsyncSuccess,
            onClick: onClick
			
        }
    };
    var add_setting_distributorName = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/distributor/getTreeNode",
			error : function() {
				layer.alert('亲，组织架构加载失败！');  
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
    	var demo = "add_treeDemo_"+selectId;
        var zTree = $.fn.zTree.getZTreeObj(demo);
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#"+selectId).attr("value", treeNode.name);
    	if(selectId == 'structureName') {
			$("#clientStructureId").attr("value", treeNode.id);
        }
        if(selectId == 'channelName') {
			$("#channelId").attr("value", treeNode.id);
        }
         if(selectId == 'distributorName'){
        	$("#distributorId").attr("value", treeNode.id);
        }
         if(selectId == 'chainName'){
        	$("#chainId").attr("value", treeNode.id);
        }
        hideMenu();
        $("#"+selectId).focus();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandAll(true);
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'structureName') {
        	$("#menuContent_st").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if (objName == 'channelName'){
        	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if(objName == 'distributorName') {
			$("#menuContent_distributorName").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}else if(objName == 'chainName') {
			$("#menuContent_chainName").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}else {

       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="structureName") {
     	   $("#menuContent_st").fadeOut("fast");
    	}
    	if(selectId =="channelName") {
     	   $("#menuContent_cl").fadeOut("fast");
    	}
    	if(selectId =="distributorName"){
    	   $("#menuContent_distributorName").fadeOut("fast");
    	}
    	if(selectId =="chainName"){
    	   $("#menuContent_chainName").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="structureName"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_st" || $(event.target).parents("#menuContent_st").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="distributorName"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_distributorName" || $(event.target).parents("#menuContent_distributorName").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="chainName"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_chainName" || $(event.target).parents("#menuContent_chainName").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="channelName") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
	            hideMenu();
        	}
    	}
    }
    
    $(document).ready(function () {
    	$("#structureName,#channelName,#distributorName,#chainName").on("click",function(){
			var id = $(this).attr("id");
			showMenu(id);
		});
		
		loadStatus();
		loadStoreType();
		loadStoreGroup();
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
		
        $.fn.zTree.init($("#add_treeDemo_structureName"), add_setting_st);
        $.fn.zTree.init($("#add_treeDemo_channelName"), add_setting_cl);
        $.fn.zTree.init($("#add_treeDemo_distributorName"), add_setting_distributorName);
        $.fn.zTree.init($("#add_treeDemo_chainName"), add_setting_chainName);
         //加载人员(人员)
         var businessman_positions = $("#businessman").attr("relationValue");
         var promotions_positions = $("#promotions").attr("relationValue");
        $("#businessman").select2({
        	width:450,
        	multiple: true,
        	cache: true,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserPosition",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page, clientPositionIds) {
					return {
						q: term,
						page: page,
						clientPositionIds: businessman_positions
					};
				},
				results: function (data,page) {
					return { results: data};
				}
			},
			formatResult: repoFormatResult,
			formatSelection: repoFormatSelection,
			escapeMarkup: function (m) { return m; }
		});
        $("#promotions").select2({
        	width:450,
        	multiple: true,
        	cache: true,
			placeholder: "输入字符查询",
			minimumInputLength: 2,
			ajax: {
				url: "${contextPath}/clientUser/getClientUserPosition",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page, clientPositionIds) {
					return {
						q: term,
						page: page,
						clientPositionIds: promotions_positions
					};
				},
				results: function (data,page) {
					return { results: data};
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
		
		$("#structureName").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
				$("#structureName").val(""); 
				$("#clientStructureId").val(""); 
			}; 
		});
		$("#channelName").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
				$("#channelName").val(""); 
				$("#channelId").val(""); 
			}; 
		});
		$("#distributorName").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
				$("#distributorName").val(""); 
				$("#distributorId").val(""); 
			}; 
		});
		$("#chainName").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
				$("#chainName").val(""); 
				$("#chainId").val(""); 
			}; 
		});
		
		//验证
		var validobj = $("#dataForm").validate({
			ignore: [],
			onkeyup: false,
			errorClass: "error",
			rules:{
				storeName:{maxlength:50},
				storeNo:{maxlength:50,isRightfulString :true},
				externalNo:{isRightfulString :true},
				creditPeriod:{digits:true},
				creditLine:{isFloat:true},
				fax:{isDigits:true,maxlength:20},
				telephoneNo:{maxlength:20},
				contact:{maxlength:30},
				mobileNo:{isMobile:true,maxlength:20},
				provinceId:{required:true},
				remark:{maxlength:200}
			},
			messages:{
				provinceId:{
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
	    
		//新增门店
		$("#savetButton").bind("click",function(){
			$(this).attr("disabled","disabled");
			if(!validobj.form()){
				$("#savetButton").removeAttr("disabled");
				return false;
			}
			if($("#storeNo").next().html() != "编号已存在") {
				$.ajax({
				url : "${contextPath}/store/addStore",
				type : "post",
				dataType:"json",
				async: false,
				data : $("#dataForm").serialize(),
				success : function(result) {
				   if(result.code=="success"){
					   layer.alert(result.message,function(){
			   				// window.location.reload();
			   				// addDialog.close();
			   				parent.document.location.href = "${contextPath}/store/query";
		   					parent.layer.closeAll('iframe');
				   		});
					}else {
						layer.alert(result.message);
						$("#savetButton").removeAttr("disabled");
			   		}
			   	},
			   	error: function(result) {
				   $("#savetButton").removeAttr("disabled");
			   	}
				});
			}else {
				$("#savetButton").removeAttr("disabled");
			}
		});
		
		//门店编号唯一性验证
		$("#storeNo").bind("change",function(){
			var elem = $(this);
			var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
			var storeNo=$(this).val().trim();
			if(storeNo == ""){
				elem.next().remove();
				validobj.form();
			} else {
				$.ajax({
					url :"${contextPath}/store/validateStoreNo",
					type : "post",
					data:{storeNo:storeNo},
					success:function(result){
						if(result.code =="error"){
							elem.after(errorHtml);
							elem.next().html("编号已存在");
						}else if(result.code =="success"){
							elem.next().remove();
							validobj.form();
						}
					}
				});
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
						layer.alert("数据加载失败！");
					}
				});
			}
			validobj.form();
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
						layer.alert("数据加载失败！");
					}
				});
			}
			validobj.form();
		});
	
    });
	
function loadStoreType(){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/getStoreType",
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
			$("#storeType").select2({
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
	
function loadStoreGroup(){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/getStoreGroup",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.storeGroupId+"\",\"text\":\""+item.groupName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.storeGroupId+"\",\"text\":\""+item.groupName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#storeGroupId").select2({
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

function loadStatus(){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/getStatus",
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
			$("#status").select2({
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
			layer.alert("数据加载失败！");
		}
	});
};
</script>