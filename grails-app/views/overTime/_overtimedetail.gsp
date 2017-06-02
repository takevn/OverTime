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
    <input type="hidden" name="totalday" value="${data.size()}">
    <g:each var="temp" in="${data}" status="stt">
        <tr>
            <td>${stt+1}</td>
            <td><g:message code="day.of.week.${temp.weekday}" /></td>
            <td>${temp.day}</td>
            <!--<td><g:textField name="startTime" id="startTime_${stt+1}" class="get-time"/></td>-->
            <td class="clockpicker " data-placement="left" data-align="top" data-autoclose="true" >
                <input type="text" class="col-sm-5 get-time" name="startTime_${stt+1}" id="startTime_${stt+1}" />
            </td>

            <!--<td><g:textField name="endTime" id="endTime_${stt+1}" class="get-time" /></td>-->
            <td class=" clockpicker " data-placement="left" data-align="top" data-autoclose="true" >
                <input type="text" class="col-sm-5 get-time" name="endTime_${stt+1}" id="endTime_${stt+1}" />
            </td>
            <td>
                <g:if test="${temp.weekday == 1 || temp.weekday == 7}">
                    <input type="text" class="col-sm-5 get-paid-leave" name="hoursPaidLeave_${stt+1}" id="hoursPaidLeave_${stt+1}" disabled="disabled"/>
                </g:if>
                <g:else>
                    <input type="text" class="col-sm-5 get-paid-leave" name="hoursPaidLeave_${stt+1}" id="hoursPaidLeave_${stt+1}" />
                </g:else>

            </td>
            <td>
                <g:if test="${temp.weekday == 1 || temp.weekday == 7}">
                    <input type="text" class="col-sm-5 get-unpaid-leave" name="hoursUnPaidLeave_${stt+1}" id="hoursUnPaidLeave_${stt+1}" disabled="disabled"/>
                </g:if>
                <g:else>
                    <input type="text" class="col-sm-5 get-unpaid-leave" name="hoursUnPaidLeave_${stt+1}" id="hoursUnPaidLeave_${stt+1}" />
                </g:else>

            </td>
            <td name ="actualTime_${stt+1}" id="actualTime_${stt+1}"></td>
            <td name="normalOvertime_${stt+1}"id="normalOvertime_${stt+1}" class="normal-overtime"></td>
            <td name="weekendOvertime_${stt+1}" id="weekendOvertime_${stt+1}" class="weekend-overtime"></td>
            <td name="statusCome_${stt+1}" id="statusCome_${stt+1}"></td>
            <g:hiddenField name="day_${stt+1}" value="${temp.day}" id="day_${stt+1}" />
            <input type="hidden" id="inputNormalOvertime_${stt+1}" name="inputNormalOvertime_${stt+1}">
            <input type="hidden" id="inputWeekendOvertime_${stt+1}" name="inputWeekendOvertime_${stt+1}">
            <input type="hidden" id="inputActualTime_${stt+1}" name="inputActualTime_${stt+1}">
            <input type="hidden" id="displayStatusCome_${stt+1}" name="displayStatusCome_${stt+1}">
        </tr>

    </g:each>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><label id="totalPaidLeave"></label></td>
        <td><label id="totalUnPaidLeave"></label></td>
        <td></td>
        <td><label id="totalNormal"></label></td>
        <td><label id="totalWeekend"></label></td>
        <input type="hidden" id="displayTotalNormal" name="displayTotalNormal">
        <input type="hidden" id="displayTotalWeekend" name="displayTotalWeekend">
        <input type="hidden" id="displayPaidLeave" name="displayPaidLeave">
        <input type="hidden" id="displayUnPaidLeave" name="displayUnPaidLeave">
        <input type="hidden" id="displayStatusCome" name="displayStatusCome">
    </tr>


    </tbody>
</table>
<input type="submit" value="confirm" class="btn btn-primary">
<script>
    $( document ).ready(function() {
        $('.clockpicker').clockpicker();
        $( ".get-time" ).blur(function() {
            var index = $(this).attr('id').split('_')[1];
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
                    data: { day: day, months: month, year: year, startTime:startTime, endTime:endTime, hoursPaidLeave:hoursPaidLeave, hoursUnPaidLeave:hoursUnPaidLeave}
                })
                .done(function( msg ) {
                    if(msg.weekend == false) {
                        $('#normalOvertime_'+index).html(msg.total);
                        $('#actualTime_'+index).html(msg.actualTimes);
                        $('#inputActualTime_'+index).val(msg.actualTimes);
                        $('#inputNormalOvertime_'+index).val(msg.total);
                        totalOt= totalOverTime('normal-overtime');
                        $('#totalNormal').html(totalOt);
                        $('#displayTotalNormal').val(totalOt);

                        if (msg.statusCome == "100") {
                            $('#statusCome_'+index).html("<g:message code='status.come.company.100'/>");
                        } else if (msg.statusCome == "200") {
                            $('#statusCome_'+index).html("<g:message code='status.come.company.200'/>");
                        } else if (msg.statusCome == "300") {
                            $('#statusCome_'+index).html("<g:message code='status.come.company.300'/>");
                        }

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