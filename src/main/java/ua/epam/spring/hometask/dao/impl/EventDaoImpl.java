package ua.epam.spring.hometask.dao.impl;

import lombok.Getter;
import lombok.Setter;
import org.aeonbits.owner.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.EventDao;
import ua.epam.spring.hometask.dao.query.EventQueryResource;
import ua.epam.spring.hometask.domain.Event;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Component
@Setter @Getter
public class EventDaoImpl implements EventDao {

    private EventQueryResource eventQueryResource = ConfigFactory.create(EventQueryResource.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Event save(Event event) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(
                    eventQueryResource.eventsSave(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getName());
            ps.setDouble(2, event.getBasePrice());
            ps.setString(3, event.getRating().name());
            return ps;
        }, holder);
        event.setId(Long.parseLong(String.valueOf(holder.getKeyList().get(0).get("id"))));
        return event;
    }

    @Override
    public void remove(Event event) {
        jdbcTemplate.update(eventQueryResource.eventsDelete(), event.getId());
    }

    @Override
    public Event getById(Long id) {
        return jdbcTemplate.queryForObject(eventQueryResource.eventsGetById(), new Object[]{id}, new BeanPropertyRowMapper<>(Event.class));
    }

    @Override
    public Collection<Event> getAll() {
        return jdbcTemplate.query(eventQueryResource.eventsGetAll(), new BeanPropertyRowMapper<>(Event.class));
    }

    @Override
    public Event getByName(String name) {
        return jdbcTemplate.queryForObject(eventQueryResource.eventsGetByName(), new Object[]{name}, new BeanPropertyRowMapper<>(Event.class));
    }
}
