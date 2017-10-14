<script type="text/javascript">
jQuery(document).ready(function(e) {
	var positionId = $("#${surveyFtlName}").val();
	loadPosition(positionId);
});

function loadPosition(positionId){
	$.ajax({
		type : "post",
		url : "${contextPath}/survey/getSurveyByName",
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
			$("#${surveyFtlName}").select2({
				placeholder: "请选择",
				allowClear: true,
				data: cData
			});
			$("#${surveyFtlName}").select2("val", positionId);
		},
		error : function(data) {
			alert("数据加载失败！");
		}
	});
};

</script>
