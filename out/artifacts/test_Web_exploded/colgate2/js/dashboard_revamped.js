var filters;
var selectedDashboardId = 0;
var selectedDashboardName = '';
var dbContentsId;
var filterObject;
var isIE8 = 0;
var isIE9 = 0;

function _dbSelectDashboard(dashboardId, dashboardName,callRefresh) {

    selectedDashboardId = dashboardId;
    selectedDashboardName = dashboardName;
    _dbSetSelectedDashboard(dashboardId, dashboardName);
    if (dashboardId == 5008) {
        $('.sideFilterPanelTop').css("padding-top", "7px");
        $('#lblDashboardFilterTitle').html("<span style='font-size:14px;'>" + selectedDashboardName + "</span>");
    }
    else {
        $('.sideFilterPanelTop').css("padding-top", "12px");
        $('#lblDashboardFilterTitle').html(selectedDashboardName);
    }

    _dbGetFilter(callRefresh);
}
function _dbSetSelectedDashboard(dashboardId, dashboardName) {
    $('#hfDashboardId').val("" + dashboardId);
    $('#hfDashboardName').val("" + dashboardName);
}

function _dbGetFilter(callRefresh) {
    _dbHideAllFilters();
    filterObject = new Object();
    var urlLink = 'Dashboard/DashboardService.asmx/GetFilter';
    if (window.location.toString().indexOf('/Dashboard/') > -1) {
        urlLink = '../' + urlLink;
    }
    $.ajax({
        type: "POST",
        url: urlLink,
        data: "{dashboardId:" + selectedDashboardId + "}",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            filters = response.d;

            $.each(filters, function (index, filter) {


                $('#div_' + filter.FilterName).show();

                if (filter.FilterName == 'rbBrand') {
                    _rpSetRBBrandOptions(selectedDashboardId);
                }


                filterObject[filter.FilterName] = '';

            });

            if (callRefresh) {
                $('#btnRefresh').click();
            }


        },
        failure: function (msg) {
            alert(msg);
        }

    });
}

function _rpSetRBBrandOptions(dbId) {
    $('#rbBrand_rpItems_Item_1').show();


    if (dbId == 5004) {

        $('#rbBrand_rpItems_Item_1').hide();
        $('#rbBrand_rpItems_Item_0').click();
    }


}

function _dbHideAllFilters() {
    //$(".filterRow").hide();
    

}


function _dbValidateAndPopulateFilters() {

    $.each(filters, function (index, filter) {

        var filterName = filter.FilterName + "";

        if (filterName.indexOf("ddl") == 0) {


            filterObject[filter.FilterName] = __rbGetDDLSelectedValue(filterName);

        }
        else if (filterName.indexOf("rb") == 0) {

            filterObject[filter.FilterName] = __rbGetDDLSelectedValue(filterName);

        }
        else if (filterName.indexOf("cb") == 0) {
            var str = '';
            var valStr = '';
            var val = $('#div_' + filterName + ' input[type=checkbox]:checked');
            var valLabel = $('#div_' + filterName + ' input[type=checkbox]:checked + label');
            for (var i = 0; i < val.length; i++) {
               
                if (valStr.length > 0) {
                    valStr = valStr + ',';
                }
               
                valStr = valStr + $(val[i]).val();
            }
            
            filterObject[filter.FilterName] = valStr;

        }


    });



    var filterStringData = _dbGetFilterStringData(filterObject);
    try { $(dbContentsId).hide(); } catch (exErr) { }
    try { $('#dashboardLoading').show(); } catch (exErr2) { }  
    var filterStringData = _dbGetFilterStringData(filterObject);
    
    _dbLoadDashboard(filterStringData);

}

function _dbGetFilterStringData(_filterObject) {

    var dataStr = 'dashboardId:' + selectedDashboardId + ",";
    if (typeof filterObject["ddlPeriod"] === "undefined") {
        dataStr = dataStr + "period:'',";
    }
    else {
        dataStr = dataStr + "period:'" + filterObject["ddlPeriod"] + "',";
    }

    if (typeof filterObject["cbCategory"] === "undefined") {
        dataStr = dataStr + "category:'',";
    }
    else {
        dataStr = dataStr + "category:'" + filterObject["cbCategory"] + "',";
    }

    if (typeof filterObject["rbBrand"] === "undefined") {
        dataStr = dataStr + "brand:'',";
    }
    else {
        dataStr = dataStr + "brand:'" + filterObject["rbBrand"] + "',";
    }

    if (typeof filterObject["cbChannel"] === "undefined") {
        dataStr = dataStr + "channel:'',";
    }
    else {
        dataStr = dataStr + "channel:'" + filterObject["cbChannel"] + "',";
    }

    if (typeof filterObject["ddlKeyAccount"] === "undefined") {
        dataStr = dataStr + "keyAccount:'',";
    }
    else {
        dataStr = dataStr + "keyAccount:'" + filterObject["ddlKeyAccount"] + "',";
    }

    if (typeof filterObject["ddlCustomer"] === "undefined") {
        dataStr = dataStr + "customer:'',";
    }
    else {
        dataStr = dataStr + "customer:'" + filterObject["ddlCustomer"] + "',";
    }

    if (typeof filterObject["cbRegion"] === "undefined") {
        dataStr = dataStr + "region:'',";
    }
    else {
        dataStr = dataStr + "region:'" + filterObject["cbRegion"] + "',";
    }

    if (typeof filterObject["ddlProvince"] === "undefined") {
        dataStr = dataStr + "province:'',";
    }
    else {
        dataStr = dataStr + "province:'" + filterObject["ddlProvince"] + "',";
    }

    if (typeof filterObject["ddlCity"] === "undefined") {
        dataStr = dataStr + "city:''";
    }
    else {
        dataStr = dataStr + "city:'" + filterObject["ddlCity"] + "'";
    }

    dataStr = "{" + dataStr + "}";

    return dataStr;

}

function _dbLoadDashboard(_filterStringData) {

    var urlLink = 'Dashboard/DashboardService.asmx/GetDashboard';
    if (window.location.toString().indexOf('/Dashboard/') > -1) {
        urlLink = '../' + urlLink;
    }
    $.ajax({
        type: "POST",
        url: urlLink,
        data: _filterStringData,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            var dashboard = response.d;
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






        },
        failure: function (msg) {
            alert(msg);
        }

    });
}

function _dbOverview(dashboard) {

    var distArray = [['Toothpaste', 0], ['Toothbrush', 0], ['Mouthwash', 0]];
    var sosArray = [['Toothpaste', 0], ['Toothbrush', 0], ['Mouthwash', 0]];
    var dmArray = [['Toothpaste', 0], ['Toothbrush', 0], ['Mouthwash', 0]];
    var index = 0;
    for (var i = 0; i < dashboard.Distribution.Data.length; i++) {
        var dist = ['' + dashboard.Distribution.Data[i].Label, dashboard.Distribution.Data[i].Value];
        if (dashboard.Distribution.Data[i].Label == 'Toothpaste') {
            index = 0;
        }
        else if (dashboard.Distribution.Data[i].Label == 'Toothbrush') {
            index = 1;
        }
        else if (dashboard.Distribution.Data[i].Label == 'Mouthwash') {
            index = 2;
        }
        distArray[index] = dist;
    }
    for (var i = 0; i < dashboard.ShareOfShelf.Data.length; i++) {
        var sos = ['' + dashboard.ShareOfShelf.Data[i].Label, dashboard.ShareOfShelf.Data[i].Value];
        if (dashboard.ShareOfShelf.Data[i].Label == 'Toothpaste') {
            index = 0;
        }
        else if (dashboard.ShareOfShelf.Data[i].Label == 'Toothbrush') {
            index = 1;
        }
        else if (dashboard.ShareOfShelf.Data[i].Label == 'Mouthwash') {
            index = 2;
        }
        sosArray[index] = sos;
    }
    for (var i = 0; i < dashboard.ShareOfDM.Data.length; i++) {
        var dm = ['' + dashboard.ShareOfDM.Data[i].Label, dashboard.ShareOfDM.Data[i].Value];
        if (dashboard.ShareOfDM.Data[i].Label == 'Toothpaste') {
            index = 0;
        }
        else if (dashboard.ShareOfDM.Data[i].Label == 'Toothbrush') {
            index = 1;
        }
        else if (dashboard.ShareOfDM.Data[i].Label == 'Mouthwash') {
            index = 2;
        }
        dmArray[index] = dm;
    }

    if (dashboard.Distribution.Data.length > 0) {
        drawBarChart('distributionBox', distArray);
    }
    else {
        $('#distributionBox').empty();
        jQuery('<div/>', {
            id:'distributionBoxLbl',
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666',
                'padding' : '0px 0px 0px 20px',
                'margin-top':'10px'
                
            }
        }).appendTo('#distributionBox');
        $('#distributionBoxLbl').html('No Results Found');
    }
    if (dashboard.ShareOfShelf.Data.length > 0) {
        drawBarChart('shareOfShelfBox', sosArray);
    }
    else {
        $('#shareOfShelfBox').empty();
        jQuery('<div/>', {
            id: 'shareOfShelfBoxLbl',
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666',
                'padding': '0px 0px 0px 20px',
                'margin-top': '10px'

            }
        }).appendTo('#shareOfShelfBox');
        $('#shareOfShelfBoxLbl').html('No Results Found');
    }
    if (dashboard.ShareOfDM.Data.length > 0) {
        drawBarChart('shareOfDMBox', dmArray);
    }
    else {
        $('#shareOfDMBox').empty();
        jQuery('<div/>', {
            id: 'shareOfDMBoxLbl',
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666',
                'padding': '0px 0px 0px 20px',
                'margin-top': '10px'

            }
        }).appendTo('#shareOfDMBox');
        $('#shareOfDMBoxLbl').html('No Results Found');
    }

    if (dashboard.Promoter.Data.length > 0) {
        drawHBarChart('promoterBox', dashboard.Promoter.Data);
    }
    else {
        $('#promoterBox').empty();
        jQuery('<div/>', {
            id: 'promoterBoxLbl',
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666',
                'padding': '0px 0px 0px 0px',
                'margin-top': '10px'

            }
        }).appendTo('#promoterBox');
        $('#promoterBoxLbl').html('No Results Found');
    }
    if (dashboard.PlanOGram1.Data.length > 0) {
        drawBubbleChart('plano1Box', dashboard.PlanOGram1.Data);
    }
    else {
        $('#plano1Box').empty();
        jQuery('<div/>', {
            id: 'plano1BoxLbl',
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666',
                'padding': '0px 0px 0px 0px',
                'margin-top': '10px'

            }
        }).appendTo('#plano1Box');
        $('#plano1BoxLbl').html('No Results Found');
    }
    if (dashboard.PlanOGram2.Data.length > 0) {
        drawBubbleChart('plano2Box', dashboard.PlanOGram2.Data);
    }
    else {
        $('#plano2Box').empty();
        jQuery('<div/>', {
            id: 'plano2BoxLbl',
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666',
                'padding': '0px 0px 0px 00px',
                'margin-top': '10px'

            }
        }).appendTo('#plano2Box');
        $('#plano2BoxLbl').html('No Results Found');
    }
    if (dashboard.PromotionUnplanned.Data != null && dashboard.PromotionUnplanned.Data.length > 0) {
        $('#promotionBoxValue1Lbl1').hide();
        drawNumLabelChart('promotionBoxValue1', dashboard.PromotionUnplanned.Data[0].Value);
        $('#promotionBoxLabel1').html(dashboard.PromotionUnplanned.Data[0].Label);
        drawGaugeChart('promotionBox2Value', dashboard.PromotionUnplanned.Data[1].Value);
        $('#promotionBox2Label').html(dashboard.PromotionUnplanned.Data[1].Label);
    }
    
    if (dashboard.PromotionPlanned.Data != null) {
        drawGaugeChart('promotionBox3Value', dashboard.PromotionPlanned.Data.Value);
        $('#promotionBox3Label').html(dashboard.PromotionPlanned.Data.Label);
    }
    if (dashboard.PromotionUnplanned.Data == null && dashboard.PromotionPlanned.Data == null) {
        $('#promotionBoxValue1Lbl1').show();
        $('#promotionBoxValue1Lbl1').html('No Results Found');
        $('#promotionBoxValue1').empty();
        $('#promotionBoxLabel1').empty();
        $('#promotionBox2Value').empty();
        $('#promotionBox2Label').empty();
        $('#promotionBox3Value').empty();
        $('#promotionBox3Label').empty();
        
    }
}

////////////////////////rendeer dashboards//////////////////////////////////

function _dbDistribution(dashboard) {

    if (dashboard.SKUDistribution.Data != null && dashboard.SKUDistribution.Data.length > 0) {

        var db = new CategorizedHBarChart(580, dbContentsId.substring(1, dbContentsId.length));
        db.init();
        db.draw(dashboard.SKUDistribution);

    }
    else {
        jQuery('<span/>', {
            id: 'lblNofound',

            css: { 'font-weight': 'bold', 'padding-left': '10px' }

        }).appendTo(dbContentsId);
        $('#lblNofound').html('No Result for Selected Criteria');
    }

}
function _dbPriceVsRSP(dashboard) {

    if (dashboard.SKUPrice.Data != null && dashboard.SKUPrice.Data.length > 0) {

        var db = new CategorizedHBarChart(580, dbContentsId.substring(1, dbContentsId.length));
        db.init();
        db.draw(dashboard.SKUPrice);

    }
    else {
        jQuery('<span/>', {
            id: 'lblNofound',

            css: { 'font-weight': 'bold', 'padding-left': '10px' }

        }).appendTo(dbContentsId);
        $('#lblNofound').html('No Result for Selected Criteria');
    }
}
function _dbShareOfShelf(dashboard) {
    if (dashboard.BrandSOS.Data != null && dashboard.BrandSOS.Data.length > 0) {

        var db = new CategorizedHBarChart(580, dbContentsId.substring(1, dbContentsId.length));
        db.init();
        db.draw(dashboard.BrandSOS);

    }
    else {
        jQuery('<span/>', {
            id: 'lblNofound',

            css: { 'font-weight': 'bold', 'padding-left': '10px' }

        }).appendTo(dbContentsId);
        $('#lblNofound').html('No Result for Selected Criteria');
    }
}
function _dbDM(dashboard) {
    if (dashboard.BrandDM.Data != null && dashboard.BrandDM.Data.length > 0) {

        var db = new CategorizedHBarChart(580, dbContentsId.substring(1, dbContentsId.length));
        db.init();
        db.draw(dashboard.BrandDM);

    }
    else {
        jQuery('<span/>', {
            id: 'lblNofound',

            css: { 'font-weight': 'bold', 'padding-left': '10px' }

        }).appendTo(dbContentsId);
        $('#lblNofound').html('No Result for Selected Criteria');
    }
}
function _dbSKUOnShelf(dashboard) {
    if (dashboard.BrandSKUOS.Data != null && dashboard.BrandSKUOS.Data.length > 0) {

        var db = new CategorizedHBarChart(580, dbContentsId.substring(1, dbContentsId.length));
        db.init();
        db.draw(dashboard.BrandSKUOS);

    }
    else {
        jQuery('<span/>', {
            id: 'lblNofound',

            css: { 'font-weight': 'bold', 'padding-left': '10px' }

        }).appendTo(dbContentsId);
        $('#lblNofound').html('No Result for Selected Criteria');
    }
}
function _dbSecondaryDisplay(dashboard) {
    if (dashboard.BrandShareByQty.Data != null && dashboard.BrandShareByQty.Data.length > 0) {

        var db = new CategorizedHBarChart(580, dbContentsId.substring(1, dbContentsId.length));
        db.init();
        db.draw(dashboard.BrandShareByQty);

    }
    else {
        jQuery('<span/>', {
            id: 'lblNofound',

            css: { 'font-weight': 'bold', 'padding-left': '10px' }

        }).appendTo(dbContentsId);
        $('#lblNofound').html('No Result for Selected Criteria');
    }
}


///////OVERVIEW CHARTS////////////////////////
function drawBarChart(boxID, data) {
    $('#' + boxID).empty();

    var plot3 = $.jqplot(boxID, [data], {
        // Tell the plot to stack the bars.

        captureRightClick: true,
        animate: !$.jqplot.use_excanvas,
        seriesDefaults: {
            renderer: $.jqplot.BarRenderer,
            rendererOptions: {
                // Put a 30 pixel margin between bars.
                barMargin: 10,
                // Highlight bars when mouse button pressed.
                // Disables default highlighting on mouse over.
                highlightMouseOver: true,
                shadow: false,
                varyBarColor: true,
                barWidth: 40,
                animation: {
                    speed: 1700
                }
            },
            pointLabels: { show: true }
        },
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer, showTickMarks: false,
                /*ticks: ['Colgate', 'Zhong Hua', 'Darlie', 'Crest'],*/
                tickOptions: {
                    fontSize: '10px', fontFamily: 'Arial'
                }
            },
            yaxis: {
                // Don't pad out the bottom of the data range.  By default,
                // axes scaled as if data extended 10% above and below the
                // actual range to prevent data points right on grid boundaries.
                // Don't want to do that here.
                padMin: 0, max: 120, min: 0, showTicks: false, show: false,
                tickOptions: {
                    fontSize: '12px', fontFamily: 'Arial', formatString: '%i%%'
                }
            }
        },
        legend: {
            show: false
        },
        grid: {
            shadow: false, drawBorder: false, drawGridlines: false
        },
        highlighter: {
            show: true,
            sizeAdjust: 7.5,
            showMarker: false,
            showTooltip: false,
            tooltipAxes: 'y',
            tooltipLocation: 'n',
            tooltipFadeSpeed: 800
        },
        seriesColors: colorArray

    });
    


}

function showPromoterGraphValLabel(idx) {

    $('#promoterGraphVal' + idx).delay(500).fadeIn(1000);

}

function drawHBarChart(boxID, data) {
    $('#' + boxID).empty();
    var max = 100;
    var maxWidth = 190;

    for (var i = 0; i < data.length; i++) {

        jQuery('<div/>', {
            id: 'promoter' + i,
            css: {
                'width': '100%'
            }
        }).appendTo('#' + boxID);

        jQuery('<span/>', {
            id: 'promoterLabel' + i,
            css: {
                'font-size': '12px',
                'font-family': 'Helvetica, Arial, sans-serif',
                'color': '#666666'
            }
        }).appendTo('#promoter' + i);

        $('#promoterLabel' + i).html(data[i].Label);

        jQuery('<div/>', {
            id: 'promoterChart' + i,
            css: {
                'width': '80%',
                'margin': '5px 0px 10px 0px'
            }
        }).appendTo('#' + boxID);

        jQuery('<div id="promoterGraph' + i + '" style="float:left;width:0px;" class="hbarchart"/>', {
            id: 'promoterGraph' + i,
            css: {

        }
    }).appendTo('#promoterChart' + i);



    jQuery('<span/>', {
        id: 'promoterGraphVal' + i,
        css: {
            'float': 'left',
            'font-size': '12px',
            'font-weight': 'bold',
            'font-family': 'Rockwell, Arial, Sans-Serif',
            'color': '#c00800',
            'padding-left': '5px'
        }
    }).appendTo('#promoterChart' + i);

    $('#promoterGraphVal' + i).html(data[i].Value + '%');
    $('#promoterGraphVal' + i).hide();
    var lab = $('#promoterGraphVal' + i);

    jQuery('<div style="clear:left;"/>', {

}).appendTo('#promoterChart' + i);

if (data[i].Value > 0) {

    //$('#promoterGraph' + i).width((data[i].Value / max) * (maxWidth));
    var widthVal = (data[i].Value / max) * (maxWidth);
    $('div#promoterGraph' + i).animate({ width: widthVal }, 1000, '', showPromoterGraphValLabel(i));
}
else {
    $('#promoterGraphVal' + i).show();
}


}


}

function drawBubbleChart(boxID, data) {
    $('#' + boxID).empty();
    var max = 100;
    var maxRad = 30;
    var minRad = 15;
    var paper = new Raphael(document.getElementById(boxID), $('#' + boxID).width(), $('#' + boxID).height());
    var startY = maxRad;

    var maxFont = 18;
    var minFont = 12;


    var val = maxRad - (((max - (data[0].Value==null?0:data[0].Value)) / max) * (maxRad - minRad));
    var c = paper.circle(maxRad, ($('#' + boxID).height() / 4), 0);
    c.attr({ 'fill': colorObject[data[0].Label], 'stroke-width': 0 });


    var val2 = maxRad - (((max - (data[2].Value==null?0:data[2].Value)) / max) * (maxRad - minRad));
    var c2 = paper.circle(maxRad, ($('#' + boxID).height() / 4) * 3, 0);
    c2.attr({ 'fill': colorObject[data[2].Label], 'stroke-width': 0 });


    var val1 = maxRad - (((max - (data[1].Value==null?0:data[1].Value)) / max) * (maxRad - minRad));
    var c1 = paper.circle(maxRad, ($('#' + boxID).height() / 2), 0);
    c1.attr({ 'fill': colorObject[data[1].Label], 'stroke-width': 0 });



    var valFont = maxFont - (((max - data[0].Value) / max) * (maxFont - minFont));
    var value = paper.text(maxRad, ($('#' + boxID).height() / 4) - (val / 2), "\n" + (data[0].Value==null?0:data[0].Value) + "%");
    value.attr({ 'fill': '#ffffff', 'font-family': 'Arial', 'font-size': valFont, 'fill-opacity': 0 });

    var valFont2 = maxFont - (((max - data[2].Value) / max) * (maxFont - minFont));
    var value2 = paper.text(maxRad, (($('#' + boxID).height() / 4) * 3) - (val2 / 2), "\n" + (data[2].Value==null?0:data[2].Value) + "%");
    value2.attr({ 'fill': '#ffffff', 'font-family': 'Arial', 'font-size': valFont2, 'fill-opacity': 0 });

    var valFont1 = maxFont - (((max - data[1].Value) / max) * (maxFont - minFont));
    var value1 = paper.text(maxRad, ($('#' + boxID).height() / 2) - (val1 / 2), "\n" + (data[1].Value==null?0:data[1].Value) + "%");
    value1.attr({ 'fill': '#ffffff', 'font-family': 'Arial', 'font-size': valFont1, 'fill-opacity': 0 });


    var label = paper.text(maxRad * 3, ($('#' + boxID).height() / 4) - (val / 2), "\n" + data[0].Label);
    label.attr({ 'fill': colorObject[data[0].Label], 'font-family': 'Arial', 'font-size': 12, 'text-anchor': 'start' });

    var label2 = paper.text(maxRad * 3, (($('#' + boxID).height() / 4) * 3) - (val2 / 2), "\n" + data[2].Label);
    label2.attr({ 'fill': colorObject[data[2].Label], 'font-family': 'Arial', 'font-size': 12, 'text-anchor': 'start' });

    var label1 = paper.text(maxRad * 3, ($('#' + boxID).height() / 2) - (val1 / 2), "\n" + data[1].Label);
    label1.attr({ 'fill': colorObject[data[1].Label], 'font-family': 'Arial', 'font-size': 12, 'text-anchor': 'start' });

    c.getBBox();
    c.animate({ 'r': val }, 1000, '', animatePlanoValLabel(value));

    c2.getBBox();
    c2.animate({ 'r': val2 }, 1000, '', animatePlanoValLabel(value2));

    c1.getBBox();
    c1.animate({ 'r': val1 }, 1000, '', animatePlanoValLabel(value1));

}

function animatePlanoValLabel(aLabel) {
    aLabel.animate({ 'fill-opacity': 1 }, 1000);

}

function drawNumLabelChart(boxID, dataValue) {

    var $counter = $('#' + boxID);
    $counter.html('00');
    $counter.animateNumber(dataValue, '', '');

}

function drawGaugeChart(root,boxID, dataValue) {
    $('#' + boxID).empty();
    var paper = new Raphael(document.getElementById(boxID), $('#' + boxID).width(), $('#' + boxID).height());
    var image = paper.image(root+'/colgate2/Images/newGauge.png', 0, 0, 75, 50);
    var pointer = paper.image(root+'/colgate2/Images/gaugeNeedle.png', 12.5, 27, 25, 11);
    pointer.transform("r+45," + 25 + "," + 11);

    var rotation = ((dataValue / 100) * 180);

    pointer.animate({ 'transform': "r" + rotation + "," + 37.5 + "," + 33 }, 500);

    var value1 = paper.text(37.5, 37, "\n" + dataValue + "%");
    value1.attr({ 'fill': '#df6a00', 'font-family': 'Arial', 'font-size': 10, 'font-weight': 'bold' });

}