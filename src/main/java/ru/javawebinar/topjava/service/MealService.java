package ru.javawebinar.topjava.service;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

@Service("mealService")
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void delete(int userId, int id) {
        checkNotFound(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return checkNotFound(repository.get(userId, id), id);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }

    public void update(int userId, Meal meal) {
        meal.setUserId(userId);
        checkNotFound(repository.save(userId, meal), meal.getId());
    }
}