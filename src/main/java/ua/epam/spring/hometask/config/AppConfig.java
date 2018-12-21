package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.dao.impl.UserDaoImpl;
import ua.epam.spring.hometask.domain.User;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;

@Configuration
@PropertySources({
//        @PropertySource("classpath:data/**/*.properties"),
        @PropertySource("classpath:db.properties"),
})
@ComponentScan("ua.epam.spring.hometask")
@ImportResource("classpath:spring.xml")
public class AppConfig {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    public DataSource dataSource() {
        final DataSource datasource = new DriverManagerDataSource(url, username, password);
        Connection connection = null;
        try {
            connection = datasource.getConnection();
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("sql/table_users.sql"), StandardCharsets.UTF_8));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(connection).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return datasource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public User getUser() {
        return new User();
    }

    @Autowired
    private User user;

    @Bean(name = "userDao")
    public UserDao getUserDao() {
        return new UserDaoImpl(new HashSet(){{add(user);}});
    }

    @Bean
    public DbInitialization dbInitialization() {
        return new DbInitialization();
    }
}
