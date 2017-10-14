<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>组织架构维护</TITLE>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/nomore-style.css"/>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />


<script language="JavaScript">
	var native_name;
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
		data: {
			simpleData: {
				enable: true
			}
		},
		edit: {
			enable: true,
			drag: {//目前禁止拖拽
				isCopy: false,
				isMove: false,
				prev: false,
				next: false,
				inner: false
			},
			editNameSelectAll: true,
			<@shiro.lacksPermission name="C2M1F3">
			showRemoveBtn: false,
			</@shiro.lacksPermission>
			<@shiro.hasPermission name="C2M1F3">
			showRemoveBtn: setRemoveBtn,
			</@shiro.hasPermission>
			<@shiro.lacksPermission name="C2M1F2">
			showRenameBtn: false,
			</@shiro.lacksPermission>
			removeTitle: "删除节点",
			renameTitle: "编辑节点名称"
		},
		callback: {
			beforeRemove: zTreeBeforeRemove,
			onRemove: zTreeOnRemove,
			beforeRename: zTreeBeforeRename,
			onRename: zTreeOnRename,
			onClick: zTreeOnClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
		},
		view: {
			<@shiro.hasPermission name="C2M1F1">
				addHoverDom: addHoverDom,
			</@shiro.hasPermission>
				removeHoverDom: removeHoverDom
		}
	};
	//异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
	};
	//设置所有的父节点不显示删除按钮
	function setRemoveBtn(treeId, treeNode) {
		return !treeNode.isParent;
	};
	
	//删除操作
	function zTreeBeforeRemove(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		var confirmFlag =confirm("确认删除[ " + treeNode.name + " ]吗？" )
		var confirmVal = false;
		if(confirmFlag){
			var data = {id:treeNode.id};
			$.ajax({
				async: false,
				type: "post",
				cache:false,
				data:data,
				url: "${contextPath}/clientStructure/deleteStructureFromTree ",
				success: function(result){
					if(result.code == "success" ){
						confirmVal = true;
					} else{
						alert('亲，删除失败！');
					}
				},
				error: function(){
					alert('亲，删除失败！');
				}
			});
		}
		return confirmVal;
	};
	
	//点击树节点时，在右侧展示界面异步加载该节点的数据，维护其他信息。
	function zTreeOnClick(event, treeId, treeNode) {
		//alert(treeNode.id + ", " + treeNode.name);
	    var data = {id:treeNode.id};
	    //隐藏层
	    $("#structure_edit").hide();
	    $.ajax({
			async: false,
			type: "post",
			dataType: 'json',
			cache:false,
			data:data,
			url: "${contextPath}/clientStructure/getJsonClientStructureById",
			success : function(result){
				$("#clientStructureId").val(result.clientStructureId);
				$("#structureName").html(result.structureName);
				if(result.parentStructureName != null && result.parentStructureName!= "") {
					$('#parentId').attr("value",result.parentId);
					zTreeOnParentAsyncSuccess(event, treeId, treeNode);
					$("#parentStructureName").html(result.parentStructureName);
					$("#tr_parentStructureName").css('display', '');
				} else {
					$("#parentStructureName").html('');
					$("#tr_parentStructureName").css('display', 'none');
				}
				$("#structureNameEn").val(result.structureNameEn);
				$("#structureNo").val(result.structureNo);
				$("#remark").val(result.remark);
				$("#selectLeaderIds").val("");
				$("#selectLeaderNames").val("");
			},
			error : function() {
				//alert('亲，加载详细部门数据出错！');
			}
		});
		//数据重新装载后，显示层
		$("#structure_edit").show();
		//清楚上一次的表单验证结果
		$(".error").html('');
	};
	
	//删除节点时，弹出被删除的节点的 tId 以及 name 信息
	function zTreeOnRemove(event, treeId, treeNode) {
		$("#structure_edit").hide();
		alert('亲，删除成功！');
	};
	var treeNodes;
	//修改名称后，弹出被修改名称的节点的 tId 以及 name 信息
	function zTreeOnRename(event, treeId, treeNode, isCancel) {
		if(native_name == treeNode.name){
			return;
		}
		var data = {id:treeNode.id,name:treeNode.name};
		$.ajax({
			async: false,
			type: "post",
			data:data,
			cache:false,
			url: "${contextPath}/clientStructure/updateStructureFromTree",
			success : function(result){
				if(result.code == "success" ){
					alert('操作成功!');
				}else{
					alert('亲，操作失败，请稍后再试！');
				}
			},
			error : function() {
				alert('亲，出现错误！修改组织架构名称失败！');
			}
		});
		//刷新右侧内容
		$("#clientStructureId").val(treeNode.id);
		$("#structureName").html(treeNode.name);
	};
	
	//禁止修改的名称的长度大于15
	function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
		if (newName.length == 0 || newName.indexOf("请输入名称")>=0) {
			alert('亲，请输入组织架构名称!');
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			setTimeout( function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		if(newName.length > 15){
			alert('亲，组织架构名称过长！');
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			setTimeout( function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		return checkRepaceName(newName,treeNode);
	};
	function checkRepaceName(newName,treeNode){
		var num;
		var data = {name:newName,oldName:treeNode.name};
		$.ajax({
			async: false,
			type: "post",
			data:data,
			cache:false,
			url: "${contextPath}/clientStructure/validateStructure",
			success : function(result){
				if(result.code == "exist"){
				layer.alert(result.message);
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					setTimeout( function(){zTree.editName(treeNode)}, 10);
					num = false;
					return false;
				}else{
					native_name = treeNode.name;
					num = true;
					return true;
				}
			}
		});
		return num;
	}
	
	//添加组织架构  
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" +treeNode.tId).length>0 || treeNode.level == 4) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加分类' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" +treeNode.tId);
		if (btn) btn.bind("click" , function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var data = {pId:treeNode.id};
			$.ajax({
				async: false,
				type: "post",
				cache:false,
				data: data,
				url: "${contextPath}/clientStructure/addStructureFromTree",
				success : function(id){
					if(id != "" && id != 0){
						treeNodes = zTree.addNodes(treeNode, {id:(id), pId:treeNode.id, name:"请输入名称" });
						if (treeNodes) {
							zTree.editName(treeNodes[0]);
						}
					} else {
						alert('亲，出现错误！新增组织架构失败!');
					}
				},
				error : function(){
					alert('亲，出现错误！新增组织架构失败!');
				}
			});
		return false;
	   });
	}
	
	
	
	//鼠标移开按钮消失  
     function removeHoverDom(treeId, treeNode) {  
           $( "#addBtn_"+treeNode.tId).unbind().remove();  
     };
	
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting);
		$.fn.zTree.init($("#treeDemoParent"), setting_p);
		//onloadZTree();
		$("a.addTop").bind("click",function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var data = {pId:0};
			var treeNodes;
			$.ajax({
				async: false,
				type: "post",
				data: data,
				url: "${contextPath}/clientStructure/addStructureFromTree",
				success : function(id){
					if(id != "" && id != 0){
						treeNodes = zTree.addNodes(null, {id:(id), pId:null, name:"请输入名称" });
						if (treeNodes) {
							zTree.editName(treeNodes[0]);
						}
					} else {
						alert('亲，出现错误！新增组织架构失败!');
					}
				},
				error : function(){
					alert('亲，出现错误！新增组织架构失败!');
				}
			});
		});
		
		$("#selectLeaders").bind("click",function(){
			var id = $("#clientStructureId").val();
			var url = "${contextPath}/clientUser/showSelectLeaders?id="+id;
			// popTypeDialog = new xDialog(url, {"id":id}, {title:"关联部门领导",iframe:true,width:800,height:500});
            layer.open({
			    type: 2,
			    title: '关联部门领导',
			    closeBtn: 1,
			    iframe:true,
			    area: ['800px', '500px'],
			    content: url
			});
			return false;	
		});
	});

	function onloadZTree(){  
        var ztreeNodes;  
       $.ajax( {  
            async : true, //是否异步  
            cache : false, //是否使用缓存  
            type : 'post', //请求方式,post  
            dataType : "json", //数据传输格式  
            url : "${contextPath}/clientStructure/getTreeNode", //请求链接  
            error : function() {  
                 alert('亲，网络有点不给力呀！');  
            },  
            success : function(data) {  
                 ztreeNodes = eval( "["+data+"]" ); //将string类型转换成json对象  
                 $.fn.zTree.init($( "#treeDemo"), setting, ztreeNodes);  
                 zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo" );
                 $( "#selectAll").bind("click" , selectAll);  
                 expandAllFlag();  
                 addClick();  
            }  
       });  
 	}
</script>
</HEAD>
<body class="main_body">
    <#assign privCode="C2M1">
		<#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">基本数据管理</a>
					<a class="linkPage active" href="${contextPath}/clientStructure/manage">组织架构维护</a>
				</div>
		 	</div>
	<div style="height:91%;width:97%;margin-top:20px; margin-left:20px; float:left;" class="div_border_ddd">
		<div style="height:90%;width:30%;float:left;" class="div_border_ddd margin_20_20">
			<div style="height:30px;vertical-align:middle;border-bottom:1px solid #DDD;" class="padding_5_8">
				<span class="bold_font">${clientName}</span>
				<span style="float:right">﹀</span>
			</div>
			<div class="content_wrap">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree treemanage"></ul>
				</div>
			</div>
		</div>
		<div id="structure_edit" style="margin-top:20px; height:90%;width:62%;float:left;display:none" class="div_border_ddd">
			<form id="dataForm" method="post">
				<input type="hidden" id="clientStructureId" name="clientStructureId" value="">
				<input type="hidden" id="selectLeaderIds" name="selectLeaderIds" value="">
				<input type="hidden" id="selectLeaderNames" name="selectLeaderNames" value="">
		        <table class="table_white_bg">
		            <tbody>
		            	<tr>
		                	<td class="td_label">部门名称：</td><td class="td_control"><span id="structureName"></span></td>
		                </tr> 
		            	<tr id="tr_parentStructureName">
		                	<td class="td_label">上级部门：</td><td class="td_control">
			               		 <input type="text" id="parentName" name="parentName" readonly="readonly" onclick="showMenu();">
			                	<input type="hidden" id="parentId" name="parentId" >
		                	</td>
		                </tr> 
		            	<tr>
		                	<td class="td_label">部门名称(英文)：</td><td class="td_control"><input type="text" id="structureNameEn" name="structureNameEn"></td>
		                </tr> 
		            	<tr>
		                	<td class="td_label">部门编号：</td><td class="td_control"><input type="text" id="structureNo" name="structureNo"></td>
		                </tr> 
						<tr>
		                    <td class="td_label">备注：</td>
		                    <td class="td_control">
		                    	<textarea id="remark" rows=2 style="width: 350px" maxlength=200 name="remark" placeholder="不超过200个字"></textarea>
		                    </td>
		                </tr>
						<!--<tr>
		                    <td class="td_label">部门领导：</td>
		                    <td class="td_control"><button id="selectLeaders" class="btn_cancel" type="button" data-dismiss="dialog">设置</button></td>
		                </tr>-->
		            </tbody>
		            <tr>
	                    <td></td>
	                    <td class="td_buttons_left">
	                    <@shiro.hasPermission name="C2M1F2">
							<button type="button" id="savetButton"  class="btn btn-success margin-left-18px">保存</button>
						</@shiro.hasPermission>
						</td>
	                </tr>
		        </table>
			</form>
		</div>
	</div>
	<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemoParent" class="ztree" style="margin-top: 0; width: 180px;">
		</ul>
	</div>
   </div>
   <#include "/footer.ftl" />
</body>
<script>
$.ajaxSetup({
    complete:function(XMLHttpRequest,textStatus){
          if(textStatus=="parsererror"){
               layer.alert("登陆超时！请重新登陆！",function(){
                   window.location.href = '${contextPath}/login';
               });
          } else if(textStatus=="error"){
              layer.alert("请求超时！请稍后再试！");
          }
    }
});

//编辑部门
$(document).ready(function() {
	$("#savetButton").bind("click",function(){
		//验证
		if(!$("#dataForm").validate({
			rules : {
				structureNameEn: {
					isEnglishContain : true,
					maxlength:50
				},
				structureNo: {
					stringCheck : true,
					maxlength:25
				},
				remark: {
					maxlength:200
				}
			}
			}).form()){
			return false;
		}
		$.ajax({
		url : "${contextPath}/clientStructure/updateClientStructure",
		type : "post",
		dataType:"json",
		async: false,
		data : $("#dataForm").serialize(),
		success : function(result) {
		   if(result.code=="success"){
				layer.alert(result.message,function(){
	   				 window.location.reload();
	   			});
			}else {
				layer.alert(result.message);
	   		}
		   }
		});						
	});
});

var setting_p = {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/clientStructure/getTreeNodeWithPer",
			error : function() {
				layer.alert('亲，组织架构加载失败！');
            }
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
			onAsyncSuccess: zTreeOnParentAsyncSuccess
        }
    };
    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemoParent");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#parentName").attr("value", treeNode.name);
		$("#parentId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnParentAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemoParent");
		zTree.expandAll(true);
		var node = zTree.getNodeByParam("id", $("#parentId").val(), null);
		if(node != null){
			$("#parentName").val(node.name);
		}
	};
    function showMenu() {
        var structureObj = $("#parentName");
        var clientStructureOffset = $("#parentName").position();
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
</script>
</HTML>