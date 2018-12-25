package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.Setter;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.mapper.AuditoriumMapper;
import ua.epam.spring.hometask.dao.query.AuditoriumQueryResource;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.HashSet;
import java.util.Set;

@Component
@Setter @Getter
public class AuditoriumDaoImpl implements AuditoriumDao {

	private AuditoriumQueryResource auditoriumQueryResource = ConfigFactory.create(AuditoriumQueryResource.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Set<Auditorium> getAll() {
		return new HashSet<>(jdbcTemplate.query(auditoriumQueryResource.auditoriumsGetAll(), new AuditoriumMapper()));
	}

	@Override
	public Auditorium getByName(String name) {
		return jdbcTemplate.queryForObject(auditoriumQueryResource.auditoriumsGetByName(), new Object[]{name}, new AuditoriumMapper());
	}
}
