package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

public abstract class AbstractController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MealService mealService;

    @Autowired
    protected UserService userService;

    protected int getUserId() {
        return authUserId();
    }

    protected int getId(HttpServletRequest request) {
        String paramId = request.getParameter("userId");
        return Integer.parseInt(paramId);
    }
}
