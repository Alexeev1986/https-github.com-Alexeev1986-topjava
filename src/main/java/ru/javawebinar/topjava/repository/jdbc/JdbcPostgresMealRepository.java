package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@Component
@Profile(POSTGRES_DB)
public class JdbcPostgresMealRepository extends AbstractJdbcMealRepository {
    public JdbcPostgresMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
