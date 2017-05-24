<table class="table table-bordered" xmlns:g="http://www.w3.org/1999/html" xmlns:g="http://www.w3.org/1999/html">
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
            <th class="col-sm-2">Approval</th>
            <th class="col-sm-1">Send Request</th>
        </tr>
    </thead>
    <tbody>
    <g:each var="temp" in="${overTimeMaster}" status="stt">
        <tr>
            <td class="col-sm-1">${stt+1}</td>
            <td class="col-sm-1">${temp.year}</td>
            <td class="col-sm-1">
                <g:if test="${temp.status == '100'}">
                    <g:link controller="overTime" action="edit" params="${[month:temp.month, year:temp.year]}">${temp.month}</g:link>
                </g:if>
                <g:else>
                    ${temp.month}
                </g:else>
            </td>

            <td class="col-sm-1">${temp.totalOvertime}</td>
            <td class="col-sm-1">${temp.totalOvertimeWeekend}</td>
            <td class="col-sm-1">${temp.totalPaidLeave}</td>
            <td class="col-sm-1">${temp.totalUnPaidLeave}</td>
            <td class="col-sm-1">${temp.status}</td>
            <td class="col-sm-2">
                <g:select from="${managerList}" optionKey="id" optionValue="username" name="maj id="managerList_${temp.id}"/>
            </td>
            <td class="col-sm-1">
                <g:if test="${temp.status == '100'}">
                    <button type="button" class="btn fa fa-send submit-btn" id="submitBtn_${temp.id}"></button>
                </g:if>
            </td>
        </tr>
    </g:each>
    </tbody>

</table>

<g:form action="sendToManager" controller="overTime" name="sendToManager" id="sendToManager">
    <input type="hidden" id="selectedManagerId" name="selectedManagerId">
    <input type="hidden" id="selectedOverTimeMasterId" name="selectedOverTimeMasterId">
</g:form>
<script>
    $( document ).ready(function() {
        $('.submit-btn').click(function() {
            var index = $(this).attr('id').split('_')[1];
            $('#selectedManagerId').val($('#managerList_'+index).val());
            $('#selectedOverTimeMasterId').val(index);
            $('#sendToManager').submit();
        });
    });
</script>
</body>
</html>