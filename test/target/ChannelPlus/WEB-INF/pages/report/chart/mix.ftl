	<#include "/common/taglibs.ftl" />
	<div id="main_mix_${parts.partId}" class="echarts_div" style="margin-top:40px;height:${parts.layoutVo.height}px;width:${parts.layoutVo.width}%;"></div>
	<input type="hidden" id="${parts.partId}_hid" name="${parts.partId}_hid" value="<#if parts.setting??>${parts.setting.buttomUnit!''}</#if>" />
    <script type="text/javascript">
    		//--- 折柱 --- 
            var mixChart_${parts.partId} = echarts.init(document.getElementById('main_mix_${parts.partId}'),"infographic");
            
            var clientId = $("#clientId").val();
            if(clientId==11){ //费列罗
            	mixChart_${parts.partId}.setTheme(firms());
            }

			//echarts showLoading方法无法使用，如果查询不到数据时，会将noDataLoading移除掉，将导致页面一片空白
			mixChart_${parts.partId}.showLoading({
			    text: '正在努力的读取数据中...'
			});
			
			var option_${parts.partId} = {
            		title : {
				        text: '',
				        y:'top',
				        x:'center',
				        textStyle :{
				        	fontSize: 14
				        }
				    },
                    tooltip : {
				        trigger: 'axis'
				    },
				    noDataLoadingOption :{
				    	text :'${parts.partName}，未能查询到数据',
				    	effect:'whirling',
				    	textStyle:{
				    		fontSize:'18'
				    	}
				    },
				    calculable : false,
				    legend: {
				    	y:'bottom',
				        data:[]
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data :[]
				        },
				        {
				            type : 'category',
				            data : []
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name : '',
				            axisLabel : {
				                formatter: "{value} <#if parts.setting??>${parts.setting.leftUnit!''}</#if>"
				            }
				        },
				        {
				            type : 'value',
				            name : '',
				            axisLabel : {
				                formatter: "{value} <#if parts.setting??>${parts.setting.rightUnit!''}</#if>"
				            }
				        }
				        
				    ],
				    series : []
            }
			
	
			//绑定事件
		    function eConsole_${parts.partId}(param) {
		    	var drillCols_div = jQuery(".drillCols_div_${parts.partId}").html();
		    	var cols = drillCols_div.split("@");
		    	for (var i = 0; i < cols.length; i++) {
		    		var v = jQuery.trim(cols[i]);
		    		if(param.seriesName==v){		    			
		    			var formVal = jQuery("#searchForm").serialize();
		    			var params = "isDrill=1&drillType="+v+"&partId="+${parts.partId};
		    			drillReport(params,param.seriesName);
		    		}
		    	}   
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
		    

		   //异步加载echarts数据
		   jQuery(document).ready(function($) {
		   		send_${parts.partId}();		   		
		   });
		   
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
			   			var v = $("#${parts.partId}_hid").val();
			   		  	option_${parts.partId}.xAxis[0].data.push(val+v);
			   		});
		   		}
		   		
		   		//option_${parts.partId}.yAxis[0].name='%'
		   		//option_${parts.partId}.yAxis[1].name=data.rightName;
		   		
		   		jQuery.each(data.series, function(index, val) {
		   			if(null!=val && ""!=val){
		   				var datas = [];
		   				if(""!=val.data){
		   					datas = val.data.split(",");
		   					for (var i = 0; i < datas.length; i++) { //将数组转换为number类型,否则会导致计算错误
		   						datas[i] = (Number(datas[i]));
		   					}
		   				}
		   					   	
		   				var item2 = {
				            name:val.name,
				            type:val.type,
				            data:datas
				        };
			        
				        if(val.place=='right'){
				        	item2.yAxisIndex=1;
				        }
				        option_${parts.partId}.series.push(item2);
				        
		   			}
		   		});
		   		
		   		if(data.values!=''){
		   			mixChart_${parts.partId}.hideLoading();
		   		}
		   		mixChart_${parts.partId}.setOption(option_${parts.partId});
		   		mixChart_${parts.partId}.on(echarts.config.EVENT.CLICK, eConsole_${parts.partId});
		   }

    </script>
    
<div class="drillCols_div_${parts.partId}" style="display:none;">
    <#list parts.drillCols as dcs>
    	${dcs.desc}<#if parts.drillCols?size gt dcs_index+1 >@</#if>
    </#list>
</div>