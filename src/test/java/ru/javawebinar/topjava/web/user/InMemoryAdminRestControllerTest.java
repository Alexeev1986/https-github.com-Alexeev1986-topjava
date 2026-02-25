package ru.javawebinar.topjava.web.user;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.BeanUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration("classpath:spring/spring-inmemory.xml")
@ActiveProfiles("inmemory")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerTest {
    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Autowired
    private ConfigurableApplicationContext appCtx;

    private static boolean isPrintedBeans = false;

    @Before
    public void setup() {
        repository.init();
        if (!isPrintedBeans) {
            isPrintedBeans = true;
            BeanUtil.printBeans(appCtx);
        }
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        Assert.assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}