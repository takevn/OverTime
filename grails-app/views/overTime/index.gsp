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
            Create OverTime
        </h1>
    </section>
    <g:form controller="overTime" action="confirm">

        <g:select from="${1..12}" name="selectedMonth" id="selectedMonth" value="${month}" />
        <div id="overTimeDetail">
            <g:render template="/overTime/overtimedetail" />
        </div>

        <g:hiddenField name="month" value="${month}" id="month" />
        <g:hiddenField name="year" value="${year}" id="year" />
    </g:form>

</div>
<asset:stylesheet src="/css/bootstrap-clockpicker.min.css"/>
<asset:stylesheet src="/css/jquery-clockpicker.min.css"/>
<asset:stylesheet src="/css/clockpicker.css"/>
<asset:stylesheet src="/css/standalone.css"/>


<asset:javascript src="/bootstrap-clockpicker.min.js"/>
<asset:javascript src="/jquery-clockpicker.min.js"/>
<asset:javascript src="/clockpicker.js"/>
<script>
    $( document ).ready(function() {
        $('#selectedMonth').on('change',(function() {
            var selectedMonth = $(this).val();
            $.ajax({
                    method: "POST",
                    url: "${createLink(controller:'overTime' ,action: 'reLoadOverTimeSheet')}",
                    data: { selectedMonth: selectedMonth}
                })
                .done(function( msg ) {
                    $('#overTimeDetail').html(msg);
                });
        }));
    });
</script>
</body>
</html>
