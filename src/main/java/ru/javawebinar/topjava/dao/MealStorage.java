package ru.javawebinar.topjava.dao;

import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealStorage {
    List<Meal> getAll();

    Meal getById(int id);

    void create(Meal meal);

    void update(int id, Meal meal);

    void delete(int id);
}
