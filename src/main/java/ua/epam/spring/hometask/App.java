package ua.epam.spring.hometask;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.User;

@Log4j2
@Component
@Scope(value = "singleton")
public class App implements ApplicationListener {

    @Autowired
    @Qualifier(value = "auditoriumDaoImpl")
    private AuditoriumDao auditoriumsDao;

    @Autowired
    @Qualifier(value = "user")
    private User user;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        App app = ctx.getBean("app", App.class);


        ctx.close();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info(applicationEvent.toString());
    }
}
