package ru.javawebinar.topjava.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getById() {
        User user = userService.getById(USER_ID);
        assertNotNull(user);
    }

    @Test
    public void getWithMeals() {
        User user = userService.getWithMeals(USER_ID);
        assertNotNull(user);
        assertNotNull(user.getMeals());
        assertFalse(user.getMeals().isEmpty());
        user.getMeals().forEach(meal -> assertEquals(USER_ID, meal.getUser().id()));
    }


}
