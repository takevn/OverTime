<!doctype html>
<html xmlns:g="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main"/>
    <title>Welcome Bip</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

</head>
<body>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            List Request
        </h1>
    </section>

    <div class="col-sm-10" class="table-responsive" id="showOverTime">
        <table class="table table-bordered">
            <thead>
            <tr >
                <th class="col-sm-1">STT</th>
                <th class="col-sm-1">Year</th>
                <th class="col-sm-1">Month</th>
                <th class="col-sm-1">Total Overtime</th>
                <th class="col-sm-1">Total Overtime Weekend</th>
                <th class="col-sm-1">Total Hours PaidLeave</th>
                <th class="col-sm-1">Total Hours UnPaidLeave</th>
                <th class="col-sm-1">Status</th>
                <th class="col-sm-1">Employee Name</th>
                <th class="col-sm-1"></th>

            </tr>
            </thead>
            <tbody>
            <g:each var="temp" in="${overTimeMasterList}" status="stt">
                <tr>
                    <td class="col-sm-1"><g:link controller="overTime" action="hrCkeck" params="${[overTimeMasterId: temp.id]}">${stt+1}</g:link></td>
                    <td class="col-sm-1">${temp.year}</td>
                    <td class="col-sm-1">${temp.month}</td>
                    <td class="col-sm-1">${temp.totalOvertime}</td>
                    <td class="col-sm-1">${temp.totalOvertimeWeekend}</td>
                    <td class="col-sm-1">${temp.totalPaidLeave}</td>
                    <td class="col-sm-1">${temp.totalUnPaidLeave}</td>
                    <td class="col-sm-1">${temp.status}</td>
                    <td class="col-sm-1">${temp.secUser.username}</td>
                    <td class="col-sm-1">
                        <button type="button" class="btn btn-primary check-btn" id="btnCheck_${temp.id}"></button>
                    </td>
                </tr>
            </g:each>
            </tbody>

        </table>
        <g:form action="hrCheckInfo" controller="overTime" name="hrCkeckForm" id="hrCkeckForm">
            <input type="hidden" name="overTimeMasterId" id="overTimeMasterId">
        </g:form>
    </div>
</div>
<script>
    $( document ).ready(function() {
        $('.check-btn').click(function() {
            var index = $(this).attr('id').split('_')[1];
            $('#overTimeMasterId').val(index);
            $('#hrCkeckForm').submit();
        });

    });
</script>
</body>
</html>
