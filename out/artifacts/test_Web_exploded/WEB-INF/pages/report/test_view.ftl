<html>
<head>
<title></title>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<script type="text/javascript" src="${contextPath}/js/freewall.js?cVer=${cVer}"></script>
</head>
<body>
	<div>
		<div class="widget-content nopadding">
			<form class="form-horizontal" action="#" id="searchForm" novalidate="novalidate">
				
				<div class="control-group">
					
					<div nowrap="true" class="fl">
								<label class="control-label">城市：</label>
								<div class="controls">
<input type="text" id="arg_province_ids" name="arg_province_ids" value="13" class="input-medium" />
<script type="text/javascript">
	//用户
	loadProvince();
	function loadProvince(){
		$.ajax({
			type : "post",
			url : "/commService/getProvinceAjax",
			dataType : "json",
			cache : false,
			success : function(data) {
				var jsonData = eval(data);
				var thisData = "[";
				$.each(jsonData, function(index, item) {
					if(index != jsonData.length-1){
						thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"},";
					} else {
						thisData += "{\"id\":\""+item.provinceId+"\",\"text\":\""+item.province+"\"}";
					}
				});
				thisData += "]";
				var cData = $.parseJSON(thisData);
				$("#arg_province_ids").select2({
					width:145,
					placeholder: "请选择",
					allowClear: true,
					data: cData
				});
			},
			error : function(data) {
				alert("数据加载失败！");
			}
	});
};

$("#arg_province_ids").on("change", function () {
		var value = $(this).val();
		if(value == "") {
			$("#").select2({
	        	width:145,
				placeholder: "请选择",
				data:[]
			});
		}
		else{
			$.ajax({
				type : "post",
				url : "/commService/findCityListByProvinceId/"+value,
				dataType : "json",
				cache : false,
				success : function(data) {
					var jsonData = eval(data);
					var thisData = "[";
					$.each(jsonData, function(index, item) {
						if(index != jsonData.length-1){
							thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"},";
						} else {
							thisData += "{\"id\":\""+item.cityId+"\",\"text\":\""+item.city+"\"}";
						}
					});
					thisData += "]";
					var cData = $.parseJSON(thisData);
					$("#").select2({
						width:145,
						placeholder: "请选择",
						allowClear: true,
						data: cData
					});
				},
				error : function(data) {
					alert("数据加载失败！");
				}
			});
		}
	});
</script>								</div>
							</div>



							<div nowrap="true" class="fl">
								<label class="control-label"> 隶属：</label>
								<div class="controls">
									<input type="text" />
								</div>
							</div>

							<div nowrap="true" class="fl">
								<label class="control-label"> AM：</label>
								<div class="controls">
									<input type="text" />
								</div>
							</div>

							<div nowrap="true" class="fl">
								<label class="control-label"> RM：</label>
								<div class="controls">
									<input type="text" />
								</div>
							</div>



				</div>
				<div class="form-actions">
					<input type="button" value="查询" class="btn btn-info fr" id="search_btn" />					
				</div>
				
			</form>
		</div>
	</div>
	

	<div class="widget-content nopadding" id="logResultList">
	<table class="table table-bordered data-table" id="c_list_22" dataid="22">
	<tbody><tr>
			<th class="fill_td"></th>
			<th class="fill_td">Q2</th>
			<th class="fill_td">Q3</th>
			<th class="fill_td">Q4</th>
			<th class="fill_td">Q4</th>
			<th class="fill_td">Q5</th>
			<th class="fill_td">Q6</th>
			<th class="fill_td">Q7</th>
			<th class="fill_td">Q8</th>
			<th class="fill_td">Q9</th>
			<th class="fill_td">Q10</th>
			<th class="fill_td">Q11</th>
			<th class="fill_td">Q12</th>
			<th class="fill_td">Q13</th>
	</tr>
	
		</tbody>
			<tbody>
			<tr>
				<td class="fill_td">上海</td>
				<td class="fill_td">18.7%</td>
				<td class="fill_td">38.9%</td>
				<td class="fill_td">54.4%</td>
				<td class="fill_td">98.6%</td>
				<td class="fill_td">18.5%</td>
				<td class="fill_td">78.7%</td>
				<td class="fill_td">88.7%</td>

				<td class="fill_td">58.7%</td>
				<td class="fill_td">68.5%</td>
				<td class="fill_td">87.0%</td>
				<td class="fill_td">57.7%</td>
				<td class="fill_td">68.7%</td>
				<td class="fill_td">67.7%</td>
			</tr>
			<tr>
				<td class="fill_td">北京</td>
				<td class="fill_td">18.7%</td>
				<td class="fill_td">44.9%</td>
				<td class="fill_td">54.4%</td>
				<td class="fill_td">98.6%</td>
				<td class="fill_td">44.5%</td>
				<td class="fill_td">64.7%</td>
				<td class="fill_td">11.7%</td>

				<td class="fill_td">58.7%</td>
				<td class="fill_td">12.5%</td>
				<td class="fill_td">12.0%</td>
				<td class="fill_td">57.7%</td>
				<td class="fill_td">68.7%</td>
				<td class="fill_td">67.7%</td>
			</tr>
			<tr>
				<td class="fill_td">广州</td>
				<td class="fill_td">18.7%</td>
				<td class="fill_td">38.9%</td>
				<td class="fill_td">54.4%</td>
				<td class="fill_td">12.6%</td>
				<td class="fill_td">18.5%</td>
				<td class="fill_td">33.7%</td>
				<td class="fill_td">88.7%</td>

				<td class="fill_td">58.7%</td>
				<td class="fill_td">21.5%</td>
				<td class="fill_td">32.0%</td>
				<td class="fill_td">57.7%</td>
				<td class="fill_td">78.7%</td>
				<td class="fill_td">67.7%</td>
			</tr>
			<tr>
				<td class="fill_td">深圳</td>
				<td class="fill_td">78.7%</td>
				<td class="fill_td">48.9%</td>
				<td class="fill_td">64.4%</td>
				<td class="fill_td">88.6%</td>
				<td class="fill_td">98.5%</td>
				<td class="fill_td">68.7%</td>
				<td class="fill_td">58.7%</td>

				<td class="fill_td">78.7%</td>
				<td class="fill_td">58.5%</td>
				<td class="fill_td">87.0%</td>
				<td class="fill_td">87.7%</td>
				<td class="fill_td">18.7%</td>
				<td class="fill_td">37.7%</td>
			</tr>
			</tbody>
		
</table>
	
</div>

	
</body>
</html>