package bip.ot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OverTime {

    public static final String PAID_LEAVE_OR_UNPAID_LEAVE = "100";
    public static final String COME_ON_TIME = "200";
    public static final String COME_LATE = "300";

    static double roundHalf(double inputNumber) {
        float roundedNumber = Math.round(inputNumber - 0.5);
        if ((inputNumber - roundedNumber) >= 0.5) {
            return (roundedNumber + 0.5);
        }
        return roundedNumber;
    }

    static Map<String, Object> getOverTimeUsingCalendar(String comeDate, String leaveDate, String hoursPaidLeave, String hoursUnPaidLeave) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar calComeDate = Calendar.getInstance();
        Calendar calLeaveDate = Calendar.getInstance();
        final double EIGHT_HOURS = 8;
        double overTimeInHours = 0;
        boolean isWeekend = false;
        double actualWokingTime = 0;
        String statusCome = "";
        Map<String, Object> resultMap = new HashMap<String, Object>() {
        };

        try {
            calComeDate.setTime(dateFormat.parse(comeDate));
            calLeaveDate.setTime(dateFormat.parse(leaveDate));

            roundComeDate(calComeDate);
            roundLeaveDate(calLeaveDate);

            actualWokingTime = calLeaveDate.get(Calendar.HOUR_OF_DAY) - calComeDate.get(Calendar.HOUR_OF_DAY);
            double diffMinute = calLeaveDate.get(Calendar.MINUTE) - calComeDate.get(Calendar.MINUTE);
            if (diffMinute == 30) {
                actualWokingTime += 0.5;
            } else if (diffMinute == -30) {
                actualWokingTime -= 0.5;
            }
            if (actualWokingTime < 0) {
                actualWokingTime = 0;
            }

            if (calLeaveDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                isWeekend = true;
            }
            // caculate overTime in hours
            // NORMAL DAY case
            if (!isWeekend) {
                overTimeInHours = actualWokingTime - EIGHT_HOURS;
            } else {// Sunday case
                overTimeInHours = actualWokingTime;
            }

            if (overTimeInHours < 0) overTimeInHours = 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        statusCome = getStatusCome(hoursPaidLeave, hoursUnPaidLeave, calComeDate, isWeekend, statusCome);

        resultMap.put("isWeekend", isWeekend);
        resultMap.put("actualWokingTime", roundHalf(actualWokingTime));
        resultMap.put("overTimeInHours", roundHalf(overTimeInHours));
        resultMap.put("statusCome", statusCome);

        return resultMap;
    }

    private static String getStatusCome(String hoursPaidLeave, String hoursUnPaidLeave, Calendar calComeDate, boolean isWeekend, String statusCome) {
        if (!isWeekend) {
            if (hoursPaidLeave != "" || hoursUnPaidLeave != "") {
                statusCome = PAID_LEAVE_OR_UNPAID_LEAVE;
            } else {
                if (calComeDate.get(Calendar.HOUR_OF_DAY) == 8) {
                    if (calComeDate.get(Calendar.MINUTE) > 0 && calComeDate.get(Calendar.MINUTE) <= 16) {
                        statusCome = COME_ON_TIME;
                    } else {
                        if (calComeDate.get(Calendar.MINUTE) > 16) {
                            statusCome = COME_LATE;
                        }
                    }
                } else if (calComeDate.get(Calendar.HOUR_OF_DAY) > 8) {
                    statusCome = COME_LATE;
                }
            }
        }
        return statusCome;
    }

    private static void roundComeDate(Calendar comeTime) {
        // if HOUR_OF_DAY then hour set to 08:00 and return
        comeTime.get(Calendar.DAY_OF_MONTH);
        if (comeTime.get(Calendar.HOUR_OF_DAY) < 8) {
            comeTime.set(Calendar.HOUR_OF_DAY, 8);
            comeTime.set(Calendar.MINUTE, 0);
            return;
        }

        if ((comeTime.get(Calendar.HOUR_OF_DAY) == 11) && (comeTime.get(Calendar.MINUTE) > 30)
                || (comeTime.get(Calendar.HOUR_OF_DAY) == 12) && (comeTime.get(Calendar.MINUTE) <= 59)) {
            comeTime.set(Calendar.HOUR_OF_DAY, 12);
            comeTime.set(Calendar.MINUTE, 0);
            return;
        } else if ((comeTime.get(Calendar.HOUR_OF_DAY) >= 13)) {
            comeTime.set(Calendar.HOUR_OF_DAY, comeTime.get(Calendar.HOUR_OF_DAY) - 1);
        }

        // minute come <= 30 then round to 30
        // minute come > 30 then round to 0 and plus come hour plus 1
        if ((comeTime.get(Calendar.MINUTE) > 0) && (comeTime.get(Calendar.MINUTE) <= 30)) {
            comeTime.set(Calendar.MINUTE, 30);
        } else if (comeTime.get(Calendar.MINUTE) > 30) {
            comeTime.set(Calendar.MINUTE, 0);
            comeTime.set(Calendar.HOUR_OF_DAY, comeTime.get(Calendar.HOUR_OF_DAY) + 1);
        }
    }

    private static void roundLeaveDate(Calendar leaveTime) {
        // if leaveTime <= 12:00 then do nothing
        if (leaveTime.get(Calendar.HOUR_OF_DAY) < 11) {

        }
        // 12:00 < if leaveTime <= 13:00 then set to 12:00
        else if (leaveTime.get(Calendar.HOUR_OF_DAY) == 12) {
            leaveTime.set(Calendar.MINUTE, 0);
        }

        // 13:00 < if leaveTime < 17:00 then set hour to (hour -1)
        else if ((13 <= leaveTime.get(Calendar.HOUR_OF_DAY)) && (leaveTime.get(Calendar.HOUR_OF_DAY) < 17)) {
            leaveTime.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY) - 1);
        }

        // 17:00 =< if leaveTime <= 17:15 then set to 17:00 after that set hour to (hour -1)
        else if ((leaveTime.get(Calendar.HOUR_OF_DAY) == 17)
                && ((0 <= leaveTime.get(Calendar.MINUTE)) && (leaveTime.get(Calendar.MINUTE) <= 15))) {
            leaveTime.set(Calendar.MINUTE, 0);
            leaveTime.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY) - 1);
        }
        // 17:15 < if leaveTime <= 19:15 then set minute to (minute - 15) after that set hour to (hour -1)
        else if (((leaveTime.get(Calendar.HOUR_OF_DAY) == 17) && (leaveTime.get(Calendar.MINUTE) > 15))
                || (leaveTime.get(Calendar.HOUR_OF_DAY) == 18)
                || ((leaveTime.get(Calendar.HOUR_OF_DAY) == 19) && (leaveTime.get(Calendar.MINUTE) <= 15))) {
            leaveTime.set(Calendar.MINUTE, leaveTime.get(Calendar.MINUTE) - 15);
            leaveTime.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY) - 1);
        }
        // 19:15 < if leaveTime <= 20:15 then set to 19:00 after that set hour to (hour -1)
        else if (((leaveTime.get(Calendar.HOUR_OF_DAY) == 19) && (leaveTime.get(Calendar.MINUTE) > 15))
                || ((leaveTime.get(Calendar.HOUR_OF_DAY) == 20) && (leaveTime.get(Calendar.MINUTE) <= 15))) {
            leaveTime.set(Calendar.HOUR_OF_DAY, 19);
            leaveTime.set(Calendar.MINUTE, 0);
            leaveTime.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY) - 1);
        }
        // 20:15 < if leaveTime then set hour to (hour - 1) and set minute to (minute - 15) after that set hour to (hour -1)
        else if (((leaveTime.get(Calendar.HOUR_OF_DAY) == 20) && (leaveTime.get(Calendar.MINUTE) > 15))
                || (leaveTime.get(Calendar.HOUR_OF_DAY) > 20)) {
            leaveTime.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY) - 1);
            leaveTime.set(Calendar.MINUTE, leaveTime.get(Calendar.MINUTE) - 15);
            leaveTime.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY) - 1);
        }
        // roundMinuteLeaveTime
        roundMinuteLeaveTime(leaveTime);
        // return
    }

    private static void roundMinuteLeaveTime(Calendar calendar) {
        if (calendar.get(Calendar.MINUTE) < 30) {
            calendar.set(Calendar.MINUTE, 0);
        } else {
            calendar.set(Calendar.MINUTE, 30);
        }
    }
}
