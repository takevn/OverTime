package book

import grails.plugin.springsecurity.annotation.Secured
import org.example.SecUser
import org.springframework.messaging.simp.SimpMessagingTemplate

import overtime.Employee
import overtime.OvertimeMaster

class HumanManageController {
    def springSecurityService
    OverTimeService overTimeService
    SimpMessagingTemplate brokerMessagingTemplate

    @Secured(['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_HR'])
    def index() {
        def currentUser = springSecurityService.currentUser
        def totalPaidLeave

        Calendar calendar = Calendar.getInstance()
        def employee = Employee.findWhere("secUser": currentUser)
        if (employee == null) {
            employee = new Employee()
            println "employee"+employee
            render view: "index", model: [employee: employee]
            return
        }
        def remainPaidLeaveTemp = employee.remainPaidLeave

        def totalPaidLeaveList = OvertimeMaster.createCriteria().list {
            eq ('year',calendar.get(Calendar.YEAR))
            eq ('secUser',currentUser)
            projections {
                sum('totalPaidLeave')
            }
        }

        double dayTotalPaidLeave = Double.valueOf(employee.totalPaidLeave)
        if (totalPaidLeaveList.get(0) > 0) {
            totalPaidLeave = totalPaidLeaveList.get(0)
            remainPaidLeaveTemp = dayTotalPaidLeave - totalPaidLeave/8
        }

        employee.remainPaidLeave = remainPaidLeaveTemp
        employee.save(flush: true)

        render view: "index", model: [employee: employee]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_HR'])
    def employeeManage() {
        Calendar calendar = Calendar.getInstance()
        def employeeListTemp = Employee.findAll(sort:"id")
        def totalLeaveList = OvertimeMaster.createCriteria().list {
            eq ('year',calendar.get(Calendar.YEAR))
            projections {
                sum('totalPaidLeave')
                groupProperty('secUser')
            }
            order("secUser", "asc")
        }
        println("totalPaidLeaveList"+totalLeaveList)
        int idx = 0
        for(employee in employeeListTemp) {
            def paidLeave = totalLeaveList.get(idx).getAt(0)
            println("paidLeave"+paidLeave)
            double totalPaidLeave = Double.valueOf(employee.totalPaidLeave)
            employee.remainPaidLeave = totalPaidLeave - paidLeave/8
            employee.save(flush: true)
            idx ++
        }
        def employeeList = Employee.findAll(sort:"id")
        render view: "employeeManage", model: [employeeList: employeeList]
    }

}
