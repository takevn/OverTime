package book

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.time.Year

class OverTimeController {
    OverTimeService overTimeService
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp =  Calendar.getInstance();

        Calendar monthStart = GregorianCalendar.getInstance();
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);

        def data=[]
        for(int i =1; i<=dayofmonth;i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            temp.get(Calendar.DAY_OF_WEEK)
            data<<[weekday:temp.get(Calendar.DAY_OF_WEEK), day:i, month:temp.get(Calendar.MONTH), year:temp.get(Calendar.YEAR)];
        }
        render view :"index",model:[data:data,month: calendar.get(Calendar.MONTH)+1, year: calendar.get(Calendar.YEAR)]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def reLoadOverTimeSheet() {
        Calendar calendar = Calendar.getInstance()
        Calendar temp =  Calendar.getInstance();
        int month = Integer.valueOf(params.selectedMonth)-1;
        Calendar monthStart = GregorianCalendar.getInstance();
        monthStart.set(Calendar.MONTH, month);
        int dayofmonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        def data=[]
        temp.set(Calendar.MONTH,month)
        for(int i =1; i<=dayofmonth;i++) {
            temp.set(Calendar.DAY_OF_MONTH, i)
            temp.get(Calendar.DAY_OF_WEEK)
            data<<[weekday:temp.get(Calendar.DAY_OF_WEEK), day:i, month:temp.get(Calendar.MONTH), year:temp.get(Calendar.YEAR)];
        }
        render( template:'/overTime/overtimedetail',model:[data:data])
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def getOverTime(){
       def response
        // ko du paramester
        def err = " my webservice need praramester as belows : date,year,month,starttime,endtime"
        def startTime = params.startTime
        def endTime = params.endTime
        def year = params.year
        def month = params.month
        def day = params.day
        def otData = overTimeService.getOverTime(year, month, day, startTime, endTime)
        response =[status:200, total:otData.overTimes, weekend: otData.isWeekend]
        render response as JSON
    }
}
