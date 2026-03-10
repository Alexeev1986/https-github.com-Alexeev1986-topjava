package ru.javawebinar.topjava.repository.datajpa;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User testUser = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(testUser, user);
        MEAL_MATCHER.assertMatch(testUser.getMeals(), meals);
    }

    @Test
    public void getWithMealsUserWithNotMeals() {
        User testUser = service.getWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(testUser, guest);
        MEAL_MATCHER.assertMatch(testUser.getMeals(), guest.getMeals());
    }

    @Test
    public void getWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithMeals(123));
    }
}
