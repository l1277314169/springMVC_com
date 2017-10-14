<input type="hidden" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" />
<select id="${fls.arg}_year" name="${fls.arg}_year" style="width: 70px;"></select>
<select id="${fls.arg}_month" name="${fls.arg}_month" style="width: 70px;"></select>
<script type="text/javascript">
	$(document).ready(function () {
		var date = new Date();
		var year = date.getFullYear();
		var hid_val = jQuery("#${fls.arg}_hid").val();
		for (var i = 2014; i <= year; i++) {
			jQuery("#${fls.arg}_year").append("<option value='"+i+"'>"+i+"</option>");
		}
		for (var j = 1; j <= 12; j++) {
			var val = null;
			if(j<10){
				val = "0"+j;
			}else{
				val = j;
			}
			jQuery("#${fls.arg}_month").append("<option value='"+val+"'>"+val+"</option>");
		}


		jQuery("#${fls.arg}_year,#${fls.arg}_month").change(function(){
			var year = jQuery("#${fls.arg}_year").val();
			var month = jQuery("#${fls.arg}_month").val();
			jQuery("#${fls.arg}").val(year+""+month); 
		});

		var args = jQuery("#${fls.arg}").val();
		if(null!=args && ""!=args){
			var _year = args.substring(0,4);
			var _month = args.substring(4,6);
			jQuery("#${fls.arg}_year").val(_year);
			jQuery("#${fls.arg}_month").val(_month);
		}		
	});
</script>