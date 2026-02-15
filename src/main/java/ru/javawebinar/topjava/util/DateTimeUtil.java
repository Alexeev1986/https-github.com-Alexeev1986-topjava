package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return (startTime == null || !lt.isBefore(startTime)) && (endTime == null || lt.isBefore(endTime));
    }

    public static boolean isBetweenHalfOpen(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return (startDate == null || !ld.isBefore(startDate)) && (endDate == null || ld.isBefore(endDate));
    }

    public static boolean isBetweenHalfOpen(LocalDateTime ldt, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (startDateTime == null || !ldt.isBefore(startDateTime)) && (endDateTime == null || ldt.isBefore(endDateTime));
    }

    public static boolean isBetweenHalfOpenByDayAndTime(LocalDateTime ltd, LocalDate startDate, LocalDate endDate,
                                                        LocalTime startTime, LocalTime endTime) {
        LocalDate mealDate = ltd.toLocalDate();
        LocalTime mealTime = ltd.toLocalTime();

        if ((startDate != null && mealDate.isBefore(startDate)) || (endDate != null && mealDate.isAfter(endDate))) return false;

        if (mealDate.equals(startDate) && startTime != null && mealTime.isBefore(startTime)) return false;

        if (mealDate.equals(endDate) && endTime != null && !mealTime.isBefore(endTime)) return false;

        if (!mealDate.equals(startDate) && !mealDate.equals(endDate)) {
            if (!DateTimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) return false;
        }

        return true;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
