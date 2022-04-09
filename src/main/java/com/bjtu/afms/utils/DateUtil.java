package com.bjtu.afms.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {
    public static final long SECOND_LENGTH = 1000;
    public static final long MINUTE_LENGTH = 60000;
    public static final long HOUR_LENGTH = 3600000;
    public static final long DAY_LENGTH = 86400000;

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

    public static Date plusMillis(long millis) {
        return plusMillis(millis, null);
    }

    public static Date plusMillis(long millis, Date date) {
        if (date == null) {
            date = new Date();
        }
        long time = date.getTime();
        time += millis;
        return new Date(time);
    }

    public static int getDayDiffer(Date date1, Date date2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(date1)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(date2)).getTime();
            return (int) ((endDateTime - startDateTime) / DAY_LENGTH);
        } catch (ParseException e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
