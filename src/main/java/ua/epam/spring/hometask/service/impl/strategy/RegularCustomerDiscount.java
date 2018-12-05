package ua.epam.spring.hometask.service.impl.strategy;

import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

public class RegularCustomerDiscount implements DiscountStrategy {

    private final static int REGULAR_DISCOUNT = 10;
    private final static int TICKETS_TO_GET_DISCOUNT = 10;

    @Override
    public byte getDiscount(User user, LocalDateTime airDate, long numberOfTickets) {
        long ticketsLeftToDiscount = TICKETS_TO_GET_DISCOUNT - numberOfTickets % TICKETS_TO_GET_DISCOUNT;
        if (ticketsLeftToDiscount <= numberOfTickets) {
            return REGULAR_DISCOUNT;
        }
        return 0;
    }
}
