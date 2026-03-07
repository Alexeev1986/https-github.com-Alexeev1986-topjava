package ru.javawebinar.topjava.service.user_service_tests.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.user_service_tests.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class UserServiceJdbcTest extends AbstractUserServiceTest {
}
