package org.o7planning.thymeleaf.service;

import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.model.Person;

public interface Person_Service {

    /**
     * @return
     * @throws SQLException
     */
    public List<Person> getPersonsList() throws SQLException;

    /**
     * @param persons
     * @return
     * @throws SQLException
     */
    public boolean insertPersons(List<Person> persons) throws SQLException;
}
