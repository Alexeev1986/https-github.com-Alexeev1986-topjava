package ru.javawebinar.topjava.service.UserServiceTests;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTests.AbstractMealServiceTest;

@ActiveProfiles({"jdbc"})
public class UserServiceJdbcTest extends AbstractUserServiceTest {
}
