package ru.javawebinar.topjava.repository.jdbc.strategy;

import java.time.LocalDateTime;

public abstract class AbstractDateTimeConversionStrategy {
    public final Object convertDataTime(LocalDateTime localDateTime) {
        return doConvertDataTime(localDateTime);
    }

    protected abstract Object doConvertDataTime(LocalDateTime localDateTime);
}
