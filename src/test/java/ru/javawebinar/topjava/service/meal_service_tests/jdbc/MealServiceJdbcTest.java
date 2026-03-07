package ru.javawebinar.topjava.service.meal_service_tests.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.meal_service_tests.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class MealServiceJdbcTest extends AbstractMealServiceTest {
}
