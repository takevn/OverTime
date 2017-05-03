package overtime

class OvertimeHistory {
    static belongsTo = [overtimeMaster: OvertimeMaster]
    int userId
    int year
    int month
    int day
    String comeTime
    String leaveTime

    static constraints = {
        userId blank: false
        year blank: false
        month blank: false
        day blank: false
        comeTime blank: false
        leaveTime blank: false
    }
}
