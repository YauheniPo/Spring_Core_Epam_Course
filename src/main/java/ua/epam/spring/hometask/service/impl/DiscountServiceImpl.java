package ua.epam.spring.hometask.service.impl;

import lombok.Setter;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.impl.strategy.discount.DiscountStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Component
public class DiscountServiceImpl implements DiscountService {

	private List<DiscountStrategy> discountStrategies;

	@Override
	public byte getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		byte maxDiscount = 0;
		for (DiscountStrategy discountStrategy : discountStrategies) {
			byte discountValue = discountStrategy.getDiscount(user, airDateTime, numberOfTickets);
			if (discountValue > maxDiscount) {
				maxDiscount = discountValue;
			}
		}
		return maxDiscount;
	}
}
