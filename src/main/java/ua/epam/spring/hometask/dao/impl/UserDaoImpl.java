package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.Setter;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;

import java.util.Collection;
import java.util.Set;

@Setter @Getter
public class UserDaoImpl implements UserDao {

    private Set<User> users;

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
        users.add(user);
        return user;
    }

    @Override
    public void remove(User user) {
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
        return users;
    }
}
