package ru.javawebinar.topjava.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeTrue;
import static ru.javawebinar.topjava.UserTestData.*;

import org.hibernate.Session;
import org.junit.Test;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Statement;

public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    public void hibernate2lvlCacheInAction() {
        assumeTrue("Hibernate 2nd level cache test skipped for JDBC", em != null);
        User before = service.get(USER_ID);
        assertThat(before).isNotNull();
        Session session = em.unwrap(Session.class);
        session.doWork(connection -> {
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("DELETE FROM users");
            }
        });
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }
}