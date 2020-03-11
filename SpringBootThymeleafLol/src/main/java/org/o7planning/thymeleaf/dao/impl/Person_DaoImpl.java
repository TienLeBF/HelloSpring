package org.o7planning.thymeleaf.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.o7planning.thymeleaf.SpringBootThymeleafLolApplication;
import org.o7planning.thymeleaf.dao.Person_Dao;
import org.o7planning.thymeleaf.model.Person;

public class Person_DaoImpl implements Person_Dao {

    @Override
    public List<Person> getPersons() throws SQLException {
        Connection connection = SpringBootThymeleafLolApplication.MYSQL_SPRING.getConnection();
        try {
            String query = "SELECT \n " + "    first_name, last_name, age, sex, email, address \n " + "FROM \n "
                    + "    spring.persons";
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
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }

    @Override
    public boolean insertPersons(List<Person> persons) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            String query = "INSERT INTO spring.persons \n"
                    + "\t(created_at, first_name, last_name, age, sex, email, address)\n"
                    + "VALUES\n"
                    + "\t(?, ?, ?, ?, ?, ?, ?)";
            connection = SpringBootThymeleafLolApplication.MYSQL_SPRING.getConnection();
            preparedStatement = connection.prepareStatement(query);
            Date createAt = new Date();
            for (Person person : persons) {
                preparedStatement.setTimestamp(1, new Timestamp(createAt.getTime()));
                preparedStatement.setString(2, person.getFirstName());
                preparedStatement.setString(3, person.getLastName());
                preparedStatement.setShort(4, person.getAge());
                preparedStatement.setShort(5, person.getSex());
                preparedStatement.setString(6, person.getEmail());
                preparedStatement.setString(7, person.getAddress());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }
}
