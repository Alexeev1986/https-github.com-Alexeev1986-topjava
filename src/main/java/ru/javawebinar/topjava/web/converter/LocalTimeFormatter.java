package ru.javawebinar.topjava.web.converter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    @NonNull
    public LocalTime parse(@NonNull String text, @NonNull Locale locale) throws ParseException {
        if (text.trim().isEmpty()) {
            throw new ParseException("Time string is empty", 0);
        }
        LocalTime result = DateTimeUtil.parseLocalTime(text);
        if (result == null) {
            throw new ParseException("Unable to parse time: " + text, 0);
        }
        return result;
    }

    @Override
    @NonNull
    public String print(@NonNull LocalTime object, @NonNull Locale locale) {
        return object.toString();
    }
}
