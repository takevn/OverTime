<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome Bip</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

</head>
<body>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Show list
        </h1>
    </section>
    <div class="col-sm-4">
        <g:select from="${2006..2020}" name="selectedYear" id="selectedYear" value="${year}" class="form-control"/>
        <g:select from="${1..12}" name="selectedMonth" id="selectedMonth" value="${month}" />
    </div>
    <div class="col-sm-12" class="table-responsive" id="showComeLateAndTakeLeave">
        <g:render template="/overTime/tempShowComeLateAndTakeLeave" />
    </div>
</div>

<script>
    $( document ).ready(function() {

        $('#selectedYear').on('change',(function() {
            var year = $('#selectedYear').val();
            var month = $('#selectedMonth').val();

            $.ajax({
                method: "POST",
                url: "${createLink(controller:'overTime' ,action: 'reloadShowComeLateAndTakeLeave')}",
                data: { selectedYear: year, selectedMonth: month}
            })
            .done(function( msg ) {
                $('#showComeLateAndTakeLeave').html(msg);
            });
        }));

        $('#selectedMonth').on('change',(function() {
            var year = $('#selectedYear').val();
            var month = $('#selectedMonth').val();

            $.ajax({
                method: "POST",
                url: "${createLink(controller:'overTime' ,action: 'reloadShowComeLateAndTakeLeave')}",
                data: { selectedYear: year, selectedMonth: month}
            })
            .done(function( msg ) {
                $('#showComeLateAndTakeLeave').html(msg);
            });
        }));


    });
</script>
</body>
</html>
