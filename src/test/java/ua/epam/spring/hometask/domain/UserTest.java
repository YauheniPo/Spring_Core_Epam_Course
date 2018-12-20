package ua.epam.spring.hometask.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import ua.epam.spring.hometask.BaseTest;
import ua.epam.spring.hometask.dao.UserDao;

@ContextConfiguration(locations = {"classpath:**/users_test.xml"})
public class UserTest extends BaseTest {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("user_test")
    private User user;

    @Test
    public void testRemove() {
        userDao.save(user);
        int userCount = userDao.getAll().size();
        userDao.remove(user);
        assertEquals(userCount - 1, userDao.getAll().size());
    }

    @Test
    public void testSave() {
        int userCount = userDao.getAll().size();
        userDao.save(user);
        assertEquals(userCount + 1, userDao.getAll().size());
    }

    @Test
    public void testGetAll() {
        assertEquals(userDao.getAll().size(), 1);
    }
}
