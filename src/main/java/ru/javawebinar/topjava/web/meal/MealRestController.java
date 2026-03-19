package ru.javawebinar.topjava.web.meal;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

@Controller
public class MealRestController extends AbstractController {
    public Meal get(int id) {
        return super.get(id);
    }

    public void delete(int id) {
        super.delete(id);
    }

    public List<MealTo> getAll() {
        return getAllTos();
    }

    public Meal create(Meal meal) {
        checkIsNew(meal);
        return super.create(meal);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        super.update(meal, id);
    }

    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return getFilteredTos(startDate, endDate, startTime, endTime);
    }
}