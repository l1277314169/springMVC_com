<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
		<input type="hidden" id="storeId" name="storeId" value="${store.storeId!''}">
        <input type="hidden" id="clientStructureId" name="clientStructureId" value="${store.clientStructureId!''}">
    	<input type="hidden" id="channelId" name="channelId" value="${store.channelId!''}">
    	<input type="hidden" id="chainId" name="chainId" value="${store.chainId!''}">
    	<input type="hidden" id="distributorId" name="distributorId" value="${store.distributorId!''}">
    	<input type="hidden" id="oldBusinessman" name="oldBusinessman" value="">
    	<input type="hidden" id="oldPromotions" name="oldPromotions" value="">
        <table class="table_white_bg">
            <tbody>
            <#assign qp = 0/>
            <#list seeList as ls>
            	<#if ls.remark =="br">
            		<#assign qp = 0/>
            		<tr>
            			<td class="td_label" ><#if ls.isMustDo = 1><i class="cc1">*</i></#if>${ls.columnDesc}：</td>
	                    <td class="td_control" colspan="3">
	                    	<#if ls.controlType = 2>
	                    		<textarea   maxlength="${ls.maxLength}" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> placeholder="不超过${ls.maxLength}个字"  <#if ls.isMustDo = 1>required</#if> disabled="disabled">${store['${ls.attributeName}']}</textarea>
	                    	<#else>
	                    		<input type="text" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> <#if ls.isMustDo = 1>required</#if> style="width:450px;" <#if ls.relationValue??>relationValue="${ls.relationValue}"</#if> value="${store['${ls.attributeName}']}" disabled="disabled"/>
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
	                    		<textarea   maxlength="${ls.maxLength}" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> placeholder="不超过${ls.maxLength}个字"  <#if ls.isMustDo = 1>required</#if> disabled="disabled">${store['${ls.attributeName}']}</textarea>
	                    	<#else>
	                    		<input type="text" id="${ls.attributeName}" name="${ls.attributeName}" <#if ls.editable = 0>readonly</#if> <#if ls.isMustDo = 1>required</#if> <#if ls.relationValue??>relationValue="${ls.relationValue}"</#if> value="${store['${ls.attributeName}']}" disabled="disabled"/>
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
							 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">关闭</button>
					</td>
			   </tr>
            </tbody>
        </table>
        
</form>
<script>
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
			alert("数据加载失败！");
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
			alert("数据加载失败！");
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
			alert("数据加载失败！");
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