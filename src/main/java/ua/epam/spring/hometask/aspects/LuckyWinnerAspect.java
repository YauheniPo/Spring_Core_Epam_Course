package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
@PropertySources({
        @PropertySource("classpath:data/data.properties"),
})
public class LuckyWinnerAspect {

    private Map<User, Boolean> luckyMap = new HashMap<>();
    @Value("${lucky.probability}")
    private Byte luckyProbability;

    @Pointcut(value = "execution(* ua.epam.spring.hometask.dao.impl.BookingDaoImpl*.bookTickets(..))")
    private void pointcutBookingTickets() {
    }

    @Pointcut(value = "execution(* ua.epam.spring.hometask.dao.impl.BookingDaoImpl*.getTicketsPrice(..))")
    private void pointcutGettingTicketsPrice() {
    }

    @After("pointcutBookingTickets() && args(tickets)")
    private void countGettingDiscount(Object tickets) {
        User user = ((Set<Ticket>) tickets).iterator().next().getUser();
        luckyMap.put(user, checkLucky());
    }

    @Around("pointcutGettingTicketsPrice() && args(user,..)")
    private Object aroundLogEvent(ProceedingJoinPoint pjp, Object user) throws Throwable {
        if (luckyMap.containsKey(user)) {
            return luckyMap.get(user) ? 0.0 : pjp.proceed();
        }
        return pjp.proceed();
    }

    private Boolean checkLucky() {
        return ThreadLocalRandom.current().nextInt(100) > 100 - luckyProbability;
    }
}
