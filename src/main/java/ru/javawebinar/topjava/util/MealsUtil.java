package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;
    public static final List<Meal> meals = Arrays.asList(
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 8, 30), "Овсянка", 400, 2),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Завтрак", 450, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 12, 30), "Салат с курицей", 850, 2),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 950, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0), "Ужин", 550, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 19, 0), "Рыба на пару", 600, 2),

        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "Тосты", 420, 2),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 30), "Завтрак", 500, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 30), "Паста", 900, 2),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Обед", 1000, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 480, 1),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Йогурт с фруктами", 300, 2)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalDateTime startDate, LocalDateTime endDate) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDate, endDate));
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
