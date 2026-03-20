package ru.javawebinar.topjava.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import ru.javawebinar.topjava.util.ValidationUtil;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final Logger log = LoggerFactory.getLogger(JdbcUserRepository.class);

    private static final UserResultSetExtractor EXTRACTOR = new UserResultSetExtractor();

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
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password,
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                       """, parameterSource) == 0) {
                return null;
            }
            updateRoles(user);
        }
        return user;
    }

    private void insertRoles(User user) {
        Set<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            return;
        }
        int[] updateCounts = jdbcTemplate.batchUpdate("INSERT INTO user_role (user_id, role) VALUES (?, ?)",
                roles.stream()
                        .map(role -> new Object[]{user.id(), role.name()})
                        .collect(Collectors.toList())
        );
        log.debug("inserted {} roles for user {}", updateCounts.length, user.id());
    }

    private void updateRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.id());
        insertRoles(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> map = new LinkedHashMap<>();
        jdbcTemplate.query("SELECT u.*, r.role FROM users u LEFT JOIN user_role r ON u.id=r.user_id ORDER BY u.name, u.email",
                (rs -> {
                    int userId = rs.getInt("id");
                    User user = map.get(userId);
                    if (user == null) {
                        user = ROW_MAPPER.mapRow(rs, rs.getRow());
                        if (user.getRoles() == null) {
                            user.setRoles(EnumSet.noneOf(Role.class));
                        }
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
    public User getByEmail(String email) {
        return jdbcTemplate.query("SELECT u.*, r.role FROM users u LEFT JOIN user_role r on u.id=r.user_id WHERE u.email=?",
                EXTRACTOR, email);
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.query("SELECT u.*, r.role FROM users u LEFT JOIN user_role r on u.id=r.user_id WHERE u.id=?",
                EXTRACTOR, id);
    }

    private static class UserResultSetExtractor implements ResultSetExtractor<User> {
        @Override
        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            User user = null;
            Set<Role> roles = EnumSet.noneOf(Role.class);
            while (rs.next()) {
                if (user == null) {
                    user = ROW_MAPPER.mapRow(rs, rs.getRow());
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
