package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingDaoImpl implements BookingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Set<Ticket> purchasedTickets = new HashSet<>();

    @Override
    public double getTicketsPrice(User user, Event event, LocalDateTime dateTime, Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        long vipSeatsCount = auditorium.countVipSeats(seats);
        long regSeats = seats.size() - vipSeatsCount;
        double eventBasePrice = event.getBasePrice();
        return eventBasePrice * regSeats + eventBasePrice * vipSeatsCount * 2;
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        purchasedTickets.addAll(tickets);
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(User user, Event event, LocalDateTime dateTime) {
        Set<Ticket> ticketSet = purchasedTickets.stream()
                .filter(ticket -> ticket.getUser().equals(user)).collect(Collectors.toSet());
        return ticketSet.stream()
                .filter(ticket -> ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
