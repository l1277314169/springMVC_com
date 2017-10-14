<script type="text/javascript">
	function loadskutype(){
		$.ajax({
			type : "post",
			url : "${contextPath}/skuType/getskuTypebyId/",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.skuTypeId+"\",\"text\":\""+item.skuTypeName+"\"},";
					} else {
						thisData += "{\"id\":\""+item.skuTypeId+"\",\"text\":\""+item.skuTypeName+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				
				$("#skutypeid").select2({
				  
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