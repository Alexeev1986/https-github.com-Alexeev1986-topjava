package ru.javawebinar.topjava.model;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javawebinar.topjava.util.ExcessFlag;

public class UserMealExcessDays {
    private final List<UserMealWithExcess> mealDay;
    private final int caloriesPerDay;

    private final Map<LocalDate, ExcessFlag> excessFlag;

    private final Map<LocalDate, Integer> dailyCalories;

    public UserMealExcessDays(List<UserMeal> meals, int caloriesPerDay) {
        this.mealDay = new ArrayList<>();
        this.caloriesPerDay = caloriesPerDay;
        excessFlag = new HashMap<>();
        dailyCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            addMeal(meal);
        }
    }

    public void addMeal(UserMeal userMeal) {
        boolean isContained = false;
        for (UserMealWithExcess meal : mealDay) {
            if (meal.getDateTime().equals(userMeal.getDateTime())) {
                isContained = true;
                break;
            }
        }
        if (!isContained) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            dailyCalories.put(mealDate, dailyCalories.getOrDefault(mealDate, 0) + userMeal.getCalories());
            excessFlag.computeIfAbsent(mealDate, date -> new ExcessFlag());
            excessFlag.get(mealDate).setValue(dailyCalories.get(mealDate) > caloriesPerDay);
            mealDay.add(new UserMealWithExcess(userMeal.getDateTime(),
                    userMeal.getDescription(), userMeal.getCalories(), excessFlag.get(mealDate)));
        } else {
            System.out.println("Ошибка! в этот день и время пользователь уже принимал пищу.");
        }
    }

    public List<UserMealWithExcess> getMealDay() {
        return Collections.unmodifiableList(mealDay);
    }

    public List<UserMealWithExcess> getUserMealWithExcessOnTimeInterval(LocalTime startTime,
                                                                        LocalTime endTime) {
        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMealWithExcess meal : mealDay) {
            if (isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(meal);
            }
        }
        return result;
    }
}
