package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealServiceTest;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealServiceTest;
import ru.javawebinar.topjava.repository.jpa.JpaMealServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({JpaMealServiceTest.class,
                     JdbcMealServiceTest.class,
                     DataJpaMealServiceTest.class})
public class AllMealServiceTestsSuite {
}
