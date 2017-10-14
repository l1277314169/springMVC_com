<select id="_year" name="_year" style="width: 80px;"></select>
<select id="_month" name="_month" style="width: 80px;"></select>
<script type="text/javascript">
	$(document).ready(function () {
		var date = new Date();
		var year = date.getFullYear();
		var hid_val = jQuery("#_hid").val();
		for (var i = 2014; i <= year; i++) {
			jQuery("#_year").append("<option value='"+i+"'>"+i+"</option>");
		}
		for (var j = 1; j <= 12; j++) {
			var val = null;
			if(j<10){
				val = "0"+j;
			}else{
				val = j;
			}
			jQuery("#_month").append("<option value='"+val+"'>"+val+"</option>");
		}

		jQuery("#_year,#_month").change(function(){
			var year = jQuery("#_year").val();
			var month = jQuery("#_month").val();
			jQuery("#${month}").val(year+""+month);
		});

		var args = jQuery("#${month}").val();
		if(null!=args && ""!=args){
			var _year = args.substring(0,4);
			var _month = args.substring(4,6);
			jQuery("#_year").val(_year);
			jQuery("#_month").val(_month);
		}		
	});
</script>