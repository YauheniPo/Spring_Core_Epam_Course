package ua.epam.spring.hometask.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.aspects.CounterAspect;
import ua.epam.spring.hometask.aspects.LuckyWinnerAspect;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.BookingDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.UserDao;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class TestBooking extends BaseTest {

    private final String EVENT_NAME_RAMBO = "Rambo";
    private final String EVENT_NAME_RAMBO_2 = "Rambo 2";

    @Autowired
    @Qualifier("auditoriumDaoImpl")
    private AuditoriumDao auditoriumsDao;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("eventDaoImpl")
    private EventDao eventDao;

    @Autowired
    private CounterAspect counterAspect;

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Autowired
    private BookingDao bookingDao;

    @Test
    public void testPrice() {
        final String USER_EMAIL = "admin@epam.com";
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

        double ticketsPrice = bookingDao.getTicketsPrice(event, event.getAirDates().first(),
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

    @Test
    public void testLuckyWinner() {
        final String USER_EMAIL = "admin@epam.com";
        final String AUDITORIUM1_NAME = "Auditorium1";
        final String AIR_DATE_EVENT = "2016-10-08T14:00";

        Event event = eventDao.getByName(EVENT_NAME_RAMBO);
        LocalDateTime airDate = LocalDateTime.parse(AIR_DATE_EVENT);
        Auditorium auditorium1 = auditoriumsDao.getByName(AUDITORIUM1_NAME);
        event.addAirDateTime(airDate, auditorium1);

        User user = userDao.getUserByEmail(USER_EMAIL);

        Ticket ticket = new Ticket(user, event, event.getAirDates().first(), 1);

        bookingDao.bookTickets(Collections.singleton(ticket));

        assertTrue(bookingDao.getPurchasedTicketsForEvent(user, event, airDate).contains(ticket));

        double ticketsPrice = bookingDao.getTicketsPrice(event, event.getAirDates().first(),
                new HashSet<>(Collections.singletonList(ticket.getSeat())));

        assertTrue(ticketsPrice >= 0);
    }
}
