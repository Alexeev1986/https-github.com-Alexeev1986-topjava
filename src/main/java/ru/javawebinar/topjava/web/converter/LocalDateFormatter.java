package ru.javawebinar.topjava.web.converter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Nullable
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (text.trim().isEmpty()) {
            return null;
        }
        return DateTimeUtil.parseLocalDate(text);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object != null ? object.toString() : "";
    }
}
