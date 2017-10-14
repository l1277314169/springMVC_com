$(document).ready(function() {
	loadChannel();
	loadProvinces();
	loadAM();
	loadRM();
	
	$("#ddlProvince_ddl").live("mouseover",function(){
		$("#cbChannel_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu2").hide();
		$("#ddlProvince_PopupMenu").show();
	});
	
	$("#cbChannel_ddl").live("mouseover",function(){
		$("#ddlProvince_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu2").hide();
		$("#cbChannel_PopupMenu").show();
	});
	
	$("#div_ddlKeyAccount").live("mouseover",function(){
		$("#cbChannel_PopupMenu").hide();
		$("#ddlProvince_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu2").hide();
		$("#ddlKeyAccount_PopupMenu").show();
	});
	
	$("#div_ddlKeyAccount2").live("mouseover",function(){
		$("#cbChannel_PopupMenu").hide();
		$("#ddlProvince_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu").hide();
		$("#ddlKeyAccount_PopupMenu2").show();
	});
	//渠道
	$("input[name='argChainIds_but']").live("click",function(){
		var name = $(this).val();
		var data = $(this).attr("data");
		$("#lblSelectedcbChannel").html(name);
		$("#channel").val(data);
		$("#cbChannel_PopupMenu").hide();
	});
	
	//AM
	$("input[name='argAccountIds_but']").live("click",function(){
		var name= $(this).val();
		var data=$(this).attr("data");
		$("#lblSelectedddlKeyAccount").html(name);
		$("#amName").val(data);
		$("#ddlKeyAccount_PopupMenu").hide();
	});
	
	//PM
	$("input[name='argRmAccountIds_but']").live("click",function(){
		var name=$(this).val();
		var data=$(this).attr("data");
		$("#lblSelectedddlKeyAccount1").html(name);
		$("#rmName").val(data);
		$("#ddlKeyAccount_PopupMenu2").hide();
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
	
	$("input[name='ddlPeriod']").live("click",function(){
		var data = $(this).attr("data");
		var value = $(this).val();
		$("#yearId").val(data);
		
		var val = $('#div_ddlPeriod input[type=button].btnPeriodSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'btnPeriod');
        }
        $(this).attr('class', 'btnPeriodSelected');
		
        loadOverView();
	});
	
	
	$("input[name='ddlPeriod2']").live("click",function(){
		var data = $(this).attr("data");
		var value = $(this).val();
		$("#yearId").val(data);
		
		var val = $('#div_ddlPeriod input[type=button].btnPeriodSelected');
        for (var i = 0; i < val.length; i++) {
            $(val[i]).attr('class', 'btnPeriod');
        }
        $(this).attr('class', 'btnPeriodSelected');
		
		refreshDashboard();
	});
	
	$(document).live("click",function(){
		$("#cbChannel_PopupMenu").hide();
		$("#ddlProvince_PopupMenu").hide();
	});
	
	loadOverView();
});

function loadProvinces(){
	var root = $("#_root").val();
	$("#ddlProvince_PopupMenu").html("");
	var _url = root+"/commService/getProvinceAjax";
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			var div = '<div><input type="button" name="ddlProvince" provinceId="0" value="All Provinces" id="ddlProvince_rpItems_Item_00" class="RBDropdownListItem province_items"></div>';
			$("#ddlProvince_PopupMenu").append(div);
			$.each(data, function(index, val) {
				var div = '<div><input type="button" name="ddlProvince" provinceId="'+val.provinceId+'" value="'+val.province+'" id="ddlProvince_rpItems_Item_'+index+'" class="RBDropdownListItem province_items"></div>';
				$("#ddlProvince_PopupMenu").append(div);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert(errorThrown);
		}
	});
}


function loadChannel() {
	var root = $("#_root").val();
	$("#channel_tbody").html("");
	var _url = root+'/channel/getTreeNode';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var tr = '<tr><td><span class="rbDdlCB"><input type="button" name="argChainIds_but" data="'+val.id+'" value="'+val.name+'" id="argChainIds_but'+index+'" class="RBDropdownListItem"></span></td></tr>';
				$("#channel_tbody").append(tr);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			
		}
	});
}

function loadAM(){
	var root = $("#_root").val();
	$("#Account_tbody").html("");
	var _url = root+'/comms/getClientUser?positionName=AM';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var tr = '<tr><td><span class="rbDdlCB"><input type="button" name="argAccountIds_but" data="'+val.clientUserId+'" value="'+val.loginName+'" id="ddlKeyAccount_rpItems_Item'+index+'" class="RBDropdownListItem"/></span></td></tr>';
				$("#Account_tbody").append(tr);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
			
		}
	});
}

function loadRM(){
	var root=$("#_root").val();
	$("#Account1_tbody").html("");
	var _url = root+'/comms/getClientUser?positionName=RM';
	jQuery.ajax({
		url : _url,
		type : 'POST',
		dataType : 'json',
		cache : false,
		success : function(data, textStatus, xhr) {
			$.each(data, function(index, val) {
				var tr = '<tr><td><span class="rbDdlCB"><input type="button" name="argRmAccountIds_but" data="'+val.clientUserId+'" value="'+val.loginName+'" id="ddlKeyAccount_rpItems_rm_Item'+index+'" class="RBDropdownListItem"/></span></td></tr>';
				$("#Account1_tbody").append(tr);
			});
		},
		error : function(xhr, textStatus, errorThrown) {
		}
		
	});
}

function loadOverView(){
	var root = $("#_root").val();
	var _url = root+'/apple/loadOverViewContent';
	//jQuery("body").showLoading();
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
			//jQuery("body").hideLoading();
		}
	});
}


function setValues(data){
	var root = $("#_root").val();
	var promoter1 = null;
	var promoter1_name = null;
	var promoter2 = null;
	var promoter2_name = null;
	
	var promoter3 = null;
	var promoter3_name = null;
	var promoter4 = null;
	var promoter4_name = null;
	
	var promoter5 = null;
	var promoter5_name = null;
	var promoter6 = null;
	var promoter6_name = null;
	
	var promoter7 = null;
	var promoter7_name = null;
	var promoter8 = null;
	var promoter8_name = null;
	
	var promoter9 = null;
	var promoter9_name = null;
	var promoter10 = null;
	var promoter10_name = null;
	
	$.each(data, function(index, val) {
		if(val.displayType==0){
			var $numOfStores = $('#_lblNumOfStores');
            $numOfStores.html('0');
            if (parseInt(val.value) > 0) {
                $numOfStores.animateNumber(parseInt(val.value), '', '');
            }
		}else if(val.displayType==1){
			promoter1 = parseInt(val.value);
			promoter1_name = val.displayDesc;
		}else if(val.displayType==2){
			promoter2 = parseInt(val.value);
			promoter2_name = val.displayDesc;
		}else if(val.displayType==3){
			promoter3 = parseInt(val.value);
			promoter3_name = val.displayDesc;
		}else if(val.displayType==4){
			promoter4 = parseInt(val.value);
			promoter4_name = val.displayDesc;
		}else if(val.displayType==5){
			promoter5 = parseInt(val.value);
			promoter5_name = val.displayDesc;
		}else if(val.displayType==6){
			promoter6 = parseInt(val.value);
			promoter6_name = val.displayDesc;
		}else if(val.displayType==7){
			promoter7 = parseInt(val.value);
			promoter7_name = val.displayDesc;
		}else if(val.displayType==8){
			promoter8 = parseInt(val.value);
			promoter8_name = val.displayDesc;
		}else if(val.displayType==9){
			promoter9 = parseInt(val.value);
			promoter9_name = val.displayDesc;
		}else if(val.displayType==10){
			promoter10 = parseInt(val.value);
			promoter10_name = val.displayDesc;
		}else if(val.displayType==11){
			var $promotionBoxValue1 = $('#promotionBoxValue1');
			$promotionBoxValue1.html('0');
            if (parseInt(val.value) > 0) {
            	$promotionBoxValue1.animateNumber(parseInt(val.value), '', '');
            }
		}else if(val.displayType==12){
			drawGaugeChart(root,"promotionBox2Value",parseInt(val.value));
		}else if(val.displayType==13){
			drawGaugeChart(root,"promotionBox3Value",parseInt(val.value));
		}
	});
	var promoterGraphVal = '[{"Value": '+promoter1+',"Label": "'+promoter1_name+'"},{"Value": '+promoter2+',"Label": "'+promoter2_name+'"}]';
	drawHBarChart("distributionBox",eval(promoterGraphVal));
	
	var promoterGraphVal2 = '[{"Value": '+promoter3+',"Label": "'+promoter3_name+'"},{"Value": '+promoter4+',"Label": "'+promoter4_name+'"}]';
	drawHBarChart("shareOfDMBox",eval(promoterGraphVal2));
	
	var promoterGraphVal3 = '[{"Value": '+promoter5+',"Label": "'+promoter5_name+'"},{"Value": '+promoter6+',"Label": "'+promoter6_name+'"}]';
	drawHBarChart("shareOfShelfBox",eval(promoterGraphVal3));
	
	var promoterGraphVal4 = '[{"Value": '+promoter7+',"Label": "'+promoter7_name+'"},{"Value": '+promoter8+',"Label": "'+promoter8_name+'"}]';
	drawHBarChart("promoterBox",eval(promoterGraphVal4));
	
	var promoterGraphVal5 = '[{"Value": '+promoter9+',"Label": "'+promoter9_name+'"},{"Value": '+promoter10+',"Label": "'+promoter10_name+'"}]';
	drawHBarChart("planoBox",eval(promoterGraphVal5));
	
	//jQuery("body").hideLoading();
}

function refreshDashboard(){
	//jQuery("body").showLoading();
	var root = $("#_root").val();
    var urlLink = root+'/apple/loadAppleDashboardVo';
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
           // jQuery("body").hideLoading();
        },
        error: function (msg) {
           // alert(msg);
           // jQuery("body").hideLoading();
        }
    });
}

