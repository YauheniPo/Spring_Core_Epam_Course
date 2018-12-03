package ua.epam.spring.hometask.domain;

import lombok.*;

import java.util.NavigableSet;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper=false)
@Getter @Setter
@RequiredArgsConstructor
public class User extends DomainObject {

    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    private NavigableSet<Ticket> tickets = new TreeSet<>();
}
