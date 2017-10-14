<input type="hidden" name="${fls.arg}" id="${fls.arg}" value="${filterMap[fls.arg]}" />
<select name="${fls.arg}_select2" id="${fls.arg}_select2" value="${filterMap[fls.arg]}" style="width:200px;"></select>
<script type="text/javascript">
	function loadYears(){
		$.ajax({
			type : "post",
			url : "${contextPath}/apple/getYearVo",
			dataType : "json",
			cache : false,
			success : function(data) {
				$.each(data, function(index, item) {
					var option = '<option value="'+item.yearId+'">'+item.yearId+'</option>';
					$("#${fls.arg}_select2").append(option);
				});
				$("#${fls.arg}_select2").val("${filterMap[fls.arg]}");
			},
			error : function(data) {
				
			}
		});
	};
loadYears();

$(document).ready(function(){
	$("#${fls.arg}_select2").live("change",function(){
		var select2Value = $("#${fls.arg}_select2").val();
		$("#${fls.arg}").val(select2Value);
	});
});
</script>