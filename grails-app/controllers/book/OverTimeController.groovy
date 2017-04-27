package book

import grails.plugin.springsecurity.annotation.Secured

class OverTimeController {
    def overTimeService
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index() {
        render "Be Strong Together"
    }

    def getOverTime(){
        def startTime = params.startTime
        def endTime = params.endTime
        def total = overTimeService.getOverTimeUsingCalendar(startTime, endTime)

        render total
    }
}
