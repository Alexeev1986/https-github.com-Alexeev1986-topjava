package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


public class MealTestData {
    public static final int MEAL_ID = START_SEQ;
    public static final int USER_ID = START_SEQ;
    public static final List<Meal> meals = Arrays.asList(
            new Meal(100000, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(100001, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );


    public static final Meal meal = new Meal(100000,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static final Meal update = new Meal(100000,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 7), "Завтрак7", 777);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 1, 0), "", 0);
    }

    public static Meal getUpdated() {
        return update;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}
