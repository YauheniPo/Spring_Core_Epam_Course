package ua.epam.spring.hometask.service.impl.strategy.discount;

import lombok.EqualsAndHashCode;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

import static ua.epam.spring.hometask.AppConsts.REGULAR_DISCOUNT;
import static ua.epam.spring.hometask.AppConsts.REGULAR_DISCOUNT_TICKETS_TO_GET;

@EqualsAndHashCode
public class RegularCustomerDiscount implements DiscountStrategy {

    @Override
    public byte getDiscount(User user, LocalDateTime airDate, long numberOfTickets) {
        long ticketsLeftToDiscount = REGULAR_DISCOUNT_TICKETS_TO_GET - numberOfTickets % REGULAR_DISCOUNT_TICKETS_TO_GET;
        if (ticketsLeftToDiscount <= numberOfTickets) {
            return REGULAR_DISCOUNT;
        }
        return 0;
    }
}
