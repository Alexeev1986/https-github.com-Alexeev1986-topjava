package ru.javawebinar.topjava.service.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.Profiles.JPA;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.hibernate.Session;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Statement;
import java.util.List;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCacheIsDisabled() {
        List<User> firstCall = service.getAll();
        List<User> secondCall = service.getAll();
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void springCacheInAction() {
        List<User> before = service.getAll();
        userRepository.delete(USER_ID);
        List<User> after = service.getAll();
        assertThat(before.size()).isGreaterThan(after.size());
    }

    @PersistenceContext
    private EntityManager em;

    @Test
    public void hibernate2lvlCacheInAction() {
        User before = service.get(USER_ID);
        assertThat(before).isNotNull();
        // Удаляем по секрету от Hibernate
        Session session = em.unwrap(Session.class);
        session.doWork(connection -> {
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("DELETE FROM users");
            }
        });
        // если кеш отключен, повторный запрос ничего не найдет, а если не отключен, то вернется юзер из кеша
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }
}