package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> user1Meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 9, 0), "Завтрак", 450),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 950),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0), "Ужин", 550),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 30), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 1500)
    );
    public static final List<Meal> user2Meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 8, 30), "Овсянка", 400),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 12, 30), "Салат с курицей", 850),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 19, 0), "Рыба на пару", 600),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 9, 0), "Тосты", 420),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 30), "Паста", 900),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Йогурт с фруктами", 300)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        return filterByPredicate(meals, caloriesPerDay, filter);
    }

    public static Map<LocalDate, Integer> getCaloriesSumByDate(Collection<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesSumByDate(meals);

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .sorted(Comparator.comparing(MealTo::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
