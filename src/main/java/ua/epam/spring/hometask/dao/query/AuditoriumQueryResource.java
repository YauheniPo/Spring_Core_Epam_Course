package ua.epam.spring.hometask.dao.query;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

import static ua.epam.spring.hometask.AppConsts.QUERY_RESOURCE;

@Sources(value = {"classpath:" + QUERY_RESOURCE})
public interface AuditoriumQueryResource extends Config {

    String auditoriumsGetByName();

    String auditoriumsGetAll();

}