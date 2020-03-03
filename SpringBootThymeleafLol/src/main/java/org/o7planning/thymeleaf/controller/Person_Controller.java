package org.o7planning.thymeleaf.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.o7planning.thymeleaf.model.Person;
import org.o7planning.thymeleaf.service.Person_Service;
import org.o7planning.thymeleaf.service.impl.Person_ServiceImpl;

public class Person_Controller {
    private Person_Service service;
    private static final Logger LOG = Logger.getLogger(Person_Controller.class.getSimpleName());

    public Person_Controller() {
        LOG.info("create a new Person_Controller");
        this.service = new Person_ServiceImpl();
    }

    /**
     * @return
     */
    public List<Person> getListPersons() {
        try {
            LOG.info("Person_Controller -> getListPersons -> is called");
            return this.service.getListPersons();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return
     */
    public boolean insertPersons(List<Person> persons) {
        try {
            LOG.info("Person_Controller -> insertPersons -> is called");
            return this.service.insertListPersons(persons);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void test() {

        List<Person> persons = this.getListPersons();
        int count = 0;
        for (Person person : persons) {
            LOG.info("\n****************** person " + ++count + " ******************\n");
            LOG.info(person.toString());
        }
    }
}
