package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

public class MainTestData {
    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;
    public static final int GUEST_ID = 100002;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");
}
