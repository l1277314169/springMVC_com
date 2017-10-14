<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<body class="main_body">
	<#assign privCode="C2M7">
		<#include "/base.ftl" />
		
		<div id="content"> 
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基础数据管理</a>
					<a class="linkPage active" href="${contextPath}/skuDistribution/showAddSkuDistribution">新增产品分销</a>
				</div>
		 	</div>

<form id="dataForm" method="post"  >
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
            	<td class="td_label"><i class="cc1">*</i>分组名称：</td>
	            	  <td  class="td_control">
		            	<input id="groupName"  type="text" name="groupName" class="input-medium"/><span id="exist" style="display:none;color:red;padding-left: 10;width:100px;">分组名称已存在!</span>
		              </td>
	              </tr>
	              <tr>
	            	  <td class="td_label"><i class="cc1">*</i>渠道：</td>
	            	  <td  class="td_control">
		            	<input id="channel"  type="text" name="channel" class="input-medium" value="${channel!''}"  readonly onclick="showMenu(this.id);"/> 
			            <input type="hidden" id="arg_channel_ids" name="channel" value="${channel!''}" />
					    <#assign channelFTL="arg_channel_ids">
					    <#include "/widgets/channel_widget.ftl" /></td>
		              </td>
	              </tr>
	              <tr>
	            	  <td class="td_label">连锁：</td>
	            	  <td  class="td_control">
		            	 <input id="chain" type="text" name="chain" class="input-medium" value="${chain!''}"  readonly onclick="showMenu(this.id);"/>  
	            	 	 <input type="hidden" id="arg_types" name="chain" value="${chain!''}" >
					 	 <#assign chainFTL="arg_types">
					     <#include "/widgets/chain_widget.ftl" />
	            	  </td>
            	  </tr>
            	  <tr>
	            	  <td class="td_label">经销商：</td>
	            	  <td  class="td_control">
		            	 <input id="distributor" type="text" name="distributor" class="input-medium" value="${distributor!''}"  readonly onclick="showMenu(this.id);"/>  
	            	  	 <input type="hidden" id="arg_types" name="distributor" value="${distributor!''}" >
					 	 <#assign distributorFTL="arg_types">
					     <#include "/widgets/jingxiaos_widget.ftl" />
	            	  </td>
            	</tr>
	               <tr>
	            	  <td class="td_label">关联SKU：</td>
	            	  <td  class="td_control">
		            	<button  type="button" id="skuId"  name="skuId"  class="btn btn-success" style="width:145px;">已关联<font id="skuCheckbox" color="#ff0000">0</font>个SKU</button>
		              </td>
	              </tr>
              	<tr>
              		<td class="td_label"></td>
	            	  <td  class="td_control">
		            	<button class="btn btn-danger" id="backButton" type="button">返回</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
		              </td>
		   		</tr>
            </tbody>
        </table>
	        <div id="menuContent_cl" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo_channel_add" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
			</div>
			<div id="menuContent_ch" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo_chain_add" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
			</div>
			<div id="menuContent_db" class="menuContent" style="display: none; position: absolute;">
				<ul id="treeDemo_distributor_add" class="ztree" style="margin-top: 0; width: 160px;">
				</ul>
			</div>
        <input type="hidden" id="channelId" name="channelId" value="">
        <input type="hidden" id="chainId" name="chainId" value="">
        <input type="hidden" id="distributorId" name="distributorId" value="">
        <input type="hidden" id="skuIds" name="skuIds">
</form>
</div>
		<#include "/footer.ftl" />
</body>
<script type="text/javascript">
var relationDialog;


	$("#backButton").bind("click",function(){
		$('#breadcrumb a:last-child',window.top.document).remove();
		window.location.href = "${contextPath}/skuDistribution/query";
	});


$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo_distributor_add"), setting_db);
	$.fn.zTree.init($("#treeDemo_channel_add"), setting_cl);
	$.fn.zTree.init($("#treeDemo_chain_add"), setting_ch);
	//分组的唯一性
	$('#groupName').change(function(){
		var groupName = $('#groupName').val();
		$.ajax({
			url : "${contextPath}/skuDistribution/onlySkuDistribution",
			type : "post",
			dataType:"json",
			async: false,
			data : {groupName:groupName},
			success : function(result) {
		   		if(result.code == "exist"){
		   			$('#exist').show();
		   		}else{
		   			$('#exist').hide();
		   		}
		   }
		});
	});
	//保存
	$("#savetButton").die().live("click",function(){
		$(this).attr("disabled","disabled");
		
		//验证
		if(!$("#dataForm").validate({
		   rules : {
					groupName: {
						required: true,
						maxlength: 25,
						isContainsSpecialChar:true,
						
					},
					channel: {
						required: true,
					},
				}
		}).form()){
			$("#savetButton").removeAttr("disabled");
			return;
		}
		
		var skuIds = $('#skuIds').val();
		if(skuIds.substring(0,1) == ","){
			var oldStr = skuIds.substring(1,skuIds.length);
			$('#skuIds').val(oldStr);
		}
		if($("#exist").is(":hidden") == false){
			$("#savetButton").removeAttr("disabled");
			return false;
		}
		$.ajax({
		url : "${contextPath}/skuDistribution/addSkuDistribution",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
				layer.alert(result.message,function(){
					//window.location.reload();
	   				//addDialog.close();
	   					parent.document.location.href = "${contextPath}/skuDistribution/query";
                    	parent.layer.closeAll('iframe');
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
	});
	
	function confirmAndRefresh(result){
		if (result.code == "success") {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				$("#searchForm").submit();
			}});
		}else {
			pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
				//layer.alert(result.message);
			}});
		}
	}
	//关联sku
	$('#skuId').click(function(){
		var url = "${contextPath}/skuDistribution/showRelationDialog";
		//relationDialog = new xDialog(url, {}, {title:"产品分销关联SKU",iframe:true,width:800,height:660});
		//return false;
		 layer.open({
			    type: 2,
			    title: '产品分销关联SKU',
			    closeBtn: 1,
			    area: ['860px', '680px'],
			    content: url
			});
	});
});
	var selectId;
	//渠道树
	var setting_cl = {
		async : {
			enable : true,
			type: "post",
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
    //连锁树
    var setting_ch = {
		async : {
			enable : true,
			type: "post",
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
    
    //经销商树
    var setting_db= {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/distributor/getTreeNode",
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
    
	$("#channel").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#channel").val(""); 
			$("#channelId").val(""); 
			}; 
	});
	$("#chain").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#chain").val(""); 
			$("#chainId").val(""); 
			}; 
	});
	$("#distributor").keydown(function(e){ 
			if(e.keyCode == 8 || e.keyCode == 46){ 
			$("#distributor").val(""); 
			$("#distributorId").val(""); 
			}; 
	});
			
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
    		$("#chainId").attr("value", treeNode.id);
		}
        if(selectId == 'channel') {
        	$("#channelId").attr("value", treeNode.id);
        }
  		if(selectId == 'distributor'){
        	$("#distributorId").attr("value", treeNode.id);
  		}
        hideMenu();
         return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.expandAll(true);
		if(treeId == "treeDemo_channel_add"){
			var node = zTree.getNodeByParam("id", $("#channelId").val(), null);
			if(node != null){
				$("#channel").val(node.name);
			}
		}
		if(treeId == "treeDemo_chain_add"){
			var node = zTree.getNodeByParam("id", $("#chainId").val(), null);
			if(node != null){
				$("#chain").val(node.name);
			}
		}
		if(treeId == "treeDemo_distributor_add"){
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
        	$("#menuContent_ch").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        } else if (objName == 'channel'){
        	$("#menuContent_cl").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
        }else if(objName == 'distributor'){
			$("#menuContent_db").css({ left: objOffset.left + "px", top: objOffset.top + obj.outerHeight() + "px" }).slideDown("fast");
       	}else{
       	
       	}
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
    	if(selectId =="chain") {
     	   $("#menuContent_ch").fadeOut("fast");
    	}
    	if(selectId =="channel") {
     	   $("#menuContent_cl").fadeOut("fast");
    	}
    	if(selectId =="distributor"){
    		$("#menuContent_db").fadeOut("fast");
    	}
        $("body").unbind("mousedown", onBodyDown);
    }        
    function onBodyDown(event) {
    	if(selectId =="chain"){
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_ch" || $(event.target).parents("#menuContent_ch").length > 0)) {
	            hideMenu();
        	}
    	};
    	if(selectId =="channel") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_cl" || $(event.target).parents("#menuContent_cl").length > 0)) {
	            hideMenu();
        	}
    	}
    	if(selectId =="distributor") {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent_db" || $(event.target).parents("#menuContent_db").length > 0)) {
	            hideMenu();
        	}
    	}
    }
</script>
