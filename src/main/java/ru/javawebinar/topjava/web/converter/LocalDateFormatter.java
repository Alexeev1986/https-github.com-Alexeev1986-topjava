package ru.javawebinar.topjava.web.converter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    @NonNull
    public LocalDate parse(@NonNull String text, @NonNull Locale locale) throws ParseException {
        if (text.trim().isEmpty()) {
            throw new ParseException("Date string is empty", 0);
        }
        return Objects.requireNonNull(DateTimeUtil.parseLocalDate(text));
    }

    @Override
    @NonNull
    public String print(@NonNull LocalDate object, @NonNull Locale locale) {
        return object.toString();
    }
}
