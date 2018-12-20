package ua.epam.spring.hometask;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.impl.AuditoriumServiceImpl;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;
import ua.epam.spring.hometask.service.impl.EventServiceImpl;
import ua.epam.spring.hometask.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@Log4j2
@Component
public class App implements ApplicationListener {

    private static final String USER_EMAIL = "lucy@epam.com";
    private static final String EVENT_NAME = "Rambo";
    private static final String AUDITORIUM1_NAME = "Auditorium1";
    private static final String AUDITORIUM2_NAME = "Auditorium2";
    private static final String AIR_DATE_EVENT = "2016-10-08T00:00";

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuditoriumServiceImpl auditoriumService;

    public static void main(String[] args) {
        ApplicationContext actx = new AnnotationConfigApplicationContext(AppConfig.class);
        App app = actx.getBean("app", App.class);

        Event event = app.eventService.getByName(EVENT_NAME);
        LocalDateTime airDate = LocalDateTime.parse(AIR_DATE_EVENT);
        Auditorium auditorium1 = app.auditoriumService.getByName(AUDITORIUM1_NAME);
        Auditorium auditorium2 = app.auditoriumService.getByName(AUDITORIUM2_NAME);
        event.addAirDateTime(airDate, auditorium1);
        event.addAirDateTime(airDate.plusDays(1), auditorium2);

        User user = app.userService.getUserByEmail(USER_EMAIL);

        Ticket ticket1 = new Ticket(user, event, event.getAirDates().first(), 1);
        Ticket ticket2 = new Ticket(user, event, event.getAirDates().first(), 2);
        Ticket ticket3 = new Ticket(user, event, event.getAirDates().last(), 1);
        Ticket ticket4 = new Ticket(user, event, event.getAirDates().last(), 2);

        double ticketsPrice = app.bookingService.getTicketsPrice(event, event.getAirDates().first(),
                new HashSet<>(Arrays.asList(ticket1.getSeat(), ticket2.getSeat())));

        user.addTicket(ticket1, ticket2, ticket3, ticket4);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info(applicationEvent.toString());
    }
}
