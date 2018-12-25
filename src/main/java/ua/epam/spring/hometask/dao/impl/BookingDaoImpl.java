package ua.epam.spring.hometask.dao.impl;

import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.dao.query.TicketQueryResource;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingDaoImpl implements BookingDao {

    private TicketQueryResource ticketQueryResource = ConfigFactory.create(TicketQueryResource.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, Set<Long> seats) {
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        long vipSeatsCount = auditorium.countVipSeats(seats);
        long regSeats = seats.size() - vipSeatsCount;
        double eventBasePrice = event.getBasePrice();
        return eventBasePrice * regSeats + eventBasePrice * vipSeatsCount * 2;
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        for(Ticket ticket : tickets) {
            jdbcTemplate.update(connection -> {
                final PreparedStatement ps = connection.prepareStatement(
                        ticketQueryResource.ticketsSave(), Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, ticket.getUser().getId());
                ps.setLong(2, ticket.getEvent().getId());
                ps.setTimestamp(3, Timestamp.valueOf(ticket.getDateTime()));
                ps.setLong(4, ticket.getSeat());
                return ps;
            });
        }
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(User user, Event event, LocalDateTime dateTime) {
        List<Ticket> ticketSet = jdbcTemplate.query(ticketQueryResource.ticketsByUserId(), new Object[]{user.getId()},
                new BeanPropertyRowMapper<>(Ticket.class));
        return ticketSet.stream()
                .filter(ticket -> ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
