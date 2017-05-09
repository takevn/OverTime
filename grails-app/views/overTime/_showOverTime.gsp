<table class="table table-bordered" xmlns:g="http://www.w3.org/1999/html" xmlns:g="http://www.w3.org/1999/html">
    <thead>
        <tr >
            <th class="col-sm-1">STT</th>
            <th class="col-sm-1">Year</th>
            <th class="col-sm-1">Month</th>
            <th class="col-sm-2">Total Overtime</th>
            <th class="col-sm-2">Total Overtime Weekend</th>
            <th class="col-sm-2">Status</th>
            <th class="col-sm-1">Approval</th>
            <th class="col-sm-1">Total Overtime Weekend</th>
        </tr>
    </thead>
    <tbody>
    <g:each var="temp" in="${overTimeMaster}" status="stt">
        <tr>
            <td class="col-sm-1">${stt+1}</td>
            <td class="col-sm-1">${temp.year}</td>
            <td class="col-sm-1">
                <g:link controller="overTime" action="edit" params="${[month:temp.month, year:temp.year]}">${temp.month}</g:link>
            </td>
            <td class="col-sm-2">${temp.totalOvertime}</td>
            <td class="col-sm-2">${temp.totalOvertimeWeekend}</td>
            <td class="col-sm-1">${temp.status}</td>
            <td class="col-sm-1">
                <g:select from="${managerList}" optionKey="id" optionValue="username" name="managerList_${temp.id}" id="managerList_${temp.id}"/>
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
            var socket = new SockJS("${createLink(uri: '/stomp')}");
				var client = Stomp.over(socket);

				client.connect({}, function() {
					client.subscribe("/user/queue/messagetouser", function(message) {

						var data = JSON.parse(message.body);
						console.log(data.notificationUnreadMessage);
                        $('#notificationNum').text(data.notificationUnreadMessage);
                        $('#messageList').append("<li><a>"+data.message+"<a></li>");
					});
				});


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