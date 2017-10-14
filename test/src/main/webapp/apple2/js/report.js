var filters;
var selectedReportId = 0;
var selectedReportName = '';



function _rpScrollTo(selector, callback) {

    $('div.mask').scrollTo(selector, 700, callback);

}

function _rpSetReportDesc(selectedReportId) {

    if (selectedReportId == 4011) {
        $('#lblReportDesc').html('(Note: Data on this report is not real time)');
    }
    else {
        $('#lblReportDesc').html('');
    }
}

function _rpSelectReport(reportId, reportName) {

    selectedReportId = reportId;
    selectedReportName = reportName;
    _rpSetSelectedReport(reportId, reportName);
    if (reportId == 4014) {
        $('#Grouping').hide();
        $('#GroupingTitle').hide();
        $('#btnRefresh').hide();
    }
    else if (reportId == 4017) {
        $('#btnRefresh').hide();
    }
    else {
        $('#Grouping').show();
        $('#GroupingTitle').show();
        $('#btnRefresh').show();
    }
    $('#lblReportFilterTitle').html(selectedReportName);

    $('#filterContent').hide();
    $('#filterLoading').show();
    $('#btnMenuBackToReport').hide();
    $('#btnFilterBackToReport').hide();
    _rpScrollTo('div.reportFilter', function () { _rpGetFilter(); $("html, body").animate({ scrollTop: 0 }, "slow"); });
}

function _rpSetSelectedReport(reportId, reportName) {
    $('#hfReportId').val("" + reportId);
    $('#hfReportName').val("" + reportName);
}
function _rpUpdateMandatoryLabels() {

    $.each(filters, function (index, filter) {

        if (filter.IsMandatory) {
            $('#lblMand_' + filter.FilterName).html('*');
        }
        else {
            $('#lblMand_' + filter.FilterName).html('');
        }

    });
}

function _rpSetRBBrandOptions(reportId) {
    document.getElementById('rbBrand_0').disabled = false;
    document.getElementById('rbBrand_1').disabled = false;
    document.getElementById('rbBrand_2').disabled = false;

    if (reportId == 4003) {
        document.getElementById('rbBrand_2').checked = false;
        document.getElementById('rbBrand_2').disabled = true;
        document.getElementById('rbBrand_0').checked = false;
        document.getElementById('rbBrand_0').disabled = true;
    }
    
    else if (reportId == 4005) {
        document.getElementById('rbBrand_1').checked = false;
        document.getElementById('rbBrand_1').disabled = true;
    }
}

function _rpValidate(isDownload) {
    var result = true;
    $('.filterError').html('');

    if (
                ($('#ddlLvl1Grouping').val() == $('#ddlLvl2Grouping').val() && $('#ddlLvl2Grouping').val() != '')
                ||
                ($('#ddlLvl1Grouping').val() == $('#ddlLvl3Grouping').val() && $('#ddlLvl3Grouping').val() != '')
                ||
                ($('#ddlLvl2Grouping').val() == $('#ddlLvl3Grouping').val() && $('#ddlLvl2Grouping').val() != '' && $('#ddlLvl3Grouping').val() != '')
                ) {
        $('#lblErr_Grouping').html('The selected groupings cannot be identical');
        result = false;
    }



    $.each(filters, function (index, filter) {

        if (filter.IsMandatory) {

            var filterName = filter.FilterName + "";
            if (filterName.indexOf("ddl") == 0) {

                if ($('#' + filterName).val() == '') {
                    $('#lblErr_' + filterName).html('Required');
                    result = false;

                }
            }
            else if (filterName.indexOf("rb") == 0) {

                var val = $('input[name=' + filterName + ']:checked').val();
                if (typeof val === "undefined") {
                    $('#lblErr_' + filterName).html('Required');
                    result = false;

                }

            }
            else if (filterName.indexOf("cb") == 0) {

                var val = $('#' + filterName + ' input[type=checkbox]:checked').length;
                if (val < 1) {
                    $('#lblErr_' + filterName).html('Required');
                    result = false;

                }
            }
            else if (filterName.indexOf("tb") == 0) {

                if ($('#' + filterName).val() == '') {
                    $('#lblErr_' + filterName).html('Required');
                    result = false;

                }
            }
        }

    });
    if (result && (isDownload==null||isDownload == 0)) {
        $('#reportContent').hide();
        $('#reportLoading').show();
        $('#btnLoadReport').click();
        _rpScrollTo('div.report', function () {
            $('#filterContent').hide();

        }
                );
    }
    return result;
}

function _rpGetFilter() {
    _rpHideAllFilters();
    $('.filterError').html('');
    $.ajax({
        type: "POST",
        url: "ReportService.asmx/GetFilter",
        data: "{reportId:" + selectedReportId + "}",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            filters = response.d;

            $.each(filters, function (index, filter) {

                $('#' + filter.FilterGroup).show();
                $('#tr_' + filter.FilterName).show();
                if (filter.IsMandatory) {
                    $('#lblMand_' + filter.FilterName).html('*');
                }
                else {
                    $('#lblMand_' + filter.FilterName).html('');
                }

                if (filter.FilterName == 'rbBrand') {
                    _rpSetRBBrandOptions(selectedReportId);
                }

            });

            $('#filterContent').show();
            $('#filterLoading').hide();

        },
        failure: function (msg) {
            alert(msg);
        }

    });
}

function _rpHideAllFilters() {
    $(".filterRow").hide();
    $('#DisplayType').hide();
    $('#Geo').hide();
    $('#Product').hide();
    $('#Period').hide();
    $('#Store').hide();
    $('#Email').hide();
}