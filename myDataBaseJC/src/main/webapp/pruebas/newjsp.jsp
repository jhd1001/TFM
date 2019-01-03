<html>
    <head>
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1.min.js"></script>
        <title>MOVER ENC. prueba 02</title>
    </head>

    <body>
<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover table-condensed">
        <thead>
            <tr>
                <th>#</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
                <th>Table heading</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
            </tr>
            <tr>
                <td>2</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
            </tr>
            <tr>
                <td>3</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
                <td>Table cell</td>
            </tr>
        </tbody>
    </table>
</div>
    <style>
.table-responsive>.fixed-column {
    position: absolute;
    display: inline-block;
    width: auto;
    border-right: 1px solid #ddd;
}
@media(min-width:768px) {
    .table-responsive>.fixed-column {
        display: none;
    }
}
        </style>
        <script>
            var $table = $('.table');
var $fixedColumn = $table.clone().insertBefore($table).addClass('fixed-column');

$fixedColumn.find('th:not(:first-child),td:not(:first-child)').remove();

$fixedColumn.find('tr').each(function (i, elem) {
    $(this).height($table.find('tr:eq(' + i + ')').height());
});
        </script>
    </body>
</html>