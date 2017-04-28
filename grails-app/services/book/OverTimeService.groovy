package book

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import bip.ot.OverTime;

@Transactional
class OverTimeService {

    float getOverTime(String year, String month, String day, String comeTime, String leaveTime) throws Exception {
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

        return overTime.getOverTimeUsingCalendar(comeDate.toString(), leaveDate.toString())
    }
}
