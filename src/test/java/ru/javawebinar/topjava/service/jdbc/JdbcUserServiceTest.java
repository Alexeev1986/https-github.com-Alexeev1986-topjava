package ru.javawebinar.topjava.service.jdbc;

import static ru.javawebinar.topjava.Profiles.JDBC;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;


@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}