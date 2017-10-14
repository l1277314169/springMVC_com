<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
	loadYear2();
	function loadYear2(){
		$.ajax({
			type : "post",
			url : "${contextPath}/comms/getYear",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) { 
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.yearId+"\",\"text\":\""+item.yearDescEn+"\"},";
					} else {
						thisData += "{\"id\":\""+item.yearId+"\",\"text\":\""+item.yearDescEn+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				$("#${fls.arg}").select2({
					width:170,
					placeholder: "请选择",
					allowClear: false,
					data: cData
				});
				$("#${fls.arg}").select2("val", '${filterMap[fls.arg]}');
			},
			error : function(data) {
				alert("数据加载失败！");
			}
	});
}
</script>