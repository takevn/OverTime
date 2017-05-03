package book

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.time.Year

class OverTimeController {
    OverTimeService overTimeService

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();

        Calendar monthStart = GregorianCalendar.getInstance();
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);

        def data = []
        for (int i = 1; i <= dayofmonth; i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            temp.get(Calendar.DAY_OF_WEEK)
            data << [weekday: temp.get(Calendar.DAY_OF_WEEK), day: i, month: temp.get(Calendar.MONTH), year: temp.get(Calendar.YEAR)];
        }
        render view: "index", model: [data: data, month: calendar.get(Calendar.MONTH) + 1, year: calendar.get(Calendar.YEAR)]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def reLoadOverTimeSheet() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();
        int month = Integer.valueOf(params.selectedMonth) - 1;
        Calendar monthStart = GregorianCalendar.getInstance();
        monthStart.set(Calendar.MONTH, month);
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        def data = []
        temp.set(Calendar.MONTH, month)
        for (int i = 1; i <= dayofmonth; i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            temp.get(Calendar.DAY_OF_WEEK)
            data << [weekday: temp.get(Calendar.DAY_OF_WEEK), day: i, month: temp.get(Calendar.MONTH), year: temp.get(Calendar.YEAR)];
        }
        render(template: '/overTime/overtimedetail', model: [data: data])
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def getOverTime() {
        def response
        // ko du paramester
        def err = " my webservice need praramester as belows : date,year,month,starttime,endtime"
        def startTime = params.startTime
        def endTime = params.endTime
        def year = params.year
        def month = params.month
        def day = params.day
        def otData = overTimeService.getOverTime(year, month, day, startTime, endTime)
        response = [status: 200, total: otData.overTimeInHours, weekend: otData.isWeekend, actualTimes: otData.actualWokingTime]
        render response as JSON
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def confirm() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();

        Calendar monthStart = GregorianCalendar.getInstance();
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH)

        def totalday = params.totalday
        def totalNormalOverTime = params.displayTotalNormal
        def totalWeekendOverTime = params.displayTotalWeekend
        def tempNormalOverTime
        def startTime
        def endTime
        def actualTime
        def weekendOvertime
        def data = []
        for (int i = 1; i <= Integer.valueOf(totalday); i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            tempNormalOverTime = params.('inputNormalOvertime_' + i)
            startTime = params.('startTime_' + i)
            endTime = params.('endTime_' + i)
            actualTime = params.('inputActualTime_' + i)
            weekendOvertime = params.('inputWeekendOvertime_' + i)
            if (tempNormalOverTime) {
                if (Double.valueOf(tempNormalOverTime) > 0) {
                    data << [weekday        : temp.get(Calendar.DAY_OF_WEEK),
                             day            : i,
                             month          : temp.get(Calendar.MONTH),
                             year           : temp.get(Calendar.YEAR),
                             startTime      : startTime,
                             endTime        : endTime,
                             actualTime     : actualTime,
                             normalOvertime : tempNormalOverTime,
                             weekendOvertime: weekendOvertime]
                }
            }
            if (weekendOvertime) {
                if (Double.valueOf(weekendOvertime) > 0) {
                    data << [weekday   : temp.get(Calendar.DAY_OF_WEEK), day: i, month: temp.get(Calendar.MONTH), year: temp.get(Calendar.YEAR), startTime: startTime, endTime: endTime,
                             actualTime: actualTime, normalOvertime: tempNormalOverTime, weekendOvertime: weekendOvertime]
                }
            }
        }
        render view: 'confirm', model: [data: data, totalNormalOverTime: totalNormalOverTime, totalWeekendOverTime: totalWeekendOverTime,
                                        month: calendar.get(Calendar.MONTH) + 1, year: calendar.get(Calendar.YEAR)]
    }
}
