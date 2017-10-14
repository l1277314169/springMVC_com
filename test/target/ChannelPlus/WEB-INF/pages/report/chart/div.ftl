<div id="main_div_${parts.partId}" class="echarts_div echarts_div_span" style="margin-top:40px;height:${parts.layoutVo.height}px;width:${parts.layoutVo.width}%;">
	<span style="padding-top:50px;color:#369;font-size:18px;">当月单店产出平均金额</span>
	<span style="color:#369;font-size:60px;padding-top:30px;font-weight:bold;" id="div1"></span>
	<span style="color:red;font-size:14px;padding-top:30px;font-weight:bold;" id="div2"></span>
</div>

<script type="text/javascript">

	function send_${parts.partId}(){
		var datas;
		var _url;
		var dashBoard = ${parts.dashboard};
		
		if(''!=dashBoard && dashBoard=='1'){
			datas = jQuery("#searchForm_${parts.partId}").serialize();
			_url = '${contextPath}/dashboard/loadEcharts/${parts.partId}';
		}else{
			datas = jQuery("#searchForm").serialize();
			_url = '${contextPath}/report/loadEcharts/${parts.partId}';
		}
		jQuery.ajax({
		  url: _url,
		  type: 'POST',
		  dataType: 'json',
		  cache:false,
		  data : datas,
		  success: function(data, textStatus, xhr) {
		  	var div1 = data.divCol1;
		  	var div2 = data.divCol2;
		  	$("#div1").html("￥"+div2);
		  	
		  	if(parseFloat(div1)<0){
		  		$("#div2").html("↓ "+div1+"% from prev month");
		  	}else{
		  		$("#div2").html("↑ "+div1+"% from prev month");
		  	}
		  },
		  error: function(xhr, textStatus, errorThrown) {
		   	layer.alert(errorThrown);
		  }
		});
	}
		    

   //异步加载echarts数据
   jQuery(document).ready(function($) {
   		send_${parts.partId}();		   		
   });
</script>