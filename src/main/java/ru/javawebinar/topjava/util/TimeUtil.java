package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if (startTime != null && lt.isBefore(startTime)) {
            return false;
        }
        if (endTime != null && !lt.isBefore(endTime)) {
            return false;
        }
        return true;
    }
}
