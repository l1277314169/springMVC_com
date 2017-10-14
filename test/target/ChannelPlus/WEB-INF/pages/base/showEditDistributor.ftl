<#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${clientId}">
		<input type="hidden"  name="distributorId" value="${distributor.distributorId!''}">
		<input type="hidden" id="searchClientStructureId" name="clientStructureId">
		<input type="hidden" id="mappingId"  name="mappingId" value="${distributor.mappingId!''}" >
		<input type="hidden"  id="clientUserIds" name="clientUserIds" value="${distributor.clientUserId}">
         <table class="table_white_bg">
            <tbody>
            	<tr>
                    <td class="td_label" >编号：</td>
                    <td class="td_control">
                    	<input type="text" name="distributorNo"  value="${distributor.distributorNo!''}">
                    </td>
                    <td class="td_label" >联系人：</td>
                    <td class="td_control">
                    	<input type="text" name="contact" value="${distributor.contact!''}">
                    </td>
               </tr> 
               <tr>
                	<td class="td_label"><i class="cc1">*</i>名称：</td>
                    <td class="td_control">
                    	<input type="text" name="distributorName" required=true value="${distributor.distributorName!''}">
                    </td>
                     <td class="td_label">简称：</td>
                    <td class="td_control">
                    	<input type="text" name="distributorAbbr"  value="${distributor.distributorAbbr!''}">
                    </td>
               </tr> 
				<tr>
                    <td class="td_label">手机号：</td>
                    <td class="td_control">
                    	<input type="text" name="mobileNo" value="${distributor.mobileNo!''}">
                    </td>
                     <td class="td_label"><i class="cc1">*</i>所在部门：</td>
                    <td class="td_control">
                    	<input type="text" id="clientStructureId" name="clientStructureId"  required=true readonly  onclick="showMenu();"  value="${distributor.structureName!''}">
                    </td>
                </tr>
              	<tr>
	                 <td class="td_label">状态：</td>
	                   <td class="td_control">
	                    <select  name="status" class="p_label"  value="${distributor.status!''}">
			             	<option value="1" <#if distributor.status==1> selected=true </#if>>有效</option>
			             	<option value="0" <#if distributor.status==0> selected=true </#if>>无效</option>
	                 	</select>
	                 </td>
	                  <td class="td_label">省份：</td>
	                  <td class="td_control"> 
		                  <select  name="provinceId" class="p_label" id="province"  onChange="changeProvince('${distributor.cityId}','${distributor.districtId}')" >
		                    <option value="">--请选择--</option>
			                <#list listProvince as option>
			                   <#if distributor.provinceId==option.provinceId>
			        			<option value="${option.provinceId}" selected=true>${option.province}</option>
			        			<#else>
			        			<option value="${option.provinceId}">${option.province}</option>
			        			</#if>
			        		</#list>
		                 </select>
	                 </td>
                </tr>
                 <tr>
                  	<td class="td_label">用户姓名：</td>
                	<td class="td_control">
                		<input id="clientUserId" name="clientUserId"  value="${distributor.clientUserId}">
                	</td>
    				  <td class="td_label">城市：</td>
	                  <td class="td_control"> 
		                  <select class="p_label" id="city"  name="cityId"    onchange="changeCity('${distributor.districtId}')"  data="${distributor.cityId!''}">
		                    <option value="">--请选择--</option>
		                  </select>
	                 </td>
                 </tr>    
                  <tr>
                    <td class="td_label">类别：</td>
                    <td class="td_control">
                    <select name="distributorType"  value="${distributor.distributorType!''}">
	                       <option value="">--请选择--</option>
			             	<#list optionList as option>
			             	<#if distributor.distributorType == option.optionValue>
			        				<option value="${option.optionValue!''}" selected=true>${option.optionText}</option>
			        			<#else>
			        				<option value="${option.optionValue!''}">${option.optionText}</option>
			        			</#if>
			        		</#list>
	                	</select>
                    </td>
	                  <td class="td_label">区县：</td>
	                  <td class="td_control"> 
		                  <select  name="districtId" class="p_label" id="district"     data="${distributor.districtId!''}">
		                    <option value="">--请选择--</option>
		                  </select>
	                 </td>
          	    </tr>
                <tr>
	                  <td class="td_label">固定电话：</td>
	                  <td class="td_control">
	                	<input type="text" name="telephoneNo" value="${distributor.telephoneNo!''}">
	                 </td>
	                 <td class="td_label">纬度：</td>
	                  <td class="td_control"> 
		                  <input type="text" name="longitude" value="${distributor.longitude!''}">
	                  </td>
               </tr>
                 <tr>
                    <td class="td_label">邮编：</td>
                    <td class="td_control">
                    	<input type="text" name="postcode" value="${distributor.postcode!''}">
                    </td>
                    <td class="td_label">经度：</td>
                    <td class="td_control">
                    	<input type="text" name="latitude" value="${distributor.latitude!''}">
                    </td>
                </tr>
                <tr>
                    <td class="td_label">传真：</td>
                    <td class="td_control">
                    	<input type="text" id="fax" name="fax"  value="${distributor.fax!''}">
                    </td>
                     <td class="td_label">地址：</td>
                    <td class="td_control">
                    	<input type="text" name="addr" value="${distributor.addr!''}">
                    </td>
                </tr>
				<tr>
                   <td class="td_label">备注：</td>
                   <td  colspan="3" class="td_control">
                    	<textarea   maxlength=200 name="remark" placeholder="不超过200个字">${distributor.remark!''}</textarea> 
                   </td>
               </tr>
               <tr>
					<td colspan="4" class="td_buttons">
						 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
						<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_pop" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
</div>
<script>
$('.btn-danger').click(function(){
  parent.editDialog.close();
});
//修改经销商
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
	url : "${contextPath}/distributor/updateDistributor",
	type : "post",
	dataType:"json",
	async: false,
	data : $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
		   	layer.alert(result.message,function(){
   				//window.location.href = "${contextPath}/distributor/query"
   				//editDialog.close();
   				parent.document.location.href = "${contextPath}/distributor/query"
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
        var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");
        //判断当前节点是否选中 如果选中则取消选中，反之选中
        if (zTree.isSelectedNode(treeNode)) {
            zTree.cancelSelectedNode(treeNode);
        } else {
            zTree.selectNode(treeNode);
        }
        return true;
    }
    function onClick(e, treeId, treeNode) {
    	//alert(treeNode.name);
        //var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");            
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
		hideMenu();
        return false;
    }
    
    //异步加载成功后
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	    var zTree = $.fn.zTree.getZTreeObj("treeDemo_pop");
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
        //$.fn.zTree.init($("#treeDemo_pop"), setting, zNodes);
        $.fn.zTree.init($("#treeDemo_pop"), setting);
          //如果省不为空，则初始化省市县
        var provinceId = $('#province').val();
    	var cityId = $('#city').attr("data");
    	var districtId = $('#district').attr("data");
    	//alert("provinceId::::"+provinceId+"cityId:::::"+cityId+"districtId:::::"+districtId);
        if(provinceId != null && provinceId !=''){
        	changeProvince(cityId,districtId);
        };
        //初始化省
        
    });
    
   $("#clientStructureId").keydown(function(e){ 
	if(e.keyCode == 8 || e.keyCode == 46){ 
	$("#clientStructureId").val(""); 
	$("#searchClientStructureId").val(""); 
	}; 
}); 
    
    
    
    function changeProvince(cityId,districtId) {
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
				if (cityId != null) {
					$("#city").val(cityId);
				}
				if(districtId != null && districtId != ""){
					changeCity(districtId);
				};
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


  $("#clientUserId").select2({
        	width:145,
			placeholder: "输入汉字查询",
			minimumInputLength: 2,
			allowClear : true,
			ajax: {
			    type:"post",
				url: "${contextPath}/clientUser/getClientUserWithoutSelf",
				dataType: 'json',
				quietMillis: 250,
				data: function (term, page) {
					return {
						q: term,
					};
				},
				results: function (data,page) {
					return { results: data };
				},
				cache: true
			},
			initSelection: function(element, callback) {
				//alert("22222::"+element.val());
				var id = $("#clientUserIds").val();
				if (id != "") {
					$.ajax("${contextPath}/distributor/getParentClientUser/"+id, {
						dataType: "json"
					}).done(function(data) { callback(data); });
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

