package ru.javawebinar.topjava.repository;

import java.util.Collection;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    Collection<Meal> getAll(int userId);
}
