<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" readonly="readonly" class="input-medium">
<script type="text/javascript">
	$(document).ready(function () {
		$("#${fls.arg}").datepicker({
			changeMonth: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
			}
		});
	});
</script>