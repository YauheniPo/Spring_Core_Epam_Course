package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class DbInitialization {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void createTables() {
        try {
//            jdbcTemplate.execute("");
        } catch (Exception ex) {
            throw ex;
        }
    }
}
