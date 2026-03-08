package ru.javawebinar.topjava.repository.jdbc.strategy;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HsqldbDataTimeStrategy implements DateTimeConversionStrategy {
    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
