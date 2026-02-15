package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
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
