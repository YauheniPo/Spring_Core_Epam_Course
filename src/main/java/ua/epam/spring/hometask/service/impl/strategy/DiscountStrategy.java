package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {

    byte getDiscount(User user, LocalDateTime airDate, long numberOfTickets);
}
