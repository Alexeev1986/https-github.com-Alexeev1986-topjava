package ru.javawebinar.topjava.service.MealServiceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MealServiceJpaTest.class,
                     MealServiceJdbcTest.class,
                     MealServiceDataJpaTest.class})
public class AllMealServiceTestsSuite {
}
