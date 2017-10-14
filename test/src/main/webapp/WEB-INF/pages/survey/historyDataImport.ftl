<html>
<head>
  <title>问卷</title>
  <#include "/common/head.ftl" />
  <#include "/common/foot.ftl" /> 
</head>

<body>
	<div>
		<div class="form-actions">
			 <button type="button" id="importBtn" class="btn btn-primary">CC_Main_Questionnaire导入</button>
			 <button type="button" id="importBtn2" class="btn btn-primary">Main_Questionnaire导入</button>
			 <button type="button" id="importDisPlayBtn" class="btn btn-primary">Secondary_Display_Questionnaire导入</button>
			<button type="button" id="importSupplementaryBtn" class="btn btn-primary">Supplementary_Questionnaire_Aug导入</button>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var importDialog = null;
	$(document).ready(function() {
		//导入窗口
		$("#importBtn").bind("click",function(){
			var url = "${contextPath}/historyDataImport/showImportDialog";
			importDialog = new xDialog(url, {}, {title:"历史数据导入",width:650,height:330 });
			return true;
		});
		
		//导入窗口
		$("#importBtn2").bind("click",function(){
			var url = "${contextPath}/historyDataImport/showImportDialog";
			importDialog = new xDialog(url, {}, {title:"历史数据导入",width:650,height:330 });
			return true;
		});
		
		//陈列面历史数据导入
		$("#importDisPlayBtn").bind("click",function(){
			var url = "${contextPath}/historyDataImport/showDisPlayImportDialog";
			importDialog = new xDialog(url, {}, {title:"历史数据导入",width:650,height:330 });
			return true;
		});
		
		$("#importSupplementaryBtn").bind("click",function(){
			var url = "${contextPath}/historyDataImport/showSupplementaryImportDialog";
			importDialog = new xDialog(url, {}, {title:"历史数据导入",width:650,height:330 });
			return true;
		});
		
	});
</script>