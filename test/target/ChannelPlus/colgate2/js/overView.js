$(document).ready(function() {
	loadChannel();
	loadAPG();
	loadAccounts();
	loadRegions();
	
	$("input[name='argCategorys']").live("click",function(){
		var categorys = $("input[name='argCategorys']");		
		var datas = new Array();
		var i = 0;
		$.each(categorys, function(index, val) {
			var checked = $(val).attr("checked");
			var label = $(val).next("label").html();
			if(checked){
				datas[i] = label;
				i++;
			}
		});
		if(datas.length==categorys.length){
			$("#lblSelectedcbCategory").html("All Categories");
		}else{
			$("#lblSelectedcbCategory").html("");
			$.each(datas, function(index, val) {
				if(index<datas.length-1){
					$("#lblSelectedcbCategory").append(val+",<br />");
				}else{
					$("#lblSelectedcbCategory").append(val);
				}
			});
		}
	});
	
	
	//渠道
	$("input[name='argChannelIds']").live("click",function(){
		var selectChannel = $("#lblSelectedcbChannel").html();		
		var channelIds = $("input[name='argChannelIds']");		
		var datas = new Array();
		var i = 0;
		$.each(channelIds, function(index, val) {
			var checked = $(val).attr("checked");
			var label = $(val).next("label").html();
			if(checked){
				datas[i] = label;
				i++;
			}
		});
		if(datas.length==channelIds.length){
			$("#lblSelectedcbChannel").html("All Channels");
		}else{
			$("#lblSelectedcbChannel").html("");
			$.each(datas, function(index, val) {
				if(index<datas.length-1){
					$("#lblSelectedcbChannel").append(val+",<br />");
				}else{
					$("#lblSelectedcbChannel").append(val);
				}
			});
		}
	});
	
	//APG 连锁
	$("input[name='argChainIds_but']").live("click",function(){
		var name = $(this).val();
		var data = $(this).attr("data");
		$("#lblSelectedddlKeyAccount").html(name);
		$("#argChainIds").val(data);
		$("#ddlKeyAccount_PopupMenu").hide();
	});
	
	//Accounts
	$("input[name='argAccountIds_but']").live("click",function(){
		var name = $(this).val();
		var data = $(this).attr("data");
		$("#lblSelectedddlCustomer").html(name);
		$("#argAccountIds").val(data);
		$("#ddlCustomer_PopupMenu").hide();
	});
	
	//brand
	$("input[name='arg_brand_name_but']").live("click",function(){
		var name = $(this).val();
		var data = $(this).attr("data");
		$("#lblSelectedrbBrand").html(name);
		$("#argBrandName").val(data);
		$("#rbBrand_PopupMenu").hide();
	});
	
	
	//Regions
	$("input[name='argStructureId']").live("click",function(){
		var depts = $("input[name='argStructureId']");
		var datas = new Array();
		var vals = new Array();
		var i = 0;
		$.each(depts, function(index, val) {
			var checked = $(val).attr("checked");
			var label = $(val).next("label").html();
			if(checked){
				datas[i] = label;
				vals[i] = $(val).val();
				i++;
			}
		});
		if(datas.length==depts.length){
			$("#lblSelectedcbRegion").html("All Regions");
		}else{
			$("#lblSelectedcbRegion").html("");
			$.each(datas, function(index, val) {
				if(index<datas.length-1){
					$("#lblSelectedcbRegion").append(val+",");
				}else{
					$("#lblSelectedcbRegion").append(val);
				}
			});
		}
		loadProvinces(vals.join(",")); 
	});
	
	//省份监听
	$(".province_items").live("click",function(){
		var data = $(this).val();
		var provinceId = $(this).attr("provinceId");
		$("#lblSelectedddlProvince").html(data);
		$("#ddlProvince_PopupMenu").hide();
		$("#province").val(provinceId);
		loadCitys(provinceId);
	});

	$(".cityItems").live("click",function(){
		var data = $(this).val();
		var cityId = $(this).attr("cityId");
		$("#lblSelectedddlCity").html(data);
		$("#city").val(cityId);
		$("#ddlCity_PopupMenu").hide();
	});
	
	
	$("input[name='ddlPeriod']").live("click",function(){
		var data = $(this).attr("data");
		var value = $(this).val();
		$("#argMonthId").val(data);
		
		var val = $('#div_ddlPeriod input[type=button].btnPeriodSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'btnPeriod');
        }
        $(this).attr('class', 'btnPeriodSelected');
		
		refresh();
	});
	
	
	$("input[name='ddlPeriod2']").live("click",function(){
		var data = $(this).attr("data");
		var value = $(this).val();
		$("#argMonthId").val(data);
		
		var val = $('#div_ddlPeriod input[type=button].btnPeriodSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'btnPeriod');
        }
        $(this).attr('class', 'btnPeriodSelected');
		
		refreshDashboard();
	});
	
	$("input[name='dashboardItems']").live("click",function(){
		var data = $(this).attr("data");
		var value = $(this).val();
		$("#dashboardId").val(data);
		var val = $('#dbMask input[type=button].NewMenuButtonSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'NewMenuButton');
        }
        $(this).attr('class', 'NewMenuButtonSelected');
		refreshDashboard();
	});
	
});


function loadChannel() {
	var root = $("#_root").val();
	var _url = root+'/channel/getTreeNode';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var tr = '<tr><td><span class="rbDdlCB"><input id="argChannelIds'+index+'" type="checkbox" name="argChannelIds" checked="checked" value="'+val.id+'"><label for="cbChannel_Items_0">'+val.name+'</label></span></td></tr>';
				$("#channel_tbody").append(tr);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

function loadAPG(){
	var root = $("#_root").val();
	var _url = root+'/chain/getTreeNode';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var div = '<div><input type="button" name="argChainIds_but" data="'+val.id+'" value="'+val.name+'" id="argChainIds_but'+index+'" class="RBDropdownListItem"></div>';
				$("#ddlKeyAccount_PopupMenu").append(div);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

function loadAccounts(){
	var root = $("#_root").val();
	var _url = root+'/comms/getAccount';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var div = '<div><input type="button" name="argAccountIds_but" data="'+val.optionValue+'" value="'+val.optionText+'" id="ddlCustomer_rpItems_Item_'+index+'" class="RBDropdownListItem"></div>';
				$("#ddlCustomer_PopupMenu").append(div);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

function loadRegions(){
	var root = $("#_root").val();
	var _url = root+'/colgate/loadColagetRegions?parentId=432';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			
			var datas = new Array();
			var i = 0;
			$.each(data, function(index, val) {
				var tr = '<tr><td><span class="rbDdlCB"><input id="argStructureId'+index+'" type="checkbox" name="argStructureId" checked="checked" value="'+val.clientStructureId+'"><label for="cbRegion_Items_0">'+val.structureNameEn+'</label></span></td></tr>';
				$("#cbRegion_PopupMenu_tbody").append(tr);
				datas[i] = val.clientStructureId;
			});
			loadProvinces(datas.join(","));
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}


function loadProvinces(value){
	if(null==value || ''==value){
		return;
	}
	$("#lblSelectedddlProvince").html("All Provinces");
	$("#province").val("");
	$("#lblSelectedddlCity").html("All Cities");
	$("#city").val("");
	
	var root = $("#_root").val();
	$("#ddlProvince_PopupMenu").html("");
	var _url = root+'/colgate/loadColagetRegions?parentId='+value;
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var div = '<div><input type="button" name="ddlProvince" provinceId="'+val.clientStructureId+'" value="'+val.structureName+'" id="ddlProvince_rpItems_Item_'+index+'" class="RBDropdownListItem province_items"></div>';
				$("#ddlProvince_PopupMenu").append(div);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

function loadCitys(value){
	if(null==value || ''==value){
		return;
	}
	$("#lblSelectedddlCity").html("All Cities");
	$("#city").val("");
	$("#ddlCity_PopupMenu").html("");
	var root = $("#_root").val();
	var _url = root+'/colgate/loadColagetRegions?parentId='+value;
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var div = '<div><input type="button" name="ddlCity" cityId="'+val.clientStructureId+'" value="'+val.structureName+'" id="ddlCity_rpItems_Item_'+index+'" class="RBDropdownListItem cityItems"></div>';
				$("#ddlCity_PopupMenu").append(div);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}

function loadOverView(){
	var root = $("#_root").val();
	var _url = root+'/colgate/loadOverViewContent';
	jQuery("body").showLoading();
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		data : $("#form1").serialize(),
		success : function(data, textStatus, xhr) {
			setValues(data);
		},
		error : function(xhr, textStatus, errorThrown) {
			
		}
	});
}


function refreshDashboard(){
	var dashBoradId = $("#dashboardId").val();
	if("5003"==dashBoradId){
		$("#brands_List").hide();
	}else{
		$("#brands_List").show();
	}
	_dbLoadDashboard();
}


function getIntValue(id){
	var val = $("#"+id).val();
	if(val==null || ''==val){
		val = 0;
	}
	return parseInt(val);
}

function setValues(data){
	var root = $("#_root").val();
	var dis1 = null;
	var dis2 = null;
	var dis3 = null;
	
	var sha1 = null;
	var sha2 = null;
	var sha3 = null;
	
	var promoter1 = null;
	var promoter2 = null;
	
	var plannoBox1 = null;
	var plannoBox2 = null;
	var plannoBox3 = null;
	
	var plano2Box1 = null;
	var plano2Box2 = null;
	var plano2Box3 = null;
	
	$.each(data, function(index, val) {
		if(val.displayType==0){
			//alert(val.value);
			//drawNumLabelChart("_lblNumOfStores",parseInt(val.value));
			var $numOfStores = $('#_lblNumOfStores');
            $numOfStores.html('0');
            if (parseInt(val.value) > 0) {
                $numOfStores.animateNumber(parseInt(val.value), '', '');
            }
			
		}else if(val.displayType==8){
			//alert(val.value);
			//drawNumLabelChart("promotionBoxValue1",parseInt(val.value));
			var $promotionBoxValue1 = $('#promotionBoxValue1');
			$promotionBoxValue1.html('0');
            if (parseInt(val.value) > 0) {
            	$promotionBoxValue1.animateNumber(parseInt(val.value), '', '');
            }
		}else if(val.displayType==10){
			drawGaugeChart(root,"promotionBox3Value",parseInt(val.value));
		}else if(val.displayType==9){
			drawGaugeChart(root,"promotionBox2Value",parseInt(val.value));
		}else if(val.displayType==1){
			if(val.category==1){
				dis1 = parseInt(val.value);
			}else if(val.category==2){
				dis2 = parseInt(val.value);
			}else if(val.category==3){
				dis3 = parseInt(val.value);
			}
		}else if(val.displayType==3){
			if(val.category==1){
				sha1 = parseInt(val.value);
			}else if(val.category==2){
				sha2 = parseInt(val.value);
			}else if(val.category==3){
				sha3 = parseInt(val.value);
			}
		}else if(val.displayType==4){
			promoter1 = parseInt(val.value);
		}else if(val.displayType==5){
			promoter2 = parseInt(val.value);
		}else if(val.displayType==6){
			if(val.category==1){
				plannoBox1 = parseInt(val.value);
			}else if(val.category==2){
				plannoBox2 = parseInt(val.value);
			}else if(val.category==3){
				plannoBox3 = parseInt(val.value);
			}
		}else if(val.displayType==7){
			if(val.category==1){
				plano2Box1 = parseInt(val.value);
			}else if(val.category==2){
				plano2Box2 = parseInt(val.value);
			}else if(val.category==3){
				plano2Box3 = parseInt(val.value);
			}
		}
	});
	
	var distArray = [['Toothpaste', dis1], ['Toothbrush', dis2], ['Mouthwash', dis3]];
	drawBarChart("distributionBox",distArray);
	
	var shaArray = [['Toothpaste', sha1], ['Toothbrush', sha2], ['Mouthwash', sha3]];
	drawBarChart("shareOfShelfBox",shaArray);
	
	var promoterGraphVal = '[{"Value": '+promoter1+',"Label": "Dress Code Compliance"},{"Value": '+promoter2+',"Label": "Stores with Promoters"}]';
	drawHBarChart("promoterBox",eval(promoterGraphVal));
	
	var data1 = '[{"Value": '+plannoBox1+',"Label": "Toothpaste"},{"Value": '+plannoBox2+',"Label": "Toothbrush"},{"Value": '+plannoBox3+',"Label": "Mouthwash"}]';
	drawBubbleChart("plano1Box",eval(data1));
	
	var data2 = '[{"Value": '+plano2Box1+',"Label": "Toothpaste"},{"Value": '+plano2Box2+',"Label": "Toothbrush"},{"Value": '+plano2Box3+',"Label": "Mouthwash"}]';
	drawBubbleChart("plano2Box",eval(data2));
	
	jQuery("body").hideLoading();
}


function _dbLoadDashboard() {
	jQuery("body").showLoading();
	var root = $("#_root").val();
    var urlLink = root+'/colgate/loadDashboardVo';
    $.ajax({
        type: "POST",
        url: urlLink,
        data: $("#form1").serialize(),
        dataType: "json",
        success: function (dashboard) {
            //var dashboard = response.d;
            try { $('#dashboardLoading').hide(); } catch (exErr2) { }
            $('#btnRefresh').attr('class', 'btnNewRefresh');
            $('#btnRefresh').attr('onclick', 'refresh();return false;');
            var $numOfStores = $('#lblNumOfStores');
            $numOfStores.html('0');
            if (dashboard.StoresAudited > 0) {
                $numOfStores.animateNumber(dashboard.StoresAudited, '', '');
            }

            switch (dashboard.DashboardId) {
                case 5001:
                    $(dbContentsId).show();
                    _dbOverview(dashboard);
                    break;
                case 5002:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbDistribution(dashboard);
                    break;
                case 5003:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbPriceVsRSP(dashboard);
                    break;
                case 5004:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbShareOfShelf(dashboard);
                    break;
                case 5005:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbDM(dashboard);
                    break;
                case 5006:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbSKUOnShelf(dashboard);
                    break;
                case 5007:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbSecondaryDisplay(dashboard);
                    break;
                case 5008:
                    $(dbContentsId).empty();
                    $(dbContentsId).show();
                    _dbSecondaryDisplay(dashboard);
                    break;

            }
            jQuery("body").hideLoading();
        },
        error: function (msg) {
            alert(msg);
            jQuery("body").hideLoading();
        }

    });
}




