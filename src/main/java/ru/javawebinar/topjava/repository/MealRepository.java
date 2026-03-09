package ru.javawebinar.topjava.repository;

import java.time.LocalDateTime;
import java.util.List;
import ru.javawebinar.topjava.model.Meal;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    default Meal getWithUser(int id, int userId) {
        throw new UnsupportedOperationException("getWithUser is not supported by this implementation");
    }
}
