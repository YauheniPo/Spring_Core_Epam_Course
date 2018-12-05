package ua.epam.spring.hometask.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.dao.impl.BookingDaoImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBooking {

    @Autowired
    @Qualifier("auditoriumDaoImpl")
    private AuditoriumDao auditoriumsDao;

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Autowired
    @Qualifier("eventDaoImpl")
    private EventDao eventDao;

    @Test
    public void testPrice() {
        final String USER_EMAIL = "lucy@epam.com";
        final String EVENT_NAME = "Rambo";
        final String AUDITORIUM1_NAME = "Auditorium1";
        final String AUDITORIUM2_NAME = "Auditorium2";
        final String AIR_DATE_EVENT = "2016-10-08T00:00";

        Event event = eventDao.getByName(EVENT_NAME);
        LocalDateTime airDate = LocalDateTime.parse(AIR_DATE_EVENT);
        Auditorium auditorium1 = auditoriumsDao.getByName(AUDITORIUM1_NAME);
        Auditorium auditorium2 = auditoriumsDao.getByName(AUDITORIUM2_NAME);
        event.addAirDateTime(airDate, auditorium1);
        event.addAirDateTime(airDate.plusDays(1), auditorium2);

        User user = userDao.getUserByEmail(USER_EMAIL);

        Ticket ticket1 = new Ticket(user, event, event.getAirDates().first(), 1);
        Ticket ticket2 = new Ticket(user, event, event.getAirDates().first(), 2);
        Ticket ticket3 = new Ticket(user, event, event.getAirDates().last(), 1);
        Ticket ticket4 = new Ticket(user, event, event.getAirDates().last(), 2);

        double ticketsPrice = new BookingDaoImpl().getTicketsPrice(event, event.getAirDates().first(), user,
                new HashSet<>(Arrays.asList(ticket1.getSeat(), ticket2.getSeat())));

        assertEquals(8.0, ticketsPrice, 0);
    }

}
