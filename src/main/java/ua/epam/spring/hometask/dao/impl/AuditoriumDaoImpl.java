package ua.epam.spring.hometask.dao.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriumDaoImpl implements AuditoriumDao {

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
