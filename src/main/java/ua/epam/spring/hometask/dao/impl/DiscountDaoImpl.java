package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.DiscountDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

public class DiscountDaoImpl implements DiscountDao {

	@Override
	public byte getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		return 0;
	}
}
