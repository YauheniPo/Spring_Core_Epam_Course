package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UserDao extends AbstractDomainObjectDao<User> {

	/**
	 * Finding user by email
	 *
	 * @param email Email of the user
	 * @return found user or <code>null</code>
	 */
	@Nullable User getUserByEmail(@Nonnull String email);
}
