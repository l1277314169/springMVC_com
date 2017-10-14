<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
	function loadBrand(value){
		$.ajax({
			type : "post",
			url : "${contextPath}/comms/getBrands?customerId="+value,
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"},";
					} else {
						thisData += "{\"id\":\""+item.brandId+"\",\"text\":\""+item.brandName+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				
				$("#${fls.arg}").select2({
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
loadBrand('${filterMap[fls.foreignArgs]}');
</script>