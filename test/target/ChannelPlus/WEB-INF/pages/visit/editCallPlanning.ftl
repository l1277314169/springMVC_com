<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<form id="dataForm" method="post">
        <table class="table_white_bg">
        	<input  name="planningId"  type="hidden" value="${callPlanning.planningId}"/>
            <tbody>
            	<tr>
            		<td class="td_label_d">执行人员：</td>
                    <td class="td_control">
                    	${clientUserName!''}
                    </td>
                </tr> 
                <tr>
                	<td class="td_label_d">任务类型：</td>
                    <td class="td_control">
	                    <select  id="visitType" name="visitType" value = "">
		                    	<option value="">--请选择--</option>
		                    	<option value="1"<#if callPlanning.visitType == 1> selected = "selected"</#if>>门店拜访</option>
		                    	<option value="2"<#if callPlanning.visitType == 2> selected = "selected"</#if>>门店协访</option>
	                    </select>
                    </td>
                </tr>
                <tr>
                	<td class="td_label_d">执行时间：</td>
                    <td class="td_control">
                    <input type="text" id="callDate" name="callDate" value="${callPlanning.callDate?string("yyyy-MM-dd")}"/><span>&nbsp;示例 ：2015-01-01</span>
                    </td>
                </tr>
                <tr>
					<td colspan="4" class="td_buttons">
						 <button data-dismiss="dialog" id="cancel" onclick="javascript:parent.layer.closeAll('iframe');" type="button" class="btn btn-danger">取消</button>
						<button id="savetButton"  type="button" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<script>
//时间插件
//$("#callDate").datepicker({
//	changeYear: true,
//	yearRange:"2014:2025",
//	prevText: '<',
//	nextText: '>',
//	onSelect:function(dateText,inst){
//		$(this).focus();
//		$(this).blur();
//	}	
//});

$("#savetButton").bind("click",function(){
	$.ajax({
	url : "${contextPath}/callPlanning/updateCallPlanning",
	type : "post",
	dataType:"json",
	async: false,
	data : $("#dataForm").serialize(),
	success : function(result) {
	   if(result.code=="success"){
	  			window.location.href = "${contextPath}/callPlanning/query"
   				// editDialog.close();
		   		parent.layer.closeAll('iframe');
		}else {
			layer.alert(result.message);
   		}
	   }
	});						
});
</script>
