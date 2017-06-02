package book

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.example.SecRole
import org.example.SecUser
import org.example.SecUserSecRole
import overtime.Notification
import overtime.OvertimeHistory
import overtime.OvertimeMaster
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate

import java.time.Year

class OverTimeController {
    def springSecurityService
    OverTimeService overTimeService
    SimpMessagingTemplate brokerMessagingTemplate


    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def index() {

        Calendar calendar = Calendar.getInstance()
        int month
        if (params.selectedMonth) {
            month = Integer.valueOf(params.selectedMonth) - 1
        } else {
            month = calendar.get(Calendar.MONTH)
        }

        Calendar monthStart = GregorianCalendar.getInstance()
        monthStart.set(Calendar.MONTH, month)
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH)

        def data = []
        for (int i = 1; i <= dayofmonth; i++) {
            monthStart.set(Calendar.MONTH, month)
            monthStart.set(Calendar.DAY_OF_MONTH, i)
            monthStart.get(Calendar.DAY_OF_WEEK)
            data << [weekday: monthStart.get(Calendar.DAY_OF_WEEK), day: i, month: month, year: monthStart.get(Calendar.YEAR)];
        }
        render view: "index", model: [data: data, month: month+1 , year: calendar.get(Calendar.YEAR),message:flash.confirmMessage]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def getOverTime() {
        def response
        // ko du paramester
        def err = " my webservice need praramester as belows : date,year,month,starttime,endtime"
        def startTime = params.startTime
        def endTime = params.endTime
        def year = params.year
        def month = params.months
        def day = params.day
        def hoursPaidLeave = params.hoursPaidLeave
        def hoursUnPaidLeave = params.hoursUnPaidLeave
        def otData = overTimeService.getOverTime(year, month, day, startTime, endTime, hoursPaidLeave, hoursUnPaidLeave)
        response = [status: 200, total: otData.overTimeInHours, weekend: otData.isWeekend, actualTimes: otData.actualWokingTime, statusCome: otData.statusCome]
        render response as JSON
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def confirm() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();

        Calendar monthStart = GregorianCalendar.getInstance();
        int selectedMonth = Integer.valueOf(params.selectedMonth)
        temp.set(Calendar.MONTH, selectedMonth-1)
        def currentUser = springSecurityService.currentUser
        def overtimeHistoryTemp
        def totalday = params.totalday
        def tempNormalOverTime
        def startTime
        def endTime
        def actualTime
        def weekendOvertime
        def overtimeMaster
        def hoursPaidleave
        def hoursUnPaidleave
        def overtimeMasterList = OvertimeMaster.createCriteria().list(){
            eq("month", selectedMonth)
            eq("year", temp.get(Calendar.YEAR))
            eq("secUser",currentUser)
        }

        if(overtimeMasterList.size() > 0) {
            overtimeMaster = overtimeMasterList[0]

        } else {
            overtimeMaster = new OvertimeMaster()
        }
        double totalNormal
        double totalWeekend
        double totalPaidLeave
        double totalUnPaidLeave
        def displayStatusCome

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
        if (params.displayPaidLeave != '') {
            totalPaidLeave = Double.valueOf(params.displayPaidLeave)
        } else {
            totalPaidLeave = 0
        }
        if (params.displayUnPaidLeave != '') {
            totalUnPaidLeave = Double.valueOf(params.displayUnPaidLeave)
        } else {
            totalUnPaidLeave = 0
        }


//        if (totalNormal > 0 || totalWeekend > 0) {
        overtimeMaster.year = temp.get(Calendar.YEAR)
        overtimeMaster.month = selectedMonth
        overtimeMaster.totalOvertime = totalNormal
        overtimeMaster.totalOvertimeWeekend = totalWeekend
        overtimeMaster.status = '100'
        overtimeMaster.totalPaidLeave = totalPaidLeave
        overtimeMaster.totalUnPaidLeave = totalUnPaidLeave
        overtimeMaster.save(flush: true)
        currentUser.addToOvertimeMaster(overtimeMaster).save(flush: true)//        }

        for (int i = 1; i <= Integer.valueOf(totalday); i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            tempNormalOverTime = params.('inputNormalOvertime_' + i)
            startTime = params.('startTime_' + i)
            endTime = params.('endTime_' + i)
            actualTime = params.('inputActualTime_' + i)
            weekendOvertime = params.('inputWeekendOvertime_' + i)
            hoursPaidleave = params.('hoursPaidLeave_'+i)
            hoursUnPaidleave = params.('hoursUnPaidLeave_'+i)
            displayStatusCome = params.('displayStatusCome_'+i)
            def overTimeHistoryCheck = OvertimeHistory.findWhere(day: i,
                                                            month: selectedMonth,
                                                            year: temp.get(Calendar.YEAR),
                                                            overtimeMaster:overtimeMaster)
            if (startTime && endTime) {
                if (!overTimeHistoryCheck) {
                    overtimeHistoryTemp = new OvertimeHistory()
                } else {
                    overtimeHistoryTemp = overTimeHistoryCheck
                }
                overtimeHistoryTemp.userId = Integer.valueOf(currentUser.id.toString())
                overtimeHistoryTemp.year = temp.get(Calendar.YEAR)
                overtimeHistoryTemp.month = selectedMonth
                overtimeHistoryTemp.day = i
                overtimeHistoryTemp.weekday = temp.get(Calendar.DAY_OF_WEEK)
                overtimeHistoryTemp.actualTime = Double.valueOf(actualTime)
                overtimeHistoryTemp.comeTime = startTime
                overtimeHistoryTemp.leaveTime = endTime
                if (tempNormalOverTime) {
                    if (Double.valueOf(tempNormalOverTime) > 0) {
                        overtimeHistoryTemp.overTimeNormal = tempNormalOverTime
                        overtimeHistoryTemp.overTimeWeekend = weekendOvertime
                    }  else {
                        overtimeHistoryTemp.overTimeWeekend = weekendOvertime
                        overtimeHistoryTemp.overTimeNormal = tempNormalOverTime
                    }
                }
                if (weekendOvertime) {
                    if (Double.valueOf(weekendOvertime) > 0) {
                        overtimeHistoryTemp.overTimeNormal = tempNormalOverTime
                        overtimeHistoryTemp.overTimeWeekend = weekendOvertime
                    }  else {
                        overtimeHistoryTemp.overTimeWeekend = weekendOvertime
                        overtimeHistoryTemp.overTimeNormal = tempNormalOverTime
                    }
                }

                overtimeHistoryTemp.hoursPaidLeave = hoursPaidleave
                overtimeHistoryTemp.hoursUnPaidLeave = hoursUnPaidleave
                overtimeHistoryTemp.statusComeCompany = displayStatusCome
                overtimeHistoryTemp.save(flush: true)
                overtimeMaster.addToOvertimeHistory(overtimeHistoryTemp).save(flush: true)

            } else {
                if (overTimeHistoryCheck) {
                    overtimeHistoryTemp = overTimeHistoryCheck

                } else {
                    overtimeHistoryTemp = new OvertimeHistory()
                }
                overtimeHistoryTemp.userId = Integer.valueOf(currentUser.id.toString())
                overtimeHistoryTemp.year = temp.get(Calendar.YEAR)
                overtimeHistoryTemp.month = selectedMonth
                overtimeHistoryTemp.day = i
                overtimeHistoryTemp.weekday = temp.get(Calendar.DAY_OF_WEEK)
                if (overtimeHistoryTemp.weekday == 1 || overtimeHistoryTemp.weekday == 7) {
                    overtimeHistoryTemp.comeTime = 0
                    overtimeHistoryTemp.leaveTime = 0
                    overtimeHistoryTemp.actualTime = 0
                } else {
                    if (!hoursPaidleave || !hoursUnPaidleave) {
                        overtimeHistoryTemp.comeTime = '8:00'
                        overtimeHistoryTemp.leaveTime = '17:00'
                        overtimeHistoryTemp.actualTime = 8
                    } else {
                        overtimeHistoryTemp.comeTime = 0
                        overtimeHistoryTemp.leaveTime = 0
                        overtimeHistoryTemp.actualTime = 0
                    }
                }

                overtimeHistoryTemp.overTimeWeekend = 0
                overtimeHistoryTemp.overTimeNormal = 0
                overtimeHistoryTemp.hoursPaidLeave = hoursPaidleave
                overtimeHistoryTemp.hoursUnPaidLeave = hoursUnPaidleave
                overtimeHistoryTemp.save(flush: true)
                overtimeMaster.addToOvertimeHistory(overtimeHistoryTemp).save(flush: true)
            }
        }
        redirect action: 'show', model: [overtimeMaster:overtimeMaster]

    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def show() {
        def currentUser = springSecurityService.currentUser
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance();
        Calendar monthStart = GregorianCalendar.getInstance();
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH)
        int selectedYear
        if (params.selectedYear == null) {
            selectedYear = calendar.get(Calendar.YEAR)
        } else {
            selectedYear = Integer.valueOf(params.selectedYear)
        }
        def overTimeMasterList = OvertimeMaster.findAllWhere(year:selectedYear,
                secUser: currentUser ).sort(){it.id}
        def roleManager = SecRole.findWhere(authority:'ROLE_MANAGER')
        def managerList = SecUserSecRole.findAllWhere(secRole:roleManager).secUser
        render view: 'show', model: [overTimeMaster: overTimeMasterList, year: calendar.get(Calendar.YEAR), managerList:managerList]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def reloadShow() {
        def currentUser = springSecurityService.currentUser
        Calendar calendar = Calendar.getInstance()
        Calendar temp = Calendar.getInstance()
        int selectedYear
        if (params.selectedYear == null) {
            selectedYear = calendar.get(Calendar.YEAR)
        } else {
            selectedYear = Integer.valueOf(params.selectedYear)
        }

        Calendar monthStart = GregorianCalendar.getInstance()

        def overTimeMasterList = OvertimeMaster.findAllWhere(year:selectedYear,
                secUser: currentUser ).sort(){it.id}
        def roleManager = SecRole.findWhere(authority:'ROLE_MANAGER')
        def managerList = SecUserSecRole.findAllWhere(secRole:roleManager).secUser

        render(template: '/overTime/showOverTime', model: [overTimeMaster: overTimeMasterList, managerList:managerList])
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def printExel() {

        long id = Long.valueOf(params.printOverTimeMasterId)
        def overTimeMaster = OvertimeMaster.get(id)
        render view: 'confirm', model: [overTimeMaster: overTimeMaster]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def showComeLateAndTakeLeave() {
        int currentUser = springSecurityService.currentUser.id
        println "currentUser = $currentUser"
        Calendar calendar = Calendar.getInstance()
        int selectedYear
        int selectedMonth
        if (params.selectedYear == null) {
            selectedYear = calendar.get(Calendar.YEAR)
        } else {
            selectedYear = Integer.valueOf(params.selectedYear)
        }

        if (params.selectedMonth == null) {
            selectedMonth = calendar.get(Calendar.MONTH)+1
        } else {
            selectedMonth = Integer.valueOf(params.selectedMonth)
        }
        println("selectedMonth"+selectedMonth+"selectedYear"+selectedYear)
        def overTimeHistory = OvertimeHistory.createCriteria().list {
            isNotNull("statusComeCompany")
            eq("month", selectedMonth)
            eq("year", selectedYear)
            eq("userId", currentUser)
        }

        render view: 'showComeLateAndTakeLeave', model: [overTimeHistory: overTimeHistory, year: calendar.get(Calendar.YEAR), month: calendar.get(Calendar.MONTH)+1]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def reloadShowComeLateAndTakeLeave() {

        Calendar calendar = Calendar.getInstance()
        int selectedYear
        int selectedMonth
        if (params.selectedYear == null) {
            selectedYear = calendar.get(Calendar.YEAR)
        } else {
            selectedYear = Integer.valueOf(params.selectedYear)
        }

        if (params.selectedMonth == null) {
            selectedMonth = calendar.get(Calendar.MONTH)+1
        } else {
            selectedMonth = Integer.valueOf(params.selectedMonth)
        }
        def overTimeHistory = OvertimeHistory.createCriteria().list {
            isNotNull("statusComeCompany")
            eq("month",selectedMonth)
            eq("year",selectedYear)
        }
        render(template: '/overTime/tempShowComeLateAndTakeLeave', model: [overTimeHistory: overTimeHistory])
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def edit() {
        def monthCurrent = Integer.valueOf(params.month)
        def yearCurrent = Integer.valueOf(params.year)
        def currentUser = springSecurityService.currentUser
        def overTimeMaster
        def overTimeMasterList = OvertimeMaster.createCriteria().list() {
            eq("month", monthCurrent)
            eq("year", yearCurrent)
            eq("secUser",currentUser)
        }


        if (overTimeMasterList.size() > 0) {
            overTimeMaster = overTimeMasterList.get(0);
        }
        render view: 'updateOverTime', model: [overTimeMaster: overTimeMaster]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def update() {
        def monthCurrent = Integer.valueOf(params.month)
        def yearCurrent = Integer.valueOf(params.year)
        def currentUser = springSecurityService.currentUser
        double totalNormal = Double.valueOf(params.displayTotalNormal)
        double totalWeekend = Double.valueOf(params.displayTotalWeekend)
        double totalPaidLeave = Double.valueOf(params.displayPaidLeave)
        double totalUnPaidLeave = Double.valueOf(params.displayUnPaidLeave)
        def overtimeMaster = OvertimeMaster.get(params.overTimeMasterId)
        overtimeMaster.totalOvertime = totalNormal
        overtimeMaster.totalOvertimeWeekend = totalWeekend
        overtimeMaster.totalPaidLeave = totalPaidLeave
        overtimeMaster.totalUnPaidLeave = totalUnPaidLeave
        overtimeMaster.save(flush: true)

        for(overtimeHistory in overtimeMaster.overtimeHistory){
            def idx = overtimeHistory.id
            if (params.('startTime_'+idx) != '' || params.('endTime_'+idx) != '') {
                overtimeHistory.comeTime = params.('startTime_'+idx)
                overtimeHistory.leaveTime = params.('endTime_'+idx)
            } else {
                overtimeHistory.comeTime = 0
                overtimeHistory.leaveTime = 0
            }

            overtimeHistory.hoursPaidLeave = params.('hoursPaidLeave_'+idx)
            overtimeHistory.hoursUnPaidLeave = params.('hoursUnPaidLeave_'+idx)
            if (params.('inputNormalOvertime_'+idx) != '') {
                overtimeHistory.overTimeNormal = params.('inputNormalOvertime_'+idx)
            } else {
                overtimeHistory.overTimeNormal = '0'
            }

            if (params.('inputWeekendOvertime_'+idx) != '') {
                overtimeHistory.overTimeWeekend = params.('inputWeekendOvertime_'+idx)
            } else {
                overtimeHistory.overTimeWeekend = '0'
            }
            overtimeHistory.overTimeWeekend = params.('inputWeekendOvertime_'+idx)
            if(params.('inputActualTime_'+idx) != ''){
                overtimeHistory.actualTime = Double.valueOf(params.('inputActualTime_'+idx))
            } else {
                overtimeHistory.actualTime = 0
            }
            overtimeHistory.statusComeCompany = params.('displayStatusCome_'+idx)
            overtimeHistory.save(flush: true)
        }
        redirect action: 'show'
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def sendToManager() {
        def overTimeMaster = OvertimeMaster.get(params.selectedOverTimeMasterId)
        overTimeMaster.status = '200'
        overTimeMaster.approval = params.selectedManagerId
        overTimeMaster.save(flush: true)
        def currentUser = springSecurityService.currentUser
        def notification = new Notification()
        notification.message = "you have new overtime request id: "+overTimeMaster.id
        notification.targetUser = SecUser.get(params.selectedManagerId).username
        notification.status = "unread"
        notification.masterId = params.selectedOverTimeMasterId
        notification.save(flush: true)

        def notificationUnreadMessage = Notification.createCriteria().list() {
            eq("status", "unread")
            eq("targetUser", SecUser.get(params.selectedManagerId).username)
        }

        def data = [message:"you have new overtime request id: "+overTimeMaster.id, notificationUnreadMessage: notificationUnreadMessage.size(),notificationId:notification.id, overTimeMasterId: overTimeMaster.id]
        brokerMessagingTemplate.convertAndSendToUser(SecUser.get(params.selectedManagerId).username,"/queue/messagetouser",data)
        redirect action: 'show'
    }

    @Secured(['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_HR'])
    def checkOvertime() {

        def overtimeMaster = OvertimeMaster.get(params.overTimeMasterId)
        def notification = Notification.get(params.notificationId)
        notification.status = 'read'
        notification.save(flush: true)
        render view: 'checkOverTime', model: [overTimeMaster: overtimeMaster, notification: notification]
    }


    @Secured(['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_HR'])
    def acceptOverTime() {
        def overtimeMaster = OvertimeMaster.get(params.overTimeMasterId)
        overtimeMaster.status = '300'
        overtimeMaster.save(flush: true)
        brokerMessagingTemplate.convertAndSend("/topic/messagetoHR","You have new message from manager, click to unasing task for detail")
        redirect action: 'show'
    }

    @Secured(['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_HR'])
    def managerReject() {
        def overtimeMaster = OvertimeMaster.get(params.overTimeMasterId)
        overtimeMaster.status = '100'
        overtimeMaster.save(flush: true)

        def notification = new Notification()
        notification.message = "you have to check Overtime sheet again "+overtimeMaster.id
        notification.targetUser = overtimeMaster.secUser.username
        notification.status = "unread"
        notification.masterId = params.selectedOverTimeMasterId
        notification.masterId = params.overTimeMasterId
        notification.save(flush: true)

        def notificationUnreadMessage = Notification.createCriteria().list() {
            eq("status", "unread")
            eq("targetUser", overtimeMaster.secUser.username)
        }
        def data = [message:"you have to check Overtime sheet again "+overtimeMaster.id, notificationUnreadMessage: notificationUnreadMessage.size(),notificationId:notification.id, overTimeMasterId: overtimeMaster.id]
        brokerMessagingTemplate.convertAndSendToUser(notification.targetUser,"/queue/messagetoemployee",data)
        redirect action: 'show'
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE'])
    def employeeCheckAgain() {
        def overtimeMaster = OvertimeMaster.get(params.overTimeMasterId)
        def notification = Notification.get(params.notificationId)
        notification.status = 'read'
        notification.save(flush: true)
        redirect action: 'edit', model: [overTimeMaster: overtimeMaster], params: [month: overtimeMaster.month, year: overtimeMaster.year]

    }

    @Secured(['ROLE_ADMIN', 'ROLE_HR'])
    def hrCheckNoti() {
        def overTimeMasterList = OvertimeMaster.createCriteria().list() {
            eq("status", "300")
        }

        redirect action: 'show'
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    @MessageMapping("/messagetouser")
    @SendTo("/queue/messagetouser")
    protected String sendManager(def data) {
        return "${data}"
    }

    @MessageMapping("/messagetoHR")
    @SendTo("/topic/messagetoHR")
    protected String sendToHR(def data) {
        return "${data}"
    }

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    @MessageMapping("/messagetoemployee")
    @SendTo("/queue/messagetoemployee")
    protected String sendToEmployee(def data) {
        return "${data}"
    }

}
