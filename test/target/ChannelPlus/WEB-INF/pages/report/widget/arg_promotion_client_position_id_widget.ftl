<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium"/>
<script type="text/javascript">
	$(document).ready(function(){
			var position = [{ id: 61, text: '促销队' }, { id: 65, text: '长促' }];
			  $("#${fls.arg}").select2({
	        	width:145,
				placeholder: "请选择",
				allowClear: true,
				data:position
			});
	});
</script>
