package book

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import overtime.OvertimeHistory
import overtime.OvertimeMaster

import java.time.Year

class OverTimeController {
    def springSecurityService
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
        render view: "index", model: [data: data, month: calendar.get(Calendar.MONTH) + 1, year: calendar.get(Calendar.YEAR),message:flash.confirmMessage]
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
            data << [weekday: temp.get(Calendar.DAY_OF_WEEK), day: i, month: temp.get(Calendar.MONTH)+1, year: temp.get(Calendar.YEAR)];
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
    def show() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();

        Calendar monthStart = GregorianCalendar.getInstance();
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);



        render view: "show", model: [month: calendar.get(Calendar.MONTH) + 1, year: calendar.get(Calendar.YEAR)]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def confirm() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();

        Calendar monthStart = GregorianCalendar.getInstance();
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH)
        def currentUser = springSecurityService.currentUser
        def overtimeHistoryTemp
        def totalday = params.totalday
        def totalNormalOverTime = params.displayTotalNormal
        def totalWeekendOverTime = params.displayTotalWeekend
        def tempNormalOverTime
        def startTime
        def endTime
        def actualTime
        def weekendOvertime
        def data = []
        def overtimeMaster
        def overtimeMasterList = OvertimeMaster.createCriteria().list(){
            eq("month", temp.get(Calendar.MONTH)+1)
            eq("year", temp.get(Calendar.YEAR))
            eq("secUser",currentUser)
        }
        println ">>>>>???>>>"+overtimeMasterList.size()
        if(overtimeMasterList.size() > 0) {
            overtimeMaster = overtimeMasterList[0]

        } else {
            overtimeMaster = new OvertimeMaster()
        }
        double totalNormal
        double totalWeekend
        if (params.displayTotalNormal != '') {
            totalNormal = Double.valueOf(params.displayTotalNormal)
        } else {
            totalNormal = 0
        }
        if (params.displayTotalWeekend != '') {
            totalWeekend = Double.valueOf(params.displayTotalWeekend)
        } else {
            totalWeekend = 0
        }
        println totalNormal
//        if (totalNormal > 0 || totalWeekend > 0) {
        overtimeMaster.year = temp.get(Calendar.YEAR)
        overtimeMaster.month = temp.get(Calendar.MONTH)+1
        overtimeMaster.totalOvertime = totalNormal
        overtimeMaster.totalOvertimeWeekend = totalWeekend
        overtimeMaster.save(flush: true)
        currentUser.addToOvertimeMaster(overtimeMaster).save(flush: true)//        }

        for (int i = 1; i <= Integer.valueOf(totalday); i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            tempNormalOverTime = params.('inputNormalOvertime_' + i)
            startTime = params.('startTime_' + i)
            endTime = params.('endTime_' + i)
            actualTime = params.('inputActualTime_' + i)
            weekendOvertime = params.('inputWeekendOvertime_' + i)
            def overTimeHistoryCheck = OvertimeHistory.findWhere(day: i,
                                                            month: temp.get(Calendar.MONTH)+1,
                                                            year: temp.get(Calendar.YEAR),
                                                            overtimeMaster:overtimeMaster)
            if (startTime && endTime) {
                if (tempNormalOverTime) {
                    if (Double.valueOf(tempNormalOverTime) > 0) {
                        if (!overTimeHistoryCheck) {
                            overtimeHistoryTemp = new OvertimeHistory()
                        } else {
                            overtimeHistoryTemp = overTimeHistoryCheck
                        }
                        overtimeHistoryTemp.userId = Integer.valueOf(currentUser.id.toString())
                        overtimeHistoryTemp.year = temp.get(Calendar.YEAR)
                        overtimeHistoryTemp.month = temp.get(Calendar.MONTH)+1
                        overtimeHistoryTemp.day = i
                        overtimeHistoryTemp.weekday = temp.get(Calendar.DAY_OF_WEEK)
                        overtimeHistoryTemp.actualTime = Double.valueOf(actualTime)
                        overtimeHistoryTemp.comeTime = startTime
                        overtimeHistoryTemp.leaveTime = endTime
                        overtimeHistoryTemp.overTimeNormal = tempNormalOverTime
                        overtimeHistoryTemp.save(flush: true)
                        overtimeMaster.addToOvertimeHistory(overtimeHistoryTemp).save(flush: true)
                    }
                }
                if (weekendOvertime) {
                    if (Double.valueOf(weekendOvertime) > 0) {
                        if (!overTimeHistoryCheck) {
                            overtimeHistoryTemp = new OvertimeHistory()
                        } else {
                            overtimeHistoryTemp = overTimeHistoryCheck
                        }
                        overtimeHistoryTemp.userId = Integer.valueOf(currentUser.id.toString())
                        overtimeHistoryTemp.year = temp.get(Calendar.YEAR)
                        overtimeHistoryTemp.month = temp.get(Calendar.MONTH)+1
                        overtimeHistoryTemp.day = i
                        overtimeHistoryTemp.weekday = temp.get(Calendar.DAY_OF_WEEK)
                        overtimeHistoryTemp.actualTime = Double.valueOf(actualTime)
                        overtimeHistoryTemp.comeTime = startTime
                        overtimeHistoryTemp.leaveTime = endTime
                        overtimeHistoryTemp.overTimeWeekend = weekendOvertime
                        overtimeHistoryTemp.save(flush: true)
                        overtimeMaster.addToOvertimeHistory(overtimeHistoryTemp).save(flush: true)

                    }
                }
            } else {
                if (overTimeHistoryCheck) {
                    overtimeHistoryTemp = overTimeHistoryCheck

                } else {
                    overtimeHistoryTemp = new OvertimeHistory()
                }
                overtimeHistoryTemp.userId = Integer.valueOf(currentUser.id.toString())
                overtimeHistoryTemp.year = temp.get(Calendar.YEAR)
                overtimeHistoryTemp.month = temp.get(Calendar.MONTH)+1
                overtimeHistoryTemp.day = i
                overtimeHistoryTemp.weekday = temp.get(Calendar.DAY_OF_WEEK)
                overtimeHistoryTemp.comeTime = 0
                overtimeHistoryTemp.leaveTime = 0
                overtimeHistoryTemp.overTimeWeekend = 0
                overtimeHistoryTemp.overTimeNormal = 0
                overtimeHistoryTemp.actualTime = 0
                overtimeHistoryTemp.save(flush: true)
                overtimeMaster.addToOvertimeHistory(overtimeHistoryTemp).save(flush: true)
            }
        }

        if (totalNormal == 0 && totalWeekend == 0) {
            flash.confirmMessage = "deo co gi thi dien an loz a"
            redirect action: "index"
        } else {
            render view: 'confirm', model: [overtimeMaster:overtimeMaster]
        }
    }

}
