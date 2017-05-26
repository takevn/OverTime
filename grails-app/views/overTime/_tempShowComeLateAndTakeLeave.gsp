
<table class="table table-bordered" xmlns:g="http://www.w3.org/1999/html" xmlns:g="http://www.w3.org/1999/html">
    <thead>
        <tr >
            <th class="col-sm-1">STT</th>
            <th class="col-sm-1">Day</th>
            <th class="col-sm-1">Weekday</th>
            <th class="col-sm-1">ComeTime</th>
            <th class="col-sm-1">LeaveTime</th>
            <th class="col-sm-1">ActualTime</th>
            <th class="col-sm-1">OverTimeNormal</th>
            <th class="col-sm-1">OverTimeWeekend</th>
            <th class="col-sm-1">HoursPaidLeave</th>
            <th class="col-sm-1">HoursUnPaidLeave</th>
            <th class="col-sm-1">StatusComeCompany</th>
        </tr>
    </thead>
    <tbody>
    <g:each var="temp" in="${overTimeHistory}" status="stt">
        <tr>
            <td class="col-sm-1">${stt+1}</td>
            <td class="col-sm-1"><g:message code="day.of.week.${temp.weekday}" /></td>
            <td class="col-sm-1">${temp.weekday}</td>
            <td class="col-sm-1">${temp.comeTime}</td>
            <td class="col-sm-1">${temp.leaveTime}</td>
            <td class="col-sm-1">${temp.overTimeNormal}</td>
            <td class="col-sm-1">${temp.overTimeWeekend}</td>
            <td class="col-sm-1">${temp.actualTime}</td>
            <td class="col-sm-1">${temp.hoursPaidLeave}</td>
            <td class="col-sm-1">${temp.hoursUnPaidLeave}</td>
            <td class="col-sm-1"><g:message code="status.come.company.${temp.statusComeCompany}" /></td>
        </tr>
    </g:each>
    </tbody>

</table>
</body>
</html>