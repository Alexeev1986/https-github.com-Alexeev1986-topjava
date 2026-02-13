package ru.javawebinar.topjava.repository.inmemory;

import java.util.Collection;
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
import ru.javawebinar.topjava.util.MealsUtil;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            mealsMap.put(meal.getId(), meal);
            log.info("Create {} for user {}", meal, userId);
            return meal;
        }

        Meal oldMeal = mealsMap.get(meal.getId());

        if (oldMeal == null || oldMeal.getUserId() != userId) {
            log.warn("Cannot update meal {} - not found or belongs to user {}, not {}",
                    meal.getId(),
                    oldMeal != null ? oldMeal.getUserId() : "null",
                    userId);
            return null;
        }
        meal.setUserId(userId);
        mealsMap.put(meal.getId(), meal);
        log.info("Update meal {} for user {}", meal, userId);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal meal = mealsMap.get(id);
        if (meal == null || meal.getUserId() != userId) {
            log.warn("Meal {} not found to user {}", id, userId);
            return false;
        }
        mealsMap.remove(id);
        log.info("Delete meal {} for user {}", id, userId);
        return true;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = mealsMap.get(id);
        if (meal == null || meal.getUserId() != userId) {
            log.warn("Meal {} not found to user {}", id, userId);
            return null;
        }
        log.info("Get meal {} for user {}", id, userId);
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> result = mealsMap.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        log.info("Get {} meals for user {}", result.size(), userId);
        return result;
    }
}

