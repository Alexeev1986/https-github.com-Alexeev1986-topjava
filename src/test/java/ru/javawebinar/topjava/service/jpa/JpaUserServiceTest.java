package ru.javawebinar.topjava.service.jpa;

import static ru.javawebinar.topjava.Profiles.JPA;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(JPA)
@TestPropertySource(properties = {"spring.cache.type=NONE",
        "spring.jpa.properties.hibernate.cache.use_second_level_cache=false"})
public class JpaUserServiceTest extends AbstractUserServiceTest {
}