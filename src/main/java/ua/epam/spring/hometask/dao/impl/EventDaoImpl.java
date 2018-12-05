package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.Setter;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.domain.Event;

import java.util.Collection;
import java.util.Set;

@Setter @Getter
public class EventDaoImpl implements EventDao {

    private Set<Event> events;

    @Override
    public Event save(Event event) {
        events.add(event);
        return event;
    }

    @Override
    public void remove(Event event) {
        events.remove(event);
    }

    @Override
    public Event getById(Long id) {
        for (Event event : events) {
            if (id.equals(event.getId())) {
                return event;
            }
        }
        return null;
    }

    @Override
    public Collection<Event> getAll() {
        return events;
    }

    @Override
    public Event getByName(String name) {
        for (Event event : events) {
            if (name.equals(event.getName())) {
                return event;
            }
        }
        return null;
    }
}
