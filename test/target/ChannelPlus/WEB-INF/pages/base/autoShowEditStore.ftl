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
		<input type="hidden" id="clientId" name="clientId" value="${clientId!''}">
		<input type="hidden" id="storeId" name="storeId" value="${store.storeId!''}">
		<input type="hidden" id="oldStoreNo" name="oldStoreNo" value="${store.storeNo!''}">
        <input type="hidden" id="clientStructureId" name="clientStructureId" value="${store.clientStructureId!''}">
    	<input type="hidden" id="channelId" name="channelId" value="${store.channelId!''}">
    	<input type="hidden" id="chainId" name="chainId" value="${store.chainId!''}">
    	<input type="hidden" id="distributorId" name="distributorId" value="${store.distributorId!''}">
    	<input type="hidden" id="oldBusinessman" name="oldBusinessman" value="">
    	<input type="hidden" id="oldPromotions" name="oldPromotions" value="">
        <table class="table_white_bg">
            <tbody>
            <#assign qp = 0/>
            <#list editList as ls>
            <#if ls.controlType == 110 >
            	<input type="hidden" id="controlType" name="controlType" value="${ls.controlType}">
            </#if>
            	<#if ls.remark =="br">
            		<#assign qp = 0/>
            		<tr>
            			<td class="td_label" ><#if ls.isMustDo = 1><i class="cc1">*</i></#if>${ls.columnDesc}：</td>
	                    <td class="td_control" colspan="3">
	                    	<#if ls.controlType = 2>
	                    		<textarea   maxlength="${ls.maxLength}" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> placeholder="不超过${ls.maxLength}个字"  <#if ls.isMustDo = 1>required</#if>>${store['${ls.attributeName}']}</textarea>
	                    	<#else>
	                    		<input type="text" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> <#if ls.isMustDo = 1>required</#if> style="width:450px;" <#if ls.relationValue??>relationValue="${ls.relationValue}"</#if> value="${store['${ls.attributeName}']}"/>
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
	                    		<textarea   maxlength="${ls.maxLength}" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> placeholder="不超过${ls.maxLength}个字"  <#if ls.isMustDo = 1>required</#if>>${store['${ls.attributeName}']}</textarea>
	                    	<#else>
	                    		<input type="text" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> <#if ls.isMustDo = 1>required</#if> <#if ls.relationValue??>relationValue="${ls.relationValue}"</#if> value="${store['${ls.attributeName}']}"/>
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
							<button id="savetButton" type="button" class="btn btn-success margin-left-18px" disabled="disabled">保存</button>
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
			if($('#controlType').val() == 110){
				var url = add_setting_chainName.async.url+"?channelId=";
				var arrayUrl = url.split("=");
				add_setting_chainName.async.url = arrayUrl[0]+"="+treeNode.id;
				$.fn.zTree.init($("#add_treeDemo_chainName"), add_setting_chainName);
			}
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
		if(treeId == "add_treeDemo_chainName"){
			var node = zTree.getNodeByParam("id", $("#chainId").val(), null);
			if(node != null){
				$("#chainName").val(node.name);
			}else{
				$("#chainName").val('');
			}
		}
		
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
    	var storeId = $("#storeId").val();
    	
    	$("#structureName,#channelName,#distributorName,#chainName").on("click",function(){
			var id = $(this).attr("id");
			showMenu(id);
		});
		
		loadStatus();
		loadStoreType();
		loadStoreGroup();
    	loadProvince();
        var provinceId = $('#provinceId').val();
        var cityId = $('#cityId').val();
        if(provinceId != null && provinceId != ""){
        	loadCity(provinceId);
        	if(cityId != null && cityId !=""){
	        	loadDistrict(cityId);
        	}
        }
        $.fn.zTree.init($("#add_treeDemo_structureName"), add_setting_st);
        $.fn.zTree.init($("#add_treeDemo_channelName"), add_setting_cl);
        $.fn.zTree.init($("#add_treeDemo_distributorName"), add_setting_distributorName);
         if($('#controlType').val() != 110){
       		$.fn.zTree.init($("#add_treeDemo_chainName"), add_setting_chainName);
        }else{
    		var url = add_setting_chainName.async.url+"?channelId=";
			var arrayUrl = url.split("=");
			add_setting_chainName.async.url = arrayUrl[0]+"="+$('#channelId').val();
			$.fn.zTree.init($("#add_treeDemo_chainName"), add_setting_chainName);
        }
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
		initBusinessman(businessman_positions,storeId);
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
		initPromotions(promotions_positions,storeId);
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
				telephoneNo:{isPhone:true,maxlength:20},
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
	    var flag  = false;
	    var conf  = false;
		//门店更新
		$("#savetButton").bind("click",function(){
			$(this).attr("disabled","disabled");
			if(!validobj.form()){
				$("#savetButton").removeAttr("disabled");
				return false;
			}
			if((flag || conf) && $("#storeNo").next().html() != "编号已存在"){
				layer.confirm("解除门店关系,将会解除未拜访计划",function(){
					$.ajax({
						url : "${contextPath}/store/updateStore",
						type : "post",
						dataType:"json",
						async: false,
						data : $("#dataForm").serialize(),
						success : function(result) {
							if(result.code=="success"){
								layer.alert(result.message,function(){
								//	window.location.reload();
								//	editDialog.close();
								parent.document.location.href = "${contextPath}/store/query/";
		   						parent.layer.closeAll('iframe');
						   		});
							}else if(result.code=="error"){
								layer.alert(result.message);
								$("#savetButton").removeAttr("disabled");
							}else {
								layer.alert(result.message);
								$("#savetButton").removeAttr("disabled");
					   		}
						},
						error: function(result) {
					   		$("#savetButton").removeAttr("disabled");
				   		}
					});	
				});
			} else if ($("#storeNo").next().html() != "编号已存在"){
				$.ajax({
					url : "${contextPath}/store/updateStore",
					type : "post",
					dataType:"json",
					async: false,
					data : $("#dataForm").serialize(),
					success : function(result) {
						if(result.code=="success"){
							layer.alert(result.message,function(){
								//window.location.reload();
								//editDialog.close();
								parent.document.location.href = "${contextPath}/store/query?mod=conf";
                    	        parent.layer.closeAll('iframe');
					   		});
						}else if(result.code=="error"){
							layer.alert(result.message);
							$("#savetButton").removeAttr("disabled");
						}else {
							layer.alert(result.message);
							$("#savetButton").removeAttr("disabled");
				   		}
					},
					error: function(result) {
				   		$("#savetButton").removeAttr("disabled");
			   		}
				});	
			}else{
				$("#savetButton").removeAttr("disabled");
			}
			$("#savetButton").removeAttr("disabled");
		});
		
		$('#businessman').on('change',function(){
			var oldBusinessman = $('#oldBusinessman').val();
			var newBusinessman = $('#businessman').val();
			if(oldBusinessman != null && oldBusinessman.length > 0){
				var oldArray = oldBusinessman.split(",");
				var newArray = newBusinessman.split(",");
				for(var i in oldArray){
					if($.inArray(oldArray[i],newArray) == -1){
						 flag = true;
						 break;
					}else{
						flag = false;
					}
				}
			}
		});
		
		$('#promotions').on('change',function(){
			var oldPromotions = $('#oldPromotions').val();
			var newPromotions = $('#promotions').val();
			if(oldPromotions != null && oldPromotions.length > 0){
				var oldArray = oldPromotions.split(",");
				var newArray = newPromotions.split(",");
				for(var i in oldArray){
					if($.inArray(oldArray[i],newArray) == -1){
						 conf = true;
						 break;
					}else{
						conf = false;
					}
				}
			}
		});
	
		//门店编号唯一性验证
		$("#storeNo").bind("change",function(){
			var oldStoreNo = $("#oldStoreNo").val();
			var elem = $(this);
			var errorHtml = "<nobr for='"+elem.attr("id")+"' class='error'></nobr>";
			var storeNo=$(this).val().trim();
			if(storeNo == "" || storeNo == oldStoreNo){
				elem.next().remove();
				validobj.form();
			} else {
				elem.next().remove();
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
						if($('#s2id_cityId').hasClass('error')){
							$('#s2id_cityId').removeClass('error');
						}
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
			}else{
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
			layer.alert("数据加载失败！");
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
			layer.alert("数据加载失败！");
		}
	});
};

function initBusinessman(initData,storeId){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/initStoreMappingData",
		dataType : "json",
		cache : false,
		data:{"storeId":storeId,"initData":initData},
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.clientUserId+"\",\"name\":\""+item.name+"\"},";
				} else {
					thisData += "{\"id\":\""+item.clientUserId+"\",\"name\":\""+item.name+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$('#businessman').select2('data', cData);
			var value = $('#businessman').val();
			$('#oldBusinessman').val(value);
			//alert($('#oldBusinessman').val());
			$("#savetButton").removeAttr("disabled");
		},
		
	});
};
function initPromotions(initData,storeId){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/initStoreMappingData",
		dataType : "json",
		cache : false,
		data:{"storeId":storeId,"initData":initData},
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.clientUserId+"\",\"name\":\""+item.name+"\"},";
				} else {
					thisData += "{\"id\":\""+item.clientUserId+"\",\"name\":\""+item.name+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$('#promotions').select2('data', cData);
			var value = $('#promotions').val();
			$('#oldPromotions').val(value);
			$("#savetButton").removeAttr("disabled");
		},
	});
};
</script>