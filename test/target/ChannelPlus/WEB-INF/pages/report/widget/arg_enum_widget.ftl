<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
getEnumSelect2();

function getEnumSelect2(){
	var jsonData = eval(${fls.enumJson});
	var thisData = "[";
	$.each(jsonData, function(index, item) {
		if(index != jsonData.length-1){
			thisData += "{\"id\":\""+item.value+"\",\"text\":\""+item.name+"\"},";
		} else {
			thisData += "{\"id\":\""+item.value+"\",\"text\":\""+item.name+"\"}";
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
}			
			
			
</script>