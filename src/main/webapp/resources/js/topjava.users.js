const userAjaxUrl = "admin/users/";
const ctx = { ajaxUrl: userAjaxUrl };

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                { "data": "name" },
                { "data": "email" },
                { "data": "roles" },
                { "data": "enabled",
                    "render": function(data) {
                        return '<input type="checkbox" class="user-activation" ' +
                            (data ? 'checked' : '') + '/>';
                    } },
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
        const id = row.attr('id');
        const enabled = checkbox.is(':checked');

        checkbox.prop('disabled', true);

        $.ajax({
            url: userAjaxUrl + id + '/status?enabled=' + enabled,
            type: 'PUT',
            success: function () {
                row.attr('data-user-enabled', enabled);
                updateTable();
            },
            complete: function (){
                checkbox.prop('disabled', false);
            }
        });
    });
});