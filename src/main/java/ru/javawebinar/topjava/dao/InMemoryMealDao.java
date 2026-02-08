package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

public class InMemoryMealDao implements MealStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealDao.class);

    private final ConcurrentHashMap<Integer, Meal> mealsMap = new ConcurrentHashMap<>();

    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public InMemoryMealDao() {
        initData();
    }

    private void initData() {
        create(new Meal(null, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500));
        create(new Meal(null, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000));
        create(new Meal(null, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500));
        create(new Meal(null, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100));
        create(new Meal(null, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(null, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500));
        create(new Meal(null, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getAll() {
        log.debug("Operation getAll in InMemoryMealDao");
        return new ArrayList<>(mealsMap.values());
    }

    @Override
    public Meal getById(int id) {
        log.debug("Operation getById in InMemoryMealDao");
        return mealsMap.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        log.debug("Operation create in InMemoryMealDao");
        meal.setId(idGenerator.incrementAndGet());
        mealsMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public synchronized Meal update(Meal updateMeal) {
        log.debug("Operation update in InMemoryMealDao");
        return mealsMap.replace(updateMeal.getId(), updateMeal);
    }

    @Override
    public void delete(int id) {
        log.debug("Operation delete in InMemoryMealDao");
        mealsMap.remove(id);
    }
}
