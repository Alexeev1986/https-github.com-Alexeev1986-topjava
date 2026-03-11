package ru.javawebinar.topjava.repository.jdbc;

import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile(POSTGRES_DB)
public class PostgresJdbcMealRepository extends AbstractJdbcMealRepository {
    public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
