<html>
<head>
<#include "/common/head.ftl" />
<title>价格管理</title>
<#include "/common/foot.ftl" />
</head>
<body class="main_body">
<#assign privCode="C2M8">
<#include "/base.ftl" />
	<div  id="content" >
		<div id="content-header">
		<div id="breadcrumb"> 
			<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
			<a href="#">基础数据管理</a>
			<a class="linkPage active" href="${contextPath}/skuPrice/query">价格管理</a>
		</div>
		</div>
		<div class="widget-content nopadding">
<form id="dataForm" action="#" method="post">
		<input type="hidden" id="clientId" name="clientId" value="${clientId}">
		<input type="hidden" id="skuPriceGroupId" name="skuPriceGroupId" value="${skuPriceGroup.skuPriceGroupId}">
		<input type="hidden" id="mappingId" name="mappingId" value="${skuPriceGroup.mappingId}">
        <table class="table_white_bg">
            <tbody>
	            <tr>
	            	  <td class="td_label"><i class="cc1">*</i>价格分组：</td>
	            	  <td class="td_control">
	            	  	<input type="text" id="groupName" class="input-medium" style="margin-top:10px;" name="groupName" value="${skuPriceGroup.groupName}">
	            	  	<input type="hidden" id="oldGroupName" name="oldGroupName" value="${skuPriceGroup.groupName}">
	        	  	  </td>
            	</tr>
            	<tr>
	            	  <td class="td_label"><i class="cc1">*</i>渠道：</td>
	            	  <td class="td_control">
						<input id="channel"  type="text" name="channel" class="input-medium" value="${channel!''}"  readonly onclick="showMenu(this.id);"/> 
			            <input type="hidden" id="arg_channel_ids" name="channel" value="${channel!''}" />
					    <#assign channelFTL="arg_channel_ids">
					    <#include "/widgets/channel_widget.ftl" />	        
			    	  </td>
	           	</tr>
	           	<tr>
	            	  <td class="td_label">连锁：</td>
	            	  <td class="td_control">
						<input id="chain" type="text" name="chain" class="input-medium" value="${chain!''}"  readonly onclick="showMenu(this.id);"/>  
	            	 	 <input type="hidden" id="arg_types" name="chain" value="${chain!''}" >
					 	 <#assign chainFTL="arg_types">
					     <#include "/widgets/chain_widget.ftl" />
	            	  </td>
	             </tr>
	             <tr>
	            	  <td class="td_label">经销商：</td>
	            	  <td class="td_control">
						<input id="distributor" type="text" name="distributor" class="input-medium" value="${distributor!''}"  readonly onclick="showMenu(this.id);"/>  
	            	  	 <input type="hidden" id="arg_types" name="distributor" value="${distributor!''}" >
					 	 <#assign distributorFTL="arg_types">
					     <#include "/widgets/jingxiaos_widget.ftl" />  
	            	  </td>
	            </tr>
	             <tr>
	            	  <td class="td_label">关联SKU：</td>
	            	  <td  class="td_control">
		            	<button id="skuId" type="button"  name="skuId"  class="btn btn-success" style="width:145px;">已关联<font id="skuCheckbox" color="#ff0000"><#if skuStr??>${skuIdsCount}<#else>0</#if></font>个SKU</button>
		              </td>
	              </tr>
              	 	<tr>
              		<td class="td_label"></td>
	            	  <td  class="td_control">
		            	<button class="btn btn-danger"  onClick="window.location.href='${contextPath}/skuPrice/query';"  type="button">返回</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
		              </td>
		   		</tr>
	          	<input type="hidden" id="channelId" name="channelId" value="${skuPriceGroup.channelId!''}">
	        	<input type="hidden" id="chainId" name="chainId" value="${skuPriceGroup.chainId!''}">
	        	<input type="hidden" id="distributorId" name="distributorId" value="${skuPriceGroup.distributorId!''}">
				<input type="hidden" id="skuPrices" name="skuPrices" value='${skuPriceGroup.skuIdAndPrices!''}'>
				<input type="hidden" id="oldSkuPrices" name="oldSkuPrices" value='${skuPriceGroup.skuIdAndPrices!''}'>
            </tbody>
        </table>
</form>
</div>
		<div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_channel_edit" class="ztree" style="margin-top: 10; width: 160px;">
			</ul>
		</div>
		
		<div id="menuContent_st" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_chain_edit" class="ztree" style="margin-top: 10; width: 160px;">
			</ul>
		</div>
		
		<div id="menuContent_distributor" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_distributor_edit" class="ztree" style="margin-top: 10; width: 160px;">
			</ul>
		</div>
		
		<div id="menuContent_category" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_category" class="ztree" style="margin-top: 10; width: 160px;">
			</ul>
		</div>
		
		<div id="menuContent_brand" class="menuContent" style="display: none; position: absolute;">
			<ul id="treeDemo_brand" class="ztree" style="margin-top: 10; width: 160px;">
			</ul>
		</div>
</div>
<#include "/footer.ftl" />
</body>
</html>
<script>
//新增SOB价格
var relationDialog;
$("#savetButton").bind("click",function(){
	//验证
	if(!$("#dataForm").validate({
	 	rules : { 
			groupName:{
				required : true,
				maxlength: 25,
				isContainsSpecialChar:true,
			remote:{
				url: "${contextPath}/skuPrice/ajaxSkuPrice", 
			    type: "post",               
			    dataType: "json",
			    async:false,             
			    data: {                
			        groupName: function() {
			            return $("#groupName").val();
			        },
			        oldGroupName: function(){
			        	return $("#oldGroupName").val();
			        }
			   	 }
			}
			},
			channelName:{
				required : true
			}
		},
		messages: {
            groupName: {
                remote: "分组名称已存在!"
            }
	    }
	}).form()){
		return;
	};
	if($("#checkCount").html() == 0){
	 	alert("亲，请选择sku产品");
		return false;
	}
	$.ajax({
	url : "${contextPath}/skuPrice/updateSkuPrice",
	type : "post",
	dataType:"json",
	async: false,
	data: $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
			layer.alert(result.message,function(){
   				window.location="${contextPath}/skuPrice/query";
   				editDialog.close();
   			});
		}else {
			layer.alert(result.message);
   		}
	   }
	});						
});

//关联SKU
$('#skuId').click(function(){
	var skuPriceGroupId = $('#skuPriceGroupId').val();
	var url = "${contextPath}/skuPrice/showSkuPriceRelationDialog?skuPriceGroupId="+skuPriceGroupId;
	//relationDialog = new xDialog(url, {}, {title:"产品价格关联SKU",iframe:true,width:800,height:660});
	//return false;
	layer.open({type: 2,title: '产品价格关联SKU',closeBtn: 1,area: ['820px', '630px'],content: url});
});

	var selectId;
	var setting_st = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/chain/getTreeNode",
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
    
    var setting_cl = {
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
            onAsyncSuccess: zTreeOnAsyncSuccess,
            onClick: onClick
			
        }
    };
    
    var setting_distributor = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/distributor/getTreeNode",
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
        
        
   var setting_category = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/category/getTreeNode",
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
    
      var setting_brand = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/brand/getTreeNode",
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
    	var demo = "treeDemo_"+selectId;
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
    	if(selectId == 'chain') {
    		$(window.parent.document).find('#chainName').attr("value", treeNode.name);
			$(window.parent.document).find('#chainId').attr("value", treeNode.id);
			$('#chainId').attr("value",treeNode.id);
        }
        if(selectId == 'channel') {
        	$(window.parent.document).find('#channelName').attr("value", treeNode.name);
			$(window.parent.document).find('#channelId').attr("value", treeNode.id);
			$("#channelId").attr("value", treeNode.id);
        }
        if(selectId == 'distributor'){
        	$(window.parent.document).find('#distributorName').attr("value", treeNode.name);
			$(window.parent.document).find('#distributorId').attr("value", treeNode.id);
        	$("#distributorId").attr("value", treeNode.id);
        }
         if(selectId == 'category'){
        	$("#categoryId").attr("value", treeNode.id);
        }
        if(selectId == 'brand'){
        	$("#brandId").attr("value", treeNode.id);
        }
        hideMenu();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandAll(true);
		if(treeId == "treeDemo_channel_edit"){
			var node = zTree.getNodeByParam("id", $("#channelId").val(), null);
			if(node != null){
				$("#channel").val(node.name);
			}
		}
		if(treeId == "treeDemo_chain_edit"){
			var node = zTree.getNodeByParam("id", $("#chainId").val(), null);
			if(node != null){
				$("#chain").val(node.name);
			}
		}
		if(treeId == "treeDemo_distributor_edit"){
			var node = zTree.getNodeByParam("id", $("#distributorId").val(), null);
			if(node != null){
				$("#distributor").val(node.name);
			}
		}
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'chain') {
        	$("#menuContent_st").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if (objName == 'channel'){
        	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if(objName == 'distributor') {
			$("#menuContent_distributor").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	} else if(objName == 'category'){
       		$("#menuContent_category").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	} else if(objName == 'brand'){
       		$("#menuContent_brand").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="chain") {
     	   $("#menuContent_st").fadeOut("fast");
    	}
    	if(selectId =="channel") {
     	   $("#menuContent_cl").fadeOut("fast");
    	}
    	if(selectId =="distributor"){
    		$("#menuContent_distributor").fadeOut("fast");
    	}
    	if(selectId =="category"){
    		$("#menuContent_category").fadeOut("fast");
    	}
    	if(selectId =="brand"){
    		$("#menuContent_brand").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="chain"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_st" || $(event.target).parents("#menuContent_st").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="distributor"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_distributor" || $(event.target).parents("#menuContent_distributor").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="channel") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
	            hideMenu();
        	}
    	}
    	if(selectId =="category") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_category" || $(event.target).parents("#menuContent_category").length > 0)) {
	            hideMenu();
        	}
    	}
    	if(selectId =="brand") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_brand" || $(event.target).parents("#menuContent_brand").length > 0)) {
	            hideMenu();
        	}
    	}
    }
$(document).ready(function () {
		$.fn.zTree.init($("#treeDemo_category"), setting_category);
		$.fn.zTree.init($("#treeDemo_brand"), setting_brand);
        $.fn.zTree.init($("#treeDemo_channel_edit"), setting_cl);
        $.fn.zTree.init($("#treeDemo_chain_edit"), setting_st);
        $.fn.zTree.init($("#treeDemo_distributor_edit"), setting_distributor);
  });
	
</script>
