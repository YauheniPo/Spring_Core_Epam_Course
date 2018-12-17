package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.dao.impl.UserDaoImpl;
import ua.epam.spring.hometask.domain.User;

import javax.sql.DataSource;
import java.util.HashSet;

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

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(url, username, password);
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

    @Bean(name = "userDaoImpl")
    public UserDao getUserDaoImpl() {
        return new UserDaoImpl(new HashSet(){{add(user);}});
    }
}
