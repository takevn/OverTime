package bip.ot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OverTime {
    double roundHalf(double inputNumber) {
        float roundedNumber = Math.round(inputNumber - 0.5);
        if ((inputNumber - roundedNumber) >= 0.5) {
            return (roundedNumber + 0.5);
        }
        return roundedNumber;
    }

    double getOverTimeUsingCalendar(String year, String month, String day, String comeTime, String leaveTime) throws Exception {
        StringBuilder comeDate = new StringBuilder();
        StringBuilder leaveDate = new StringBuilder();

        comeDate.append(year);
        leaveDate.append(year);

        if (month.length() == 1) month = String.format("0%s", month);
        comeDate.append("-").append(month);
        leaveDate.append("-").append(month);

        if (day.length() == 1) day = String.format("0%s", day);
        comeDate.append("-").append(day);
        leaveDate.append("-").append(day);

        comeDate.append(" ").append(comeTime);
        leaveDate.append(" ").append(leaveTime);

        return getOverTimeUsingCalendar(comeDate.toString(), leaveDate.toString());
    }

    double getOverTimeUsingCalendar(String comeDate, String leaveDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        DateFormat df = new SimpleDateFormat("HH:mm");
        Calendar calComeDate = Calendar.getInstance();
        Calendar calLeaveDate = Calendar.getInstance();
        final double NINE_HOURS = 9;
        final double FIFTEEN_MINUTES = 60 * 1000 * 15;
        double overTimeInHours = 0;

        try {
            calComeDate.setTime(df.parse(comeDate));
            calLeaveDate.setTime(df.parse(leaveDate));

            roundComeDate(calComeDate);
            roundLeaveDate(calLeaveDate);

            float a = calComeDate.get(Calendar.HOUR_OF_DAY);
            float b = calComeDate.get(Calendar.MINUTE);
            float c = calLeaveDate.get(Calendar.HOUR_OF_DAY);
            float d = calLeaveDate.get(Calendar.MINUTE);

            // TODO this code will be remove
            float comeDateInMiliseconds = calComeDate.getTime().getTime();
            float comdateHours = comeDateInMiliseconds / (60 * 1000 * 60) % 60;
            float leaveDarteInMiliseconds = calLeaveDate.getTime().getTime();
            float leavedateHours = leaveDarteInMiliseconds / (60 * 1000 * 60) % 60;
            float diff = leavedateHours - comdateHours;

            double diffHour = calLeaveDate.get(Calendar.HOUR_OF_DAY) - calComeDate.get(Calendar.HOUR_OF_DAY);
            double diffMinute = calLeaveDate.get(Calendar.MINUTE) - calComeDate.get(Calendar.MINUTE);
            if (diffMinute == 30) {
                diffHour += 0.5;
            } else if (diffMinute == -30) {
                diffHour -= 0.5;
            }

            // NORMAL DAY case
            if (calLeaveDate.get(Calendar.DAY_OF_WEEK) != 1) {
                overTimeInHours = diffHour - NINE_HOURS;
            } else {// Sunday case
                // leave after 13:00
                if (calLeaveDate.get(Calendar.HOUR_OF_DAY) > 13) {
                    overTimeInHours = diffHour - 1;
                }
            }

            if (overTimeInHours < 0) return 0;

            return roundHalf(overTimeInHours);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return overTimeInHours;
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
                calendar.set(calendar.get(Calendar.MINUTE), 0);
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
