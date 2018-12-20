package ua.epam.spring.hometask.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

@Component
public class AuditoriumServiceImpl implements AuditoriumService {

	@Autowired
	private AuditoriumDao auditoriumDao;
	
	@Override
	public Set<Auditorium> getAll() {
		return auditoriumDao.getAll();
	}

	@Override
	public Auditorium getByName(String name) {
		return auditoriumDao.getByName(name);
	}
}
