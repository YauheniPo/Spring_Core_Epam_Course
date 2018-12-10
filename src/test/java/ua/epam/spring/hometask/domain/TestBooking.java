package ua.epam.spring.hometask.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.aspects.CounterAspect;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.UserDao;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

public class TestBooking extends BaseTest {

    private final String EVENT_NAME_RAMBO = "Rambo";
    private final String EVENT_NAME_RAMBO_2 = "Rambo 2";

    @Autowired
    @Qualifier("auditoriumDaoImpl")
    private AuditoriumDao auditoriumsDao;

    @Autowired
    @Qualifier("userDaoImpl")
    private UserDao userDao;

    @Autowired
    @Qualifier("eventDaoImpl")
    private EventDao eventDao;

    @Autowired
    private CounterAspect counterAspect;

    @Autowired
    private BookingDao bookingDao;

    @Test
    public void testPrice() {
        final String USER_EMAIL = "lucy@epam.com";
        final String AUDITORIUM1_NAME = "Auditorium1";
        final String AUDITORIUM2_NAME = "Auditorium2";
        final String AIR_DATE_EVENT = "2016-10-08T14:00";

        Event event = eventDao.getByName(EVENT_NAME_RAMBO);
        LocalDateTime airDate = LocalDateTime.parse(AIR_DATE_EVENT);
        Auditorium auditorium1 = auditoriumsDao.getByName(AUDITORIUM1_NAME);
        Auditorium auditorium2 = auditoriumsDao.getByName(AUDITORIUM2_NAME);
        event.addAirDateTime(airDate, auditorium1);
        event.addAirDateTime(airDate.plusDays(1), auditorium2);

        User user = userDao.getUserByEmail(USER_EMAIL);

        Ticket ticket1 = new Ticket(user, event, event.getAirDates().first(), 1);
        Ticket ticket2 = new Ticket(user, event, event.getAirDates().first(), 2);


        double ticketsPrice = bookingDao.getTicketsPrice(event, event.getAirDates().first(), user,
                new HashSet<>(Arrays.asList(ticket1.getSeat(), ticket2.getSeat())));

        assertEquals(8.0, ticketsPrice, 0);
    }

    @Test
    public void testCounterAspect() {
        Event eventRambo = eventDao.getByName(EVENT_NAME_RAMBO);
        Event eventRambo2 = eventDao.getByName(EVENT_NAME_RAMBO_2);

        assertEquals(2, counterAspect.getCounter().size());
        assertEquals(1, (int) counterAspect.getCounter().get(eventRambo).getCountGettingEventByName());
        assertEquals(1, (int) counterAspect.getCounter().get(eventRambo2).getCountGettingEventByName());
    }
}
