package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private static int authUserId = 1;

    public static int authUserId() {
        return authUserId;
    }

    public static void setAuthUserId(int userId) {
        SecurityUtil.authUserId = userId;
        log.info("Authorization from user {}", authUserId);
    }

    public static int authUserCaloriesPerDay() {
        return authUserId == 1 ? 2000 : 1800;
    }
}