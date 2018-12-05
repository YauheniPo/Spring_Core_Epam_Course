package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface EventDao extends AbstractDomainObjectDao<Event> {

	/**
	 * Finding event by name
	 *
	 * @param name Name of the event
	 * @return found event or <code>null</code>
	 */
	@Nullable Event getByName(@Nonnull String name);
}
