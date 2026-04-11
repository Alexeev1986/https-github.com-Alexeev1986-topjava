const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            return JSON.parse(stringData, function (key, value) {
                return (key === 'dateTime') ? value.substring(0, 16).replace('T', ' ') : value;
            })
        }
    }
});

$(function () {
    makeEditable( $("#datatable").DataTable(
       {"ajax": { "url": mealAjaxUrl, "dataSrc": "" },
            "paging": false,
            "info": true,
            "columns": [
                { "data": "dateTime" },
                { "data": "description" },
                { "data": "calories" },
                { "orderable": false, "defaultContent": "", "render": renderEditBtn },
                { "orderable": false, "defaultContent": "", "render": renderDeleteBtn }
            ],
            "order": [[0, "desc"]],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-meal-excess", data.excess)
            }
       }
    ));

    if (typeof $.fn.datetimepicker !== 'undefined') {
        console.log("datepicker is load")
    }

    $.datetimepicker.setLocale(localization);

    const startDate = $('#startDate');
    const endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (date) {
            this.setOptions({ maxDate: endDate.val() ? endDate.val() : false })
        }
    });

    endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow: function (date) {
            this.setOptions({ minDate: startDate.val() ? startDate.val() : false })
        }
    });

    const startTime = $('#startTime');
    const endTime = $('#endTime');
    startTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (time) {
            this.setOptions({minTime: endTime.val() ? endTime.val() : false })
        }
    });

    endTime.datetimepicker({
        datepicker: false,
        format: 'H:i',
        onShow: function (time) {
            this.setOptions({maxTime: startTime.val() ? startTime.val() : false })
        }
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
});

