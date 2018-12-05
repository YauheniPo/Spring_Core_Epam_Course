package ua.epam.spring.hometask.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper=false)
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User extends DomainObject {

    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    private LocalDateTime birthday;
    private NavigableSet<Ticket> tickets = new TreeSet<>();

    public boolean addTicket(Ticket... ticket) {
        return tickets.addAll(Arrays.asList(ticket));
    }
}
