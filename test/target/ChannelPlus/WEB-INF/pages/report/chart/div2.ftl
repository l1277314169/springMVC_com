<div id="main_div_${parts.partId}" class="echarts_div echarts_div_span" style="margin-top:40px;height:${parts.layoutVo.height}px;width:${parts.layoutVo.width}%;">
	<span style="padding-top:50px;color:#532c08;font-size:14px;">当月均周均店金额</span>
	<span style="padding-top:20px;color:#532c08;font-size:14px;" id="div3"></span>
	<span style="color:#532c08;font-size:40px;padding-top:30px;font-weight:bold;" id="div1"></span>
	<span style="color:#53af89;font-size:14px;padding-top:30px;font-weight:bold;" id="div2"></span>
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
		  	var div3 = data.divCol3;
		  	$("#div1").html("￥"+div1);
		  	$("#div3").html("数据更新至"+div2);
		  	if(div3<0){
		  		$("#div2").html("↓ "+div3+"% from prev year");
		  	}else{
		  		$("#div2").html("↑ "+div3+"% from prev year");
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