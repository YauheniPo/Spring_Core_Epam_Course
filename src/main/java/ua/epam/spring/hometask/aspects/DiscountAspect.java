package ua.epam.spring.hometask.aspects;

import lombok.Getter;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.impl.strategy.discount.BirthDayDiscount;
import ua.epam.spring.hometask.service.impl.strategy.discount.DiscountStrategy;
import ua.epam.spring.hometask.service.impl.strategy.discount.RegularCustomerDiscount;

import java.util.HashMap;
import java.util.Map;

import static ua.epam.spring.hometask.AppConsts.BIRTHDAY_DISCOUNT;
import static ua.epam.spring.hometask.AppConsts.REGULAR_DISCOUNT;

@Aspect
@Component
public class DiscountAspect {

    @Getter
    private Map<User, Map<DiscountStrategy, Integer>> counter = new HashMap<>();
    private final Map<Byte, DiscountStrategy> discounts = new HashMap<Byte, DiscountStrategy>() {{
        put(BIRTHDAY_DISCOUNT, new BirthDayDiscount());
        put(REGULAR_DISCOUNT, new RegularCustomerDiscount());
    }};

    @Pointcut(value = "execution(* ua.epam.spring.hometask.service.impl.DiscountServiceImpl*.getDiscount(..))")
    private void pointcutGettingDiscount() {
    }

    @AfterReturning(
            pointcut = "pointcutGettingDiscount() && args(user,..)",
            returning = "discount")
    private void countGettingDiscount(Object user, Object discount) {
        if ((Byte) discount != 0) {
            DiscountStrategy dscnt = discounts.get(discount);
            if (dscnt != null) {
                if (!counter.containsKey(user)) {
                    counter.put((User) user, new HashMap<DiscountStrategy, Integer>() {{
                        put(dscnt, 0);
                    }});
                }
                if (!counter.get(user).containsKey(dscnt)) {
                    counter.get(user).put(dscnt, 0);
                }
                counter.get(user).put(dscnt, counter.get(user).get(dscnt) + 1);
                counter.put((User) user, counter.get(user));
            }
        }
    }
}
