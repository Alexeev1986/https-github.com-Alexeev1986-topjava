package ru.javawebinar.topjava.repository.jdbc;

import static ru.javawebinar.topjava.Profiles.HSQL_DB;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile(HSQL_DB)
public class HsqldbJdbcMealRepository extends AbstractJdbcMealRepository {
    public HsqldbJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object convertDataTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
