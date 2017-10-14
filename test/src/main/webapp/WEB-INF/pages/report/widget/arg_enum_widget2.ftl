<select name="${fls.arg}" id="${fls.arg}" value="${filterMap[fls.arg]}" style="width:200px;">
	<option value=""></option>
</select>
<script type="text/javascript">
getEnumSelect2();
function getEnumSelect2(){
	var jsonData = eval(${fls.enumJson});
	$.each(jsonData, function(index, item) {
		var option = '<option value="'+item.value+'">'+item.name+'</option>';
		$("#${fls.arg}").append(option);
	});
	
	$("#${fls.arg}").val(${filterMap[fls.arg]});
}
</script>