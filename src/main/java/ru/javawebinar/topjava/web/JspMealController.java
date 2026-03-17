package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping()
    public String getAll(Model model) {
        int userId = authUserId();
        log.info("get all for user {}", userId);
        model.addAttribute("meals", service.getAll(userId));
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        int userId = authUserId();
        log.info("filter for user {}", userId);
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", service.getBetweenInclusive(startDate, endDate, userId));
        return "meals";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        log.info("createForm");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        int userId = authUserId();
        log.info("update for meal {} for user {}", id, userId);
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        int userId = authUserId();
        log.info("save for user {}", userId);

        String idParam = request.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
                );
        if (idParam != null && idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            meal.setId(id);
            service.update(meal, userId);
        } else {
            service.create(meal, userId);
        }
        return "redirect:/meals";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
