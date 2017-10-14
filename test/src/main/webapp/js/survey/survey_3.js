function checkSurvey(flag){
	if (flag) {
		layer.closeAll();
	}
	$("input[type='text']").each(function(){
		var id = $(this).attr("id");
		if($.inArray(id,getSkuArray())==-1){//sku不需要验证价格
			var value = $("#"+id).val();
			if(!isEmpty(value) && value==1){ //有售卖，必须有价格
				var ids = id.split("_");
				if(ids.length==3 && ids[1]==1){
					var priceId4 = ids[0]+"_4_"+ids[2];
					if(existsID(priceId4)){
						var priceVal = $("#"+priceId4).val();
						if(isEmpty(priceVal)){
							layer.tips('有售卖必须有价格', '#'+priceId4,{
							    tips: [2, '#3595CC'],
							    time: 15000,
							    tipsMore: true
							});
							flag = false;
						}
					}
				}
			}else if(!isEmpty(value) && value==0){ //无售卖，必定无SKU数&陈列面数&价格
				var ids = id.split("_");
				if(ids.length==3 && ids[1]==1){
					var skuCountId = ids[0]+"_2_"+ids[2];//sku数
					var displayId = ids[0]+"_3_"+ids[2];//陈列面数
					var priceId = ids[0]+"_4_"+ids[2]; //价格
					
					if(existsID(skuCountId)){
						if(!isEmpty($("#"+skuCountId).val())){
							layer.tips('无售卖SKU数不能有值', '#'+skuCountId,{
							    tips: [2, '#3595CC'],
							    time: 15000,
							    tipsMore: true
							});
							flag = false;
						}
					}
					if(existsID(displayId)){
						if(!isEmpty($("#"+displayId).val())){
							layer.tips('无售卖陈列面数不能有值', '#'+displayId,{
							    tips: [2, '#3595CC'],
							    time: 15000,
							    tipsMore: true
							});
							flag = false;
						}
					}
					if(existsID(priceId)){
						if(!isEmpty($("#"+priceId).val())){
							layer.tips('无售卖价格不能有值', '#'+priceId,{
							    tips: [2, '#3595CC'],
							    time: 15000,
							    tipsMore: true
							});
							flag = false;
						}
					}
				}
			}
		}
	});
	
	//sku验证
	/*var skuIds = getSkuArray();
	$.each(skuIds, function(index, val) {
		var ids = val.split("_");
		var priceId3 = ids[0]+"_3_"+ids[2];
		var priceId2 = ids[0]+"_2_"+ids[2];
		
		var value = $("#"+val).val();
		if(!isEmpty(value) && value==1){
			if(existsID(priceId3) && existsID(priceId2)){
				var priceId3Val = $("#"+priceId3).val();
				var priceId2Val = $("#"+priceId2).val();
				if(isEmpty(priceId3Val) && isEmpty(priceId2Val)){
					layer.tips('有售卖必须有价格', '#'+priceId3,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
					flag = false;
				}
			}else if(existsID(priceId3)){
				var priceId3Val = $("#"+priceId3).val();
				if(isEmpty(priceId3Val)){
					layer.tips('有售卖必须有价格', '#'+priceId3,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
					flag = false;
				}
			}
		}else{
			var priceId3Val = $("#"+priceId3).val();
			var priceId2Val = $("#"+priceId2).val();
			if(existsID(priceId3) && existsID(priceId2)){
				var priceId3Val = $("#"+priceId3).val();
				var priceId2Val = $("#"+priceId2).val();
				if(!isEmpty(priceId3Val)){
					layer.tips('无售卖价格不能有值', '#'+priceId3,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
					flag = false;
				}else if(!isEmpty(priceId2Val)){
					layer.tips('无售卖价格不能有值', '#'+priceId2,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
					flag = false;
				}
			}else if(existsID(priceId3)){
				var priceId3Val = $("#"+priceId3).val();
				if(!isEmpty(priceId3Val)){
					layer.tips('无售卖价格不能有值', '#'+priceId3,{
					    tips: [2, '#3595CC'],
					    time: 15000,
					    tipsMore: true
					});
					flag = false;
				}
			}
			
		}
	});*/
	
	
	//5.a012-a003中只要有一个有分销，那么a035必须为有分销
	var aIds = getA012ToA003Ids();
	var a35 = $("#48_1_1").val();
	if(a35!=1){
		$.each(aIds, function(index, val) {
			var v = $("#"+val+"_1_1").val();
			if(!isEmpty(v) && v==1){
				layer.tips('a012-a003中只要有一个有分销，那么a035必须为有分销','#48_1_1',{
				    tips: [2, '#3595CC'],
				    time: 15000,
				    tipsMore: true
				});
				flag = false;
			}
		});
	}
	
	//6.b015-b120中只要一个有分销，b028必须为有分销
	var bIds = getB015ToB120Ids();
	var b028 = $("#98_1_1").val();
	if(b028!=1){
		$.each(bIds, function(index, val) {
			var v = $("#"+val+"_1_1").val();
			if(!isEmpty(v) && v==1){
				layer.tips('b015-b120中只要一个有分销，b028必须为有分销','#98_1_1',{
				    tips: [2, '#3595CC'],
				    time: 15000,
				    tipsMore: true
				});
				flag = false;
			}
		});
	}
	
	//7.b301-b314中只要一个有分销，b311必须为有分销
	var b2Ids = getB301ToB314Ids();
	var b311 = $("#115_1_1").val();
	if(b311!=1){
		$.each(b2Ids, function(index, val) {
			var v = $("#"+val+"_1_1").val();
			if(!isEmpty(v) && v==1){
				layer.tips('b301-b314中只要一个有分销，b311必须为有分销','#115_1_1',{
				    tips: [2, '#3595CC'],
				    time: 15000,
				    tipsMore: true
				});
				flag = false;
			}
		});
	}
	
	//8.c104-c105中只要一个有分销，c011必须为有分销
	var cIds = getC104toC105Ids();
	var c011 = $("#136_1_1").val();
	if(c011!=1){
		$.each(cIds, function(index, val) {
			var v = $("#"+val+"_1_1").val();
			if(!isEmpty(v) && v==1){
				layer.tips('c104-c105中只要一个有分销，c011必须为有分销','#136_1_1',{
				    tips: [2, '#3595CC'],
				    time: 15000,
				    tipsMore: true
				});
				flag = false;
			}
		});
	}
	
	return flag;
}

function getC104toC105Ids(){
	var ids = new Array('122','123','124','125','126','127','128','129','130','131','132','133','134','135');
	return ids;
}

function getB301ToB314Ids(){//缺少301
	var ids = new Array('106','107','108','109','110','111','112','113','114');
	return ids;
}

function getB015ToB120Ids(){
	var ids = new Array('49','50','55','56','58','59','61','62','54','51','52','57','60','64','63','65','66','67','70','72','73','68','69','71','74','75','76','77','78','79','80','81','82','83','84','85','86','87','88','89','90','91','92','93','94','95','96','97');
	return ids;
}

function getA012ToA003Ids(){
	var ids = new Array('1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47');
	return ids;
}

function getSkuArray(){
	var skuArray = new Array("48_1_1","100_1_1","101_1_1","102_1_1","103_1_1","104_1_1","105_1_1","98_1_1","115_1_1","116_1_1","117_1_1","118_1_1","119_1_1","120_1_1","121_1_1","136_1_1","137_1_1","138_1_1");
	return skuArray;
}

//判断id是否存在
function existsID(id){
	if($("#"+id).length>0){
		return true;
	}else{
		return false;
	} 
}

//判断数据是否空
function isEmpty(val){
	if(null==val || ''==val){
		return true;
	}else{
		return false;
	}
}
