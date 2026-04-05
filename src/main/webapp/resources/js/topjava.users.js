const userAjaxUrl = "admin/users/";
const ctx = { ajaxUrl: userAjaxUrl };

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                { "data": "name" },
                { "data": "email" },
                { "data": "roles" },
                { "data": "enabled"},
                { "data": "registered" },
                { "defaultContent": "Edit", "orderable": false },
                { "defaultContent": "Delete", "orderable": false }
            ],
            "order": [[0, "asc"]]
        })
    );

    $('#datatable').on('change', '.user-activation', function () {
        const checkbox = $(this);
        const row = checkbox.closest('tr');
        const id = checkbox.data('id');
        const enabled = checkbox.is(':checked');
        const previousState = !enabled;

        $.ajax({
            url: userAjaxUrl + id + '/status?enabled=' + enabled,
            type: 'PUT',
            success: function () {
                if (enabled) {
                    row.removeClass('disabled-user');
                } else {
                    row.addClass('disabled-user');
                }
                successNoty("User" + (enabled ? "activated" : "deactivated"));
            },
            error: function () {
                checkbox.prop('checked', previousState);
            }
        });
    });
});

function updateTable() {
    $.get(ctx.ajaxUrl, function (data) {
        updateTableWithData(data);
    })
}