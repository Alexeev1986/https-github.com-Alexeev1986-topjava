package ru.javawebinar.topjava.service.jpa;

import static ru.javawebinar.topjava.Profiles.JPA;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import java.util.List;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCacheIsDisabled() {
        log.debug("CacheManager class: {}", cacheManager.getClass());
        List<User> firstCall = service.getAll();
        List<User> secondCall = service.getAll();
    }
}