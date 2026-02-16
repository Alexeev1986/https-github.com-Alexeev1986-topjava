package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static boolean isBetweenHalfOpenByDayAndTime(LocalDateTime ltd, LocalDate startDate, LocalDate endDate,
                                                        LocalTime startTime, LocalTime endTime) {

        LocalDate date = ltd.toLocalDate();
        LocalTime time = ltd.toLocalTime();
        LocalDate adjustedEndDate = endDate != null ? endDate.plusDays(1) : null;
        boolean dateInInterval = isBetweenHalfOpen(date, startDate, adjustedEndDate);
        boolean timeInInterval = isBetweenHalfOpen(time, startTime, endTime);
        return dateInInterval && timeInInterval;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
