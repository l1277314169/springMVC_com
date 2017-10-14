<!-- 周选择控件 -->
<input type="text" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" readonly="readonly" class="input-medium">&nbsp;&nbsp;至&nbsp;&nbsp;
<input type="text" id="${fls.foreignArgs}" name="${fls.foreignArgs}" value="${filterMap[fls.foreignArgs]}" readonly="readonly" class="input-medium">
<script type="text/javascript">
	$(document).ready(function () {
		$("#${fls.arg}").datepicker({
			changeMonth: true,
			yearRange:"2014:2025",
			prevText: '<',
			nextText: '>',
			onSelect:function(dateText,inst){
				var arrDate = dateText.split("-");
				var year = arrDate[0];
				var month = arrDate[1];
				var day = arrDate[2];
				
				var selectDate = new Date();
				selectDate.setFullYear(year);
				selectDate.setMonth(parseInt(month)-1);
				selectDate.setDate(day);
				
				var weekId = selectDate.getDay()-1;
				if(weekId==-1){
					weekId = 6;
				}
				var firstDay = new Date(Date.parse(selectDate.toString())-86400000*weekId);
				var fYear = firstDay.getFullYear(); 
				var fMonth = firstDay.getMonth() + 1 < 10 ? "0" + (firstDay.getMonth() + 1) : firstDay.getMonth() + 1;
				var fDate = firstDay.getDate() < 10 ? "0" + firstDay.getDate() : firstDay.getDate();
				jQuery("#${fls.arg}").val(fYear+"-"+fMonth+"-"+fDate);
				
				var lastDay = new Date(Date.parse(firstDay.toString())+86400000*6);
				var lYear = lastDay.getFullYear(); 
				var lMonth = lastDay.getMonth() + 1 < 10 ? "0" + (lastDay.getMonth() + 1) : lastDay.getMonth() + 1;
				var lDate = lastDay.getDate() < 10 ? "0" + lastDay.getDate() : lastDay.getDate();
				jQuery("#${fls.foreignArgs}").val(lYear+"-"+lMonth+"-"+lDate);
			}
		});
	});
	
</script>