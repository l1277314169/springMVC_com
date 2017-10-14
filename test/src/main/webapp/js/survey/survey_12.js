function checkSurvey(flag){
	if(flag){
		layer.closeAll();
	}
	flag = select2Empty(flag);
//	flag = select2Empty(flag);
	flag = checkDate(flag);
	flag = checkMaxImge(flag,"457_163_79");
	flag = checkStoreAddr(flag);
	var arr1 = isVerify("441_159_78", "441_160_78" ,"441_161_78" ,"441_162_78");	//A001
	var arr2 = isVerify("442_159_78", "442_160_78" ,"442_161_78" ,"442_162_78");	//A002
	var arr3 = isVerify("443_159_78", "443_160_78" ,"443_161_78" ,"443_162_78");	//A003
	var arr4 = isVerify("444_159_78", "444_160_78" ,"444_161_78" ,"444_162_78");	//A004
	var arr5 = isVerify("445_159_78", "445_160_78" ,"445_161_78" ,"445_162_78");	//A005
	var arr6 = isVerify("446_159_78", "446_160_78" ,"446_161_78" ,"446_162_78");	//A006
	var arr7 = isVerify("447_159_78", "447_160_78" ,"447_161_78" ,"447_162_78");	//A007
	var arr8 = isVerify("448_159_78", "448_160_78" ,"448_161_78" ,"448_162_78");	//A008
	var arr9 = isVerify("449_159_78", "449_160_78" ,"449_161_78" ,"449_162_78");	//A009
	var arr10 = isVerify("450_159_78", "450_160_78" ,"450_161_78" ,"450_162_78");	//A010
	var arr11 = isVerify("451_159_78", "451_160_78" ,"451_161_78" ,"451_162_78");	//A011
	var arr12 = isVerify("452_159_78", "452_160_78" ,"452_161_78" ,"452_162_78"); 	//A012
	var arr13 = isVerify("453_159_78", "453_160_78" ,"453_161_78" ,"453_162_78"); 	//A013
	var arr14 = isVerify("454_159_78", "454_160_78" ,"454_161_78" ,"454_162_78"); 	//A014
	var arr15 = isVerify("455_159_78", "455_160_78" ,"455_161_78" ,"455_162_78"); 	//A015
	var arr16 = isVerify("456_159_78", "456_160_78" ,"456_161_78" ,"456_162_78"); 	//A016

	var array = new Array();
	array = arrayToArray(array,arr1);
	array = arrayToArray(array,arr2);
	array = arrayToArray(array,arr3);
	array = arrayToArray(array,arr4);
	array = arrayToArray(array,arr5);
	array = arrayToArray(array,arr6);
	array = arrayToArray(array,arr7);
	array = arrayToArray(array,arr8);
	array = arrayToArray(array,arr9);
	array = arrayToArray(array,arr10);
	array = arrayToArray(array,arr11);
	array = arrayToArray(array,arr12);
	array = arrayToArray(array,arr13);
	array = arrayToArray(array,arr14);
	array = arrayToArray(array,arr15);
	array = arrayToArray(array,arr16);
	
	for(var i = 0 ; i < array.length ; i++){
		message = array[i];
		layer.tips(message.message, "#"+message.id,{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
	}
	if(array.length > 0){
		return false;
	}
	return flag;
}

function setData(){
	for(var i = 41 ; i <= 53; i++){
		var em = $("#4"+i+"_161_78");
		var vr = em.val();
		var id = "4"+i+"_161_78";
		id = id.replace('161','162');
		if(!isEmpty(vr)){
			$("#"+id).select2({
				width:206,
				multiple: true,
				allowClear: false,
				data: getData(vr)
			});
		}
	}
}


/*$(function (){
	
	setTimeout(function (){
		for(var i = 41 ; i <= 53; i++){
			var em = $("#4"+i+"_161_78");
			var vr = em.val();
			var id = "4"+i+"_161_78";
			id = id.replace('161','162');
			if(!isEmpty(vr)){
				$("#"+id).select2({
					width:206,
//					placeholder: "请选择",
					multiple: true,
					allowClear: false,
					data: getData(vr)
				});
			}
		}
	}, "800"); 
	
	$("#441_161_78,#442_161_78,#443_161_78,#444_161_78," +
			"#445_161_78,#446_161_78,#447_161_78,#448_161_78," +
			"#449_161_78,#450_161_78,#451_161_78,#452_161_78").blur(function (){
				
		var vr = this.value;
		var id = this.name;
		id = id.replace('161','162');
		if(!isEmpty(vr)){
			$("#"+id).select2({
				width:206,
//				placeholder: "请选择",
				multiple: true,
				allowClear: true,
				data: getData(vr)
			});
			$("#"+id).val(0);
			$("#s2id_"+id).val(0);
		}
	});
});*/


function getData(key){
	var data12 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' },{ id: 7, text: '7' }, { id: 8, text: '8' },{ id: 9, text: '9' }, { id: 10, text: '10' }, { id: 11, text: '11' }, { id: 12, text: '12' }];
	var data11 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' },{ id: 7, text: '7' }, { id: 8, text: '8' },{ id: 9, text: '9' }, { id: 10, text: '10' }, { id: 11, text: '11' }];
	var data10 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' },{ id: 7, text: '7' }, { id: 8, text: '8' },{ id: 9, text: '9' }, { id: 10, text: '10' }];
	var data9 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' },{ id: 7, text: '7' }, { id: 8, text: '8' },{ id: 9, text: '9' }];
	var data8 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' },{ id: 7, text: '7' }, { id: 8, text: '8' }];
	var data7 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' },{ id: 7, text: '7' }];
	var data6 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }, { id: 6, text: '6' }];
	var data5 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' },{ id: 5, text: '5' }];
	var data4 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }, { id: 4, text: '4' }];
	var data3 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' },{ id: 3, text: '3' }];
	var data2 = [{ id: 0, text: '0' }, { id: 1, text: '1' }, { id: 2, text: '2' }];
	var data1 = [{ id: 0, text: '0' }, { id: 1, text: '1' }];
	var data0 = [{ id: 0, text: '0' }];
	if(!isEmpty(key)){
		if(key ==  12 ){
			return data12;
		}else if(key ==  11){
			return data11;
		}else if(key ==  10){
			return data10;
		}else if(key ==  9){
			return data9
		}else if(key ==  8){
			return data8
		}else if(key ==  7){
			return data7
		}else if(key ==  6){
			return data6
		}else if(key ==  5){
			return data5
		}else if(key ==  4){
			return data4
		}else if(key ==  3){
			return data2
		}else if(key ==  2){
			return data2
		}else if(key ==  1){
			return data1
		}else{
			return data0
		}
	}
	return null;
}



function checkStoreAddr(flag){
	var a = $("#439_152_76").val();
	if(a=='0'){
		var b = $("#439_153_76").val();
		if(isEmpty(b)){
			layer.tips('正确地址需要填写', '#439_153_76',{
			    tips: [2, '#3595CC'],
			    time: 10000,
			    tipsMore: true
			});
			flag = false;
		}
	}else if(a=='1'){
		var b = $("#439_153_76").val();
		if(!isEmpty(b)){
			layer.tips('正确地址无需填写', '#439_153_76',{
			    tips: [2, '#3595CC'],
			    time: 10000,
			    tipsMore: true
			});
			flag = false;
		}
	}
	return flag;
}

function select2Empty(flag){
	var v_156 = $("#440_156_77").select2("val");
	if(isEmpty(v_156)){
		layer.tips('请选择', '#s2id_440_156_77',{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
		flag = false;
	}else{
		if(v_156=="8"){
			$("#440_158_77").removeAttr("disabled");
		}else{
			$("#440_158_77").attr("disabled","disabled");
		}
	}
	/*var v_157 = $("#440_157_77").val();
	if(isEmpty(v_157)){
		layer.tips('请选择', '#s2id_440_157_77',{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
		flag = false;
	}*/
	return flag;
}

/**
 * 如果为空返回 true  否则返回  false
 * 			
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val){
	if(null==val || ''==val){
		return true;
	}else{
		return false;
	}
}
/**
 * 数组合并
 * @param array		保存数组
 * @param toArray	需要被合并的数组
 */

function arrayToArray(array,toArray){
	if(array.length == 0){
		return toArray;
	}
	for(var i = 0 ; i < array.length ; i++){
		message = array[i];
		toArray[toArray.length] = message;
	}
	return toArray;
}

/**
 * 
 * @param v1	分销商
 * @param v2	陈列面数
 * @param v3	货架总层数
 * @param v4	产品摆放在第几层
 * @returns {Array}	
 */

function isVerify(v1, v2 ,v3 ,v4){
	var arr = new Array();
	var ta1 = $("#"+v1).val();	//分销商
	var tb1 = $("#"+v2).val();	//陈列面数
	var tc1 = $("#"+v3).val();	//货架总层数
	var td1 = $("#"+v4).val();	//产品摆放在第几层
//	if(tb1 < 0){
//		arr[arr.length] = new resultMessage(v2,"陈列面必须大于0");
//	}else if(tc1 < 0){
//		arr[arr.length] = new resultMessage(v3,"货架总层数必须大于0");
//	}
	if(ta1<0 || ta1>4){
		arr[arr.length] = new resultMessage(v1,"分销商必须1~4.");
	}else{
		if(ta1.length == 0 || ta1 == "" || null == ta1){
			if(tb1.length != 0 || tc1.length != 0 || td1.length != 0){
				arr[arr.length] = new resultMessage(v1,"分销商必须不能为空");
			}
		}
		if(ta1 == 0|| ta1 == 2 || ta1 == 3 || ta1 ==4){
			//所有必须为0
			if(tb1 !=  0 || tb1 == ""){
				if(ta1.length != 0 || tc1.length != 0 || td1.length != 0){
					arr[arr.length] = new resultMessage(v2,"陈列面必须为0");
				}
			}else if(tc1 != 0 || tc1 == ""){
				arr[arr.length] = new resultMessage(v3,"货架总层数必须为0");
			}else if(td1 != 0 || td1 == "" ){
//				var td2 = $("#s2id_"+v4).val();
//				alert("A_"+td1 + "B_"+td2);
//				if(td1.length != 0){
					arr[arr.length] = new resultMessage("s2id_"+v4,"产品摆放必须为0");
//				}
			}
		}else if(ta1 == 1 || ta1 == "1"){
			if(tb1 ==  0 || tb1 == "" || tb1 <0 || tb1.length == 0){
				arr[arr.length] = new resultMessage(v2,"陈列面必须为大于0");
			}else if(tc1 <0 || tc1 == "" || tc1 > 12 || tc1.length == 0){
				arr[arr.length] = new resultMessage(v3,"货架总层数必须是1~12");
			}else if(td1 <= 0 || td1 == "" || td1 > 12 || td1.length == 0){
				arr[arr.length] = new resultMessage("s2id_"+v4,"产品摆放在必须是1~12");
			}
			
			var maxT1c1 = parseInt(tc1);
			if(!isNaN(td1)){
				var currentVal = parseInt(td1);
				var maxFlagTrue = false;
				if(currentVal>maxT1c1){
					maxFlagTrue = true;
				}
				if(maxFlagTrue){
					arr[arr.length] = new resultMessage(v4,"产品摆放层数不能大于货架总层数");
				}
			}else{
				var td1s = td1.split(",");
				var maxFlagTrue = false;
				for(var i in td1s){
					var currentVal = parseInt(i);
					if(currentVal>maxT1c1){
						maxFlagTrue = true;
					}
				}
				if(maxFlagTrue){
					arr[arr.length] = new resultMessage(v4,"产品摆放层数不能大于货架总层数");
				}
			}
			if(tb1.length >0 ){
				if(tc1 <= 0 || tc1 == "" || tc1 > 12 || tc1.length == 0){
					arr[arr.length] = new resultMessage(v3,"货架总层数必须是1~12");
				} 
			}
			
			if(tc1.length != 0){
				if(td1.length == 0){
					arr[arr.length] = new resultMessage("s2id_"+v4,"货架总层数必须是1~12");
				}
			}
		}	
	}
	
//	if(ta1<0 || ta1>4){
//		arr[arr.length] = new resultMessage(v1,"分销商必须1~4.");
//	}else if(tb1<0 ){
//		arr[arr.length] = new resultMessage(v2,"陈列面数必须大于0");
//	}else if(tc1<0 || tc1>12){
//		arr[arr.length] = new resultMessage(v3,"货架总层数1~12层.");
//	}else if(td1<0 || td1>12){
//		arr[arr.length] = new resultMessage(v4,"产品只能摆放在1~12层");
//	}else {
//		//分销商处理
//		if(ta1 == 1){
//			if(tb1 < 1){
//				arr[arr.length] = new resultMessage(v2,"分销为1时，陈列面数必须大于0 ");
//			}
//		}else{
//			if(tb1 != 0){
//				arr[arr.length] = new resultMessage(v2,"陈列面数必须等于0");
//			}else if(tc1 != 0){
//				arr[arr.length] = new resultMessage(v3,"货架总层数必须等于0");
//			}else if(td1 != 0){
//				arr[arr.length] = new resultMessage(v4,"产品摆放数必须等于0");
//			}
//		}
//		//陈列面数
//		if(tb1 == 0){
//			if(ta1==0 || ta1 == 2 || ta1 == 3 || ta1 == 4){
//				
//			}else{
//				arr[arr.length] = new resultMessage(v2,"分销必须等于1");
//			}
//		}else{
//			if(ta1 != 1){
//				arr[arr.length] = new resultMessage(v2,"陈列面数必须等于0");
//			}
//		}
//		//货架总层数
//		var max = getMax(td1);
//		if(max >12 || max < 0){
//			arr[arr.length] = new resultMessage(v4,"产品摆必须放在第1~12层");
//		}
//		
//		//货架总层数
////		if(tc1 > max || tc1 < td1){
////			if(!isEmpty(td1)){
////				if(ta1 == 1){
////					if(td1 != ""){
////						arr[arr.length] = new resultMessage(v3,"货架总层数应大于等于产品摆放的最大层数值");
////					}
////				}
////			}
////		}
//		if(ta1 == 1 && tb1 > 0 ){
//			if(tc1 == 0){
//				arr[arr.length] = new resultMessage(v3,"货架总层数不能为0");
//			}
////			else if(td1 ==0 ){
////				arr[arr.length] = new resultMessage(v4,"产品摆放在第几层不能为0");
////			}
//		}
//		if(!isEmpty(tb1)){
//			if(!isEmpty(tc1)){
//				if(!isEmpty(td1)){
//					if(isEmpty(ta1)){
//						if(tb1!=0 && tc1 != 0 && td1!=0){
//							arr[arr.length] = new resultMessage(v1,"分销必须是1");
//						}else{
//							arr[arr.length] = new resultMessage(v1,"分销必须是0、2、3、4");
//						}
//					}
//				}
//			}
//		}
//		//摆放位置
//		if(tb1 > 0 && tc1 > 0){
//			if(td1.length == 0){
//				arr[arr.length] = new resultMessage(v4,"摆放位置不能为空");
//			}
//		}
//	}
	return arr;
}

/**
 * 寻求最大值
 * @param vr
 */

function getMax(vr){
	var tdall = vr.split(',');
	var max = -1;
	if(tdall.length > 1){
		for(var i in tdall){
			if(tdall[i] > max){
				max = tdall[i];
			}
		}
	}else{
		max = vr;
	}
}

/**
 * 返回实体对象
 * 
 */
function resultMessage(id,message){ //use factory 
	var obj=new Object(); 
	obj.id=id; 
	obj.message=message; 
	return obj; 
}


function checkDate(flag){
	var startDate = $("#439_154_76").val();
	if(isEmpty(startDate)){
		layer.tips('开始时间不能为空','#439_154_76',{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
		flag = false;
	}
	
	var endDate = $("#439_155_76").val();
	if(isEmpty(endDate)){
		layer.tips('结束时间不能为空','#439_155_76',{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
		flag = false;
	}
	
	 var d1 = new Date(startDate.replace(/\-/g, "\/"));  
	 var d2 = new Date(endDate.replace(/\-/g, "\/"));
	 
	 if(d1>=d2){  
		 layer.tips('开始时间不能大于结束时间','#439_154_76',{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
		flag = false;
	 }
	
	return flag;
}

function checkMaxImge(flag,id){
	setUploaderVal();
	var value = $("#"+id).val();
	var temp = value.split(",");
	if(temp.length<3){
		layer.tips('图片最少上传3张','#fileList_'+id,{
		    tips: [2, '#3595CC'],
		    time: 10000,
		    tipsMore: true
		});
		flag = false;
	}
	return flag;
}
