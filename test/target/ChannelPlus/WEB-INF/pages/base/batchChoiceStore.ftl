<#import "/common/pagination.ftl" as pagination>
<div class="widget-content nopadding" id="logResultList">
	<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th><input type="checkbox" id="checkAll" name="checkAll"></th>
						<th>所属部门</th>
						<th>店铺编号</th>
						<th>店铺名称</th>
						<th>地址</th>
						<th>关联人</th>
					</tr>
					<tbody>
						<#list pageParam.items as store>
							<tr>
								<td><input name="chkItem" type="checkbox" class="checkboxStore" value="${store.storeId!''}" data="${store.storeName!''}"/></td>
								<td>${store.structureName}</td>
								<td>${store.storeNo}</td>
								<td title="${store.storeName}">
									<#if store.storeName?? && store.storeName?length gt 8>
										${store.storeName?substring(0,8)}...
									<#else>
										${store.storeName!''}
									</#if>
								</td>
								<td title="${store.addr!''}">
									<#if store.addr?? && store.addr?length gt 8>
										${store.addr?substring(0,8)}...
									<#else>
										${store.addr!''}
									</#if>
								</td>
								<td title="${store.names!''}">
									<#if store.names?? && store.names?length gt 8>
										${store.names?substring(0,8)}...
									<#else>
										${store.names!''}
									</#if>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
			<div class="paging">	
    			<@pagination.paging pageParam true "#logResultList"/>
			</div>
			<input type="hidden" id="count" value="${count}">
</div>
<script>

$(document).ready(function () {
				
        $("#clientUserId2").select2({
	        	width:150,
				placeholder: "输入字符查询",
				minimumInputLength: 2,
				allowClear: true,
				ajax: {
					url: "${contextPath}/clientUser/getClientUserPosition",
					dataType: 'json',
					quietMillis: 250,
					data: function (term, page) {
						return {
							q: term,
							page: page
						};
					},
					results: function (data,page) {
						return { results: data};
					}
				},
				initSelection: function(element, callback) {
					var id = $(element).val();
					if (id !== "") {
						$.ajax("${contextPath}/clientUser/getClientUser/"+id, {
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
		});
    
    
	//全选              
	var array = $("#hiddenStoreId").val().split(',');
	var storeIds = $("#hiddenStoreId").val();
	
	$("#checkAll").bind("click",function(){
		//取消所有人员的时候再次得到里面的值
		if($('#hiddenStoreId').val()==''){
			array = new Array();	
		}else{
			array = $('#hiddenStoreId').val().split(',');
		}
		if($(this).attr('checked')){
			 $("[name = chkItem]:checkbox").each(function(){
				 if(!$(this).prop("checked")){
	   				  $(this).attr("checked",true);
	   				  array.push($(this).val());
				  	  $('#checkCount').html(array.length);
				 }
			 });
				storeIds = array.join(",");
				if($('#checkCount').html() != 0){
					//$('#storeAlls').attr("disabled","disabled");
				}else{
					$('#storeAlls').removeAttr("disabled");
				}
		}else{
			 $("[name = chkItem]:checkbox").each(function(){
				  $(this).attr("checked",false);
				  array.splice($.inArray($(this).val(),array),1);
				  $('#checkCount').html(array.length);
	 		}); 
	 			storeIds = array.join(","); 
	 			if($('#checkCount').html() != 0){
					//$('#storeAlls').attr("disabled","disabled");
				}else{
					$('#storeAlls').removeAttr("disabled");
				}
		}
		$("#hiddenStoreId").val(storeIds);
		
		//监听input value变化
		listeningInput();
		
	});
	
	$("[name = chkItem]:checkbox").bind("click",function(){
			if($('#hiddenStoreId').val()==''){
				array = new Array();	
			}else{
				array = $('#hiddenStoreId').val().split(',');
			}
			if($(this).attr('checked')){
				$(this).attr("checked",true);
				array.push($(this).val());
			  	$('#checkCount').html(array.length);
				storeIds = array.join(",");
				if($('#checkCount').html() != 0){
					//$('#storeAlls').attr("disabled","disabled");
				}else{
					$('#storeAlls').removeAttr("disabled");
				}
			}else{
				$(this).attr("checked",false);
				array.splice($.inArray($(this).val(),array),1);
			  	$('#checkCount').html(array.length);
				storeIds = array.join(",");
				if($('#checkCount').html() != 0){
					//$('#storeAlls').attr("disabled","disabled");
				}else{
					$('#storeAlls').removeAttr("disabled");
				}
			}
		$("#hiddenStoreId").val(storeIds);
		if($('.checkboxStore:checked').length == $('.checkboxStore').length){
			$('#checkAll').attr("checked",true);
		}else{
			$('#checkAll').attr("checked",false);
		}
		if(array.length == $('#count').val()){
			$('#storeAlls').attr("checked",true);
		}else{
			$('#storeAlls').attr("checked",false);
		}
		
		//监听input value变化
		listeningInput();
	});
	
	//翻页选中
	var array = $('#hiddenStoreId').val().split(",");
	if($('#hiddenStoreId').val()==""){
		array = new Array();
	}
	$('#checkCount').html(array.length);
	 $("[name = chkItem]:checkbox").each(function () {
	    	for (var index in array) {
	            if ($(this).val() == array[index]){
	                $(this).attr("checked", "checked");
	                break;
	            }
	            
	        }
	});
 

	//如果全选中翻页也就选中
	if($('.checkboxStore:checked').length == $('.checkboxStore').length && $('.checkboxStore:checked').length > 0){
			$('#checkAll').attr("checked",true);
		}else{
			$('#checkAll').attr("checked",false);
	}
	
	//如果选中所有人翻页 就选中
	if($('#storeAlls').prop("checked")){
		array = $('#hiddenStoreId').val().split(",");
		$('#checkCount').html(array.length);
		$('.checkboxStore').attr("checked",true);
		//$('.checkboxStore').attr("disabled","disabled");
		$('#checkAll').attr("checked",true);
		//$('#checkAll').attr("disabled","disabled");
	}

	$('#storeAlls').click(function(){
		if($(this).prop("checked")){
			$('.checkboxStore').attr("checked",true);
			//$('.checkboxStore').attr("disabled","disabled");
			$('#checkAll').attr("checked",true);
			//$('#checkAll').attr("disabled","disabled");
			 
			$.post("${contextPath}/store/getBatchStore",$('#dataForm').serialize(),
					function(data){
						if(data!=""){
							$("#hiddenStoreId").val(data['storeIds']);
							array = $('#hiddenStoreId').val().split(",");
							$('#checkCount').html(array.length);
						}
					}
			);
		}else{
			$.post("${contextPath}/store/getBatchStore",$('#dataForm').serialize(),
					function(data){
						if(data!=""){
							$("#hiddenStoreId").val(data['storeIdsCheckAll']);
							if($('#hiddenStoreId').val()==''){
								array = new Array();	
							}else{
								array = $('#hiddenStoreId').val().split(',');
							}
							$('#checkCount').html(array.length);
						}
					}
			);
			$('.checkboxStore').attr("checked",false);
			$('.checkboxStore').removeAttr("disabled");
			$('#checkAll').attr("checked",false);
			$('#checkAll').removeAttr("disabled");
		}
			listeningInput();
	});
		//监听input value变化
		function listeningInput(){
			var oldStoreIdStrs = $('#oldStoreIdStrs').val();
			var hiddenStoreId = $('#hiddenStoreId').val();
			if(oldStoreIdStrs != null && oldStoreIdStrs.length > 0){
				var oldArray = oldStoreIdStrs.split(",");
				var newArray = hiddenStoreId.split(",");
				for(var i in oldArray){
					if($.inArray(oldArray[i],newArray) == -1){
						 flag = true;
						 break;
					}else{
						flag = false;
					}
				}
			}
		}
</script>
