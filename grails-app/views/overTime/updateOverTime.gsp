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
    ${message}
    <g:form controller="overTime" action="update" name="timesheetform" id="timesheetform">

        <input type="text" name="selectedMonth" id="selectedMonth" value="${overTimeMaster.month}" disabled="disabled"/>
        <div id="overTimeDetail">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>WeekDay</th>
                    <th>Day</th>
                    <th>StartTime</th>
                    <th>EndTime</th>
                    <th>Hours PaidLeave</th>
                    <th>Hours UnPaidLeave</th>
                    <th>Actual Times</th>
                    <th>Normal OverTimes</th>
                    <th>Weekend OverTimes</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                    <g:each var="temp" in="${overTimeMaster.overtimeHistory.sort(){it.day}}" status="stt">
                        <tr>
                            <td>${stt+1}</td>
                            <td><g:message code="day.of.week.${temp.weekday}" /></td>
                            <td id="">${temp.day}</td>
                            <!--<td><g:textField name="startTime" id="startTime_${stt+1}" class="get-time"/></td>-->
                            <td class="clockpicker " data-placement="left" data-align="top" data-autoclose="true" >
                                <g:if test="${temp.comeTime == '0'}">
                                    <input type="text" class="col-sm-5 get-time" name="startTime_${temp.id}" id="startTime_${temp.id}" />
                                </g:if>
                                <g:else>
                                    <input type="text" class="col-sm-5 get-time" name="startTime_${temp.id}" id="startTime_${temp.id}" value="${temp.comeTime}" />
                                </g:else>
                            </td>

                            <!--<td><g:textField name="endTime" id="endTime_${stt+1}" class="get-time" /></td>-->
                            <td class=" clockpicker " data-placement="left" data-align="top" data-autoclose="true" >
                                <g:if test="${temp.comeTime == '0'}">
                                    <input type="text" class="col-sm-5 get-time" name="endTime_${temp.id}" id="endTime_${temp.id}"/>
                                </g:if>
                                <g:else>
                                    <input type="text" class="col-sm-5 get-time" name="endTime_${temp.id}" id="endTime_${temp.id}" value="${temp.leaveTime}"/>
                                </g:else>

                            </td>
                            <td>
                                <g:if test="${temp.weekday == 1 || temp.weekday == 7}">
                                    <input type="text" class="col-sm-5 get-paid-leave" name="hoursPaidLeave_${temp.id}" id="hoursPaidLeave_${temp.id}" disabled="disabled"/>
                                </g:if>
                                <g:else>
                                    <input type="text" class="col-sm-5 get-paid-leave" name="hoursPaidLeave_${temp.id}" id="hoursPaidLeave_${temp.id}" value="${temp.hoursPaidLeave}"/>
                                </g:else>

                            </td>
                            <td>
                                <g:if test="${temp.weekday == 1 || temp.weekday == 7}">
                                    <input type="text" class="col-sm-5 get-unpaid-leave" name="hoursUnPaidLeave_${temp.id}" id="hoursUnPaidLeave_${temp.id}" disabled="disabled"/>
                                </g:if>
                                <g:else>
                                    <input type="text" class="col-sm-5 get-unpaid-leave" name="hoursUnPaidLeave_${temp.id}" id="hoursUnPaidLeave_${temp.id}" value="${temp.hoursUnPaidLeave}" />
                                </g:else>

                            </td>
                            <td id="actualTime_${temp.id}">${temp.actualTime}</td>
                            <td id="normalOvertime_${temp.id}" class="normal-overtime">${temp.overTimeNormal}</td>
                            <td id="weekendOvertime_${temp.id}" class="weekend-overtime">${temp.overTimeWeekend}</td>
                            <td name="statusCome_${temp.id}" id="statusCome_${temp.id}">${temp.statusComeCompany}</td>
                            <input type="hidden" id="inputNormalOvertime_${temp.id}" name="inputNormalOvertime_${temp.id}" value="${temp.overTimeNormal}">
                            <input type="hidden" id="inputWeekendOvertime_${temp.id}" name="inputWeekendOvertime_${temp.id}" value="${temp.overTimeWeekend}">
                            <input type="hidden" id="inputActualTime_${temp.id}" name="inputActualTime_${temp.id}" value="${temp.actualTime}">
                            <input type="hidden" id="displayStatusCome_${temp.id}" name="displayStatusCome_${temp.id}">
                            <g:hiddenField name="day_${temp.id}" value="${temp.day}" id="day_${temp.id}" />
                        </tr>

                    </g:each>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><label id="totalPaidLeave">${overTimeMaster.totalPaidLeave}</label></td>
                        <td><label id="totalUnPaidLeave">${overTimeMaster.totalUnPaidLeave}</label></td>
                        <td></td>
                        <td><label id="totalNormal">${overTimeMaster.totalOvertime}</label></td>
                        <td><label id="totalWeekend">${overTimeMaster.totalOvertimeWeekend}</label></td>
                        <input type="hidden" id="displayTotalNormal" name="displayTotalNormal" value="${overTimeMaster.totalOvertime}">
                        <input type="hidden" id="displayTotalWeekend" name="displayTotalWeekend" value="${overTimeMaster.totalOvertimeWeekend}">
                        <input type="hidden" id="displayPaidLeave" name="displayPaidLeave" value="${overTimeMaster.totalPaidLeave}">
                        <input type="hidden" id="displayUnPaidLeave" name="displayUnPaidLeave" value="${overTimeMaster.totalPaidLeave}">

                    </tr>

                </tbody>
            </table>
            <input type="submit" value="update" class="btn btn-primary">
        </div>
        <g:hiddenField name="overTimeMasterId" value="${overTimeMaster.id}" id="overTimeMasterId" />
        <g:hiddenField name="month" value="${overTimeMaster.month}" id="month" />
        <g:hiddenField name="year" value="${overTimeMaster.year}" id="year" />
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
    $('.clockpicker').clockpicker();
    $( ".get-time" ).blur(function() {
        var index = $(this).attr('id').split('_')[1];
        console.log(index);
        var day = $('#day_'+index).val();
        var month = $('#selectedMonth').val();
        var year = $('#year').val();
        var startTime = $('#startTime_'+index).val();
        var endTime = $('#endTime_'+index).val();
        var hoursPaidLeave = $('#hoursPaidLeave_'+index).val();
        var hoursUnPaidLeave = $('#hoursUnPaidLeave_'+index).val();
        var totalOt;
        if (startTime != '' && endTime!='') {
            $.ajax({
                method: "POST",
                url: "${createLink(controller:'overTime' ,action: 'getOverTime')}",
                data: { day: day, months: month, year: year, startTime:startTime, endTime:endTime,hoursPaidLeave:hoursPaidLeave, hoursUnPaidLeave:hoursUnPaidLeave }
            })
            .done(function( msg ) {
                if(msg.weekend == false) {
                    $('#normalOvertime_'+index).html(msg.total);
                    $('#actualTime_'+index).html(msg.actualTimes);
                    $('#inputActualTime_'+index).val(msg.actualTimes);
                    $('#inputNormalOvertime_'+index).val(msg.total);
                    $('#normalOvertime_'+index).val(msg.total);
                    totalOt= totalOverTime('normal-overtime');
                    $('#totalNormal').html(totalOt);
                    $('#displayTotalNormal').val(totalOt);
                    $('#statusCome_'+index).html(msg.statusCome);
                    $('#displayStatusCome_'+index).val(msg.statusCome);

                } else {
                    $('#weekendOvertime_'+index).html(msg.total);
                    $('#inputWeekendOvertime_'+index).val(msg.total);
                    $('#inputActualTime_'+index).val(msg.actualTimes);
                    $('#actualTime_'+index).html(msg.actualTimes);
                    totalOt= totalOverTime('weekend-overtime');
                    $('#totalWeekend').html(totalOt);
                    $('#displayTotalWeekend').val(totalOt);
                }

            });
        } else if (startTime != '' ||  endTime!=''){
            $('#actualTime_'+index).html(0);
            $('#normalOvertime_'+index).html(0);
            $('#weekendOvertime_'+index).html(0);
            $('#inputWeekendOvertime_'+index).val(0);
            $('#inputActualTime_'+index).val(0);
            $('#totalNormal').html(totalOverTime('normal-overtime'));
            $('#totalWeekend').html(totalOverTime('weekend-overtime'));
            var totalNormal = $('#totalNormal').text();
            var totalWeekend = $('#totalWeekend').text();
            $('#displayTotalNormal').val(totalNormal);
            $('#displayTotalWeekend').val(totalWeekend);
        }

    });
    function totalOverTime($class) {
    var total = 0;
        $("."+$class).each(function(index,item) {
            if($( this ).text()!= '') {
                total = total + parseFloat($(this).text());
            }
        });
        return total;
    }
    $( ".get-paid-leave" ).change(function() {
            var totalPaid;
            totalPaid = totalPaidLeave('get-paid-leave');
            $('#totalPaidLeave').text(totalPaid);
            $('#displayPaidLeave').val(totalPaid);

        });

        $( ".get-unpaid-leave" ).change(function() {
            var totalUnPaid;
            totalUnPaid = totalPaidLeave('get-unpaid-leave');
            $('#totalUnPaidLeave').text(totalUnPaid);
            $('#displayUnPaidLeave').val(totalUnPaid);
        });

        function totalPaidLeave($class) {
        var total = 0;
            $("."+$class).each(function(index,item) {
                console.log($(this).val());
                if($( this ).val()!= '') {
                    total = total + parseFloat($(this).val());
                }
            });
            return total;
        }
    });
</script>
</body>
</html>
