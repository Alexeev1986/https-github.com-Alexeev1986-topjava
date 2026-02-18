package ru.javawebinar.topjava.repository.inmemory;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> mealsByUser = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.user1Meals) {
            save(1, meal);
        }
        for (Meal meal : MealsUtil.user2Meals) {
            save(2, meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.debug("Save meal {} for user {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsByUser.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
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
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        List<Meal> result = (userMeals == null) ?
                Collections.emptyList() :
                getMeals(userMeals.values(), meal -> true);
        log.debug("Get {} meals for user {}", result.size(), userId);
        return result;
    }

    @Override
    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        log.debug("Get filtered meals for user {} on startDate {} and endDate {}", userId, startDate, endDate);
        Map<Integer, Meal> userMeals = mealsByUser.get(userId);
        if (userMeals == null) {
            return Collections.emptyList();
        }
        LocalDate newEndDate = (endDate != null) ? endDate.plusDays(1) : null;
        Predicate<Meal> filter = (startDate == null && endDate == null)
                ? meal -> true
                : meal -> isBetweenHalfOpen(meal.getDate(), startDate, newEndDate);
        return getMeals(userMeals.values(), filter);
    }

    private List<Meal> getMeals(Collection<Meal> meals, Predicate<Meal> filter) {
        return meals.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

