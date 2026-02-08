package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.dao.MealStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealStorage mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new InMemoryMealDao();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        log.debug(action);
        switch (action) {
            case "update":
                log.debug("Updated meal");
                Meal updateMeal = createdMealByreByRequest(request);
                mealDao.update(updateMeal);
                response.sendRedirect(request.getContextPath() + "/meals?action=show");
                return;
            case "create":
                log.debug("Created new meal");
                Meal saveMeal = createdMealByreByRequest(request);
                mealDao.create(saveMeal);
                response.sendRedirect(request.getContextPath() + "/meals?action=show");
                return;
            default:
                throw new ServletException("Unknown doPost action: " + action);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        log.debug(action);
        RequestDispatcher dispatcher;
        int id;
        if (action == null) {
            action = "show";
        }
        try {
            switch (action) {
                case "show":
                    log.debug("Show meals list");
                    request.setAttribute("mealsTo", MealsUtil.getMealToList(mealDao.getAll()));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp");
                    dispatcher.forward(request, response);
                    return;
                case "update":
                    log.debug("Show update form");
                    id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("meal", mealDao.getById(id));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mealForm.jsp");
                    dispatcher.forward(request, response);
                    return;
                case "delete":
                    log.debug("Deleted meal");
                    id = Integer.parseInt(request.getParameter("id"));
                    mealDao.delete(id);
                    response.sendRedirect(request.getContextPath() + "/meals?action=show");
                    return;
                case "create":
                    log.debug("Show create form");
                    request.setAttribute("meal", new Meal());
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mealForm.jsp");
                    dispatcher.forward(request, response);
                    return;
                default:
                    throw new ServletException("Unknown doGet action: " + action);
            }
        } catch (ServletException e) {
            String message = "Error processing action: " + action;
            log.error(message, e);
            response.sendRedirect(request.getContextPath() + "/meals?action=show");
        }
    }

    private Meal createdMealByreByRequest(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        Integer id = (idParam != null && !idParam.trim().isEmpty()) ? Integer.parseInt(idParam) : null;
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        return new Meal(id, dateTime, description, calories);
    }
}
