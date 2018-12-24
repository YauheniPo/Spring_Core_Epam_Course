package ua.epam.spring.hometask.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;

import static ua.epam.spring.hometask.AppConsts.QUERY_RESOURCE;

@Sources(value = {"classpath:" + QUERY_RESOURCE})
public interface DbQueryResource extends Config {

    String usersGetByEmail();

    String usersGetById();

    String usersGetAll();

    String usersDelete();

    String usersSave();

    String auditoriumsGetByName();

    String auditoriumsGetAll();

}