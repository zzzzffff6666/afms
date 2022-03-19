package com.bjtu.afms.utils;

import java.util.Date;

public class DateUtil {
    private static final long SECOND_LENGTH = 1000;
    private static final long MINUTE_LENGTH = 60000;
    private static final long HOUR_LENGTH = 3600000;
    private static final long DAY_LENGTH = 86400000;

    public static Date plusSeconds(int second) {
        return plusSeconds(second, null);
    }

    public static Date plusSeconds(int second, Date date) {
        if (date == null) {
            date = new Date();
        }
        long time = date.getTime();
        time += second * SECOND_LENGTH;
        return new Date(time);
    }

    public static Date plusMinutes(int minute) {
        return plusMinutes(minute, null);
    }

    public static Date plusMinutes(int minute, Date date) {
        if (date == null) {
            date = new Date();
        }
        long time = date.getTime();
        time += minute * MINUTE_LENGTH;
        return new Date(time);
    }

    public static Date plusHours(int hour) {
        return plusHours(hour, null);
    }

    public static Date plusHours(int hour, Date date) {
        if (date == null) {
            date = new Date();
        }
        long time = date.getTime();
        time += hour * HOUR_LENGTH;
        return new Date(time);
    }

    public static Date plusDays(int day) {
        return plusDays(day, null);
    }

    public static Date plusDays(int day, Date date) {
        if (date == null) {
            date = new Date();
        }
        long time = date.getTime();
        time += day * DAY_LENGTH;
        return new Date(time);
    }

    public static long plusMillis(long millis) {
        return plusMillis(millis, null);
    }

    public static long plusMillis(long millis, Date date) {
        if (date == null) {
            date = new Date();
        }
        long time = date.getTime();
        time += millis;
        return time;
    }
}
