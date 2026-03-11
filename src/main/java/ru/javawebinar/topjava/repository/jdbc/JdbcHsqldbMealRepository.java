package ru.javawebinar.topjava.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static ru.javawebinar.topjava.Profiles.HSQL_DB;

@Component
@Profile(HSQL_DB)
public class JdbcHsqldbMealRepository extends AbstractJdbcMealRepository {
    public JdbcHsqldbMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
