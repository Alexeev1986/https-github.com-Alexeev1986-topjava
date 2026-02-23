package ru.javawebinar.topjava.service;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.BeanUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles("jdbc")
@RunWith(SpringRunner.class)
@Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private ConfigurableApplicationContext appCtx;

    private static boolean isPrintedBeans = false;

    @Before
    public void setup() {
        if (!isPrintedBeans) {
            isPrintedBeans = true;
            BeanUtil.printBeans(appCtx);
        }
    }

    @Test
    public void get() {
        assertMatch(service.get(MEAL_ID, USER_ID), meal);
    }

    @Test
    public void getAdmin() {
        assertMatch(service.get(adminMeals.get(0).getId(), ADMIN_ID), adminMeals.get(0));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID );
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filteredMeals = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30),
                USER_ID);
        assertMatch(filteredMeals, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenInclusiveAdmin() {
        List<Meal> filteredMeals = service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 31),
                LocalDate.of(2020, Month.JANUARY, 31),
                ADMIN_ID);
        assertMatch(filteredMeals, getAdminMeals());
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(USER_ID);
        assertMatch(actualMeals, getUserMeals());
    }

    @Test
    public void getAllAdmin() {
        List<Meal> actualMeals = service.getAll(ADMIN_ID);
        assertMatch(actualMeals, getAdminMeals());
    }

    @Test
    public void update() {
        Meal update = getUpdated();
        service.update(update, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal update = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(update, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicate = getDuplicate();
        assertThrows(DataAccessException.class, () -> service.create(duplicate, USER_ID));
    }
}