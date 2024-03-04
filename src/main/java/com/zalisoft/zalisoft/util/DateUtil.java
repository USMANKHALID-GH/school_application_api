package com.zalisoft.zalisoft.util;



import com.zalisoft.zalisoft.enums.ResponseMessageEnum;
import com.zalisoft.zalisoft.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


@Slf4j
public class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static final DateFormat defaultFormatter = new SimpleDateFormat(DEFAULT_PATTERN);

    public static Date createExpireDate() {
        return DateUtils.addMinutes(new Date(), 5);
    }

    public static void checkExpireDate(Date expireDate, ResponseMessageEnum responseMessageEnum) {
        if (new Date().compareTo(expireDate) > 0) {
            throw new BusinessException(responseMessageEnum);
        }
    }

    public  static boolean isNewYear(){
            LocalDate currentDate = LocalDate.now();
            LocalDate firstDayOfYear = LocalDate.of(currentDate.getYear(), 1, 1);
            return currentDate.isEqual(firstDayOfYear);

    }

    public static String defaultFormat(Date date) {
        return defaultFormatter.format(date);
    }

    public static String today() {
        return defaultFormatter.format(new Date());
    }

    public static Date beginOfDay() {
        return DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
    }

    public static Date beginOfTomorrow() {
        return DateUtils.addDays(beginOfDay(), 1);
    }

    public static Date beginOfMonth() {
        return DateUtils.truncate(new Date(), Calendar.MONTH);
    }

    public static LocalDateTime beginOfMonthAsLocalDateTime() {
        return convertDateToLocalDateTime(DateUtils.truncate(new Date(), Calendar.MONTH));
    }

    public static Date beginOfLastMonth() {
        return DateUtils.addMonths(beginOfMonth(), -1);
    }

    public static Date beginOfNextMonth() {
        return DateUtils.addMonths(beginOfMonth(), 1);
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return new java.sql.Timestamp(date.getTime()).toLocalDateTime();
    }

    public  static LocalDateTime  getLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
