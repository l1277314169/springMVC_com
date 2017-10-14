<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" class="input-medium" />
<script type="text/javascript">
	var structureData = [{ id: 1, text: '大区' }, { id: 2, text: '小区' }];
	  $("#${fls.arg}").select2({
			width:145,
			placeholder: "请选择",
			allowClear: true,
			data: structureData
        });
</script>