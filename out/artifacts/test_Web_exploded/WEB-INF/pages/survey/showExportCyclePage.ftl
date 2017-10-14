<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="_dataForm" method="post" name="_dataForm">
	<input type="hidden" id="dataId" name="dataId" value="${dataId}" />
	<input type="hidden" id="arg_start_date" name="arg_start_date" />
	<input type="hidden" id="arg_end_date" name="arg_end_date" />
	
    <table class="table_white_bg">
        <tbody>
           <tr>
                <td class="td_label"><i class="cc1">*</i>周期：</td>
                <td class="td_control">
                	<input type="hidden" id="cycle" name="cycle" value="${feedbackDate}" readonly class="input-medium" />
                	<#assign month="cycle">
			  		<#include "/widgets/month_widget.ftl" />
                </td>
            </tr>
            <tr>
				<td colspan="2" class="td_buttons">
					<button data-dismiss="dialog" type="button" onClick="javascript:parent.layer.closeAll('iframe');" class="btn btn-danger">取消</button>
					<button id="exportButton" type="button" class="btn btn-success margin-left-18px">导出</button>
				</td>
		   </tr>
        </tbody>
    </table>
</form>
<script type="text/javascript">
    $(document).ready(function() {
        $("#exportButton").click(function(){
        	$(this).attr("disabled","disabled");
            var cycle = $("#cycle").val();
            var dataId = $("#dataId").val();
            var year = cycle.substring(0,4);
            var month = cycle.substring(4,6);
            var firstdate = year + '-' + month + '-01';
            var day = new Date(year,month,0);
            var lastdate = year + '-' + month + '-' + day.getDate();
            $("#arg_start_date").val(firstdate);
            $("#arg_end_date").val(lastdate);
			var url = "${contextPath}/export/execute/"+dataId;
			$('#_dataForm').attr("action",url);
			$('#_dataForm').submit();
			//parent.layer.closeAll('iframe');
        });
        
        jQuery("#_year,#_month").change(function(){
			$("#exportButton").removeAttr("disabled");
		});
        
        
    });
</script>