<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<#include "/common/taglibs.ftl" />
<title>尤妮佳-销量管理</title>
</head>
<body>
		<div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						
					</div>				 
					<div class="form-actions">			
						<@shiro.hasPermission name="C1M5F1">
							<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						</@shiro.hasPermission>
						<!--<input type="submit" value="查询" class="btn btn-info fr" id="search_btn"> -->
					</div>
				</form>
			</div>
		</div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
	 	//导入窗口
		$("#importBtn").bind("click",function(){
		    var url = "${contextPath}/factMonthlySales/showImportDialog";
		    $.post(url, {}, function(str){
				layer.open({
				    type: 1,
				    title: false,
				    closeBtn: 1,
				    area: ['700px', '295px'],
				    skin: 'layui-layer-rim',
				    content: str
				});
			});
		});
		
		
});
</script>