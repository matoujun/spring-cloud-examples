package org.matoujun.cloud.common;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author matoujun

 */
public final class TimeUtil {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern
            ("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMAT_YYYYMMDD = DateTimeFormat.forPattern
            ("yyyyMMdd");

    public static final Integer DATE_TIME_FORMAT_LENGTH = 19;

    public static Date parseDayTime(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        if (time.length() > DATE_TIME_FORMAT_LENGTH) {
            time = time.substring(0, DATE_TIME_FORMAT_LENGTH);
        }
        DateTime dateTime = DateTime.parse(time, DATE_TIME_FORMAT);
        return dateTime.toDate();
    }

    public static String nowTime() {
        return formatNow(DATE_TIME_FORMAT);
    }

    public static String formatNow(DateTimeFormatter formatter) {
        DateTime dateTime = new DateTime();
        return dateTime.toString(formatter);
    }

    public static String toStringSecond(Date date) {
        return new DateTime(date).toString(DATE_TIME_FORMAT);
    }
}
