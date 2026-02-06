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
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private final MealDao mealDao = new MealDao();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        log.debug(action);
        if (action.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal updateMeal = new Meal(id, dateTime, description, calories);
            mealDao.update(id, updateMeal);
            response.sendRedirect("/topjava/meals?action=show");
        }
        if (action.equals("create")) {
            int id = Integer.parseInt(request.getParameter("id"));
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal saveMeal = new Meal(id, dateTime, description, calories);
            mealDao.save(saveMeal);
            response.sendRedirect("/topjava/meals?action=show");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        log.debug(action);
        RequestDispatcher dispatcher;
        int id;
        switch (action) {
            case "show":
                log.debug("Show meals list");
                request.setAttribute("mealsTo", mealDao.index());
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp");
                dispatcher.forward(request, response);
                return;
            case "update":
                log.debug("Show update form");
                id = Integer.parseInt(request.getParameter("id"));
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp");
                request.setAttribute("meal", mealDao.getMealById(id));
                dispatcher.forward(request, response);
                return;
            case "delete":
                id = Integer.parseInt(request.getParameter("id"));
                mealDao.delete(id);
                response.sendRedirect("/topjava/meals?action=show");
                return;
            case "create":
                log.debug("Show create form");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/create.jsp");
                request.setAttribute("dateTime", LocalDateTime.now());
                dispatcher.forward(request, response);
                return;
            default:
                throw new ServletException("Unknown action: " + action);
        }
    }
}
