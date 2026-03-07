package ru.javawebinar.topjava.service.user_service_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.user_service_tests.datajpa.UserServiceDataJpaTest;
import ru.javawebinar.topjava.service.user_service_tests.jdbc.UserServiceJdbcTest;
import ru.javawebinar.topjava.service.user_service_tests.jpa.UserServiceJpaTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserServiceJpaTest.class,
                     UserServiceJdbcTest.class,
                     UserServiceDataJpaTest.class})
public class AllUserServiceTestsSuite {
}
