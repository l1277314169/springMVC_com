<script type="text/javascript" src="${contextPath}/js/citylist.js"></script>
<script type="text/javascript" src="${contextPath}/js/querycity.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/cityquery.css"/>
<script>
  $(function() {
     $( "#datepicker").datepicker();
     $("#datepicker").datepicker( "option", "dateFormat", "yy-mm-dd");
  });
  </script>
		
		<div class="filter_div_element">
			日期：<input type="text" id="datepicker" name = "datepicker" readonly="readonly" class="input-medium" style="height:20px;" value="${datepicker}" />
		</div>
		<div class="filter_div_element">
			
			城市：
			<input type="text" id="cityName" name="cityName" value="${cityName}" class="inputText city_text" style="height:20px;" />
            <input id="cityId" name="cityId" type="hidden" class="city_value" value="${cityId}" />
            
            <script>
            	//城市监听框
				jQuery('.city_text').querycity({
					'data':citysFlight,
					'tabs':labelFromcity
				});
				
				function setValue(input,obj){
			    	input.val($(obj).html());//赋值
			        var cityId = getCityId($(obj).html());
			        var cId = jQuery(input).parent().find(".city_value").attr("id"); //设置隐藏域的值为ID
			        jQuery("#"+cId).val(cityId);
			    }
            </script>
            
		</div>
		
		<div class="filter_div_element">
			项目：<input type="text" id="projectName" name="projectName" class="input-medium" value="${projectName}" style="top:-3px;" />
			<script type="text/javascript">
				loadVisitType();
				//账号状态
				function loadVisitType(){
					$.ajax({
					type : "post",
					url : "${contextPath}/comms/getProject",
					dataType : "json",
					cache : false,
					success : function(data) {
						var jsonData = eval(data);
						var thisData = "[";
						$.each(jsonData, function(index, item) {
							if(index != jsonData.length-1){
								thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"},";
							} else {
								thisData += "{\"id\":\""+item.id+"\",\"text\":\""+item.name+"\"}";
							}
						});
						thisData += "]";
						var cData = $.parseJSON(thisData);
						$("#projectName").select2({
							width:145,
							placeholder: "请选择",
							allowClear: true,
							data: cData
						});
					},
					error : function(data) {
						layer.alert("数据加载失败！");
					}
				});
				};
			</script>
		</div>
		
		