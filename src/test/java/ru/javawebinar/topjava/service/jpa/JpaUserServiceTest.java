package ru.javawebinar.topjava.service.jpa;

import static ru.javawebinar.topjava.Profiles.JPA;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractJpaUserServiceTest;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractJpaUserServiceTest {
}