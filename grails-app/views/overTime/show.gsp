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
    </div>
    <div class="col-sm-10" class="table-responsive" id="showOverTime">
        <g:render template="/overTime/showOverTime" />
    </div>
</div>
<script>
    $( document ).ready(function() {

        $('#selectedYear').on('change',(function() {
            var year = $('#selectedYear').val();
            console.log('year'+year);
            $.ajax({
                method: "POST",
                url: "${createLink(controller:'overTime' ,action: 'reloadShow')}",
                data: { selectedYear: year}
            })
            .done(function( msg ) {
                $('#showOverTime').html(msg);
            });
        }));


    });
</script>
</body>
</html>
