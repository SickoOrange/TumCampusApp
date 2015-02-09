package de.tum.in.tumcampus.auxiliary;

import android.content.Context;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final long SECOND_MILLIS = 1000L;
    private static final long MINUTE_MILLIS = 60L * SECOND_MILLIS;
    private static final long HOUR_MILLIS = 60L * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24L * HOUR_MILLIS;

    private static final String formatSQL = "yyyy-MM-dd HH:mm:ss"; // 2014-06-30 16:31:57
    private static final String formatISO = "yyyy-MM-dd'T'HH:mm:ss'Z'"; // 2014-06-30T16:31:57.878Z

    public static String getRelativeTimeISO(String timestamp, Context context) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatISO, Locale.ENGLISH);
            return DateUtils.getRelativeTime(formatter.parse(timestamp), context);
        } catch (Exception e) {
            Utils.log(e);
        }
        return "";
    }

    public static String getRelativeTimeSQL(String timestamp, Context context) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.formatSQL, Locale.ENGLISH);
            return DateUtils.getRelativeTime(formatter.parse(timestamp), context);
        } catch (Exception e) {
            Utils.log(e);
        }
        return "";
    }

    private static String getRelativeTime(Date date, Context context) {

        return android.text.format.DateUtils.getRelativeDateTimeString(context, date.getTime(),
                android.text.format.DateUtils.MINUTE_IN_MILLIS, android.text.format.DateUtils.DAY_IN_MILLIS * 2L, 0).toString();
    }

    public static String getTimeOrDay(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.formatSQL, Locale.ENGLISH); // 2014-06-30 16:31:57
            return DateUtils.getTimeOrDay(formatter.parse(time));
        } catch (Exception e) {
            //Utils.log(e);
        }
        return "";
    }

    public static String getTimeOrDay(Date time) {
        long timeInMilis = time.getTime();
        long now = DateUtils.getCurrentTime().toMillis(false);

        //Catch future dates: current clock might be running behind
        if (timeInMilis > now || timeInMilis <= 0) {
            return "Gerade eben";
        }

        // TODO: localize
        final long diff = now - timeInMilis;
        if (diff < MINUTE_MILLIS) {
            return "Gerade eben";
        } else if (diff < 24 * HOUR_MILLIS) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            return formatter.format(time);
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Gestern";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            return formatter.format(time);
        }
    }

    private static Time getCurrentTime() {
        Time now = new Time();
        now.setToNow();
        return now;
    }
}