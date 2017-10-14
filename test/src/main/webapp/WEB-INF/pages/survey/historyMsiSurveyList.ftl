 <html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<style type="text/css">
	.ui-datepicker-calendar {
    	display: none;
    }
</style>
</head>
<body>
   <div class="widget-content nopadding">
			<table class="table table-bordered data-table">
				<thead>
					<tr>
						<th>问卷编号</th>
						<th>问卷名称</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>操作</th>
					</tr>
				</thead>

				<#list msiSurveys as msiSurvey>
				<tbody>
					<tr>
						<td>${msiSurvey.surveyNo}</td>
						<td>${msiSurvey.surveyName}</td>
						<td>${msiSurvey.startDate?date}</td>
						<td>${msiSurvey.endDate?date}</td>
						<td class="table_img" style="text-align: center;" >
							<a msiSurveyId="${msiSurvey.msiSurveyId}" href="javascript:void(0);" storeId="${store.storeId}" storeName="${store.storeName!''}" storeNo="${store.storeNo!''}"  class="addMsi">添加</a>
						</td>
					</tr>
				</tbody>
				</#list>
			</table>
		</div>
 </body>
</html>
<script>
 $(function(){
	$(".addMsi").bind("click",function(){
		var storeId=$(this).attr("storeId");
	    var storeNo=$(this).attr("storeNo");
	    var storeName=$(this).attr("storeName");
	    var msiSurveyId = $(this).attr("msiSurveyId");
		var url = "${contextPath}/msiSurveyFeedback/showAddHistoryMsiSurveyData/"+storeId+"/"+storeNo+"/"+storeName+"/"+msiSurveyId;
		window.location.href=url;
	});
});
</script>
 