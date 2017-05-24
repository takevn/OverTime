package overtime

import org.example.SecUser

class OvertimeMaster {
    static hasMany = [overtimeHistory: OvertimeHistory]
    static belongsTo = [secUser: SecUser]
    Date dateCreated
    Date lastUpdated
    int year
    int month
    int day
    double totalOvertime
    double totalOvertimeWeekend
    double totalPaidLeave
    double totalUnPaidLeave
    String approval
    String hrcheck
    String status
    static constraints = {
        status nullable:true
        approval nullable:true
        hrcheck nullable:true
    }
}
