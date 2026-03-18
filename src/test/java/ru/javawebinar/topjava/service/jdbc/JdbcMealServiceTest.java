package ru.javawebinar.topjava.service.jdbc;

import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import java.time.Month;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    @Override
    @Test
    public void createWithException() {
        assertThrows(DataIntegrityViolationException.class,
                () -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), null, 300), USER_ID));

        assertThrows(DataIntegrityViolationException.class,
                () -> service.create(new Meal(null, null, "Description", 300), USER_ID));
    }
}