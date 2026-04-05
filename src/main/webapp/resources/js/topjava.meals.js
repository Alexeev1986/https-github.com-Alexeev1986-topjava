const mealAjaxUrl = "profile/meals/";
const ctx = { ajaxUrl: mealAjaxUrl };

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                { "data": "dateTime" },
                { "data": "description" },
                { "data": "calories" },
                { "defaultContent": "Edit", "orderable": false },
                { "defaultContent": "Delete", "orderable": false }
            ],
            "order": [[0, "desc"]]
        })
    );
});

function filter() {
    isFilterActive = true;
    $.get(mealAjaxUrl + "filter", {
        startDate: $('#startDate').val(),
        endDate: $('#endDate').val(),
        startTime: $('#startTime').val(),
        endTime: $('#endTime').val()
    }, function (filteredMeals) {
        ctx.datatableApi.clear().rows.add(filteredMeals).draw();
    });
    return false;
}

function resetFilter() {
    $('#startDate').val('');
    $('#endDate').val('');
    $('#startTime').val('');
    $('#endTime').val('');

    isFilterActive = false;

    updateTable();
}