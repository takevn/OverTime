<table class="table table-hover">
    <thead>
    <tr>
        <th>#</th>
        <th>WeekDay</th>
        <th>Day</th>
        <th>StartTime</th>
        <th>EndTime</th>
        <th>Normal OverTime</th>
        <th>Weekend OverTime</th>
    </tr>
    </thead>
    <tbody>
    <g:each var="temp" in="${data}" status="stt">
        <tr>
            <td>${stt+1}</td>
            <td><g:message code="day.of.week.${temp.weekday}" /></td>
            <td>${temp.day}</td>
            <!--<td><g:textField name="startTime" id="startTime_${stt+1}" class="get-time"/></td>-->
            <td class="clockpicker " data-placement="left" data-align="top" data-autoclose="true" >
                <input type="text" class="col-sm-4 get-time" name="startTime" id="startTime_${stt+1}" />
            </td>

            <!--<td><g:textField name="endTime" id="endTime_${stt+1}" class="get-time" /></td>-->
            <td class=" clockpicker " data-placement="left" data-align="top" data-autoclose="true" >
                <input type="text" class="col-sm-4 get-time" name="endTime" id="endTime_${stt+1}" />
            </td>
            <td id="normalOvertime_${stt+1}" class="normal-overtime"></td>
            <td id="weekendOvertime_${stt+1}" class="weekend-overtime"></td>
            <g:hiddenField name="day_${stt+1}" value="${temp.day}" id="day_${stt+1}" />
        </tr>

    </g:each>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><label id="totalNormal"></label></td>
        <td><label id="totalWeekend"></label></td>
    </tr>

    </tbody>
</table>

<script>
    $( document ).ready(function() {
        $('.clockpicker').clockpicker();
        $( ".get-time" ).blur(function() {
            var index = $(this).attr('id').split('_')[1];
            var day = $('#day_'+index).val();
            var month = $('#month').val();
            var year = $('#year').val();
            var startTime = $('#startTime_'+index).val();
            var endTime = $('#endTime_'+index).val();
            var totalOt;
            if (startTime != '' && endTime!='') {
                $.ajax({
                    method: "POST",
                    url: "${createLink(controller:'overTime' ,action: 'getOverTime')}",
                    data: { day: day, month: month, year: year, startTime:startTime, endTime:endTime }
                })
                .done(function( msg ) {
                    if(msg.weekend == false) {
                        $('#normalOvertime_'+index).html(msg.total);
                        totalOt= totalOverTime('normal-overtime');
                        $('#totalNormal').html(totalOt);
                    } else {
                        $('#weekendOvertime_'+index).html(msg.total);
                        totalOt= totalOverTime('weekend-overtime');
                        $('#totalWeekend').html(totalOt);
                    }

                });
            }

        });
        function totalOverTime($class) {
        var total = 0;
            $("."+$class).each(function(index,item) {
                if($( this ).text()!= '') {
                    total = total + parseFloat($(this).text());
                    console.log('total'+total);
                }
            });
            return total;
        }
    });
</script>