<input type="hidden" name="${fls.arg}" id="${fls.arg}" value="${filterMap[fls.arg]}" />
<select name="${fls.arg}_select2" id="${fls.arg}_select2" value="${filterMap[fls.arg]}" style="width:200px;">
	<option value=""></option>
</select>
<input type = "hidden" id="account_HID" name="account_HID" value="${filterMap[fls.arg]}" />
<script type="text/javascript">
	function loadAccount2(){
		$.ajax({
			type : "post",
			url : "${contextPath}/comms/getAccount",
			dataType : "json",
			cache : false,
			success : function(data) {
				var val = $("#account_HID").val();
				$.each(data, function(index, item) {
					var v = item.optionText==null?"":item.optionText;
					var option = '<option value="'+v +'">'+item.optionText+'</option>';
					$("#${fls.arg}_select2").append(option);
				});
				if(val!=""){
					$("#${fls.arg}_select2").val("${filterMap[fls.arg]}");
					//$("#${fls.arg}").val("${filterMap[fls.arg]}");
				}
			},
			error : function(data) {
				
			}
		});
	};
loadAccount2();

$(document).ready(function(){
	$("#${fls.arg}_select2").live("change",function(){
		var select2Value = $("#${fls.arg}_select2").val();
		$("#${fls.arg}").val(select2Value);
	});
});
</script>