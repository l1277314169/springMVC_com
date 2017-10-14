<#include "/common/head.ftl" />
<form id="dataForm" method="post">
<input type="hidden" id="roleId" name="roleId" value="${roleId!''}">
<input type="hidden" id="checkedWebOld" name="checkedWebOld">
<input type="hidden" id="checkedWebNew" name="checkedWebNew">
<input type="hidden" id="checkedMobileOld" name="checkedMobileOld">
<input type="hidden" id="checkedMobileNew" name="checkedMobileNew">
<input type="hidden" id="checkedDataOld" name="checkedDataOld">
<input type="hidden" id="checkedDataNew" name="checkedDataNew">
<table class="roles_table">
	<thead><tr>
		<th>WEB 权限</th>
		<th>手机权限</th>
		<th>数据权限</th>
	</tr></thead>
	<tr>
		<td>
			<div class="zTreeRoleBackground left">
				<ul id="treeWeb" class="ztree treeRolePrivileges"></ul>
			</div>
		</td>
		<td>
			<div class="zTreeRoleBackground left">
				<ul id="treeMobile" class="ztree treeRolePrivileges"></ul>
			</div>
		</td>
		<td>
			<div class="zTreeRoleBackground left">
				<ul id="treeData" class="ztree treeRolePrivileges"></ul>
			</div>
		</td>
	</tr>
   <tr>
		<td colspan="3" class="buttons">
				<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
				<button id="savetButton" type="button" class="btn btn-success margin-left-18px">保存</button>
		</td>
   </tr>
</table>
</form>

<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript">
	var str_web,str_old_web,str_mobile,str_old_mobile,str_data,str_old_data;
	var roleId = $('#roleId').val();
	var settingWeb = {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/clientRoles/getWebTreeNode/"+roleId,
			error : function() {  
                 alert('亲，组织架构加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType:{ "Y" : "", "N" : "" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess
		},
		view: {
		}
	};
	
	var settingMobile = {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/clientRoles/getMobileTreeNode/"+roleId,
			error : function() {  
                 alert('亲，组织架构加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType:{ "Y" : "", "N" : "" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess
		},
		view: {
		}
	};
	
	var settingData = {
		async : {
			enable : true,
			type: "post",
			url : "${contextPath}/clientRoles/getDataTreeNode/"+roleId,
			error : function() {  
                 alert('亲，组织架构加载失败！');  
            }, 
			autoParam : [ "id" ]
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType : {"Y": "ps", "N": "ps"}
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess
		},
		view: {
		}
	};
	
	
	//异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var treeWeb = $.fn.zTree.getZTreeObj("treeWeb");
		var checkedNodesWeb = treeWeb.getCheckedNodes(true);
		str_old_web = getCheckedNodesWithStr(checkedNodesWeb,'id');
		
		treeWeb.expandAll(true);
	    var treeMobile = $.fn.zTree.getZTreeObj("treeMobile");
		var checkedNodesMobile = treeMobile.getCheckedNodes(true);
		str_old_mobile = getCheckedNodesWithStr(checkedNodesMobile,'cId');
		
		treeMobile.expandAll(true);
	    var treeData = $.fn.zTree.getZTreeObj("treeData");
		var checkedNodesData = treeData.getCheckedNodes(true);
		str_old_Data = getCheckedNodesWithStr(checkedNodesData,'id');
		treeData.expandAll(true);
		
		$("#checkedWebOld").val(str_old_web);
		$("#checkedMobileOld").val(str_old_mobile);
		$("#checkedDataOld").val(str_old_Data);
	}
	
	function getCheckedNodesWithNotHalf(nodes,id){
		var str = "";
		var dom = id;
		var str_tmp = eval(nodes);
		var len = str_tmp.length;
		$.each(str_tmp, function(i) {
	    	if(!this.getCheckStatus().half){
		        if(i != len-1) {
		        	if(dom =='cId') {
	    				str += this.cId+",";
	    			}else {
	    				str += this.id+",";
	    			}
		        } else {
		        	if(dom =='cId') {
    					str += this.cId;
	    			}else {
	    				str += this.id;
	    			}
		        }
	    	}
	    });
	    return str;
	}
	
	function getCheckedNodesWithStr(nodes,id){
		var str = "";
		var dom = id;
		var str_tmp = eval(nodes);
		var len = str_tmp.length;
		$.each(str_tmp, function(i) {
    		if(i != len-1) {
    			if(dom =='cId') {
    				str += this.cId+",";
    			}else {
    				str += this.id+",";
    			}
	        } else {
	        	if(dom =='cId') {
    				str += this.cId;
    			}else {
    				str += this.id;
    			}
	        }
	    });
	    return str;
	}
	
	$(document).ready(function () {
		$.fn.zTree.init($("#treeWeb"), settingWeb);
		$.fn.zTree.init($("#treeMobile"), settingMobile);
		$.fn.zTree.init($("#treeData"), settingData);
		
		$('#savetButton').click(function(){
			$(this).attr("disabled","disabled");
			var treeWeb = $.fn.zTree.getZTreeObj("treeWeb");
			var treeMobile = $.fn.zTree.getZTreeObj("treeMobile");
			var treeData = $.fn.zTree.getZTreeObj("treeData");
			
			var checkedNodesWeb = treeWeb.getCheckedNodes(true);
			var checkedNodesMobile = treeMobile.getCheckedNodes(true);
			var checkedNodesData = treeData.getCheckedNodes(true);

			str_web = getCheckedNodesWithStr(checkedNodesWeb,'id');
			str_mobile = getCheckedNodesWithStr(checkedNodesMobile,'cId');
			str_data = getCheckedNodesWithNotHalf(checkedNodesData,'id');

			$("#checkedWebNew").val(str_web);
			$("#checkedMobileNew").val(str_mobile);
			$("#checkedDataNew").val(str_data);
			
		    //alert("str_web:"+str_web +"--str_mobile:"+str_mobile+"--str_data:"+str_data);
			$.ajax({
				url : "${contextPath}/clientRoles/updateRolePrivileges",
				type : "post",
				dataType:"json",
				async: false,
				data :$("#dataForm").serialize(),
				success : function(result) {
					if(result.code=="success"){
						layer.alert(result.message,function(){
							//showRolePrivileges.close();
						parent.document.location.href = "${contextPath}/clientRoles/query?mod=conf";
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
		});
     })
</script>
