<#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
	<input type="hidden" id="searchClientStructureId" name="clientStructureId">
        <table class="table_white_bg" >
            <tbody>
            	<tr>
                    <td class="td_label" >编号：</td>
                    <td class="td_control"><input type="text" name="distributorNo" ></td>
                    <td class="td_label">联系人：</td>
                    <td class="td_control">
                    	<input type="text" name="contact">
                    </td>
               </tr> 
               <tr>
                	<td class="td_label"><i class="cc1">*</i>名称：</td>
                    <td class="td_control"><input type="text" name="distributorName" required=true></td>
                    <td class="td_label" >简称：</td>
                    <td class="td_control">
                    	<input type="text" name="distributorAbbr">
                    </td>
               </tr> 
				<tr>
                    <td class="td_label">手机号：</td>
                    <td class="td_control">
                    	<input type="text" name="mobileNo">
                    </td>
                      <td class="td_label"><i class="cc1">*</i>所在部门：</td>
                    <td class="td_control">
                    	<input type="text" id="clientStructureId" name="clientStructureId" readonly   onclick="showMenu();" required=true>
                    </td>
                </tr>
              	<tr>
	                 <td class="td_label">状态：</td>
	                   <td class="td_control">
	                    <select  name="status" class="p_label" >
			             	<option value="1">有效</option>
			             	<option value="0">无效</option>
	                 	</select>
	                 </td>
	                  <td class="td_label">省份：</td>
	                  <td class="td_control"> 
		                  <select  name="provinceId" class="p_label" id="province"  onChange="changeProvince()" >
		                    <option value="">--请选择--</option>
			                <#list listprovince as option>
			        			<option value="${option.provinceId}">${option.province}</option>
			        		</#list>
		                 </select>
	                 </td>
                </tr>
                 <tr>
                	<td class="td_label">用户姓名：</td>
                	<td class="td_control">
                		<input id="clientUserId" name="clientUserId" >
                	</td>
	                  <td class="td_label">城市：</td>
	                  <td class="td_control"> 
		                  <select class="p_label" id="city"  name="cityId"  onchange="changeCity()" >
		                    <option value="">--请选择--</option>
		                  </select>
	                 </td>
                 </tr>    
                  <tr>
                    <td class="td_label">类别：</td>
                    <td class="td_control">
                    <select name="distributorType" >
	                       <option value="">--请选择--</option>
			             	<#list optionList as option>
			        			<option value="${option.optionValue!''}">${option.optionText}</option>
			        		</#list>
	                	</select>
                    </td>
	                  <td class="td_label">区县：</td>
	                  <td class="td_control"> 
		                  <select  name="districtId" class="p_label" id="district" >
		                    <option value="">--请选择--</option>
		                  </select>
	                 </td>
          	    </tr>
                <tr>
	                  <td class="td_label">固定电话：</td>
	                  <td class="td_control">
	                	<input type="text" name="telephoneNo">
	                 </td>
	                 <td class="td_label">纬度：</td>
	                  <td class="td_control"> 
		                  <input type="text" name="longitude">
	                  </td>
              	 </tr>
                 <tr>
                    <td class="td_label">邮编：</td>
                    <td class="td_control">
                    	<input type="text" name="postcode">
                    </td>
                    <td class="td_label">经度：</td>
                    <td class="td_control">
                    	<input type="text" name="latitude">
                    </td>
                </tr>
                <tr>
                    <td class="td_label">传真：</td>
                    <td class="td_control">
                    	<input type="text" id="fax" name="fax"  >
                    </td>
                    <td class="td_label">地址：</td>
                    <td class="td_control">
                    	<input type="text" name="addr">
                    </td>
                </tr>
               <tr>
                   <td class="td_label">备注：</td>
                   <td  colspan="4" class="td_control">
                    	<textarea   maxlength=200 name="remark" placeholder="不超过200个字"></textarea> 
                   </td>
               </tr>
               <tr>
					<td colspan="4" class="td_buttons">
							 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			</tbody>
        </table>
</form>
<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_1" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
</div>
<script>
$('.btn-danger').click(function(){
  parent.addDialog.close();
});
//新增经销商
$("#savetButton").bind("click",function(){
	//验证
	if(!$("#dataForm").validate({
	   rules : {
				distributorNo: {
					maxlength: 50
				},
				distributorAbbr: {
					maxlength: 50,
				},
				distributorName: {
					maxlength: 100,
				},
				addr: {
					maxlength: 200,
				},
				contact:{
					maxlength: 50,
				},
				telephoneNo:{
				    isPhone : true
				},
				mobileNo: {
					isMobile : true
				},
				postcode: {
					isZipCode : true
				},
			}
	}).form()){
		return false;
	}
	$.ajax({
	url : "${contextPath}/distributor/addDistributor",
	type : "post",
	dataType:"json",
	async: false,
	data : $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
		   	layer.alert(result.message,function(){
   				parent.document.location.href = "${contextPath}/distributor/query"
   				//addDialog.close();
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
        
    function beforeClick(treeId, treeNode) {
      
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_1");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
		$("#clientStructureId").attr("value", treeNode.name);
		$("#searchClientStructureId").attr("value", treeNode.id);
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo_1");
		zTree.expandAll(true);
	};
    function showMenu() {
        var structureObj = $("#clientStructureId");
        var clientStructureOffset = $("#clientStructureId").position();
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
        //$.fn.zTree.init($("#treeDemo_1"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo_1"), setting);
        
    });
    
    
$("#clientStructureId").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
	$("#clientStructureId").val(""); 
	$("#searchClientStructureId").val(""); 
	}; 
});
    
    
    
    function changeProvince() {
		$("#city").empty();
		$("#district").empty();
		var value = $("#province").val();
		$.ajax({
			type : "get",
			url : "${contextPath}/clientUser/findForListProvince/"+value,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理 
				$("#city").append("<option value=''>--请选择--</option>");
				$.each(jsonData, function(index, item) {
					$("#city").append(
							"<option value='"+item.cityId+"'>" + item.city
									+ "</option>");
				});
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});

	}
	function changeCity() {
		$("#district").empty();
		var value = $("#city").val();
		$.ajax({
			type : "get",
			url : "${contextPath}/clientUser/findForListDistrict/"+value,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);//接收到的数据转化为JQuery对象，由JQuery为我们处理
				 	$("#district").append("<option value=''>--请选择--</option>");
				$.each(jsonData, function(index, item) {
					$("#district").append(
							"<option value='"+item.districtId+"'>"
									+ item.district + "</option>");
				});
			},
			error : function(data) {
				layer.alert("数据加载失败！");
			},
			complete : function(data) {
			}
		});
	}


 $("#clientUserId").select2({
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


</script>

