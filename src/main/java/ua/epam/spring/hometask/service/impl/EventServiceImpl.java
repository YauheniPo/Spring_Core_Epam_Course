package ua.epam.spring.hometask.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

@Component
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;
	
	@Override
	public Event save(Event object) {
		return eventDao.save(object);
	}

	@Override
	public void remove(Event object) {
		eventDao.remove(object);
	}

	@Override
	public Event getById(Long id) {
		return eventDao.getById(id);
	}

	@Override
	public Collection<Event> getAll() {
		return eventDao.getAll();
	}

	@Override
	public Event getByName(String name) {
		return eventDao.getByName(name);
	}
}
