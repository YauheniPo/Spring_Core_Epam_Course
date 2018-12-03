package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface EventDao extends AbstractDomainObjectService<Event> {

	@Nullable Event getByName(@Nonnull String name);
}
