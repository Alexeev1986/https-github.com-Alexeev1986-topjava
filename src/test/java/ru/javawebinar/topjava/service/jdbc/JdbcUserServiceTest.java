package ru.javawebinar.topjava.service.jdbc;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Override
    @Test
    public void createWithException() {
        assertThrows(DuplicateKeyException.class,
                () -> service.create(new User(null, "Duplicate", "user@yandex.ru", "password", Role.USER)));

        assertThrows(DataIntegrityViolationException.class,
                () -> service.create(new User(null, null, "mail@yandex.ru", "password", Role.USER)));

        assertThrows(DataIntegrityViolationException.class,
                () -> service.create(new User(null, "User", null, "password", Role.USER)));

        assertThrows(DataIntegrityViolationException.class,
                () -> service.create(new User(null, "User", "mail@yandex.ru", null, Role.USER)));
    }

    @Test
    public void printUserRoles() {
        log.debug("get() User roles {}", service.get(USER_ID).getRoles());
        log.debug("get() Admin roles {}", service.get(ADMIN_ID).getRoles());

        log.debug("getByEmail() User roles {}", service.getByEmail("user@yandex.ru").getRoles());
        log.debug("getByEmail() Admin roles {}", service.getByEmail("admin@gmail.com").getRoles());

        List<User> users = service.getAll();
        for (User user : users) {
            log.debug("getAll() {} roles {}", user.getName(), user.getRoles());
        }
    }
}