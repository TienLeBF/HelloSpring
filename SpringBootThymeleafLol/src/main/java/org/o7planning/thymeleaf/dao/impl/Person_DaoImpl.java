package org.o7planning.thymeleaf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.o7planning.thymeleaf.dao.Person_Dao;
import org.o7planning.thymeleaf.model.Person;

public class Person_DaoImpl implements Person_Dao {

    @Override
    public List<Person> getUser(Connection connection) throws SQLException {
        try {
            String query = "SELECT \n "
                    + "    first_name, last_name, age, sex, email, address \n "
                    + "FROM \n "
                    + "    spring.person";
            PreparedStatement preStatement = connection.prepareStatement(query);
            ResultSet resultSet = preStatement.executeQuery();
            List<Person> persons = new ArrayList<Person>();
            Person person = null;
            while (null != resultSet && resultSet.next()) {
                person = new Person();
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setAge(resultSet.getShort("age"));
                person.setSex(resultSet.getShort("sex"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));

                persons.add(person);
            }

            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean inserPersons(Connection connection, List<Person> persons) throws SQLException {
        try {
            String query = "";

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

}
