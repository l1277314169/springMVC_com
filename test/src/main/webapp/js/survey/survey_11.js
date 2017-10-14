function checkSurvey(flag){
	//layer.closeAll();
	if(flag){
		layer.closeAll();
	}
	
	flag = MTZ(flag);
	flag = checkDate(flag);
	
	//part1
	flag = checkPart1(flag,"367");
	flag = checkPart1(flag,"368");
	
	//最少上传一张照片
	flag = minImage(flag, "422_139_66");
	flag = minImage(flag, "423_140_67");
	flag = minImage(flag, "424_141_68");
	
	//图片最大只能上传5张
	flag = checkMaxImge(flag, "419_136_63");
	flag = checkMaxImge(flag, "420_137_64");
	flag = checkMaxImge(flag, "421_138_65");
	flag = checkMaxImge(flag, "422_139_66");
	flag = checkMaxImge(flag, "423_140_67");
	flag = checkMaxImge(flag, "424_141_68");
	
	flag = HJJS(flag);
	
	flag = part3NullCheck(flag);
	flag = part4NullCheck(flag);
	flag = part5NullCheck(flag);
	
	flag = part5VauleCheck(flag,"414");
	flag = part5VauleCheck(flag,"415");
	flag = part5VauleCheck(flag,"416");
	flag = part5VauleCheck(flag,"417");
	flag = part5VauleCheck(flag,"418");
	
	return flag;
}

//图片上限验证
function checkMaxImge(flag,id){
	setUploaderVal();
	var value = $("#"+id).val();
	if(!isEmpty(value)){
		var temp = value.split(",");
		if(temp.length>5){
			layer.tips('图片最大上传超过上限5张','#fileList_'+id,{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}


function MTZ(flag){
	setUploaderVal();
	var value = $("#419_136_63").val();
	if(isEmpty(value)){
		layer.tips('必须上传一张照片','#fileList_419_136_63',{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	
	if(!isEmpty(value)){
		var temp = value.split(",");
		if(temp.length>1){
			layer.tips('只能上传1张图片','#fileList_419_136_63',{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

function minImage(flag,id){
	setUploaderVal();
	var value = $("#"+id).val();
	if(isEmpty(value)){
		layer.tips('最少上传一张照片','#fileList_'+id,{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	return flag;
}

function checkDate(flag){
	var startDate = $("#428_145_72").val();
	if(isEmpty(startDate)){
		layer.tips('开始时间不能为空','#428_145_72',{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	
	var endDate = $("#428_146_72").val();
	if(isEmpty(endDate)){
		layer.tips('结束时间不能为空','#428_146_72',{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	
	 var d1 = new Date(startDate.replace(/\-/g, "\/"));  
	 var d2 = new Date(endDate.replace(/\-/g, "\/"));
	 
	 if(d1>=d2){  
		 layer.tips('开始时间不能大于结束时间','#428_146_72',{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	 }
	
	return flag;
}


//货架节数
function HJJS(flag){
	var b001 = $("#395_122_58").val();
	var b002 = $("#396_122_58").val();
	var b003 = $("#397_122_58").val();
	
	var b004 = $("#398_122_58").val();
	var b005 = $("#399_122_58").val();
	var b006 = $("#400_122_58").val();
	
	var b007 = $("#401_122_58").val();
	var b008 = $("#402_122_58").val();
	var b009 = $("#403_122_58").val();
	
	var b010 = $("#404_123_59").val();
	var b011 = $("#405_123_59").val();
	
	var total = parseFloat(getValue(b001)) + parseFloat(getValue(b002)) + parseFloat(getValue(b003)) + parseFloat(getValue(b004)) + parseFloat(getValue(b005)) 
				+ parseFloat(getValue(b006)) + parseFloat(getValue(b007)) + parseFloat(getValue(b008)) + parseFloat(getValue(b009)) + parseFloat(getValue(b010));
	
	if(total!=parseFloat(getValue(b011))){
		layer.tips('所有奶粉货架节数错误','#405_123_59',{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	return flag;
}

//前：空白  后：空白  前：1 后：不能空白
function checkPart1(flag,id){
	var a =  $("#"+id+"_120_57").val();
	var b =  $("#"+id+"_121_57").val();
	if(isEmpty(a)){
		if(!isEmpty(b)){
			layer.tips('是否分销未填写，是否缺货不能填写',"#"+id+"_121_57",{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	
	if(!isEmpty(a) && a==1){
		if(isEmpty(b)){
			layer.tips('有分销，是否缺货必须填写',"#"+id+"_121_57",{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}


/*function checkPart3(flag){
	var a = $("#425_142_69").attr("checked");
	if(!a){// 未选中
		var b = $("#406_124_60").attr("checked");
		var c = $("#406_125_60").attr("checked");
		var d = $("#406_126_60").attr("checked");
		var e = $("#407_127_60").attr("checked");
		var f = $("#407_128_60").attr("checked");
		var g = $("#408_129_60").attr("checked");
		var h = $("#408_130_60").attr("checked");
		var i = $("#408_131_60").attr("checked");
		var file = $("#422_139_66").val();
		if(isEmpty(file)){
			if(b || c || d || e || f || g || h || i){
				layer.tips('请至少上传一张照片','#fileList_422_139_66',{
				    tips: [2, '#3595CC'],
				    time: 20000,
				    tipsMore: true
				});
				flag = false;
			}
		}
	}
	return flag;
}*/

/*function checkPart4(flag){
	var a = $("#426_143_70").attr("checked");
	if(!a){// 未选中
		var b_1 = $("#409_132_61").val();
		var b_2 = $("#409_133_61").val();
		var c_1 = $("#410_132_61").val();
		var c_2 = $("#410_133_61").val();
		var d_1 = $("#411_132_61").val();
		var d_2 = $("#411_133_61").val();
		var e_1 = $("#412_132_61").val();
		var e_2 = $("#412_133_61").val();
		var f_1 = $("#413_132_61").val();
		var f_2 = $("#413_133_61").val();
		var file = $("#423_140_67").val();
		if(isEmpty(file)){
			if(!isEmpty(b_1) || !isEmpty(b_2) || !isEmpty(c_1) || !isEmpty(c_2) || !isEmpty(d_1) || !isEmpty(d_2) ||
					!isEmpty(e_1) || !isEmpty(e_2) || !isEmpty(f_1) || !isEmpty(f_2)){
				layer.tips('请至少上传一张照片','#fileList_423_140_67',{
				    tips: [2, '#3595CC'],
				    time: 20000,
				    tipsMore: true
				});
				flag = false;
			}
		}
	}
	return flag;
}*/


/*function checkPart5(flag){
	var a = $("#427_144_71").attr("checked");
	if(!a){// 未选中
		var b = $("#414_134_62").attr("checked");
		var c = $("#415_134_62").attr("checked");
		var d = $("#416_134_62").attr("checked");
		var e = $("#417_134_62").attr("checked");
		var f = $("#418_134_62").attr("checked");
		var file = $("#424_141_68").val();
		if(isEmpty(file)){
			if(b || c || d || e || f){
				layer.tips('请至少上传一张照片','#424_141_68',{
				    tips: [2, '#3595CC'],
				    time: 20000,
				    tipsMore: true
				});
				flag = false;
			}
		}
	}
	return flag;
}*/


function getValue(val){
	if(isEmpty(val)){
		val = 0;
	}
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


$(document).ready(function() {
	
	//parts3
	$("#425_142_69").click(function(){
		var flag = $(this).attr("checked");
		var ids =  new Array("406_124_60","406_125_60","406_126_60","407_127_60","407_128_60","408_129_60","408_130_60","408_131_60");
		if(flag){
			$.each(ids, function(index, val) {
				$("#"+val).attr("disabled",true);
			});
		}else{
			$.each(ids, function(index, val) {
				$("#"+val).attr("disabled",false);
			});
		}
	})
	$("#406_124_60,#406_125_60,#406_126_60,#407_127_60,#407_128_60,#408_129_60,#408_130_60,#408_131_60").click(function(){
		checkPart3();
	});
	
	//parts4
	$("#426_143_70").click(function(){
		var flag = $(this).attr("checked");
		var ids =  new Array("409_132_61","409_133_61","410_132_61","410_133_61","411_132_61","411_133_61","412_132_61","412_133_61","413_132_61","413_133_61");
		if(flag){
			$.each(ids, function(index, val) {
				$("#"+val).attr("disabled",true);
			});
		}else{
			$.each(ids, function(index, val) {
				$("#"+val).attr("disabled",false);
			});
		}
	})
	$("#409_132_61,#409_133_61,#410_132_61,#410_133_61,#411_132_61,#411_133_61,#412_132_61,#412_133_61,#413_132_61,#413_133_61").keyup(function(){
		checkPart4();
	});
	
	
	//parts5
	$("#427_144_71").click(function(){
		var flag = $(this).attr("checked");
		var ids =  new Array("414_134_62","415_134_62","416_134_62","417_134_62","418_134_62");
		var ids2 = new Array("414_135_62","415_135_62","416_135_62","417_135_62","418_135_62");
		
		if(flag){
			$.each(ids, function(index, val) {
				$("#"+val).attr("disabled",true);
			});
			
			$.each(ids2, function(index, val) {
				$("#"+val).attr("disabled",true);
			});
			
		}else{
			$.each(ids, function(index, val) {
				$("#"+val).attr("disabled",false);
			});
			
			$.each(ids2, function(index, val) {
				$("#"+val).attr("disabled",false);
			});
		}
	})
	$("#414_134_62,#415_134_62,#416_134_62,#417_134_62,#418_134_62").click(function(){
		checkPart5();
	});
	
	$("#414_135_62,#415_135_62,#416_135_62,#417_135_62,#418_135_62").keyup(function(){
		checkPart5();
	});
	
});

function checkPart3(){
	var ids =  new Array("406_124_60","406_125_60","406_126_60","407_127_60","407_128_60","408_129_60","408_130_60","408_131_60");
	var flag = allNotChecked(ids);
	if(flag){
		$("#425_142_69").attr("disabled",false);
	}else{
		$("#425_142_69").attr("disabled",true);
	}
}


function setPart3(){
	var flag = $("#425_142_69").attr("checked");
	if(flag){
		var ids =  new Array("406_124_60","406_125_60","406_126_60","407_127_60","407_128_60","408_129_60","408_130_60","408_131_60");
		$.each(ids, function(index, val) {
			$("#"+val).attr("disabled",true);
		});
	}
}

function part3NullCheck(flag){
	var bool = $("#425_142_69").attr("checked");
	if(!bool){
		var ids =  new Array("406_124_60","406_125_60","406_126_60","407_127_60","407_128_60","408_129_60","408_130_60","408_131_60");
		var tag = allNotChecked(ids);
		if(tag){
			layer.tips('必须选中一项',"#406_124_60",{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}



function checkPart4(){
	var ids =  new Array("409_132_61","409_133_61","410_132_61","410_133_61","411_132_61","411_133_61","412_132_61","412_133_61","413_132_61","413_133_61");
	var flag = allNotValue(ids);
	if(flag){
		$("#426_143_70").attr("disabled",false);
	}else{
		$("#426_143_70").attr("disabled",true);
	}
}


function setPart4(){
	var flag = $("#426_143_70").attr("checked");
	if(flag){
		var ids =  new Array("409_132_61","409_133_61","410_132_61","410_133_61","411_132_61","411_133_61","412_132_61","412_133_61","413_132_61","413_133_61");
		$.each(ids, function(index, val) {
			$("#"+val).attr("disabled",true);
		});
	}
}


function part4NullCheck(flag){
	var bool = $("#426_143_70").attr("checked");
	if(!bool){
		var ids =  new Array("409_132_61","409_133_61","410_132_61","410_133_61","411_132_61","411_133_61","412_132_61","412_133_61","413_132_61","413_133_61");
		var tag = allNotValue(ids);
		if(tag){
			layer.tips('必须填写一项',"#409_132_61",{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

function checkPart5(){
	var ids =  new Array("414_134_62","415_134_62","416_134_62","417_134_62","418_134_62");
	var ids2 = new Array("414_135_62","415_135_62","416_135_62","417_135_62","418_135_62");
	var flag = allNotChecked(ids);
	var flag2 = allNotValue(ids2);
	if(flag && flag2){
		$("#427_144_71").attr("disabled",false);
	}else{
		$("#427_144_71").attr("disabled",true);
	}
}


function setPart5(){
	var flag = $("#427_144_71").attr("checked");
	if(flag){
		var ids =  new Array("414_134_62","415_134_62","416_134_62","417_134_62","418_134_62");
		$.each(ids, function(index, val) {
			$("#"+val).attr("disabled",true);
		});
		var ids2 = new Array("414_135_62","415_135_62","416_135_62","417_135_62","418_135_62");
		$.each(ids2, function(index, val) {
			$("#"+val).attr("disabled",true);
		});
	}
}


function part5NullCheck(flag){
	var bool = $("#427_144_71").attr("checked");
	if(!bool){
		var ids =  new Array("414_134_62","415_134_62","416_134_62","417_134_62","418_134_62");
		var tag = allNotChecked(ids);
		if(tag){
			layer.tips('必须选中一项',"#414_134_62",{
			    tips: [2, '#3595CC'],
			    time: 20000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

//所有的都选中了，返回true
function allChecked(ids){
	var bool = true;
	$.each(ids, function(index, val) {
		var flag  =$("#"+val).attr("checked");
		if(!flag){
			bool = false;
			return false
		}
	});
	return bool;
}

//所有的都没选中，返回true
function allNotChecked(ids){
	var bool = true;
	$.each(ids, function(index, val) {
		var flag =$("#"+val).attr("checked");
		if(flag){
			bool = false;
			return false
		}
	});
	return bool;
}

//所有都没填值
function allNotValue(ids){
	var bool = true;
	$.each(ids, function(index, val) {
		var flag =$("#"+val).val();
		if(!isEmpty(flag)){
			bool = false;
			return false
		}
	});
	return bool;
}

function setPart(){
	setPart3();
	setPart4();
	setPart5();
}

function part5VauleCheck(flag,id){
	var bool =  $("#"+id+"_134_62").attr("checked");
	var value = $("#"+id+"_135_62").val();
	if(bool && isEmpty(value)){
		layer.tips('不能为空',"#"+id+"_135_62",{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	if(!bool && !isEmpty(value)){
		layer.tips('不能有值',"#"+id+"_135_62",{
		    tips: [2, '#3595CC'],
		    time: 20000,
		    tipsMore: true
		});
		flag = false;
	}
	return flag;
}


