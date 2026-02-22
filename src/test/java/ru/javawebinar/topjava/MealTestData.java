package ru.javawebinar.topjava;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(START_SEQ + 3,  LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(START_SEQ + 4,  LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 5,  LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(START_SEQ + 6,  LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),  "Еда на граничное значение", 100),
            new Meal(START_SEQ + 7,  LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(START_SEQ + 8,  LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(START_SEQ + 9,  LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),

            new Meal(START_SEQ + 10, LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0),  "Завтрак", 900),
            new Meal(START_SEQ + 11, LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Обед", 1000),
            new Meal(START_SEQ + 12, LocalDateTime.of(2020, Month.JANUARY, 31, 19, 0), "Ужин", 6000)
    );

    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static final List<Meal> userMeals = meals.subList(0, 7);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 1, 0), "", 0);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 7), "Завтрак7", 777);
    }

    public static Meal getDuplicate() {
        return new Meal(null, meals.get(3).getDateTime(), "Дубликат", 200);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
