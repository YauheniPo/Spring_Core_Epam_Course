package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Collection;
import java.util.Set;

@Setter @Getter
@RequiredArgsConstructor
@Scope("prototype")
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @NonNull private Set<User> users;

    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    "insert into users (firstname, lastname, email, birthday) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, Timestamp.valueOf(user.getBirthday().toLocalDate().atStartOfDay()));
            return ps;
        }, holder);
        user.setId(Long.parseLong(String.valueOf(holder.getKeyList().get(0).get("id"))));
        users.add(user);
        return user;
    }

    @Override
    public void remove(User user) {
        jdbcTemplate.update("delete from users where id = ?", user.getId());
        users.remove(user);
    }

    @Override
    public User getById(Long id) {
        for (User user : users) {
            if (id.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query("select * from users", new BeanPropertyRowMapper(User.class));
    }

    @PostConstruct
    private void init() {
        users.forEach(this::save);
    }
}
