<!-- option枚举 -->
<input type="text" id="${fls.arg}" name="${fls.arg}" class="input-medium" dataPosition="${filterMap[fls.arg]}"/>
<script type="text/javascript">
jQuery(document).ready(function(e) {
	var optionId = $("#${fls.arg}").attr("dataPosition");
	loadOption(optionId);
});

function loadOption(optionId){
	$.ajax({
		type : "post",
		url : "${contextPath}/store/getstoreOptionsType",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"},";
				} else {
					thisData += "{\"id\":\""+item.optionValue+"\",\"text\":\""+item.optionText+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#${fls.arg}").select2({
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
			$("#${fls.arg}").select2("val", optionId);
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};

</script>
