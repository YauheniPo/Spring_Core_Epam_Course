package ua.epam.spring.hometask.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Log4j2
@Component
class DbInitialization {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void createTables() {
        final DataSource datasource = jdbcTemplate.getDataSource();
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(datasource).getConnection();
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("sql/users_table.sql"), StandardCharsets.UTF_8));
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("sql/auditoriums_table.sql"), StandardCharsets.UTF_8));
        } catch (SQLException e) {
            log.error(e);
        } finally {
            try {
                Objects.requireNonNull(connection).close();
            } catch (SQLException e) {
                log.error(e);
            }
        }

        setDataTables();
    }

    private void setDataTables() {
        final DataSource datasource = jdbcTemplate.getDataSource();
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(datasource).getConnection();
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("sql/users_data.sql"), StandardCharsets.UTF_8));
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("sql/auditoriums_data.sql"), StandardCharsets.UTF_8));
        } catch (SQLException | ScriptStatementFailedException e) {
            log.error(e);
        } finally {
            try {
                Objects.requireNonNull(connection).close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
    }
}
