<#include "/common/head.ftl" />
 <#include "/common/foot.ftl" />
<form id="dataForm">
		<input type="hidden" name="clientId" value="${clientId}">
        <table class="pup_input" style="width:600px;">
            <tbody>
            	<tr>
                    <td class="p_label">用户名：
                	</td>
                    <td><input type="text" name="loginName">
                    </td>
             
             
              <td class="p_label">身份证号：</td>
                    <td>
                    	<input type="text" name="idcard">
                    </td>
                   
                </tr> 
                <tr>
                	<td class="p_label">用户编号：
                	</td>
                    <td><input type="text" name="userNo" required=true>
                 <div id="positionNameError"></div>
                    </td>
                    <td class="p_label">姓名：</td>
                    <td>
                    	<input type="text" name="name" required=true>
                    </td>
                </tr> 
				<tr>
                      <td class="p_label">手机号：</td>
                    <td>
                    	<input type="text" name="mobileNo">
                    </td>
                     <td class="p_label">地址：</td>
                    <td>
                    	<input type="text" name="addr">
                    </td>
                </tr>
              <tr>
                 <td class="p_label">性别：</td>
                   <td> <select  name="sex" class="p_label">
                    <option value="">--请选择--</option>
             	<#list sexType as option>
        		<option value="${option.getKey ()}">${option.getCnName()}</option>
        	</#list>
                 </select>
                 </td>
                 <td class="p_label">所在省：</td>
                  <td> <select  name="provinceId" class="p_label" id="province"  onChange="changeProvince()">
                    <option value="">--请选择--</option>
                 <option value="1">湖南</option>
                 <option value="1">湖北</option>
                 </select>
                 </td>
               </tr>
                 <tr>
                <td class="p_label">人员状态：</td>
                   <td> <select class="p_label" name="status">
                    <option value="">--请选择--</option>
              <#list position as option>
        		<option value="${option.getKey ()}">${option.getCnName()}</option>
        	</#list>
                 </select>
                 </td>
                    <td class="p_label">所在市：</td>
                  <td> <select  name="cityId" class="p_label" id="city">
                    <option value="">--请选择--</option>
                 <option value="1">湖南</option>
                 <option value="">湖北</option>
                 </select>
                 </td>
                 </tr>    
             <tr>
               <td class="p_label">职位：</td>
                    <td>
                    	<select name="clientPositionId">
                    	<option value="">--请选择--</option>
                    		<#list cpList as option>
                    			<option value="${option.clientPositionId!''}">${option.positionName!''}</option>
                    		</#list>
                    	</select>
                    </td>
                    
                        <td class="p_label">所在县：</td>
                  <td> <select  name="districtId" class="p_label" id="district">
                    <option value="">--请选择--</option>
                 <option value="1">湖南</option>
                 <option value="1">湖北</option>
                 </select>
                 </td>
             </tr>
                <tr>
                      <td class="p_label">电话：</td>
                    <td >
                    	<input type="text" name="telephoneNo">
                    </td>
                    
                    <td class="p_label">权限控制：</td>
                    <td>
                    	<input type="text" name="roles">
                    </td>
                      
                 
                </tr>
                
                 <tr>
                    
                     <td class="p_label">邮编：</td>
                    <td>
                    	<input type="text" name="postcode">
                    </td>
                    
                      
                    <td class="p_label">所在部门：</td>
                    <td>
                    	<input type="text" id="clientStructureId" name="clientStructureId" readonly value="${clientStructureId}"   Onfocus="showMenu();" >
                    </td>
               
                </tr>
                <tr>
                <td class="p_label">帐号状态：</td>
                    <td>
                    	<select name="isActivation">
                    	<option value="">--请选择--</option>
                    			<#list state as option>
                    			<option value="${option.getKey ()}">${option.getCnName()}</option>
                    		</#list>
                    	</select>
                    </td>
        
                </tr>
    
               <tr>
                 <td class="p_label">备注：</td>
                   <td  colspan="3">
                    	<textarea  style="width: 300px;" maxlength=200 name="remark"></textarea> 不超过200个字
                    </td>
            
               </tr>
            </tbody>
        </table>
</form>
<div class="bottompage">
<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
<button class="btn_ok" id="savetButton">保存</button>
</div>
<div id="menuContent" class="menuContent" style="display: none;">
		<ul id="treeDemo" class="ztree" style="margin-left:90px ;margin-bottom :500px width: 160px;">
		</ul>
</div>
<script>
//新增职位
$("#savetButton").bind("click",function(){
	//验证
	if(!$("#dataForm").validate().form()){
		return;
	}
	$.ajax({
	url : "${contextPath}/clientUser/addClientUser",
	type : "post",
	dataType:"json",
	async: false,
	data : $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
			layer.alert(result.message,function(){
   				// addDialog.close();
   				 // window.location.href = "${contextPath}/clientUser/query"
   				 parent.document.location.href = "${contextPath}/clientUser/query";
                 parent.layer.closeAll('iframe');
   			});
		}else {
			layer.alert(result.message);
   		}
	   }
	});	
					
});





var setting = {
		async : {
			enable : true,
			type: "get",
			url : "${contextPath}/clientStructure/getTreeNode",
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
        var check = (treeNode && !treeNode.isParent);
        //if (!check) alert("只能选择城市...");
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return check;
    }
    function onClick(e, treeId, treeNode) {
    	//alert(treeNode.name);
        //var zTree = $.fn.zTree.getZTreeObj("treeDemo");            
        //var nodes = zTree.getSelectedNodes(),v = "";
        //nodes.sort(function compare(a, b) { return a.id - b.id; });
        //for (var i = 0, l = nodes.length; i < l; i++) {
        //    v += nodes[i].name + ",";
        //}
        //if (v.length > 0) v = v.substring(0, v.length - 1);
        //var structureObj = $("#structureSel");
		//structureObj.attr("value", v);
		$("#clientStructureId").attr("value", treeNode.name);
		$("#searchClientStructureId").attr("value", treeNode.id);
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
	};
	
    function showMenu() {
        var structureObj = $("#structureSel");
        var cityOffset = $("#structureSel").offset();
        $("#menuContent").css({ left: cityOffset.left + "px", top: cityOffset.top + structureObj.outerHeight() + "px" }).slideDown("fast");
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
        //$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo"), setting);
        
    });
    
    
    
    
    
    function changeProvince(cityId, districtId) {
		$("#city").empty();
		$("#district").empty();
		var value = $("#province").val();
		var parms = {
			"provinceId" : value,
		};
		$.ajax({
			type : "POST",
			url : "${contextPath}/clientUser/Province",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理 
				$.each(jsonData, function(index, item) {
					$("#city").append(
							"<option value='"+item.cityId+"'>" + item.city
									+ "</option>");
				});
				if (cityId != null) {
					$("#city").val(cityId);
				}
				changeCity(districtId);
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});

	}
	function changeCity(districtId) {
		$("#district").empty();
		var value = $("#city").val();
		var parms = {
			"cityId" : value,
		};
		$.ajax({
			type : "POST",
			url : "${ctx}/district/list",
			data : parms,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理 
				$.each(jsonData, function(index, item) {
					$("#district").append(
							"<option value='"+item.districtId+"'>"
									+ item.district + "</option>");
				});
				if (districtId != null) {
					$("#district").val(districtId);
				}
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
	}

</script>

