package ua.epam.spring.hometask.service.impl.strategy.discount;

import lombok.EqualsAndHashCode;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;

import static ua.epam.spring.hometask.AppConsts.BIRTHDAY_DISCOUNT;
import static ua.epam.spring.hometask.AppConsts.BIRTHDAY_DISCOUNT_DAYS_INTERVAL;

@EqualsAndHashCode
public class BirthDayDiscount implements DiscountStrategy {

    @Override
    public byte getDiscount(User user, LocalDateTime airDate, long numberOfTickets) {
        if (user.getBirthday() == null) {
            return 0;
        }
        if (Math.abs(airDate.getDayOfYear() - user.getBirthday().getDayOfYear()) <= BIRTHDAY_DISCOUNT_DAYS_INTERVAL) {
            return BIRTHDAY_DISCOUNT;
        }
        return 0;
    }
}
