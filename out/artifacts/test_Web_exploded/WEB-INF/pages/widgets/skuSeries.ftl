<script type="text/javascript">
	function loadskuSeries(){
		$.ajax({
			type : "post",
			url : "${contextPath}/skuSeries/getSkuSeries/",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.skuSeriesId+"\",\"text\":\""+item.skuSeriesName+"\"},";
					} else {
						thisData += "{\"id\":\""+item.skuSeriesId+"\",\"text\":\""+item.skuSeriesName+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				
				$("#skuSeriesId").select2({
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
loadskuSeries();
</script>