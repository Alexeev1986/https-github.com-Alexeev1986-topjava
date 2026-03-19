package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

public abstract class AbstractController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MealService service;

    public int getUserId() {
        return authUserId();
    }

    public int getCaloriesPerDay() {
        return authUserCaloriesPerDay();
    }

    public List<MealTo> getAllTos() {
        int userId = getUserId();
        log.debug("gerAllTos for user {}", userId);
        return getTos(service.getAll(userId), getCaloriesPerDay());
    }

    public List<MealTo> getFilteredTos(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                       @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = getUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}",
                startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id) {
        int userId = getUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = getUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public Meal create(Meal meal) {
        checkIsNew(meal);
        int userId = getUserId();
        log.info("create meal {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        int userId = getUserId();
        log.info("update meal {} for user {}", meal, userId);
        service.update(meal, userId);
    }
}
