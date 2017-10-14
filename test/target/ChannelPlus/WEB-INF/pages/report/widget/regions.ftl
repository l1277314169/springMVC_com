<input type="hidden" name="${fls.arg}" id="${fls.arg}" />
<select name="${fls.arg}_select2" id="${fls.arg}_select2" value="${filterMap[fls.arg]}" style="width:200px;">
	<option value="432">National</option>
</select>
<script type="text/javascript">
loadRegions();
function loadRegions(){
	var _url = '${contextPath}/colgate/loadColagetRegions?parentId=432';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, item) {
				var option = '<option value="'+item.clientStructureId+'">'+item.structureNameEn+'</option>';
				$("#${fls.arg}_select2").append(option);
			});
			$("#${fls.arg}_select2").val("${filterMap[fls.arg]}");
			$("#${fls.arg}").val("${filterMap[fls.arg]}");
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

$(document).ready(function(){
	$("#${fls.arg}_select2").live("change",function(){
		var select2Value = $("#${fls.arg}_select2").val();
		$("#${fls.arg}").val(select2Value);
	});
});
</script>