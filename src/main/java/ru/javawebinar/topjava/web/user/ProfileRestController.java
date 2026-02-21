package ru.javawebinar.topjava.web.user;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

@Controller
public class ProfileRestController extends AbstractUserController {
    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}