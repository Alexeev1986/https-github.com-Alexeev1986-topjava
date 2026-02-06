package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    public static final int CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS;

    static {
        MEALS = new ArrayList<>();
        MEALS.add(new Meal(1, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500));
        MEALS.add(new Meal(2, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000));
        MEALS.add(new Meal(3, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500));
        MEALS.add(new Meal(4, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100));
        MEALS.add(new Meal(5, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000));
        MEALS.add(new Meal(6, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500));
        MEALS.add(new Meal(7, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410));
    }

    public DataInitializer() {
    }
}
