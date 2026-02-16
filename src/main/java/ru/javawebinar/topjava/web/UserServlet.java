package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("select")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            SecurityUtil.setAuthUserId(userId);
            log.info("Switched to user: {}", userId);
            response.sendRedirect("index.html");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        int user = SecurityUtil.authUserId();
        request.setAttribute("userName", (user == 1) ? "User" : "Admin");
        request.setAttribute("selectedUserId", user);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
