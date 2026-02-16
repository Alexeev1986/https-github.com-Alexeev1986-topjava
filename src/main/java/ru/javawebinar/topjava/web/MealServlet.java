package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_FORMATTER;
import static ru.javawebinar.topjava.util.DateTimeUtil.TIME_FORMATTER;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;

public class MealServlet extends HttpServlet {
    private MealRestController controller;

    @Override
    public void init() {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        controller = ctx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : getId(request),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(getId(request), meal);
        }
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                filter(request, response);
                break;
            case "all":
            default:
                request.setAttribute("meals", controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");
        String startTimeParam = request.getParameter("startTime");
        String endTimeParam = request.getParameter("endTime");
        LocalDate startDate = parseDate(startDateParam);
        LocalDate endDate = parseDate(endDateParam);
        LocalTime startTime = parseTime(startTimeParam);
        LocalTime endTime = parseTime(endTimeParam);

        List<MealTo> meals = controller.getWithFilters(startDate, startTime, endDate, endTime);
        request.setAttribute("meals", meals);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private LocalDate parseDate(String dateStr) {
        return (dateStr != null && !dateStr.isEmpty()) ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
    }

    private LocalTime parseTime(String timeStr) {
        return (timeStr != null && !timeStr.isEmpty()) ? LocalTime.parse(timeStr, TIME_FORMATTER) : null;
    }
}
