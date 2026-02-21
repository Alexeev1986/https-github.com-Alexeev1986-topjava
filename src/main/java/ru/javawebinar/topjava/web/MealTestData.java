package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(100000, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(100001, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static final Meal update = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 7), "Завтрак7", 777);

    public static final Meal duplicate = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Дубликат", 200);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 1, 0), "", 0);
    }

    public static Meal getUpdated() {
        return update;
    }

    public static Meal getDuplicate() {
        return duplicate;
    }
}
