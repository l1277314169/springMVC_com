<script type="text/javascript">
	jQuery(document).ready(function() {
		function AddDays(date){
			var nd = new Date(date);
			nd.setDate(nd.getDate()+#{spaceDate?number});
			var y = nd.getFullYear();
			var m = nd.getMonth()+1;
			var d = nd.getDate();
			if(m <= 9) m = "0"+m;
			if(d <= 9) d = "0"+d; 
			var cdate = y+"-"+m+"-"+d;
			return cdate;
		}
		
		//时间插件
		$("#${startDate}").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				var endText=AddDays(dateText);
				$("#${endDate}").datepicker("option","minDate",dateText);
				$("#endDate").datepicker("option","maxDate",endText);
				$(this).focus();
				$(this).blur();
			}
		});
		
		$("#${endDate}").datepicker({
			changeYear: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			beforeShow: function() {
				var query=$("#${startDate}").val();
				var dateText=AddDays(query);
				$("#${endDate}").datepicker("option","maxDate",dateText);
			},
			onSelect:function(dateText,inst){
				$("#${startDate}").datepicker("option","maxDate",dateText);
				$(this).focus();
				$(this).blur();
			}
		});
});
</script>