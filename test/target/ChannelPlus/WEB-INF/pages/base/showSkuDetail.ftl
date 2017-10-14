<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form>
        <table class="table_white_bg">
            <tbody >
            	 <tr >
                   <td class="td_label"><i class="cc1">*</i>产品名称：</td>
                   <td class="td_control"  title="${sku.skuName!''}">
                  		<#if sku.skuName?? && sku.skuName?length gt 15>
							${sku.skuName?substring(0,15)}...
						<#else>
							${sku.skuName!''}
						</#if>
				   </td>
                   <td class="td_label">产品英文名称：</td>
                   <td class="td_control">
                    	${sku.skuNameEn!''}
                   </td>
                 </tr>
                 <tr >
                   <td class="td_label" >产品编码：</td>
                   <td class="td_control">
                  		${sku.skuNo!''}
				   </td>
                   <td class="td_label" >产品简称：</td>
                   <td class="td_control">
                    	${sku.skuNameAbbr!''}
                   </td>
                 </tr>
                 <tr >
                   <td class="td_label">产品条码:</td>		
                   <td class="td_control">
                   		${sku.barcode!''}
                   </td>
                   <td class="td_label">产品价格：</td>
                   <td class="td_control">
                  		${sku.price!''}
				   </td>
                 </tr>
                 <tr >
                   <td class="td_label" ><i class="cc1">*</i>品牌：</td>
                   <td class="td_control">
                   		<#if brand??>
                  			${brand.brandName!''}
                  		</#if>
				   </td>
                   <td class="td_label" ><i class="cc1">*</i>品类：</td>
                   <td class="td_control">
                   		<#if category??>
                  			${category.categoryName!''}
                  		</#if>
                   </td>
                 </tr>
                 <tr >
                   <td class="td_label" >产品规格：</td>
                   <td class="td_control">
                  		${sku.spec!''}
				   </td>
                   <td class="td_label">包装规格：</td>
                   <td class="td_control">
                    	${sku.packSpec!''}
                   </td>
                    
                 </tr>
                 <tr >
                   <td class="td_label">产品重量：</td>
                   <td class="td_control">
                  		${sku.weight!''}
				   </td>
                   <td class="td_label" >产品体积：</td>
                   <td class="td_control">
                    	${sku.volume!''}
                   </td>
                 </tr>
                 
                 <tr >
                   <td class="td_label">单位：</td>
                   <td class="td_control">
                   		<#if unit??>
                   			${unit.unitGroupName!''}
                   		</#if>
				   </td>
				   <td class="td_label">产品分组</td>
				   <td class="td_control">
				   		<#if skuGroup??>
				   			${skuGroup.groupName!''}
				   		</#if>
				   </td>
                 </tr>
                  <tr>
                  	 <td class="td_label">产品分类:</td>
	                    <td class="td_control">
	                      <#if skutype??>
				   			 ${skutype.skuTypeName!''} 
				   		</#if>
	                     
	                     
	                   </td>
                    <td class="td_label">产品系列:</td>
	                    <td class="td_control">
	                     <#if skuservice??> 
				   			 ${skuservice.skuSeriesName!''}
				   		</#if>
	                    
	                    	 
	                    </td>
					    
                 </tr>
                 
                 <tr>
                   <td class="td_label"><i class="cc1">*</i>是否主打：</td>
                   <td class="td_control">
                   		<#if sku.isMain == "1">
	                    	是
                    	<#else>
	                    	否
                    	</#if>
                   </td>
                   <td class="td_label"></td><td class="td_control"></td>
                 </tr>
                 <tr>
                    <td style="height:39px" class="td_label" style="vertical-align:top;">备注：</td>
                    <td style="height:39px" colspan="3" valign="middle" class="td_control">
                    	${sku.remark!''}
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">

				 <button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">关闭</button>
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
<script>
   //编辑保存
	$("#savetButton").bind("click",function(){
		//验证
		if(!$("#dataForm").validate({
				rules : {
					skuName:{maxlength:50,required:true},
					skuNo:{maxlength:50},
					skuNameAbbr:{maxlength:50},
					barcode:{maxlength:50},
					brand:{required:true},
					category:{required:true},
					spec:{isFloatGtZero:true},
					packSpec:{isFloatGtZero:true},
					weight:{isFloatGtZero:true},
					volume:{isFloatGtZero:true},
					unitId:{required:true},
					price:{isFloatGtZero:true},
					remark:{maxlength:200}
				}
				}).form()){
			return;
		}
		$.ajax({
		url : "${contextPath}/sku/updateSku",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
	   				window.location.href = "${contextPath}/sku/query"
	   				editDialog.close();
			}else {
				layer.alert(result.message);
	   		}
		   }
		});						
	});

	var selectId;
	var add_setting_brand = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/brand/getTreeNode",
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
	
	var add_setting_category = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/category/getTreeNode",
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
			onAsyncSuccess: zTreeOnAsyncSuccess,
	        onClick: onClick
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
    	if(selectId == 'brand') {
			$("#brandId").attr("value", treeNode.id);
        }
        if(selectId == 'category') {
			$("#categoryId").attr("value", treeNode.id);
        }
        hideMenu();
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
        if(objName == 'brand') {
        	$("#menuContent_brand").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        };if(objName == 'category') {
        	$("#menuContent_category").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        };
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="brand") {
     	   $("#menuContent_brand").fadeOut("fast");
    	}
    	if(selectId =="category") {
     	   $("#menuContent_category").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="brand"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_brand" || $(event.target).parents("#menuContent_brand").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="category") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_category" || $(event.target).parents("#menuContent_category").length > 0)) {
	            hideMenu();
        	}
    	}
    }
    
    $(document).ready(function(){
		$.fn.zTree.init($("#add_treeDemo_brand"), add_setting_brand);
	    $.fn.zTree.init($("#add_treeDemo_category"), add_setting_category);
	});   
</script>
