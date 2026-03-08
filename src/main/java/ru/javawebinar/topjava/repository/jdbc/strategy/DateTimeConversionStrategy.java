package ru.javawebinar.topjava.repository.jdbc.strategy;

import java.time.LocalDateTime;

public interface DateTimeConversionStrategy {
    Object convertDataTime(LocalDateTime localDateTime);
}
