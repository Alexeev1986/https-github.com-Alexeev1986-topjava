package ru.javawebinar.topjava.repository.jdbc.strategy;

import java.time.LocalDateTime;

public class PostgresDataTimeStrategy implements DateTimeConversionStrategy {
    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
