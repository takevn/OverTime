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
        '11:59'  | 12   | 0
        '11:60'  | 12   | 0
        // After 12:00
        '12:00'  | 12   | 0
        '12:16'  | 12   | 0
        '12:30'  | 12   | 0
        '12:59'  | 12   | 0
        '12:60'  | 12   | 0
    }

    @Unroll
    def "roundLeaveDate: #leaveDate => #hour hour and #minute minute"() {
        given:
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calLeaveDate = Calendar.getInstance();
        calLeaveDate.setTime(df.parse(leaveDate));
        overTime.roundLeaveDate(calLeaveDate)

        expect:
        calLeaveDate.get(Calendar.HOUR_OF_DAY) == hour
        calLeaveDate.get(Calendar.MINUTE) == minute

        where:
        leaveDate          | hour | minute
        // leaveTime <= 12:00
        '2017-06-01 11:00' | 11   | 00
        '2017-06-01 12:00' | 12   | 00
        // 12:00 < if leaveTime <= 13:00 then set to 12:00
        '2017-06-01 12:01' | 12   | 00
        '2017-06-01 12:15' | 12   | 00
        '2017-06-01 12:30' | 12   | 00
        '2017-06-01 12:59' | 12   | 00
        '2017-06-01 13:00' | 12   | 00
        // 13:00 < if leaveTime < 17:00 then set hour to (hour -1)
        '2017-06-01 13:01' | 12   | 00
        '2017-06-01 14:15' | 13   | 00
        '2017-06-01 15:30' | 14   | 30
        '2017-06-01 16:45' | 15   | 30
        '2017-06-01 17:00' | 16   | 00
        // 17:00 < if leaveTime <= 17:15 then set to 17:00 after that set hour to (hour -1)
        '2017-06-01 17:01' | 16   | 00
        '2017-06-01 17:15' | 16   | 00
        // 17:15 < if leaveTime <= 19:15 then set minute to (minute - 15) after that set hour to (hour -1)
        '2017-06-01 17:16' | 16   | 00
        '2017-06-01 17:35' | 16   | 00
        '2017-06-01 17:45' | 16   | 30
        '2017-06-01 17:46' | 16   | 30
        '2017-06-01 18:15' | 17   | 00
        '2017-06-01 19:14' | 17   | 30
        '2017-06-01 19:15' | 18   | 00
        // 19:15 < if leaveTime <= 20:15 then set to 19:00 after that set hour to (hour -1)
        '2017-06-01 19:16' | 18   | 00
        '2017-06-01 19:46' | 18   | 00
        '2017-06-01 20:15' | 18   | 00
        // 20:15 < if leaveTime then set hour to (hour - 1) and set minute to (minute - 15) after that set hour to (hour -1)
        '2017-06-01 20:16' | 18   | 00
        '2017-06-01 20:44' | 18   | 00
        '2017-06-01 20:45' | 18   | 30
        '2017-06-01 22:00' | 19   | 30
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

        where:
        comeDate           | leaveDate          | isWeekend | actualWorkingTime | ot
        // come before 08:00
        "2010-04-30 6:00"  | "2010-04-30 22:00" | false     | 11.5              | 3.5
        "2010-04-30 6:00"  | "2010-04-30 22:00" | false     | 11.5              | 3.5

        "2010-04-30 7:00"  | "2010-04-30 16:30" | false     | 7.5               | 0
        "2010-04-30 7:00"  | "2010-04-30 17:00" | false     | 8                 | 0
        "2010-04-30 7:00"  | "2010-04-30 17:45" | false     | 8.5               | 0.5
        "2010-04-30 7:00"  | "2010-04-30 19:15" | false     | 10                | 2
        "2010-04-30 7:00"  | "2010-04-30 20:00" | false     | 10                | 2
        "2010-04-30 7:00"  | "2010-04-30 20:15" | false     | 10                | 2
        "2010-04-30 7:00"  | "2010-04-30 23:15" | false     | 13                | 5

        "2010-04-30 7:59"  | "2010-04-30 16:30" | false     | 7.5               | 0
        "2010-04-30 7:59"  | "2010-04-30 17:00" | false     | 8                 | 0
        "2010-04-30 7:59"  | "2010-04-30 17:45" | false     | 8.5               | 0.5
        "2010-04-30 7:59"  | "2010-04-30 19:15" | false     | 10                | 2
        "2010-04-30 7:59"  | "2010-04-30 20:00" | false     | 10                | 2
        "2010-04-30 7:59"  | "2010-04-30 20:15" | false     | 10                | 2
        "2010-04-30 7:59"  | "2010-04-30 23:15" | false     | 13                | 5

        "2010-04-29 8:00"  | "2010-04-29 22:00" | false     | 11.5              | 3.5
        // come after 12h
        "2010-04-30 12:00" | "2010-04-30 16:30" | false     | 3.5               | 0
        "2010-04-30 12:00" | "2010-04-30 17:00" | false     | 4                 | 0
        "2010-04-30 12:00" | "2010-04-30 17:45" | false     | 4.5               | 0
        "2010-04-30 12:00" | "2010-04-30 19:15" | false     | 6                 | 0
        "2010-04-30 12:00" | "2010-04-30 20:00" | false     | 6                 | 0
        "2010-04-30 12:00" | "2010-04-30 20:15" | false     | 6                 | 0
        "2010-04-30 12:00" | "2010-04-30 23:15" | false     | 9                 | 1
        "2010-04-30 13:00" | "2010-04-30 17:35" | false     | 4                 | 0

        // sunday case
        "2017-04-30 8:00"  | "2017-04-30 17:35" | true      | 8                 | 8
        "2017-04-30 8:15"  | "2017-04-30 16:30" | true      | 7                 | 7
        "2017-04-30 8:30"  | "2017-04-30 16:30" | true      | 7                 | 7
        "2017-04-30 8:31"  | "2017-04-30 16:30" | true      | 6.5               | 6.5
        "2017-04-30 7:00"  | "2017-04-30 16:30" | true      | 7.5               | 7.5
        "2017-04-30 7:00"  | "2017-04-30 17:00" | true      | 8                 | 8
        "2017-04-30 7:00"  | "2017-04-30 17:45" | true      | 8.5               | 8.5
        "2017-04-30 7:00"  | "2017-04-30 19:15" | true      | 10                | 10
        "2017-04-30 7:00"  | "2017-04-30 20:00" | true      | 10                | 10
        "2017-04-30 7:00"  | "2017-04-30 20:15" | true      | 10                | 10
        "2017-04-30 7:00"  | "2017-04-30 23:15" | true      | 13                | 13
        "2017-04-30 13:00"  | "2017-04-30 17:35" | true      | 4                 | 4
    }

}
