package ru.javawebinar.topjava.service.UserServiceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.MealServiceTests.MealServiceDataJpaTest;
import ru.javawebinar.topjava.service.MealServiceTests.MealServiceJdbcTest;
import ru.javawebinar.topjava.service.MealServiceTests.MealServiceJpaTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({UserServiceJpaTest.class,
                     UserServiceJdbcTest.class,
                     UserServiceDataJpaTest.class})
public class AllUserServiceTestsSuite {
}
