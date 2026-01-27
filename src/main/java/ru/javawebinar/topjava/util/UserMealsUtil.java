package ru.javawebinar.topjava.util;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealExcessDays;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCyclesOption2(meals,
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreamsOption2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreamsOption2V2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCyclesOption1(List<UserMeal> meals,
                                                                   LocalTime startTime,
                                                                   LocalTime endTime,
                                                                   int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = getDailyCaloriesByCycles(meals);
        List<UserMealWithExcess> filteredItems = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (isBetweenHalfOpen(mealTime, startTime, endTime)) {
                boolean excess = dailyCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                filteredItems.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        excess
                ));
            }
        }
        return filteredItems;
    }

    private static Map<LocalDate, Integer> getDailyCaloriesByCycles(List<UserMeal> meals) {
        Map<LocalDate, Integer> result = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            result.put(date, result.getOrDefault(date, 0) + meal.getCalories());
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreamsOption1(List<UserMeal> meals,
                                                                    LocalTime startTime,
                                                                    LocalTime endTime,
                                                                    int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = getDailyCaloriesByStream(meals);
        return meals.stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        dailyCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                )
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getDailyCaloriesByStream(List<UserMeal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(
                        meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)
                ));
    }

    public static List<UserMealWithExcess> filteredByCyclesOption2(List<UserMeal> meals,
                                                                   LocalTime startTime,
                                                                   LocalTime endTime,
                                                                   int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = new HashMap<>();
        Map<LocalDate, ExcessFlag> excessFlags = new HashMap<>();
        List<UserMealWithExcess> filteredItems = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            dailyCalories.put(mealDate, dailyCalories.getOrDefault(mealDate, 0) + meal.getCalories());
            excessFlags.computeIfAbsent(mealDate, date -> new ExcessFlag());
            excessFlags.get(mealDate).setValue(dailyCalories.get(mealDate) > caloriesPerDay);
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (isBetweenHalfOpen(mealTime, startTime, endTime)) {
                filteredItems.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        excessFlags.get(mealDate)
                ));
            }
        }
        return filteredItems;
    }

    private static List<UserMealWithExcess> filteredByStreamsOption2(List<UserMeal> meals,
                                                                     LocalTime startTime,
                                                                     LocalTime endTime,
                                                                     int caloriesPerDay) {
        return new UserMealExcessDays(meals, caloriesPerDay).getMealDay().stream()
                .filter(meal -> isBetweenHalfOpen(
                        meal.getDateTime().toLocalTime(),
                        startTime,
                        endTime
                ))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExcess> filteredByStreamsOption2V2(List<UserMeal> meals,
                                                                     LocalTime startTime,
                                                                     LocalTime endTime,
                                                                     int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(
                        meal -> meal.getDateTime().toLocalDate(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                dayMeals -> {
                                    int totalCalories = dayMeals.stream()
                                            .mapToInt(UserMeal::getCalories)
                                            .sum();
                                    ExcessFlag excessFlag = new ExcessFlag();
                                    excessFlag.setValue(totalCalories > caloriesPerDay);
                                    return dayMeals.stream()
                                            .filter(meal -> isBetweenHalfOpen(
                                                    meal.getDateTime().toLocalTime(), startTime,endTime))
                                            .map(meal -> new UserMealWithExcess(
                                                    meal.getDateTime(),
                                                    meal.getDescription(),
                                                    meal.getCalories(),
                                                    excessFlag
                                            ))
                                            .collect(Collectors.toList());
                                }
                        )
                ))
                .values().stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(UserMealWithExcess::getDateTime))
                .collect(Collectors.toList());
    }
}

