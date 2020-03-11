package org.o7planning.thymeleaf.logic;

import java.sql.SQLException;
import java.util.List;

import org.o7planning.thymeleaf.dao.Person_Dao;
import org.o7planning.thymeleaf.dao.impl.Person_DaoImpl;
import org.o7planning.thymeleaf.model.Person;

public class Person_Logic {
    private Person_Dao dao;

    public Person_Logic() {
        this.dao = new Person_DaoImpl();
    }

    public List<Person> getPersons() throws SQLException {
        try {
            // this.validateData(firstName, lastName, age, sex, email, address);
            return this.dao.getPersons();
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean insertPersons(List<Person> persons)
            throws SQLException {
        try {
            return this.dao.insertPersons(persons);
        } catch (SQLException e) {
            throw e;
        } finally {
        }
    }

    public boolean validateData(String firstName, String lastName, int i, int j, String email, String address) {
        return true;
    }

}