import spock.lang.Specification
import spock.lang.Unroll

import java.text.DateFormat
import java.text.SimpleDateFormat

class OverTimeTest extends Specification {
    OverTime overTime

    void testGetHoursBetween() {
    }

    def setup() {
        overTime = new OverTime()
    }

    @Unroll
    def "roundHalf: #inputNumber = #result"() {

        expect:
        overTime.roundHalf(inputNumber) == result

        where:
        inputNumber | result
        9.0f        | 9
        9.4f        | 9
        9.5f        | 9.5
        9.6f        | 9.5
        9.9f        | 9.5
        10f         | 10
    }

    @Unroll
    def "roundComeDate: #comeDate => #hour hour and #minute minute"() {
        given:
        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar calComeDate = Calendar.getInstance();
        calComeDate.setTime(df.parse(comeDate));
        overTime.roundComeDate(calComeDate)

        expect:
        calComeDate.get(Calendar.HOUR_OF_DAY) == hour
        calComeDate.get(Calendar.MINUTE) == minute

        where:
        comeDate | hour | minute
        // Before 08:00
        "07:00"  | 8    | 0
        '07:16'  | 8    | 0
        '07:30'  | 8    | 0
        '07:59'  | 8    | 0
        '07:60'  | 8    | 0
        // After 08:00
        '08:00'  | 8    | 0
        '08:16'  | 8    | 30
        '08:30'  | 8    | 30
        '08:59'  | 9    | 0
        '08:60'  | 9    | 0
        // After 11:00
        '11:00'  | 11   | 0
        '11:16'  | 11   | 30
        '11:30'  | 11   | 30
        '11:59'  | 13   | 0
        '11:60'  | 13   | 0
        // After 12:00
        '12:00'  | 13   | 0
        '12:16'  | 13   | 0
        '12:30'  | 13   | 0
        '12:59'  | 13   | 0
        '12:60'  | 13   | 0
    }

    @Unroll
    def "roundLeaveDate: #leaveDate => #hour hour and #minute minute"() {
        given:
        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar calLeaveDate = Calendar.getInstance();
        calLeaveDate.setTime(df.parse(leaveDate));
        overTime.roundLeaveDate(calLeaveDate)

        expect:
        calLeaveDate.get(Calendar.HOUR_OF_DAY) == hour
        calLeaveDate.get(Calendar.MINUTE) == minute

        where:
        leaveDate | hour | minute
        // Before 18:00
        "17:00"   | 16   | 30
        '17:14'   | 16   | 30
        '17:15'   | 17   | 0
        '17:16'   | 17   | 0
        '17:44'   | 17   | 0
        '17:45'   | 17   | 30
        '17:46'   | 17   | 30
        '17:59'   | 17   | 30
        // After 18:00
        "18:00"   | 17   | 30
        '18:14'   | 17   | 30
        '18:15'   | 18   | 0
        '18:16'   | 18   | 0
        '18:44'   | 18   | 0
        '18:45'   | 18   | 30
        '18:46'   | 18   | 30
        '18:59'   | 18   | 30
        // After 19:00
        "19:00"   | 18   | 30
        '19:14'   | 18   | 30
        '19:15'   | 19   | 0
        '19:16'   | 19   | 0
        '19:44'   | 19   | 0
        '19:45'   | 19   | 0
        '19:46'   | 19   | 0
        '19:59'   | 19   | 0
        // After 12:00
        "20:00"   | 19   | 0
        '20:14'   | 19   | 0
        '20:15'   | 19   | 0
        '20:16'   | 19   | 0
        '20:44'   | 19   | 0
        '20:45'   | 19   | 30
        '20:46'   | 19   | 30
        '20:59'   | 19   | 30
    }

    @Unroll
    def "getOverTimeUsingCalendar(#comeDate, #leaveDate): #result hour"() {
        expect:
        overTime.getOverTimeUsingCalendar(comeDate, leaveDate) == result

        where:
        comeDate          | leaveDate          | result
//        '2010-04-30 07:00' | '2010-04-30 18:00' | 0.5
        "7:00" | "16:30" | 0
        "7:00" | "17:00" | 0
        "7:00" | "17:45" | 0.5
        "7:00" | "19:15" | 2
        "7:00" | "20:00" | 2
        "7:00" | "20:15" | 2
        "7:00" | "23:15" | 5
        "7:59" | "16:30" | 0
        "7:59" | "17:00" | 0
        "7:59" | "17:45" | 0.5
        "7:59" | "19:15" | 2
        "7:59" | "20:00" | 2
        "7:59" | "20:15" | 2
        "7:59" | "23:15" | 5
        "8:00" | "16:30" | 0
        "8:00" | "17:00" | 0
        "8:00" | "17:45" | 0.5
        "8:00" | "19:15" | 2
        "8:00" | "20:00" | 2
        "8:00" | "20:15" | 2
        "8:00" | "23:15" | 5
        "8:01" | "16:30" | 0
        "8:01" | "17:00" | 0
        "8:01" | "17:45" | 0
        "8:01" | "19:15" | 1.5
        "8:01" | "20:00" | 1.5
        "8:01" | "20:15" | 1.5
        "8:01" | "23:15" | 4.5
        "7:59" | "16:30" | 0
        "7:59" | "17:00" | 0
        "7:59" | "17:45" | 0.5
        "7:59" | "19:15" | 2
        "7:59" | "20:00" | 2
        "7:59" | "20:15" | 2
        "7:59" | "23:15" | 5
        "8:00" | "16:30" | 0
        "8:00" | "17:00" | 0
        "8:00" | "17:45" | 0.5
        "8:00" | "19:15" | 2
        "8:00" | "20:00" | 2
        "8:00" | "20:15" | 2
        "8:00" | "23:15" | 5
        "8:01" | "16:30" | 0
        "8:01" | "17:00" | 0
        "8:01" | "17:45" | 0
        "8:01" | "19:15" | 1.5
        "8:01" | "20:00" | 1.5
        "8:01" | "20:15" | 1.5
        "8:01" | "23:15" | 4.5
        "7:59" | "16:30" | 0
        "7:59" | "17:00" | 0
        "7:59" | "17:45" | 0.5
        "7:59" | "19:15" | 2
        "7:59" | "20:00" | 2
        "7:59" | "20:15" | 2
        "7:59" | "23:15" | 5
        "8:00" | "16:30" | 0
        "8:00" | "17:00" | 0
        "8:00" | "17:45" | 0.5
        "8:00" | "19:15" | 2
        "8:00" | "20:00" | 2
        "8:00" | "20:15" | 2
        "8:00" | "23:15" | 5
        "8:01" | "16:30" | 0
        "8:01" | "17:00" | 0
        "8:01" | "17:45" | 0
        "8:01" | "19:15" | 1.5
        "8:01" | "20:00" | 1.5
        "8:01" | "20:15" | 1.5
        "8:01" | "23:15" | 4.5
        "7:59" | "16:30" | 0
        "7:59" | "17:00" | 0
        "7:59" | "17:45" | 0.5
        "7:59" | "19:15" | 2
        "7:59" | "20:00" | 2
        "7:59" | "20:15" | 2
        "7:59" | "23:15" | 5
        "8:00" | "16:30" | 0
        "8:00" | "17:00" | 0
        "8:00" | "17:45" | 0.5
        "8:00" | "19:15" | 2
        "8:00" | "20:00" | 2
        "8:00" | "20:15" | 2
        "8:00" | "23:15" | 5
        "8:01" | "16:30" | 0
        "8:01" | "17:00" | 0
        "8:01" | "17:45" | 0
        "8:01" | "19:15" | 1.5
        "8:01" | "20:00" | 1.5
        "8:01" | "20:15" | 1.5
        "8:01" | "23:15" | 4.5
    }

}
