<!-- 人员职位 -->
<input type="text" id="${fls.arg}" name="${fls.arg}" class="input-medium" value="${filterMap[fls.arg]}" />
<script type="text/javascript">
jQuery(document).ready(function(e) {
	var positionId = $("#${fls.arg}").val();
	loadPosition(positionId);
});

function loadPosition(positionId){
	$.ajax({
		type : "post",
		url : "${contextPath}/clientPosition/getClientPositionByMobilePer",
		dataType : "json",
		cache : false,
		success : function(data) {
			var jsonData = eval(data);
			var thisData = "[";
			$.each(jsonData, function(index, item) {
				if(index != jsonData.length-1){
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"},";
				} else {
					thisData += "{\"id\":\""+item.clientPositionId+"\",\"text\":\""+item.positionName+"\"}";
				}
			});
			thisData += "]";
			var cData = $.parseJSON(thisData);
			$("#${fls.arg}").select2({
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
			$("#${fls.arg}").select2("val", positionId);
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};

</script>
