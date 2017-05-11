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
            Check OverTime
        </h1>
    </section>
    <div class="text-font ">
        Month: ${overTimeMaster.month}    Year: ${overTimeMaster.year}
        <br>
        <br>
        Division: IT
        <br>
        <br>
        Name: ${overTimeMaster.secUser.username}
    </div>
    <g:form controller="overTime" action="checked" name="timesheetform" id="timesheetform">
        <div id="overTimeDetail">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>WeekDay</th>
                    <th>Day</th>
                    <th>StartTime</th>
                    <th>EndTime</th>
                    <th>Actual Times</th>
                    <th>Normal OverTimes</th>
                    <th>Weekend OverTimes</th>
                </tr>
                </thead>
                <tbody>
                    <g:each var="temp" in="${overTimeMaster.overtimeHistory.sort(){it.day}}" status="stt">
                        <tr>
                            <td>${stt+1}</td>
                            <td><g:message code="day.of.week.${temp.weekday}" /></td>
                            <td id="">${temp.day}</td>
                            <!--<td><g:textField name="startTime" id="startTime_${stt+1}" class="get-time"/></td>-->
                            <td>
                                <input type="text" class="col-sm-4 get-time" name="startTime_${temp.id}" id="startTime_${temp.id}" value="${temp.comeTime}" disabled="disabled"/>
                            </td>

                            <!--<td><g:textField name="endTime" id="endTime_${stt+1}" class="get-time" /></td>-->
                            <td>
                                <input type="text" class="col-sm-4 get-time" name="endTime_${temp.id}" id="endTime_${temp.id}" value="${temp.leaveTime}" disabled="disabled"/>
                            </td>
                            <td id="actualTime_${temp.id}">${temp.actualTime}</td>
                            <td id="normalOvertime_${temp.id}" class="normal-overtime">${temp.overTimeNormal}</td>
                            <td id="weekendOvertime_${temp.id}" class="weekend-overtime">${temp.overTimeWeekend}</td>
                        </tr>

                    </g:each>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><label id="totalNormal">${overTimeMaster.totalOvertime}</label></td>
                        <td><label id="totalWeekend">${overTimeMaster.totalOvertimeWeekend}</label></td>
                    </tr>

                </tbody>
            </table>
            <input type="hidden" id="overTimeMasterId" name="overTimeMasterId" value="${overTimeMaster.id}">
            <input type="hidden" id="notificationId" name="notificationId" value="${notification.id}">
            <g:actionSubmit value="Accept" action="acceptOverTime" />
            <g:actionSubmit value="reject" action="rejectOverTime" />

        </div>
    </g:form>

</div>
</body>
</html>
