package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserServiceTest;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserServiceTest;
import ru.javawebinar.topjava.repository.jpa.JpaUserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({JpaUserServiceTest.class,
                     JdbcUserServiceTest.class,
                     DataJpaUserServiceTest.class})
public class AllUserServiceTestsSuite {
}
