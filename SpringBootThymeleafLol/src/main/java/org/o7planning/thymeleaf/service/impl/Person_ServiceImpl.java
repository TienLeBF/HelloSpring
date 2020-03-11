package org.o7planning.thymeleaf.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.logic.Person_Logic;
import org.o7planning.thymeleaf.model.Person;
import org.o7planning.thymeleaf.service.Person_Service;

public class Person_ServiceImpl implements Person_Service {
    private Person_Logic logic;

    public Person_ServiceImpl() {
        this.logic = new Person_Logic();
    }

    @Override
    public List<Person> getPersonsList() throws SQLException {
        try {
            return this.logic.getPersons();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean insertPersons(List<Person> persons) throws SQLException {
        try {
            return this.logic.insertPersons(persons);
        } catch (SQLException e) {
            throw e;
        }
    }


}
