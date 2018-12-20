package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

@Setter @Getter
public class AuditoriumDaoImpl implements AuditoriumDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Set<Auditorium> auditoriums;

	@Override
	public Set<Auditorium> getAll() {
		return auditoriums;
	}

	@Override
	public Auditorium getByName(String name) {
		for (Auditorium auditorium : auditoriums) {
			if (name.equals(auditorium.getName())) {
				return auditorium;
			}
		}
		return null;
	}
}
