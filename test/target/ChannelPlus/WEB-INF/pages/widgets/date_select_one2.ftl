<script type="text/javascript">
	jQuery(document).ready(function() {
		//时间插件
		$("#${dateFtlName}").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			maxDate: new Date(),
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				$(this).focus();
				$(this).blur();
			}
		});
});
</script>