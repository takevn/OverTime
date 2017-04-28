package overtime

class OvertimeHistory {
    int userId
    int year
    int month
    int day
    int comeHour
    int comeMinute
    int leaveHour
    int leaveMinute

    static constraints = {
        userId blank: false
        year blank: false
        month blank: false
        day blank: false
        comeHour blank: false
        comeMinute blank: false
        leaveHour blank: false
        leaveMinute blank: false
//        if (comeHour > leaveHour) return ['comeHourBiggerThanleaveHour']
        comeHour validator: {val, obj, errors->
            if(obj.leaveHour < val) errors.rejectValue('comeTime', 'noMatch')
        }
    }
}
