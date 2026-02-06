package ru.javawebinar.topjava.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.DataInitializer;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

public class MealDao {
    private final List<Meal> mealsList = DataInitializer.MEALS;
    int initNumber = mealsList.stream().mapToInt(Meal::getId).max().orElse(0);
    private final AtomicInteger idGenerator = new AtomicInteger(initNumber);

    public List<MealTo> index() {
        Map<LocalDate, Integer> caloriesSumByDate = mealsList.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        return mealsList.stream()
                .map(meal -> createTo(meal,
                        caloriesSumByDate.get(meal.getDate()) > DataInitializer.CALORIES_PER_DAY))
                .collect(Collectors.toList());
    }

    public Meal getMealById(int id) {
        return mealsList.stream().filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    public void save(Meal meal) {
        meal.setId(idGenerator.incrementAndGet());
        mealsList.add(meal);
    }

    public void update(int id, Meal updateMeal) {
        Meal mealToBeUpdated = getMealById(id);
        mealToBeUpdated.setDescription(updateMeal.getDescription());
        mealToBeUpdated.setCalories(updateMeal.getCalories());
    }

    public void delete(int id) {
        mealsList.removeIf(p -> p.getId() == id);
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(),
                meal.getDescription(), meal.getCalories(), excess);
    }
}
