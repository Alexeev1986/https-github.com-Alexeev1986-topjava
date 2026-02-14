package ru.javawebinar.topjava.repository.inmemory;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> mealsByUser = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            mealsByUser.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
            log.info("Create {} for user {}", meal, userId);
            return meal;
        }
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.warn("Cannot update meal {} - user {} has no meals", meal.getId(), userId);
            return null;
        }
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (oldMeal.getUserId() != userId) {
                log.warn("Cannot update meal {} belongs to user {}, not {}", id, oldMeal.getUserId(), userId);
                return oldMeal;
            }
            meal.setUserId(userId);
            return meal;
        });
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.warn("Cannot delete meal {} - user {} has no meals", id, userId);
            return false;
        }
        Meal meal = userMeals.get(id);
        if (meal == null || meal.getUserId() != userId) {
            log.warn("Meal {} not found to user {}", id, userId);
            return false;
        }
        boolean remove = userMeals.remove(id, meal);
        if (remove) {
            log.info("Delete meal {} for user {}", id, userId);
        } else {
            log.warn("Meal {} alredy deleted", id);
        }
        if (userMeals.isEmpty()) {
            mealsByUser.remove(userId);
        }
        return remove;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.warn("Meal {} not found for user {} ", id, userId);
            throw new NotFoundException("Meal" + id + " not found for user " + userId);
        }
        Meal meal = userMeals.get(id);
        if (meal == null || meal.getUserId() != userId) {
            log.warn("Meal {} not found for user {}", id, userId);
            throw new NotFoundException("Meal " + id + " not found for user " + userId);
        }
        log.info("Get meal {} for user {}", id, userId);
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        List<Meal> result;
        if (userMeals == null) {
            result = List.of();
        } else {
            result = userMeals.values().stream()
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        log.info("Get {} meals for user {}", result.size(), userId);
        return result;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);

        return userMeals.values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

