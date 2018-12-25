package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
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
	public double getTicketsPrice(User user, Event event, LocalDateTime dateTime, Set<Long> seats) {
		double price = bookingDao.getTicketsPrice(event, dateTime, seats);
		byte discount = discountService.getDiscount(user, event, dateTime, seats.size());
		return price - (price / 100) * discount;
	}

	@Override
	public void bookTickets(Set<Ticket> tickets) {
		bookingDao.bookTickets(tickets);
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(User user, Event event, LocalDateTime dateTime) {
		return bookingDao.getPurchasedTicketsForEvent(user, event, dateTime);
	}
}
