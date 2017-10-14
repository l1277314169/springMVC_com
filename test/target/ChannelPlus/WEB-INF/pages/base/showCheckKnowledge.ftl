<style>
	.table_white_bg .td_label {
   	 width: 100px;
	}
</style>
<table class="table_white_bg" style="height: 92%;">
            <tbody> 
                <!--<tr>
                	<td class="td_label"><i class="cc1">*</i>资料类型</td>
                    <td class="td_control">
	                   <#if knowledge.knowledgeType == 1 > 资料1 </#if>
	                   <#if knowledge.knowledgeType == 2 > 资料2</#if>
                    </td>
                </tr>-->
                <tr>
                    <td class="td_label" ><i style="color: red;">*</i>标题：</td>
                    <td class="td_control">
                    	${knowledge.title!''}
                    </td>
                    <td class="td_label">阅读数：</td>
                    <td class="td_control">
                    	${knowledge.readTimes!''}
                    </td>
                </tr>
                    <tr>
                    <td class="td_label">点赞数：</td>
                    <td class="td_control">
                    	${knowledge.likeTimes!''}
                    </td>
                    <td class="td_label">开始时间：</td>
                    <td class="td_control">
                     <#if (knowledge.endDate)??> ${(knowledge.startDate)?string("yyyy-MM-dd HH:mm:ss")}</#if> 
                    </td>
                </tr>
               <tr>
                    <td class="td_label">结束时间：</td>
                    <td class="td_control">
                     <#if (knowledge.endDate)??> ${(knowledge.endDate)?string("yyyy-MM-dd HH:mm:ss")}</#if>
                    </td>
                </tr>
                 <tr>
                    <td class="td_label"><i style="color: red;">*</i>内容：</td>
                    <td  class="td_control_d">
                    	${knowledge.content!''}
                    </td>
                 </tr>
				<tr>
                    <td class="td_label">资料展示照片：</td>
                    <td class="td_control">
                    	<#if (knowledge.avatar)?? >
							   <#list (knowledge.avatar)?split(",") as x> 
							   <#if (x) !=null>
							   <img class="img_lightBox" href="${contextPath}/uploadfiles/${knowledge.clientId}/web/${x}" src="${contextPath}/uploadfiles/${knowledge.clientId}/web/thumbnail/s_${x}" />
							   </#if>
							   </#list>
							</#if>
                    </td>
                </tr>
                <tr>
                    <td class="td_label">学习资料附件：</td>
                    <td class="td_control">
                    <#if (knowledge.fileName)?? >
					    <#list (knowledge.fileName)?split(",") as c> 
							   <#if (c) !=null>
				    <a href="${contextPath}/uploadfiles/${knowledge.clientId}/web/${c}">${c}</a>
							   </#if>
							   </#list>
							</#if>
                    </td>
                </tr>
             <tr>
	 		<td class="td_label"></td>
			<td colspan="3" class="td_buttons">
				<button data-dismiss="dialog" type="button" class="btn btn-danger" style="margin-right:80px">关闭</button>
			</td>
	   </tr>   
    </tbody>
</table>