package ru.javawebinar.topjava.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

public class MealDao implements MealStorage {
    public static final int CALORIES_PER_DAY = 2000;
    private static final Logger log = LoggerFactory.getLogger(MealDao.class);
    private static List<Meal> meals = new ArrayList<>();

    static {
        meals.add(new Meal(1, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(2, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(3, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(4, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(5, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(6, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(7, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410));
    }

    private final List<Meal> mealsList = new CopyOnWriteArrayList<>(meals);
    int initNumber = mealsList.stream().mapToInt(Meal::getId).max().orElse(0);
    private final AtomicInteger idGenerator = new AtomicInteger(initNumber);

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsList);
    }

    @Override
    public Meal getById(int id) {
        return mealsList.stream().filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    @Override
    public synchronized void create(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(idGenerator.incrementAndGet());
            mealsList.add(meal);
        } else {
            update(meal.getId(), meal);
        }
    }

    @Override
    public synchronized void update(int id, Meal updateMeal) {
        Meal mealToBeUpdated = getById(id);
        if (mealToBeUpdated == null) {
            String message = "Meal with id " + id + " not found";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        mealToBeUpdated.setDateTime(updateMeal.getDateTime());
        mealToBeUpdated.setDescription(updateMeal.getDescription());
        mealToBeUpdated.setCalories(updateMeal.getCalories());
    }

    @Override
    public synchronized void delete(int id) {
        mealsList.removeIf(p -> p.getId() == id);
    }
}
