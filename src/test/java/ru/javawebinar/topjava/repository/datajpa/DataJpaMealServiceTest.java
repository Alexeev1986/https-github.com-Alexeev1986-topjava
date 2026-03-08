package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void getWithUser() {
        Meal meal = mealService.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);

        assertNotNull(meal);
        assertNotNull(meal.getUser());
        assertEquals(ADMIN_MEAL_ID, meal.id());
        assertEquals(ADMIN_ID, meal.getUser().id());

        MEAL_MATCHER.assertMatch(meal, adminMeal1);
    }
}
