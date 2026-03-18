package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private Environment environment;

    @Test
    public void testActiveProfiles() {
        log.info("=== Active profile === {}", Arrays.toString(environment.getActiveProfiles()));
        log.info("=== Properties === {}", "jpa.showSql = " + environment.getProperty("jpa.showSql"));
    }
}