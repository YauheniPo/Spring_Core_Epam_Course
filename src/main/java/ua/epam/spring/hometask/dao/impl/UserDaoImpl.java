package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.utils.DbQueryResource;

import java.sql.*;
import java.util.Collection;

@Setter @Getter
@RequiredArgsConstructor
@Component
public class UserDaoImpl implements UserDao {

    private DbQueryResource dbQueryResource = ConfigFactory.create(DbQueryResource.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(dbQueryResource.usersGetByEmail(), new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User save(User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    dbQueryResource.usersSave(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, Timestamp.valueOf(user.getBirthday().toLocalDate().atStartOfDay()));
            return ps;
        }, holder);
        user.setId(Long.parseLong(String.valueOf(holder.getKeyList().get(0).get("id"))));
        return user;
    }

    @Override
    public void remove(User user) {
        jdbcTemplate.update(dbQueryResource.usersDelete(), user.getId());
    }

    @Override
    public User getById(Long id) {
        return jdbcTemplate.queryForObject(dbQueryResource.usersGetById(), new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(dbQueryResource.usersGetAll(), new BeanPropertyRowMapper<>(User.class));
    }
}
