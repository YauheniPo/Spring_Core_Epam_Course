package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

@Component
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	@Override
	public double getTicketsPrice(Event event, LocalDateTime dateTime, Set<Long> seats) {
		return bookingDao.getTicketsPrice(event, dateTime, seats);
	}

	@Override
	public void bookTickets(Set<Ticket> tickets) {
		bookingDao.bookTickets(tickets);
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
		return null;
	}
}
