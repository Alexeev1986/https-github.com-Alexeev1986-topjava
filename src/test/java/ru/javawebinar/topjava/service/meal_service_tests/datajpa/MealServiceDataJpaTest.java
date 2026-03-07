package ru.javawebinar.topjava.service.meal_service_tests.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.meal_service_tests.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class MealServiceDataJpaTest extends AbstractMealServiceTest {
}
