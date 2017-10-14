<html>
<head>
<#include "/common/head.ftl" />
<title>分销关联产品页面</title>
<#include "/common/foot.ftl" />
</head>
<body>
<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate" method="post">
		<div class="control-group">
			<div class="fl">
				<label class="control-label " for="skuNo">产品编号：</label>
				<div class="controls">
					<input type="text" class="input-medium" name="skuNo" value="${skuNo!''}">
				</div>
			</div>
			<div class="fl">
				<label class="control-label " for="category">产品名称：</label>
				<div class="controls">
					<input type="text" class="input-medium" name="skuName" value="${skuName!''}">
				</div>
			</div>
			<div class="fl">
				<label class="control-label " for="category">品牌：</label>
				<div class="controls">
					<input  type="text" id="brand" name="brand" class="input-medium" value="${brand!''}"  readonly onclick="showMenu(this.id);"/> 
				</div>
			</div>
			<div class="fl">
				<label class="control-label " for="category">品类：</label>
				<div class="controls">
					<input  type="text" id="category" name="category" class="input-medium" value="${category!''}"  readonly onclick="showMenu(this.id);"/>
				</div>
			</div>
		 </div> 
		 <div>
			<label class="control-label" style="margin-left: 10px;"><nobr>您已选择：<span id ="checkCount" style="color:red;padding-right: 8px;">0</span>个产品</nobr></label>
			<label class="control-label" style="padding-left: 20px;">所有产品：<input type="checkbox" id="all" name="all"></label>
			<div class="form-actions">
				<input type="submit" value="查询" style="margin-right:30px;" class="btn btn-info fr" id="search_btn">
		 	</div> 
		 </div>
			<input type="hidden" id="categoryId" name="categoryId" value="${categoryId!''}">
			<input type="hidden" id="brandId" name="brandId" value="${brandId!''}">
			<input type="hidden" id="count" value="${count}">
	</form>
<div class="div_list_show">
	<table class="table table-bordered data-table" id="c_list">
			<tr>
				<th><input type="checkbox" id="checkAll" name="checkAll"></th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>品牌</th>
				<th>品类</th>
				<th>单价</th>
			</tr>
		<#list pageParam.items as sku>
			<tr>
				<td><input name="chkItem" type="checkbox" class="checkboxSku" value="${sku.skuId!''}" /></td>
				<td>${sku.skuNo!''}</td>
				<td title="${sku.skuName!''}">
						<#if sku.skuName?? && sku.skuName?length gt 15>
							${sku.skuName?substring(0,15)}...
						<#else>
							${sku.skuName!''}
						</#if>
				</td>
				<td>${sku.brandName!''}</td>
				<td>${sku.categoryName!''}</td>
				<td>${sku.price!''}</td>
			</tr>
		</#list>
	</table>
	<#if pageParam.items?exists>
		<div class="paging" >
		   ${pageParam.getPagination()}
		</div> 
	</#if>
	<div style="text-align:center;">
		<button data-dismiss="dialog" id="cancel" onClick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">关闭</button>
	</div>
</div>
<div id="menuContent_br" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_brand" class="ztree" style="margin-top: 0; width: 160px;">
	</ul>
</div>
<div id="menuContent_ca" class="menuContent" style="display: none; position: absolute;">
	<ul id="treeDemo_category" class="ztree" style="margin-top: 0; width: 160px;">
	</ul>
</div>
</body>
</html>
<script type="text/javascript">
var array = $(window.parent.document).find("#skuIds").val().split(",");
 if(array == ","){
 	$("#checkCount").html(0);
 }else{
	$("#checkCount").html(array.length-1);
 }
var skuIds;
$(document).ready(function(){
	 $.fn.zTree.init($("#treeDemo_category"), setting_ca);
	 $.fn.zTree.init($("#treeDemo_brand"), setting_br);
	 $('#cancel').click(function(){
	 	parent.relationDialog.close();
	 });
	  $("[name = chkItem]:checkbox").each(function () {
    	for (var index in array) {
            if ($(this).val() == array[index]){
                $(this).attr("checked", "checked");
	            break;
	        }
	    }
	  });
	  //翻页时单选全部选中 全选就选中
	  if($('.checkboxSku:checked').length == $('.checkboxSku').length){
			$('#checkAll').attr("checked",true);
		}else{
			$('#checkAll').attr("checked",false);
		}
		 //翻页所有sku选中
		if($('#checkAll').prop("checked")){
			if($('#checkCount').html() == $('#count').val()){
				$('#all').attr("checked",true);
			}
		}else{
			$('#all').attr("checked",false);
		}
	 //单选
	 $("[name = chkItem]:checkbox").bind("click",function(){
	 	array = $(window.parent.document).find("#skuIds").val().split(",");
		if($(this).attr('checked')){
			$(this).attr("checked",true);
			array.push($(this).val());
			$('#checkCount').html(array.length-1);
		}else{
			$(this).attr("checked",false);
			array.splice($.inArray($(this).val(),array),1);
			$('#checkCount').html(array.length-1);
		}
		skuIds = array.join(",");
		$(window.parent.document).find("#skuCheckbox").html(array.length-1);
		$(window.parent.document).find("#skuIds").val(skuIds);
		if($('.checkboxSku:checked').length == $('.checkboxSku').length){
			$('#checkAll').attr("checked",true);
		}else{
			$('#checkAll').attr("checked",false);
		}
		 //翻页所有sku选中
		if($('#checkAll').prop("checked")){
			if($('#checkCount').html() == $('#count').val()){
				$('#all').attr("checked",true);
			}
		}else{
			$('#all').attr("checked",false);
		}
	});
	//全选
	$('#checkAll').click(function(){
		array = $(window.parent.document).find("#skuIds").val().split(",");
		if($(this).prop("checked")){
			$('.checkboxSku').each(function(){
				if(!$(this).prop("checked")){
					array.push($(this).val());
					$("#checkCount").html(array.length-1);
					$(this).attr("checked",true);
				};	
			})
		}else{
			$('.checkboxSku').each(function(){
				if($(this).prop("checked")){
					array.splice($.inArray($(this).val(),array),1);
					$("#checkCount").html(array.length-1);
					$(this).attr("checked",false);
				}
			})
		}
		skuIds = array.join(",");
		$(window.parent.document).find("#skuCheckbox").html(array.length-1);
		$(window.parent.document).find("#skuIds").val(skuIds);
		$('#checkAlls').attr("checked",false);
		 //翻页所有sku选中
		if($('#checkAll').prop("checked")){
			if($('#checkCount').html() == $('#count').val()){
				$('#all').attr("checked",true);
			}
		}else{
			$('#all').attr("checked",false);
		}
	});
});

var selectId;
 //品类树
    var setting_ca = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/category/getTreeNode",
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
    
    var setting_br = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/brand/getTreeNode",
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
	};
	
    function showMenu(objName) {
    	selectId = objName;
    	var obj = $("#"+objName);
        var objOffset = obj.position();
        if(objName == 'brand') {
        	$("#menuContent_br").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if(objName == 'category') {
			$("#menuContent_ca").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}else{
       	
       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="brand") {
     	   $("#menuContent_br").fadeOut("fast");
    	}
    	if(selectId =="category"){
    		$("#menuContent_ca").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="brand"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_br" || $(event.target).parents("#menuContent_br").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="category"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_ca" || $(event.target).parents("#menuContent_ca").length > 0)) {
	            hideMenu();
        	}
    	};
    }
    
//所有产品
$('#all').click(function(){
	if($(this).prop("checked")){
		//父页面的保存sku全部清空
		$(window.parent.document).find("#skuIds").val("");
		$(window.parent.document).find("#skuCheckbox").html($('#count').val());
		$('#checkCount').html($('#count').val());
		$('.checkboxSku').attr("checked",true);
		$('#checkAll').attr("checked",true);
		//关联所有sku
		$.ajax({
			type : "post",
			url : "${contextPath}/skuDistribution/relevanceAllSku",
			dataType:"json",
			data:$('#searchForm').serialize(),
			success : function(result) {
				$(window.parent.document).find("#skuIds").val(","+result);
			}
		});
	}else{
		$(window.parent.document).find("#skuCheckbox").html(0);
		$(window.parent.document).find("#skuIds").val("");
		$('#checkCount').html(0);
		$('.checkboxSku').attr("checked",false);
		$('#checkAll').attr("checked",false);
	}
});
</script>