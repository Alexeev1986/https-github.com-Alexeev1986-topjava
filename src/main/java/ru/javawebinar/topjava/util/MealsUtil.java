package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public class MealsUtil {
    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime,
                                                 LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = getCaloriesSumByDate(meals);

        Predicate<Meal> timeFilter = (startTime == null || endTime == null)
                ? meal -> true
                : meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime);

        return meals.stream()

                .filter(timeFilter)
                .map(meal -> createToMealTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesSumByDate(List<Meal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
    }

    private static MealTo createToMealTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(),
                meal.getDescription(), meal.getCalories(), excess);
    }
}
