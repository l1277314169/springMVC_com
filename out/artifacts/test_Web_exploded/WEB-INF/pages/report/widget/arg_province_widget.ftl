<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
	//用户
	loadProvince();
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
				$("#${fls.arg}").select2({
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

$("#${fls.arg}").on("change", function () {
		var value = $(this).val();
		if(value == "") {
			$("#${fls.foreignArgs}").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
		}
		else{
			$.ajax({
				type : "post",
				url : "${contextPath}/commService/findCityListByProvinceId/"+value,
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
					$("#${fls.foreignArgs}").select2({
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
		}
	});
</script>