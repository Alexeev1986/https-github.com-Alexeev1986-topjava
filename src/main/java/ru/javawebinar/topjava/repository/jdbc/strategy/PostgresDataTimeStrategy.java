package ru.javawebinar.topjava.repository.jdbc.strategy;

import java.time.LocalDateTime;

public class PostgresDataTimeStrategy extends AbstractDateTimeConversionStrategy {
    @Override
    public Object doConvertDataTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
