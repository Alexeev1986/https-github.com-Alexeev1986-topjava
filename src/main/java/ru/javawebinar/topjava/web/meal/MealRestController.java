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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        return getTos(getAllByUser(), authUserCaloriesPerDay());
    }

    public List<MealTo> getWithFilters(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> allUserMeal = getAllByUser();
        Predicate<Meal> timePredicate = meal -> isBetweenHalfOpenByDayAndTime(
                meal.getDateTime(), startDate, endDate, startTime, endTime);
        return getTos(allUserMeal, authUserCaloriesPerDay(), timePredicate);
    }

    public Meal get(int id) {
        return service.get(authUserId(), id);
    }

    public Meal create(Meal meal) {
        checkIsNew(meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        service.delete(authUserId(), id);
    }

    public void update(int id, Meal meal) {
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }

    private List<Meal> getAllByUser() {
        return service.getAll(authUserId());
    }
}