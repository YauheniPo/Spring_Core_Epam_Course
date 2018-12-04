package ua.epam.spring.hometask;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.dao.impl.BookingDaoImpl;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@Log4j2
@Component
@Scope
public class App implements ApplicationListener {

    private static final String USER_EMAIL = "lucy@epam.com";
    private static final String EVENT_NAME = "Rambo";
    private static final String AUDITORIUM1_NAME = "Auditorium1";
    private static final String AUDITORIUM2_NAME = "Auditorium2";
    private static final String AIR_DATE_EVENT = "2016-10-08T00:00";

    @Resource(name = "auditoriumDaoImpl")
    private AuditoriumDao auditoriumsDao;

    @Resource(name = "userDaoImpl")
    private UserDao userDao;

    @Resource(name = "eventDaoImpl")
    private EventDao eventDao;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        App app = ctx.getBean("app", App.class);

        Event event = app.eventDao.getByName(EVENT_NAME);
        LocalDateTime airDate = LocalDateTime.parse(AIR_DATE_EVENT);
        Auditorium auditorium1 = app.auditoriumsDao.getByName(AUDITORIUM1_NAME);
        Auditorium auditorium2 = app.auditoriumsDao.getByName(AUDITORIUM2_NAME);
        event.addAirDateTime(airDate, auditorium1);
        event.addAirDateTime(airDate.plusDays(1), auditorium2);

        User user = app.userDao.getUserByEmail(USER_EMAIL);

        Ticket ticket1 = new Ticket(user, event, event.getAirDates().first(), 1);
        Ticket ticket2 = new Ticket(user, event, event.getAirDates().first(), 2);
        Ticket ticket3 = new Ticket(user, event, event.getAirDates().last(), 1);
        Ticket ticket4 = new Ticket(user, event, event.getAirDates().last(), 2);

        double ticketsPrice = new BookingDaoImpl().getTicketsPrice(event, event.getAirDates().first(), user,
                new HashSet<>(Arrays.asList(ticket1.getSeat(), ticket2.getSeat())));

        user.addTicket(ticket1, ticket2, ticket3, ticket4);


        ctx.close();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info(applicationEvent.toString());
    }
}
