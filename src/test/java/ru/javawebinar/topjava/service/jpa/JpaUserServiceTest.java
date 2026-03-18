package ru.javawebinar.topjava.service.jpa;

import static ru.javawebinar.topjava.Profiles.JPA;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {
}