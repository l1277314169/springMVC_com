<input type="checkbox" id="${fls.arg}" name="${fls.arg}" value="${filterMap[fls.arg]}" <#if filterMap[fls.arg]==1>checked</#if> class="input-medium" style="margin-left: 60px;"/>
<script type="text/javascript">
	$(document).ready(function(){
			$("#${fls.arg}").bind("click",function(){
				if($(this).attr("checked")) {
					$("#${fls.arg}").val("1");
				}
			});
	});
</script>
