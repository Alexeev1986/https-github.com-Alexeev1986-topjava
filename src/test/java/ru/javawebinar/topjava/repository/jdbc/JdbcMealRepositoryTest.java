package ru.javawebinar.topjava.repository.jdbc;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.MealTestData;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.web.MealTestData.*;
import static ru.javawebinar.topjava.web.MealTestData.MEAL_ID;
import static ru.javawebinar.topjava.web.UserTestData.ADMIN_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles("jdbc")
@RunWith(SpringRunner.class)
public class JdbcMealRepositoryTest {

    @Autowired
    private MealRepository repository;


    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void save() {
        Meal created = repository.save(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(repository.get(newId, USER_ID), newMeal);
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void update() {
        Meal update = getUpdated();
        repository.save(update, USER_ID);
        assertEquals(repository.get(MEAL_ID, USER_ID), update);
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateWithWrongUserId() {
        Meal update = getUpdated();
        Meal result = repository.save(update, ADMIN_ID);
        assertNull(result);
        assertMatch(repository.get(MEAL_ID, USER_ID), meal);
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void delete() {
        boolean delete = repository.delete(MEAL_ID, USER_ID);
        assertTrue(delete);
        assertNull(repository.get(MEAL_ID, USER_ID));
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteWithWrongUserId() {
        boolean delete = repository.delete(MEAL_ID, ADMIN_ID);
        assertFalse(delete);
        assertNotNull(repository.get(MEAL_ID, USER_ID));
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void get() {
        Meal meal = repository.get(MEAL_ID, USER_ID);
        assertMatch(MealTestData.meal, meal);
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getWithWrongUserId() {
        Meal meal = repository.get(MEAL_ID, ADMIN_ID);
        assertNull(meal);
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAll() {
        List<Meal> actualMeals = repository.getAll(USER_ID);
        assertMatch(actualMeals, meals.toArray(new Meal[0]));
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllWithWrongUserId() {
        List<Meal> actualMeals = repository.getAll(ADMIN_ID);
        assertTrue(actualMeals.isEmpty());
    }

    @Test
    @Sql(scripts = "/db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBetweenHalfOpen() {
        List<Meal> filteredMeals = repository.getBetweenHalfOpen(
                LocalDateTime.of(2020, Month.JANUARY, 30, 7, 0),
                LocalDateTime.of(2020, Month.JANUARY, 30, 23, 0),
                USER_ID);
        assertMatch(filteredMeals, meals.get(0), meals.get(1), meals.get(2));
    }

    private static void assertMatch(Meal actual, Meal expected) {
        Assertions.assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    private static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}