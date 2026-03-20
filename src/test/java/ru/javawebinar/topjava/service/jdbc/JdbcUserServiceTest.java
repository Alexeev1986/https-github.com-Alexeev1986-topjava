package ru.javawebinar.topjava.service.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import java.util.List;


@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void springCacheInAction() {
        List<User> before = service.getAll();
        userRepository.delete(USER_ID);
        List<User> after = service.getAll();
        assertThat(before.size()).isGreaterThan(after.size());
    }

}