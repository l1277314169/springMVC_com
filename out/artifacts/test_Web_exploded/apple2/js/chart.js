//////HBarChart////////////////////////
function HBarChart(width, containerId) {
    var paper;
    this._width = width-10;
    this._containerId = containerId;

    this.init = function () {

        paper = new Raphael(document.getElementById(this._containerId), this._width, 150);

    }
    this.draw = function (chart) {
        var layout = new StackLayout(0, 0, this._width - 20, 0, "VERTICAL", "LEFT", "TOP", 5);
        paper.setSize(this._width, chart.Data.length * 27);
        if (isIE8 == 1) {
            chart.LabelFontSize = 13;
        }
        for (var i = 0; i < chart.Data.length; i++) {

            var hlayout = new StackLayout(0, 0, this._width - 20, 23, "HORIZONTAL", "LEFT", "MIDDLE", 10);
            var labellayout = new StackLayout(0, 0, chart.LabelRatio * (this._width - 20), 23, "HORIZONTAL", "LEFT", "MIDDLE", 10);
            var valuelayout = new StackLayout(0, 0, chart.ValueRatio * (this._width - 20), 23, "HORIZONTAL", "LEFT", "MIDDLE", 10);

            var label = paper.text(0, 0, chart.Data[i].Label + " ");
            label.attr({ 'fill': chart.LabelColor, 'font-family': 'Arial', 'font-size': chart.LabelFontSize, 'text-anchor': 'start', 'height': 14 });

            var words = (chart.Data[i].Label + " ").split(" ");
            var maxWidth = chart.LabelRatio * (this._width - 20);
            var tempText = "";
            for (var j = 0; j < words.length; j++) {
                label.attr("text", tempText + " " + words[j]);
                if (label.getBBox().width > maxWidth) {
                    tempText += "\n" + words[j];
                } else {
                    tempText += " " + words[j];
                }
            }
            label.attr("text", "\n" + tempText.substring(1));


            labellayout.addElement(label);
            hlayout.addElement(labellayout);
            if (isIE8 == 1) {
                label.attr("y", label.attr("y") + 3);
            }
            var value = paper.text(0, 0, "\n" + chart.Data[i].Prefix + chart.Data[i].Value.toString() + chart.Data[i].Postfix);
            value.attr({ 'fill': chart.LabelColor, 'font-family': 'Arial', 'font-size': chart.LabelFontSize, 'text-anchor': 'start', 'height': 14 });

            valuelayout.addElement(value);

            valuelayout = hlayout.addElement(valuelayout);
            if (isIE8 == 1) {
                value.attr("y", value.attr("y") + 3);
            }
            valuelayout.elements[0].getBBox().x;

            var bar = paper.rect(0, 0, 0, 10, 2);
            bar.attr({ 'fill': chart.Data[i].Color, 'stroke-width': 0 });
            bar = hlayout.addElement(bar);

            if (isIE8 == 1) {
                bar.attr("y", bar.attr("y") - 7);
               
            }
            else if (isIE9 == 1) {
                bar.attr("y", bar.attr("y") +3);
               
            }


            bar.getBBox();
            if (chart.Data[i].Value > 0) {
                bar.animate(
		                { 'width': (chart.Data[i].Value / chart.MaxValue) * (chart.BarRatio * (this._width - 20)) },
		                1000,
		                'easeIn',
			                function () {
			                    this.getBBox();
			                    this.glow(
				                {
				                    'width': 1,
				                    'fill': true,
				                    'opacity': 0.2,
				                    'offsetx': 1,
				                    'offsety': 1
				                }
				                );
			                }
		                );
            }
            layout.addElement(hlayout);
        }

    }
}
//////////////////////////////////////////////////
///////////////GaugeChart/////////////////////////
function GaugeChart(width, containerId) {
    var paper;
    this._width = width - 10;
    this._containerId = containerId;

    this.init = function () {

        paper = new Raphael(document.getElementById(this._containerId), this._width, 160);


    }
    this.draw = function (chart) {


        var valueLayout = new StackLayout(5, 110, 150, 23, "HORIZONTAL", "CENTER", "MIDDLE", 10);
        var labellayout = new StackLayout(170, 60, 0.35 * (this._width - 10), 23, "HORIZONTAL", "LEFT", "MIDDLE", 10);



        var image = paper.image('Guage.png', 0, 0, 150, 150)


        var label = paper.text(0, 0, "\n" + chart.Data.Label);
        label.attr({ 'fill': chart.LabelColor, 'font-size': chart.LabelFontSize, 'text-anchor': 'start' });


        var words = chart.Data.Label.split(" ");
        var maxWidth = 0.35 * (this._width - 10);
        var tempText = "";
        for (var i = 0; i < words.length; i++) {
            label.attr("text", tempText + " " + words[i]);
            if (label.getBBox().width > maxWidth) {
                tempText += "\n" + words[i];
            } else {
                tempText += " " + words[i];
            }
        }

        label.attr("text", tempText.substring(1));
        labellayout.addElement(label);


        var value = paper.text(0, 0, chart.Data.Prefix + chart.Data.Value.toString() + chart.Data.Postfix);
        value.attr({ 'fill': '#ffffff', 'font-size': 14, 'font-weight': 'bold', 'text-anchor': 'start' });
        valueLayout.addElement(value);
        var cir = paper.circle(75, 75, 6)
        cir.attr({ 'fill': '#ffffff', 'stroke-width': 0 });
        var bar = paper.rect(25, 73, 50, 4, 2);
        bar.attr({ 'fill': '#ffffff', 'stroke-width': 0 });

        var tx = 75;
        var ty = 75;
        if (isIE8 == 1) {
            tx = 73;
            ty = 73;
        }

        bar.transform("r-40," + tx + "," + ty );

        var range = 260;

        var rotation = ((chart.Data.Value / chart.Data.MaxValue) * 260) - 40;
        bar.animate(
		            { 'transform': "r" + rotation + "," + tx + "," + ty },
		            500,
		            'easeIn',
			            function () {

			            }
		            );

    }
}
//////////////////////////////////////////////////
//////////////NumberLabelChart/////////////////
(function ($) {
    $.fn.animateNumber = function (to, prefix, postfix) {
        var $ele = $(this),
            num = parseInt($ele.html()),
            up = to > num,
            num_interval = Math.abs(num - to) / 90;

        var loop = function () {
            num = up ? Math.ceil(num + num_interval) : Math.floor(num - num_interval);
            if ((up && num > to) || (!up && num < to)) {
                num = to;
                clearInterval(animation)
            }
            $ele.html(prefix + num + postfix);
        }

        var animation = setInterval(loop, 30);
    }
})(jQuery)


function NumberLabelChart(width,containerId) {

    var paper;
    this._width = width - 10;
    this._containerId = containerId;

    this.init = function () {
        
        $('#' + this._containerId).empty

    }
    this.draw = function (chart) {
        var layout = new StackLayout(10, 30, this._width, 0, "VERTICAL", "LEFT", "TOP", 35);
        
        for (var i = 0; i < chart.Data.length; i++) {
           
            var html = "<span id='numLabelVal" + i + "' style='color:" + chart.Data[i].Color + ";font-size:" + chart.ValueFontSize + "px;font-weight:bold;font-family:Helvetica,Arial;font-style:italic;'>0" + "</span></td>"+"<td>&nbsp;&nbsp;&nbsp;&nbsp;<span style='color:" + chart.LabelColor + ";font-size:" + chart.LabelFontSize + "px;margin-bottom:18px;'>" + chart.Data[i].Label + "</span><br/><br/>";
            
            
            $('#' + this._containerId).append(html);
            var span = $('#numLabelVal' + i);
            var dataValue = chart.Data[i].Value;
            var prefix = chart.Data[i].Prefix;
            var postfix = chart.Data[i].Postfix;
            var $counter = $('#numLabelVal' + i);
            $counter.animateNumber(dataValue,prefix,postfix);
           
        }
       
    }
}
//////////////////////////////////////////////////
//////////////CategorizedHBarChart/////////////////
function CategorizedHBarChart(width, containerId) {
    var labelColWidth;
    var totalExtraColWidth;
    var perExtraColWidth;
    var valueColWidth;
    var labelColor;
    var labelFontSize;
    var numOfExtraInfo;
    var maxValue;
    var haveSubCateWidth = 0;
    var rowWidth = 0;
    var animArray;
    var barArray;
    
    this._width = width - 10;
    this._containerId = containerId;

    this.init = function () {

        //paper = new Raphael(document.getElementById(this._containerId), this._width, "100%");
        $('<img/>').src = '/colgate2/Images/ItoggleUp.png';
        $('<img/>').src = '/colgate2/Images/toggleDown.png';



    }
    this.drawRow = function (divId, data, vLayout, paper) {

        var hLayout = new StackLayout(0, 0, rowWidth, 23, "HORIZONTAL", "LEFT", "MIDDLE", 0);
        var labelLayout = new StackLayout(0, 0, labelColWidth, 23, "HORIZONTAL", "LEFT", "BOTTOM", 0);
        var label = paper.text(0, 0, data.Label);
        label.attr({ 'fill': labelColor, 'font-size': labelFontSize, 'text-anchor': 'start' });
        labelLayout.addElement(label);
        hLayout.addElement(labelLayout);



        for (var i = 0; i < numOfExtraInfo; i++) {

            var infoLayout = new StackLayout(0, 0, perExtraColWidth, 23, "HORIZONTAL", "LEFT", "BOTTOM", 0);
            var info = paper.text(0, 0, data['ExtraInfo' + (i + 1)].toString());
            info.attr({ 'fill': labelColor, 'font-size': labelFontSize });
            infoLayout.addElement(info);
            hLayout.addElement(infoLayout);
            info.attr({ 'y': info.attr('y') + 2 });
        }
        var valueLayout = new StackLayout(0, 0, valueColWidth, 23, "HORIZONTAL", "LEFT", "MIDDLE", 5);
        var valueLabelLayout = new StackLayout(0, 0, 40, 23, "HORIZONTAL", "CENTER", "BOTTOM", 5);
        var value = paper.text(0, 0, data.Prefix + data.Value.toString() + data.Postfix);
        value.attr({ 'fill': labelColor, 'font-size': labelFontSize });
        valueLabelLayout.addElement(value);
        //valueLayout.addElement(valueLabelLayout);
        if (data.Value > 0) {
            var bar = paper.rect(0, 0, 0, 17, 2);
            bar.attr({ 'fill': data.Color, 'stroke-width': 0 });
            bar = valueLayout.addElement(bar);
            bar.getBBox();
        
            var anim = Raphael.animation({ 'width': (data.Value / maxValue) * (((valueColWidth - 40)) - 20) },
		                1000,
		                'easeIn',
			                function () {
			                    this.getBBox();
			                    this.glow(
				                {
				                    'width': 1,
				                    'fill': true,
				                    'opacity': 0.2,
				                    'offsetx': 1,
				                    'offsety': 1
				                }
				                );
			                });
            //bar.animate(anim);
            animArray.push(anim);
            barArray.push(bar);
        }
        hLayout.addElement(valueLabelLayout);
        hLayout.addElement(valueLayout);
        vLayout.addElement(hLayout);
        label.attr({ 'y': label.attr('y') + 2 });
        value.attr({ 'y': value.attr('y') + 2 });
    }
    this.draw = function (chart) {


        if (chart.IsSubCategorised == true) {
            haveSubCateWidth = 20;
        }
        rowWidth = this._width - haveSubCateWidth;
        numOfExtraInfo = chart.NumOfExtraInfo;
        labelColWidth = (0.45 * (rowWidth));
        totalExtraColWidth = 0.3 * (rowWidth);
        perExtraColWidth = (totalExtraColWidth / 3);
        valueColWidth = (0.25 * (rowWidth)) + ((3 - numOfExtraInfo) * perExtraColWidth);
        labelColor = chart.LabelColor;
        labelFontSize = chart.LabelFontSize;
        maxValue = chart.MaxValue;
        animArray = new Array();
        barArray = new Array();

        jQuery('<span/>', {
            id: 'spanLabelCol',
            css: {

                'color': "'" + chart.LabelColor + "'",
                'font-size': '12px',
                'font-weight': 'bold',
                'float': 'left'
            }
        }).appendTo('#' + this._containerId);

        if (numOfExtraInfo == 3) {
            $('#spanLabelCol').css('width', (labelColWidth + haveSubCateWidth - 30) + 'px');
        }
        else {
            $('#spanLabelCol').css('width', (labelColWidth + haveSubCateWidth - 10) + 'px');
        }



        $('#spanLabelCol').html(chart.LabelHeader);

        for (var j = 0; j < chart.NumOfExtraInfo; j++) {
            jQuery('<span/>', {
                id: 'spanExtraCol' + j,
                css: {

                    'color': "'" + chart.LabelColor + "'",
                    'font-size': '12px',
                    'font-weight': 'bold',
                    'float': 'left',
                    'text-align':'center'
                }
            }).appendTo('#' + this._containerId);
            $('#spanExtraCol' + j).css('width', perExtraColWidth + 'px');
            $('#spanExtraCol' + j).html(chart["ExtraInfo" + (j + 1) + "Header"].toString());
        }

        jQuery('<span/>', {
            id: 'spanValueCol',
            css: {

                'color': "'" + chart.LabelColor + "'",
                'font-size': '12px',
                'font-weight': 'bold',
                'float': 'left',
                'padding-left': '0px'
            }
        }).appendTo('#' + this._containerId);
        $('#spanValueCol').css('width', valueColWidth + 'px');
        $('#spanValueCol').html(chart.ValueHeader)

        jQuery('<div/>', {
            id: 'headerLine',
            css: {

                'border-bottom': 'solid 1px black',
                'width': '100%',
                'clear': 'both',
                'padding-top': '5px'
            }
        }).appendTo('#' + this._containerId);

        if (chart.IsSubCategorised == true) {
            this.drawWithSubCategory(chart);
        }
        else {
            this.drawWithoutSubCategory(chart);
        }

        for(var i=0;i<animArray.length;i++) {
            barArray[i].animate(animArray[i]);
        }
    }
    this.drawWithoutSubCategory = function (chart) {
        var currentVLayout;
        var currentPaper;
        var currentCat = '';
        var currentCatId = '';
        var itemCounter = 0;
        for (var i = 0; i < chart.Data.length; i++) {

            if (chart.Data[i].Category != currentCat) {

                if (i != 0) {

                    currentPaper.setSize(this._width, itemCounter * 28);
                }
                itemCounter = 0;
                currentCat = chart.Data[i].Category;
                currentCatId = i.toString();
                jQuery('<div/>', {
                    id: '' + currentCatId,
                    css: {

                        'width': '100%',
                        'padding-top': '10px'

                    }
                }).appendTo('#' + this._containerId);
                jQuery('<img/>', {
                    id: 'img' + currentCatId,
                    src: '/colgate2/Images/toggleDown.png',
                    css: {
                        'margin-right': '5px'
                    }
                }).appendTo('#' + currentCatId);
                jQuery('<span/>', {
                    id: 'lbl' + currentCatId,
                    css: {

                        'color': "'" + chart.LabelColor + "'",
                        'font-size': "'" + chart.LabelFontSize + "px'",
                        'font-weight': 'bold',
                        'cursor': 'pointer'

                    }
                }).appendTo('#' + currentCatId);
                $('#' + 'lbl' + currentCatId).html(currentCat)
                $('#' + 'lbl' + currentCatId).toggle(
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleUp.png');
                        },
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleDown.png');
                        });
                jQuery('<div/>', {
                    id: 'div' + currentCatId,
                    css: {

                        'padding-top': '5px'

                    }
                }).appendTo('#' + currentCatId);
                
                currentPaper = new Raphael(document.getElementById('div' + currentCatId), rowWidth, "100%");
                currentVLayout = new StackLayout(0, 0, this._width, 0, "VERTICAL", "LEFT", "TOP", 5);
                itemCounter++;
                this.drawRow('div' + currentCatId, chart.Data[i], currentVLayout, currentPaper);
            }
            else {

                itemCounter++;
                this.drawRow('div' + currentCatId, chart.Data[i], currentVLayout, currentPaper);
            }

        }
        currentPaper.setSize(this._width, itemCounter * 28);
    }
    this.drawWithSubCategory = function (chart) {


        var currentVLayout;
        var currentPaper;



        var currentCat = '';
        var currentCatId = '';
        var currentSubCat = '';
        var currentSubCatId = '';
        var itemCounter = 0;
        for (var i = 0; i < chart.Data.length; i++) {

            if (chart.Data[i].Category != currentCat) {
                if (i != 0) {

                    currentPaper.setSize(this._width - 20, itemCounter * 28);
                }
                itemCounter = 0;
                currentCat = chart.Data[i].Category;
                currentCatId = i.toString();
                jQuery('<div/>', {
                    id: '' + currentCatId,
                    css: {

                        'width': '100%',
                        'padding-top': '10px'

                    }
                }).appendTo('#' + this._containerId);
                jQuery('<img/>', {
                    id: 'img' + currentCatId,
                    src: '/colgate2/Images/toggleDown.png',
                    css: {
                        'margin-right': '5px'
                    }
                }).appendTo('#' + currentCatId);
                jQuery('<span/>', {
                    id: 'lbl' + currentCatId,
                    css: {

                        'color': "'" + chart.LabelColor + "'",
                        'font-size': "'" + chart.LabelFontSize + "px'",
                        'font-weight': 'bold',
                        'cursor': 'pointer'

                    }
                }).appendTo('#' + currentCatId);
                $('#' + 'lbl' + currentCatId).html(currentCat)
                $('#' + 'lbl' + currentCatId).toggle(
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleUp.png');
                        },
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleDown.png');
                        });
                jQuery('<div/>', {
                    id: 'div' + currentCatId,
                    css: {

                        'padding-left': '20px'

                    }
                }).appendTo('#' + currentCatId);

                currentSubCat = chart.Data[i].SubCategory;
                currentSubCatId = i.toString();
                jQuery('<div/>', {
                    id: currentCatId + currentSubCatId,
                    css: {

                        'padding-left': '0px',
                        'padding-top': '5px'

                    }
                }).appendTo('#' + 'div' + currentCatId);
                jQuery('<img/>', {
                    id: 'img' + currentCatId + currentSubCatId,
                    src: '/colgate2/Images/toggleDown.png',
                    css: {
                        'margin-right': '5px'
                    }
                }).appendTo('#' + currentCatId + currentSubCatId);
                jQuery('<span/>', {
                    id: 'lbl' + currentCatId + currentSubCatId,
                    css: {

                        'color': "'" + chart.LabelColor + "'",
                        'font-size': "'" + chart.LabelFontSize + "px'",
                        'font-weight': 'bold',
                        'cursor': 'pointer'

                    }
                }).appendTo('#' + currentCatId + currentSubCatId);
                $('#' + 'lbl' + currentCatId + currentSubCatId).html(currentSubCat)
                $('#' + 'lbl' + currentCatId + currentSubCatId).toggle(
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleUp.png');
                        },
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleDown.png');
                        });
                jQuery('<div/>', {
                    id: 'div' + currentCatId + currentSubCatId,
                    css: {
                        'padding-top': '5px'

                    }
                }).appendTo('#' + currentCatId + currentSubCatId);

                currentPaper = new Raphael(document.getElementById('div' + currentCatId + currentSubCatId), rowWidth, "100%");
                currentVLayout = new StackLayout(0, 0, this._width - 20, 0, "VERTICAL", "LEFT", "TOP", 5);
                itemCounter++;
                this.drawRow('div' + currentCatId + currentSubCatId, chart.Data[i], currentVLayout, currentPaper);
            }
            else {

                if (currentSubCat != chart.Data[i].SubCategory) {
                    currentPaper.setSize(this._width - 20, itemCounter * 28);
                    itemCounter = 0;
                    currentSubCat = chart.Data[i].SubCategory;
                    currentSubCatId = i.toString();
                    jQuery('<div/>', {
                        id: currentCatId + currentSubCatId,
                        css: {

                            'padding-left': '0px',
                            'padding-top': '5px'

                        }
                    }).appendTo('#' + 'div' + currentCatId);
                    jQuery('<img/>', {
                        id: 'img' + currentCatId + currentSubCatId,
                        src: '/colgate2/Images/toggleDown.png',
                        css: {
                            'margin-right': '5px'
                        }
                    }).appendTo('#' + currentCatId + currentSubCatId);
                    jQuery('<span/>', {
                        id: 'lbl' + currentCatId + currentSubCatId,
                        css: {

                            'color': "'" + chart.LabelColor + "'",
                            'font-size': "'" + chart.LabelFontSize + "px'",
                            'font-weight': 'bold',
                            'cursor': 'pointer'

                        }
                    }).appendTo('#' + currentCatId + currentSubCatId);

                    $('#' + 'lbl' + currentCatId + currentSubCatId).html(currentSubCat)
                    $('#' + 'lbl' + currentCatId + currentSubCatId).toggle(
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleUp.png');
                        },
                        function () {
                            var lblId = $(this).attr('id');
                            var divId = 'div' + lblId.substring(3, lblId.length);
                            var imgId = 'img' + lblId.substring(3, lblId.length);
                            $('#' + divId).slideToggle('slow');
                            $('#' + imgId).attr('src', '/colgate2/Images/toggleDown.png');
                        });
                    jQuery('<div/>', {
                        id: 'div' + currentCatId + currentSubCatId,
                        css: {
                            'padding-top': '5px'

                        }
                    }).appendTo('#' + currentCatId + currentSubCatId);

                    currentPaper = new Raphael(document.getElementById('div' + currentCatId + currentSubCatId), rowWidth, "100%");
                    currentVLayout = new StackLayout(0, 0, this._width - 20, 0, "VERTICAL", "LEFT", "TOP", 5);
                    itemCounter++;
                    this.drawRow('div' + currentCatId + currentSubCatId, chart.Data[i], currentVLayout, currentPaper);
                }
                else {
                    itemCounter++;
                    this.drawRow('div' + currentCatId + currentSubCatId, chart.Data[i], currentVLayout, currentPaper);
                }

            }


        }
        if (currentPaper != null) {
            currentPaper.setSize(this._width - 20, itemCounter * 28);
        }
    }
}