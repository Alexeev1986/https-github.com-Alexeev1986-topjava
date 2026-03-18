package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

public abstract class AbstractController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MealService service;

    protected int getUserId() {
        return authUserId();
    }

    protected int getCaloriesPerDay() {
        return authUserCaloriesPerDay();
    }

    protected List<MealTo> getAllTos() {
        int userId = getUserId();
        log.debug("gerAllTos for user {}", userId);
        return getTos(service.getAll(userId), getCaloriesPerDay());
    }

    protected List<MealTo> getFilteredTos(LocalDate startDate, LocalDate endDate,
                                          LocalTime startTime, LocalTime endTime) {
        int userId = getUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}",
                startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}
