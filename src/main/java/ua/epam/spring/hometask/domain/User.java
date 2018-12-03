package ua.epam.spring.hometask.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.NavigableSet;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper=false)
@Getter @Setter
public class User extends DomainObject {

    private String firstName;
    private String lastName;
    private String email;
    private NavigableSet<Ticket> tickets = new TreeSet<>();
}
