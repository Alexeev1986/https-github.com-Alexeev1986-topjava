package ru.javawebinar.topjava.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password,
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User getByEmail(String email) {
        // return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        setRoles(user);
        return user;
    }

    private void setRoles(User user) {
        if (user == null) return;
        List<Role> roles = jdbcTemplate.query("SELECT * FROM user_role WHERE user_id=?",
                (rs, rowNum) -> Role.valueOf(rs.getString("role")), user.id());
        user.setRoles(roles);
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> map = new LinkedHashMap<>();
        jdbcTemplate.query("SELECT u.*, r.role FROM users u LEFT JOIN user_role r ON u.id=r.user_id ORDER BY u.name, u.email",
                (rs -> {
                    int userId = rs.getInt("id");
                    User user = map.get(userId);

                    if (user == null) {
                        user = ROW_MAPPER.mapRow(rs, 0);
                        map.put(userId, user);
                    }
                    String roleStr = rs.getString("role");
                    if (roleStr != null) {
                        user.getRoles().add(Role.valueOf(roleStr));
                    }
                }));

        return new ArrayList<>(map.values());
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.query("SELECT u.*, r.role FROM users u LEFT JOIN user_role r on u.id=r.user_id WHERE u.id=?",
                new UserResultSetExtractor(), id);
    }

    private static class UserResultSetExtractor implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            User user = null;
            Set<Role> roles = new HashSet<>();
            while (rs.next()) {
                if (user == null) {
                    user = ROW_MAPPER.mapRow(rs, 0);
                }
                String roleStr = rs.getString("role");
                if (roleStr != null) {
                    roles.add(Role.valueOf(roleStr));
                }
            }
            if (user != null) {
                user.setRoles(roles);
            }
            return user;
        }
    }
}
