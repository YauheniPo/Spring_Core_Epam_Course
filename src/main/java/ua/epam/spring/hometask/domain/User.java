package ua.epam.spring.hometask.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper=false, exclude = {"tickets"})
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Scope("prototype")
public class User extends DomainObject {

    @NonNull private Long id;
    @Value("${user.firstName}")
    @NonNull private String firstName;
    @Value("${user.lastName}")
    @NonNull private String lastName;
    @Value("${user.email}")
    @NonNull private String email;
    @Value("#{T(java.time.LocalDate).parse('${user.birthday}', T(java.time.format.DateTimeFormatter).ofPattern('yyyy-MM-dd')).atStartOfDay()}")
    private LocalDateTime birthday;
    private NavigableSet<Ticket> tickets = new TreeSet<>();

    public boolean addTicket(Ticket... ticket) {
        return tickets.addAll(Arrays.asList(ticket));
    }
}
