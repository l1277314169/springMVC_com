	<#include "/common/taglibs.ftl" />
	<div id="main_pie_${parts.partId}" class="echarts_div" style="margin-top:40px;height:${parts.layoutVo.height}px;width:${parts.layoutVo.width}%;"></div>
    <script type="text/javascript">
    		//--- 折柱 ---
            var pieChart_${parts.partId} = echarts.init(document.getElementById('main_pie_${parts.partId}'),"infographic");
            
            var clientId = $("#clientId").val();
            if(clientId==11){ //费列罗
            	pieChart_${parts.partId}.setTheme(firms());
            }
            
		    pieChart_${parts.partId}.showLoading({
			    text: '正在努力的读取数据中...',
			});
			
			option_${parts.partId} = {
			    title : {
			        text: '',
			        y:'top',
				    x:'center',
			        textStyle :{
			        	fontSize: 14
			        }
			    },
			    noDataLoadingOption :{
			    	text :'${parts.partName}，未能查询到数据',
			    	effect:'whirling',
			    	textStyle:{
			    		fontSize:'18'
			    	}
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        y:'bottom',
			        x : 'left',
			        data:[]
			    },
			    series : [
			        
			    ]
			}
			
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
		   		  		setAttributeForMix_${parts.partId}(data);
		   		  },
		   		  error: function(xhr, textStatus, errorThrown) {
		   		   		layer.alert(errorThrown);
		   		  }
		   		});
		    }
		   
		   /**
		    * 给图表赋值,在异步加载成功以后调用
		    */
		   function setAttributeForMix_${parts.partId}(data){
		   		
		   		option_${parts.partId}.title.text = data.partName;
		   		
		   		if(null!=data.legendData && ""!=data.legendData){
					var legendData = data.legendData.split(",");
			   		jQuery.each(legendData, function(index, val) {
			   		  	option_${parts.partId}.legend.data.push(val);
			   		});
		   		}
		   		
		   		if(null!=data.buttomData && ""!=data.buttomData){
		   			var xAxis0 = data.buttomData.split(",");
			   		jQuery.each(xAxis0, function(index, val) {
			   		  	option_${parts.partId}.xAxis[0].data.push(val);
			   		});
		   		}
		   		if(null==data.seriesStr || ''==data.seriesStr){
		   			var item = {
			            name:data.partName,
			            type:'pie',
			            radius : '55%',
			            center: ['50%','60%'],
			            data:[]
		        	}
		   			option_${parts.partId}.series.push(item);
		   		}else{
		   			var datas = eval("["+data.seriesStr+"]");
	    			var item = {
			            name:data.partName,
			            type:'pie',
			            radius : '55%',
			            center: ['50%','60%'],
			            data:datas
		        	}
		        	option_${parts.partId}.series.push(item);
		   		}
		   		
			    if(data.values!=''){
		   			pieChart_${parts.partId}.hideLoading();
		   		}
		   		pieChart_${parts.partId}.setOption(option_${parts.partId});
		   		
		   }
		   
		   //异步加载echarts数据
		   jQuery(document).ready(function($) {
		   		send_${parts.partId}();
		   });
    </script>
    
<div class="drillCols_div_${parts.partId}" style="display:none;">
    <#list parts.drillCols as dcs>
    	${dcs.desc}<#if parts.drillCols?size gt dcs_index+1 >@</#if>
    </#list>
</div>