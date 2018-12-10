package ua.epam.spring.hometask.dao.impl;

import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingDaoImpl implements BookingDao {

    private Set<Ticket> purchasedTickets;

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        long vipSeatsCount = auditorium.countVipSeats(seats);
        long regSeats = seats.size() - vipSeatsCount;
        double eventBasePrice = event.getBasePrice();
        return eventBasePrice * regSeats + eventBasePrice * vipSeatsCount * 2;
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        tickets.forEach(ticket -> {
            if (ticket.getEvent().equals(purchasedTickets.iterator().next().getEvent())) {
                purchasedTickets.add(ticket);
            }
        });
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        return purchasedTickets.stream()
                .filter(ticket -> ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
