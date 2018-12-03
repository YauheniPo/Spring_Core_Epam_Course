package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface UserDao extends AbstractDomainObjectService<User> {

	@Nullable User getUserByEmail(@Nonnull String email);
}
