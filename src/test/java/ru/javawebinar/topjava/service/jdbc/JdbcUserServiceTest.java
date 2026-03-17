package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.Profiles.JDBC;

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
}