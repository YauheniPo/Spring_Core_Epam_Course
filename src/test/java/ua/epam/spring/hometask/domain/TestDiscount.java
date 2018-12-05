package ua.epam.spring.hometask.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.service.DiscountService;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TestDiscount extends BaseTest {

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
    @Qualifier("discountServiceImpl")
    private DiscountService discountService;

    private User user;
    private Event event;
    private Ticket ticket;

    @Before
    public void before() {
        final String USER_EMAIL = "lucy@epam.com";
        final String EVENT_NAME = "Rambo";
        final String AUDITORIUM1_NAME = "Auditorium1";
        final String AIR_DATE_EVENT = "2016-09-14T14:00";

        this.event = eventDao.getByName(EVENT_NAME);
        LocalDateTime airDate = LocalDateTime.parse(AIR_DATE_EVENT);
        Auditorium auditorium1 = auditoriumsDao.getByName(AUDITORIUM1_NAME);
        event.addAirDateTime(airDate, auditorium1);

        this.user = userDao.getUserByEmail(USER_EMAIL);

        this.ticket = new Ticket(user, event, event.getAirDates().first(), 1);
    }

    @Test
    public void testBirthdayDiscount() {
        user.setBirthday(LocalDateTime.parse("1990-09-14T00:00"));

        byte discount = discountService.getDiscount(user, event, ticket.getDateTime(), 1);

        assertEquals(discount, 5);
    }

    @Test
    public void testRegularCustomerDiscount() {
        byte discount = discountService.getDiscount(user, event, ticket.getDateTime(), 11);

        assertEquals(discount, 10);
    }
}
