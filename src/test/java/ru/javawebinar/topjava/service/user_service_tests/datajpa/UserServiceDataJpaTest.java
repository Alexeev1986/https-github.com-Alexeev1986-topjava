package ru.javawebinar.topjava.service.user_service_tests.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.user_service_tests.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJpaTest extends AbstractUserServiceTest {
}
