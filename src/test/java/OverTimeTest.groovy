import bip.ot.OverTime
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
        9.0         | 9
        9.4         | 9
        9.5         | 9.5
        9.6         | 9.5
        9.9         | 9.5
        10          | 10
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
        "16:00"   | 15   | 30
        '16:14'   | 15   | 30
        '16:15'   | 16   | 0
        '16:16'   | 16   | 0
        '16:44'   | 16   | 0
        '16:45'   | 16   | 30
        '16:46'   | 16   | 30
        '16:59'   | 16   | 30
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
    def "getOverTimeUsingCalendar(#comeDate, #leaveDate): #actualWorkingTime hour, #ot hour, isWeekend: #isWeekend"() {
        given:
        Map<String, Object> resultMap = new HashMap<String, Object>() {}
        resultMap = overTime.getOverTimeUsingCalendar(comeDate, leaveDate, "0", "0")

        expect:
        resultMap.get("isWeekend") == isWeekend
        resultMap.get("actualWokingTime") == actualWorkingTime
        resultMap.get("overTimeInHours") == ot
//        resultMap.get("statusCome") == status

        where:
        comeDate           | leaveDate          | isWeekend | actualWorkingTime | ot
        '2010-04-30 07:00' | '2010-04-30 18:00' | false     | 8.5               | 0.5
        "2010-04-30 7:00"  | "2010-04-30 16:30" | false     | 7.5               | 0
        "2010-04-30 7:00"  | "2010-04-30 17:00" | false     | 8                 | 0
        "2010-04-30 7:00"  | "2010-04-30 17:45" | false     | 8.5               | 0.5
        "2010-04-30 7:00"  | "2010-04-30 19:15" | false     | 10                | 2
        "2010-04-30 7:00"  | "2010-04-30 20:00" | false     | 10                | 2
        "2010-04-30 7:00"  | "2010-04-30 20:15" | false     | 10                | 2
        "2010-04-30 7:00"  | "2010-04-30 23:15" | false     | 13                | 5
        "2010-04-30 6:00"  | "2010-04-30 22:00" | false     | 11.5              | 3.5
        "2010-04-29 7:00"  | "2010-04-29 22:00" | false     | 11.5              | 3.5
        "2010-04-29 8:00"  | "2010-04-29 22:00" | false     | 11.5              | 3.5
        "2010-04-30 6:00"  | "2010-04-30 22:00" | false     | 11.5              | 3.5
        "2010-04-30 7:59"  | "2010-04-30 16:30" | false     | 7.5               | 0
        "2010-04-30 7:59"  | "2010-04-30 17:00" | false     | 8                 | 0
        "2010-04-30 7:59"  | "2010-04-30 17:45" | false     | 8.5               | 0.5
        "2010-04-30 7:59"  | "2010-04-30 19:15" | false     | 10                | 2
        "2010-04-30 7:59"  | "2010-04-30 20:00" | false     | 10                | 2
        "2010-04-30 7:59"  | "2010-04-30 20:15" | false     | 10                | 2
        "2010-04-30 7:59"  | "2010-04-30 23:15" | false     | 13                | 5
        "2010-04-30 8:00"  | "2010-04-30 16:30" | false     | 7.5               | 0
        "2010-04-30 8:00"  | "2010-04-30 17:00" | false     | 8                 | 0
        "2010-04-30 8:00"  | "2010-04-30 17:45" | false     | 8.5               | 0.5
        "2010-04-30 8:00"  | "2010-04-30 19:15" | false     | 10                | 2
        "2010-04-30 8:00"  | "2010-04-30 20:00" | false     | 10                | 2
        "2010-04-30 8:00"  | "2010-04-30 20:15" | false     | 10                | 2
        "2010-04-30 8:00"  | "2010-04-30 23:15" | false     | 13                | 5
        "2010-04-30 8:01"  | "2010-04-30 16:30" | false     | 7                 | 0
        "2010-04-30 8:01"  | "2010-04-30 17:00" | false     | 7.5               | 0
        "2010-04-30 8:01"  | "2010-04-30 17:45" | false     | 8                 | 0
        "2010-04-30 8:01"  | "2010-04-30 19:15" | false     | 9.5               | 1.5
        "2010-04-30 8:01"  | "2010-04-30 20:00" | false     | 9.5               | 1.5
        "2010-04-30 8:01"  | "2010-04-30 20:15" | false     | 9.5               | 1.5
        "2010-04-30 8:01"  | "2010-04-30 23:15" | false     | 12.5              | 4.5
        "2010-04-30 12:05" | "2010-04-30 17:14" | false     | 4                 | 0

        // sunday case
        "2017-04-30 7:00"  | "2017-04-30 16:30" | true      | 7.5               | 7.5
        "2017-04-30 7:00"  | "2017-04-30 17:00" | true      | 8                 | 8
        "2017-04-30 7:00"  | "2017-04-30 17:45" | true      | 8.5               | 8.5
        "2017-04-30 7:00"  | "2017-04-30 19:15" | true      | 10                | 10
        "2017-04-30 7:00"  | "2017-04-30 20:00" | true      | 10                | 10
        "2017-04-30 7:00"  | "2017-04-30 20:15" | true      | 10                | 10
        "2017-04-30 7:00"  | "2017-04-30 23:15" | true      | 13                | 13
    }

}
