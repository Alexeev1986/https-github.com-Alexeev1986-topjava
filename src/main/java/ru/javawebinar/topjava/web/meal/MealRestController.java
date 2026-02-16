package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpenByDayAndTime;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.service.MealService;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getWithFilters(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("Filter meals from {} {} to {} {} from user {}", startDate, startTime, endDate, endTime, authUserId());
        List<Meal> allUserMeal = service.getAll(authUserId());
        Predicate<Meal> timePredicate = meal -> isBetweenHalfOpenByDayAndTime(
                meal.getDateTime(), startDate, endDate, startTime, endTime);
        return getTos(allUserMeal, authUserCaloriesPerDay(), timePredicate);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId(), id);
    }

    public void update(int id, Meal meal) {
        log.info("update {} with id={}, with user {}", meal, meal.getId(), authUserId());
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }
}