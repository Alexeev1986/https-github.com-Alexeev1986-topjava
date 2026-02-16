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
        boolean afterStartDate = startDate == null || !date.isBefore(startDate);
        boolean beforeEndDate = endDate == null || !endDate.isBefore(date);
        boolean startTimeValid = true;
        if (startDate != null && date.equals(startDate) && startTime != null) {
            startTimeValid = !time.isBefore(startTime);
        }
        boolean endTimeValid = true;
        if (endDate != null && date.equals(endDate) && endTime != null) {
            endTimeValid = time.isBefore(endTime);
        }
        boolean globalTimeValid = true;
        if (startDate == null && endDate == null) {
            globalTimeValid = (startTime == null || !time.isBefore(startTime)) &&
                    (endTime == null || time.isBefore(endTime));
        }
        return afterStartDate && beforeEndDate && startTimeValid && endTimeValid && globalTimeValid;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
