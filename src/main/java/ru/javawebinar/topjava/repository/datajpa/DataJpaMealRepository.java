package ru.javawebinar.topjava.repository.datajpa;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private final CrudMealRepository crudRepository;
    private final CrudUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = userRepository.getReferenceById(userId);
        if (meal.isNew()) {
            meal.setUser(ref);
            return crudRepository.save(meal);
        } else {
            Meal existingMeal = crudRepository.findByIdAndUserId(meal.id(), userId);
            if (existingMeal != null) {
                existingMeal.setDateTime(meal.getDateTime());
                existingMeal.setDescription(meal.getDescription());
                existingMeal.setCalories(meal.getCalories());
                existingMeal.setUser(ref);
                return crudRepository.save(existingMeal);
            }
            throw new NotFoundException("Not found entity with id=" + meal.id());
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetween(startDateTime, endDateTime, userId);
    }
}
