package ua.epam.spring.hometask.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User save(User object) {
		return userDao.save(object);
	}

	@Override
	public void remove(User object) {
		userDao.remove(object);
	}

	@Override
	public User getById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public Collection<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
}
