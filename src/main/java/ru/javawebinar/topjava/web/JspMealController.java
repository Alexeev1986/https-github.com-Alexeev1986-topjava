package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractController {
    @GetMapping()
    public String getAll(Model model) {
        int userId = getUserId();
        log.info("get all for user {}", userId);
        model.addAttribute("meals", mealService.getAllMealTo(userId));
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(HttpServletRequest request, Model model) {
        int userId = getUserId();
        log.info("filter for user {}", userId);
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", mealService.getBetweenInclusive(startDate, endDate, userId));
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
        int userId = getUserId();
        log.info("update for meal {} for user {}", id, userId);
        Meal meal = mealService.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        int userId = getUserId();
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
            mealService.update(meal, userId);
        } else {
            mealService.create(meal, userId);
        }
        return "redirect:/meals";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = getUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }
}
