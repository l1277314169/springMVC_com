
<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<style type="text/css">
    #ui-datepicker-div {z-index:99999 !important;}
    .table_white_bg tr td{
        height: 30px;
        padding: 5px;
    }
</style>
<form id="dataForm" method="post">
		<input type="hidden" name="clientId" value="${s.clientId}">
		<input type="hidden" name="feedbackId" value="${s.feedbackId}">
        <table class="table_white_bg">
            <tbody>
            	<tr>
            	    <td class="td_label" >store Code：</td>
                    <td  class="td_control"><input type="text" name="storeCode" disabled="true"  value="${s.storeCode}" ></td>
            	  <td class="td_label" >store Name：</td>
                    <td  class="td_control">
             			<input type="text" name="storeName" disabled="true"  value="${s.storeName}" >
                    </td>
            	</tr>
            	<tr>
                    <td class="td_label" ><i class="cc1">*</i>时间：</td>
                    <td class="td_control">
                    	<input type="hidden" id="monthId" name="monthId"  value="${s.monthId}" required=true >
                    	<#assign month="monthId">
					  <#include "/widgets/month_widget.ftl" />
                    	
                    </td>
            	 <td class="td_label"><i class="cc1">*</i>礼品发放数量：</td>
                    <td class="td_control">
                    	<input type="text" id="numOfGift" name="numOfGift" value="${s.numOfGift}" required=true />
                    </td>
                </tr> 
                <tr>
	             <td class="td_label" ><i class="cc1">*</i>联招会员数量：</td>
	                    <td class="td_control">
	                    	<input type="text" id="numOfMember" name="numOfMember" required=true value="${s.numOfMember}" required=true />
	                    </td>
	                  <td class="td_label"><i class="cc1">*</i>电访合格率：</td>
	                   <td class="td_control"> 
		                   <input type="text" id="rateOfReviews" name="rateOfReviews" value="${s.rateOfReview}" required=true />
	                 </td>
                </tr> 
			<tr>
                    <td class="td_label"><i class="cc1">*</i>不合格会员1：</td>
                    <td class="td_control">
                    	<input type="text" name="num1OfUnqualified" value="${s.num1OfUnqualified}" required=true />
                    </td>
                    
				  <td class="td_label"><i class="cc1">*</i>不合格会员2：</td>
                    <td class="td_control">
                    	 	<input type="text" id="num2OfUnqualified" name="num2OfUnqualified" value="${s.num2OfUnqualified}" required=true />
                    </td>
                </tr>
              <tr>
	                <td class="td_label"><i class="cc1">*</i>合格核销数：</td>
	                    <td class="td_control">
	                    	<input type="text" name="numOfVerification" value="${s.numOfVerification}"required />
	                    </td>
	                 <td class="td_label">最终服务费：</td>
	                  <td class="td_control"> 
		                   <input type="text"   disabled="true"   value="${(s.numOfVerification*100)+(s.numOfGift*30)}" required />
	                 </td>
                </tr>
                
               <tr>
					<td colspan="4" class="td_buttons">
							<button data-dismiss="dialog" id="cancelEdit" type="button" class="btn btn-danger">取消</button>
							<button type="button" id="savetButton" class="btn btn-success margin-left-18px">保存</button>
					</td>
			   </tr>
            </tbody>
        </table>
</form>
<div id="menuContent" class="menuContent" style="display: none; position: absolute;">
		<ul id="treeDemo_pop" class="ztree" style="margin-top: 0; width: 160px;">
		</ul>
</div>
<input type="hidden" id="logname" name="logname">
</html>
<script type="text/javascript">

$(".btn-danger").bind("click",function(){
		parent.layer.closeAll('iframe');
 	});
 	

$(".btn-success").bind("click",function(){
	$(this).attr("disabled","disabled");
		//验证
		if(!$("#dataForm").validate({
				rules : {
					numOfGift:{isIntGteZero:true,required:true},
					numOfMember:{isIntGteZero:true,required:true},
					rateOfReviews:{isIntGteZero:true,required:true},
					num1OfUnqualified:{isIntGteZero:true,required:true},
					num2OfUnqualified:{isIntGteZero:true,required:true},
					numOfVerification:{isIntGteZero:true,required:true},
					
				}
				}).form()){
				$("#savetButton").removeAttr("disabled");
				return;
		}
		layer.confirm("确认修改?",function(){
					$.ajax({
						url : "${contextPath}/contract/updateFeeback",
						type : "post",
						dataType:"json",
						async: false,
						data : $("#dataForm").serialize(),
						success : function(result) {
							layer.alert('修改成功！',function(){
							//window.parent.location.reload();
							parent.document.location.href = "${contextPath}/contract/checkedQuery";
							parent.layer.closeAll('iframe');
							})
						},
					error:function(result){
					layer.msg('修改失败！') ;
				}
						})
						
				})


})
</script>