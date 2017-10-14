<script type="text/javascript">
	function loadskutype(){
		$.ajax({
			type : "post",
			url : "${contextPath}/clientStructure/getClientStructurebyId/",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.clientStructureId+"\",\"text\":\""+item.structureName+"\"},";
					} else {
						thisData += "{\"id\":\""+item.clientStructureId+"\",\"text\":\""+item.structureName+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				
				$("#clientStructureId").select2({
				  
					width:145,
					placeholder: "请选择",
					allowClear: true,
					data: cData
				});
			},
			error : function(data) {
				//alert("数据加载失败！");
			}
		});
	};
loadskutype();
</script>