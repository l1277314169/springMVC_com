<html>  
<body style="font-family:'Arial Unicode MS';font-size:12px;line-height:24px">  
  <table>
	  	<tr>
	  		<td>门店编号：</td>
	  		<td>${storeNo}</td>
	  	</tr>
	  	<tr>
	  		<td>门店名称：</td>
	  		<td>${storeName}</td>
	  	</tr>
	  	<tr>
	  		<td>调研日期：</td>
	  		<td>${(msiSurveyFeedback.feedbackDate)?string("yyyy-MM-dd")}</td>
	  	</tr>
	  	<tr>
	  		<td>访问员：</td>
	  		<td>${msiSurveyFeedback.visitor}</td>
	  	</tr>
	  	<tr>
	  		<td>开始时间：</td>
	  		<td>${(msiSurveyFeedback.startTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
	  	</tr>
	  	<tr>
	  		<td>结束时间：</td>
	  		<td>${(msiSurveyFeedback.endTime)?string("yyyy-MM-dd HH:mm:ss")}</td>
	  	</tr>
	  	<tr>
	  		<td>审核门店得分：</td>
	  		<td><#if approvalMsiSurveyFeedback?exists>
	  				<#if approvalMsiSurveyFeedback.score?exists>
	  					${approvalMsiSurveyFeedback.score}
	  				</#if>
  				<#else>审核员未打分</#if>
	  		</td>
	  	</tr>
  </table>
  
<#assign vmiIndex = 0/>
<#assign maintainIndex = 0/>
<#list msiQuestionGroups as msiQuestionGroup>

	<table style="table-layout:fixed; word-break:break-strict;width:auto;height:auto;margin-bottom: 10px;">
		<tr>
			<td style="background:#777;color: #fff;width: 100%;">
				<#if msiQuestionGroup?exists>
					<#if msiQuestionGroup.groupName?exists>
						${msiQuestionGroup.groupName}
					</#if>
				</#if>
			</td>
		</tr>
			<#list msiQuestionGroup.childrenList as childrenMsiQuestionGroup>
				<tr>
					<td style="background:#777;color: #fff;width: 100%;font-size:8px;line-height:24px"><#if childrenMsiQuestionGroup?exists><#if childrenMsiQuestionGroup.groupName?exists>${childrenMsiQuestionGroup.groupName}</#if></#if></td>
				</tr>
				<tr>
					<td id="<#if childrenMsiQuestionGroup?exists>${childrenMsiQuestionGroup.questionGroupId}</#if>">
						<#if childrenMsiQuestionGroup.msiQuestionVos ??>
							<#list childrenMsiQuestionGroup.msiQuestionVos as msiQuestion>
								<#assign questionType = msiQuestion.questionType/>
								<#if questionType !=3>
										<span style="font-weight:bold;">${msiQuestion.questionNo}</span><span style="padding-left:5px;font-weight:bold;" id="${msiQuestion.msiQuestionId}_show">${msiQuestion.question}</span>
										<#if msiQuestion.approvalDataList?exists>
											<#if msiQuestion.approvalDataList??>
												<#list msiQuestion.approvalDataList as msiAnswer>
													<#if msiAnswer.checked == 1>
														：<span>${msiAnswer.answerNo}</span><span style="padding-left:5px;">${msiAnswer.answer}</span><br/> 
													</#if> 
												</#list>
											</#if>
										<#else>审核员未填写数据</#if>
								 </#if>	
							 		<br/>
							    	<#list msiQuestion.msiSurveyFeedbackAttachments as atts>
							    		<img href="${domain}/uploadfiles/${clientId}/web/thumbnail/xl_${atts.attachment}" src="${domain}/uploadfiles/${clientId}/web/thumbnail/l_${atts.attachment}" />
							    		<#if ((atts_index)+1)%5==0><br/></#if>
							    	</#list>
								 	<br/>
								 <hr/> 
							</#list>
						</#if>
					</td>
				</tr>
			</#list>
	</table>
</#list>
</body>  
</html>