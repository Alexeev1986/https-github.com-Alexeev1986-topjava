const mealAjaxUrl = "profile/meals/";
const ctx = { ajaxUrl: mealAjaxUrl };

function getCurrentDateTime() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}

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

    $('#editRow').on('show.bs.modal', function () {
        if (!$('#id').val()) {
            $('#dateTime').val(getCurrentDateTime());
        }
    });

    $(".edit").click(function () {
        editRow($(this).closest('tr').attr("id"));
    });
});

function editRow(id) {
    var rowData = ctx.datatableApi.row($('#' + id)).data();
    form.find("#id").val(rowData.id);
    form.find("#dateTime").val(rowData.dateTime.replace(' ', 'T').substring(0, 16));
    form.find("#description").val(rowData.description);
    form.find("#calories").val(rowData.calories);
    $("#editRow").modal();
}

