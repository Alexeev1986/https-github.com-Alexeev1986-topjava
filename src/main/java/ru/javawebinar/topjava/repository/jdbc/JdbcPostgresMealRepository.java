package ru.javawebinar.topjava.repository.jdbc;

import java.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPostgresMealRepository extends AbstractJdbcMealRepository {
    public JdbcPostgresMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
