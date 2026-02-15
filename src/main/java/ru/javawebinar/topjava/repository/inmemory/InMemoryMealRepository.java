package ru.javawebinar.topjava.repository.inmemory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
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
        log.debug("Save meal {} for user {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            mealsByUser.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
            log.debug("Create {} for user {}", meal, userId);
            return meal;
        }
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.trace("Cannot update meal {} - user {} has no meals", meal.getId(), userId);
            return null;
        }
        Meal oldMeal = userMeals.get(meal.getId());
        if (oldMeal == null) {
            log.trace("Meal {} not found for user {}", meal.getId(), userId);
            return null;
        }
        meal.setUserId(userId);
        if (userMeals.replace(meal.getId(), oldMeal, meal)) {
            log.debug("Update meal {} for user {}", meal.getId(), userId);
            return meal;
        } else {
            log.warn("Failed to update meal {} for user {} ", meal, userId);
            return null;
        }
    }

    @Override
    public boolean delete(int userId, int id) {
        log.debug("Delete meal {} for user {}", id, userId);
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.trace("Cannot delete meal {} - user {} has no meals", id, userId);
            return false;
        }
        Meal meal = userMeals.get(id);
        if (meal == null) {
            log.trace("Meal {} not found to user {}", id, userId);
            return false;
        }
        boolean remove = userMeals.remove(id, meal);
        if (remove) {
            log.info("Delete meal {} for user {}", id, userId);
        } else {
            log.warn("Meal {} already deleted", id);
        }
        return remove;
    }

    @Override
    public Meal get(int userId, int id) {
        log.debug("Get meal {} for user {}", id, userId);
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.trace("No meals found for user {} ", userId);
            return null;
        }
        Meal meal = userMeals.get(id);
        if (meal == null) {
            log.trace("Meal {} not found for user {}", id, userId);
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.debug("Get all meals for user {}", userId);
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        List<Meal> result = (userMeals == null) ?
                Collections.emptyList() :
                userMeals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
        log.debug("Get {} meals for user {}", result.size(), userId);
        return result;
    }

    @Override
    public List<Meal> getBetweenHalfOpenByDayAndTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId) {
        log.debug("Get meals for user {} between dates: {} {} and {} {}", userId, startDate, startTime, endDate, endTime);
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            log.trace("No meals found for user {}", userId);
            return Collections.emptyList();
        }
        return userMeals.values().stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpenByDayAndTime(
                        meal.getDateTime(), startDate, endDate, startTime, endTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

