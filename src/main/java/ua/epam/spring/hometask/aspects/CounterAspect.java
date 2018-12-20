package ua.epam.spring.hometask.aspects;

import lombok.Getter;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
public class CounterAspect {

    @Getter
    private Map<Event, CounterModel> counter = new HashMap<>();

    @Pointcut(value = "execution(* ua.epam.spring.hometask.dao.impl.EventDaoImpl*.getByName(..))")
    private void pointcutGettingEventByName() {
    }

    @Pointcut(value = "execution(* ua.epam.spring.hometask.dao.impl.BookingDaoImpl*.getTicketsPrice(..))")
    private void pointcutGettingTicketsPrice() {
    }

    @Pointcut(value = "execution(* ua.epam.spring.hometask.dao.impl.BookingDaoImpl*.bookTickets(..))")
    private void pointcutBookingTickets() {
    }

    @AfterReturning(
            pointcut = "pointcutGettingEventByName()",
            returning = "event")
    private void countGettingEventByName(Object event) {
        if (event != null) {
            if (!counter.containsKey(event)) {
                counter.put((Event) event, new CounterModel());
            }
            counter.put((Event) event, counter.get(event).addAccessToEventByName());
        }
    }

    @Before(value = "pointcutGettingTicketsPrice() && args(event,..)")
    private void countGettingTicketsPrice(Object event) {
        if (event != null) {
            if (!counter.containsKey(event)) {
                counter.put((Event) event, new CounterModel());
            }
            counter.put((Event) event, counter.get(event).addAccessToTicketsPrice());
        }
    }

    @Before(value = "pointcutBookingTickets() && args(tckts)")
    private void countBookingTickets(Object tckts) {
        Set<Ticket> tickets = (Set<Ticket>) tckts;
        for (Ticket ticket : tickets) {
            if (!counter.containsKey(ticket.getEvent())) {
                counter.put(ticket.getEvent(), new CounterModel());
            }
            counter.put(ticket.getEvent(), counter.get(ticket.getEvent()).addAccessToBookingTickets());
        }
    }

    @Getter
    public class CounterModel {
         private Integer countGettingEventByName = 0;
         private Integer countGettingTicketsPrice = 0;
         private Integer countBookingTickets = 0;

         private CounterModel addAccessToEventByName() {
            ++countGettingEventByName;
            return this;
        }

        private CounterModel addAccessToTicketsPrice() {
            ++countGettingTicketsPrice;
            return this;
        }

        private CounterModel addAccessToBookingTickets() {
            ++countBookingTickets;
            return this;
        }
    }
}
