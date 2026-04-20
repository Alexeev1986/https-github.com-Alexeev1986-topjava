package ru.javawebinar.topjava.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.ValidationUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);

        if (e instanceof DataIntegrityViolationException) {
            String rootMessage = rootCause.getMessage();
            if (rootMessage != null && rootMessage.toLowerCase().contains("users_unique_email_idx")) {
                log.warn("Duplicate email attempt: {}", rootMessage);
                ModelAndView mav = new ModelAndView("profile");
                if (req.getRequestURL().toString().contains("/register")) {
                    mav.addObject("register", true);
                }
                UserTo userTo = new UserTo();

                userTo.setName(req.getParameter("name"));
                userTo.setEmail(req.getParameter("email"));
                userTo.setPassword(req.getParameter("password"));
                String caloriesParam = req.getParameter("caloriesPerDay");
                userTo.setCaloriesPerDay(caloriesParam != null &&
                        !caloriesParam.isBlank() ? Integer.parseInt(caloriesParam) : 0);

                mav.addObject("userTo", userTo);
                mav.addObject("duplicateEmailError", "Пользователь с таким email уже существует");
                return mav;
            }
        }

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ModelAndView mav = new ModelAndView("exception",
                Map.of("exception", rootCause, "message", rootCause.toString(), "status", httpStatus));
        mav.setStatus(httpStatus);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;
    }
}
