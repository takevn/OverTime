package book

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import bip.ot.OverTime;

@Transactional
class OverTimeService {

    def getOverTime(String year, String month, String day, String comeTime, String leaveTime, String hoursPaidLeave, String hoursUnPaidLeave) throws Exception {
        StringBuilder comeDate = new StringBuilder()
        StringBuilder leaveDate = new StringBuilder()
        OverTime overTime = new OverTime();

        comeDate.append(year)
        leaveDate.append(year)

        if (month.length() == 1) month = String.format("0%s", month)
        comeDate.append("-").append(month)
        leaveDate.append("-").append(month)

        if (day.length() == 1) day = String.format("0%s", day)
        comeDate.append("-").append(day)
        leaveDate.append("-").append(day)

        comeDate.append(" ").append(comeTime)
        leaveDate.append(" ").append(leaveTime)

        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day))
        calendar.set(Calendar.MONTH, Integer.parseInt(month)-1)
        calendar.set(Calendar.YEAR, Integer.parseInt(year))

        def map =  overTime.getOverTimeUsingCalendar(comeDate.toString(), leaveDate.toString(), hoursPaidLeave, hoursUnPaidLeave)
        return map
    }
}
