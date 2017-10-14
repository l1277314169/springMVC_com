function __enableAjaxProgressBar() {
    __showAjaxLoading = true;
    setTimeout(function () { __showAjaxLoading = false; }, 200);
}
function __InitUpdateProgressPanel() {
    var __manager = Sys.WebForms.PageRequestManager.getInstance();
    if (__manager) {
        __manager.add_endRequest(__OnEndRequest);
        __manager.add_beginRequest(__OnBeginRequest);
    }
}
function __OnBeginRequest(sender, args) {
    var postBackElement = $(args.get_postBackElement());
    var pgs = postBackElement.attr("UpdateProgressID");
    var controlsToDisable = postBackElement.attr("ControlsToDisable");

    if (pgs && pgs != '') {
        var progressBars = pgs.split(',');
        $(progressBars).each(function () {
            $("#" + this).css("display", "");
        });
    }

    if (controlsToDisable && controlsToDisable != '') {
        var cs = controlsToDisable.split(',');
        $(cs).each(function () {
            var ctl = $("#" + this);
            ctl.attr("disabled", true);
            ctl.addClass("readonly")
        });
    }
}
function __OnEndRequest(sender, args) {
}
function __ButtonClick(bid) {
    if (typeof (Page_ClientValidate) == 'function') {
        validationResult = Page_ClientValidate('FixPreviousValidationErrorBug');
    }
    var prm = Sys.WebForms.PageRequestManager.getInstance();
    prm._doPostBack(bid, '');
}

function ToggleSection(cid, ctl) {
    var c = $(ctl);
    var st = c.attr("st");
    if (st == "O") {
        $('.sub_' + cid, $("#" + c.attr("pid"))).show();
        c.attr("st", "C");
    }
    else {
        $('.sub_' + cid, $("#" + c.attr("pid"))).hide();
        c.attr("st", "O");
    }
}

function __rbDDLSelectedItem(hmeID, selectedValue, selectedText, hfSelectedValueClientID, hfSelectedTextClientID, lblSelectedClientID, autoPostBack) {

    $('#' + hfSelectedValueClientID).val(selectedValue);
    $('#' + lblSelectedClientID).html(selectedText);
    $('#' + hfSelectedTextClientID).val(selectedText);
    var hovermenu = $find(hmeID);
    hovermenu._hoverBehavior._hoverElement.style.visibility = "hidden";

    if (autoPostBack == 1) {
        return true;
    }
    else {
        return false;
    }

}

function __rbCBLSelectedItem(cbListId, hfSelectedValueClientID, hfSelectedTextClientID, lblSelectedClientID,hfTotalNoOfItemsId,allItemsSelectedText,defaultText) {

    
    var val = $('#' + cbListId + ' input[type=checkbox]:checked');
    var valCount = val.length;
    var totalNoOfItem = parseInt($('#' + hfTotalNoOfItemsId).val());
    if (valCount == totalNoOfItem) {

        $('#' + lblSelectedClientID).html(allItemsSelectedText);
    }
    else if (valCount == 0) {
        $('#' + lblSelectedClientID).html(defaultText);
    }
    else {
        var valLabel = $('#' + cbListId + ' input[type=checkbox]:checked + label');
        var str = '';
        for (var i = 0; i < valLabel.length; i++) {
            if (str.length > 0) {
                str = str + ', ';
            }
            str = str + $(valLabel[i]).text();
        }
        $('#' + lblSelectedClientID).html(str);
    }




}

function __rbGetDDLSelectedValue(rbControlId) {

    return $('#' + rbControlId + '_hfSelectedValue').val();

}