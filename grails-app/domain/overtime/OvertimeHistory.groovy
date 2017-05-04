package overtime

class OvertimeHistory {
    static belongsTo = [overtimeMaster: OvertimeMaster]
    Date dateCreated
    Date lastUpdated
    int userId
    int year
    int month
    int day
    int weekday
    String comeTime
    String leaveTime
    String overTimeNormal
    String overTimeWeekend
    Double actualTime

    static constraints = {
        userId blank: false
        year blank: false
        month blank: false
        day blank: false
        comeTime blank: false
        leaveTime blank: false
        overTimeNormal nullable: true
        overTimeWeekend nullable: true
    }
}
