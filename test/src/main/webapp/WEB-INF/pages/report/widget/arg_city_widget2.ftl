<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
loadCity2();
function loadCity2(){
	$.ajax({
			type : "post",
			url : "${contextPath}/commService/getCityAjax",
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
}
</script>