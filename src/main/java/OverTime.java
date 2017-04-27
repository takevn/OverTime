import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OverTime {
    float roundHalf(float inputNumber) {
        float roundedNumber = Math.round(inputNumber - 0.5f);
        if ((inputNumber - roundedNumber) >= 0.5f) {
            return (roundedNumber + 0.5f);
        }
        return roundedNumber;
    }

    float getOverTimeUsingCalendar(String comeDate, String leaveDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calComeDate = Calendar.getInstance();
        Calendar calLeaveDate = Calendar.getInstance();
        final int NINE_HOURS = 9;
        final long FIFTEEN_MINUTES = 60 * 1000 * 15;
        float overTimeInHours = 0;

        try {
            calComeDate.setTime(df.parse(comeDate));
            calLeaveDate.setTime(df.parse(leaveDate));

            roundComeDate(calComeDate);
            roundLeaveDate(calLeaveDate);
            float comeDateInMiliseconds = calComeDate.getTime().getTime();
            float comdateHours = comeDateInMiliseconds / (60 * 1000 * 60) % 60;
            float leaveDarteInMiliseconds = calLeaveDate.getTime().getTime();
            float leavedateHours = leaveDarteInMiliseconds / (60 * 1000 * 60) % 60;
            float diff = leavedateHours - comdateHours;

            overTimeInHours = diff - NINE_HOURS;

            if (overTimeInHours < 0) return 0;

            return overTimeInHours;
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
        roundHourOfLeaveDate(calendar);

        // if      minute leave < 15 then round to 30 and hour -1
        // else if minute leave < 45 then round to 0
        // else    round to 30
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (calendar.get(Calendar.MINUTE) < 15) {
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        } else if (calendar.get(Calendar.MINUTE) < 45) {
            calendar.set(Calendar.MINUTE, 0);

        } else {
            calendar.set(Calendar.MINUTE, 30);
        }
    }

    private Calendar roundHourOfLeaveDate(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 20) {
            if (calendar.get(Calendar.MINUTE) < 15){
                calendar.set(Calendar.MINUTE, 15);
            }
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
            return calendar;
        }
        if ((calendar.get(Calendar.HOUR_OF_DAY) >= 19) && (calendar.get(Calendar.MINUTE) >= 15)) {
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 15);
            return calendar;
        }
        return calendar;
    }
}
