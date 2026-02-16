package ru.javawebinar.topjava.service;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;

@Service()
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

    public List<MealTo> getBetweenHalfOpenByDayAndTime(LocalDate startDate, LocalTime startTime,
                                                       LocalDate endDate, LocalTime endTime,
                                                       int userId, int caloriesPerDay) {
        return repository.getBetweenHalfOpenByDayAndTime(startDate, startTime, endDate, endTime, userId, caloriesPerDay);
    }

    public void update(int userId, Meal meal) {
        checkNotFound(repository.save(userId, meal), meal.getId());
    }
}