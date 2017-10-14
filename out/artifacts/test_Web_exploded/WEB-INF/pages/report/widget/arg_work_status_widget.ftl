<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
	loadStatus();
	//在职状态
	function loadStatus(){
		$.ajax({
			type : "post",
			url : "${contextPath}/clientUser/getStatus",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"},";
					} else {
						thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"}";
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
				layer.alert("数据加载失败！");
			}
		});
	};
</script>