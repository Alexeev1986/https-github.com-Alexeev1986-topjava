package ru.javawebinar.topjava.to;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import ru.javawebinar.topjava.model.Meal;

public class MealsFilterResult {
    private final List<Meal> meals;
    private final Map<LocalDate, Integer> excessFlags;

    public MealsFilterResult(List<Meal> meals, Map<LocalDate, Integer> excessFlags) {
        this.meals = meals;
        this.excessFlags = excessFlags;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public Map<LocalDate, Integer> getExcessFlags() {
        return excessFlags;
    }
}
