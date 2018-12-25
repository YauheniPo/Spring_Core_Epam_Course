package ua.epam.spring.hometask.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.Auditorium;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AuditoriumMapper implements RowMapper<Auditorium> {

    @Override
    public Auditorium mapRow(ResultSet rs, int rowNum) throws SQLException {
        Auditorium auditorium = new Auditorium();

        auditorium.setId(rs.getLong("id"));
        auditorium.setName(rs.getString("name"));
        auditorium.setNumberOfSeats(rs.getLong("number_of_seats"));
        auditorium.setVipSeats(Arrays.stream((Integer[])(rs.getArray("vip_seats").getArray())).collect(Collectors.toSet()));

        return auditorium;
    }
}
