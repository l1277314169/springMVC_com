<script type="text/javascript">
loadProvince();
loadCity($("#provinceId").val());

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
}

function loadCity(value){
	if(null==value || ''==value){
		$("#cityId").select2({
        	width:145,
			placeholder: "请选择",
			data:[]
		});
	}else{
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
				$("#cityId").select2({
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
}

$("#provinceId").on("change", function () {
		var value = $(this).val();
		if(value == "") {
			$("#cityId").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
	        $("#districtId").select2({
	        	width:145,
				placeholder: "请选择",
				data: []
	        });
		}else{
			loadCity(value);
		}
	});
	
$("#cityId").on("change", function () {
	var value = $(this).val();
	if(value == "") {
        $("#districtId").select2({
        	width:145,
			placeholder: "请选择",
			data: []
        });
	}
});
</script>