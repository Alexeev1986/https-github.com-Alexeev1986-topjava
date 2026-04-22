package ru.javawebinar.topjava.web.user;

import javax.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {
    @GetMapping
    public String profile() {
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            return "profile";
        }
        try {
            super.update(userTo, SecurityUtil.authUserId());
            SecurityUtil.get().setTo(userTo);
            status.setComplete();
            return "redirect:/meals";
        } catch (DataIntegrityViolationException e) {
            handleDuplicateEmail(e, result);
            return "profile";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        }
        try {
            super.create(userTo);
            status.setComplete();
            return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
        } catch (DataIntegrityViolationException e) {
            handleDuplicateEmail(e, result);
            model.addAttribute("register", true);
            return "profile";
        }
    }

    private void handleDuplicateEmail(DataIntegrityViolationException e, BindingResult result) {
        Throwable rootCause = e.getRootCause();
        if (rootCause != null && rootCause.getMessage().toLowerCase().contains("users_unique_email_idx")) {
            result.rejectValue("email", "user.duplicate.email");
        } else {
            throw e;
        }
    }
}