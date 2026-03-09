package ru.javawebinar.topjava.repository.jdbc.strategy;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HsqldbDataTimeStrategy extends AbstractDateTimeConversionStrategy {
    @Override
    public Object doConvertDataTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
