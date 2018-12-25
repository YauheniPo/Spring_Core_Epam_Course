package ua.epam.spring.hometask.dao.query;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;

import static ua.epam.spring.hometask.AppConsts.QUERY_RESOURCE;

@Sources(value = {"classpath:" + QUERY_RESOURCE})
public interface UserQueryResource extends Config {

    String usersGetByEmail();

    String usersGetById();

    String usersGetAll();

    String usersDelete();

    String usersSave();

}