package bip.ot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OverTime {
    double roundHalf(double inputNumber) {
        float roundedNumber = Math.round(inputNumber - 0.5);
        if ((inputNumber - roundedNumber) >= 0.5) {
            return (roundedNumber + 0.5);
        }
        return roundedNumber;
    }

    Map<String, Object> getOverTimeUsingCalendar(String comeDate, String leaveDate, String hoursPaidLeave, String hoursUnPaidLeave) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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

            calComeDate.setTime(df.parse(comeDate));
            calLeaveDate.setTime(df.parse(leaveDate));
            if (calLeaveDate.get(Calendar.DAY_OF_WEEK) == 1) {
                isWeekend = true;
            }
            if (!isWeekend) {
                if (hoursPaidLeave != "" || hoursUnPaidLeave != "") {
                    statusCome = "100";
                } else {
                    int a = calComeDate.get(Calendar.HOUR_OF_DAY);
                    int b = calComeDate.get(Calendar.MINUTE);
                    if (calComeDate.get(Calendar.HOUR_OF_DAY) == 8) {
                        if (calComeDate.get(Calendar.MINUTE) > 0 && calComeDate.get(Calendar.MINUTE) <= 16) {
                            statusCome = "200";
                        } else {
                            if (calComeDate.get(Calendar.MINUTE) > 16) {
                                statusCome = "300";
                            }
                        }
                    } else if (calComeDate.get(Calendar.HOUR_OF_DAY) > 8) {
                        statusCome = "300";
                    }
                }
            }
            roundComeDate(calComeDate);
            roundLeaveDate(calLeaveDate);

            actualWokingTime = calLeaveDate.get(Calendar.HOUR_OF_DAY) - calComeDate.get(Calendar.HOUR_OF_DAY);
            double diffMinute = calLeaveDate.get(Calendar.MINUTE) - calComeDate.get(Calendar.MINUTE);
            if (diffMinute == 30) {
                actualWokingTime += 0.5;
            } else if (diffMinute == -30) {
                actualWokingTime -= 0.5;
            }
            // leave after 13:00
            if (calLeaveDate.get(Calendar.HOUR_OF_DAY) > 13) {
                if (calComeDate.get(Calendar.HOUR_OF_DAY) < 12) {
                    actualWokingTime -= 1;
                }

            }
            // NORMAL DAY case
            if (!isWeekend) {
                overTimeInHours = actualWokingTime - EIGHT_HOURS;
                if (actualWokingTime < 8) {
                    actualWokingTime += 0.5;
                }
            } else {// Sunday case
                overTimeInHours = actualWokingTime;
            }

            if (overTimeInHours < 0) overTimeInHours = 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resultMap.put("isWeekend", isWeekend);
        resultMap.put("actualWokingTime", roundHalf(actualWokingTime));
        resultMap.put("overTimeInHours", roundHalf(overTimeInHours));
        resultMap.put("statusCome", statusCome);
        return resultMap;
    }

    private void roundComeDate(Calendar calendar) {
        // if HOUR_OF_DAY then hour set to 08:00 and return
        calendar.get(Calendar.DAY_OF_MONTH);
        if (calendar.get(Calendar.HOUR_OF_DAY) < 8) {
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            return;
        }

        if ((calendar.get(Calendar.HOUR_OF_DAY) == 11) && (calendar.get(Calendar.MINUTE) > 30)
                || (calendar.get(Calendar.HOUR_OF_DAY) == 12) && (calendar.get(Calendar.MINUTE) < 59)) {
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 0);
            return;
        }

        // minute come <= 30 then round to 30
        // minute come > 30 then round to 0 and plus come hour plus 1
        if ((calendar.get(Calendar.MINUTE) > 0) && (calendar.get(Calendar.MINUTE) <= 30)) {
            calendar.set(Calendar.MINUTE, 30);
        } else if (calendar.get(Calendar.MINUTE) > 30) {
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
        }
    }

    private void roundLeaveDate(Calendar calendar) {
        // if HourOfLeaveDate > 19:15 aproate round
        // TODO remove test code
        float c0 = calendar.get(Calendar.HOUR_OF_DAY);
        float d0 = calendar.get(Calendar.MINUTE);
        roundHourOfLeaveDate(calendar);
        // TODO remove test code
        float c = calendar.get(Calendar.HOUR_OF_DAY);
        float d = calendar.get(Calendar.MINUTE);

        // if DAY_OF_WEEK == 1(SUNDAY) and HOUR_OF_DAY = 12 then set minute = 0
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            if (calendar.get(Calendar.HOUR_OF_DAY) == 12) {
                calendar.set(Calendar.MINUTE, 0);
            }
            if (calendar.get(Calendar.MINUTE) < 30) {
                calendar.set(Calendar.MINUTE, 0);
            } else {
                calendar.set(Calendar.MINUTE, 30);
            }
            return;
        }
        roundMinuteLeaveTime(calendar);

        // TODO remove test code
        float c1 = calendar.get(Calendar.HOUR_OF_DAY);
        float d2 = calendar.get(Calendar.MINUTE);
    }

    private void roundMinuteLeaveTime(Calendar calendar) {
        // if      minute leave < 15 then round to 30 and hour -1
        // else if minute leave < 45 then round to 0
        // else    round to 30

        if (calendar.get(Calendar.MINUTE) < 15) {
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        } else if (calendar.get(Calendar.MINUTE) < 45) {
            calendar.set(Calendar.MINUTE, 0);
        } else {
            calendar.set(Calendar.MINUTE, 30);
        }
    }

    private void roundHourOfLeaveDate(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        // if hour > 20:15
        if ((calendar.get(Calendar.HOUR_OF_DAY) > 20) ||
                ((calendar.get(Calendar.HOUR_OF_DAY) == 20) && (calendar.get(Calendar.MINUTE) >= 15))) {

            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);

        } else if (((calendar.get(Calendar.HOUR_OF_DAY) == 20) && (calendar.get(Calendar.MINUTE) < 15)) ||
                (calendar.get(Calendar.HOUR_OF_DAY) == 19) && (calendar.get(Calendar.MINUTE) >= 15)) {
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 15);

        }
    }
}
