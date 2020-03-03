package org.o7planning.thymeleaf.service;

import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.model.Person;

public interface Person_Service {

    /**
     * @return
     * @throws SQLException
     */
    public List<Person> getListPersons() throws SQLException;

    /**
     * @param persons
     * @return
     * @throws SQLException
     */
    public boolean insertListPersons(List<Person> persons) throws SQLException;
}
