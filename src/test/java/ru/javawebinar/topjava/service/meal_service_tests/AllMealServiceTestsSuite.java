package ru.javawebinar.topjava.service.meal_service_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.meal_service_tests.datajpa.MealServiceDataJpaTest;
import ru.javawebinar.topjava.service.meal_service_tests.jdbc.MealServiceJdbcTest;
import ru.javawebinar.topjava.service.meal_service_tests.jpa.MealServiceJpaTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({MealServiceJpaTest.class,
                     MealServiceJdbcTest.class,
                     MealServiceDataJpaTest.class})
public class AllMealServiceTestsSuite {
}
