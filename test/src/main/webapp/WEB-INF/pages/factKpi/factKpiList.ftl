<html>
<head>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<title>销量目标导入</title>
<style type="text/css">
	.ui-datepicker-calendar {
    	display: none;
    }
</style>
</head>
<body class="main_body" >
		<#assign privCode="C1M17">
		<#include "/base.ftl" />
		
		<div id="content" >
			<div id="content-header">
				<div id="breadcrumb"> 
					<a href="${contextPath}" title="回主页" class="tip-bottom"><i class="icon-home"></i>主页</a>
					<a href="#">执行管理</a>
					<a class="linkPage active" href="${contextPath}/factKpi/query">销量目标导入</a>
				</div>
		 	</div>
			<div class="widget-content nopadding">
				<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
					<div class="control-group">
						<div nowrap="true" class="fl">
							<label class="control-label fl" for="positionId">月份编号：</label>
							<div class="controls">
							  <input type="text" id="monthId" name="monthId" value="<#if monthId!=''>${monthId}</#if>" readonly="readonly" class="input-medium" />
							</div>
						</div>
						<div class="fl">
							<label class="control-label fl" for="storeName">门店名称：</label>
							<div class="controls">
							  <input type="text" id="storeName" name="storeName" class="input-medium" value="${storeName}">
							</div>
						</div>	 
					</div>
					<div class="form-actions">
							<button type="button" id="importBtn" class="btn btn-primary">导入</button>
						<input type="submit" value="查询" class="btn btn-info fr" id="search_btn">
					</div>
					<input type="hidden" name="page" value="${page}">
				</form>
			</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="c_list">
					<tr>
				      	<th>目标月份</th>
				      	<th>门店</th>
				      	<th>销售目标</th>
					</tr>
					<tbody>
						<#list pageParam.items as factKpi>
						<tr>
								<td>${factKpi.monthId!''}</td>
								<td>${factKpi.storeName!''}</td>
								<td>${factKpi.salesAmount!''}</td>
						</tr>
						</#list> 
					</tbody>
				</table>
				<#if pageParam.items?exists> 
					<div class="paging" > 
					   ${pageParam.getPagination()}
					</div> 
				</#if>
			</div>
			</div>
			<#include "/footer.ftl" />			
</body>
</html>
<script>
var importDialog;
$(function(){
		//导入窗口
	$("#importBtn").bind("click",function(){
		var url = "${contextPath}/factKpi/showImportDialog";
			 layer.open({
			    type: 2,
			    title: '销量目标导入',
			    closeBtn: 1,
			    area: ['650px', '347px'],
			    content: url
			});
		//importDialog = new xDialog(url, {}, {title:"销量目标导入",width:650,height:330 });
		//return false;
	});

	$("#monthId").datepicker({
		changeMonth:true,
		changeYear:true,
		dateFormat:"yymm",
		yearRange:"2014:2025",
		prevText: "<",
		nextText: ">",
		closeText: "确定",
		showButtonPanel: true,
		onClose:function(dateText,inst){
            var date = new Date();
            var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();//得到选中的月份值	
            var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();//得到选中的年份值
            date.setFullYear(year);
            date.setMonth(month);           
            $("#monthId").datepicker('option', 'dateFormat','yymm');
            $('#monthId').datepicker('setDate',date);
		}
	});
});
</script>