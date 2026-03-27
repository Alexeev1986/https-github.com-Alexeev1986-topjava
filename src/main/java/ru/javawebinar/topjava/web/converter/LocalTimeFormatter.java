package ru.javawebinar.topjava.web.converter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;
import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return DateTimeUtil.parseLocalTime(text);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object != null ? object.toString() : "";
    }
}
