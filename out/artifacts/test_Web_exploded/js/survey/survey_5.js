function checkSurvey(flag){
	if (flag) {
		layer.closeAll();
	}
	calcArea();
	
	$.each(getCrestIds(), function(index, val) { //所有佳洁士
		var v = $("#"+val).val();
		if(isEmpty(v)){
			layer.tips('必录，如果门店没有陈列填“0”','#'+val,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	});
	
	//start:竞品陈列数量和面积必须同时有值
	//大堆
	flag = checkJP(flag,"192"); //Crest 佳洁士
	flag = checkJP(flag,"193"); //Darlie 黑人 
	flag = checkJP(flag,"194"); //YNBY 云南白药
	flag = checkJP(flag,"195"); //Zhonghua 中华
	
	//小堆
	flag = checkJP(flag,"201"); //Crest 佳洁士
	flag = checkJP(flag,"202"); //Darlie 黑人 
	flag = checkJP(flag,"203"); //YNBY 云南白药
	flag = checkJP(flag,"204"); //Zhonghua 中华
	
	//端架
	flag = checkJP(flag,"210"); //Crest 佳洁士
	flag = checkJP(flag,"211"); //Darlie 黑人 
	flag = checkJP(flag,"212"); //YNBY 云南白药
	flag = checkJP(flag,"213"); //Zhonghua 中华
	
	//门店柱子
	flag = checkJP(flag,"219"); //Crest 佳洁士
	flag = checkJP(flag,"220"); //Darlie 黑人 
	flag = checkJP(flag,"221"); //Listerine李施德林
	flag = checkJP(flag,"222"); //Smiclean可洁可净
	
	//落地架
	flag = checkJP(flag,"228"); //Crest 佳洁士
	flag = checkJP(flag,"229"); //Darlie 黑人 
	flag = checkJP(flag,"230"); //YNBY 云南白药
	flag = checkJP(flag,"231"); //Zhonghua 中华
	flag = checkJP(flag,"232"); //Smiclean可洁可净
	//end:竞品陈列数量和面积必须同时有值
	
	//start:高露洁大堆(大堆头-面积为2平米或以上的堆头)牙刷面积+牙膏面积+漱口水面积 大于等于2
	flag = checkDDT(flag,"187");
	flag = checkDDT(flag,"188");
	flag = checkDDT(flag,"189");
	flag = checkDDT(flag,"190");
	flag = checkDDT(flag,"191");
	//end:高露洁大堆(大堆头-面积为2平米或以上的堆头)牙刷面积+牙膏面积+漱口水面积 大于等于2
	
	//start:竞品大堆（牙刷面积+牙膏面积+漱口水面积）/陈列数量  大于等于2
	flag = checkDDT_JP(flag,"192");
	flag = checkDDT_JP(flag,"193");
	flag = checkDDT_JP(flag,"194");
	flag = checkDDT_JP(flag,"195");
	//end:竞品大堆（牙刷面积+牙膏面积+漱口水面积）/陈列数量  大于等于2
	
	//start:高露洁小堆(堆头-面积为2平米以下的堆头)牙刷面积+牙膏面积+漱口水面积 小于2
	flag = checkXDT(flag, "196");
	flag = checkXDT(flag, "197");
	flag = checkXDT(flag, "198");
	flag = checkXDT(flag, "199");
	flag = checkXDT(flag, "200");
	//end:高露洁小堆(堆头-面积为2平米以下的堆头)牙刷面积+牙膏面积+漱口水面积 小于2
	
	//start:针对高露洁的二级陈列：牙刷面积有值，SKU数不一定有值；牙刷SKU数有值，牙刷面积一定要有值；牙膏面积有值，牙膏SKU数一定要有值；牙膏SKU数有值，牙膏面积一定要有值
	flag = check2JCL(flag, "187");
	flag = check2JCL(flag, "188");
	flag = check2JCL(flag, "189");
	flag = check2JCL(flag, "190");
	flag = check2JCL(flag, "191");
	
	flag = check2JCL(flag, "196");
	flag = check2JCL(flag, "197");
	flag = check2JCL(flag, "198");
	flag = check2JCL(flag, "199");
	flag = check2JCL(flag, "200");
	
	flag = check2JCL(flag, "205");
	flag = check2JCL(flag, "206");
	flag = check2JCL(flag, "207");
	flag = check2JCL(flag, "208");
	flag = check2JCL(flag, "209");
	
	flag = check2JCL(flag, "214");
	flag = check2JCL(flag, "215");
	flag = check2JCL(flag, "216");
	flag = check2JCL(flag, "217");
	flag = check2JCL(flag, "218");
	
	flag = check2JCL(flag, "223");
	flag = check2JCL(flag, "224");
	flag = check2JCL(flag, "225");
	flag = check2JCL(flag, "226");
	flag = check2JCL(flag, "227");
	
	flag = check2JCL(flag, "233");
	flag = check2JCL(flag, "234");
	flag = check2JCL(flag, "235");
	flag = check2JCL(flag, "236");
	flag = check2JCL(flag, "237");
	
	flag = check2JCL(flag, "238");
	flag = check2JCL(flag, "239");
	flag = check2JCL(flag, "240");
	flag = check2JCL(flag, "241");
	flag = check2JCL(flag, "242");
	
	flag = check2JCL(flag, "243");
	flag = check2JCL(flag, "244");
	flag = check2JCL(flag, "245");
	flag = check2JCL(flag, "246");
	flag = check2JCL(flag, "247");
	
	//end:针对高露洁的二级陈列：牙刷面积有值，SKU数不一定有值；牙刷SKU数有值，牙刷面积一定要有值；牙膏面积有值，牙膏SKU数一定要有值；牙膏SKU数有值，牙膏面积一定要有值
	
	
	//start:竞品小堆
	flag = checkXDT_JP(flag, "201");
	flag = checkXDT_JP(flag, "202");
	flag = checkXDT_JP(flag, "203");
	flag = checkXDT_JP(flag, "204");
	//end:竞品小堆
	
	return flag;
}


$(document).ready(function() {
	$("input[type='text']").blur(function(){
		calcArea();
	});
});


//计算总面积
function calcArea(){
	$("input[type='hidden']").each(function(){
		var id = $(this).attr("id");
		if(null!=id && id!=''){
			var ids = id.split("_");
			if(ids.length==3 && ids[1]==39){
				var a_39_id = ids[0]+"_39_"+ids[2];
				var a_40_id = ids[0]+"_40_"+ids[2];
				var a_43_id = ids[0]+"_43_"+ids[2];
				var a_54_id = ids[0]+"_54_"+ids[2];
				
				var a_40_val = getValue($("#"+a_40_id).val());
				var a_43_val = getValue($("#"+a_43_id).val());
				var a_54_val = getValue($("#"+a_54_id).val());
				var area = parseFloat(parseFloat(a_40_val)+parseFloat(a_43_val)+parseFloat(a_54_val));
				if(isNaN(area)){
					area = 0;
				}
				$("#"+a_39_id).val(area);
			}
		}
		
	});
}

//验证大堆头（2.高露洁大堆(大堆头-面积为2平米或以上的堆头)牙刷面积+牙膏面积+漱口水面积 大于等于2）
function checkDDT(flag,id){
	var a_val = $("#"+id+"_40_14").val(); //牙刷面积
	var b_val = $("#"+id+"_43_14").val(); //牙膏面积
	var c_val = $("#"+id+"_54_14").val(); //漱口水面积
	if(!isEmpty(a_val) || !isEmpty(b_val) || !isEmpty(c_val)){
		var baseID = id+"_39_14";
		var alertId = id+"_40_14";
		var val = $("#"+baseID).val();
		if(parseFloat(val)<2){
			layer.tips('大堆头总面积不能小于2','#'+alertId,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

//竞品大堆（牙刷面积+牙膏面积+漱口水面积）/陈列数量  大于等于2
function checkDDT_JP(flag,id){
	var a_val = $("#"+id+"_40_14").val(); //牙刷面积
	var b_val = $("#"+id+"_43_14").val(); //牙膏面积
	var c_val = $("#"+id+"_54_14").val(); //漱口水面积
	if(!isEmpty(a_val) || !isEmpty(b_val) || !isEmpty(c_val)){
		var baseID = id+"_39_14"; //总面积
		var val = $("#"+baseID).val();
		var items = $("#"+id+"_38_14").val(); //竞品数量
		var alertId = id+"_38_14";
		
		if((parseFloat(val)/parseInt(items))<2){
			layer.tips('竞品大堆总面积/陈列数量 不能小于2','#'+alertId,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

//验证小堆头 (高露洁小堆(堆头-面积为2平米以下的堆头)牙刷面积+牙膏面积+漱口水面积 小于2)
function checkXDT(flag,id){
	var a_val = $("#"+id+"_40_14").val(); //牙刷面积
	var b_val = $("#"+id+"_43_14").val(); //牙膏面积
	var c_val = $("#"+id+"_54_14").val(); //漱口水面积
	if(!isEmpty(a_val) || !isEmpty(b_val) || !isEmpty(c_val)){
		var baseID = id+"_39_14";
		var val = $("#"+baseID).val();
		var alertId = id+"_40_14";
		if(parseFloat(val)>2){
			layer.tips('小堆头总面积不能大于2','#'+alertId,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}


function checkXDT_JP(flag,id){
	var a_val = $("#"+id+"_40_14").val(); //牙刷面积
	var b_val = $("#"+id+"_43_14").val(); //牙膏面积
	var c_val = $("#"+id+"_54_14").val(); //漱口水面积
	if(!isEmpty(a_val) || !isEmpty(b_val) || !isEmpty(c_val)){
		var baseID = id+"_39_14";
		var val = $("#"+baseID).val();
		var items = $("#"+id+"_38_14").val(); //竞品数量
		var alertId = id+"_38_14";
		
		if((parseFloat(val)/parseInt(items))>2){
			layer.tips('竞品小堆总面积/陈列数量 不能大于2','#'+alertId,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

//竞品陈列数量和面积必须同时有值
function checkJP(flag,id){
	var baseID = id+"_39_14";
	var baseVal = $("#"+baseID).val();
	var a = $("#"+id+"_40_14").val();
	var b = $("#"+id+"_43_14").val();
	var c = $("#"+id+"_54_14").val();
	var alertId = id+"_38_14";
	
	if(!isEmpty(baseVal) && baseVal>0){
		if(isEmpty(a) && isEmpty(b) && isEmpty(c)){
			layer.tips('竞品陈列数量和面积必须同时有值','#'+alertId,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	if(!isEmpty(a) || !isEmpty(b) || !isEmpty(c)){
		if(isEmpty(baseVal)){
			layer.tips('竞品陈列数量和面积必须同时有值','#'+alertId,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

//针对高露洁的二级陈列：牙刷面积有值，SKU数不一定有值；牙刷SKU数有值，牙刷面积一定要有值；牙膏面积有值，牙膏SKU数一定要有值；牙膏SKU数有值，牙膏面积一定要有值
function check2JCL(flag,id){
	var baseID = id+"_40_14";
	var ys_area = $("#"+baseID).val(); //牙刷面积
	var sku_360_id = id+"_41_14";
	var sku_qr_id = id+"_42_14";
	var sku_360 = $("#"+sku_360_id).val();//360牙膏
	var sku_qr  = $("#"+sku_qr_id).val();//纤柔牙刷
	
	if(!isEmpty(sku_360) || !isEmpty(sku_qr)){
		if(isEmpty(ys_area)){
			layer.tips('牙刷SKU数有值，牙刷面积一定要有值','#'+baseID,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	
	var base_yg_id = id+"_43_14";
	var yg_area = $("#"+base_yg_id).val();//牙膏面积
	var sku_a = $("#"+id+"_44_14").val();
	var sku_b = $("#"+id+"_45_14").val();
	var sku_c = $("#"+id+"_46_14").val();
	var sku_d = $("#"+id+"_47_14").val();
	var sku_e = $("#"+id+"_48_14").val();
	var sku_f = $("#"+id+"_49_14").val();
	var sku_g = $("#"+id+"_50_14").val();
	var sku_h = $("#"+id+"_51_14").val();
	var sku_i = $("#"+id+"_52_14").val();
	
	if(!isEmpty(yg_area)){ //牙膏面积有值sku一定要有值
		if(isEmpty(sku_a) && isEmpty(sku_b) && isEmpty(sku_c) && isEmpty(sku_d) && isEmpty(sku_e) && isEmpty(sku_f) && isEmpty(sku_g) && isEmpty(sku_h) && isEmpty(sku_i)){
			layer.tips('牙膏面积有值sku一定要有值','#'+base_yg_id,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	
	if(!isEmpty(sku_a) || !isEmpty(sku_b) || !isEmpty(sku_c) || !isEmpty(sku_d) || !isEmpty(sku_e) || !isEmpty(sku_f) || !isEmpty(sku_g) || !isEmpty(sku_h) || !isEmpty(sku_i)){
		if(isEmpty(yg_area)){
			layer.tips('牙膏SKU数有值，牙膏面积一定要有值','#'+base_yg_id,{
			    tips: [2, '#3595CC'],
			    time: 15000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	
	return flag;
	
}


function getCrestIds(){
	var ids = new Array('192_38_14','192_40_14','192_43_14','192_54_14');
	return ids;
}


function getValue(val){
	var temp=/^\d+(\.\d+)?$/;
	if(!temp.test(val)){
		val = 0;
	}
	/*if(isEmpty(val)||isNaN(val)){
		val = 0;
	}*/
	return val;
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
