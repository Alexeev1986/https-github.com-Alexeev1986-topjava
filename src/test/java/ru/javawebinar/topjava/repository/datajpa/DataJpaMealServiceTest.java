package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import static org.junit.Assert.assertNotNull;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertNotNull(meal);
        MEAL_MATCHER.assertMatch(meal, adminMeal1);
    }
}
