package com.test.kafkaTest.kafkaConf;



import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static LocalDateTime timestampReversDate(Long date) {
        Instant instant = Instant.ofEpochMilli(date);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date parse(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return localDateTimeToDate(LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    //string->Date
    public static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(dateStr);
        return localDateToDate(localDate);
    }

    //java.time.LocalDate --> java.util.Date
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null)
            return null;

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    //Date -> String
    public static String DateToString(Date date) {
        if(date == null)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    //LocalDateTime->String
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        if(localDateTime == null)
            return null;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(localDateTime);
    }
    //LocalDateTime->String
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        if(localDateTime == null)
            return null;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(localDateTime);
    }

    //String->LocalDateTime
    public static LocalDateTime stringToLocalDateTime(String time){
        if(StringUtils.isEmpty(time))
            return null;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time,df);
    }
    //String->LocalDateTime
    public static LocalDateTime stringToLocalDateTime(String time, String format){
        if(StringUtils.isEmpty(time))
            return null;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time,df);
    }

}
