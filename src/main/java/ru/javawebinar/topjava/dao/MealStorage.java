package ru.javawebinar.topjava.dao;

import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealStorage {
    List<Meal> getAll();

    Meal getById(int id);

    Meal create(Meal meal);

    Meal update(Meal meal);

    void delete(int id);
}
