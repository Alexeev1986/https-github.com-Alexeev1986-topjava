package ru.javawebinar.topjava.repository.inmemory;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

public class inMemoryTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");
}
