<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>Loreal销量管理</title>
</head>
<body>
		<div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<!-- 
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="storeId"></label>
							<div class="controls">
								<input id="structureName" name="structureName" required="required" readonly type="text" value=""/>
							</div>
						</div>
						-->
					</div>				 
					<div class="form-actions">			
						<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
				</form>
			</div>
			<div class="widget-content nopadding" style="overflow:auto;width:auto;">
				
				<!--
				<table class="table table-bordered data-table" id="c_list">
					<tr>
						<th class="fill_td">月份编号</th>
						<th class="fill_td">门店编号</th>
				      	<th class="fill_td">门店名称</th>
						<th class="fill_td">操作</th>
					</tr>
					<tbody>
						<tr>					
							<td class="fill_td">&nbsp;</td>
							<td class="fill_td"></td>
							<td class="fill_td"></td>
							<td class="fill_td"></td>						
						</tr>
					</tbody>
				</table>
				 -->
			</div>
			
		</div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
	
		$("#importBtn").bind("click",function(){
			var url = "${contextPath}/factMonthlySales/showFerreroImportDialog";
			layer.open({
			    type: 2,
			    title: '销量导入',
			    shadeClose: false,
			    closeBtn: 2,
			    shade: 0.5,
			    area: ['660', '340'],
			    content: url
			});
		});
	});
</script>